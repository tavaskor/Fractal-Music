package ca.vaskor.terry.fractalmusic.lib;

import javax.sound.midi.*;

import java.util.Queue;
import java.util.LinkedList;


/**
 * Creates a monophonic MIDI sequence on channel 1
 * 
 * @author Terry Vaskor
 */
public class MIDISequenceCreator implements Runnable {
    /**
     * 
     * @param noteGen    Used to get every {@link Note} to be played.
     * @param instrument The instrument to use in this MIDI sequence.
     * @param volume     The volume setting for all generated notes.
     * @throws InvalidMidiDataException
     * @throws MidiUnavailableException 
     */
    public MIDISequenceCreator(NoteGenerator noteGen, GeneralMIDIInstrument instrument, int volume) 
            throws InvalidMidiDataException, MidiUnavailableException {
        gen = noteGen;
		
        // Initialize variables used by both this class and the inner class's thread.
        seqer = javax.sound.midi.MidiSystem.getSequencer();
        synth = MidiSystem.getSynthesizer();
        seqer.open();
        seq = new Sequence(Sequence.PPQ, TICKS_PER_QUARTER_NOTE, 1); 
        Track[] trackList = seq.getTracks();
        editTrack = trackList[0];
        
        midiInstrument = instrument;
        vol = volume;
    }
    
    
    /**
     * Stops playing this sequence of MIDI data.
     */
    public void haltExecution() {
        finished = true;
        seqer.stop();
    }
    
    /**
     * Begins playing the sequence of MIDI data.
     * 
     * This will continue until the thread is explicitly stopped by
     * a call to {@link #haltExecution}.
     */
    @Override
    public void run() {
        // Start the NoteAdder to populate the MIDI sequence.
        NoteAdder na = new NoteAdder();
        na.start();

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
    }

    /**
     * A wrapper for {@link #run} which creates a new {@link java.lang.Thread}
     * to do the playing.
     */
    public void playSequence() {
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
                System.err.println(" (Loop " + seqer.getTickPosition() + ")");
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
                System.err.println(" (Removing ... " + bert.getMessage() + ", " + bert.getTick() + ")");
//                MIDISequenceCreator.this.editTrack.remove(bert); // Actual removal disturbs MIDI playback... :(
                bert = MIDISequenceCreator.this.eventTrack.remove();
            }
        }
    }

    private static final int DEFAULT_VELOCITY = 85;

    private boolean finished = false;

    private Synthesizer synth;
    private Sequencer seqer;
    private Sequence seq;
    private Track editTrack;
    private NoteGenerator gen;
    private Queue<MidiEvent> eventTrack = new LinkedList<MidiEvent>();
    private final Object lockProtector = new Object();
    private GeneralMIDIInstrument midiInstrument;
    private int vol;

	
    private class NoteAdder implements Runnable {
        private int adderPosition = 1;
        
        private static final int COARSE_VOLUME_CTRL = 7;

        public NoteAdder() {
            // Set the instrument first as well as the volume
            ShortMessage prgChange = new ShortMessage();
            ShortMessage volChange = new ShortMessage();
            try {
                prgChange.setMessage(
                        ShortMessage.PROGRAM_CHANGE, 
                        1,
                        midiInstrument.ordinal(),
                        0
                        );
                volChange.setMessage(
                        ShortMessage.CONTROL_CHANGE,
                        1,
                        COARSE_VOLUME_CTRL,
                        vol
                        );
            } catch (InvalidMidiDataException e) { System.err.println("FAIL"); }
            MidiEvent meep = new MidiEvent(prgChange, adderPosition);
            editTrack.add(meep);
            meep = new MidiEvent(volChange, adderPosition + 1);
            editTrack.add(meep);
            adderPosition+=2;
            
            addNotes();
        }

        // Add notes until we are 10*(length of whole note) ahead of the current location
        // in the MIDI sequence.
        private void addNotes() {
            try {
                while ( 
                        (adderPosition - seqer.getTickPosition()) 
                        < 
                        10 * durationToMIDITicks(Duration.WHOLE)
                        ) {
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
            System.err.println(
                    "Adding " + toAdd.toString() + ": " + 
                    meep.getMessage() + ", " + meep.getTick() + " + " + 
                    moop.getMessage() + ", " + moop.getTick()
                    );
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
