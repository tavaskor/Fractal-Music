/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

/**
 *
 * @author tavaskor
 */
public class ForkWindowButton extends javax.swing.JButton {
    
    public ForkWindowButton() {
        super("Create New Window");
        addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                // Launch a new window when pressed.
                // TODO: Give it the same parameters as the current window!?
                MainGuiFrame.main(new String[]{});
            }
        });    
    }
}
