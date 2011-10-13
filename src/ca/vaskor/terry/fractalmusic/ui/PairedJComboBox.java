/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

/**
 *
 * @author tavaskor
 */
public class PairedJComboBox extends javax.swing.JComboBox {    
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
