/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.windows.find;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import lang.LanguageHandler;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author eddy6
 */
public class FindInstrument extends JPanel {

    // instance variables
    private int modType;
    private LanguageHandler languageHandler;
    private Border findInstrumentBorder;
    private GridBagLayout findInstrumentLayout;
    private GridBagConstraints fic;
    private JLabel instrumentSelectionTypeLabel;
    private JRadioButton singleInstrumentRadioButton;
    private JRadioButton instrumentRangeRadioButton;
    private ButtonGroup findInstrumentGroup;
    private JLabel firstInstrumentLabel;
    private JSpinner firstInstrumentSpinner;
    private JLabel secondInstrumentLabel;
    private JSpinner secondInstrumentSpinner;
    private JLabel trailingEmptyLabel;
    private JCheckBox trailingEmptyCheckBox;

    public FindInstrument(int modType, LanguageHandler languageHandler) {
        this.modType = modType;
        this.languageHandler = languageHandler;
        init();
    }

    // getters
    public JRadioButton getSingleInstrumentRadioButton() {
        return singleInstrumentRadioButton;
    }

    public JRadioButton getInstrumentRangeRadioButton() {
        return instrumentRangeRadioButton;
    }

    public JSpinner getFirstInstrumentSpinner() {
        return firstInstrumentSpinner;
    }

    public JSpinner getSecondInstrumentSpinner() {
        return secondInstrumentSpinner;
    }

    public JCheckBox getTrailingEmptyCheckBox() {
        return trailingEmptyCheckBox;
    }

    public void init() {

        // set the layout
        findInstrumentLayout = new GridBagLayout();
        setLayout(findInstrumentLayout);

        // setup the constrants
        fic = new GridBagConstraints();
        fic.anchor = GridBagConstraints.SOUTHWEST;
        //fnc.fill = GridBagConstraints.BOTH;
        fic.insets = DEF_INSETS;

        // set the border
        findInstrumentBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        findInstrumentBorder
                = BorderFactory.createTitledBorder(findInstrumentBorder,
                        languageHandler
                                .getLanguageText("instrument"),
                        0, 0, BOLD_FONT);

        // set options border
        setBorder(findInstrumentBorder);

        // instrument selection type label
        instrumentSelectionTypeLabel = new JLabel(languageHandler
                .getLanguageText("pattern.find.instrument.selection"));

        fic.gridx = 0;
        fic.gridwidth = GridBagConstraints.REMAINDER;
        fic.weightx = 1;
        fic.gridy = 0;
        fic.gridheight = 1;
        fic.weighty = 0;

        add(instrumentSelectionTypeLabel, fic);

        // single note radio button
        singleInstrumentRadioButton = new JRadioButton(languageHandler
                .getLanguageText("pattern.find.selection.single"));

        fic.gridy++;
        fic.gridwidth = 1;

        add(singleInstrumentRadioButton, fic);

        // note range radio button
        instrumentRangeRadioButton = new JRadioButton(languageHandler
                .getLanguageText("pattern.find.selection.range"));

        fic.gridx++;
        fic.gridwidth = GridBagConstraints.REMAINDER;

        add(instrumentRangeRadioButton, fic);

        // button structure
        findInstrumentGroup = new ButtonGroup();
        findInstrumentGroup.add(singleInstrumentRadioButton);
        findInstrumentGroup.add(instrumentRangeRadioButton);

        // first note label
        firstInstrumentLabel = new JLabel(languageHandler
                .getLanguageText("pattern.find.instrument.first"));

        fic.gridx = 0;
        fic.gridwidth = 1;
        fic.weightx = 1;
        fic.gridy++;

        add(firstInstrumentLabel, fic);

        // second note label
        secondInstrumentLabel = new JLabel(languageHandler
                .getLanguageText("pattern.find.instrument.second"));

        fic.gridx = 1;
        fic.gridwidth = GridBagConstraints.REMAINDER;
        fic.weightx = 1;

        add(secondInstrumentLabel, fic);

        // first note combo box
        firstInstrumentSpinner = new JSpinner();

        fic.gridwidth = 1;
        fic.gridx = 0;
        fic.gridy++;

        add(firstInstrumentSpinner, fic);

        // second note combo box
        secondInstrumentSpinner = new JSpinner();

        fic.gridwidth = GridBagConstraints.REMAINDER;
        fic.gridx++;

        add(secondInstrumentSpinner, fic);

        // trailing note check box label
        trailingEmptyLabel = new JLabel(languageHandler
                .getLanguageText("pattern.find.instrument.trailing_empties"));

        fic.gridx = 0;
        fic.gridwidth = GridBagConstraints.REMAINDER;
        fic.weightx = 1;
        fic.gridy++;

        add(trailingEmptyLabel, fic);

        // trailing note check box
        trailingEmptyCheckBox = new JCheckBox(languageHandler
                .getLanguageText("pattern.find.instrument.trailing_empties.check"));

        fic.gridy++;
        fic.gridwidth = GridBagConstraints.REMAINDER;
        fic.weightx = 1;

        add(trailingEmptyCheckBox, fic);

        // add trailing empty JPanel
        fic.gridx = 0;
        fic.gridy++;
        fic.weighty = 1.0;
        fic.gridheight = GridBagConstraints.REMAINDER;

        add(new JPanel(), fic);
    }
}
