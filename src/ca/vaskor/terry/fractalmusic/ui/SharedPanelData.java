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
 *
 * @author tavaskor
 */

// Everything is package scope as it is meant for internal use only.
public class SharedPanelData {
    public SharedPanelData(
            MIDIPitch low, MIDIPitch high, Duration shortD, Duration longD, 
            GeneralMIDIInstrument instr,
            ScaleType scl, String seed, boolean seedEnabled) {
        lowPitch = low;
        highPitch = high;
        shortLength = shortD;
        longLength = longD;
        instrument = instr;
        scale = scl;
        randomSeed = seed;
        isSeedEnabled = seedEnabled;
    }
    
    final MIDIPitch             lowPitch;
    final MIDIPitch             highPitch;
    final Duration              shortLength;
    final Duration              longLength;
    final GeneralMIDIInstrument instrument;
    final ScaleType             scale;
    final String                randomSeed;
    final boolean               isSeedEnabled;
}
