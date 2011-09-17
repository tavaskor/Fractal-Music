package ca.vaskor.terry.fractalmusic;

import javax.swing.*;
import java.awt.GridLayout;
import java.lang.NumberFormatException;

import java.util.List;

/**
 * 
 */

// TODO:
// Modify the combo boxes so the lowest value for highPitch is lowPitch and the
// highest value for lowPitch is highPitch.
// Treat the length fields in a similar fashion.

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
	
	private static final List<Duration> durations =
                Duration.getRange(Duration.LOWEST_DURATION, Duration.HIGHEST_DURATION);
                
        private static final List<MIDIPitch> pitches= 
                MIDIPitch.getRange(MIDIPitch.LOWEST_PITCH, MIDIPitch.HIGHEST_PITCH);
	
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
		lowPitchField = new JComboBox(pitches.toArray());
		highPitchField = new JComboBox(pitches.toArray());
		longLengthCombo = new JComboBox(durations.toArray());
		shortLengthCombo = new JComboBox(durations.toArray());
		randomSeedField = new JTextField("12345", 10);

		lowPitchField.setSelectedIndex(32);
		highPitchField.setSelectedIndex(96);
		longLengthCombo.setSelectedIndex(Duration.QUARTER.ordinal());
		shortLengthCombo.setSelectedIndex(Duration.SIXTEENTH.ordinal());

		
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
	
	public MIDIPitch getLowPitch() throws NumberFormatException, OutOfMIDIRangeException {
		return MIDIPitch.getMIDIPitch(lowPitchField.getSelectedIndex());
	}
	
	public MIDIPitch getHighPitch() throws NumberFormatException, OutOfMIDIRangeException {
		return MIDIPitch.getMIDIPitch(highPitchField.getSelectedIndex());
	}
	
	public Duration getShortLength() {
		return durations.get(shortLengthCombo.getSelectedIndex());
	}
	
	public Duration getLongLength() {
		return durations.get(longLengthCombo.getSelectedIndex());
	}
	
	public Long getRandomSeed() throws NumberFormatException {
		return new Long(randomSeedField.getText());
	}
}
