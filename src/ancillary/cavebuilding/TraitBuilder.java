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
 * @author MMLaptop
 */
public class TraitBuilder {
    
    /**
     * An array with the RESOURCE and HIDDEN_RESOURCE trait types
     * @return 
     */
    private static Trait.trait_type[] resourceHiddenArray() {
        return new Trait.trait_type[]{Trait.trait_type.HIDDEN_RESOURCE, Trait.trait_type.RESOURCE};
    }
    
    /**
     * An array with the PERSONAL_RESOURCE and RESULT trait types
     * @return 
     */
    private static Trait.trait_type[] personalResourceResultArray() {
        return new Trait.trait_type[]{Trait.trait_type.PERSONAL_RESOURCE, Trait.trait_type.RESULT};
    }
    
    /**
     * An array with the RESOURCE, RESULT, and PRODUCTION trait types
     * @return 
     */
    private static Trait.trait_type[] resourceProductionResultArray() {
        return new Trait.trait_type[]{Trait.trait_type.RESOURCE, Trait.trait_type.RESULT, Trait.trait_type.PRODUCTION};
    }
    
    /**
     * An array with the PERSONAL_RESOURCE and HIDDEN_RESOURCE trait types
     * @return 
     */
    private static Trait.trait_type[] personalHiddenResourceArray() {
        return new Trait.trait_type[]{Trait.trait_type.PERSONAL_RESOURCE, Trait.trait_type.HIDDEN_RESOURCE};
    }
    
    /**
     * An array with the PERSONAL_RESOURCE, HIDDEN_RESOURCE, and RESULT trait types
     * @return 
     */
    private static Trait.trait_type[] personalHiddenResourceResultArray() {
        return new Trait.trait_type[]{Trait.trait_type.PERSONAL_RESOURCE, Trait.trait_type.HIDDEN_RESOURCE, Trait.trait_type.RESULT};
    }
    
    /**
     * An array with the CREATION and RESULT trait types
     * @return 
     */
    private static Trait.trait_type[] creationResultArray() {
        return new Trait.trait_type[]{Trait.trait_type.CREATION, Trait.trait_type.RESULT};
    }
    
    /**
     * An array with the REQUIREMENT and RESOURCE trait types
     * @return 
     */
    private static Trait.trait_type[] reqResourceArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.RESOURCE};
    }
    
    /**
     * An array with the REQUIREMENT, EQUALTO, and RESOURCE trait types
     * @return 
     */
    private static Trait.trait_type[] reqEqualResourceArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.RESOURCE, Trait.trait_type.EQUALTO};
    }
    
    /**
     * An array with the REQUIREMENT, NOTEQUAL, and RESOURCE trait types
     * @return 
     */
    private static Trait.trait_type[] reqNotEqualResourceArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.RESOURCE, Trait.trait_type.NOTEQUAL};
    }
    
    /**
     * An array with the REQUIREMENT, LESSTHAN, and RESOURCE trait types
     * @return 
     */
    private static Trait.trait_type[] reqLessThanResourceArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.RESOURCE, Trait.trait_type.LESSTHAN};
    }
    
    /**
     * An array with the REQUIREMENT, GREATERTHAN, and RESOURCE trait types
     * @return 
     */
    private static Trait.trait_type[] reqGreaterThanResourceArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.RESOURCE, Trait.trait_type.GREATERTHAN};
    }
    
    /**
     * An array with the REQUIREMENT and CREATION trait types
     * @return 
     */
    private static Trait.trait_type[] reqCreationArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.CREATION};
    }
    
     /**
     * An array with the REQUIREMENT, EQUALTO, and CREATION trait types
     * @return 
     */
    private static Trait.trait_type[] reqEqualCreationArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.CREATION, Trait.trait_type.EQUALTO};
    }
    
    /**
     * An array with the REQUIREMENT, NOTEQUAL, and CREATION trait types
     * @return 
     */
    private static Trait.trait_type[] reqNotEqualCreationArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.CREATION, Trait.trait_type.NOTEQUAL};
    }
    
    /**
     * An array with the REQUIREMENT, LESSTHAN, and CREATION trait types
     * @return 
     */
    private static Trait.trait_type[] reqLessThanCreationArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.CREATION, Trait.trait_type.LESSTHAN};
    }
    
    /**
     * An array with the REQUIREMENT, GREATERTHAN, and CREATION trait types
     * @return 
     */
    private static Trait.trait_type[] reqGreaterThanCreationArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.CREATION, Trait.trait_type.GREATERTHAN};
    }
    
    /**
     * An array with the REQUIREMENT and PERSONAL_RESOURCE trait types
     * @return 
     */
    private static Trait.trait_type[] reqPersonalArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.PERSONAL_RESOURCE};
    }
    
     /**
     * An array with the REQUIREMENT, EQUALTO, and PERSONAL_RESOURCE trait types
     * @return 
     */
    private static Trait.trait_type[] reqEqualPersonalArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.PERSONAL_RESOURCE, Trait.trait_type.EQUALTO};
    }
    
    /**
     * An array with the REQUIREMENT, NOTEQUAL, and PERSONAL_RESOURCE trait types
     * @return 
     */
    private static Trait.trait_type[] reqNotEqualPersonalArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.PERSONAL_RESOURCE, Trait.trait_type.NOTEQUAL};
    }
    
    /**
     * An array with the REQUIREMENT, LESSTHAN, and PERSONAL_RESOURCE trait types
     * @return 
     */
    private static Trait.trait_type[] reqLessThanPersonalArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.PERSONAL_RESOURCE, Trait.trait_type.LESSTHAN};
    }
    
    /**
     * An array with the REQUIREMENT, GREATERTHAN, and PERSONAL_RESOURCE trait types
     * @return 
     */
    private static Trait.trait_type[] reqGreaterThanPersonalArray() {
        return new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.PERSONAL_RESOURCE, Trait.trait_type.GREATERTHAN};
    }
    
    public static EnumSet<Trait.trait_type> arrayToSet(Trait.trait_type[] input) {
        return EnumSet.copyOf(Arrays.asList(input));
    }
    
    /**
     * A set with the RESOURCE and HIDDEN_RESOURCE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> resourceHidden() {
        return arrayToSet(resourceHiddenArray());
    }
    
    /**
     * A set with the PERSONAL_RESOURCE and RESULT trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> personalResourceResult() {
        return arrayToSet(personalResourceResultArray());
    }
    
    /**
     * A set with the RESOURCE and PRODUCTION trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> resourceProduction() {
        return EnumSet.of(Trait.trait_type.RESOURCE, Trait.trait_type.PRODUCTION);
    }
    
    /**
     * A set with the RESOURCE, RESULT, and PRODUCTION trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> resourceProductionResult() {
        return arrayToSet(resourceProductionResultArray());
    }
    
    /**
     * A set with the PERSONAL_RESOURCE and HIDDEN_RESOURCE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> personalHiddenResource() {
        return arrayToSet(personalHiddenResourceArray());
    }
    
    /**
     * A set with the PERSONAL_RESOURCE, HIDDEN_RESOURCE, and RESULT trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> personalHiddenResourceResult() {
        return arrayToSet(personalHiddenResourceResultArray());
    }
    
    /**
     * A set with the CREATION and RESULT trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> creationResult() {
        return arrayToSet(creationResultArray());
    }
    
    /**
     * A set with the UNCREATE and RESULT trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> uncreateResult() {
        return EnumSet.of(Trait.trait_type.UNCREATE, Trait.trait_type.RESULT);
    }
    
    /**
     * A set with the AUTOTASK and EACHTURN trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> autoEachTurn() {
        return EnumSet.of(Trait.trait_type.AUTOTASK, Trait.trait_type.EACHTURN);
    }
    
    /**
     * A set with the AUTOTASK and ACTIVE_CHANGE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> autoActiveChange() {
        return EnumSet.of(Trait.trait_type.AUTOTASK, Trait.trait_type.ACTIVE_CHANGE);
    }
    
    /**
     * A set with the AUTOTASK, ACTIVE_CHANGE, and UNCANCELABLE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> autoActiveChangeUncan() {
        return EnumSet.of(Trait.trait_type.AUTOTASK, Trait.trait_type.ACTIVE_CHANGE, Trait.trait_type.UNCANCELABLE);
    }
    
    /**
     * A set with the REQUIREMENT and RESOURCE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqResource() {
        return arrayToSet(reqResourceArray());
    }
    
    /**
     * A set with the REQUIREMENT, EQUALTO, and RESOURCE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqEqualResource() {
        return arrayToSet(reqEqualResourceArray());
    }
    
    /**
     * A set with the REQUIREMENT, NOTEQUAL, and RESOURCE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqNotEqualResource() {
            return arrayToSet(reqNotEqualResourceArray());
    }
    
    /**
     * A set with the REQUIREMENT, LESSTHAN, and RESOURCE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqLessThanResource() {
        return arrayToSet(reqLessThanResourceArray());
    } 
    
    /**
     * A set with the REQUIREMENT, GREATERTHAN, and RESOURCE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqGreaterThanResource() {
        return arrayToSet(reqGreaterThanResourceArray());
    }
    
    /**
     * A set with the REQUIREMENT and CREATION trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqCreation() {
        return arrayToSet(reqCreationArray());
    }
    
     /**
     * A set with the REQUIREMENT, EQUALTO, and CREATION trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqEqualCreation() {
        return arrayToSet(reqEqualCreationArray());
    }
    
    /**
     * A set with the REQUIREMENT, NOTEQUAL, and CREATION trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqNotEqualCreation() {
        return arrayToSet(reqNotEqualCreationArray());
    }
    
    /**
     * A set with the REQUIREMENT, LESSTHAN, and CREATION trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqLessThanCreation() {
        return arrayToSet(reqLessThanCreationArray());
    }
    
    /**
     * A set with the REQUIREMENT, GREATERTHAN, and CREATION trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqGreaterThanCreation() {
       return arrayToSet(reqGreaterThanCreationArray());
    }
    
    /**
     * A set with the REQUIREMENT and CREATION_LINK trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqCreationLink() {
        return arrayToSet(new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.CREATION_LINK});
    }
    
     /**
     * A set with the REQUIREMENT, EQUALTO, and CREATION_LINK trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqEqualCreationLink() {
        return arrayToSet(new Trait.trait_type[]{Trait.trait_type.EQUALTO, Trait.trait_type.REQUIREMENT, Trait.trait_type.CREATION_LINK});
    }
    
    /**
     * A set with the REQUIREMENT, NOTEQUAL, and CREATION_LINK trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqNotEqualCreationLink() {
        return arrayToSet(new Trait.trait_type[]{Trait.trait_type.NOTEQUAL, Trait.trait_type.REQUIREMENT, Trait.trait_type.CREATION_LINK});
    }
    
    /**
     * A set with the REQUIREMENT, LESSTHAN, and CREATION_LINK trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqLessThanCreationLink() {
        return arrayToSet(new Trait.trait_type[]{Trait.trait_type.LESSTHAN, Trait.trait_type.REQUIREMENT, Trait.trait_type.CREATION_LINK});
    }
    
    /**
     * A set with the REQUIREMENT, GREATERTHAN, and CREATION_LINK trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqGreaterThanCreationLink() {
       return arrayToSet(new Trait.trait_type[]{Trait.trait_type.GREATERTHAN, Trait.trait_type.REQUIREMENT, Trait.trait_type.CREATION_LINK});
    }
    
    /**
     * A set with the REQUIREMENT and PERSONAL_RESOURCE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqPersonal() {
        return arrayToSet(reqPersonalArray());
    }
    
     /**
     * A set with the REQUIREMENT, EQUALTO, and PERSONAL_RESOURCE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqEqualPersonal() {
        return arrayToSet(reqEqualPersonalArray());
    }
    
    /**
     * A set with the REQUIREMENT, NOTEQUAL, and PERSONAL_RESOURCE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqNotEqualPersonal() {
        return arrayToSet(reqNotEqualPersonalArray());
    }
    
    /**
     * An array with the REQUIREMENT, LESSTHAN, and PERSONAL_RESOURCE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqLessThanPersonal() {
        return arrayToSet(reqLessThanPersonalArray());
    }
    
    /**
     * An array with the REQUIREMENT, GREATERTHAN, and PERSONAL_RESOURCE trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqGreaterThanPersonal() {
        return arrayToSet(reqGreaterThanPersonalArray());
    }
   
    /**
     * A set with the REQUIREMENT and ROOM trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqRoom() {
        return arrayToSet(new Trait.trait_type[]{Trait.trait_type.REQUIREMENT, Trait.trait_type.ROOM});
    }
    
     /**
     * A set with the REQUIREMENT, EQUALTO, and ROOM trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqEqualRoom() {
        return arrayToSet(new Trait.trait_type[]{Trait.trait_type.EQUALTO, Trait.trait_type.REQUIREMENT, Trait.trait_type.ROOM});
    }
    
    /**
     * A set with the REQUIREMENT, NOTEQUAL, and ROOM trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqNotEqualRoom() {
        return arrayToSet(new Trait.trait_type[]{Trait.trait_type.NOTEQUAL, Trait.trait_type.REQUIREMENT, Trait.trait_type.ROOM});
    }
    
    /**
     * A set with the REQUIREMENT, LESSTHAN, and ROOM trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqLessThanRoom() {
        return arrayToSet(new Trait.trait_type[]{Trait.trait_type.LESSTHAN, Trait.trait_type.REQUIREMENT, Trait.trait_type.ROOM});
    }
    
    /**
     * A set with the REQUIREMENT, GREATERTHAN, and ROOM trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqGreaterThanRoom() {
       return arrayToSet(new Trait.trait_type[]{Trait.trait_type.GREATERTHAN, Trait.trait_type.REQUIREMENT, Trait.trait_type.ROOM});
    }
    
    /**
     * A set with the REQUIREMENT, EQUALTO, and ROOM_LINK trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqEqualRoomLink() {
        return arrayToSet(new Trait.trait_type[]{Trait.trait_type.EQUALTO, Trait.trait_type.REQUIREMENT, Trait.trait_type.ROOM_LINK});
    }
    
    /**
     * A set with the REQUIREMENT, NOTEQUAL, and ROOM_LINK trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqNotEqualRoomLink() {
        return arrayToSet(new Trait.trait_type[]{Trait.trait_type.NOTEQUAL, Trait.trait_type.REQUIREMENT, Trait.trait_type.ROOM_LINK});
    }
    
    /**
     * A set with the REQUIREMENT, LESSTHAN, and ROOM_LINK trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqLessThanRoomLink() {
        return arrayToSet(new Trait.trait_type[]{Trait.trait_type.LESSTHAN, Trait.trait_type.REQUIREMENT, Trait.trait_type.ROOM_LINK});
    }
    
    /**
     * A set with the REQUIREMENT, GREATERTHAN, and ROOM_LINK trait types
     * @return 
     */
    public static EnumSet<Trait.trait_type> reqGreaterThanRoomLink() {
       return arrayToSet(new Trait.trait_type[]{Trait.trait_type.GREATERTHAN, Trait.trait_type.REQUIREMENT, Trait.trait_type.ROOM_LINK});
    }
    
    public static EnumSet<Trait.trait_type> attribute() {
        return EnumSet.of(Trait.trait_type.ATTRIBUTE);
    } 
    
    public static EnumSet<Trait.trait_type> active_change() {
        return EnumSet.of(Trait.trait_type.ACTIVE_CHANGE);
    } 
    
    public static EnumSet<Trait.trait_type> autotask() {
        return EnumSet.of(Trait.trait_type.AUTOTASK);
    } 
    
    public static EnumSet<Trait.trait_type> combat() {
        return EnumSet.of(Trait.trait_type.COMBAT);
    }
    
    public static EnumSet<Trait.trait_type> creation() {
        return EnumSet.of(Trait.trait_type.CREATION);
    }
    
    public static EnumSet<Trait.trait_type> creation_link() {
        return EnumSet.of(Trait.trait_type.CREATION_LINK);
    }
    
    public static EnumSet<Trait.trait_type> eachturn() {
        return EnumSet.of(Trait.trait_type.EACHTURN);
    }
    
    public static EnumSet<Trait.trait_type> equalto() {
        return EnumSet.of(Trait.trait_type.EQUALTO);
    }
    
    public static EnumSet<Trait.trait_type> event() {
        return EnumSet.of(Trait.trait_type.EVENT);
    }
    
    public static EnumSet<Trait.trait_type> flavor() {
        return EnumSet.of(Trait.trait_type.FLAVOR);
    }
    
    public static EnumSet<Trait.trait_type> greaterthan() {
        return EnumSet.of(Trait.trait_type.GREATERTHAN);
    }
    
    public static EnumSet<Trait.trait_type> hidden_resource() {
        return EnumSet.of(Trait.trait_type.HIDDEN_RESOURCE);
    }
    
    public static EnumSet<Trait.trait_type> lessthan() {
        return EnumSet.of(Trait.trait_type.LESSTHAN);
    }
    
    public static EnumSet<Trait.trait_type> multi() {
        return EnumSet.of(Trait.trait_type.MULTI);
    }
    
    public static EnumSet<Trait.trait_type> notequal() {
        return EnumSet.of(Trait.trait_type.NOTEQUAL);
    }
    
    public static EnumSet<Trait.trait_type> personal_resource() {
        return EnumSet.of(Trait.trait_type.PERSONAL_RESOURCE);
    }
    
    public static EnumSet<Trait.trait_type> production() {
        return EnumSet.of(Trait.trait_type.PRODUCTION);
    }
    
    public static EnumSet<Trait.trait_type> requirement() {
        return EnumSet.of(Trait.trait_type.REQUIREMENT);
    }
    
    public static EnumSet<Trait.trait_type> resource() {
        return EnumSet.of(Trait.trait_type.RESOURCE);
    }
    
    public static EnumSet<Trait.trait_type> result() {
        return EnumSet.of(Trait.trait_type.RESULT);
    }
    
    public static EnumSet<Trait.trait_type> room() {
        return EnumSet.of(Trait.trait_type.ROOM);
    }
    
    public static EnumSet<Trait.trait_type> room_link() {
        return EnumSet.of(Trait.trait_type.ROOM_LINK);
    }
    
    public static EnumSet<Trait.trait_type> room_change() {
        return EnumSet.of(Trait.trait_type.ROOM_CHANGE);
    }
    
    public static EnumSet<Trait.trait_type> uncreate() {
        return EnumSet.of(Trait.trait_type.UNCREATE);
    }
}
