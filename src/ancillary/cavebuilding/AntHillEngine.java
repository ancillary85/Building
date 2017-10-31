/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class AntHillEngine extends Engine {

    private ArrayList<ActiveEntity> ants;
    private ArrayList<Room> rooms;
    private HashMap<Active, HashSet<Active>> entityLinks;
    
    
    public AntHillEngine(){
        setUpEntities(null);
        setUpRooms(null);
        setUpBuilder();
        setUpLinks();
        setUpWarnings();
    }
    
    public AntHillEngine(List<Room> initRooms, List<ActiveEntity> initEntities) {
        setUpRooms(initRooms);
        setUpEntities(initEntities);
        setUpBuilder();
        setUpLinks();
        setUpWarnings();
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
    public void setRoom(Room r, int pos) {
        if(pos < 0 || pos >= rooms.size()) {
            return;
        }
        
        if(rooms.get(pos) == null) {
            rooms.set(pos, r);
        }
        else {
            rooms.get(pos).matchRoom(r);
        }
    }
    
    @Override
    public void setRoomEmpty(Room r) {
        r.setName("Empty");
        r.setId("empty");
        r.setType(Room.roomType.WALL);
        r.setDescription("Unworked");
        r.setTraits(new ArrayList<Trait>());
        r.setTasks(new ArrayList<Task>());
    }
    
    @Override
    public List<Room> getReachableRooms() {
        ArrayList<Room> reachableRooms = new ArrayList();
        
        for(Room r : rooms) {
            if(r.isReachable()) {
                reachableRooms.add(r);
            }
        }
        
        return reachableRooms;
    }

    @Override
    public void setReachable(int pos, boolean reachable) {
        rooms.get(pos).setReachable(reachable);
    }
    
    @Override
    public void changeRoom(Room r, Task t) {
        try {
            Class c = AntRoomBuilder.class;
            Method m = null;
            
            for(Trait result : t.getResults()) {
                if(TraitEvaluator.isRoomChangeTrait(result)) {
                    m = c.getMethod(result.getDesc());
                    break;
                }
            }
            
            if(m == null) {
                return;
            }
            
            r.matchRoomShallow((Room)m.invoke(c, new Object[0]));
            r.setReachable(false);
        } catch (Throwable ex) {
            System.out.println(ex);
        }
    }
    
    @Override
    public void changeActiveEntity(ActiveEntity e, Task t) {
        String s = null;
        for(Trait result : t.getResults()) {
            if(TraitEvaluator.isActiveChangeTrait(result)) {
                s = result.getDesc();
                break;
            }
        }

        if(s == null) {
            return;
        }

        ActiveEntity toMatch = builder.makeEntity(s, null);

        e.matchEntity(toMatch);
        Task autoTask = super.getAutoTask(e);
        
        if(autoTask != null) {
            e.setTaskAndTimer(autoTask);
        }
        
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
        rooms = new ArrayList<Room>();
        
        if(initRooms != null) {
            for(Room r : initRooms) {
                if(r == null) {
                    rooms.add(new Room());
                }
                else {
                    rooms.add(new Room(r));
                }
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

    private void setUpWarnings() {
        ArrayList<Trait> warningRequirements = new ArrayList();
        warningRequirements.add(new Trait("Food", 0, TraitBuilder.reqGreaterThanResource()));
        GameEvent defaultAntFoodWarning = new GameEvent("Food is Low", "The queen worries that there soon will be no more food!", "Food Warning", warningRequirements, null);
        super.updateWarnings.add(defaultAntFoodWarning);
        
        warningRequirements.clear();
        warningRequirements.add(new Trait("Machete", 0, TraitBuilder.reqGreaterThanResource()));
        GameEvent defaultAntMacheteWarning = new GameEvent("Machete Supply Crisis", "The queen worries that there soon will be no more machetes!", "Machete Warning", warningRequirements, null);
        super.updateWarnings.add(defaultAntMacheteWarning);
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
    public void linkActives(Active e1, Active e2) {
        HashSet<Active> links1 = entityLinks.get(e1);
        HashSet<Active> links2 = entityLinks.get(e2);
        if(links1 == null) {
            links1 = new HashSet<Active>();
        }
        
        if(links2 == null) {
            links2 = new HashSet<Active>();
        }
        
        links1.add(e2);
        entityLinks.put(e1, links1);
        links2.add(e1);
        entityLinks.put(e2, links2);
    }

    @Override
    public boolean areLinked(Active e1, Active e2) {
        if(entityLinks.get(e1) == null) {
            return false;
        }
        
        if(entityLinks.get(e2) == null) {
            return false;
        }
        
        return entityLinks.get(e1).contains(e2) && entityLinks.get(e2).contains(e1);
    }

    @Override
    public void unlinkActives(Active e1, Active e2) {
        HashSet<Active> links1 = entityLinks.get(e1);
        HashSet<Active> links2 = entityLinks.get(e2);
        if(links1 != null) {
            links1.remove(e2);
        }
        
        if(links2 != null) {
            links2.remove(e1);
        }
        
    }
    
    @Override
    public HashSet<Active> unlinkAllActives(Active e1) {
        HashSet<Active> removed = new HashSet();
        
        if(entityLinks.get(e1) == null) {
            return removed;
        }
        
        for(Active linked : getLinkedActives(e1)) {
            removed.add(linked);
            HashSet<Active> links = entityLinks.get(linked);
            if(links != null) {
                links.remove(e1);
            }
        }
        
        entityLinks.get(e1).clear();
        return removed;
    }

    @Override
    public HashSet<Active> getLinkedActives(Active e) {
        if(entityLinks.get(e) == null) {
            HashSet<Active> links = new HashSet();
            entityLinks.put(e, links);
        }
        
        return entityLinks.get(e);
    }
    
    @Override
    public boolean isLinked(Active e) {
        if(entityLinks.get(e) == null) {
            return false;
        }
        
        return !entityLinks.get(e).isEmpty();
    }
}
