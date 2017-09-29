/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
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
    protected EntityBuilder builder;
    private CaveController controller;
    
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
    public EntityBuilder getBuilder() {
        return builder;
    }

    /**
     * @param builder the builder to set
     */
    public void setBuilder(EntityBuilder builder) {
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
    
    /**
     * Calls TraitEvaluator.resourcesFromGroup(this.getGlobalResources(), this.getActiveEntities()) and
     * then loops through the ActiveEntities and updates them and checks for a completed task. The results
     * of completed tasks are added to the global resources using addResource(name, value) if they are not personal resources.
     * If they are personal resources, it uses addResource(trait.getName(), trait.getValue()).
     * If any of the completed Tasks have trait_type.CREATION results, they are processed.
     * Engines that want more or different behavior should override this. Most Engines can begin their
     * version of update() with super() or just copy the previous line of code.
     */
    public void update() {
        traitEval.resourcesFromGroup(this.getGlobalResources(), this.getActiveEntities());
        
        ArrayList<ActiveEntity> entitiesToAdd = new ArrayList();
        ArrayList<String> IDsToRemove = new ArrayList();
        
        for(ActiveEntity e : this.getActiveEntities()) {
            e.entityUpdate(null);
            
            if(e.getTaskCompleted()) {
                for(Trait result : e.getCurrentTask().getResults()) {
                    // Check for personal resources
                    if(TraitEvaluator.isPResourceTrait(result)) {
                        e.addTrait(result);
                    }
                    // Check for creating new ActiveEntities
                    else if(TraitEvaluator.isCreationTrait(result)) {
                        for(int i = 0; i < result.getValue(); i++) {
                            ActiveEntity newlyMade = builder.makeEntity(result.getName(), null, null);
                            entitiesToAdd.add(newlyMade);
                        }
                    }
                    // Check for resources
                    else if(TraitEvaluator.isResourceTrait(result)) {
                        this.addResource(result.getName(), result.getValue(), result.getDesc());
                    }
                    else if(TraitEvaluator.isUncreateTrait(result)) {
                        IDsToRemove.add(result.getName());
                    }     
                }
                
                unlinkAllEntities(e);
                
            }// End of if taskcompleted
        }// End of for each ActiveEntity loop
        
        // Add any created things
        for(ActiveEntity justCreated : entitiesToAdd) {
            controller.addActive(justCreated);
        }
        
        // Remove any removed things
        for(String id : IDsToRemove) {
            controller.removeByID(id);
        }
        
        //InactiveEntities ?
    }
    
    /**
     * Cancels the current Task of the given ActiveEntity, refunds any costs it had, and unlinks the 
     * ActiveEntity to any others it had been linked too. It calls clearTask() for each of them.
     * @param e 
     */
    public void cancelTaskRefundCosts(ActiveEntity e) {
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
        HashSet<ActiveEntity> removed = unlinkAllEntities(e);
        for(ActiveEntity link : removed) {
            link.clearTask();
        }
    }
    
    
    /**
     * Calls e.setTaskAndTimer(t), then evaluates the costs of t. Resource costs are added to the global
     * resource pool, and personal resources to ActiveEntity e.
     * @param t the chosen Task
     * @param e the ActiveEntity to do the Task
     */
    public void setTaskPayCosts(Task t, ActiveEntity e) {
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
            if(entity.getId().equalsIgnoreCase(id)) {
                count++;
            }
        }
        
        return count;
    }
    
    public int getFreeEntityCount(String id) {
        int count = 0;
        
        for(ActiveEntity entity : getActiveEntities()) {
            if(entity.getId().equalsIgnoreCase(id) && !entity.isBusy()) {
                count++;
            }
        }
        
        return count;
    }
    
    public int getRoomCount(String id) {
        int count = 0;
        
        for(Room r : getRooms()) {
            if(r.getRoomID().equalsIgnoreCase(id)) {
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
    public boolean requirementMet(Trait req, ActiveEntity e) {
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
    
    public boolean requirementsAllMet(Task t, ActiveEntity e) {
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
    
    public boolean equalToReqMet(Trait req, ActiveEntity e) {
        switch(TraitEvaluator.requirementType(req)) {
            case RESOURCE: return getResourceValue(req.getName()) == req.getValue();
            case CREATION: return getEntityCount(req.getName()) == req.getValue();
            case CREATION_LINK: return getFreeEntityCount(req.getName()) == req.getValue();
            case PERSONAL_RESOURCE: return e.getTraitValue(req.getName()) == req.getValue();
            case ROOM: return getRoomCount(req.getName()) == req.getValue();
            default: return false;
        }
    }
    
    public boolean notEqualReqMet(Trait req, ActiveEntity e) {
        switch(TraitEvaluator.requirementType(req)) {
            case RESOURCE: return getResourceValue(req.getName()) != req.getValue();
            case CREATION: return getEntityCount(req.getName()) != req.getValue();
            case CREATION_LINK: return getFreeEntityCount(req.getName()) != req.getValue();
            case PERSONAL_RESOURCE: return e.getTraitValue(req.getName()) != req.getValue();
            case ROOM: return getRoomCount(req.getName()) != req.getValue();
            default: return false;
        }
    }
    
    public boolean lessThanReqMet(Trait req, ActiveEntity e) {
        switch(TraitEvaluator.requirementType(req)) {
            case RESOURCE: return getResourceValue(req.getName()) < req.getValue();
            case CREATION: return getEntityCount(req.getName()) < req.getValue();
            case CREATION_LINK: return getFreeEntityCount(req.getName()) < req.getValue();
            case PERSONAL_RESOURCE: return e.getTraitValue(req.getName()) < req.getValue();
            case ROOM: return getRoomCount(req.getName()) < req.getValue();
            default: return false;
        }
    }
    
    public boolean greaterThanReqMet(Trait req, ActiveEntity e) {
        switch(TraitEvaluator.requirementType(req)) {
            case RESOURCE: return getResourceValue(req.getName()) > req.getValue();
            case CREATION: return getEntityCount(req.getName()) > req.getValue();
            case CREATION_LINK: return getFreeEntityCount(req.getName()) > req.getValue();
            case PERSONAL_RESOURCE: return e.getTraitValue(req.getName()) > req.getValue();
            case ROOM: return getRoomCount(req.getName()) > req.getValue();
            default: return false;
        }
    }
    
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
    public abstract void linkEntities(ActiveEntity e1, ActiveEntity e2);
    public abstract boolean areLinked(ActiveEntity e1, ActiveEntity e2);
    public abstract void unlinkEntities(ActiveEntity e1, ActiveEntity e2);
    public abstract HashSet<ActiveEntity> unlinkAllEntities(ActiveEntity e1);
    public abstract HashSet<ActiveEntity> getLinkedEntities(ActiveEntity e);
    public abstract boolean isLinked(ActiveEntity e);
}
