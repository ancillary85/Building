/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
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
public class ActiveEntityTest {
    
    public ActiveEntityTest() {
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
     * Test of getTasks method on empty Tasks, of class ActiveEntity.
     */
    @Test
    public void testGetNoTasks() {
        ActiveEntity instance = new ActiveEntity();
        int expResult = 0;
        int result = instance.getTasks().size();
        assertEquals(expResult, result);
    }

        /**
     * Test of getTasks method, of class ActiveEntity.
     * Tests same size, same entries, same order
     * 
     */
    @Test
    public void testGetTasks() {
        Task DIG = new Task("Dig", 1, null, null, new String[]{"+1 " + Task.RESOURCE +  " space"}, "Expand the colony");
        Task t = new Task();
        ArrayList<Task> testTasks = new ArrayList();
        testTasks.add(t);
        testTasks.add(DIG);
        ActiveEntity instance = new ActiveEntity("test", "Tester", "nowhere", testTasks);
        List<Task> expResult = new ArrayList();
        expResult.add(t);
        expResult.add(DIG);
        List<Task> result = instance.getTasks();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getTasks method of class ActiveEntity
     * Tests that the returned List<Task> is unmodifiable and throws an exception
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testModifyGetTasks() {
         Task DIG = new Task("Dig", 1, null, null, new String[]{"+1 " + Task.RESOURCE +  " space"}, "Expand the colony");
         Task t = new Task();
         ArrayList<Task> testTasks = new ArrayList();
         testTasks.add(t);
         testTasks.add(DIG);
         ActiveEntity instance = new ActiveEntity("test", "Tester", "nowhere", testTasks);
         instance.getTasks().add(new Task());
    }
    
    /**
     * Test of getTasks method of class ActiveEntity, using an empty list of tasks
     * Tests that the returned List<Task> is unmodifiable and throws an exception
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testModifyGetNoTasks() {
         ActiveEntity instance = new ActiveEntity();
         instance.getTasks().add(new Task());
    }
    
    /**
     * Test of setTasks method, of class ActiveEntity.
     * Starting with no Tasks, setting  to no Tasks
     */
    @Test
    public void testSetNoneToNoneTasks() {
        List<Task> newTasks = null;
        ActiveEntity instance = new ActiveEntity();
        instance.setTasks(newTasks);
        assertTrue(instance.getTasks().isEmpty());
    }

    /**
     * Test of setTasks method, of class ActiveEntity.
     * Starting with some Tasks, setting to no Tasks
     */
    @Test
    public void testSetSomeToNoneTasks() {
        List<Task> newTasks = null;
        Task DIG = new Task("Dig", 1, null, null, new String[]{"+1 " + Task.RESOURCE +  " space"}, "Expand the colony");
         Task t = new Task();
         ArrayList<Task> testTasks = new ArrayList();
         testTasks.add(t);
         testTasks.add(DIG);
         ActiveEntity instance = new ActiveEntity("test", "Tester", "nowhere", testTasks);
         instance.setTasks(newTasks);
        assertTrue(instance.getTasks().isEmpty());
    }
    
    /**
     * Test of setTasks method, of class ActiveEntity.
     * Starting with no Tasks, setting to some Tasks
     */
    @Test
    public void testSetNoneToSomeTasks() {
        Task DIG = new Task("Dig", 1, null, null, new String[]{"+1 " + Task.RESOURCE +  " space"}, "Expand the colony");
         Task t = new Task();
         ArrayList<Task> testTasks = new ArrayList();
         testTasks.add(t);
         testTasks.add(DIG);
         ActiveEntity instance = new ActiveEntity();
         instance.setTasks(testTasks);
        assertFalse(instance.getTasks().isEmpty());
        assertTrue(instance.getTasks().get(0).getName().equals("no task"));
        assertTrue(instance.getTasks().get(1).getName().equals("Dig"));
    }
    
    /**
     * Test of setTasks method, of class ActiveEntity.
     * Starting with some Tasks, setting to some Tasks
     */
    @Test
    public void testSetSomeToSomeTasks() {
        Task DIG = new Task("Dig", 1, null, null, new String[]{"+1 " + Task.RESOURCE +  " space"}, "Expand the colony");
         Task t = new Task();
         ArrayList<Task> testTasks = new ArrayList();
         testTasks.add(t);
         testTasks.add(DIG);
         ActiveEntity instance = new ActiveEntity("test", "Tester", "nowhere", testTasks);
         instance.setTasks(testTasks);
        assertTrue(instance.getTasks().size() == 2);
        assertTrue(instance.getTasks().get(0).getName().equals("no task"));
        assertTrue(instance.getTasks().get(1).getName().equals("Dig"));
        //show that the Tasks are independent of the originals
        testTasks.clear();
        assertTrue(instance.getTasks().size() == 2);
        assertTrue(instance.getTasks().get(0).getName().equals("no task"));
        assertTrue(instance.getTasks().get(1).getName().equals("Dig"));
        //one more change
        testTasks.add(new Task());
        testTasks.add(new Task());
        testTasks.add(DIG);
        testTasks.add(t);
        instance.setTasks(testTasks);
        assertTrue(instance.getTasks().size() == 4);
        assertTrue(instance.getTasks().get(0).getName().equals("no task"));
        assertTrue(instance.getTasks().get(1).getName().equals("no task"));
        assertTrue(instance.getTasks().get(2).getName().equals("Dig"));
        assertTrue(instance.getTasks().get(3).getName().equals("no task"));
    }
    
    /**
     * Test of addTask method, of class ActiveEntity.
     */
    @Test
    public void testAddTask() {
        Task newTask = null;
        ActiveEntity instance = new ActiveEntity();
        assertTrue(instance.getTasks().isEmpty());
        instance.addTask(newTask);
        assertTrue(instance.getTasks().get(0).getDuration() == 0);
        assertTrue(instance.getTasks().size() == 1);
        instance.addTask(new Task());
        assertTrue(instance.getTasks().size() == 2);
        assertTrue(instance.getTasks().get(1).getDuration() == 0);
        newTask = new Task("Dig", 1, null, null, new String[]{"+1 " + Task.RESOURCE +  " space"}, "Expand the colony");
        assertTrue(instance.getTasks().get(0).getDuration() == 0);
        assertTrue(instance.getTasks().size() == 1);
    }

    /**
     * Test of addTasks method, of class ActiveEntity.
     */
    @Test
    public void testAddTasks() {
        List<Task> newTasks = null;
        ActiveEntity instance = new ActiveEntity();
        instance.addTasks(newTasks);
        assertTrue(instance.getTasks().get(0).getDuration() == 0);
        assertTrue(instance.getTasks().size() == 1);
        
        newTasks = new ArrayList();
        newTasks.add(new Task());
        assertTrue(instance.getTasks().get(0).getDuration() == 0);
        assertTrue(instance.getTasks().size() == 1);
    }

    /**
     * Test of removeTask method, of class ActiveEntity.
     */
    @Test
    public void testRemoveTask() {
        Task oldTask = null;
        ActiveEntity instance = new ActiveEntity();
        instance.removeTask(oldTask);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTaskTimer method, of class ActiveEntity.
     */
    @Test
    public void testGetTaskTimer() {
        ActiveEntity instance = new ActiveEntity();
        int expResult = 0;
        int result = instance.getTaskTimer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTaskTimer method, of class ActiveEntity.
     */
    @Test
    public void testSetTaskTimer() {
        int newTime = 0;
        ActiveEntity instance = new ActiveEntity();
        instance.setTaskTimer(newTime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTaskTimerFromCurrentTask method, of class ActiveEntity.
     */
    @Test
    public void testSetTaskTimerFromCurrentTask() {
        ActiveEntity instance = new ActiveEntity();
        instance.setTaskTimerFromCurrentTask();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentTask method, of class ActiveEntity.
     */
    @Test
    public void testGetCurrentTask() {
        ActiveEntity instance = new ActiveEntity();
        Task expResult = null;
        Task result = instance.getCurrentTask();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCurrentTask method, of class ActiveEntity.
     */
    @Test
    public void testSetCurrentTask_int() {
        int taskNumber = 0;
        ActiveEntity instance = new ActiveEntity();
        instance.setCurrentTask(taskNumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCurrentTask method, of class ActiveEntity.
     */
    @Test
    public void testSetCurrentTask_Task() {
        Task newTask = null;
        ActiveEntity instance = new ActiveEntity();
        instance.setCurrentTask(newTask);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAndSetCurrentTask method, of class ActiveEntity.
     */
    @Test
    public void testAddAndSetCurrentTask() {
        Task newTask = null;
        ActiveEntity instance = new ActiveEntity();
        instance.addAndSetCurrentTask(newTask);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTaskCompleted method, of class ActiveEntity.
     */
    @Test
    public void testGetTaskCompleted() {
        ActiveEntity instance = new ActiveEntity();
        boolean expResult = false;
        boolean result = instance.getTaskCompleted();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTaskCompletedProp method, of class ActiveEntity.
     */
    @Test
    public void testGetTaskCompletedProp() {
        ActiveEntity instance = new ActiveEntity();
        SimpleBooleanProperty expResult = null;
        SimpleBooleanProperty result = instance.getTaskCompletedProp();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTaskCompleted method, of class ActiveEntity.
     */
    @Test
    public void testSetTaskCompleted() {
        boolean taskStatus = false;
        ActiveEntity instance = new ActiveEntity();
        instance.setTaskCompleted(taskStatus);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of idle method, of class ActiveEntity.
     */
    @Test
    public void testIdle() {
        ActiveEntity instance = new ActiveEntity();
        instance.idle();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of entityUpdate method, of class ActiveEntity.
     */
    @Test
    public void testEntityUpdate() {
        String[] args = null;
        ActiveEntity instance = new ActiveEntity();
        instance.entityUpdate(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of completeTask method, of class ActiveEntity.
     */
    @Test
    public void testCompleteTask() {
        ActiveEntity instance = new ActiveEntity();
        instance.completeTask();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cancelTask method, of class ActiveEntity.
     */
    @Test
    public void testCancelTask() {
        ActiveEntity instance = new ActiveEntity();
        instance.cancelTask();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
