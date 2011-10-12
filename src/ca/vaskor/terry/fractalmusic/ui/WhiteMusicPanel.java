/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

import ca.vaskor.terry.fractalmusic.lib.NoteRangeRestrictor;
import ca.vaskor.terry.fractalmusic.lib.NoteGenerator;
import ca.vaskor.terry.fractalmusic.lib.WhiteNoteGenerator;

/**
 *
 * @author tavaskor
 */
public class WhiteMusicPanel extends MusicPanel {
    public WhiteMusicPanel(javax.swing.ButtonGroup radioGroup, boolean buttonSelected) {
        super("White music", radioGroup, buttonSelected);
    }
    
    @Override
    public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr, java.util.Random randGen) {
        return new WhiteNoteGenerator(nrr, randGen);
    }
}
