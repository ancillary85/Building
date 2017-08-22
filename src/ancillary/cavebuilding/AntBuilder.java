/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Alecto
 */
public class AntBuilder {
    public final static String ANT = "ant";
    public final static String WORKER = "worker";
    public final static String SOLDIER = "soldier";
    public final static String DRONE = "drone";
    public final static String QUEEN = "queen";
    public final static String EGG = "egg";
    public final static String LARVA = "larva";
    public final static String PUPA = "pupa";
    public final static String WORKER_IDLE = "Just a cog in the machine.";
    public final static String SOLDIER_IDLE = "Semper Fi!";
    public final static String DRONE_IDLE = "How ya doing, baby?";
    public final static String QUEEN_IDLE = "Let them eat cake";
    public final static String EGG_IDLE = "Don't put in baskets.";
    public final static String LARVA_IDLE = "Om nom nom!";
    public final static String PUPA_IDLE = "Zzzzz.";
    
    
     //  public ActiveEntity(String id, String name, String location, List<Task> tasks)
    public final static ActiveEntity makeWorker(String name, String location) {
        
        if(name == null || name.equals("")) {name = "Random Worker";}
        if(location == null || location.equals("")) {location = "somewhere";}
        List<Task> tasks = Arrays.asList(AntTaskBuilder.workerTasks());
        ActiveEntity w = new ActiveEntity(WORKER + " " + ANT, name, location, tasks, WORKER_IDLE);
        return w;
    }
    
    public final static ActiveEntity makeSoldier(String name, String location) {
        
        if(name == null || name.equals("")) {name = "Random Soldier";}
        if(location == null || location.equals("")) {location = "somewhere";}
        List<Task> tasks = Arrays.asList(AntTaskBuilder.soldierTasks());
        ActiveEntity s = new ActiveEntity(SOLDIER + " " + ANT, name, location, tasks, SOLDIER_IDLE);
        return s;
    }
}