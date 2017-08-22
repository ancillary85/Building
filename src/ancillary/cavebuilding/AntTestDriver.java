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

        System.out.println("ANT TEST! " + choochoo.getAnts().size());
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
    
    public static void printAnts() {
        for(ActiveEntity bug : choochoo.getAnts()) {
            System.out.println(choochoo.antToString(bug) + ": \"" + bug.status() + "\"");
        }
    }
}
