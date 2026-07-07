/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.generator.handlers;

import static sound.effects.SoundIntMixer.interpolateIntSample;
import static sound.effects.SoundIntMixer.cubicInterpolateIntSample;

/**
 *
 * @author eddy6
 */
public class IntSamplerPointsHandler {
    
    // current points
    private long currentLeftPoint;
    private long currentRightPoint;
    // last points returned
    private long leftPoint;
    private long rightPoint;
    // array cache for points (up to 8 groups of 1 to 2 samples)
    private long[][] pointCache;
    // return points array
    private long[] returnPoints;
    
    public IntSamplerPointsHandler(byte channels, byte cachePoints) {
        returnPoints = new long[channels];
        pointCache = new long[cachePoints][channels];
    }
    
    // getters
    public long getCurrentLeftPoint() {
        return currentLeftPoint;
    }

    public long getCurrentRightPoint() {
        return currentRightPoint;
    }

    public long getLeftPoint() {
        return leftPoint;
    }

    public long getRightPoint() {
        return rightPoint;
    }

    public long[][] getPointCache() {
        return pointCache;
    }

    public long[] getReturnPoints() {
        return returnPoints;
    }
    
    // setters
    public void setCurrentLeftPoint(long currentLeftPoint) {
        this.currentLeftPoint = currentLeftPoint;
    }

    public void setCurrentRightPoint(long currentRightPoint) {
        this.currentRightPoint = currentRightPoint;
    }

    public void setLeftPoint(long leftPoint) {
        this.leftPoint = leftPoint;
    }

    public void setRightPoint(long rightPoint) {
        this.rightPoint = rightPoint;
    }
    
    public void setupReturnPoints() {
        returnPoints[0] = leftPoint;
        
        if (returnPoints.length >= 2) {
            returnPoints[1] = rightPoint;
        }
    }
    
    public void obtainNextHoldSamples() {
        currentLeftPoint = pointCache[1][0];
        if (returnPoints.length >= 2) {
            currentRightPoint = pointCache[1][1];
        }
    }

    public void interpolateFromCache(byte channel,
            byte resampleType, double percentage) {
        long point;
        if (resampleType < 4 || resampleType > 6) {
            point = interpolateIntSample(pointCache[0][channel],
                    pointCache[1][channel],
                    percentage);
        } else {
            point = cubicInterpolateIntSample(pointCache[2][channel],
                    pointCache[1][channel], pointCache[0][channel],
                    pointCache[3][channel], percentage);
        }
        switch (channel) {
            case 0:
            default:
                leftPoint = point;
                break;
            case 1:
                rightPoint = point;
                break;
        }
    }

    public void stairStepTo(byte channel, long newSample, double percentage) {
        
        long point, currentPoint;
        
        switch (channel) {
            case 0:
            default:
                currentPoint = currentLeftPoint;
                break;
            case 1:
                currentPoint = currentRightPoint;
                break;
        }
        
        // update the sample
        pointCache[0][0] = currentPoint;
        pointCache[1][0] = newSample;

        // interpolate left sample
        point = interpolateIntSample(pointCache[0][0],
                        pointCache[1][0], percentage);

        currentPoint = pointCache[1][0];
        
        switch (channel) {
            case 0:
            default:
                leftPoint = point;
                currentLeftPoint = currentPoint;
                break;
            case 1:
                rightPoint = point;
                currentRightPoint = currentPoint;
                break;
        }
    }

    public void stairStepHold() {
        leftPoint = currentLeftPoint;
        if (returnPoints.length >= 2) {
            rightPoint = currentRightPoint;
        }
    }
}
