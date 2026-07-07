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
import ui.view.samples.LoopingTools;
import ui.view.samples.SampleSoundOptions;

/**
 *
 * @author eddy6
 */
public class LoopingToolsController extends GenericController {

    // instance variables
    private LoopingTools loopingTools;
    private ISampleSynth selectedSample;
    private LanguageHandler languageHandler;
    private int loopOldIndex;
    private int loopStartOldValue;
    private int loopEndOldValue;
    

    public LoopingToolsController(LoopingTools loopingTools, 
            LanguageHandler languageHandler) {
        this.loopingTools = loopingTools;
        this.languageHandler = languageHandler;
        // set loop otions listeners
        loopingTools.addLoopComboBoxActionListener(e -> loopComboBoxOnChange());
        loopingTools.addLoopStartSpinnerChangeListener(e -> loopStartSpinnerOnChange());
        loopingTools.addLoopEndSpinnerChangeListener(e -> loopEndSpinnerOnChange());
    }
    
    public void loopComboBoxOnChange() {

        JComboBox loopComboBox = loopingTools.getLoopComboBox();

        int index = loopComboBox.getSelectedIndex();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(loopComboBox, loopOldIndex);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);

            loopOldIndex = index;
        }

        if (isAlteringDataSources()) {

            // alter sample
            switch (index) {
                case 1:
                    selectedSample.setLooped(true);
                    selectedSample.setPingPongLooped(false);
                    break;
                case 2:
                    selectedSample.setLooped(false);
                    selectedSample.setPingPongLooped(true);
                default:
                    selectedSample.setLooped(false);
                    selectedSample.setPingPongLooped(false);
                    break;
            }
        }
    }

    public void loopStartSpinnerOnChange() {

        JSpinner loopStartSpinner = loopingTools.getLoopStartSpinner();

        int value = (int) loopStartSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(loopStartSpinner,
                            loopStartOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            loopStartOldValue = value;
        }

        if (isAlteringDataSources()) {

            // update sample pan value
            selectedSample.setLoopBeginning(value);
        }
    }

    public void loopEndSpinnerOnChange() {

        JSpinner loopEndSpinner = loopingTools.getLoopEndSpinner();

        int value = (int) loopEndSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(loopEndSpinner,
                            loopEndOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            loopEndOldValue = value;
        }

        if (isAlteringDataSources()) {

            // update sample pan value
            selectedSample.setLoopEnd(value);
        }
    }
    
    public void sampleOnChange(ISampleSynth selectedSample, int modType,
            UndoManager currentUndoManager) {

        this.selectedSample = selectedSample;
        this.setCurrentUndoManager(currentUndoManager);

        // set recording undos and alterations to false for a sample change
        setRecordingUndos(false);
        setAlteringDataSources(false);

        // loop
        loopOldIndex = 0;

        if (selectedSample.isLooped()) {

            loopOldIndex = 1;
        } else if (selectedSample.isPingPongLooped()) {

            loopOldIndex = 2;
        }

        loopingTools.getLoopComboBox()
                .setSelectedIndex(loopOldIndex);

        // loop start
        // set old value
        loopStartOldValue = (int) selectedSample.getLoopBeginning();

        SpinnerNumberModel loopStartModel
                = new SpinnerNumberModel(loopStartOldValue, 0,
                        selectedSample.getSampleLength(), 1);

        loopingTools.getLoopStartSpinner()
                .setModel(loopStartModel);

        // loop end
        // set old value
        loopEndOldValue = (int) selectedSample.getLoopEnd();

        SpinnerNumberModel loopEndModel
                = new SpinnerNumberModel(loopEndOldValue, 0,
                        selectedSample.getSampleLength(), 1);

        loopingTools.getLoopEndSpinner()
                .setModel(loopEndModel);

        // set record undo back to true
        setRecordingUndos(true);
        setAlteringDataSources(true);
    }
}
