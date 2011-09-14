package ca.vaskor.terry.fractalmusic;

import java.util.Vector;

class FractalNoiseGenerator extends RandomizedNoteGenerator implements NoteGenerator {
	public FractalNoiseGenerator(NoteRangeRestrictor nrr, long randomSeed) throws InvalidDataSpreadException, OutOfMIDIRangeException {
		super(nrr, randomSeed);

		// All ranges must be powers of 2
		int pitchDiff = nrr.getHighPitch() - nrr.getLowPitch() + 1;
		checkValueSpread(pitchDiff, "Pitch");

		int lengthDiff = Converter.noteLengthToConsecutiveInt( nrr.getShortestLength() ) - Converter.noteLengthToConsecutiveInt( nrr.getLongestLength() ) + 1;
		checkValueSpread(lengthDiff, "Length");

		// Now need to size vectors
		int pitchGapSize = (int) ( Math.log(pitchDiff) / Math.log(2) );
		pitchRollChecker = new Vector<Integer>();
		pitchRollValues = new Vector<Integer>();
		for (int i = 0; i < pitchGapSize; i++) {
			pitchRollChecker.addElement(new Integer(0));
			pitchRollValues.addElement(new Integer(getNextInt(2)) );
			doRollMultiplier(pitchRollValues, i);
		}

		int lengthGapSize = (int) ( Math.log(lengthDiff) / Math.log(2) );
		lengthRollChecker = new Vector<Integer>();
		lengthRollValues = new Vector<Integer>();
		for (int i = 0; i < lengthGapSize; i++) {
			lengthRollChecker.addElement(new Integer(0));
			lengthRollValues.addElement(new Integer(getNextInt(2)) );
			doRollMultiplier(lengthRollValues, i);
		}

		System.out.print("... Initial Contents of Vectors\n...");
		for (int i = 0; i < pitchRollValues.size(); i++) {
			System.out.print(pitchRollValues.elementAt(i) + " ");
		}
		System.out.print("\n...");
		for (int i = 0; i < lengthRollValues.size(); i++) {
			System.out.print(lengthRollValues.elementAt(i) + " ");
		}

		nextNote = calculateNextNote();
	}

	public Note getNextNote() throws OutOfMIDIRangeException {
		Note currentNote = nextNote;
		nextNote = calculateNextNote();
		return currentNote;
	}

	private Note calculateNextNote() throws OutOfMIDIRangeException {
		int maxPitchIndex =  incrementCheckerAndReturnMaxIndex(pitchRollChecker);
		int maxLengthIndex = incrementCheckerAndReturnMaxIndex(lengthRollChecker);
		for (int i = 0; i <= maxPitchIndex; i++) {
			pitchRollValues.remove(i);
			pitchRollValues.insertElementAt(new Integer(getNextInt(2)), i);
			doRollMultiplier(pitchRollValues, i);
		}
		for (int i = 0; i <= maxLengthIndex; i++) {
			lengthRollValues.remove(i);
			lengthRollValues.insertElementAt(new Integer(getNextInt(2)), i);
			doRollMultiplier(lengthRollValues, i);
		}
		int notePitch = 0;
		int noteLength = 0;
		for (int i = 0; i < pitchRollValues.size(); i++) {
			notePitch += ( (Integer) pitchRollValues.elementAt(i) ).intValue();
		}
		notePitch += restrictor.getLowPitch();

		for (int i = 0; i < lengthRollValues.size(); i++) {
			noteLength += ( (Integer) lengthRollValues.elementAt(i) ).intValue();
		}

		int toReturnLength = Converter.consecutiveIntToNoteLength(noteLength);
		System.out.println("Note: pitch " + notePitch + ", length " + toReturnLength);
		return new Note(new MIDIPitch(notePitch), 
                        Duration.durationFromDenominator(toReturnLength));
	}

	private int incrementCheckerAndReturnMaxIndex(Vector<Integer> toIncrement) {
		int i;
		for (i = 0; i < toIncrement.size(); i++) {
			if ( toIncrement.elementAt(i) == 0) {
				toIncrement.remove(i);
				toIncrement.insertElementAt(1, i);
				break;
			}
			toIncrement.remove(i);
			toIncrement.insertElementAt(0, i);
		}
		if (i >= toIncrement.size()) {
			i = toIncrement.size() - 1;
		}
		return i;
	}

	private void doRollMultiplier(Vector<Integer> pitchRollValues, int arrayLocation) {
		if (( (Integer)pitchRollValues.elementAt(arrayLocation) ).intValue() == 1) {
			for (int j = 0; j < arrayLocation; j++) {
				Integer test = new Integer( ((Integer) pitchRollValues.elementAt(arrayLocation)).intValue() );
				test = new Integer( test.intValue() * 2);
				pitchRollValues.remove(arrayLocation);
				pitchRollValues.insertElementAt(test, arrayLocation);
			}
		}
	}


	private void checkValueSpread(int pitchDiff, String valuename) throws InvalidDataSpreadException {     
                if ( (pitchDiff != 128) && (pitchDiff != 64) && (pitchDiff != 32)
                        && (pitchDiff != 16) && (pitchDiff != 8) &&
                        (pitchDiff != 4) && (pitchDiff != 2) && (pitchDiff != 1) ) {
                        throw new InvalidDataSpreadException(valuename + " spread " + pitchDiff);
                }
	}

	private Vector<Integer> pitchRollChecker;
	private Vector<Integer> lengthRollChecker;

	private Vector<Integer> pitchRollValues;
	private Vector<Integer> lengthRollValues;

	private Note nextNote;


/*
        public static void main(String args[]) throws Exception {
                NoteGenerator noteGen = new FractalNoiseGenerator(
                        new NoteRangeRestrictor(32, 95, 1, 128),
                        258);
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
