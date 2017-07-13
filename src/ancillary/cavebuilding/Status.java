/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

/**
 *
 * @author Mike
 */
public class Status {
    private String name;
    private String duration;
    private Effect[] effects;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return the effects
     */
    public Effect[] getEffects() {
        return effects;
    }

    /**
     * @param effects the effects to set
     */
    public void setEffects(Effect[] effects) {
        this.effects = effects;
    }
}
