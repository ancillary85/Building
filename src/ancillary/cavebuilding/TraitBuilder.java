/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

/**
 *
 * @author MMLaptop
 */
public class TraitBuilder {
    
    /**
     * An array with the RESOURCE and HIDDEN_RESOURCE trait types
     */
    public static Trait.trait_type[] resourceHidden = new Trait.trait_type[]{Trait.trait_type.HIDDEN_RESOURCE, Trait.trait_type.RESOURCE};
    
    /**
     * An array with the PERSONAL_RESOURCE and RESULT trait types
     */
    public static Trait.trait_type[] personalResourceResult = new Trait.trait_type[]{Trait.trait_type.PERSONAL_RESOURCE, Trait.trait_type.RESULT};
    
    /**
     * An array with the RESOURCE, RESULT, and PRODUCTION trait types
     */
    public static Trait.trait_type[] resourceProductionResult = new Trait.trait_type[]{Trait.trait_type.RESOURCE, Trait.trait_type.RESULT, Trait.trait_type.PRODUCTION};
    
    /**
     * An array with the PERSONAL_RESOURCE and HIDDEN_RESOURCE trait types
     */
    public static Trait.trait_type[] personalHiddenResource = new Trait.trait_type[]{Trait.trait_type.PERSONAL_RESOURCE, Trait.trait_type.HIDDEN_RESOURCE};
    
    /**
     * An array with the PERSONAL_RESOURCE, HIDDEN_RESOURCE, and RESULT trait types
     */
    public static Trait.trait_type[] personalHiddenResourceResult = new Trait.trait_type[]{Trait.trait_type.PERSONAL_RESOURCE, 
            Trait.trait_type.HIDDEN_RESOURCE, Trait.trait_type.RESULT};
}
