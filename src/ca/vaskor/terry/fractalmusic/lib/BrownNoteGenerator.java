package ca.vaskor.terry.fractalmusic.lib;

public abstract class BrownNoteGenerator extends RandomizedNoteGenerator implements NoteGenerator {
    public BrownNoteGenerator(
            NoteRangeRestrictor overallRange, java.util.Random randGen, 
            int lowPitchChange, int highPitchChange, 
            int lowLengthStep, int highLengthStep
            ) {
        super(overallRange, randGen);
        lowPC = lowPitchChange;
        highPC = highPitchChange;
        lowLC = lowLengthStep;
        highLC = highLengthStep;

        // Set first note to midpoint of ranges
        nextPitchIndex = (int) ( restrictor.getNumPitches() / 2 );
        nextLengthIndex = (int) ( restrictor.getNumDurations() / 2 );
    }

    @Override
    public Note getNextNote() throws OutOfMIDIRangeException {
        int currentPitchIndex = nextPitchIndex;
        int currentLengthIndex = nextLengthIndex;
        nextPitchIndex = calculateChange(
                currentPitchIndex, lowPC, highPC, restrictor.getNumPitches()
                );
        nextLengthIndex = calculateChange(
                currentLengthIndex, lowLC, highLC, restrictor.getNumDurations()
                );
        return new Note(restrictor.getPitch(currentPitchIndex), 
                restrictor.getDuration(currentLengthIndex));
    }

    protected abstract int calculateChange(
            int baseIndex, int maxNegChange,
            int maxPosChange, int maxIndex
            );

    private int nextPitchIndex, nextLengthIndex;

    private int lowPC;
    private int highPC;
    private int lowLC;
    private int highLC;
}
