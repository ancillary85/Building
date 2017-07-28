/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ants;

import ancillary.cavebuilding.AntHillEngine;
import ancillary.cavebuilding.ActiveEntity;
import ancillary.cavebuilding.Entity;
import ancillary.cavebuilding.Room;
import java.util.List;
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
public class AntHillEngineTest {
    
    public AntHillEngineTest() {
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
     * Test of addRoom method, of class AntHillEngine.
     */
    @Test
    public void testAddRoom() {
        System.out.println("addRoom");
        Room r = null;
        AntHillEngine instance = new AntHillEngine();
        instance.addRoom(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRooms method, of class AntHillEngine.
     */
    @Test
    public void testGetRooms() {
        System.out.println("getRooms");
        AntHillEngine instance = new AntHillEngine();
        List<Room> expResult = null;
        List<Room> result = instance.getRooms();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeRoom method, of class AntHillEngine.
     */
    @Test
    public void testRemoveRoom() {
        System.out.println("removeRoom");
        Room r = null;
        AntHillEngine instance = new AntHillEngine();
        instance.removeRoom(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRooms method, of class AntHillEngine.
     */
    @Test
    public void testSetRooms() {
        System.out.println("setRooms");
        List<Room> newRooms = null;
        AntHillEngine instance = new AntHillEngine();
        instance.setRooms(newRooms);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addActiveEntity method, of class AntHillEngine.
     */
    @Test
    public void testAddActiveEntity() {
        ActiveEntity e = null;
        AntHillEngine instance = new AntHillEngine();
        instance.addActiveEntity(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntities method, of class AntHillEngine.
     */
    @Test
    public void testGetActiveEntities() {
        AntHillEngine instance = new AntHillEngine();
        List<ActiveEntity> expResult = null;
        List<ActiveEntity> result = instance.getActiveEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEntities method, of class AntHillEngine.
     */
    @Test
    public void testSetActiveEntities() {
        System.out.println("setEntities");
        List<ActiveEntity> newEntities = null;
        AntHillEngine instance = new AntHillEngine();
        instance.setActiveEntities(newEntities);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeEntity method, of class AntHillEngine.
     */
    @Test
    public void testRemoveActiveEntity() {
        System.out.println("removeEntity");
        ActiveEntity e = null;
        AntHillEngine instance = new AntHillEngine();
        instance.removeActiveEntity(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAnts method, of class AntHillEngine.
     */
    @Test
    public void testGetAnts() {
        System.out.println("getAnts");
        AntHillEngine instance = new AntHillEngine();
        List<ActiveEntity> expResult = null;
        List<ActiveEntity> result = instance.getAnts();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validateAnt method, of class AntHillEngine.
     */
    @Test
    public void testValidateAnt() {
        System.out.println("validateAnt");
        ActiveEntity a = null;
        boolean expResult = false;
        boolean result = AntHillEngine.validateAnt(a);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class AntHillEngine.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        AntHillEngine instance = new AntHillEngine();
        instance.update();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of antToString method, of class AntHillEngine.
     */
    @Test
    public void testAntToString() {
        System.out.println("antToString");
        ActiveEntity e = null;
        AntHillEngine instance = new AntHillEngine();
        String expResult = "";
        String result = instance.antToString(e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
