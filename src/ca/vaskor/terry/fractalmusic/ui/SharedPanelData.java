/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;
    
import ca.vaskor.terry.fractalmusic.lib.MIDIPitch;
import ca.vaskor.terry.fractalmusic.lib.Duration;
import ca.vaskor.terry.fractalmusic.lib.ScaleType;
import ca.vaskor.terry.fractalmusic.lib.GeneralMIDIInstrument;

/**
 * A storage structure for selections in the GUI that apply to all types of music.
 * 
 * All fields are package scope as they are meant to be used internally in
 * the ui package only.
 * 
 * @author Terry Vaskor
 */
public class SharedPanelData {
    public SharedPanelData(
            MIDIPitch low, MIDIPitch high, Duration shortD, Duration longD, 
            GeneralMIDIInstrument instr, int vol,
            ScaleType scl, String seed, boolean seedEnabled) {
        lowPitch = low;
        highPitch = high;
        shortLength = shortD;
        longLength = longD;
        instrument = instr;
        volume = vol;
        scale = scl;
        randomSeed = seed;
        isSeedEnabled = seedEnabled;
    }
    
    final MIDIPitch             lowPitch;
    final MIDIPitch             highPitch;
    final Duration              shortLength;
    final Duration              longLength;
    final GeneralMIDIInstrument instrument;
    final int                   volume;
    final ScaleType             scale;
    final String                randomSeed;
    final boolean               isSeedEnabled;
}
