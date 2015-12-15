//                              -*- Mode: Java -*- 
// QDLinkManager.java --- 
// Author          : Matthias Wiesmann
// Created On      : Thu Dec 16 16:44:10 1999
// Last Modified By: Matthias Wiesmann
// Last Modified On: Thu Dec 16 16:46:04 1999
// Update Count    : 1
// Status          : Renamed
// 

package ch.epfl.lse.jqd.managers;

import java.util.Hashtable;
import java.util.Properties;
import java.util.Enumeration;

import java.awt.Rectangle;
import java.awt.Point;

/** This class implements a link manager
 *  the different texts of a picture
 *  a linked with their drawing rectangles
 *  This tool can be used to make a picture
 *  clickable
 * @author Matthias Wiesmann
 * @version 1.0
 */ 

public class QDLinkManager 
{
    protected Hashtable rects ;
    protected boolean    concat_mode= false;
    protected Rectangle  concat_rect= null; 
    protected String     concat_text = null;

    public QDLinkManager()  {
	   resetText();
    } // QDLinkManager
    
    /** Resets the links */
    
    public void resetText() {
	rects = new Hashtable();
    } // resetText
    
    /** Links a text with a rectangle
     * @param text the text 
     * @param rect the rectangle
     */ 

    protected void putText(String text,Rectangle rect) {
	rects.put(rect,text);
    } // putText
    
    /** Finds the text at a position
     * @param p the point to check
     * @return the text at point p
     * <code>null</code> if none
     */ 

    public String getText(Point p) {
	Enumeration e = rects.keys();
	while(e.hasMoreElements()) {
	    final Rectangle r = 
		(Rectangle) e.nextElement();
	    if (r.contains(p)){
		final Object o = rects.get(r);
		if (o == null) return  null; 
		final String s = (String) o ;
		return s; 
	    } // if
	} // while
	return  null;
    } // getText
    
    /** Concatenates the text with the current text 
     *  and adds the rect to the current rect
     * @param text the text
     * @param rect the rectangle
     * @see #concat_text
     * @see #concat_rect
     */ 

    protected void concatText(String text,Rectangle rect) {
	if (concat_rect== null) {
	    concat_text = text ;
	    concat_rect = rect ;
	    return ;
	} else {
	    concat_text+=text ;
	    concat_rect.add(rect);
	} // if else
    } // concatText
    
    /** Links a text and a rect, either directly
     *  or by concatenating it with the previous
     * @param text the text
     * @param rect the rectangle
     */ 

    public void addText(String text,Rectangle rect) {
	if (concat_mode) concatText(text,rect);
	else putText(text,rect);
    } // addText
    
    /** Sets the mode of the manager 
     * @param mode <dl compact>
     * <dt><code>true</code><dd>text and rects are concatenated
     * <dt><code>false</code><dd>text and rects are added directly
     * </dl>
     */

    public void setConcat(boolean mode) {
	concat_mode = mode ;
	if (mode) {
	    concat_text = null ;
	    concat_rect = null ;
	} else {
	    if (concat_text!= null)
		putText(concat_text,concat_rect);
	}// if/else
    } // setConcat
} // QDLinkManager
