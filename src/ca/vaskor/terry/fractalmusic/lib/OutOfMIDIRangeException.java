package ca.vaskor.terry.fractalmusic.lib;

/**
 * 
 * This signifies that data was supplied that would create an invalid MIDI
 * pitch according to the MIDI specification.
 * 
 * @author Terry Vaskor
 */
public class OutOfMIDIRangeException extends javax.sound.midi.InvalidMidiDataException {
    private static final long serialVersionUID = 5362073644721848311L;
    
    /**
     * Creates an exception based on a numeric code for the pitch value.
     * 
     * @param midiCode The erroneous code that was provided.
     */
    public OutOfMIDIRangeException(int midiCode) {
        super("Attempt to create a MIDIPitch with value " + midiCode +
                "; valid range is " + MIDIPitch.LOWEST_PITCH.getMIDICode() +
                " to " + MIDIPitch.HIGHEST_PITCH.getMIDICode());
    }
    
    /**
     * Creates an exception based on a pitch name and octave number.
     * 
     * @param name   The pitch within the scale.
     * @param octave The octave in which the pitch occurs.
     */
    public OutOfMIDIRangeException(PitchName name, int octave) {
        super("Attempt to create the MIDIPitch " + name.toString() + octave +
                "; valid range is " + MIDIPitch.LOWEST_PITCH.toString() +
                " to " + MIDIPitch.HIGHEST_PITCH.toString());
    }
}
