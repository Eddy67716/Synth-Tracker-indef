/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.generator.handlers;

/**
 *
 * @author eddy6
 */
public class IntSampleHandler implements Cloneable {

    // instance variables
    // most recent point
    private long intPoint;
    // most recent stereo points
    private long[] intPoints;
    // most recent volume calcualted point
    private long intVolumeCalculatedPoint;
    // most recent volume calculated points
    private long[] intVolumeCalculatedPoints;

    public IntSampleHandler(int channels, boolean volumeCalculated) {
        if (channels > 1) {
            intPoints = new long[channels];
            if (volumeCalculated) {
                intVolumeCalculatedPoints = new long[channels];
            }
        }
    }

    // getters
    public long getIntPoint() {
        return intPoint;
    }

    public long[] getIntPoints() {
        return intPoints;
    }

    public long getIntVolumeCalculatedPoint() {
        return intVolumeCalculatedPoint;
    }

    public long[] getIntVolumeCalculatedPoints() {
        return intVolumeCalculatedPoints;
    }
    
    // setters
    public void setIntPoint(long intPoint) {
        this.intPoint = intPoint;
    }

    public void setIntPoints(long[] intPoints) {
        this.intPoints = intPoints;
    }

    public void setIntVolumeCalculatedPoint(long intVolumeCalculatedPoint) {
        this.intVolumeCalculatedPoint = intVolumeCalculatedPoint;
    }

    public void setIntVolumeCalculatedPoints(long[] intVolumeCalculatedPoints) {
        this.intVolumeCalculatedPoints = intVolumeCalculatedPoints;
    }
    
    // other methods
    public void zeroSetPoints() {
        for (int i = 0; i < intPoints.length; i++) {
            intPoints[i] = 0;
        }
    }
    
    public Object Clone() throws CloneNotSupportedException {
        IntSampleHandler clonedSH = (IntSampleHandler) super.clone();

        // points
        clonedSH.intPoints = new long[intPoints.length];

        // volume calculated poitns
        if (intVolumeCalculatedPoints != null) {
            clonedSH.intVolumeCalculatedPoints
                    = new long[intVolumeCalculatedPoints.length];
        }

        return clonedSH;
    }

}
