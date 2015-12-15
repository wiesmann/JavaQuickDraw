//                              -*- Mode: Java -*- 
// QDWrongComponentNumber.java --- 
// Author          : Matthias Wiesmann
// Created On      : Wed Dec  6 11:47:44 2000
// Last Modified By: Matthias Wiesmann
// Last Modified On: Wed Dec  6 12:50:15 2000
// Update Count    : 4
// Status          : OK
// 

package ch.epfl.lse.jqd.image;

import ch.epfl.lse.jqd.basics.QDException;

/** Signals that a wrong number of components was declared in a Pixmap.
 *  @author Matthias Wiesmann
 *  @version 1.0
 */ 

public class QDWrongComponentNumber extends QDException
{
    protected final int component_number ;
    public QDWrongComponentNumber(int n) {
	component_number=n ;
    }// QDWrongComponentNumber

     public String toString() {
	 return "Illegal Number of Components for Direct Mode: "+
	     component_number ;
     }// toString
} 
