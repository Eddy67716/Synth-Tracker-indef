/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package settings.format;

import file.ISaveableData;
import io.IReadable;
import io.IWritable;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author eddy6
 */
public class UiSettingsData implements ISaveableData {

    // constance
    public static final byte LENGTH = 6;
    public static final byte LIGHT_MODE = 0;
    public static final byte DARK_MODE = 1;
    public static final byte CUSTOM_MODE = 2;
    public static final Color LIGHT_MODE_COLOUR = Color.WHITE;
    public static final Color DARK_MODE_COLOUR = Color.BLACK;
    public static final Color LIGHT_TEXT_COLOUR = Color.BLACK;
    public static final Color DARK_TEXT_COLOUR = Color.WHITE;
    // custom flags
    public static final byte NOTE_COLOUR_FLAG = 0x1;
    public static final byte INSTR_COLOUR_FLAG = 0x2;
    public static final byte VOL_COLOUR_FLAG = 0x4;
    public static final byte PAN_COLOUR_FLAG = 0x8;
    public static final byte EFFECT_COLOUR_FLAG = 0x10;
    public static final byte MOD_OSC_COLOUR_FLAG = 0x20;
    public static final short OSCILOSCOPE_COLOUR_FLAG = 0x80;
    public static final Color DEF_NOTE_COLOUR = new Color(0xbb00ff);
    public static final Color DEF_INST_COLOUR = new Color(0x000088);
    public static final Color DEF_VOLUME_COLOUR = new Color(0x008800);
    public static final Color DEF_PAN_COLOUR = new Color(0x880088);
    public static final Color DEF_EFFECT_COLOUR = new Color(0x008800);
    public static final Color DEF_MOD_OSC_EFF_COLOUR = new Color(0x0000cc);
    public static final Color DEF_OSCILOSCOPE_COLOUR = Color.GREEN;

    // instance variables
    private byte displaySchemeMode;
    private Color mainColour;
    private Color textColour;
    private Color highlightColour;
    private boolean customNoteColourUsed;
    private boolean customInstColourUsed;
    private boolean customVolColourUsed;
    private boolean customPanColourUsed;
    private boolean customEffectColour;
    private boolean customModOscEffectColour;
    private boolean customOscilColourUsed;
    private Color noteColour;
    private Color instrumentColour;
    private Color volumeColour;
    private Color panEffectColour;
    private Color defEffectColour;
    private Color modOscEffectColour;
    private Color osciliscopeColour;

    public UiSettingsData() {
        displaySchemeMode = LIGHT_MODE;
        mainColour = LIGHT_MODE_COLOUR;
        textColour = LIGHT_TEXT_COLOUR;
        highlightColour = Color.BLUE;
        noteColour = DEF_NOTE_COLOUR;
        instrumentColour = DEF_INST_COLOUR;
        volumeColour = DEF_VOLUME_COLOUR;
        panEffectColour = DEF_PAN_COLOUR;
        defEffectColour = DEF_EFFECT_COLOUR;
        modOscEffectColour = DEF_MOD_OSC_EFF_COLOUR;
        osciliscopeColour = DEF_OSCILOSCOPE_COLOUR;
    }
    
    // getters
    public Color getMainColour() {
        return mainColour;
    }

    public Color getTextColour() {
        return textColour;
    }

    public Color getHighlightColour() {
        return highlightColour;
    }

    public Color getNoteColour() {
        return noteColour;
    }

    public Color getInstrumentColour() {
        return instrumentColour;
    }

    public Color getVolumeColour() {
        return volumeColour;
    }

    public Color getPanEffectColour() {
        return panEffectColour;
    }

    public Color getDefEffectColour() {
        return defEffectColour;
    }

    public Color getModOscEffectColour() {
        return modOscEffectColour;
    }

    public Color getOsciliscopeColour() {
        return osciliscopeColour;
    }

    @Override
    public boolean write(IWritable w) throws IOException {

        // display scheme mode
        w.writeByte(displaySchemeMode);

        if (displaySchemeMode >= 3) {
            // write main colour
            w.writeIntAsTwentyFourBit(mainColour.getRGB() & 0xffffff);

            // write text colour
            w.writeIntAsTwentyFourBit(textColour.getRGB() & 0xffffff);
        }

        // write highlight colour
        w.writeIntAsTwentyFourBit(highlightColour.getRGB() & 0xffffff);
        
        // write flags
        short flags = 0;
        
        // custom note colour
        flags |= customNoteColourUsed ? NOTE_COLOUR_FLAG : 0;
        
        // custom intstrument colour
        flags |= customInstColourUsed ? INSTR_COLOUR_FLAG : 0;
        
        // custom volume colour
        flags |= customVolColourUsed ? VOL_COLOUR_FLAG : 0;
        
        // custom pan colour
        flags |= customPanColourUsed ? PAN_COLOUR_FLAG : 0;
        
        // custom effect colour
        flags |= customEffectColour ? EFFECT_COLOUR_FLAG : 0;
        
        // custom oscilator modulator colour
        flags |= customModOscEffectColour ? INSTR_COLOUR_FLAG : 0;
        
        // custom oscilloscope colour
        flags |= customOscilColourUsed ? OSCILOSCOPE_COLOUR_FLAG : 0;
        
        // write the flags
        w.writeShort(flags);
        
        if (customNoteColourUsed) {
            // write note colour
            w.writeIntAsTwentyFourBit(noteColour.getRGB() & 0xffffff);
        }
        
        if (customInstColourUsed) {
            // write instrument colour
            w.writeIntAsTwentyFourBit(instrumentColour.getRGB() & 0xffffff);
        }
        
        if (customVolColourUsed) {
            // write volume colour
            w.writeIntAsTwentyFourBit(volumeColour.getRGB() & 0xffffff);
        }
        
        if (customPanColourUsed) {
            // write pan colour
            w.writeIntAsTwentyFourBit(panEffectColour.getRGB() & 0xffffff);
        }
        
        if (customEffectColour) {
            // write default effect colour
            w.writeIntAsTwentyFourBit(defEffectColour.getRGB() & 0xffffff);
        }
        
        if (customModOscEffectColour) {
            // write oscilator modulator effect colour
            w.writeIntAsTwentyFourBit(modOscEffectColour.getRGB() & 0xffffff);
        }
        
        if (customOscilColourUsed) {
            // write custom osciloscope colour
            w.writeIntAsTwentyFourBit(osciliscopeColour.getRGB() & 0xffffff);
        }

        return true;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException,
            IllegalArgumentException {

        // read display scheme mode
        displaySchemeMode = r.getByte();

        // read main colour if display scheme is 2
        switch (displaySchemeMode) {
            case 0:
                mainColour = LIGHT_MODE_COLOUR;
                textColour = LIGHT_TEXT_COLOUR;
                break;
            case 1:
                mainColour = DARK_MODE_COLOUR;
                textColour = DARK_TEXT_COLOUR;
                break;
            case 2:
                mainColour = new Color(r.get24BitInt());
                textColour = new Color(r.get24BitInt());
                break;
        }
        
        highlightColour = new Color(r.get24BitInt());
        
        // read flags
        short flags = r.getShort();
        
        // custom note colour
        customNoteColourUsed = (flags & NOTE_COLOUR_FLAG) ==  NOTE_COLOUR_FLAG;
        
        // custom intstrument colour
        customInstColourUsed = (flags & INSTR_COLOUR_FLAG) 
                ==  INSTR_COLOUR_FLAG;
        
        // custom volume colour
        customVolColourUsed = (flags & VOL_COLOUR_FLAG) ==  VOL_COLOUR_FLAG;
        
        // custom pan colour
        customPanColourUsed = (flags & PAN_COLOUR_FLAG) ==  PAN_COLOUR_FLAG;
        
        // custom effect colour
        customEffectColour = (flags & EFFECT_COLOUR_FLAG) 
                ==  EFFECT_COLOUR_FLAG;
        
        // custom oscilator modulator colour
        customModOscEffectColour = (flags & INSTR_COLOUR_FLAG) 
                ==  INSTR_COLOUR_FLAG;
        
        // custom oscilloscope colour
        customOscilColourUsed = (flags & OSCILOSCOPE_COLOUR_FLAG) 
                ==  OSCILOSCOPE_COLOUR_FLAG;
        
        if (customNoteColourUsed) {
            // read note colour
            noteColour = new Color(r.get24BitInt());
        }
        
        if (customInstColourUsed) {
            // read instrument colour
            instrumentColour = new Color(r.get24BitInt());
        }
        
        if (customVolColourUsed) {
            // read volume colour
            volumeColour = new Color(r.get24BitInt());
        }
        
        if (customPanColourUsed) {
            // read pan colour
            panEffectColour = new Color(r.get24BitInt());
        }
        
        if (customEffectColour) {
            // read default effect colour
            defEffectColour = new Color(r.get24BitInt());
        }
        
        if (customModOscEffectColour) {
            // read oscilator modulator effect colour
            modOscEffectColour = new Color(r.get24BitInt());
        }
        
        if (customOscilColourUsed) {
            // read custom osciloscope colour
            osciliscopeColour = new Color(r.get24BitInt());
        }

        return true;
    }

    @Override
    public int length() {
        int length = LENGTH;
        
        if (displaySchemeMode > 2) {
            length += 6;
        }
        
        if (customNoteColourUsed) {
            length += 3;
        }
        
        if (customInstColourUsed) {
            length += 3;
        }
        
        if (customVolColourUsed) {
            length += 3;
        }
        
        if (customPanColourUsed) {
            length += 3;
        }
        
        if (customEffectColour) {
            length += 3;
        }
        
        if (customModOscEffectColour) {
            length += 3;
        }
        
        if (customOscilColourUsed) {
            length += 3;
        }
        
        return length;
    }
}
