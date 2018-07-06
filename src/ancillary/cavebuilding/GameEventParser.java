/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.EnumSet;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author MMLaptop
 */
public class GameEventParser {
    
    public static GameEvent parseEvent(Node n) {
        GameEvent result = new GameEvent();
        if(!n.hasAttributes()) {
            return result;
        }
        
        NamedNodeMap map = n.getAttributes();
        
        if(map.getNamedItem("idnum") != null) {
            try {
                result.setIdNum(Integer.parseInt(map.getNamedItem("idnum").getNodeValue()));
            }
            catch(NumberFormatException e) {
                result.setIdNum(-1);
            }
        }
        
        if(map.getNamedItem("name") != null) {
            result.setName(map.getNamedItem("name").getNodeValue());
        }
        
        return result;
    }
    
     //needs more sanitizing
    public static ArrayList<Trait> parseByName(Node n, String name) {
        
        NodeList nChildren = n.getChildNodes();
        Node desiredChildNode = null;
        for(int i = 0; i < nChildren.getLength(); i++) {
            if(nChildren.item(i).getNodeName().equals(name)) {
                desiredChildNode = nChildren.item(i);
                break;
            }
        }
        
        if(desiredChildNode == null) {
            return null;
        }
        
        NodeList listFromChild = desiredChildNode.getChildNodes();
        ArrayList<Trait> parsedTraits = new ArrayList<>();
            
        for(int i = 0; i < listFromChild.getLength(); i++) {
            //There had better only be traits in my list of costs. I'm going to ignore anything else
            if(!listFromChild.item(i).getNodeName().equals("trait")) {
                continue;
            }

            parsedTraits.add(TraitParser.parseTrait(listFromChild.item(i)));
        }
        
        if(parsedTraits.size() > 0) {
            return parsedTraits;
        }
        else {
            return null;
        }
    }
}
