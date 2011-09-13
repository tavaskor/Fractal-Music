package ca.vaskor.terry.fractalmusic;

public class Converter {
	public static int noteLengthToConsecutiveInt(int noteLength) {
		return (int) ( Math.log(noteLength) / Math.log(2) );
	}

	// Need to emulate returning noteLEngth^2
	public static int consecutiveIntToNoteLength(int noteLength) {
		int retVal = 1;
		for (int i = 1; i < noteLength + 1; i++) {
			retVal *= 2;
		}
		return retVal;
	}

	public static final int TICKS_PER_QUARTER_NOTE = 64;

	public static int noteLengthToMIDITicks(int noteLength) throws IllegalNoteLengthException {
		int tpqn = TICKS_PER_QUARTER_NOTE;
		switch(noteLength) {
			case 1: return tpqn * 4;
			case 2: return tpqn * 2;
			case 4: return tpqn;
			case 8: return (int) (tpqn / 2);
			case 16: return (int) (tpqn / 4);
			case 32: return (int) (tpqn / 8);
			case 64: return (int) (tpqn / 16);
			case 128: return (int) (tpqn / 32);
		}
		throw new IllegalNoteLengthException(noteLength);
	}



	public static void main(String args[]) throws IllegalNoteLengthException{
		int noteLengths[] = new int[8];
		noteLengths[0] = 1;
		for (int i = 1; i < 8; i++) {
			noteLengths[i] = noteLengths[i - 1] * 2;
		}

		for (int i = 0; i < 8; i++) {
			System.out.println("noteLengthToConsecutiveInt(" + noteLengths[i] + "): " + noteLengthToConsecutiveInt(noteLengths[i]));
		}

		System.out.println();

		for (int i = 0; i <= 7; i++) {
			System.out.println("consecutiveIntToNoteLength(" + i + "): " + consecutiveIntToNoteLength(i));
		}

		System.out.println();

		for (int i = 0; i < 8; i++) {
			System.out.println("noteLengthToMIDITicks(" + noteLengths[i] + "): " + noteLengthToMIDITicks(noteLengths[i]));
		}
	}
}
