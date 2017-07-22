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
public class AntHillEngine extends Engine{

    private ArrayList<ActiveEntity> ants;
    private ArrayList<Room> rooms;
    
    public AntHillEngine(){
        setUpEntities(null);
        setUpRooms(null);
    }
    
    public AntHillEngine(List<Room> initRooms, List<Entity> initEntities) {
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
    
    @Override
    public void addEntity(Entity e) {
        if(e.isActive() && validateAnt(e)) {
            ants.add(new ActiveEntity(e));
        }
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
    public boolean validateAnt(Entity a) {
        if(a.getID().equalsIgnoreCase("ant") || a.getID().startsWith("ant ") || a.getID().endsWith(" ant")) {
            if(a.isActive() && a instanceof ActiveEntity) {
                return true;
            }
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
    
    public String antToString(Entity e) {
        if(!validateAnt(e)) {
            return e.getName() + " is not an ant";
        }
        
        String s = new String(e.getName());
        
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
}
