/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sound.generator;

import sound.generator.handlers.FrequencyHandler;

/**
 *
 * @author eddy6
 */
public interface IPitchGeneratable extends IPointGenerateable, Cloneable{
    
    // get the frequencyHandler
    public FrequencyHandler getFrequencyHandler();
    
    // get the note
    public byte getNote();
    
    // get the frequency
    public double getFrequency();
    
    // get the original frequency (if a detuned sample)
    public double getOriginalFrequency();
    
    // get the most recent generated sample
    public double getPoint();
    
    // get the most recent volume applied sample
    public double getVolumeAppliedPoint();
    
    // get the most recent generated sample
    public long getIntPoint();

    // get the most recent volume applied sample
    public long getVolumeAppliedIntPoint();
    
    // get the amplitude of the waveform
    public double getAmplitude();
    
    // set the volume
    public void setAmplitude(double amplitude);
    
    // change the frequency when the tick update is ran
    public void setFrequencyAtTickUpdate(double frequency);
    
    // tunes the frequency up or down using cents
    public void tuneFrequencyAtTickUpdate(double cents);
    
    // deals with vibrato offset at tick update
    public void handleVibratoOffsetAtTickUpdate(double vibratoCents);
    
    // dealt with internal modulator offset
    public void handleInternalModOffset(double modulatorCents);
    
    // handle external modulator offset at tick update
    public void handleModulatorOffset(double modulatorOffset);
    
    // pitch bends the sound by cents
    public void pitchBend(double cents);
    
    // portamento bend the sounds by cents
    public void portamento(double cents);
    
    // set portamento note when sliding to a note
    public void portamentoToNote(byte note);
    
    // legato jump to note
    public void legatoJumpTo(byte note);
    
    // check the portamento
    public boolean checkPortamentoFinished();
    
    // clone the sample player
    public Object clone() throws CloneNotSupportedException;
    
    // update any frequency, pitch bend, slide and/or vibrato changes changes
    public void tickUpdate();
    
    // update any freqeuncy weights and other changes (only ran if frequency is changed)
    public void frequencyUpdate();
    
    // offset by samples
    public void offsetBySamples(int samples);
    
    // increment by one sample without generating anything
    public void sampleStepUp();
    
    // generate a block of mono points
    public void generateMonoSampleBlock(double[] sampleBlock);
    
    // generate a block of mono points
    public void generateMonoSampleBlock(double[] sampleBlock, 
            boolean mixedTo);
    
    // generate a block of mono 32-bit points
    public void generateMonoIntSampleBlock(long[] sampleBlock);
    
    // generate a block of mono 32-bit points
    public void generateMonoIntSampleBlock(long[] sampleBlock,
            boolean mixedTo);
    
    // resets all values of sound player
    public void reset();
}
