/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author MLaptop
 */
public class InactiveEntity implements Entity {

    /**
     * The Entity's id
     */
    protected String id = "default";
    
    /**
     * The Entity's name, to be displayed to the player
     */
    protected SimpleStringProperty name = new SimpleStringProperty("nothing");
    
    /**
     * The Entity's traits
     */
    protected ArrayList<Trait> traits = new ArrayList();
    
    /**
     * The Entity's builderBadge, for looking it up in a Builder. This is null if there is no predefined entry for the Entity
     */
    protected String builderBadge = null;
    
    protected Trait.trait_type[] traitDisplayPriority = {Trait.trait_type.ATTRIBUTE, Trait.trait_type.PRODUCTION, Trait.trait_type.COMBAT, Trait.trait_type.FLAVOR};
    
    
    public InactiveEntity() {
        
    }
    
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String newID) {
        id = newID;
    }

    @Override
    public String getName() {
        return name.get();
    }

    @Override
    public SimpleStringProperty getNameProp() {
        return name;
    }

    @Override
    public void setName(String newName) {
        name.set(newName);
    }

    @Override
    public List<Trait> getTraits() {
        return traits;
    }

    @Override
    public void setTraits(List<Trait> newTraits) {
        traits = new ArrayList(newTraits);
    }

    @Override
    public boolean idMatch(String idFragment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Trait.trait_type[] getTraitDisplayPriority() {
        return traitDisplayPriority;
    }

    @Override
    public void setTraitDisplayPriority(Trait.trait_type[] newDisplayPriority) {
        traitDisplayPriority = newDisplayPriority;
    }

    @Override
    public void addTraitValue(String name, int value) {
        for(Trait res : traits) {
            if(res.getName().equals(name)) {
                res.addToValue(value);
                return; //return if we found the trait in the trait list
            }
        }
    }

    @Override
    public boolean hasTrait(String name) {
        for(Trait t : traits) {
            if(t.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getTraitValue(String name) {
        for(Trait t : traits) {
            if(t.getName().equalsIgnoreCase(name)) {
                return t.getValue();
            }
        }
        return 0;
    }

    @Override
    public void addTrait(Trait t) {
        for(Trait res : traits) {
            if(res.getName().equals(t.getName())) {
                res.addToValue(t.getValue());
                return; //return if we found the resource in the pool
            }
        }
        
        traits.add(new Trait(t));
    }    
    
    @Override
    public String getBuilderBadge() {
        return builderBadge;
    }
    
    @Override
    public void setBuilderBadge(String newBadge) {
        builderBadge = newBadge;
    }
}
