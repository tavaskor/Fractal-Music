import javax.swing.*;
import java.awt.GridLayout;
import java.lang.NumberFormatException;

/**
 * 
 */

/**
 * @author Terry Vaskor
 *
 */
public class SharedPanel extends JPanel {
	private static final long serialVersionUID = 6273677888021571673L;

	private JComboBox lowPitchField;
	private JComboBox highPitchField;
	private JComboBox longLengthCombo;
	private JComboBox shortLengthCombo;
	
	private JTextField randomSeedField;
	
	private static final String[] noteLengths = {
		"Whole note", "1/2 note", "1/4 note", "1/8 note",
		"1/16 note", "1/32 note", "1/64 note", "1/128 note"
	};
	
	private static final int NUM_PITCHES = 128;
	private static final int PITCH_IN_OCTAVE = 12;
	private static String getNoteName(int noteWithinOctave) {
		switch (noteWithinOctave) {
		case 0: return "C";
		case 1: return "C#";
		case 2: return "D";
		case 3: return "D#";
		case 4: return "E";
		case 5: return "F";
		case 6: return "F#";
		case 7: return "G";
		case 8: return "G#";
		case 9: return "A";
		case 10: return "A#";
		case 11: return "B";
		default: return "DNE";
		}
	}
	private static String[] getPitchNames() {
		String[] ret = new String[NUM_PITCHES];
		
		for (int i = 0; i * PITCH_IN_OCTAVE < NUM_PITCHES - 1; i++) {
			System.err.println(i);
			for (int j = 0; j < PITCH_IN_OCTAVE; j++) {
				int pitchval = (i * PITCH_IN_OCTAVE) + j;
				if (pitchval >= NUM_PITCHES) break;
				ret[pitchval] = getNoteName(j) + i;
			}
		}
		
		return ret;
	}
	private static final String[] pitchNames = getPitchNames();
	
	public SharedPanel() {
		// Add appropriate subsections to this Panel
		this.setLayout( new GridLayout(3, 1) );
		
		JPanel pitchOptions = new JPanel();
		JPanel lengthOptions = new JPanel();		
		JPanel otherOptions = new JPanel();
		
		this.add(pitchOptions);
		this.add(lengthOptions);
		this.add(otherOptions);
		
		// Now Set up private fields
		lowPitchField = new JComboBox(pitchNames);
		highPitchField = new JComboBox(pitchNames);
		longLengthCombo = new JComboBox(noteLengths);
		shortLengthCombo = new JComboBox(noteLengths);
		randomSeedField = new JTextField("12345", 10);

		lowPitchField.setSelectedIndex(32);
		highPitchField.setSelectedIndex(96);
		longLengthCombo.setSelectedIndex(2);
		shortLengthCombo.setSelectedIndex(4);

		
		// And then add these private fields to the proper subsections of the panel.

		otherOptions.setLayout( new BoxLayout( otherOptions, BoxLayout.X_AXIS ) );
		otherOptions.add( new JLabel("Random seed: ") );
		otherOptions.add( randomSeedField );

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
	}
	
	public int getLowPitch() throws NumberFormatException {
		return lowPitchField.getSelectedIndex();
	}
	
	public int getHighPitch() throws NumberFormatException {
		return highPitchField.getSelectedIndex();
	}
	
	public int getShortLength() {
		return shortLengthCombo.getSelectedIndex();
	}
	
	public int getLongLength() {
		return longLengthCombo.getSelectedIndex();
	}
	
	public Long getRandomSeed() throws NumberFormatException {
		return new Long(randomSeedField.getText());
	}
}
