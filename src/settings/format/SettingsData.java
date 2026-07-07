/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package settings.format;

import file.ISaveableFile;
import io.Reader;
import io.Writer;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author eddy6
 */
public class SettingsData implements ISaveableFile {
    
    // constancs
    public static final byte KNOWN_LENGTH = 2;
    // instance variables
    private short version;
    private UiSettingsData uiSettingsData;
    private PatternSettingsData patternSettingsData;
    private SoundSettingsData soundSettingsData;
    private MidiSettingsData midiSettingsData;
    
    public SettingsData() {
        uiSettingsData = new UiSettingsData();
        patternSettingsData = new PatternSettingsData();
        soundSettingsData = new SoundSettingsData();
        midiSettingsData = new MidiSettingsData();
    }

    public short getVersion() {
        return version;
    }

    public UiSettingsData getUiSettingsData() {
        return uiSettingsData;
    }

    public PatternSettingsData getPatternSettingsData() {
        return patternSettingsData;
    }

    public SoundSettingsData getSoundSettingsData() {
        return soundSettingsData;
    }

    public MidiSettingsData getMidiSettingsData() {
        return midiSettingsData;
    }
    
    

    @Override
    public boolean write() throws IOException {
        Writer w = new Writer("./settings.bin");
        
        uiSettingsData.write(w);
        
        patternSettingsData.write(w);
        
        soundSettingsData.write(w);

        midiSettingsData.write(w);
        
        w.close();
        
        return true;
    }

    @Override
    public boolean read() throws IOException, FileNotFoundException, IllegalArgumentException {
        Reader r = new Reader("./settings.bin");
        
        uiSettingsData.read(r);
        
        patternSettingsData.read(r);
        
        soundSettingsData.read(r);

        midiSettingsData.read(r);
        
        r.close();
        
        return true;
    }

    @Override
    public int length() {
        int length = KNOWN_LENGTH + uiSettingsData.length() 
                + patternSettingsData.length() + soundSettingsData.length()
                + midiSettingsData.length();
        
        return length;
    }
    
}
