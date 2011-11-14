/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.lib;

import java.util.Random;

/**
 * 
 * Generates notes based on a "pink note" algorithm.
 * 
 * This involves rolling dice to determine the next values for pitches, etc.
 * This is determined by counting in binary and checking which digits change;
 * the corresponding dice only are rolled.  On roll #0 all are rolled; on
 * roll #1, one die is rolled; on roll #2, two dice are rolled; on roll #3, one
 * die is rolled, etc.
 * 
 * @author Terry Vaskor
 */
public class PinkNoteGenerator implements NoteGenerator {
    
    /**
     * 
     * @param nrr                  The restrictor to use for note generation.
     * @param numberOfPitchDice    The number of dice across which to spread the range of pitches given by nrr.
     * @param numberOfDurationDice The number of dice across which to spread the range of durations given by nrr.
     * @param randGen              The random number generator to use to determine dice rolls.
     */
    public PinkNoteGenerator(
            NoteRangeRestrictor nrr, 
            int numberOfPitchDice, int numberOfDurationDice, 
            Random randGen
            ) {
        restrictor = nrr;
        pitchDice = new BinaryRollCountingDiceAggregator(nrr.getNumPitches() - 1, numberOfPitchDice, randGen);
        durationDice = new BinaryRollCountingDiceAggregator(nrr.getNumDurations() - 1, numberOfDurationDice, randGen);
    }
    
    /**
     * Obtains the next note, based on the rolls of the applicable 
     * dice for pitch and duration.
     * 
     * @exception OutOfMIDIRangeException if any dice rolls provide a value
     * out of the range allowed by the MIDI specification.
     * 
     * @return    The next generated note.
     */
    @Override
    public Note getNextNote() throws OutOfMIDIRangeException {
        pitchDice.roll();
        durationDice.roll();
        
        return new Note(
                restrictor.getPitch(pitchDice.get()),
                restrictor.getDuration(durationDice.get())
                );
    }
    
    private final NoteRangeRestrictor restrictor;
    
    private final AbstractDice pitchDice;
    
    private final AbstractDice durationDice;
}
