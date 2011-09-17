/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author tavaskor
 */
public enum Duration {
    ONE_HUNDRED_TWENTY_EIGHTH(128), SIXTY_FOURTH(64), 
    THIRTY_SECOND(32), SIXTEENTH(16), EIGHTH(8), 
    QUARTER(4), HALF(2), WHOLE(1);
    
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
    
    public String toString() {
        String firstPart; 
        if (this == WHOLE) {
            firstPart = "Whole";
        } else {
            firstPart = "1/" + getNumericDenominator();
        }
        return firstPart + " note";
    }
    
    // NB: This seems somewhat ridiculous, but it will work quickly for now.
    //     It's assumed the array slice from MIDIPitch is better if this is
    //     used repeatedly / frequently...
    public static List<Duration> getRange(Duration end1, Duration end2) {
        if (end2.ordinal() < end1.ordinal()) { return getRange(end2, end1); }
        
        ArrayList<Duration> returnDurations = new ArrayList<Duration>();
        for (Duration d : Duration.values()) {
            if ((end1.ordinal() <= d.ordinal()) &&
                    (end2.ordinal() >= d.ordinal())) {
                returnDurations.add(d);
            }
        }
        
        return returnDurations;
    }
    
    private int denom;
}
