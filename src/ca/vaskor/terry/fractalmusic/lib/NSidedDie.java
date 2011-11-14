/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

import java.util.Random;

/**
 * Represents a single die with a specific number of sides.
 * 
 * These die will roll values in the range [0 .. numberOfSides - 1].
 * 
 * @author Terry Vaskor
 */
final class NSidedDie implements AbstractDice {
    /**
     * 
     * @param numberOfSides The number of sides this particular die should have.
     * @param randGen       The random number generator used to determine the value of the next roll.
     */
    public NSidedDie(final int numberOfSides, final Random randGen) {
        numSides = numberOfSides;
        randomSelector = randGen;
        roll();
    }
    
    /**
     * "Roll" this die, causing a potentially new value to be the current one.
     */
    @Override
    public void roll() {
        if (numSides != 0) {
            currentValue = randomSelector.nextInt(numSides);
        }
    }
    
    /**
     * 
     * @return The current value active on this die.
     */
    @Override
    public int get() {
        return currentValue;
    }
    
    /**
     * @return The maximum value that can be rolled, equal to numberOfSides - 1.
     */
    @Override
    public int maximumRollableValue() {
        return numSides - 1;
    }
    
    private final Random randomSelector;
    private final int numSides;
    private int currentValue = 0;
}
