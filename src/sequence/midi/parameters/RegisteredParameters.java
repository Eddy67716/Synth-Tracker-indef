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
public abstract class RegisteredParameters implements IParameterUpdatable {

    // constants
    public static final byte INITIAL_MODULATOR_OFFSET = 64;
    public static final byte PITCH_BEND = 0;
    public static final byte FINE_TUNING = 1;
    public static final byte COARSE_TUNING = 2;
    public static final byte MODULATION_DEPTH = 5;
    public static final short FIRST_DRAWBAR_RPN = 4096;
    public static final short DRAWBAR_16 = FIRST_DRAWBAR_RPN;
    public static final short DRAWBAR_5_13 = FIRST_DRAWBAR_RPN + 1;
    public static final short DRAWBAR_8 = FIRST_DRAWBAR_RPN + 2;
    public static final short DRAWBAR_4 = FIRST_DRAWBAR_RPN + 3;
    public static final short DRAWBAR_2_23 = FIRST_DRAWBAR_RPN + 4;
    public static final short DRAWBAR_2 = FIRST_DRAWBAR_RPN + 5;
    public static final short DRAWBAR_1_35 = FIRST_DRAWBAR_RPN + 6;
    public static final short DRAWBAR_1_13 = FIRST_DRAWBAR_RPN + 7;
    public static final short DRAWBAR_1 = FIRST_DRAWBAR_RPN + 8;
    public static final short VIB_CHOR_TYPE = FIRST_DRAWBAR_RPN + 9;
    public static final short VIB_CHOR_ENABLED = FIRST_DRAWBAR_RPN + 10;
    public static final short PERCUS_ENABLED = FIRST_DRAWBAR_RPN + 11;
    public static final short PERCUS_VOLUME = FIRST_DRAWBAR_RPN + 12;
    public static final short PERCUS_SPEED = FIRST_DRAWBAR_RPN + 13;
    public static final short PERCUS_TYPE = FIRST_DRAWBAR_RPN + 14;
    public static final short KEY_CLICK_AMOUNT = FIRST_DRAWBAR_RPN + 15;
    public static final short LEAKAGE_AMOUNT = FIRST_DRAWBAR_RPN + 16;
    public static final short LAST_DRAWBAR_RPN = 4161;
    public static final byte DRAWBAR_LENGTH = (byte) (LAST_DRAWBAR_RPN
            - FIRST_DRAWBAR_RPN);
    public static final short FIRST_ROTARY_RPN = 6176;
    public static final short ROTARY_SPEED = FIRST_ROTARY_RPN;
    public static final short ROTARY_EFFECT = FIRST_ROTARY_RPN + 1;
    public static final short ROTARY_BREAK = FIRST_ROTARY_RPN + 2;
    public static final short HORN_SLOW_SPEED = FIRST_ROTARY_RPN + 3;
    public static final short HORN_FAST_SPEED = FIRST_ROTARY_RPN + 4;
    public static final short WOOFER_SLOW_SPEED = FIRST_ROTARY_RPN + 5;
    public static final short WOOFER_FAST_SPEED = FIRST_ROTARY_RPN + 6;
    public static final short HORN_ACCELERATE = FIRST_ROTARY_RPN + 7;
    public static final short HORN_DECELERATE = FIRST_ROTARY_RPN + 8;
    public static final short WOOFER_ACCELERATE = FIRST_ROTARY_RPN + 9;
    public static final short WOOFER_DECELERATE = FIRST_ROTARY_RPN + 10;
    public static final short HORN_LEVEL = FIRST_ROTARY_RPN + 11;
    public static final short WOOFER_LEVEL = FIRST_ROTARY_RPN + 12;
    public static final short OVERDRIVE_AMOUNT = FIRST_ROTARY_RPN + 13;
    public static final short LAST_ROTARY_RPN = 6189;
    public static final byte ROTARY_LENGTH = (byte) (LAST_ROTARY_RPN
            - FIRST_ROTARY_RPN);

    // instance variables
    // default accumulators
    private short registeredEventType;
    private short pitchBendEventAccumulator;
    private boolean pitchBendEventUpdated;
    private short fineTuneEventAccumulator;
    private boolean fineTuneEventUpdated;
    private byte coarseTuneEventAccumulator;
    private boolean coarseTuneEventUpdated;
    // GM 2 event
    private short modulatorDepthEventAccumulator;
    private boolean modulatorDepthEventUpdated;

    public RegisteredParameters() {
        modulatorDepthEventAccumulator = INITIAL_MODULATOR_OFFSET;
    }

    // getters
    public short getRegisteredEventType() {
        return registeredEventType;
    }

    @Override
    public short getEventType() {
        return getRegisteredEventType();
    }

    public short getPitchBendEventAccumulator() {
        return pitchBendEventAccumulator;
    }

    public boolean isPitchBendEventUpdated() {
        return pitchBendEventUpdated;
    }

    public short getFineTuneEventAccumulator() {
        return fineTuneEventAccumulator;
    }

    public boolean isFineTuneEventUpdated() {
        return fineTuneEventUpdated;
    }

    public short getCoarseTuneEventAccumulator() {
        return coarseTuneEventAccumulator;
    }

    public boolean isCoarseTuneEventUpdated() {
        return coarseTuneEventUpdated;
    }

    public short getModulatorDepthEventAccumulator() {
        return modulatorDepthEventAccumulator;
    }

    public boolean isModulatorDepthEventUpdated() {
        return modulatorDepthEventUpdated;
    }

    public abstract long getDrawbarOrganEventAccumulator(int index);

    public abstract long getRotaryEventAccumulator(int index);

    // setters
    public void setRegisteredEventType(short registeredEventType) {
        this.registeredEventType = registeredEventType;
    }

    @Override
    public void setEventType(short eventType) {
        setRegisteredEventType(eventType);
    }

    @Override
    public void setEventMSB(byte registeredEventMSB) {
        registeredEventType &= MIDI_MASK;
        registeredEventType |= (registeredEventMSB << 7);
    }

    @Override
    public void setEventLSB(byte registeredEventLSB) {
        registeredEventType &= MIDI_MASK << 7;
        registeredEventType |= (registeredEventLSB);
    }

    public void setPitchBendEventAccumulator(short pitchBendEventAccumulator) {
        this.pitchBendEventAccumulator = pitchBendEventAccumulator;
    }

    public void setFineTuneEventAccumulator(short fineTuneEventAccumulator) {
        this.fineTuneEventAccumulator = fineTuneEventAccumulator;
    }

    public void setCoarseTuneEventAccumulator(byte coarseTuneEventAccumulator) {
        this.coarseTuneEventAccumulator = coarseTuneEventAccumulator;
    }

    public void setModulatorDepthEventAccumulator(short modulatorDepthEventAccumulator) {
        this.modulatorDepthEventAccumulator = modulatorDepthEventAccumulator;
    }

    @Override
    public void paremeterNumberChange(boolean msb, byte value) {
        // registered parameter
        switch (registeredEventType) {
            case 0:
                // pitch bend
                short oldPitchBendEventAccumulator = pitchBendEventAccumulator;
                if (msb) {
                    pitchBendEventAccumulator &= MIDI_MASK;
                    pitchBendEventAccumulator |= (value << 7);
                } else {
                    pitchBendEventAccumulator &= MIDI_MASK << 7;
                    pitchBendEventAccumulator |= (value);
                }
                pitchBendEventUpdated = (oldPitchBendEventAccumulator
                        != pitchBendEventAccumulator);
                break;
            case 1:
                // fine retune
                short oldFineTuneEventAccumulator = fineTuneEventAccumulator;
                if (msb) {
                    fineTuneEventAccumulator &= MIDI_MASK;
                    fineTuneEventAccumulator |= (value << 7);
                } else {
                    fineTuneEventAccumulator &= MIDI_MASK << 7;
                    fineTuneEventAccumulator |= (value);
                }
                fineTuneEventUpdated = (oldFineTuneEventAccumulator
                        != fineTuneEventAccumulator);
                break;
            case 2:
                // coarse retune
                if (msb) {
                    coarseTuneEventAccumulator = value;
                }
            case 5:
                // modulator max depth
                short oldModDepthEventAccumulator 
                        = modulatorDepthEventAccumulator;
                if (msb) {
                    modulatorDepthEventAccumulator &= MIDI_MASK;
                    modulatorDepthEventAccumulator |= (value << 7);
                } else {
                    modulatorDepthEventAccumulator &= MIDI_MASK << 7;
                    modulatorDepthEventAccumulator |= (value);
                }
                modulatorDepthEventUpdated = (oldModDepthEventAccumulator
                        != modulatorDepthEventAccumulator);
                break;
            default:
                break;
        }
    }

    @Override
    public void setDataDirectly(short rpn, long value) {
        registeredEventType = rpn;
        switch (registeredEventType) {
            case 0:
                // pitch bend
                short oldPitchBendEventAccumulator = pitchBendEventAccumulator;
                pitchBendEventAccumulator = (short) (value >>> 18);
                pitchBendEventUpdated = (oldPitchBendEventAccumulator
                        != pitchBendEventAccumulator);
                break;
            case 1:
                // fine retune
                short oldFineTuneEventAccumulator = fineTuneEventAccumulator;
                fineTuneEventAccumulator = (short) (value >>> 18);
                fineTuneEventUpdated = (oldFineTuneEventAccumulator
                        != fineTuneEventAccumulator);
                break;
            case 2:
                // coarse retune
                coarseTuneEventAccumulator = (byte) (value >>> 25);
            case 5:
                // modulator max depth
                modulatorDepthEventAccumulator = (short) (value >>> 18);
                break;
            default:
                break;
        }
    }

    @Override
    public void incrementData(byte value) {
        // add to appropriate accumulator
        switch (registeredEventType) {
            case 0:
                // pitch bend
                pitchBendEventAccumulator += value;
                if (pitchBendEventAccumulator > 16383) {
                    pitchBendEventAccumulator = 16383;
                }
                break;
            case 1:
                // fine retune
                fineTuneEventAccumulator += value;
                if (fineTuneEventAccumulator > 16383) {
                    fineTuneEventAccumulator = 16383;
                }
                break;
            case 2:
                // coarse retune
                if (coarseTuneEventAccumulator + value > 127) {
                    coarseTuneEventAccumulator = 127;
                } else {
                    coarseTuneEventAccumulator += value;
                }
            case 5:
                // modulator max depth
                modulatorDepthEventAccumulator += value;
                if (modulatorDepthEventAccumulator > 16383) {
                    modulatorDepthEventAccumulator = 16383;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void decrementData(byte value) {
        // subtract from appropriate accumulator
        switch (registeredEventType) {
            case 0:
                // pitch bend
                pitchBendEventAccumulator -= value;
                if (pitchBendEventAccumulator < 0) {
                    pitchBendEventAccumulator = 0;
                }
                break;
            case 1:
                // fine retune
                fineTuneEventAccumulator -= value;
                if (fineTuneEventAccumulator < 0) {
                    fineTuneEventAccumulator = 0;
                }
                break;
            case 2:
                // coarse retune
                if (coarseTuneEventAccumulator - value < 0) {
                    coarseTuneEventAccumulator = 0;
                } else {
                    coarseTuneEventAccumulator -= value;
                }
            case 5:
                // modulator max depth
                modulatorDepthEventAccumulator -= value;
                if (modulatorDepthEventAccumulator < 0) {
                    modulatorDepthEventAccumulator = 0;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void notifyEffectsUpdated() {
        pitchBendEventUpdated = fineTuneEventUpdated = coarseTuneEventUpdated
                = modulatorDepthEventUpdated = false;
    }
}
