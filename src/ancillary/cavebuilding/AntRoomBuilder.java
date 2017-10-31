/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;

/**
 *
 * @author MLaptop
 */
public class AntRoomBuilder {
    //public Room(String initName, String initID, roomSize initSize, roomType initType, String desc, Room[] neighborhood, ArrayList<Trait> initTraits, ArrayList<Tasks> initTasks)
    public static Room surface() {
        Room outside = new Room("Surface", "surface", Room.roomSize.SMALL, Room.roomType.EXTERIOR, "The outdoors", null, null, null);
        
        Trait result = new Trait("Ant Hill", 1, TraitBuilder.room_change());
        result.setDesc("antHill");
        Task embiggen = new Task("Dig a new entrance", 2, "Digging",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{result},
            "Start an ant hill");
        outside.getTasks().add(embiggen);

        return outside;
    }
    
    public static Room antHill() {
        Room hill = new Room("Ant Hill", "hill", Room.roomSize.SMALL, Room.roomType.ENTRANCE, "A small ant hill", null, null, null);
        
        Trait result = new Trait("Ant Hill", 1, TraitBuilder.room_change());
        result.setDesc("antHill");
        Task embiggen = new Task("Make the hill larger", 2, "Expanding",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{result},
            "Enlarge the entrance");
        hill.getTasks().add(embiggen);
        
        return hill;
    }
    
    public static Room antHillLarge() {
        Room hill = new Room("Ant Hill", "hill", Room.roomSize.LARGE, Room.roomType.ENTRANCE, "A large ant hill", null, null, null);
        return hill;
    }
    
    public static Room dirtSquare() {
        Room wall = new Room("Dirt", "dirt", Room.roomSize.SMALL, Room.roomType.WALL, "", null, null, null);
        Trait result = new Trait("Empty Room", 1, TraitBuilder.room_change());
        result.setDesc("readySquare");
        
        Task digOut = new Task("Excavate", 2, "Clearing Dirt",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{result},
            "Clear the dirt to make a new room");
        wall.getTasks().add(digOut);
        return wall;
    }
    
    public static Room readySquare() {
        Room empty = new Room("Empty Room", "dirt", Room.roomSize.SMALL, Room.roomType.WALL, "", null, null, null);
        empty.getTasks().addAll(readyRoomTasks());
        return empty;
    }
    
    public static Room hallway() {
        Room corridor = new Room("Hallway", "hallway", Room.roomSize.SMALL, Room.roomType.CORRIDOR, "A hallway between rooms", null, null, null);
        return corridor;
    }
    
    public static Room shaft() {
        Room stairs = new Room("Shaft", "shaft", Room.roomSize.SMALL, Room.roomType.SHAFT, "A vertical passage", null, null, null);
        return stairs;
    }
    
    public static Room queenChamber() {
        Room queenRoom = new Room("Queen's Chamber", "throne", Room.roomSize.SMALL, Room.roomType.QUARTERS, "The queen lives here", null, null, null);
        
        Trait result = new Trait("Large Queen's Chamber", 1, TraitBuilder.room_change());
        result.setDesc("queenChamberLarge");
        Task embiggen = new Task("Upgrade to Large size", 2, "Expanding",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{result},
            "Expand this room");
        queenRoom.getTasks().add(embiggen);
        
        return queenRoom;
    }
    
    public static Room queenChamberLarge() {
        Room queenRoom = new Room("Queen's Chamber", "throne", Room.roomSize.LARGE, Room.roomType.QUARTERS, "The queen lives here", null, null, null);
        return queenRoom;
    }
    
    public static Room eggNursery() {
        Room eggRoom = new Room("Egg Nursery", "egg nursery", Room.roomSize.SMALL, Room.roomType.NEST, "Room for keeping eggs", null, null, null);
        
        Trait result = new Trait("Large Egg Nursery", 1, TraitBuilder.room_change());
        result.setDesc("eggNurseryLarge");
        Task embiggen = new Task("Upgrade to Large size", 2, "Expanding",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{result},
            "Expand this room");
        eggRoom.getTasks().add(embiggen);
        
        return eggRoom;
    }
    
    public static Room eggNurseryLarge() {
        Room eggRoom = new Room("Egg Nursery", "egg nursery", Room.roomSize.LARGE, Room.roomType.NEST, "Large room for keeping eggs", null, null, null);
        return eggRoom;
    }
    
    public static Room larvaNursery() {
        Room larvaRoom = new Room("Larva Nursery", "larva nursery", Room.roomSize.SMALL, Room.roomType.NEST, "Room for growing larva", null, null, null);
        
        Trait result = new Trait("Large Larva Nursery", 1, TraitBuilder.room_change());
        result.setDesc("larvaNurseryLarge");
        Task embiggen = new Task("Upgrade to Large size", 2, "Expanding",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{result},
            "Expand this room");
        larvaRoom.getTasks().add(embiggen);
        
        return larvaRoom;
    }
    
    public static Room larvaNurseryLarge() {
        Room larvaRoom = new Room("Larva Nursery", "larva nursery", Room.roomSize.LARGE, Room.roomType.NEST, "Large room for growing larva", null, null, null);
        return larvaRoom;
    }
    
    public static Room pupaNursery() {
        Room pupaRoom = new Room("Pupa Nursery", "pupa nursery", Room.roomSize.SMALL, Room.roomType.NEST, "Quiet room where pupas change", null, null, null);
        
        Trait result = new Trait("Large Pupa Nursery", 1, TraitBuilder.room_change());
        result.setDesc("pupaNurseryLarge");
        Task embiggen = new Task("Upgrade to Large size", 2, "Expanding",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{result},
            "Expand this room");
        pupaRoom.getTasks().add(embiggen);
        
        return pupaRoom;
    }
    
    public static Room pupaNurseryLarge() {
        Room pupaRoom = new Room("Pupa Nursery", "pupa nursery", Room.roomSize.LARGE, Room.roomType.NEST, "Large, quiet room where pupas change", null, null, null);
        return pupaRoom;
    }
    
    public static Room larder() {
        Room foodRoom = new Room("Small Larder", "larder", Room.roomSize.SMALL, Room.roomType.HOARD, "For storing things to eat", null, null, null);
        
        Trait result = new Trait("Large Larder", 1, TraitBuilder.room_change());
        result.setDesc("larderLarge");
        Task embiggen = new Task("Upgrade to Large size", 2, "Expanding",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{result},
            "Expand this room");
        foodRoom.getTasks().add(embiggen);
        
        return foodRoom;
    }
    
    public static Room larderLarge() {
        Room foodRoom = new Room("Large Larder", "larder", Room.roomSize.LARGE, Room.roomType.HOARD, "Large food storage room", null, null, null);
        return foodRoom;
    }
    
    public static Room winterQuarters() {
        Room winterRoom = new Room("Winter Quarters", "winter", Room.roomSize.SMALL, Room.roomType.QUARTERS, "A place to wait out the winter", null, null, null);
        return winterRoom;
    }
    
    public static Room workerQuarters() {
        Room workerRoom = new Room("Worker Quarters", "resting", Room.roomSize.SMALL, Room.roomType.QUARTERS, "Room for ants to rest", null, null, null);
        
        Trait result = new Trait("Large Workers Quarters", 1, TraitBuilder.room_change());
        result.setDesc("workerQuartersLarge");
        Task embiggen = new Task("Upgrade to Large size", 2, "Expanding",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{result},
            "Expand this room");
        workerRoom.getTasks().add(embiggen);
        
        return workerRoom;
    }
    
    public static Room workerQuartersLarge() {
        Room workerRoom = new Room("Worker Quarters", "resting", Room.roomSize.LARGE, Room.roomType.QUARTERS, "Large room for ants to rest", null, null, null);
        return workerRoom;
    }
    
    public static Room garbagePile() {
        Room trashRoom = new Room("Refuse Pile", "trash", Room.roomSize.SMALL, Room.roomType.STORAGE, "A place for unwanted material", null, null, null);
        
        Trait result = new Trait("Large Refuse Pile", 1, TraitBuilder.room_change());
        result.setDesc("garbagePileLarge");
        Task embiggen = new Task("Upgrade to Large size", 2, "Expanding",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{result},
            "Expand this room");
        trashRoom.getTasks().add(embiggen);
        
        return trashRoom;
    }
    
    public static Room garbagePileLarge() {
        Room trashRoom = new Room("Refuse Pile", "trash", Room.roomSize.LARGE, Room.roomType.STORAGE, "A large place for unwanted material", null, null, null);
        return trashRoom;
    }
    
    public static ArrayList<Task> readyRoomTasks() {
        ArrayList<Task> tasks = new ArrayList();
        
        Trait resultEN = new Trait("Egg Nursery", 1, TraitBuilder.room_change());
        resultEN.setDesc("eggNursery");
        Task makeEN = new Task("Build Egg Nursery", 2, "Building",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{resultEN},
            "Make a space for eggs to incubate");
        tasks.add(makeEN);
        
        Trait resultGP = new Trait("Refuse Pile", 1, TraitBuilder.room_change());
        resultGP.setDesc("garbagePile");
        Task makeGP = new Task("Build Refuse Pile", 2, "Building",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{resultGP},
            "Make space for the queen");
        tasks.add(makeGP);
        
        Trait resultH = new Trait("Hallway", 1, TraitBuilder.room_change());
        resultH.setDesc("hallway");
        Task makeH = new Task("Build Hallway", 2, "Building",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{resultH},
            "Make a hallway");
        tasks.add(makeH);
        
        Trait resultL = new Trait("Larder", 1, TraitBuilder.room_change());
        resultL.setDesc("larder");
        Task makeL = new Task("Build Larder", 2, "Building",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{resultL},
            "Make food storage");
        tasks.add(makeL);
        
        Trait resultLN = new Trait("Larva Nursery", 1, TraitBuilder.room_change());
        resultLN.setDesc("larvaNursery");
        Task makeLN = new Task("Build Larva Nursery", 2, "Building",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{resultLN},
            "Make a space for larva to grow");
        tasks.add(makeLN);
        
        Trait resultPN = new Trait("Pupa Nursery", 1, TraitBuilder.room_change());
        resultPN.setDesc("pupaNursery");
        Task makePN = new Task("Build Pupa Nursery", 2, "Building",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{resultPN},
            "Make a space for pupa to change");
        tasks.add(makePN);
        
        Trait resultQC = new Trait("Queen's Chamber", 1, TraitBuilder.room_change());
        resultQC.setDesc("queenChamber");
        Task makeQC = new Task("Build Queen's Chamber", 2, "Building",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink()), new Trait("Throne", 0, TraitBuilder.reqEqualRoom())},
            new Trait[]{resultQC},
            "Make space for the queen");
        tasks.add(makeQC);
        
        Trait resultS = new Trait("Shaft", 1, TraitBuilder.room_change());
        resultS.setDesc("shaft");
        Task makeS = new Task("Build Shaft", 2, "Building",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{resultS},
            "Make a vertical passage");
        tasks.add(makeS);
        
        Trait resultWQ = new Trait("Winter Quarters", 1, TraitBuilder.room_change());
        resultWQ.setDesc("winterQuarters");
        Task makeWQ = new Task("Build Winter Quarters", 2, "Building",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{resultWQ},
            "Make a warm place for winter");
        tasks.add(makeWQ);
        
        Trait resultWrkQ = new Trait("Worker Quarters", 1, TraitBuilder.room_change());
        resultWrkQ.setDesc("workerQuarters");
        Task makeWrkQ = new Task("Build Worker Quarters", 2, "Building",
            null,
            new Trait[]{new Trait("Ant", 0, TraitBuilder.reqGreaterThanCreationLink())},
            new Trait[]{resultWrkQ},
            "Make a space for ants to rest");
        tasks.add(makeWrkQ);
        
        return tasks;
    }
}