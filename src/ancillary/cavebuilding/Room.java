/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author MM
 */

public class Room {

    /* room sizes */
    public static enum roomSize{SMALL, LARGE, HUGE}
    
    /* room types */
    public static enum roomType{WALL, ENTRANCE, CORRIDOR, SHAFT, HOARD, NEST, STORAGE, QUARTERS, EXTERIOR}
    
    // doors are numbered 0,1,etc, clockwise from top
    // current maximum number of doors is 4    
    public final int MAXDOORS = 4;
    //contents
    //occupants
    private SimpleStringProperty roomName;
    private String roomID;
    private roomSize size;
    private roomType type;
    private SimpleStringProperty description;
    private Room[] neighbors;
    private ArrayList<Trait> traits;
    private int roomNumber = -1;
    private int xPos = -1;
    private int yPos = -1;
    
    public Room() {
        roomName = new SimpleStringProperty("Wall");
        roomID = "placeholder";
        size = roomSize.SMALL;
        type = roomType.WALL;
        description = new SimpleStringProperty("Unworked");
        neighbors = new Room[MAXDOORS];
        traits = new ArrayList();
    }
    
    public Room(Room r) {
        roomName = new SimpleStringProperty(r.getRoomName());
        roomID = r.getRoomID();
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
    }
    
    public Room(String initName, String initID, roomSize initSize, roomType initType, String desc, Room[] neighborhood, ArrayList<Trait> initTraits) {
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
    }

    /**
     * @return the roomName property
     */
    public SimpleStringProperty getRoomNameProp() {
        return roomName;
    }
    
    /**
     * @return the roomName
     */
    public String getRoomName() {
        return roomName.get();
    }

    /**
     * @param newName the roomName to set
     */
    public void setRoomName(String newName) {
        roomName.set(newName);
    }

    /**
     * @return the roomID
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * @param newID the id to set
     */
    public void setSize(String newID) {
        roomID = newID;
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
     * @param description the description to set
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

    /**
     * @return the traits
     */
    public ArrayList<Trait> getTraits() {
        return traits;
    }

    /**
     * @param traits the traits to set
     */
    public void setTraits(ArrayList<Trait> traits) {
        this.traits = traits;
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
    
    private void setUpTraits(ArrayList<Trait> initTraits) {
        traits = new ArrayList();
        
        if(initTraits == null) {
            return;
        }
        
        for(Trait t : initTraits) {
            traits.add(new Trait(t));
        }
    }
}
