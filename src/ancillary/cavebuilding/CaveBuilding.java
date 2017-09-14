/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author MM
 */
public class CaveBuilding extends Application {
       
    private Text texty;
    private static final int SQUARESIZE = 100;
    private final int BUTTON_PREF_WIDTH = 70;
    private final int BUTTON_PREF_HEIGHT = 20;
    
    private BorderPane mainPane;
    private ToolBar topToolBar;
    private ScrollPane centerScroll;
    private GridPane centerGrid;
    private HBox resBox;
    private ListChangeListener resListener;
    
    private ArrayList<MenuButton> buttons;
    
    private Engine motor;

    //this is just for experimenting
    public enum direction {UP, DOWN, LEFT, RIGHT}
        
    @Override
    public void init() throws Exception {
        System.out.println("Hello!");
    }
    
    @Override
    public void stop() throws Exception {
        System.out.println("Goodbye!");
    }
    
    @Override
    public void start(Stage primaryStage) {

//        motor = new AntHillEngine();
//        setUpResources();
//        
//        mainPane = new BorderPane();
//        mainPane.setId("main-pane");
//        
//        setUpTopPane();
//        mainPane.setTop(topToolBar);
//        
//        setUpCenterScroll();
//        setUpCenterGrid();
//        setUpResBox();
//        setUpResourceListener();
//        centerScroll.setContent(centerGrid);        
//        mainPane.setCenter(centerScroll);
//        mainPane.setBottom(resBox);
//        
//        Scene scene = new Scene(mainPane);
//        scene.getStylesheets().add(CaveBuilding.class.getResource("CaveBuilding.css").toExternalForm());
//        
//        primaryStage.setTitle("Cave Builder");
//        primaryStage.setScene(scene);
//        primaryStage.sizeToScene();
//        primaryStage.show();
        
    try {
            motor = new AntHillEngine();
            MainController controller = new MainController(primaryStage, motor);
            FXMLLoader loader1 = new FXMLLoader(CaveBuilding.class.getResource("mainWindow.fxml"));
            loader1.setController(controller);
            FXMLLoader loader2 = new FXMLLoader(CaveBuilding.class.getResource("detailWindow.fxml"));
            loader2.setController(controller);
            primaryStage.setScene(new Scene(loader1.load()));
            
            Scene detailScene = new Scene(loader2.load());
            Stage detailStage = new Stage();
            detailStage.setScene(detailScene);
            controller.setDetailStage(detailStage);
            
            primaryStage.setTitle("CaveBuilding");
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(CaveBuilding.class.getName()).log(Level.SEVERE, null, ex);
        }

//        EntityTesting();
        
//        try {
//            
//            TaskParseTest();
//        } catch (SAXException | ParserConfigurationException | IOException ex) {
//            Logger.getLogger(CaveBuilding.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void showThisAndWait(Stage s) {
        s.showAndWait();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void setUpResources() {
        motor.addResource("Food", 10.0);
        motor.addResource("Food", 10.0);
        motor.addResource("Light bulbs", 2.0);
        motor.addResource("Enemies", 15.0);
        motor.addResource("Morale", -3.0);
    }
    
    private void setUpTopPane() {
        
        MenuItem menuItem1 = new MenuItem("Foo -1");
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                motor.addResource(new Trait("Foo", -1, Trait.trait_type.RESOURCE));
            }
        });
        
        MenuItem menuItem2 = new MenuItem("Foo +1");
        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                motor.addResource(new Trait("Foo", 1, Trait.trait_type.RESOURCE));
            }
        });
        
        CustomMenuItem menuItem3 = new CustomMenuItem(new Label("Update"));
        menuItem3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        
                        motor.update();
                    }
        });
        
        MenuButton menuButton = new MenuButton("Options");
        menuButton.getItems().addAll(menuItem1, menuItem2, menuItem3);
        
        Button updateButton = new Button("Update");
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        
                        motor.update();
                    }
        });
        
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
            }
        });
        
        topToolBar = new ToolBar(
         updateButton,
         new Separator(),
         menuButton,
        new Separator(),
        exitButton);
        
        topToolBar.setId("top-tool-bar");
    }

    private void setUpCenterScroll() {
        centerScroll = new ScrollPane();
        centerScroll.setId("center-scroll");
    }

    private void setUpCenterGrid() {
        centerGrid = new GridPane();
        centerGrid.setId("center-grid");
        centerGrid.setAlignment(Pos.CENTER);
        centerGrid.setPadding(new Insets(25, 25, 25, 25));
        
        populate();        
    }
    
    private void populate() {
        buttons = new ArrayList<>();
        
        //temp 5x5 grid of buttons
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                String antName = "Ant " + (j*5 +i + 1);
                ActiveEntity ant = AntBuilder.makeWorker(antName, null);
                motor.addActiveEntity(ant);
                
                MenuButton antButton = new MenuButton();
                MenuItem m1 = new MenuItem("Print name and ID");
                m1.setOnAction((ActionEvent e) -> {
                    System.out.println(ant.getName() + ": " + ant.getID());
                });
                
                MenuItem m2 = new MenuItem("Print tasks");
                m2.setOnAction((ActionEvent e) -> {
                    for(Task t : ant.getTasks()) {
                        System.out.println(t.toString());
                    }
                    System.out.println();
                });
                
                Menu nestedTasks = new Menu("Tasks");
                nestedTasks.getItems().addAll(new MenuItem("Test"), new MenuItem("Foo"));
                                                
                antButton.getItems().addAll(m1, m2, nestedTasks);
                
                antButton.textProperty().bind(ant.getNameProp());
                antButton.setPrefSize(SQUARESIZE, SQUARESIZE);
                antButton.setId("center-grid-button");
                
                centerGrid.add(antButton, i, j);
                buttons.add(antButton);
            }
        }
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
            HBox resPair = new HBox();
            resPair.setId("res-pair");
            resPair.setSpacing(15);
            
            Label name = new Label();
            name.textProperty().bind(t.getNameProp());
            name.setId("res-name");
            
            Label value = new Label();
            value.textProperty().bind(t.getValueProp().asString());
            value.setId("res-value");
            
            resPair.getChildren().add(name);
            resPair.getChildren().add(value);
            resPair.getChildren().add(new Separator(Orientation.VERTICAL));
            
            resBox.getChildren().add(resPair);
        }
    }
    
    private void setUpResBox() {
        resBox = new HBox();
        resBox.setId("res-box");
        resBox.setPadding(new Insets(25));
        
//        Label count = new Label();
//        count.textProperty().bind(motor.getGlobalResources().sizeProperty().asString());
//        resBox.getChildren().add(count);

        resBoxFill();
    }

    public void setEngine(Engine e) {
        motor = e;
    }
    
    public Engine getEngine() {
        return motor; 
    }
    
    private void EntityTesting() {
        ArrayList<ActiveEntity> ants = new ArrayList();
        ArrayList<Task> tasks = new ArrayList();
        
        Task dig = new Task("dig", 2, null, null, null, null);
        Task eat = new Task ("eat", 1, null, null, null, null);
        Task forage = new Task("forage", 3, null, null, null, null);
        Task fight = new Task("fight", 1, null, null, null, null);
        tasks.add(dig);
        tasks.add(eat);
        tasks.add(forage);
        tasks.add(fight);
        
        ants.add(new ActiveEntity("worker", "Worker 1", "Hill", tasks, null, AntBuilder.WORKER_TRAITS));
        ants.add(new ActiveEntity("worker", "Worker 2", "Burrow", tasks, null, AntBuilder.WORKER_TRAITS));
        ants.add(new ActiveEntity("worker", "Worker 3", "Hill", tasks, null, AntBuilder.WORKER_TRAITS));
        ants.add(new ActiveEntity("worker", "Worker 4", "Outside", tasks, null, AntBuilder.WORKER_TRAITS));
        
        ants.get(0).setCurrentTask(0);
        ants.get(0).setTaskTimerFromCurrentTask();
        ants.get(1).setCurrentTask(1);
        ants.get(1).setTaskTimerFromCurrentTask();
        ants.get(2).setCurrentTask(2);
        ants.get(2).setTaskTimerFromCurrentTask();
        ants.get(3).setCurrentTask(3);
        ants.get(3).setTaskTimerFromCurrentTask();
        
        for(int i = 0; i < ants.size(); i++) {
            ants.get(i).entityUpdate(null);
        }
        System.out.println("NEW TIME STEP");
        for(int i = 0; i < ants.size(); i++) {
            ants.get(i).entityUpdate(null);
        }
        System.out.println("NEW TIME STEP");
        for(int i = 0; i < ants.size(); i++) {
            ants.get(i).entityUpdate(null);
        }
        System.out.println("NEW TIME STEP");
        for(int i = 0; i < ants.size(); i++) {
            ants.get(i).entityUpdate(null);
        }
        
    }
    
    private void TaskParseTest() throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder dB = fact.newDocumentBuilder();
        Document doc = dB.parse(XMLParserTest.class.getResourceAsStream("TaskParserTest.xml"));
        doc.normalize();
        NodeList nL = doc.getElementsByTagName("task");
        
        Task t1 = TaskParser.parseTask(nL.item(0));
        Task t2 = TaskParser.parseTask(nL.item(1));
        System.out.println(t1.toString());
        System.out.println(t2.toString());
        System.out.println(t1.equals(t2));
        
        System.out.println("M-m-multi parse!");
        
        Task[] tasks = TaskParser.parseTasks("TaskParserTest.xml");
        
        for(int i = 0; i < tasks.length; i++) {
            System.out.println(tasks[i].toString());
        }
    }
    
}
