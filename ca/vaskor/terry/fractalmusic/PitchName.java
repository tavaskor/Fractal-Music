/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic;


/**
 *
 * @author tavaskor
 */

public enum PitchName implements Comparable<PitchName> {
    C, C_SHARP, D, D_SHARP, E, F, 
    F_SHARP, G, G_SHARP, A, A_SHARP, B;
    
    public final static String SHARP = "\u266F";
    public final static String FLAT = "\u266D";
    
    @Override
    public String toString() {
        if (this == PitchName.C_SHARP) return "C" + SHARP;
        if (this == PitchName.D_SHARP) return "E" + FLAT;
        if (this == PitchName.F_SHARP) return "F" + SHARP;
        if (this == PitchName.G_SHARP) return "A" + FLAT;
        if (this == PitchName.A_SHARP) return "B" + FLAT; 
        return super.toString();
    }
}