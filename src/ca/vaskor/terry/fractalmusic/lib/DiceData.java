/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author tavaskor
 */
class DiceData {
    DiceData(int rangeSize, int numberOfDice, java.util.Random gen) {
        diceList = new ArrayList<Dice>(numberOfDice);
        
        // There need to be enough dice sides to cover all possible combinations.
        // So, for five values and three die, the number of sides need to be
        // 3 (0, 1, 2), 3 (0, 1, 2) and 2 (0, 1).
        int baseSidesPerDie = ((rangeSize-1) / numberOfDice) + 1;
        int numberOfDieWithOneMoreSide = ((rangeSize-1) % numberOfDice) + 1;

        for (int i = 0; i < numberOfDieWithOneMoreSide; i++) {
            diceList.add(i, new Dice(baseSidesPerDie + 1, gen));
        }
        for (int i = numberOfDieWithOneMoreSide; i < numberOfDice; i++) {
            diceList.add(i, new Dice(baseSidesPerDie, gen));
        }

        // the roll counter will clock over
        maxRollCounter = 1 << numberOfDice;
    }

    void roll() {
        int oldRollCounter = currentRollCounter;
        currentRollCounter = increment(currentRollCounter);

        List<Integer> diceToRoll = getFlippedBits(oldRollCounter, currentRollCounter);

        // Roll any necessary dice, deciding whether to roll
        // based on which bit positions in the binary counter
        // have flipped.
        for (int dieNum : diceToRoll) {
            diceList.get(dieNum).roll();
        }
    }

    int get() {
        int sum = 0;
        for (Dice die : diceList) {
            sum += die.get();
        }
        return sum;
    }

    // The following is not private for JUnit.  It probably more properly
    // belongs in some utility collection.
    // Return a list of all of the bits that have flipped, starting with 
    // index 0 as the "ones" position, index 1 as "twos", index 2 as "fours", etc.
    /*private*/ static List<Integer> getFlippedBits(int val1, int val2) {
        final int MAX_BITS = (int)(Math.log(Integer.MAX_VALUE) / Math.log(2));

        // XOR the values; the positions that are '1' are what's changed.
        int flipTrack = val1 ^ val2;

        ArrayList<Integer> retVal = new ArrayList<Integer>();
        for (int i = 0; i < MAX_BITS; i++) {
            int bitCheckMask = 1 << i;
            if ((flipTrack & bitCheckMask) != 0) {
                retVal.add(i);
            }
        }
        return retVal;
    }

    private int increment(int currVal) {
        return (currVal + 1) % maxRollCounter;
    }


    private final List<Dice> diceList;
    private final int maxRollCounter;

    private int currentRollCounter = 0;
}
