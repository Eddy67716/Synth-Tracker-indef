/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sound.generator.handlers;

/**
 *
 * @author eddy6
 */
public interface IVolumeAccumulator extends Cloneable, Comparable   {
    
    public double getVolume();
    public double getAttenuationVolume();
    public double getStaticVolume();
    public double getTotalVolume();
    public void setMixedAtMixVolume(boolean mixedAtMixVolume);
    public void setGlobalVolume(double globalVolume);
    public void setChannelVolume(double channelVolume);
    public void setBaseVolume(double volume);
    public double calculateTotalVolume();
    public int calculateTotalIntVolume();
    public void sampleUpdate();
    public void updateAttenuationVolume();
    public void updateStaticVolume();
    public void accumulateTotalVolume(double volume);
    public void reset();
    public Object clone() throws CloneNotSupportedException;
}
