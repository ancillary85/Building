/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author MLaptop
 */
public class RoomSummary {
    /**
     * Generates a VBox to display a Room's summary
     * @param room the Room to generate a summary for
     * @return a VBox containing a Label for the room's name, a horizontal separator, and it's status
     */
    public static VBox getSummary(Room room){
        VBox labels = new VBox();
        Label name = new Label();
        name.textProperty().bind(room.getNameProp());
        Label status = new Label();
        
        room.getBusyProp().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue.booleanValue()) {
                status.setText(room.getCurrentTask().getGerund());
            }
            else {
                //status.setText(entity.getIdleText());
                status.setText("");
            }
        });
                
        Label timer = new Label();
        room.getTaskTimerProp().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if(room.getTaskTimer() > 0) {
                timer.setText("(" + room.getTaskTimer() + ")");
            }
            else {
                timer.setText("");
            }
            
        });
        
        HBox statusBox = new HBox();
        statusBox.setAlignment(Pos.CENTER);
        statusBox.getChildren().addAll(status, timer);
        
        name.setId("room-button-name");
        status.setId("room-button-status");
        statusBox.setId("room-button-status-box");
        labels.setId("room-button-Node");
        labels.getChildren().addAll(name, new Separator(Orientation.HORIZONTAL), statusBox);
        
        return labels;
    }
}
