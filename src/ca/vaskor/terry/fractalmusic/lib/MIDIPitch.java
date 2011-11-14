/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

import java.util.HashMap;
import java.util.EnumMap;

import java.util.List;
import java.util.ArrayList;

/**
 * A container representing a valid pitch according to the MIDI specification.
 * 
 * The pitch can be indexed either by the raw MIDI number, or by a combination
 * of {@link PitchName} and octave offset.
 * 
 * @author Terry Vaskor
 */
public class MIDIPitch implements Comparable<MIDIPitch> {
    /**
     * Construct a new MIDIPitch using a raw MIDI number.
     * 
     * @param midiNumber The number of the MIDI pitch according to the MIDI specification.
     */
    private MIDIPitch(int midiNumber) {
        midiCode = midiNumber;
    }
    
    /**
     * Obtain a MIDIPitch given the name of the pitch and an octave offset.
     * 
     * @param name   The name of the pitch
     * @param octave The octave in which the pitch will sound
     * @exception OutOfMIDIRangeException if the combination of pitch name
     *     and octave would result in an invalid pitch according to the
     *     MIDI specification
     */
    public static MIDIPitch getMIDIPitch(PitchName name, int octave) throws OutOfMIDIRangeException {
        try {
            return getMIDIPitch((octave * NOTES_IN_OCTAVE) + nameToMidiOffset.get(name));
        } catch (OutOfMIDIRangeException exception) {
            // Create a more meaningful exception (for the given parameters)
            // than one with simply a MIDI code.
            throw new OutOfMIDIRangeException(name, octave);
        }
    }
    
    /**
     * Obtain a MIDIPitch given a raw MIDI number.
     * 
     * @param midiCode The number of the pitch according to the MIDI specification.
     * @exception OutOfMIDIRangeException if the pitch number is not in the range
     *     allowed by the MIDI specification.
     */
    public static MIDIPitch getMIDIPitch(int midiCode) throws OutOfMIDIRangeException {
        
        if ((midiCode < LOWEST_VALID_MIDI_CODE) || 
                (midiCode > HIGHEST_VALID_MIDI_CODE)) {
            throw new OutOfMIDIRangeException(midiCode);
        }
        
        return allPitches.get(midiCode);
    }
    
    
    
    
    /**
     * 
     * @return The raw MIDI number for this pitch.
     */
    public int getMIDICode() {
        return midiCode;
    }
    
    /**
     * 
     * @return The pitch name, stripped of any octave context.
     */
    public PitchName getScaleNote() {
        return midiOffsetToName.get(midiCode % NOTES_IN_OCTAVE);
    }
    
    /**
     * 
     * @return The octave within which this pitch lies.
     */
    public int getOctave() {
        return midiCode / NOTES_IN_OCTAVE;
    }
    
    
    /**
     * 
     * @return A string representation consisting of the pitch name 
     *     concatenated with the octave number.
     */
    @Override
    public String toString() {
        return getScaleNote().toString() + getOctave();
    }
    
    
    @Override
    public int compareTo(MIDIPitch other) {
        return (new Integer(this.getMIDICode())).compareTo(other.getMIDICode());
    }
    
    /**
     * 
     * @param end1 The pitch at the bottom end of the desired range.
     * @param end2 The pitch at the top end of the desired range.
     * @return A list of all valid MIDIPitches between end1 and end2, inclusive.
     */
    public static List<MIDIPitch> getRange(MIDIPitch end1, MIDIPitch end2) {
        if (end2.compareTo(end1) < 0) { return getRange(end2, end1); }
        
        return allPitches.subList(end1.getMIDICode(), end2.getMIDICode()+ 1);
    }
    
    
    private int midiCode;
    
    
    private static HashMap<Integer, PitchName> getMidiToNameMap() {
        HashMap<Integer, PitchName> carrot = new HashMap<Integer, PitchName>();
        
        int i = 0;
        for (PitchName p : PitchName.values()) {
            carrot.put(i, p);
            i++;
        }
        
        return carrot;
    }
    
    private static EnumMap<PitchName, Integer> getNameToMidiMap() {
        EnumMap<PitchName, Integer> celery = new EnumMap<PitchName, Integer>(PitchName.class);
        
        int i = 0;
        for (PitchName p : PitchName.values()) {
            celery.put(p, i);
            i++;
        }
        
        return celery;
    }
    
    
            
    
    private static final int NOTES_IN_OCTAVE = 12;
    private static final int LOWEST_VALID_MIDI_CODE = 0;
    private static final int HIGHEST_VALID_MIDI_CODE = 127;
    
    
    private static final HashMap<Integer, PitchName> midiOffsetToName = getMidiToNameMap();
    private static final EnumMap<PitchName, Integer> nameToMidiOffset = getNameToMidiMap();
    
    
    // Used to keep all MIDIPitches resident in memory.
    // As this is fixed at 127, it should be an acceptable hit,
    // and significantly reduce the garbage collector's workload.
    private static List<MIDIPitch> getAllMIDIPitches() {
        ArrayList<MIDIPitch> returnContainer = new ArrayList<MIDIPitch>(
                HIGHEST_VALID_MIDI_CODE - LOWEST_VALID_MIDI_CODE + 1
                );
        
        for (int i = LOWEST_VALID_MIDI_CODE; i <= HIGHEST_VALID_MIDI_CODE; i++) {
            returnContainer.add(new MIDIPitch(i));
        }
        
        return returnContainer;
    }
    private static final List<MIDIPitch> allPitches = getAllMIDIPitches();
    
    // Store the lowest and highest pitches in a static, public-accessible way.    
    public static final MIDIPitch LOWEST_PITCH = allPitches.get(LOWEST_VALID_MIDI_CODE);
    public static final MIDIPitch HIGHEST_PITCH = allPitches.get(HIGHEST_VALID_MIDI_CODE);
}
