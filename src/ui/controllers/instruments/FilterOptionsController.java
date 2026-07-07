/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.instruments;

import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import lang.LanguageHandler;
import module.IInstrument;
import ui.controllers.GenericController;
import ui.controllers.undo.UndoableCheckBoxChange;
import ui.controllers.undo.UndoableSliderChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.instruments.FilterOptions;

/**
 *
 * @author eddy6
 */
public class FilterOptionsController extends GenericController {
    
    // instance variables
    private FilterOptions filterOptions;
    private LanguageHandler languageHandler;
    private IInstrument selectedInstrument;
    private int filterCutoffSpinnerOldValue;
    private int filterCutoffSliderOldValue;
    private int filterRezSpinnerOldValue;
    private int filterRezSliderOldValue;

    // constructor
    public FilterOptionsController(FilterOptions filterOptions, LanguageHandler languageHandler) {
        this.filterOptions = filterOptions;
        this.languageHandler = languageHandler;
    }
    
    // filter events
    public void cutoffOnChange() {

        JCheckBox cutoff = filterOptions.getFilterCheckBox();

        boolean isSelected = cutoff.isSelected();

        filterOptions.getFilterCutoffSpinner().setEnabled(isSelected);
        filterOptions.getFilterCutoffSlider().setEnabled(isSelected);

        if (isRecordingUndos()) {
            // undo event
            UndoableCheckBoxChange checkBoxChange
                    = new UndoableCheckBoxChange(cutoff);

            getCurrentUndoManager().addEdit(checkBoxChange);
        }

        if (isAlteringDataSources()) {

            // update instrument panning value
            selectedInstrument.setFiltered(isSelected);
        }
    }

    public void cutoffSpinnerOnChange() {

        JSpinner cutoffSpinner = filterOptions.getFilterCutoffSpinner();

        int value = (int) cutoffSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        filterOptions.getFilterCutoffSlider().setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(cutoffSpinner,
                            filterCutoffSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }

        filterCutoffSpinnerOldValue = value;

        if (isAlteringDataSources()) {

            // update instrument global volume value
            selectedInstrument.setInitialFilterCutoff((short) value);
        }
    }

    public void cutoffSliderOnChange() {

        JSlider cutoffSlider = filterOptions.getFilterCutoffSlider();

        int value = cutoffSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        filterOptions.getFilterCutoffSpinner().setValue(value);
        setRecordingUndos(recordUndoState);

        if (!cutoffSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(cutoffSlider,
                                filterCutoffSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }

            filterCutoffSliderOldValue = value;

            if (isAlteringDataSources()) {

                // update instrument initial filter cutoff value
                selectedInstrument.setInitialFilterCutoff((short) value);
            }
        }
    }

    public void resonanceOnChange() {

        JCheckBox cutoff = filterOptions.getResonanceCheckBox();

        boolean isSelected = cutoff.isSelected();

        filterOptions.getFilterResonanceSpinner().setEnabled(isSelected);
        filterOptions.getFilterResonanceSlider().setEnabled(isSelected);

        if (isRecordingUndos()) {
            // undo event
            UndoableCheckBoxChange checkBoxChange
                    = new UndoableCheckBoxChange(cutoff);

            getCurrentUndoManager().addEdit(checkBoxChange);
        }

        if (isAlteringDataSources()) {

            // update instrument panning value
            selectedInstrument.setUsingResonance(isSelected);
        }
    }

    public void resonanceSpinnerOnChange() {

        JSpinner rezSpinner = filterOptions.getFilterResonanceSpinner();

        int value = (int) rezSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        filterOptions.getFilterResonanceSlider().setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(rezSpinner,
                            filterRezSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }

        filterRezSpinnerOldValue = value;

        if (isAlteringDataSources()) {

            // update instrument initial filter resonance value
            selectedInstrument.setInitialFilterResonance((short) value);
        }
    }

    public void resonanceSliderOnChange() {

        JSlider rezSlider = filterOptions.getFilterResonanceSlider();

        int value = rezSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        filterOptions.getFilterResonanceSpinner().setValue(value);
        setRecordingUndos(recordUndoState);

        if (!rezSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(rezSlider,
                                filterRezSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }

            filterRezSliderOldValue = value;

            if (isAlteringDataSources()) {

                // update instrument initial filter resonance value
                selectedInstrument.setInitialFilterResonance((short) value);
            }
        }
    }
}
