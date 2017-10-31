/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author Alecto
 */
public class MainController implements Initializable, CaveController {
    
    @FXML
    private ScrollPane leftScroll;
    @FXML
    private VBox leftPane;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ToolBar topToolBar;
    @FXML
    private ScrollPane centerScroll;
    @FXML
    private GridPane centerGrid;
    @FXML
    private HBox resBox;
    @FXML
    private Label entityDetailName;
    @FXML
    private Label entityCurrentTask;
    @FXML
    private Button cancelCurrentTask;
    @FXML
    private ScrollPane traitsScroll;
    @FXML 
    private ScrollPane tasksScroll;
    @FXML
    private VBox traitsVBox;
    @FXML
    private VBox tasksVBox;
    @FXML
    private Label roomDetailName;    
    @FXML
    private Button cancelCurrentRoomTask;
    @FXML
    private Label roomDetailStatus;    
    @FXML
    private VBox roomTraits;
    @FXML
    private VBox roomNeighbors;
    @FXML
    private VBox roomTasks;
    @FXML
    private Button eventDetailCloseButton;
    @FXML
    private Label eventDetailName;
    @FXML
    private StackPane eventBodyStack;
    @FXML
    private AnchorPane eventBodyAnchorA;
    @FXML
    private TextArea eventDetailBodyA;
    @FXML
    private SplitPane eventBodySplitB;
    @FXML
    private ListView<GameEvent> eventBodyListB;
    @FXML
    private VBox eventBodyVBoxB;
    
    
    private Stage eventDetail;
    private Stage roomDetail;
    private Stage entityDetail;
    private Stage primaryStage;
    private Engine motor;
    private ListChangeListener resListener;
    private ArrayList<Pair<ActiveEntity, Button>> entitiesToButtons = new ArrayList();
    private static final int SQUARESIZE = 100;
    private static final int ROOM_COLUMNS = 10;
    private static final int ROOM_ROWS = 10;
    private int gridX = 0;
    private int gridY = 0;
    private int roomCount = 0;
    private boolean init = false;
    private Trait.trait_type[] defaultPriorities = {Trait.trait_type.ATTRIBUTE, Trait.trait_type.PRODUCTION, Trait.trait_type.COMBAT, Trait.trait_type.FLAVOR};
    private StringProperty stringProp;
    private boolean mustOfferUpdateWarnings = true;
    
    public MainController(Stage initPrimary) {
        primaryStage = initPrimary;
        setUpDefaultMotor();
        motor.setController(this);
        setUpDefaultDetail();
        setUpDefaultRooms();
    }
    
    public MainController(Stage initPrimary, Engine initEngine) {
        primaryStage = initPrimary;
        motor = initEngine;
        motor.setController(this);
        setUpDefaultDetail();
        setUpDefaultRooms();
    }
    
    public MainController(Stage initPrimary, Engine initEngine, Stage initDetails) {
        primaryStage = initPrimary;
        motor = initEngine;
        entityDetail = initDetails;
        setUpDefaultRooms();
        motor.setController(this);
    }
    
    private void setUpDefaultMotor() {
        motor = new AntHillEngine();
    }
    
    private void setUpDefaultDetail() {
        entityDetail = new Stage();
        
        HBox hb = new HBox();
        Button b = new Button("Close");
        
        hb.getChildren().addAll(b);
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(init) {
            return;
        }
        
        setUpResources();
        setUpTopPane();
        setUpCenterGrid();
        setUpResBox();
        setUpResourceListener();
        
        init = true;
    }    

    private void setUpResources() {
        motor.addResource("Food", 10, "For eating");
        motor.addResource("Food", 10, "Should not be used");
        motor.addResource("Light bulbs", 2, "Bright idea");
        motor.addResource("Enemies", 15, "Foes");
        motor.addResource("Morale", -3, "Meh");
    }
    
    private void setUpUpdateWarningListener() {
        eventBodyListB.getFocusModel().focusedItemProperty().addListener((ObservableValue<? extends GameEvent> observable, GameEvent oldValue, GameEvent newValue) -> {
            eventBodyVBoxB.getChildren().clear();
            if(newValue == null) {
                return;
            }

            Label eventTitle = new Label(newValue.title);
            eventTitle.setWrapText(true);
            eventBodyVBoxB.getChildren().add(eventTitle);
            eventBodyVBoxB.getChildren().add(new Label());
            Label eventBody = new Label(newValue.body);
            eventBody.setWrapText(true);
            eventBodyVBoxB.getChildren().add(eventBody);
        });

        eventBodyListB.setCellFactory((ListView<GameEvent> temp) -> {
            return new WarningCell();
        });
    }
    
    private void setUpDefaultRooms() {
        
        for(int i = 0; i < ROOM_ROWS * ROOM_COLUMNS; i++) {
            motor.addRoom(new Room());
        }
    }
    
    private void setUpTopPane() {
        
        
    }

    private void setUpCenterGrid() {        
        for(Node n : centerGrid.getChildren()) {
            GridPane.setHgrow(n, Priority.ALWAYS);
            GridPane.setVgrow(n, Priority.ALWAYS);
        }
        
        centerGrid.getColumnConstraints().clear();
        
        for(int i = 0; i < ROOM_COLUMNS; i++) {
            ColumnConstraints c = new ColumnConstraints();
            c.setFillWidth(true);
            c.setHgrow(Priority.ALWAYS);
            centerGrid.getColumnConstraints().add(c);
        }
        
        centerGrid.getRowConstraints().clear();
        
        for(int i = 0; i < ROOM_ROWS; i++) {
            RowConstraints r = new RowConstraints();
            r.setFillHeight(true);
            r.setVgrow(Priority.ALWAYS);
            centerGrid.getRowConstraints().add(r);
        }
        
        populate();
        populateRooms();
        
        centerGrid.setGridLinesVisible(false);
        
        centerScroll.setFitToHeight(true);
        centerScroll.setFitToWidth(true);
    }
    
    public void populate() {
        
        Button killButton = new Button("Remove Foo");
        killButton.setOnAction((ActionEvent e) -> {
            removeActive(motor.getResourceValue("Foo"));
        });
        topToolBar.getItems().add(killButton);
        
        for(int i = 0; i < 5; i++) {
            addActive(motor.getBuilder().makeEntity(AntBuilder.WORKER + "," + AntBuilder.ANT, null));
        }
        
        addActive(motor.getBuilder().makeEntity(AntBuilder.SOLDIER + "," + AntBuilder.ANT, "Soldier 1"));
        
        ActiveEntity test = motor.getBuilder().makeEntity(AntBuilder.SOLDIER + "," + AntBuilder.ANT, "Soldier 2");
        test.getTraits().add(new Trait());
        test.getTraits().add(new Trait("Test Trait 1", 55, EnumSet.of(Trait.trait_type.FLAVOR)));
        test.getTraits().add(new Trait("Test Trait 2", 123456789, EnumSet.of(Trait.trait_type.FLAVOR)));
        test.getTraits().add(new Trait("Test Trait 3", 0, EnumSet.of(Trait.trait_type.FLAVOR)));
        test.getTraits().add(new Trait("Test Trait 4 with a longer name", 42, EnumSet.of(Trait.trait_type.FLAVOR)));
        addActive(test);
        
        addActive(motor.getBuilder().makeEntity(AntBuilder.QUEEN + "," + AntBuilder.ANT, "Victoria"));
        addActive(motor.getBuilder().makeEntity(AntBuilder.SOLDIER + "," + AntBuilder.DRONE + "," + AntBuilder.ANT, "Fight&Love"));
    }
    
    public void fillLeftPane() {
        leftPane.getChildren().clear();
        for(Pair<ActiveEntity, Button> p : entitiesToButtons) {
            leftPane.getChildren().add(p.getValue());
        }
    }
    
    // "surface" Rooms are marked reachable
    @Override
    public void addRoom(Room r) {
        Button rB = new Button();
        motor.setRoom(r, roomCount);
        Room addedRoom = motor.getRooms().get(roomCount);
        
        addedRoom.setxPos(gridX);
        addedRoom.setyPos(gridY);
        addedRoom.setRoomNumber(roomCount);
        if(addedRoom.getId().equalsIgnoreCase("surface")) {
            addedRoom.setReachable(true);
            rB.setId("room-button");
        }
        else {
            addedRoom.setReachable(false);
            rB.setId("room-button-unreachable");
        }
        
        rB.setGraphic(RoomSummary.getSummary(addedRoom));
        rB.setOnAction((ActionEvent e) -> {
            showRoomWindow(addedRoom);
        });
        
        addedRoom.getReachableProp().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue.booleanValue()) {
                rB.setId("room-button-unreachable");
            }
            else {
                if(addedRoom.getType() == Room.roomType.WALL) {
                    rB.setId("room-button-semivalid");
                }
                else {
                    rB.setId("room-button");
                }
            }
        });
      
        // Establish neighbors
        if(addedRoom.getyPos() > 0) {
            addedRoom.setNorthNeighbor(motor.getRooms().get(addedRoom.getRoomNumber() - ROOM_COLUMNS));
        }
        
        if(addedRoom.getxPos() < ROOM_COLUMNS - 1) {
            addedRoom.setEastNeighbor(motor.getRooms().get(addedRoom.getRoomNumber() + 1));
        }
        
        if(addedRoom.getyPos() < ROOM_ROWS - 1) {
            addedRoom.setSouthNeighbor(motor.getRooms().get(addedRoom.getRoomNumber() + ROOM_COLUMNS));
        }
        
        if(addedRoom.getxPos() > 0) {
            addedRoom.setWestNeighbor(motor.getRooms().get(addedRoom.getRoomNumber() - 1));
        }
        
        // Add button, prepare new coordinates for next Room.
        centerGrid.add(rB, gridX, gridY);
        gridX++;
        
        if(gridX >= ROOM_COLUMNS) {
            gridX = 0;
            gridY++;
        }
        
        if(gridY >= ROOM_ROWS) {
            // no change
        }
        
        roomCount++;
    }
    
    @Override
    public void removeRoom(Room r) {
        motor.setRoomEmpty(r);
    }
    
    public void updateNeighborhood() {
        List<Room> rooms = motor.getReachableRooms();
        
        for(Room r : rooms) {
            updateNeighbors(r);
        }
        
    }
    
    public void updateNeighbors(Room r) {
        //r.getxPos() < 0 || r.getyPos() < 0 || r.getxPos() >= ROOM_COLUMNS || r.getyPos() >= ROOM_ROWS
        // Do we care about out of bounds?
        
        //System.out.println("UpdateNeighbors for " + r.getRoomName() + "," + r.getRoomNumber());
        
        // No effect if unreachable. Reachability does not go through walls
        if(!r.isReachable() || r.getType() == Room.roomType.WALL) {
          //  System.out.println(r.getRoomName() + " is unreachable or a wall");
            return;
        }
        
//        for(int i = 0; i < r.getNeighbors().length; i++) {
//            if(r.getNeighbors()[i] == null) {
//                System.out.print(i + " is null, ");
//            }
//            else {
//                System.out.print(i + " is " + r.getNeighbors()[i].getRoomName() + "," + r.getNeighbors()[i].getRoomNumber() + "; ");
//            }
//        }
//        System.out.println();
        
        for(Room neighbor : r.getNeighbors()) {
            // A non-existant neighbor, or one that is already reachable needs no attention
            if(neighbor == null || neighbor.isReachable()) {
            //    System.out.println("... is null or already reachable");
                continue;
            }
            //System.out.print(" Neighbor " + neighbor.getRoomName() + ", " + neighbor.getRoomNumber() + " of " + r.getRoomName());            
            // Reachability does not go through walls
            neighbor.setReachable(true);
            if(neighbor.getType() == Room.roomType.WALL) {
                continue;
            }
            
            updateNeighbors(neighbor);
        }
    }
    
    @Override
    public void addActive(ActiveEntity a) {
        motor.addActiveEntity(a);
        Button testButton = new Button();
        testButton.setGraphic(EntitySummary.getSummary(a));
        testButton.setId("summary-button");
            
        testButton.setOnAction((ActionEvent e) -> {
            showDetailWindow(a);
        });
        
        entitiesToButtons.add(new Pair(a, testButton));
        fillLeftPane();
    }
    
    @Override
    public void removeActive(ActiveEntity a) {
        for(int i = 0; i < entitiesToButtons.size(); i++) {
            if(entitiesToButtons.get(i).getKey().equals(a)) {
                entitiesToButtons.remove(i);
                motor.removeActiveEntity(a);
                fillLeftPane();
                return;
            }
        }
    }
    
    @Override
    public void removeActive(int index) {
        if(index >= entitiesToButtons.size() || index < 0) {
            return;
        }
        
        removeActive(entitiesToButtons.get(index).getKey());
    }
    
    @Override
    public void removeByID(String id) {
        for(int i = 0; i < entitiesToButtons.size(); i++) {
            if(entitiesToButtons.get(i).getKey().getId().equals(id)) {
                removeActive(entitiesToButtons.get(i).getKey());
                return;
            }
        }
    }
    
    public void populate1() {
        //String id, String name, String location, List<Task> newTasks, String idleText, List<Trait> traits)
        //addActive(motor.getBuilder().makeEntity(AntBuilder.QUEEN, "Victoria", null));
        //(String initName, int initDuration, String initGerund, Trait[] initCosts, Trait[] initRequirements, Trait[] initResults, String initFlavor)
        ArrayList<Task> testTasks = new ArrayList();
        Task assemble = new Task("Assemble Contraption", 1, "Cobbling",
            new Trait[]{new Trait("Doodad", -1, EnumSet.of(Trait.trait_type.RESOURCE))},
            new Trait[]{new Trait("Doodad", 0, TraitBuilder.reqGreaterThanResource())},
            new Trait[]{new Trait("Contraption", 1, TraitBuilder.resource())},
            "X's and 0's!");
        testTasks.add(assemble);
        Task scavenge = new Task("Scavenge Doodad", 1, "Scavenging",
            new Trait[]{new Trait("Time", -1, TraitBuilder.resource())},
            new Trait[]{new Trait("Nonsense", 1000, TraitBuilder.reqLessThanResource())},
            new Trait[]{new Trait("Doodad", 1, TraitBuilder.resource())},
            "Lots of lovely filth down here!");
        testTasks.add(scavenge);
        
        testTasks.add(AntTaskBuilder.larvaCare());
        
        Task makeLarva = new Task("Make Larva", 1, "Larvaing",
            null,
            null,
            new Trait[]{new Trait("Larva", 3, TraitBuilder.creation())},
            "test making a larva");
        testTasks.add(makeLarva);
        
        Task larvaLink = new Task("Work with larva", 1, "Synergy!",
            new Trait[]{new Trait("Time", -1, TraitBuilder.resource()), new Trait("Synergy", -6, TraitBuilder.resource())},
            new Trait[]{new Trait("Larva", 2, TraitBuilder.reqGreaterThanCreationLink()), new Trait("Food", 0, TraitBuilder.reqGreaterThanResource()),
                new Trait("Synergy", 5, TraitBuilder.reqGreaterThanResource())},
            new Trait[]{new Trait("Success", 1, TraitBuilder.resource())},
            "Test linked task");
        testTasks.add(larvaLink);
        
        Task roomTask = new Task("Go where you belong", 1, "Being trash",
            null,
            new Trait[]{new Trait("Trash", 0, TraitBuilder.reqGreaterThanRoom())},
            new Trait[]{new Trait("Success", 1, TraitBuilder.resource())},
            "This is where you belong forever!");
        testTasks.add(roomTask);
        
        Task winterTask = new Task("Stay in winter quarters", 1, "Hibernating",
            null,
            new Trait[]{new Trait("Winter", 0, TraitBuilder.reqGreaterThanRoom())},
            new Trait[]{new Trait("Success", 1, TraitBuilder.resource())},
            "Get toasty");
        testTasks.add(winterTask);
        
        ArrayList<Trait> testTraits = new ArrayList();
        Trait trait1 = new Trait("Cuteness", 8, "uguu", TraitBuilder.flavor());
        trait1.setSortingPriority(0);
        testTraits.add(trait1);
        
        Trait str = new Trait("Strength", 12, "blah", TraitBuilder.attribute());
        str.setSortingPriority(1);
        Trait dex = new Trait("Dexterity", 12, "blah", TraitBuilder.attribute());
        dex.setSortingPriority(2);
        Trait con = new Trait("Constitution", 12, "blah", TraitBuilder.attribute());
        con.setSortingPriority(3);
        Trait intellect = new Trait("Intelligence", 12, "blah", TraitBuilder.attribute());
        intellect.setSortingPriority(4);
        Trait wis = new Trait("Wisdom", 12, "blah", TraitBuilder.attribute());
        wis.setSortingPriority(5);
        Trait cha = new Trait("Charmisma", 12, "blah", TraitBuilder.attribute());
        cha.setSortingPriority(6);
        testTraits.add(cha);
        testTraits.add(str);
        testTraits.add(wis);
        Trait noValue = new Trait("Should come before Cuteness, no value", 100, "En guarde!", TraitBuilder.combat());
        noValue.setShowValue(false);
        testTraits.add(noValue);
        testTraits.add(dex);
        testTraits.add(intellect);
        testTraits.add(con);
        testTraits.add(new Trait("Shoudle be last", 2, "foo", TraitBuilder.resource()));
        testTraits.add(new Trait("Synergy per day", 1, "Synergy +1\n\nBefore combat", TraitBuilder.resourceProduction()));
        Trait.trait_type[] priorities = new Trait.trait_type[]{Trait.trait_type.ATTRIBUTE, Trait.trait_type.COMBAT, Trait.trait_type.FLAVOR};
        
        ActiveEntity test = new ActiveEntity("DRONE,ANT", "Tester", null, testTasks, "doo dee doo", testTraits);
        addActive(test);
        
        ActiveEntity test2 = new ActiveEntity();
        test2.setId("DRONE,ANT");
        addActive(test2);
        
        ActiveEntity test3 = new ActiveEntity();
        test3.setId("DRONE,ANT");
        testTraits.add(dex);
        testTraits.add(con);
        testTraits.add(con);
        testTraits.add(con);
        test3.setTraits(testTraits);
        addActive(test3);
        
        ActiveEntity test4 = new ActiveEntity();
        test4.setId("DRONE,ANT");
        test4.addTrait(new Trait());
        test4.addTrait(new Trait("sdadsd", -4, TraitBuilder.eachturn()));
        addActive(test4);
        
        
        Button killButton = new Button("Remove Foo");
        killButton.setOnAction((ActionEvent e) -> {
            removeActive(motor.getResourceValue("Foo"));
        });
        topToolBar.getItems().add(killButton);
//        System.out.println(task1.getName() + " requirements met? " + motor.requirementsMet(task1, test));
//        System.out.println(task2.getName() + " requirements met? " + motor.requirementsMet(task2, test));
//        System.out.println("Do I have greater than zero doodads? " + motor.greaterThanReqMet(task1.getRequirements().get(0), test));
//        System.out.println("Do I have greater than zero doodads? " + motor.greaterThanReqMet(new Trait("Doodad", 0, TraitBuilder.reqGreaterThanResource), test));
//        System.out.println("Adding 1 Doodad");
//        motor.addResource("Doodad", 1);
//        System.out.println(task1.getName() + " requirements met? " + motor.requirementsMet(task1, test));
//        System.out.println(task2.getName() + " requirements met? " + motor.requirementsMet(task2, test));
//        System.out.println("Do I have greater than zero doodads? " + motor.greaterThanReqMet(task1.getRequirements().get(0), test));
//        System.out.println("Do I have greater than zero doodads? " + motor.greaterThanReqMet(new Trait("Doodad", 0, TraitBuilder.reqGreaterThanResource), test));
//        
//        System.out.println(test.getName() + " has " + test.getTasks().size() + " tasks");
//        for(Task bar : test.getTasks()) {
//            for(Trait req : bar.getRequirements()) {
//                Trait.trait_type[] types = req.getTypes();
//            
//                for(Trait.trait_type foo : types) {
//                    System.out.print(foo + " ");
//                }
//            
//                System.out.println(req.getName() + " " + TraitEvaluator.requirementCondition(req) + "  " + req.getValue());
//                System.out.println("------------");
//            }
//        }
//        
//        
//        System.out.println(task3.getName() + " requirements met? " + motor.requirementsMet(task3, test));
//        System.out.println("Larva req " + test.getTasks().get(2).getRequirements().get(0).getName() + " met: " + 
//                motor.greaterThanReqMet(test.getTasks().get(2).getRequirements().get(0), test));
//        System.out.println("Larva req " + test.getTasks().get(2).getRequirements().get(1).getName() + " met: " + 
//                motor.greaterThanReqMet(test.getTasks().get(2).getRequirements().get(1), test));
        
        
      /*  System.out.println(test.getName() + " has " + test.getTasks().size() + " tasks");
        for(Task bar : test.getTasks()) {
        
            System.out.println(bar.getName() + " requirements met? " + motor.requirementsAllMet(bar, test));
            for(Trait req : bar.getRequirements()) {
                Trait.trait_type[] types = req.getTypes();
            
                System.out.println(req.getName() + " " + TraitEvaluator.requirementCondition(req) + "  " + req.getValue());
                System.out.println("------------");
            }
        }
        System.out.println(motor.lessThanReqMet(new Trait("Nonsense", 1000, TraitBuilder.reqLessThanResource), test));
        System.out.println(motor.getResourceValue("nonsense"));
*/

    }
    
    public void populate2() {
        Button killButton = new Button("Remove Foo");
        killButton.setOnAction((ActionEvent e) -> {
            removeActive(motor.getResourceValue("Foo"));
        });
        topToolBar.getItems().add(killButton);
        
        String testTitle = "Test Event";
        String testBody = "Once upon a time, there was a little girl named Little Red Riding Hood. \n\n"
                + "She always wore a red cape and hood when she went out, so you can see how clever people were about making up nicknames. "
                + "One day, she decided to take a picnic basket of food to her ill grandmother, who lived on the other side of the forest. \n\n"
                + "Her mother packed ...\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                + "Foo\n"
                + "Bar\n"
                + "Baz\n"
                + "Alice\n"
                + "Bob\n";
        GameEvent testEvent = new GameEvent(testTitle, testBody);
        Button eventButton = new Button("Game Event");
        topToolBar.getItems().add(eventButton);
        eventButton.setOnAction((ActionEvent e) -> {
            showEventWindow(testEvent);
        });
        addActive(motor.getBuilder().makeEntity("Queen,Ant", "Elizabeth"));
    }
    
    public void populateRooms() {
        
        for(int i = 0; i < ROOM_COLUMNS; i++) {
            addRoom(AntRoomBuilder.surface());
        }
        
        for(int i = ROOM_COLUMNS; i < ROOM_ROWS * ROOM_COLUMNS; i++) {
            addRoom(AntRoomBuilder.dirtSquare());
        }
        
        updateNeighborhood();
        
        
    }
    
    private Collection<MenuItem> menuOfTasks(ActiveEntity ant) {
        ArrayList<MenuItem> antTasks = new ArrayList<>(ant.getTasks().size());
        
        for(Task t : ant.getTasks()) {
            MenuItem taskMenu = new MenuItem();
            taskMenu.textProperty().bind(t.getNameProp());
            
            taskMenu.setOnAction((ActionEvent e) -> {
                ant.setTaskAndTimer(t);
            });
            
            antTasks.add(taskMenu);
        }
        
        return antTasks;
    }
    
    private void setUpResourceListener() {
        resListener = new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                
                resBoxFill();
//                while(c.next()) {
//                    if(c.wasPermutated()) {
//                        resBoxFill();
//                    }
//                    else if(c.wasUpdated()) {
//                    //hopefully not needed?
//                    }
//                    else if(c.wasReplaced()) {
//                        resBoxFill();
//                    }
//                    else if(c.wasRemoved()) {
//                    
//                    }
//                    else if(c.wasAdded()) {
//                    
//                    }
//                }
            }
            
        };
        
        motor.getGlobalResources().addListener(resListener);
    }
    
    private void resBoxFill() {
        
        resBox.getChildren().clear();
        
        
        for(Trait t : motor.getGlobalResources()) {
            if(TraitEvaluator.isHiddenResource(t)) {
                continue;
            }
            
            HBox resPair = new HBox();
            resPair.setId("res-pair");
            resPair.setSpacing(15);
            
            Label name = new Label();
            name.textProperty().unbind();
            name.textProperty().bind(t.getNameProp());
            name.setId("res-name");
            
            Label value = new Label();
            value.textProperty().unbind();
            value.textProperty().bind(t.getValueProp().asString());
            value.setId("res-value");
            
            resPair.getChildren().add(name);
            resPair.getChildren().add(value);
            resPair.getChildren().add(new Separator(Orientation.VERTICAL));
            
            resBox.getChildren().add(resPair);
        }
    }
    
    private void setUpResBox() {
        
//        Label count = new Label();
//        count.textProperty().bind(motor.getGlobalResources().sizeProperty().asString());
//        resBox.getChildren().add(count);

        resBoxFill();
    }

    public void setUpEntityDetailWindow() {
         
        entityDetail.initOwner(primaryStage);
        entityDetail.initModality(Modality.APPLICATION_MODAL);
        entityDetail.initStyle(StageStyle.UNDECORATED);
        entityDetail.setResizable(false);
        entityDetail.sizeToScene();        
        entityDetail.setTitle("Details");
    }
    
    public void setUpRoomDetailWindow() {
        roomDetail.initOwner(primaryStage);
        roomDetail.initModality(Modality.APPLICATION_MODAL);
        roomDetail.initStyle(StageStyle.UNDECORATED);
        roomDetail.setResizable(false);
        roomDetail.sizeToScene();        
        roomDetail.setTitle("Details");
    }
    
    public void setUpEventDetailWindow() {
        eventDetail.initOwner(primaryStage);
        eventDetail.initModality(Modality.APPLICATION_MODAL);
        eventDetail.initStyle(StageStyle.UNDECORATED);
        eventDetail.setResizable(false);
        eventDetail.sizeToScene();        
    }
    
    public void entityDetailClose(ActionEvent e) {
        entityDetail.close();
    }
    
    public void roomDetailClose(ActionEvent e) {
        roomDetail.close();
    }
    
    public void eventDetailClose(ActionEvent e) {
        eventDetail.close();
    }
    
    public void fooPlus(ActionEvent e) {
        motor.addResource(new Trait("Foo", 1, EnumSet.of(Trait.trait_type.RESOURCE)));
    }
    
    public void fooMinus(ActionEvent e) {
        motor.addResource(new Trait("Foo", -1, EnumSet.of(Trait.trait_type.RESOURCE)));
    }
    
    @Override
    public void updateFired(ActionEvent e) {
        motor.checkUpdateWarnings();
        
        if(!motor.getUnresolvedUpdateWarnings().isEmpty() && mustOfferUpdateWarnings) {
            offerUpdateWarnings();
            return;
        }
        
        motor.update();
        updateNeighborhood();
        
        mustOfferUpdateWarnings = true;
    }
    
    public void offerUpdateWarnings() {
        boolean continueUpdateWarnings = false;
        
        eventBodySplitB.toFront();
        eventBodySplitB.setVisible(true);
        
        eventBodyAnchorA.toBack();
        eventBodyAnchorA.setVisible(false);
        
        eventDetailName.setText("Warnings");
        
        eventBodyListB.getItems().clear();
        eventBodyListB.setItems(FXCollections.observableArrayList(motor.getUnresolvedUpdateWarnings()));
        
        if(!eventDetail.isShowing()) {
            eventDetail.showAndWait();
        }
        
        mustOfferUpdateWarnings = false;
//        for(GameEvent warning : motor.getUnresolvedUpdateWarnings()) {
//            for(Trait result : warning.getResults()) {
//                if(TraitEvaluator.isUncancelable(result)) {
//                    continueUpdateWarnings = true;
//                }
//            }
//        }
    }
    
    @Override
    public void exitFired(ActionEvent e) {
        entityDetail.close();
        roomDetail.close();
        Platform.exit();
    }
    
    public void taskSelected(ActionEvent e) {
        
    }
    
    public void showDetailWindow(ActiveEntity e) {
        
        entityDetailName.setText(e.getName());
        
        fillTraitsPane(e, defaultPriorities);        
        
        if(e.isBusy()) {
            fillTasksPaneBusy(e);
        }
        else {
            fillTasksPane(e);
        }
        
        if(!entityDetail.isShowing()) {
            entityDetail.showAndWait();
        }
    }
    
    public void showRoomWindow(Room r) {
        roomDetailName.setText(r.getName());
        if(r.isReachable()) {
            roomDetailStatus.setText("Reachable");
            fillRoomTasksPane(r);
        }
        else {
            roomDetailStatus.setText("Unreachable");
            fillRoomTasksUnreachable(r);
        }
        
        
        fillRoomTraitsPane(r);
        fillNeighborsPane(r);
        
        if(!roomDetail.isShowing()) {
            roomDetail.showAndWait();
        }
    }
    
    public void showEventWindow(GameEvent g) {
        eventDetailName.setText(g.getTitle());
        eventDetailBodyA.setText(g.getBody());
      
        eventBodySplitB.toBack();
        eventBodySplitB.setVisible(false);
        
        eventBodyAnchorA.toFront();
        eventBodyAnchorA.setVisible(true);
        
        if(!eventDetail.isShowing()) {
            eventDetail.showAndWait();
        }
    }
    
    /**
     * Fill the Traits tab on the entity detail window
     * @param e the ActiveEntity being displayed
     */
    public void fillTraitsPane(ActiveEntity e) {
        traitsVBox.getChildren().clear();
        FlowPane traitsFlow = new FlowPane();
        traitsFlow.setId("detail-trait-flow");
        traitsFlow.setPrefWrapLength(750);
        traitsVBox.getChildren().add(traitsFlow);
        
        for(Trait t : e.getTraits()) {
            VBox traitBox = new VBox();
            traitBox.setId("detail-trait-box");
            Label traitName = new Label();
            traitName.textProperty().bind(t.getNameProp());
            traitName.setId("detail-trait-name");
            Label traitValue = new Label();
            traitValue.textProperty().bind(t.getValueProp().asString());
            traitValue.setId("detail-trait-value");
            
            HBox nameValuePair = new HBox();
            nameValuePair.getChildren().addAll(traitName, traitValue);
            nameValuePair.setId("detail-trait-pair");
            
            traitBox.getChildren().add(nameValuePair);
            
            if(t.getDesc() != null && t.getDesc().length() > 0) {
                Tooltip traitDesc = new Tooltip();
                traitDesc.textProperty().bind(t.getDescProp());
                traitDesc.setId("detail-trait-description");
                Tooltip.install(traitBox, traitDesc);
            }
            
            traitsFlow.getChildren().add(traitBox);
        }
    }
    
    /**
     * Fill the Traits tab on the entity detail window using given display priorities for the traits and to label the
     * groups of trait types. 
     * @param e the ActiveEntity being displayed
     * @param priorities the display priorities to use
     */
    public void fillTraitsPane(ActiveEntity e, Trait.trait_type[] priorities) {
        traitsVBox.getChildren().clear();

        e.getTraits().sort(TraitEvaluator.traitComparator(priorities)); 
       
        int traitGroupCursor = 0;
        int loops = 0;
        while(traitGroupCursor < e.getTraits().size() && loops < 20){
            loops++;
            Label traitTypeLabel = new Label(TraitEvaluator.parseHighestTraitTypeString(e.getTraits().get(traitGroupCursor), priorities));
            traitTypeLabel.setId("detail-trait-group");
            traitsVBox.getChildren().add(traitTypeLabel);
            FlowPane traitsFlow = new FlowPane();
            traitsFlow.setId("detail-trait-flow");
            traitsFlow.setPrefWrapLength(800);
            traitsVBox.getChildren().add(traitsFlow);
            
            for(int traitCursor = traitGroupCursor; traitCursor < e.getTraits().size(); traitCursor++) {
                Trait currentTrait = e.getTraits().get(traitCursor);
                // If we've found a new trait type group, pop out of the for loop so the while loop can add a new Label and Flowpane
                if(!TraitEvaluator.parseHighestTraitTypeString(e.getTraits().get(traitGroupCursor), priorities).equals(
                        TraitEvaluator.parseHighestTraitTypeString(currentTrait, priorities))) {
                    traitGroupCursor = traitCursor;
                    break;
                }
                
                VBox traitBox = new VBox();
                traitBox.setId("detail-trait-box");
                Label traitName = new Label();
                traitName.setText(currentTrait.getName());
                traitName.setId("detail-trait-name");
                HBox nameValuePair = new HBox();
                nameValuePair.setId("detail-trait-pair");
                nameValuePair.getChildren().add(traitName);
                // If the value is not to be shown, don't show it
                if(currentTrait.getShowValue()) {
                    Label traitValue = new Label();
                    traitValue.setText(currentTrait.getValue() + "");
                    traitValue.setId("detail-trait-value");
                    nameValuePair.getChildren().add(traitValue);                
                }
                
                traitBox.getChildren().add(nameValuePair);

                if(currentTrait.getDesc() != null && currentTrait.getDesc().length() > 0) {
                    Tooltip traitDesc = new Tooltip();
                    traitDesc.setText(currentTrait.getDesc());
                    traitDesc.setId("detail-trait-description");
                    Tooltip.install(traitBox, traitDesc);
                }

                traitsFlow.getChildren().add(traitBox);
                
                // If we have reached the end, leave the while loop
                if(traitCursor == e.getTraits().size() - 1) {
                    traitGroupCursor = e.getTraits().size();
                }   
            }
            if(traitGroupCursor < e.getTraits().size()) {
                traitsVBox.getChildren().add(new Separator(Orientation.HORIZONTAL));
            }
        }
    }
    
    public void fillTasksPane(ActiveEntity e) {
        entityCurrentTask.setText("Not busy");
        cancelCurrentTask.setDisable(true);
        
        tasksVBox.getChildren().clear();
        FlowPane tasksFlow = new FlowPane();
        tasksFlow.setId("entity-detail-task-flow");
        tasksFlow.setPrefWrapLength(750);
        tasksVBox.getChildren().add(tasksFlow);
        
        
        for(Task t : e.getTasks()) {
            VBox taskBox = new VBox();
            taskBox.setId("entity-detail-task-box");
            
            HBox nameBox = new HBox();
            nameBox.setId("entity-detail-task-name-pane");
            Label taskName = new Label(t.getName());
            taskName.setId("entity-detail-task-name");            
            
            ButtonBase taskButton = new Button("Start");
            taskButton.setId("entity-detail-task-button");
            if(!motor.requirementsAllMet(t, e)) {
                taskButton.setDisable(true);
            }
            else {
                // Checking if the Task needs to link with others
                boolean link;
                if(t.getRequirements().isEmpty()) {
                    link = false;
                }
                else {
                    link = t.getRequirements().stream().anyMatch((input -> TraitEvaluator.isCreationLinkTrait(input)));
                }
                
                if(link) {
                    taskButton = linkTaskButton(t, e);
                    taskButton.setId("entity-detail-task-button");
                }
                // no task link
                else {
                    taskButton.setOnAction((ActionEvent click) -> {
                        motor.setTaskPayCosts(t, e);
                        fillTasksPaneBusy(e);
                        fillTraitsPane(e);
                    });
                }
            }
            
            nameBox.getChildren().addAll(taskName, taskButton);
            //////////////// DURATION 
            HBox durationBox = new HBox();
            durationBox.setId("entity-detail-task-duration-pane");
            Label durationName = new Label("Duration");
            durationName.setId("entity-detail-task-duration");
            Label durationNumber = new Label(t.getDuration() + "");
            durationNumber.setId("entity-detail-task-duration-number");
            durationBox.getChildren().addAll(durationName, durationNumber);
            /////////////// COSTS
            VBox costBox = new VBox();
            costBox.setId("entity-detail-task-cost-vbox");
            Label costTitle = new Label("Costs");
            costTitle.setId("entity-detail-task-cost-title");
            HBox costsPane = new HBox();
            costsPane.setId("entity-detail-task-cost-pane");
            if(t.getCosts().size() < 1) {
                Label temp = new Label("none");
                temp.setId("entity-detail-task-cost");
                costsPane.getChildren().add(temp);
            }
            else {
                for(Trait cost : t.getCosts()) {
                    Label temp = new Label(cost.toString());
                    temp.setId("entity-detail-task-cost");
                    costsPane.getChildren().add(temp);
                }
            }
            
            costBox.getChildren().addAll(costTitle, costsPane);
            
            ////////////// REQUIREMENTS
            VBox requirementsBox = new VBox();
            requirementsBox.setId("entity-detail-task-requirement-vbox");
            Label requirementsTitle = new Label("Requirements");
            requirementsTitle.setId("entity-detail-task-requirement-title");
            HBox requirementsPane = new HBox();
            requirementsPane.setId("entity-detail-task-requirement-pane");
            ArrayList<Label> reqLabels = new ArrayList<Label>();
            ArrayList<Trait> reqTraits = new ArrayList<Trait>();
            if(t.getRequirements().size() < 1) {
                Label temp = new Label("none");
                temp.setId("entity-detail-task-requirement");
                reqLabels.add(temp);
                requirementsPane.getChildren().add(temp);
            }
            else {
                for(Trait req : t.getRequirements()) {
                    String reqString;
                    Label temp = new Label();
                    switch(TraitEvaluator.requirementCondition(req)) {
                        case EQUALTO:            reqString = req.getName() + " = " + req.getValue(); 
                                                            break;
                        case NOTEQUAL:          reqString = req.getName() + " != " + req.getValue();
                                                            break;
                        case LESSTHAN:          reqString = req.getName() + " < " + req.getValue(); 
                                                            break;
                        case GREATERTHAN:   reqString = req.getName() + " > " + req.getValue(); 
                                                            break;
                        default: reqString = "none";
                    }
                    
                    if(TraitEvaluator.isCreationLinkTrait(req)) {
                        reqString = "Free " + reqString;
                    }
                    
                    temp.setText(reqString);
                    reqLabels.add(temp);
                    reqTraits.add(req);
                    requirementsPane.getChildren().add(temp);
                    
                }
            }
            
            setUpReqs(reqLabels, reqTraits, e);
            requirementsBox.getChildren().addAll(requirementsTitle, requirementsPane);
            
            /////////////////  RESULTS
            VBox resultsBox = new VBox();
            requirementsBox.setId("entity-detail-task-result-vbox");
            Label resultsTitle = new Label("Results");
            resultsTitle.setId("entity-detail-task-result-title");
            HBox resultsPane = new HBox();
            resultsPane.setId("entity-detail-task-result-pane");
            if(t.getResults().size() < 1) {
                Label temp = new Label("none");
                temp.setId("entity-detail-task-result");
                resultsPane.getChildren().add(temp);
            }
            else {
                for(Trait res : t.getResults()) {
                    Label temp = new Label(res.toString());
                    temp.setId("entity-detail-task-result");
                    resultsPane.getChildren().add(temp);
                }
            }
            
            resultsBox.getChildren().addAll(resultsTitle, resultsPane);
            
            taskBox.getChildren().addAll(nameBox, new Separator(Orientation.HORIZONTAL), 
                                                      durationBox, new Separator(Orientation.HORIZONTAL), 
                                                      costBox, new Separator(Orientation.HORIZONTAL), 
                                                      requirementsBox, new Separator(Orientation.HORIZONTAL), 
                                                      resultsBox);
            
            ///////////// FLAVOR
            if(t.getFlavor() != null && t.getFlavor().length() > 0) {
                Tooltip taskDesc = new Tooltip();
                taskDesc.textProperty().bind(t.getFlavorProp());
                taskDesc.setId("entity-detail-task-description");
                Tooltip.install(taskBox, taskDesc);
            }
            
            tasksFlow.getChildren().add(taskBox);
        }
        
    }
    
    public void fillTasksPaneBusy(ActiveEntity e) {
        
        if(motor.isLinked(e) && e.getCurrentTask().getName().equalsIgnoreCase("Link")) {
            entityCurrentTask.setText(e.getCurrentTask().getGerund());
            cancelCurrentTask.setDisable(true);
        }
        else if(motor.currentUncancelableTask(e)) {
            entityCurrentTask.setText(e.getCurrentTask().getGerund());
            cancelCurrentTask.setDisable(true);
        }
        else {
            entityCurrentTask.setText(e.getCurrentTask().getGerund());
            cancelCurrentTask.setDisable(false);
            cancelCurrentTask.setOnAction((ActionEvent click) -> {
                motor.cancelTaskRefundCosts(e);
                fillTasksPane(e);
                fillTraitsPane(e);
            });
        }
        tasksVBox.getChildren().clear();
        FlowPane tasksFlow = new FlowPane();
        tasksFlow.setId("entity-detail-task-flow");
        tasksFlow.setPrefWrapLength(750);
        tasksVBox.getChildren().add(tasksFlow);
        
        
        for(Task t : e.getTasks()) {
            VBox taskBox = new VBox();
            taskBox.setId("entity-detail-task-box");
            
            HBox nameBox = new HBox();
            nameBox.setId("entity-detail-task-name-pane");
            Label taskName = new Label(t.getName());
            taskName.setId("entity-detail-task-name");            
            ButtonBase taskButton = new Button("Busy");
            taskButton.setId("entity-detail-task-button");
            taskButton.setDisable(true);
            nameBox.getChildren().addAll(taskName, taskButton);
            //////////////// DURATION 
            HBox durationBox = new HBox();
            durationBox.setId("entity-detail-task-duration-pane");
            Label durationName = new Label("Duration");
            durationName.setId("entity-detail-task-duration");
            Label durationNumber = new Label(t.getDuration() + "");
            durationNumber.setId("entity-detail-task-duration-number");
            durationBox.getChildren().addAll(durationName, durationNumber);
            /////////////// COSTS
            VBox costBox = new VBox();
            costBox.setId("entity-detail-task-cost-vbox");
            Label costTitle = new Label("Costs");
            costTitle.setId("entity-detail-task-cost-title");
            HBox costsPane = new HBox();
            costsPane.setId("entity-detail-task-cost-pane");
            if(t.getCosts().size() < 1) {
                Label temp = new Label("none");
                temp.setId("entity-detail-task-cost");
                costsPane.getChildren().add(temp);
            }
            else {
                for(Trait cost : t.getCosts()) {
                    Label temp = new Label(cost.toString());
                    temp.setId("entity-detail-task-cost");
                    costsPane.getChildren().add(temp);
                }
            }
            
            costBox.getChildren().addAll(costTitle, costsPane);
            
            ////////////// REQUIREMENTS
            VBox requirementsBox = new VBox();
            requirementsBox.setId("entity-detail-task-requirement-vbox");
            Label requirementsTitle = new Label("Requirements");
            requirementsTitle.setId("entity-detail-task-requirement-title");
            HBox requirementsPane = new HBox();
            requirementsPane.setId("entity-detail-task-requirement-pane");
            if(t.getRequirements().size() < 1) {
                Label temp = new Label("none");
                temp.setId("entity-detail-task-requirement");
                requirementsPane.getChildren().add(temp);
            }
            else {
                for(Trait req : t.getRequirements()) {
                    String reqString;
                    Label temp = new Label();
                    switch(TraitEvaluator.requirementCondition(req)) {
                        case EQUALTO:            reqString = req.getName() + " = " + req.getValue(); 
                                                            break;
                        case NOTEQUAL:          reqString = req.getName() + " != " + req.getValue();
                                                            break;
                        case LESSTHAN:          reqString = req.getName() + " < " + req.getValue(); 
                                                            break;
                        case GREATERTHAN:   reqString = req.getName() + " > " + req.getValue(); 
                                                            break;
                        default: reqString = "none";
                    }
                    
                    if(TraitEvaluator.isCreationLinkTrait(req)) {
                        reqString = "Free " + reqString;
                    }
                    
                    temp.setText(reqString);
                    temp.setId("entity-detail-task-requirement");
                    requirementsPane.getChildren().add(temp);
                    
                }
            }
            
            requirementsBox.getChildren().addAll(requirementsTitle, requirementsPane);
            
            /////////////////  RESULTS
            VBox resultsBox = new VBox();
            requirementsBox.setId("entity-detail-task-result-vbox");
            Label resultsTitle = new Label("Results");
            resultsTitle.setId("entity-detail-task-result-title");
            HBox resultsPane = new HBox();
            resultsPane.setId("entity-detail-task-result-pane");
            if(t.getResults().size() < 1) {
                Label temp = new Label("none");
                temp.setId("entity-detail-task-result");
                resultsPane.getChildren().add(temp);
            }
            else {
                for(Trait res : t.getResults()) {
                    Label temp = new Label(res.toString());
                    temp.setId("entity-detail-task-result");
                    resultsPane.getChildren().add(temp);
                }
            }
            
            resultsBox.getChildren().addAll(resultsTitle, resultsPane);
            
            taskBox.getChildren().addAll(nameBox, new Separator(Orientation.HORIZONTAL), 
                                                      durationBox, new Separator(Orientation.HORIZONTAL), 
                                                      costBox, new Separator(Orientation.HORIZONTAL), 
                                                      requirementsBox, new Separator(Orientation.HORIZONTAL), 
                                                      resultsBox);
            
            ///////////// FLAVOR
            if(t.getFlavor() != null && t.getFlavor().length() > 0) {
                Tooltip taskDesc = new Tooltip();
                taskDesc.textProperty().set(t.getFlavorProp().get());
                taskDesc.setId("entity-detail-task-description");
                Tooltip.install(taskBox, taskDesc);
            }
            
            if(t.equals(e.getCurrentTask())) {
                taskBox.setId("entity-detail-task-box-selected");
            }
            tasksFlow.getChildren().add(taskBox);
        }
    }
    
    public void setUpReqs(ArrayList<Label> reqLabels, ArrayList<Trait> reqTraits, Active e) {
    
        for(int i = 0; i < reqTraits.size(); i++) {
            if(motor.requirementMet(reqTraits.get(i), e)) {
                reqLabels.get(i).setId("entity-detail-task-requirement");
            }
            else {
                reqLabels.get(i).setId("entity-detail-task-requirement-invalid");
            }
        }
    }
    
    /**
     * Fills in the SplitMenuButton for a Task needing to link other entities.
     * It looks at all the entities that aren't busy, and selects the ones that have ID's that contain all the names of the 
     * requirement Traits. The number of required entities to link with is currently set to the highest value among
     * the link requirement Traits.
     * @param t
     * @param e
     * @return 
     */
    public ButtonBase linkTaskButton(Task t, Active e) {
        // Get the Id's that are required and establish how many links are needed, so we can tell when we have enough. 
        // Since we shouldn't have gotten this far without all requirements being met, there should be enough. I don't want 
        // too many chosen, if there are more than needed.
        int neededTempCount = 0;
        ArrayList<String> idList = new ArrayList();
        
        Iterator<Trait> creationLinks = t.getRequirements().stream().filter((input -> TraitEvaluator.isCreationLinkTrait(input))).iterator();
        while(creationLinks.hasNext()) {
            Trait req = creationLinks.next();
            idList.add(req.getName());
            neededTempCount = Math.max(neededTempCount, req.getValue() + 1);
        }
        //t.getRequirements().stream().map(input -> input.getName()).collect(Collectors.toCollection(ArrayList::new));
        
        // Each potentail entity must not be busy, and it's ID has to contain all the required ID's.
        ArrayList<ActiveEntity> potentialLinks = new ArrayList();
        for(ActiveEntity potential : motor.getActiveEntities().stream().filter(input -> !input.isBusy()).collect(Collectors.toList())) {
            boolean allMatch = true;
            for(String idString : idList) {
                if(!potential.idMatch(idString)) {
                    allMatch = false;
                    break;
                }
            }
            if(allMatch) {
                potentialLinks.add(potential);
            }
        }
        
        final int needed = neededTempCount;
        SimpleIntegerProperty selectedCount = new SimpleIntegerProperty(0);
        HashSet<ActiveEntity> finalSelection = new HashSet();
        SplitMenuButton tempButton = new SplitMenuButton();
        tempButton.setText("Select " + needed);
        
        //Fill the MenuButton's menu with the potential links
        for(ActiveEntity potential : potentialLinks) {
            CheckBox potentialBox = new CheckBox(potential.getName());
            potentialBox.setId("entity-detail-task-links");
            CustomMenuItem potentialSelector = new CustomMenuItem(potentialBox);
            potentialSelector.setHideOnClick(false);
            potentialBox.setOnAction((ActionEvent click) -> {
                if(potentialBox.isSelected()) {
                    selectedCount.set(selectedCount.get() + 1);
                    finalSelection.add(potential);
                }
                else {
                    selectedCount.set(selectedCount.get() - 1);
                    finalSelection.remove(potential);    
                }
            });
            
            selectedCount.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                if(newValue.intValue() >= needed) {
                    tempButton.setText("Start");
                    if(!potentialBox.isSelected()) {
                        potentialBox.setDisable(true);
                    }
                }
                else {
                    tempButton.setText("Select " + (needed - newValue.intValue()));
                    potentialBox.setDisable(false);
                }
            });

            tempButton.getItems().add(potentialSelector);
        }

        tempButton.setOnAction((ActionEvent click) -> {
            if(selectedCount.intValue() == needed) {
                for(ActiveEntity partner : finalSelection) {
                    motor.linkActives(e, partner);
                    partner.setLinked(t, e);
                }
                
                motor.setTaskPayCosts(t, e);
                
                if(e instanceof Room) {
                    fillRoomTasksPane((Room)e);
                    fillRoomTraitsPane((Room)e);
                }
                else {
                    fillTasksPaneBusy((ActiveEntity)e);
                    fillTraitsPane((ActiveEntity)e);
                }

            }
        });
        
        return tempButton;
    }
    
    public void fillNeighborsPane(Room r) {
        roomNeighbors.getChildren().clear();
        FlowPane neighborFlow = new FlowPane();
        neighborFlow.setId("detail-trait-flow");
        neighborFlow.setPrefWrapLength(750);
        roomNeighbors.getChildren().add(neighborFlow);
        
        for(Room neighbor : r.getNeighbors()) {
            VBox neighborBox = new VBox();
            neighborBox.setId("detail-trait-box");
            Label neighborName = new Label();
            neighborName.setId("detail-trait-name");
            Label neighborPosition = new Label();
            neighborPosition.setText("");
            neighborPosition.setId("detail-trait-value");
            if(neighbor == null) {
                neighborName.setText("Nobody");
                neighborBox.setId("detail-trait-box-invalid");
            }
            else {
                neighborName.setText(neighbor.getName() + " " + neighbor.getRoomNumber());
                if(neighbor.equals(r.getNorthNeighbor())) {neighborPosition.setText("North: ");}
                if(neighbor.equals(r.getEastNeighbor())) {neighborPosition.setText("East: ");}
                if(neighbor.equals(r.getSouthNeighbor())) {neighborPosition.setText("South: ");}
                if(neighbor.equals(r.getWestNeighbor())) {neighborPosition.setText("West: ");}
                
                if(!neighbor.isReachable()) {
                    neighborBox.setId("detail-trait-box-invalid");
                }
            }
            
            HBox nameDirectionPair = new HBox();
            nameDirectionPair.getChildren().addAll(neighborPosition, neighborName);
            nameDirectionPair.setId("detail-trait-pair");
            
            neighborBox.getChildren().add(nameDirectionPair);
            
            neighborFlow.getChildren().add(neighborBox);
        }
    }
    
    public void fillRoomTasksPane(Room activeRoom) {

        if(activeRoom.isBusy()) {
            cancelCurrentRoomTask.setDisable(false);
            cancelCurrentRoomTask.setOnAction((ActionEvent click) -> {
                motor.cancelTaskRefundCosts(activeRoom);
                fillRoomTasksPane(activeRoom);
                fillRoomTraitsPane(activeRoom);
            });
        }
        else {
            cancelCurrentRoomTask.setDisable(true);
        }
        
        roomTasks.getChildren().clear();
        FlowPane tasksFlow = new FlowPane();
        tasksFlow.setId("entity-detail-task-flow");
        tasksFlow.setPrefWrapLength(750);
        roomTasks.getChildren().add(tasksFlow);
        
        
        for(Task t : activeRoom.getTasks()) {
            VBox taskBox = new VBox();
            taskBox.setId("entity-detail-task-box");
            
            HBox nameBox = new HBox();
            nameBox.setId("entity-detail-task-name-pane");
            Label taskName = new Label(t.getName());
            taskName.setId("entity-detail-task-name");            
            
            ButtonBase taskButton = new Button("Start");
            taskButton.setId("entity-detail-task-button");
            
            boolean inactive = false;
            if(activeRoom.isBusy()) {
                taskButton.setDisable(true);
                taskButton.setText("Busy");
                inactive = true;
            }
            
            if(!motor.requirementsAllMet(t, activeRoom)) {
                taskButton.setDisable(true);
                inactive = true;
            }
            
            if(!inactive) {
                // Checking if the Task needs to link with others
                boolean link;
                if(t.getRequirements().isEmpty()) {
                    link = false;
                }
                else {
                    link = t.getRequirements().stream().anyMatch((input -> TraitEvaluator.isCreationLinkTrait(input)));
                }
                
                if(link) {
                    taskButton = linkTaskButton(t, activeRoom);
                    taskButton.setId("entity-detail-task-button");
                }
                // no task link
                else {
                    taskButton.setOnAction((ActionEvent click) -> {
                        motor.setTaskPayCosts(t, activeRoom);
                        fillRoomTasksPane(activeRoom);
                        fillRoomTraitsPane(activeRoom);
                        fillNeighborsPane(activeRoom);
                    });
                }
            }
            
            nameBox.getChildren().addAll(taskName, taskButton);
            //////////////// DURATION 
            HBox durationBox = new HBox();
            durationBox.setId("entity-detail-task-duration-pane");
            Label durationName = new Label("Duration");
            durationName.setId("entity-detail-task-duration");
            Label durationNumber = new Label(t.getDuration() + "");
            durationNumber.setId("entity-detail-task-duration-number");
            durationBox.getChildren().addAll(durationName, durationNumber);
            /////////////// COSTS
            VBox costBox = new VBox();
            costBox.setId("entity-detail-task-cost-vbox");
            Label costTitle = new Label("Costs");
            costTitle.setId("entity-detail-task-cost-title");
            HBox costsPane = new HBox();
            costsPane.setId("entity-detail-task-cost-pane");
            if(t.getCosts().size() < 1) {
                Label temp = new Label("none");
                temp.setId("entity-detail-task-cost");
                costsPane.getChildren().add(temp);
            }
            else {
                for(Trait cost : t.getCosts()) {
                    Label temp = new Label(cost.toString());
                    temp.setId("entity-detail-task-cost");
                    costsPane.getChildren().add(temp);
                }
            }
            
            costBox.getChildren().addAll(costTitle, costsPane);
            
            ////////////// REQUIREMENTS
            VBox requirementsBox = new VBox();
            requirementsBox.setId("entity-detail-task-requirement-vbox");
            Label requirementsTitle = new Label("Requirements");
            requirementsTitle.setId("entity-detail-task-requirement-title");
            HBox requirementsPane = new HBox();
            requirementsPane.setId("entity-detail-task-requirement-pane");
            ArrayList<Label> reqLabels = new ArrayList<Label>();
            ArrayList<Trait> reqTraits = new ArrayList<Trait>();
            if(t.getRequirements().size() < 1) {
                Label temp = new Label("none");
                temp.setId("entity-detail-task-requirement");
                reqLabels.add(temp);
                requirementsPane.getChildren().add(temp);
            }
            else {
                for(Trait req : t.getRequirements()) {
                    String reqString;
                    Label temp = new Label();
                    switch(TraitEvaluator.requirementCondition(req)) {
                        case EQUALTO:            reqString = req.getName() + " = " + req.getValue(); 
                                                            break;
                        case NOTEQUAL:          reqString = req.getName() + " != " + req.getValue();
                                                            break;
                        case LESSTHAN:          reqString = req.getName() + " < " + req.getValue(); 
                                                            break;
                        case GREATERTHAN:   reqString = req.getName() + " > " + req.getValue(); 
                                                            break;
                        default: reqString = "none";
                    }
                    
                    if(TraitEvaluator.isCreationLinkTrait(req)) {
                        reqString = "Free " + reqString;
                    }
                    
                    temp.setText(reqString);
                    reqLabels.add(temp);
                    reqTraits.add(req);
                    requirementsPane.getChildren().add(temp);
                    
                }
            }
            
            setUpReqs(reqLabels, reqTraits, activeRoom);
            requirementsBox.getChildren().addAll(requirementsTitle, requirementsPane);
            
            /////////////////  RESULTS
            VBox resultsBox = new VBox();
            requirementsBox.setId("entity-detail-task-result-vbox");
            Label resultsTitle = new Label("Results");
            resultsTitle.setId("entity-detail-task-result-title");
            HBox resultsPane = new HBox();
            resultsPane.setId("entity-detail-task-result-pane");
            if(t.getResults().size() < 1) {
                Label temp = new Label("none");
                temp.setId("entity-detail-task-result");
                resultsPane.getChildren().add(temp);
            }
            else {
                for(Trait res : t.getResults()) {
                    Label temp = new Label(res.toString());
                    temp.setId("entity-detail-task-result");
                    resultsPane.getChildren().add(temp);
                }
            }
            
            resultsBox.getChildren().addAll(resultsTitle, resultsPane);
            
            taskBox.getChildren().addAll(nameBox, new Separator(Orientation.HORIZONTAL), 
                                                      durationBox, new Separator(Orientation.HORIZONTAL), 
                                                      costBox, new Separator(Orientation.HORIZONTAL), 
                                                      requirementsBox, new Separator(Orientation.HORIZONTAL), 
                                                      resultsBox);
            
            ///////////// FLAVOR
            if(t.getFlavor() != null && t.getFlavor().length() > 0) {
                Tooltip taskDesc = new Tooltip();
                taskDesc.textProperty().bind(t.getFlavorProp());
                taskDesc.setId("entity-detail-task-description");
                Tooltip.install(taskBox, taskDesc);
            }
            
            if(t.equals(activeRoom.getCurrentTask())) {
                taskBox.setId("entity-detail-task-box-selected");
            }
            tasksFlow.getChildren().add(taskBox);
        }
    }
    
    public void fillRoomTasksUnreachable(Room r) {
        cancelCurrentRoomTask.setDisable(true);
        roomTasks.getChildren().clear();
    }
    
    public void fillRoomTraitsPane(Room r) {
        roomTraits.getChildren().clear();
        FlowPane traitsFlow = new FlowPane();
        traitsFlow.setId("detail-trait-flow");
        traitsFlow.setPrefWrapLength(750);
        roomTraits.getChildren().add(traitsFlow);
        
        for(Trait t : r.getTraits()) {
            VBox traitBox = new VBox();
            traitBox.setId("detail-trait-box");
            Label traitName = new Label();
            traitName.textProperty().bind(t.getNameProp());
            traitName.setId("detail-trait-name");
            Label traitValue = new Label();
            traitValue.textProperty().bind(t.getValueProp().asString());
            traitValue.setId("detail-trait-value");
            
            HBox nameValuePair = new HBox();
            nameValuePair.getChildren().addAll(traitName, traitValue);
            nameValuePair.setId("detail-trait-pair");
            
            traitBox.getChildren().add(nameValuePair);
            
            if(t.getDesc() != null && t.getDesc().length() > 0) {
                Tooltip traitDesc = new Tooltip();
                traitDesc.textProperty().bind(t.getDescProp());
                traitDesc.setId("detail-trait-description");
                Tooltip.install(traitBox, traitDesc);
            }
            
            traitsFlow.getChildren().add(traitBox);
        }
    }
    
    public void setPrimaryStage(Stage newPrimary) {
        primaryStage = newPrimary;
    }
    
    public void setEntityDetailStage(Stage newDetail) {
        entityDetail = newDetail;
        setUpEntityDetailWindow();
    }
    
    public void setRoomDetailStage(Stage newDetail) {
        roomDetail = newDetail;
        setUpRoomDetailWindow();
    }
    
    public void setEventDetailStage(Stage newDetail) {
        eventDetail = newDetail;
        setUpUpdateWarningListener();
        setUpEventDetailWindow();
    }
    
    @Override
    public void setEngine(Engine e) {
        motor = e;
    }
    
    @Override
    public Engine getEngine() {
        return motor; 
    }
    
    
    /**
     * @return the leftScroll
     */
    public ScrollPane getLeftScroll() {
        return leftScroll;
    }

    /**
     * @param leftScroll the leftScroll to set
     */
    public void setLeftScroll(ScrollPane leftScroll) {
        this.leftScroll = leftScroll;
    }

    /**
     * @return the leftPane
     */
    public VBox getLeftPane() {
        return leftPane;
    }

    /**
     * @param leftPane the leftPane to set
     */
    public void setLeftPane(VBox leftPane) {
        this.leftPane = leftPane;
    }

    /**
     * @return the mainBorderPane
     */
    public BorderPane getMainBorderPane() {
        return mainBorderPane;
    }

    /**
     * @param mainBorderPane the mainBorderPane to set
     */
    public void setMainBorderPane(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    /**
     * @return the topToolBar
     */
    public ToolBar getTopToolBar() {
        return topToolBar;
    }

    /**
     * @param topToolBar the topToolBar to set
     */
    public void setTopToolBar(ToolBar topToolBar) {
        this.topToolBar = topToolBar;
    }

    /**
     * @return the centerScroll
     */
    public ScrollPane getCenterScroll() {
        return centerScroll;
    }

    /**
     * @param centerScroll the centerScroll to set
     */
    public void setCenterScroll(ScrollPane centerScroll) {
        this.centerScroll = centerScroll;
    }

    /**
     * @return the centerGrid
     */
    public GridPane getCenterGrid() {
        return centerGrid;
    }

    /**
     * @param centerGrid the centerGrid to set
     */
    public void setCenterGrid(GridPane centerGrid) {
        this.centerGrid = centerGrid;
    }

    /**
     * @return the resBox
     */
    public HBox getResBox() {
        return resBox;
    }

    /**
     * @param resBox the resBox to set
     */
    public void setResBox(HBox resBox) {
        this.resBox = resBox;
    }

    /**
     * @return the stringProp
     */
    public StringProperty getStringProp() {
        return stringProp;
    }

    /**
     * @param stringProp the stringProp to set
     */
    public void setStringProp(StringProperty stringProp) {
        this.stringProp = stringProp;
    }
    
    private class WarningCell extends ListCell<GameEvent> {
        
        protected WarningCell() {
            super();
            super.setWrapText(true);
            super.setTextFill(Color.BLUEVIOLET);
            super.setFocusTraversable(false);
//            super.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//                if(newValue) {
//                    System.out.println("focus");
//                    super.setFocused(false);
//                }
//            });
        }
        
        @Override
        protected void updateItem(GameEvent item, boolean empty) {
            super.updateItem(item, empty);
            
            if(empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.toString());
                
                
            }
        }
    }
    
}