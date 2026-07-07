/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.generator.handlers;

/**
 *
 * @author eddy6
 */
public class FloatPanHandler implements Cloneable {
    
    // constants
    public static final byte DEF_PANNING = 0;
    public static final float PAN_NORMALISER = 0.5f;
    
    // instance variables
    // panning value -1 = left pan 0 = central pan and 1 = right pan
    private double panning;
    // midi left pan
    private double midiLeftPan = -1;
    // midi right pan
    private double midiRightPan = -1;
    // used to set left point
    private double leftPanAmplitude;
    // used to set right point
    private double rightPanAmplitude;
    // inverts the right channel
    private boolean surround;
    // used for panbrellos
    private double panbrelloOffset;
    
    public FloatPanHandler(double panning, boolean surround) {
        this.panning = panning;
        this.surround = surround;
        panbrelloOffset = panning;
        updatePanValues();
    }
    
    // getters
    public double getPanning() {
        return panning;
    }

    public double getLeftPanAmplitude() {
        return leftPanAmplitude;
    }

    public double getRightPanAmplitude() {
        return rightPanAmplitude;
    }

    public boolean isSurround() {
        return surround;
    }

    public double getPanbrelloOffset() {
        return panbrelloOffset;
    }
    
    // setters
    public void setPanning(double panning) {
        this.panning = panning;
        updatePanValues();
    }

    public void setSurround(boolean surround) {
        this.surround = surround;
    }

    public void setPanbrelloOffset(double panbrelloOffset) {
        this.panbrelloOffset = panbrelloOffset;
    }
    
    public void accumulcatePanning(double panningOffset) {
        panning += panningOffset;
        updatePanValues();
    }
    
    public void panbrelloShift(double panbrelloAmount) {
        panning = panbrelloOffset + panbrelloAmount;
        updatePanValues();
    }
    
    public void setMidiPanning(double midiLeftPan, double midiRightPan) {
        this.midiLeftPan = midiLeftPan;
        this.midiRightPan = midiRightPan;
        updatePanValues();
    }

    public void setLeftPanAmplitude(double leftPanAmplitude) {
        this.leftPanAmplitude = leftPanAmplitude;
    }

    public void setRightPanAmplitude(double rightPanAmplitude) {
        this.rightPanAmplitude = rightPanAmplitude;
    }
    
    public void updatePanValues() {
        double normalisedPanning = panning * PAN_NORMALISER;
        leftPanAmplitude = PAN_NORMALISER - normalisedPanning;
        if (leftPanAmplitude > 1) {
            leftPanAmplitude = 1;
        }
        if (midiLeftPan > -1) {
            leftPanAmplitude *= midiLeftPan;
        }
        rightPanAmplitude = PAN_NORMALISER + normalisedPanning;
        if (rightPanAmplitude > 1) {
            rightPanAmplitude = 1;
        }
        if (midiRightPan > -1) {
            rightPanAmplitude *= midiRightPan;
        }
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
