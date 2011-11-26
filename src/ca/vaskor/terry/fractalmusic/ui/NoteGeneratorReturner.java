package ca.vaskor.terry.fractalmusic.ui;

import ca.vaskor.terry.fractalmusic.lib.MIDIPitch;
import ca.vaskor.terry.fractalmusic.lib.NoteRangeRestrictor;
import ca.vaskor.terry.fractalmusic.lib.NoteGenerator;

/**
 * An abstract user interface element that is capable of constructing and returning
 * a {@link ca.vaskor.terry.fractalmusic.lib.NoteGenerator}.
 * 
 * It also associates with a particular {@link MusicType} which can be queried.
 * 
 * @author Terry Vaskor
 */
public interface NoteGeneratorReturner {
    /**
     * 
     * @param nrr     A restrictor to apply to the returned value
     * @param randGen The random number generator to be used by the returned value
     * @return A {@link NoteGenerator} which can be used to create a sequence of {@link MIDIPitch}es.
     */
    public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr, java.util.Random randGen);
    
    /**
     * 
     * @return The type of music for which this panel will return a {@link NoteGenerator}.
     */
    public MusicType getType();
}
