/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

/**
 * A button that displays appropriate music start and stop text.
 * 
 * @author Terry Vaskor
 */
public class MusicToggleButton extends javax.swing.JButton {
    private static final String START_STRING = "Generate Music";
    private static final String STOP_STRING = "Stop Playing";
    
    /**
     * Create a new button, defaulting to the "request that music be played"
     * state.
     */
    public MusicToggleButton() {
        super(START_STRING);
    }
    
    /**
     * Change state between requesting music be started and requesting that
     * it be stopped.
     */
    public void toggle() {
        if (inStartState) {
            setText(STOP_STRING);
        } else {
            setText(START_STRING);
        }
        inStartState = !inStartState;
    }
    
    private boolean inStartState = true;
}
