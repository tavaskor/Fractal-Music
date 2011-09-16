package ca.vaskor.terry.fractalmusic;

import java.util.List;

public class NoteRangeRestrictor {
    public NoteRangeRestrictor(
            MIDIPitch lowPitch, MIDIPitch highPitch, 
            Duration lowLength, Duration highLength
            ) {
        this(MIDIPitch.getRange(lowPitch, highPitch),
                Duration.getRange(lowLength, highLength));
    }
	public NoteRangeRestrictor(List<MIDIPitch> rangeOfPitches, List<Duration> rangeOfDurations) {
		pitchRange = rangeOfPitches;
                lengthRange = rangeOfDurations;
	}
        
        public int getNumPitches() { return pitchRange.size(); }
        public int getNumDurations() { return lengthRange.size(); }
        public MIDIPitch getPitch(int index) { return pitchRange.get(index); }
        public Duration getDuration(int index) { return lengthRange.get(index); }

	private List<MIDIPitch> pitchRange;
	private List<Duration> lengthRange;
}
