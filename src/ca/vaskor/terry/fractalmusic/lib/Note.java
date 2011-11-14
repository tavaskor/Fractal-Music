package ca.vaskor.terry.fractalmusic.lib;


/**
 * 
 * Represents a MIDI note, encapsulating the pitch and duration of the note.
 * 
 * @author Terry Vaskor
 * 
 */
public class Note {
    /**
     * 
     * @param pitch    The pitch of the note
     * @param duration The duration of the note
     */
    public Note(MIDIPitch pitch, Duration duration) {
        note = pitch;
        length = duration;
    }

    /**
     * 
     * @return The pitch of the note.
     */
    public MIDIPitch getPitch() {
        return note;
    }

    /**
     * 
     * @return The duration of the note.
     */
    public Duration getDuration() {
        return length;
    }
        
    /**
     * 
     * @return A representation of the note that provides all stored
     *         information about it in a convenient human-readable format.
     */
    @Override
    public String toString() {
        return note.toString() + " " + length.toString();
    }

    private MIDIPitch note;
    private Duration length;
} 
