package ca.vaskor.terry.fractalmusic.lib;

public class RandomizedNoteGenerator {
	public RandomizedNoteGenerator(NoteRangeRestrictor nrr, long randomSeed) {
		restrictor = nrr;
		randGen = new java.util.Random(randomSeed);
	}

	protected int getNextInt(int maxPlusOne) {
		return randGen.nextInt(maxPlusOne);
	}

	protected NoteRangeRestrictor restrictor;
	private java.util.Random randGen;
}
