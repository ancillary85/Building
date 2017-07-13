/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.List;

/**
 *
 * @author Mike
 */
public abstract class Engine {
    protected List<Room> rooms;    
    
    public abstract void action();
    public abstract void addRoom(Room r);
    public abstract List<Room> getRooms();
    
}
