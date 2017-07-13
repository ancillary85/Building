/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.List;
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
public class CaveEngine extends Engine {
    
    private final int BUTTON_PREF_WIDTH = 70;
    private final int BUTTON_PREF_HEIGHT = 20;
    private Stage caveDetailWindow;
    
    public CaveEngine(Stage primaryStage){
        setUpCaveDetailWindow(primaryStage);
    }
    
    private void setUpCaveDetailWindow(Stage primaryStage) {
        caveDetailWindow = new Stage();
        caveDetailWindow.setTitle("Cavern Details");
        caveDetailWindow.initOwner(primaryStage);
        caveDetailWindow.initModality(Modality.WINDOW_MODAL);
        //caveDetailWindow.initStyle(StageStyle.UNDECORATED);
        
        BorderPane detailBase = new BorderPane();
        
        Scene detailScene = new Scene(detailBase);
        caveDetailWindow.setScene(detailScene);
        caveDetailWindow.setResizable(false);
        detailScene.getStylesheets().add(CaveBuilding.class.getResource("CaveBuilding.css").toExternalForm());
        
        Button okButton = new Button("OK");
        okButton.setMinSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);
        
        okButton.setOnAction((ActionEvent e) -> {
            caveDetailWindow.close();
        });
        
        Button cancelButton = new Button("Cancel");
        cancelButton.setMinSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);
        
        cancelButton.setOnAction((ActionEvent e) -> {
            caveDetailWindow.close();
        });
        
        HBox okCancel = new HBox();
        okCancel.setPadding(new Insets(25, 25, 25, 25));
        okCancel.setSpacing(10.0);
        okCancel.getChildren().addAll(okButton, cancelButton);
        detailBase.setBottom(okCancel);
        
        caveDetailWindow.sizeToScene(); 
    }

    @Override
    public void action() {
        caveDetailWindow.showAndWait();
    }

    @Override
    public void addRoom(Room r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Room> getRooms() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
