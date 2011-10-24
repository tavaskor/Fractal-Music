/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;

/**
 *
 * @author tavaskor
 */
public class NSidedDieTest {
    
    public NSidedDieTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    private java.util.Random randGen = new java.util.Random();
    
    /**
     * Test of numberOfSides method, of class Dice.
     */
    @Test
    public void testMaximumRollableValue() {
        System.out.println("maximumRollableValue");
        int[] testSizes = { 1, 2, 500 };
        for (int i : testSizes) {
            NSidedDie instance = new NSidedDie(i, randGen);
            assertEquals("Maximum value should be one less than the number of provided sides", i-1, instance.maximumRollableValue());
        }
    }
    
    /**
     * Test of roll method, of class Dice.
     */
    @Test
    public void testRollAndGet() {
        System.out.println("roll, get");
        for (int i = 0; i < 100; i++) {
            getWithValue(i);
        }
    }
    
    private void getWithValue(final int num_sides) {
        NSidedDie instance = new NSidedDie(num_sides, randGen);
        DiceTesterCommon.runDiceTest(instance);
    }
}
