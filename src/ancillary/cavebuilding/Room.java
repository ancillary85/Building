/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author MM
 */

public class Room implements Active{

    /* room sizes */
    public static enum roomSize{SMALL, LARGE, HUGE}
    
    /* room types */
    public static enum roomType{WALL, ENTRANCE, CORRIDOR, SHAFT, HOARD, NEST, STORAGE, QUARTERS, EXTERIOR}
    
    // doors are numbered 0,1,etc, clockwise from top
    // current maximum number of doors is 4    
    public final int MAXDOORS = 4;
    //contents
    //occupants
    protected SimpleStringProperty roomName;
    protected String roomID;
    protected roomSize size;
    protected roomType type;
    protected SimpleStringProperty description;
    protected Room[] neighbors;
    protected ArrayList<Trait> traits;
    protected Trait.trait_type[] traitDisplayPriority = {Trait.trait_type.ATTRIBUTE, Trait.trait_type.PRODUCTION, Trait.trait_type.COMBAT, Trait.trait_type.FLAVOR};
    protected ArrayList<Task> tasks;
    protected SimpleIntegerProperty taskTimer = new SimpleIntegerProperty(-1);
    protected Task currentTask = new Task();
    protected SimpleBooleanProperty taskCompleted = new SimpleBooleanProperty(false);
    protected SimpleBooleanProperty busy = new SimpleBooleanProperty(false);
    protected int roomNumber = -1;
    protected int xPos = -1;
    protected int yPos = -1;
    protected SimpleBooleanProperty reachable = new SimpleBooleanProperty(true);
    protected String builderBadge = null;
    
    public Room() {
        roomName = new SimpleStringProperty("Wall");
        roomID = "placeholder";
        size = roomSize.SMALL;
        type = roomType.WALL;
        description = new SimpleStringProperty("Unworked");
        neighbors = new Room[MAXDOORS];
        traits = new ArrayList();
        tasks = new ArrayList();
    }
    
    public Room(Room r) {
        roomName = new SimpleStringProperty(r.getName());
        roomID = r.getId();
        size = r.getSize();
        type = r.getType();
        description = new SimpleStringProperty(r.getDescription());
        if(r.getNeighbors() == null || r.getNeighbors().length > MAXDOORS) {
            neighbors = new Room[MAXDOORS];
        }
        else {
            neighbors = r.getNeighbors();
        }
        setUpTraits(r.getTraits());
        setUpTasks(r.getTasks());
    }
    
    public Room(String initName, String initID, roomSize initSize, roomType initType, String desc, Room[] neighborhood, ArrayList<Trait> initTraits, ArrayList<Task> initTasks) {
        roomName = new SimpleStringProperty(initName);
        roomID = initID;
        size = initSize;
        type = initType;
        description = new SimpleStringProperty(desc);
        if(neighborhood == null || neighborhood.length > MAXDOORS) {
            neighbors = new Room[MAXDOORS];
        }
        else {
            neighbors = neighborhood;
        }
        setUpTraits(initTraits);
        setUpTasks(initTasks);
    }

    /**
     * @return the Room's name
     */
    @Override
    public String getName() {
        return roomName.get();
    }
    
    @Override
    public SimpleStringProperty getNameProp() {
        return roomName;
    }
    
    @Override
    public void setName(String newName) {
        roomName.set(newName);
    }
    
    
    @Override
    public String getId() {
        return roomID;
    }

    @Override
    public void setId(String id) {
        roomID = id;
    }
    
    /**
     * @return the size
     */
    public roomSize getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(roomSize size) {
        this.size = size;
    }

    /**
     * @return the type
     */
    public roomType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(roomType type) {
        this.type = type;
    }

    /**
     * @return the description property
     */
    public SimpleStringProperty getDescriptionProp() {
        return description;
    }

    /**
     * @return the description 
     */
    public String getDescription() {
        return description.get();
    }
    
    /**
     * @param newDesc the description to set
     */
    public void setDescription(String newDesc) {
        description.set(newDesc);
    }

    /**
     * @return the neighbors
     */
    public Room[] getNeighbors() {
        return neighbors;
    }

    /**
     * @param neighbors the neighbors to set
     */
    public void setNeighbors(Room[] neighbors) {
        this.neighbors = neighbors;
    }

    public Room getNorthNeighbor() {
        return neighbors[0];
    }
    
    public void setNorthNeighbor(Room r) {
        neighbors[0] = r;
    }
    
    public Room getEastNeighbor() {
        return neighbors[1];
    }
    
    public void setEastNeighbor(Room r) {
        neighbors[1] = r;
    }
    
    public Room getSouthNeighbor() {
        return neighbors[2];
    }
    
    public void setSouthNeighbor(Room r) {
        neighbors[2] = r;
    }
    
    public Room getWestNeighbor() {
        return neighbors[3];
    }
    
    public void setWestNeighbor(Room r) {
        neighbors[3] = r;
    }
    
    /**
     * @return the traits
     */
    @Override
    public ArrayList<Trait> getTraits() {
        return traits;
    }

    /**
     * @param traits the traits to set
     */
    @Override
    public void setTraits(List<Trait> traits) {
        this.traits = new ArrayList(traits);
    }

    @Override
    public Trait.trait_type[] getTraitDisplayPriority() {
        return traitDisplayPriority;
    }

    @Override
    public void setTraitDisplayPriority(Trait.trait_type[] traitDisplayPriority) {
        this.traitDisplayPriority = traitDisplayPriority;
    }

    /**
     * Adds the given trait to the Room. If the Trait List already contains a Trait with the same name, 
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
     * Adds the given trait to the Room. If the Trait List already contains a Trait with the 
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
    
    /**
     * @return the Room's "busy" SimpleBooleanProperty
     */
    @Override
    public SimpleBooleanProperty getBusyProp() {
        return busy;
    }
    
    /**
     * Used to check if the Room is busy
     * @return true if the Room is busy
     */
    @Override
    public boolean isBusy() {
        return busy.get();
    }
    
    /**
     * Set the Room's status to busy
     */
    @Override
    public void setBusy() {
        busy.set(true);
    }
    
    /**
     * Set the Room's status to not busy.
     */
    @Override
    public void setNotBusy() {
        busy.set(false);
    }
    
    /**
     * @return the tasks
     */
    @Override
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * @param newTasks the tasks to set
     */
    @Override
    public void setTasks(List<Task> newTasks) {
        this.tasks = new ArrayList(newTasks);
    }

    @Override
    public void addTask(Task newTask) {
        if(newTask != null) {
            tasks.add(newTask);
        }
    }

    @Override
    public void removeTask(Task oldTask) {
        tasks.remove(oldTask);
        if(currentTask.equals(oldTask)) {
            currentTask.setNoTask();
            this.setNotBusy();
        }       
    }
    
    /**
     * @return the taskTimer
     */
    @Override
    public int getTaskTimer() {
        return taskTimer.get();
    }

    /**
     * @return the taskTimer property
     */
    @Override
    public SimpleIntegerProperty getTaskTimerProp() {
        return taskTimer;
    }
    
    /**
     * @param newTimer the taskTimer to set
     */
    @Override
    public void setTaskTimer(int newTimer) {
        taskTimer.set(newTimer);
    }

    /**
     * @return the currentTask
     */
    @Override
    public Task getCurrentTask() {
        return currentTask;
    }

    /**
     * @param currentTask the currentTask to set
     */
    @Override
    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }
  
    /**
     * @return taskCompleted as a boolean 
     */
    @Override
    public boolean getTaskCompleted() {
        return taskCompleted.get();
    }
    
    /**
     * @return the Room's "taskCompleted" property
     */
    @Override
    public SimpleBooleanProperty getTaskCompletedProp() {
        return taskCompleted;
    }

    /**
     * Sets the Room's taskCompleted property
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
            }
        }       
    }
    
    /**
     * Sets the Room's taskTimer to zero, leaves currentTask alone, taskCompleted to true, and marks it not busy
     */
    @Override
    public void completeTask() {
        taskTimer.set(0);
        this.setNotBusy();
        taskCompleted.set(true);
    }
    
    /**
     * Sets the Room's taskTimer to zero, currentTask to "no task," taskCompleted to false, and marks it not busy
     */
    @Override
    public void clearTask() {
        taskTimer.set(0);
        currentTask.setNoTask();
        this.setNotBusy();
        taskCompleted.set(false);
    }
    
    /**
     * Sets the taskTimer by looking up the duration of the current Task
     */
    @Override
    public void setTaskTimerFromCurrentTask() {
        taskTimer.set(currentTask.getDuration());
    }
    
    /**
     * Sets the Room's current Task, sets its taskTimer from the current Task, marks it as busy, 
     * and taskCompleted to false. If the provided Task is null or is not in the Room's List of Tasks, no changes are made.
     * 
     * The Room's taskTimer IS set by this method. 
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
    
    @Override
    public void matchActive(Active toMatch) {
        this.busy.set(toMatch.isBusy());
        this.currentTask.setToNewTask(toMatch.getCurrentTask());
        this.taskCompleted.set(toMatch.getTaskCompleted());
        this.taskTimer.set(toMatch.getTaskTimer());
        this.tasks = new ArrayList(toMatch.getTasks());
        this.traits = new ArrayList(toMatch.getTraits());
    }
    
    
    public void matchRoom(Room r) {
        this.setName(r.getName());
        this.setId(r.getId());
        this.setRoomNumber(r.getRoomNumber());
        this.setType(r.getType());
        this.setSize(r.getSize());
        this.setDescription(r.getDescription());
        this.setNeighbors(r.getNeighbors());
        this.setxPos(r.getxPos());
        this.setyPos(r.getyPos());
        this.setReachable(r.isReachable());
        this.matchActive(r);
    }
    
    /**
     * Doesn't copy over neighbors, room number, reachable, or positions
     * @param r 
     */
    public void matchRoomShallow(Room r) {
        this.setName(r.getName());
        this.setId(r.getId());
        this.setType(r.getType());
        this.setSize(r.getSize());
        this.setDescription(r.getDescription());
        this.matchActive(r);
    }
    
    /**
     * @return the roomNumber
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber the roomNumber to set
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    /**
     * @return the xPos
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * @param xPos the xPos to set
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * @return the yPos
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * @param yPos the yPos to set
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    
    /**
     * @return the reachable status
     */
    public boolean isReachable() {
        return reachable.get();
    }

    /**
     * @param newReach the reachable status to set
     */
    public void setReachable(boolean newReach) {
        reachable.set(newReach);
    }
    
    /**
     * @return the reachable property
     */
    public SimpleBooleanProperty getReachableProp() {
        return reachable;
    }
    
    private void setUpTraits(ArrayList<Trait> initTraits) {
        traits = new ArrayList();
        
        if(initTraits == null) {
            return;
        }
        
        for(Trait t : initTraits) {
            traits.add(new Trait(t));
        }
    }
    
    private void setUpTasks(ArrayList<Task> initTasks) {
        tasks = new ArrayList();
        
        if(initTasks == null) {
            return;
        }
        
        for(Task t : initTasks) {
            tasks.add(t);
        }
    }
    
    @Override
    public boolean idMatch(String idFragment) {
        for(String piece : roomID.split(",")) {
            if(piece.equalsIgnoreCase(idFragment)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void setLinked(Task t, Active e) {
        currentTask.setLinkTask(t, e);
        setNotBusy();
        setBusy();
        taskCompleted.set(false);
        setTaskTimerFromCurrentTask();
    }

    @Override
    public String getBuilderBadge() {
        return builderBadge;
    }

    @Override
    public void setBuilderBadge(String newBadge) {
        builderBadge = newBadge;
    }
}
