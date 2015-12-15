//                              -*- Mode: Java -*- 
// CompressedImage.java --- 
// Author          : Matthias Wiesmann
// Created On      : Thu Dec 16 16:49:54 1999
// Last Modified By: Matthias Wiesmann
// Last Modified On: Thu Dec 16 16:50:19 1999
// Update Count    : 2
// Status          : Renamed
// 

package ch.epfl.lse.jqd.qt;

import ch.epfl.lse.jqd.basics.*;
import ch.epfl.lse.jqd.utils.*;
import ch.epfl.lse.jqd.io.* ; 
import ch.epfl.lse.jqd.image.* ; 

import java.awt.*;
import java.awt.image.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.text.MessageFormat ; 

/** This class represents a QuickTime image  
 *  For the moment the only Codec that is supported is JPEG, as we can hand of the data to AWT. 
 *  @version 2.0
 *  @author Matthias Wiesmann
 *  @see http://www.koders.com/c/fidB7F8536ECDEB81FE572D5CC9F65B69C43A1CC4B4.aspx
 */ 

public class CompressedImage implements QDBitSource {
    
    protected Rectangle destination ; 
    /** total size of ImageDescription including extra data ( CLUTs and other per sequence data ) */
    protected int size ;
    /** what kind of codec compressed this data */
    protected String compressor_creator ;
    protected String compressor_developper ;
    protected short version ;
    protected short revision ;
    protected int   temporal_quality ;
    protected int   spatial_quality ;
    /** how many pixels wide and high is this data */
    protected Dimension dimension ;
    /** horizontal resolution */
    protected double h_res ;
    /** vertical resolution */
    protected double v_res ;
    protected int data_size ;
    protected short frame_number ;
    protected String name ;
    /** what depth is this data (1-32) or ( 33-40 grayscale ) */
    protected short depth ;
    /** clut id or if 0 clut follows  or -1 if no clut */
    protected short CLUT_id ; 
    protected byte[] data ; 
    
    public CompressedImage(Rectangle destination) {
	this.destination = destination ; 
    } // CompressedImage
    
    public int read(QDInputStream input) throws IOException, QDException {
	size = input.readInt();
	compressor_creator = input.readCreator() ; 
	input.skipBytes(8); 
	version = input.readShort();
	revision = input.readShort(); 
	compressor_developper = input.readCreator() ; 
	temporal_quality = input.readInt(); 
	spatial_quality = input.readInt();  
	dimension = input.readDimension() ;
	h_res = input.readFixed() ; 
	v_res = input.readFixed() ; 
	data_size = input.readInt(); 
	frame_number = input.readShort(); 
	name = input.readStr31(); 
	depth = input.readShort(); 
	CLUT_id = input.readShort(); 
	return size ;
    } // load
    
    public int readData(QDInputStream input) throws IOException {
	data = new byte[data_size] ; 
	return input.read(data,0,data_size) ;
    } // readData 
    
    public Image getImage() {
	final Toolkit kit = Toolkit.getDefaultToolkit() ; 
	final Image image = kit.createImage(data) ; 
	return image ;
    } // getImage 
    
    public boolean isSupported() {
	return compressor_creator.equals("jpeg");
    } // isSupported
    
    public Rectangle getDestination() {
	return destination ;  
    } // getDestination
    
    protected final static String FORMAT = "{0}, destination={1}, dimension={2}, compressor={3}-{4}, resolution=[{5},{6}], depth={7} size={8,Number,########}, datasize={9,Number,#######}, CLUT={10}" ; 
    
    public String toString() { 
	final Object[] array = { name, destination, dimension, compressor_creator, compressor_developper, new Double(h_res), 
	    new Double(v_res), new Integer(depth), new Integer(size), new Integer(data_size), new Integer(CLUT_id) } ; 
	return MessageFormat.format(FORMAT,array); 
    } // toString
    

} // CompressedImage
