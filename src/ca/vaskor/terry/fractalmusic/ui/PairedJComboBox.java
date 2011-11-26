/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * This class pairs two {@link javax.swing.JComboBox}es together that store
 * the same ordered list of {@link java.lang.Object}s with one combo box being
 * the "lower" selected value and the other being the "higher" selected value,
 * and modify themselves to ensure that it is impossible to select a value in
 * "higher" lower than the value in "lower" and vice versa.
 * 
 * As an example, consider the case where each list stores the numbers 1, 2, ... 10.
 * If 3 is selected in the "lower" box, then the "higher" box will only display
 * the options 3 through 10.  Similarly, if 4 is selected in the "higher" box,
 * then the "lower" box will only display the options 1 through 4.
 *
 * @author Terry Vaskor
 */
public class PairedJComboBox extends ArtificiallyEnlargedJComboBox {    
    
    /**
     * Create a PairedJComboBox.
     * 
     * @param items The contents to list
     */
    public PairedJComboBox(Object[] items) {
        super(items);
        fullList = items;
        highestRenderedIndex = items.length - 1;
        
        addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                // Ignore that the state change if this code 
                // is what is changing the state.
                // Otherwise, update the state of the other field.
                if (!currentlyModifyingState) {
                    partner.updateState();
                }
            }
        });
    }
    
    /**
     * Pair this with another {@link PairedJComboBox}.
     * 
     * @param higherPartner The "higher" combo box (the caller is the "lower" one)
     */
    public void pairWithHigher(PairedJComboBox higherPartner) {
        isHigher = false;
        higherPartner.isHigher = true;
        
        partner = higherPartner;
        higherPartner.partner = this;
    }
    
    static private final int NO_SELECTION = -1;  // javadoc for JComboBox
    
    private void updateState() {
        // Make sure we don't endlessly recurse while reconstructing the menu
        currentlyModifyingState = true;

        int currentGlobalSelectedIndex = 
                getSelectedIndex() + lowestRenderedIndex;
        int partnerCurrentGlobalSelectedIndex = 
                partner.getSelectedIndex() + partner.lowestRenderedIndex;

        // Abort if nothing is yet selected
        if (
                (currentGlobalSelectedIndex == NO_SELECTION) ||
                (partnerCurrentGlobalSelectedIndex == NO_SELECTION)
                ) {
            return;
        }

        // Determine a new low or high index
        if (isHigher) {
            lowestRenderedIndex = partnerCurrentGlobalSelectedIndex;
        } else {
            highestRenderedIndex = partnerCurrentGlobalSelectedIndex;
        }

        // Redo the list given that information
        removeAllItems();
        for (int i = lowestRenderedIndex; i <= highestRenderedIndex; i++) {
            addItem(fullList[i]);
        }

        // Use currentGlobalSelectedIndex to calculate what selected index should be.
        // If this is still being initialized, this will cause an exception;
        // disregard it as the correct thing will be done each future call.
        try {
            this.setSelectedIndex(currentGlobalSelectedIndex - lowestRenderedIndex);
        } catch (java.lang.IllegalArgumentException exn) {
        }
            
        // Allow updates again
        currentlyModifyingState = false;
    }
    
    
    PairedJComboBox partner = null;
    int lowestRenderedIndex = 0;
    boolean currentlyModifyingState = false;
    int highestRenderedIndex;
    boolean isHigher;
    
    Object[] fullList;
}
