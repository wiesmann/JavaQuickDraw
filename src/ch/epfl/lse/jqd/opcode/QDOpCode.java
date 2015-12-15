//                              -*- Mode: Java -*- 
// QDOpCode.java --- 
// Author          : Matthias Wiesmann
// Created On      : Fri Jul  9 14:20:54 1999
// Last Modified By: Matthias Wiesmann
// Last Modified On: Fri Dec  1 12:15:36 2000
// Update Count    : 9
// Status          : Unknown, Use with caution!
// 

package ch.epfl.lse.jqd.opcode;

import ch.epfl.lse.jqd.basics.*;
import ch.epfl.lse.jqd.utils.*;
import ch.epfl.lse.jqd.io.*;
import java.awt.*;

import java.io.IOException;


/** This interface describes an QuickDraw Opcode
 *  @version 1.1
 *  @author Matthias Wiesmann
 */

public interface QDOpCode {
    static final int NoOp = 0x000 ;
    static final int ClipRegionOP = 0x0001 ;
    static final int BackPatOP = 0x0002 ;
    static final int TxFontOP =  0x0003 ;
    static final int TxFaceOP =  0x0004 ;
    static final int TxModeOP =  0x0005 ;
    static final int SpExtraOP = 0x0006 ; 
    static final int PenSizeOP = 0x0007 ;
    static final int PenModeOP = 0x0008 ;
    static final int PenPatOP = 0x0009 ;
    static final int FillPatOP = 0x000A ;
    static final int OvalSizeOP = 0x000B ;
    static final int SetOriginOP = 0x000C ;
    static final int TxSizeOP = 0x000D ;
    static final int ForeColorOP = 0x000E ;
    static final int BackColorOP = 0x000F ;
    static final int TxRatioOP = 0x0010 ;
    static final int VersionOP = 0x0011 ;
    static final int BkPixPat = 0x0012 ;    
    static final int PnPixPat = 0x0013 ;    
    static final int FillPixPat = 0x0014 ;  
    static final int PnLocHFrac = 0x0015 ;  
    static final int ChExtra = 0x0016;      // ! not implemented
    static final int RGBForeColorOP = 0x001A ;
    static final int RGBBackColorOP = 0x001B ;
    static final int HiliteMode = 0x001C;  
    static final int HiliteColor = 0x001D; 
    static final int DefHilite = 0x001E;   
    static final int OpColor = 0x001F;      // ! not implemented
    static final int LineOP = 0x0020 ; 
    static final int LineFromOP = 0x0021 ; 
    static final int ShortLineOP = 0x0022 ;
    static final int ShortLineFromOP = 0x0023 ;
    static final int LongTextOP = 0x0028 ;
    static final int DHTextOP = 0x0029 ;
    static final int DVTextOP = 0x002A ;
	
    static final int DHDVTextOP = 0x002B ;
	
    static final int FontNameOP = 0x002C ;
    static final int GlyphStateOP = 0x002E ;
    static final int FrameRectOP = 0x0030 ;
    static final int PaintRectOP = 0x0031 ;
    static final int EraseRectOP = 0x0032 ;
    static final int InvertRectOP = 0x0033 ;
    static final int FillRectOP  = 0x0034 ;
    static final int FrameSameRectOP = 0x0038 ;
    static final int PaintSameRectOP = 0x0039 ;
    static final int FrameRoundRectOP = 0x0040 ;
    static final int PaintRoundRectOP = 0x0041 ;
    static final int FrameSameRoundRectOP = 0x0048 ;
    static final int PaintSameRoundRectOP = 0x0049 ;
    static final int FrameOvalOP = 0x0050 ;
    static final int PaintOvalOP = 0x0051 ;
    static final int FillOvalOP = 0x0054 ;
    static final int FrameSameOvalOP = 0x0058 ;
    static final int PaintSameOvalOP = 0x0059 ;
    static final int FrameArcOP = 0x0060 ;
    static final int PaintArcOP = 0x0061 ;
    static final int FrameSameArcOP = 0x0068 ;
    static final int PaintSameArcOP = 0x0069 ;
    static final int FramePolyOP = 0x0070 ;
    static final int PaintPolyOP = 0x0071 ;
    static final int FrameRegionOP = 0x0080 ;
    static final int PaintRegionOP = 0x0081 ;
    static final int EraseRegionOP = 0x0082 ;
    static final int InvertRegionOP = 0x0083 ;
    static final int FillRegionOP =  0x0084 ;
    static final int BitRectOP =  0x0090 ;
    static final int DirectBitsRectOP = 0x009A ;
    static final int PackBitRectOP =  0x0098 ;
    static final int ShortCommentOP = 0x00A0 ;
    static final int LongCommentOP = 0x00A1 ;
    static final int DefHiliteOP = 0x001E ;
    
    static final int EOFOP = 0x00FF ; 
	
    static final int HeaderOP = 0x0C00 ;

    static final int CompressedQuickTime = 0x8200 ;

    /** Reads the opcode data from a stream
     *  @param theStream the stream containing the data
     *  @return the number of bytes read
     *  @exception IOException problem with the stream
     *  @exception QDException QuickDraw parsing error 
     */ 
    int read(QDInputStream theStream) 
	throws IOException, QDException ;

    /** Executes the opcode into a QuickDraw Port
     *  @param thePort the port to execute into
     *  @exception QDException QuickDraw display error 
     */ 
    void execute(QDPort  thePort) 
	throws QDException;
} // OpCode
	
