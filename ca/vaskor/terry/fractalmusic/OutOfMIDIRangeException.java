package ca.vaskor.terry.fractalmusic;

public class OutOfMIDIRangeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5362073644721848311L;

	public OutOfMIDIRangeException(int value) {
		super("" + value);
	}
	public OutOfMIDIRangeException(MIDIPitch value) {
		super("" + value);
	}
}
