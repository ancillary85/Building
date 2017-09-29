/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 *
 * @author Mike
 */
public class CaveEngine extends Engine {
       
    private ArrayList<InactiveEntity> inactives;
    private ArrayList<ActiveEntity> actives;
    private ArrayList<Room> rooms;
    
    public CaveEngine(){
        setUpEntities(null);
        setUpRooms(null);
    }
    
    public CaveEngine(List<Room> initRooms, List<ActiveEntity> initEntities) {
        setUpEntities(initEntities);
        setUpRooms(initRooms);
    }
    
    @Override
    public void addRoom(Room r) {
        rooms.add(r);
    }

    @Override
    public List<Room> getRooms() {
        return rooms;
    }

    @Override
    public void removeRoom(Room r) {
        rooms.remove(r);
    }

    @Override
    public void setRooms(List<Room> newRooms) {
        rooms = new ArrayList<Room>(newRooms);
    }
    
    public void addActive(ActiveEntity a) {
        actives.add(a);
    }  

    public List<ActiveEntity> getActives() {
        return actives;
    }

    public void setActives(List<ActiveEntity> newActives) {
        actives = new ArrayList<ActiveEntity>(newActives);
    }
    
    public void removeActive(ActiveEntity a) {
        actives.remove(a);
    }
    
    public void addInactive(InactiveEntity i) {
        inactives.add(i);
    }
    
    public List<InactiveEntity> getInactives() {
        return inactives;
    }
    
    public void setInactives(List<InactiveEntity> newInactives) {
        inactives = new ArrayList<InactiveEntity>(newInactives);
    }
            
    public void removeInactive(InactiveEntity i) {
        inactives.remove(i);
    }

    @Override
    public void update() {
        for(ActiveEntity a :actives) {
            a.entityUpdate(null);
        }
        
        for(Room r : rooms) {
            // CURRENTLY NOTHING!
        }
    }
    
    private void setUpRooms(List<Room> initRooms) {
        if(initRooms == null) {
            rooms = new ArrayList<Room>();
        }
        else {
            rooms = new ArrayList<Room>(initRooms);
        }
    }
    
    private void setUpEntities(List<ActiveEntity> initEntities) {
        if(initEntities == null) {
            actives = new ArrayList<ActiveEntity>();
            inactives = new ArrayList<InactiveEntity>();
        }
        else {
            for(ActiveEntity e : initEntities) {
                addActiveEntity(e);
            }
        }
    }

    @Override
    public void addActiveEntity(ActiveEntity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addInactiveEntity(InactiveEntity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ActiveEntity> getActiveEntities() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InactiveEntity> getInactiveEntities() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setActiveEntities(List<ActiveEntity> newEntities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setInactiveEntities(List<InactiveEntity> newEntities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeActiveEntity(ActiveEntity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeInactiveEntity(InactiveEntity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void linkEntities(ActiveEntity e1, ActiveEntity e2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean areLinked(ActiveEntity e1, ActiveEntity e2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void unlinkEntities(ActiveEntity e1, ActiveEntity e2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashSet<ActiveEntity> getLinkedEntities(ActiveEntity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isLinked(ActiveEntity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashSet<ActiveEntity> unlinkAllEntities(ActiveEntity e1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 

}
