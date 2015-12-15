//                              -*- Mode: Java -*- 
// QDPackedBitMap.java --- 
// Author          : Matthias Wiesmann
// Created On      : Tue Nov 21 15:24:48 2000
// Last Modified By: Matthias Wiesmann
// Last Modified On: Wed Dec  6 11:53:13 2000
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

/** Quickdraw Packed BitMap 
 *  @version 1.0 revised 
 *  @author Matthias Wiesmann
 */ 

public class QDPackedBitMap extends QDBitMap
{
    public QDPackedBitMap(int rowBytes) {
	super(rowBytes);
    } // QDBitMap
    
    /** Reads the compacted data 
     */ 

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
	pixData = QDBitUtils.bit2Byte(data,QDUtils.rect2Dim(bounds),rowBytes) ;
	ColorModel twoBits = QDColorTable.get1BitModel(mode);
	image_prod = new MemoryImageSource(bounds.width,
					   bounds.height,
					   twoBits,pixData,0,bounds.width);
	return(data_len);
    } // readData
    
    /** Factory method, builds either a packed or unpacked bitmap 
     *  @param rb the rowbytes value 
     *  @return the right BitMap kind
     */ 

    public static QDBitMap newMap(short rb) {
	if (rb >0) return new QDPackedBitMap(rb);
	else return new QDPackedPixMap(rb);
    } // newMap
    
} // QDPackedBitMap
