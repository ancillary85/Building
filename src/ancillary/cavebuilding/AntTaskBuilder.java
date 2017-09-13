/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.Arrays;

/**
 *
 * @author Alecto
 */


public class AntTaskBuilder {
 
    private static final Trait.trait_type[] rPR = TraitBuilder.resourceProductionResult;
    private static final Trait.trait_type[] pRR = TraitBuilder.personalResourceResult;
    
    public static Task dig() {
        Task DIG = new Task("Dig", 1, "Digging",
            null, 
            null,
            new Trait[]{new Trait("Space", 1.0, rPR)},
            "Expand the colony");
        return DIG;
    }
    public static Task forage() {
        Task FORAGE = new Task("Forage", 2, "Foraging",
            null, 
            null, 
            new Trait[]{new Trait("Food", 1.0, rPR)},
            "Search for food");
        return FORAGE;
    }
    public static Task hunt() {
        Task HUNT = new Task("Hunt", 3, "Hunting",
            new String[]{"Stamina -1 " + Task.PERSONAL_RESOURCE}, 
            new String[]{"Stamina >0 " + Task.RESOURCE}, 
            new Trait[]{new Trait("Food", 2.0, rPR)},
            "Hunt for food");
        return HUNT;
    }
    public static Task larvaCare() {
        Task LARVA_CARE = new Task("Tend to Larva", 1, "Larva tending",
            new String[]{"Food -1 " + Task.RESOURCE, Task.SELF + " = " + AntBuilder.WORKER}, 
            new String[]{"Food >0 " + Task.RESOURCE, "Larva >0 " + Task.ENTITY}, 
            new Trait[]{new Trait("Larva Stamina", 1.0, pRR)}, 
            "Care for growing larval ants");
        return LARVA_CARE;
    }
    public static Task eat() {
        Task EAT = new Task("Eat", 2, "Eating",
            new String[]{"Food -1 " + Task.RESOURCE}, 
            new String[]{"Food >0 " + Task.RESOURCE}, 
            new Trait[]{new Trait("Stamina", 4.0, rPR)},
            "Eat some food");
        return EAT;
    }
    public static Task rest() {
        Task REST = new Task("Rest", 1, "Resting",
            null, 
            null, 
            new Trait[]{new Trait("Stamina", 1.0, pRR)},
            "Take a rest");
        return REST;
    }
    
    public static Task fight() {
        Task FIGHT = new Task("Fight", 1, "Fighting",
            new String[]{"Stamina -2 " + Task.PERSONAL_RESOURCE},
            new String[]{"Stamina >1 " + Task.PERSONAL_RESOURCE},
            new Trait[]{new Trait("Enemies", -1.0, rPR)},
            "Fight an enemy");
        return FIGHT;
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
}
