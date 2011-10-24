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

import java.util.Collection;
import java.util.ArrayList;

/**
 *
 * @author tavaskor
 */
public class DiceDataUtilTest {
    
    public DiceDataUtilTest() {
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
    
    private static java.util.Random testRandom = new java.util.Random();
    
    
    private int totalMaximum(Collection<AbstractDice> orkOrk) {
        int total = 0;
        for (AbstractDice die : orkOrk) {
            total += die.maximumRollableValue();
        }
        return total;
    }
    
    /**
     * Test of getDice method, of class DiceDataUtil.
     */
    @Test
    public void testGetDice_properMaximum() {
        System.out.println("getDice");
        for (int rangeSize = 1; rangeSize < 100; rangeSize++) {
            for (int numberOfDice = 1; numberOfDice < 100; numberOfDice++) {
                Collection result = DiceDataUtil.getDice(rangeSize, numberOfDice, testRandom);
                assertEquals(
                        "Total of maximum value rollable on all dice should be the provided maximum range size, independent of number of dice",
                        rangeSize,
                        totalMaximum(result)
                        );
            }
        }
    }

    /**
     * Test of getFlippedBits method, of class DiceDataUtil.
     */
    @Test
    public void testGetFlippedBits() {
        System.out.println("getFlippedBits");
        
        Collection<Integer> sameTest = DiceDataUtil.getFlippedBits(5, 5);
        Collection<Integer> expResult = new ArrayList<Integer>();
        assertEquals("Two equal numbers should have no bits different", expResult, sameTest);
        
        Collection<Integer> oneOff = DiceDataUtil.getFlippedBits(0, 1);
        expResult.add(0);
        assertEquals("0 and 1 should differ in one bit", expResult, oneOff);
        
        Collection<Integer> twoOff = DiceDataUtil.getFlippedBits(11, 14);
        expResult.add(2);
        assertEquals("11 and 14 should differ in two bits", expResult, twoOff);
    }
}
