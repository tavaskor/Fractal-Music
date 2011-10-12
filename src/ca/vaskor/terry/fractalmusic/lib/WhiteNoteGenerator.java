package ca.vaskor.terry.fractalmusic.lib;

public class WhiteNoteGenerator implements NoteGenerator {
    public WhiteNoteGenerator(NoteRangeRestrictor nrr, java.util.Random randGen) {
        restrictor = nrr;
        gen = randGen;
    }
    
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
