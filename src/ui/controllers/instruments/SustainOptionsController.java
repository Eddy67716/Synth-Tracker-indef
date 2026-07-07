/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.instruments;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import lang.LanguageHandler;
import module.IInstrument;
import ui.controllers.GenericController;
import ui.controllers.undo.UndoableComboBoxChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.instruments.SustainOptions;

/**
 *
 * @author eddy6
 */
public class SustainOptionsController extends GenericController {
    
    // instance variables
    private SustainOptions sustainOptions;
    private LanguageHandler languageHandler;
    private IInstrument selectedInstrument;
    private int fadeoutOldValue;
    private int nnaComboBoxOldValue;
    private int dntComboBoxOldValue;
    private int dnaComboBoxOldValue;
    
    // constructor
    public SustainOptionsController(SustainOptions sustainOptions, 
            LanguageHandler languageHandler) {
        this.sustainOptions = sustainOptions;
        this.languageHandler = languageHandler;
        // set the sustain option actions
        sustainOptions.addFadeOutSpinnerChangeListener(e -> fadeOutOnChange());
        sustainOptions.addNewNoteActionActionListener(e -> newNoteActionPerform());
        sustainOptions.addDupNoteTypeActionListener(e -> dupNoteTypeActionPerform());
        sustainOptions.addDupNoteActionActionListener(e -> dupNoteActionPerform());
    }
    
    // sustain option events
    public void fadeOutOnChange() {

        JSpinner fadeOutSpinner = sustainOptions.getFadeOutSpinner();

        // commit edit
        try {

            // makes sure it's a number divided by 32
            if ((int) fadeOutSpinner.getValue() % 32 != 0) {
                int modulus = (int) fadeOutSpinner.getValue() % 32;
                int currentValue = (int) fadeOutSpinner.getValue();
                // round to the closes number
                if (modulus > 16) {
                    fadeOutSpinner.setValue(currentValue - modulus + 32);
                } else {
                    fadeOutSpinner.setValue(currentValue - modulus);
                }
                // return because rest of code has been run in a recursion
                return;
            }

            fadeOutSpinner.commitEdit();

            int storeValue = (int) fadeOutSpinner.getValue() / 32;

            if (isRecordingUndos()) {
                // undo event
                UndoableSpinnerChange spinnerChange
                        = new UndoableSpinnerChange(fadeOutSpinner,
                                fadeoutOldValue);

                // append event to manager
                getCurrentUndoManager().addEdit(spinnerChange);
            }

            if (isAlteringDataSources()) {

                // update instrument fade value
                selectedInstrument.setFadeOut(storeValue);
            }

            fadeoutOldValue = (int) fadeOutSpinner.getValue();

        } catch (java.text.ParseException e) {

            // play an error sound
            playErrorSound();

            fadeOutSpinner.setValue(fadeoutOldValue);
        }
    }

    public void newNoteActionPerform() {

        JComboBox newNoteActionComboBox = sustainOptions
                .getNewNoteActionComboBox();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(newNoteActionComboBox,
                            nnaComboBoxOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);
        }

        if (isAlteringDataSources()) {

            // alter instrument
            selectedInstrument.setNewNoteAction((byte) newNoteActionComboBox
                    .getSelectedIndex());
        }

        nnaComboBoxOldValue = newNoteActionComboBox.getSelectedIndex();
    }

    public void dupNoteTypeActionPerform() {
        JComboBox dupNoteTypeComboBox = sustainOptions.getDupNoteTypeComboBox();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(dupNoteTypeComboBox,
                            dntComboBoxOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);
        }

        if (isAlteringDataSources()) {

            // alter instrument
            selectedInstrument.setDuplicateCheckType(
                    (byte) dupNoteTypeComboBox.getSelectedIndex());
        }

        dntComboBoxOldValue = dupNoteTypeComboBox.getSelectedIndex();
    }

    public void dupNoteActionPerform() {
        JComboBox dupNoteActionComboBox = sustainOptions
                .getDupNoteTypeComboBox();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(dupNoteActionComboBox,
                            dnaComboBoxOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);
        }

        if (isAlteringDataSources()) {

            // alter instrument
            selectedInstrument.setDuplicateCheckAction(
                    (byte) dupNoteActionComboBox.getSelectedIndex());
        }

        dnaComboBoxOldValue = dupNoteActionComboBox.getSelectedIndex();
    }
    
}
