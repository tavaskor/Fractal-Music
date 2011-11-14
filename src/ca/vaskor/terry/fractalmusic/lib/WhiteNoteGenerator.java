package ca.vaskor.terry.fractalmusic.lib;

/**
 * Calculates the next pitch based on a "white note" algorithm.
 * This simply randomly selects the next values for all note parameters
 * from the full allowable range.
 * 
 * @author Terry Vaskor
 */
public class WhiteNoteGenerator implements NoteGenerator {
    /**
     * 
     * @param nrr     The restrictor to use for note generation.
     * @param randGen The random number generator used to select parameters for the next note.
     */
    public WhiteNoteGenerator(NoteRangeRestrictor nrr, java.util.Random randGen) {
        restrictor = nrr;
        gen = randGen;
    }
    
    /**
     * Randomly selects pitch, duration for the next note from the full allowable range.
     * 
     * @return The next generated note
     * @throws OutOfMIDIRangeException If the restrictor returns a pitch value out of the range allowed by the MIDI specification.
     */
    @Override
    public Note getNextNote() throws OutOfMIDIRangeException {
        int pitchIndex = gen.nextInt(restrictor.getNumPitches());
        int lengthIndex = gen.nextInt(restrictor.getNumDurations());
        
        return new Note(restrictor.getPitch(pitchIndex),
                restrictor.getDuration(lengthIndex)
                );
    }
    
    private NoteRangeRestrictor restrictor;
    private java.util.Random gen;
}
