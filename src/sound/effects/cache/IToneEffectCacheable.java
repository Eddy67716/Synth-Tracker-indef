/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sound.effects.cache;

import sequence.midi.handlers.MidiParameterHandler;

/**
 *
 * @author eddy6
 */
public interface IToneEffectCacheable extends IEffectCacheable {
    
    public short getPlayingNotes();

    public short getActiveNotes();

    public byte getKeyCount();

    public MidiParameterHandler getMidiEffects();
    
    public boolean isTopLevelCache();
    
    public void setTopLevelCache(boolean topLevelCache);
    
    public void tickUpdateCache();

    public void incrementPlayingNotes();

    public void incrementActiveNotes();

    public void incrementKeyCount();

    public void decrementPlayingNotes();

    public void decrementActiveNotes();

    public void decrementKeyCount();
}
