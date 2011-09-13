public class NoteRangeRestrictor {
	public NoteRangeRestrictor(int lowPitch, int highPitch, int lowLength, int highLength) throws OutOfMIDIRangeException, IllegalNoteLengthException {
		if (outOfMIDIRange(lowPitch)) {
			throw new OutOfMIDIRangeException(lowPitch);
		} else if (outOfMIDIRange(highPitch)) {
			throw new OutOfMIDIRangeException(highPitch);
		} else if (invalidNoteLength(lowLength)) {
			throw new IllegalNoteLengthException(lowLength);
		} else if (invalidNoteLength(highLength)) {
			throw new IllegalNoteLengthException(highLength);
		}
		lowestPitch = lowPitch;
		highestPitch = highPitch;
		lowestLength = lowLength;
		highestLength = highLength;
	}

	public int getLowPitch() { return lowestPitch; }
	public int getHighPitch() { return highestPitch; }
	public int getLongestLength() { return lowestLength; }
	public int getShortestLength() { return highestLength; }

	private boolean outOfMIDIRange(int pitch) {
		return ( (pitch < 0) || (pitch > 127) );
	}

	private boolean invalidNoteLength(int length) {
		int checkLength = 1;
		for (int powerOfTwo = 0; powerOfTwo < 8; powerOfTwo++) {
//			System.out.println("Length: " + length + "; Check: "
//					+ checkLength);
			if (length == checkLength) {
				return false;
			}
			checkLength *= 2;
		}
		return true;
	}

	private int lowestPitch;
	private int highestPitch;
	private int lowestLength;
	private int highestLength;
}
