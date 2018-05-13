/*4.7333/3.8444
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author MM
 */
public class CaveBuilding extends Application {
    
    private MainController controller;
    private AntHillEngine motor;

    //this is just for experimenting
    public enum direction {UP, DOWN, LEFT, RIGHT}
        
    @Override
    public void init() throws Exception {
        System.out.println("Hello!");        
        initializeEngine();
    }
    
    @Override
    public void stop() throws Exception {
        System.out.println("Goodbye!");
    }
    
    @Override
    public void start(Stage primaryStage) {
        try {
            controller = new MainController(primaryStage, motor);
            FXMLLoader loader1 = new FXMLLoader(CaveBuilding.class.getResource("mainWindow.fxml"));
            loader1.setController(controller);
            FXMLLoader loader2 = new FXMLLoader(CaveBuilding.class.getResource("detailWindow.fxml"));
            loader2.setController(controller);
            FXMLLoader loader3 = new FXMLLoader(CaveBuilding.class.getResource("roomWindow.fxml"));
            loader3.setController(controller);
            FXMLLoader loader4 = new FXMLLoader(CaveBuilding.class.getResource("eventWindow.fxml"));
            loader4.setController(controller);
            
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
            
            Scene eventScene = new Scene(loader4.load());
            Stage eventStage = new Stage();
            eventStage.setScene(eventScene);
            controller.setEventDetailStage(eventStage);
            
            primaryStage.setTitle("CaveBuilding");
            primaryStage.show();
            
            //TaskParseTest();
            //XMLParseTest();
            
        } catch(Exception ex) {

//catch (IOException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(CaveBuilding.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void initializeEngine() throws ParserConfigurationException, SAXException, IOException {
        motor = new AntHillEngine();
        
        //Read XML file with some number of "list" nodes, each containing one type of thing.
        //A "list" should have a "type" to say if it is a list of Traits, Tasks, etc., and it should have a "name" so we know 
        //how to organize things.
        DocumentBuilder docBuild = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document docTraits = docBuild.parse(CaveBuilding.class.getResourceAsStream("BasicTraits.xml"));
        docTraits.normalize();
        NodeList nL = docTraits.getElementsByTagName("list");
        
        //Loop through my NodeList of "list"'s to sort their contents appropriately by "type" and "name"
        for(int nLIndex = 0; nLIndex < nL.getLength(); nLIndex++) {
            Node currentNode = nL.item(nLIndex);
            //Skip empty ones
            if(!currentNode.hasChildNodes()) {
                continue;
            }
            
            String currentName = currentNode.getAttributes().getNamedItem("name").getNodeValue();
            String currentType = currentNode.getAttributes().getNamedItem("type").getNodeValue();
            NodeList children = currentNode.getChildNodes();
            
            prepareDefaultsArrayList(currentName, currentType, children);
        }
        
        //defaultsTesting();
        predefinedToBuilder(docTraits.getElementsByTagName("traitDefaults"));
        
    }
    
    //Scans the id nums of the elements in the NodeList, in order to see how big the ArrayList needs to be.
    //The ArrayList is padded with nulls to reach that size
    //Then the Nodes are read into the ArrayList, and that ArrayList is given to the Engine with its listName
    //I'm assuming that the NodeList is for Traits, Tasks, ActiveEntities, etc that have an idnum
    private void prepareDefaultsArrayList(String listName, String listType, NodeList nL) {
        int targetSize = 0;
        
        for(int i = 0; i < nL.getLength(); i++) {
                       
            Node tempNode = nL.item(i);
            if(tempNode == null || !tempNode.hasAttributes()) {
                continue;
            }
            
            Node possibleIDNum = tempNode.getAttributes().getNamedItem("idnum");
            
            if(possibleIDNum != null) {
                int size = Integer.parseInt(possibleIDNum.getNodeValue());
                if(size > targetSize) {
                    targetSize = size;
                }
            }
        }
        
        //Increase by 1 so that the array is the correct size. If my largest idnum is 4, the array has an index 4, but is size 5
        targetSize++;
        
        if(listType.equalsIgnoreCase("trait")) {
            ArrayList<Trait> traitsList = new ArrayList();
            for(int nullPad = 0; nullPad < targetSize; nullPad++) {
                traitsList.add(null);
            }
            
            for(int traitListIndex = 0; traitListIndex < nL.getLength(); traitListIndex++) {
                Node temp = nL.item(traitListIndex);
                if(!temp.hasAttributes() || !temp.getNodeName().equals("trait")) {
                    continue;
                }
                
                Trait currentTrait = TraitParser.parseTrait(nL.item(traitListIndex));
                traitsList.set(currentTrait.getIdNum(), currentTrait);
            }
            
            motor.getPredefinedData().addDefaultTraits(listName, traitsList);
        }
        else if(listType.equalsIgnoreCase("task")) {
            ArrayList<Task> tasksList = new ArrayList();
            for(int nullPad = 0; nullPad < targetSize; nullPad++) {
                tasksList.add(null);
            }
            
            for(int taskListIndex = 0; taskListIndex < nL.getLength(); taskListIndex++) {
                Node temp = nL.item(taskListIndex);
                if(!temp.hasAttributes()) {
                    continue;
                }
                
                Task currentTask = TaskParser.parseTask(temp);
                tasksList.set(currentTask.getIdNum(), currentTask);
            }
            
            motor.getPredefinedData().addDefaultTasks(listName, tasksList);
        }
        
    }
    
    //Uses the PredefinedData to populate some of the ID->traits map that the Builder has
    private void predefinedToBuilder(NodeList defaultTraits) {
            
        for(int i = 0; i < defaultTraits.getLength(); i++) {
            Node currentIDNode = defaultTraits.item(i);
            if(!currentIDNode.hasAttributes() || !currentIDNode.hasChildNodes()) {
                continue;
            }
            
            String currentIDName = currentIDNode.getAttributes().getNamedItem("ID").getNodeValue();
            ArrayList<Trait> traitsList = new ArrayList();
            NodeList traitsOfCurrentID = currentIDNode.getChildNodes();
            
            for(int childIndex = 0; childIndex < traitsOfCurrentID.getLength(); childIndex++) {
                if(traitsOfCurrentID.item(childIndex).hasAttributes()) {
                    NamedNodeMap targets = traitsOfCurrentID.item(childIndex).getAttributes();
                    String targetList = targets.getNamedItem("listName").getNodeValue();
                    int targetIndex = Integer.parseInt(targets.getNamedItem("listIndex").getNodeValue());
                    int defaultValue = Integer.parseInt(targets.getNamedItem("value").getNodeValue());
                    
                    //targetIndex < 0 means we want the whole list
                    if(targetIndex < 0) {
                        for(int targetListIndex = 0; targetListIndex < motor.getPredefinedData().getThisTraitList(targetList).size(); targetListIndex++) {
                            if(motor.getPredefinedData().getTraitByNum(targetList, targetListIndex) != null) {
                                Trait result = new Trait(motor.getPredefinedData().getTraitByNum(targetList, targetListIndex));
                                result.setValue(defaultValue);
                                traitsList.add(result);
                            }
                        }
                    }
                    else {
                        if(motor.getPredefinedData().getTraitByNum(targetList, targetIndex) != null) {
                            Trait result = new Trait(motor.getPredefinedData().getTraitByNum(targetList, targetIndex));
                            result.setValue(defaultValue);
                            traitsList.add(result);
                        }
                    }
                }
            }
            
            motor.getBuilder().setTraits(currentIDName, traitsList);
        }
    }
    
    private void defaultsTesting() {
        HashMap<String, ArrayList<Trait>> foo = motor.getPredefinedData().getDefaultTraits();
        
        System.out.println("Keys");
        for(String s : foo.keySet()) {
            System.out.print(s + " ");
        }
        System.out.println();
        
        for(String s : foo.keySet()) {
            System.out.println(s + " contents:");
            for(Trait t : foo.get(s)) {
                if(t == null) {
                    continue;
                }
                
                System.out.print(t + "; ");
            }
            System.out.println();
        }
        
        System.out.println(motor.getPredefinedData().getTraitByNum("skills", -2));
        System.out.println(motor.getPredefinedData().getTraitByNum("skills", 0));
        System.out.println(motor.getPredefinedData().getTraitByNum("skills", 1));
        System.out.println(motor.getPredefinedData().getTraitByNum("skills", 2));
        System.out.println(motor.getPredefinedData().getTraitByNum("skills", 3));
        System.out.println(motor.getPredefinedData().getTraitByNum("skills", 4));
        System.out.println(motor.getPredefinedData().getTraitByNum("skills", 5));
        System.out.println(motor.getPredefinedData().getTraitByNum("skills", 6));
        System.out.println(motor.getPredefinedData().getTraitByNum("skills", 23));
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
            ants.get(i).activeUpdate(null);
        }
        System.out.println("NEW TIME STEP");
        for(int i = 0; i < ants.size(); i++) {
            ants.get(i).activeUpdate(null);
        }
        System.out.println("NEW TIME STEP");
        for(int i = 0; i < ants.size(); i++) {
            ants.get(i).activeUpdate(null);
        }
        System.out.println("NEW TIME STEP");
        for(int i = 0; i < ants.size(); i++) {
            ants.get(i).activeUpdate(null);
        }
        
    }
    
    private void XMLParseTest() throws IOException {
//        AntBuilder ab = new AntBuilder();
//        ActiveEntity testPilot = ab.makeQueen("Victoria", "the ether");
//        File testBed = new File("marshallingTest.xml");
//        JAXB.marshal(testPilot, testBed);
        ActiveEntity rebuild = JAXB.unmarshal(new File("marshallingTest.xml"), ActiveEntity.class);
        System.out.println(rebuild.getName());
    }
    
    private void TaskParseTest() throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder dB = fact.newDocumentBuilder();
        Document doc = dB.parse(XMLParserTest.class.getResourceAsStream("TaskParserTest.xml"));
        doc.normalize();
        NodeList nL = doc.getElementsByTagName("task");
        
        for(int i = 0; i < nL.getLength(); i++) {
            Task t = TaskParser.parseTask(nL.item(i));
            
            System.out.println("Name: " + t.getName() + "; Duration: " + t.getDuration() + "; Gerund: " + t.getGerund() + "; Flavor: " + t.getFlavor());
            System.out.println("COSTS: " + t.getCosts().size());
            for(Trait cost : t.getCosts()) {
                System.out.println(cost.toStringVerbose());
            }
            
            System.out.println("REQS: " + t.getRequirements().size());
            for(Trait req : t.getRequirements()) {
                System.out.println(req.toStringVerbose());
            }
            
            System.out.println("RESULTS: " + t.getResults().size());
            for(Trait result : t.getResults()) {
                System.out.println(result.toStringVerbose());
            }
        }
       
        /*
        System.out.println("M-m-multi parse!");
        
        Task[] tasks = TaskParser.parseTasks("TaskParserTest.xml");
        
        for(int i = 0; i < tasks.length; i++) {
            System.out.println(tasks[i].toString());
        }
        */
    }
    
    private void TraitParseTest() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder dB = fact.newDocumentBuilder();
        Document doc = dB.parse(CaveBuilding.class.getResourceAsStream("TraitParserTest.xml"));
        doc.normalize();
        NodeList nL = doc.getElementsByTagName("trait");
        
        for(int i = 0; i < nL.getLength(); i++) {
            System.out.println(TraitParser.parseTrait(nL.item(i)).toStringVerbose());
            System.out.println("---");
        }
    }
    
}
