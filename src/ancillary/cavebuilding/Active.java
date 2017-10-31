/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author MLaptop
 */
public interface Active extends Entity {
    
     /**
     * Sets the Active's taskTimer to zero, leaves currentTask alone, taskCompleted to true, and marks it not busy
     */
    public void completeTask(); 
    
    /**
     * Sets the Active's taskTimer to zero, currentTask to "no task," taskCompleted to false, and marks it not busy
     */
    public void clearTask();
    
    /**
     * Sets the taskTimer by looking up the duration of the current Task
     */
    public void setTaskTimerFromCurrentTask();
    
    /**
     * Sets the Active's current Task, sets its taskTimer from the current Task, marks it as busy, 
     * and taskCompleted to false. If the provided Task is null or is not in the Active's List of Tasks, no changes are made.
     * 
     * The Active's taskTimer IS set by this method. 
     * @param t the Task to use
     */
    public void setTaskAndTimer(Task t);
    
    /**
     * @return the Active's "busy" SimpleBooleanProperty
     */
    public SimpleBooleanProperty getBusyProp();
    
    /**
     * Used to check if the Active is busy
     * @return true if the Active is busy
     */
    public boolean isBusy();
    
    /**
     * Set the Active's status to busy
     */
    public void setBusy();
    
    /**
     * Set the Active's status to not busy.
     */
    public void setNotBusy();
    
    /**
     * @return the tasks
     */
    public List<Task> getTasks();

    /**
     * @param newTasks the tasks to set
     */
    public void setTasks(List<Task> newTasks);

    /**
     * If newTask == null, it does nothing.
     * @param newTask 
     */
    public void addTask(Task newTask);
    
    /**
     * Removes a Task. If that Task is the current Task, then the 
     * current Task is set to "no task," and the Entity is marked not busy.
     * @param oldTask 
     */
    public void removeTask(Task oldTask);
    
    /**
     * @return the taskTimer
     */
    public int getTaskTimer();

    /**
     * @return the taskTimer property
     */
    public SimpleIntegerProperty getTaskTimerProp();
    
    /**
     * @param newTimer the taskTimer to set
     */
    public void setTaskTimer(int newTimer);

    /**
     * @return the currentTask
     */
    public Task getCurrentTask();

    /**
     * Sets the Entity's current Task, marks it as busy, and taskCompleted to false.
     * If the provided Task is null or is not in the Entity's List of Tasks, no changes are made.
     * 
     * The Entity's taskTimer is NOT set by this method. 
     * Calling setTaskTimerFromCurrentTask() afterwards is recommended.
     * Alternatively, use setTaskAndTimer()
     * @param newTask the Task to use
     */
    public void setCurrentTask(Task newTask);
  
    /**
     * @return taskCompleted as a boolean 
     */
    public boolean getTaskCompleted();
    
    /**
     * @return the Active's "taskCompleted" property
     */
    public SimpleBooleanProperty getTaskCompletedProp();

    /**
     * Sets the Active's taskCompleted property
     * @param taskStatus 
     */
    public void setTaskCompleted(boolean taskStatus);
    
    /**
     * Presently does nothing!
     */
    public void idle();
    
    /**
     *  Calls idle() and clearTask() if the entity is not busy, or it has a taskTimer < 0. Otherwise, the 
    * taskTimer is decremented. If it reaches zero, completeTask() is called.
     * @param args 
     */
    public void activeUpdate(String[] args);
    
    /**
     * Makes the Active this is called on match the Active-specific fields of the toMatch Active. It should 
     * change Tasks, Traits, etc.
     * @param toMatch 
     */
    public void matchActive(Active toMatch);
    
    /**
     * Links this entity with another for a task. It's task timer is set to match the task's duration.
     * @param t
     * @param e 
     */
    public void setLinked(Task t, Active e);
}
