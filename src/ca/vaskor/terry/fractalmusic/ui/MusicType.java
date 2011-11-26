/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

/**
 * An enum for the types of supported music.
 * This is used primarily to facilitate creation of a new music panel with
 * a set of settings copied from another music panel.
 *
 * @author Terry Vaskor
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
