/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound.generator;

import sound.filters.IFilterable;
import module.ISynthesiser;
import sound.effects.IVibrato;
import sound.effects.cache.IToneEffectCacheable;
import sound.generator.handlers.IVolumeAccumulator;

/**
 *
 * @author Edward Jenkins
 * © Edward Jenkins 2021, 2022, 2023, 2024 and 2025
 */
public interface ISoundPlayer extends IStereoPointGenerateable, 
        IPitchGeneratable, Comparable {
    
    // get the sample spec
    public ISynthesiser getSynthSpec();
    
    // get the synth effect spec
    public IToneEffectCacheable getSynthEffectCache();
    
    // is the top level generator
    public boolean isTopLevelGenerator();
    
    // get the most recent generated stereo samples
    public double[] getPoints();
    
    // get the most recent volume applied stereo samples
    public double[] getVolumeAppliedPoints();

    // get the most recent generated stereo samples
    public long[] getIntPoints();

    // get the most recent volume applied stereo samples
    public long[] getIntVolumeAppliedPoints();
    
    // get whether amplitude is maxed at half value
    public boolean isAmplitudeMaxedAtHalf();
    
    // get the volume accumulator
    public IVolumeAccumulator getVolumeAccumulator();
    
    // get the total volume value of sample
    public double getTotalVolume();
    
    // true if soft pedal engauged
    public boolean isSoftPedalOn();
    
    // get the normalised amplitude of the waveform
    public double getFilterNormalisedAmplitude();
    
    // true when sample is finished
    public boolean isFinished();
    
    // get the vibrato
    public IVibrato getVibrato();
    
    // get filter
    public IFilterable getFilter();
    
    // get releaseing note
    public boolean isReleasingNote();
    
    // set as top level generator
    public void setTopLevelGenerator(boolean topLevelGenerator);
    
    // set whether this amplitude is maxed at half
    public void setAmplitudeMaxedAtHalf(boolean amplitudeMaxedAtHalf);
    
    // set the volume
    public void setVolume(double volume);
    
    // set the velocity (only used for recycle note ons)
    public void setVelocity(int velocity, byte velocityBits);
    
    // sets sample to finished
    public void setFinished(boolean finished);
    
    // set filter
    public void setFilter(IFilterable filter);
    
    // set channel pan value
    public void setPanning(double panning);
    
    // set midi channel panning
    public void setMidiPanning(double midiLeftPan, double midiRightPan);
    
    // inverts a channel if true
    public void setSurround(boolean surround);
    
    // accumulate a pan value
    public void accumulcatePanning(double panningOffset);
    
    // return true if each channel is completely independant
    public boolean isIndependentStereo();
    
    // generates a block of sample points 
    public void generateStereoSampleBlock(double[][] samplesBlock);
    
    // generates a block of sample points 
    public void generateStereoSampleBlock(double[][] samplesBlock, 
            boolean mixedTo);
    
    // generates a block of sample points within a specified length
    public void generateStereoSampleBlock(double[][] samplesBlock, 
            int length, boolean mixedTo);
    
    // generates a block of 32-bit sample points 
    public void generateStereoIntSampleBlock(long[][] samplesBlock);
    
    // generates a block of 32-bit sample points 
    public void generateStereoIntSampleBlock(long[][] samplesBlock, 
            boolean mixedTo);
    
    // generates a block of 32-bit sample points within a specified length
    public void generateStereoIntSampleBlock(long[][] samplesBlock, 
            int length, boolean mixedTo);
    
    // shift pan value
    public void panbrelloShift(double panbrelloValue);
    
    // notify of a tick rate update
    public void updateTickRate();
    
    // set note off event
    public void noteOff();
    
    // set note end event
    public void noteEnd();
    
    // handles the legato
    public void handleLegato(boolean leagto);
}
