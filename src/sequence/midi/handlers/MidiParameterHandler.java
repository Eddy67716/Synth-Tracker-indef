/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi.handlers;

import static sequence.midi.MidiConstants.MIDI_1_CONTROL_FRACTIONAL;
import static sequence.midi.MidiConstants.MIDI_2_CONTROL_FRACTIONAL;
import sequence.midi.parameters.Midi1Control;
import sequence.midi.parameters.Midi1NonRegisteredParameters;
import sequence.midi.parameters.Midi1RegisteredParameters;
import sequence.midi.parameters.Midi2Control;
import sequence.midi.parameters.Midi2NonRegisteredParameters;
import sequence.midi.parameters.Midi2RegisteredParameters;
import sequence.midi.parameters.MidiControl;
import sequence.midi.parameters.NonRegisteredParameters;
import sequence.midi.parameters.RegisteredParameters;

/**
 *
 * @author eddy6
 */
public class MidiParameterHandler {
    
    // instance variables
    private boolean midi2;
    private PitchBendHandler pitchBendHandler;
    private ChannelPressureHandler channelPressureHandler;
    private MidiControl midiControl;
    private RegisteredParameters registeredParameters;
    private NonRegisteredParameters nonRegisteredParameters;
    // combines control change 1, RPN 4, and Channel pressure mod depth
    private double modulatorDepth;
    private boolean modulatorDepthUpdated;
    
    public MidiParameterHandler(boolean midi2, byte channel, 
            boolean rhythmControl, short bankSelect) {
        this.midi2 = midi2;
        pitchBendHandler = new PitchBendHandler(midi2);
        channelPressureHandler = new ChannelPressureHandler(midi2);
        if (midi2) {
            midiControl = new Midi2Control(channel, rhythmControl);
            registeredParameters = new Midi2RegisteredParameters();
            nonRegisteredParameters = new Midi2NonRegisteredParameters();
        } else {
            midiControl = new Midi1Control(channel, rhythmControl, bankSelect);
            registeredParameters = new Midi1RegisteredParameters();
            nonRegisteredParameters = new Midi1NonRegisteredParameters();
        }
    }
    
    public MidiParameterHandler(boolean rhythmChannel) {
        this(false, (rhythmChannel) ? (byte)9 : (byte)0, rhythmChannel, 
                (short)0);
    }
    
    // getters
    public boolean isMidi2() {
        return midi2;
    }

    public PitchBendHandler getPitchBendHandler() {
        return pitchBendHandler;
    }

    public ChannelPressureHandler getChannelPressureHandler() {
        return channelPressureHandler;
    }

    public MidiControl getMidiControl() {
        return midiControl;
    }

    public RegisteredParameters getRegisteredParameters() {
        return registeredParameters;
    }

    public NonRegisteredParameters getNonRegisteredParameters() {
        return nonRegisteredParameters;
    }

    public double getModulatorDepth() {
        return modulatorDepth;
    }
    
    // setters
    public void setMidiControl(MidiControl midiControl) {
        this.midiControl = midiControl;
    }
    
    public void updateModDepth() {
        double oldModulatorDepth = modulatorDepth;
        if (midiControl != null) {
            switch (midiControl) {
                case Midi2Control m2c -> {

                    modulatorDepth = (double) m2c.getModulation()
                            * MIDI_2_CONTROL_FRACTIONAL;
                }
                case Midi1Control m1c -> {
                    modulatorDepth = (double) m1c.getModulation()
                            * MIDI_1_CONTROL_FRACTIONAL;
                }
                default -> {
                }
            }
        } else {
            modulatorDepth = 0;
        }
        if (registeredParameters.getModulatorDepthEventAccumulator() != 128) {
            modulatorDepth *= ((double)registeredParameters
                    .getModulatorDepthEventAccumulator() / 128);
        }
        modulatorDepthUpdated = (oldModulatorDepth != modulatorDepth);
    }
    
    public void updatePitchBendValue() {

        short pitchBendEventAccumulator
                = registeredParameters.getPitchBendEventAccumulator();

        // update range
        pitchBendHandler.updateBendRange(pitchBendEventAccumulator);
    }
    
    public void notifyEffectsUpdated() {
        pitchBendHandler.notifyEffectsUpdated();
        channelPressureHandler.notifyEffectsUpdated();
        if (midiControl != null) {
            midiControl.notifyControlUpdated();
        }
        registeredParameters.notifyEffectsUpdated();
        nonRegisteredParameters.notifyEffectsUpdated();
        modulatorDepthUpdated = false;
    }
}
