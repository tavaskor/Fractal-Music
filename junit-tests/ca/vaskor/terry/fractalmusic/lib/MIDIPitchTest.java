/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

/*
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
 */
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 *
 * @author tavaskor
 */
public class MIDIPitchTest {
    
    public MIDIPitchTest() {
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
     * Assumption checks: The static portion managed to create
     *    lowest note and highest note correctly.
     */
    @Test
    public void testLowestPitchConstruction() throws OutOfMIDIRangeException {
        System.out.println("MIDIPitch.LOWEST_PITCH was constructed as expected");
        MIDIPitch instance = MIDIPitch.LOWEST_PITCH;
        int expCode = 0;
        PitchName expPitch = PitchName.C;
        int expOctave = 0;
        String expString = "C0";
        assertNotNull(instance);
        assertEquals(expCode, instance.getMIDICode());
        assertEquals(expPitch, instance.getScaleNote());
        assertEquals(expOctave, instance.getOctave());
        assertEquals(expString, instance.toString());
    }
    @Test
    public void testHighestPitchConstruction() throws OutOfMIDIRangeException {
        System.out.println("MIDIPitch.HIGHEST_PITCH was constructed as expected");
        MIDIPitch instance = MIDIPitch.HIGHEST_PITCH;
        int expCode = 127;
        PitchName expPitch = PitchName.G;
        int expOctave = 10;
        String expString = "G10";
        assertNotNull(instance);
        assertEquals(expCode, instance.getMIDICode());
        assertEquals(expPitch, instance.getScaleNote());
        assertEquals(expOctave, instance.getOctave());
        assertEquals(expString, instance.toString());
    }
    
    private MIDIPitch getMIDIPitch(int midiCode) throws OutOfMIDIRangeException {
        return MIDIPitch.getMIDIPitch(midiCode);
    }
    
    private MIDIPitch getMIDIPitch(PitchName note, int octave) throws OutOfMIDIRangeException {
        return MIDIPitch.getMIDIPitch(note, octave);
    }
    
    /** 
     * Test 1 below lowest range of constructor, in both formats.
     */
    @Test(expected=OutOfMIDIRangeException.class)
    public void testLowMIDICode() throws OutOfMIDIRangeException {
        System.out.println("Code -1 fails");
        getMIDIPitch(-1);
    }
    @Test(expected=OutOfMIDIRangeException.class)
    public void testLowPitch() throws OutOfMIDIRangeException {
        System.out.println("Note B in octave -1 fails");
        getMIDIPitch(PitchName.B, -1);
    }
    
    /** 
     * Test 1 above highest range of constructor, in both formats.
     */
    @Test(expected=OutOfMIDIRangeException.class)
    public void testHighMIDICode() throws OutOfMIDIRangeException {
        System.out.println("Code 128 fails");
        getMIDIPitch(128);
    }
    @Test(expected=OutOfMIDIRangeException.class)
    public void testHighPitch() throws OutOfMIDIRangeException {
        System.out.println("Note G# in octave 10 fails");
        getMIDIPitch(PitchName.G_SHARP, 10);
    }
    
    /**
     * Test of getMIDICode method, of class MIDIPitch.
     */
    @Test
    public void testGetMIDICode() throws OutOfMIDIRangeException {
        final int TEST_NUMBER = 32;
        System.out.println("getMIDICode");
        MIDIPitch instance = getMIDIPitch(TEST_NUMBER);
        int expResult = TEST_NUMBER;
        int result = instance.getMIDICode();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getScaleNote method, of class MIDIPitch.
     */
    @Test
    public void testgetScaleNote() throws OutOfMIDIRangeException {
        final PitchName TEST_PITCH = PitchName.F;
        final int TEST_OCTAVE = 6;
        System.out.println("testgetScaleNote");
        MIDIPitch instance = getMIDIPitch(TEST_PITCH, TEST_OCTAVE);
        assertEquals(TEST_PITCH, instance.getScaleNote());
    }
    
    /**
     * Test of getMIDICode method, of class MIDIPitch.
     */
    @Test
    public void testgetOctave() throws OutOfMIDIRangeException {
        final PitchName TEST_PITCH = PitchName.F;
        final int TEST_OCTAVE = 6;
        System.out.println("testgetOctave");
        MIDIPitch instance = getMIDIPitch(TEST_PITCH, TEST_OCTAVE);
        assertEquals(TEST_OCTAVE, instance.getOctave());
    }
    
    /**
     * Test of toString method, for white note, of class MIDIPitch.
     */
    @Test
    public void testToString() throws OutOfMIDIRangeException {
        System.out.println("toString: C5");
        MIDIPitch instance = getMIDIPitch(PitchName.C, 5);
        String expResult = "C5";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of toString method, for expected-sharp black note, of class MIDIPitch.
     */
    @Test
    public void testToStringBlackSharp() throws OutOfMIDIRangeException {
        System.out.println("toString: F" + PitchName.SHARP + "5");
        MIDIPitch instance = getMIDIPitch(PitchName.F_SHARP, 5);
        String expResult = "F" + PitchName.SHARP + "5";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of toString method, for expected-flat black note, of class MIDIPitch.
     */
    @Test
    public void testToStringBlackFlat() throws OutOfMIDIRangeException {
        System.out.println("toString: B" + PitchName.FLAT + "5");
        MIDIPitch instance = getMIDIPitch(PitchName.A_SHARP, 5);
        String expResult = "B" + PitchName.FLAT + "5";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getRange; expected pitches, expected number.
     */
    @Test
    public void testgetRange() throws OutOfMIDIRangeException {
        final int lowMIDICode = 36;
        final int highMIDICode = 44;
        
        List<MIDIPitch> testRange = MIDIPitch.getRange(
                getMIDIPitch(lowMIDICode), 
                getMIDIPitch(highMIDICode)
                );
        
        int index = 0;
        for (; index < highMIDICode - lowMIDICode + 1; ++index) {
            assertEquals(index + lowMIDICode, testRange.get(index).getMIDICode());
        }
        // We should now be "looking at" one above the requested range.
        assertEquals(highMIDICode + 1, index + lowMIDICode);
        try {
            testRange.get(index);
            fail("Should have triggered IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException exception) {
        }
    }

    /**
     * Test of toString method, of class MIDIPitch.
     */
    /*
    @Test
    public void testToString() {
        System.out.println("toString");
        MIDIPitch instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */
}
