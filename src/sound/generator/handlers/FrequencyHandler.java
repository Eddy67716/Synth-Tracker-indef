/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.generator.handlers;

import static music.note.NoteFrequencyConstants.CENT_TUNING_RATIO;
import static sound.tables.StrConstants.SAMPLE_RATE;

/**
 *
 * @author Edward Jenkins
 */
public class FrequencyHandler implements Cloneable {

    // constants
    public static final byte DEF_BEND_OFFSET = 0;

    // instance variables
    // sample rate
    private int sampleRate;
    // the current frequency (plus all offsets) frequency is a play rate for
    // a sample if an instance of this extends PlayRateHandler
    private double currentFrequency;
    // current period
    private double currentPeriod;
    // the original frequency without any detune, pitch bend or vibrato offsets
    private double originalFrequency;
    // the detune offset in hertz
    private double detuneOffset;
    // the currentFrequency to update to on a tick update
    private double holdFrequency;
    // the currentFrequency to slde to (currently unused if -1)
    private double portamentoFrequency;
    private boolean slidingToNote;
    private boolean upPortamento;
    // the currentFrequency that is being updated
    private double updatingFrequency;
    // the cent offset to be accumulated on a tick update
    private double portamentoCentOffset;
    // the period offset to be accumulated on a tick update
    private double periodOffset;
    // the cent offset of a pitch bend operation
    private double bendCentOffset;
    // the last bend offset
    private double lastBendCentOffset;
    // the internal vibrato cent offet
    private double vibratoCentOffset;
    // the internal modulator cent offset (pitch envelopes)
    private double internalModCentOffset;
    // used for external vibrato modulation
    private double externalModCentOffset;
    private double externalModPeriodOffset;

    // all args constructor
    public FrequencyHandler(double frequency, int sampleRate,
            double bendCentOffset) {
        this.currentFrequency = frequency;
        currentPeriod = 1d / frequency;
        this.sampleRate = sampleRate;
        this.bendCentOffset = bendCentOffset;
        portamentoFrequency = -1;
        holdFrequency = frequency;
        originalFrequency = frequency;
        hardSetFrequency();
    }

    // 2 arg constructor
    public FrequencyHandler(double frequency, int sampleRate) {
        this(frequency, sampleRate, DEF_BEND_OFFSET);
    }

    // 1 arg constructor
    public FrequencyHandler(double frequency) {
        this(frequency, SAMPLE_RATE);
    }

    // getters
    public int getSampleRate() {
        return sampleRate;
    }

    public double getCurrentFrequency() {
        return currentFrequency;
    }

    public double getOriginalFrequency() {
        return originalFrequency;
    }

    public double getDetuneOffset() {
        return detuneOffset;
    }

    public double getHoldFrequency() {
        return holdFrequency;
    }

    public double getPortamentoFrequency() {
        return portamentoFrequency;
    }

    public boolean isSlidingToNote() {
        return slidingToNote;
    }

    public double getPortamentoCentOffset() {
        return portamentoCentOffset;
    }

    public double getBendCentOffset() {
        return bendCentOffset;
    }

    public double getVibratoCentOffset() {
        return vibratoCentOffset;
    }

    public double getInternalModCentOffset() {
        return internalModCentOffset;
    }

    public double getExternalModCentOffset() {
        return externalModCentOffset;
    }

    // setters
    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public void setCurrentFrequency(double currentFrequency) {
        this.currentFrequency = currentFrequency;
        setupPeriods();
    }

    public void setOriginalFrequency(double originalFrequency) {
        this.originalFrequency = originalFrequency;
        setCurrentFrequency(originalFrequency);
    }

    public void setDetuneOffset(double detuneOffset) {
        this.detuneOffset = detuneOffset;
    }

    public void setHoldFrequency(double holdFrequency) {
        this.holdFrequency = holdFrequency;
    }

    public void setPortamentoFrequency(double portamentoFrequency) {
        this.portamentoFrequency = portamentoFrequency;
        slidingToNote = true;
        upPortamento = portamentoFrequency > originalFrequency;
    }

    public void setPortamentoCentOffset(double portamentoCentOffset) {
        this.portamentoCentOffset = portamentoCentOffset;
    }

    public void setPeriodOffset(double periodOffset) {
        this.periodOffset = periodOffset;
    }

    public void setBendCentOffset(double bendCentOffset) {
        this.bendCentOffset = bendCentOffset;
    }

    public void setVibratoCentOffset(double vibratoCentOffset) {
        this.vibratoCentOffset = vibratoCentOffset;
    }

    public void setInternalModCentOffset(double internalModCentOffset) {
        this.internalModCentOffset = internalModCentOffset;
    }

    public void setExternalModCentOffset(double externalModCentOffset) {
        this.externalModCentOffset = externalModCentOffset;
    }

    // other methods
    public void setupPeriods() {
        currentPeriod = 1d / currentFrequency;
    }

    public void sampleStepUp() {

    }

    protected void updateFrequency() {
        this.currentFrequency = holdFrequency;
        setupPeriods();
        originalFrequency = currentFrequency;
    }

    public void updateFrequency(double frequency) {
        this.currentFrequency = frequency;
        setupPeriods();
        originalFrequency = frequency;
        holdFrequency = frequency;
    }

    public void detuneFrequency() {
        currentFrequency += detuneOffset;
        setupPeriods();
    }

    public void detuneFrequency(double offset) {
        detuneOffset = offset;
        currentFrequency += offset;
        setupPeriods();
    }

    public void processPortamentoOffset() {
        if (slidingToNote) {

            if (upPortamento) {
                originalFrequency *= Math.pow(CENT_TUNING_RATIO, portamentoCentOffset);

                if (originalFrequency > portamentoFrequency) {
                    finishPortamento();
                }
            } else {
                originalFrequency *= Math.pow(CENT_TUNING_RATIO, -portamentoCentOffset);

                if (originalFrequency < portamentoFrequency) {
                    finishPortamento();
                }
            }
            holdFrequency = originalFrequency;
            portamentoCentOffset = 0;
        }
    }

    public void finishPortamento() {
        originalFrequency = portamentoFrequency;
        slidingToNote = false;
        portamentoFrequency = -1;
    }

    public void centBendFrequency() {
        updatingFrequency *= Math.pow(CENT_TUNING_RATIO, bendCentOffset);
    }

    public void centVibratoFrequency() {
        updatingFrequency *= Math.pow(CENT_TUNING_RATIO, vibratoCentOffset);
    }

    public void centInternalModFrequency() {
        updatingFrequency *= Math.pow(CENT_TUNING_RATIO, internalModCentOffset);
    }

    public void centVibratoModFrequency() {
        updatingFrequency *= Math.pow(CENT_TUNING_RATIO, externalModCentOffset);
    }

    public boolean tickUpdate() {
        return hardSetFrequency();
    }

    /**
     * Changed the current frequency to the hold frequency
     *
     * @return true if the currentFrequency was changed
     */
    public boolean hardSetFrequency() {
        boolean frequencyChanged = false;

        // check portamento offset
        if (portamentoCentOffset != 0) {
            processPortamentoOffset();
            frequencyChanged = true;
        }

        updatingFrequency = originalFrequency;

        if (holdFrequency != currentFrequency) {
            updateFrequency();
            frequencyChanged = true;
        }
        if (bendCentOffset != 0) {
            centBendFrequency();
            if (bendCentOffset != lastBendCentOffset) {
                lastBendCentOffset = bendCentOffset;
                frequencyChanged = true;
            }
        }
        if (vibratoCentOffset != 0) {
            centVibratoFrequency();
            frequencyChanged = true;
        }
        if (internalModCentOffset != 0) {
            centInternalModFrequency();
            frequencyChanged = true;
        }
        if (externalModCentOffset != 0) {
            centVibratoModFrequency();
            frequencyChanged = true;
        }

        currentFrequency = updatingFrequency;
        setupPeriods();
        if (detuneOffset != 0) {
            detuneFrequency();
            frequencyChanged = true;
        }

        return frequencyChanged;
    }

    public boolean hardSetFrequency(double frequency) {
        updateFrequency(frequency);
        return hardSetFrequency();
    }

    public void accumulatePortamentoCents(double cents) {
        portamentoCentOffset += cents;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void reset() {
        currentFrequency = holdFrequency = originalFrequency;
        bendCentOffset = lastBendCentOffset = vibratoCentOffset
                = internalModCentOffset = externalModCentOffset = 0;
        hardSetFrequency();
    }
}
