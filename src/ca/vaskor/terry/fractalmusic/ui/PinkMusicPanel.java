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
    public PinkMusicPanel(
            javax.swing.ButtonGroup radioGroup,
            java.util.List<Object> pinkOptions,
            boolean buttonSelected) {
        super(MusicType.PINK, radioGroup, buttonSelected);
        
        JPanel pinkMusicRest = new RecursiveEnableJPanel();
        this.add( pinkMusicRest, BorderLayout.CENTER );
        numPitchDice = new JComboBox(pinkPitchDiceOptions);
        numLengthDice = new JComboBox(pinkLengthDiceOptions);
        if (pinkOptions == null) {
            numPitchDice.setSelectedItem(6);
            numLengthDice.setSelectedItem(3);
        } else {
            numPitchDice.setSelectedItem(pinkOptions.get(0));
            numLengthDice.setSelectedItem(pinkOptions.get(1));
        }
        pinkMusicRest.add( new JLabel("# pitch die:") );
        pinkMusicRest.add( numPitchDice );
        pinkMusicRest.add( new JLabel("# length die:") );
        pinkMusicRest.add( numLengthDice );

    }
    
    @Override
    public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr, java.util.Random randGen) {
        int pitchDice = (Integer) numPitchDice.getSelectedItem();
        int lengthDice = (Integer) numLengthDice.getSelectedItem();
        return new AdditiveNoteGenerator(nrr, pitchDice, lengthDice, randGen);
    }
    
    @Override
    public java.util.EnumMap<MusicType, java.util.List<Object>> getData() {
        return putInHash(numPitchDice.getSelectedItem(), numLengthDice.getSelectedItem());
    }
    
    private JComboBox numPitchDice;
    private JComboBox numLengthDice;

    private static final Integer[] pinkPitchDiceOptions = 
        {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final Integer[] pinkLengthDiceOptions = {1, 2, 3, 4};
}
