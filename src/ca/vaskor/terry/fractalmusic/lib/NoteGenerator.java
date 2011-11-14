package ca.vaskor.terry.fractalmusic.lib;

/**
 * An interface for something that will return an indefinite sequence of notes.
 * 
 * @author Terry Vaskor
 */
public interface NoteGenerator {
    /**
     * Obtain the next generated note in the sequence.
     * 
     * @return    The next note in the sequence.
     * @exception OutOfMIDIRangeException if the next note 
     *            would be invalid according to the MIDI specification.
     */
    public Note getNextNote() throws OutOfMIDIRangeException;
}
