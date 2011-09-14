package ca.vaskor.terry.fractalmusic;

public class ReflectingBrownNoiseGenerator extends BrownNoiseGenerator {
	public ReflectingBrownNoiseGenerator(
                NoteRangeRestrictor nrr, long randomSeed, 
                int lowestPitchChange, int highestPitchChange,
                int lowestLengthStep, int highestLengthStep)
                throws OutOfMIDIRangeException {
            super(nrr, randomSeed, lowestPitchChange, highestPitchChange, lowestLengthStep, highestLengthStep);
	}

	protected Note calculateNextNote(Note baseNote) throws OutOfMIDIRangeException {
		int newPitch = calculateChange( baseNote.getPitch().getMIDICode(),
			getLowestPitchChange(), getHighestPitchChange(),
			restrictor.getLowPitch(), restrictor.getHighPitch() );

		int baseConLen = Converter.noteLengthToConsecutiveInt( baseNote.getDuration().getNumericDenominator() );
		int longestConLen = Converter.noteLengthToConsecutiveInt( restrictor.getLongestLength() );
		int shortestConLen = Converter.noteLengthToConsecutiveInt( restrictor.getShortestLength() );
		int newConLen = calculateChange( baseConLen, 
			getLowestConsecutiveLengthChange(),
			getHighestConsecutiveLengthChange(),
			longestConLen, shortestConLen );
		int newLength = Converter.consecutiveIntToNoteLength(newConLen);
		return new Note(new MIDIPitch(newPitch), 
                        Duration.durationFromDenominator(newLength));
	}

	private int calculateChange(int startVal, int maxNegChange,
			int maxPosChange, int minVal, int maxVal) {
		int changeAmount = getNextInt(maxPosChange - maxNegChange + 1) + maxNegChange;
		int newVal = startVal + changeAmount;
		if ( (newVal < minVal) || (newVal > maxVal) ) {
			newVal = startVal - changeAmount;
			if ( (newVal < minVal) || (newVal > maxVal) ) {
				newVal = startVal;
			}
		}

		return newVal;
	}	


/*
        public static void main(String args[]) throws Exception {
                NoteGenerator noteGen = new ReflectingBrownNoiseGenerator(
                        new NoteRangeRestrictor(35, 85, 2, 64),
                        1099, -1, 1, -1, 1);
//                        1099, -1, 1, -1, 1);
//                        1199, -1, 1, -1, 1);
                MIDISequenceCreator msc = new MIDISequenceCreator(noteGen, 180);
                                                                                
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
