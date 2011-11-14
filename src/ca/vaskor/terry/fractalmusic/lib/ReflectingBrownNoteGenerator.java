package ca.vaskor.terry.fractalmusic.lib;

/**
 * This class generates "Brown notes" in a way that prevents stepping outside
 * of the boundaries of the acceptable range by simply "reflecting back"
 * automatically whenever a value at the high or low end is reached.
 * 
 * @author Terry Vaskor
 */
public class ReflectingBrownNoteGenerator extends BrownNoteGenerator {
    /**
     * 
     * @param nrr                The restrictor to use for note generation.
     * @param randGen            The random number generator used to determine step direction and size.
     * @param lowestPitchChange  The lowest allowable delta in pitch.
     * @param highestPitchChange The highest allowable delta in pitch.
     * @param lowestLengthStep   The lowest allowable delta in duration.
     * @param highestLengthStep  The highest allowable delta in duration.
     */
    public ReflectingBrownNoteGenerator(
            NoteRangeRestrictor nrr, java.util.Random randGen,
            int lowestPitchChange, int highestPitchChange,
            int lowestLengthStep, int highestLengthStep) {
        super(
                nrr, lowestPitchChange, highestPitchChange, 
                lowestLengthStep, highestLengthStep
                );
        gen = randGen;
    }
    
    /**
     * Calculates the amount of change from the previous note on one parameter
     * (such as pitch or duration), given caps on the size of the step 
     * in each direction.
     * 
     * If the change would push the note outside of the range denoted by the
     * NoteRangeRestrictor provided to the constructor, this is resolved by
     * "reflecting" the change back toward the median allowed value.
     * 
     * @param startVal      The numerical representation of the value being changed.
     * @param maxNegChange  The maximum amount startVal can be changed in the negative direction.
     * @param maxPosChange  The maximum amount startVal can be changed in the positive direction.
     * @param maxValPlusOne One above the maximum allowable value for the value produced.
     * @return The numerical representation for the new value.
     */
    @Override
    protected int calculateChange(
            int startVal, int maxNegChange, int maxPosChange, int maxValPlusOne
            ) {
        int minVal = 0;
        int maxVal = maxValPlusOne - 1;
        int changeAmount = gen.nextInt(maxPosChange - maxNegChange + 1) + maxNegChange;
        int newVal = startVal + changeAmount;
        if ( (newVal < minVal) || (newVal > maxVal) ) {
            newVal = startVal - changeAmount;
            if ( (newVal < minVal) || (newVal > maxVal) ) {
                newVal = startVal;
            }
        }

        return newVal;
    }	
    
    private java.util.Random gen;
}
