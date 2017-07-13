/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.io.IOException;
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
    
    public static Task parseTask(Node n) {
        NamedNodeMap map = n.getAttributes();
        
        String name = map.getNamedItem("name").getNodeValue();
        int duration = Integer.parseInt(map.getNamedItem("duration").getNodeValue());
        String[] costs = map.getNamedItem("costs").getNodeValue().split(",");
        String[] requirements = map.getNamedItem("requirements").getNodeValue().split(",");
        String[] results = map.getNamedItem("results").getNodeValue().split(",");
        String flavor = n.getTextContent().trim();
        Task t = new Task(name, duration, costs, requirements, results, flavor);
        
        return t;
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
}
