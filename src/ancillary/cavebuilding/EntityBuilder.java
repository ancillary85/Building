/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.List;
import java.util.Set;

/**
 *
 * @author MLatop
 */
public interface EntityBuilder {

    ActiveEntity makeEntity();
    ActiveEntity makeEntity(String id, String name, String location);
    Namer getNamer();
    void setNamer(Namer newNamer);
    int getEntityCount(String id);
    Set<String> getIds(); 
    Trait[] getTraits(String id);
    Task[] getTasks(String id);
    String getIdle(String id);
    String parseId(String id);
}
