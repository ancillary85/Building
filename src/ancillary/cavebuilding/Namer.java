/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.HashSet;
import java.util.Random;

/**
 *
 * @author MLaptop
 */
public class Namer {
 
    private final static String[] KOLstart = {"B", "Fr", "G", "Gr", "H", "St", "T", "Tr", "W"};
    private final static String[] KOLmid = {"a", "o", "u"};
    private final static String[] KOLend = {"bastian", "bediah", "bert", "ddeus", "derick", "dolph", "fferson", "fflesby", "garth", "ginald", "llace", "lliam", "nald", "nathan", "ncent", "nderson", "njamin", "nklin", "raham", "sevelt", "tchell", "berta", "brina", "chelle", "gdalena", "landa", "linda", "ndrea", "nnifer", "phelia", "redith", "rgaret", "rgarita", "rlotte", "ronica", "sabella", "sadora", "semary", "xanne"};
    
    private Random randomizer;
    private HashSet<String> previousNames;
    private int maxNames;
    
    public Namer() {
        randomizer = new Random();
        previousNames = null;
        maxNames = -1;
    }
    
    /**
     * Creates a HashSet to store names it has made in order to not give repeats, if unique = true.
     * @param unique 
     */
    public Namer(boolean unique) {
        randomizer = new Random();
        if(unique) {
            previousNames = new HashSet();
            maxNames = calculateMaxNames();
        }
        else {
            previousNames = null;
            maxNames = -1;
        }
    }
    
    /**
     * Initializes the Namer's Random with the given seed.
     * @param seed
     */
    public Namer(long seed) {
        randomizer = new Random(seed);
        previousNames = null;
        maxNames = -1;
    }
    
    /**
     * Initializes the Namer's Random with the given seed.
     * Creates a HashSet to store names it has made in order to not give repeats, if unique = true.
     * @param seed
     * @param unique
     */
    public Namer(long seed, boolean unique) {
        randomizer = new Random(seed);
        if(unique) {
            previousNames = new HashSet();
            maxNames = calculateMaxNames();
        }
        else {
            previousNames = null;
            maxNames = -1;
        }
    }
    
    /**
     * Want to check for repeats and if we've exhausted our options
     * @return 
     */
    public String makeName() {
        String result = makeNameKOL();
        if(previousNames == null || previousNames.size() == maxNames) {
            return result;
        }
        
        while(previousNames.contains(result)) {
            result = makeNameKOL();
        }
        
        return result;
    }
    
    public String makeNameKOL() {
        String result = KOLstart[randomizer.nextInt(KOLstart.length)];
        result += KOLmid[randomizer.nextInt(KOLmid.length)];
        result += KOLend[randomizer.nextInt(KOLend.length)];
        
        return result;
    }
    
    public Random getRandom() {
        return randomizer;
    }
    
    public void setRandom(Random newR) {
        randomizer = newR;
    }   
    
    public int getMaxNames() {
        return maxNames;
    }
    
    private int calculateMaxNames() {
        return KOLstart.length * KOLmid.length * KOLend.length;
    }
}
