package ca.vaskor.terry.fractalmusic.ui;

import javax.swing.*;
import java.awt.GridLayout;

import java.util.List;


import ca.vaskor.terry.fractalmusic.lib.NoteRangeRestrictor;
import ca.vaskor.terry.fractalmusic.lib.Duration;
import ca.vaskor.terry.fractalmusic.lib.MIDIPitch;
import ca.vaskor.terry.fractalmusic.lib.PitchName;
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
public class SharedPanel
extends RecursiveEnableJPanel
implements DataRetriever<SharedPanelData> {
	private static final long serialVersionUID = 6273677888021571673L;
	
	private static final List<Duration> durations =
                Duration.getRange(Duration.LOWEST_DURATION, Duration.HIGHEST_DURATION);
                
        private static final List<MIDIPitch> pitches= 
                MIDIPitch.getRange(MIDIPitch.LOWEST_PITCH, MIDIPitch.HIGHEST_PITCH);

	private PairedJComboBox lowPitchField = 
                new PairedJComboBox(pitches.toArray());
	private PairedJComboBox highPitchField = 
                new PairedJComboBox(pitches.toArray());
	private PairedJComboBox longLengthCombo = 
                new PairedJComboBox(durations.toArray());
	private PairedJComboBox shortLengthCombo = 
                new PairedJComboBox(durations.toArray());
	private JComboBox scaleCombo = 
                new JComboBox(ScaleType.values());
	
        private JCheckBox randomSeedEnable = new JCheckBox("Random seed: ");
	private JTextField randomSeedField;
	
	public SharedPanel(SharedPanelData settings) {
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
		
		// Now initialize values of the GUI fields
                
                initializeFields(settings);
		
		// And then add these fields to the proper subsections of the panel.

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
        
        private void initializeFields(SharedPanelData settings) {
            lowPitchField.pairWithHigher(highPitchField);
            shortLengthCombo.pairWithHigher(longLengthCombo);
            
            if (settings == null) {
                try {
                    initializeFields(
                            new SharedPanelData(
                                    MIDIPitch.getMIDIPitch(PitchName.G_SHARP, 2),
                                    MIDIPitch.getMIDIPitch(PitchName.C, 8),
                                    Duration.SIXTEENTH,
                                    Duration.QUARTER,
                                    ScaleType.CHROMATIC,
                                    "12345",
                                    false
                                    )
                            );
                    return;
                } catch (ca.vaskor.terry.fractalmusic.lib.OutOfMIDIRangeException exn) {
                    throw new Error("Code error: Hardcoded MIDI value out of range!");
                }
            }
            randomSeedField = new JTextField(settings.randomSeed, 10);
            randomSeedEnable.setSelected(settings.isSeedEnabled);

            lowPitchField.setSelectedItem(settings.lowPitch);
            highPitchField.setSelectedItem(settings.highPitch);
            longLengthCombo.setSelectedItem(settings.longLength);
            shortLengthCombo.setSelectedItem(settings.shortLength);
            scaleCombo.setSelectedItem(settings.scale);
        }
	
        public NoteRangeRestrictor getNoteRangeRestrictor() {
            SharedPanelData dat = getData();
            return new NoteRangeRestrictor(
                    dat.lowPitch,
                    dat.highPitch,
                    dat.shortLength,
                    dat.longLength,
                    dat.scale
                    );
        }
	
	public Long getRandomSeed() throws NumberFormatException {
            if (randomSeedEnable.isSelected()) {
                return new Long(randomSeedField.getText());
            } else {
                return null;
            }
	}
        
        @Override
        public SharedPanelData getData() {
            return new SharedPanelData(
                    (MIDIPitch) lowPitchField.getSelectedItem(),
                    (MIDIPitch) highPitchField.getSelectedItem(),
                    (Duration) shortLengthCombo.getSelectedItem(),
                    (Duration) longLengthCombo.getSelectedItem(),
                    (ScaleType) scaleCombo.getSelectedItem(),
                    randomSeedField.getText(),
                    randomSeedEnable.isSelected()
                    );
        }
}
