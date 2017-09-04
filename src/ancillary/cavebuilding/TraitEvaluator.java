/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.List;

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
                    toAdd.add(new Trait(resourceFromDescription(production), production.getValue(), getRESOURCE_DESC(), Trait.trait_type.RESOURCE));
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
        boolean production = false;
        boolean resource = false;
        boolean result = false;
        
        for(Trait.trait_type foo : t.getTypes()) {
            if(foo == Trait.trait_type.PRODUCTION) {production = true;}
            if(foo == Trait.trait_type.RESOURCE) {resource = true;}
            if(foo == Trait.trait_type.RESULT) {result = true;}
        }
        return production && resource && result;
    }
    
    public static boolean isProductionTrait(Trait t) {
        for(Trait.trait_type foo : t.getTypes()) {
            if(foo == Trait.trait_type.PRODUCTION) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isResourceTrait(Trait t) {
        for(Trait.trait_type foo : t.getTypes()) {
            if(foo == Trait.trait_type.RESOURCE) {
                return true;
            }
        }
        return false;
    }
  
    public static boolean isResultTrait(Trait t) {
        for(Trait.trait_type foo : t.getTypes()) {
            if(foo == Trait.trait_type.RESULT) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isPResourceTrait(Trait t) {
        for(Trait.trait_type foo : t.getTypes()) {
            if(foo == Trait.trait_type.PERSONAL_RESOURCE) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean resourcesMatch(Trait stock, Trait production) {
        return (stock.getName().equals(resourceFromDescription(production)) || stock.getName().equals(production.getName()));
    } 
    
    public static String resourceFromDescription(Trait t) {
        return t.getDesc().substring(0, t.getDesc().indexOf(" "));
    }
}
