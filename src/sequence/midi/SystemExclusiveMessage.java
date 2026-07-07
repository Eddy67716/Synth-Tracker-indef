/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi;

/**
 *
 * @author eddy6
 */
public class SystemExclusiveMessage {
    
    // constants
    public static final byte NON_REAL_TIME_ID = 0x7e;
    public static final byte REAL_TIME_ID = 0x7f;
    public static final byte ALL_DEVICES = 0x7f;
    // non real time
    // general MIDI
    public static final byte GENERAL_MIDI = 9;
    // sub 2 for general midi
    public static final byte GENERAL_MIDI_1_ON = 1;
    public static final byte GENERAL_MIDI_OFF = 2;
    public static final byte GENERAL_MIDI_2_ON = 3;
    // DLS
    public static final byte DOWNLOADABLE_SOUND = 10;
    // sub 2 for DLS
    public static final byte DLS_ON = 1;
    public static final byte DLS_OFF = 2;
    public static final byte DLS_VOICE_ALLOCATION_ON = 3;
    public static final byte DLS_VOICE_ALLOCATION_OFF = 3;
    // real time
    public static final byte MIDI_TIME_CODE = 1;
    // sub 2 for midi time code
    public static final byte MTC_FULL_MESSAGE = 1;
    public static final byte MTC_USER_BITS = 2;
    // MIDI show controller
    public static final byte MIDI_SHOW_CONTROL = 2;
    // sub 2 for midi show control
    public static final byte MSC_EXTENSIONS = 1;
    public static final byte MSC_COMMANDS = 2;
    // Notation info
    public static final byte NOTATION_INFO = 3;
    // Device control
    public static final byte DEVICE_CONTROL = 4;
    // sub 2 for device control
    public static final byte DC_MASTER_VOLUME = 1;
    public static final byte DC_BALANCE = 2;
    public static final byte DC_MASTER_COARSE_TUNING = 3;
    public static final byte DC_MASTER_FINE_TUNING = 4;
    public static final byte DC_GLOBAL_PARAMETER = 5;
    // control destination
    public static final byte CONTROL_DESTINATION_SETTING = 9;
    // sub to for control destination
    public static final byte CT_CHANNEL_PRESSURE = 1;
    public static final byte CT_POLY_KEY_PRESSURE = 2;
    public static final byte CT_CONTROL_CHANGE = 3;
    // parameters for control destination
    public static final byte PITCH_CONTROL = 0;
    public static final byte CUTOFF_CONTROL = 1;
    public static final byte AMPLITUDE_CONTROL = 2;
    public static final byte LFO_PITCH_DEPTH = 3;
    public static final byte LFO_CUTOFF_DEPTH = 4;
    public static final byte LFO_AMPLITUDE_DEPTH = 5;
    
    // instance variables
    private int idNumber;
    private byte deviceId;
    private byte subId1;
    private byte subId2;
    private byte[] systemExlusiveData;
    
    public SystemExclusiveMessage(byte[] midiMessage) {
        byte index = 1;
        idNumber = midiMessage[index];
        index++;
        deviceId = midiMessage[index];
        index++;
        subId1 = midiMessage[index];
        index++;
        subId2 = midiMessage[index];
        index++;
        systemExlusiveData = new byte[midiMessage.length - index];
        System.arraycopy(midiMessage, index, systemExlusiveData, 0, 
                systemExlusiveData.length);
    }

    public int getIdNumber() {
        return idNumber;
    }

    public byte getDeviceId() {
        return deviceId;
    }

    public byte getSubId1() {
        return subId1;
    }

    public byte getSubId2() {
        return subId2;
    }

    public byte[] getSystemExlusiveData() {
        return systemExlusiveData;
    }
}
