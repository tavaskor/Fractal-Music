package ca.vaskor.terry.fractalmusic;

/**
 * 
 */

/**
 * @author Terry Vaskor
 *
 */
public abstract class NoteGeneratorReturner extends javax.swing.JPanel {
	public abstract NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr, long randomSeed);
}
