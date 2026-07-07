/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.module;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.undo.UndoManager;
import lang.LanguageHandler;
import module.IModuleHeader;
import ui.controllers.GenericController;
import ui.controllers.undo.UndoableButtonGroupChange;
import ui.controllers.undo.UndoableCheckBoxChange;
import ui.view.module.ModuleOptions;

/**
 *
 * @author eddy6
 */
public class ModuleOptionsController extends GenericController {
    
    // instance variables
    private ModuleOptions moduleOptions;
    private LanguageHandler languageHandler;
    private IModuleHeader moduleHeader;
    private ButtonModel channelSelectionOldButton;
    private ButtonModel slideSelectionOldButton;
    private ButtonModel effectSelectionOldButton;
    private ButtonModel instrumentSelectionOldButton;

    // constructor
    public ModuleOptionsController(ModuleOptions moduleOptions, 
            LanguageHandler languageHandler, IModuleHeader moduleHeader, 
            UndoManager undoManager) {
        this.moduleOptions = moduleOptions;
        this.languageHandler = languageHandler;
        this.moduleHeader = moduleHeader;
        setCurrentUndoManager(undoManager);
        // set module options
        moduleOptions.addStereoOptionsAction(e -> stereoOptionChange());
        moduleOptions.addMonoOptionsAction(e -> monoOptionChange());
        moduleOptions.addInstrumentOptionsAction(e -> instrumentOptionChange());
        moduleOptions.addSampleOptionsAction(e -> sampleOptionChange());
        moduleOptions.addLinearSlideOptionsAction(e -> linearSlideOptionChange());
        moduleOptions.addAmigaSlideOptionsAction(e -> amigaSlideOptionChange());
        moduleOptions.addOldEffectOptionsAction(e -> oldEffectsOptionChange());
        moduleOptions.addNewEffectOptionsAction(e -> newEffectsOptionChange());
        moduleOptions.addPortamentoLinkAction(e -> portamentoLinkOnChange());
        loadHeaderProperties();
    }
    
    // module options
    public void stereoOptionChange() {
        
        ButtonGroup channelButtionGroup = moduleOptions.getChannelGroup();
        
        JRadioButton stereoRadioButton = moduleOptions.getStereoOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(channelButtionGroup, 
                            channelSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        channelSelectionOldButton = stereoRadioButton.getModel();

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setStereo(stereoRadioButton.isSelected());
        }
    }
    
    public void monoOptionChange() {
        
        ButtonGroup channelButtionGroup = moduleOptions.getChannelGroup();
        
        JRadioButton monoRadioButton = moduleOptions.getMonoOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(channelButtionGroup, 
                            channelSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        channelSelectionOldButton = monoRadioButton.getModel();

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setStereo(!monoRadioButton.isSelected());
        }
    }
    
    public void instrumentOptionChange() {
        
        ButtonGroup instrumentButtonGroup = moduleOptions.getInstrumentGroup();
        
        JRadioButton instrumentRadioButton = moduleOptions
                .getInstrumentOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(instrumentButtonGroup, 
                            instrumentSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        instrumentSelectionOldButton = instrumentRadioButton.getModel();

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setUsingInstruments(
                    instrumentRadioButton.isSelected());
        }
    }
    
    public void sampleOptionChange() {
        
        ButtonGroup instrumentButtonGroup = moduleOptions.getInstrumentGroup();
        
        JRadioButton sampleRadioButton = moduleOptions.getSampleOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(instrumentButtonGroup, 
                            instrumentSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        instrumentSelectionOldButton = sampleRadioButton.getModel();

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setUsingInstruments(!sampleRadioButton.isSelected());
        }
    }
    
    public void linearSlideOptionChange() {
        
        ButtonGroup slideButtonGroup = moduleOptions.getSlideGroup();
        
        JRadioButton linearSlideRadioButton = moduleOptions
                .getLinearSlidesOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(slideButtonGroup, 
                            slideSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        slideSelectionOldButton = linearSlideRadioButton.getModel();

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setUsingLinearSlides(
                    linearSlideRadioButton.isSelected());
        }
    }
    
    public void amigaSlideOptionChange() {
        
        ButtonGroup slideButtonGroup = moduleOptions.getSlideGroup();
        
        JRadioButton amigaSlideRadioButton = moduleOptions
                .getAmigaSlidesOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(slideButtonGroup, 
                            slideSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        slideSelectionOldButton = amigaSlideRadioButton.getModel();

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setUsingLinearSlides(!amigaSlideRadioButton
                    .isSelected());
        }
    }
    
    public void oldEffectsOptionChange() {
        
        ButtonGroup effectButtonGroup = moduleOptions.getEffectGroup();
        
        JRadioButton oldEffectsRadioButton = moduleOptions
                .getOldEffectsOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(effectButtonGroup, 
                            this.effectSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        effectSelectionOldButton = oldEffectsRadioButton.getModel();

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setUsingOldEffects(oldEffectsRadioButton.isSelected());
        }
    }
    
    public void newEffectsOptionChange() {
        
        ButtonGroup slideButtonGroup = moduleOptions.getEffectGroup();
        
        JRadioButton newEffectsRadioButton = moduleOptions
                .getNewEffecstOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(slideButtonGroup, 
                            effectSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        effectSelectionOldButton = newEffectsRadioButton.getModel();

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setUsingOldEffects(!newEffectsRadioButton.isSelected());
        }
    }
    
    // portamento link
    public void portamentoLinkOnChange() {
        
        JCheckBox portamentoLinkCheckBox = moduleOptions.getPortamentoLink();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableCheckBoxChange checkBoxChange
                    = new UndoableCheckBoxChange(portamentoLinkCheckBox);

            getCurrentUndoManager().addEdit(checkBoxChange);
        }

        if (isAlteringDataSources()) {

            // update module global volume value
            moduleHeader.setPortamentoLinked(portamentoLinkCheckBox
                    .isSelected());
        }
    }
    
    private void loadHeaderProperties() {
        
        // set recording undos and alterations to false for a header load
        setRecordingUndos(false);
        setAlteringDataSources(false);
        
        // channels
        boolean stereo = moduleHeader.isStereo();
        
        if (stereo) {
            // stereo
            moduleOptions.getStereoOption().setSelected(true);
            channelSelectionOldButton = moduleOptions.getStereoOption()
                    .getModel();
        } else {
            // mono
            moduleOptions.getMonoOption().setSelected(true);
            channelSelectionOldButton = moduleOptions.getMonoOption()
                    .getModel();
        }
        
        // instruments
        boolean instrumental = moduleHeader.isUsingInstruments();
        
        if (instrumental) {
            // instruments
            moduleOptions.getInstrumentOption().setSelected(true);
            instrumentSelectionOldButton = moduleOptions.getInstrumentOption()
                    .getModel();
        } else {
            // samples
            moduleOptions.getSampleOption().setSelected(true);
            instrumentSelectionOldButton = moduleOptions.getSampleOption()
                    .getModel();
        }
        
        // slides
        boolean linear = moduleHeader.isUsingLinearSlides();
        
        if (linear) {
            // old
            moduleOptions.getLinearSlidesOption().setSelected(true);
            slideSelectionOldButton = moduleOptions.getLinearSlidesOption()
                    .getModel();
        } else {
            // new
            moduleOptions.getAmigaSlidesOption().setSelected(true);
            slideSelectionOldButton = moduleOptions.getAmigaSlidesOption()
                    .getModel();
        }
        
        // effects
        boolean old = moduleHeader.isUsingOldEffects();
        
        if (old) {
            // old
            moduleOptions.getOldEffectsOption().setSelected(true);
            effectSelectionOldButton = moduleOptions.getOldEffectsOption()
                    .getModel();
        } else {
            // new
            moduleOptions.getNewEffecstOption().setSelected(true);
            effectSelectionOldButton = moduleOptions.getNewEffecstOption()
                    .getModel();
        }
        
        // set record undo back to true
        setRecordingUndos(true);
        setAlteringDataSources(true);
    }
}
