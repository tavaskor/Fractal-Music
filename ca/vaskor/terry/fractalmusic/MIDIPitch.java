/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic;

import java.util.HashMap;
import java.util.EnumMap;

/**
 *
 * @author tavaskor
 */
public class MIDIPitch {
    
    /**
     * 
     * @param name
     * @param octave
     * @throws OutOfMIDIRangeException
     */
    public MIDIPitch(PitchName name, int octave) throws OutOfMIDIRangeException {
        try {
            constructMIDIPitch((octave * NOTES_IN_OCTAVE) + nameToMidiOffset.get(name));
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
    public MIDIPitch(int midiNumber) throws OutOfMIDIRangeException {
        constructMIDIPitch(midiNumber);
    }
    
    private void constructMIDIPitch(int midiNumber) throws OutOfMIDIRangeException {
        midiCode = midiNumber;
        
        if ((midiCode < LOWEST_VALID_MIDI_CODE) || 
                (midiCode > HIGHEST_VALID_MIDI_CODE)) {
            // TODO
            // Sometimes passing "this" inside a constructor triggers nullptr exceptions.
            // Fix OutOfMIDIRangeException to take raw data.
            throw new OutOfMIDIRangeException(midiNumber);
        }
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
    
    // Store the lowest and highest pitches in a static way.
    // Use a helper to bypass the constructor exceptions; something is seriously
    // wrong with this class if they fail.
    private static MIDIPitch constructStaticPitch(int num) {
        try {
            return new MIDIPitch(num);
        } catch (OutOfMIDIRangeException exception) {
            return null;
        }
    }
    
    public static final MIDIPitch LOWEST_PITCH = constructStaticPitch(LOWEST_VALID_MIDI_CODE);
    public static final MIDIPitch HIGHEST_PITCH = constructStaticPitch(HIGHEST_VALID_MIDI_CODE);
    
    private static final HashMap<Integer, PitchName> midiOffsetToName = getMidiToNameMap();
    private static EnumMap<PitchName, Integer> nameToMidiOffset = getNameToMidiMap();
}
