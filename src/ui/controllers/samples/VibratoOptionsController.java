/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.samples;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.undo.UndoManager;
import lang.LanguageHandler;
import module.ISampleSynth;
import ui.controllers.GenericController;
import ui.controllers.undo.UndoableComboBoxChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.models.EditSampleViewModel;
import ui.view.models.LoadViewModel;
import ui.view.samples.VibratoOptions;

/**
 *
 * @author eddy6
 */
public class VibratoOptionsController extends GenericController {

    // instance variables
    private VibratoOptions vibratoOptions;
    private ISampleSynth selectedSample;
    private LanguageHandler languageHandler;
    private int oldVibratoWaveform;
    private int oldVibratoSpeed;
    private int oldVibratoDepth;
    private int oldVibratoDelay;

    public VibratoOptionsController(VibratoOptions vibratoOptions, LanguageHandler languageHandler) {
        this.vibratoOptions = vibratoOptions;
        this.languageHandler = languageHandler;
        // set vibrato options listeners
        vibratoOptions.addVibSpeedSpinnerChangeListener(e -> vibratoSpeedOnChange());
        vibratoOptions.addVibDepthSpinnerChangeListener(e -> vibratoDepthOnChange());
        vibratoOptions.addVibDelaySpinnerChangeListener(e -> vibratoDelayOnChange());
        vibratoOptions.addVibWaveformComboBoxActionListenr(e -> vibratoWaveformOnChange());
    }
    
    // vibrato options
    public void vibratoSpeedOnChange() {

        JSpinner vibSpeedSpinner = vibratoOptions
                .getVibSpeedSpinner();

        int value = (int) vibSpeedSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(vibSpeedSpinner,
                            oldVibratoSpeed);

            getCurrentUndoManager().addEdit(spinnerChange);

            oldVibratoSpeed = value;
        }

        if (isAlteringDataSources()) {

            // update sample pan value
            selectedSample.setVibratoSpeed(value);
        }
    }

    public void vibratoDepthOnChange() {

        JSpinner vibDepthSpinner = vibratoOptions
                .getVibSpeedSpinner();

        int value = (int) vibDepthSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(vibDepthSpinner,
                            oldVibratoDepth);

            getCurrentUndoManager().addEdit(spinnerChange);

            oldVibratoDepth = value;
        }

        if (isAlteringDataSources()) {

            // update sample pan value
            selectedSample.setFullVibratoDepth(value);
        }
    }

    public void vibratoDelayOnChange() {

        JSpinner vibDelaySpinner = vibratoOptions
                .getVibSpeedSpinner();

        int value = (int) vibDelaySpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(vibDelaySpinner,
                            oldVibratoDelay);

            getCurrentUndoManager().addEdit(spinnerChange);

            oldVibratoDelay = value;
        }

        if (isAlteringDataSources()) {

            // update sample pan value
            selectedSample.setVibratoDelay(value);
        }
    }

    public void vibratoWaveformOnChange() {

        JComboBox vibWaveformComboBox = vibratoOptions
                .getVibWaveformComboBox();

        int index = vibWaveformComboBox.getSelectedIndex();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(vibWaveformComboBox,
                            oldVibratoWaveform);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);

            oldVibratoWaveform = index;
        }

        if (isAlteringDataSources()) {

            selectedSample.setVibratoWaveform((byte)index);
        }
    }
    
    public void sampleOnChange(ISampleSynth selectedSample, int modType,
            UndoManager currentUndoManager) {

        this.selectedSample = selectedSample;
        this.setCurrentUndoManager(currentUndoManager);

        // set recording undos and alterations to false for a sample change
        setRecordingUndos(false);
        setAlteringDataSources(false);
        
        // vibrato speed
        // set old value
        oldVibratoSpeed = (int) selectedSample.getFullVibratoSpeed();

        vibratoOptions.getVibSpeedSpinner()
                .setValue(oldVibratoSpeed);

        // vibrato depth
        // set old value
        oldVibratoDepth = (int) selectedSample.getFullVibratoDepth();

        vibratoOptions.getVibDepthSpinner()
                .setValue(oldVibratoDepth);

        // vibrato delay
        // set old value
        oldVibratoDelay = (int) selectedSample.getVibratoDelay();

        vibratoOptions.getVibDelaySpinner()
                .setValue(oldVibratoDelay);

        // vibrato waveform
        // set old value
        oldVibratoWaveform = selectedSample.getVibratoWaveform();

        vibratoOptions.getVibWaveformComboBox()
                .setSelectedIndex(oldVibratoWaveform);

        // set record undo back to true
        setRecordingUndos(true);
        setAlteringDataSources(true);
    }
}
