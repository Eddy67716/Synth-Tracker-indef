/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.generator.handlers;

/**
 *
 * @author eddy6
 */
public class IntegerPanHandler {
    
    // constants
    public static final byte DEF_PANNING = 0;
    public static final float PAN_NORMALISER = 0.5f;
    
    // instance variables
    // panning value -1 = left pan 0 = central pan and 1 = right pan
    private double panning;
    // used to set left point (0-32768)
    private int leftPanAmplitude;
    // used to set right point (0-32768)
    private int rightPanAmplitude;
    // inverts the right channel
    private boolean surround;
    // used for panbrellos
    private double panbrelloOffset;
    
    // constructor
    public IntegerPanHandler(double panning, boolean surround) {
        
    }
}
