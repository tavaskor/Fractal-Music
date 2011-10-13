package ca.vaskor.terry.fractalmusic.ui;

import ca.vaskor.terry.fractalmusic.lib.NoteRangeRestrictor;
import ca.vaskor.terry.fractalmusic.lib.NoteGenerator;

/**
 * @author Terry Vaskor
 *
 */
public interface NoteGeneratorReturner {
	public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr, java.util.Random randGen);
        public MusicType getType();
}
