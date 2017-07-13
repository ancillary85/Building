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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    
    private Engine motor;
    private LinkedList<Entity> people;
    
    
    //this is just for experimenting
    public enum direction {UP, DOWN, LEFT, RIGHT}
        
    
    @Override
    public void start(Stage primaryStage) {

        getEngine(primaryStage);
        getOccupants(primaryStage);
        mainPane = new BorderPane();
        mainPane.setId("main-pane");
        
        setUpTopPane();
        mainPane.setTop(topToolBar);
        
        setUpCenterScroll();
        setUpCenterGrid();
        centerScroll.setContent(centerGrid);        
        mainPane.setCenter(centerScroll);
        
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(CaveBuilding.class.getResource("CaveBuilding.css").toExternalForm());
        
        primaryStage.setTitle("Cave Builder");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        
        //EntityTesting();
        
        try {
            
            TaskParseTest();
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            Logger.getLogger(CaveBuilding.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
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
        //temp 5x5 grid of buttons
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                Button b = new Button();
                b.setPrefSize(SQUARESIZE, SQUARESIZE);
                b.setId("center-grid-button");
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        
                        motor.action();
                    }
                });
                
                centerGrid.add(b, i, j);
                
            }
        }
    }

    private void getEngine(Stage primaryStage) {
        //motor = new CaveEngine(primaryStage); 
        motor = new testEngine(primaryStage);
    }
    
    private void getOccupants(Stage primaryStage) {
        people = new LinkedList<Entity>();
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
        
        ants.add(new ActiveEntity("worker", "Worker 1", "Hill", tasks));
        ants.add(new ActiveEntity("worker", "Worker 2", "Burrow", tasks));
        ants.add(new ActiveEntity("worker", "Worker 3", "Hill", tasks));
        ants.add(new ActiveEntity("worker", "Worker 4", "Outside", tasks));
        
        ants.get(0).setCurrentTask(0);
        ants.get(0).setTaskTimerFromCurrentTask();
        ants.get(1).setCurrentTask(1);
        ants.get(1).setTaskTimerFromCurrentTask();
        ants.get(2).setCurrentTask(2);
        ants.get(2).setTaskTimerFromCurrentTask();
        ants.get(3).setCurrentTask(3);
        ants.get(3).setTaskTimerFromCurrentTask();
        
        for(int i = 0; i < ants.size(); i++) {
            ants.get(i).update(null);
        }
        System.out.println("NEW TIME STEP");
        for(int i = 0; i < ants.size(); i++) {
            ants.get(i).update(null);
        }
        System.out.println("NEW TIME STEP");
        for(int i = 0; i < ants.size(); i++) {
            ants.get(i).update(null);
        }
        System.out.println("NEW TIME STEP");
        for(int i = 0; i < ants.size(); i++) {
            ants.get(i).update(null);
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
