/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

import java.util.EnumMap;
import java.util.List;

/**
 * A storage structure for data that was selected for each type of music to be generated.
 * 
 * All fields are package scope as they are meant to be used internally in
 * the ui package only.
 * 
 * @author tavaskor
 */
public class GeneratorPanelData {
    GeneratorPanelData(MusicType chosen) {
        selected = chosen;
    }
    
    final MusicType selected;
    final EnumMap<MusicType, List<Object>> optionMap = 
            new EnumMap<MusicType, List<Object>>(MusicType.class);
}
