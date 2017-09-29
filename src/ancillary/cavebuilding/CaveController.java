/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import javafx.event.ActionEvent;

/**
 *
 * @author MLaptop
 */
interface CaveController {
    
    void addRoom(Room r);
    void removeRoom(Room r);
    void addActive(ActiveEntity a);
    void removeActive(ActiveEntity a);
    void removeActive(int index);
    void removeByID(String id);
    void setEngine(Engine e);
    Engine getEngine();
    void exitFired(ActionEvent e);
    void updateFired(ActionEvent e);
}
