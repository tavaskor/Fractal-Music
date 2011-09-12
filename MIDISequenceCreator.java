import javax.sound.midi.*;

// Create a monophonic MIDI sequence on channel 1

public class MIDISequenceCreator {
	public MIDISequenceCreator(NoteGenerator noteGen, int numNotes) throws InvalidMidiDataException, IllegalNoteLengthException {
		seq = new Sequence(Sequence.PPQ, Converter.TICKS_PER_QUARTER_NOTE, 1);
		Track[] trackList = seq.getTracks();
		Track editTrack = trackList[0];

		ShortMessage progChange = new ShortMessage();
		progChange.setMessage(ShortMessage.CONTROL_CHANGE, 1, 1, 1);
		editTrack.add(new MidiEvent(progChange, 1));

		// Forward by some amount; let's arbitrarily add a quarter
		// note delay
		int currentPosition = Converter.TICKS_PER_QUARTER_NOTE;
		final int DEFAULT_VELOCITY = 85;

		for (int i = 0; i < numNotes; i++) {
			Note toAdd = noteGen.getNextNote();
			int pitch = toAdd.getPitch();
			ShortMessage noteOn = new ShortMessage();
			noteOn.setMessage(ShortMessage.NOTE_ON, 1, pitch,
				DEFAULT_VELOCITY);
			editTrack.add(new MidiEvent(noteOn, currentPosition));

			int length = toAdd.getDuration();
			currentPosition += Converter.noteLengthToMIDITicks(length);
			ShortMessage noteOff = new ShortMessage();
			noteOff.setMessage(ShortMessage.NOTE_OFF, 1, pitch,
				DEFAULT_VELOCITY);
			editTrack.add(new MidiEvent(noteOff, currentPosition-1));
		}
	}

	Sequence getSequence() {
		return seq;
	}

	boolean playSequence() {
		try {
                	javax.sound.midi.Sequencer seqer = javax.sound.midi.MidiSystem.getSequencer();
                	seqer.open();
                	seqer.setSequence(seq);
                	seqer.start();
                	while (seqer.isRunning()) {}
                	seqer.stop();
		} catch (Exception e) {
			return false;
		}
		return true;
        }

	private Sequence seq;



	public static void main(String args[]) throws Exception {
		NoteGenerator noteGen = new WhiteNoiseGenerator(
			new NoteRangeRestrictor(35, 85, 1, 64),
			43);	
		MIDISequenceCreator msc = new MIDISequenceCreator(noteGen, 30);

		Sequence seq = msc.getSequence();
		Sequencer seqer = MidiSystem.getSequencer();
		seqer.open();
		seqer.setSequence(seq);
		seqer.start();
		while (seqer.isRunning()) {}
		seqer.stop();
		System.exit(0);
	}
}
