/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.effects.cache;

import sequence.midi.handlers.MidiParameterHandler;

/**
 *
 * @author eddy6
 */
public abstract class GenericToneEffectCache implements IToneEffectCacheable {

    // instance variables
    // top level cache
    private boolean topLevelCache;
    private short playingNotes;
    private short activeNotes;
    private byte keyCount;
    private MidiParameterHandler midiEffects;

    public GenericToneEffectCache(MidiParameterHandler midiEffects) {
        topLevelCache = true;
        playingNotes = 0;
        this.midiEffects = midiEffects;
    }

    // getters
    public boolean isTopLevelCache() {
        return topLevelCache;
    }
    
    public short getPlayingNotes() {
        return playingNotes;
    }

    public short getActiveNotes() {
        return activeNotes;
    }

    public byte getKeyCount() {
        return keyCount;
    }

    public MidiParameterHandler getMidiEffects() {
        return midiEffects;
    }
    
    // setter
    public void setTopLevelCache(boolean topLevelCache) {
        this.topLevelCache = topLevelCache;
    }

    public void incrementPlayingNotes() {
        playingNotes++;
        incrementActiveNotes();
        //System.out.println("Playing notes: " + playingNotes);
    }

    public void incrementActiveNotes() {
        activeNotes++;
        //System.out.println("Active notes: " + activeNotes);
    }

    public void incrementKeyCount() {
        keyCount++;
        //System.out.println("Key count: " + keyCount);
    }

    public void decrementPlayingNotes() {
        playingNotes--;
        //System.out.println("Playing notes: " + playingNotes);
    }

    public void decrementActiveNotes() {
        activeNotes--;
        //System.out.println("Active notes: " + activeNotes);
    }

    public void decrementKeyCount() {
        keyCount--;
        //System.out.println("Key count: " + keyCount);
    }

    public void pitchBend(short bendOffset) {
    }

    public void midi2PitchBend(long bendOffset) {
    }

    public void applyPressure(byte pressure) {
    }

    public void applyMidi2Pressure(long pressure) {
    }

    public void applyPolyPressure(byte note, byte pressure) {
        
    }

    public void applyMidi2PolyPressure(byte note, long pressure) {
        
    }

    public void updateMidiSoundControl(byte controller, byte value) {
        
    }

    public void updateMidi2SoundControl(byte controller, long value) {
        
    }

    public abstract void updateRegisteredParameter(short rpn, int value);

    public abstract void updateMidi2RegisteredParameter(short rpn, long value);

    public abstract void updateNonRegisteredParameter(short nrpn, int value);

    public abstract void updateMidi2NonRegisteredParameter(short nrpn,
            long value);

}
