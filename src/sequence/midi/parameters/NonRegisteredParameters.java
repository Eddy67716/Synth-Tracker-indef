/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi.parameters;

import static sequence.midi.MidiConstants.MIDI_MASK;

/**
 *
 * @author eddy6
 */
public abstract class NonRegisteredParameters implements IParameterUpdatable {

    // constants
    public static final short EVENT_COUNT = 16384;
    
    // parameter updates
    private final boolean[] eventUpdates;
    
    public NonRegisteredParameters() {
        eventUpdates = new boolean[EVENT_COUNT];
    }

    public boolean[] getEventUpdates() {
        return eventUpdates;
    }

    public abstract long getEventAccumulator(short id);
    
    
    @Override
    public void notifyEffectsUpdated() {
        for (int i = 0; i < EVENT_COUNT; i++) {
            if (eventUpdates[i]) {
                eventUpdates[i] = false;
            }
        }
    }
}
