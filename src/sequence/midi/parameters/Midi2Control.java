/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi.parameters;

import static sequence.midi.MidiConstants.MIDI_2_BIAS;

/**
 *
 * @author eddy6
 */
public class Midi2Control extends MidiControl{
    
    // instance variables
    private long modulation;
    private long breathController;
    private long footController;
    private long portamentoTime;
    private long channelVolume;
    private long balance;
    private long pan;
    private long channelExpression;
    private long[] effectControls;
    private long[] generalPurpaceControllers;
    private long[] soundControllers;
    // 1 is reverb, 2 is tremulant, 3 is chorus, 4 is detune, 
    // 5 is filter modulator
    private long[] effectDepths;
    
    public Midi2Control(byte channel, boolean rhythmControl) {
        super(channel, rhythmControl);
        channelVolume = 4294967295l;
        breathController = 4294967295l;
        channelExpression = 4294967295l;
        pan = MIDI_2_BIAS;
        effectControls = new long[EFFECT_CONTROL_COUNT];
        generalPurpaceControllers = new long[GENERAL_PURPACE_CONTROLLER_COUNT];
        soundControllers = new long[SOUND_CONTROLLER_COUNT];
        for (int i = 0; i < soundControllers.length; i++) {
            soundControllers[i] = MIDI_2_BIAS;
        }
        effectDepths = new long[EFFECT_DEPTH_COUNT];
    }

    @Override
    public void controlChange(byte control, long value) {
        switch (control) {
            case MODULATION_WHEEL_MSB:
                // modulation msb
                if (!isRhythmControl()) {
                    long oldModulation = modulation;
                    modulation = value;
                    if (oldModulation != modulation) {
                        setModWheelUpdated(true);
                    }
                }
                break;
            case BREATH_CONTROLLER_MSB:
                // breath controller msb
                long oldBreathController = breathController;
                breathController = value;
                if (oldBreathController != breathController) {
                    setModWheelUpdated(true);
                }
                break;
            case FOOT_CONTROLLER_MSB:
                // foot controller msb
                long oldFootController = footController;
                footController = value;
                if (oldFootController != footController) {
                    setFootControllerUpdated(true);
                }
                break;
            case PORTAMENTO_TIME_MSB:
                // portamento time msb
                long oldPortamentoTime = portamentoTime;
                portamentoTime = value;
                if (oldPortamentoTime != portamentoTime) {
                    setPortamentoTimeUpdated(true);
                }
                break;
            case CHANNEL_VOLUME_MSB:
                // channel volume msb
                long oldChannelVolume = channelVolume;
                channelVolume = value;
                if (oldChannelVolume != channelVolume) {
                    setChannelVolumeUpdated(true);
                }
                break;
            case BALANCE_MSB:
                // balance msb
                long oldBalance = balance;
                balance = value;
                if (oldBalance != balance) {
                    setBalanceUpdated(true);
                }
                break;
            case PAN_MSB:
                // pan msb
                long oldPan = pan;
                pan = value;
                if (oldPan != pan) {
                    setPanUpdated(true);
                }
                break;
            case EXPRESSION_MSB:
                // expression controler msb
                long oldExpression = channelExpression;
                channelExpression = value;
                if (oldExpression != channelExpression) {
                    setExpressionUpdated(true);
                }
                break;
            case EFFECT_CONTROL_1_MSB:
            case EFFECT_CONTROL_2_MSB:
                // effect control msb
                byte effectNumber = (byte) (control - EFFECT_CONTROL_1_MSB);
                long oldEffect = effectControls[effectNumber];
                effectControls[effectNumber] = value;;
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
                long oldGeneralControl
                        = generalPurpaceControllers[generalControlNumber];
                generalPurpaceControllers[generalControlNumber] = value;
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
                setPortamentoNoteControl((byte)value);
                break;
            case EFFECT_REVERB_DEPTH:
            case EFFECT_TREMOLO_DEPTH:
            case EFFECT_CHORUS_DEPTH:
            case EFFECT_DETUNE_DEPTH:
            case EFFECT_PHASER_DEPTH:
                // effect depth
                byte effectDepthNumber = (byte) (control
                        - EFFECT_REVERB_DEPTH);
                long oldEffectDepth
                        = effectDepths[effectDepthNumber];
                effectDepths[control - 91] = value;
                if (oldEffectDepth != effectDepths[effectDepthNumber]) {
                    getEffectDepthsUpdates()[effectDepthNumber] = true;
                }
                break;
            default:

                if (control >= SOUND_VARIATION_CONTROLLER
                        && control <= UNDEFINED_SOUND_CONTROLLER) {
                    byte soundControlNumber = (byte) (control
                            - SOUND_VARIATION_CONTROLLER);
                    long oldSoundControl
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
    public long getModulation() {
        return modulation;
    }

    @Override
    public long getBreathController() {
        return breathController;
    }

    @Override
    public long getFootController() {
        return footController;
    }

    @Override
    public long getPortamentoTime() {
        return portamentoTime;
    }

    @Override
    public long getChannelVolume() {
        return channelVolume;
    }

    @Override
    public long getBalance() {
        return balance;
    }

    @Override
    public long getPan() {
        return pan;
    }

    @Override
    public long getChannelExpression() {
        return channelExpression;
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
