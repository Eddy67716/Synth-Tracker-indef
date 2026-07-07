/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sequence.midi.parameters;

/**
 *
 * @author eddy6
 */
public interface IParameterUpdatable {
    
    public short getEventType();
    
    public void setEventType(short eventType);
    
    // set the event MSB
    public void setEventMSB(byte registeredEventMSB);

    // set the event LSB
    public void setEventLSB(byte registeredEventLSB);
    
    public void setDataDirectly(short eventNo, long value);
    
    // alters one of the bytes of a parameter
    public void paremeterNumberChange(boolean msb, byte value);
    
    // add the value to the parameter
    public void incrementData(byte value);
    
    // subtracts the value from the parameter
    public void decrementData(byte value);
    
    public void notifyEffectsUpdated();
}
