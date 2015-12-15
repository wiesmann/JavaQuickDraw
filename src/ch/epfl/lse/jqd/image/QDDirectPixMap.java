//                              -*- Mode: Java -*- 
// QDDirectPixMap.java --- 
// Author          : Matthias Wiesmann
// Created On      : Tue Nov 21 15:22:07 2000
// Last Modified By: Matthias Wiesmann
// Last Modified On: Wed Dec  6 11:55:43 2000
// Update Count    : 7
// Status          : Unknown, Use with caution!
// 

package ch.epfl.lse.jqd.image;

import ch.epfl.lse.jqd.basics.*;
import ch.epfl.lse.jqd.utils.*;
import java.awt.*;
import java.awt.image.*;

import ch.epfl.lse.jqd.io.QDInputStream;
import java.io.IOException;

/** QuickDraw Direct Pix Map
 *  This class describes a RGB described pixmap
 *  @author Matthias Wiesmann
 *  @version 1.0 revised
 */ 

public class QDDirectPixMap extends QDPixMap
{
    /** Base address of the PixMap in memory.
     *  Not used / neither usefull
     */ 
    protected long baseAddr ;

    public QDDirectPixMap()
	{} // QDDirectPixMap
    /** The color model for direct 24 bits images */ 
    protected final static ColorModel direct24Model = new DirectColorModel(24,0xff0000,0xff00,0xff);
    /** The color model for direct 16 bits images */ 
    protected final static ColorModel direct16Model = new DirectColorModel(15,0x7C00,0x3e0,0x1f);
    
    public int 	readStart(QDInputStream theStream) 
	throws IOException, QDException {
	baseAddr=theStream.readInt();
	rowBytes = convertRowBytes(theStream.readShort());
	bounds = theStream.readRect();
	pmVersion = theStream.readShort() ;
	packType = theStream.readShort() ;
	packSize=theStream.readInt();
	hRes = theStream.readFixed() ;
	vRes = theStream.readFixed() ;
	pixelType=theStream.readShort();
	pixelSize=theStream.readShort();
	cmpCount=theStream.readShort();
	cmpSize=theStream.readShort();
	planeByte=theStream.readInt();
	colorTableID=theStream.readInt();
	theStream.skipBytes(4);
	return(40);
    } // read
   
    /** Reads data for packing mode 1?
     *  Not implemented. 
     */
    public int readPack1(QDInputStream theStream) 
	throws IOException, QDException {
	int data_size = rowBytes * bounds.height ;
	return(data_size);
    } // readPack1
    
    /** Reads data for packing mode 3 
     *  (16-bit RLE compressed pixmaps). 
     *  @param theStream the data stream
     *  @return the number of byte read
     *  @exception QDException illegal component number
     */ 
    public int readPack3(QDInputStream theStream) 
	throws IOException, QDException {
	int data_len = 0;
	short data[][] = new short[bounds.height][bounds.width] ;
	for (int line=0;line<bounds.height;line++) {
	    int lineCount ;
	    if (rowBytes>250)	{ // line Count is Byte
		lineCount=theStream.readUnsignedShort();
		data_len+=(2+lineCount);
	    } else	{ // line count is word
		lineCount=theStream.readUnsignedByte() ;
		data_len+=(1+lineCount);
	    } // line count is word
	    byte[] packed = new byte[lineCount] ;
	    theStream.readFully(packed);
	    QDBitUtils.unpackLine(packed,data[line]);
	} // for
	int pixData[] = QDBitUtils.short2RGB(data,QDUtils.rect2Dim(bounds));
	image_prod = new MemoryImageSource(bounds.width,bounds.height,
					   direct16Model,
					   pixData,0,bounds.width);
	return(data_len);
    } // readPack3

    /** Reads data for packing mode 4 
     *  (24-bit RLE compressed pixmap). 
     *  @param theStream the data stream
     *  @return the number of byte read
     *  @exception QDException illegal component number
     */ 

    public int readPack4(QDInputStream theStream) 
	throws IOException, QDException {
	int data_len = 0;
	if (cmpCount!=3) throw new QDWrongComponentNumber(cmpCount);
	byte data[][] = new byte[bounds.height][bounds.width*cmpCount] ;
	for (int line=0;line<bounds.height;line++) {
	    int lineCount; 
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
	int pixData[] = QDBitUtils.byte2RGB(data,QDUtils.rect2Dim(bounds));
	image_prod = new MemoryImageSource(bounds.width,bounds.height,direct24Model,pixData,0,bounds.width);
	return (data_len);
    } // readPack4
    
    /** This method reads the data according to the packing type
     *  @param theStream input Stream
     *  @exception IOException I/O problem
     *  @exception QDException <ul>
     *             <li>unknown pack type
     *             <li>wrong number of components
     *             </ul>
     *  @see #readPack4
     *  @see #readPack3
     */ 

    public int 	readData(QDInputStream theStream) 
	throws IOException, QDException {
	// System.err.println(this.toString());
	int data_len = 0 ;
	switch (packType) {
	case 3: data_len+=readPack3(theStream); break ;
	case 4: data_len+=readPack4(theStream); break ;
	default: throw  new QDUnknownPackException(packType);
	} //
	return data_len ; 
    } // readData
	
    protected String nameString()
	{ return("Direct Pixmap");}
    
} // QDDirectPixMap
	
	



