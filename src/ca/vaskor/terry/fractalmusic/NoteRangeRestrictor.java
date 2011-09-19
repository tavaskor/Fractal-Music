package ca.vaskor.terry.fractalmusic;

import java.util.List;
import java.util.ArrayList;

public class NoteRangeRestrictor {
    
    public NoteRangeRestrictor(
            MIDIPitch lowPitch, MIDIPitch highPitch, 
            Duration lowLength, Duration highLength,
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
        lengthRange = Duration.getRange(lowLength, highLength);
    }

    public int getNumPitches() { return pitchRange.size(); }
    public int getNumDurations() { return lengthRange.size(); }
    public MIDIPitch getPitch(int index) { return pitchRange.get(index); }
    public Duration getDuration(int index) { return lengthRange.get(index); }

    private List<MIDIPitch> pitchRange;
    private List<Duration> lengthRange;
}
