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

    private enum Ant {EGG, LARVA, PUPA, WORKER, SOLDIER, DRONE, QUEEN}
    
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
        if(e.isActive()) {
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
        if(e.isActive()) {
            ants.remove(e);
        }
    }
    
    public void addAnt(ActiveEntity a) {
        ants.add(a);
    }  

    public List<ActiveEntity> getAnts() {
        return ants;
    }

    public void setAnts(List<ActiveEntity> newActives) {
        ants = new ArrayList<ActiveEntity>(newActives);
    }
    
    public void removeAnt(ActiveEntity a) {
        ants.remove(a);
    }
    
    /**
     * Takes an ActiveEntity and attempts to confirm that it is an ant by checking its name and traits.
     * @param a The ActiveEntity to be validated
     * @return A new ActiveEntity, 
     */
    public ActiveEntity validateAnt(ActiveEntity a) {
        return null;
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
