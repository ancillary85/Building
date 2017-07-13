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
public class XMLParserTest {
    
    public static void parseTest() throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder dB = fact.newDocumentBuilder();
        Document doc = dB.parse(XMLParserTest.class.getResourceAsStream("entityXMLTest.xml"));
        doc.normalize();
        NodeList nL = doc.getElementsByTagName("cat");
        NamedNodeMap map = nL.item(0).getAttributes();
        
        
        System.out.println(nL.getLength());
        System.out.println(nL.item(0).hasAttributes());
        System.out.println(nL.item(0).getNodeName());
        System.out.println(nL.item(0).getTextContent().trim());
        System.out.println(map.getLength());
        System.out.println(map.item(0).getNodeName());
        System.out.println(map.item(0).getTextContent().trim());
        System.out.println(map.item(1).getNodeName());
        System.out.println(map.item(1).getTextContent().trim());
    }
}
