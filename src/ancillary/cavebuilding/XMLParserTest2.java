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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Mike
 */
public class XMLParserTest2 {
    
    public static void parseTest() throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder dB = fact.newDocumentBuilder();
        Document doc = dB.parse(XMLParserTest2.class.getResourceAsStream("entityXMLTest2.xml"));
        doc.normalize();
        NodeList nL = doc.getElementsByTagName("entity");
        
        for(int i = 0; i < nL.getLength(); i++) {
            NamedNodeMap map = nL.item(i).getAttributes();
            System.out.println("ID: " + map.getNamedItem("id").getNodeValue() + 
                "; Name: " + map.getNamedItem("name").getNodeValue() +
                "; HP: " + map.getNamedItem("hp").getNodeValue() + 
                "; Flavor: " + nL.item(i).getTextContent().trim());
            
            
//ID: worker; Name: Worker; HP: 1
//ID: soldier; Name: Soldier Ant; HP: 3
//ID: drone; Name: Drone Ant; HP: 4
//ID: queen; Name: Queen Ant; HP: 5
        }
    }
}