package ca.vaskor.terry.fractalmusic.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.GridLayout;
import java.awt.Color;
import java.util.ArrayList;

import ca.vaskor.terry.fractalmusic.lib.NoteRangeRestrictor;
import ca.vaskor.terry.fractalmusic.lib.NoteGenerator;

/**
 * @author Terry
 *
 */
public class RadioGeneratorPanel extends NoteGeneratorReturner {

	private static final long serialVersionUID = -8041084622014142658L;
	
        private ArrayList<MusicPanel> musicPanels = new ArrayList<MusicPanel>();
	
	public RadioGeneratorPanel() {
		final ButtonGroup musicOptionGroup = new ButtonGroup();
                
                musicPanels.add(new WhiteMusicPanel(musicOptionGroup, true));
                musicPanels.add(new BrownMusicPanel(musicOptionGroup, false));
                musicPanels.add(new PinkMusicPanel(musicOptionGroup, false));
                
                // Add each MusicPanel to a grid layout; put a definitive
                // black border around each to make separation clear.
		this.setLayout( new GridLayout(3, 1) );
		Border blackLineBorder = BorderFactory.createLineBorder(Color.black);
                
                for (MusicPanel mp : musicPanels) {
                    this.add( mp );
                    mp.setBorder(blackLineBorder);
                }
	}
	
	/* (non-Javadoc)
	 * @see NoteGeneratorReturner#getNoteGenerator(NoteRangeRestrictor, long)
	 */
	@Override
	public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr,
			java.util.Random randGen) {
            for (MusicPanel mp : musicPanels) {
                if (mp.buttonIsSelected()) {
                    return mp.getNoteGenerator(nrr, randGen);
                }
            }
            
            // We should never reach this point... :\
            return null;
	}

}
