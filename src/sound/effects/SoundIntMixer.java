/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.effects;

import static sound.generator.GenericSoundPlayer.PAN_NORMALISER;
import static sound.tables.StrConstants.INT_VOLUME_SHIFT;
import static sound.tables.StrConstants.MAX_INT_VOLUME;

/**
 *
 * @author eddy6
 */
public class SoundIntMixer {

    public static void mixIntSamples(long[] samplesA, long[] samplesB) {

        mixIntSamples(samplesA, samplesB, false);
    }

    public static void mixIntSamples(long[] samplesA, long[] samplesB,
            boolean invert) {

        for (int i = 0; i < samplesA.length; i++) {
            if (invert) {
                samplesA[i] -= samplesB[i];
            } else {
                samplesA[i] += samplesB[i];
            }
        }
    }

    public static void mixIntSampleGroup(long[][] sampleGroupA,
            long[][] sampleGroupB, int mixLength) {

        for (int i = 0; i < sampleGroupA.length; i++) {
            mixIntSamples(sampleGroupA[i], sampleGroupB[i]);
        }
    }

    public static void ringModulateIntSamples(double[] samplesA,
            double[] samplesB) {

        for (int i = 0; i < samplesA.length; i++) {
            samplesA[i] *= samplesB[i];
        }
    }

    public static long applyIntAmplitude(long sample, int amplitude,
            byte shiftCount) {
        sample *= amplitude;
        sample >>= shiftCount;

        return sample;
    }

    public static long applyIntAmplitude(long sample, int amplitude) {
        return applyIntAmplitude(sample, amplitude, INT_VOLUME_SHIFT);
    }

    public static void applyIntAmplitude(long[] samples, int amplitude,
            byte shiftCount) {

        for (int i = 0; i < samples.length; i++) {
            samples[i] *= amplitude;
            samples[i] >>= shiftCount;
        }
    }

    public static void applyIntAmplitude(long[] samples, int amplitude) {
        applyIntAmplitude(samples, amplitude, INT_VOLUME_SHIFT);
    }

    public static void applyIntGroupAmplitude(long[][] sampleGroup,
            int amplitude, byte shiftCount) {

        for (long[] sampleGroup1 : sampleGroup) {
            applyIntAmplitude(sampleGroup1, amplitude, shiftCount);
        }
    }

    public static void applyPanningToIntSample(long point,
            long[] returnPoints, double panValue) {

        // setup pan valeus
        double normalisedPanning = panValue * PAN_NORMALISER;

        // left calculations
        double normalisedLeftPanAmplitude = PAN_NORMALISER
                - normalisedPanning;
        if (normalisedLeftPanAmplitude > 1) {
            normalisedLeftPanAmplitude = 1;
        }

        int leftPanAmplitude = (int) Math.round(normalisedLeftPanAmplitude
                * MAX_INT_VOLUME);

        // right calculations
        double normalisedRightPanAmplitude = PAN_NORMALISER
                + normalisedPanning;
        if (normalisedRightPanAmplitude > 1) {
            normalisedRightPanAmplitude = 1;
        }

        int rightPanAmplitude = (int) Math.round(normalisedRightPanAmplitude
                * MAX_INT_VOLUME);

        // left channel
        returnPoints[0] = point * leftPanAmplitude;

        returnPoints[0] >>= INT_VOLUME_SHIFT;

        // right channel
        returnPoints[1] = point * rightPanAmplitude;

        returnPoints[1] >>= INT_VOLUME_SHIFT;
    }

    public static void applyBalanceToIntSamples(long[] points, double panValue) {

        // setup pan valeus
        double normalisedPanning = panValue * PAN_NORMALISER;

        // left calculations
        double normalisedLeftPanAmplitude = PAN_NORMALISER
                - normalisedPanning;
        if (normalisedLeftPanAmplitude > 1) {
            normalisedLeftPanAmplitude = 1;
        }

        int leftPanAmplitude = (int) Math.round(normalisedLeftPanAmplitude
                * MAX_INT_VOLUME);

        // right calculations
        double normalisedRightPanAmplitude = PAN_NORMALISER
                + normalisedPanning;
        if (normalisedRightPanAmplitude > 1) {
            normalisedRightPanAmplitude = 1;
        }

        int rightPanAmplitude = (int) Math.round(normalisedRightPanAmplitude
                * MAX_INT_VOLUME);

        // left channel
        points[0] *= leftPanAmplitude;

        points[0] >>= INT_VOLUME_SHIFT;

        // right channel
        points[1] *= rightPanAmplitude;

        points[1] >>= INT_VOLUME_SHIFT;
    }

    public static long[] interpolateIntSamples(long[] floorSamples,
            long[] ceilingSamples, double percentage) {

        int ceilingVolume = (int) Math.round(percentage * MAX_INT_VOLUME);
        int floorVolume = MAX_INT_VOLUME - ceilingVolume;

        for (int i = 0; i < floorSamples.length; i++) {
            if (percentage != 0) {
                floorSamples[i] = ((floorSamples[i] * floorVolume)
                        >> INT_VOLUME_SHIFT + (ceilingSamples[i]
                        * ceilingVolume >> INT_VOLUME_SHIFT));
            }
        }

        return floorSamples;
    }

    public static long interpolateIntSample(long floorSample,
            long ceilingSample, double percentage) {
        long returnPoint;

        int ceilingVolume = (int) Math.round(percentage * MAX_INT_VOLUME);
        int floorVolume = MAX_INT_VOLUME - ceilingVolume;

        if (percentage == 0) {
            returnPoint = floorSample;
        } else {
            returnPoint = ((floorSample * floorVolume) >> INT_VOLUME_SHIFT
                    + (ceilingSample * ceilingVolume >> INT_VOLUME_SHIFT));
        }

        return returnPoint;
    }

    public static long[] cubicInterpolateIntSamples(long[] lowSamples,
            long floorSamples[], long ceilingSamples[],
            long highSamples[], double percentage) {

        // method variables
        long a0, a1, a2, a3;

        int percentage1 = (int) Math.round(percentage * MAX_INT_VOLUME);
        int percentage2 = (percentage1 >> 8) * (percentage1 >> 8);

        for (int i = 0; i < lowSamples.length; i++) {

            // set a values
            a0 = highSamples[i] - ceilingSamples[i] - lowSamples[i]
                    + floorSamples[i];
            a1 = lowSamples[i] - floorSamples[i] - a0;
            a2 = ceilingSamples[i] - lowSamples[i];
            a3 = floorSamples[i];

            // set return value
            lowSamples[i] = (a0 * percentage1) >> INT_VOLUME_SHIFT;
            lowSamples[i] = ((lowSamples[i] * percentage2) >> INT_VOLUME_SHIFT) 
                    + a1;
            lowSamples[i] = ((lowSamples[i] * percentage2) >> INT_VOLUME_SHIFT) 
                    + a2;
            lowSamples[i] = ((lowSamples[i] * percentage1) >> INT_VOLUME_SHIFT) 
                    + a3;
        }

        return lowSamples;
    }

    public static long cubicInterpolateIntSample(long lowSample,
            long floorSample, long ceilingSample, long highSample,
            double percentage) {

        // method variables
        long returnPoint, a0, a1, a2, a3;

        int percentage1 = (int) Math.round(percentage * MAX_INT_VOLUME);
        int percentage2 = (percentage1 >> 8) * (percentage1 >> 8);

        // set a values
        a0 = highSample - ceilingSample - lowSample + floorSample;
        a1 = lowSample - floorSample - a0;
        a2 = ceilingSample - lowSample;
        a3 = floorSample;

        // set return value
        returnPoint = (a0 * percentage1) >> INT_VOLUME_SHIFT;
        returnPoint = ((returnPoint * percentage2) >> INT_VOLUME_SHIFT) + a1;
        returnPoint = ((returnPoint * percentage2) >> INT_VOLUME_SHIFT) + a2;
        returnPoint = ((returnPoint * percentage1) >> INT_VOLUME_SHIFT) + a3;

        return returnPoint;
    }
}
