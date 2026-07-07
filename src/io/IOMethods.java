/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

/**
 *
 * @author Edward Jenkins © 2021-2023
 */
public class IOMethods {
    
    // constants
    public static final boolean LITTLE_ENDIAN = true;
    public static final boolean BIG_ENDIAN = false;
    
    /**
     * Reverses the byte order of an array of bytes for converting big to little
     * endian or vice versa
     * 
     * @param array The array of bytes to reverse
     * @param length length of array area to be flipped
     */
    public static void reverseEndian(byte[] array, int length) {

        // reverse the order of byes
        for (int i = 0, j = length - 1; i < length / 2; i++, j--) {
            byte value = array[j];
            array[j] = array[i];
            array[i] = value;
        }
    }
    
    /**
     * Set string to a specified length, adds [null] if it is smaller than length
     * 
     * @param oldString The string to change length of
     * @param length The length to enlarge or cut down to
     * @return The new string with the specified length
     */
    public static String setStringToLength(String oldString, int length) {
        String newString;
        
        if (oldString.length() > length) {
            newString = oldString.substring(0, length);
        } else if (oldString.length() < length) {
            StringBuilder sb = new StringBuilder(oldString);
            
            while (sb.length() < length) {
                sb.append("\0");
            }
            
            return sb.toString();
            
        } else {
            newString = oldString;
        }
        
        return newString;
    }
}
