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
import ca.vaskor.terry.fractalmusic.lib.ReflectingBrownNoteGenerator;

/**
 *
 * @author tavaskor
 */
public class BrownMusicPanel extends MusicPanel {
    
    public BrownMusicPanel(javax.swing.ButtonGroup radioGroup, boolean buttonSelected) {
        super("Brown music", radioGroup, buttonSelected);
        
        JPanel brownMusicRest = new JPanel();
        this.add( brownMusicRest, BorderLayout.CENTER );
        pitchSpread = new JComboBox(brownSpreadOptions);
        lengthSpread = new JComboBox(brownSpreadOptions);
        pitchSpread.setSelectedIndex(0);
        lengthSpread.setSelectedIndex(0);
        brownMusicRest.add( new JLabel("Max pitch change:") );
        brownMusicRest.add( pitchSpread );
        brownMusicRest.add( new JLabel("Max length step:") );
        brownMusicRest.add( lengthSpread );
    }
    
    @Override
    public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr, long randomSeed) {
        int pitchDiff = (Integer) pitchSpread.getSelectedItem();
        int lengthDiff = (Integer) lengthSpread.getSelectedItem();
        return new ReflectingBrownNoteGenerator(nrr, randomSeed, 
                    -pitchDiff, pitchDiff, -lengthDiff, lengthDiff);
    }
    
    private static final Integer[] brownSpreadOptions = {1, 2, 3};
    private JComboBox pitchSpread;
    private JComboBox lengthSpread;
}
