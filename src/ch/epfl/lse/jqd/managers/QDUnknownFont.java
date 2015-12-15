//
//  QDUnknownFont.java
//  JavaQuickdraw
//
//  Created by Matthias Wiesmann on 19.07.06.
//  Copyright 2006 JAIST. All rights reserved.
//

package ch.epfl.lse.jqd.managers  ;

import ch.epfl.lse.jqd.basics.QDException;

public class QDUnknownFont extends QDException {
    final int number ;
    public QDUnknownFont(int number)
    {
	this.number=number ;
    } // QDUnknowFont
    
    public String toString()
    {
	return("Unknown font number: "+number);
    } // toString
} // QDUnknowFont
