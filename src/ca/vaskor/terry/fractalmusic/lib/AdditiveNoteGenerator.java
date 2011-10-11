package ca.vaskor.terry.fractalmusic.lib;

import java.util.Vector;
import java.util.List;

public class AdditiveNoteGenerator extends DiceRollNoteGenerator {

	private static List<Integer> determineDiceSides(int rangeSize, int numberOfDividingDice) {
		int numberOfSides = (rangeSize / numberOfDividingDice) + 1;
		Vector<Integer> resultVector = new Vector<Integer>();
		for (int i = 0; i < numberOfDividingDice; i++) {
			resultVector.addElement( new Integer(numberOfSides) );
		}

		int indexToDecrement = resultVector.size();
		while (sumOfValues(resultVector) != rangeSize - 1) {
			indexToDecrement -= 1;
			if (indexToDecrement < 0) {
				// should not happen
				indexToDecrement = resultVector.size() - 1;
			}

			int replacementValue = ( (Integer) resultVector.elementAt(indexToDecrement)).intValue() - 1;
			resultVector.setElementAt( new Integer(replacementValue), indexToDecrement);
		}

		System.out.print("Returning Vector with values:");
		for (int i = 0; i < resultVector.size(); i++) {
			System.out.print(" " + 
				( (Integer) resultVector.elementAt(i) ).intValue()
			);
		}
		System.out.println();
		return resultVector;
	}

	private static int sumOfValues(List<? extends Integer> toSum) {
		int sum = 0;
		for (int i = 0; i < toSum.size(); i++) {
			sum += toSum.get(i);
		}
		return sum;
	}

        public AdditiveNoteGenerator(NoteRangeRestrictor nrr, int numberOfPitchDice, int numberOfLengthDice, Long randomSeed) {
            super(
                    nrr,
                    determineDiceSides( nrr.getNumPitches(), numberOfPitchDice),
                    determineDiceSides( nrr.getNumDurations(), numberOfLengthDice),
                    randomSeed
                    );
	}



    @Override
	protected int calculateNextPitch() { 
		return diceSum(pitchDiceValue);
	}

    @Override
	protected int calculateNextLength() {
		return diceSum(lengthDiceValue);
	}


	private int diceSum(List<? extends Integer> listOfDice) {
		int sum = 0;
		for (int i = 0; i < listOfDice.size(); i++) {
			sum += listOfDice.get(i);
		}
		return sum;
	}
}
