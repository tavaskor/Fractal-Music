/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

import javax.swing.JRadioButton;
import java.awt.BorderLayout;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

/**
 * Common elements for all panels that control user parameters related to the
 * creation of {@link ca.vaskor.terry.fractalmusic.lib.NoteGenerator}s.
 * 
 * @author Terry Vaskor
 */
public abstract class MusicPanel extends GeneratorPanel<EnumMap<MusicType, List<Object>>> {
    
    /**
     * 
     * @param type           The type of music this panel generates.
     * @param radioGroup     The radio button group to which this panel belongs.
     * @param buttonSelected True if this panel is to be selected.
     */
    public MusicPanel(MusicType type, javax.swing.ButtonGroup radioGroup, boolean buttonSelected) {
        
        identifiedType = type;
        String musicType = type.toString();
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
    
    /**
     * 
     * @return True if this panel is currently selected; false otherwise.
     */
    public final boolean buttonIsSelected() {
        return button.isSelected();
    }
    
    /**
     * @return The type of music this panel generates.
     */
    @Override
    public MusicType getType() {
        return identifiedType;
    }
    
    /**
     * A helper to assist subclasses in implementing {@link #getData()}.
     * 
     * @param objects Everything to add to the hash
     * @return A hash mapping the type to a list of the provided objects.
     */
    protected EnumMap<MusicType, List<Object>> putInHash(Object ... objects) {
        EnumMap<MusicType, List<Object>> retVal = 
                new EnumMap<MusicType, List<Object>>(MusicType.class);
        
        retVal.put(getType(), Arrays.asList(objects));
        
        return retVal;
    }
    
    JRadioButton button;
    MusicType identifiedType;
}
