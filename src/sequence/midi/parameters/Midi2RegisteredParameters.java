/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi.parameters;

import static sequence.midi.MidiConstants.MIDI_MASK;
import static sequence.midi.parameters.RegisteredParameters.DRAWBAR_LENGTH;
import static sequence.midi.parameters.RegisteredParameters.FIRST_DRAWBAR_RPN;
import static sequence.midi.parameters.RegisteredParameters.FIRST_ROTARY_RPN;
import static sequence.midi.parameters.RegisteredParameters.LAST_DRAWBAR_RPN;
import static sequence.midi.parameters.RegisteredParameters.LAST_ROTARY_RPN;
import static sequence.midi.parameters.RegisteredParameters.ROTARY_LENGTH;

/**
 *
 * @author eddy6
 */
public class Midi2RegisteredParameters extends RegisteredParameters {
    
    // instance variables
    // hammond organ RPN accumulators
    private final long[] drawbarOrganEventAccumulators;
    // rotary accumulators
    private final long[] rotaryEventAccumulators;

    public Midi2RegisteredParameters() {
        drawbarOrganEventAccumulators = new long[DRAWBAR_LENGTH];
        rotaryEventAccumulators = new long[ROTARY_LENGTH];
    }

    @Override
    public long getDrawbarOrganEventAccumulator(int index) {

        long returnValue = 0;

        if (getRegisteredEventType() >= FIRST_DRAWBAR_RPN
                && getRegisteredEventType() <= LAST_DRAWBAR_RPN) {
            index -= FIRST_DRAWBAR_RPN;
            returnValue = drawbarOrganEventAccumulators[index];
        }

        return returnValue;
    }

    @Override
    public long getRotaryEventAccumulator(int index) {
        long returnValue = 0;

        if (getRegisteredEventType() >= FIRST_ROTARY_RPN
                && getRegisteredEventType() <= LAST_ROTARY_RPN) {
            index -= FIRST_ROTARY_RPN;
            returnValue = rotaryEventAccumulators[index];
        }

        return returnValue;
    }

    @Override
    public void paremeterNumberChange(boolean msb, byte value) {
        super.paremeterNumberChange(msb, value);
        if (getRegisteredEventType() >= FIRST_DRAWBAR_RPN
                && getRegisteredEventType() <= LAST_DRAWBAR_RPN) {
            // drawbar organ RPNs
            int drawbarIndex = getRegisteredEventType() - FIRST_DRAWBAR_RPN;
            if (msb) {
                drawbarOrganEventAccumulators[drawbarIndex] &= MIDI_MASK;
                drawbarOrganEventAccumulators[drawbarIndex]
                        |= (value << 7);
            } else {
                drawbarOrganEventAccumulators[drawbarIndex]
                        &= MIDI_MASK << 7;
                drawbarOrganEventAccumulators[drawbarIndex] |= (value);
            }
        } else if (getRegisteredEventType() >= FIRST_ROTARY_RPN
                && getRegisteredEventType() <= LAST_ROTARY_RPN) {
            // drawbar organ RPNs
            int rotaryIndex = getRegisteredEventType() - FIRST_ROTARY_RPN;
            if (msb) {
                rotaryEventAccumulators[rotaryIndex] &= MIDI_MASK;
                rotaryEventAccumulators[rotaryIndex]
                        |= (value << 7);
            } else {
                rotaryEventAccumulators[rotaryIndex]
                        &= MIDI_MASK << 7;
                rotaryEventAccumulators[rotaryIndex] |= (value);
            }
        }
    }

    @Override
    public void decrementData(byte value) {
        super.decrementData(value);
        if (getRegisteredEventType() >= FIRST_DRAWBAR_RPN
                && getRegisteredEventType() <= LAST_DRAWBAR_RPN) {
            // drawbar organ RPNs
            int drawbarIndex = getRegisteredEventType() - FIRST_DRAWBAR_RPN;
            if (drawbarOrganEventAccumulators[drawbarIndex] + value > 16383) {
                drawbarOrganEventAccumulators[drawbarIndex] = 16383;
            } else {
                drawbarOrganEventAccumulators[drawbarIndex] += value;
            }
        } else if (getRegisteredEventType() >= FIRST_ROTARY_RPN
                && getRegisteredEventType() <= LAST_ROTARY_RPN) {
            // drawbar organ RPNs
            int rotaryIndex = getRegisteredEventType() - FIRST_ROTARY_RPN;
            if (rotaryEventAccumulators[rotaryIndex] + value > 16383) {
                rotaryEventAccumulators[rotaryIndex] = 16383;
            } else {
                rotaryEventAccumulators[rotaryIndex] += value;
            }
        }
    }

    @Override
    public void incrementData(byte value) {
        super.incrementData(value);
        if (getRegisteredEventType() >= FIRST_DRAWBAR_RPN
                && getRegisteredEventType() <= LAST_DRAWBAR_RPN) {
            // drawbar organ RPNs
            int drawbarIndex = getRegisteredEventType() - FIRST_DRAWBAR_RPN;
            if (drawbarOrganEventAccumulators[drawbarIndex] - value < 0) {
                drawbarOrganEventAccumulators[drawbarIndex] = 0;
            } else {
                drawbarOrganEventAccumulators[drawbarIndex] -= value;
            }
        } else if (getRegisteredEventType() >= FIRST_ROTARY_RPN
                && getRegisteredEventType() <= LAST_ROTARY_RPN) {
            // drawbar organ RPNs
            int rotaryIndex = getRegisteredEventType() - FIRST_ROTARY_RPN;
            if (rotaryEventAccumulators[rotaryIndex] + value > 16383) {
                rotaryEventAccumulators[rotaryIndex] = 16383;
            } else {
                rotaryEventAccumulators[rotaryIndex] += value;
            }
        }
    }
}
