/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Mike
 */

public class ActiveEntity implements Active {

    /**
     * The Entity's id
     */
    protected String id;
    
    /**
     * The Entity's name, to be displayed to the player
     */
    protected SimpleStringProperty name;
    
    /**
     * Whether the Entity is busy
     */
    protected SimpleBooleanProperty busy;
    
    /**
     * The Entity's location
     */
    protected SimpleStringProperty location;
    
    /**
     * The Entity's traits
     */
    protected ArrayList<Trait> traits;
    protected ArrayList<Task> tasks;
    protected SimpleIntegerProperty taskTimer;
    protected Task currentTask;
    protected SimpleBooleanProperty taskCompleted;
    protected SimpleStringProperty idleText;
    protected Trait.trait_type[] traitDisplayPriority = {Trait.trait_type.ATTRIBUTE, Trait.trait_type.PRODUCTION, Trait.trait_type.COMBAT, Trait.trait_type.FLAVOR};
    
    /**
     * The Entity's builderBadge, for looking it up in a Builder. This is null if there is no predefined entry for the Entity
     */
    protected String builderBadge = null;
    
    public ActiveEntity() {
        this.id = "placeholder";
        this.name = new SimpleStringProperty("blank");
        this.busy = new SimpleBooleanProperty(false);
        this.location = new SimpleStringProperty("nowhere");
        this.tasks = new ArrayList<Task>();
        this.taskTimer = new SimpleIntegerProperty(-1);
        this.currentTask = new Task();
        this.taskCompleted = new SimpleBooleanProperty(false);
        this.idleText = new SimpleStringProperty("doing nothing");
        this.traits = new ArrayList<Trait>();
    }
    
    
    /**
     * Please don't give an empty list of tasks
     * @param id
     * @param name
     * @param location
     * @param newTasks 
     * @param idleText 
     * @param traits 
     */
    public ActiveEntity(String id, String name, String location, List<Task> newTasks, String idleText, List<Trait> traits) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.busy = new SimpleBooleanProperty(false);
        this.location = new SimpleStringProperty(location);        
        this.setUpTasks(newTasks);
        this.taskTimer = new SimpleIntegerProperty(-1);
        this.currentTask = new Task();
        this.taskCompleted = new SimpleBooleanProperty(false);
        this.idleText = new SimpleStringProperty(idleText);
        this.traits = new ArrayList<Trait>(traits);
    }
    
    public ActiveEntity(String id, String name, String location, List<Task> newTasks, String idleText, Trait[] traits) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.busy = new SimpleBooleanProperty(false);
        this.location = new SimpleStringProperty(location);        
        this.setUpTasks(newTasks);
        this.taskTimer = new SimpleIntegerProperty(-1);
        this.currentTask = new Task();
        this.taskCompleted = new SimpleBooleanProperty(false);
        this.idleText = new SimpleStringProperty(idleText);
        this.traits = new ArrayList<Trait>(Arrays.asList(traits));
    }
    
    /*
    public ActiveEntity(ActiveEntity e) {
        
        if(e == null)   {
            this.id = "placeholder";
            this.name = new SimpleStringProperty("blank");
            this.active = new SimpleBooleanProperty(true);
            this.busy = new SimpleBooleanProperty(false);
            this.location = new SimpleStringProperty("nowhere");
            this.tasks = new ArrayList<Task>();
            this.idleText = new SimpleStringProperty("doing nothing");
            this.traits = new SimpleStringProperty("none");
        }
        else            {
            this.id = e.getID();
            this.name = new SimpleStringProperty(e.getName());
            this.active = new SimpleBooleanProperty(true);
            this.busy = new SimpleBooleanProperty(false);
            this.location = new SimpleStringProperty(e.getLocation());
            this.setUpTasks(e.getTasks());
            this.idleText = new SimpleStringProperty(e.getIdleText());
            this.traits = new SimpleStringProperty(e.getTraits());
        }
        
        this.taskTimer = 0;
        this.currentTask = new Task();
        this.taskCompleted = new SimpleBooleanProperty(false);
    }*/
    
    /**
     * Return a List of the Entity's tasks.
     * @return a List
     */
    @Override
    public List<Task> getTasks() {
        return tasks;
    }
    
    @Override
    public void setTasks(List<Task> newTasks) {
        if(newTasks == null) {
            tasks = new ArrayList<Task>();
        }
        else {
            tasks = new ArrayList<Task>(newTasks);
        }
    }
    
    private void setUpTasks(List<Task> newTasks) {
        if(newTasks == null) {
            tasks = new ArrayList<Task>();
        }
        else {
            tasks = new ArrayList<Task>(newTasks);
        }
    }
    
    @Override
    public void addTask(Task newTask){
        if(newTask != null) {
            tasks.add(newTask);
        }
    }
    
    public void addTasks(List<Task> newTasks) {
        if(newTasks == null) {
            addTask(null);
        }
        else {
            for(Task t : newTasks) {
                addTask(t);
            }
        }
    }
    
    /**
     * Removes a Task. If that Task is the current Task, then the 
     * current Task is set to "no task," and the Entity is marked not busy.
     * @param oldTask 
     */
    @Override
    public void removeTask(Task oldTask) {
        tasks.remove(oldTask);
        if(currentTask.equals(oldTask)) {
            currentTask.setNoTask();
            this.setNotBusy();
        }       
    }
    
    @Override
    public SimpleIntegerProperty getTaskTimerProp() {
        return taskTimer;
    }
    
    @Override
    public int getTaskTimer() {
        return taskTimer.get();
    }
    
    @Override
    public void setTaskTimer(int newTime) {
        taskTimer.set(newTime);
    }
    
    /**
     * Sets the taskTimer by looking up the duration of the current Task
     */
    @Override
    public void setTaskTimerFromCurrentTask() {
        taskTimer.set(currentTask.getDuration());
    }
    
    @Override
    public Task getCurrentTask() {
        return currentTask;
    }
    
    /**
     * Sets the Entity's current Task, sets its taskTimer from the current Task, marks it as busy, 
     * and taskCompleted to false. If the provided Task is null or is not in the Entity's List of Tasks, no changes are made.
     * 
     * The Entity's taskTimer IS set by this method. 
     * @param t the Task to use
     */
    @Override
    public void setTaskAndTimer(Task t) {
        
        if(t == null || !tasks.contains(t)) {return;}
        
        currentTask.setToNewTask(t);
        setNotBusy();
        setBusy();
        taskCompleted.set(false);
        setTaskTimerFromCurrentTask();
    }
    
    /**
     * Sets the Entity's current Task, marks it as busy, and taskCompleted to false.
     * The parameter is used as the index of the Entity's List of Tasks.
     * If the given index is outside of the range, no changes are made.
     * 
     * The Entity's taskTimer is NOT set by this method. 
     * Calling setTaskTimerFromCurrentTask() afterwards is recommended.
     * Alternatively, use setTaskAndTimer()
     * @param taskNumber the index of the Entity's Task List
     */
    public void setCurrentTask(int taskNumber) {
        if(taskNumber >= tasks.size() || taskNumber < 0) {return;}
        
        currentTask.setToNewTask(tasks.get(taskNumber));
        setNotBusy();
        setBusy();
        taskCompleted.set(false);
    }
    
    /**
     * Sets the Entity's current Task, marks it as busy, and taskCompleted to false.
     * If the provided Task is null or is not in the Entity's List of Tasks, no changes are made.
     * 
     * The Entity's taskTimer is NOT set by this method. 
     * Calling setTaskTimerFromCurrentTask() afterwards is recommended.
     * Alternatively, use setTaskAndTimer()
     * @param newTask the Task to use
     */
    @Override
    public void setCurrentTask(Task newTask) {
        
        if(newTask == null || !tasks.contains(newTask)) {return;}
        
        currentTask.setToNewTask(newTask);
        setNotBusy();
        setBusy();
        taskCompleted.set(false);
    }
    
    /**
     * Sets the Entity's current Task, marks it as busy, and taskCompleted to false.
     * If the provided Task is not in the Entity's List of Tasks, it is added to the List beforehand.
     * If the provided Task is null, no changes are made.
     * 
     * The Entity's taskTimer is NOT set by this method. 
     * Calling setTaskTimerFromCurrentTask() afterwards is recommended.
     * Alternatively, use setTaskAndTimer()
     * 
     * @param newTask the Task to use
     */
    public void addAndSetCurrentTask(Task newTask) {
        if(newTask == null) {return;}
        if(!tasks.contains(newTask)) {tasks.add(newTask);}
        
        currentTask.setToNewTask(newTask);
        setNotBusy();
        setBusy();
        taskCompleted.set(false);
    }

    @Override
    public void matchActive(Active toMatch) {
        this.setId(toMatch.getId());
        this.setName(toMatch.getName());
        this.busy.set(toMatch.isBusy());
        this.currentTask.setToNewTask(toMatch.getCurrentTask());
        this.taskCompleted.set(toMatch.getTaskCompleted());
        this.taskTimer.set(toMatch.getTaskTimer());
        this.setTasks(toMatch.getTasks());
        this.setTraits(toMatch.getTraits());
    }
    
    public void matchEntity(ActiveEntity e) {
        this.setId(e.getId());
        this.setName(e.getName());
        this.setTraits(e.getTraits());
    }
    
    /**
     * Links this entity with another for a task. It's task timer is set to match the task's duration.
     * @param t
     * @param e 
     */
    @Override
    public void setLinked(Task t, Active e) {
        currentTask.setLinkTask(t, e);
        setNotBusy();
        setBusy();
        taskCompleted.set(false);
        setTaskTimerFromCurrentTask();
    }
    
    /**
     * @return taskCompleted as a boolean 
     */
    @Override
    public boolean getTaskCompleted() {
        return taskCompleted.get();
    }
    
    /**
     * @return the Entity's "taskCompleted" property
     */
    @Override
    public SimpleBooleanProperty getTaskCompletedProp() {
        return taskCompleted;
    }

    /**
     * Sets the Entity's taskCompleted property
     * @param taskStatus 
     */
    @Override
    public void setTaskCompleted(boolean taskStatus) {
        taskCompleted.set(taskStatus);
    }
    
    /**
     * Presently does nothing!
     */
    @Override
    public void idle() {
        
    }
    
    /**
     *  Calls idle() and clearTask() if the entity is not busy, or it has a taskTimer < 0. Otherwise, the 
    * taskTimer is decremented. If it reaches zero, completeTask() is called.
     * @param args 
     */
    @Override
    public void activeUpdate(String[] args) {
        //If it isn't doing anything, move on
        if(!isBusy() || taskTimer.get() < 0) {
            idle();
            clearTask();
            return;
        }
        
        if(taskTimer.get() > 0) {            
            taskTimer.set(taskTimer.get() - 1);
       
            if(taskTimer.get() == 0 && !taskCompleted.get()) {
               completeTask();
               return;
            }
        }       
    }
    
    /**
     * Sets the Entity's taskTimer to zero, leaves currentTask alone, taskCompleted to true, and marks it not busy
     */
    @Override
    public void completeTask() {
        taskTimer.set(0);
        this.setNotBusy();
        taskCompleted.set(true);
    }
    
    /**
     * Sets the Entity's taskTimer to zero, currentTask to "no task," taskCompleted to false, and marks it not busy
     */
    @Override
    public void clearTask() {
        taskTimer.set(0);
        currentTask.setNoTask();
        this.setNotBusy();
        taskCompleted.set(false);
    }
     
    /**
     * 
     * @return  the Entity's idle text
     */
    public String getIdleText(){
        return idleText.get();
    }
    
    /**
     * 
     * @return the Entity's idle text SimpleStringProperty
     */
    public SimpleStringProperty getIdleTextProp() {
        return idleText;
    }
    
    public void setIdleText(String newIdle) {
        idleText.set(newIdle);
    }
    
    /**
     * @return the Entity's name as a String
     */
    @Override
    public String getName() {
        return name.get();
    }
    
    /**
     * @return the Entity's name SimpleStringProperty
     */
    public SimpleStringProperty getNameProp() {
        return name;
    }
    
    @Override
    public void setName(String newName) {
        name.set(newName);
    }

    /**
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Returns true if any of the Strings in this.getId().split(",") equals idFragment.
     * If idFragment contains commas, there's gonna be no luck.
     * @param idFragment 
     * @return 
     */
    @Override
    public boolean idMatch(String idFragment) {
        for(String piece : id.split(",")) {
            if(piece.equalsIgnoreCase(idFragment)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return the Entity's "busy" SimpleBooleanProperty
     */
    @Override
    public SimpleBooleanProperty getBusyProp() {
        return busy;
    }
    
    /**
     * Used to check if the Entity is busy
     * @return true if the Entity is busy
     */
    @Override
    public boolean isBusy() {
        return busy.get();
    }
    
    /**
     * Set the Entity's status to busy
     */
    @Override
    public void setBusy() {
        busy.set(true);
    }
    
    /**
     * Set the Entity's status to not busy.
     */
    @Override
    public void setNotBusy() {
        busy.set(false);
    }
    
    /**
     * @return the Entity's location as a String
     */
    public String getLocation() {
        return location.get();
    }
    
    /**
     * Set the Entity's location to newLocation
     * @param newLocation 
     */
    public void setLocation(String newLocation) {
        location.set(newLocation);
    }
    
    /**
     * @return the Entity's "location" SimpleStringProperty
     */
    public SimpleStringProperty getLocationProp() {
        return location;
    }
    
    /**
     * @return the traits as a List
     */
    @Override
    public List<Trait> getTraits() {
        return traits;
    }
    
    /**
     * @param newTraits the traits to set
     */
    @Override
    public void setTraits(List<Trait> newTraits) {
        this.traits = new ArrayList<Trait>(newTraits);
    }

    /**
     * @return the traitDisplayPriority
     */
    @Override
    public Trait.trait_type[] getTraitDisplayPriority() {
        return traitDisplayPriority;
    }

    /**
     * @param traitDisplayPriority the traitDisplayPriority to set
     */
    @Override
    public void setTraitDisplayPriority(Trait.trait_type[] traitDisplayPriority) {
        this.traitDisplayPriority = traitDisplayPriority;
    }
    
    /**
     * Adds the given trait to the entity. If the Trait List already contains a Trait with the same name, 
     * it adds their values. If it does not have one, it does nothing. 
     * Negative values are allowed.
     * 
     * @param name the name of the Trait
     * @param value the value of the Trait
     */
    @Override
    public void addTraitValue(String name, int value) {
        
        for(Trait res : traits) {
            if(res.getName().equals(name)) {
                res.addToValue(value);
                return; //return if we found the trait in the trait list
            }
        }
    }
    
    @Override
    public boolean hasTrait(String name) {
        for(Trait t : traits) {
            if(t.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int getTraitValue(String name) {
        for(Trait t : traits) {
            if(t.getName().equalsIgnoreCase(name)) {
                return t.getValue();
            }
        }
        return 0;
    }
    
    /**
     * Adds the given trait to the entity. If the Trait List already contains a Trait with the 
     * same name, it adds their values. If it does not have one, it adds the Trait to the pool.
     * Negative values are allowed.
     * 
     * @param t the Trait
     */
    @Override
    public void addTrait(Trait t) {
        
        for(Trait res : traits) {
            if(res.getName().equals(t.getName())) {
                res.addToValue(t.getValue());
                return; //return if we found the resource in the pool
            }
        }
        
        traits.add(new Trait(t));
    }

    @Override
    public String getBuilderBadge() {
        return builderBadge;
    }
    
    @Override
    public void setBuilderBadge(String newBadge) {
        builderBadge = newBadge;
    }
    
    /**
     * @return the entity's idle text if it is not busy, and returns it's current task's flavor if it is busy
     */
    public String status() {
        if(isBusy()) {
            return currentTask.getFlavor();
        }
        else {
            return idleText.get();
        }
    }
}
