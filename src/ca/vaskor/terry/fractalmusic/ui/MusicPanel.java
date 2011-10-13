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
 *
 * @author tavaskor
 */
public abstract class MusicPanel extends GeneratorPanel<EnumMap<MusicType, List<Object>>> {
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
    
    public final boolean buttonIsSelected() {
        return button.isSelected();
    }
    
    @Override
    public MusicType getType() {
        return identifiedType;
    }
    
    @Override
    public abstract EnumMap<MusicType, List<Object>> getData();
    
    protected EnumMap<MusicType, List<Object>> putInHash(Object ... objects) {
        EnumMap<MusicType, List<Object>> retVal = 
                new EnumMap<MusicType, List<Object>>(MusicType.class);
        
        retVal.put(getType(), Arrays.asList(objects));
        
        return retVal;
    }
    
    JRadioButton button;
    MusicType identifiedType;
}
