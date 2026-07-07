/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.effects;

import java.nio.ByteBuffer;

/**
 *
 * @author eddy6
 */
public class SoundByteBuffer {
    
    private byte bitrate;
    private boolean floating;
    private ByteBuffer buffer;
    
    public SoundByteBuffer(byte bitrate, boolean floating) {

        this.bitrate = bitrate;
        this.floating = floating;
        
        // switch on bitrate
        switch (bitrate) {
            case 64:
                buffer = ByteBuffer.allocate(8);
                buffer.asDoubleBuffer();
                break;
            case 32:
                buffer = ByteBuffer.allocate(4);
                if (floating) {
                    buffer.asFloatBuffer();
                } else {
                    buffer.asIntBuffer();
                }
                break;
            case 24:
                buffer = ByteBuffer.allocate(4);
                buffer.asIntBuffer();
                break;
            case 16:
                buffer = ByteBuffer.allocate(2);
                buffer.asShortBuffer();
                break;
            case 8:
            default:
                break;
        }
    }

    public byte[] convertToBytes(byte[] output, double point) {

        buffer.rewind();
        
        // switch on bitrate
        switch (bitrate) {
            case 64:
                buffer.putDouble(point);
                System.arraycopy(buffer.array(), 0, output, 0, 8);
                break;
            case 32:
                if (floating) {
                    buffer.putFloat((float) point);
                    System.arraycopy(buffer.array(), 0, output, 0, 4);
                } else {
                    buffer.putInt((int) point);
                    System.arraycopy(buffer.array(), 0, output, 0, 4);
                }
                break;
            case 24:
                buffer.putInt((int) point);
                System.arraycopy(buffer.array(), 1, output, 0, 3);
                break;
            case 16:
                buffer.putShort((short) point);
                System.arraycopy(buffer.array(), 0, output, 0, 2);
                break;
            case 8:
                output[0] = (byte) point;
                break;
            default:
                output[0] = (byte) 0;
                break;
        }
        return output;
    }
}
