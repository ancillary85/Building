/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
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
        name.textProperty().bind(room.getRoomNameProp());
        Label status = new Label();
        
        HBox statusBox = new HBox();
        statusBox.getChildren().addAll(status);
        
        name.setId("room-button-name");
        status.setId("room-button-status");
        statusBox.setId("room-button-status-box");
        labels.setId("room-button-Node");
        labels.getChildren().addAll(name, new Separator(Orientation.HORIZONTAL), statusBox);
        
        return labels;
    }
}
