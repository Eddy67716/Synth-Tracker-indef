/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.generator;

import sequence.midi.handlers.PitchBendHandler;
import sequence.midi.parameters.MidiControl;
import static sound.effects.SoundFloatMixer.mixSamples;
import static sound.effects.SoundIntMixer.mixIntSamples;
import sound.generator.handlers.IntegerPanHandler;
import sound.generator.handlers.FloatPanHandler;
import sound.generator.handlers.FloatSampleHandler;
import sound.generator.handlers.IntSampleHandler;

/**
 *
 * @author eddy6
 * @version 0.1 © Edward Jenkins 2024
 */
public abstract class GenericSoundPlayer implements ISoundPlayer, Cloneable {

    // constants
    public static final byte DEF_PANNING = 0;
    public static final float PAN_NORMALISER = 0.5f;
    public static final boolean DEF_SURROUND = false;

    // instance variables
    // sound note
    private byte note;
    // top level generator for sound if true
    private boolean topLevelGenerator;
    // int mixed sample
    private boolean intMixed;
    // hold 2 pedal on
    private boolean hold2On;
    // float sample handler
    private FloatSampleHandler floatSampleHandler;
    // int sample handler
    private IntSampleHandler intSampleHandler;
    // panning handler
    private FloatPanHandler floatPanHandler;
    // integer panning handler
    private IntegerPanHandler intPanHandler;

    // all-args constructor
    public GenericSoundPlayer(boolean surround, boolean intMixed) {
        this.intMixed = intMixed;
        topLevelGenerator = true;
        if (intMixed) {
            intSampleHandler = new IntSampleHandler(2, true);
            intPanHandler = new IntegerPanHandler(DEF_PANNING, surround);
        } else {
            floatPanHandler = new FloatPanHandler(DEF_PANNING, surround);
            floatSampleHandler = new FloatSampleHandler(2, true);
        }
    }

    // 1 args constructor
    public GenericSoundPlayer(boolean intMixed) {
        this(intMixed, DEF_SURROUND);
    }

    // getters
    @Override
    public boolean isTopLevelGenerator() {
        return topLevelGenerator;
    }

    public FloatSampleHandler getFloatSampleHandler() {
        return floatSampleHandler;
    }

    @Override
    public byte getNote() {
        return note;
    }

    @Override
    public double getPoint() {
        return floatSampleHandler.getPoint();
    }

    @Override
    public double[] getPoints() {
        return floatSampleHandler.getPoints();
    }

    @Override
    public double getVolumeAppliedPoint() {
        return floatSampleHandler.getVolumeAppliedPoint();
    }

    @Override
    public double[] getVolumeAppliedPoints() {
        return floatSampleHandler.getVolumeAppliedPoints();
    }

    public boolean isIntMixed() {
        return intMixed;
    }

    public IntSampleHandler getIntSampleHandler() {
        return intSampleHandler;
    }

    @Override
    public long getIntPoint() {
        return intSampleHandler.getIntPoint();
    }

    @Override
    public long[] getIntPoints() {
        return intSampleHandler.getIntPoints();
    }

    @Override
    public long getVolumeAppliedIntPoint() {
        return intSampleHandler.getIntVolumeCalculatedPoint();
    }

    @Override
    public long[] getIntVolumeAppliedPoints() {
        return intSampleHandler.getIntVolumeCalculatedPoints();
    }

    public FloatPanHandler getFloatPanHandler() {
        return floatPanHandler;
    }

    // setters
    @Override
    public void setTopLevelGenerator(boolean topLevelGenerator) {
        this.topLevelGenerator = topLevelGenerator;
    }

    public void setNote(byte note) {
        this.note = note;
    }
    
    @Override
    public abstract void setVelocity(int velocity, byte velocityBits);

    @Override
    public void setPanning(double panning) {
        floatPanHandler.setPanning(panning);
    }

    @Override
    public void setMidiPanning(double midiLeftPan, double midiRightPan) {
        floatPanHandler.setMidiPanning(midiLeftPan, midiRightPan);
    }

    @Override
    public void setSurround(boolean surround) {
        floatPanHandler.setSurround(surround);
    }

    // accumulate a pan value
    @Override
    public void accumulcatePanning(double panningOffset) {
        floatPanHandler.accumulcatePanning(panningOffset);
    }

    @Override
    public void panbrelloShift(double panbrelloAmount) {
        floatPanHandler.panbrelloShift(panbrelloAmount);
    }

    @Override
    public void generateMonoSampleBlock(double[] sampleBlock,
            boolean mixedTo) {
        double sample;
        double generatedSample;

        for (int i = 0; i < sampleBlock.length; i++) {
            sample = sampleBlock[i];
            generatedSample = generateSamplePoint();
            if (mixedTo) {
                sample *= generatedSample;
            } else {
                sample = generatedSample;
            }
            sampleBlock[i] = sample;
        }
    }

    @Override
    public void generateMonoSampleBlock(double[] sampleBlock) {
        generateMonoSampleBlock(sampleBlock, false);
    }

    @Override
    public void generateMonoIntSampleBlock(long[] sampleBlock) {
        generateMonoIntSampleBlock(sampleBlock, false);
    }

    @Override
    public void generateMonoIntSampleBlock(long[] sampleBlock, boolean mixedTo) {
        long sample;
        long generatedSample;

        for (int i = 0; i < sampleBlock.length; i++) {
            sample = sampleBlock[i];
            generatedSample = generateIntSamplePoint();
            if (mixedTo) {
                sample *= generatedSample;
            } else {
                sample = generatedSample;
            }
            sampleBlock[i] = sample;
        }
    }

    @Override
    public void generateStereoSampleBlock(double[][] samplesBlock) {
        generateStereoSampleBlock(samplesBlock, false);
    }

    @Override
    public void generateStereoSampleBlock(double[][] samplesBlock,
            boolean mixedTo) {

        double[] generatedSamples;

        for (double[] samples : samplesBlock) {
            generatedSamples = generateStereoSamplePoints();
            if (mixedTo) {
                mixSamples(samples, generatedSamples);
            } else {
                System.arraycopy(generatedSamples, 0, samples, 0,
                        generatedSamples.length);
            }
        }
    }

    @Override
    public void generateStereoSampleBlock(double[][] samplesBlock,
            int length, boolean mixedTo) {
        double[] generatedSamples;

        for (int i = 0; i < length; i++) {
            generatedSamples = generateStereoSamplePoints();
            if (mixedTo) {
                mixSamples(samplesBlock[i], generatedSamples);
            } else {
                System.arraycopy(generatedSamples, 0, samplesBlock[i], 0,
                        generatedSamples.length);
            }
        }
    }

    @Override
    public void generateStereoIntSampleBlock(long[][] samplesBlock) {
        generateStereoIntSampleBlock(samplesBlock, false);
    }

    @Override
    public void generateStereoIntSampleBlock(long[][] samplesBlock,
            boolean mixedTo) {
        long[] generatedSamples;

        for (long[] samples : samplesBlock) {
            generatedSamples = generateStereoIntSamplePoints();
            if (mixedTo) {
                mixIntSamples(samples, generatedSamples);
            } else {
                System.arraycopy(generatedSamples, 0, samples, 0,
                        generatedSamples.length);
            }
        }
    }

    @Override
    public void generateStereoIntSampleBlock(long[][] samplesBlock, int length,
            boolean mixedTo) {
        long[] generatedSamples;

        for (int i = 0; i < length; i++) {
            generatedSamples = generateStereoIntSamplePoints();
            if (mixedTo) {
                mixIntSamples(samplesBlock[i], generatedSamples);
            } else {
                System.arraycopy(generatedSamples, 0, samplesBlock[i], 0,
                        generatedSamples.length);
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        GenericSoundPlayer cloneGSP = (GenericSoundPlayer) super.clone();

        if (intMixed) {

        } else {
            // panHandler
            cloneGSP.floatPanHandler = (FloatPanHandler) floatPanHandler.clone();

            // points
            cloneGSP.floatSampleHandler = (FloatSampleHandler) floatSampleHandler.Clone();
        }

        return cloneGSP;
    }

    @Override
    public void tickUpdate() {

        if (topLevelGenerator) {
            
            // pitch bend handler
            PitchBendHandler pitchBendHandler = getSynthEffectCache()
                    .getMidiEffects().getPitchBendHandler();

            if (pitchBendHandler != null
                    && pitchBendHandler.isPitchBendUpdated()) {
                pitchBend(pitchBendHandler.getCentBendValue());
            }

            // midi controls
            MidiControl midiControl = getSynthEffectCache().getMidiEffects()
                    .getMidiControl();

            if (midiControl != null) {
                if (midiControl.isHoldTwoUpdated()) {
                    hold2On = midiControl.isHoldTwoOn();
                }
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof GenericSoundPlayer gsp) {

            return 0;
        }
        return 1;
    }
}
