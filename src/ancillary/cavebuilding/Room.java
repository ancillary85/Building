/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.awt.Point;

/**
 *
 * @author MM
 */

public class Room {
    /* room sizes */
    public static final int SMALL = 0; // 1x1
    public static final int LARGE = 1; // 2x2
    public static final int  HALL = 2; // 2x1
    public static final int SHAFT = 3; // 1x2
    
    /* room types */
    public static final int ENTRANCE = 0;
    public static final int CORRIDOR = 1;
    public static final int HOARD = 2;
    public static final int NEST = 3;
    public static final int STORAGE = 4;
    public static final int QUAARTERS = 5;
    
    public final int MAXDOORS = 8;
    // doors are numbered 0,1,etc, clockwise from upper left corner
    // current maximum number of doors is 8    
    private boolean[] doors;
    private int roomType;
    private String roomName;
    private int roomNum;
    private static int roomTotal = 0;
    private int roomSize;
    private Point location; // location of upper left corner
    private int[] contents;
    private int[] occupants;
    
    public Room() {
        doors = new boolean[MAXDOORS];
        roomType = -1;
        roomName = "blank";
        roomNum = roomTotal;
        roomTotal++;
        roomSize = -1;
        location = new Point(-1, -1);
        contents = new int[0];
        occupants = new int[0];
    }
    
    public Room(boolean[] doors, int roomType, String roomName, int roomSize, 
            Point location, int[] contents, int[] occupants) {
        this.doors = doors;
        this.roomType = roomType;
        this.roomName = roomName;
        roomNum = roomTotal;
        roomTotal++;
        this.roomSize = roomSize;
        this.location = location;
        this.contents = contents;
        this.occupants = occupants;
    }
        
    public boolean[] getDoors(){
        return doors;
    }
    
    public int getRoomType() {
        return roomType;
    }
    
    public String getRoomName() {
        return roomName;
    }
    
    public static int getRoomTotal() {
        return roomTotal;
    }
    
    public  int getRoomNum() {
        return roomNum;
    }
    
    public int getRoomSize(){
        return roomSize;
    }
    
    public Point getLocation() {
        return location;
    }
    
    public int[] getContents() {
        return contents;
    }
    
    public int[] getOccupants() {
        return occupants;
    }
    
    public void setDoors(boolean[] newDoors) {
        if(newDoors.length > MAXDOORS){
         //too many doors!
            return;
        }            
        
        doors = newDoors;
    }
    
    public void setRoomType(int newType) {
        roomType = newType;
    }
    
    public void setRoomName(String newName) {
        roomName = newName;
    }
    
    public void setRoomSize(int newSize) {
        roomSize = newSize;
    }
    
    public void setLocation(Point newLocation) {
        location = new Point(newLocation);
    }
    
    public void setContents(int[] newContents) {
        contents = newContents;
    }
    
    public void setOccupants(int[] newOccupants) {
        occupants = newOccupants;
    }
}
