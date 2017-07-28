/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

import ancillary.cavebuilding.AntTaskBuilder;
import ancillary.cavebuilding.Task;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alecto
 */
public class AntTaskBuilderTest {
    
    public AntTaskBuilderTest() {
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
     * Test of dig method, of class AntTaskBuilder.
     */
    @Test
    public void testDig() {
        System.out.println("dig");
        Task expResult = null;
        Task result = AntTaskBuilder.dig();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of forage method, of class AntTaskBuilder.
     */
    @Test
    public void testForage() {
        System.out.println("forage");
        Task expResult = null;
        Task result = AntTaskBuilder.forage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hunt method, of class AntTaskBuilder.
     */
    @Test
    public void testHunt() {
        System.out.println("hunt");
        Task expResult = null;
        Task result = AntTaskBuilder.hunt();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of larvaCare method, of class AntTaskBuilder.
     */
    @Test
    public void testLarvaCare() {
        System.out.println("larvaCare");
        Task expResult = null;
        Task result = AntTaskBuilder.larvaCare();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eat method, of class AntTaskBuilder.
     */
    @Test
    public void testEat() {
        System.out.println("eat");
        Task expResult = null;
        Task result = AntTaskBuilder.eat();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rest method, of class AntTaskBuilder.
     */
    @Test
    public void testRest() {
        System.out.println("rest");
        Task expResult = null;
        Task result = AntTaskBuilder.rest();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fight method, of class AntTaskBuilder.
     */
    @Test
    public void testFight() {
        System.out.println("fight");
        Task expResult = null;
        Task result = AntTaskBuilder.fight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of antTasks method, of class AntTaskBuilder.
     */
    @Test
    public void testAntTasks() {
        System.out.println("antTasks");
        Task[] expResult = null;
        Task[] result = AntTaskBuilder.antTasks();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of workerTasks method, of class AntTaskBuilder.
     */
    @Test
    public void testWorkerTasks() {
        System.out.println("workerTasks");
        Task[] expResult = null;
        Task[] result = AntTaskBuilder.workerTasks();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of soldierTasks method, of class AntTaskBuilder.
     */
    @Test
    public void testSoldierTasks() {
        System.out.println("soldierTasks");
        Task[] expResult = null;
        Task[] result = AntTaskBuilder.soldierTasks();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
