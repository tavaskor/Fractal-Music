package ca.vaskor.terry.fractalmusic.lib;

public class ReflectingBrownNoteGenerator extends BrownNoteGenerator {
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
