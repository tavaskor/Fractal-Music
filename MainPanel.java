import javax.swing.*;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;

public class MainPanel extends JPanel implements java.awt.event.ActionListener {
	public MainPanel() {
		super();
		this.setLayout(new BorderLayout());

		generateCommand = new JButton("Generate Music");
		this.add(generateCommand, BorderLayout.SOUTH);

		JPanel theRest = new JPanel();
		this.add(theRest, BorderLayout.CENTER);

		JPanel splitOptions = new JPanel();
		theRest.add(splitOptions);
		JPanel sharedOptions = new JPanel();
		theRest.add(sharedOptions);

		lowPitchField = new JTextField("32", 4);
		highPitchField = new JTextField("96", 4);
		longLengthCombo = new JComboBox(noteLengths);
		shortLengthCombo = new JComboBox(noteLengths);
		randomSeedField = new JTextField("31415", 10);
		noteNumberField = new JTextField("30", 10);

		longLengthCombo.setSelectedIndex(1);
		shortLengthCombo.setSelectedIndex(4);

		JPanel pitchOptions = new JPanel();
		JPanel lengthOptions = new JPanel();		
		JPanel otherOptions = new JPanel();
		sharedOptions.setLayout( new GridLayout(3, 1) );
		sharedOptions.add(pitchOptions);
		sharedOptions.add(lengthOptions);
		sharedOptions.add(otherOptions);

		otherOptions.setLayout( new BoxLayout( otherOptions, BoxLayout.X_AXIS ) );
		otherOptions.add( new JLabel("Random seed: ") );
		otherOptions.add( randomSeedField );
		otherOptions.add( new JLabel("# notes: ") );
		otherOptions.add( noteNumberField );

		pitchOptions.setLayout( new BoxLayout( pitchOptions, BoxLayout.X_AXIS ) );
		pitchOptions.add( new JLabel("Low pitch: ") );
		pitchOptions.add( lowPitchField );
		pitchOptions.add( new JLabel("High pitch: ") );
		pitchOptions.add( highPitchField );

		lengthOptions.setLayout( new BoxLayout( lengthOptions, BoxLayout.X_AXIS ) );
		lengthOptions.add( new JLabel("Shortest length: ") );
		lengthOptions.add(shortLengthCombo);
		lengthOptions.add( new JLabel("Longest length: ") );
		lengthOptions.add(longLengthCombo);

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

		splitOptions.setLayout( new GridLayout(3, 1) );
		splitOptions.add( whiteMusicPanel );
		splitOptions.add( brownMusicPanel );
		splitOptions.add( pinkMusicPanel );

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
/*
		splitOptions.setBorder(blackLineBorder);
		sharedOptions.setBorder(blackLineBorder);
*/
		whiteMusicPanel.setBorder(blackLineBorder);
		brownMusicPanel.setBorder(blackLineBorder);
		pinkMusicPanel.setBorder(blackLineBorder);

		// Set event listener
		generateCommand.addActionListener(this);
	}


	public void actionPerformed(java.awt.event.ActionEvent event) {
		//JOptionPane.showMessageDialog(this, "There's no \"there\" there.");
		int highPitch, lowPitch;
		try {
			lowPitch = (new Integer(lowPitchField.getText())).intValue();
			highPitch = (new Integer(highPitchField.getText())).intValue();
		} catch (java.lang.NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "One or both pitches specified are not valid numbers.");
			return;
		}

		if (highPitch < lowPitch) {
			JOptionPane.showMessageDialog(this, "Low pitch " + lowPitch + " must be lower than high pitch " + highPitch);
			return;
		}
		if (highPitch > 127) {
			JOptionPane.showMessageDialog(this, "Highest valid MIDI pitch is 127");
			return;
		}
		if (lowPitch < 0) {
			JOptionPane.showMessageDialog(this, "Lowest valid MIDI pitch is 0");
			return;
		}

		int selectedIndex = longLengthCombo.getSelectedIndex();
		int longLength = Converter.consecutiveIntToNoteLength(selectedIndex);
		selectedIndex = shortLengthCombo.getSelectedIndex();
		int shortLength =  Converter.consecutiveIntToNoteLength(selectedIndex);
		if (longLength > shortLength) {
			JOptionPane.showMessageDialog(this, "Longest note length should be longer than shortest note value");
			return;
		}

		long randomSeed;
		int numNotes;
		try {
                        randomSeed = (new Long(randomSeedField.getText())).longValue();
		} catch (java.lang.NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Random seed is not a properly formatted long integer");
                        return;
                }

		try {
			numNotes = (new Integer(noteNumberField.getText())).intValue();
		} catch (java.lang.NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Number of notes is not specified as a valid integer");
			return;
		}

		int radioButtonSelected = 0;
		for (; radioButtonSelected < musicSelections.length; radioButtonSelected++) {
			if (musicSelections[radioButtonSelected].isSelected()) {
				break;
			}
		}

		NoteGenerator ng;
		if (musicTypes[radioButtonSelected] == WHITE_MUSIC) {
			try {
				ng = new WhiteNoiseGenerator(
					new NoteRangeRestrictor(lowPitch, highPitch, longLength, shortLength),
					randomSeed
				);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Undetermined error: " + e.toString() );
				return;
			}
		} else if (musicTypes[radioButtonSelected] == PINK_MUSIC) {
			int pitchDice = numPinkPitchDice.getSelectedIndex() + 1;
			int lengthDice = numPinkLengthDice.getSelectedIndex() + 1;
			try {
				ng = new AdditiveNoteGenerator(
					new NoteRangeRestrictor(lowPitch,
highPitch, longLength, shortLength),
					pitchDice, lengthDice, randomSeed
				);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Undetermined error: " + e.toString() );
				return;
			}
		} else { // BROWN_MUSIC
			int pitchDiff = brownPitchSpread.getSelectedIndex() + 1;
			int lengthDiff = brownLengthSpread.getSelectedIndex() + 1;
			try {
				ng = new ReflectingBrownNoiseGenerator(
					new NoteRangeRestrictor(lowPitch,
highPitch, longLength, shortLength),
					randomSeed, -pitchDiff, pitchDiff, -lengthDiff, lengthDiff
				);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Undetermined error: " + e.toString() );
				return;
			}
		}

		try {
			MIDISequenceCreator msc = new MIDISequenceCreator(ng, numNotes);
			msc.playSequence();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Unspecified MIDI Error:\n" + e.toString());
		}
	}

	private JButton generateCommand;
	private JTextField lowPitchField;
	private JTextField highPitchField;
	private JComboBox longLengthCombo;
	private JComboBox shortLengthCombo;

	private JRadioButton[] musicSelections;
	private JComboBox brownPitchSpread;
	private JComboBox brownLengthSpread;
	private JComboBox numPinkPitchDice;
	private JComboBox numPinkLengthDice;

	private JTextField randomSeedField;
	private JTextField noteNumberField;

	private static final String[] brownSpreadOptions = {"1", "2", "3"};
	private static final String[] pinkPitchDiceOptions = {
		"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
	};
	private static final String[] pinkLengthDiceOptions = {"1", "2", "3", "4"};

	private static final String[] noteLengths = {
		"Whole note", "1/2 note", "1/4 note", "1/8 note",
		"1/16 note", "1/32 note", "1/64 note", "1/128 note"
	};

	private static final String WHITE_MUSIC = "White music";
	private static final String BROWN_MUSIC = "Brown music";
	private static final String PINK_MUSIC = "Pink music";
	private static final String[] musicTypes = {
		WHITE_MUSIC, BROWN_MUSIC, PINK_MUSIC
	};
}
