/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Mike
 */
public class TaskParser {
    
    public static final String DELIM = ":";
    
    public static Task parseTask(Node n) {
        //check for null
        NamedNodeMap map = n.getAttributes();
        Task result = new Task();
        
        if(map.getNamedItem("name") != null) {
            result.setName(map.getNamedItem("name").getNodeValue());
        }
        
        if(map.getNamedItem("duration") != null) {
            try {
                result.setDuration(Integer.parseInt(map.getNamedItem("duration").getNodeValue()));
            }
            catch(NumberFormatException e) {
                //Leave at default value
            }
        }
        
        ArrayList<Trait> costsList = parseByName(n, "costs");
        if(costsList != null) {
            result.setCostsList(costsList);
        }
        
        ArrayList<Trait> reqsList = parseByName(n, "requirements");
        if(reqsList != null) {
            result.setRequirementsList(reqsList);
        }
        
        ArrayList<Trait> resultsList = parseByName(n, "results");
        if(resultsList != null) {
            result.setResultsList(resultsList);
        }
        
        if(map.getNamedItem("gerund") != null) {
            result.setGerund(map.getNamedItem("gerund").getNodeValue());
        }
        
        if(map.getNamedItem("flavor") != null) {
            result.setFlavor(map.getNamedItem("flavor").getNodeValue());
        }
        //Task t = new Task(name, duration, parseCosts(costs), parseRequirements(requirements), parseResults(results), flavor);
        return result;
    }
    
    public static Task[] parseTasks(String file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder dB = fact.newDocumentBuilder();
        Document doc = dB.parse(TaskParser.class.getResourceAsStream(file));
        doc.normalize();
        NodeList nL = doc.getElementsByTagName("task");
        
        Task[] tasks = new Task[nL.getLength()];
        
        for(int i = 0; i < nL.getLength(); i++) {
            tasks[i] = parseTask(nL.item(i));
        }
               
        return tasks;
    }
    
    public static Trait[] parseResults(String[] inResults) {
        
        Trait[] outResults = new Trait[inResults.length];
        String[] temp;
        
        for(int i = 0; i < inResults.length; i++) {
            temp = inResults[i].split(DELIM);
            outResults[i] = new Trait(temp[0], Integer.parseInt(temp[1]), TraitBuilder.resourceProductionResult());
        }
        
        return outResults;
    }
    
    public static Trait[] parseRequirements(String[] inReq) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
