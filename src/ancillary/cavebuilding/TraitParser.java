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
public class TraitParser {
    
    public static Trait parseTrait(Node n) {
        Trait result = new Trait();
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
        
        if(map.getNamedItem("valueMin") != null) {
            try {
                result.setValueMin(Integer.parseInt(map.getNamedItem("valueMin").getNodeValue()));
            }
            catch(NumberFormatException e) {
                result.setValueMin(Integer.MIN_VALUE);
            }
        }
        
        if(map.getNamedItem("valueMax") != null) {
            try {
                result.setValueMax(Integer.parseInt(map.getNamedItem("valueMax").getNodeValue()));
            }
            catch(NumberFormatException e) {
                result.setValueMax(Integer.MAX_VALUE);
            }
        }
        
        if(map.getNamedItem("value") != null) {
            try {
                int value = Integer.parseInt(map.getNamedItem("value").getNodeValue());
                
                if(value <= result.getValueMax() && value >= result.getValueMin()) {
                    result.setValue(value);
                }
            }
            catch(NumberFormatException e) {
                result.setValue(0);
            }
        }
        
        if(map.getNamedItem("description") != null) {
            result.setDesc(map.getNamedItem("description").getNodeValue());
        }
    
        if(map.getNamedItem("types") != null) {
            
            String[] typesInput = map.getNamedItem("types").getNodeValue().split(",");
            ArrayList<Trait.trait_type> typesOutput = new ArrayList();
            
            for(String s : typesInput) {
                try{
                    typesOutput.add(Trait.trait_type.valueOf(s.toUpperCase().trim()));
                }
                catch(IllegalArgumentException e) {
                    continue;
                }
            }
            
            result.setTypes(EnumSet.copyOf(typesOutput));
        }
        
        if(map.getNamedItem("sortingPriority") != null) {
            try {
                result.setSortingPriority(Integer.parseInt(map.getNamedItem("sortingPriority").getNodeValue()));
            }
            catch(NumberFormatException e) {
                result.setSortingPriority(-1);
            }
        }
        
        if(map.getNamedItem("showValue") != null) {
            result.setShowValue(Boolean.parseBoolean(map.getNamedItem("sortingPriority").getNodeValue()));
        }
        
        return result;
    }
    
    /**
     * 
     * @param inputArray
     * @param combine Whether or not to add together Traits with the same name
     * @return 
     */
    public static Trait[] parseTraits(String[] inputArray, boolean combine) {
        return null;
    }
    
}
/*
name; value; valueMin; valueMax; description; types; sortingPriority; showValue

private SimpleStringProperty name;
    private SimpleIntegerProperty value;
    private SimpleIntegerProperty valueMin = new SimpleIntegerProperty(Integer.MIN_VALUE);
    private SimpleIntegerProperty valueMax = new SimpleIntegerProperty(Integer.MAX_VALUE);
    private SimpleStringProperty description;
    private EnumSet<trait_type> types;
    private int sortingPriority = -1;
    private SimpleBooleanProperty showValue = new SimpleBooleanProperty(true);
*/