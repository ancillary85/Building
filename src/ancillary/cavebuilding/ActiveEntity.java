/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Mike
 */

public class ActiveEntity extends Entity {

    private ArrayList<Task> tasks;
    private int taskTimer;
    private Task currentTask;
    private SimpleBooleanProperty taskCompleted;
    
    public ActiveEntity() {
        super();
        this.tasks = new ArrayList<Task>();
        this.taskTimer = 0;
        this.currentTask = new Task();
        this.taskCompleted = new SimpleBooleanProperty(false);
    }
    
    
    /**
     * Please don't give an empty list of tasks
     * @param id
     * @param name
     * @param location
     * @param tasks 
     */
    public ActiveEntity(String id, String name, String location, List<Task> tasks) {
        super(id, name, true, location);
        this.tasks = new ArrayList<Task>(tasks);
        this.taskTimer = 0;
        this.currentTask = new Task();
        this.taskCompleted = new SimpleBooleanProperty(false);
    }
    
    public ActiveEntity(Entity e) {
        super(e.id, e.name.get(), true, e.location.get());
        this.tasks = new ArrayList<Task>(tasks);
        this.taskTimer = 0;
        this.currentTask = new Task();
        this.taskCompleted = new SimpleBooleanProperty(false);
    }
    
    /**
     * Return an unmodifiable List of the Entity's tasks.
     * Attempts to modify it will get an UnsupportedOperationException
     * @return an unmodifiable List
     */
    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }
    
    public void setTasks(List<Task> newTasks) {
        tasks = new ArrayList<Task>(newTasks);
    }
    
    public void addTask(Task newTask){
        tasks.add(newTask);
    }
    
    public void addTasks(List<Task> newTasks) {
        tasks.addAll(tasks);
    }
    
    /**
     * Removes a Task. If that Task is the current Task, then the 
     * current Task is set to "no task," and the Entity is marked not busy.
     * @param oldTask 
     */
    public void removeTask(Task oldTask) {
        tasks.remove(oldTask);
        if(currentTask.equals(oldTask)) {
            currentTask.setNoTask();
        }
        
        super.setNotBusy();
    }
    
    public int getTaskTimer() {
        return taskTimer;
    }
    
    public void setTaskTimer(int newTime) {
        taskTimer = newTime;
    }
    
    /**
     * Sets the taskTimer by looking up the duration of the current Task
     */
    public void setTaskTimerFromCurrentTask() {
        taskTimer = currentTask.getDuration();
    }
    
    public Task getCurrentTask() {
        return currentTask;
    }
    
    /**
     * Sets the Entity's current Task, marks it as busy, and taskCompleted to false.
     * The parameter is used as the index of the Entity's List of Tasks.
     * If the given index is outside of the range, no changes are made.
     * 
     * The Entity's taskTimer is NOT set by this method. 
     * Calling setTaskTimerFromCurrentTask() afterwards is recommended.
     * @param taskNumber the index of the Entity's Task List
     */
    public void setCurrentTask(int taskNumber) {
        if(taskNumber >= tasks.size() || taskNumber < 0) {return;}
        
        currentTask = tasks.get(taskNumber);
        super.setBusy();
        taskCompleted.set(false);
    }
    
    /**
     * Sets the Entity's current Task, marks it as busy, and taskCompleted to false.
     * If the provided Task is not in the Entity's List of Tasks, no changes are made.
     * 
     * The Entity's taskTimer is NOT set by this method. 
     * Calling setTaskTimerFromCurrentTask() afterwards is recommended.
     * @param newTask the Task to use
     */
    public void setCurrentTask(Task newTask) {
        if(!tasks.contains(newTask)) {return;}
        
        currentTask = newTask;
        super.setBusy();
        taskCompleted.set(false);
    }
    
    /**
     * Sets the Entity's current Task, marks it as busy, and taskCompleted to false.
     * If the provided Task is not in the Entity's List of Tasks, it is added to the List beforehand.
     * 
     * The Entity's taskTimer is NOT set by this method. 
     * Calling setTaskTimerFromCurrentTask() afterwards is recommended.
     * @param newTask the Task to use
     */
    public void addAndSetCurrentTask(Task newTask) {
        if(!tasks.contains(newTask)) {
            tasks.add(newTask);
        }
        
        currentTask = newTask;
        super.setBusy();
        taskCompleted.set(false);
    }
    
    /**
     * @return taskCompleted as a boolean 
     */
    public boolean getTaskCompleted() {
        return taskCompleted.get();
    }
    
    /**
     * @return the Entity's "taskCompleted" property
     */
    public SimpleBooleanProperty getTaskCompletedProp() {
        return taskCompleted;
    }

    /**
     * Sets the Entity's taskCompleted property
     * @param taskStatus 
     */
    public void setTaskCompleted(boolean taskStatus) {
        taskCompleted.set(taskStatus);
    }
    
    /**
     * Presently does nothing!
     */
    public void idle() {
        
    }
    
    /**
     * Mostly just tests right now
     * The taskTimer is decremented. If it reaches zero or less, it is set to 0, 
     * currentTask is set to NoTask, and the Entity is marked not busy.
     * @param args 
     */
    @Override
    public void entityUpdate(String[] args) {
        if(currentTask.isNoTask()) {
            idle();
        }
        
        if(taskTimer > 0) {            
            taskTimer--;
            return;
        }       
        
        completeTask();
    }
    
    /**
     * Sets the Entity's taskTimer to zero, currentTask to "no task," taskCompleted to true, and marks it not busy
     */
    public void completeTask() {
        taskTimer = 0;
        currentTask.setNoTask();      
        super.setNotBusy();
        taskCompleted.set(true);
    }
    
    /**
     * Sets the Entity's taskTimer to zero, currentTask to "no task," taskCompleted to false, and marks it not busy
     */
    public void cancelTask() {
        taskTimer = 0;
        currentTask.setNoTask();
        super.setNotBusy();
        taskCompleted.set(false);
    }
    
}
