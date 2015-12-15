/*
Quickdraw QDBitsRectOP OpCodes 
*/
package ch.epfl.lse.jqd.opcode;

import ch.epfl.lse.jqd.basics.*;
import ch.epfl.lse.jqd.utils.*;
import ch.epfl.lse.jqd.image.*;
import java.awt.*;

import ch.epfl.lse.jqd.io.QDInputStream;

public class QDPackedBitsRectOP extends QDBitsRectOP {

    public int read(QDInputStream theStream) 
	throws java.io.IOException, QDException {
	final short rowbytes = theStream.readShort();
	bMap = QDPackedBitMap.newMap(rowbytes);
	final int b_size = bMap.read(theStream);
	return(b_size+2);
    }// read

    public String toString() {
	return("Packed Bit Rect "+bMap);
    }
} // QDBitsRectOP
