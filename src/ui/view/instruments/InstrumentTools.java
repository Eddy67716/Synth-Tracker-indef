/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.instruments;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import lang.LanguageHandler;
import module.IInstrument;

/**
 *
 * @author Edward Jenkins
 */
public class InstrumentTools extends JPanel {

    // instance variables
    private int modType;
    private LanguageHandler languageHandler;
    // this panel
    private GridBagLayout toolsLayout;
    private GridBagConstraints tc;
    // basics
    private IInstrument selectedInstrument;
    // instrument details (file name, etc)
    private InstrumentDetails instrumentDetails;
    // sound options
    private InstrumentSoundOptions soundOptions;
    // sustain options
    private SustainOptions sustainOptions;
    // filter options
    private FilterOptions filterOptions;
    // random options
    private RandomOffsets randomOffsets;
    // MIDI options
    private MidiOptions midiOptions;
    // Note map view
    private NoteMapView noteMapView;

    public InstrumentTools(int modType, LanguageHandler languageHandler) {
        this.modType = modType;
        this.languageHandler = languageHandler;

        init();
    }

    // gettes
    public int getModType() {
        return modType;
    }

    public InstrumentDetails getInstrumentDetails() {
        return instrumentDetails;
    }

    public InstrumentSoundOptions getSoundOptions() {
        return soundOptions;
    }

    public SustainOptions getSustainOptions() {
        return sustainOptions;
    }

    public FilterOptions getFilterOptions() {
        return filterOptions;
    }

    public RandomOffsets getRandomOffsets() {
        return randomOffsets;
    }

    public MidiOptions getMidiOptions() {
        return midiOptions;
    }

    public NoteMapView getNoteMapView() {
        return noteMapView;
    }

    public void init() {
        
        // set layout
        toolsLayout = new GridBagLayout();
        tc = new GridBagConstraints();
        tc.anchor = GridBagConstraints.SOUTHWEST;
        tc.fill = GridBagConstraints.BOTH;  
        this.setLayout(toolsLayout);

        // add innstrument details to tools
        instrumentDetails = new InstrumentDetails(modType, languageHandler);
        //tc.fill = GridBagConstraints.BOTH;
        tc.gridx = 0;
        tc.weightx = 0;
        tc.gridy = 0;
        tc.weighty = 0.5;
        this.add(instrumentDetails, tc);
        
        // add sustain options to tools
        sustainOptions = new SustainOptions(modType, languageHandler);
        tc.gridy++;
        tc.weighty = 0.5;
        tc.gridheight = GridBagConstraints.REMAINDER;
        this.add(sustainOptions, tc);
        
        // add random offsets to tools
        randomOffsets = new RandomOffsets(modType, languageHandler);
        tc.gridx++;
        tc.gridy = 0;
        tc.weighty = 0.5;
        tc.gridheight = 1;
        add(randomOffsets, tc);
        
        // add MIDI options
        midiOptions = new MidiOptions(modType, languageHandler);
        tc.gridy = 1;
        tc.weighty = 0.5;
        tc.gridheight = GridBagConstraints.REMAINDER;
        add(midiOptions, tc);
        
        // add filter options to tools
        filterOptions = new FilterOptions(modType, languageHandler);
        tc.gridx++;
        tc.gridy = 0;
        tc.weighty = 0.5;
        tc.gridheight = 1;
        add(filterOptions, tc);
        
        // add instrument sound options to tools 
        soundOptions = new InstrumentSoundOptions(modType, languageHandler);
        tc.gridy = 1;
        tc.weighty = 0.5;
        tc.gridheight = GridBagConstraints.REMAINDER;
        add(soundOptions, tc);
        
        // add note map view
        noteMapView = new NoteMapView(modType, languageHandler);
        tc.gridx++;
        tc.gridy = 0;
        tc.weighty = 1;
        tc.gridheight = GridBagConstraints.REMAINDER;
        add(noteMapView, tc);
        
        // add trailing JPanel
        tc.gridx++;
        tc.gridy = 0;
        tc.weightx = 1.0;
        tc.gridwidth = GridBagConstraints.REMAINDER;
        tc.gridheight = 1;
        this.add(new JPanel(), tc);
        
    }
}
