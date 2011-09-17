package ca.vaskor.terry.fractalmusic;

public class IllegalNoteLengthException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6530505585718532793L;

	public IllegalNoteLengthException(int offendingValue) {
		super("" + offendingValue);
	}
}
