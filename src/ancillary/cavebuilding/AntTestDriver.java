/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;

/**
 *
 * @author Mike
 */
public class AntTestDriver {
    private static ArrayList<ActiveEntity> ants;
    private static AntHillEngine choochoo;
    
    public static void main(String[] args) {
        ants = new ArrayList();
        ants.add(AntBuilder.makeSoldier("Bob", null));
        ants.add(AntBuilder.makeSoldier("Rambo", null));
        ants.add(AntBuilder.makeWorker("Alice", null));
        ants.add(AntBuilder.makeWorker("Beatrice", null));
        
        choochoo = new AntHillEngine(null, ants);
        
        antTaskResultTest();
    }
    
    public static void printAnts() {
        for(ActiveEntity bug : choochoo.getAnts()) {
            System.out.println(choochoo.antToString(bug) + ": \"" + bug.status() + "\"");
        }
    }
    
    public static void antTaskResultTest() {
        ActiveEntity Bob = choochoo.getAnts().get(0);
        
        choochoo.addResource(new Trait("Food", 10, "Delicious food!", Trait.trait_type.RESOURCE));
        System.out.println(choochoo.resourcesReport() + "\n");
        choochoo.update();
        System.out.println(choochoo.resourcesReportDesc() + "\n");
        choochoo.update();
        System.out.println(choochoo.resourcesReport() + "\n");
        choochoo.update();
        System.out.println(choochoo.resourcesReport() + "\n");
        //at -2 food and 6 poop, going to make a soilder dig for +1 space
        System.out.println(Bob.getName() + "; Busy? " + Bob.isBusy() + "; Current Task? " + Bob.getCurrentTask().getName() + "; TaskTimer? " + Bob.getTaskTimer() + "; Task Completed? " + Bob.getTaskCompleted());
        Bob.setTaskAndTimer(AntTaskBuilder.dig());
        System.out.println(Bob.getName() + "; Busy? " + Bob.isBusy() + "; Current Task? " + Bob.getCurrentTask().getName() + "; TaskTimer? " + Bob.getTaskTimer() + "; Task Completed? " + Bob.getTaskCompleted());
        choochoo.update();
        System.out.println(choochoo.resourcesReport() + "\n");
        System.out.println(Bob.getName() + "; Busy? " + Bob.isBusy() + "; Current Task? " + Bob.getCurrentTask().getName() + "; TaskTimer? " + Bob.getTaskTimer() + "; Task Completed? " + Bob.getTaskCompleted());
        choochoo.update();
        System.out.println(choochoo.resourcesReport() + "\n");
        System.out.println(Bob.getName() + "; Busy? " + Bob.isBusy() + "; Current Task? " + Bob.getCurrentTask().getName() + "; TaskTimer? " + Bob.getTaskTimer() + "; Task Completed? " + Bob.getTaskCompleted());
        //make the soldier forage
        Bob.setTaskAndTimer(AntTaskBuilder.forage());
        choochoo.update();
        System.out.println(choochoo.resourcesReport() + "\n");
        System.out.println(Bob.getName() + "; Busy? " + Bob.isBusy() + "; Current Task? " + Bob.getCurrentTask().getName() + "; TaskTimer? " + Bob.getTaskTimer() + "; Task Completed? " + Bob.getTaskCompleted());
        choochoo.update();
        System.out.println(choochoo.resourcesReport() + "\n");
        System.out.println(Bob.getName() + "; Busy? " + Bob.isBusy() + "; Current Task? " + Bob.getCurrentTask().getName() + "; TaskTimer? " + Bob.getTaskTimer() + "; Task Completed? " + Bob.getTaskCompleted());
    }
    
    public static void antUpdateTest() {
        System.out.println("ANT TEST! " + choochoo.getAnts().size() + " ants.");
        printAnts();
        System.out.println();
        System.out.println(choochoo.getAnts().get(0).getName());
        System.out.println(choochoo.getAnts().get(0).getCurrentTask().getName());
        choochoo.getAnts().get(0).setCurrentTask(AntTaskBuilder.dig());
        System.out.println(choochoo.getAnts().get(0).getName());
        System.out.println(choochoo.getAnts().get(0).getCurrentTask().getName());
        System.out.println();
        printAnts();
        choochoo.update();
        System.out.println();
        printAnts();
    }
}
