/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

import java.util.Random;

/**
 *
 * @author tavaskor
 */
final class NSidedDie implements AbstractDice {
    public NSidedDie(final int numberOfSides, final Random randGen) {
        numSides = numberOfSides;
        randomSelector = randGen;
        roll();
    }
    
    @Override
    public void roll() {
        if (numSides != 0) {
            currentValue = randomSelector.nextInt(numSides);
        }
    }
    
    @Override
    public int get() {
        return currentValue;
    }
    
    @Override
    public int maximumRollableValue() {
        return numSides - 1;
    }
    
    private final Random randomSelector;
    private final int numSides;
    private int currentValue = 0;
}
