package ca.vaskor.terry.fractalmusic;

import javax.sound.midi.*;

import java.util.Queue;
import java.util.LinkedList;

//Create a monophonic MIDI sequence on channel 1

public class MIDISequenceCreator implements Runnable {
	public MIDISequenceCreator(NoteGenerator noteGen) 
	throws InvalidMidiDataException, MidiUnavailableException {
		gen = noteGen;
		
		// Initialize variables used by both this class and the inner class's thread.
		seqer = javax.sound.midi.MidiSystem.getSequencer();
		seqer.open();
		seq = new Sequence(Sequence.PPQ, TICKS_PER_QUARTER_NOTE, 1); 
		Track[] trackList = seq.getTracks();
		editTrack = trackList[0];
	}
	
	
	public void haltExecution() {
		finished = true;
	}
	
    @Override
	public void run() {
		// Start the NoteAdder to populate the MIDI sequence.
		NoteAdder na = new NoteAdder();
		na.start();
		
		System.err.println("Boogie");
		// Now start playing!
		try {
			seqer.setSequence(seq);
			seqer.start();

			// To try to help with memory leaks, remove old notes from the
			// beginning of the sequence after they have been played.
			// This method only returns when the sequence is stopped.
			removeOldNotesUntilFinished();

			seqer.stop();
		} catch (Exception e) {}
		System.err.println("Boogah");
	}

	void playSequence() {
		Thread t = new Thread(this);
		t.start();
	}
	
	private void removeOldNotesUntilFinished() {
		MidiEvent bert;
		synchronized(MIDISequenceCreator.this.lockProtector) {
			bert = MIDISequenceCreator.this.eventTrack.remove();
		}
		
		while (!MIDISequenceCreator.this.finished) {
			// Get the next event to remove

			// Wait until it's *very* safe to remove
			boolean continueLoop = true;
			while (continueLoop && !this.finished) {
				System.err.println("Loop " + seqer.getTickPosition());
				try {
					while ((seqer.getTickPosition() - bert.getTick()) 
							< 2 * durationToMIDITicks(Duration.WHOLE)) {
						Thread.sleep(150);
					}
					continueLoop = false;
				}
				catch (Exception e) {
					System.err.println(e.toString());
				}
			}

			// Now remove it.
			synchronized(MIDISequenceCreator.this.lockProtector) {
				System.err.println("Removing ... " + bert.getMessage() + ", " + bert.getTick());
//				MIDISequenceCreator.this.editTrack.remove(bert); // Actual removal disturbs MIDI playback... :(
				bert = MIDISequenceCreator.this.eventTrack.remove();
			}
		}
	}

	private static final int DEFAULT_VELOCITY = 85;

	private boolean finished = false;

	private Sequencer seqer;
	private Sequence seq;
	private Track editTrack;
	private NoteGenerator gen;
	private Queue<MidiEvent> eventTrack = new LinkedList<MidiEvent>();
	private final Object lockProtector = new Object();

	
	private class NoteAdder implements Runnable {
		private int adderPosition = 1;
		
		public NoteAdder() {
			System.err.println("Constructor begun");
			addNotes();
			System.err.println("Constructor complete");
		}
		
		// Add notes until we are 10*(length of whole note) ahead of the current location
		// in the MIDI sequence.
		private void addNotes() {
			try {
				while ( (adderPosition - seqer.getTickPosition()) < 10 * durationToMIDITicks(Duration.WHOLE)) {
					addANote();
				}
			}
			catch (Exception e) {
				System.err.println(e.toString());
				addNotes();
			}
		}
		
		// Add an extra note to the queue/sequence
		private void addANote() throws Exception {			
			Note toAdd = gen.getNextNote();
			int pitch = toAdd.getPitch().getMIDICode();
			
			ShortMessage noteOn = new ShortMessage();
			noteOn.setMessage(ShortMessage.NOTE_ON, 1, pitch,
					DEFAULT_VELOCITY);
			MidiEvent meep = new MidiEvent(noteOn, adderPosition);

			Duration length = toAdd.getDuration();
			adderPosition += durationToMIDITicks(length);
			
			ShortMessage noteOff = new ShortMessage();
			noteOff.setMessage(ShortMessage.NOTE_OFF, 1, pitch,
					DEFAULT_VELOCITY);
			MidiEvent moop = new MidiEvent(noteOff, adderPosition - 1);
			
			// Concurrent step which needs to be protected
			synchronized(MIDISequenceCreator.this.lockProtector) {
				editTrack.add(meep);
				eventTrack.add(meep);
				editTrack.add(moop);
				eventTrack.add(moop);
			}
			System.err.println("Adding ... " + meep.getMessage() + ", " + meep.getTick() + " - " 
					+ moop.getMessage() + ", " + moop.getTick() + " ...");
		}
		
        @Override
		public void run() {
			while (!MIDISequenceCreator.this.finished) {
				try {
					addNotes();
					Thread.sleep(53);
				} 
				catch (InterruptedException e) {}
			}
		}
		
		public void start() {
			Thread t = new Thread(this);
			t.start();
		}
	}
        
        
    private static final int TICKS_PER_QUARTER_NOTE = 64;
    
    private static int durationToMIDITicks(Duration noteLength) {
        final int tpqn = TICKS_PER_QUARTER_NOTE;
        switch(noteLength) {
            case WHOLE: return tpqn * 4;
            case HALF: return tpqn * 2;
            case QUARTER: return tpqn;
            case EIGHTH: return (int) (tpqn / 2);
            case SIXTEENTH: return (int) (tpqn / 4);
            case THIRTY_SECOND: return (int) (tpqn / 8);
            case SIXTY_FOURTH: return (int) (tpqn / 16);
            default: return (int) (tpqn / 32);
        }
    }
}
