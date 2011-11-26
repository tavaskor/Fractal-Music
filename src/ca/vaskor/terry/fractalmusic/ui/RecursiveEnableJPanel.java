/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

/**
 * An extension of {@link javax.swing.JPanel} that will enable or disable
 * its subelements recursively.
 * 
 * @author Terry Vaskor
 */
public class RecursiveEnableJPanel extends javax.swing.JPanel {
    
    /**
     * Instead of just enabling or disabling this element, also iterate through
     * all child elements and perform the enable or disable action on them
     * as well.
     * 
     * @param setting True if the element and its children should be enabled,
     *                false if they should be disabled.
     */
    @Override
    public void setEnabled(boolean setting) {
        super.setEnabled(setting);
        
        for (java.awt.Component target : getComponents()) {
            target.setEnabled(setting);
        }
    }
}
