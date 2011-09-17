package ca.vaskor.terry.fractalmusic;


// TODO:
// Eliminate  the remaining "ticks per" stuff... 
// probably belongs in a more general class that returns values 
// based on a given tempo setting.
public class Converter {
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
			System.out.println("noteLengthToMIDITicks(" + noteLengths[i] + "): " + noteLengthToMIDITicks(noteLengths[i]));
		}
	}
}
