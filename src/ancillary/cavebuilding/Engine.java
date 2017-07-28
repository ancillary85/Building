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

    public abstract void addRoom(Room r);
    public abstract List<Room> getRooms();
    public abstract void setRooms(List<Room> newRooms);
    public abstract void removeRoom(Room r);
    public abstract void addActiveEntity(ActiveEntity e);
    public abstract void addInactiveEntity(InactiveEntity e);
    public abstract List<ActiveEntity> getActiveEntities();
    public abstract List<InactiveEntity> getInactiveEntities();
    public abstract void setActiveEntities(List<ActiveEntity> newEntities);
    public abstract void setInactiveEntities(List<InactiveEntity> newEntities);
    public abstract void removeActiveEntity(ActiveEntity e);
    public abstract void removeInactiveEntity(InactiveEntity e);
    public abstract void update();    
}
