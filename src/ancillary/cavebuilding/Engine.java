/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 *
 * @author Mike
 */
public abstract class Engine {

    protected SimpleListProperty<Trait> global_resources;
    private TraitEvaluator traitEval;

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
     */
    public void addResource(String name, double value) {
        
        for(Trait res : global_resources) {
            if(res.getName().equals(name)) {
                res.addToValue(value);
                return; //return if we found the resource in the pool
            }
        }
        
        global_resources.get().add(new Trait(name, value, Trait.trait_type.RESOURCE));
    }
    
    /**
     * Adds the given resource to the global pool. If the global resource pool already contains a resource Trait with the 
     * same name, it adds their values. If it does not have one, it adds the Trait to the pool.
     * Negative values are allowed.
     * 
     * @param t the resource
     */
    public void addResource(Trait t) {
        
        for(Trait res : global_resources.get()) {
            if(res.getName().equals(t.getName())) {
                res.addToValue(t.getValue());
                return; //return if we found the resource in the pool
            }
        }
        
        global_resources.add(t);
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
     * Calls TraitEvaluator.resourcesFromGroup(this.getGlobalResources(), this.getActiveEntities()) and
     * then loops through the ActiveEntities and updates them and checks for a completed task. The results
     * of completed tasks are added to the global resources using addResource(name, value) if they are not personal resources.
     * If they are personal resources, it uses addResource(trait.getName(), trait.getValue()).
     * Engines that want more or different behavior should override this. Most Engines can begin their
     * version of update() with super() or just copy the previous line of code.
     */
    public void update() {
        traitEval.resourcesFromGroup(this.getGlobalResources(), this.getActiveEntities());
        
        for(ActiveEntity e : this.getActiveEntities()) {
            e.entityUpdate(null);
            
            if(e.getTaskCompleted()) {
                for(Trait result : e.getCurrentTask().getResults()) {
                    if(TraitEvaluator.isPResourceTrait(result)) {
                        e.addTrait(result);
                    }
                    else {
                        this.addResource(result.getName(), result.getValue());
                    }
                }
            }
        }
        
        //InactiveEntities ?
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
    
}
