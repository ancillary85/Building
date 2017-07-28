/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.Arrays;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mike
 */
public class TaskTest {
    
    public TaskTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isNoTask method, of class Task.
     */
    @Test
    public void testIsNoTask() {
        Task instance = new Task();
        assertTrue(instance.isNoTask());
        instance = new Task("Dig", 2, null, null, null, "Expand the colony");
        assertFalse(instance.isNoTask());
    }

    /**
     * Test of setNoTask method, of class Task.
     */
    @Test
    public void testSetNoTask() {
        Task instance = new Task();
        assertTrue(instance.getName().equals("no task"));
        instance.setNoTask();
        assertTrue(instance.getName().equals("no task"));
        instance = new Task("Dig", 2, null, null, null, "Expand the colony");
        assertFalse(instance.getName().equals("no task"));
        instance.setNoTask();
        assertTrue(instance.getName().equals("no task"));
        assertTrue(instance.getDuration() < 1);
    }

    /**
     * Test of toString method, of class Task.
     */
//    @Test
//    public void testToString() {
//        Task instance = new Task();
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of equals method, of class Task.
     */
    @Test
    public void testEquals() {
        Object t = null;
        Task instance = new Task();
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(t));
        assertTrue(instance.equals(new Task()));
        instance = new Task("Dig", 2, null, null, null, "Expand the colony");
        assertFalse(instance.equals(new Task()));
        assertTrue(instance.equals(new Task("Dig", 2, null, null, null, "Expand the colony")));
        assertFalse(instance.equals("test"));
    }

    /**
     * Test of hashCode method, of class Task.
     */
    @Test
    public void testHashCode() {
        Task t1 = new Task();
        Task t2 = new Task();
        assertEquals(t1.hashCode(), t1.hashCode());
        assertTrue(t1.equals(t2));
        assertEquals(t1.hashCode(), t2.hashCode());
        t1.setName("asdasdasd");
        assertEquals(t1.hashCode(), t1.hashCode());
        assertEquals(t1.hashCode(), t1.hashCode());
        t1 = new Task("Dig", 2, null, null, null, "Expand the colony");
        t2 = new Task("Dig", 2, null, null, null, "Expand the colony");
        assertEquals(t1.hashCode(), t1.hashCode());
        assertTrue(t1.equals(t2));
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    /**
     * Test of getName method, of class Task.
     */
    @Test
    public void testGetName() {
        Task instance = new Task();
        assertEquals(instance.getName(), "no task");
        instance = new Task("Dig", 2, null, null, null, "Expand the colony");
        assertEquals(instance.getName(), "Dig");

    }

    /**
     * Test of getDuration method, of class Task.
     */
    @Test
    public void testGetDuration() {
        Task instance = new Task();
        assertEquals(instance.getDuration(), 0);
        instance = new Task("Dig", 2, null, null, null, "Expand the colony");
        assertEquals(instance.getDuration(), 2);
    }

    /**
     * Test of setName method, of class Task.
     */
    @Test
    public void testSetName() {
        Task instance = new Task();
        instance.setName("foobar");
        assertEquals(instance.getName(), "foobar");
    }

    /**
     * Test of setDuration method, of class Task.
     */
    @Test
    public void testSetDuration() {
        Task instance = new Task();
        instance.setDuration(2000);
        assertEquals(instance.getDuration(), 2000);
    }

    /**
     * Test of getCosts method, of class Task.
     */
    @Test
    public void testGetCosts() {
        Task t1 = new Task();
        assertArrayEquals(t1.getCosts(), new String[]{"none"});        
        
        t1 = new Task("Eat", 2, 
            new String[]{"-1 " + Task.RESOURCE + " food"}, 
            new String[]{">0 " + Task.RESOURCE + " food"}, 
            new String[]{"+4 " + Task.PERSONAL_RESOURCE + " stamina"}, 
            "Eat some food");
        
        String[] resultCosts = t1.getCosts();
        String[] expectedCosts = new String[]{"-1 " + Task.RESOURCE + " food"}; 
        
        assertEquals(resultCosts.length, expectedCosts.length);
        assertEquals(resultCosts[0], expectedCosts[0]);
        
        t1 = new Task("Eat", 2, 
            new String[]{"-1 " + Task.RESOURCE + " food", 
                "+4 " + Task.PERSONAL_RESOURCE + " stamina", 
                ">0 " + Task.RESOURCE + " food"}, 
            null, null, "Eat some food");
        
        resultCosts = t1.getCosts();
        Arrays.sort(resultCosts);
        expectedCosts = new String[]{"-1 " + Task.RESOURCE + " food", 
                "+4 " + Task.PERSONAL_RESOURCE + " stamina", 
                ">0 " + Task.RESOURCE + " food"};
        Arrays.sort(expectedCosts);
        
        assertArrayEquals(resultCosts, expectedCosts);
       
    }

    /**
     * Test of getRequirements method, of class Task.
     */
    @Test
    public void testGetRequirements() {
        Task t1 = new Task();
        assertArrayEquals(t1.getRequirements(), new String[]{"none"});        
        
        t1 = new Task("Eat", 2, 
            new String[]{"-1 " + Task.RESOURCE + " food"}, 
            new String[]{">0 " + Task.RESOURCE + " food"}, 
            new String[]{"+4 " + Task.PERSONAL_RESOURCE + " stamina"}, 
            "Eat some food");
        
        String[] resultReq = t1.getRequirements();
        String[] expectedReq = new String[]{">0 " + Task.RESOURCE + " food"}; 
        
        assertEquals(resultReq.length, expectedReq.length);
        assertEquals(resultReq[0], expectedReq[0]);
        
        t1 = new Task("Eat", 2, 
            null, 
            new String[]{"-1 " + Task.RESOURCE + " food", 
                "+4 " + Task.PERSONAL_RESOURCE + " stamina", 
                ">0 " + Task.RESOURCE + " food"}, 
            null, "Eat some food");
        
        resultReq = t1.getRequirements();
        Arrays.sort(resultReq);
        expectedReq = new String[]{"-1 " + Task.RESOURCE + " food", 
                "+4 " + Task.PERSONAL_RESOURCE + " stamina", 
                ">0 " + Task.RESOURCE + " food"};
        Arrays.sort(expectedReq);
        
        assertArrayEquals(resultReq, expectedReq);
    }

    /**
     * Test of getResults method, of class Task.
     */
    @Test
    public void testGetResults() {
        Task t1 = new Task();
        assertArrayEquals(t1.getResults(), new String[]{"none"});        
        
        t1 = new Task("Eat", 2, 
            new String[]{"-1 " + Task.RESOURCE + " food"}, 
            new String[]{">0 " + Task.RESOURCE + " food"}, 
            new String[]{"+4 " + Task.PERSONAL_RESOURCE + " stamina"}, 
            "Eat some food");
        
        String[] resultResult = t1.getResults();
        String[] expectedResults = new String[]{"+4 " + Task.PERSONAL_RESOURCE + " stamina"}; 
        
        assertEquals(resultResult.length, expectedResults.length);
        assertEquals(resultResult[0], expectedResults[0]);
        
        t1 = new Task("Eat", 2, 
            null,
            null, 
            new String[]{"-1 " + Task.RESOURCE + " food", 
                "+4 " + Task.PERSONAL_RESOURCE + " stamina", 
                ">0 " + Task.RESOURCE + " food"}, 
            "Eat some food");
        
        resultResult = t1.getResults();
        Arrays.sort(resultResult);
        expectedResults = new String[]{"-1 " + Task.RESOURCE + " food", 
                "+4 " + Task.PERSONAL_RESOURCE + " stamina", 
                ">0 " + Task.RESOURCE + " food"};
        Arrays.sort(expectedResults);
        
        assertArrayEquals(resultResult, expectedResults);
    }

    /**
     * Test of getNameProp method, of class Task.
     */
//    @Test
//    public void testGetNameProp() {
//        System.out.println("getNameProp");
//        Task instance = new Task();
//        SimpleStringProperty expResult = null;
//        SimpleStringProperty result = instance.getNameProp();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of setNameProp method, of class Task.
     */
//    @Test
//    public void testSetNameProp() {
//        SimpleStringProperty name = null;
//        Task instance = new Task();
//        instance.setNameProp(name);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getDurationProp method, of class Task.
     */
//    @Test
//    public void testGetDurationProp() {
//        System.out.println("getDurationProp");
//        Task instance = new Task();
//        SimpleIntegerProperty expResult = null;
//        SimpleIntegerProperty result = instance.getDurationProp();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of setDurationProp method, of class Task.
     */
//    @Test
//    public void testSetDurationProp() {
//        SimpleIntegerProperty duration = null;
//        Task instance = new Task();
//        instance.setDurationProp(duration);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getCostsProp method, of class Task.
     */
//    @Test
//    public void testGetCostsProp() {
//        System.out.println("getCostsProp");
//        Task instance = new Task();
//        SimpleStringProperty[] expResult = null;
//        SimpleStringProperty[] result = instance.getCostsProp();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of setCostsProp method, of class Task.
     */
//    @Test
//    public void testSetCostsProp() {
//        SimpleStringProperty[] costs = null;
//        Task instance = new Task();
//        instance.setCostsProp(costs);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getRequirementsProp method, of class Task.
     */
//    @Test
//    public void testGetRequirementsProp() {
//        System.out.println("getRequirementsProp");
//        Task instance = new Task();
//        SimpleStringProperty[] expResult = null;
//        SimpleStringProperty[] result = instance.getRequirementsProp();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of setRequirementsProp method, of class Task.
     */
//    @Test
//    public void testSetRequirementsProp() {
//        SimpleStringProperty[] requirements = null;
//        Task instance = new Task();
//        instance.setRequirementsProp(requirements);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getResultsProp method, of class Task.
     */
//    @Test
//    public void testGetResultsProp() {
//        System.out.println("getResultsProp");
//        Task instance = new Task();
//        SimpleStringProperty[] expResult = null;
//        SimpleStringProperty[] result = instance.getResultsProp();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of setResultsProp method, of class Task.
     */
//    @Test
//    public void testSetResultsProp() {
//        SimpleStringProperty[] results = null;
//        Task instance = new Task();
//        instance.setResultsProp(results);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getFlavorProp method, of class Task.
     */
//    @Test
//    public void testGetFlavorProp() {
//        System.out.println("getFlavorProp");
//        Task instance = new Task();
//        SimpleStringProperty expResult = null;
//        SimpleStringProperty result = instance.getFlavorProp();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getFlavor method, of class Task.
     */
    @Test
    public void testGetFlavor() {
        Task t1 = new Task("Eat", 2, 
            new String[]{"-1 " + Task.RESOURCE + " food"}, 
            new String[]{">0 " + Task.RESOURCE + " food"}, 
            new String[]{"+4 " + Task.PERSONAL_RESOURCE + " stamina"}, 
            "Eat some food");
        
        assertEquals(t1.getFlavor(), "Eat some food");
    }

    /**
     * Test of setFlavorProp method, of class Task.
     */
//    @Test
//    public void testSetFlavorProp() {
//        SimpleStringProperty flavor = null;
//        Task instance = new Task();
//        instance.setFlavorProp(flavor);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
