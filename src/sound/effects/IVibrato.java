/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sound.effects;

/**
 *
 * @author eddy6
 */
public interface IVibrato extends ILowFreequencyOscilator {
    
    // output is a cent offset
    public double getCurrentVibratoPoint();

    // run everytime the tick rate changes
    public void setSampleRate(int sampleRate);
}
