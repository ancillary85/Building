/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author MLaptop
 */
public interface Entity {

    /**
     * @return the id
     */
    public String getId();

    /**
     * @param newID the id to set
     */
    public void setId(String newID);
            
    /**
     * @return the Entity's name as a String
     */
    public String getName();
    
    /**
     * @return the Entity's name SimpleStringProperty
     */
    public SimpleStringProperty getNameProp();
    
    /**
     * Sets the Entity's name
     * @param newName 
     */
    public void setName(String newName);
    
    /**
     * @return the traits as a List
     */
    public List<Trait> getTraits();
    
    /**
     * @param newTraits the traits to set
     */
    public void setTraits(List<Trait> newTraits);    
    
    /**
     * Returns true if any of the Strings in this.getId().split(",") equals idFragment.
     * If idFragment contains commas, there's gonna be no luck.
     * @param idFragment 
     * @return 
     */
    public boolean idMatch(String idFragment);

    /**
     * @return the traitDisplayPriority
     */
    public Trait.trait_type[] getTraitDisplayPriority();

    /**
     * @param newDisplayPriority the traitDisplayPriority to set
     */
    public void setTraitDisplayPriority(Trait.trait_type[] newDisplayPriority);
    
    /**
     * Adds the given trait to the Entity. If the Trait List already contains a Trait with the same name, 
     * it adds their values. If it does not have one, it does nothing. 
     * Negative values are allowed.
     * 
     * @param name the name of the Trait
     * @param value the value of the Trait
     */
    public void addTraitValue(String name, int value);
    
    /**
     * Searches the Trait List for a Trait with a matching name, and returns true if found.
     * Returns false, otherwise.
     * @param name
     * @return 
     */
    public boolean hasTrait(String name);
    
    /**
     * Searches the Trait List for a Trait with a matching name, and returns its value if found.
     * Returns zero, otherwise.
     * @param name
     * @return 
     */
    public int getTraitValue(String name);
    
    /**
     * Adds the given trait to the Entity. If the Trait List already contains a Trait with the 
     * same name, it adds their values. If it does not have one, it adds the Trait to the pool.
     * Negative values are allowed.
     * 
     * @param t the Trait
     */
    public void addTrait(Trait t);
    
    /**
     * If the Entity has a predetermined entry in a builder, returns a String to identify it. If the Entity does not
     * have such an entry, this returns null;
     * @return 
     */
    public String getBuilderBadge();
    
    /**
     * Sets the Entity's builderBadge
     * @param newBadge 
     */
    public void setBuilderBadge(String newBadge);
    
}