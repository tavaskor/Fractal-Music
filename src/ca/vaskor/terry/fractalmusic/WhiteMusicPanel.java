/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic;

/**
 *
 * @author tavaskor
 */
public class WhiteMusicPanel extends MusicPanel {
    public WhiteMusicPanel(javax.swing.ButtonGroup radioGroup, boolean buttonSelected) {
        super("White music", radioGroup, buttonSelected);
    }
    
    @Override
    public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr, long randomSeed) {
        return new WhiteNoiseGenerator(nrr, randomSeed);
    }
}
