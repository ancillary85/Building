/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Alecto
 */
public class MainController implements Initializable {
    
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
    private FlowPane traitsFlow;
    @FXML 
    private FlowPane tasksFlow;
    
    private Stage entityDetail;
    private Stage primaryStage;
    private Engine motor;
    private ListChangeListener resListener;
    private ArrayList<MenuButton> buttons;
    private static final int SQUARESIZE = 100;
    private static final int ROOM_COLUMNS = 5;
    private static final int ROOM_ROWS = 5;
    private boolean init = false;
    
    private StringProperty stringProp;
    
    public MainController(Stage initPrimary) {
        primaryStage = initPrimary;
        setUpDefaultMotor();
        setUpDefaultDetail();
    }
    
    public MainController(Stage initPrimary, Engine initEngine) {
        primaryStage = initPrimary;
        motor = initEngine;
        setUpDefaultDetail();
    }
    
    public MainController(Stage initPrimary, Engine initEngine, Stage initDetails) {
        primaryStage = initPrimary;
        motor = initEngine;
        entityDetail = initDetails;
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
        setUpEntityDetailWindow();
        
        init = true;
    }    

    private void setUpResources() {
        motor.addResource("Food", 10.0);
        motor.addResource("Food", 10.0);
        motor.addResource("Light bulbs", 2.0);
        motor.addResource("Enemies", 15.0);
        motor.addResource("Morale", -3.0);
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
        
        
        centerScroll.setFitToHeight(true);
        centerScroll.setFitToWidth(true);
    }
    
    public void populate() {
        
        for(int i = 0; i < 5; i++) {
            ActiveEntity ant = AntBuilder.makeWorker("Ant " + i, null);
            motor.addActiveEntity(ant);
            
            Button antButton = new Button();
            antButton.setGraphic(EntitySummary.getSummary(ant));
            antButton.setId("summary-button");
            
            antButton.setOnAction((ActionEvent e) -> {
                showDetailWindow(ant);
            });
            
            leftPane.getChildren().add(antButton);
        }
        
        ActiveEntity soldier = AntBuilder.makeSoldier("Soldier 1", null);
        motor.addActiveEntity(soldier);
        Button antButton = new Button();
        antButton.setGraphic(EntitySummary.getSummary(soldier));
        antButton.setId("summary-button");
            
        antButton.setOnAction((ActionEvent e) -> {
            showDetailWindow(soldier);
        });
            
        leftPane.getChildren().add(antButton);
        
        
        
        ActiveEntity test = AntBuilder.makeSoldier("Soldier 2", null);
        test.getTraits().add(new Trait());
        test.getTraits().add(new Trait("Test Trait 1", 55.0, Trait.trait_type.FLAVOR));
        test.getTraits().add(new Trait("Test Trait 2", 123456789.0, Trait.trait_type.FLAVOR));
        test.getTraits().add(new Trait("Test Trait 3", 0, Trait.trait_type.FLAVOR));
        test.getTraits().add(new Trait("Test Trait 4 with a longer name", 42, Trait.trait_type.FLAVOR));
        motor.addActiveEntity(test);
        Button testButton = new Button();
        testButton.setGraphic(EntitySummary.getSummary(test));
        testButton.setId("summary-button");
            
        testButton.setOnAction((ActionEvent e) -> {
            showDetailWindow(test);
        });
            
        leftPane.getChildren().add(testButton);
    }
    
    public void populate1() {
        buttons = new ArrayList<>();
        
        //temp 5x5 grid of buttons
        for(int column = 0; column < ROOM_COLUMNS; column++) {
            for(int row = 0; row < ROOM_ROWS; row++) {
                String antName = "Ant " + (row*5 +column + 1);
                ActiveEntity ant = AntBuilder.makeWorker(antName, null);
                motor.addActiveEntity(ant);
                
                Menu nestedTasks = new Menu("Tasks");
                nestedTasks.getItems().addAll(menuOfTasks(ant));
                
                MenuItem temp1 = new MenuItem("Current Task Timer");
                temp1.setOnAction((ActionEvent e) -> {
                    System.out.println(ant.getTaskTimer());
                });
                
                MenuButton antButton = new MenuButton();
                Node summary = EntitySummary.getSummary(ant);
                antButton.setGraphic(summary);
                antButton.getItems().addAll(nestedTasks, temp1);
                antButton.setId("summary-button");
                //centerGrid.add(antButton, column, row);
                leftPane.getChildren().add(antButton);
                buttons.add(antButton);
            }
        }
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
    
    public void addWorker(String name) {
        ActiveEntity worker = AntBuilder.makeWorker(name, null);
        motor.addActiveEntity(worker);
        Button antButton = new Button();
        antButton.setGraphic(EntitySummary.getSummary(worker));
        antButton.setId("summary-button");
            
        antButton.setOnAction((ActionEvent e) -> {
            showDetailWindow(worker);
        });
            
        leftPane.getChildren().add(antButton);
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
    
    public void entityDetailClose(ActionEvent e) {
        entityDetail.close();
    }
    
    public void fooPlus(ActionEvent e) {
                motor.addResource(new Trait("Foo", 1, Trait.trait_type.RESOURCE));
    }
    
    public void fooMinus(ActionEvent e) {
                motor.addResource(new Trait("Foo", -1, Trait.trait_type.RESOURCE));
    }
    
    public void updateFired(ActionEvent e) {
        motor.update();
    }
    
    public void exitFired(ActionEvent e) {
        entityDetail.close();
        Platform.exit();
    }
    
    public void taskSelected(ActionEvent e) {
        
    }
    
    public void showDetailWindow(ActiveEntity e) {
        
        entityDetailName.textProperty().unbind();
        entityDetailName.textProperty().bind(e.getNameProp());
        
        fillTraitsPane(e);
        
        fillTasksPane(e);
        
        if(!entityDetail.isShowing()) {
            entityDetail.showAndWait();
        }
    }
    
    public void fillTraitsPane(ActiveEntity e) {
        traitsFlow.getChildren().clear();
        
        for(Trait t : e.getTraits()) {
            VBox traitBox = new VBox();
            traitBox.setId("entity-detail-trait-box");
            Label traitName = new Label();
            traitName.textProperty().bind(t.getNameProp());
            traitName.setId("entity-detail-trait-name");
            Label traitValue = new Label();
            traitValue.textProperty().bind(t.getValueProp().asString());
            traitValue.setId("entity-detail-trait-value");
            
            HBox nameValuePair = new HBox();
            nameValuePair.getChildren().addAll(traitName, traitValue);
            nameValuePair.setId("entity-detail-trait-pair");
            
            traitBox.getChildren().add(nameValuePair);
            
            if(t.getDesc() != null && t.getDesc().length() > 0) {
                Tooltip traitDesc = new Tooltip();
                traitDesc.textProperty().bind(t.getDescProp());
                traitDesc.setId("entity-detail-trait-description");
                Tooltip.install(traitBox, traitDesc);
            }
            
            traitsFlow.getChildren().add(traitBox);
        }
    }
    
    public void fillTasksPane(ActiveEntity e) {
        tasksFlow.getChildren().clear();
        
        for(Task t : e.getTasks()) {
            VBox taskBox = new VBox();
            taskBox.setId("entity-detail-task-box");
            
            HBox nameBox = new HBox();
            nameBox.setId("entity-detail-task-name-pane");
            Label taskName = new Label(t.getName());
            taskName.setId("entity-detail-task-name");            
            Button taskButton = new Button("Start");
            taskButton.setId("entity-detail-task-button");
            taskButton.setOnAction((ActionEvent click) -> {
                e.setTaskAndTimer(t);
            });
            nameBox.getChildren().addAll(taskName, taskButton);
            //entity-detail-task-duration/cost/requirement/result and
            //entity-detail-task-duration/cost/requirement/result-number
            
            HBox durationBox = new HBox();
            durationBox.setId("entity-detail-task-duration-pane");
            Label durationName = new Label("Duration");
            durationName.setId("entity-detail-task-duration");
            Label durationNumber = new Label(t.getDuration() + "");
            durationNumber.setId("entity-detail-task-duration-number");
            durationBox.getChildren().addAll(durationName, durationNumber);
            
//    private SimpleStringProperty[] costs;
//    private SimpleStringProperty[] requirements;
//    private SimpleObjectProperty<Trait[]> results;
            HBox costBox = new HBox();
            costBox.setId("entity-detail-task-cost-pane");
            Label costTitle = new Label("Costs");
            costTitle.setId("entity-detail-task-cost-title");
            
            costBox.getChildren().add(costTitle);
            
            taskBox.getChildren().addAll(nameBox, new Separator(Orientation.HORIZONTAL), 
                                                      durationBox, new Separator(Orientation.HORIZONTAL), 
                                                      costBox);
            
            if(t.getFlavor() != null && t.getFlavor().length() > 0) {
                Tooltip taskDesc = new Tooltip();
                taskDesc.textProperty().bind(t.getFlavorProp());
                taskDesc.setId("entity-detail-task-description");
                Tooltip.install(taskBox, taskDesc);
            }
            
            
            tasksFlow.getChildren().add(taskBox);
        }
    }
    
    public void setPrimaryStage(Stage newPrimary) {
        primaryStage = newPrimary;
    }
    
    public void setDetailStage(Stage newDetail) {
        entityDetail = newDetail;
        setUpEntityDetailWindow();
    }
    
    public void setEngine(Engine e) {
        motor = e;
    }
    
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
    
}
