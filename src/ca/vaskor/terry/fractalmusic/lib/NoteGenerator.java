package ca.vaskor.terry.fractalmusic.lib;

public interface NoteGenerator {
	public Note getNextNote() throws OutOfMIDIRangeException;
}
