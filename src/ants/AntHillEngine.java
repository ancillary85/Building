/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

import ancillary.cavebuilding.ActiveEntity;
import ancillary.cavebuilding.Engine;
import ancillary.cavebuilding.Entity;
import ancillary.cavebuilding.InactiveEntity;
import ancillary.cavebuilding.Room;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mike
 */
public class AntHillEngine extends Engine{

    private ArrayList<ActiveEntity> ants;
    private ArrayList<Room> rooms;
    
    public AntHillEngine(){
        setUpEntities(null);
        setUpRooms(null);
    }
    
    public AntHillEngine(List<Room> initRooms, List<Entity> initEntities) {
        setUpRooms(initRooms);
        setUpEntities(initEntities);
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
    
    @Override
    public List<Entity> getEntities() {
        ArrayList<Entity> entities = new ArrayList(ants);       
        return entities;
    }

    @Override
    public void setEntities(List<Entity> newEntities) {
        ants = null;
        setUpEntities(newEntities);
    }

    @Override
    public void removeEntity(Entity e) {
        ants.remove(e);
    }
    
    public List<ActiveEntity> getAnts() {
        return ants;
    }
    
    /**
     * Takes an Entity and attempts to confirm that it is an ant by checking its id.
     * @param a The Entity to be validated
     * @return true if we have an ant, false otherwise
     */
    public static boolean validateAnt(ActiveEntity a) {
        if(a.getID().equals(AntBuilder.ANT) || a.getID().startsWith(AntBuilder.ANT + " ")  || a.getID().endsWith(" " + AntBuilder.ANT)) {
             return true;
        }
        return false;
    }

    @Override
    public void update() {
        for(ActiveEntity a :ants) {
            a.entityUpdate(null);
        }
        
        for(Room r : rooms) {
            // CURRENTLY NOTHING!
        }
    }
    
    public String antToString(ActiveEntity e) {
        if(!validateAnt(e)) {
            return e.getName() + " is not an ant";
        }
        
        String s = e.getName();
        
        return s;
    }
    
    private void setUpRooms(List<Room> initRooms) {
        if(initRooms == null) {
            rooms = new ArrayList<Room>();
        }
        else {
            rooms = new ArrayList<Room>(initRooms);
        }
    }
    
    private void setUpEntities(List<Entity> initEntities) {
        if(initEntities == null) {
            ants = new ArrayList<ActiveEntity>();
        }
        else {
            for(Entity e : initEntities) {
                addEntity(e);
            }
        }
    }

    @Override
    public void addActiveEntity(ActiveEntity e) {
        if(e.isActive() && AntHillEngine.validateAnt(e)) {
            ActiveEntity bug = new ActiveEntity(e);
            ants.add(bug);
        }
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
}
