package ca.vaskor.terry.fractalmusic;

public class OutOfMIDIRangeException extends Exception {
    private static final long serialVersionUID = 5362073644721848311L;
    
    public OutOfMIDIRangeException(int midiCode) {
        super("Attempt to create a MIDIPitch with value " + midiCode +
                "; valid range is " + MIDIPitch.LOWEST_PITCH.getMIDICode() +
                " to " + MIDIPitch.HIGHEST_PITCH.getMIDICode());
    }
    public OutOfMIDIRangeException(PitchName name, int octave) {
        super("Attempt to create the MIDIPitch " + name.toString() + octave +
                "; valid range is " + MIDIPitch.LOWEST_PITCH.toString() +
                " to " + MIDIPitch.HIGHEST_PITCH.toString());
    }
}
