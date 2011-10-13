/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

import java.util.EnumMap;
import java.util.List;

/**
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
