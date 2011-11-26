package ca.vaskor.terry.fractalmusic.ui;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.BorderLayout;


import ca.vaskor.terry.fractalmusic.lib.NoteRangeRestrictor;
import ca.vaskor.terry.fractalmusic.lib.NoteGenerator;
import ca.vaskor.terry.fractalmusic.lib.ReflectingBrownNoteGenerator;

/**
 * A GUI panel to collect information for a {@link ca.vaskor.terry.fractalmusic.lib.BrownNoteGenerator}.
 * 
 * @author Terry Vaskor
 */
public class BrownMusicPanel extends MusicPanel {
    
    /**
     * 
     * @param radioGroup     The radio button group to which this panel belongs.
     * @param brownOptions   The options to be selected in this panel.  If null, the defaults will be used.
     * @param buttonSelected True if this should be the selected panel.
     */
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
    
    /**
     * 
     * @param nrr     A restrictor to apply to the returned {@link ca.vaskor.terry.fractalmusic.lib.NoteGenerator}.
     * @param randGen The random number generator to use to generate notes.
     * @return A {@link ca.vaskor.terry.fractalmusic.lib.BrownNoteGenerator}
     * based on the options selected in this panel.
     */
    @Override
    public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr, java.util.Random randGen) {
        int pitchDiff = (Integer) pitchSpread.getSelectedItem();
        int lengthDiff = (Integer) lengthSpread.getSelectedItem();
        return new ReflectingBrownNoteGenerator(nrr, randGen, 
                    -pitchDiff, pitchDiff, -lengthDiff, lengthDiff);
    }
    
    /**
     * 
     * @return A list of the selections for the pitch and duration spreads on this panel.
     */
    @Override
    public java.util.EnumMap<MusicType, java.util.List<Object>> getData() {
        return putInHash(pitchSpread.getSelectedItem(), 
                lengthSpread.getSelectedItem());
    }
    
    private static final Integer[] brownSpreadOptions = {1, 2, 3};
    private JComboBox pitchSpread;
    private JComboBox lengthSpread;
}
