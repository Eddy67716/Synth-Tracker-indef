/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.windows.replace;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 *
 * @author eddy6
 */
public class ReplacePanel extends JPanel {
    // instance variables
    private int modType;
    private GridBagConstraints rpc;
    // note
    private ReplaceNote ReplaceNote;
    // instrument
    private ReplaceInstrument ReplaceInstrument;
    // volume
    private ReplaceVolume ReplaceVolume;
    // effect 1
    private ReplaceEffect replaceEffect1;
    // effect 2
    private ReplaceEffect ReplaceEffect2;
    
    // constructor
    public ReplacePanel(byte modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    public ReplaceNote getReplaceNote() {
        return ReplaceNote;
    }

    public ReplaceInstrument getReplaceInstrument() {
        return ReplaceInstrument;
    }

    public ReplaceVolume getReplaceVolume() {
        return ReplaceVolume;
    }

    public ReplaceEffect getReplaceEffect1() {
        return replaceEffect1;
    }

    public ReplaceEffect getReplaceEffect2() {
        return ReplaceEffect2;
    }
    
    public void init() {
        this.setLayout(new GridBagLayout());
        rpc = new GridBagConstraints();
        rpc.fill = GridBagConstraints.BOTH;  
        rpc.anchor = GridBagConstraints.NORTHWEST;
        
        // find note
        ReplaceNote = new ReplaceNote(this.modType);
        
        rpc.weightx = 1;
        rpc.weighty = 0;
        rpc.gridwidth = GridBagConstraints.REMAINDER;
        rpc.gridheight = 1;
        rpc.gridx = 0;
        rpc.gridy = 0;
        
        add(ReplaceNote, rpc);
        
        // find instrument
        ReplaceInstrument = new ReplaceInstrument(modType);
        
        rpc.gridy++;
        
        add(ReplaceInstrument, rpc);
        
        // find volume effect
        ReplaceVolume = new ReplaceVolume(modType);
        
        rpc.gridy++;
        
        add(ReplaceVolume, rpc);
        
        // find effect 1
        replaceEffect1 = new ReplaceEffect(modType);
        
        rpc.gridy++;
        rpc.weighty = 1;
        rpc.gridheight = GridBagConstraints.REMAINDER;
        
        add(replaceEffect1, rpc);
    }
}
