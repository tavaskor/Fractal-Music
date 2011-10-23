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
public final class Dice {
    public Dice(final int numberOfSides, final Random randGen) {
        numSides = numberOfSides;
        randomSelector = randGen;
        roll();
    }
    
    public void roll() {
        if (numSides != 0) {
            currentValue = randomSelector.nextInt(numSides);
        }
    }
    
    public int get() {
        return currentValue;
    }
    
    private final int numSides;
    private final Random randomSelector;
    private int currentValue = 0;
    
    // Inspection test.
    public static void main(String[] args) {
        Dice tester = new Dice(15, new Random());
        for (int i = 0; i < 20; i++) {
            tester.roll();
            System.out.println(tester.get());
        }
    }
}
