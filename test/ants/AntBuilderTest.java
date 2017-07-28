/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

import ancillary.cavebuilding.AntBuilder;
import ancillary.cavebuilding.ActiveEntity;
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
public class AntBuilderTest {
    
    public AntBuilderTest() {
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
     * Test of makeWorker method, of class AntBuilder.
     */
    @Test
    public void testMakeWorker() {
        System.out.println("makeWorker");
        String name = "";
        String location = "";
        ActiveEntity expResult = null;
        ActiveEntity result = AntBuilder.makeWorker(name, location);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeSoldier method, of class AntBuilder.
     */
    @Test
    public void testMakeSoldier() {
        System.out.println("makeSoldier");
        String name = "";
        String location = "";
        ActiveEntity expResult = null;
        ActiveEntity result = AntBuilder.makeSoldier(name, location);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
