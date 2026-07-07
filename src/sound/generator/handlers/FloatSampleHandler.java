/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.generator.handlers;

/**
 *
 * @author eddy6
 */
public class FloatSampleHandler implements Cloneable{
    
    // instance variabels
    // most recent point
    private double point;
    // most recent stereo points
    private double[] points;
    // most recent volume calcualted point
    private double volumeAppliedPoint;
    // most recent volume calculated stereo points
    private double[] volumeAppliedPoints;
    
    public FloatSampleHandler(int channels, boolean volumeApplied) {
        if (channels > 1) {
            points = new double[channels];
            if (volumeApplied) {
                volumeAppliedPoints = new double[channels];
            }
        }
    }
    
    // getters
    public double getPoint() {
        return point;
    }

    public double[] getPoints() {
        return points;
    }

    public double getVolumeAppliedPoint() {
        return volumeAppliedPoint;
    }

    public double[] getVolumeAppliedPoints() {
        return volumeAppliedPoints;
    }
    
    // setters
    public void setPoint(double point) {
        this.point = point;
    }

    public void setPoints(double[] points) {
        this.points = points;
    }

    public void setVolumeAppliedPoint(double volumeAppliedPoint) {
        this.volumeAppliedPoint = volumeAppliedPoint;
    }

    public void setVolumeAppliedPoints(double[] volumeAppliedPoints) {
        this.volumeAppliedPoints = volumeAppliedPoints;
    }
    
    // other methods
    public void zeroSetPoints() {
        for (int i = 0; i < points.length; i++) {
            points[i] = 0;
        }
    }
    
    // apply volume
    public void applyVolume(double volume) {
        volumeAppliedPoint = point * volume;
    }
    
    public void applyStereoVolume(double volume) {
        for (int i = 0; i < volumeAppliedPoints.length; i++) {
            volumeAppliedPoints[i] = points[i] * volume;
        }
    }
    
    public Object Clone() throws CloneNotSupportedException {
        FloatSampleHandler clonedSH = (FloatSampleHandler) super.clone();

        // points
        clonedSH.points = new double[points.length];

        // volume calculated poitns
        if (volumeAppliedPoints != null) {
            clonedSH.volumeAppliedPoints
                    = new double[volumeAppliedPoints.length];
        }

        return clonedSH;
    }
}
