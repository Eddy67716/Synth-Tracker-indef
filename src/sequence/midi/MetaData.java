/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi;

/**
 *
 * @author eddy6
 */
public class MetaData {
    
    // constants
    // meta type
    public static final byte META_SEQUENCE = 0x00;
    public static final byte META_TEXT = 0x01;
    public static final byte META_COPYRIGHT = 0x02;
    public static final byte META_TRACK_NAME = 0x03;
    public static final byte META_INSTRUMENT_NAME = 0x04;
    public static final byte META_LYRIC = 0x05;
    public static final byte META_MARKER = 0x06;
    public static final byte META_CUE_POINT = 0x07;
    public static final byte META_DEVICE_NAME = 0x09;
    public static final byte META_CHANNEL_PREFIX = 0x20;
    public static final byte META_PORT = 0x21;
    public static final byte META_EOT = 0x2F;
    public static final byte META_TEMPO = 0x51;
    public static final byte META_SMPTE_OFFSET = 0x54;
    public static final byte META_TIME_SIGNATURE = 0x58;
    public static final byte META_KEY_SIGNATURE = 0x59;
    
    // instance variabesl
    private byte metaType;
    private short metaLength;
    private byte[] metaData;

    public MetaData(byte[] midiMessage) {
        metaType = midiMessage[1];
        metaLength = (short) (midiMessage[2] & 0xff);
        metaData = new byte[midiMessage.length - 3];
        System.arraycopy(midiMessage, 3, metaData, 0, metaData.length);
    }

    public byte getMetaType() {
        return metaType;
    }

    public short getMetaLength() {
        return metaLength;
    }

    public byte[] getMetaData() {
        return metaData;
    }
    
    public String getMetaString() {

        // char for conversion
        char aChar;

        // stringBuilder
        StringBuilder byteStringBuilder = new StringBuilder();

        // loop through bytes
        for (byte aByte : metaData) {

            // convert byte to char
            aChar = (char) (aByte & 0xff);

            // append aChar to the string builder
            byteStringBuilder.append(aChar);
        }

        // return string
        return byteStringBuilder.toString();
    }
}
