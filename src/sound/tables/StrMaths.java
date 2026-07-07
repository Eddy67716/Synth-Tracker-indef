/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.tables;

import static sound.effects.FloatBitLimiter.THRYTY_TWO_BIT_CLAMP;
import static sound.tables.StrConstants.INT_VOLUME_SHIFT;
import static sound.tables.StrConstants.MAX_INT_VOLUME;
import static sound.tables.StrConstants.STR_VELOCITY_EXPONENT;

/**
 *
 * @author Edward Jenkins
 */
public class StrMaths {

    // constnats
    public static final double[] SINE_TABLE = generateSineWave();
    public static final int[] INTEGER_SINE_TABLE = generateIntSineWave();
    public static final int SINE_LENGTH = 5513;
    public static final double LOG_10_2 = Math.log10(2);
    public static final double INVERSE_LOG_10_2 = 1.0 / LOG_10_2;
    public static final double LOG_2 = Math.log(2);
    public static final double INVERSE_LOG_2 = 1.0 / LOG_2;
    public static final double LOG_10 = Math.log(10);
    public static final double INVERSE_LOG_10 = 1.0 / LOG_10;
    public static final double INVERSE_20 = 1.0 / 20;

    private static double[] generateSineWave() {
        double[] sineTable = new double[SINE_LENGTH];

        for (int i = 0; i < sineTable.length; i++) {
            sineTable[i] = Math.sin(Math.TAU / SINE_LENGTH * i);
        }

        return sineTable;
    }
    
    public static int[] generateIntSineWave() {
        int[] sineTable = new int[SINE_LENGTH];
        
        for (int i = 0; i < sineTable.length; i++) {
            sineTable[i] = (int) Math.round(THRYTY_TWO_BIT_CLAMP 
                    * SINE_TABLE[i] - 0.5);
        }
        
        return sineTable;
    }

    public static double getSinePoint(double phase) {

        phase -= (int) phase;

        if (phase < 0) {
            phase += 1.0;
        }

        // method variables
        double point1, point2, currentPoint;

        double index = phase * (SINE_LENGTH - 1);

        point1 = SINE_TABLE[(int) index];

        point2 = SINE_TABLE[(int) Math.ceil(index)];

        double percentage = index - (int) index;
        if (percentage == 0) {
            currentPoint = point1;
        } else {
            currentPoint = (point1 * (1 - percentage) + point2
                    * percentage);
        }

        return currentPoint;
    }

    public static double getCoSinePoint(double phase) {
        
        phase += 0.25;

        phase -= (int) phase;

        if (phase < 0) {
            phase += 1.0;
        }

        // method variables
        double point1, point2, currentPoint;

        double index = phase * (SINE_LENGTH - 1);

        point1 = SINE_TABLE[(int) index];

        point2 = SINE_TABLE[(int) Math.ceil(index)];

        double percentage = index - (int) index;
        if (percentage == 0) {
            currentPoint = point1;
        } else {
            currentPoint = (point1 * (1 - percentage) + point2
                    * percentage);
        }

        return currentPoint;
    }
    
    public static long getIntSinePoint(double phase) {

        phase -= (int) phase;

        if (phase < 0) {
            phase += 1.0;
        }

        // method variables
        long point1, point2, currentPoint;

        double index = phase * (SINE_LENGTH - 1);

        point1 = INTEGER_SINE_TABLE[(int) index];

        point2 = INTEGER_SINE_TABLE[(int) Math.ceil(index)];

        double percentage = index - (int) index;
        
        int ceilingVolume = (int)Math.round(percentage * MAX_INT_VOLUME);
        int floorVolume = MAX_INT_VOLUME - ceilingVolume;
        
        if (percentage == 0) {
            currentPoint = point1;
        } else {
            currentPoint = ((point1 * floorVolume) >> INT_VOLUME_SHIFT
                        + (point2 * ceilingVolume >> INT_VOLUME_SHIFT));
        }

        return currentPoint;
    }

    public static long getIntCoSinePoint(double phase) {

        phase += 0.25;

        phase -= (int) phase;

        if (phase < 0) {
            phase += 1.0;
        }

        // method variables
        long point1, point2, currentPoint;

        double index = phase * (SINE_LENGTH - 1);

        point1 = INTEGER_SINE_TABLE[(int) index];

        point2 = INTEGER_SINE_TABLE[(int) Math.ceil(index)];

        double percentage = index - (int) index;
        
        int ceilingVolume = (int)Math.round(percentage * MAX_INT_VOLUME);
        int floorVolume = MAX_INT_VOLUME - ceilingVolume;
        
        if (percentage == 0) {
            currentPoint = point1;
        } else {
            currentPoint = ((point1 * floorVolume) >> 15
                        + (point2 * ceilingVolume >> 15));
        }

        return currentPoint;
    }

    // returns a sinc point from a percentage (not a radion unit)
    public double sinc(double value) {

        // method variables
        double thetaValue;

        if (value == 0) {
            value = 1;
        } else {

            thetaValue = Math.TAU * value;

            double sineValue = getSinePoint(value);

            value = sineValue * (1 / thetaValue);
        }
        return value;
    }

    public static double getInterpolationPercentage(double percentage, byte rate, 
            double exponent) {
        if (percentage > 1 || Double.isNaN(percentage) 
                || Double.isInfinite(percentage)) {
            percentage = 1;
        }
        switch (rate) {
            default:
                // linear
                break;
            case 1:
                // exponential
                percentage = Math.pow(percentage, exponent);
                break;
            case 2:
                // log
                percentage = 1 - Math.pow(1 - percentage, exponent);
                break;
            case 3:
                // cosine
                percentage = getSinePoint(0.75 + percentage * 0.5) * 0.5 + 0.5;
                break;
            case 4:
                // hard off
                percentage = (percentage == 1) ? 1 : 0;
                break;
        }
        
        if (percentage > 1 || Double.isNaN(percentage) 
                || Double.isInfinite(percentage)) {
            percentage = 1;
        }
        
        return percentage;
    }
    
    // converts a decibel value to an amplitude
    public static double getAmplitudeFromDecibels(double decibels) {
        return Math.pow(10, decibels * INVERSE_20);
    }
    
    public static double getDecibelsFromAmplitude(double amplitude) {
        return 20 * Math.log10(amplitude);
    }
    
    // used for easier decibel transition with a 0-1 value
    public static double getAmplitudeFromDekabels(double dekabels) {
        return Math.pow(10, dekabels * 3);
    }
    
    public static double getDekabelsFromAmplitude(double amplitude) {
        return 0.2 * Math.log10(amplitude);
    }
    
    // converts a normalised midi velocity to an amplitude
    public static double getAmplitudeFromVelocity(double velocity) {
        return Math.pow(velocity, STR_VELOCITY_EXPONENT);
    }
    
    public static double getVelocityFromAmplitude(double amplitude) {
        return Math.pow(amplitude, 1 / STR_VELOCITY_EXPONENT);
    }
    
    // convert a noralised amplitude to an int amplitude
    public static int getIntAmplitudeFromAmplitude(double amplitude, 
            byte shiftAmount) {
        return (int)Math.round(amplitude * (1 << shiftAmount));
    }
    
    public static int getIntAmplitudeFromAmplitude(double amplitude) {
        return getIntAmplitudeFromAmplitude(amplitude, INT_VOLUME_SHIFT);
    }
    
    public static double getOctaveOffset(double freqeuncyA, double frequencyB) {
        return Math.log(freqeuncyA / frequencyB) * INVERSE_LOG_2;
    }
    
    public static double getCentOffset(double freqeuncyA, double frequencyB) {
        return 1200 * getOctaveOffset(freqeuncyA, frequencyB);
    }
    
    public static byte getMinimumBitrateForInt(long intNumber) {
        
        byte min = 2, max = 64;
        byte i;

        if (intNumber == 0) {
            return 1;
        } else if (intNumber > 0) {
            // positive number
            do {
                i = (byte) ((min + max) / 2);
                
                if ((Math.pow(2, i) / 2 - 1 < intNumber)) {
                    min = (byte) (i + 1);
                    i++;
                } else {
                    max = (byte) (i - 1);
                }
            } while (min <= max);
        } else {
            // negative number
            do {
                i = (byte) ((min + max) / 2);

                if (-(Math.pow(2, i) / 2) > intNumber) {
                    min = (byte) (i + 1);
                    i++;
                } else {
                    max = (byte) (i - 1);
                }
            } while (min <= max);
        }

        return i;
    }
    
    public static byte getMinimumBitrateForUnsignedInt(long intNumber) {
        
        byte min = 2, max = 64;
        byte i;

        if (intNumber == 0 || intNumber == 1) {
            return 1;
        } else if (intNumber > 0) {
            // positive number
            do {
                i = (byte) ((min + max) / 2);
                
                if ((Math.pow(2, i) - 1 < intNumber)) {
                    min = (byte) (i + 1);
                    i++;
                } else {
                    max = (byte) (i - 1);
                }
            } while (min <= max);
        } else {
            i = -1;
        }

        return i;
    }
}
