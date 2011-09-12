public abstract class BrownNoiseGenerator extends RandomizedNoteGenerator implements NoteGenerator {
	public BrownNoiseGenerator(NoteRangeRestrictor overallRange, long randomSeed, int lowPitchChange, int highPitchChange, int lowLengthStep, int highLengthStep) {
		super(overallRange, randomSeed);
		lowPC = lowPitchChange;
		highPC = highPitchChange;
		lowLC = lowLengthStep;
		highLC = highLengthStep;

		// Set first note to midpoint of ranges
		int pitch = (int) ( (restrictor.getLowPitch() + restrictor.getHighPitch()) / 2 );

		int low = Converter.noteLengthToConsecutiveInt( restrictor.getLongestLength() );
		int high = Converter.noteLengthToConsecutiveInt( restrictor.getShortestLength() );
		int length = Converter.consecutiveIntToNoteLength( (low + high) / 2 );

		nextNote = new Note(pitch, length);
	}

	public Note getNextNote() {
		Note currentNote = nextNote;
		nextNote = calculateNextNote(currentNote);
		return currentNote;
	}

	protected int getLowestPitchChange() { return lowPC; }
	protected int getHighestPitchChange() { return highPC; }
	protected int getLowestConsecutiveLengthChange() { return lowLC; }
	protected int getHighestConsecutiveLengthChange() { return highLC; }

	protected abstract Note calculateNextNote(Note baseNote);

	private Note nextNote;

	private int lowPC;
	private int highPC;
	private int lowLC;
	private int highLC;
}
