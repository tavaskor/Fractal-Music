public class OutOfMIDIRangeException extends Exception {
	public OutOfMIDIRangeException(int value) {
		super("" + value);
	}
}
