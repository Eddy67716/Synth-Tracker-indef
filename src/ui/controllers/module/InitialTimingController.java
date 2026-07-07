/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.module;

import javax.swing.JSpinner;
import javax.swing.undo.UndoManager;
import lang.LanguageHandler;
import module.IModuleHeader;
import ui.controllers.GenericController;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.module.InitialTimings;

/**
 *
 * @author eddy6
 */
public class InitialTimingController extends GenericController {
    
    // instance variables
    private InitialTimings initialTimings;
    private LanguageHandler languageHandler;
    private IModuleHeader moduleHeader;
    private int tempoOldValue;
    private int tickSpeedOldValue;

    // constructor
    public InitialTimingController(InitialTimings initialTimings, 
            LanguageHandler languageHandler, IModuleHeader moduleHeader, 
            UndoManager undoManager) {
        this.initialTimings = initialTimings;
        this.languageHandler = languageHandler;
        this.moduleHeader = moduleHeader;
        setCurrentUndoManager(undoManager);
        // initial speed
        initialTimings.addTickSpeedSpinnerChangeListener(
                e -> this.tickSpeedSpinnerOnChange());
        // initial tempo
        initialTimings.addBpmSpinnerChangeListener(
                e -> this.tempoSpinnerOnChange());
        loadHeaderProperties();
    }
    
    // initial timings
    public void tickSpeedSpinnerOnChange() {
        
        JSpinner tickSpeedSpinner = initialTimings.getTickSpeedSpinner();

        int value = (int) tickSpeedSpinner.getValue();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(tickSpeedSpinner,
                            tickSpeedOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            tickSpeedOldValue = value;
        }

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setInitialSpeed((short) value);
        }
    }
    
    // initial timings
    public void tempoSpinnerOnChange() {
        
        JSpinner tempoSpinner = initialTimings.getTempoSpinner();
        
        int value = (int) tempoSpinner.getValue();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(tempoSpinner,
                            tempoOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            tempoOldValue = value;
        }

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setInitialTempo((short) value);
        }
    }
    
    private void loadHeaderProperties() {
        
        // set recording undos and alterations to false for a header load
        setRecordingUndos(false);
        setAlteringDataSources(false);
        
        // intials speed
        tickSpeedOldValue = moduleHeader.getInitialSpeed();
        initialTimings.getTickSpeedSpinner()
                .setValue((int)moduleHeader.getInitialSpeed());
        
        // initial tempo
        tempoOldValue = moduleHeader.getInitialTempo();
        initialTimings.getTempoSpinner()
                .setValue((int)moduleHeader.getInitialTempo());
        
        // set record undo back to true
        setRecordingUndos(true);
        setAlteringDataSources(true);
    }
}
