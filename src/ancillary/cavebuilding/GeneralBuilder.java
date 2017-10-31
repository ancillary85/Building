/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author MLaptop
 */
public class GeneralBuilder implements ActiveEntityBuilder {

    protected HashSet<String> IDs = new HashSet();
    
    protected HashMap<String, ActiveEntity> badgesToEntities = new HashMap();
    
    protected HashMap<String, Integer> counts = new HashMap();
    
    protected Namer names;
    
    protected HashMap<String, Task[]> tasks = new HashMap();
    
    protected HashMap<String, Trait[]> traits = new HashMap();
    
    protected Trait.trait_type[] traitDisplayPriority = {Trait.trait_type.ATTRIBUTE, Trait.trait_type.RESULT, Trait.trait_type.COMBAT, Trait.trait_type.FLAVOR};
    
    protected HashMap<String, String> idle = new HashMap();
    
    public GeneralBuilder() {
        names = new Namer();
    }
    
    public GeneralBuilder(Namer n) {
        names = n;
    }
    
    public void addID(String newID) {
        IDs.add(newID);
    }

    public void addBadge(String newBadge, ActiveEntity hasBadge) {
        badgesToEntities.put(newBadge, hasBadge);
    }
    
    public void removeID(String id) {
        IDs.remove(id);
        tasks.remove(id);
        traits.remove(id);
        idle.remove(id);
    }
    
    public void removeBadge(String badge) {
        badgesToEntities.remove(badge);
    }
    
    @Override
    public String getBuilderID() {
        return "General Builder";
    }

    @Override
    public ActiveEntity makeEntity() {
        return new ActiveEntity();
    }

    @Override
    public ActiveEntity makeEntity(String id, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ActiveEntity makeEntity(String badge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        if(counts.containsKey(id)) {
            return counts.get(id);
        }
        else {
            return 0;
        }
    }

    @Override
    public void resetEntityCount(String id) {
        counts.put(id, new Integer(0));
    }

    @Override
    public void setEntityCount(String id, int value) {
        counts.put(id, new Integer(value));
    }

    @Override
    public Set<String> getIds() {
        return IDs;
    }

    @Override
    public Set<String> getBadges() {
        return badgesToEntities.keySet();
    }
    
    @Override
    public List<Trait> getTraits(String id) {
        if(traits.containsKey(id)) {
            return Arrays.asList(traits.get(id));
        }
        else {
            return new ArrayList<Trait>();
        }
    }

    @Override
    public List<Task> getTasks(String id) {
        if(tasks.containsKey(id)) {
            return Arrays.asList(tasks.get(id));
        }
        else {
            return new ArrayList<Task>();
        }
    }

    @Override
    public String getIdle(String id) {
        return idle.get(id);
    }

    @Override
    public void setTraits(String id, List<Trait> newTraits) {
        traits.put(id, (Trait[])newTraits.toArray());
    }

    @Override
    public void setTasks(String id, List<Task> newTasks) {
        tasks.put(id, (Task[])newTasks.toArray());
    }

    @Override
    public void setIdle(String id, String newIdle) {
        idle.put(id, newIdle);
    }

    /**
     * Makes no attempt to pick out an id. Returns its input.
     * @param id
     * @return 
     */
    @Override
    public String parseId(String id) {
        return id;
    }

    @Override
    public Trait.trait_type[] getTraitDisplayPriority() {
        return traitDisplayPriority;
    }

    @Override
    public void setTraitDisplayPriority(Trait.trait_type[] priorities) {
        traitDisplayPriority = priorities;
    }

    @Override
    public Comparator<String> idComparator() {
        return new Comparator<String>() {
            @Override
            public int compare(String id1, String id2) {
                if(parseId(id1 + "," + id2).equalsIgnoreCase(id1)) {
                    return -1;
                }
                else {
                    return 1;
                }
            }
        };
    }
    
    @Override
    public List<Trait> combineTraits(String id) {
        HashSet<String> traitNames = new HashSet();
        ArrayList<Trait> combinedTraits = new ArrayList();
        String[] sortedIDS = id.split(",");      
        Arrays.sort(sortedIDS, idComparator());
        
        for(String subID: sortedIDS) {
            for(Trait t : getTraits(subID)) {
                if(!traitNames.contains(t.getName())) {
                    traitNames.add(t.getName());
                    combinedTraits.add(t);
                }
            }
        }
        
        return combinedTraits;
    }

    @Override
    public List<Task> combineTasks(String id) {
        HashSet<String> taskNames = new HashSet();
        ArrayList<Task> combinedTasks = new ArrayList();
        String[] sortedIDS = id.split(",");      
        Arrays.sort(sortedIDS, idComparator());
        
        for(String subID: sortedIDS) {
            for(Task t : getTasks(subID)) {
                if(!taskNames.contains(t.getName())) {
                    taskNames.add(t.getName());
                    combinedTasks.add(t);
                }
            }
        }
        
        return combinedTasks;
    }
}
