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

        private void setMinimums(NoteRangeRestrictor nrr) {
                lowestPitch = nrr.getLowPitch();
                lowestConsecutiveLength = Converter.noteLengthToConsecutiveInt( nrr.getLongestLength());
        }

	public AdditiveNoteGenerator(NoteRangeRestrictor nrr, int numberOfPitchDice, int numberOfLengthDice, long randomSeed) {
		super(
			determineDiceSides( nrr.getHighPitch() - nrr.getLowPitch() + 1, numberOfPitchDice),
			 determineDiceSides( Converter.noteLengthToConsecutiveInt(nrr.getShortestLength()) - Converter.noteLengthToConsecutiveInt(nrr.getLongestLength()) + 1, numberOfLengthDice),
			randomSeed
		);
		setMinimums(nrr);
	}



	protected int calculateNextPitch() { 
		//System.out.println("Pitch dice values:" + stringList(pitchDiceValue));
		return diceSum(pitchDiceValue) + lowestPitch;
	}

	protected int calculateNextLength() {
		//System.out.println("Length dice values:" + stringList(lengthDiceValue));
		return Converter.consecutiveIntToNoteLength( diceSum(lengthDiceValue) + lowestConsecutiveLength);
	}


	private int diceSum(List<? extends Integer> listOfDice) {
		int sum = 0;
		for (int i = 0; i < listOfDice.size(); i++) {
			sum += listOfDice.get(i);
		}
		return sum;
	}

	private int lowestPitch;
	private int lowestConsecutiveLength;




	/*
	public static void main(String args[]) throws Exception {
                NoteGenerator noteGen = new AdditiveNoteGenerator(
                        new NoteRangeRestrictor(52, 55, 1, 32),
			2, 3,
                        24958);
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
