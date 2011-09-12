import java.util.Vector;

public abstract class DiceRollNoteGenerator implements NoteGenerator {
	public DiceRollNoteGenerator(int numPitchDice, int numDiceSides, int numLengthDice, int numLengthSides, long randomSeed) {
		createDice(numPitchDice, numLengthDice);

		for (int i = 0; i < numPitchDice; i++) {
			pitchDiceMax.setElementAt(new Integer(numDiceSides), i);
			pitchDiceRollCheck.setElementAt(new Integer(1), i);
		}
		for (int i = 0; i < numLengthDice; i++) {
			lengthDiceMax.setElementAt(new Integer(numLengthSides), i);
			lengthDiceRollCheck.setElementAt(new Integer(1), i);
		}

		randGen = new java.util.Random(randomSeed);
	}

	public DiceRollNoteGenerator(Vector numSidesPerPitchDice, Vector numSidesPerLengthDice, long randomSeed) {
		int numPitchDice = numSidesPerPitchDice.size();
		int numLengthDice = numSidesPerLengthDice.size();
		createDice(numPitchDice, numLengthDice);

		for (int i = 0; i < numPitchDice; i++) {
			pitchDiceMax.setElementAt(new Integer( ((Integer) numSidesPerPitchDice.elementAt(i)).intValue()), i);
			pitchDiceRollCheck.setElementAt(new Integer(1), i);
		}
		for (int i = 0; i < numLengthDice; i++) {
			lengthDiceMax.setElementAt(new Integer( ((Integer) numSidesPerLengthDice.elementAt(i)).intValue()), i);
			lengthDiceRollCheck.setElementAt(new Integer(1), i);
		}

		randGen = new java.util.Random(randomSeed);
	}

	private void createDice(int numPitchDice, int numLengthDice) {
                pitchDiceValue = new Vector(numPitchDice);
                pitchDiceMax = new Vector(numPitchDice);
                pitchDiceRollCheck = new Vector(numPitchDice);
		pitchDiceValue.setSize(numPitchDice);
		pitchDiceMax.setSize(numPitchDice);
		pitchDiceRollCheck.setSize(numPitchDice);

                lengthDiceValue = new Vector(numLengthDice);
                lengthDiceMax = new Vector(numLengthDice);
                lengthDiceRollCheck = new Vector(numLengthDice);
		lengthDiceValue.setSize(numLengthDice);
		lengthDiceMax.setSize(numLengthDice);
		lengthDiceRollCheck.setSize(numLengthDice);
	}

	public Note getNextNote() {
		int highestPitchToChange = incrementCheckerAndReturnMaxIndex(pitchDiceRollCheck);
		int highestLengthToChange = incrementCheckerAndReturnMaxIndex(lengthDiceRollCheck);
		return calculateNextRolledNote(highestPitchToChange, highestLengthToChange);
	}

	private Note calculateNextRolledNote(int highestPitchDice, int highestLengthDice) {
		for (int i = 0; i <= highestPitchDice; i++) {
			rollPitchDice(i);
		}
		for (int i = 0; i <= highestLengthDice; i++) {
			rollLengthDice(i);
		}

		int pitchReturn = calculateNextPitch();
		int lengthReturn = calculateNextLength();

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

        private int incrementCheckerAndReturnMaxIndex(Vector toIncrement) {
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

	protected Vector pitchDiceValue;
	private Vector pitchDiceMax;
	private Vector pitchDiceRollCheck;

	protected Vector lengthDiceValue;
	private Vector lengthDiceMax;
	private Vector lengthDiceRollCheck;

	private java.util.Random randGen;
}
