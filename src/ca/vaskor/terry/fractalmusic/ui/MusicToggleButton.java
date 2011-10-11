/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

/**
 *
 * @author tavaskor
 */
public class MusicToggleButton extends javax.swing.JButton {
    private static final String START_STRING = "Generate Music";
    private static final String STOP_STRING = "Stop Playing";
    
    public MusicToggleButton() {
        super(START_STRING);
    }
    
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
