/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

/**
 * An abstraction of a die or collection of dice.
 * 
 * @author Terry Vaskor
 */
public interface AbstractDice {
    /**
     * Rolls the die, possibly changing its value to a value on any of the faces
     * of the die.
     */
    public void roll();
    
    /**
     * @return The current value of the die.
     */
    public int get();
    
    /**
     * @return The highest value that can be rolled using the die.
     */
    public int maximumRollableValue();
}
