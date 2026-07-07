/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.samples;

import java.awt.Dimension;
import ui.view.samples.SampleTools;
import ui.view.samples.SampleUI;
import ui.view.samples.SampleWindow;
import ui.view.models.EditSampleViewModel;
import ui.view.models.LoadViewModel;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.undo.UndoManager;
import lang.LanguageHandler;
import ui.controllers.undo.UndoableCheckBoxChange;
import ui.controllers.undo.UndoableComboBoxChange;
import ui.controllers.undo.UndoableSliderChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.samples.LoopingTools;
import ui.view.samples.SampleCanvas;
import ui.view.samples.SampleDetails;
import ui.view.samples.SampleSoundOptions;
import ui.view.samples.SamplingTools;
import ui.view.samples.SustainLoopTools;
import ui.view.samples.VibratoOptions;
import module.ISampleSynth;
import ui.controllers.GenericController;

/**
 *
 * @author Edward Jenkins
 */
public class SampleController extends GenericController {

    // instance variables
    private SampleUI sampleUI;
    private EditSampleViewModel editSampleVM;
    private LoadViewModel loadVM;
    private ISampleSynth selectedSample;
    private UndoManager[] sampleManagers;
    private LanguageHandler languageHandler;
    private SampleDetailsController sampleDetailsController;
    private SampleSoundOptionsController sampleSoundOptionsController;
    private LoopingToolsController loopingToolsController;
    private SustainLoopToolsController sustainLoopToolsController;
    private SamplingToolsController samplingToolsController;
    private VibratoOptionsController vibratoOptionsController;

    // constructor
    public SampleController(SampleUI sampleUI, LoadViewModel loadVM) {
        super();
        this.sampleUI = sampleUI;
        this.languageHandler = sampleUI.getLanguageHandler();
        this.loadVM = loadVM;

        // initialise undo managers
        sampleManagers = new UndoManager[loadVM.getSamples().size()];
        for (int i = 0; i < sampleManagers.length; i++) {
            sampleManagers[i] = new UndoManager();
        }

        // set sample details controller
        sampleDetailsController 
                = new SampleDetailsController(sampleUI.getTools()
                        .getSampleDetails(), languageHandler);

        // set sound options controller
        sampleSoundOptionsController 
                = new SampleSoundOptionsController(sampleUI.getTools()
                        .getSoundOptions(), languageHandler);
        
        // set looping tools controller
        loopingToolsController
                = new LoopingToolsController(sampleUI.getTools()
                        .getLoopingTools(), languageHandler);
        
        // set sustain loop tools controller
        sustainLoopToolsController
                = new SustainLoopToolsController(sampleUI.getTools()
                        .getSusLoopTools(), languageHandler);
        
        // set sampoing tool controller
        samplingToolsController
                = new SamplingToolsController(sampleUI.getTools()
                        .getSamplingTools(), languageHandler);

        // set vibrato options controller
        vibratoOptionsController
                = new VibratoOptionsController(sampleUI.getTools()
                        .getVibratoOptions(), languageHandler);
        
        // set sample canvas slider options
        SampleWindow sw = sampleUI.getSampleWindow();
        sw.addZoomSliderChangeEvent(e -> zoomSliderOnChange());

        this.sampleUI.addSampleSelectSpinnerChangeListener(
                e -> sampleOnChange());
        // set drop target
        this.sampleUI.setDropTarget(new DropTarget() {

            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        loadSample(file);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        sampleOnChange();
    }

    @Override
    public void setRecordingUndos(boolean recordingUndos) {
        super.setRecordingUndos(recordingUndos); 
        sampleDetailsController.setRecordingUndos(recordingUndos);
        sampleSoundOptionsController.setRecordingUndos(recordingUndos);
        loopingToolsController.setRecordingUndos(recordingUndos);
        sustainLoopToolsController.setRecordingUndos(recordingUndos);
        samplingToolsController.setRecordingUndos(recordingUndos);
        vibratoOptionsController.setRecordingUndos(recordingUndos);
    }
    
    public void zoomSliderOnChange() {
        SampleCanvas canvas = sampleUI.getSampleWindow().getCanvas();
        
        int zoomValue = sampleUI.getSampleWindow().getZoomSlider().getValue();
        
        double widthRatio = 1d / selectedSample.getSampleLength();
        
        double currentWidthRatio = zoomValue * widthRatio;
        
        int newWidth = (int)Math.round(1800 / currentWidthRatio);
        
        canvas.setSize(new Dimension(newWidth, canvas.getHeight()));
        canvas.setPreferredSize(new Dimension(newWidth, canvas.getHeight()));
        
        canvas.repaint();
    }

    public void sampleOnChange() {

        int modType = sampleUI.getTools().getModType();

        // set recording undos and alterations to false for a sample change
        setRecordingUndos(false);
        setAlteringDataSources(false);

        // loop code
        if ((int) sampleUI.getSampleSelectSpinner().getValue()
                == loadVM.getSamples().size() + 1) {
            sampleUI.getSampleSelectSpinner().setValue(1);
        } else if ((int) sampleUI.getSampleSelectSpinner().getValue() == 0) {
            sampleUI.getSampleSelectSpinner().setValue(loadVM.getSamples()
                    .size());
        }

        // get selected sample value
        int value = (int) sampleUI.getSampleSelectSpinner().getValue()
                - 1;

        // get sample
        sampleUI.setSelectedSample(value);
        selectedSample = loadVM.getSamples()
                .get(sampleUI.getSelectedSample());

        // set current undoManager
        setCurrentUndoManager(sampleManagers[value]);
        
        // change sample for sample details controller
        sampleDetailsController.sampleOnChange(selectedSample, modType, 
                getCurrentUndoManager());
        
        // change sample for sample sound options
        sampleSoundOptionsController.sampleOnChange(selectedSample, modType, 
                getCurrentUndoManager());

        // change sample for loop tools
        loopingToolsController.sampleOnChange(selectedSample, modType, 
                getCurrentUndoManager());
        
        // change sample for sustain loop tools
        sustainLoopToolsController.sampleOnChange(selectedSample, modType, 
                getCurrentUndoManager());
        
        // change sample for sampling tools
        samplingToolsController.sampleOnChange(selectedSample, modType, 
                getCurrentUndoManager());
                
                // change sample for vibrato options
        vibratoOptionsController.sampleOnChange(selectedSample, modType, 
                getCurrentUndoManager());
        
        // zoom slider value
        JSlider zoomSlider = sampleUI.getSampleWindow().getZoomSlider();
        zoomSlider.setMaximum(selectedSample.getSampleLength());
        zoomSlider.setMinimum(20);
        zoomSlider.setValue(selectedSample.getSampleLength());

        // sample data
        SwingUtilities.invokeLater(() -> {
            sampleUI.getSampleWindow().getCanvas()
                    .setSampleData(selectedSample.getAudioSampleData());
        });

        // set record undo back to true
        setRecordingUndos(true);
        setAlteringDataSources(true);
    }

    public void loadSample(File file) {

        // lodad sample using loadVM
        loadVM.readSampleFile(file.getPath());
    }
}
