/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.generator;

/**
 *
 * @author eddy6
 */
public interface IPointGenerateable {
    
    // generates a mono sample
    public double generateSamplePoint();
    
    // generates a mono 32-bit sample
    public long generateIntSamplePoint();
}
