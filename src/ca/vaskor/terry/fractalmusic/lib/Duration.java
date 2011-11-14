/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author tavaskor
 */
public enum Duration {
    ONE_HUNDRED_TWENTY_EIGHTH(128), SIXTY_FOURTH(64), 
    THIRTY_SECOND(32), SIXTEENTH(16), EIGHTH(8), 
    QUARTER(4), HALF(2), WHOLE(1);
    
    private Duration(int denominator) {
        denom = denominator;
    }
    
    public int getNumericDenominator() {
        return denom;
    }
    
    /**
     * Converts a numeric denominator into a Duration.
     * 
     * For example, given a denominator of 4, this function will return QUARTER.
     * 
     * @param denominator The denominator of the fraction representing a note duration
     * @return The Duration representation of the note with that denominator.
     */
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
    
    /**
     * 
     * @return A representation of the note as a fraction followed by " note",
     *         or "Whole note" in the special WHOLE case.
     */
    public String toString() {
        String firstPart; 
        if (this == WHOLE) {
            firstPart = "Whole";
        } else {
            firstPart = "1/" + getNumericDenominator();
        }
        return firstPart + " note";
    }
    
    /**
     * Get a list of contiguous durations between two particular values.
     * 
     * @param end1 The shortest duration desired.
     * @param end2 The longest duration desired.
     * @return A list of all durations between end1 and end2, inclusive.
     */
    public static List<Duration> getRange(Duration end1, Duration end2) {
        if (end2.compareTo(end1) < 0) { return getRange(end2, end1); }
        
        return allDurations.subList(end1.ordinal(), end2.ordinal() + 1);
    }
    
    private static List<Duration> getAllDurations() {
        return new ArrayList<Duration>(Arrays.asList(Duration.values()));
    }
    private static final List<Duration> allDurations = getAllDurations();
    
    public static final Duration LOWEST_DURATION = allDurations.get(0);
    public static final Duration HIGHEST_DURATION = 
            allDurations.get(allDurations.size() - 1);
    
    private int denom;
}
