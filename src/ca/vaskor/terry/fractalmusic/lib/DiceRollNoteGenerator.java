package ca.vaskor.terry.fractalmusic.lib;

import java.util.Vector;
import java.util.List;

public abstract class DiceRollNoteGenerator implements NoteGenerator {
	public DiceRollNoteGenerator(NoteRangeRestrictor restrictor, int numPitchDice, int numDiceSides, int numLengthDice, int numLengthSides, Long randomSeed) {
		createDice(numPitchDice, numLengthDice);

		for (int i = 0; i < numPitchDice; i++) {
			pitchDiceMax.set(i, numDiceSides);
			pitchDiceRollCheck.set(i, 1);
		}
		for (int i = 0; i < numLengthDice; i++) {
			lengthDiceMax.set(i, numLengthSides);
			lengthDiceRollCheck.set(i, 1);
		}

		randGen = new java.util.Random();
                if (randomSeed != null) {
                    randGen.setSeed(randomSeed);
                }
                
                nrr = restrictor;
	}

	public DiceRollNoteGenerator(NoteRangeRestrictor restrictor, List<? extends Integer> numSidesPerPitchDice, List<? extends Integer> numSidesPerLengthDice, Long randomSeed) {
		int numPitchDice = numSidesPerPitchDice.size();
		int numLengthDice = numSidesPerLengthDice.size();
		createDice(numPitchDice, numLengthDice);

		for (int i = 0; i < numPitchDice; i++) {
			pitchDiceMax.set(i, numSidesPerPitchDice.get(i));
			pitchDiceRollCheck.set(i, 1);
		}
		for (int i = 0; i < numLengthDice; i++) {
			lengthDiceMax.set(i, numSidesPerLengthDice.get(i));
			lengthDiceRollCheck.set(i, 1);
		}


		randGen = new java.util.Random();
                if (randomSeed != null) {
                    randGen.setSeed(randomSeed);
                }
                
                nrr = restrictor;
	}

	private void createDice(int numPitchDice, int numLengthDice) {
        pitchDiceValue = new Vector<Integer>(numPitchDice);
        pitchDiceMax = new Vector<Integer>(numPitchDice);
        pitchDiceRollCheck = new Vector<Integer>(numPitchDice);
        pitchDiceValue.setSize(numPitchDice);
        pitchDiceMax.setSize(numPitchDice);
        pitchDiceRollCheck.setSize(numPitchDice);

        lengthDiceValue = new Vector<Integer>(numLengthDice);
        lengthDiceMax = new Vector<Integer>(numLengthDice);
        lengthDiceRollCheck = new Vector<Integer>(numLengthDice);
        lengthDiceValue.setSize(numLengthDice);
        lengthDiceMax.setSize(numLengthDice);
        lengthDiceRollCheck.setSize(numLengthDice);
	}

	public Note getNextNote() throws OutOfMIDIRangeException {
		int highestPitchToChange = incrementCheckerAndReturnMaxIndex(pitchDiceRollCheck);
		int highestLengthToChange = incrementCheckerAndReturnMaxIndex(lengthDiceRollCheck);
		return calculateNextRolledNote(highestPitchToChange, highestLengthToChange);
	}

	private Note calculateNextRolledNote(int highestPitchDice, int highestLengthDice) throws OutOfMIDIRangeException {
		for (int i = 0; i <= highestPitchDice; i++) {
			rollPitchDice(i);
		}
		for (int i = 0; i <= highestLengthDice; i++) {
			rollLengthDice(i);
		}

		MIDIPitch pitchReturn = nrr.getPitch(calculateNextPitch());
		Duration lengthReturn = nrr.getDuration(calculateNextLength());

		System.out.println("Note: pitch " + pitchReturn +
			", length " + lengthReturn);
                return new Note(pitchReturn, lengthReturn);
	}

	protected abstract int calculateNextPitch();
	protected abstract int calculateNextLength();

	private int getNextInt(int maxPlusOne) {
		return randGen.nextInt(maxPlusOne);
	} 

	private void rollPitchDice(int diceNumber) {
		int maxRollValue = ( (Integer) pitchDiceMax.elementAt(diceNumber)).intValue();
		int rolledValue = getNextInt(maxRollValue + 1);
		pitchDiceValue.setElementAt(new Integer(rolledValue), diceNumber);
	}

	private void rollLengthDice(int diceNumber) {
		int maxRollValue = ( (Integer) lengthDiceMax.elementAt(diceNumber)).intValue();
		int rolledValue = getNextInt(maxRollValue + 1);
		lengthDiceValue.setElementAt(new Integer(rolledValue), diceNumber);
	}

        private int incrementCheckerAndReturnMaxIndex(Vector<Integer> toIncrement) {
                int i;
                for (i = 0; i < toIncrement.size(); i++) {
                        if ( ((Integer) toIncrement.elementAt(i) ).intValue() == 0) {
                                toIncrement.remove(i);
                                toIncrement.insertElementAt(new Integer(1), i);
                                break;
                        }
                        toIncrement.remove(i);
                        toIncrement.insertElementAt(new Integer(0), i);
                }
                if (i >= toIncrement.size()) {
                        i = toIncrement.size() - 1;
                }
                return i;
        }
        
        private NoteRangeRestrictor nrr;

	protected Vector<Integer> pitchDiceValue;
	private Vector<Integer> pitchDiceMax;
	private Vector<Integer> pitchDiceRollCheck;

	protected Vector<Integer> lengthDiceValue;
	private Vector<Integer> lengthDiceMax;
	private Vector<Integer> lengthDiceRollCheck;

	private java.util.Random randGen;
}
