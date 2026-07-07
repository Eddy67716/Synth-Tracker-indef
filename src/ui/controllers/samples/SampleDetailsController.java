/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.samples;

import javax.swing.JTextField;
import javax.swing.undo.UndoManager;
import lang.LanguageHandler;
import module.ISampleSynth;
import ui.controllers.GenericController;
import ui.view.models.EditSampleViewModel;
import ui.view.models.LoadViewModel;
import ui.view.samples.SampleDetails;
import ui.view.samples.SampleUI;

/**
 *
 * @author eddy6
 */
public class SampleDetailsController extends GenericController {

    // instance variables
    private SampleDetails sampleDetails;
    private ISampleSynth selectedSample;
    private LanguageHandler languageHandler;
    
    public SampleDetailsController(SampleDetails sampleDetails, 
            LanguageHandler languageHandler) {
        this.sampleDetails = sampleDetails;
        this.languageHandler = languageHandler;
        
        // set sample details lisiteners
        sampleDetails.addSampleNameFieldActionListener(e -> sampleNameOnChange());
        sampleDetails.addFileNameFieldActionListener(e -> fileNameOnChange());
    }
    
    // sample details
    private void sampleNameOnChange() {

        JTextField sampleNameField = sampleDetails
                .getSampleNameField();

        String sampleNameText = sampleNameField.getText();

        if (isRecordingUndos()) {
            //sampleNameText.
        }

        if (isAlteringDataSources()) {
            selectedSample.setSampleName(sampleNameText);
        }
    }

    private void fileNameOnChange() {

        String sampleNameText = sampleDetails.getFileNameField().getText();

        if (isAlteringDataSources()) {

            selectedSample.setDosFileName(sampleNameText.substring(0, 12));
        }
    }
    
    public void sampleOnChange(ISampleSynth selectedSample, int modType, 
            UndoManager currentUndoManager) {

        this.selectedSample = selectedSample;
        this.setCurrentUndoManager(currentUndoManager);

        // set recording undos and alterations to false for a sample change
        setRecordingUndos(false);
        setAlteringDataSources(false);
        
        // sample name
        sampleDetails
                .getSampleNameField().setText(selectedSample.getSampleName());
        sampleDetails.getSampleNameField().getDocument()
                .addUndoableEditListener(currentUndoManager);

        // DOS file name
        if (modType == 2 || modType >= 4) {
            sampleDetails.getFileNameField().setText(selectedSample
                            .getDosFileName());
            sampleDetails.getFileNameField().getDocument()
                    .addUndoableEditListener(currentUndoManager);
        }
        
        // format
        String outputString;

        if (selectedSample.isCompressed()) {
            outputString = languageHandler
                            .getLanguageText("sample.details.format.compressed");
        } else {
            outputString = (selectedSample.isSigned()) 
                    ? languageHandler
                            .getLanguageText("sample.details.format.signed")
                    : languageHandler
                            .getLanguageText("sample.details.format.unsigned");
        }
        
        String bitrate = languageHandler
                            .getLanguageText("sample.details.format.bit")
                .replaceFirst("%1", Short
                        .toString(selectedSample.getBitRate()));

        outputString += bitrate;

        sampleDetails.getSampleFormatLabel().setText(outputString);

        // channels
        outputString = (selectedSample.isStereo()) 
                ? languageHandler
                            .getLanguageText("sound.channels.stereo") 
                : languageHandler
                            .getLanguageText("sound.channels.mono");

        sampleDetails.getSampleChannelLabel().setText(outputString);

        // length
        outputString = Integer.toString(selectedSample.getSampleLength());

        sampleDetails.getSampleLengthLabel().setText(outputString);

        // set record undo back to true
        setRecordingUndos(true);
        setAlteringDataSources(true);
    }
    
}
