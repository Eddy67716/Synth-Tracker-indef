/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.samples;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.undo.UndoManager;
import lang.LanguageHandler;
import module.ISampleSynth;
import ui.controllers.GenericController;
import ui.controllers.undo.UndoableComboBoxChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.samples.SamplingTools;

/**
 *
 * @author eddy6
 */
public class SamplingToolsController extends GenericController {

    // instance variables
    private SamplingTools samplingTools;
    private ISampleSynth selectedSample;
    private LanguageHandler languageHandler;
    private int oldC5Speed;
    private int oldTransposeIndex;

    public SamplingToolsController(SamplingTools samplingTools, LanguageHandler languageHandler) {
        this.samplingTools = samplingTools;
        this.languageHandler = languageHandler;
        // set sampling tools listeners
        samplingTools.addC5SampleRateSpinnerChangeEvent(
                e -> c5SampleRateSpinnerOnChange());
        samplingTools.addSampleTransposeComboBox(e -> sampleTransposeComboBoxOnChange());
    }

    // sampling tools
    public void c5SampleRateSpinnerOnChange() {

        JSpinner c5SampleRateSpinner = samplingTools
                .getC5SampleRateSpinner();

        int value = (int) c5SampleRateSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(c5SampleRateSpinner,
                            oldC5Speed);

            getCurrentUndoManager().addEdit(spinnerChange);

            oldC5Speed = value;
        }

        if (isAlteringDataSources()) {

            // update sample pan value
            selectedSample.setC5Speed(value);
        }
    }

    public void sampleTransposeComboBoxOnChange() {

        JComboBox sampleTransposeComboBox = samplingTools
                .getSampleTransposeComboBox();

        int index = sampleTransposeComboBox.getSelectedIndex();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(sampleTransposeComboBox,
                            oldTransposeIndex);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);

            oldTransposeIndex = index;
        }

        if (isAlteringDataSources()) {

            //TODO
        }
    }

    public void sampleOnChange(ISampleSynth selectedSample, int modType,
            UndoManager currentUndoManager) {

        this.selectedSample = selectedSample;
        this.setCurrentUndoManager(currentUndoManager);

        // set recording undos and alterations to false for a sample change
        setRecordingUndos(false);
        setAlteringDataSources(false);

        // Middle C speed
        // set old value
        oldC5Speed = selectedSample.getC5Speed();

        samplingTools.getC5SampleRateSpinner()
                .setValue(oldC5Speed);

        oldTransposeIndex = samplingTools
                .getSampleTransposeComboBox().getSelectedIndex();

        // set record undo back to true
        setRecordingUndos(true);
        setAlteringDataSources(true);
    }
}
