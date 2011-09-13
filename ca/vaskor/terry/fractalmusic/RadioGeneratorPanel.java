import javax.swing.*;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;

/**
 * @author Terry
 *
 */
public class RadioGeneratorPanel extends NoteGeneratorReturner {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8041084622014142658L;
	
	
	private JRadioButton[] musicSelections;
	private JComboBox brownPitchSpread;
	private JComboBox brownLengthSpread;
	private JComboBox numPinkPitchDice;
	private JComboBox numPinkLengthDice;


	private static final String[] brownSpreadOptions = {"1", "2", "3"};
	private static final String[] pinkPitchDiceOptions = {
		"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
	};
	private static final String[] pinkLengthDiceOptions = {"1", "2", "3", "4"};


	private static final String WHITE_MUSIC = "White music";
	private static final String BROWN_MUSIC = "Brown music";
	private static final String PINK_MUSIC = "Pink music";
	private static final String[] musicTypes = {
		WHITE_MUSIC, BROWN_MUSIC, PINK_MUSIC
	};
	
	
	public RadioGeneratorPanel() {
		final int numMusicOptions = 3;
		musicSelections = new JRadioButton[numMusicOptions];
		final ButtonGroup musicOptionGroup = new ButtonGroup();
		for (int i = 0; i < numMusicOptions; i++) {
			musicSelections[i] = new JRadioButton(musicTypes[i]);
			musicSelections[i].setActionCommand(musicTypes[i]);
			musicOptionGroup.add(musicSelections[i]);
		}
		musicSelections[0].setSelected(true);

		JPanel whiteMusicPanel = new JPanel();
		JPanel brownMusicPanel = new JPanel();
		JPanel pinkMusicPanel = new JPanel(); 
		JPanel[] musicPanels = new JPanel[numMusicOptions];
		musicPanels[0] = whiteMusicPanel;
		musicPanels[1] = brownMusicPanel;
		musicPanels[2] = pinkMusicPanel;

		this.setLayout( new GridLayout(3, 1) );
		this.add( whiteMusicPanel );
		this.add( brownMusicPanel );
		this.add( pinkMusicPanel );

		for (int i = 0; i < numMusicOptions; i++) {
			musicPanels[i].setLayout( new BorderLayout() );
			musicPanels[i].add( musicSelections[i], BorderLayout.WEST );
		}

		JPanel brownMusicRest = new JPanel();
		brownMusicPanel.add( brownMusicRest, BorderLayout.CENTER );
		brownPitchSpread = new JComboBox(brownSpreadOptions);
		brownLengthSpread = new JComboBox(brownSpreadOptions);
		brownPitchSpread.setSelectedIndex(0);
		brownLengthSpread.setSelectedIndex(0);
		brownMusicRest.add( new JLabel("Max pitch change:") );
		brownMusicRest.add( brownPitchSpread );
		brownMusicRest.add( new JLabel("Max length step:") );
		brownMusicRest.add( brownLengthSpread );

		JPanel pinkMusicRest = new JPanel();
		pinkMusicPanel.add( pinkMusicRest, BorderLayout.CENTER );
		numPinkPitchDice = new JComboBox(pinkPitchDiceOptions);
		numPinkLengthDice = new JComboBox(pinkLengthDiceOptions);
		numPinkPitchDice.setSelectedIndex(5);
		numPinkLengthDice.setSelectedIndex(2);
		pinkMusicRest.add( new JLabel("# pitch die:") );
		pinkMusicRest.add( numPinkPitchDice );
		pinkMusicRest.add( new JLabel("# length die:") );
		pinkMusicRest.add( numPinkLengthDice );

		// Control Borders
		Border blackLineBorder = BorderFactory.createLineBorder(Color.black);
		whiteMusicPanel.setBorder(blackLineBorder);
		brownMusicPanel.setBorder(blackLineBorder);
		pinkMusicPanel.setBorder(blackLineBorder);
	}
	
	/* (non-Javadoc)
	 * @see NoteGeneratorReturner#getNoteGenerator(NoteRangeRestrictor, long)
	 */
	@Override
	public NoteGenerator getNoteGenerator(NoteRangeRestrictor nrr,
			long randomSeed) {
		int radioButtonSelected = 0;
		for (; radioButtonSelected < musicSelections.length; radioButtonSelected++) {
			if (musicSelections[radioButtonSelected].isSelected()) {
				break;
			}
		}

		NoteGenerator ng = null;  // Default value
		if (musicTypes[radioButtonSelected] == WHITE_MUSIC) {
			try {
				ng = new WhiteNoiseGenerator(nrr, randomSeed);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Undetermined error: " + e.toString() );
			}
		} else if (musicTypes[radioButtonSelected] == PINK_MUSIC) {
			int pitchDice = numPinkPitchDice.getSelectedIndex() + 1;
			int lengthDice = numPinkLengthDice.getSelectedIndex() + 1;
			try {
				ng = new AdditiveNoteGenerator(nrr, pitchDice, lengthDice, randomSeed);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Undetermined error: " + e.toString() );
			}
		} else { // BROWN_MUSIC
			int pitchDiff = brownPitchSpread.getSelectedIndex() + 1;
			int lengthDiff = brownLengthSpread.getSelectedIndex() + 1;
			try {
				ng = new ReflectingBrownNoiseGenerator(nrr, randomSeed, 
						-pitchDiff, pitchDiff, -lengthDiff, lengthDiff);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Undetermined error: " + e.toString() );
			}
		}
		return ng;
	}

}
