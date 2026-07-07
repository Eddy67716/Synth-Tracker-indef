/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.instruments;

import lang.LanguageHandler;
import module.IInstrument;
import ui.controllers.GenericController;
import ui.view.instruments.NoteMapView;

/**
 *
 * @author eddy6
 */
public class NoteMapController extends GenericController {
    
    // instance variables
    private NoteMapView noteMapView;
    private LanguageHandler languageHandler;
    private IInstrument selectedInstrument;

    public NoteMapController(NoteMapView noteMapView, LanguageHandler languageHandler) {
        this.noteMapView = noteMapView;
        this.languageHandler = languageHandler;
    }
    
    
}
