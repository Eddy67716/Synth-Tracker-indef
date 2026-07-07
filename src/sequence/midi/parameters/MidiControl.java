/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi.parameters;

/**
 *
 * @author eddy6
 */
public abstract class MidiControl {

    // constants
    public static final byte EFFECT_CONTROL_COUNT = 2;
    public static final byte GENERAL_PURPACE_CONTROLLER_COUNT = 8;
    public static final byte SOUND_CONTROLLER_COUNT = 10;
    public static final byte EFFECT_DEPTH_COUNT = 5;
    // controller values
    // MSB values
    // bank not used here in Midi 2
    public static final byte BANK_MSB = 0;
    public static final byte MODULATION_WHEEL_MSB = 1;
    public static final byte BREATH_CONTROLLER_MSB = 2;
    public static final byte FOOT_CONTROLLER_MSB = 4;
    public static final byte PORTAMENTO_TIME_MSB = 5;
    // this data entry is not used in Midi 2
    public static final byte DATA_ENTRY_MSB = 6;
    public static final byte CHANNEL_VOLUME_MSB = 7;
    public static final byte BALANCE_MSB = 8;
    public static final byte PAN_MSB = 10;
    public static final byte EXPRESSION_MSB = 11;
    public static final byte EFFECT_CONTROL_1_MSB = 12;
    public static final byte EFFECT_CONTROL_2_MSB = 13;
    public static final byte GENERAL_PURPOSE_CONTROL_1_MSB = 16;
    public static final byte GENERAL_PURPOSE_CONTROL_2_MSB = 17;
    public static final byte GENERAL_PURPOSE_CONTROL_3_MSB = 18;
    public static final byte GENERAL_PURPOSE_CONTROL_4_MSB = 19;
    // LSB values (Ignored in Midi 2 due to it being tricky to deal with unsigned longs in java)
    public static final byte BANK_LSB = 32;
    public static final byte MODULATION_WHEEL_LSB = 33;
    public static final byte BREATH_CONTROLLER_LSB = 34;
    public static final byte FOOT_CONTROLLER_LSB = 36;
    public static final byte PORTAMENTO_TIME_LSB = 37;
    // this data entry is not used in Midi 2
    public static final byte DATA_ENTRY_LSB = 38;
    public static final byte CHANNEL_VOLUME_LSB = 39;
    public static final byte BALANCE_LSB = 40;
    public static final byte PAN_LSB = 42;
    public static final byte EXPRESSION_LSB = 43;
    public static final byte EFFECT_CONTROL_1_LSB = 44;
    public static final byte EFFECT_CONTROL_2_LSB = 45;
    public static final byte GENERAL_PURPOSE_CONTROL_1_LSB = 48;
    public static final byte GENERAL_PURPOSE_CONTROL_2_LSB = 49;
    public static final byte GENERAL_PURPOSE_CONTROL_3_LSB = 50;
    public static final byte GENERAL_PURPOSE_CONTROL_4_LSB = 51;
    // togglable values
    public static final byte DAMPER_PEDAL = 64;
    public static final byte PORTAMENTO_SWITCH = 65;
    public static final byte SOSTENUTO_PEDAL = 66;
    public static final byte SOFT_PEDAL = 67;
    public static final byte LEGATO_FOOTSWITCH = 68;
    public static final byte HOLD_2 = 69;
    // sound controllers
    public static final byte SOUND_VARIATION_CONTROLLER = 70;
    public static final byte FILTER_REZ_CONTROLLER = 71;
    public static final byte RELEASE_TIME_CONTROLLER = 72;
    public static final byte ATTACK_TIME_CONTROLLER = 73;
    public static final byte FILTER_CUTOFF_CONTROLLER = 74;
    // GM 2 sound controllers
    public static final byte DECAY_TIME_CONTROLLER = 75;
    public static final byte VIBRATO_RATE_CONTROLLER = 76;
    public static final byte VIBRATO_DEPTH_CONTROLLER = 77;
    public static final byte VIBRATO_DELAY_CONTROLLER = 78;
    public static final byte UNDEFINED_SOUND_CONTROLLER = 79;
    // more general purpose controllers
    public static final byte GENERAL_PURPOSE_CONTROL_5 = 80;
    public static final byte GENERAL_PURPOSE_CONTROL_6 = 81;
    public static final byte GENERAL_PURPOSE_CONTROL_7 = 82;
    public static final byte GENERAL_PURPOSE_CONTROL_8 = 83;
    public static final byte PORTAMENTO_CONTROL = 84;
    // High res velocity is not used in Midi 2
    public static final byte HIGH_RESOLUTION_VELOCITY = 88;
    // effect values
    public static final byte EFFECT_REVERB_DEPTH = 91;
    public static final byte EFFECT_TREMOLO_DEPTH = 92;
    public static final byte EFFECT_CHORUS_DEPTH = 93;
    public static final byte EFFECT_DETUNE_DEPTH = 94;
    public static final byte EFFECT_PHASER_DEPTH = 95;
    // not used in Midi 2
    public static final byte DATA_INCREMENT = 96;
    public static final byte DATA_DECREMENT = 97;
    public static final byte NON_RPN_LSB = 98;
    public static final byte NON_RPN_MSB = 99;
    public static final byte RPN_LSB = 100;
    public static final byte RPN_MSB = 101;

    // instance variables
    private final byte channel;
    private boolean rhythmControl;
    private boolean modWheelUpdated;
    private boolean breathControlUpdated;
    private boolean footControllerUpdated;
    private boolean portamentoTimeUpdated;
    private boolean channelVolumeUpdated;
    private boolean balanceUpdated;
    private boolean panUpdated;
    private boolean expressionUpdated;
    private final boolean[] effectControlUpdates;
    private final boolean[] generalPurposeControlUpdates;
    private boolean damperPedalOn;
    private boolean damplerPedalUpdated;
    private boolean portamentoSwitchOn;
    private boolean portamentoSwitchUpdated;
    private boolean sustenutoPedalOn;
    private final boolean[] sustenutoEnabledNotes;
    private boolean sustenutoPedalUpdated;
    private boolean softPedalOn;
    private boolean softPedalUpdated;
    private boolean legatoFootswitchOn;
    private boolean legatoFootswitchUpdated;
    private boolean holdTwoOn;
    private boolean holdTwoUpdated;
    private final boolean[] soundControlUpdates;
    private byte portamentoNoteControl;
    private boolean portamentoNoteUpdated;
    private final boolean[] effectDepthsUpdates;

    // constructor
    public MidiControl(byte channel, boolean rhythmControl) {
        this.channel = channel;
        this.rhythmControl = rhythmControl;
        effectControlUpdates = new boolean[EFFECT_CONTROL_COUNT];
        generalPurposeControlUpdates
                = new boolean[GENERAL_PURPACE_CONTROLLER_COUNT];
        sustenutoEnabledNotes = new boolean[128];
        soundControlUpdates = new boolean[SOUND_CONTROLLER_COUNT];
        effectDepthsUpdates = new boolean[EFFECT_DEPTH_COUNT];
    }

    // getters
    public byte getChannel() {
        return channel;
    }

    public boolean isRhythmControl() {
        return rhythmControl;
    }

    public boolean isModWheelUpdated() {
        return modWheelUpdated;
    }

    public boolean isBreathControlUpdated() {
        return breathControlUpdated;
    }

    public boolean isFootControllerUpdated() {
        return footControllerUpdated;
    }

    public boolean isPortamentoTimeUpdated() {
        return portamentoTimeUpdated;
    }

    public boolean isChannelVolumeUpdated() {
        return channelVolumeUpdated;
    }

    public boolean isBalanceUpdated() {
        return balanceUpdated;
    }

    public boolean isPanUpdated() {
        return panUpdated;
    }

    public boolean isExpressionUpdated() {
        return expressionUpdated;
    }

    public boolean[] getEffectControlUpdates() {
        return effectControlUpdates;
    }

    public boolean[] getGeneralPurposeControlUpdates() {
        return generalPurposeControlUpdates;
    }

    public boolean isDamplerPedalUpdated() {
        return damplerPedalUpdated;
    }

    public boolean isPortamentoSwitchOn() {
        return portamentoSwitchOn;
    }

    public boolean isPortamentoSwitchUpdated() {
        return portamentoSwitchUpdated;
    }

    public boolean isSustenutoPedalOn() {
        return sustenutoPedalOn;
    }

    public boolean[] getSustenutoEnabledNotes() {
        return sustenutoEnabledNotes;
    }

    public boolean isSustenutoPedalUpdated() {
        return sustenutoPedalUpdated;
    }

    public boolean isSoftPedalOn() {
        return softPedalOn;
    }

    public boolean isSoftPedalUpdated() {
        return softPedalUpdated;
    }

    public boolean isLegatoFootswitchOn() {
        return legatoFootswitchOn;
    }

    public boolean isLegatoFootswitchUpdated() {
        return legatoFootswitchUpdated;
    }

    public boolean isHoldTwoOn() {
        return holdTwoOn;
    }

    public boolean isHoldTwoUpdated() {
        return holdTwoUpdated;
    }

    public boolean[] getSoundControlUpdates() {
        return soundControlUpdates;
    }

    public byte getPortamentoNoteControl() {
        return portamentoNoteControl;
    }

    public boolean isPortamentoNoteUpdated() {
        return portamentoNoteUpdated;
    }

    public boolean[] getEffectDepthsUpdates() {
        return effectDepthsUpdates;
    }

    // setters
    public void setRhythmControl(boolean rhythmControl) {
        this.rhythmControl = rhythmControl;
    }

    public void setModWheelUpdated(boolean modWheelUpdated) {
        this.modWheelUpdated = modWheelUpdated;
    }

    public void setBreathControlUpdated(boolean breathControlUpdated) {
        this.breathControlUpdated = breathControlUpdated;
    }

    public void setFootControllerUpdated(boolean footControllerUpdated) {
        this.footControllerUpdated = footControllerUpdated;
    }

    public void setPortamentoTimeUpdated(boolean portamentoTimeUpdated) {
        this.portamentoTimeUpdated = portamentoTimeUpdated;
    }

    public void setChannelVolumeUpdated(boolean channelVolumeUpdated) {
        this.channelVolumeUpdated = channelVolumeUpdated;
    }

    public void setBalanceUpdated(boolean balanceUpdated) {
        this.balanceUpdated = balanceUpdated;
    }

    public void setPanUpdated(boolean panUpdated) {
        this.panUpdated = panUpdated;
    }

    public void setExpressionUpdated(boolean expressionUpdated) {
        this.expressionUpdated = expressionUpdated;
    }

    public void setPortamentoNoteControl(byte portamentoNoteControl) {
        portamentoNoteUpdated
                = (this.portamentoNoteControl != portamentoNoteControl);
        this.portamentoNoteControl = portamentoNoteControl;
    }

    public void setDamperPedalOn(boolean damperPedalOn) {
        damplerPedalUpdated = (this.damperPedalOn != damperPedalOn);
        this.damperPedalOn = damperPedalOn;
    }

    public void setPortamentoSwitchOn(boolean portamentoSwitchOn) {
        portamentoSwitchUpdated
                = (this.portamentoSwitchOn != portamentoSwitchOn);
        this.portamentoSwitchOn = portamentoSwitchOn;
    }

    public void setSustenutoPedalOn(boolean sustenutoPedalOn) {
        sustenutoPedalUpdated
                = (this.portamentoSwitchOn != portamentoSwitchOn);
        this.sustenutoPedalOn = sustenutoPedalOn;
    }

    public void setSoftPedalOn(boolean softPedalOn) {
        softPedalUpdated = (this.softPedalOn != softPedalOn);
        this.softPedalOn = softPedalOn;
    }

    public void setLegatoFootswitchOn(boolean legatoFootswitchOn) {
        this.legatoFootswitchUpdated
                = (this.legatoFootswitchOn != legatoFootswitchOn);
        this.legatoFootswitchOn = legatoFootswitchOn;
    }

    public void setHoldTwoOn(boolean holdTwoOn) {
        holdTwoUpdated = (this.holdTwoOn != holdTwoOn);
        this.holdTwoOn = holdTwoOn;
    }

    // reset
    public void reset() {
        setDamperPedalOn(false);
        setPortamentoSwitchOn(false);
        setSustenutoPedalOn(false);
        setLegatoFootswitchOn(false);
        setSoftPedalOn(false);
        setHoldTwoOn(false);
    }

    public void notifyControlUpdated() {
        modWheelUpdated = false;
        breathControlUpdated = false;
        footControllerUpdated = false;
        portamentoTimeUpdated = false;
        channelVolumeUpdated = false;
        balanceUpdated = false;
        panUpdated = false;
        expressionUpdated = false;
        for (int i = 0; i < effectControlUpdates.length; i++) {
            effectControlUpdates[i] = false;
        }
        for (int i = 0; i < generalPurposeControlUpdates.length; i++) {
            generalPurposeControlUpdates[i] = false;
        }
        damplerPedalUpdated = false;
        portamentoSwitchUpdated = false;
        sustenutoPedalUpdated = false;
        softPedalUpdated = false;
        legatoFootswitchUpdated = false;
        holdTwoUpdated = false;
        for (int i = 0; i < soundControlUpdates.length; i++) {
            soundControlUpdates[i] = false;
        }
        portamentoNoteUpdated = false;
        for (int i = 0; i < effectDepthsUpdates.length; i++) {
            effectDepthsUpdates[i] = false;
        }
    }

    // abstract variables
    public abstract void controlChange(byte control, long value);

    public abstract long getModulation();

    public abstract long getBreathController();

    public abstract long getFootController();

    public abstract long getPortamentoTime();

    public abstract long getChannelVolume();

    public abstract long getBalance();

    public abstract long getPan();

    public abstract long getChannelExpression();

    public abstract long getEffectControls(byte index);

    public abstract long getGeneralPurpaceControllers(byte index);

    public boolean isDamperPedalOn() {
        return damperPedalOn;
    }

    public abstract long getSoundControllers(byte index);

    public abstract long getEffectDepths(byte index);
}
