/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.module;

import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.undo.UndoManager;
import lang.LanguageHandler;
import module.IChannel;
import module.IModuleHeader;
import module.it.format.EditHistoryEvent;
import module.it.format.ItHeader;
import ui.controllers.GenericController;
import ui.controllers.undo.UndoableButtonGroupChange;
import ui.controllers.undo.UndoableCheckBoxChange;
import ui.controllers.undo.UndoableSliderChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.module.ChannelPanel;
import ui.view.module.DetailsUI;
import ui.view.module.InitialTimings;
import ui.view.module.ModuleDetails;
import ui.view.module.ModuleMessage;
import ui.view.module.ModuleOptions;
import ui.view.module.ModuleSoundOptions;
import ui.view.models.EditSampleViewModel;
import ui.view.models.LoadViewModel;

/**
 *
 * @author Edward Jenkins
 */
public class ModuleController extends GenericController {
    
    // instance variables
    private DetailsUI detailsUI;
    private EditSampleViewModel editSampleVM;
    private LoadViewModel loadVM;
    private IModuleHeader moduleHeader;
    private LanguageHandler languageHandler;
    private ModuleDetailsController moduleDetailsController;
    private ModuleSoundOptionsController moduleSoundOptionsController;
    private InitialTimingController initialTimingController;
    private ModuleOptionsController moduleOptionsController;
    private ChannelController[] channelControllers;
    private UndoManager detailsManager;
    
    public ModuleController(DetailsUI detailsUI, LoadViewModel loadVM) {
        super();
        this.detailsUI = detailsUI;
        this.loadVM = loadVM;
        this.languageHandler = detailsUI.getLanguageHandler();
        moduleHeader = loadVM.getHeader();
        
        // initialise undoManager
        detailsManager = new UndoManager();
        
        // module details
        moduleDetailsController = new ModuleDetailsController(detailsUI
                .getModuleTools().getModuleDetails(), languageHandler, 
                moduleHeader, detailsManager);
        
        // module sould options
        moduleSoundOptionsController = new ModuleSoundOptionsController(detailsUI
                .getModuleTools().getModuleSoundOptions(), languageHandler, 
                moduleHeader, detailsManager);
        
        // initial timings
        initialTimingController = new InitialTimingController(detailsUI
                .getModuleTools().getModuleTiming(), languageHandler, 
                moduleHeader, detailsManager);
        
        // module options
        moduleOptionsController = new ModuleOptionsController(detailsUI
                .getModuleTools().getModuleOptions(), languageHandler, 
                moduleHeader, detailsManager);
        
        setCurrentUndoManager(detailsManager);
        
        loadHeaderProperties();
        
        ChannelPanel[] channelPanels = detailsUI.getChannelsPanel()
                .getChannelsPanel().getChannelPanels();
        
        // set up the channel controllers array
        channelControllers = new ChannelController[channelPanels.length];
        
        List<IChannel> channels = loadVM.getHeader().getIChannels();
        
        // setup channel controllers
        for (int i = 0; i < channelPanels.length; i++) {
            
            channelControllers[i] = new ChannelController(channelPanels[i],
                loadVM, detailsManager, channels.get(i));
        }
        
    }
    
    // controller methods
    @Override
    public void setRecordingUndos(boolean recordingUndos) {
        super.setRecordingUndos(recordingUndos); 
        moduleDetailsController.setRecordingUndos(recordingUndos);
        moduleSoundOptionsController.setRecordingUndos(recordingUndos);
        initialTimingController.setRecordingUndos(recordingUndos);
        moduleOptionsController.setRecordingUndos(recordingUndos);
    }
    
    private void loadHeaderProperties() {
        
        // set recording undos and alterations to false for a header load
        setRecordingUndos(false);
        setAlteringDataSources(false);
        
        // message
        ModuleMessage moduleMessage 
                = detailsUI.getModuleTools().getModuleMessage();
        
        moduleMessage.getMessageArea().setText(moduleHeader.getMessage());
        
        // add the undo manager
        moduleMessage.getMessageArea().getDocument()
                .addUndoableEditListener(detailsManager);
        
        // edit histories
        if (moduleHeader instanceof ItHeader) {
            
            ItHeader itHeader = (ItHeader) moduleHeader;
            
            JTextArea editHistoryArea = detailsUI.getChannelsPanel()
                .getEditHistoryPanel().getEditHistoryArea();
            
            EditHistoryEvent[] editHistoryEvents 
                    = itHeader.getEditHistoryEvents();
            
            if (editHistoryEvents != null) {
                int i = 0;
                for (EditHistoryEvent editHistoryEvent : editHistoryEvents) {
                    editHistoryArea.append(editHistoryEvent.toString());
                    if (i < editHistoryEvents.length - 1) {
                        editHistoryArea.append("\n");
                    }
                }
            }
        }
        
        // set record undo back to true
        setRecordingUndos(true);
        setAlteringDataSources(true);
    }
    
    @Override
    public void redo() {
        for (ChannelController controller : channelControllers) {
            controller.setRecordingUndos(false);
        }
        super.redo(); 
        for (ChannelController controller : channelControllers) {
            controller.setRecordingUndos(true);
        }
    }

    @Override
    public void undo() {
        for (ChannelController controller : channelControllers) {
            controller.setRecordingUndos(false);
        }
        super.undo(); 
        for (ChannelController controller : channelControllers) {
            controller.setRecordingUndos(true);
        }
    }
}
