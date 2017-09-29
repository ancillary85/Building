/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.io.IOException;
import java.util.ArrayList;
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
    
    private AntHillEngine motor;

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
        try {
            motor = new AntHillEngine();
            MainController controller = new MainController(primaryStage, motor);
            FXMLLoader loader1 = new FXMLLoader(CaveBuilding.class.getResource("mainWindow.fxml"));
            loader1.setController(controller);
            FXMLLoader loader2 = new FXMLLoader(CaveBuilding.class.getResource("detailWindow.fxml"));
            loader2.setController(controller);
            FXMLLoader loader3 = new FXMLLoader(CaveBuilding.class.getResource("roomWindow.fxml"));
            loader3.setController(controller);
            
            Scene mainScene = new Scene(loader1.load());
            primaryStage.setScene(mainScene);
            
            Scene detailScene = new Scene(loader2.load());
            Stage detailStage = new Stage();
            detailStage.setScene(detailScene);
            controller.setEntityDetailStage(detailStage);
            
            Scene roomScene = new Scene(loader3.load());
            Stage roomStage = new Stage();
            roomStage.setScene(roomScene);
            controller.setRoomDetailStage(roomStage);
            
            primaryStage.setTitle("CaveBuilding");
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(CaveBuilding.class.getName()).log(Level.SEVERE, null, ex);
        }

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
    

    public void setEngine(AntHillEngine e) {
        motor = e;
    }
    
    public AntHillEngine getEngine() {
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
        
        AntBuilder builder = new AntBuilder();
        ants.add(new ActiveEntity("worker", "Worker 1", "Hill", tasks, null, builder.getTraits(AntBuilder.WORKER)));
        ants.add(new ActiveEntity("worker", "Worker 2", "Burrow", tasks, null, builder.getTraits(AntBuilder.WORKER)));
        ants.add(new ActiveEntity("worker", "Worker 3", "Hill", tasks, null, builder.getTraits(AntBuilder.WORKER)));
        ants.add(new ActiveEntity("worker", "Worker 4", "Outside", tasks, null, builder.getTraits(AntBuilder.WORKER)));
        
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
