/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * An enum specifying a variety of different scales that can be used by
 * a given NoteRangeRestrictor to select pitches.
 * 
 * @author Terry Vaskor
 */
public enum ScaleType {
    CHROMATIC("Chromatic", 1,1,1,1,1,1,1,1,1,1,1,1), 
    MAJOR("Major", 2,2,1,2,2,2,1), 
    HARMONIC_MINOR("Harmonic Minor", 2,1,2,2,1,3,1),
    NATURAL_MINOR("Natural Minor",2,1,2,2,1,2,2),
    PENTATONIC("Pentatonic", 2,2,3,2,3),
    HEXATONIC_BLUES("Hexatonic Blues", 3,2,1,1,3,2),
    HEPTATONIC_BLUES("Heptatonic Blues", 2,1,2,1,3,1,2),
    NINE_NOTE_BLUES("Nine Note Blues", 2,1,1,1,2,2,1,1,1),
    LIMITED_TRANSPORTATION_1("Mode of Limited Transposition #1", 2,2,2,2,2,2),
    LIMITED_TRANSPORTATION_2("Mode of Limited Transposition #2", 1,2,1,2,1,2,1,2),
    LIMITED_TRANSPORTATION_3("Mode of Limited Transposition #3", 2,1,1,2,1,1,2,1,1),
    LIMITED_TRANSPORTATION_4("Mode of Limited Transposition #4", 1,1,3,1,1,1,3,1),
    LIMITED_TRANSPORTATION_5("Mode of Limited Transposition #5", 1,4,1,1,4,1),
    LIMITED_TRANSPORTATION_6("Mode of Limited Transposition #6", 2,2,1,1,2,2,1,1),
    LIMITED_TRANSPORTATION_7("Mode of Limited Transposition #7", 1,1,1,2,1,1,1,1,2,1);
    
    /**
     * 
     * @param name                         A more conveniently human-readable representation for this enum.
     * @param semitonesBetweenScaleDegrees A list of the number of semitones between successive scale notes.
     *                                     The total must be a multiple of 12.
     * @throws Error If the compile-time semitones between scale degrees do not add up to a multiple of twelve,
     *               this scale would be invalid; the ScaleType source code is incorrect and needs to be modified.
     */
    ScaleType(String name, int ... semitonesBetweenScaleDegrees) {
        scaleName = name;
        
        semitoneSkips = new ArrayList<Integer>(semitonesBetweenScaleDegrees.length);
        int scaleEndCheck = 0;
        for (int i : semitonesBetweenScaleDegrees) {
            semitoneSkips.add(i);
            scaleEndCheck += i;
        }
        if ((scaleEndCheck % numScaleDegrees) != 0) {
            // The start and end note of the scale are not the same!
            // Consider this a "compiler error"
            throw new Error("ScaleType internal constructor error: " +
                    "Scale degrees must be a multiple of " + 
                    numScaleDegrees +
                    "; saw " + scaleEndCheck);
        }
    }
    
    /**
     * 
     * @return A conveniently human-readable representation of this enum value.
     */
    @Override
    public String toString() {
        return scaleName;
    }
    
    /**
     * 
     * @return The number of semitones between each successive pitch in the scale.
     *         The total must be a multiple of 12.
     */
    public List<? extends Integer> getScaleDegrees() {
        return semitoneSkips;
    }
    
    private ArrayList<Integer> semitoneSkips;
    
    private String scaleName;
    
    private final int numScaleDegrees = PitchName.values().length;
}
