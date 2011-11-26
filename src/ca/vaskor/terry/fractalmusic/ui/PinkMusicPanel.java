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
import ca.vaskor.terry.fractalmusic.lib.PinkNoteGenerator;

/**
 * A GUI panel to collect information for a {@link ca.vaskor.terry.fractalmusic.lib.PinkNoteGenerator}.
 * 
 * @author Terry Vaskor
 */
public class PinkMusicPanel extends MusicPanel {
    
    /**
     * 
     * @param radioGroup     The radio button group to which this panel belongs.
     * @param pinkOptions   The options to be selected in this panel.  If null, the defaults will be used.
     * @param buttonSelected True if this should be the selected panel.
     */
    public PinkMusicPanel(
            javax.swing.ButtonGroup radioGroup,
            java.util.List<Object> pinkOptions,
            boolean buttonSelected) {
        super(MusicType.PINK, radioGroup, buttonSelected);
        
        JPanel pinkMusicRest = new RecursiveEnableJPanel();
        this.add( pinkMusicRest, BorderLayout.CENTER );
        numPitchDice = new ArtificiallyEnlargedJComboBox(pinkPitchDiceOptions);
        numLengthDice = new ArtificiallyEnlargedJComboBox(pinkLengthDiceOptions);
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
    
    /**
     * 
     * @param nrr     A restrictor to apply to the returned {@link ca.vaskor.terry.fractalmusic.lib.NoteGenerator}.
     * @param randGen The random number generator to use to generate notes.
     * @return A {@link ca.vaskor.terry.fractalmusic.lib.PinkNoteGenerator}
     * based on the options selected in this panel.
     */
    @Override
    public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr, java.util.Random randGen) {
        int pitchDice = (Integer) numPitchDice.getSelectedItem();
        int lengthDice = (Integer) numLengthDice.getSelectedItem();
        return new PinkNoteGenerator(nrr, pitchDice, lengthDice, randGen);
    }
    
    /**
     * 
     * @return The selected items for pitch and duration.
     */    
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
