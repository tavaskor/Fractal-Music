/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic;

import java.util.List;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tavaskor
 */
public class DurationTest {
    
    public DurationTest() {
    }

    /*
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
     */


    /**
     * Test of getNumericDenominator method, of class Duration.
     */
    @Test
    public void testGetNumericDenominator() {
        System.out.println("getNumericDenominator");
        
        Duration instance = Duration.WHOLE;
        int expResult = 1;
        int result = instance.getNumericDenominator();
        assertEquals(expResult, result);
        
        instance = Duration.QUARTER;
        expResult = 4;
        result = instance.getNumericDenominator();
        assertEquals(expResult, result);
        
        instance = Duration.SIXTEENTH;
        expResult = 16;
        result = instance.getNumericDenominator();
        assertEquals(expResult, result);
    }

    /**
     * Test of durationFromDenominator method, of class Duration.
     */
    @Test
    public void testDurationFromDenominator() {
        System.out.println("durationFromDenominator");
        
        int denominator = 1;
        Duration expResult = Duration.WHOLE;
        Duration result = Duration.durationFromDenominator(denominator);
        assertEquals(expResult, result);
        
        denominator = 4;
        expResult = Duration.QUARTER;
        result = Duration.durationFromDenominator(denominator);
        assertEquals(expResult, result);
        
        denominator = 16;
        expResult = Duration.SIXTEENTH;
        result = Duration.durationFromDenominator(denominator);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Duration.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        
        Duration instance = Duration.WHOLE;
        String expResult = "Whole note";
        String result = instance.toString();
        assertEquals(expResult, result);
        
        instance = Duration.QUARTER;
        expResult = "1/4 note";
        result = instance.toString();
        assertEquals(expResult, result);
        
        instance = Duration.SIXTEENTH;
        expResult = "1/16 note";
        result = instance.toString();
        assertEquals(expResult, result);
    }
    
    private static List<Duration> getExpectedFullDurationList() {
        List expResult = new ArrayList<Duration>(Duration.values().length);
        expResult.add(Duration.ONE_HUNDRED_TWENTY_EIGHTH);
        expResult.add(Duration.SIXTY_FOURTH);
        expResult.add(Duration.THIRTY_SECOND);
        expResult.add(Duration.SIXTEENTH);
        expResult.add(Duration.EIGHTH);
        expResult.add(Duration.QUARTER);
        expResult.add(Duration.HALF);
        expResult.add(Duration.WHOLE);
        
        return expResult;        
    }

    /**
     * Test of getRange method, of class Duration, on the full possible range.
     */
    @Test
    public void testGetRangeFull() {
        System.out.println("getRange, all elements");
        Duration end1 = Duration.LOWEST_DURATION;
        Duration end2 = Duration.HIGHEST_DURATION;
        List<Duration> expResult = DurationTest.getExpectedFullDurationList();
        List result = Duration.getRange(end1, end2);
        assertEquals(expResult, result);
    }

    /**
     * Test of getRange method, of class Duration, on the full possible range, arguments reversed.
     */
    @Test
    public void testGetRangeFullReversed() {
        System.out.println("getRange, all elements, with arguments reversed");
        Duration end1 = Duration.HIGHEST_DURATION;
        Duration end2 = Duration.LOWEST_DURATION;
        List<Duration> expResult = DurationTest.getExpectedFullDurationList();
        List result = Duration.getRange(end1, end2);
        assertEquals(expResult, result);
    }

    /**
     * Test of getRange method, of class Duration, on a subrange.
     */
    @Test
    public void testGetRangePart() {
        System.out.println("getRange, sublist of elements");
        Duration end1 = Duration.SIXTEENTH;
        Duration end2 = Duration.HALF;
        
        List expResult = new ArrayList<Duration>();
        expResult.add(Duration.SIXTEENTH);
        expResult.add(Duration.EIGHTH);
        expResult.add(Duration.QUARTER);
        expResult.add(Duration.HALF);
        
        List result = Duration.getRange(end1, end2);
        assertEquals(expResult, result);
    }
}
