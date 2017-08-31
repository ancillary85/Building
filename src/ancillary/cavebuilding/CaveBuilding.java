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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
    
    private ArrayList<Button> buttons;
    
    private Engine motor;

    //this is just for experimenting
    public enum direction {UP, DOWN, LEFT, RIGHT}
        
    
    @Override
    public void start(Stage primaryStage) {

        motor = new AntHillEngine();
        setUpResources();
        
        mainPane = new BorderPane();
        mainPane.setId("main-pane");
        
        setUpTopPane();
        mainPane.setTop(topToolBar);
        
        setUpCenterScroll();
        setUpCenterGrid();
        setUpResBox();
        centerScroll.setContent(centerGrid);        
        mainPane.setCenter(centerScroll);
        mainPane.setBottom(resBox);
        
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(CaveBuilding.class.getResource("CaveBuilding.css").toExternalForm());
        
        primaryStage.setTitle("Cave Builder");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        
//        EntityTesting();
        
//        try {
//            
//            TaskParseTest();
//        } catch (SAXException | ParserConfigurationException | IOException ex) {
//            Logger.getLogger(CaveBuilding.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
        topToolBar = new ToolBar(
         new Button("New"),
         new Button("Open"),
         new Button("Save"),
         new Separator(),
         new Button("Clean"),
         new Button("Compile"),
         new Button("Run"),
         new Separator(),
         new Button("Debug"),
         new Button("Profile"));
        
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
                
                Button b = new Button();
                b.textProperty().bind(ant.getNameProp());
                b.setPrefSize(SQUARESIZE, SQUARESIZE);
                b.setId("center-grid-button");
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        
                        motor.update();
                    }
                });
                
                centerGrid.add(b, i, j);
                buttons.add(b);
            }
        }
    }
    
    private void setUpResBox() {
        resBox = new HBox();
        resBox.setId("res-box");
        resBox.setPadding(new Insets(25));
        
        Label count = new Label("" + motor.getGlobalResources().size());
        
        /*
        want to somehow bind count to motor.getGlobalResources().size()
        */
        
        
        resBox.getChildren().add(count);

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
