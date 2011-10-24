/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

import java.util.HashSet;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author tavaskor
 */
class DiceTesterCommon {
    static void runDiceTest(AbstractDice instance) {
        final int max_valid = instance.maximumRollableValue();
        
        // Make a *very* high number of test runs, so it is excessively
        // unlikely for every value not to be rolled.
        final int NUM_TEST_RUNS = (max_valid + 1) * 100;
        
        int result1 = instance.get();
        int result2 = instance.get();
        int result3 = instance.get();
        assertEquals("Call to get() should not change dice values", result1, result2);
        assertEquals("Call to get() should not change dice values", result1, result3);
        assertEquals("Call to get() should not change dice values", result2, result3);
        
        HashSet<Integer> seenResults = new HashSet<Integer>();
        for (int i = 0; i < NUM_TEST_RUNS; i++) {
            instance.roll();
            assertTrue(
                    "Rolled die value should be at least 0 instead of " + instance.get(), 
                    instance.get() >= 0
                    );
            assertTrue(
                    "Rolled dice value should be at most one less than the number of sides, " + max_valid + ", instead of " + instance.get(), 
                    instance.get() <= max_valid
                    );
            seenResults.add(instance.get());
        }
        for (int j = 0; j <= max_valid; j++) {
            assertTrue(
                    "The dice roll " + j + " was not generated (range 0 to " + max_valid + ")",
                    seenResults.contains(j)
                    );
        }
    }
}
