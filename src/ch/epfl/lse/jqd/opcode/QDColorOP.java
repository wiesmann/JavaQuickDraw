//                              -*- Mode: Java -*- 
// QDColorOP.java --- 
// Author          : Matthias Wiesmann
// Created On      : Fri Jul  9 11:48:52 1999
// Last Modified By: Matthias Wiesmann
// Last Modified On: Thu Nov 30 15:35:34 2000
// Update Count    : 8
// Status          : Renamed
// 

package ch.epfl.lse.jqd.opcode;

import ch.epfl.lse.jqd.basics.*;
import ch.epfl.lse.jqd.utils.*;
import ch.epfl.lse.jqd.io.*;

import java.awt.Color;
import java.awt.SystemColor;

import java.io.IOException;
import ch.epfl.lse.jqd.io.QDInputStream;

/** This opcode selects the color mode.
 * @author Matthias Wiesmann
 * @version 1.1
 */

public class QDColorOP implements QDOpCode 
{
    public static final short backColor = 0 ;
    public static final short frontColor  = 1 ;
    public static final short opColor = 2 ;
    public static final short hiliteColor = 3 ;
	
    protected Color theColor ;
    protected final short type ;
	
    public int 	read(QDInputStream theStream) 
	throws IOException, QDException {
	    theColor = theStream.readColor();
	    return(6);
	} // read

    public QDColorOP(short a_type) {
	    this.type = a_type ;
	} // QDColorOP
		
    /** Transforms a QuickDraw verb into text
     * @param a_type QuickDraw verb 
     * @return text
     */

    protected static String verb2String(short a_type) {
	switch (a_type) {
	case backColor:   return("back");
	case frontColor:  return("front");
	case opColor:     return("operation");
	case hiliteColor: return("hilite");
	} // switch
	return("");
    } // toString
    
   

    /** Executes the opcode.<br>
     *  <strong>Warning</strong>:
     * the <code>operation</code> color verb is not
     * handled
     * @param thePort the port to execute in
     */ 

    public void	execute(QDPort thePort) {
	switch (type) {
	case frontColor:  
	    thePort.fgColor = theColor ; 
	    break ;
	case backColor:   
	    thePort.bgColor = theColor ; 
	    break ;
	case hiliteColor: 
	    thePort.hlColor = theColor ;
	    break ;
	} // switch
	thePort.colorOperation();
    } // execute 
    
    
    public String toString() {
	return(verb2String(type)+" color opcode"+theColor);}
} // QDHeaderOP
