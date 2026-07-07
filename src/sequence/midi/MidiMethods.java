/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi;

import static sequence.midi.MidiConstants.MIDI_2_SHIFT;
import static sequence.midi.MidiConstants.MIDI_SHIFT;
import sound.tables.StrMaths;

/**
 *
 * @author Edward Jenkins © Edward Jenkins 2024
 */
public class MidiMethods {

    private static final double TYPE_1_OFFSET = 10;
    private static final double TYPE_1_NORMALISER = 990;
    private static final double TYPE_2_OFFSET = 1;
    private static final double TYPE_2_NORMALISER = 9;
    private static final double TYPE_3_OFFSET = 0.333333;
    private static final double TYPE_3_NORMALISER = 0.666667;
    private static final double TYPE_4_OFFSET = 0.1;
    private static final double TYPE_4_NORMALISER = 0.323333;
    private static final double TYPE_5_OFFSET = 0.01;
    private static final double TYPE_5_NORMALISER = 0.1;
    private static final long TYPE_1_BOUND = 28;
    private static final long TYPE_2_BOUND = 76;
    private static final long TYPE_3_BOUND = 112;
    private static final long TYPE_4_BOUND = 120;
    private static final long TYPE_5_BOUND = 128;

    public static double getPortamentoRate(long rate, boolean midi2Channel) {

        byte bitShiftAmount = (midi2Channel) ? MIDI_2_SHIFT : MIDI_SHIFT;
        double portamentoRate;
        double normaliser;
        long linearIndex;
        double linearPoint;
        double inversePoint;
        long type1Bound = TYPE_1_BOUND << bitShiftAmount;
        long type2Bound = TYPE_2_BOUND << bitShiftAmount;
        long type3Bound = TYPE_3_BOUND << bitShiftAmount;
        long type4Bound = TYPE_4_BOUND << bitShiftAmount;
        long type5Bound = TYPE_5_BOUND << bitShiftAmount;

        if (rate > 0 && rate <= type1Bound) {
            normaliser = 1.0 / type1Bound;
            linearIndex = rate;
            linearPoint = linearIndex * normaliser;
            inversePoint = 1 - linearPoint;
            inversePoint = StrMaths.getInterpolationPercentage(inversePoint,
                    (byte) 1, 15);
            portamentoRate = inversePoint * TYPE_1_NORMALISER + TYPE_1_OFFSET;

        } else if (rate <= type2Bound) {
            normaliser = 1.0 / (type2Bound - type1Bound);
            linearIndex = rate - type1Bound;
            linearPoint = linearIndex * normaliser;
            inversePoint = 1 - linearPoint;
            portamentoRate = inversePoint * TYPE_2_NORMALISER + TYPE_2_OFFSET;

        } else if (rate <= type3Bound) {
            normaliser = 1.0 / (type3Bound - type2Bound);
            linearIndex = rate - type2Bound;
            linearPoint = linearIndex * normaliser;
            inversePoint = 1 - linearPoint;
            portamentoRate = inversePoint * TYPE_3_NORMALISER + TYPE_3_OFFSET;
        } else if (rate <= type4Bound) {
            normaliser = 1.0 / (type4Bound - type3Bound);
            linearIndex = rate - type3Bound;
            linearPoint = linearIndex * normaliser;
            inversePoint = 1 - linearPoint;
            inversePoint = StrMaths.getInterpolationPercentage(inversePoint,
                    (byte) 2, 1.5);
            portamentoRate = inversePoint * TYPE_4_NORMALISER + TYPE_4_OFFSET;
        } else if (rate < type5Bound) {
            normaliser = 1.0 / (type5Bound - type4Bound);
            linearIndex = rate - type4Bound;
            linearPoint = linearIndex * normaliser;
            inversePoint = 1 - linearPoint;
            inversePoint = StrMaths.getInterpolationPercentage(inversePoint,
                    (byte) 2, 1.5);
            portamentoRate = inversePoint * TYPE_5_NORMALISER + TYPE_5_OFFSET;
        } else {
            portamentoRate = 0.01;
        }

        return portamentoRate;
    }

    private static long powerOf2(byte exponent) {
        return 1 << exponent;
    }

    // used to scale midi1 values to midi 2 values
    public static long scaleUp(long sourceValue, byte sourceBits,
            byte destinationBits) {

        // number of bits to upscale
        byte scaleBits = (byte) (destinationBits - sourceBits);

        // center value for srcBits, e.g.
        // 0x40 (64) for 7 bits
        // 0x2000 (8192) for 14 bits
        // simple bit shift
        long srcCenter = powerOf2((byte) (sourceBits - 1));
        long bitShiftedValue = sourceValue << scaleBits;
        if (sourceValue <= srcCenter) {
            return bitShiftedValue;
        }

        // expanded bit repeat scheme
        // we must repeat all but the highest bit
        byte repeatBits = (byte) (sourceBits - 1);
        long repeatMask = powerOf2(repeatBits) - 1;
        // repeat bit sequence
        long repeatValue = sourceValue & repeatMask;
        if (scaleBits > repeatBits) {
            // need to repeat multiple times
            repeatValue <<= (scaleBits - repeatBits);
        } else {
            repeatValue >>= (repeatBits - scaleBits);
        }
        while (repeatValue != 0) {

            // fill lower bits with repeatValue
            bitShiftedValue |= repeatValue;

            // move repeat bit sequence to next position
            repeatValue >>= repeatBits;
        }
        return bitShiftedValue;
    }

    // used to scale midi2 values to midi1 values
    public static long scaleDown(long sourceVale, byte sourceBits,
            byte destinationBits) {
        // simple bit shift
        byte scaleBits = (byte) (sourceBits - destinationBits);
        return sourceVale >> scaleBits;
    }
}
