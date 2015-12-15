//                              -*- Mode: Java -*- 
// QDParser.java --- 
// Author          : Matthias Wiesmann
// Created On      : Fri Jul  9 11:32:41 1999
// Last Modified By: Matthias Wiesmann
// Last Modified On: Tue Nov 21 15:27:15 2000
// Update Count    : 19
// Status          : OK
// 

package ch.epfl.lse.jqd.opcode;

import ch.epfl.lse.jqd.basics.*;
import ch.epfl.lse.jqd.utils.*;

import java.awt.*;

/** This class converts opcodes numbers into their class equivalent.
*  It acts as an Opcode factory.
*  <br><strong>To Do:</strong> implement it using hashtable and reflections.
*
*  @author Matthias Wiesmann
*  @version 1.0
*/ 

public final class QDParser {
    /** OpCode factory.
    * Reserved opcodes are produced by the 
    * <code>factory</code> method in class 
    * <code>QDReservedOP</code>.
    * @see QDReservedOP#factory. 
    * @param theCode the number of the opcode
    * @return an instance of the correct opcode. 
    *  <code>null</code> if none is available
    *
    * @see http://developer.apple.com/documentation/mac/quickdraw/QuickDraw-461.html#HEADING461-0
    */
    public final static QDOpCode getOp(int theCode) {
	switch (theCode) {
	    case QDOpCode.NoOp:        return QDNoOp.instance ;  
	    case QDOpCode.VersionOP:   return new QDVersionOP() ;
	    case QDOpCode.EOFOP:       return QDEOFOpCode.instance;
		
	    case QDOpCode.PnLocHFrac:  return new QDPnLocHFrac(); 
	    case QDOpCode.HeaderOP:    return new QDHeaderOP() ;
	    case QDOpCode.TxFontOP:    return new QDTxFontOP() ;
	    case QDOpCode.SpExtraOP:   return new QDSpExtraOp() ; 
	    case QDOpCode.DefHiliteOP: return new QDDefHiliteOP() ;
	    case QDOpCode.HiliteMode:  return new QDHiliteOP();
	    case QDOpCode.TxSizeOP:    return new QDTxSizeOP() ;
	    case QDOpCode.TxFaceOP:    return new QDTxFaceOP() ;
	    case QDOpCode.TxRatioOP:   return new QDTxRatioOP() ;
	    case QDOpCode.GlyphStateOP:   return new QDGlyphStateOP() ;
	    case QDOpCode.ShortCommentOP: return new QDShortCommentOP() ;
	    case QDOpCode.LongCommentOP:  return new QDLongCommentOP() ;
	    case QDOpCode.PenModeOP:      return new QDPnMode() ;
	    case QDOpCode.ClipRegionOP:   return new QDClipRgnOP();
	    case QDOpCode.PenSizeOP:      return new QDPnSizeOP();
	    case QDOpCode.OvalSizeOP:     return new QDOvSizeOP();
	    case QDOpCode.PenPatOP:       return new QDPatOP(QDPort.penPat);
	    case QDOpCode.FillPatOP:      return new QDPatOP(QDPort.fillPat);
	    case QDOpCode.BackPatOP:      return new QDPatOP(QDPort.backPat);
	    case QDOpCode.PnPixPat:       return new QDPixPatOp(QDPort.penPat);
	    case QDOpCode.BkPixPat:       return new QDPixPatOp(QDPort.backPat);
	    case QDOpCode.FillPixPat:       return new QDPixPatOp(QDPort.fillPat);  
	    case QDOpCode.FontNameOP:     return new QDFontNameOP();
	    case QDOpCode.SetOriginOP:    return new QDOriginOP();
	    case QDOpCode.TxModeOP:       return new QDTextModeOP() ;
	    case QDOpCode.DHDVTextOP:     return new QDDHDVTextOP();
	    case QDOpCode.DVTextOP:       return new QDDVTextOP();
	    case QDOpCode.DHTextOP:       return new QDDHTextOP();
	    case QDOpCode.LongTextOP:     return new QDLongTextOP();
	    case QDOpCode.ForeColorOP:    return new QDOldColor(QDColorOP.frontColor);
	    case QDOpCode.BackColorOP:    return new QDOldColor(QDColorOP.backColor);
	    case QDOpCode.RGBForeColorOP: return new QDColorOP(QDColorOP.frontColor);
	    case QDOpCode.RGBBackColorOP: return new QDColorOP(QDColorOP.backColor);
		
	    case QDOpCode.ShortLineOP:    return new QDShortLineOP();
	    case QDOpCode.ShortLineFromOP: return new QDShortLineFromOP();
	    case QDOpCode.LineFromOP:      return new QDLineFromOP();
	    case QDOpCode.LineOP:          return new QDLineOP();
	    case QDOpCode.FramePolyOP:     return new QDPolyOpCode(QDVerbs.frameVerb); 
	    case QDOpCode.PaintPolyOP:     return new QDPolyOpCode(QDVerbs.paintVerb); 
	    case QDOpCode.PaintRectOP:     return new QDRectOpCode(QDVerbs.paintVerb); 
	    case QDOpCode.FrameRectOP:     return new QDRectOpCode(QDVerbs.frameVerb); 
	    case QDOpCode.FillRectOP:      return new QDRectOpCode(QDVerbs.fillVerb);
	    case QDOpCode.EraseRectOP:     return new QDRectOpCode(QDVerbs.eraseVerb); 
	    case QDOpCode.InvertRectOP:     return new QDRectOpCode(QDVerbs.invertVerb);
	    case QDOpCode.PaintOvalOP:     return new QDOvalOpCode(QDVerbs.paintVerb); 
	    case QDOpCode.FrameOvalOP:     return new QDOvalOpCode(QDVerbs.frameVerb); 
	    case QDOpCode.FillOvalOP:      return new QDOvalOpCode(QDVerbs.paintVerb); 
	    case QDOpCode.PaintArcOP:      return new QDArcOpCode(QDVerbs.paintVerb); 
	    case QDOpCode.FrameArcOP:      return new QDArcOpCode(QDVerbs.frameVerb); 
	    case QDOpCode.PaintRoundRectOP: return new QDRoundRectOpCode(QDVerbs.paintVerb); 
	    case QDOpCode.FrameRoundRectOP: return new QDRoundRectOpCode(QDVerbs.frameVerb); 
	    case QDOpCode.PaintSameRectOP:  return new QDSameRectOpCode(QDVerbs.paintVerb); 
	    case QDOpCode.FrameSameRectOP:  return new QDSameRectOpCode(QDVerbs.frameVerb); 
	    case QDOpCode.PaintSameRoundRectOP: return new QDSameRoundRectOpCode(QDVerbs.paintVerb); 
	    case QDOpCode.FrameSameRoundRectOP: return new QDSameRoundRectOpCode(QDVerbs.frameVerb); 
	    case QDOpCode.PaintSameOvalOP: return new QDSameOvalOpCode(QDVerbs.paintVerb); 
	    case QDOpCode.FrameSameOvalOP: return new QDSameOvalOpCode(QDVerbs.frameVerb);
	    case QDOpCode.PaintSameArcOP:  return new QDSameArcOpCode(QDVerbs.paintVerb); 
	    case QDOpCode.FrameSameArcOP:  return new QDSameArcOpCode(QDVerbs.frameVerb);
		
	    case QDOpCode.FrameRegionOP:     return new QDRegionOP(QDVerbs.frameVerb);
	    case QDOpCode.PaintRegionOP:     return new QDRegionOP(QDVerbs.paintVerb);
	    case QDOpCode.EraseRegionOP:     return new QDRegionOP(QDVerbs.eraseVerb);
	    case QDOpCode.InvertRegionOP:     return new QDRegionOP(QDVerbs.fillVerb);   
	    case QDOpCode.FillRegionOP:     return new QDRegionOP(QDVerbs.fillVerb);   
	    case QDOpCode.BitRectOP:       return new QDBitsRectOP(); 
	    case QDOpCode.PackBitRectOP:    return new QDPackedBitsRectOP(); 
	    case QDOpCode.DirectBitsRectOP: return new QDDirectBitsRectOP(); 
	    case QDOpCode.CompressedQuickTime: return new QDCompressedQuickTime();
	} // switch
	return QDReservedOP.factory(theCode);
    } // getOp
    
    /** checks if an opcode can be discarded.
    *  The check is done by verifying if the opcode 
    *  is an instance of the <code>QDDiscard</code>
    *  interface, if this is the case the <code>discard</code>
    *  method is checked. 
    *  Here are a few examples of discarded opcodes:
    *  <ul><li>NoOps
    *  <li>Unused Comments
    *  <li>Undefined opcodes (apple reserved)
    *  <li>Unimplemented opcodes (glyph state)
    *  </ul>
    *  @param op the opcode to check
    *  @return <code>true</code> if the opcode can be discarded 
    *  @see QDDiscard#discard
    */
    
    public final static boolean discard(QDOpCode op) {
	if (op instanceof QDDiscard) return ((QDDiscard) op).discard();
	return false ;
    } // discard
    
    
    public static boolean isEOF(QDOpCode code) {
	return QDEOFOpCode.instance.equals(code); 
    }
    
    
} // QDParser
