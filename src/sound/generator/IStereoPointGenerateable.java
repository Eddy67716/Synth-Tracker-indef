/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sound.generator;

/**
 *
 * @author eddy6
 */
public interface IStereoPointGenerateable extends IPointGenerateable {
    
    // generates two stereo samples
    public double[] generateStereoSamplePoints();
    
    // generates two stereo 32-bit samples
    public long[] generateStereoIntSamplePoints();
}
