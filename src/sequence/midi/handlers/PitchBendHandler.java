/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi.handlers;

import static sequence.midi.MidiConstants.MIDI_MASK;

/**
 *
 * @author eddy6
 */
public class PitchBendHandler {
    
    // constants
    public static final short MIDI_1_PITCH_BEND_BIAS = 8192;
    public static final long MIDI_2_PITCH_BEND_BIAS = 2147483648l;
    public static final short INITIAL_PITCH_BEND_OFFSET = 200;
    public static final double MIDI_1_BEND_RATIO = 1.0 / 8191;
    public static final double MIDI_2_BEND_RATIO = 1.0 / 2147483647;
    
    // instance variables
    private boolean midi2;
    private long pitchBend;
    private long unbiasedPitchBend;
    // holds current pitch bend value in cents
    private double centBendValue;
    // pitch bend offset in cents
    private short pitchBendOffset;
    private boolean pitchBendUpdated;
    
    public PitchBendHandler(boolean midi2) {
        this.midi2 = midi2;
        pitchBendOffset = INITIAL_PITCH_BEND_OFFSET;
        if (midi2) {
            pitchBend = MIDI_2_PITCH_BEND_BIAS;
        } else {
            pitchBend = MIDI_1_PITCH_BEND_BIAS;
        }
        unbiasedPitchBend = 0;
    }

    // getter
    public long getPitchBend() {
        return pitchBend;
    }
    
    public double getCentBendValue() {
        return centBendValue;
    }

    public boolean isPitchBendUpdated() {
        return pitchBendUpdated;
    }

    public void setPitchBend(long pitchBend) {
        this.pitchBend = pitchBend;
        updatePitchBend();
    }
    
    public void updatePitchBend() {
        double oldCentBendValue = centBendValue;
        if (midi2) {
            unbiasedPitchBend = pitchBend - MIDI_2_PITCH_BEND_BIAS;

            centBendValue = (double) unbiasedPitchBend * MIDI_2_BEND_RATIO
                    * pitchBendOffset;
        } else {
            unbiasedPitchBend = pitchBend - MIDI_1_PITCH_BEND_BIAS;

            centBendValue = (double) unbiasedPitchBend * MIDI_1_BEND_RATIO
                    * pitchBendOffset;
        }
        pitchBendUpdated = (oldCentBendValue != centBendValue);
    }
    
    public void updateBendRange(short bendRange) {
        // LSB value is cents
        pitchBendOffset = (short) (bendRange & MIDI_MASK);

        // MSB value is tones
        pitchBendOffset += (short) ((bendRange >>> 7)
                & MIDI_MASK) * 100;
        updatePitchBend();
    }
    
    public void notifyEffectsUpdated() {
        pitchBendUpdated = false;
    }
}
