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
 * Generates a Node to display an ActiveEntity's summary
 * @author MLaptop
 */
public class EntitySummary {
    
    /**
     * Generates a VBox to display an ActiveEntity's summary
     * @param entity the entity to generate a summary for
     * @return a VBox containing a Label for the entity's name, a horizontal separator, and it's status
     */
    public static VBox getSummary(ActiveEntity entity){
        VBox labels = new VBox();
        Label name = new Label();
        name.textProperty().bind(entity.getNameProp());
        Label status = new Label();
        
        entity.getBusyProp().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue.booleanValue()) {
                status.setText(entity.getCurrentTask().getGerund());
            }
            else {
                status.setText("");
            }
        });
                
        Label timer = new Label();
        entity.getTaskTimerProp().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if(entity.getTaskTimer() > 0) {
                timer.setText("(" + entity.getTaskTimer() + ")");
            }
            else {
                timer.setText("");
            }
            
        });
        
        HBox statusBox = new HBox();
        statusBox.getChildren().addAll(status, timer);
        
        name.setId("summary-button-name");
        status.setId("summary-button-status");
        timer.setId("summary-button-timer");
        statusBox.setId("summary-button-timer-box");
        labels.setId("summary-button-Node");
        labels.getChildren().addAll(name, new Separator(Orientation.HORIZONTAL), statusBox);
        
        return labels;
    }
    
}
