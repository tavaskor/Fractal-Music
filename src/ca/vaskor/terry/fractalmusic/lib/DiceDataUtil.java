/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class contains package-specific static functions that are used by
 * DiceData.  They conceptually could just be private to DiceData, but are
 * complex enough that they warrant special unit testing, and so are 
 * listed here instead.
 * 
 * @author Terry Vaskor
 */
class DiceDataUtil {
    /**
     * Get a list of dice that can be used to calculate values for a given music parameter.
     * 
     * @param rangeSize    The total size of the range of values that can be generated
     * @param numberOfDice The number of die to partition the range into
     * @param gen          Random number generator to pass to each die
     * @return A list of Dice, representing the number of sides generated
     * dice should have given a maximum generated value and number of dice to
     * use.
     */
    static java.util.List<? extends AbstractDice> getDice(int rangeSize, int numberOfDice, java.util.Random gen) {
        ArrayList<NSidedDie> diceList = new ArrayList<NSidedDie>(numberOfDice);
        
        // There need to be enough dice sides to cover all possible combinations.
        // So, for five values and three die, the number of sides need to be
        // 3 (0, 1, 2), 3 (0, 1, 2) and 2 (0, 1).
        int baseSidesPerDie = ((rangeSize-1) / numberOfDice) + 1;
        int numberOfDieWithOneMoreSide = ((rangeSize-1) % numberOfDice) + 1;

        for (int i = 0; i < numberOfDieWithOneMoreSide; i++) {
            diceList.add(i, new NSidedDie(baseSidesPerDie + 1, gen));
        }
        for (int i = numberOfDieWithOneMoreSide; i < numberOfDice; i++) {
            diceList.add(i, new NSidedDie(baseSidesPerDie, gen));
        }
        
        return diceList;
    }
    
    /**
     * Given two integers, determine which bits have flipped, counting from the
     * right-most bit as bit number 0.
     * 
     * @param val1 One of the values to compare to isolate which bits are different.
     * @param val2 The other value to compare to isolate which bits are different.
     * @return A {@link java.util.Collection} of all of the bits that have flipped, 
     *         starting with index 0 as the "ones" position, index 1 as "twos", 
     *         index 2 as "fours", etc.
     */
    static java.util.Collection<Integer> getFlippedBits(int val1, int val2) {
        final int MAX_BITS = (int)(Math.log(Integer.MAX_VALUE) / Math.log(2));

        // XOR the values; the positions that are '1' are what's changed.
        int flipTrack = val1 ^ val2;

        HashSet<Integer> retVal = new HashSet<Integer>();
        for (int i = 0; i < MAX_BITS; i++) {
            int bitCheckMask = 1 << i;
            if ((flipTrack & bitCheckMask) != 0) {
                retVal.add(i);
            }
        }
        return retVal;
    }
}
