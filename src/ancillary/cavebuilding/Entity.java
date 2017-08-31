/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Mike
 */
public class Entity {

 /**
     * The Entity's id
     */
    protected String id;
    
    /**
     * The Entity's name, to be displayed to the player
     */
    protected SimpleStringProperty name;
    
    /**
     * Whether the Entity can be interacted with
     */
    protected SimpleBooleanProperty active;
    
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
    protected SimpleStringProperty traits;
    
    public Entity() {
        this.id = "placeholder";
        this.name = new SimpleStringProperty("blank");
        this.active = new SimpleBooleanProperty(false);
        this.busy = new SimpleBooleanProperty(false);
        this.location = new SimpleStringProperty("nowhere");
        this.traits = new SimpleStringProperty("none");
    }
    
    public Entity(String id, String name, boolean active, String location, String traits) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.active = new SimpleBooleanProperty(active);
        this.busy = new SimpleBooleanProperty(false);
        this.location = new SimpleStringProperty(location);
        this.traits = new SimpleStringProperty(traits);
    }
    
    public Entity(Entity e) {
        if(e == null) {
            this.id = "placeholder";
            this.name = new SimpleStringProperty("blank");
            this.active = new SimpleBooleanProperty(false);
            this.busy = new SimpleBooleanProperty(false);
            this.location = new SimpleStringProperty("nowhere");
            this.traits = new SimpleStringProperty("none");
        }
        else {
            this.id = e.getID();
            this.name = new SimpleStringProperty(e.getName());
            this.active = new SimpleBooleanProperty(e.isActive());
            this.busy = new SimpleBooleanProperty(false);
            this.location = new SimpleStringProperty(e.getLocation());
            this.traits = new SimpleStringProperty(e.getTraits());
        }
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
        return name.get();
    }
    
    /**
     * @return the Entity's name SimpleStringProperty
     */
    public SimpleStringProperty getNameProp() {
        return name;
    }
    
    public void setName(String newName) {
        name.set(newName);
    }
    
    /**
     * @return the Entity's "active" SimpleBooleanProperty
     */
    public SimpleBooleanProperty getActiveProp() {
        return active;
    }
    
    /**
     * Used to check if the Entity can act
     * @return true if the Entity is an active one
     */
    public boolean isActive() {
        return active.get();
    }
    
    /**
     * @return the Entity's "busy" SimpleBooleanProperty
     */
    public SimpleBooleanProperty getBusyProp() {
        return busy;
    }
    
    /**
     * Used to check if the Entity is busy
     * @return true if the Entity is busy
     */
    public boolean isBusy() {
        return busy.get();
    }
    
    /**
     * Set the Entity's status to busy
     */
    public void setBusy() {
        busy.set(true);
    }
    
    /**
     * Set the Entity's status to not busy.
     */
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
     * @return the traits as a String
     */
    public String getTraits() {
        return traits.get();
    }

    /**
     * @return the Entity's "trait" SimpleStringProperty
     */
    public SimpleStringProperty getTraitsProp() {
        return traits;
    }
    
    /**
     * @param traits the traits to set
     */
    public void setTraits(SimpleStringProperty traits) {
        this.traits = traits;
    }    
}