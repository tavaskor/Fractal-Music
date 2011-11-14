/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

import java.util.List;

/**
 * This "die" actually represents a collection of die.  The amount read from it
 * is the sum of the total rolled on each individual die.
 * 
 * On each roll, not all of the contained dice are rolled.  The ones selected
 * for rolling are determined by the algorithm in {@link DiceDataUtil.getFlippedBits}.
 * 
 * @author Terry Vaskor
 */
class BinaryRollCountingDiceAggregator implements AbstractDice {
    
    /**
     * 
     * @param rangeSize    The total range of values that should be rollable.
     * @param numberOfDice The number of dice across which the range should be divided.
     * @param gen          The random number generator to provide to all contained dice.
     */
    BinaryRollCountingDiceAggregator(int rangeSize, int numberOfDice, java.util.Random gen) {
        diceList = DiceDataUtil.getDice(rangeSize, numberOfDice, gen);
        maxRollCounter = 1 << numberOfDice;
        maxRollableValue = rangeSize;
    }

    /**
     * Roll a subset of the contained dice.
     */
    @Override
    public void roll() {
        int oldRollCounter = currentRollCounter;
        currentRollCounter = increment(currentRollCounter);

        java.util.Collection<Integer> diceToRoll = DiceDataUtil.getFlippedBits(oldRollCounter, currentRollCounter);

        // Roll any necessary dice, deciding whether to roll
        // based on which bit positions in the binary counter
        // have flipped.
        for (int dieNum : diceToRoll) {
            diceList.get(dieNum).roll();
        }
    }

    /**
     * @return The sum of the values of all contaioned dice.
     */
    @Override
    public int get() {
        int sum = 0;
        for (AbstractDice die : diceList) {
            sum += die.get();
        }
        return sum;
    }
    
    /**
     * 
     * @return The sum of the maximum rollable values of all of the contained dice.
     */
    @Override
    public int maximumRollableValue() {
        return maxRollableValue;
    }

    private int increment(int currVal) {
        return (currVal + 1) % maxRollCounter;
    }


    private final List<? extends AbstractDice> diceList;
    private final int maxRollCounter;
    private final int maxRollableValue;

    private int currentRollCounter = 0;
}
