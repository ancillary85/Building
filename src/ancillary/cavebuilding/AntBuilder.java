/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Mlaptop
 */
public class AntBuilder extends GeneralBuilder{
    
    // ID's 
    public final static String ANT = "Ant";
    public final static String WORKER = "Worker";
    public final static String SOLDIER = "Soldier";
    public final static String DRONE = "Drone";
    public final static String QUEEN = "Queen";
    public final static String EGG = "Egg";
    public final static String LARVA = "Larva";
    public final static String PUPA = "Pupa";
    
    public AntBuilder() {
        super();
        names = new Namer();
        setUpIDS();
        setUpCounts();
        setUpTraits();
        setUpTasks();
        setUpIdle();
        setUpBadges();
    }
    
    private void setUpIDS() {
        IDs = new HashSet();
        IDs.add(ANT);
        IDs.add(WORKER);
        IDs.add(SOLDIER);
        IDs.add(DRONE);
        IDs.add(QUEEN);
        IDs.add(EGG);
        IDs.add(LARVA);
        IDs.add(PUPA);
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
    
    private void setUpBadges() {
        for(String ant : IDs) {
            badgesToEntities.put(ant, this.makeEntity(ant, null));
        }
        
        for(String ant : IDs) {
            resetEntityCount(ant);
        }
    } 
    
    public ActiveEntity makeWorker(String name, String location) {
        counts.put(ANT, counts.get(ANT) + 1);
        counts.put(WORKER, counts.get(WORKER) + 1);
        String tempName = name;
        String tempLoc = location;
        if(name == null || name.equals("")) {tempName = "Random Worker";}
        if(location == null || location.equals("")) {tempLoc = "somewhere";}
        List<Task> defaultTasks = Arrays.asList(tasks.get(WORKER));
        ActiveEntity w = new ActiveEntity(WORKER + "," + ANT, tempName, tempLoc, defaultTasks, idle.get(WORKER), traits.get(WORKER));
        w.setTraitDisplayPriority(traitDisplayPriority);
        return w;
    }
    
    public ActiveEntity makeSoldier(String name, String location) {
        counts.put(ANT, counts.get(ANT) + 1);
        counts.put(SOLDIER, counts.get(SOLDIER) + 1);
        if(name == null || name.equals("")) {name = "Random Soldier";}
        if(location == null || location.equals("")) {location = "somewhere";}
        List<Task> defaultTasks = Arrays.asList(tasks.get(SOLDIER));
        ActiveEntity s = new ActiveEntity(SOLDIER + "," + ANT, name, location, defaultTasks, idle.get(SOLDIER), traits.get(SOLDIER));
        s.setTraitDisplayPriority(traitDisplayPriority);
        return s;
    }
    
    public ActiveEntity makeQueen(String name, String location) {
        counts.put(ANT, counts.get(ANT) + 1);
        counts.put(QUEEN, counts.get(QUEEN) + 1);
        if(name == null || name.equals("")) {name = "Random Queen";}
        if(location == null || location.equals("")) {location = "somewhere";}
        List<Task> defaultTasks = Arrays.asList(tasks.get(QUEEN));
        ActiveEntity s = new ActiveEntity(QUEEN + "," + ANT, name, location, defaultTasks, idle.get(QUEEN), traits.get(QUEEN));
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
    public ActiveEntity makeEntity(String id, String name) {
        String parsed = parseId(id);
        String tempName = name;
        if(name == null) {
            tempName = defaultName(parsed);
        }
        
        List<Task> groupedTasks;
        List<Trait> groupedTraits;
        
        if(parsed.equals(EGG) || parsed.equals(LARVA))
        {
            groupedTasks = Arrays.asList(tasks.get(parsed));
            groupedTraits = Arrays.asList(traits.get(parsed));
        }
        else if(parsed.equals(PUPA)) {
            groupedTasks = Arrays.asList(AntTaskBuilder.getPupaTask(parsePupa(id)));
            groupedTraits = Arrays.asList(traits.get(PUPA));
        }
        else {
            groupedTasks = super.combineTasks(id);
            groupedTraits = super.combineTraits(id);
        }
        
        ActiveEntity result = new ActiveEntity(id, tempName, null, groupedTasks, idle.get(parsed), groupedTraits);
        result.setTraitDisplayPriority(traitDisplayPriority);
        result.clearTask();
        
        return result;
    }
    
    @Override
    public ActiveEntity makeEntity(String badge) {
        if(!badgesToEntities.containsKey(badge)) {
            return null;
        }
        
        ActiveEntity result = badgesToEntities.get(badge);
        result.setName(defaultName(badge));
        return result;
    }
    
    public String defaultName(String id) {
        if(counts.containsKey(id)) {
            if(!id.equals(EGG) && !id.equals(LARVA) && !id.equals(PUPA)) {
                counts.put(ANT, counts.get(ANT) + 1);
            }
            counts.put(id, counts.get(id) + 1);
            return id + " " + counts.get(id);
        }
        
        return id;
    }
    
    /**
     * Returns WORKER if parsing fails
     * @param id
     * @return a valid ant id
     */
    @Override
    public String parseId(String id) {
        String result = WORKER;
        for(String s : id.split(",")) {
            if(s.equalsIgnoreCase(QUEEN)) {
                result = QUEEN;
                break;
            }
            if(s.equalsIgnoreCase(DRONE)) {
                if(!result.equals(QUEEN)) {
                    result = DRONE;
                }
            }
            if(s.equalsIgnoreCase(SOLDIER)) {
                if(!result.equals(QUEEN) && !result.equals(DRONE)) {
                    result = SOLDIER;
                }
            }
            if(s.equalsIgnoreCase(WORKER)) {
                if(!result.equals(QUEEN) && !result.equals(DRONE) && !result.equals(SOLDIER)) {
                    result = WORKER;
                }
            }
                
            if(s.equalsIgnoreCase(EGG)) {return EGG;}
            if(s.equalsIgnoreCase(LARVA)) {return LARVA;}
            if(s.equalsIgnoreCase(PUPA)) {return PUPA;}
        }
        
        return result;
    }
    
    /**
     * Pulls out what kind of ant the pupa should become from the id. Returns WORKER if unsure.
     * @param id
     * @return 
     */
    public String parsePupa(String id) {
        String[] parts = id.split(",");
        if(parts.length != 2) {
            return WORKER;
        }
        
        if(parts[0].equalsIgnoreCase(PUPA)) {
            return parts[1];
        }
        
        if(parts[1].equalsIgnoreCase(PUPA)) {
            return parts[0];
        }
        
        return WORKER;
    }

    @Override
    public String getBuilderID() {
        return "Ant Builder";
    }
}
