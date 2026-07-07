/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi.parameters;

import static sequence.midi.MidiConstants.GEN_2_DRUM_MSB;
import static sequence.midi.MidiConstants.MIDI_14_BIT_BIAS;
import static sequence.midi.MidiConstants.MIDI_7_BIT_BIAS;
import static sequence.midi.MidiConstants.MIDI_MASK;
import static sequence.midi.MidiConstants.MIDI_SHIFT;

/**
 *
 * @author Edward Jenkins
 */
public class Midi1Control extends MidiControl {

    // instance variables
    private short bankSelect;
    private boolean bankUpdated;
    private short modulation;
    private short breathController;
    private short footController;
    private short portamentoTime;
    private short dataEntry;
    private boolean dataEntryUpdated;
    private short channelVolume;
    private short balance;
    private short pan;
    private short channelExpression;
    private final short[] effectControls;
    private short[] generalPurpaceControllers;
    private final byte[] soundControllers;
    private byte highResolutionVelocity;
    // 1 is reverb, 2 is tremulant, 3 is chorus, 4 is detune, 
    // 5 is filter modulator
    private final byte[] effectDepths;
    private short nrParametricNumber;
    private boolean nrParameterUpdated;
    private short regParametricNumber;
    private boolean regParameterUpdated;

    // constructor
    public Midi1Control(byte channel, boolean rhythmControl, short bankSelect) {
        super(channel, rhythmControl);
        this.bankSelect = bankSelect;
        channelVolume = (short) 16383;
        breathController = (short) 16383;
        channelExpression = (short) 16383;
        pan = MIDI_14_BIT_BIAS;
        effectControls = new short[EFFECT_CONTROL_COUNT];
        generalPurpaceControllers = new short[GENERAL_PURPACE_CONTROLLER_COUNT];
        soundControllers = new byte[SOUND_CONTROLLER_COUNT];
        for (int i = 0; i < soundControllers.length; i++) {
            soundControllers[i] = MIDI_7_BIT_BIAS;
        }
        effectDepths = new byte[EFFECT_DEPTH_COUNT];
    }

    // getters
    public short getBankSelect() {
        return bankSelect;
    }

    public boolean isBankUpdated() {
        return bankUpdated;
    }

    public long getModulation() {
        return modulation;
    }

    public long getBreathController() {
        return breathController;
    }

    public long getFootController() {
        return footController;
    }

    public long getPortamentoTime() {
        return portamentoTime;
    }

    public long getDataEntry() {
        return dataEntry;
    }

    public long getChannelVolume() {
        return channelVolume;
    }

    public long getBalance() {
        return balance;
    }

    public long getPan() {
        return pan;
    }

    public long getChannelExpression() {
        return channelExpression;
    }

    public short[] getEffectControls() {
        return effectControls;
    }

    public short[] getGeneralPurpaceControllers() {
        return generalPurpaceControllers;
    }

    public byte[] getSoundControllers() {
        return soundControllers;
    }

    public byte getHighResolutionVelocity() {
        return highResolutionVelocity;
    }

    public byte[] getEffectDepths() {
        return effectDepths;
    }

    public short getNrParametricNumber() {
        return nrParametricNumber;
    }

    public short getRegParametricNumber() {
        return regParametricNumber;
    }

    // setter
    public void setBank(short bank) {
        short oldBank = bankSelect;
        bankSelect = bank;
        if (bankSelect != oldBank) {
            bankUpdated = true;
        }
    }

    public void setBankMsb(byte bankMsb) {
        if (getChannel() == 9 && bankMsb != 121) {
            bankMsb = GEN_2_DRUM_MSB;
        }
        this.setRhythmControl(bankMsb == GEN_2_DRUM_MSB);
        short oldBank = bankSelect;
        bankSelect &= MIDI_MASK;
        bankSelect |= (bankMsb << MIDI_SHIFT);
        if (bankSelect != oldBank) {
            bankUpdated = true;
        }
    }

    public void setBankLsb(byte bankLsb) {
        short oldBank = bankSelect;
        bankSelect &= (MIDI_MASK << MIDI_SHIFT);
        bankSelect |= bankLsb;
        if (bankSelect != oldBank) {
            bankUpdated = true;
        }
    }

    public void setBankUpdated(boolean bankUpdated) {
        this.bankUpdated = bankUpdated;
    }

    public void controlChange(byte control, byte value) {
        switch (control) {
            case BANK_MSB:
                // bank msb
                setBankMsb(value);
                break;
            case BANK_LSB:
                // bank lsb
                setBankLsb(value);
                break;
            case MODULATION_WHEEL_MSB:
                // modulation msb
                if (!isRhythmControl()) {
                    short oldModulation = modulation;
                    modulation &= MIDI_MASK;
                    modulation |= (value << MIDI_SHIFT);
                    if (oldModulation != modulation) {
                        setModWheelUpdated(true);
                    }
                }
                break;
            case MODULATION_WHEEL_LSB:
                // modulation lsb
                if (!isRhythmControl()) {
                    short oldModulation = modulation;
                    modulation &= (MIDI_MASK << MIDI_SHIFT);
                    modulation |= value;
                    if (oldModulation != modulation) {
                        setModWheelUpdated(true);
                    }
                }
                break;
            case BREATH_CONTROLLER_MSB:
                // breath controller msb
                short oldBreathController = breathController;
                breathController &= MIDI_MASK;
                breathController |= (value << MIDI_SHIFT);
                if (oldBreathController != breathController) {
                    setModWheelUpdated(true);
                }
                break;
            case BREATH_CONTROLLER_LSB:
                // breath controller  lsb
                oldBreathController = breathController;
                breathController &= (MIDI_MASK << MIDI_SHIFT);
                breathController |= value;
                if (oldBreathController != breathController) {
                    setBreathControlUpdated(true);
                }
                break;
            case FOOT_CONTROLLER_MSB:
                // foot controller msb
                short oldFootController = footController;
                footController &= MIDI_MASK;
                footController |= (value << MIDI_SHIFT);
                if (oldFootController != footController) {
                    setFootControllerUpdated(true);
                }
                break;
            case FOOT_CONTROLLER_LSB:
                // foot controller  lsb
                oldFootController = footController;
                footController &= (MIDI_MASK << MIDI_SHIFT);
                footController |= value;
                if (oldFootController != footController) {
                    setFootControllerUpdated(true);
                }
                break;
            case PORTAMENTO_TIME_MSB:
                // portamento time msb
                short oldPortamentoTime = portamentoTime;
                portamentoTime &= MIDI_MASK;
                portamentoTime |= (value << MIDI_SHIFT);
                if (oldPortamentoTime != portamentoTime) {
                    setPortamentoTimeUpdated(true);
                }
                break;
            case PORTAMENTO_TIME_LSB:
                // portamento time lsb
                oldPortamentoTime = portamentoTime;
                portamentoTime &= (MIDI_MASK << MIDI_SHIFT);
                portamentoTime |= value;
                if (oldPortamentoTime != portamentoTime) {
                    setPortamentoTimeUpdated(true);
                }
                break;
            case DATA_ENTRY_MSB:
                // data entry  msb
                short oldDataEntry = dataEntry;
                dataEntry &= MIDI_MASK;
                dataEntry |= (value << MIDI_SHIFT);
                if (oldDataEntry != dataEntry) {
                    dataEntryUpdated = true;
                }
                break;
            case DATA_ENTRY_LSB:
                // data entry lsb
                oldDataEntry = dataEntry;
                dataEntry &= (MIDI_MASK << MIDI_SHIFT);
                dataEntry |= value;
                if (oldDataEntry != dataEntry) {
                    dataEntryUpdated = true;
                }
                break;
            case CHANNEL_VOLUME_MSB:
                // channel volume msb
                short oldChannelVolume = channelVolume;
                channelVolume &= MIDI_MASK;
                channelVolume |= (value << MIDI_SHIFT);
                if (oldChannelVolume != channelVolume) {
                    setChannelVolumeUpdated(true);
                }
                break;
            case CHANNEL_VOLUME_LSB:
                // channel volume lsb
                oldChannelVolume = channelVolume;
                channelVolume &= (MIDI_MASK << MIDI_SHIFT);
                channelVolume |= value;
                if (oldChannelVolume != channelVolume) {
                    setChannelVolumeUpdated(true);
                }
                break;
            case BALANCE_MSB:
                // balance msb
                short oldBalance = balance;
                balance &= MIDI_MASK;
                balance |= (value << MIDI_SHIFT);
                if (oldBalance != balance) {
                    setBalanceUpdated(true);
                }
                break;
            case BALANCE_LSB:
                // balance lsb
                oldBalance = balance;
                balance &= (MIDI_MASK << MIDI_SHIFT);
                balance |= value;
                if (oldBalance != balance) {
                    setBalanceUpdated(true);
                }
                break;
            case PAN_MSB:
                // pan msb
                short oldPan = pan;
                pan &= MIDI_MASK;
                pan |= (value << MIDI_SHIFT);
                if (oldPan != pan) {
                    setPanUpdated(true);
                }
                break;
            case PAN_LSB:
                // pan lsb
                oldPan = pan;
                pan &= (MIDI_MASK << MIDI_SHIFT);
                pan |= value;
                if (oldPan != pan) {
                    setPanUpdated(true);
                }
                break;
            case EXPRESSION_MSB:
                // expression controler msb
                short oldExpression = channelExpression;
                channelExpression &= MIDI_MASK;
                channelExpression |= (value << MIDI_SHIFT);
                if (oldExpression != channelExpression) {
                    setExpressionUpdated(true);
                }
                break;
            case EXPRESSION_LSB:
                // expression controler lsb
                oldExpression = channelExpression;
                channelExpression &= (MIDI_MASK << MIDI_SHIFT);
                channelExpression |= value;
                if (oldExpression != channelExpression) {
                    setExpressionUpdated(true);
                }
                break;
            case EFFECT_CONTROL_1_MSB:
            case EFFECT_CONTROL_2_MSB:
                // effect control msb
                byte effectNumber = (byte) (control - EFFECT_CONTROL_1_MSB);
                short oldEffect = effectControls[effectNumber];
                effectControls[effectNumber] &= MIDI_MASK;
                effectControls[effectNumber] |= (value << MIDI_SHIFT);
                if (oldEffect != effectControls[effectNumber]) {
                    getEffectControlUpdates()[effectNumber] = true;
                }
                break;
            case EFFECT_CONTROL_1_LSB:
            case EFFECT_CONTROL_2_LSB:
                // effect control lsb
                effectNumber = (byte) (control - EFFECT_CONTROL_1_LSB);
                oldEffect = effectControls[effectNumber];
                effectControls[effectNumber] &= (MIDI_MASK << MIDI_SHIFT);
                effectControls[effectNumber] |= value;
                if (oldEffect != effectControls[effectNumber]) {
                    getEffectControlUpdates()[effectNumber] = true;
                }
                break;
            case GENERAL_PURPOSE_CONTROL_1_MSB:
            case GENERAL_PURPOSE_CONTROL_2_MSB:
            case GENERAL_PURPOSE_CONTROL_3_MSB:
            case GENERAL_PURPOSE_CONTROL_4_MSB:
                // general purpose control msb
                byte generalControlNumber = (byte) (control
                        - GENERAL_PURPOSE_CONTROL_1_MSB);
                short oldGeneralControl
                        = generalPurpaceControllers[generalControlNumber];
                generalPurpaceControllers[generalControlNumber] &= MIDI_MASK;
                generalPurpaceControllers[generalControlNumber] |= (value
                        << MIDI_SHIFT);
                if (oldGeneralControl
                        != generalPurpaceControllers[generalControlNumber]) {
                    getGeneralPurposeControlUpdates()[generalControlNumber]
                            = true;
                }
                break;
            case GENERAL_PURPOSE_CONTROL_1_LSB:
            case GENERAL_PURPOSE_CONTROL_2_LSB:
            case GENERAL_PURPOSE_CONTROL_3_LSB:
            case GENERAL_PURPOSE_CONTROL_4_LSB:
                // general purpose control lsb
                generalControlNumber = (byte) (control
                        - GENERAL_PURPOSE_CONTROL_1_LSB);
                oldGeneralControl
                        = generalPurpaceControllers[generalControlNumber];
                generalPurpaceControllers[generalControlNumber] &= (MIDI_MASK
                        << MIDI_SHIFT);
                generalPurpaceControllers[generalControlNumber] |= value;
                if (oldGeneralControl
                        != generalPurpaceControllers[generalControlNumber]) {
                    getGeneralPurposeControlUpdates()[generalControlNumber]
                            = true;
                }
                break;
            case GENERAL_PURPOSE_CONTROL_5:
            case GENERAL_PURPOSE_CONTROL_6:
            case GENERAL_PURPOSE_CONTROL_7:
            case GENERAL_PURPOSE_CONTROL_8:
                // general purpose control
                generalPurpaceControllers[control - 80] = value;
                break;
            case DAMPER_PEDAL:
                // damper pedal
                if (!isRhythmControl()) {
                    setDamperPedalOn(value >= 64);
                }
                break;
            case PORTAMENTO_SWITCH:
                // portamento
                if (!isRhythmControl()) {
                    setPortamentoSwitchOn(value >= 64);
                }
                break;
            case SOSTENUTO_PEDAL:
                // sustenuto pedal
                if (!isRhythmControl()) {
                    setSustenutoPedalOn(value >= 64);
                }
                break;
            case SOFT_PEDAL:
                // soft pedal
                if (!isRhythmControl()) {
                    setSoftPedalOn(value >= 64);
                }
                break;
            case LEGATO_FOOTSWITCH:
                // legato footswitch
                if (!isRhythmControl()) {
                    setLegatoFootswitchOn(value >= 64);
                }
                break;
            case HOLD_2:
                // hold 2
                if (!isRhythmControl()) {
                    setHoldTwoOn(value >= 64);
                }
                break;
            case PORTAMENTO_CONTROL:
                // portamento control
                setPortamentoNoteControl(value);
                break;
            case HIGH_RESOLUTION_VELOCITY:
                // high resolution velocity
                highResolutionVelocity = value;
            case EFFECT_REVERB_DEPTH:
            case EFFECT_TREMOLO_DEPTH:
            case EFFECT_CHORUS_DEPTH:
            case EFFECT_DETUNE_DEPTH:
            case EFFECT_PHASER_DEPTH:
                // effect depth
                byte effectDepthNumber = (byte) (control
                        - EFFECT_REVERB_DEPTH);
                byte oldEffectDepth
                        = effectDepths[effectDepthNumber];
                effectDepths[control - 91] = value;
                if (oldEffectDepth != effectDepths[effectDepthNumber]) {
                    getEffectDepthsUpdates()[effectDepthNumber] = true;
                }
                break;
            case DATA_INCREMENT:
                // data increment
                if (dataEntry + value <= 16383) {
                    dataEntry += value;
                } else {
                    dataEntry = 16383;
                }
                break;
            case DATA_DECREMENT:
                // data decrement
                if (dataEntry - value >= 0) {
                    dataEntry -= value;
                } else {
                    dataEntry = 0;
                }
                break;
            case NON_RPN_LSB:
                // non registered parametric number LSB
                nrParametricNumber &= (MIDI_MASK << MIDI_SHIFT);
                nrParametricNumber |= value;
                break;
            case NON_RPN_MSB:
                // non registered parametric number MSB
                nrParametricNumber &= MIDI_MASK;
                nrParametricNumber |= (value << MIDI_SHIFT);
                break;
            case RPN_LSB:
                // registered parametric number LSB
                regParametricNumber &= (MIDI_MASK << MIDI_SHIFT);
                regParametricNumber |= value;
                break;
            case RPN_MSB:
                // registered parametric number MSB
                regParametricNumber &= MIDI_MASK;
                regParametricNumber |= (value << MIDI_SHIFT);
                break;
            default:

                if (control >= SOUND_VARIATION_CONTROLLER
                        && control <= UNDEFINED_SOUND_CONTROLLER) {
                    byte soundControlNumber = (byte) (control
                            - SOUND_VARIATION_CONTROLLER);
                    byte oldSoundControl
                            = soundControllers[soundControlNumber];
                    // sound controllers
                    soundControllers[soundControlNumber] = value;
                    if (oldSoundControl != soundControllers[soundControlNumber]) {
                        getSoundControlUpdates()[soundControlNumber] = true;
                    }
                }

                break;
        }
    }

    @Override
    public void reset() {
        super.reset();
        modulation = 0;
        channelExpression = (short) 16383;
        regParametricNumber = (short) 16383;
        pan = 8192;
    }

    @Override
    public void controlChange(byte control, long value) {
        controlChange(control, (byte) value);
    }

    @Override
    public long getEffectControls(byte index) {
        return effectControls[index];
    }

    @Override
    public long getGeneralPurpaceControllers(byte index) {
        return generalPurpaceControllers[index];
    }

    @Override
    public long getSoundControllers(byte index) {
        return soundControllers[index];
    }

    @Override
    public long getEffectDepths(byte index) {
        return effectDepths[index];
    }
}
