/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

/**
 *
 * @author tavaskor
 */
public class RecursiveEnableJPanel extends javax.swing.JPanel {
    
    @Override
    public void setEnabled(boolean setting) {
        super.setEnabled(setting);
        
        for (java.awt.Component target : getComponents()) {
            target.setEnabled(setting);
        }
    }
}
