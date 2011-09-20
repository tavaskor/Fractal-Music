/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

import javax.swing.JRadioButton;
import java.awt.BorderLayout;

/**
 *
 * @author tavaskor
 */
public abstract class MusicPanel extends NoteGeneratorReturner {
    public MusicPanel(String musicType, javax.swing.ButtonGroup radioGroup, boolean buttonSelected) {
        button = new JRadioButton(musicType);
        button.setActionCommand(musicType);
        if (radioGroup != null) {
            radioGroup.add(button);
        }
        if (buttonSelected) {
            button.setSelected(true);
        }
        
        this.setLayout(new BorderLayout());
        this.add(button, BorderLayout.WEST);
    }
    
    public final boolean buttonIsSelected() {
        return button.isSelected();
    }
    
    JRadioButton button;
}
