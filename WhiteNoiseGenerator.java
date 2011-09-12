public class WhiteNoiseGenerator extends RandomizedNoteGenerator implements NoteGenerator {
	public WhiteNoiseGenerator(NoteRangeRestrictor nrr, long randomSeed) {
		super(nrr, randomSeed);
	}

	public Note getNextNote() {
		int lowestPitch = restrictor.getLowPitch();
		int pitch = lowestPitch + getNextInt( restrictor.getHighPitch() - lowestPitch + 1);

		int longLength = restrictor.getLongestLength();
		int shortLength = restrictor.getShortestLength();
		int lowRange = Converter.noteLengthToConsecutiveInt(longLength);
		int randomNum = lowRange + getNextInt( 
			Converter.noteLengthToConsecutiveInt(shortLength) -
			lowRange + 1);
		int length = Converter.consecutiveIntToNoteLength(randomNum);

		return new Note(pitch, length);
	}

        public static void main(String args[]) throws Exception {
                NoteGenerator noteGen = new WhiteNoiseGenerator(
                        new NoteRangeRestrictor(35, 85, 1, 64),
                        58);
                MIDISequenceCreator msc = new MIDISequenceCreator(noteGen, 30);
                                                                                
                javax.sound.midi.Sequence seq = msc.getSequence();
                javax.sound.midi.Sequencer seqer = javax.sound.midi.MidiSystem.getSequencer();
                seqer.open();
                seqer.setSequence(seq);
                seqer.start();
                while (seqer.isRunning()) {}
                seqer.stop();
                System.exit(0);
        }

}
