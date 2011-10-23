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
public class PinkNoteGenerator implements NoteGenerator {
    public PinkNoteGenerator(
            NoteRangeRestrictor nrr, 
            int numberOfPitchDice, int numberOfDurationDice, 
            Random randGen
            ) {
        restrictor = nrr;
        pitchDice = new DiceData(nrr.getNumPitches() - 1, numberOfPitchDice, randGen);
        durationDice = new DiceData(nrr.getNumDurations() - 1, numberOfDurationDice, randGen);
    }
    
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
    
    private final DiceData pitchDice;
    private final DiceData durationDice;
}
