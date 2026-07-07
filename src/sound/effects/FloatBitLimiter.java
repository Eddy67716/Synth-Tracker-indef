/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound.effects;

/**
 *
 * @author Edward Jenkins
 */
public class FloatBitLimiter {

    // constants
    public static final double THRYTY_TWO_BIT_CLAMP = 2147483647.5;
    public static final double TWENTY_FOUR_BIT_CLAMP = 8388607.5;
    public static final double SIXTEEN_BIT_CLAMP = 32767.5;
    public static final double EIGHT_BIT_CLAMP = 127.5;

    // instance variables
    private int bitRate;
    private boolean floatingPoint;
    private boolean dither;
    private double bitrateFactor;
    private double ditherFactor;

    // constructor
    public FloatBitLimiter(int bitRate, boolean floatingPoint, boolean dither) {
        this.bitRate = bitRate;
        this.floatingPoint = floatingPoint;
        this.dither = dither;
        switch (this.bitRate) {
            default:
                throw new IllegalArgumentException("Invalid bitrate!");
            case 64:
                break;
            case 32:
                bitrateFactor = THRYTY_TWO_BIT_CLAMP;
                break;
            case 24:
                bitrateFactor = TWENTY_FOUR_BIT_CLAMP;
                break;
            case 16:
                bitrateFactor = SIXTEEN_BIT_CLAMP;
                break;
            case 8:
                bitrateFactor = EIGHT_BIT_CLAMP;
                break;
        }
        if (this.dither) {
            ditherFactor = 1d / bitrateFactor * 0.1;
        }
    }

    public FloatBitLimiter(int bitRate, boolean floatingPoint) {
        this(bitRate, floatingPoint, false);
    }

    // calculate amplitude
    public double calculateBitLimit(double point) {

        double newPoint;

        if (dither) {
            point += (Math.random() - 0.5) * 2 * ditherFactor;
        }

        // get the peak amplitude
        switch (bitRate) {
            // 64 bit
            case 64:
                newPoint = point;
                break;
            // 32-8 bit
            default:
                if (floatingPoint) {
                    newPoint = (float) point;
                } else {
                    newPoint = (int) Math.round(bitrateFactor
                            * point - 0.5);
                }
                break;
        }

        return newPoint;
    }
}
