//                              -*- Mode: Java -*- 
// QDPackedPixMap.java --- 
// Author          : Matthias Wiesmann
// Created On      : Tue Nov 21 15:22:24 2000
// Last Modified By: Matthias Wiesmann
// Last Modified On: Wed Dec  6 11:53:47 2000
// Update Count    : 2
// Status          : Unknown, Use with caution!
// 

package ch.epfl.lse.jqd.image;

import ch.epfl.lse.jqd.basics.*;
import ch.epfl.lse.jqd.utils.*;

import java.awt.*;
import java.awt.image.*;
import ch.epfl.lse.jqd.io.QDInputStream;
import java.io.IOException;

/** This class describes a RLE packed pixmap
 *  @author Matthias Wiesmann
 *  @version 1.0 revised
 *  @see java.utils.QDBitUtils#unpackLine
 */ 

public class QDPackedPixMap extends QDPixMap
{
    public QDPackedPixMap(int rowBytes) {
	super(rowBytes);
    } // QDPixMap
    
    protected int readData(QDInputStream theStream) 
	throws IOException, QDException {
	int data_len = 0;
	byte data[][] = new byte[bounds.height][rowBytes] ;
	for (int line=0;line<bounds.height;line++) {
	    int lineCount ;
	    if (rowBytes>250)	{ // line Count is Byte
		lineCount=theStream.readUnsignedShort();
		data_len+=(2+lineCount);
	    } else { // line count is word
		lineCount=theStream.readUnsignedByte() ;
		data_len+=(1+lineCount);
	    } // line count is word
	    byte[] packed = new byte[lineCount] ;
	    theStream.readFully(packed);
	    QDBitUtils.unpackLine(packed,data[line]);
	} // for line	
	byte[] pixData  ;
	switch (pixelSize) {
	case 1: pixData = QDBitUtils.bit2Byte(data,QDUtils.rect2Dim(bounds),
					      rowBytes) ; break ;
	case 2: pixData = QDBitUtils.twobit2Byte(data,QDUtils.rect2Dim(bounds),
						 rowBytes) ; break ;
	case 4: pixData = QDBitUtils.fourbit2Byte(data,QDUtils.rect2Dim(bounds),
						  rowBytes) ; break ;
	case 8: pixData = QDBitUtils.byte2byte(data,QDUtils.rect2Dim(bounds),
					       rowBytes) ; break ;
	default: throw new QDPixMapNotSupported(pixelSize,pixelType,packType);
	} // switch
	image_prod = new MemoryImageSource(bounds.width,
					   bounds.height,
					   colorTable.toModel(mode),
					   pixData,0,bounds.width);
	return(data_len);
    } // readData
    
    protected String nameString() { 
	return("packed pixmap");}
} //  QDPackedPixMap


