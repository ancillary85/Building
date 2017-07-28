/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ancillary.cavebuilding;

import java.util.ArrayList;
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
        ActiveEntity instance = new ActiveEntity("test", "Tester", "nowhere", testTasks, null);
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
         ActiveEntity instance = new ActiveEntity("test", "Tester", "nowhere", testTasks, null);
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
         ActiveEntity instance = new ActiveEntity("test", "Tester", "nowhere", testTasks, null);
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
         ActiveEntity instance = new ActiveEntity("test", "Tester", "nowhere", testTasks, null);
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
        assertTrue(instance.getTasks().size() == 2);
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
        instance.addTasks(newTasks);
        assertTrue(instance.getTasks().get(1).getDuration() == 0);
        assertTrue(instance.getTasks().size() == 2);
        
        newTasks.add(new Task());
        instance.addTasks(newTasks);
        assertTrue(instance.getTasks().get(3).getDuration() == 0);
        assertTrue(instance.getTasks().size() == 4);
    }

    /**
     * Test of removeTask method, of class ActiveEntity.
     */
    @Test
    public void testRemoveTask() {
        //remove a null from a new ActiveEntity
        ActiveEntity instance = new ActiveEntity();
        assertTrue(instance.getCurrentTask().isNoTask());
        assertTrue(instance.getTasks().isEmpty());
        assertFalse(instance.isBusy());
        instance.removeTask(null);
        assertTrue(instance.getCurrentTask().isNoTask());
        assertTrue(instance.getTasks().isEmpty());
        assertFalse(instance.isBusy());
        
        //remove a "no task"
        instance.removeTask(new Task());
        assertTrue(instance.getCurrentTask().isNoTask());
        assertTrue(instance.getTasks().isEmpty());
        assertFalse(instance.isBusy());
        
        //Give it a real task, remove a "no task"
        Task newTask = new Task("Dig", 1, null, null, new String[]{"+1 " + Task.RESOURCE +  " space"}, "Expand the colony");
        instance.addAndSetCurrentTask(newTask);
        assertTrue(instance.getCurrentTask().equals(newTask));
        assertFalse(instance.getTasks().isEmpty());
        assertTrue(instance.isBusy());
        assertTrue(instance.getTasks().size() == 1);
        instance.removeTask(new Task());
        assertTrue(instance.getCurrentTask().equals(newTask));
        assertFalse(instance.getTasks().isEmpty());
        assertTrue(instance.isBusy());
        assertTrue(instance.getTasks().size() == 1);
        
        //Given a real task, remove a null
        instance.removeTask(null);
        assertTrue(instance.getCurrentTask().equals(newTask));
        assertFalse(instance.getTasks().isEmpty());
        assertTrue(instance.isBusy());
        assertTrue(instance.getTasks().size() == 1);
        
        //add a "no task" and remove it
        instance.addTask(new Task());
        assertTrue(instance.getCurrentTask().equals(newTask));
        assertFalse(instance.getTasks().isEmpty());
        assertTrue(instance.isBusy());
        assertTrue(instance.getTasks().size() == 2);
        instance.removeTask(new Task());
        assertTrue(instance.getCurrentTask().equals(newTask));
        assertFalse(instance.getTasks().isEmpty());
        assertTrue(instance.isBusy());
        assertTrue(instance.getTasks().size() == 1);

        //remove the real task
        instance.removeTask(newTask);
        assertTrue(instance.getCurrentTask().isNoTask());
        assertTrue(instance.getTasks().isEmpty());
        assertFalse(instance.isBusy());    
    
    }

    /**
     * Test of getTaskTimer method, of class ActiveEntity.
     */
    @Test
    public void testGetTaskTimer() {
        ActiveEntity instance = new ActiveEntity();
        assertEquals(0, instance.getTaskTimer());
        
        instance.setTaskTimer(5);
        assertEquals(5, instance.getTaskTimer());
        
        Task newTask = new Task("Dig", 1, null, null, new String[]{"+1 " + Task.RESOURCE +  " space"}, "Expand the colony");
        instance.addAndSetCurrentTask(newTask);
        instance.setTaskTimerFromCurrentTask();
        assertEquals(1, instance.getTaskTimer());
    }

    /**
     * Test of setTaskTimer method, of class ActiveEntity.
     */
    @Test
    public void testSetTaskTimer() {
        ActiveEntity instance = new ActiveEntity();
        assertEquals(0, instance.getTaskTimer());
        
        instance.setTaskTimer(5);
        assertEquals(5, instance.getTaskTimer());
    }

    /**
     * Test of setTaskTimerFromCurrentTask method, of class ActiveEntity.
     */
    @Test
    public void testSetTaskTimerFromCurrentTask() {
        ActiveEntity instance = new ActiveEntity();
        instance.setTaskTimerFromCurrentTask();
        assertEquals(0, instance.getTaskTimer());
        
        Task newTask = new Task("Dig", 1, null, null, new String[]{"+1 " + Task.RESOURCE +  " space"}, "Expand the colony");
        instance.addAndSetCurrentTask(newTask);
        instance.setTaskTimerFromCurrentTask();
        assertEquals(1, instance.getTaskTimer());
        
        instance.setTaskTimer(-3);
        assertEquals(-3, instance.getTaskTimer());
        instance.setTaskTimerFromCurrentTask();
        assertEquals(1, instance.getTaskTimer());
    }

    /**
     * Test of getCurrentTask method, of class ActiveEntity.
     */
    @Test
    public void testGetCurrentTask() {
        ActiveEntity instance = new ActiveEntity();
        assertEquals(new Task(), instance.getCurrentTask());
        
        Task newTask = new Task("Dig", 1, null, null, new String[]{"+1 " + Task.RESOURCE +  " space"}, "Expand the colony");
        instance.addAndSetCurrentTask(newTask);
        assertEquals(newTask, instance.getCurrentTask());
        assertFalse(instance.getCurrentTask().equals(new Task()));
        
        newTask.setName("foobar");
        assertEquals(newTask, instance.getCurrentTask());
        assertFalse(instance.getCurrentTask().equals(new Task()));
        
        newTask = new Task();
        assertFalse(instance.getCurrentTask().equals(newTask));
    }

    /**
     * Test of setCurrentTask method, of class ActiveEntity.
     */
    @Test
    public void testSetCurrentTask_int() {
        ActiveEntity instance = new ActiveEntity();
        assertTrue(instance.getTasks().isEmpty());
        assertFalse(instance.isBusy());
        instance.setCurrentTask(0);
        assertTrue(instance.getTasks().isEmpty());
        assertFalse(instance.isBusy());
        
        ArrayList<Task> testTasks = new ArrayList();
        instance = new ActiveEntity("test", "Tester", null, testTasks, null);
        instance.setCurrentTask(0);
        assertTrue(instance.getTasks().isEmpty());
        assertFalse(instance.isBusy());
        
        testTasks.add(new Task());
        testTasks.add(new Task("Dig", 1, null, null, null, "Expand the colony"));
        instance = new ActiveEntity("test", "Tester", null, testTasks, null);
        instance.setCurrentTask(0);
        assertFalse(instance.getTasks().isEmpty());
        assertTrue(instance.isBusy());
        assertTrue(instance.getCurrentTask().getName().equals("no task"));
        instance.setCurrentTask(1);
        assertTrue(instance.isBusy());
        assertTrue(instance.getCurrentTask().getName().equals("Dig"));
        instance.setCurrentTask(3);
        assertTrue(instance.getCurrentTask().getName().equals("Dig"));
        instance.setCurrentTask(-3);
        assertTrue(instance.getCurrentTask().getName().equals("Dig"));
    }

    /**
     * Test of setCurrentTask method, of class ActiveEntity.
     */
    @Test
    public void testSetCurrentTask_Task() {
        Task newTask = null;
        ActiveEntity instance = new ActiveEntity();
        assertTrue(instance.getTasks().isEmpty());
        assertFalse(instance.isBusy());
        instance.setCurrentTask(newTask);
        assertTrue(instance.getTasks().isEmpty());
        assertFalse(instance.isBusy());
        
        ArrayList<Task> testTasks = new ArrayList();
        testTasks.add(new Task());
        testTasks.add(new Task("Dig", 1, null, null, null, "Expand the colony"));
        instance = new ActiveEntity("test", "Tester", null, testTasks, null);
        
        instance.setCurrentTask(newTask);
        assertFalse(instance.isBusy());
        
        instance.setCurrentTask(new Task());
        assertTrue(instance.isBusy());
        assertTrue(instance.getCurrentTask().getName().equals("no task"));
        
        instance.setCurrentTask(new Task("Dig", 1, null, null, null, "Expand the colony"));
        assertTrue(instance.isBusy());
        assertTrue(instance.getCurrentTask().getName().equals("Dig"));
        
        instance.setCurrentTask(new Task("foobar", 1, null, null, null, ""));
        assertTrue(instance.isBusy());
        assertTrue(instance.getCurrentTask().getName().equals("Dig"));
    }

    /**
     * Test of addAndSetCurrentTask method, of class ActiveEntity.
     */
    @Test
    public void testAddAndSetCurrentTask() {
        Task newTask = null;
        ActiveEntity instance = new ActiveEntity();
        assertFalse(instance.isBusy());
        instance.addAndSetCurrentTask(newTask);
        assertFalse(instance.isBusy());
        assertTrue(instance.getTasks().isEmpty());
        
        instance.addAndSetCurrentTask(new Task());
        assertTrue(instance.isBusy());
        assertTrue(instance.getCurrentTask().getName().equals("no task"));
        assertTrue(instance.getTasks().size() == 1);
        
        instance.addAndSetCurrentTask(new Task("Dig", 1, null, null, null, "Expand the colony"));
        assertTrue(instance.getCurrentTask().getName().equals("Dig"));
        assertTrue(instance.getTasks().size() == 2);
        
        instance.addAndSetCurrentTask(new Task());
        assertTrue(instance.isBusy());
        assertTrue(instance.getCurrentTask().getName().equals("no task"));
        assertTrue(instance.getTasks().size() == 2);
    }

    /**
     * Test of getTaskCompleted method, of class ActiveEntity.
     */
    @Test
    public void testGetTaskCompleted() {
        ActiveEntity instance = new ActiveEntity();
        assertFalse(instance.getTaskCompleted());
        
        ArrayList<Task> testTasks = new ArrayList();
        testTasks.add(new Task());
        testTasks.add(new Task("Dig", 1, null, null, null, "Expand the colony"));
        instance = new ActiveEntity("test", "Tester", null, testTasks, null);
        assertFalse(instance.getTaskCompleted());
        instance.setCurrentTask(1);
        assertFalse(instance.getTaskCompleted());
        instance.completeTask();
        assertTrue(instance.getTaskCompleted());
    }


    /**
     * Test of setTaskCompleted method, of class ActiveEntity.
     */
    @Test
    public void testSetTaskCompleted() {
        ActiveEntity instance = new ActiveEntity();
        assertFalse(instance.getTaskCompleted());
        instance.setTaskCompleted(false);
        assertFalse(instance.getTaskCompleted());
        instance.setTaskCompleted(true);
        assertTrue(instance.getTaskCompleted());
    }

    /**
     * Test of idle method, of class ActiveEntity.
     */
    @Test
    public void testIdle() {
        ActiveEntity instance = new ActiveEntity();
        instance.idle();
        //Expecting nothing!
    }

    /**
     * Test of entityUpdate method, of class ActiveEntity.
     */
    @Test
    public void testEntityUpdate() {
        ActiveEntity instance = new ActiveEntity();
        assertTrue(instance.getTaskTimer() == 0);
        instance.entityUpdate(null);
        assertTrue(instance.getTaskTimer() == 0);
        
        instance.addAndSetCurrentTask(new Task("Dig", 2, null, null, null, "Expand the colony"));
        instance.setTaskTimerFromCurrentTask();
        assertFalse(instance.getTaskCompleted());
        assertTrue(instance.getTaskTimer() == 2);
        assertTrue(instance.getCurrentTask().getName().equals("Dig"));
        instance.entityUpdate(null);
        assertFalse(instance.getTaskCompleted());
        assertTrue(instance.getTaskTimer() == 1);
        assertTrue(instance.getCurrentTask().getName().equals("Dig"));
        instance.entityUpdate(null);
        assertFalse(instance.getTaskCompleted());
        assertTrue(instance.getTaskTimer() == 0);
        assertTrue(instance.getCurrentTask().getName().equals("Dig"));
        instance.entityUpdate(null);
        assertTrue(instance.getTaskCompleted());
        assertTrue(instance.getTaskTimer() == 0);
        assertTrue(instance.getCurrentTask().getName().equals("no task"));
    }

    /**
     * Test of completeTask method, of class ActiveEntity.
     */
    @Test
    public void testCompleteTask() {
        ActiveEntity instance = new ActiveEntity();
        assertFalse(instance.getTaskCompleted());
        instance.completeTask();
        assertTrue(instance.getTaskCompleted());
        instance.completeTask();
        assertTrue(instance.getTaskCompleted());
    }

    /**
     * Test of cancelTask method, of class ActiveEntity.
     */
    @Test
    public void testCancelTask() {
        ActiveEntity instance = new ActiveEntity();
        assertTrue(instance.getCurrentTask().getDuration() == 0);
        assertTrue(instance.getCurrentTask().isNoTask());
        assertFalse(instance.isBusy());
        assertFalse(instance.getTaskCompleted());
 
        instance.cancelTask();
        assertTrue(instance.getCurrentTask().getDuration() == 0);
        assertTrue(instance.getCurrentTask().isNoTask());
        assertFalse(instance.isBusy());
        assertFalse(instance.getTaskCompleted());
        
        instance.addAndSetCurrentTask(new Task());
        assertTrue(instance.getCurrentTask().isNoTask());
        assertTrue(instance.isBusy());
        assertFalse(instance.getTaskCompleted());
        
        instance.cancelTask();
        assertTrue(instance.getCurrentTask().getDuration() == 0);
        assertTrue(instance.getCurrentTask().isNoTask());
        assertFalse(instance.isBusy());
        assertFalse(instance.getTaskCompleted());
        
        instance.addAndSetCurrentTask(new Task("Dig", 1, null, null, null, "Expand the colony"));
        assertTrue(instance.getCurrentTask().getDuration() == 1);
        assertFalse(instance.getCurrentTask().isNoTask());
        assertTrue(instance.isBusy());
        assertFalse(instance.getTaskCompleted());
        
        instance.cancelTask();
        assertTrue(instance.getCurrentTask().getDuration() == 0);
        assertTrue(instance.getCurrentTask().isNoTask());
        assertFalse(instance.isBusy());
        assertFalse(instance.getTaskCompleted());
    }
    
}