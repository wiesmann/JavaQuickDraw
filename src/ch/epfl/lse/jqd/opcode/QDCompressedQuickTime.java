

package ch.epfl.lse.jqd.opcode;

import ch.epfl.lse.jqd.basics.*;
import ch.epfl.lse.jqd.utils.*;
import ch.epfl.lse.jqd.io.*;

import ch.epfl.lse.jqd.qt.CompressedImage;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.text.MessageFormat ; 

/** This opcode represents compressed QuickTime Data.
 *  Parsing works with JPEG and TIFF encoded files, there seems to be an issue with formats that reference clut data. 
 *  Maybe there is something wrong in the offset calculation, check with long data files. 
 *  @version 2.0
 *  @author Matthias Wiesmann
 */ 

public class QDCompressedQuickTime implements QDOpCode {
    /** Length of QuickTime data */
    protected int length ;
    /** Compressed image  */
    protected CompressedImage image ;
    /** number of images ? */
    protected int number ;
    
    final static protected int SKIP_HEAD = 4*16 ; 
    final static protected int SKIP_MIDDLE = 8 ; 
    final static protected int SKIP_TAIL = 128 ; 
    
    public int read(QDInputStream input) throws IOException, QDException {
	length=input.readInt();
	number=input.readInt();
	input.skipBytes(SKIP_HEAD-(SKIP_MIDDLE+QDInputStream.RECT_SIZE)) ; 
	final Rectangle destination = input.readRect() ;
	input.skipBytes(SKIP_MIDDLE); 
	image = new CompressedImage(destination);
	final int intern = image.read(input);
	final int data_read = image.readData(input); 
	final int data_len = length - intern - SKIP_HEAD - 4;
	if (data_read<data_len) {
	    final int align_skip = data_len-data_read ;
	    assert(align_skip>0) ; 
	    System.err.println("aligning: "+align_skip); 
	    input.skipBytes(align_skip); 
	}
	if (image.isSupported()) {
	    input.skipBytes(SKIP_TAIL) ; 
	    return 4+length + SKIP_TAIL ;
	} else {
	    return 4+length; 
	}
    } // read
    
    public void	execute(QDPort thePort) throws QDException {
	thePort.stdBits(image) ;
    } // execute 
    
    protected final static String FORMAT = "QuickTime Compressed [length={0,Number,#######}, number={1}, {2}]" ; 
    
    public String toString() {
	final Object[] array = { new Integer(length), new Integer(number), image } ;
	return MessageFormat.format(FORMAT,array) ; 
    } // toString
    
} // QDCompressedQuickTime
