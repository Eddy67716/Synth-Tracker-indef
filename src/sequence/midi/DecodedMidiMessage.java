/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi;

import static javax.sound.midi.MetaMessage.META;

/**
 *
 * @author Edward Jenkins
 */
public class DecodedMidiMessage {
    
    // constants
    // status msnybbles
    public static final byte NOTE_OFF = 0x8;
    public static final byte NOTE_ON = 0x9;
    public static final byte POLYPHONIC_KEY_PRESSURE = 0xa;
    public static final byte CONTROL_CHANGE = 0xb;
    public static final byte PROGRAM_CHANGE = 0xc;
    public static final byte CHANNEL_PRESSURE = 0xd;
    public static final byte PITCH_BEND_CHAGE = 0xe;
    public static final byte SYSTEM_EXCLUSIVE = 0xf;

    // instance varialbes
    private int track;
    private byte note;
    private byte velocity;
    private byte channel;
    private byte status;
    private boolean noteOnAsOff;
    private boolean meta;
    private boolean systemExclusive;
    private MetaData metaData;
    private SystemExclusiveMessage systemExclusiveMessage;

    public DecodedMidiMessage(int track, byte[] midiMessage) {
        this.track = track;
        status = (byte) ((midiMessage[0] >>> 4) & 0x0f);
        if (midiMessage[0] == (byte)META) {
            meta = true;
            metaData = new MetaData(midiMessage);
        } else if ((status) == SYSTEM_EXCLUSIVE
                && (midiMessage[0] & 0xf) == 0) {
            systemExclusive = true;
            systemExclusiveMessage = new SystemExclusiveMessage(midiMessage);
        } else {
            channel = (byte) (midiMessage[0] & 0x0f);

            note = midiMessage[1];

            if (midiMessage.length == 3) {
                velocity = midiMessage[2];
                // change a note on to a note off if velocity is zero
                if (status == NOTE_ON && velocity == 0) {
                    status = NOTE_OFF;
                    velocity = 64;
                    noteOnAsOff = true;
                }
            }
        }
    }

    public DecodedMidiMessage(byte[] midiMessage) {
        this(-1, midiMessage);
    }

    // gettes
    public int getTrack() {
        return track;
    }

    public byte getNote() {
        return note;
    }

    public byte getVelocity() {
        return velocity;
    }

    public byte getChannel() {
        return channel;
    }

    public byte getStatus() {
        return status;
    }
    
    public boolean isNoteOnAsOff() {
        return noteOnAsOff;
    }

    public boolean isMeta() {
        return meta;
    }
    
    public boolean isSystemExclusive() {
        return systemExclusive;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public SystemExclusiveMessage getSystemExclusiveMessage() {
        return systemExclusiveMessage;
    }
}
