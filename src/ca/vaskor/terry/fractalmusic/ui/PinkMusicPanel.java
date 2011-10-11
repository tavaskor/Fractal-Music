/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.BorderLayout;

import ca.vaskor.terry.fractalmusic.lib.NoteRangeRestrictor;
import ca.vaskor.terry.fractalmusic.lib.NoteGenerator;
import ca.vaskor.terry.fractalmusic.lib.AdditiveNoteGenerator;

/**
 *
 * @author tavaskor
 */
public class PinkMusicPanel extends MusicPanel {
    public PinkMusicPanel(javax.swing.ButtonGroup radioGroup, boolean buttonSelected) {
        super("Pink music", radioGroup, buttonSelected);
        
        JPanel pinkMusicRest = new RecursiveEnableJPanel();
        this.add( pinkMusicRest, BorderLayout.CENTER );
        numPitchDice = new JComboBox(pinkPitchDiceOptions);
        numLengthDice = new JComboBox(pinkLengthDiceOptions);
        numPitchDice.setSelectedIndex(5);
        numLengthDice.setSelectedIndex(2);
        pinkMusicRest.add( new JLabel("# pitch die:") );
        pinkMusicRest.add( numPitchDice );
        pinkMusicRest.add( new JLabel("# length die:") );
        pinkMusicRest.add( numLengthDice );

    }
    
    @Override
    public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr, long randomSeed) {
        int pitchDice = (Integer) numPitchDice.getSelectedItem();
        int lengthDice = (Integer) numLengthDice.getSelectedItem();
        return new AdditiveNoteGenerator(nrr, pitchDice, lengthDice, randomSeed);
    }
    
    private JComboBox numPitchDice;
    private JComboBox numLengthDice;

    private static final Integer[] pinkPitchDiceOptions = 
        {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final Integer[] pinkLengthDiceOptions = {1, 2, 3, 4};
}
