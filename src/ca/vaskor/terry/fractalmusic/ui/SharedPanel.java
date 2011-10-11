package ca.vaskor.terry.fractalmusic.ui;

import javax.swing.*;
import java.awt.GridLayout;

import java.util.List;


import ca.vaskor.terry.fractalmusic.lib.NoteRangeRestrictor;
import ca.vaskor.terry.fractalmusic.lib.Duration;
import ca.vaskor.terry.fractalmusic.lib.MIDIPitch;
import ca.vaskor.terry.fractalmusic.lib.ScaleType;

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
public class SharedPanel extends RecursiveEnableJPanel {
	private static final long serialVersionUID = 6273677888021571673L;

	private JComboBox lowPitchField;
	private JComboBox highPitchField;
	private JComboBox longLengthCombo;
	private JComboBox shortLengthCombo;
	private JComboBox scaleCombo;
	
        private JCheckBox randomSeedEnable;
	private JTextField randomSeedField;
	
	private static final List<Duration> durations =
                Duration.getRange(Duration.LOWEST_DURATION, Duration.HIGHEST_DURATION);
                
        private static final List<MIDIPitch> pitches= 
                MIDIPitch.getRange(MIDIPitch.LOWEST_PITCH, MIDIPitch.HIGHEST_PITCH);
	
	public SharedPanel() {
		// Add appropriate subsections to this Panel
		this.setLayout( new GridLayout(4, 1) );
		
		JPanel pitchOptions = new RecursiveEnableJPanel();
		JPanel lengthOptions = new RecursiveEnableJPanel();	
                JPanel scaleOptions = new RecursiveEnableJPanel();
		JPanel otherOptions = new RecursiveEnableJPanel();
		
		this.add(pitchOptions);
		this.add(lengthOptions);
                this.add(scaleOptions);
		this.add(otherOptions);
		
		// Now Set up private fields
		lowPitchField = new JComboBox(pitches.toArray());
		highPitchField = new JComboBox(pitches.toArray());
		longLengthCombo = new JComboBox(durations.toArray());
		shortLengthCombo = new JComboBox(durations.toArray());
                scaleCombo = new JComboBox(ScaleType.values());
                
                randomSeedEnable = new JCheckBox("Random seed: ");
		randomSeedField = new JTextField("12345", 10);
                randomSeedEnable.setSelected(false);

		lowPitchField.setSelectedIndex(32);
		highPitchField.setSelectedIndex(96);
		longLengthCombo.setSelectedIndex(Duration.QUARTER.ordinal());
		shortLengthCombo.setSelectedIndex(Duration.SIXTEENTH.ordinal());
                scaleCombo.setSelectedIndex(0);

		
		// And then add these private fields to the proper subsections of the panel.

                scaleOptions.setLayout( new BoxLayout( scaleOptions, BoxLayout.X_AXIS ) );
		scaleOptions.add( new JLabel("Scale: ") );
		scaleOptions.add( scaleCombo );
                
		otherOptions.setLayout( new BoxLayout( otherOptions, BoxLayout.X_AXIS ) );
		otherOptions.add( randomSeedEnable );
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
	
        public NoteRangeRestrictor getNoteRangeRestrictor() {
            return new NoteRangeRestrictor(
                    (MIDIPitch) lowPitchField.getSelectedItem(),
                    (MIDIPitch) highPitchField.getSelectedItem(),
                    (Duration) shortLengthCombo.getSelectedItem(),
                    (Duration) longLengthCombo.getSelectedItem(),
                    (ScaleType) scaleCombo.getSelectedItem()
                    );
        }
	
	public Long getRandomSeed() throws NumberFormatException {
            if (randomSeedEnable.isSelected()) {
                return new Long(randomSeedField.getText());
            } else {
                return null;
            }
	}
}
