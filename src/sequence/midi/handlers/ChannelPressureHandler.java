/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi.handlers;

import static sequence.midi.MidiConstants.MIDI_1_VELOCITY_FRACTIONAL;
import static sequence.midi.MidiConstants.MIDI_2_CONTROL_FRACTIONAL;
import sequence.midi.SystemExclusiveMessage;
import static sound.effects.SoundFloatMixer.interpolateSample;

/**
 *
 * @author eddy6
 */
public class ChannelPressureHandler {

    // instance variabels
    private boolean midi2;
    private byte midi1channelPressure;
    private long midi2channelPressure;
    private double fineChannelPressure;
    private byte pitchSemitoneDepth;
    private double currentSemitoneDepth;
    private double filterCentDepth;
    private double currentfilterCentDepth;
    private double amplitudeDepth;
    private double currentAmplitudeDepth;
    private double lfoPitchDepth;
    private double currentLfoPitchDepth;
    private double lfoFilterDepth;
    private double currentLfoFilterDepth;
    private double lfoAmplitudeDepth;
    private double currentLfoAmplitudeDepth;
    private boolean pressureUpdated;

    public ChannelPressureHandler(boolean midi2) {
        this.midi2 = false;
        amplitudeDepth = currentAmplitudeDepth = 1;
    }

    // getters
    public byte getMidi1channelPressure() {
        return midi1channelPressure;
    }

    public double getFineChannelPressure() {
        return fineChannelPressure;
    }

    public byte getPitchSemitoneDepth() {
        return pitchSemitoneDepth;
    }

    public double getCurrentSemitoneDepth() {
        return currentSemitoneDepth;
    }

    public double getFilterCentDepth() {
        return filterCentDepth;
    }

    public double getCurrentfilterCentDepth() {
        return currentfilterCentDepth;
    }

    public double getAmplitudeDepth() {
        return amplitudeDepth;
    }

    public double getCurrentAmplitudeDepth() {
        return currentAmplitudeDepth;
    }

    public double getLfoPitchDepth() {
        return lfoPitchDepth;
    }

    public double getCurrentLfoPitchDepth() {
        return currentLfoPitchDepth;
    }

    public double getLfoFilterDepth() {
        return lfoFilterDepth;
    }

    public double getCurrentLfoFilterDepth() {
        return currentLfoFilterDepth;
    }

    public double getLfoAmplitudeDepth() {
        return lfoAmplitudeDepth;
    }

    public double getCurrentLfoAmplitudeDepth() {
        return currentLfoAmplitudeDepth;
    }

    public boolean isPressureUpdated() {
        return pressureUpdated;
    }

    // setters
    public void setChannelPressure(long pressure) {
        double oldFineChannelPressure = fineChannelPressure;
        if (midi2) {
            midi2channelPressure = pressure;
            fineChannelPressure = (double) midi2channelPressure
                    * MIDI_2_CONTROL_FRACTIONAL;
        } else {
            midi1channelPressure = (byte) pressure;
            fineChannelPressure = (double) midi1channelPressure
                    * MIDI_1_VELOCITY_FRACTIONAL;
        }
        if (oldFineChannelPressure != fineChannelPressure) {
            this.pressureUpdated = true;
            if (pitchSemitoneDepth != 0) {
                currentSemitoneDepth = pitchSemitoneDepth
                        * fineChannelPressure;
            }
            if (filterCentDepth != 0) {
                currentfilterCentDepth = filterCentDepth
                        * fineChannelPressure;
            }
            if (amplitudeDepth != 1) {
                currentAmplitudeDepth = interpolateSample(1, amplitudeDepth,
                        fineChannelPressure);
            } else {
                currentAmplitudeDepth = 1;
            }
            if (lfoPitchDepth != 0) {
                currentLfoPitchDepth = lfoPitchDepth * fineChannelPressure;
            }
            if (lfoFilterDepth != 0) {
                currentLfoFilterDepth = lfoFilterDepth * fineChannelPressure;
            }
            if (lfoAmplitudeDepth != 0) {
                currentLfoAmplitudeDepth = lfoAmplitudeDepth * fineChannelPressure;
            }
        }
    }

    public void setFineChannelPressure(double fineChannelPressure) {
        this.fineChannelPressure = fineChannelPressure;
    }

    public void setPitchSemitoneDepth(byte pitchSemitoneDepth) {
        this.pitchSemitoneDepth = pitchSemitoneDepth;
    }

    public void setCurrentSemitoneDepth(double currentSemitoneDepth) {
        this.currentSemitoneDepth = currentSemitoneDepth;
    }

    public void setFilterCentDepth(double filterCentDepth) {
        this.filterCentDepth = filterCentDepth;
    }

    public void setCurrentfilterCentDepth(double currentfilterCentDepth) {
        this.currentfilterCentDepth = currentfilterCentDepth;
    }

    public void setAmplitudeDepth(double amplitudeDepth) {
        this.amplitudeDepth = amplitudeDepth;
    }

    public void setCurrentAmplitudeDepth(double currentAmplitudeDepth) {
        this.currentAmplitudeDepth = currentAmplitudeDepth;
    }

    public void setLfoPitchDepth(double lfoPitchDepth) {
        this.lfoPitchDepth = lfoPitchDepth;
    }

    public void setCurrentLfoPitchDepth(double currentLfoPitchDepth) {
        this.currentLfoPitchDepth = currentLfoPitchDepth;
    }

    public void setLfoFilterDepth(double lfoFilterDepth) {
        this.lfoFilterDepth = lfoFilterDepth;
    }

    public void setCurrentLfoFilterDepth(double currentLfoFilterDepth) {
        this.currentLfoFilterDepth = currentLfoFilterDepth;
    }

    public void setLfoAmplitudeDepth(double lfoAmplitudeDepth) {
        this.lfoAmplitudeDepth = lfoAmplitudeDepth;
    }

    public void setCurrentLfoAmplitudeDepth(double currentLfoAmplitudeDepth) {
        this.currentLfoAmplitudeDepth = currentLfoAmplitudeDepth;
    }

    public void controllerChange(byte[] data) {
        if (data.length > 2) {

            // set all depths to default
            pitchSemitoneDepth = 0;
            filterCentDepth = 0;
            amplitudeDepth = 1;
            lfoPitchDepth = lfoFilterDepth = lfoAmplitudeDepth = 0;

            for (int i = 1; i < data.length; i += 2) {
                switch (data[i]) {
                    case SystemExclusiveMessage.PITCH_CONTROL:
                        // pitch semitone offset between -24 and 24
                        pitchSemitoneDepth = (byte) (data[i + 1] - 64);
                        break;
                    case SystemExclusiveMessage.CUTOFF_CONTROL:
                        // filter cutoff control -9600 to 9450 cents
                        filterCentDepth = ((double) data[i + 1] - 64) / 63
                                * 9450;
                        break;
                    case SystemExclusiveMessage.AMPLITUDE_CONTROL:
                        amplitudeDepth = (double) data[i + 1] / 64;
                        break;
                    case SystemExclusiveMessage.LFO_PITCH_DEPTH:
                        lfoPitchDepth = (double) data[i + 1]
                                * MIDI_1_VELOCITY_FRACTIONAL * 600;
                        break;
                    case SystemExclusiveMessage.LFO_CUTOFF_DEPTH:
                        lfoFilterDepth = (double) data[i + 1]
                                * MIDI_1_VELOCITY_FRACTIONAL * 2400;
                        break;
                    case SystemExclusiveMessage.LFO_AMPLITUDE_DEPTH:
                        lfoAmplitudeDepth = (double) data[i + 1]
                                * MIDI_1_VELOCITY_FRACTIONAL;
                        break;
                }
            }
        }
    }
    
    public void notifyEffectsUpdated() {
        pressureUpdated = false;
    }
}
