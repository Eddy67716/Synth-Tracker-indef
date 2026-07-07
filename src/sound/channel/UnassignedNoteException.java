/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.channel;

/**
 *
 * @author Edward Jenkins
 */
public class UnassignedNoteException extends Exception{
    
    // constructors
    public UnassignedNoteException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UnassignedNoteException(String message) {
        super(message);
    }
    
    public UnassignedNoteException(Throwable cause) {
        super(cause);
    }
}
