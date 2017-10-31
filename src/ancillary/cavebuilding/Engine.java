/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;

/**
 *
 * @author Mike
 */
public abstract class Engine {

    protected SimpleListProperty<Trait> global_resources;
    protected TraitEvaluator traitEval;
    protected ActiveEntityBuilder builder;
    protected CaveController controller;
    protected ArrayList<GameEvent> timedEvents = new ArrayList();
    protected ArrayList<GameEvent> untimedEvents = new ArrayList();
    protected ArrayList<GameEvent> updateWarnings = new ArrayList();
    protected ArrayList<GameEvent> unresolvedUpdateWarnings = new ArrayList();
    protected int turnCount = 0;
    
    public Engine() {
        this.global_resources = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.traitEval = new TraitEvaluator();
    }
    
    /**
     * @return the global resource pool
     */
    public SimpleListProperty<Trait> getGlobalResources() {
        return global_resources;
    }
    
    /**
     * Adds the given resource to the global pool. If the global resource pool already contains a resource Trait with the 
     * same name, it adds their values. If it does not have one, it makes a new resource Trait with the value and no description. 
     * Negative values are allowed.
     * 
     * @param name the name of the resource
     * @param value the value of the resource
     * @param desc the description of the resource
     */
    public void addResource(String name, int value, String desc) {
        
        for(Trait res : global_resources) {
            if(res.getName().equals(name)) {
                res.addToValue(value);
                return; //return if we found the resource in the pool
            }
        }
        
        EnumSet<Trait.trait_type> traitTypes = EnumSet.of(Trait.trait_type.RESOURCE);
        global_resources.get().add(new Trait(name, value, desc, traitTypes));
    }
    
    /**
     * Adds the given resource to the global pool. If the global resource pool already contains a resource Trait with the 
     * same name, it adds their values. If it does not have one, it adds the Trait to the pool.
     * Negative values are allowed.
     * 
     * @param t the resource
     */
    public void addResource(Trait t) {
        addResource(t.getName(), t.getValue(), t.getDesc());
    }
    
    /**
     * @return a String representation of the global resources' names and values
     */
    public String resourcesReport() {
        String[] result = new String[global_resources.size()];
        
        for(int i = 0; i < result.length; i++) {
            result[i] = global_resources.get(i).getName() + ": " + global_resources.get(i).getValue();
        }
        
        return String.join("\n", result);
    }
    
    /**
     * @return a String representation of the global resources' names, values, and descriptions
     */
    public String resourcesReportDesc() {
        String[] result = new String[global_resources.size()];
        
        for(int i = 0; i < result.length; i++) {
            result[i] = global_resources.get(i).getName() + ": " + global_resources.get(i).getValue() + " --- " + global_resources.get(i).getDesc();
        }
        
        return String.join("\n", result);
    }

    /**
     * @return the traitEval
     */
    public TraitEvaluator getTraitEval() {
        return traitEval;
    }

    /**
     * @param traitEval the traitEval to set
     */
    public void setTraitEval(TraitEvaluator traitEval) {
        this.traitEval = traitEval;
    }

    /**
     * @return the builder
     */
    public ActiveEntityBuilder getBuilder() {
        return builder;
    }

    /**
     * @param builder the builder to set
     */
    public void setBuilder(ActiveEntityBuilder builder) {
        this.builder = builder;
    }

    /**
     * @return the controller
     */
    public CaveController getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(CaveController controller) {
        this.controller = controller;
    }
    
    public int getTurnCount() {
        return turnCount;
    }
    
    public void setTurnCount(int newTurnCount) {
        turnCount = newTurnCount;
    }
    
    public ArrayList<GameEvent> getTimedEvents() {
        return timedEvents;
    }
    
    public void setTimedEvents(ArrayList<GameEvent> newTimed) {
        timedEvents = newTimed;
    }
    
    public ArrayList<GameEvent> getUntimedEvents() {
        return untimedEvents;
    }
    
    public void setUntimedEvents(ArrayList<GameEvent> newUntimed) {
        untimedEvents = newUntimed;
    }
    
    public ArrayList<GameEvent> getUpdateWarnings() {
        return updateWarnings;
    }
    
    public void setUpdateWarnings(ArrayList<GameEvent> newWarnings) {
        updateWarnings = newWarnings;
    }
    
    public ArrayList<GameEvent> getUnresolvedUpdateWarnings() {
        return unresolvedUpdateWarnings;
    }
    
    /**
     * Calls TraitEvaluator.resourcesFromGroup(this.getGlobalResources(), this.getActiveEntities()) and
     * then loops through the ActiveEntities and updates them and checks for a completed task. The results
     * of completed tasks are added to the global resources using addResource(name, value) if they are not personal resources.
     * If they are personal resources, it uses addResource(trait.getName(), trait.getValue()).
     * If any of the completed Tasks have trait_type.CREATION results, they are processed.
     * If any of the completed Tasks have trait_type.ROOM_CHANGE results, they are processed.
     * Then it does all this for Rooms. 
     * Then it checks all the GameEvents in timedEvents and untimedEvents.
     * Finally, it increments the turnCount.
     * 
     * Engines that want more or different behavior should override this. Most Engines can begin their
     * version of update() with super() or just copy the previous line of code.
     */
    public void update() {
        traitEval.resourcesFromGroup(this.getGlobalResources(), this.getActiveEntities());
        
        ArrayList<ActiveEntity> entitiesToAdd = new ArrayList();
        ArrayList<String> IDsToRemove = new ArrayList();
        HashMap<ActiveEntity,Task> activesToChange = new HashMap();
        ArrayList<Room> roomsToChange = new ArrayList();
        
        for(ActiveEntity e : this.getActiveEntities()) {
            e.activeUpdate(null);
            
            if(e.getTaskCompleted()) {
                boolean changeSelf = false;
                
                for(Trait result : e.getCurrentTask().getResults()) {
                    // Check for personal resources
                    if(TraitEvaluator.isPResourceTrait(result)) {
                        e.addTrait(result);
                    }
                    // Check for creating new ActiveEntities
                    else if(TraitEvaluator.isCreationTrait(result)) {
                        for(int i = 0; i < result.getValue(); i++) {
                            ActiveEntity newlyMade;
                            newlyMade = builder.makeEntity(result.getName(), null);
                            
                            entitiesToAdd.add(newlyMade);
                        }
                    }
                    // Check for changing self
                    else if(TraitEvaluator.isActiveChangeTrait(result)) {
                        changeSelf = true;
                    }
                    // Check for changing others
                    else if(TraitEvaluator.isActiveChangeOther(result)) {
                        for(Active a : getLinkedActives(e)) {
                            if(a instanceof ActiveEntity) {
                                activesToChange.put((ActiveEntity)a, e.getCurrentTask());
                            }
                        }
                    }                                        
                    // Check for changing Rooms
                    else if(TraitEvaluator.isRoomChangeTrait(result)) {
                        System.out.println("Looks like we're making a room!");
                    }
                    // Check for resources
                    else if(TraitEvaluator.isResourceTrait(result)) {
                        this.addResource(result.getName(), result.getValue(), result.getDesc());
                    }
                    // Check for removing things
                    else if(TraitEvaluator.isUncreateTrait(result)) {
                        IDsToRemove.add(result.getName());
                    }     
                } // end of looping through the Task's results
                
                unlinkAllActives(e);
                
                if(changeSelf) {
                    changeActiveEntity(e, e.getCurrentTask());
                }
                else {
                    e.clearTask();
                }
            }// End of if taskcompleted
        }// End of for each ActiveEntity loop
        
        // Add any created things
        for(ActiveEntity justCreated : entitiesToAdd) {
            Task autoTask = this.getAutoTask(justCreated);
                
            if(autoTask != null) {
                justCreated.setTaskAndTimer(autoTask);
            }
            
            controller.addActive(justCreated);
        }
        
        // Remove any removed things
        for(String id : IDsToRemove) {
            controller.removeByID(id);
        }
        
        // Change others
        for(ActiveEntity toChange : activesToChange.keySet()) {
            changeActiveEntity(toChange, activesToChange.get(toChange));
        }
        
        entitiesToAdd.clear();
        IDsToRemove.clear();
        activesToChange.clear();
        roomsToChange.clear();
        //InactiveEntities ?
        
        //Rooms
        for(Room r : this.getRooms()) {
            r.activeUpdate(null);
            
            if(r.getTaskCompleted()) {
                boolean changeRoom = false;
                
                for(Trait result : r.getCurrentTask().getResults()) {
                    // Check for personal resources
                    if(TraitEvaluator.isPResourceTrait(result)) {
                        r.addTrait(result);
                    }
                    // Check for creating new ActiveEntities
                    else if(TraitEvaluator.isCreationTrait(result)) {
                        ActiveEntity newlyMade;
                        newlyMade = builder.makeEntity(result.getName(), null);
                            
                            
                        entitiesToAdd.add(newlyMade);
                    }
                    // Check for changing Rooms
                    else if(TraitEvaluator.isRoomChangeTrait(result)) {
                        roomsToChange.add(r);
                        changeRoom = true;
                    }
                    // Check for resources
                    else if(TraitEvaluator.isResourceTrait(result)) {
                        this.addResource(result.getName(), result.getValue(), result.getDesc());
                    }
                    else if(TraitEvaluator.isUncreateTrait(result)) {
                        IDsToRemove.add(result.getName());
                    }     
                }
                
                unlinkAllActives(r);
                if(changeRoom)
                {
                    changeRoom(r, r.getCurrentTask());
                }
            }// End of if taskcompleted
        }// End of for each Room loop
        
        
        // Add any created things
        for(ActiveEntity justCreated : entitiesToAdd) {
            Task autoTask = this.getAutoTask(justCreated);
                System.out.println("Engine thinks " + justCreated.getName() + " has # tasks " + justCreated.getTasks().size());
            if(autoTask != null) {
                
                System.out.println("Engine thinks " + justCreated.getName() + " has auto task " + autoTask.getName());
                justCreated.setTaskAndTimer(autoTask);
            }
            controller.addActive(justCreated);
        }
        
        // Remove any removed things
        for(String id : IDsToRemove) {
            controller.removeByID(id);
        }
        
        checkTimedEvents();
        checkUntimedEvents();
        
        turnCount++;
    }
    
    public void checkTimedEvents() {
        
    }
        
    public void checkUntimedEvents() {
        
    }
    
    public void checkUpdateWarnings() {
        unresolvedUpdateWarnings.clear();
        
        for(GameEvent warning : updateWarnings) {
            for(Trait req : warning.getRequirements()) {
                if(!requirementMet(req, null)) {
                    unresolvedUpdateWarnings.add(warning);
                    break; // move to next warning
                }
            }
        }
    }
    
    /**
     * Returns null if it doesn't have an auto task
     * @param e
     * @return 
     */
    public Task getAutoTask(ActiveEntity e) {
        // Check for auto task
        for(Task t : e.getTasks()) {
            for(Trait result : t.getResults()) {
                if(TraitEvaluator.isAutoTaskTrait(result)) {
                    return t;
                }
            }
        }
        return null;
    }
    
    public boolean currentUncancelableTask(ActiveEntity e) {
        for(Trait result : e.getCurrentTask().getResults()) {
            if(TraitEvaluator.isUncancelable(result)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Cancels the current Task of the given Active, refunds any costs it had, and unlinks the 
     * Active to any others it had been linked too. It calls clearTask() for each of them.
     * @param e 
     */
    public void cancelTaskRefundCosts(Active e) {
        Task t = e.getCurrentTask();
        
        for(Trait cost : t.getCosts()) {
            if(TraitEvaluator.isPResourceTrait(cost)) {
                e.addTraitValue(cost.getName(), cost.getValue() * -1);
            }
            else {
                this.addResource(cost.getName(), cost.getValue() * -1, cost.getDesc());
            }
        }
        
        e.clearTask();
        HashSet<Active> removed = unlinkAllActives(e);
        for(Active link : removed) {
            link.clearTask();
        }
    }
    
    
    /**
     * Calls e.setTaskAndTimer(t), then evaluates the costs of t. Resource costs are added to the global
     * resource pool, and personal resources to Active e.
     * @param t the chosen Task
     * @param e the Active to do the Task
     */
    public void setTaskPayCosts(Task t, Active e) {
        e.setTaskAndTimer(t);
        
        for(Trait cost : t.getCosts()) {
            if(TraitEvaluator.isPResourceTrait(cost)) {
                e.addTrait(cost);
            }
            else {
                this.addResource(cost);
            }
        }
    }
    
    public int getResourceValue(String name) {
        for(Trait resource : global_resources) {
            if(resource.getName().equalsIgnoreCase(name)) {
                return resource.getValue();
            }
        }
        return 0;
    }
    
    public int getEntityCount(String id) {
        int count = 0;
        
        for(ActiveEntity entity : getActiveEntities()) {
            
            for(String subID : entity.getId().split(",")) {
                if(subID.equalsIgnoreCase(id)) {
                    count++;
                    break;
                }
            }
        }
        
        return count;
    }
    
    public int getFreeEntityCount(String id) {
        int count = 0;
        
        for(ActiveEntity entity : getActiveEntities()) {
            if(entity.isBusy()) {
                continue;
            }
            
            for(String subID : entity.getId().split(",")) {
                if(subID.equalsIgnoreCase(id)) {
                    count++;
                    break;
                }
            }
        }
        
        return count;
    }
    
    public int getEntityCountByName(String name) {
        int count = 0;
        
        for(ActiveEntity entity : getActiveEntities()) {
            if(entity.getName().equalsIgnoreCase(name)) {
                    count++;
            }
        }
        
        return count;
    }
    
    public int getFreeEntityCountByName(String name) {
        int count = 0;
        
        for(ActiveEntity entity : getActiveEntities()) {
            if(entity.isBusy()) {
                continue;
            }
            
            if(entity.getName().equalsIgnoreCase(name)) {
                    count++;
            }
        }
        
        return count;
    }
    
    public int getRoomCount(String id) {
        int count = 0;
        
        for(Room r : getRooms()) {
            if(r.getId().equalsIgnoreCase(id)) {
                count++;
            }
        }
        
        return count;
    }
    
    public int getRoomCountByName(String name) {
        int count = 0;
        
        for(Room r : getRooms()) {
            if(r.getName().equalsIgnoreCase(name)) {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * Returns true if given a null or an invalid requirement
     * @param t
     * @param e
     * @return 
     */
    public boolean requirementMet(Trait req, Active e) {
        if(req == null || !TraitEvaluator.isRequirement(req)) {
            return true;
        }
        
        Trait.trait_type condition = TraitEvaluator.requirementCondition(req);
            if(condition == null) {
                
                return false;
            }
    
            switch(condition) {
                case EQUALTO:           if(!equalToReqMet(req, e)) 
                                                        {return false;}
                                                    break;
                case NOTEQUAL:         if(!notEqualReqMet(req, e))
                                                        {return false;}
                                                    break;
                case LESSTHAN:          if(!lessThanReqMet(req, e)) 
                                                        {return false;}
                                                    break;
                case GREATERTHAN:   if(!greaterThanReqMet(req, e)) 
                                                        {return false;}
                                                    break;
            }
        
        return true;
    }
    
    public boolean requirementsAllMet(Task t, Active e) {
    //if there are no requirements, then true
        if(t.getRequirements().isEmpty()) {
            return true;
        }
        
    // I am assuming a requirement can only have one condition, so no lessthan and equal
        for(Trait req : t.getRequirements()) {
            if(!requirementMet(req, e)){
                return false;
            }
        }
        
        return true;
    }
    
    public boolean equalToReqMet(Trait req, Active e) {
        switch(TraitEvaluator.requirementType(req)) {
            case RESOURCE: return getResourceValue(req.getName()) == req.getValue();
            case CREATION: return getEntityCount(req.getName()) == req.getValue();
            case CREATION_LINK: return getFreeEntityCount(req.getName()) == req.getValue();
            case PERSONAL_RESOURCE: return e.getTraitValue(req.getName()) == req.getValue();
            case ROOM_LINK:
            case ROOM: return getRoomCount(req.getName()) == req.getValue();
            default: return false;
        }
    }
    
    public boolean notEqualReqMet(Trait req, Active e) {
        switch(TraitEvaluator.requirementType(req)) {
            case RESOURCE: return getResourceValue(req.getName()) != req.getValue();
            case CREATION: return getEntityCount(req.getName()) != req.getValue();
            case CREATION_LINK: return getFreeEntityCount(req.getName()) != req.getValue();
            case PERSONAL_RESOURCE: return e.getTraitValue(req.getName()) != req.getValue();
            case ROOM_LINK:
            case ROOM: return getRoomCount(req.getName()) != req.getValue();
            default: return false;
        }
    }
    
    public boolean lessThanReqMet(Trait req, Active e) {
        switch(TraitEvaluator.requirementType(req)) {
            case RESOURCE: return getResourceValue(req.getName()) < req.getValue();
            case CREATION: return getEntityCount(req.getName()) < req.getValue();
            case CREATION_LINK: return getFreeEntityCount(req.getName()) < req.getValue();
            case PERSONAL_RESOURCE: return e.getTraitValue(req.getName()) < req.getValue();
            case ROOM_LINK:
            case ROOM: return getRoomCount(req.getName()) < req.getValue();
            default: return false;
        }
    }
    
    public boolean greaterThanReqMet(Trait req, Active e) {
        switch(TraitEvaluator.requirementType(req)) {
            case RESOURCE: return getResourceValue(req.getName()) > req.getValue();
            case CREATION: return getEntityCount(req.getName()) > req.getValue();
            case CREATION_LINK: return getFreeEntityCount(req.getName()) > req.getValue();
            case PERSONAL_RESOURCE: return e.getTraitValue(req.getName()) > req.getValue();
            case ROOM_LINK:
            case ROOM: return getRoomCount(req.getName()) > req.getValue();
            default: return false;
        }
    }
    
    public abstract void addRoom(Room r);
    public abstract List<Room> getRooms();
    public abstract List<Room> getReachableRooms();
    public abstract void setReachable(int pos, boolean reachable);
    public abstract void setRooms(List<Room> newRooms);
    public abstract void setRoom(Room r, int pos);
    public abstract void setRoomEmpty(Room r);
    public abstract void removeRoom(Room r);
    public abstract void changeRoom(Room r, Task t);
    public abstract void changeActiveEntity(ActiveEntity e, Task t);
    public abstract void addActiveEntity(ActiveEntity e);
    public abstract void addInactiveEntity(InactiveEntity e);
    public abstract List<ActiveEntity> getActiveEntities();
    public abstract List<InactiveEntity> getInactiveEntities();
    public abstract void setActiveEntities(List<ActiveEntity> newEntities);
    public abstract void setInactiveEntities(List<InactiveEntity> newEntities);
    public abstract void removeActiveEntity(ActiveEntity e);
    public abstract void removeInactiveEntity(InactiveEntity e);
    public abstract void linkActives(Active e1, Active e2);
    public abstract boolean areLinked(Active e1, Active e2);
    public abstract void unlinkActives(Active e1, Active e2);
    public abstract HashSet<Active> unlinkAllActives(Active e1);
    public abstract HashSet<Active> getLinkedActives(Active e);
    public abstract boolean isLinked(Active e);
}
