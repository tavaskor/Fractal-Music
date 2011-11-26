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
    
    /**
     * 
     * @param radioGroup     The radio button group to which this panel belongs.
     * @param buttonSelected True if this should be the selected panel.
     */
    public WhiteMusicPanel(javax.swing.ButtonGroup radioGroup, boolean buttonSelected) {
        super(MusicType.WHITE, radioGroup, buttonSelected);
    }
    
    @Override
    public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr, java.util.Random randGen) {
        return new WhiteNoteGenerator(nrr, randGen);
    }
    
    @Override
    public java.util.EnumMap<MusicType, java.util.List<Object>> getData() {
        return putInHash();
    }
}
