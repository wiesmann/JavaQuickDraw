//                              -*- Mode: Java -*- 
// QDVerbs.java --- 
// Author          : Matthias Wiesmann
// Created On      : Thu Dec 16 16:23:46 1999
// Last Modified By: Matthias Wiesmann
// Last Modified On: Thu Dec 16 16:24:04 1999
// Update Count    : 2
// Status          : Renamed
// 

package ch.epfl.lse.jqd.basics;

/** This class cannot be instanciated 
 *  it contains all constant and methods for the different quickDraw Verbs
 *  @author Matthias Wiesmann
 *  @version 1.1 revised
 */

public  abstract class QDVerbs 
{
    
    public final static String[] verb_names = { "frame", "paint", "erase", "invert", "fill" } ; 
    
    /** draw the outline of the object */
    public static final short frameVerb = 0 ;
    /** fill the object with the pattern */
    public static final short paintVerb = 1 ;
    /** fill the object with the background color */
    public static final short eraseVerb = 2 ;
    /** invert the object - not well supported */
    public static final short invertVerb = 3 ;
    /** fill the object with the fill color */
    public static final short fillVerb = 4 ;
    /** not an actual QD Verb, used to signal text drawing */ 
    public static final short txtVerb = 255 ; 
    /** not an actual QD Verb, used to signal highlight mode */
    public static final short highVerb = 256 ;


    /** Gets the text version of the QuickDraw Verb
     *  @param theVerb the verb to transalte
     *  @return a text version of the verb 
     */ 

    public static final String toString(short verb) {
	if (verb>=0 && verb< verb_names.length) {
	    return verb_names[verb] ; 
	} else {
	    return "unknown verb" ; 
	} 
    } // toString
    
    public static final String toString(int verb) {
	return toString((short) verb);
    } // toString
} // QDVerbs


