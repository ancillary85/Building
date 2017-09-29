/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

/**
 *
 * @author MLaptop
 */
public class AntRoomBuilder {
    //public Room(String initName, String initID, roomSize initSize, roomType initType, String desc, Room[] neighborhood, ArrayList<Trait> initTraits)
    public static Room surface() {
        Room outside = new Room("Surface", "surface", Room.roomSize.SMALL, Room.roomType.EXTERIOR, "The outdoors", null, null);
        return outside;
    }
    
    public static Room antHill() {
        Room hill = new Room("Ant Hill", "hill", Room.roomSize.SMALL, Room.roomType.ENTRANCE, "A small ant hill", null, null);
        return hill;
    }
    
    public static Room antHillLarge() {
        Room hill = new Room("Ant Hill", "hill", Room.roomSize.LARGE, Room.roomType.ENTRANCE, "A large ant hill", null, null);
        return hill;
    }
    
    public static Room hallway() {
        Room corridor = new Room("Hallway", "hallway", Room.roomSize.SMALL, Room.roomType.CORRIDOR, "A hallway between rooms", null, null);
        return corridor;
    }
    
    public static Room shaft() {
        Room stairs = new Room("Shaft", "shaft", Room.roomSize.SMALL, Room.roomType.SHAFT, "A vertical passage", null, null);
        return stairs;
    }
    
    public static Room queenChamber() {
        Room queenRoom = new Room("Queen's Chamber", "throne", Room.roomSize.SMALL, Room.roomType.QUARTERS, "The queen lives here", null, null);
        return queenRoom;
    }
    
    public static Room queenChamberLarge() {
        Room queenRoom = new Room("Queen's Chamber", "throne", Room.roomSize.LARGE, Room.roomType.QUARTERS, "The queen lives here", null, null);
        return queenRoom;
    }
    
    public static Room eggNursery() {
        Room eggRoom = new Room("Egg Nursery", "egg nursery", Room.roomSize.SMALL, Room.roomType.NEST, "Room for keeping eggs", null, null);
        return eggRoom;
    }
    
    public static Room eggNurseryLarge() {
        Room eggRoom = new Room("Egg Nursery", "egg nursery", Room.roomSize.LARGE, Room.roomType.NEST, "Large room for keeping eggs", null, null);
        return eggRoom;
    }
    
    public static Room larvaNursery() {
        Room larvaRoom = new Room("Larva Nursery", "larva nursery", Room.roomSize.SMALL, Room.roomType.NEST, "Room for growing larva", null, null);
        return larvaRoom;
    }
    
    public static Room larvaNurseryLarge() {
        Room larvaRoom = new Room("Larva Nursery", "larva nursery", Room.roomSize.LARGE, Room.roomType.NEST, "Large room for growing larva", null, null);
        return larvaRoom;
    }
    
    public static Room pupaNursery() {
        Room pupaRoom = new Room("Pupa Nursery", "pupa nursery", Room.roomSize.SMALL, Room.roomType.NEST, "Quiet room where pupas change", null, null);
        return pupaRoom;
    }
    
    public static Room pupaNurseryLarge() {
        Room pupaRoom = new Room("Pupa Nursery", "pupa nursery", Room.roomSize.LARGE, Room.roomType.NEST, "Large, quiet room where pupas change", null, null);
        return pupaRoom;
    }
    
    public static Room larder() {
        Room foodRoom = new Room("Small Larder", "larder", Room.roomSize.SMALL, Room.roomType.HOARD, "For storing things to eat", null, null);
        return foodRoom;
    }
    
    public static Room larderLarge() {
        Room foodRoom = new Room("Large Larder", "larder", Room.roomSize.LARGE, Room.roomType.HOARD, "Large food storage room", null, null);
        return foodRoom;
    }
    
    public static Room winterQuarters() {
        Room winterRoom = new Room("Winter Quarters", "winter", Room.roomSize.SMALL, Room.roomType.QUARTERS, "A place to wait out the winter", null, null);
        return winterRoom;
    }
    
    public static Room workerQuarters() {
        Room workerRoom = new Room("Worker Quarters", "resting", Room.roomSize.SMALL, Room.roomType.QUARTERS, "Room for ants to rest", null, null);
        return workerRoom;
    }
    
    public static Room workerQuartersLarge() {
        Room workerRoom = new Room("Worker Quarters", "resting", Room.roomSize.LARGE, Room.roomType.QUARTERS, "Large room for ants to rest", null, null);
        return workerRoom;
    }
    
    public static Room garbagePile() {
        Room trashRoom = new Room("Refuse Pile", "trash", Room.roomSize.SMALL, Room.roomType.STORAGE, "A place for unwanted material", null, null);
        return trashRoom;
    }
    
    public static Room garbagePileLarge() {
        Room trashRoom = new Room("Refuse Pile", "trash", Room.roomSize.LARGE, Room.roomType.STORAGE, "A large place for unwanted material", null, null);
        return trashRoom;
    }
}