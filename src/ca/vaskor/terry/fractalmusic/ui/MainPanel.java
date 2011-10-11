package ca.vaskor.terry.fractalmusic.ui;

import javax.swing.*;
import java.awt.BorderLayout;

import ca.vaskor.terry.fractalmusic.lib.NoteGenerator;
import ca.vaskor.terry.fractalmusic.lib.MIDISequenceCreator;

public class MainPanel extends JPanel implements java.awt.event.ActionListener {
    
    private static final long serialVersionUID = 7274657133880441695L;
    
    public MainPanel() {
        super();
        this.setLayout(new BorderLayout());
        
        // Set event listener
        generateCommand.addActionListener(this);
        
        this.add(generateCommand, BorderLayout.SOUTH);
        
        JPanel theRest = new JPanel();
        this.add(theRest, BorderLayout.CENTER);
        
        theRest.add(this.ngr);
        theRest.add(this.sharedOpts);
        
        layoutChildPanel(theRest);
    }
        
    private void layoutChildPanel(JPanel theRest) {
        SpringLayout layout = new SpringLayout();
        theRest.setLayout(layout);
        
        final int PANEL_PADDING = 5;
        
        // Put the NoteGeneratorReturner at the top of the panel,
        // centred horizontally.
        layout.putConstraint(SpringLayout.NORTH, ngr,
                PANEL_PADDING,
                SpringLayout.NORTH, theRest);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, ngr,
                0,
                SpringLayout.HORIZONTAL_CENTER, theRest);
        
        // Put the SharedPanel directly below the NoteGeneratorReturner,
        // also centred horizontally.
        layout.putConstraint(SpringLayout.NORTH, sharedOpts,
                PANEL_PADDING,
                SpringLayout.SOUTH, ngr);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, sharedOpts,
                0,
                SpringLayout.HORIZONTAL_CENTER, theRest);
        
        // Enforce a minimum panel height by putting a constraint on the
        // south side of it.
        layout.putConstraint(SpringLayout.SOUTH, theRest,
                Spring.constant(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING * 1000),
                SpringLayout.SOUTH, sharedOpts);
        
        
        // Make sure the components do not stretch vertically given the SOUTH
        // constraint by setting their maximum and preferred heights to the
        // same as their minimum heights.
        java.awt.Component[] comps = { ngr, sharedOpts };
        String[] attributes = { SpringLayout.HEIGHT, SpringLayout.WIDTH };
        for (java.awt.Component component : comps) {
            for (String attribute: attributes) {
                int minValue = layout.getConstraint(attribute, component).getMinimumValue();
                Spring newSpring = Spring.constant(minValue, minValue, minValue);
                layout.getConstraints(component).setConstraint(attribute, newSpring);
            }
        }
        
        // Ensure a minimum panel width too, by ensuring the minimum width is
        // at least as wide as any contained components.
        int maxwidth = Math.max(
                layout.getConstraint(SpringLayout.WIDTH, ngr).getMinimumValue(),
                layout.getConstraint(SpringLayout.WIDTH, sharedOpts).getMinimumValue()
                ) 
                + (PANEL_PADDING * 2);
        layout.getConstraints(theRest).setConstraint(
                SpringLayout.WIDTH,
                Spring.constant(maxwidth, maxwidth, maxwidth)
                );
    }


    // The button has been pressed.
    // Either halt the playing of the MIDI music, or generate a new sequence
    // and play it.
    @Override
    public void actionPerformed(java.awt.event.ActionEvent event) {
        if (this.currentlyPlayingMIDI) {
            // Stop execution of the music
            msc.haltExecution();
            
            // Restore the UI to a state where it accepts user input.
            currentlyPlayingMIDI = false;
            generateCommand.setText(START_STRING);
            
            return;
        }
		
        // Read all of the general options.
        long randomSeed;
        try {
            randomSeed = sharedOpts.getRandomSeed();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Random seed must be a properly formatted long integer");
            return;
        }
        
        NoteGenerator ng = ngr.getNoteGenerator(
                sharedOpts.getNoteRangeRestrictor(),
                randomSeed
                );
        
        try {
            // Create and play a new MIDI sequence based on the NoteGenerator
            // created from the user's specifications
            msc = new MIDISequenceCreator(ng);
            msc.playSequence();
            
            // Modify the user interface so the user can no longer modify anything
            // and only has the option to stop the music.
            currentlyPlayingMIDI = true;
            generateCommand.setText(STOP_STRING);
            
            // TODO: Launch a new window.
            // TODO: Give it the same parameters as the current window!?
            MainGuiFrame.main(new String[]{});
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "MIDI Error:\n" + e.toString());
        }
    }
	
    private static String START_STRING = "Generate Music";
    private static String STOP_STRING = "Stop Playing";

    private boolean currentlyPlayingMIDI = false;
    private MIDISequenceCreator msc;

    private JButton generateCommand = new JButton(START_STRING);
    private SharedPanel sharedOpts = new SharedPanel();
    private NoteGeneratorReturner ngr = new RadioGeneratorPanel();
}
