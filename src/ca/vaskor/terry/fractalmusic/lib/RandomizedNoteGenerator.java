package ca.vaskor.terry.fractalmusic.lib;

public class RandomizedNoteGenerator {
	public RandomizedNoteGenerator(NoteRangeRestrictor nrr, Long randomSeed) {
		restrictor = nrr;
		randGen = new java.util.Random();
                if (randomSeed != null) {
                    randGen.setSeed(randomSeed);
                }
	}

	protected int getNextInt(int maxPlusOne) {
		return randGen.nextInt(maxPlusOne);
	}

	protected NoteRangeRestrictor restrictor;
	private java.util.Random randGen;
}
