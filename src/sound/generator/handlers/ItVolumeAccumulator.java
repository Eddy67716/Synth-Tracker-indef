/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.generator.handlers;

import static sound.tables.StrConstants.MAX_INT_VOLUME;
import static sound.tables.StrConstants.getMixVolume;

/**
 *
 * @author eddy6
 */
public class ItVolumeAccumulator implements  IVolumeAccumulator, 
        Cloneable, Comparable  {
    
    // instance variables
    // top level accumulator (uses mix value if true)
    private boolean mixedAtMixVolume = true;
    // default level used in condition accumulation
    private double defaultLevel = 1.0;
    // instrument globalVolume
    private double instrumentGlobalVolume = 1;
    // global volume (1 if undefined)
    private double globalVolume;
    // channel volume
    private double channelVolume = 1;
    // attenuation volume (volumes that aren't tied to other effects such as a filter)
    private double attenuationVolume;
    // current tracker volume
    private double volume;
    // the static volume of the pre amp volume and amplitude combined
    private double staticVolume;
    // values for total volume accumulaton
    private double volumeAccumulator = 1;
    // instrument fade
    private double instrumentFadeVolume = 1;
    // instrument envelope nodes
    private double instrumentEnvelopeVolume = 1;
    // tremolo volume
    private double tremoloVolume = 1;
    // all volumes accumulated together
    private double totalVolume = 1;
    
    public ItVolumeAccumulator(double volume, double globalVolume) {
        this.volume = volume;
        this.globalVolume = globalVolume;
        updateAttenuationVolume();
        updateStaticVolume();
    }
    
    // getters
    public boolean isTopLevelAccumulator() {
        return mixedAtMixVolume;
    }
    
    public double getDefaultLevel() {
        return defaultLevel;
    }
    
    public double getInstrumentGlobalVolume() {
        return instrumentGlobalVolume;
    }

    public double getGlobalVolume() {
        return globalVolume;
    }

    public double getChannelVolume() {
        return channelVolume;
    }

    @Override
    public double getAttenuationVolume() {
        return attenuationVolume;
    }

    @Override
    public double getVolume() {
        return volume;
    }

    @Override
    public double getStaticVolume() {
        return staticVolume;
    }

    public double getVolumeAccumulator() {
        return volumeAccumulator;
    }

    public double getInstrumentFadeVolume() {
        return instrumentFadeVolume;
    }

    public double getInstrumentEnvelopeVolume() {
        return instrumentEnvelopeVolume;
    }

    public double getTremoloVolume() {
        return tremoloVolume;
    }

    @Override
    public double getTotalVolume() {
        return totalVolume;
    }

    // setters
    @Override
    public void setMixedAtMixVolume(boolean mixedAtMixVolume) {
        this.mixedAtMixVolume = mixedAtMixVolume;
        updateAttenuationVolume();
    }
    
    protected void setDefaultLevel(double defaultLevel) {
        this.defaultLevel = defaultLevel;
    }
    
    public void setInstrumentGlobalVolume(double instrumentGlobalVolume) {
        this.instrumentGlobalVolume = instrumentGlobalVolume;
        updateAttenuationVolume();
    }

    @Override
    public void setGlobalVolume(double globalVolume) {
        this.globalVolume = globalVolume;
        updateAttenuationVolume();
    }

    @Override
    public void setChannelVolume(double channelVolume) {
        this.channelVolume = channelVolume;
        updateAttenuationVolume();
    }

    public void setAttenuationVolume(double attenuationVolume) {
        this.attenuationVolume = attenuationVolume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
    
    @Override
    public void setBaseVolume(double volume) {
        setVolume(volume);
        updateStaticVolume();
    }

    public void setStaticVolume(double staticVolume) {
        this.staticVolume = staticVolume;
    }

    public void setVolumeAccumulator(double volumeAccumulator) {
        this.volumeAccumulator = volumeAccumulator;
    }

    public void setInstrumentFadeVolume(double instrumentFadeVolume) {
        this.instrumentFadeVolume = instrumentFadeVolume;
    }

    public void setInstrumentEnvelopeVolume(double instrumentEnvelopeVolume) {
        this.instrumentEnvelopeVolume = instrumentEnvelopeVolume;
    }

    public void setTremoloVolume(double tremoloVolume) {
        this.tremoloVolume = tremoloVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    @Override
    public double calculateTotalVolume() {
        totalVolume = staticVolume;
        if (instrumentFadeVolume != defaultLevel) {
            totalVolume *= instrumentFadeVolume;
        }
        if (instrumentEnvelopeVolume != defaultLevel) {
            totalVolume *= instrumentEnvelopeVolume;
        }
        if (volumeAccumulator != defaultLevel) {
            totalVolume *= volumeAccumulator;
        }
        return totalVolume;
    }

    @Override
    public int calculateTotalIntVolume() {
        return (int) Math.round(calculateTotalVolume() * MAX_INT_VOLUME);
    }

    @Override
    public void sampleUpdate() {
        volumeAccumulator = defaultLevel;
    }

    @Override
    public void updateAttenuationVolume() {
        attenuationVolume = (mixedAtMixVolume)
                    ? getMixVolume() : getDefaultLevel();
        if (channelVolume != defaultLevel) {
            attenuationVolume *= channelVolume;
        }
        if (globalVolume != defaultLevel) {
            attenuationVolume *= globalVolume;
        }
        if (instrumentGlobalVolume != defaultLevel) {
            attenuationVolume *= instrumentGlobalVolume;
        }
    }

    @Override
    public void updateStaticVolume() {
        updateAttenuationVolume();
        staticVolume = attenuationVolume;
        if (volume != defaultLevel) {
            staticVolume *= volume;
        }
    }

    @Override
    public void accumulateTotalVolume(double volume) {
        volumeAccumulator *= volume;
    }
    
    @Override
    public void reset() {
        //updateStaticVolume();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(Object o) {

        if (o instanceof ItVolumeAccumulator vh) {

            if (staticVolume != vh.staticVolume) {
                return (staticVolume > vh.staticVolume) ? 1 : -1;
            }

            if (totalVolume != vh.totalVolume) {
                return (totalVolume > vh.totalVolume) ? 1 : -1;
            }
        }
        return 1;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.staticVolume) 
                ^ (Double.doubleToLongBits(this.staticVolume) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.totalVolume) 
                ^ (Double.doubleToLongBits(this.totalVolume) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItVolumeAccumulator other = (ItVolumeAccumulator) obj;
        if (Double.doubleToLongBits(this.staticVolume) 
                != Double.doubleToLongBits(other.staticVolume)) {
            return false;
        }
        return Double.doubleToLongBits(this.totalVolume) 
                == Double.doubleToLongBits(other.totalVolume);
    }
}
