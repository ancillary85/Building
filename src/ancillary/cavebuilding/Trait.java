/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.Arrays;
import java.util.Objects;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author MMLaptop
 */
public class Trait {

    public static enum trait_type{FLAVOR, COMBAT, RESOURCE, PRODUCTION, EACHTURN, MULTI, RESULT, 
                                                PERSONAL_RESOURCE, HIDDEN_RESOURCE}    
    private static final double EPSILON = 0.000001;
    
    private SimpleStringProperty name;
    private SimpleDoubleProperty value;
    private SimpleStringProperty description;
    private trait_type[] types;
    
    public Trait() {
        this.name = new SimpleStringProperty("none");
        this.value = new SimpleDoubleProperty(0.0);
        this.description = new SimpleStringProperty("");        
        this.setUpTypes(null);
    }
    
    public Trait(String name, double value, trait_type singleType) {
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleDoubleProperty(value);    
        this.description = new SimpleStringProperty("");
        this.setUpTypes(new trait_type[]{singleType});
    }
    
    public Trait(String name, double value, String description, trait_type singleType) {
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleDoubleProperty(value);    
        this.description = new SimpleStringProperty(description);
        this.setUpTypes(new trait_type[]{singleType});
    }
    
    public Trait(String name, double value, trait_type[] multiTypes) {
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleDoubleProperty(value);    
        this.description = new SimpleStringProperty("");
        this.setUpTypes(multiTypes);
    }
    
    public Trait(String name, double value, String description, trait_type[] multiTypes) {
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleDoubleProperty(value);    
        this.description = new SimpleStringProperty(description);
        this.setUpTypes(multiTypes);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name.get();
    }
    
    /**
     * @param newName the new name to set 
     */
    public void setName(String newName) {
        name.set(newName);
    }
    
    /**
     * @return the name property
     */
    public SimpleStringProperty getNameProp() {
        return name;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value.get();
    }

    /**
     * @param newValue the value to set
     */
    public void setValue(double newValue) {
        value.set(newValue);
    }

    /**
     * @return the value property
     */
    public SimpleDoubleProperty getValueProp() {
        return value;
    }
    
    public void addToValue(double add) {
        value.set(value.get() + add);
    }
    
    /**
     * @return the description
     */
    public String getDesc() {
        return description.get();
    }

    /**
     * @param newDescription the description to set
     */
    public void setDesc(String newDescription) {
        description.set(newDescription);
    }
    
    /**
     * @return the description property
     */
    public SimpleStringProperty getDescProp() {
        return description;
    }
    
    /**
     * @return the types as an array
     */
    public trait_type[] getTypes() {
        return types;
    }
    
    /**
     * @param multiTypes the type to set
     */
    public void setTypes(trait_type[] multiTypes) {
        if(!validateTypeArray(multiTypes)) {
            this.types = new trait_type[]{trait_type.FLAVOR};
        }
        else {
            this.types = multiTypes;
        }
    }
    
    private boolean validateTypeArray(trait_type[] multiTypes) {
        if(multiTypes == null) {return false;} //is the array null?
        //are any of the array elements null?
        for(trait_type temp : multiTypes) {
            if(temp == null) {return false;}
        }
        
        return true;
    }
    
    private void setUpTypes(trait_type[] multiTypes) {
        if(!validateTypeArray(multiTypes)) {
            this.types = new trait_type[]{trait_type.FLAVOR};
        }
        else {
            this.types = multiTypes;
        }
    }
    
    @Override
    public String toString() {
        return name.get() + ": " + value.get();
    }
    
    @Override
    public boolean equals(Object t) {
        if(t == null) {return false;} //is the other null?
        if(this == t) {return true;} //is the other me?
        if(!(t instanceof Trait)) {return false;} //is it a Trait?
        
        Trait temp = (Trait) t;
        if(!this.getName().equals(temp.getName())) {return false;} //same name?
        if(Math.abs(this.getValue() - temp.getValue()) > EPSILON) {return false;} //same-ish value?
        if(!Arrays.equals(this.getTypes(), temp.getTypes())) {return false;} //same types?
        return this.getDesc().equals(temp.getDesc()); //same description?;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.value);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.types);
        return hash;
    }
    
}
