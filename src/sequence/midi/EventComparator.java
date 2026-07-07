/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sequence.midi;

import java.util.Comparator;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

/**
 *
 * @author Edward Jenkins
 */
public class EventComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {

        int returnValue = 0;

        if (o1 instanceof MidiEvent me1
                && o2 instanceof MidiEvent me2) {

            // compare by ticks
            if (me1.getTick() != me2.getTick()) {
                returnValue = (me1.getTick() > me2.getTick()) ? 1 : -1;
                return returnValue;
            }

            // compare by messages
            returnValue = compareMessage(me1.getMessage(), me2.getMessage());

            // compare by message type
        } else if (o1 instanceof MidiEvent) {
            returnValue = 1;
        } else if (o2 instanceof MidiEvent) {
            returnValue = -1;
        }

        return returnValue;
    }

    public int compareMessage(MidiMessage mm1, MidiMessage mm2) {

        int value = 0;

        if (mm1.getStatus() == ShortMessage.SYSTEM_RESET) {
            value = compareOnSystemResetMessage(mm1, mm2);
        } else if ((mm1.getStatus() & 0xF) == 0xF) {
            value = compareOnSystemCommonMessage(mm1, mm2);
        } else if ((mm1.getStatus() & ShortMessage.CONTROL_CHANGE)
                == ShortMessage.CONTROL_CHANGE) {
            value = compareOnControlChangeMessage(mm1, mm2);
        } else if ((mm1.getStatus() & ShortMessage.PROGRAM_CHANGE)
                == ShortMessage.PROGRAM_CHANGE) {
            value = compareOnProgramChangeMessage(mm1, mm2);
        } else if ((mm1.getStatus() & ShortMessage.PITCH_BEND)
                == ShortMessage.PITCH_BEND) {
            value = compareOnPitchBendMessage(mm1, mm2);
        } else if ((mm1.getStatus() & ShortMessage.NOTE_OFF)
                == ShortMessage.NOTE_OFF) {
            value = compareOnNoteOffMessage(mm1, mm2);
        } else if ((mm1.getStatus() & ShortMessage.NOTE_ON)
                == ShortMessage.NOTE_ON) {
            value = compareOnNoteOnMessage(mm1, mm2);
        } else if ((mm1.getStatus() & ShortMessage.CHANNEL_PRESSURE)
                == ShortMessage.CHANNEL_PRESSURE) {
            value = compareOnChannelPressureMessage(mm1, mm2);
        } else if ((mm1.getStatus() & ShortMessage.POLY_PRESSURE)
                == ShortMessage.POLY_PRESSURE) {
            value = compareOnKeyPressureMessage(mm1, mm2);
        } else {
            value = compareOnUnknownMessage(mm1, mm2);
        }

        return value;
    }

    public int compareOnSystemResetMessage(MidiMessage mm1, MidiMessage mm2) {

        int value = 0;

        if (mm2.getStatus() == ShortMessage.SYSTEM_RESET) {
            value = compareOnUnknownMessage(mm1, mm2);
        } else {
            value = 1;
        }

        return value;
    }

    public int compareOnSystemCommonMessage(MidiMessage mm1, MidiMessage mm2) {

        int value = 0;

        if (mm1.getStatus() == ShortMessage.SYSTEM_RESET) {
            value = -1;
        } else if ((mm1.getStatus() & 0xF) == 0xF) {
            value = compareOnUnknownMessage(mm1, mm2);
        } else {
            value = 1;
        }

        return value;
    }

    public int compareOnControlChangeMessage(MidiMessage mm1, MidiMessage mm2) {

        int value = 0;

        if (mm1.getStatus() == ShortMessage.SYSTEM_RESET) {
            value = -1;
        } else if ((mm1.getStatus() & 0xF) == 0xF) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.CONTROL_CHANGE)
                == ShortMessage.CONTROL_CHANGE) {
            value = compareOnUnknownMessage(mm1, mm2);
        } else {
            value = 1;
        }

        return value;
    }

    public int compareOnProgramChangeMessage(MidiMessage mm1, MidiMessage mm2) {

        int value = 0;

        if (mm1.getStatus() == ShortMessage.SYSTEM_RESET) {
            value = -1;
        } else if ((mm1.getStatus() & 0xF) == 0xF) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.CONTROL_CHANGE)
                == ShortMessage.CONTROL_CHANGE) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.PROGRAM_CHANGE)
                == ShortMessage.PROGRAM_CHANGE) {
            value = compareOnUnknownMessage(mm1, mm2);
        } else {
            value = 1;
        }

        return value;
    }

    public int compareOnPitchBendMessage(MidiMessage mm1, MidiMessage mm2) {

        int value = 0;

        if (mm1.getStatus() == ShortMessage.SYSTEM_RESET) {
            value = -1;
        } else if ((mm1.getStatus() & 0xF) == 0xF) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.CONTROL_CHANGE)
                == ShortMessage.CONTROL_CHANGE) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.PROGRAM_CHANGE)
                == ShortMessage.PROGRAM_CHANGE) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.PITCH_BEND)
                == ShortMessage.PITCH_BEND) {
            value = compareOnUnknownMessage(mm1, mm2);
        } else {
            value = 1;
        }

        return value;
    }

    public int compareOnNoteOffMessage(MidiMessage mm1, MidiMessage mm2) {

        int value = 0;

        if (mm1.getStatus() == ShortMessage.SYSTEM_RESET) {
            value = -1;
        } else if ((mm1.getStatus() & 0xF) == 0xF) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.CONTROL_CHANGE)
                == ShortMessage.CONTROL_CHANGE) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.PROGRAM_CHANGE)
                == ShortMessage.PROGRAM_CHANGE) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.PITCH_BEND)
                == ShortMessage.PITCH_BEND) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.NOTE_OFF)
                == ShortMessage.NOTE_OFF) {
            value = compareOnUnknownMessage(mm1, mm2);
        } else {
            value = 1;
        }

        return value;
    }

    public int compareOnNoteOnMessage(MidiMessage mm1, MidiMessage mm2) {

        int value = 0;

        if (mm1.getStatus() == ShortMessage.SYSTEM_RESET) {
            value = -1;
        } else if ((mm1.getStatus() & 0xF) == 0xF) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.CONTROL_CHANGE)
                == ShortMessage.CONTROL_CHANGE) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.PROGRAM_CHANGE)
                == ShortMessage.PROGRAM_CHANGE) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.PITCH_BEND)
                == ShortMessage.PITCH_BEND) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.NOTE_OFF)
                == ShortMessage.NOTE_OFF) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.NOTE_ON)
                == ShortMessage.NOTE_ON) {
            value = compareOnUnknownMessage(mm1, mm2);
        } else {
            value = 1;
        }

        return value;
    }

    public int compareOnChannelPressureMessage(MidiMessage mm1, MidiMessage mm2) {

        int value = 0;

        if (mm1.getStatus() == ShortMessage.SYSTEM_RESET) {
            value = -1;
        } else if ((mm1.getStatus() & 0xF) == 0xF) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.CONTROL_CHANGE)
                == ShortMessage.CONTROL_CHANGE) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.PROGRAM_CHANGE)
                == ShortMessage.PROGRAM_CHANGE) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.PITCH_BEND)
                == ShortMessage.PITCH_BEND) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.NOTE_OFF)
                == ShortMessage.NOTE_OFF) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.NOTE_ON)
                == ShortMessage.NOTE_ON) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.CHANNEL_PRESSURE)
                == ShortMessage.CHANNEL_PRESSURE) {
            value = compareOnUnknownMessage(mm1, mm2);
        } else {
            value = 1;
        }

        return value;
    }

    public int compareOnKeyPressureMessage(MidiMessage mm1, MidiMessage mm2) {

        int value = 0;

        if (mm1.getStatus() == ShortMessage.SYSTEM_RESET) {
            value = -1;
        } else if ((mm1.getStatus() & 0xF) == 0xF) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.CONTROL_CHANGE)
                == ShortMessage.CONTROL_CHANGE) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.PROGRAM_CHANGE)
                == ShortMessage.PROGRAM_CHANGE) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.PITCH_BEND)
                == ShortMessage.PITCH_BEND) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.NOTE_OFF)
                == ShortMessage.NOTE_OFF) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.NOTE_ON)
                == ShortMessage.NOTE_ON) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.CHANNEL_PRESSURE)
                == ShortMessage.CHANNEL_PRESSURE) {
            value = -1;
        } else if ((mm1.getStatus() & ShortMessage.POLY_PRESSURE)
                == ShortMessage.POLY_PRESSURE) {
            value = compareOnUnknownMessage(mm1, mm2);
        } else {
            value = 1;
        }

        return value;
    }

    public int compareOnUnknownMessage(MidiMessage mm1, MidiMessage mm2) {
        int value = 0;

        byte[] mm1Data = mm1.getMessage();
        byte[] mm2Data = mm2.getMessage();

        if (mm1Data.length != mm2Data.length) {
            value = (mm1Data.length > mm2Data.length) ? 1 : -1;
        } else {
            int i = 1;

            do {
                if (mm1Data[i] != mm2Data[i]) {
                    value = (mm1Data[i] > mm2Data[i]) ? 1 : -1;
                }
                i++;
            } while (value == 0 && i < mm1Data.length);
        }

        return value;
    }

    @Override
    public Comparator reversed() {
        return Comparator.super.reversed();
    }

}
