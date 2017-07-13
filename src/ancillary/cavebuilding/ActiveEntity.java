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

/**
 *
 * @author Mike
 */

public class ActiveEntity extends Entity {

    private ArrayList<Task> tasks;
    private int taskTimer;
    private Task currentTask;
    
    public ActiveEntity() {
        super();
        tasks = new ArrayList<Task>();
        taskTimer = 0;
        currentTask = new Task();
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
        taskTimer = 0;
        currentTask = new Task();
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
     * Sets the Entity's current Task and marks it as busy.
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
    }
    
    /**
     * Sets the Entity's current Task and marks it as busy.
     * If the provided Task is not in the Entity's List of Tasks,
     * no changes are made.
     * The Entity's taskTimer is NOT set by this method. 
     * Calling setTaskTimerFromCurrentTask() afterwards is recommended.
     * @param newTask the Task to use
     */
    public void setCurrentTask(Task newTask) {
        if(!tasks.contains(newTask)) {
            return;
        }
        
        currentTask = newTask;
        super.setBusy();
    }
    
    /**
     * Sets the Entity's current Task and marks it as busy.
     * If the provided Task is not in the Entity's List of Tasks,
     * it is added to the List beforehand.
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
    }
    

    /**
     * Mostly just tests right now
     * The taskTimer is decremented. If it reaches zero or less, it is set to 0, 
     * currentTask is set to NoTask, and the Entity is marked not busy.
     * @param args 
     */
    @Override
    public void update(String[] args) {
        if(currentTask.isNoTask()) {
            return;
        }
        
        if(taskTimer > 0) {
            System.out.println("UPADTE ID: " + super.getID() + " Name: " + 
                super.getName() + " Active?: " +super.isActive() + 
                " Location: " + getLocation() + " Current Task: " + 
                currentTask.getName() + " Remaining Duration: " + 
                taskTimer);
            System.out.println();    
            
            taskTimer--;
            return;
        }
        
        
        System.out.println("ID: " + super.getID() + " Name: " + super.getName() + 
                " Active?: " +super.isActive() + " Location: " + getLocation() +
                " Task Complete: " + currentTask.getName() + 
                " Task Duration: " + currentTask.getDuration());
        System.out.println();
        
        taskTimer = 0;
        currentTask.setNoTask();      
        super.setNotBusy();
    }
    
}
