/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Mike
 */
public class AntHillEngine extends Engine{

    private ArrayList<ActiveEntity> ants;
    private ArrayList<Room> rooms;
    private HashMap<ActiveEntity, HashSet<ActiveEntity>> entityLinks;
    
    public AntHillEngine(){
        setUpEntities(null);
        setUpRooms(null);
        setUpBuilder();
        setUpLinks();
    }
    
    public AntHillEngine(List<Room> initRooms, List<ActiveEntity> initEntities) {
        setUpRooms(initRooms);
        setUpEntities(initEntities);
        setUpBuilder();
        setUpLinks();
    }
       
    @Override
    public void addRoom(Room r) {
        rooms.add(r);
        
        // If it doens't have a valid position, don't worry about it
        if(r.getxPos() < 0 || r.getyPos() < 0) {
            return;
        }
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
    
    public List<ActiveEntity> getAnts() {
        return ants;
    }
    
    /**
     * Takes an Entity and attempts to confirm that it is an ant by checking its id.
     * @param a The Entity to be validated
     * @return true if we have an ant, false otherwise
     */
    public static boolean validateAnt(ActiveEntity a) {
        if(a == null) {
            return false;
        }
        
        String[] parts = a.getId().split(" ");
        
        for(String s : parts) {
            if(s.equalsIgnoreCase(AntBuilder.ANT)) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public void update() {
        super.update();
        
//        for(ActiveEntity a :ants) {
//            a.entityUpdate(null);
//        }
//        
//        for(Room r : rooms) {
//            // CURRENTLY NOTHING!
//        }
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
            rooms = new ArrayList<Room>(initRooms.size());
            for(Room r : initRooms) {
                if(r == null) {
                    continue;
                }
                
                rooms.add(new Room(r));
            }
        }
    }
    
    private void setUpEntities(List<ActiveEntity> initEntities) {
        if(initEntities == null) {
            ants = new ArrayList<ActiveEntity>();
        }
        else {
            ants = new ArrayList<ActiveEntity>(initEntities.size());
            for(ActiveEntity e : initEntities) {
                addActiveEntity(e);
            }
        }
    }
    
    private void setUpBuilder() {
        super.builder = new AntBuilder();
    }
    
    private void setUpLinks() {
        entityLinks = new HashMap();
    }

    /**
     * Adds the given ActiveEntity e if it is not null, and e does not already exist 
     * @param e The ActiveEntity to add
     */
    @Override
    public void addActiveEntity(ActiveEntity e) {
        if(e != null && !ants.contains(e)) {
            ants.add(e);
        }
    }

    @Override
    public void addInactiveEntity(InactiveEntity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ActiveEntity> getActiveEntities() {
        return ants;
    }

    @Override
    public List<InactiveEntity> getInactiveEntities() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setActiveEntities(List<ActiveEntity> newEntities) {
        ants = null;
        setUpEntities(newEntities);
    }

    @Override
    public void setInactiveEntities(List<InactiveEntity> newEntities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeActiveEntity(ActiveEntity e) {
        ants.remove(e);
    }

    @Override
    public void removeInactiveEntity(InactiveEntity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void linkEntities(ActiveEntity e1, ActiveEntity e2) {
        HashSet<ActiveEntity> links1 = entityLinks.get(e1);
        HashSet<ActiveEntity> links2 = entityLinks.get(e2);
        if(links1 == null) {
            links1 = new HashSet<ActiveEntity>();
        }
        
        if(links2 == null) {
            links2 = new HashSet<ActiveEntity>();
        }
        
        links1.add(e2);
        entityLinks.put(e1, links1);
        links2.add(e1);
        entityLinks.put(e2, links2);
    }

    @Override
    public boolean areLinked(ActiveEntity e1, ActiveEntity e2) {
        if(entityLinks.get(e1) == null) {
            return false;
        }
        
        if(entityLinks.get(e2) == null) {
            return false;
        }
        
        return entityLinks.get(e1).contains(e2) && entityLinks.get(e2).contains(e1);
    }

    @Override
    public void unlinkEntities(ActiveEntity e1, ActiveEntity e2) {
        HashSet<ActiveEntity> links1 = entityLinks.get(e1);
        HashSet<ActiveEntity> links2 = entityLinks.get(e2);
        if(links1 != null) {
            links1.remove(e2);
        }
        
        if(links2 != null) {
            links2.remove(e1);
        }
        
    }
    
    @Override
    public HashSet<ActiveEntity> unlinkAllEntities(ActiveEntity e1) {
        HashSet<ActiveEntity> removed = new HashSet();
        
        if(entityLinks.get(e1) == null) {
            return removed;
        }
        
        for(ActiveEntity linked : getLinkedEntities(e1)) {
            removed.add(linked);
            HashSet<ActiveEntity> links = entityLinks.get(linked);
            if(links != null) {
                links.remove(e1);
            }
        }
        
        entityLinks.get(e1).clear();
        return removed;
    }

    @Override
    public HashSet<ActiveEntity> getLinkedEntities(ActiveEntity e) {
        if(entityLinks.get(e) == null) {
            HashSet<ActiveEntity> links = new HashSet();
            entityLinks.put(e, links);
        }
        
        return entityLinks.get(e);
    }
    
    @Override
    public boolean isLinked(ActiveEntity e) {
        if(entityLinks.get(e) == null) {
            return false;
        }
        
        return !entityLinks.get(e).isEmpty();
    }

}
