//                              -*- Mode: Java -*- 
// QDModes.java --- 
// Author          : Matthias Wiesmann
// Created On      : Thu Dec 16 16:26:00 1999
// Last Modified By: Matthias Wiesmann
// Last Modified On: Thu Dec 16 16:26:11 1999
// Update Count    : 2
// Status          : Renamed
// 

package ch.epfl.lse.jqd.basics;

import java.awt.*;
import java.awt.image.*;

/** This abstract class contains the Quickdraw Modes Definitions
 *  @author Matthias Wiesmann
 *  @version 1.1
 */	
public abstract class QDModes {
    
    protected final static String[] mode_names = { "copy", "or", "xor", "bic", "not copy", "not or", "not xor", "not bic" } ; 
    
    public final static short copy = 0 ;
    public final static short or = 1 ;
    public final static short xor = 2 ;
    public final static short bic = 3 ;
    public final static short notCopy = 4 ;
    public final static short notOr = 5 ;
    public final static short notXor = 6 ;
    public final static short notBic = 7 ;
    /** pattern bit mask */ 
    public final static short pat = 8 ;
    /** dither bit mask */
    public final static short ditherCopy = 64 ;
	
    /** is the mode a pattern */
    public static boolean isPat(short mode) {
	return  ((mode & pat) !=0) ;
    } // isPat

    /** is the mode a dithered mode */
    public static boolean isDither(short mode) {
	return (mode > ditherCopy) ;
    } // isDither

    /** is the mode a xor mode */
    public static boolean isXor(short mode) {
	if ((mode % pat)==xor) return true ;
	return false ;
    } // isXor
    
    /** builds the base string of the mode */
    protected static String baseString(short mode) {
	final int index = mode % mode_names.length ; 
	return mode_names[index] ; 
    } // baseString
    
    /** builds the text version of the mode 
     *  @param mode the mode to convert
     *  @return the text version
     *  @see #baseString
     */ 
    public static String toString(short mode) {
	String type  ;
	String dither ;
	if (isPat(mode)) type="pattern" ; else type="source" ;
	if (isDither(mode)) dither="dither" ; else dither="" ;
	return(type+" "+baseString(mode)+" "+dither);
    } // toString
	
} // QDModes
