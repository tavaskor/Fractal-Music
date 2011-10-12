package ca.vaskor.terry.fractalmusic.lib;

public class RandomizedNoteGenerator {
	public RandomizedNoteGenerator(NoteRangeRestrictor nrr, java.util.Random randGen) {
		restrictor = nrr;
                gen = randGen;
	}

	protected int getNextInt(int maxPlusOne) {
		return gen.nextInt(maxPlusOne);
	}

	protected NoteRangeRestrictor restrictor;
	private java.util.Random gen;
}
