/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.module;

import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.undo.UndoManager;
import lang.LanguageHandler;
import module.IModuleHeader;
import ui.controllers.GenericController;
import ui.controllers.undo.UndoableSliderChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.module.ModuleSoundOptions;

/**
 *
 * @author eddy6
 */
public class ModuleSoundOptionsController extends GenericController {
    
    // instance variables
    private ModuleSoundOptions moduleSoundOptions;
    private LanguageHandler languageHandler;
    private IModuleHeader moduleHeader;
    private int channelOldValue;
    private int globalVolumeSpinnerOldValue;
    private int globalVolumeSliderOldValue;
    private int mixSpinnerOldValue;
    private int mixSliderOldValue;
    private int panSeparationSpinnerOldValue;
    private int panSeparationSliderOldValue;

    // constructor
    public ModuleSoundOptionsController(ModuleSoundOptions moduleSoundOptions, 
            LanguageHandler languageHandler, IModuleHeader moduleHeader, 
            UndoManager undoManager){
        this.moduleSoundOptions = moduleSoundOptions;
        this.languageHandler = languageHandler;
        this.moduleHeader = moduleHeader;
        setCurrentUndoManager(undoManager);
        // channels
        moduleSoundOptions.addChannelSpinnerChangeListener(
                e -> channelSpinnerOnChange());
        // global volume
        moduleSoundOptions.addGlobVolumeValChangeListener(
                e -> globalVolumeSpinnerOnChange());
        moduleSoundOptions.addGlobVolumeSliderChangeListener(
                e -> globalVolumeSliderOnChange());
        // mix volume
        moduleSoundOptions.addMixVolumeValChangeListener(
                e -> mixVolumeSpinnerOnChange());
        moduleSoundOptions.addMixVolumeSliderChangeListener(
                e -> mixVolumeSliderOnChange());
        // pan separation
        moduleSoundOptions.addPanSepValChangeListener(
                e -> panSeparationSpinnerOnChange());
        moduleSoundOptions.addPanSepSliderChangeListener(
                e -> panSeparationSliderOnChange());
        loadHeaderProperties();
    }
    
    public void channelSpinnerOnChange() {
        
        JSpinner channelSpinner = moduleSoundOptions.getChannelSpinner();

        int value = (int) channelSpinner.getValue();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(channelSpinner,
                            channelOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            channelOldValue = value;
        }

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setChannelCount((byte) value);
        }
    }
    
    public void globalVolumeSpinnerOnChange() {

        JSpinner globalVolumeSpinner = moduleSoundOptions
                .getGlobalVolumeValue();

        int value = (int) globalVolumeSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        moduleSoundOptions.getGlobalVolumeSlider().setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(globalVolumeSpinner,
                            globalVolumeSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }
        
        globalVolumeSpinnerOldValue = value;

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setGlobalVolume((byte) value);
        }
    }

    public void globalVolumeSliderOnChange() {

        JSlider globalVolumeSlider = moduleSoundOptions.getGlobalVolumeSlider();

        int value = globalVolumeSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        moduleSoundOptions.getGlobalVolumeValue().setValue(value);
        setRecordingUndos(recordUndoState);

        if (!globalVolumeSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(globalVolumeSlider,
                                globalVolumeSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }
            
            globalVolumeSliderOldValue = value;

            if (isAlteringDataSources()) {

                // update sample global volume value
                moduleHeader.setGlobalVolume((byte) value);
            }
        }
    }
    
    public void mixVolumeSpinnerOnChange() {

        // get the spinner
        JSpinner mixVolumeSpinner = moduleSoundOptions.getMixVolumeValue();

        int value = (int) mixVolumeSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        moduleSoundOptions.getMixVolumeSlider().setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(mixVolumeSpinner,
                            mixSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }
        
        mixSpinnerOldValue = value;

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setMixVolume((short) value);
        }
    }

    public void mixVolumeSliderOnChange() {

        JSlider mixVolumeSlider = moduleSoundOptions.getMixVolumeSlider();

        int value = mixVolumeSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        moduleSoundOptions.getMixVolumeValue().setValue(value);
        setRecordingUndos(recordUndoState);

        if (!mixVolumeSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(mixVolumeSlider,
                                mixSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }
            
            mixSliderOldValue = value;

            if (isAlteringDataSources()) {

                // update sample global volume value
                moduleHeader.setMixVolume((short) value);
            }
        }
    }
    
    public void panSeparationSpinnerOnChange() {

        JSpinner panSeparationSpinner = moduleSoundOptions
                .getPanSeparationValue();

        int value = (int) panSeparationSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        moduleSoundOptions.getPanSeparationSlider().setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(panSeparationSpinner,
                            panSeparationSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }
        
        panSeparationSpinnerOldValue = value;

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setMixVolume((short) value);
        }
    }

    public void panSeparationSliderOnChange() {

        JSlider panSeparationSlider = moduleSoundOptions
                .getPanSeparationSlider();

        int value = panSeparationSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        moduleSoundOptions.getPanSeparationValue().setValue(value);
        setRecordingUndos(recordUndoState);

        if (!panSeparationSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(panSeparationSlider,
                                panSeparationSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }
            
            panSeparationSliderOldValue = value;

            if (isAlteringDataSources()) {

                // update sample global volume value
                moduleHeader.setMixVolume((short) value);
            }
        }
    }
    
    private void loadHeaderProperties() {
        
        // set recording undos and alterations to false for a header load
        setRecordingUndos(false);
        setAlteringDataSources(false);
        
        // set channels
        channelOldValue = moduleHeader.getChannelCount();
        moduleSoundOptions.getChannelSpinner().setValue(
                (int)moduleHeader.getChannelCount());
        
        // global volume
        globalVolumeSpinnerOldValue = moduleHeader.getGlobalVolume();
        moduleSoundOptions.getGlobalVolumeValue()
                .setValue((int)moduleHeader.getGlobalVolume());
        globalVolumeSliderOldValue = moduleHeader.getGlobalVolume();
        moduleSoundOptions.getGlobalVolumeSlider()
                .setValue(moduleHeader.getGlobalVolume());
        
        // mix
        mixSpinnerOldValue = moduleHeader.getMixVolume();
        moduleSoundOptions.getMixVolumeValue()
                .setValue((int)moduleHeader.getMixVolume());
        mixSliderOldValue = moduleHeader.getMixVolume();
        moduleSoundOptions.getMixVolumeSlider()
                .setValue(moduleHeader.getMixVolume());
        
        // pan separation
        panSeparationSpinnerOldValue = moduleHeader.getPanSeparation();
        moduleSoundOptions.getPanSeparationValue()
                .setValue((int)moduleHeader.getPanSeparation());
        panSeparationSliderOldValue = moduleHeader.getPanSeparation();
        moduleSoundOptions.getPanSeparationSlider()
                .setValue(moduleHeader.getPanSeparation());
        
        // set record undo back to true
        setRecordingUndos(true);
        setAlteringDataSources(true);
    }
}
