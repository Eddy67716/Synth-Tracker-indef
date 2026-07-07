/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package settings.format;

import file.ISaveableData;
import io.IReadable;
import io.IWritable;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author eddy6
 */
public class SoundSettingsData implements ISaveableData {
    
    // constants
    public static final byte LENGTH = 6;
    public static final int[] DEF_SAMPLE_RATES = {
        8000, 11025, 16000, 22050, 32000, 44100, 
        48000, 88200, 96000, 192000, 384000
    };
    public static final byte SIGNED_8_BIT = 0;
    public static final byte SIGNED_16_BIT = 1;
    public static final byte SIGNED_24_BIT = 2;
    public static final byte SIGNED_32_BIT = 3;
    public static final byte UNSIGNED_8_BIT = 4;
    public static final byte UNSIGNED_16_BIT = 5;
    public static final byte FLOATING_32_BIT = 6;
    public static final byte FLOATING_64_BIT = 7;
    // flags
    public static final byte FLOAT_MIX_FLAG = 0x1;
    public static final byte MONO_FLAG = 0x2;
    
    // instance variables
    private int sampleRate;
    private byte bitrate;
    private boolean mixAsFloats;
    private boolean mono;
    
    // constructor
    public SoundSettingsData() {
        sampleRate = DEF_SAMPLE_RATES[5];
        bitrate = SIGNED_16_BIT;
        mixAsFloats = true;
        mono = false;
    }

    @Override
    public boolean write(IWritable w) throws IOException {
        
        // write samplerate
        w.writeInt(sampleRate);
        
        // write bitrate
        w.writeByte(bitrate);
        
        byte flags = 0;
        
        // use float mixing
        flags |=  mixAsFloats ? FLOAT_MIX_FLAG : 0;
        
        // use mono
        flags |=  mono ? MONO_FLAG : 0;
        
        w.writeByte(flags);
        
        return true;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {
        
        // read sample rate
        sampleRate = r.getInt();
        
        // read bitrate
        bitrate = r.getByte();
        
        byte flags = r.getByte();
        
        // use float mixer
        mixAsFloats = (flags & FLOAT_MIX_FLAG) == FLOAT_MIX_FLAG;
        
        // use mono
        mono = (flags & MONO_FLAG) == MONO_FLAG; 
        
        return true;
    }

    @Override
    public int length() {
        return LENGTH;
    }
}
