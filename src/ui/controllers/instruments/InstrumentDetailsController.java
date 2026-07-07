/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.instruments;

import javax.swing.JTextField;
import lang.LanguageHandler;
import module.IInstrument;
import ui.controllers.GenericController;
import ui.view.instruments.InstrumentDetails;

/**
 *
 * @author eddy6
 */
public class InstrumentDetailsController extends GenericController {
    
    // instance variables
    private InstrumentDetails instrumentDetails;
    private LanguageHandler languageHandler;
    private IInstrument selectedInstrument;

    // constructor
    public InstrumentDetailsController(InstrumentDetails instrumentDetails, 
            LanguageHandler languageHandler) {
        this.instrumentDetails = instrumentDetails;
        this.languageHandler = languageHandler;
        
        // set instrument details lisiteners
        instrumentDetails.addInstrumentNameActionListener(e -> instrumentNameOnChange());
        instrumentDetails.addInstrumentFileNameActionListener(e -> fileNameOnChange());
    }
    
    // sample details
    private void instrumentNameOnChange() {

        JTextField sampleNameField = instrumentDetails
                .getFileNameField();

        String instrumentNameText = sampleNameField.getText();

        if (isAlteringDataSources()) {
            selectedInstrument.setInstrumentName(instrumentNameText);
        }
    }

    private void fileNameOnChange() {

        String fileNameText = instrumentDetails.getFileNameField().getText();

        if (isAlteringDataSources()) {

            selectedInstrument.setDosFileName(fileNameText.substring(0, 12));
        }
    }
}
