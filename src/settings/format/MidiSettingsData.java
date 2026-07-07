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
public class MidiSettingsData implements ISaveableData {
    
    // constants
    public static final byte LENGTH = 4;
    
    // instance variables
    private int midiDelayInMilliseconds;

    @Override
    public boolean write(IWritable w) throws IOException {
        w.writeInt(midiDelayInMilliseconds);
        
        return true;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, 
            IllegalArgumentException {
        midiDelayInMilliseconds = r.getInt();
        
        return true;
    }

    @Override
    public int length() {
        return LENGTH;
    }
}
