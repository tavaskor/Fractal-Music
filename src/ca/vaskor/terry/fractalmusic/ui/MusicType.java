/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

/**
 *
 * @author tavaskor
 */
public enum MusicType {
    WHITE("White"), BROWN("Brown"), PINK("Pink");
    
    MusicType(String label) {
        type = label;
    }
    
    @Override
    public String toString() {
        return type + " music";
    }
    
    private String type;
}
