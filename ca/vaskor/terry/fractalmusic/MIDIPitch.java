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
    
    public MIDIPitch(PitchName name, int octave) throws OutOfMIDIRangeException {
        this((octave * 12) + nameToMidiOffset.get(name));
    }
    
    public MIDIPitch(int midiNumber) throws OutOfMIDIRangeException {
        midiCode = midiNumber;
        
        if ((midiCode < 0) || (midiCode > 127)) {
            throw new OutOfMIDIRangeException(this);
        }
    }
    
    
    public int getMIDICode() {
        return midiCode;
    }
    
    @Override
    public String toString() {
        PitchName scaleNote = midiOffsetToName.get(midiCode % 12);
        return getNamePortion(scaleNote) + (midiCode / 12);
    }
    
    private String getNamePortion(PitchName pName) {
        if (pName == PitchName.C_SHARP) return "C#";
        if (pName == PitchName.D_SHARP) return "D#";
        if (pName == PitchName.F_SHARP) return "F#";
        if (pName == PitchName.G_SHARP) return "G#";
        if (pName == PitchName.A_SHARP) return "A#"; // "B\u266D"?
        return pName.toString();
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
    
    private static final HashMap<Integer, PitchName> midiOffsetToName = getMidiToNameMap();
    private static EnumMap<PitchName, Integer> nameToMidiOffset = getNameToMidiMap();
}
