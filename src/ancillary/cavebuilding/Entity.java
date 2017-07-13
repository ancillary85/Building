/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

/**
 *
 * @author Mike
 */
public abstract class Entity {
    
    /**
     * The Entity's id
     */
    protected String id;
    
    /**
     * The Entity's name, to be displayed to the player
     */
    protected String name;
    
    /**
     * Whether the Entity can act
     */
    protected final boolean active;
    
    /**
     * Whether the Entity is busy
     */
    protected boolean busy;
    
    /**
     * The Entity's location
     */
    protected String location;
    
    public Entity() {
        id = "placeholder";
        name = "blank";
        active = false;
        busy = false;
        location = "nowhere";
    }
    
    public Entity(String id, String name, boolean active, String location) {
        this.id = id;
        this.name = name;
        this.active = active;
        busy = false;
        this.location = location;
    }
    
    /**
     * @return the Entity's ID as a String
     */
    public String getID() {
        return id;
    }
            
    /**
     * @return the Entity's name as a String
     */
    public String getName() {
        return name;
    }
    
    /**
     * Used to check if the Entity can act
     * @return true if the Entity is an active one
     */
    public boolean isActive() {
        return active;
    }
    
    /**
     * Used to check if the Entity is busy
     * @return true if the Entity is busy
     */
    public boolean isBusy() {
        return busy;
    }
    
    /**
     * Set the Entity's status to busy
     */
    public void setBusy() {
        busy = true;
    }
    
    /**
     * Set the Entity's status to not busy.
     */
    public void setNotBusy() {
        busy = false;
    }
    
    public abstract void setName(String name);
    public abstract void update(String[] args);
    public abstract String getLocation();
    public abstract void setLocation(String arg);
    
}
