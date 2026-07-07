/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi;

/**
 *
 * @author eddy6
 */
public class MidiConstants {
    
    // General midi 2 melodic bank
    public static final byte GEN_2_MSB = 121;
    // General midi 2 percussion bank
    public static final byte GEN_2_DRUM_MSB = 120;
    public static final byte MIDI_MASK = 0b1111111;
    public static final byte MIDI_SHIFT = (byte) 7;
    public static final byte MIDI_2_SHIFT = (byte) 18;
    public static final short INITIAL_PITCH_BEND_OFFSET = 200;
    // bias values
    public static final byte MIDI_7_BIT_BIAS = 64;
    public static final short MIDI_14_BIT_BIAS = 8192;
    public static final long MIDI_2_BIAS = 2147483648l;
    // fractional for midi volumes and controls
    public static final double LFO_TONE_FRACTIONAL = 1.0 / 128;
    public static final double MIDI_1_VELOCITY_FRACTIONAL = 1.0 / 127;
    public static final double MIDI_1_EXTENDED_VEL_FRACTIONAL = 1.0 / 16383;
    public static final double MIDI_2_VELOCITY_FRACTIONAL = 1.0 / 65535;
    public static final double MIDI_1_CONTROL_FRACTIONAL 
            = MIDI_1_EXTENDED_VEL_FRACTIONAL;
    public static final double MIDI_2_CONTROL_FRACTIONAL = 1.0 / 4294967295l;
    public static final double MIDI_1_SOUND_CONTROL_FRACTIONAL = 1.0 / 64;
}
