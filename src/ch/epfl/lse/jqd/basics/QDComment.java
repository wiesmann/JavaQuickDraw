//                              -*- Mode: Java -*- 
// QDComment.java --- 
// Author          : Matthias Wiesmann
// Created On      : Mon Jul 12 15:08:17 1999
// Last Modified By: Matthias Wiesmann
// Last Modified On: Tue Dec  5 17:13:16 2000
// Update Count    : 16
// Status          : OK
// 

package ch.epfl.lse.jqd.basics;

import ch.epfl.lse.jqd.opcode.QDDiscard;

/** This class represents a QuickDraw Comment.
 *  Comment are not executed has OpCodes, but serves as hints.
 *  @author Matthias Wiesmann
 *  @version 1.1
 */ 

public abstract class QDComment {

    /** begin QuickDraw Grouping */
    public final static int groupBegin = 0 ;
    /** end QuickDraw Grouping */ 
    public final static int groupEnd = 1   ;
    
    /** proprietary comment */ 
    public final static int proprietary = 100;
    
    public final static int macDrawBegin = 130 ;
    public final static int macDrawEnd = 131 ;

    /** Marks the begining of a group in the picture */
    public final static int groupedBegin = 140 ;
    /** Marks the end of a group in the picture */ 
    public final static int groupedEnd = 141 ;

    public final static int bitmapBegin = 142 ;
    public final static int bitmapEnd = 143 ;

    /** marks the beginning of a chunk of text */
    public final static int textBegin 	= 150;
    /** marks the end of a chunk of text */
    public final static int textEnd 	= 151;
    /** marks the beginning of a string of text */
    public final static int stringBegin = 152 ;
    /** marks the end of a string of text */
    public final static int stringEnd 	= 153;
    /** marks the center of the text */ 
    public final static int textCenter  = 154;
    public final static int lineLayoutOff = 155;
    public final static int lineLayoutOn = 156;
    public final static int lineLayoutClient = 157;
    /** begining of a polygon */
    public final static int polyStart 	= 160;
    /** end of a polygon */ 
    public final static int polyEnd 	= 161;
    public final static int polyCurve   = 162;
    public final static int polyIgnore  = 163;
    public final static int polySmooth  = 164;
    public final static int polyClose   = 165;
    public final static int arrow1 = 170 ;
    public final static int arrow2 = 171 ;
    public final static int arrow3 = 172 ;
    public final static int arrowEnd = 173;
    public final static int rotateBegin = 200;
    public final static int rotateEnd   = 201;
    public final static int creator = 498 ;
    /** marks the begining of a section that should be 
     *  ignored if postscript data is used 
     */ 
    public final static int postscriptStart = 190 ;
    /** marks the end of a section that should be 
     *  ignored if postscript data is used
     */ 
    public final static int postscriptEnd = 191 ; 
    /** reference to a postscript handle in memory
     *  @deprecated This comment type has been deprecated by apple
     *  Should only appear in spool files
     */ 
    public final static int postscriptHandle = 192 ; 
    /** reference to a postscript file 
     *   @deprecated This comment type has been deprecated by apple
     *  Should only appear in spool files
     */ 
    public final static int postscriptFile = 193 ; 

    public final static int testIsPostscript = 194 ; 


    /** the kind of the comment */
    protected int kind ;
    
    /** Gets the kind of the comment 
     *  @return the kind 
     */ 
    public int getKind() {
	return kind;
    } // getKind

    /** Gets the text version of the comment 
     * @param type the comment code 
     * @return the name of the comment
     */ 

    public static String toString(int type) {
	switch(type) {
        case creator:                   return("Picture Creator");
        case groupedBegin:
	case groupBegin:                return("Begin Group");
        case groupedEnd:
	case groupEnd:                  return("End Group");
        case bitmapBegin:               return("Bitmap begin");
	case bitmapEnd:                 return("Bitmap end");
	case arrow1:                    return("Arrow at end");
        case arrow2:                    return("Arrow at start");
	case arrow3:                    return("Arrow at both");
        case arrowEnd:                  return("End of arrow");
	case proprietary:               return("Proprietary");
	case textBegin:                 return("Text Begin");
	case textEnd: 	                return("Text End");
	case stringBegin: 	        return("String Begin");
	case stringEnd: 	        return("String End");
	case textCenter:	        return("Text Center");
	case lineLayoutOff: 	        return("Line Layout Off");
	case lineLayoutOn: 	        return("Line Layout On");
	case lineLayoutClient: 	        return("Client Line Layout");
	case polyStart: 	        return("Poly Start");
	case polyEnd: 		        return("Poly End");
	case polyIgnore: 	       	return("Poly Ignore");
	case polySmooth: 		return("Poly Smooth");
	case polyClose: 		return("Poly Close");
	case rotateBegin: 		return("Rotate Begin");
	case rotateEnd: 		return("Rotate End");
	case 180: 			return("Dashed Line");
	case 181: 			return("Dashed End");
	case 182: 			return("Set Line Width");
	case postscriptStart: 		return("PostScript Begin");
	case postscriptEnd: 		return("PostScript End");
	case postscriptHandle: 		return("PostScript Handle");
	case postscriptFile: 		return("PostScript File");
	case testIsPostscript: 		return("Text is PostScript");
	case 195: 			return("Resource PS");
	case 196: 			return("PS Begin No Save");
	case 197: 			return("Set Gray");
	case 210: 			return("Forms Printing");
	case 211: 			return("End Forms Printing");
	case 220: 			return("CM Begin Profile");
	case 221: 			return("CM End Profile");
	case 222: 			return("CM Enable Color Matching");
	case 223: 			return("CM Disable Color Matching");
	} // switch
	return(Integer.toString(type));
    } // toString
   
} // QDComment


