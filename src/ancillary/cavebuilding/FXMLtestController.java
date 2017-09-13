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
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
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
public class FXMLtestController implements Initializable {
    
    @FXML
    private ScrollPane leftScroll;
    @FXML
    private VBox leftPane;
    @FXML
    private ScrollPane rightScroll;
    @FXML
    private VBox rightPane;
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
    private Text reportText;
    @FXML
    private Stage entityDetailWindow;
    @FXML
    private Control tempShowDetailWindow;

    private Stage primaryStage;
    private Engine motor;
    private ListChangeListener resListener;
    private ArrayList<MenuButton> buttons;
    private static final int SQUARESIZE = 100;
    private static final int ROOM_COLUMNS = 5;
    private static final int ROOM_ROWS = 5;
    
    private StringProperty stringProp;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        motor = new AntHillEngine();
        setUpResources();
        setUpTopPane();
        setUpCenterGrid();
        setUpResBox();
        setUpResourceListener();
        //setUpEntityDetailWindow();
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
        buttons = new ArrayList<>();
        
        //temp 5x5 grid of buttons
        for(int column = 0; column < ROOM_COLUMNS; column++) {
            for(int row = 0; row < ROOM_ROWS; row++) {
                String antName = "Ant " + (row*5 +column + 1);
                ActiveEntity ant = AntBuilder.makeWorker(antName, null);
                motor.addActiveEntity(ant);
                
                MenuItem m1 = new MenuItem("Print name and ID");
                m1.setOnAction((ActionEvent e) -> {
                    System.out.println(ant.getName() + ": " + ant.getID());
                    reportTextPrint(ant.getName() + " talked about itself");
                });
                
                MenuItem m2 = new MenuItem("Print tasks");
                m2.setOnAction((ActionEvent e) -> {
                    String results = "";
                    
                    for(Task t : ant.getTasks()) {
                        results += t.toString() + "\n";
                    }
                    reportTextPrint(results + "----------\n");
                });
                
                MenuItem m3 = new MenuItem("Am I cool?");
                m3.setOnAction((ActionEvent e) -> {
                    ant.setName(ant.getName() + " is cool!");
                });
                
                Menu nestedTasks = new Menu("Tasks");
                nestedTasks.getItems().addAll(menuOfTasks(ant));
                
                MenuButton antButton = new MenuButton();
                VBox labels = new VBox();
                Label name = new Label();
                name.textProperty().bind(ant.getNameProp());
                Label status = new Label("");
                
                ant.getBusyProp().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if(newValue.booleanValue()) {
                            status.setText(ant.getCurrentTask().getGerund());
                        }
                        else {
                            status.setText("");
                        }
                    }
                });
                
                name.setId("center-grid-button-name");
                status.setId("center-grid-button-status");
                labels.setId("center-grid-button-VBox");
                labels.getChildren().addAll(name, new Separator(Orientation.HORIZONTAL), status);
                antButton.setGraphic(labels);
                antButton.getItems().addAll(m1, m2, m3, nestedTasks);
                antButton.setId("center-grid-button");
                
                
                centerGrid.add(antButton, column, row);
                buttons.add(antButton);
            }
        }
    }    
    
    private Collection<MenuItem> menuOfTasks(ActiveEntity ant) {
        ArrayList<MenuItem> antTasks = new ArrayList<>(ant.getTasks().size());
        
        for(Task t : ant.getTasks()) {
            MenuItem tempMenu = new MenuItem();
            tempMenu.textProperty().bind(t.getNameProp());
            
            tempMenu.setOnAction((ActionEvent e) -> {
                ant.setTaskAndTimer(t);
            });
            
            MenuItem temp1 = new MenuItem("temp1");
            MenuItem temp2 = new MenuItem("temp2");
            ContextMenu cm = new ContextMenu(temp1, temp2);
            
            tempMenu.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
                System.out.println("detected");
                cm.show(centerGrid, Side.RIGHT, 0, 0);
            });
            
            
            antTasks.add(tempMenu);
        }
        
        
        return antTasks;
    }
    
    private void reportTextPrint(String s) {
        if(reportText.getText().length() > 10000) {
            reportText.setText(reportText.getText().substring(0, 5000));
        }
        
        reportText.setText(s + "\n" + reportText.getText());
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
        
//        Label count = new Label();
//        count.textProperty().bind(motor.getGlobalResources().sizeProperty().asString());
//        resBox.getChildren().add(count);

        resBoxFill();
    }

    public void setUpEntityDetailWindow(Scene s) {
         
        entityDetailWindow = new Stage();
        entityDetailWindow.initOwner(primaryStage);
//        entityDetailWindow.initModality(Modality.APPLICATION_MODAL);
//        entityDetailWindow.initStyle(StageStyle.DECORATED);
//        entityDetailWindow.setResizable(false);
//        entityDetailWindow.sizeToScene();        
        

        //entityDetailWindow.setScene(new Scene(FXMLLoader.load(FXMLtestController.class.getResource("detailWindow.fxml"))));
        entityDetailWindow.setScene(s);
//        dummyDetail();
        entityDetailWindow.setTitle("Details");

        
        
    }
    
    private void dummyDetail() {
        //entityDetailWindow.setScene(new Scene(new Button("HI!")));
        
        try {
           FXMLLoader loader = new FXMLLoader(CaveBuilding.class.getResource("detailWindow.fxml")); 
           //Parent p = FXMLLoader.load(FXMLtestController.class.getResource("detailWindow.fxml"));
            System.out.println("1");
            System.out.println(loader.getLocation());
            entityDetailWindow.setScene(loader.load());
        }
        catch (Exception e) {
            System.out.println("2");
            System.out.println(e);
        }
    }
    
    public void entityDetailWindowClose(ActionEvent e) {
        entityDetailWindow.close();
    }
    
    public void fooPlus(ActionEvent e) {
                motor.addResource(new Trait("Foo", 1, Trait.trait_type.RESOURCE));
    }
    
    public void fooMinus(ActionEvent e) {
                motor.addResource(new Trait("Foo", -1, Trait.trait_type.RESOURCE));
    }
    
    public void updateButtonFired(ActionEvent e) {
        motor.update();
    }
    
    public void exitButtonFired(ActionEvent e) {
        Platform.exit();
    }
    
    public void clearReportText(ActionEvent e) {
        reportText.setText("");
    }
    
    public void taskSelected(ActionEvent e) {
        
    }
    
    public void tempShowDetailWindow(ActionEvent e) {
        if(entityDetailWindow.isShowing()) {
            return;
        }
        else {
            entityDetailWindow.showAndWait();
        }
    }
    
    public void setPrimaryStage(Stage s) {
        primaryStage = s;
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
     * @return the rightScroll
     */
    public ScrollPane getRightScroll() {
        return rightScroll;
    }

    /**
     * @param rightScroll the rightScroll to set
     */
    public void setRightScroll(ScrollPane rightScroll) {
        this.rightScroll = rightScroll;
    }

    /**
     * @return the rightPane
     */
    public VBox getRightPane() {
        return rightPane;
    }

    /**
     * @param rightPane the rightPane to set
     */
    public void setRightPane(VBox rightPane) {
        this.rightPane = rightPane;
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
