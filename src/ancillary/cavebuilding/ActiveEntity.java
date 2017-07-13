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
    private int currentTask;
    
    public ActiveEntity() {
        super();
        tasks = new ArrayList<Task>();
        taskTimer = 0;
        currentTask = -1;
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
        currentTask = -1;
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
        taskTimer = tasks.get(currentTask).getDurationProp().get();
    }
    
    public int getCurrentTask() {
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
        
        currentTask = taskNumber;
        super.setBusy();
    }
    
    @Override
    public void setName(String name) {
        super.name = name;
    }

    /**
     * Mostly just tests right now
     * The taskTimer is decremented. If it reaches zero or less, it is set to 0, 
     * currentTask is set to -1, and the Entity is marked not busy.
     * @param args 
     */
    @Override
    public void update(String[] args) {
        if(currentTask == -1) {
            return;
        }
        
        if(taskTimer > 0) {
            System.out.println("UPADTE ID: " + super.getID() + " Name: " + 
                super.getName() + " Active?: " +super.isActive() + 
                " Location: " + getLocation() + " Current Task: " + 
                tasks.get(currentTask).getNameProp() + " Remaining Duration: " + 
                taskTimer);
            System.out.println();    
            
            taskTimer--;
            return;
        }
        
        
        System.out.println("ID: " + super.getID() + " Name: " + super.getName() + 
                " Active?: " +super.isActive() + " Location: " + getLocation() +
                " Task Complete: " + tasks.get(currentTask).getNameProp() + 
                " Task Duration: " + tasks.get(currentTask).getDurationProp());
        System.out.println();
        
        taskTimer = 0;
        currentTask = -1;      
        super.setNotBusy();
    }

    @Override
    public String getLocation() {
        return super.location;
    }

    @Override
    public void setLocation(String location) {
        super.location = location;
    }
    
}
