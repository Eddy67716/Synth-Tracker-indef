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
public class Midi1NonRegisteredParameters extends NonRegisteredParameters{

    // instance variables
    // default accumulators
    private short nonRegisteredEventType;
    // rotary accumulators
    private final short[] eventAccumulators;
    

    public Midi1NonRegisteredParameters() {
        super();
        eventAccumulators = new short[EVENT_COUNT];
    }

    // getters
    public short getNonRegisteredEventType() {
        return nonRegisteredEventType;
    }

    public short[] getEventAccumulators() {
        return eventAccumulators;
    }

    @Override
    public short getEventType() {
        return getNonRegisteredEventType();
    }

    @Override
    public long getEventAccumulator(short id) {
        return eventAccumulators[id];
    }

    // setters
    public void setNonRegisteredEventType(short nonRegisteredEventType) {
        this.nonRegisteredEventType = nonRegisteredEventType;
    }

    @Override
    public void setEventType(short eventType) {
        setNonRegisteredEventType(eventType);
    }

    @Override
    public void setEventMSB(byte registeredEventMSB) {
        nonRegisteredEventType &= MIDI_MASK;
        nonRegisteredEventType |= (registeredEventMSB << 7);
    }

    @Override
    public void setEventLSB(byte registeredEventLSB) {
        nonRegisteredEventType &= MIDI_MASK << 7;
        nonRegisteredEventType |= (registeredEventLSB);
    }

    @Override
    public void setDataDirectly(short eventNo, long value) {
        short oldEventAccumulator = eventAccumulators[nonRegisteredEventType];
        eventAccumulators[eventNo] = (short)value;
        getEventUpdates()[nonRegisteredEventType] = (oldEventAccumulator 
                != eventAccumulators[nonRegisteredEventType]);
    }

    @Override
    public void paremeterNumberChange(boolean msb, byte value) {
        short oldEventAccumulator = eventAccumulators[nonRegisteredEventType];
        if (msb) {
            eventAccumulators[nonRegisteredEventType] &= MIDI_MASK;
            eventAccumulators[nonRegisteredEventType]
                    |= (value << 7);
        } else {
            eventAccumulators[nonRegisteredEventType]
                    &= MIDI_MASK << 7;
            eventAccumulators[nonRegisteredEventType] |= (value);
        }
        getEventUpdates()[nonRegisteredEventType] = (oldEventAccumulator 
                != eventAccumulators[nonRegisteredEventType]);
    }

    @Override
    public void incrementData(byte value) {
        // add to accumulator
        short oldEventAccumulator = eventAccumulators[nonRegisteredEventType];
        eventAccumulators[nonRegisteredEventType] -= value;
        if (eventAccumulators[nonRegisteredEventType] > 16383) {
            eventAccumulators[nonRegisteredEventType] = 16383;
        }
        getEventUpdates()[nonRegisteredEventType] = (oldEventAccumulator 
                != eventAccumulators[nonRegisteredEventType]);
    }

    @Override
    public void decrementData(byte value) {
        // add to accumulator
        short oldEventAccumulator = eventAccumulators[nonRegisteredEventType];
        eventAccumulators[nonRegisteredEventType] -= value;
        if (eventAccumulators[nonRegisteredEventType] < 0) {
            eventAccumulators[nonRegisteredEventType] = 0;
        }
        getEventUpdates()[nonRegisteredEventType] = (oldEventAccumulator 
                != eventAccumulators[nonRegisteredEventType]);
    }
}
