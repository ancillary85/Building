/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

import ancillary.cavebuilding.ActiveEntity;
import ancillary.cavebuilding.Entity;
import java.util.ArrayList;

/**
 *
 * @author Mike
 */
public class AntTestDriver {
    
    public static void main(String[] args) {
        ArrayList<ActiveEntity> ants = new ArrayList();
        ants.add(AntBuilder.makeSoldier("Bob", null));
        ants.add(AntBuilder.makeSoldier("Rambo", null));
        ants.add(AntBuilder.makeWorker("Alice", null));
        ants.add(AntBuilder.makeWorker("Beatrice", null));
        
        AntHillEngine choochoo = new AntHillEngine(null, ants);
        
        for(ActiveEntity bug : choochoo.getAnts()) {
            System.out.println(choochoo.antToString(bug));
        }
    }
}
