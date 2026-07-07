/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.instruments;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import lang.LanguageHandler;
import module.IInstrument;
import ui.controllers.GenericController;
import ui.controllers.undo.UndoableCheckBoxChange;
import ui.controllers.undo.UndoableComboBoxChange;
import ui.controllers.undo.UndoableSliderChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.instruments.InstrumentSoundOptions;

/**
 *
 * @author eddy6
 */
public class InstrumentSoundOptionsController extends GenericController {
    
    // instance variables
    private InstrumentSoundOptions instrumentSoundOptions;
    private LanguageHandler languageHandler;
    private IInstrument selectedInstrument;
    private int globalVolSpinnerOldValue;
    private int globalVolSliderOldValue;
    private int panSpinnerOldValue;
    private int panSliderOldValue;
    private int pitchPanSeperationOldValue;
    private int pitchPanCentreOldValue;

    // constructor
    public InstrumentSoundOptionsController(InstrumentSoundOptions instrumentSoundOptions, 
            LanguageHandler languageHandler) {
        this.instrumentSoundOptions = instrumentSoundOptions;
        this.languageHandler = languageHandler;
        // set the sound actions
        instrumentSoundOptions.addGlobVolumeValChangeListener(e -> globalVolumeSpinnerOnChange());
        instrumentSoundOptions.addGlobVolumeSliderChangeListener(e -> globalVolumeSliderOnChange());
        instrumentSoundOptions.addPaningActionListener(e -> panOnChange());
        instrumentSoundOptions.addDefPanValChangeListener(e -> defaultPanningSpinnerOnChange());
        instrumentSoundOptions.addDefPanSliderChangeListener(e -> defaultPanningSliderOnChange());
        instrumentSoundOptions.addPitchPanSpinnerChangeListener(e -> pitchPanOnChange());
        instrumentSoundOptions.addPitchCentreComboBoxItemListener(e -> pitchPanCentreOnChange());
    }
    
    // sound options events
    public void globalVolumeSpinnerOnChange() {

        JSpinner globalVolumeSpinner = instrumentSoundOptions
                .getGlobalVolumeValue();

        int value = (int) globalVolumeSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentSoundOptions.getGlobalVolumeSlider()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(globalVolumeSpinner,
                            globalVolSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }

        globalVolSpinnerOldValue = value;

        if (isAlteringDataSources()) {

            // update instrument global volume value
            selectedInstrument.setGlobalVolumeValue((byte) value);
        }
    }

    public void globalVolumeSliderOnChange() {

        JSlider globalVolumeSlider = instrumentSoundOptions
                .getGlobalVolumeSlider();

        int value = globalVolumeSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentSoundOptions.getGlobalVolumeValue()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (!globalVolumeSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(globalVolumeSlider,
                                globalVolSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }

            globalVolSliderOldValue = value;

            if (isAlteringDataSources()) {

                // update instrument global volume value
                selectedInstrument.setGlobalVolumeValue((byte) value);
            }
        }
    }

    public void panOnChange() {

        JCheckBox panning = instrumentSoundOptions
                .getPanning();

        boolean isSelected = panning.isSelected();

        instrumentSoundOptions.getDefaultPanningSlider()
                .setEnabled(isSelected);
        instrumentSoundOptions.getDefaultPanningValue()
                .setEnabled(isSelected);

        if (isRecordingUndos()) {
            // undo event
            UndoableCheckBoxChange checkBoxChange
                    = new UndoableCheckBoxChange(panning);

            getCurrentUndoManager().addEdit(checkBoxChange);
        }

        if (isAlteringDataSources()) {

            // update instrument panning value
            selectedInstrument.setPanning(isSelected);
        }
    }

    public void defaultPanningSpinnerOnChange() {

        JSpinner defaultPanningSpinner = instrumentSoundOptions
                .getDefaultPanningValue();

        int value = (int) defaultPanningSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentSoundOptions.getDefaultPanningSlider()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(defaultPanningSpinner,
                            panSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }

        panSpinnerOldValue = value;

        if (isAlteringDataSources()) {

            // update instrument global volume value
            selectedInstrument.setNormalisedPanValue((byte) value);
        }
    }

    public void defaultPanningSliderOnChange() {
        JSlider defaultPanningSlider = instrumentSoundOptions
                .getDefaultPanningSlider();

        int value = defaultPanningSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentSoundOptions.getDefaultPanningValue().setValue(value);
        setRecordingUndos(recordUndoState);

        if (!defaultPanningSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(defaultPanningSlider,
                                panSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }

            panSliderOldValue = value;

            if (isAlteringDataSources()) {

                // update instrument global volume value
                selectedInstrument.setNormalisedPanValue((byte) value);
            }
        }
    }

    public void pitchPanOnChange() {

        JSpinner pitchPanSpinner = instrumentSoundOptions
                .getPitchPanSeparationSpinner();

        int value = (int) pitchPanSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(pitchPanSpinner,
                            pitchPanSeperationOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            pitchPanSeperationOldValue = value;
        }

        if (isAlteringDataSources()) {

            // update instrument global volume value
            selectedInstrument.setPitchPanSeparation((byte) value);
        }

        selectedInstrument.setPitchPanSeparation((byte) value);
    }

    public void pitchPanCentreOnChange() {

        JComboBox pitchPanCentreComboBox = instrumentSoundOptions
                .getPitchPanCentreNoteComboBox();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(pitchPanCentreComboBox,
                            pitchPanCentreOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);

            // update old value to current one
            pitchPanCentreOldValue = pitchPanCentreComboBox.getSelectedIndex();
        }

        if (isAlteringDataSources()) {

            // alter instrument
            selectedInstrument.setPitchPanCentre((byte) pitchPanCentreComboBox
                    .getSelectedIndex());
        }
    }
}
