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
 *
 * @author tavaskor
 */
public class MIDIPitch implements Comparable<MIDIPitch> {
    
    private MIDIPitch(int midiNumber) {
        midiCode = midiNumber;
    }
    
    /**
     * 
     * @param name
     * @param octave
     * @throws OutOfMIDIRangeException
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
     * 
     * @param midiNumber
     * @throws OutOfMIDIRangeException
     */
    public static MIDIPitch getMIDIPitch(int midiCode) throws OutOfMIDIRangeException {
        
        if ((midiCode < LOWEST_VALID_MIDI_CODE) || 
                (midiCode > HIGHEST_VALID_MIDI_CODE)) {
            // TODO
            // Sometimes passing "this" inside a constructor triggers nullptr exceptions.
            // Fix OutOfMIDIRangeException to take raw data.
            throw new OutOfMIDIRangeException(midiCode);
        }
        
        return allPitches.get(midiCode);
    }
    
    
    
    
    /**
     * 
     * @return
     */
    public int getMIDICode() {
        return midiCode;
    }
    public PitchName getScaleNote() {
        return midiOffsetToName.get(midiCode % NOTES_IN_OCTAVE);
    }
    public int getOctave() {
        return midiCode / NOTES_IN_OCTAVE;
    }
    
    
    /**
     * 
     * @return
     */
    @Override
    public String toString() {
        return getScaleNote().toString() + getOctave();
    }
    
    @Override
    public int compareTo(MIDIPitch other) {
        return (new Integer(this.getMIDICode())).compareTo(other.getMIDICode());
    }
    
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
