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
public enum ScaleType {
    CHROMATIC(1,1,1,1,1,1,1,1,1,1,1,1), 
    MAJOR(2,2,1,2,2,2,1), 
    HARMONIC_MINOR(2,1,2,2,1,3,1),
    NATURAL_MINOR(2,1,2,2,1,2,2),
    PENTATONIC(2,2,3,2,3),
    HEXATONIC_BLUES(3,2,1,1,3,2),
    HEPTATONIC_BLUES(2,1,2,1,3,1,2),
    NINE_NOTE_BLUES(2,1,1,1,2,2,1,1,1),
    LIMITED_TRANSPORTATION_1(2,2,2,2,2,2),
    LIMITED_TRANSPORTATION_2(1,2,1,2,1,2,1,2),
    LIMITED_TRANSPORTATION_3(2,1,1,2,1,1,2,1,1),
    LIMITED_TRANSPORTATION_4(1,1,3,1,1,1,3,1),
    LIMITED_TRANSPORTATION_5(1,4,1,1,4,1),
    LIMITED_TRANSPORTATION_6(2,2,1,1,2,2,1,1),
    LIMITED_TRANSPORTATION_7(1,1,1,2,1,1,1,1,2,1);
    
    ScaleType(int ... semitonesBetweenScaleDegrees) {
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
    
    public List<? extends Integer> getScaleDegrees() {
        return semitoneSkips;
    }
    private ArrayList<Integer> semitoneSkips;
    
    private final int numScaleDegrees = PitchName.values().length;
}
