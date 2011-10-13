package ca.vaskor.terry.fractalmusic.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.GridLayout;
import java.awt.Color;
import java.util.ArrayList;

import java.util.List;

import ca.vaskor.terry.fractalmusic.lib.NoteRangeRestrictor;
import ca.vaskor.terry.fractalmusic.lib.NoteGenerator;

/**
 * @author Terry
 *
 */
public class RadioGeneratorPanel extends GeneratorPanel<GeneratorPanelData> {

	private static final long serialVersionUID = -8041084622014142658L;
	
        private ArrayList<MusicPanel> musicPanels = new ArrayList<MusicPanel>();
	
	public RadioGeneratorPanel(GeneratorPanelData dat) {
            MusicType selectedPanel = MusicType.PINK;
            List<Object> brownOptions = null;
            List<Object> pinkOptions = null;
            if (dat != null) {
                selectedPanel = dat.selected;
                brownOptions = dat.optionMap.get(MusicType.BROWN);
                pinkOptions = dat.optionMap.get(MusicType.PINK);
            }
            
            final ButtonGroup musicOptionGroup = new ButtonGroup();

            musicPanels.add(new WhiteMusicPanel(
                    musicOptionGroup,
                    selectedPanel == MusicType.WHITE
                    ));
            musicPanels.add(new BrownMusicPanel(
                    musicOptionGroup,
                    brownOptions,
                    selectedPanel == MusicType.BROWN));
            musicPanels.add(new PinkMusicPanel(
                    musicOptionGroup,
                    pinkOptions,
                    selectedPanel == MusicType.PINK));

            // Add each MusicPanel to a grid layout; put a definitive
            // black border around each to make separation clear.
            this.setLayout( new GridLayout(3, 1) );
            Border blackLineBorder = BorderFactory.createLineBorder(Color.black);

            for (MusicPanel mp : musicPanels) {
                this.add( mp );
                mp.setBorder(blackLineBorder);
            }
	}
        
	@Override
	public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr,
			java.util.Random randGen) {
            return getSelectedPanel().getNoteGenerator(nrr, randGen);
        }
        
        @Override
        public MusicType getType() {
            return getSelectedPanel().getType();
        }
                
	private MusicPanel getSelectedPanel() {
            for (MusicPanel mp : musicPanels) {
                if (mp.buttonIsSelected()) {
                    return mp;
                }
            }
            
            // We should never reach this point... :\
            return null;
        }
       
        @Override
        public GeneratorPanelData getData() {
            GeneratorPanelData retVal = new GeneratorPanelData(getType());
            
            for (MusicPanel mp : musicPanels) {
                retVal.optionMap.putAll(mp.getData());
            }
            
            return retVal;
        }
}
