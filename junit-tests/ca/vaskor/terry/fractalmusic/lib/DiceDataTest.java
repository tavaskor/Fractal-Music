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

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 *
 * @author tavaskor
 */
public class DiceDataTest {
    
    public DiceDataTest() {
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
        // Make a *very* high number of test runs, so it is excessively
        // unlikely for every value not to be rolled.
        final int num_test_runs = upper_bound * 100;
        
        System.out.println("get: " + upper_bound);
        DiceData instance = new DiceData(upper_bound, NUM_DICE, new Random());
        
        
        System.out.println("  Subsequent calls do not modify results");
        int result1 = instance.get();
        int result2 = instance.get();
        int result3 = instance.get();
        assertEquals(result1, result2, result3);
        
        
        HashSet<Integer> seenResults = new HashSet<Integer>();
        System.out.println("  Only valid results, and *every* valid result, is returned");
        for (int i = 0; i < num_test_runs; i++) {
            instance.roll();
            assertTrue(instance.get() >= 0);
            assertTrue(instance.get() <= upper_bound);
            seenResults.add(instance.get());
        }
        
        for (int j = 0; j <= upper_bound; j++) {
            System.out.println("    " + j + " was a rolled value: " + seenResults.contains(j));
            assertTrue(seenResults.contains(j));
        }
    }
    
    /*
     * 
     */
    @Test
    public void testGetFlippedBits() {
        System.out.println("getFlippedBits");
        
        System.out.println("  Ensure two equal numbers have no bits different");
        List<Integer> sameTest = DiceData.getFlippedBits(5, 5);
        List<Integer> expResult = new ArrayList<Integer>();
        assertEquals(expResult, sameTest);
        
        System.out.println("  Two numbers with one bit different");
        List<Integer> oneOff = DiceData.getFlippedBits(0, 1);
        expResult.add(0);
        assertEquals(expResult, oneOff);
        
        System.out.println("  Two numbers with several bits different");
        List<Integer> twoOff = DiceData.getFlippedBits(11, 14);
        expResult.add(2);
        assertEquals(expResult, twoOff);
    }
}
