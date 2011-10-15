/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;


import ca.vaskor.terry.fractalmusic.lib.NoteRangeRestrictor;
import ca.vaskor.terry.fractalmusic.lib.NoteGenerator;
import ca.vaskor.terry.fractalmusic.lib.ReflectingBrownNoteGenerator;

/**
 *
 * @author tavaskor
 */
public class BrownMusicPanel extends MusicPanel {
    
    public BrownMusicPanel(
            javax.swing.ButtonGroup radioGroup,
            java.util.List<Object> brownOptions,
            boolean buttonSelected
            ) {
        super(MusicType.BROWN, radioGroup, buttonSelected);
        
        JPanel brownMusicRest = new RecursiveEnableJPanel();
        this.add( brownMusicRest, BorderLayout.CENTER );
        pitchSpread = new ArtificiallyEnlargedJComboBox(brownSpreadOptions);
        lengthSpread = new ArtificiallyEnlargedJComboBox(brownSpreadOptions);
        if (brownOptions == null) {
            pitchSpread.setSelectedItem(1);
            lengthSpread.setSelectedItem(1);
        } else {
            pitchSpread.setSelectedItem(brownOptions.get(0));
            lengthSpread.setSelectedItem(brownOptions.get(1));
        }
        brownMusicRest.add( new JLabel("Max pitch change:") );
        brownMusicRest.add( pitchSpread );
        brownMusicRest.add( new JLabel("Max length step:") );
        brownMusicRest.add( lengthSpread );
    }
    
    @Override
    public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr, java.util.Random randGen) {
        int pitchDiff = (Integer) pitchSpread.getSelectedItem();
        int lengthDiff = (Integer) lengthSpread.getSelectedItem();
        return new ReflectingBrownNoteGenerator(nrr, randGen, 
                    -pitchDiff, pitchDiff, -lengthDiff, lengthDiff);
    }
    
    @Override
    public java.util.EnumMap<MusicType, java.util.List<Object>> getData() {
        return putInHash(pitchSpread.getSelectedItem(), 
                lengthSpread.getSelectedItem());
    }
    
    private static final Integer[] brownSpreadOptions = {1, 2, 3};
    private JComboBox pitchSpread;
    private JComboBox lengthSpread;
}
