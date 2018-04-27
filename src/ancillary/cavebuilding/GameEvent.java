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
public class GameEvent {
    
    protected String title = "";
    protected String name = "";
    protected String body = "";
    protected int targetTurn = -1;
    protected String data = "";
    protected ArrayList<Trait> requirements = new ArrayList();
    protected ArrayList<Trait> results = new ArrayList();
    protected String badge = null;
    protected boolean suppressed = false;
    protected boolean skippable = true;
    
    
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
    
    @Override
    public String toString() {
        return name;
    }
}

