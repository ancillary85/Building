/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Alecto
 */
public class AntBuilder {
    
    // ID 
    public final static String ANT = "ant";
    public final static String WORKER = "worker";
    public final static String SOLDIER = "soldier";
    public final static String DRONE = "drone";
    public final static String QUEEN = "queen";
    public final static String EGG = "egg";
    public final static String LARVA = "larva";
    public final static String PUPA = "pupa";
    
    public final static Trait[] WORKER_TRAITS = new Trait[] {new Trait("Cuteness", 4, "Will Soldier-sempai notice me?", Trait.trait_type.FLAVOR), 
                                                                                        new Trait("Dedication", 7, "I will do my best!", Trait.trait_type.FLAVOR),
                                                                                        new Trait("Poop test", 1, "Poop +1\nWhat else do ants always do?", TraitBuilder.resourceProductionResult),
                                                                                        new Trait("Hunger", -1, "Food -1", TraitBuilder.resourceProductionResult)
                                                                       };
    public final static Trait[] SOLDIER_TRAITS = new Trait[] {new Trait("Coolness", 6, "Oh, hello.", Trait.trait_type.FLAVOR), 
                                                                                        new Trait("Sturdy", 7, "I am the sword and shield of the nest!", Trait.trait_type.COMBAT), 
                                                                                        new Trait("Dedication", 7, "I will do my best!", Trait.trait_type.FLAVOR),
                                                                                        new Trait("Hunger", -1, "Food -1", TraitBuilder.resourceProductionResult)
                                                                       };
    public final static Trait[] DRONE_TRAITS = new Trait[] {new Trait("Ecchi", 4, "Hey, foxy mama.", Trait.trait_type.FLAVOR), 
                                                                                     new Trait("Lazy", 9, "Can't someone else do it?", Trait.trait_type.FLAVOR)
                                                                       };
    public final static Trait[] QUEEN_TRAITS = new Trait[] {new Trait("Cuteness", 3, "Ara ara~", Trait.trait_type.FLAVOR), 
                                                                                     new Trait("Dedication", 8, "Duty to the very end", Trait.trait_type.FLAVOR)
                                                                       };
    public final static Trait[] EGG_TRAITS = new Trait[] {new Trait("Cuteness", 7, "...", Trait.trait_type.FLAVOR)};
    public final static Trait[] LARVA_TRAITS = new Trait[] {new Trait("Cuteness", 8, "uguu", Trait.trait_type.FLAVOR)};
    public final static Trait[] PUPA_TRAITS = new Trait[] {new Trait("Cuteness", 6, "I hope you like my new look.", Trait.trait_type.FLAVOR)};
    
    // Idle
    public final static String WORKER_IDLE = "Just a cog in the machine.";
    public final static String SOLDIER_IDLE = "Semper Fi!";
    public final static String DRONE_IDLE = "How ya doing, baby?";
    public final static String QUEEN_IDLE = "Let them eat cake";
    public final static String EGG_IDLE = "Don't put in baskets.";
    public final static String LARVA_IDLE = "Om nom nom!";
    public final static String PUPA_IDLE = "Zzzzz.";
    
    
     //  public ActiveEntity(String id, String name, String location, List<Task> tasks)
    public final static ActiveEntity makeWorker(String name, String location) {
        
        if(name == null || name.equals("")) {name = "Random Worker";}
        if(location == null || location.equals("")) {location = "somewhere";}
        List<Task> tasks = Arrays.asList(AntTaskBuilder.workerTasks());
        ActiveEntity w = new ActiveEntity(WORKER + " " + ANT, name, location, tasks, WORKER_IDLE, WORKER_TRAITS);
        return w;
    }
    
    public final static ActiveEntity makeSoldier(String name, String location) {
        
        if(name == null || name.equals("")) {name = "Random Soldier";}
        if(location == null || location.equals("")) {location = "somewhere";}
        List<Task> tasks = Arrays.asList(AntTaskBuilder.soldierTasks());
        ActiveEntity s = new ActiveEntity(SOLDIER + " " + ANT, name, location, tasks, SOLDIER_IDLE, SOLDIER_TRAITS);
        return s;
    }
}
