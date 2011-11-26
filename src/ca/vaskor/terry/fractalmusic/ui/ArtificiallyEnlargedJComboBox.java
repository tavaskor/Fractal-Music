/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

import java.awt.Dimension;

/**
 * JComboBoxes are being rendered too narrow.  Even the preferred size is too
 * small for values to be displayed properly.
 * 
 * This class exists solely to inflate the width so the values display as expected.
 * 
 * @author tavaskor
 */
public class ArtificiallyEnlargedJComboBox extends javax.swing.JComboBox {
    ArtificiallyEnlargedJComboBox(Object[] objs) {
        super(objs);
    }

    /**
     * 
     * @return A slightly inflated value from the default minimum size
     *         of this {@link javax.swing.JComboBox}.
     */
    @Override
    public Dimension getMinimumSize() {
        return inflate(super.getMinimumSize());
    }
    
    /**
     * 
     * @return A slightly inflated value from the default preferred size
     *         of this {@link javax.swing.JComboBox}.
     */
    @Override
    public Dimension getPreferredSize() {
        return inflate(super.getPreferredSize());
    }
    
    private Dimension inflate(Dimension original) {
        double tooSmallWidth = original.getWidth();
        return new java.awt.Dimension(
                (int)(tooSmallWidth * 1.1), 
                (int)original.getHeight()
                );
    }
}
