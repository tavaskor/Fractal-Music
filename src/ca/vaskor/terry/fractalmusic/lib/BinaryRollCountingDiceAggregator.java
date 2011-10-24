/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

import java.util.List;

/**
 *
 * @author tavaskor
 */
class BinaryRollCountingDiceAggregator implements AbstractDice {
    BinaryRollCountingDiceAggregator(int rangeSize, int numberOfDice, java.util.Random gen) {
        diceList = DiceDataUtil.getDice(rangeSize, numberOfDice, gen);
        maxRollCounter = 1 << numberOfDice;
        maxRollableValue = rangeSize;
    }

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

    @Override
    public int get() {
        int sum = 0;
        for (AbstractDice die : diceList) {
            sum += die.get();
        }
        return sum;
    }
    
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
