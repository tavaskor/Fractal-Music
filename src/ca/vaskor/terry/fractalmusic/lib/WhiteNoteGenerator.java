package ca.vaskor.terry.fractalmusic.lib;

public class WhiteNoteGenerator extends RandomizedNoteGenerator implements NoteGenerator {
    public WhiteNoteGenerator(NoteRangeRestrictor nrr, java.util.Random randGen) {
        super(nrr, randGen);
    }
    
    @Override
    public Note getNextNote() throws OutOfMIDIRangeException {
        int pitchIndex = getNextInt(restrictor.getNumPitches());
        int lengthIndex = getNextInt(restrictor.getNumDurations());
        
        return new Note(restrictor.getPitch(pitchIndex),
                restrictor.getDuration(lengthIndex));
	}
}
