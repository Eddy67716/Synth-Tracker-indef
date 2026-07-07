/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.instruments;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import lang.LanguageHandler;
import module.IInstrument;
import org.json.JSONException;
import ui.controllers.GenericController;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.instruments.MidiOptions;

/**
 *
 * @author eddy6
 */
public class MidiOptionsController extends GenericController {
    
    // instance variables
    private MidiOptions midiOptions;
    private LanguageHandler languageHandler;
    private IInstrument selectedInstrument;
    private int midiProgramOldValue;
    private int midiChannelOldValue;
    private int midiBankOldValue;

    // constructor
    public MidiOptionsController(MidiOptions midiOptions, 
            LanguageHandler languageHandler) {
        this.midiOptions = midiOptions;
        this.languageHandler = languageHandler;
        // set the midi actions
        midiOptions.addMidiChannelChangeListener(e -> midiChannelOnChange());
        midiOptions.addMidiInstrumentChangeListener(e -> midiProgramOnChange());
        midiOptions.addMidiBankChangeListener(e -> midiBankOnChange());
    }
    
    // midi option events
    public void midiChannelOnChange() {

        JSpinner midiChannelSpinner = midiOptions.getMidiChannelSpinner();

        int value = (int) midiChannelSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(midiChannelSpinner,
                            midiChannelOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(spinnerChange);
        }

        if (isAlteringDataSources()) {

            // update instrument global volume value
            selectedInstrument.setMidiChannel((short) value);
        }

        midiChannelOldValue = value;
    }

    public void midiProgramOnChange() {
        JSpinner instrumentSpinner = midiOptions.getMidiInstrumentSpinner();

        int value = (int) instrumentSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(instrumentSpinner,
                            midiProgramOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(spinnerChange);
        }

        if (isAlteringDataSources()) {

            // update instrument global volume value
            selectedInstrument.setMidiProgram((byte) ((int) instrumentSpinner
                    .getValue() - 1));
        }

        midiProgramOldValue = value;

        // change program text
        programTextChange(instrumentSpinner);

        // update instrument midi name
        instrumentNameChange();
    }

    public void programTextChange(JSpinner instrumentSpinner) {

        // get spinner value
        int programValue = (int) instrumentSpinner.getValue();

        String programTextID = "midi.program.";

        int channelValue = (int) midiOptions.getMidiChannelSpinner().getValue();

        if (programValue == 0) {
            programTextID += "unspecified";
        } else {
            programTextID += Integer.toString(programValue);

        }

        if (channelValue == 10) {
            programTextID += ".drumset";
            try {
                languageHandler.getLanguageText(programTextID);
            } catch (JSONException e) {
                programTextID = "midi.program.unspecified.drumset";
            }
        }

        // update tool tip text to appropriate instrument
        instrumentSpinner.setToolTipText(languageHandler
                .getLanguageText(programTextID));
    }

    public void midiBankOnChange() {
        JSpinner midiBankSpinner = midiOptions.getMidiBankSpinner();

        int value = (int) midiBankSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(midiBankSpinner,
                            midiBankOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(spinnerChange);
        }

        if (isAlteringDataSources()) {

            // update instrument global volume value
            selectedInstrument.setMidiBank((short) value);
        }

        midiBankOldValue = value;

        // update instrument midi name
        instrumentNameChange();
    }

    public void instrumentNameChange() {

        JLabel midiInstrumentNameLabel = midiOptions
                .getMidiInstrumentDisplayLabel();

        int bank = (int) midiOptions.getMidiBankSpinner().getValue();

        int program = (int) midiOptions.getMidiInstrumentSpinner().getValue();

        int channel = (int) midiOptions.getMidiChannelSpinner().getValue();

        String nameIndexString = "midi.program.";

        String midiInstrumentName;

        if (program == 0) {
            nameIndexString += "unspecified";
        } else {
            nameIndexString += Integer.toString(program);
        }

        if (channel == 10 || (bank == 15360 && channel == 11)) {

            nameIndexString += ".drumset";
            try {
                midiInstrumentName = languageHandler
                    .getLanguageText(nameIndexString);
            } catch (JSONException e) {
                nameIndexString = "midi.program.unspecified.drumset";
                midiInstrumentName = languageHandler
                    .getLanguageText(nameIndexString);
            }

        } else if (bank != 0 && bank != 15488) {

            String bankTest = nameIndexString + ".bank." + bank;

            try {
                bankTest = languageHandler.getLanguageText(bankTest);
            } catch (JSONException e) {
                bankTest = languageHandler.getLanguageText("midi.bank_unknown");
                bankTest = bankTest.replaceFirst("%1", languageHandler
                        .getLanguageText(nameIndexString));
            }
            midiInstrumentName = bankTest;

        } else {
            midiInstrumentName = languageHandler
                    .getLanguageText(nameIndexString);
        }

        midiInstrumentNameLabel.setText(midiInstrumentName);
    }
}
