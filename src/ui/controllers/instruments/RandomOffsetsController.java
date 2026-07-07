/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.instruments;

import javax.swing.JSlider;
import javax.swing.JSpinner;
import lang.LanguageHandler;
import module.IInstrument;
import ui.controllers.GenericController;
import ui.controllers.undo.UndoableSliderChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.instruments.RandomOffsets;

/**
 *
 * @author eddy6
 */
public class RandomOffsetsController extends GenericController {
    
    // instance variables
    private RandomOffsets randomOffsets;
    private LanguageHandler languageHandler;
    private IInstrument selectedInstrument;
    private int randomVolumeSpinnerOldValue;
    private int randomVolumeSliderOldValue;
    private int randomPanSpinnerOldValue;
    private int randomPanSliderOldValue;
    private int randomCutoffSpinnerOldValue;
    private int randomCutoffSliderOldValue;
    private int randomRezSpinnerOldValue;
    private int randomRezSliderOldValue;
    
    // constructor
    public RandomOffsetsController(RandomOffsets randomOffsets, 
            LanguageHandler languageHandler) {
        this.randomOffsets = randomOffsets;
        this.languageHandler = languageHandler;
    }
    
    // random variation events
    public void randomVolumeSpinnerOnChange() {

        JSpinner randomVolSpinner = randomOffsets.getRandomVolumeSpinner();

        int value = (int) randomVolSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        randomOffsets.getRandomVolumeSlider().setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(randomVolSpinner,
                            randomVolumeSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }

        randomVolumeSpinnerOldValue = value;

        if (isAlteringDataSources()) {

            // update instrument random volume value
            selectedInstrument.setRandomVolumeVariation((byte) value);
        }
    }

    public void randomVolumeSliderOnChange() {

        JSlider randomVolSlider = randomOffsets.getRandomVolumeSlider();

        int value = randomVolSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        randomOffsets.getRandomVolumeSpinner().setValue(value);
        setRecordingUndos(recordUndoState);

        if (!randomVolSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(randomVolSlider,
                                randomVolumeSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }

            randomVolumeSliderOldValue = value;

            if (isAlteringDataSources()) {

                // update instrument random volume value
                selectedInstrument.setRandomVolumeVariation((byte) value);
            }
        }
    }

    public void randomPanningSpinnerOnChange() {

        JSpinner randomPanSpinner = randomOffsets.getRandomPanningSpinner();

        int value = (int) randomPanSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        randomOffsets.getRandomPanningSlider().setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(randomPanSpinner,
                            randomPanSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }

        randomPanSpinnerOldValue = value;

        if (isAlteringDataSources()) {

            // update instrument random panning value
            selectedInstrument.setRandomPanningVariation((byte) value);
        }
    }

    public void randomPanningSliderOnChange() {
        JSlider randomPanSlider = randomOffsets.getRandomPanningSlider();

        int value = randomPanSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        randomOffsets.getRandomPanningSpinner().setValue(value);
        setRecordingUndos(recordUndoState);

        if (!randomPanSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(randomPanSlider,
                                randomPanSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }

            randomPanSliderOldValue = value;

            if (isAlteringDataSources()) {

                // update instrument random pan value
                selectedInstrument.setRandomPanningVariation((byte) value);
            }
        }
    }
    
}
