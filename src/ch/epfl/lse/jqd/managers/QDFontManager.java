package ch.epfl.lse.jqd.managers;

import ch.epfl.lse.jqd.basics.QDException;
import java.awt.Font;
import java.util.Hashtable;

/** This class represents the QuickDraw Font Manager
 *  It offers services to translate QuickDraw fonts 
 *  into their AWT equivalent.
 *  @author Matthias Wiesmann
 *  @version 1.2 revised
 *  @see java.awt.Font
 */

public class QDFontManager  {
    
    public static short GenevaID = 3 ; 
    public static short MonacoID = 4 ; 
    public static short VeniceID = 5 ; 
    public static short LondonID = 6 ; 
    public static short AthensID = 7 ; 
    public static short SanFranciscoID = 8 ; 
    public static short TorontoID = 9 ; 
    public static short CairoID = 11 ; 
    public static short LosAngelesID = 12 ; 
    public static short TimesID = 20 ; 
    public static short HelveticaID = 21 ;
    public static short CourierID = 22 ; 
    public static short SymbolID = 23 ; 
    public static short MobileID = 24 ; 
	
    /** This table contains the different fonts */ 
    protected final Hashtable fonts  ;
	
    /** Constructor, initializes the font table */
    public QDFontManager() {
	fonts = new Hashtable() ;
	addFont("Helvetica",HelveticaID);
	addFont("Courier",CourierID) ;
	addFont("Times",TimesID) ; 
    } // QDFontManager
    
    /** links a font name with a given font id
     * @param name the name of the font to insert
     * @number the id the font is linked to 
     */ 
    public void addFont(String name, int number) {
	Integer index= new Integer(number);
	if (fonts.containsKey(index)) return ;
	fonts.put(index,name);
    } // addFont
    
    /** returns the name of a font for an id 
     * @param number the id of the font
     * @return the name of the font 
     */ 
		
    public String getName(int number) throws QDException {
	Integer index= new Integer(number);
	String name = (String) fonts.get(index) ;
	if (null==name) throw new QDUnknownFont(number);
	return name ;
    } // getFont
    
    /** builds a font 
     * @param number the QuickDraw id of the font
     * @param style the AWT style of the font
     * @param size the point size of the font
     * @return the font, <code>helvetica</code> if the font is not found
     */
		
    public Font getFont(int number, int style, int size) throws QDException {
	String name ;
	try {
	    name =getName(number);
	} catch (QDUnknownFont e) { 
	    name="Helvetica" ; }
	final Font result= new Font(name,style,size) ;
	// System.out.println(number +"->"+name+"->"+result);
	return(result);
    } // getFont 
    
    /** Text information about the font manager
     * @return information string
     */ 
    public String toString() {
	return("Font Manager "+fonts);
    } // toString
    
} // QDFontManager
	
	

