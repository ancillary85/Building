/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Alecto
 */
public class AntBuilder implements EntityBuilder{
    
    // ID's 
    public final static String ANT = "Ant";
    public final static String WORKER = "Worker";
    public final static String SOLDIER = "Soldier";
    public final static String DRONE = "Drone";
    public final static String QUEEN = "Queen";
    public final static String EGG = "Egg";
    public final static String LARVA = "Larva";
    public final static String PUPA = "Pupa";
    
    private HashMap<String, Integer> counts;
    
    private Namer names;
    
    private HashMap<String, Task[]> tasks;
    
    private HashMap<String, Trait[]> traits;
    
    private Trait.trait_type[] traitDisplayPriority = {Trait.trait_type.ATTRIBUTE, Trait.trait_type.RESULT, Trait.trait_type.COMBAT, Trait.trait_type.FLAVOR};
    
    private HashMap<String, String> idle;
    
    public AntBuilder() {
        names = new Namer();
        setUpCounts();
        setUpTraits();
        setUpTasks();
        setUpIdle();
    }
    
    private void setUpCounts() {
        counts = new HashMap();
        counts.put(ANT, new Integer(0));
        counts.put(WORKER, new Integer(0));
        counts.put(SOLDIER, new Integer(0));
        counts.put(DRONE, new Integer(0));
        counts.put(QUEEN, new Integer(0));
        counts.put(EGG, new Integer(0));
        counts.put(LARVA, new Integer(0));
        counts.put(PUPA, new Integer(0));
    }
    
    private void setUpTasks() {
        tasks = new HashMap();
        tasks.put(ANT, AntTaskBuilder.antTasks());
        tasks.put(WORKER, AntTaskBuilder.workerTasks());
        tasks.put(SOLDIER, AntTaskBuilder.soldierTasks());
        tasks.put(DRONE, AntTaskBuilder.droneTasks());
        tasks.put(QUEEN, AntTaskBuilder.queenTasks());
        tasks.put(EGG, AntTaskBuilder.eggTasks());
        tasks.put(LARVA, AntTaskBuilder.larvaTasks());
        tasks.put(PUPA, AntTaskBuilder.pupaTasks());
    }
    
    private void setUpTraits() {
        traits = new HashMap();
        traits.put(WORKER, new Trait[] {new Trait("Cuteness", 4, "Will Soldier-sempai notice me?", TraitBuilder.flavor()), 
                                                       new Trait("Dedication", 7, "I will do my best!", TraitBuilder.flavor()), 
                                                       new Trait("Poop test", 1, "Poop +1\nWhat else do ants always do?", TraitBuilder.resourceProductionResult()), 
                                                       new Trait("Hunger", -1, "Food -1", TraitBuilder.resourceProductionResult())});
        traits.put(SOLDIER, new Trait[] {new Trait("Coolness", 6, "Oh, hello.", TraitBuilder.flavor()), 
                                                       new Trait("Sturdy", 7, "I am the sword and shield of the nest!", TraitBuilder.combat()), 
                                                       new Trait("Dedication", 7, "I will do my best!", TraitBuilder.flavor()),
                                                       new Trait("Hunger", -1, "Food -1", TraitBuilder.resourceProductionResult())});
        
        traits.put(DRONE, new Trait[]   {new Trait("Ecchi", 4, "Hey, foxy mama.", TraitBuilder.flavor()), 
                                                       new Trait("Lazy", 9, "Can't someone else do it?", TraitBuilder.flavor())});
        traits.put(QUEEN, new Trait[]   {new Trait("Cuteness", 3, "Ara ara~", TraitBuilder.flavor()), 
                                                       new Trait("Dedication", 8, "Duty to the very end", TraitBuilder.flavor())});
        traits.put(EGG, new Trait[] {new Trait("Cuteness", 7, "...", TraitBuilder.flavor())});
        traits.put(LARVA, new Trait[] {new Trait("Cuteness", 8, "uguu", TraitBuilder.flavor())});
        traits.put(PUPA, new Trait[] {new Trait("Cuteness", 6, "I hope you like my new look.", TraitBuilder.flavor())});
    }
    
    private void setUpIdle() {
        idle = new HashMap();
        idle.put(ANT, "");
        idle.put(WORKER, "Just a cog in the machine.");
        idle.put(SOLDIER, "Semper Fi!");
        idle.put(DRONE, "How ya doing, baby?");
        idle.put(QUEEN, "Let them eat cake.");
        idle.put(EGG, "Don't put in baskets.");
        idle.put(LARVA, "Om nom nom!");
        idle.put(PUPA, "Zzzzz.");
    }
    
     
    public ActiveEntity makeWorker(String name, String location) {
        counts.put(ANT, counts.get(ANT) + 1);
        counts.put(WORKER, counts.get(WORKER) + 1);
        String tempName = name;
        String tempLoc = location;
        if(name == null || name.equals("")) {tempName = "Random Worker";}
        if(location == null || location.equals("")) {tempLoc = "somewhere";}
        List<Task> defaultTasks = Arrays.asList(tasks.get(WORKER));
        ActiveEntity w = new ActiveEntity(WORKER, tempName, tempLoc, defaultTasks, idle.get(WORKER), traits.get(WORKER));
        w.setTraitDisplayPriority(traitDisplayPriority);
        return w;
    }
    
    public ActiveEntity makeSoldier(String name, String location) {
        counts.put(ANT, counts.get(ANT) + 1);
        counts.put(SOLDIER, counts.get(SOLDIER) + 1);
        if(name == null || name.equals("")) {name = "Random Soldier";}
        if(location == null || location.equals("")) {location = "somewhere";}
        List<Task> defaultTasks = Arrays.asList(tasks.get(SOLDIER));
        ActiveEntity s = new ActiveEntity(SOLDIER, name, location, defaultTasks, idle.get(SOLDIER), traits.get(SOLDIER));
        s.setTraitDisplayPriority(traitDisplayPriority);
        return s;
    }

    /**
     * Defaults to generic worker ant
     * @return a worker ant
     */
    @Override
    public ActiveEntity makeEntity() {
        return makeWorker(null, null);
    }

    @Override
    public ActiveEntity makeEntity(String id, String name, String location) {
        String parsed = parseId(id);
        List<Task> defaultTasks = Arrays.asList(tasks.get(parsed));
        String tempName = name;
        String tempLoc = location;
        if(name == null || name.equals("")) {tempName = defaultName(parsed);}
        if(location == null || location.equals("")) {tempLoc = "somewhere";}
        ActiveEntity result = new ActiveEntity(parsed, tempName, tempLoc, defaultTasks, idle.get(parsed), traits.get(parsed));
        result.setTraitDisplayPriority(traitDisplayPriority);
        return result;
    }

    public String defaultName(String parsed) {
        if(counts.containsKey(parsed)) {
            counts.put(ANT, counts.get(ANT) + 1);
            counts.put(parsed, counts.get(parsed) + 1);
            return parsed + " " + counts.get(parsed);
        }
        
        return parsed;
    }
    
    /**
     * Returns WORKER if parsing fails
     * @param id
     * @return a valid ant id
     */
    @Override
    public String parseId(String id) {
        String[] parts = id.split(" ");
        for(String s : parts) {
            if(s.equalsIgnoreCase(WORKER)) {return WORKER;}
            if(s.equalsIgnoreCase(SOLDIER)) {return SOLDIER;}
            if(s.equalsIgnoreCase(DRONE)) {return DRONE;}
            if(s.equalsIgnoreCase(QUEEN)) {return QUEEN;}
            if(s.equalsIgnoreCase(EGG)) {return EGG;}
            if(s.equalsIgnoreCase(LARVA)) {return LARVA;}
            if(s.equalsIgnoreCase(PUPA)) {return PUPA;}
        }
        
        return WORKER;
    }
    
    @Override
    public Namer getNamer() {
        return names;
    }

    @Override
    public void setNamer(Namer newNamer) {
        names = newNamer;
    }

    @Override
    public int getEntityCount(String id) {
        return counts.get(id).intValue();
    }

    @Override
    public Set<String> getIds() {
        return idle.keySet();
    }

    @Override
    public Trait[] getTraits(String id) {
        return traits.get(id);
    }

    @Override
    public Task[] getTasks(String id) {
        return tasks.get(id);
    }
    
    @Override
    public String getIdle(String id) {
        return idle.get(id);
    }
}
