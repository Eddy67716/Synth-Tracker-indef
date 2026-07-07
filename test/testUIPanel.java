
// Java program to illustrate 
// working of SwingWorker
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import lang.LanguageHandler;
import ui.view.samples.AdsrEnvelopeTools;
import ui.view.windows.find.FindEffect;
import ui.view.windows.find.FindInstrument;
import ui.view.windows.find.FindNote;
import ui.view.windows.find.FindPanel;
import ui.view.windows.find.FindVolume;
import ui.view.windows.replace.ReplaceInstrument;
import ui.view.windows.replace.ReplacePanel;
import ui.view.windows.replace.ReplaceVolume;

/**
 *
 * @author Edward Jenkins
 */
public class testUIPanel extends JFrame {

    // instance variables
    private JPanel testPanel;

    // constructor
    public testUIPanel() throws IOException {
        testPanel = new FindPanel((byte)4, new LanguageHandler());
        init();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // run the frame
        SwingUtilities.invokeLater(() -> {
            try {
                new testUIPanel().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(testUIPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Synth Tracker");
        add(testPanel);
        pack();
        setLocationRelativeTo(null);
    }
}
