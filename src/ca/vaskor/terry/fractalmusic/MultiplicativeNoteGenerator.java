package ca.vaskor.terry.fractalmusic;

class MultiplicativeNoteGenerator extends DiceRollNoteGenerator {

	private static int getNumPitchDice(NoteRangeRestrictor nrr) throws InvalidDataSpreadException {
		int pitchDiff = nrr.getHighPitch() - nrr.getLowPitch() + 1;
		checkValueSpread(pitchDiff, "Pitch");
		return (int) (Math.log(pitchDiff) / Math.log(2));
	}

	private static int getNumLengthDice(NoteRangeRestrictor nrr) throws InvalidDataSpreadException {
		int lengthDiff =  Converter.noteLengthToConsecutiveInt( nrr.getShortestLength() ) - Converter.noteLengthToConsecutiveInt( nrr.getLongestLength()) + 1;
		checkValueSpread(lengthDiff, "Length");
		return (int) (Math.log(lengthDiff) / Math.log(2));
	}

	private static void checkValueSpread(int pitchDiff, String valuename) throws InvalidDataSpreadException {
                if ( (pitchDiff != 128) && (pitchDiff != 64) && (pitchDiff != 32)
                        && (pitchDiff != 16) && (pitchDiff != 8) &&
                        (pitchDiff != 4) && (pitchDiff != 2) && (pitchDiff != 1) ) {
                        throw new InvalidDataSpreadException(valuename + " spread " + pitchDiff);
                }
        }

	private void setMinimums(NoteRangeRestrictor nrr) {
		lowestPitch = nrr.getLowPitch();
		lowestConsecutiveLength = Converter.noteLengthToConsecutiveInt( nrr.getLongestLength());
	}

	public MultiplicativeNoteGenerator(NoteRangeRestrictor nrr, long randomSeed) throws InvalidDataSpreadException {
		super(getNumPitchDice(nrr), 1, getNumLengthDice(nrr), 1, randomSeed);
		setMinimums(nrr);
	}

	protected int calculateNextPitch() {
		return calculateReturnValue(pitchDiceValue) + lowestPitch;
	}

	protected int calculateNextLength() {
		return Converter.consecutiveIntToNoteLength( calculateReturnValue(lengthDiceValue) + lowestConsecutiveLength );
	}

	private int calculateReturnValue(java.util.Vector<Integer> pizza) {
		int returnValue = 0;
		for (int i = 0; i < pizza.size(); i++) {
			if ( ( (Integer) pizza.elementAt(i) ).intValue() == 1) {
				int toAdd = 1;
				for (int j = 0; j < i; j++) {
					toAdd *= 2;
				}
				returnValue += toAdd;
			}
		}
		return returnValue;
	}


	private int lowestPitch;
	private int lowestConsecutiveLength;

	/*
        public static void main(String args[]) throws Exception {
                NoteGenerator noteGen = new MultiplicativeNoteGenerator(
                        new NoteRangeRestrictor(32, 95, 1, 128),
                        1258);
                MIDISequenceCreator msc = new MIDISequenceCreator(noteGen, 130);

                javax.sound.midi.Sequence seq = msc.getSequence();
                javax.sound.midi.Sequencer seqer = javax.sound.midi.MidiSystem.getSequencer();
                seqer.open();
                seqer.setSequence(seq);
                seqer.start();
                while (seqer.isRunning()) {}
                seqer.stop();
                System.exit(0);
        }
*/
}
