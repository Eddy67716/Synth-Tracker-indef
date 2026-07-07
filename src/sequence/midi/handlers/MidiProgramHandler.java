/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi.handlers;

/**
 *
 * @author eddy6
 */
public class MidiProgramHandler {
    
    // instance variables
    private short bank;
    private boolean bankUpdated;
    private byte program;
    private boolean programUpdated;
    
    public MidiProgramHandler(short bank, byte program) {
        this.bank = bank;
        this.program = program;
    }
    
    // getters
    public short getBank() {
        return bank;
    }

    public boolean isBankUpdated() {
        return bankUpdated;
    }

    public byte getProgram() {
        return program;
    }

    public boolean isProgramUpdated() {
        return programUpdated;
    }
    
    // setters
    public void setBank(short bank) {
        bankUpdated = (this.bank != bank);
        this.bank = bank;
    }

    public void setProgram(byte program) {
        programUpdated = (this.program != program);
        this.program = program;
    }
    
    public void programChanged() {
        bankUpdated = programUpdated = false;
    }
}
