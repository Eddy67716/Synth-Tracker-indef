/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi.parameters;

import sequence.midi.handlers.ChannelPressureHandler;
import sequence.midi.handlers.PitchBendHandler;

/**
 *
 * @author eddy6
 */
public abstract class MidiParameters {
    
    // instance variables
    // program information
    private byte program;
    // channel pressure
    private ChannelPressureHandler channelPressureHandler;
    private PitchBendHandler pitchBendHandler;
    // final pitch bend amount in cents
    private double centBendValue;
    
    // constructor
    public MidiParameters(byte program, boolean midi2) {
        this.program = program;
        channelPressureHandler = new ChannelPressureHandler(midi2);
    }
    
    // getters
    public byte getProgram() {
        return program;
    }

    public ChannelPressureHandler getChannelPressureHandler() {
        return channelPressureHandler;
    }
    
    public abstract MidiControl getMidiControl();
    
    public abstract RegisteredParameters getRegisteredParameters();
    
    public abstract NonRegisteredParameters getNonRegisteredParameters();

    public PitchBendHandler getPitchBendHandler() {
        return pitchBendHandler;
    }    
}
