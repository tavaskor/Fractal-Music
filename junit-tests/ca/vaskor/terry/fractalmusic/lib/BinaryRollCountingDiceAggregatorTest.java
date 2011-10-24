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
import java.util.Random;

/**
 *
 * @author tavaskor
 */
public class BinaryRollCountingDiceAggregatorTest {
    
    public BinaryRollCountingDiceAggregatorTest() {
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
    
    /**
     * Test of get method, of class DiceData.
     */
    
    final int NUM_DICE = 3;
    @Test
    public void testGet() {
        // Ensure that the following cases are *all* tested:
        // that the number mod NUM_DICE === 0, number mod NUM_DICE === 1,
        // ... , number mod NUM_DICE === NUM_DICE - 1.
        getWithValue(1);
        getWithValue(5);
        getWithValue(11);
        getWithValue(18);
    }
    
    private void getWithValue(int upper_bound) {
        System.out.println("get: " + upper_bound);
        
        AbstractDice instance = new BinaryRollCountingDiceAggregator(upper_bound, NUM_DICE, new Random());
        DiceTesterCommon.runDiceTest(instance);
    }
}
