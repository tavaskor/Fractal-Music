package ca.vaskor.terry.fractalmusic;

// Class to represent a note; takes values for pitch and note length

public class Note {
	public Note(int pitch, int duration) {
		note = pitch;
		length = duration;
	}

	public int getPitch() {
		return note;
	}

	public int getDuration() {
		return length;
	}

	private int note;
	private int length;
} 
