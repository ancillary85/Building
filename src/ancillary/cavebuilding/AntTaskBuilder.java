/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.Arrays;

/**
 *
 * @author Alecto
 */

//public Task(String Name, int Duration, String[] Costs , String[] Requirements, String[] Results, String Flavor)

public class AntTaskBuilder {
 
    public static Task dig() {
        Task DIG = new Task("Dig", 1, 
            null, 
            null, 
            new String[]{"+1 " + Task.RESOURCE +  " space"}, 
            "Expand the colony");
        return DIG;
    }
    public static Task forage() {
        Task FORAGE = new Task("Forage", 2, 
            null, 
            null, 
            new String[]{"+1 " + Task.RESOURCE +  " food"}, 
            "Search for food");
        return FORAGE;
    }
    public static Task hunt() {
        Task HUNT = new Task("Hunt", 3, 
            new String[]{"-1 " + Task.PERSONAL_RESOURCE + " stamina"}, 
            new String[]{">0 " + Task.RESOURCE + " stamina"}, 
            new String[]{"+2 " + Task.RESOURCE + " food"}, 
            "Hunt for food");
        return HUNT;
    }
    public static Task larvaCare() {
        Task LARVA_CARE = new Task("Tend to Larva", 1, 
            new String[]{"-1 " + Task.RESOURCE + " food", Task.SELF + " = " + AntBuilder.WORKER}, 
            new String[]{">0 " + Task.RESOURCE + " food", ">0 " + Task.ENTITY + " larva"}, 
            new String[]{"+1 " + Task.OTHER_ENTITY_RESOURCE + " larva stamina"}, 
            "Care for growing larval ants");
        return LARVA_CARE;
    }
    public static Task eat() {
        Task EAT = new Task("Eat", 2, 
            new String[]{"-1 " + Task.RESOURCE + " food"}, 
            new String[]{">0 " + Task.RESOURCE + " food"}, 
            new String[]{"+4 " + Task.PERSONAL_RESOURCE + " stamina"}, 
            "Eat some food");
        return EAT;
    }
    public static Task rest() {
        Task REST = new Task("Rest", 1, 
            null, 
            null, 
            new String[]{"+1 " + Task.PERSONAL_RESOURCE + " stamina"}, 
            "Take a rest");
        return REST;
    }
    
    public static Task fight() {
        Task FIGHT = new Task("Fight", 1,
            new String[]{"-2 " + Task.PERSONAL_RESOURCE + " stamina"},
            new String[]{">1 " + Task.PERSONAL_RESOURCE + " stamina"},
            new String[]{"-1 " + Task.RESOURCE + " enemy"},
            "Fight an enemy");
        return FIGHT;
    }
    
    public static Task[] antTasks() {
        return new Task[]{dig(), forage(), hunt(), eat(), rest()};
    }
    
    public static Task[] workerTasks() {
        Task[] workTasks = Arrays.copyOf(antTasks(), antTasks().length + 1);
        workTasks[workTasks.length - 1] = larvaCare();
        return workTasks;
    }
    
    public static Task[] soldierTasks() {
        Task[] soldierTasks = Arrays.copyOf(antTasks(), antTasks().length + 1);
        soldierTasks[soldierTasks.length - 1] = fight();
        return soldierTasks;
    }
}
