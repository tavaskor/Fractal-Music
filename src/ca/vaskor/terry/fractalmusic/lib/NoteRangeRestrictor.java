package ca.vaskor.terry.fractalmusic.lib;

import java.util.List;
import java.util.ArrayList;

/**
 * Places restrictions on what notes can be generated based on high and low
 * pitch, long and short length, and a particular scale to use.  
 * 
 * Given these parameters, the restrictor then 
 * specifies notes according to ordered indices given those parameters.
 * 
 * For example, given a low pitch of C5, a high pitch of A5, and a scale type
 * of ScaleType.MAJOR, the pitches in order would be as follows.  Index 0 would
 * be C5, 1 would be D5, 2 would be E5, 3 would be F5, 4 would be G5, and 5
 * would be A5.
 * 
 * @author Terry Vaskor
 */
public class NoteRangeRestrictor {
    
    /**
     * Specify all restrictions that should be placed on the range of generated notes.
     * 
     * @param lowPitch    The lowest pitch that can be generated.
     * @param highPitch   The highest pitch that can be generated.
     * @param shortLength The shortest duration that can be generated.
     * @param longLength  The longest duration that can be generated.
     * @param scaleToUse  The scale to use to generate notes.
     */
    public NoteRangeRestrictor(
            MIDIPitch lowPitch, MIDIPitch highPitch, 
            Duration shortLength, Duration longLength,
            ScaleType scaleToUse
            ) {
        MIDIPitch nextPitch = lowPitch;
        int scaleIndex = -1;
        List<? extends Integer> scale = scaleToUse.getScaleDegrees();
        pitchRange = new ArrayList<MIDIPitch>();
        
        // Add notes based on the scale until we reach highPitch.
        while (nextPitch.compareTo(highPitch) <= 0) {
            pitchRange.add(nextPitch);
            scaleIndex = (scaleIndex + 1) % scale.size();
            int semitoneIncrease = scale.get(scaleIndex);
            try {
                nextPitch = MIDIPitch.getMIDIPitch(nextPitch.getMIDICode() + semitoneIncrease);
            } catch (OutOfMIDIRangeException exception) {
                // Looks like we jumped past the highest possible note, so break.
                break;
            }
        }
        lengthRange = Duration.getRange(shortLength, longLength);
    }

    /**
     * 
     * @return The number of pitches that can be generated.
     */
    public int getNumPitches() { return pitchRange.size(); }
    
    /**
     * 
     * @return The number of durations that can be generated.
     */
    public int getNumDurations() { return lengthRange.size(); }
    
    /**
     * 
     * @param index The index of the pitch to obtain.
     * @return      The pitch at that index.
     */
    public MIDIPitch getPitch(int index) { return pitchRange.get(index); }
    
    /**
     * 
     * @param index The index of the duration to obtain.
     * @return      The duration at that index.
     */
    public Duration getDuration(int index) { return lengthRange.get(index); }

    private List<MIDIPitch> pitchRange;
    private List<Duration> lengthRange;
}
