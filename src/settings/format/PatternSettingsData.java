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
public class PatternSettingsData implements ISaveableData {

    // constants
    public static final byte LENGTH = 7;
    public static final byte REFER_TO_COMMON = -1;
    public static final byte AMIGA_NOTATION = 0;
    public static final byte YAMAHA_NOTATION = 1;
    public static final byte SIENTIFIC_NOTATION = 2;
    public static final byte TRACKER_NOTATION = 3;
    // flags
    public static final byte AMIGA_VOLUMES_FLAG = 0x1;

    // instance variables
    private byte commonNotation;
    private byte amigaNotation;
    private byte s3mNotation;
    private byte fastTrackerNotation;
    private byte impulseTrackerNotation;
    private byte synthTrackerNotation;
    private boolean hidingAmigaVolumes;

    @Override
    public boolean write(IWritable w) throws IOException {

        // write common notation type
        w.writeByte(commonNotation);

        // write amiga notation type
        w.writeByte(amigaNotation);

        // write scream tracker notation type
        w.writeByte(s3mNotation);

        // write fast tracker notation type
        w.writeByte(fastTrackerNotation);

        // write impulste tracker notation type
        w.writeByte(impulseTrackerNotation);

        // write synth tracker notation type
        w.writeByte(synthTrackerNotation);

        // flags
        byte flags = 0;

        flags |= (hidingAmigaVolumes) ? AMIGA_VOLUMES_FLAG : 0;

        return true;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {

        // read common notation type
        commonNotation = r.getByte();

        // read amiga notation type
        amigaNotation = r.getByte();

        // read scream tracker notation type
        s3mNotation = r.getByte();

        // read fast tracker notation type
        fastTrackerNotation = r.getByte();

        // read impulste tracker notation type
        impulseTrackerNotation = r.getByte();

        // read synth tracker notation type
        synthTrackerNotation = r.getByte();
        
        byte flags = r.getByte();
        
        hidingAmigaVolumes = (flags & AMIGA_VOLUMES_FLAG) == AMIGA_VOLUMES_FLAG;
        
        return true;
    }

    @Override
    public int length() {
        return LENGTH;
    }
}
