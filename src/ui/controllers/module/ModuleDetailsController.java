/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.module;

import javax.swing.JTextField;
import javax.swing.undo.UndoManager;
import lang.LanguageHandler;
import module.IModuleHeader;
import ui.controllers.GenericController;
import ui.view.module.ModuleDetails;

/**
 *
 * @author eddy6
 */
public class ModuleDetailsController extends GenericController {
    
    // instance variables
    private ModuleDetails moduleDetails;
    private LanguageHandler languageHandler;
    private IModuleHeader moduleHeader;

    // constructor
    public ModuleDetailsController(ModuleDetails moduleDetails, 
            LanguageHandler languageHandler, IModuleHeader moduleHeader, 
            UndoManager undoManager) {
        this.languageHandler = languageHandler;
        this.moduleDetails = moduleDetails;
        this.moduleHeader = moduleHeader;
        setCurrentUndoManager(undoManager);
        // set module details listeners
        moduleDetails.addSongNameFieldActionListener(e -> songNameOnChange());
        moduleDetails.addSongArtistFieldActionListener(e -> artistNameOnChange());
        loadHeaderProperties();
    }
    
    // mod details
    private void songNameOnChange() {

        JTextField songNameField = moduleDetails.getSongNameField();

        String sampleNameText = songNameField.getText();

        if (isAlteringDataSources()) {
            moduleHeader.setSongName(sampleNameText);
        }
    }

    private void artistNameOnChange() {

        JTextField artistNameField = moduleDetails.getSongArtistField();
        
        String songArtistText = artistNameField.getText();

        if (isAlteringDataSources()) {

            moduleHeader.setAritistName(songArtistText);
        }
    }
    
    private void loadHeaderProperties() {
        
        // set recording undos and alterations to false for a header load
        setRecordingUndos(false);
        setAlteringDataSources(false);
        
        // name
        moduleDetails.getSongNameField().setText(moduleHeader.getSongName());
        moduleDetails.getSongNameField().getDocument()
                .addUndoableEditListener(getCurrentUndoManager());
        
        // artist name
        moduleDetails.getSongArtistField().setText(moduleHeader.getArtistName());
        moduleDetails.getSongArtistField().getDocument()
                .addUndoableEditListener(getCurrentUndoManager());
        
        // set record undo back to true
        setRecordingUndos(true);
        setAlteringDataSources(true);
    }
}
