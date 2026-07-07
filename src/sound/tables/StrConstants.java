/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.tables;

/**
 *
 * @author Edward Jenkins
 */
public class StrConstants {
    
    // sample rate
    public static final int SAMPLE_RATE = 44100;
    
    // nyquist frequency
    public static final int NYQUIST_FREQUENCY = SAMPLE_RATE / 2;
    
    // sample rate ratio
    public static final double INVERSE_SAMPLE_RATE = 1d / SAMPLE_RATE;
    
    // sample rate ratio
    public static final double MILLISECOND_SAMPLES = (double)SAMPLE_RATE 
            / 1000;
    
    // midi velocity exponent
    public static final double STR_VELOCITY_EXPONENT = 1.25;
    
    // int mixed
    public static final boolean INT_MIXED = false;
    
    // amplitude clamp
    public static final int MAX_INT_VOLUME = 0x10000;
    
    // shift amount
    public static final byte INT_VOLUME_SHIFT = 16;
    
    // shift amount
    public static final byte INT_MIX_SHIFT = 7;
    
    // tick rate
    private static int ticksPerSecond;
    
    // tick ratio
    private static double inverseTicksPerSecond;
    
    private static double mixVolume = 0.33;

    public static int getTicksPerSecond() {
        return ticksPerSecond;
    }

    public static double getInverseTicksPerSecond() {
        return inverseTicksPerSecond;
    }

    public static double getMixVolume() {
        return mixVolume;
    }

    public static void setTicksPerSecond(int ticksPerSecond) {
        StrConstants.ticksPerSecond = ticksPerSecond;
        StrConstants.inverseTicksPerSecond = 1d / ticksPerSecond;
    }

    public static void setMixVolume(double mixVolume) {
        StrConstants.mixVolume = mixVolume;
    }
}
