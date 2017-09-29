/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.Arrays;
import java.util.EnumSet;

/**
 *
 * @author Alecto
 */


public class AntTaskBuilder {
 
    public static Task dig() {
        Task DIG = new Task("Dig", 1, "Digging",
            new Trait[]{new Trait("Patience", -1, TraitBuilder.personal_resource())}, 
            null,
            new Trait[]{new Trait("Space", 1, TraitBuilder.resource())},
            "Expand the colony");
        return DIG;
    }
    public static Task forage() {
        Task FORAGE = new Task("Forage", 2, "Foraging",
            new Trait[]{new Trait("Machetes", -1, TraitBuilder.resource())}, 
            null, 
            new Trait[]{new Trait("Food", 1, TraitBuilder.resource())},
            "Search for food");
        return FORAGE;
    }
    public static Task hunt() {
        Task HUNT = new Task("Hunt", 3, "Hunting",
            new Trait[]{new Trait("Stamina", -1, TraitBuilder.personal_resource())}, 
            new Trait[]{new Trait("Stamina", 0, TraitBuilder.reqGreaterThanPersonal())}, 
            new Trait[]{new Trait("Food", 2, TraitBuilder.resource())},
            "Hunt for food");
        return HUNT;
    }
    public static Task larvaCare() {
        Task LARVA_CARE = new Task("Tend to Larva", 1, "Larva tending",
            new Trait[]{new Trait("Food", -1, TraitBuilder.resource())}, 
            new Trait[]{new Trait("Food", 0, TraitBuilder.reqGreaterThanResource()), new Trait("Larva", 0, "Larva in need", TraitBuilder.reqGreaterThanCreation())}, 
            new Trait[]{new Trait("Larva Stamina", 1, TraitBuilder.resourceHidden())}, 
            "Care for growing larval ants");
        return LARVA_CARE;
    }
    public static Task eat() {
        Task EAT = new Task("Eat", 2, "Eating",
            new Trait[]{new Trait("Food", -1, TraitBuilder.resource())}, 
            new Trait[]{new Trait("Food", 0, TraitBuilder.reqGreaterThanResource())}, 
            new Trait[]{new Trait("Stamina", 4, TraitBuilder.personal_resource())},
            "Eat some food");
        return EAT;
    }
    public static Task rest() {
        Task REST = new Task("Rest", 1, "Resting",
            null, 
            null, 
            new Trait[]{new Trait("Stamina", 1, TraitBuilder.personal_resource())},
            "Take a rest");
        return REST;
    }
    
    public static Task fight() {
        Task FIGHT = new Task("Fight", 1, "Fighting",
            new Trait[]{new Trait("Stamina", -2, TraitBuilder.personal_resource())},
            new Trait[]{new Trait("Stamina", 1, TraitBuilder.reqGreaterThanPersonal())},
            new Trait[]{new Trait("Enemies", -1, TraitBuilder.resource()), new Trait("Stamina", -1, TraitBuilder.personal_resource())},
            "Fight an enemy");
        return FIGHT;
    }
    
    public static Task brood() {
        Task BROOD = new Task("Brood", 2, "Laying eggs",
            new Trait[]{new Trait("Patience", -1, TraitBuilder.personal_resource())}, 
            null,
            new Trait[]{new Trait("Egg", 2, TraitBuilder.creation())},
            "Lay new eggs");
        return BROOD;
    }
    
    public static Task eatEgg() {
        Task EATEGG = new Task("Eat an Egg", 1, "Eating an egg", 
            null,
            new Trait[]{new Trait("Egg", 0, "Egg to eat", TraitBuilder.reqGreaterThanCreation())},
            new Trait[]{new Trait("Egg", -1, TraitBuilder.uncreate())},
            "Eat an egg to regain strength");
        
        return EATEGG;
    }
    
    public static Task[] antTasks() {
        return new Task[]{dig(), forage(), hunt(), eat(), rest()};
    }
    
    public static Task[] workerTasks() {
        Task[] workTasks = Arrays.copyOf(antTasks(), antTasks().length + 1);
        workTasks[workTasks.length - 1] = larvaCare();
        return workTasks;
    }
    
    public static Task[] soldierTasks() {
        Task[] soldierTasks = Arrays.copyOf(antTasks(), antTasks().length + 1);
        soldierTasks[soldierTasks.length - 1] = fight();
        return soldierTasks;
    }

    static Task[] droneTasks() {
        return antTasks();
    }

    static Task[] queenTasks() {
        Task[] queenTasks = new Task[]{dig(), eat(), rest(), brood(), eatEgg()};
        
        return queenTasks;
    }

    static Task[] eggTasks() {
        return antTasks();
    }

    static Task[] larvaTasks() {
        return antTasks();
    }

    static Task[] pupaTasks() {
        return antTasks();
    }
}
