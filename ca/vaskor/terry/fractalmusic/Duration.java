/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic;

/**
 *
 * @author tavaskor
 */
public enum Duration {
    WHOLE(1), HALF(2), QUARTER(4), EIGHTH(8), SIXTEENTH(16), 
    THIRTY_SECOND(32), SIXTY_FOURTH(64), ONE_HUNDRED_TWENTY_EIGHTH(128);
    
    Duration(int denominator) {
        denom = denominator;
    }
    
    public int getNumericDenominator() {
        return denom;
    }
    
    public static Duration durationFromDenominator(int denominator) {
        switch(denominator) {
            case 1: return WHOLE;
            case 2: return HALF;
            case 4: return QUARTER;
            case 8: return EIGHTH;
            case 16: return SIXTEENTH;
            case 32: return THIRTY_SECOND;
            case 64: return SIXTY_FOURTH;
            default: return ONE_HUNDRED_TWENTY_EIGHTH;
        }
    }
    
    private int denom;
}
