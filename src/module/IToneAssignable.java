/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package module;

import io.Reader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import module.cache.GenericToneCache;
import sound.channel.UnassignedNoteException;
import sound.generator.ISoundPlayer;
import sequence.midi.handlers.MidiParameterHandler;
import sound.effects.cache.GenericToneEffectCache;
import sound.effects.cache.IToneEffectCacheable;

/**
 *
 * @author Edward Jenkins
 */
public interface IToneAssignable extends ISaveableModule, Cloneable {

    /**
     * Builds a sound player interface object that can generate sample points.
     * @param note The MIDI note number
     * @param toneEffectCache All effects used by all channel instances of this synth.
     * @param volume How loud to play the note
     * @param volumeBitWidth The bit width of this volume value. For an
     * amplitude, this must be a power of 2 number; for a velocity, this must be
     * 1 less than a power of 2 number.
     * @param velocity Interpret volume as MIDI velocity instead of an 
     * amplitude if true.
     * @return a sound that can generate sample points.
     * @throws CloneNotSupportedException
     * @throws UnassignedNoteException 
     */
    public ISoundPlayer assignByNote(int note, int volume, byte volumeBitWidth, 
            boolean velocity, GenericToneEffectCache toneEffectCache) 
            throws CloneNotSupportedException, UnassignedNoteException;

    /**
     * Builds a sound player interface object that can generate sample points with
     * no bend offset parameter.
     * @param note The MIDI note number
     * @param volume How loud to play the note
     * @param volumeBitWidth The bit width of this volume value. For an
     * amplitude, this must be a power of 2 number; for a velocity, this must be
     * 1 less than a power of 2 number.
     * @param velocity Interpret volume as MIDI velocity value instead of an 
     * amplitude if true.
     * @return a sound that can generate sample points.
     * @throws CloneNotSupportedException
     * @throws UnassignedNoteException 
     */
    public ISoundPlayer assignByNote(int note, int volume, byte volumeBitWidth, 
            boolean velocity)
            throws CloneNotSupportedException, UnassignedNoteException;

    /**
     * Sets up any cache effect values.Used for MIDI playback.
     * @param midiEffects
     * @return the effect cache used for this synth per midi channel
     */
    public abstract IToneEffectCacheable setupInstrumentEffectCache(MidiParameterHandler midiEffects);
    
    /**
     * Sets up any cache values. 
     * Used for tracker playback.
     */
    public abstract void setupInstrumentCache();
    
    /**
     * pack for writing.
     */
    public void packForWriting();
    
    public GenericToneCache getToneCache();
    
    /**
     * Resets update for new sample.
     */
    public void sampleUpdateReset();
    
    public byte getNewNoteAction();

    public byte getDuplicateCheckType();
    
    public byte[][] getNoteGroups();

    public byte getDuplicateCheckAction();
    
    public short getMidiBank();
    
    public byte getMidiProgram();
    
    public void setFilePath(String filePath);
    
    public void setMidiBank(short midiBank);
    
    public void setMidiProgram(byte midiProgram);
}
