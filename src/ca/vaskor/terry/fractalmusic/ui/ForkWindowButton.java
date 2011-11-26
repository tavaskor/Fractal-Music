/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vaskor.terry.fractalmusic.ui;

/**
 * A specialized button whose action is to fork a new {@link MainGuiFrame},
 * retaining all of the selections of the source frame.
 * 
 * @author Terry Vaskor
 */
public class ForkWindowButton extends javax.swing.JButton {
    private static final int X_DISPLACEMENT = 15;
    
    /**
     * 
     * @param relativeTo       A GUI object relative to which the new frame will be created.
     * @param panelToMonitor   Panel from which to retrieve data about general selected options.
     * @param musicTypeMonitor Another panel from which to retrieve data about selections for particular types of music generation.
     */
    public ForkWindowButton(
            final javax.swing.JComponent relativeTo,
            final DataRetriever<SharedPanelData> panelToMonitor,
            final DataRetriever<GeneratorPanelData> musicTypeMonitor
            ) {
        super("Create New Window");
        addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) {
                // Launch a new window when pressed.
                // Make it slightly down and to the right from the bounds of
                // the parent window.
                // TODO: Give it the same parameters as the current window!?
                final MainGuiFrame newFrame = new MainGuiFrame(
                        panelToMonitor.getData(),
                        musicTypeMonitor.getData()
                        );
                newFrame.setLocationRelativeTo(relativeTo);
                
                java.awt.Point intermediateLocation = newFrame.getLocation();
                intermediateLocation.translate(X_DISPLACEMENT, 0);
                newFrame.setLocation(intermediateLocation);
                
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        newFrame.setVisible(true);
                    }
                });
            }
        });
    }
}
