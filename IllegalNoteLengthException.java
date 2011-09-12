public class IllegalNoteLengthException extends Exception {
	public IllegalNoteLengthException(int offendingValue) {
		super("" + offendingValue);
	}
}
