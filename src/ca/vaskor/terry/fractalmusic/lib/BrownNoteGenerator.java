package ca.vaskor.terry.fractalmusic.lib;

/**
 * An abstract class specifying the basics of "brown note" generation,
 * which involves generations of notes within a fixed number of steps of the
 * previous notes, in terms of all note parameters.
 * 
 * @author Terry Vaskor
 */
public abstract class BrownNoteGenerator implements NoteGenerator {
    /**
     * 
     * @param nrr             The restrictor to use for note generation.
     * @param lowPitchChange  The smallest allowed step for pitch changes.
     * @param highPitchChange The largest allowed step for pitch changes.
     * @param lowLengthStep   The smallest allowed step for duration changes.
     * @param highLengthStep  The largest allowed step for duration changes.
     */
    public BrownNoteGenerator(
            NoteRangeRestrictor nrr,
            int lowPitchChange, int highPitchChange, 
            int lowLengthStep, int highLengthStep
            ) {
        restrictor = nrr;
        
        lowPC = lowPitchChange;
        highPC = highPitchChange;
        lowLC = lowLengthStep;
        highLC = highLengthStep;

        // Set first note to midpoint of ranges
        nextPitchIndex = (int) ( restrictor.getNumPitches() / 2 );
        nextLengthIndex = (int) ( restrictor.getNumDurations() / 2 );
    }

    /**
     * @return A Note within a fixed number of steps of the previous note,
     * given arguments provided to the constructor.
     * 
     * @throws OutOfMIDIRangeException 
     */
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

    
    /**
     * Calculates the amount of change from the previous note on one parameter
     * (such as pitch or duration), given caps on the size of the step 
     * in each direction.
     * 
     * @param startVal        The numerical representation of the value being changed.
     * @param maxNegChange    The maximum amount startVal can be changed in the negative direction.
     * @param maxPosChange    The maximum amount startVal can be changed in the positive direction.
     * @param maxIndexPlusOne One above the maximum allowable value for the value produced.
     * @return The numerical representation for the new value.
     */
    protected abstract int calculateChange(
            int startVal, int maxNegChange,
            int maxPosChange, int maxIndexPlusOne
            );

    private int nextPitchIndex, nextLengthIndex;

    private int lowPC;
    private int highPC;
    private int lowLC;
    private int highLC;
    
    private NoteRangeRestrictor restrictor;
}
