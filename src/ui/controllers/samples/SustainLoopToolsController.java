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
import ui.view.samples.SustainLoopTools;

/**
 *
 * @author eddy6
 */
public class SustainLoopToolsController extends GenericController {

    // instance variables
    private SustainLoopTools sustainLoopTools;
    private ISampleSynth selectedSample;
    private LanguageHandler languageHandler;
    private int sustainLoopOldIndex;
    private int susLoopStartOldValue;
    private int susLoopEndOldValue;

    public SustainLoopToolsController(SustainLoopTools sustainLoopTools,
            LanguageHandler languageHandler) {
        this.sustainLoopTools = sustainLoopTools;
        this.languageHandler = languageHandler;
        // set sustain loop otions listeners
        sustainLoopTools.addSusLoopComboBoxActionListener(e -> susLoopComboBoxOnChange());
        sustainLoopTools.addSusLoopStartSpinnerChangeListener(e
                -> susLoopStartSpinnerOnChange());
        sustainLoopTools.addSusLoopEndSpinnerChangeListener(e
                -> susLoopEndSpinnerOnChange());
    }

    // sustain loop tools
    public void susLoopComboBoxOnChange() {

        JComboBox susLoopComboBox = sustainLoopTools.getSusLoopComboBox();

        int index = susLoopComboBox.getSelectedIndex();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(susLoopComboBox,
                            sustainLoopOldIndex);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);

            sustainLoopOldIndex = index;
        }

        if (isAlteringDataSources()) {

            // alter sample
            switch (index) {
                case 1:
                    selectedSample.setSustainLooped(true);
                    selectedSample.setPingPongSustainLooped(false);
                    break;
                case 2:
                    selectedSample.setSustainLooped(false);
                    selectedSample.setPingPongSustainLooped(true);
                default:
                    selectedSample.setSustainLooped(false);
                    selectedSample.setPingPongSustainLooped(false);
                    break;
            }
        }
    }

    public void susLoopStartSpinnerOnChange() {

        JSpinner susLoopStartSpinner = sustainLoopTools
                .getSusLoopStartSpinner();

        int value = (int) susLoopStartSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(susLoopStartSpinner,
                            susLoopStartOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            susLoopStartOldValue = value;
        }

        if (isAlteringDataSources()) {

            // update sample pan value
            selectedSample.setSustainLoopBeginning(value);
        }
    }

    public void susLoopEndSpinnerOnChange() {

        JSpinner susLoopEndSpinner = sustainLoopTools.getSusLoopEndSpinner();

        int value = (int) susLoopEndSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(susLoopEndSpinner,
                            susLoopEndOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            susLoopEndOldValue = value;
        }

        if (isAlteringDataSources()) {

            // update sample pan value
            selectedSample.setSustainLoopEnd(value);
        }
    }

    public void sampleOnChange(ISampleSynth selectedSample, int modType,
            UndoManager currentUndoManager) {

        this.selectedSample = selectedSample;
        this.setCurrentUndoManager(currentUndoManager);

        // set recording undos and alterations to false for a sample change
        setRecordingUndos(false);
        setAlteringDataSources(false);

        // sustain loop
        sustainLoopOldIndex = 0;

        if (selectedSample.isSustainLooped()) {

            sustainLoopOldIndex = 1;
        } else if (selectedSample.isPingPongLooped()) {

            sustainLoopOldIndex = 2;
        }

        sustainLoopTools.getSusLoopComboBox()
                .setSelectedIndex(sustainLoopOldIndex);

        // sustain loop start
        susLoopStartOldValue = (int) selectedSample
                .getSustainLoopBeginning();

        SpinnerNumberModel susLoopStartModel
                = new SpinnerNumberModel(susLoopStartOldValue, 0,
                        selectedSample.getSampleLength(), 1);

        sustainLoopTools.getSusLoopStartSpinner()
                .setModel(susLoopStartModel);

        // sustain loop end
        susLoopEndOldValue = (int) selectedSample
                .getSustainLoopEnd();

        SpinnerNumberModel susLoopEndModel
                = new SpinnerNumberModel(susLoopEndOldValue, 0,
                        selectedSample.getSampleLength(), 1);

        sustainLoopTools.getSusLoopEndSpinner()
                .setModel(susLoopEndModel);

        // set record undo back to true
        setRecordingUndos(true);
        setAlteringDataSources(true);
    }
}
