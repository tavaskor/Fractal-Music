package ca.vaskor.terry.fractalmusic.lib;

// Class to represent a note; takes values for pitch and note length

public class Note {
    public Note(MIDIPitch pitch, Duration duration) {
        note = pitch;
        length = duration;
    }

    public MIDIPitch getPitch() {
        return note;
    }

    public Duration getDuration() {
        return length;
    }
        
    @Override
    public String toString() {
        return note.toString() + " " + length.toString();
    }

    private MIDIPitch note;
    private Duration length;
} 
