//                              -*- Mode: Java -*- 
// QDBitUtils.java --- 
// Author          : Matthias Wiesmann
// Created On      : Thu Dec 16 16:37:53 1999
// Last Modified By: Matthias Wiesmann
// Last Modified On: Thu Dec 16 16:38:06 1999
// Update Count    : 1
// Status          : Renamed
// 

package ch.epfl.lse.jqd.utils;

import ch.epfl.lse.jqd.basics.*;
import java.awt.*;

/** This class contains static methods to do various 
 *  bit conversions and manipulations.
 *  It also contains RLE decompression methods. 
 *  @author Matthias Wiesmann
 *  @version 1.1
 */ 

public final class QDBitUtils
{
    protected QDBitUtils() {} 

    /** Counts the number of on bit in an integer
     * @param data the integer to check 
     * @count bitnumber until bit 
     */ 
    
    public final static int countBits(int data, int bitnumber) {
	int count=0 ;
	int bit= 1 ;
	for (int j=0 ; j<bitnumber ; j++) {
	    if ((data & bit)!=0) count++ ;
	    bit*=2 ;
	} // for j
	return(count);
    } // countBits
    
    /** Builds an array of bytes from a bitmap 
     *  @param bitData the bit map data 
     *  @param d the dimension of the bit map
     *  @param rb the number of bytes per row
     *  @return an array of bytes
     */ 

    public final static byte[] bit2Byte(byte[][] bitData, Dimension d, int rb) {
	byte pixData[]= new byte[d.height*d.width] ;
	for(int line=0;line<d.height;line++)
	    for(int row=0;row<rb;row++) {
		int number = bitData[line][row] ;
		int bit=1 ;
		for (int b=0; b<8 ; b++) {
		    int line_index = row*8+(7-b) ;
		    if (line_index<d.width) {
			int index = line_index + line*d.width ;
			if ((number & bit)!=0) pixData[index]=1 ;
			else pixData[index]=0 ;
		    } // we are inside
		    bit*=2 ;
		} // for bits
	    } // for line/row
	return(pixData);
    } // bit2Byte
    
    /** Builds an array of bytes from a 2bit pixmap 
     *  @param bitData the bit map data 
     *  @param d the dimension of the bit map
     *  @param rb the number of bytes per row
     *  @return an array of bytes
     */ 

    public final static byte[] twobit2Byte(byte[][] bitData, Dimension d, int rb) {
	byte pixData[]= new byte[d.height*d.width] ;
	for(int line=0;line<d.height;line++)
	    for(int row=0;row<rb;row++) {
		int number = bitData[line][row] ;
		int bit=1 ;
		for (int b=0; b<4 ; b++) {
		    int line_index = row*4+(3-b) ;
		    if (line_index<d.width) {
			int index = line_index + line*d.width ;
			byte result = 0 ;
			if ((number & bit)!=0) result+=1 ;
			if (((number & (2*bit))!=0)) result+=2 ;
			pixData[index]=result ;
		    } // we are inside
		    bit*=4 ;
		} // for b
	    } // for line / row
	return(pixData);
    } // twobit2Byte
    
    /** Builds an array of bytes from a 4bit pixmap 
     *  @param bitData the bit map data 
     *  @param d the dimension of the bit map
     *  @param rb the number of bytes per row
     *  @return an array of bytes
     */ 

    public final static byte[] fourbit2Byte(byte[][] bitData, Dimension d, int rb) {
	byte pixData[]= new byte[d.height*d.width] ;
	for(int line=0;line<d.height;line++)
	    for(int row=0;row<rb;row++) {
		int number = bitData[line][row] ;
		int bit=1 ;
		for (int b=0; b<2 ; b++) {
		    int line_index = row*2+(1-b) ;
		    if (line_index<d.width) {
			int index = line_index + line*d.width ;
			byte result = 0 ;
			if ((number & bit)!=0) result+=1 ;
			if (((number & (2*bit))!=0)) result+=2 ;
			if (((number & (4*bit))!=0)) result+=4 ;
			if (((number & (8*bit))!=0)) result+=8 ;
			pixData[index]=result ;
		    } // we are inside
		    bit*=16 ;
		} // for b
	    } // for line / row
	    return(pixData);
    } // twobit2Byte	
    
    /** Builds an array of bytes from a 8-bit pixmap 
     *  @param bitData the bit map data 
     *  @param d the dimension of the bit map
     *  @param rb the number of bytes per row
     *  @return an array of bytes
     */ 

    public final static byte[] byte2byte(byte[][] bitData, Dimension d, int rb) {
	byte pixData[]= new byte[d.height*d.width] ;
	for(int line=0;line<d.height;line++)
	    for(int row=0;row<d.width;row++) {
		pixData[line*d.width+row]=bitData[line][row] ;
	    } // for
	return pixData;
    } // byte2byte

    /** Transforms a byte into an integer value 
     *  @param b the byte to convert
     *  @return the integer value 
     */ 

    private final static int byte2int(byte b) {
	return (int) b & 0xff  ;
    } // byte2int

    /** Converts 3 bytes into a RGB color 
     *  @param r red value 
     *  @param g green value
     *  @param b blue value 
     */ 

    protected final static int byte2RGB(byte r, byte g, byte b) {
	return 
	    (byte2int(r) * 0x10000) |
	    (byte2int(g) * 0x100) |
	    (byte2int(b)) ;
    } // byte2RGB

    /** Converts an array of bytes into an array of RGB integers
     *  @param bitData the matrix of bytes
     *  @param d the dimension of the pixmap
     *  @return an array of 24-bit RGB integers 
     */ 

    public final static int[] byte2RGB(byte[][] bitData, Dimension d) {
	int pixData[] = new int[d.height*d.width] ;
	for(int line=0;line<d.height;line++) {
	    for(int row=0;row<d.width;row++) {
		final byte r = bitData[line][row] ;
		final byte g = bitData[line][row+d.width]  ;
		final byte b = bitData[line][row+2*d.width] ;
		pixData[line*d.width+row] = byte2RGB(r,g,b);
	    } // for row 
	} // for line
	return pixData ;
    } // byte2RGB

    /** Converts an array of shorts into an array of RGB integers 
     *  @param bitData the matrix of bytes
     *  @param d the dimension of the pixmap
     *  @return an array of 16-bit RGB integers 
     */ 

    public final static int[] short2RGB(short[][] bitData, Dimension d) {
	int pixData[] = new int[d.height*d.width] ;
	for(int line=0;line<d.height;line++) {
	    for(int row=0;row<d.width;row++) {
		pixData[line*d.width+row] = bitData[line][row] & 0xffff ;
	    } // for row 
	} // for line
	return pixData ;
    } // short2RGB
    
    /** Unpacks a line of data in 8 bit chunks
     *  @param in the line to unpack
     *  @param out the line to unpack into
     *  @exception jqd.basics.QDException corrupt data line
     */ 

    public final static void unpackLine(byte[] in, byte[] out) 
	throws QDException {
	int in_count = 0 ;
	int out_count = 0 ;
	while (in_count<in.length) {
	    int flag = in[in_count] ;
	    in_count ++ ;
	    if (flag<0)  {
		final int runLength = (-flag)+1;
		byte runData =  in[in_count] ;
		in_count ++ ;
		for (int i=0;i<runLength;i++)
		    out[out_count+i] = runData ;
		out_count+=(runLength) ;
	    } // run length
	    else {
		final int disLength = flag +1 ;
		for (int i=0;i<disLength;i++)
		    out[out_count+i] = in[in_count+i] ;
		out_count+=(disLength) ;
		in_count+=(disLength) ;
	    } // discrete run
	} // while
	if (in_count!=in.length) throw new QDUnpackException();
    } // unpackLine

    /** Converts two bytes into a short 
     *  @param hi hi byte
     *  @param lo low byte
     *  @return the short value
     */ 

    protected final static short bytes2short(byte hi, byte lo) {
	return (short) (((hi & 0xff)* 0x100 ) | (lo & 0xff));
    } // byte2short

    /** Unpacks a line of data in 16 bit chunks
     *  @param in the line to unpack
     *  @param out the line to unpack into
     *  @exception jqd.basics.QDException corrupt data line
     */ 

    public final static void unpackLine(byte[] in, short[] out)
	throws QDException {
	int in_count = 0 ;
	int out_count = 0 ;
	while (in_count<in.length) {
	    int flag = in[in_count] ;
	    in_count ++ ;
	    if (flag<0)  {
		final int runLength = (-flag)+1;
		final short runData =  bytes2short(in[in_count],in[in_count+1]);
		in_count +=2 ;
		for (int i=0;i<runLength;i++)
		    out[out_count+i] = runData ;
		out_count+=(runLength) ;
	    } // run length
	    else {
		final int disLength = flag +1 ;
		for (int i=0;i<disLength;i++) {
		    final short data = bytes2short(in[in_count+i*2],
						   in[in_count+1+i*2]);
		    out[out_count+i] = data ;
		} // for
		out_count+=(disLength) ;
		in_count+=(disLength*2) ;
		} // discrete run
	} // while
	if (in_count!=in.length) throw new QDUnpackException();
    }// unpackLine


} // QDBitUtils


/** Conversion Exception */ 	

class QDBitConversionException extends QDException
{
} // QDBitConversionException


