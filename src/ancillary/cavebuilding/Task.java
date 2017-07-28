/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.Arrays;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Mike
 */
public class Task {

    public final static String RESOURCE = "r";
    public final static String PERSONAL_RESOURCE = "pr";
    public final static String OTHER_ENTITY_RESOURCE = "oer";
    public final static String ENTITY = "e";
    public final static String STATUS = "s";
    public final static String OTHER_ENTITY_STATUS = "oes";
    public final static String SELF = "self";
    
    private SimpleStringProperty name;
    private SimpleIntegerProperty duration;
    private SimpleStringProperty[] costs;
    private SimpleStringProperty[] requirements;
    private SimpleStringProperty[] results;
    private SimpleStringProperty flavor;
    
    public Task() {
        name = new SimpleStringProperty("no task");
        duration = new SimpleIntegerProperty(0);
        setUpCosts(null);
        setUpRequirements(null);
        setUpResults(null);
        setUpFlavor(null);
    }
    
    public Task(String initName, int initDuration, String[] initCosts , 
       String[] initRequirements, String[] initResults, String initFlavor) {
        name = new SimpleStringProperty(initName);
        duration = new SimpleIntegerProperty(initDuration);
        
         setUpCosts(initCosts);
         setUpRequirements(initRequirements);
         setUpResults(initResults);        
         setUpFlavor(initFlavor);        
    }
    
    public Task(Task t) {
        name = new SimpleStringProperty(t.getName());
        duration = new SimpleIntegerProperty(t.getDuration());
        setUpCosts(t.getCosts());
        setUpRequirements(t.getRequirements());
        setUpResults(t.getResults());
        setUpFlavor(t.getFlavor());
    }
    
    private void setUpCosts(String[] initCosts) {
        if(initCosts == null) {
            costs = new SimpleStringProperty[1];
            costs[0] = new SimpleStringProperty("none");
        }
        else {
            costs = new SimpleStringProperty[initCosts.length];
            for(int i = 0; i < initCosts.length; i++) {
                costs[i] = new SimpleStringProperty(new String(initCosts[i]));
            }
        }
    }
    
    private void setUpRequirements(String[] initRequirements) {
        if(initRequirements == null) {
            requirements = new SimpleStringProperty[1];
            requirements[0] = new SimpleStringProperty("none");
        }
        else {
            requirements = new SimpleStringProperty[initRequirements.length];
            for(int i = 0; i < initRequirements.length; i++) {
                requirements[i] = new SimpleStringProperty(new String(initRequirements[i]));
            }
        }
    }
    
    private void setUpResults(String[] initResults) {
        if(initResults == null) {
            results = new SimpleStringProperty[1];
            results[0] = new SimpleStringProperty("none");
        }
        else {
            results = new SimpleStringProperty[initResults.length];
            for(int i = 0; i < initResults.length; i++) {
                results[i] = new SimpleStringProperty(new String(initResults[i]));
            }
        }
    }
    
    private void setUpFlavor(String initFlavor) {
        if(initFlavor == null) {
            flavor = new SimpleStringProperty("Idle");
        }
        else {
            flavor = new SimpleStringProperty(new String(initFlavor));
        }
    }
    
    public boolean isNoTask() {
        return name.get().equals("no task") && duration.get() < 1;
    }
    
    public void setNoTask() {
        name.set("no task");
        duration.set(0);
    }
    
    @Override
    public String toString() {
        String s = name.get() + ": " + duration.get() + " turns";
        s += ", ";
        
        String[] costArray = new String[costs.length];
        for(int i = 0; i < costs.length; i++) {
            costArray[i] = costs[i].get();
        }
        
        s += String.join(", ", costArray);
        s += "\nRequires: ";
        
        String[] requirementArray = new String[requirements.length];
        for(int i = 0; i < requirements.length; i++) {
            requirementArray[i] = requirements[i].get();
        }
        
        s += String.join(", ", requirementArray);
        s += "\nResults: ";
        
        String[] resultArray = new String[results.length];
        for(int i = 0; i < results.length; i++) {
            resultArray[i] = results[i].get();
        }
        
        s += String.join(", ", resultArray);
        s += "\n";
        s += "\"" + flavor.get() + "\"";
        return s;
    }
    
    @Override
    public boolean equals(Object t) {
        if(t == null) {return false;} //is the other null?
        if(this == t) {return true;} //is the other me?
        if(!(t instanceof Task)) {return false;} //is it a Task?
        
        Task temp = (Task) t;
        if(!this.getName().equals(temp.getName())) {return false;} //same name?
        if(this.getDuration() != temp.getDuration()) {return false;} //same duration?
        if(!Arrays.deepEquals(this.getCosts(), temp.getCosts())) {return false;} //same costs?
        if(!Arrays.deepEquals(this.getRequirements(), temp.getRequirements())) {return false;} //same requirements?
        if(!Arrays.deepEquals(this.getResults(), temp.getResults())) {return false;} //same results?
        return this.getFlavor().equals(temp.getFlavor()); //same flavor?
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.getName());
        hash = 53 * hash + Objects.hashCode(this.getDuration());
        hash = 53 * hash + Arrays.deepHashCode(this.getCosts());
        hash = 53 * hash + Arrays.deepHashCode(this.getRequirements());
        hash = 53 * hash + Arrays.deepHashCode(this.getResults());
        hash = 53 * hash + Objects.hashCode(this.getFlavor());
        return hash;
    }
    
        
    /**
     * @return the name as a String
     */
    public String getName() {
        return name.get();
    }
    
    /**
     * @return the duration as an int
     */
    public int getDuration() {
        return duration.get();
    }
    
    public void setName(String newName) {
        name.set(newName);
    }
    
    public void setDuration(int newDuration) {
        duration.set(newDuration);
    }
    
    /**
     * @return the costs as an array of Strings
     */
    public String[] getCosts() {
        String[] costArray = new String[costs.length];
        
        for(int i = 0; i < costs.length; i++) {
            costArray[i] = costs[i].get();
        }
        
        return costArray;
    }
    
    /**
     * @return the requirements as an array of Strings
     */
    public String[] getRequirements() {
        String[] requirementArray = new String[requirements.length];
        
        for(int i = 0; i < requirements.length; i++) {
            requirementArray[i] = requirements[i].get();
        }
        
        return requirementArray;
    }
    
    /**
     * @return the results as an array of Strings
     */
    public String[] getResults() {
        String[] resultArray = new String[results.length];
        
        for(int i = 0; i < results.length; i++) {
            resultArray[i] = results[i].get();
        }
        
        return resultArray;
    }
    
    /**
     * @return the name property
     */
    public SimpleStringProperty getNameProp() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setNameProp(SimpleStringProperty name) {
        this.name = name;
    }

    /**
     * @return the duration
     */
    public SimpleIntegerProperty getDurationProp() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDurationProp(SimpleIntegerProperty duration) {
        this.duration = duration;
    }

    /**
     * @return the costs
     */
    public SimpleStringProperty[] getCostsProp() {
        return costs;
    }

    /**
     * @param costs the costs to set
     */
    public void setCostsProp(SimpleStringProperty[] costs) {
        this.costs = costs;
    }

    /**
     * @return the requirements
     */
    public SimpleStringProperty[] getRequirementsProp() {
        return requirements;
    }

    /**
     * @param requirements the requirements to set
     */
    public void setRequirementsProp(SimpleStringProperty[] requirements) {
        this.requirements = requirements;
    }

    /**
     * @return the results
     */
    public SimpleStringProperty[] getResultsProp() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResultsProp(SimpleStringProperty[] results) {
        this.results = results;
    }

    /**
     * @return the flavor property
     */
    public SimpleStringProperty getFlavorProp() {
        return flavor;
    }
    
    /**
     * @return the flavor as a String
     */
    public String getFlavor() {
        return flavor.get();
    }

    /**
     * @param flavor the flavor to set
     */
    public void setFlavorProp(SimpleStringProperty flavor) {
        this.flavor = flavor;
    }


}
