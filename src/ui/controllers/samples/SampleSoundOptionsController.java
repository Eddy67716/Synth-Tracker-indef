/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.samples;

import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.undo.UndoManager;
import lang.LanguageHandler;
import module.ISampleSynth;
import ui.controllers.GenericController;
import ui.controllers.undo.UndoableCheckBoxChange;
import ui.controllers.undo.UndoableSliderChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.models.EditSampleViewModel;
import ui.view.models.LoadViewModel;
import ui.view.samples.SampleSoundOptions;

/**
 *
 * @author eddy6
 */
public class SampleSoundOptionsController extends GenericController {

    // instance variables
    private SampleSoundOptions soundOptions;
    private ISampleSynth selectedSample;
    private LanguageHandler languageHandler;
    private int defaultVolSpinnerOldValue;
    private int defaultVolSliderOldValue;
    private int globalVolSpinnerOldValue;
    private int globalVolSliderOldValue;
    private int panSpinnerOldValue;
    private int panSliderOldValue;

    public SampleSoundOptionsController(SampleSoundOptions soundOptions,
            LanguageHandler languageHandler) {
        this.soundOptions = soundOptions;
        this.languageHandler = languageHandler;
        soundOptions.addDefVolumeValChangeListener(e -> defaultVolumeSpinnerOnChange());
        soundOptions.addDefVolumeSliderChangeListener(e -> defaultVolumeSliderOnChange());
        soundOptions.addGlobVolumeValChangeListener(e -> globalVolumeSpinnerOnChange());
        soundOptions.addGlobVolumeSliderChangeListener(e -> globalVolumeSliderOnChange());
        soundOptions.addPaningActionListener(e -> panOnChange());
        soundOptions.addDefPanValChangeListener(e -> defaultPanningSpinnerOnChange());
        soundOptions.addDefPanSliderChangeListener(e -> defaultPanningSliderOnChange());
    }

    // sound options
    public void defaultVolumeSpinnerOnChange() {

        JSpinner defaultVolumeSpinner = soundOptions
                .getDefaultVolumeValue();

        int value = (int) defaultVolumeSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        soundOptions.getDefaultVolumeSlider()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(defaultVolumeSpinner,
                            defaultVolSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }

        defaultVolSpinnerOldValue = value;

        if (isAlteringDataSources()) {

            // update sample default volume value
            selectedSample.setDefaultVolume((byte) value);
        }
    }

    public void defaultVolumeSliderOnChange() {

        JSlider defaultVolumeSlider = soundOptions
                .getDefaultVolumeSlider();

        int value = defaultVolumeSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        soundOptions.getDefaultVolumeValue()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (!defaultVolumeSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(defaultVolumeSlider,
                                defaultVolSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }

            defaultVolSliderOldValue = value;

            if (isAlteringDataSources()) {

                // update sample default volume value
                selectedSample.setDefaultVolume((byte) value);
            }
        }
    }

    public void globalVolumeSpinnerOnChange() {

        JSpinner globalVolumeSpinner = soundOptions
                .getGlobalVolumeValue();

        int value = (int) globalVolumeSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        soundOptions.getGlobalVolumeSlider()
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

            // update sample global volume value
            selectedSample.setGlobalVolume((byte) value);
        }
    }

    public void globalVolumeSliderOnChange() {

        JSlider globalVolumeSlider = soundOptions
                .getGlobalVolumeSlider();

        int value = globalVolumeSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        soundOptions.getGlobalVolumeValue()
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

                // update sample global volume value
                selectedSample.setGlobalVolume((byte) value);
            }
        }
    }

    public void panOnChange() {

        JCheckBox panning = soundOptions
                .getPanning();

        boolean isSelected = panning.isSelected();

        soundOptions.getDefaultPanningSlider()
                .setEnabled(isSelected);
        soundOptions.getDefaultPanningValue()
                .setEnabled(isSelected);

        if (isRecordingUndos()) {
            // undo event
            UndoableCheckBoxChange checkBoxChange
                    = new UndoableCheckBoxChange(panning);

            getCurrentUndoManager().addEdit(checkBoxChange);
        }

        if (isAlteringDataSources()) {

            // update sample panning
            selectedSample.setPanning(isSelected);
        }
    }

    public void defaultPanningSpinnerOnChange() {
        JSpinner defaultPanningSpinner = soundOptions
                .getDefaultPanningValue();

        int value = (int) defaultPanningSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        soundOptions.getDefaultPanningSlider()
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

            // update sample pan value
            selectedSample.setPanValue((byte) value);
        }
    }

    public void defaultPanningSliderOnChange() {
        JSlider defaultPanningSlider = soundOptions
                .getDefaultPanningSlider();

        int value = defaultPanningSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        soundOptions.getDefaultPanningValue()
                .setValue(value);
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

                // update sample pan value
                selectedSample.setPanValue((byte) value);
            }
        }
    }

    public void sampleOnChange(ISampleSynth selectedSample, int modType,
            UndoManager currentUndoManager) {

        this.selectedSample = selectedSample;
        this.setCurrentUndoManager(currentUndoManager);

        // set recording undos and alterations to false for a sample change
        setRecordingUndos(false);
        setAlteringDataSources(false);

        // default volume
        // set old value
        defaultVolSpinnerOldValue = selectedSample.getDefaultVolume();
        defaultVolSliderOldValue = selectedSample.getDefaultVolume();

        soundOptions
                .getDefaultVolumeSlider().setValue(defaultVolSpinnerOldValue);

        defaultVolumeSliderOnChange();

        // global volume
        // set old value
        globalVolSpinnerOldValue = selectedSample.getGlobalVolume();
        globalVolSliderOldValue = selectedSample.getGlobalVolume();

        soundOptions
                .getGlobalVolumeSlider().setValue(globalVolSpinnerOldValue);

        globalVolumeSliderOnChange();

        // panning
        soundOptions
                .getPanning().setSelected(selectedSample.isPanning());

        panOnChange();

        // default panning
        // set old value
        panSpinnerOldValue = selectedSample.getPanValue();
        panSliderOldValue = selectedSample.getPanValue();

        soundOptions
                .getDefaultPanningSlider().setValue(panSpinnerOldValue);

        defaultPanningSliderOnChange();

        // set record undo back to true
        setRecordingUndos(true);
        setAlteringDataSources(true);
    }

}
