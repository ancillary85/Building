/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mike
 */
public abstract class Engine {

    public abstract void addRoom(Room r);
    public abstract List<Room> getRooms();
    public abstract void setRooms(List<Room> newRooms);
    public abstract void removeRoom(Room r);
    public abstract void addEntity(Entity e);
    public abstract List<Entity> getEntities();
    public abstract void setEntities(List<Entity> newEntities);
    public abstract void removeEntity(Entity e);
    public abstract void update();    
}
