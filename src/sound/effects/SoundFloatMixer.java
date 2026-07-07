/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.effects;

import java.nio.ByteBuffer;
import static sound.generator.GenericSoundPlayer.PAN_NORMALISER;

/**
 *
 * @author Edward Jenkins
 */
public class SoundFloatMixer {

    public static void mixSamples(double[] samplesA, double[] samplesB) {

        mixSamples(samplesA, samplesB, false);
    }
    
    public static void mixSamples(double[] samplesA, double[] samplesB, 
            boolean invert) {

        for (int i = 0; i < samplesA.length; i++) {
            if (invert) {
                samplesA[i] -= samplesB[i];
            } else {
                samplesA[i] += samplesB[i];
            }
        }
    }
    
    public static void mixSampleGroup(double[][] sampleGroupA,
            double[][] sampleGroupB, int mixLength) {

        for (int i = 0; i < sampleGroupA.length; i++) {
            mixSamples(sampleGroupA[i], sampleGroupB[i]);
        }
    }

    public static void ringModulateSamples(double[] samplesA,
            double[] samplesB) {

        for (int i = 0; i < samplesA.length; i++) {
            samplesA[i] *= samplesB[i];
        }
    }

    public static void applyAmplitude(double[] samples, double amplitude) {

        for (int i = 0; i < samples.length; i++) {
            samples[i] *= amplitude;
        }
    }
    
    public static void applyGroupAmplitude(double[][] sampleGroup,
            double amplitude) {

        for (double[] sampleGroup1 : sampleGroup) {
            applyAmplitude(sampleGroup1, amplitude);
        }
    }
    
    public static void applyPanningToSample(double point, 
            double[] returnPoints, double panValue) {
        
        // setup pan valeus
        double normalisedPanning = panValue * PAN_NORMALISER;
        double leftPanAmplitude = PAN_NORMALISER - normalisedPanning;
        if (leftPanAmplitude > 1) {
            leftPanAmplitude = 1;
        }
        double rightPanAmplitude = PAN_NORMALISER + normalisedPanning;
        if (rightPanAmplitude > 1) {
            rightPanAmplitude = 1;
        }

        // left channel
        returnPoints[0] = point * leftPanAmplitude;

        // right channel
        returnPoints[1] = point * rightPanAmplitude;
    }

    public static void applyBalanceToSamples(double[] points, double panValue) {
        
        // setup pan values
        double normalisedPanning = panValue * PAN_NORMALISER;
        double leftPanAmplitude = PAN_NORMALISER - normalisedPanning;
        if (leftPanAmplitude > 1) {
            leftPanAmplitude = 1;
        }
        double rightPanAmplitude = PAN_NORMALISER + normalisedPanning;
        if (rightPanAmplitude > 1) {
            rightPanAmplitude = 1;
        }

        // left channel
        points[0] *= leftPanAmplitude;

        // right channel
        points[1] *= rightPanAmplitude;
    }

    public static double[] interpolateSamples(double[] floorSamples,
            double[] ceilingSamples, double percentage) {
        for (int i = 0; i < floorSamples.length; i++) {
            if (percentage != 0) {
                floorSamples[i] = (floorSamples[i] * (1 - percentage)
                        + ceilingSamples[i] * percentage);
            }
        }

        return floorSamples;
    }

    public static double interpolateSample(double floorSample,
            double ceilingSample, double percentage) {
        double returnPoint;
        if (percentage == 0) {
            returnPoint = floorSample;
        } else {
            returnPoint = (floorSample * (1 - percentage)
                    + ceilingSample * percentage);
        }

        return returnPoint;
    }

    public static double[] cubicInterpolateSamples(double[] lowSamples,
            double floorSamples[], double ceilingSamples[],
            double highSamples[], double percentage) {

        // method variables
        double returnPoint, a0, a1, a2, a3,
                percentage2 = percentage * percentage;

        for (int i = 0; i < lowSamples.length; i++) {
            
            // set a values
            a0 = highSamples[i] - ceilingSamples[i] - lowSamples[i] 
                    + floorSamples[i];
            a1 = lowSamples[i] - floorSamples[i] - a0;
            a2 = ceilingSamples[i] - lowSamples[i];
            a3 = floorSamples[i];

            // set return value
            lowSamples[i] = (a0 * percentage * percentage2 + a1 * percentage2
                    + a2 * percentage + a3);
        }

        return lowSamples;
    }

    public static double cubicInterpolateSample(double lowSample,
            double floorSample, double ceilingSample, double highSample,
            double percentage) {

        // method variables
        double returnPoint, a0, a1, a2, a3,
                percentage2 = percentage * percentage;

        // set a values
        a0 = highSample - ceilingSample - lowSample + floorSample;
        a1 = lowSample - floorSample - a0;
        a2 = ceilingSample - lowSample;
        a3 = floorSample;

        // set return value
        returnPoint = (a0 * percentage * percentage2 + a1 * percentage2
                + a2 * percentage + a3);

        return returnPoint;
    }
}
