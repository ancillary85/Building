/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author MLatop
 */
public interface ActiveEntityBuilder {

    /**
     * Returns a String unique to this Builder's class. Multiple instances of the same Builder class should have the 
     * same id, and the same id should be consistently used.
     * @return a String unique to this Builder's class. Multiple instances of the same Builder class should have the same id
     */
    String getBuilderID();
    
    /**
     * Returns the default for this Builder
     * @return an ActiveEntity
     */
    ActiveEntity makeEntity();
    
    /**
     * Makes an ActiveEntity based on the given id. If name != null, it is used for the ActiveEntity. If name == null,
     * the Builder's usual behavior determines the name.
     * @param id
     * @param name
     * @return 
     */
    ActiveEntity makeEntity(String id, String name);
  
    /**
     * Makes an ActiveEntity based on the given builderBadge.
     * @param badge
     * @return 
     */
    ActiveEntity makeEntity(String badge);
    
    /**
     * Returns the Namer being used to make names
     * @return 
     */
    Namer getNamer();
    
    /**
     * Sets the Namer to use
     * @param newNamer 
     */
    void setNamer(Namer newNamer);
    
    /**
     * Gets the default name to use for an ActiveEntity with the given id
     * @param id
     * @return 
     */
    String defaultName(String id);
    
    /**
     * Returns a count of how many ActiveEntities with the given id this Builder has made
     * @param id
     * @return 
     */
    int getEntityCount(String id);
    
    /**
     * Sets the count for the given id to zero.
     * @param id 
     */
    void resetEntityCount(String id);
    
    /**
     * Sets the count for the given id to the given value. No promises if you give it a negative number!
     * @param id
     * @param value 
     */
    void setEntityCount(String id, int value);
    
    /**
     * Returns all the id's this Builder uses
     * @return 
     */
    Set<String> getIds(); 
    
    /**
     * Returns all the badge's this Builder uses
     * @return 
     */
    Set<String> getBadges(); 
    
    /**
     * Returns the Traits that this Builder associates with the given id.
     * @param id
     * @return 
     */
    List<Trait> getTraits(String id);
    
    /**
     * Returns the Tasks that this Builder associates with the given id.
     * @param id
     * @return 
     */
    List<Task> getTasks(String id);
    
    /**
     * Returns the idle text that this Builder associates with the given id.
     * @param id
     * @return 
     */
    String getIdle(String id);
    
    /**
     * Sets the Traits that this Builder associates with the given id.
     * @param id
     * @param newTraits
     */
    void setTraits(String id, List<Trait> newTraits);
    
    /**
     * Sets the Tasks that this Builder associates with the given id.
     * @param id
     * @param newTasks 
     */
    void setTasks(String id, List<Task> newTasks);
    
    /**
     * Sets the idle text that this Builder associates with the given id.
     * @param id
     * @param newIdle
     */
    void setIdle(String id, String newIdle);
    
    /**
     * Returns the most relevant id 
     * @param id
     * @return 
     */
    String parseId(String id);
    
    /**
     * Uses parseId to make a comparator to order a String of sub-ids.
     * 
     * Note: this comparator imposes orderings that are inconsistent with String equals.
     * @return an id comparator
     */
    Comparator<String> idComparator();
    
    /**
     * Get the order that trait types should be displayed
     * @return 
     */
    Trait.trait_type[] getTraitDisplayPriority();
    
    /**
     * Set the order that trait types should be displayed to priorities
     * @param priorities 
     */
    void setTraitDisplayPriority(Trait.trait_type[] priorities);
    
    /**
     * Makes a final list of Traits for all the various sub-ids in the given id. If multiple Traits with the same name
     * are found, it keeps only the ones of whichever sub-id ranks higher through parseId.
     * 
     * For example, if Workers have Cuteness 4 and Soldiers have Cuteness 3, a Worker,Solider will have Cuteness 3
     * @param id
     * @return 
     */
    List<Trait> combineTraits(String id);
    
    /**
     * Makes a final list of Tasks for all the various sub-ids in the given id. If multiple Tasks with the same name
     * are found, it keeps only the ones of whichever sub-id ranks higher through parseId.
     * @param id
     * @return 
     */
    List<Task> combineTasks(String id);
}
