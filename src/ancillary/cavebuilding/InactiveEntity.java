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
public class InactiveEntity extends Entity {

    public InactiveEntity() {
        super();
    }
    
    public InactiveEntity(Entity e) {
        super(e.id, e.name.get(), false, e.location.get(), e.traits.get());
    }
    
    public void entityUpdate(String[] args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
