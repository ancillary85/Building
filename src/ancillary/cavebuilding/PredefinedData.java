/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author MMLaptop
 */
public class PredefinedData {
    private HashMap<String, ArrayList<Trait>> defaultTraits;
    private HashMap<String, ArrayList<Task>> defaultTasks;
    
    public PredefinedData() {
        defaultTraits = new HashMap();
        defaultTasks = new HashMap();
    }
    
    /**
     * @return the defaultTraits
     */
    public HashMap<String, ArrayList<Trait>> getDefaultTraits() {
        return defaultTraits;
    }
    
    /**
     * Asks the defaultTraits HashMap to return the ArrayList of Traits that matches the key traitListName
     * @param traitListName
     * @return null if nothing in the map
     */
    public ArrayList<Trait> getThisTraitList(String traitListName) {
        return defaultTraits.get(traitListName);
    }

    /**
     * Gets an ArrayList from the HashMap, and then returns the given index
     * @param traitListName
     * @param idnum
     * @return null if nothing in the map
     */
    public Trait getTraitByNum(String traitListName, int idnum) {
        ArrayList<Trait> temp = defaultTraits.get(traitListName);
        if(temp == null || idnum < 0 || idnum >= temp.size()) {
            return null;
        }
        
        return temp.get(idnum);
    }
    
    public void addDefaultTraits(String listName, ArrayList<Trait> traitsList) {
        defaultTraits.put(listName, traitsList);
    }
    
    /**
     * @param defaultTraits the defaultTraits to set
     */
    public void setDefaultTraits(HashMap<String, ArrayList<Trait>> defaultTraits) {
        this.defaultTraits = defaultTraits;
    }

    /**
     * @return the defaultTasks
     */
    public HashMap<String, ArrayList<Task>> getDefaultTasks() {
        return defaultTasks;
    }

    /**
     * Asks the defaultTasks HashMap to return the ArrayList of Tasks that matches the key taskListName
     * @param taskListName
     * @return null if nothing in the map
     */
    public ArrayList<Task> getThisTaskList(String taskListName) {
        return defaultTasks.get(taskListName);
    }

    /**
     * Gets an ArrayList from the HashMap, and then returns the given index
     * @param taskListName
     * @param idnum
     * @return null if nothing in the map
     */
    public Task getTaskByNum(String taskListName, int idnum) {
        ArrayList<Task> temp = defaultTasks.get(taskListName);
        if(temp == null) {
            return null;
        }
        
        return temp.get(idnum);
    }
    
    public void addDefaultTasks(String listName, ArrayList<Task> tasksList) {
        defaultTasks.put(listName, tasksList);
    }
    
    /**
     * @param defaultTasks the defaultTasks to set
     */
    public void setDefaultTasks(HashMap<String, ArrayList<Task>> defaultTasks) {
        this.defaultTasks = defaultTasks;
    }
}
