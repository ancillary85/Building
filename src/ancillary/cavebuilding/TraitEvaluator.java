/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collector;

/**
 *
 * @author MMLaptop
 */
public class TraitEvaluator {

    private String RESOURCE_DESC = "";
    
    
    
    
    /**
     * @return the RESOURCE_DESC
     */
    public String getRESOURCE_DESC() {
        return RESOURCE_DESC;
    }

    /**
     * @param RESOURCE_DESC the RESOURCE_DESC to set
     */
    public void setRESOURCE_DESC(String RESOURCE_DESC) {
        this.RESOURCE_DESC = RESOURCE_DESC;
    }
    
    /**
     * Update the given List of resources, represented as Traits, by evaluating the resource production Traits 
     * of the given ActiveEntity
     * 
     * Presently, only one resource per Trait is allowed, so no Mining: +2 ore, +1 gold
     * 
     * @param global_resources the List of resources to be adjusted
     * @param dude the ActiveEntity whose Traits will be used
     */
    public void resourcesFromTraits(List<Trait> global_resources, ActiveEntity dude) {
        
        Trait[] dudeTraits = dude.getTraits().stream().filter(t -> validResourceTrait(t)).toArray(Trait[]::new);
        ArrayList<Trait> toAdd = new ArrayList();
        
        for(Trait production : dudeTraits) {
            boolean alreadyInGlobalResources = false;
    
            for(Trait stock : global_resources) {
            
                if(resourcesMatch(stock, production)) {
                    stock.addToValue(production.getValue());
                    alreadyInGlobalResources = true;
                    break; //stop looking once we've found one
                }
            }
            
            //If we never found it in global_resources, be ready to add it in after we're done iterating
            if(!alreadyInGlobalResources) {
                EnumSet<Trait.trait_type> traitTypes = EnumSet.of(Trait.trait_type.RESOURCE);
                toAdd.add(new Trait(resourceFromDescription(production), production.getValue(), getRESOURCE_DESC(), traitTypes));
            }
        }
        
        //Put in all the resources that were not in the global pool
        global_resources.addAll(toAdd);
    }
    
    /**
     * Update the given List of resources, represented as Traits, by evaluating the resource production Traits 
     * of all the given ActiveEntities. This calls resourcesFromTraits(global_resources, e) for each ActiveEntity in dudes.
     * @param global_resources the List of resources to be adjusted
     * @param dudes the ActiveEntities whose Traits will be used
     */
    public void resourcesFromGroup(List<Trait> global_resources, List<ActiveEntity> dudes) {
        for(ActiveEntity e : dudes) {
            resourcesFromTraits(global_resources, e);
        }
    }
    
    public static boolean validResourceTrait(Trait t)  {
        boolean production = t.getTypes().contains(Trait.trait_type.PRODUCTION);
        boolean resource = t.getTypes().contains(Trait.trait_type.RESOURCE);
        boolean result = t.getTypes().contains(Trait.trait_type.RESULT);
        
        return production && resource && result;
    }
    
    public static Trait.trait_type highestPriorityType(Trait t, Trait.trait_type[] priorities) {
        for(Trait.trait_type pType : priorities) {
            if(isTraitType(t, pType)) {
                return pType;
            }
        }
        
        return null;
    }
    
    public static String parseHighestTraitTypeString(Trait currentTrait, Trait.trait_type[] priorities) {
        if(currentTrait.getTypes().size() < 1) {
            return TraitEvaluator.groupString(Trait.trait_type.GENERAL);
        }
        
        for(Trait.trait_type type: priorities) {
            if(currentTrait.getTypes().contains(type)) {
                return TraitEvaluator.groupString(type);
            }
        }
        
        return TraitEvaluator.groupString(Trait.trait_type.GENERAL);
    }
    
    public static String groupString(Trait.trait_type t) {
        switch(t) {
            case FLAVOR: return "Flavor";
            case COMBAT: return "Combat";
            case RESOURCE: return "Resources";
            case PRODUCTION: return "Production";
            case ATTRIBUTE: return "Attributes";
            case PERSONAL_RESOURCE: return "Personal Resources";
            case GENERAL: return "General";
            default: return "General";
        }
    }
    
    public static boolean isTraitType(Trait t, Trait.trait_type type) {
        return t.getTypes().contains(type);
    }
    
    public static boolean isCreationTrait(Trait t) {
        return t.getTypes().contains(Trait.trait_type.CREATION);
    }
    
    public static boolean isCreationLinkTrait(Trait t) {
        return t.getTypes().contains(Trait.trait_type.CREATION_LINK);
    }
    
    public static boolean isUncreateTrait(Trait t) {
        return t.getTypes().contains(Trait.trait_type.UNCREATE);
    }
    
    public static boolean isAttributeTrait(Trait t) {
        return t.getTypes().contains(Trait.trait_type.ATTRIBUTE);
    }
    
    public static boolean isProductionTrait(Trait t) {
        return t.getTypes().contains(Trait.trait_type.PRODUCTION);
    }
    
    public static boolean isResourceTrait(Trait t) {
        return t.getTypes().contains(Trait.trait_type.RESOURCE);
    }
  
    public static boolean isResultTrait(Trait t) {
        return t.getTypes().contains(Trait.trait_type.RESULT);
    }
    
    public static boolean isPResourceTrait(Trait t) {
        return t.getTypes().contains(Trait.trait_type.PERSONAL_RESOURCE);
    }
    
    public static boolean isHiddenResource(Trait t) {
        return t.getTypes().contains(Trait.trait_type.HIDDEN_RESOURCE);
    }
    
    public static boolean isRoomTrait(Trait t) {
        return t.getTypes().contains(Trait.trait_type.ROOM);
    }
    
    public static boolean isRequirement(Trait t) {
        return t.getTypes().contains(Trait.trait_type.REQUIREMENT);
    }
    
    /**
     * RETURNS NULL IF INVALID
     * @param t
     * @return 
     */
    public static Trait.trait_type requirementCondition(Trait t) {
        
        if(t.getTypes().contains(Trait.trait_type.EQUALTO)) {return Trait.trait_type.EQUALTO;}
        if(t.getTypes().contains(Trait.trait_type.NOTEQUAL)) {return Trait.trait_type.NOTEQUAL;}
        if(t.getTypes().contains(Trait.trait_type.LESSTHAN)) {return Trait.trait_type.LESSTHAN;}
        if(t.getTypes().contains(Trait.trait_type.GREATERTHAN)) {return Trait.trait_type.GREATERTHAN;}
        
        return null;
    }
    
    /**
     * RETURNS NULL IF INVALID
     * @param t
     * @return 
     */
    public static Trait.trait_type requirementType(Trait t) {
        
        if(t.getTypes().contains(Trait.trait_type.RESOURCE)) {return Trait.trait_type.RESOURCE;}
        if(t.getTypes().contains(Trait.trait_type.CREATION)) {return Trait.trait_type.CREATION;}
        if(t.getTypes().contains(Trait.trait_type.PERSONAL_RESOURCE)) {return Trait.trait_type.PERSONAL_RESOURCE;}
        if(t.getTypes().contains(Trait.trait_type.CREATION_LINK)) {return Trait.trait_type.CREATION_LINK;}
        if(t.getTypes().contains(Trait.trait_type.ROOM)) {return Trait.trait_type.ROOM;}
        
        return null;
    }
    
    public static boolean resourcesMatch(Trait stock, Trait production) {
        return (stock.getName().equalsIgnoreCase(production.getName()) || stock.getName().equalsIgnoreCase(resourceFromDescription(production)));
    } 
    
    public static String resourceFromDescription(Trait t) {
        return t.getDesc().substring(0, t.getDesc().indexOf(" "));
    }
    
    
    /**
     * Uses an array of Trait types to decide how to order things. Each trait type in the array it given priority over all trait 
     * types in later positions. If a trait type is not given a priority,it is treated as lowest. Within a given trait type, they are ordered
     * by their sortingPriority.
     * 
     * Note: this comparator imposes orderings that are inconsistent with equals.
     * @param priorities
     * @return a Trait comparator
     */
    public static Comparator<Trait> traitComparator(Trait.trait_type[] priorities) {
        return new Comparator<Trait>() {
            @Override
            public int compare(Trait t1, Trait t2) {
                for(int i = 0; i < priorities.length; i++) {
                    if(t1.getTypes().contains(priorities[i])) {
                        if(t2.getTypes().contains(priorities[i])) {
                            return t1.getSortingPriority() - t2.getSortingPriority();
                        }
                        else {
                            return -1;
                        }
                    }
                    else if(t2.getTypes().contains(priorities[i])) {
                        return 1;
                    }
                }
                
                return t1.getSortingPriority() - t2.getSortingPriority();
            }
        };
    }
}
