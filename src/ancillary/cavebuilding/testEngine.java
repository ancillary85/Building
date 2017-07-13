/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Mike
 */
public class testEngine extends Engine {
    
    private final int BUTTON_PREF_WIDTH = 70;
    private final int BUTTON_PREF_HEIGHT = 20;
    private Stage testDetailWindow;
    
    public testEngine(Stage primaryStage){
        setUpTestDetailWindow(primaryStage);
        rooms = new ArrayList<Room>();
    }
    
    private void setUpTestDetailWindow(Stage primaryStage) {
        testDetailWindow = new Stage();
        testDetailWindow.setTitle("Cavern Details");
        testDetailWindow.initOwner(primaryStage);
        testDetailWindow.initModality(Modality.WINDOW_MODAL);
        //caveDetailWindow.initStyle(StageStyle.UNDECORATED);
        
        BorderPane detailBase = new BorderPane();
        
        Scene detailScene = new Scene(detailBase);
        testDetailWindow.setScene(detailScene);
        testDetailWindow.setResizable(false);
        detailScene.getStylesheets().add(CaveBuilding.class.getResource("CaveBuilding.css").toExternalForm());
        
        Button okButton = new Button("OK");
        okButton.setMinSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);
        
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e) {
                
                testDetailWindow.close();
            }
        });
        
        Button cancelButton = new Button("Cancel");
        cancelButton.setMinSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);
        
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e) {
                
                testDetailWindow.close();
            }
        });
        
        HBox okCancel = new HBox();
        okCancel.setPadding(new Insets(25, 25, 25, 25));
        okCancel.setSpacing(10.0);
        okCancel.getChildren().addAll(okButton, cancelButton);
        detailBase.setBottom(okCancel);
        
        testDetailWindow.sizeToScene(); 
    }

    @Override
    public void action() {
        testDetailWindow.showAndWait();
        System.out.println("Hi!");
    }

    @Override
    public void addRoom(Room r) {
        System.out.println(Room.getRoomTotal());
    }

    @Override
    public ArrayList<Room> getRooms() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
