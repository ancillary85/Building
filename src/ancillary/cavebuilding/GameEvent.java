/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author MLaptop
 */

/*
    GameEvent needs to be able to show up at predefined times, after a countdown, when conditions are met, randomly, etc.
    It should be able to cause just about any state change: add/remove/adjust Traits/Tasks/etc, start a new event, etc.
    It could just be there to be read. It should be able to offer choices that have results.
    
    Example: Event "Go Buy a Chainsaw" is triggered. It adds a Task for buying a chainsaw. After that Task is completed, it
    is replaced with a Task for using a chainsaw.
*/

public class GameEvent {
    
    protected String title = "";
    protected String name = "";
    protected String body = "";
    protected int targetTurn = -1;
    protected double oddsToOccur = 1.0;
    protected int oddsTail = 0;
    protected int turnsMissed = 0;
    protected String data = "";
    protected ArrayList<Trait> requirements = new ArrayList();
    protected ArrayList<Trait> results = new ArrayList();
    protected String badge = null;
    protected boolean suppressed = false;
    protected boolean skippable = true;
    protected boolean repeats = false;
    protected int idNum = -1;
    
    public GameEvent() {
        //NOTHING
    }
    
    public GameEvent(String initTitle, String initBody) {
        title = initTitle;
        body = initBody;
    }
    
    public GameEvent(String initTitle, String initBody, String initName, List<Trait> initReq, List<Trait> initRes) {
        title = initTitle;
        body = initBody;
        name = initName;
        if(initReq != null) {
            requirements = new ArrayList(initReq);
        }
        
        if(initRes != null) {
            results = new ArrayList(initRes);
        }
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getName() {
        return name;
    }
    
    public String getBody() {
        return body;
    }
    
    public int getTargetTurn() {
        return targetTurn;
    }
    
    public double getOddsToOccur() {
        return oddsToOccur;
    }
    
    public int getOddsTail() {
        return oddsTail;
    }
    
    public String getData() {
        return data;
    }
    
    public ArrayList<Trait> getRequirements() {
        return requirements;
    }
    
    public ArrayList<Trait> getResults() {
        return results;
    }
    
    public String getBadge() {
        return badge;
    }
    
    public void setTitle(String newTitle) {
        title = newTitle;
    }
    
    public void setName(String newName) {
        name = newName;
    }
    
    public void setBody(String newBody) {
        body = newBody;
    }
    
    public void setTargetTurn(int newTarget) {
        targetTurn = newTarget;
    }
    
    public void setOddsToOccur(double newOdds) {
        oddsToOccur = newOdds;
    }
    
    public void setOddsTail(int tail) {
        oddsTail = tail;
    }
    
    public int getTurnsMissed() {
        return turnsMissed;
    }
    
    public void setTurnsMissed(int turns) {
        turnsMissed = turns;
    }
    
    public void turnHasPassed() {
        turnsMissed++;
    }
    
    public void setData(String newData) {
        data = newData;
    }
    
    public void setRequirements(ArrayList<Trait> newReq) {
        if(newReq == null) {
            requirements = new ArrayList();
        }
        else {
            requirements = newReq;
        }
    }
    
    public void setResults(ArrayList<Trait> newRes) {
        if(newRes == null) {
            results = new ArrayList();
        }
        else {
            results = newRes;
        }
    }
    
    public void setBadge(String newBadge) {
        badge = newBadge;
    }
    
    public boolean isSuppressed() {
        return suppressed;
    }
    
    public void setSuppressed(boolean newSuppression) {
        suppressed = newSuppression;
    }
    
    public boolean isSkippable() {
        return skippable;
    }
    
    public void setSkippable(boolean newSkipping) {
        skippable = newSkipping;
    }
    
    public boolean isRepeatable() {
        return repeats;
    }
    
    public void setRepeatable(boolean newRepeatability) {
        repeats = newRepeatability;
    }
    
    public int getIdNum() {
        return idNum;
    }
    
    public void setIdNum(int newID) {
        idNum = newID;
    }
    
    @Override
    public String toString() {
        return name;
    }
}

