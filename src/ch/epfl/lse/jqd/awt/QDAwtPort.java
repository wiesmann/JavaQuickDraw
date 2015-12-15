//                              -*- Mode: Java -*- 
// QDAwtPort.java --- 
// Author          : Matthias Wiesmann
// Created On      : Tue Oct  5 12:04:11 1999
// Last Modified By: Matthias Wiesmann
// Last Modified On: Wed Dec  6 09:33:40 2000
// Update Count    : 30
// Status          : Renamed
// 

package ch.epfl.lse.jqd.awt;

import ch.epfl.lse.jqd.basics.*;
import java.awt.*;
import java.awt.image.*;
import ch.epfl.lse.jqd.opcode.*;
import ch.epfl.lse.jqd.utils.*;
import ch.epfl.lse.jqd.managers.*;
import ch.epfl.lse.jqd.image.*;

/** This class represents an concrete instanciation of the QDPort class
 *  All drawing operations are routed into an AWT Graphics object.
 *  Some basic optimisations are in place, mostly to avoid recalculing 
 *  colors and fonts at each operation. 
 *  @author Matthias Wiesmann
 *  @version 2.0 
 */ 

public class QDAwtPort extends QDPort {

    /** the zoom manager */
    protected QDZoomer zoom = null ;

    
    /** Is color changed since last operation?
     * Must the foreground color be recalculated?
     * @see #stdColor
     */
    protected boolean colorDirty ; 
    /** Are font Attributes changed ? 
     *  If not, the font attributed need not be recalculated
     * @see #stdText
     */ 
    protected boolean txDirty ;
    /** Is drawing blocked ?*/
    protected boolean canDraw = true ;
    /** The graphics object drawing operations will be performed in */
    protected Graphics destGraphics ;
    /** The component used for auxilliary calls (getImage etc...)*/
    protected Component destComponent ;
   

      /** builds a Port from a component
     * @param c the component to use for AWT Resources
     */
    public QDAwtPort(Component c) {
	this.destComponent = c ;
	zoom = new QDZoomer();
	fontMgr = new QDFontManager();
	linkMgr = new QDLinkManager();
	txtNormal();
	penNormal();
    } // QDPort

    public QDZoomer getZoomManager() {
	return zoom;
    } // getZoomManager
        
    /** This method is called when the picture origin must be changed 
	*	@param d the new origin of the port 
	*/
    
    public void setOrigin(Dimension d) {
	zoom.translate(d);
    } // setOrigin
    
    
     /** sets the Rectangle of the Port,
     * the zoomer is adapted to it
     * @param r the port rectangle
     */ 
    public void setPortRect(Rectangle r) {
	portRect = destComponent.getBounds();
	zoom = new QDZoomer(portRect,r);
	setPortGraphics(destComponent.getGraphics()) ;
    } // setPortRect
	      
    /** sets the graphics for drawing operations
     * @param g the destination graphics
     */ 
    public void setPortGraphics(Graphics g) {
	destGraphics = g ;
    }  // setPortGraphics

    /** Marks a text operation 
     *  used to set text optimisation flag
     *  @see #txDirty  
     */ 

    public void txOperation() {
	txDirty = true ;
    } // txOperation

    /** Marks a color operation
     *  used to set color optimisation flag
     *  @see #colorDirty
     */ 

    public void colorOperation() {
	colorDirty = true ;
    } // colorOperation

    /** Clipping is not implemented as such
     *  Basically, this method only sets a flag if
     *  the clipping region is used to prevent any 
     *  form of drawing
     */ 

    public void clip(QDRegion region) {
	clipRect = region.getBounds();
	canDraw = clipRect.intersects(portRect);
    } // clipRect

    public void penNormal() {
	super.penNormal();
	canDraw= true; 
    } // penNormal

    /** This method calculates the foreground and background colors
     *  depending of the quickdraw verb
     *  This is tricky because quickdraw manages more than 5 concurent colors
     *  and AWT only supports 2 (foreground and background). 
     *  @param verb the quickdraw verb used in the next drawing operation
     *  Highlight and inversion don't work very well. 
     */ 

    public void	stdColor(int verb) throws QDException{
	if (colorDirty==false) return ;
	switch (verb) {
	case QDVerbs.frameVerb:
	case QDVerbs.paintVerb:
	    if (QDModes.isXor(pnMode)) destGraphics.setXORMode(Color.black);
	    else destGraphics.setPaintMode();
	    destGraphics.setColor(patterns[penPat].patColor(fgColor,bgColor));
	    break ;
	case QDVerbs.fillVerb:	
	    destGraphics.setPaintMode();
	    destGraphics.setColor(patterns[fillPat].patColor(fgColor,bgColor));
	    break ;
	case QDVerbs.invertVerb:
	    destGraphics.setXORMode(Color.black);
	case QDVerbs.txtVerb:
	    destGraphics.setColor(fgColor);
	    break ;
	case QDVerbs.highVerb:
	    destGraphics.setColor(hlColor);
	    destGraphics.setXORMode(hlColor);
	    break; 
	} // switch
    } // stdColor

    public void	stdLine(Point newPt) throws QDException{
	if (canDraw== false) return ;
	if (pnLoc.equals(newPt)== true) return ;
	final Point a = zoom.transform(pnLoc) ;
	final Point b = zoom.transform(newPt) ; 
	final Dimension ps = zoom.transform(pnSize);
	stdColor(QDVerbs.frameVerb);
	QDGraphUtils.drawLine(destGraphics,a,b,ps);
	stdPoint(newPt);
    } // stdLine
    
    public void	stdRect(int verb, Rectangle r) throws QDException {
	// if (canDraw== false) return ;
	final Rectangle rect = zoom.transform(r.intersection(clipRect));
	stdColor(verb);
	switch (verb) {
	case QDVerbs.frameVerb: 	
	    final Dimension ps = zoom.transform(pnSize);
	    QDGraphUtils.frameRect(destGraphics,rect,ps); 
	    break;
	case QDVerbs.paintVerb:	
	case QDVerbs.invertVerb:
	case QDVerbs.fillVerb:
	    destGraphics.fillRect(rect.x,rect.y,rect.width,rect.height); 
	    break;
	case QDVerbs.eraseVerb:
	    destGraphics.clearRect(rect.x,rect.y,rect.width,rect.height); 
	    break;
	} // case
	lastRect= r;
    } // stdRect

    /** Bottleneck function to draw polygons 
      * @param verb the action to do with the polygon
      * @param Polygon the polygon to handle 
      */ 
    
    public void stdPoly(int verb, Polygon p) throws QDException{
	if (canDraw== false) return ;
	stdColor(verb);
	final Polygon poly = zoom.transform(p);
	switch (verb)  {
	case QDVerbs.frameVerb: 	
	    final Dimension ps = zoom.transform(pnSize);
	    QDGraphUtils.framePoly(destGraphics,poly,ps);
	    break ;
	case QDVerbs.paintVerb:	
	    destGraphics.fillPolygon(poly) ; 
	    break ;				
	} // break
    } // stdPoly
    
    /** Control is dispateched to the QDRegion object */

    public void stdRgn(int verb, QDRegion rgn) throws QDException{
	if (rgn.isRectangle()) stdRect(verb,rgn.getBounds());
	else {
	    switch(verb) {
	    case QDVerbs.frameVerb: rgn.frame(this); break;	
	    case QDVerbs.paintVerb: rgn.paint(this); break;
	    } // for
	} // else
    } // stdRgn
    
    /** Control is dispateched to graph utils */

     public void stdOval(int verb, Rectangle r) throws QDException {
	if (canDraw== false) return ;
	stdColor(verb);
	Rectangle rect = zoom.transform(r);
	switch (verb)  {
	case QDVerbs.frameVerb : 	
	    Dimension ps = zoom.transform(pnSize);
	    QDGraphUtils.frameOval(destGraphics,rect,ps); 
	    break ;
	case QDVerbs.paintVerb :
	case QDVerbs.fillVerb :	
	    destGraphics.fillOval(rect.x,rect.y,rect.width,rect.height) ; 
	    break ;	
	} // switch
	lastRect= r ;	
    } // stdOval

    /** Control is dispatched to GraphUtils */

    public void stdRRect(int verb, Rectangle r) throws QDException {
	if (canDraw== false) return ;
	stdColor(verb);
	final Rectangle rect = zoom.transform(r);
	switch (verb)  {
	case QDVerbs.frameVerb: 	
	    Dimension ps = zoom.transform(pnSize);
	    QDGraphUtils.frameRRect(destGraphics,rect,ovalSize,ps); 
	    break ;
	case QDVerbs.paintVerb:	
	    destGraphics.fillRoundRect(rect.x,rect.y,rect.width,
				       rect.height,ovalSize.width,
				       ovalSize.height); 
	    break ;	
	} // switch
	lastRect= r ;	
    } // stdRRect

    /** Standart arc drawing method 
     *  Control is dispateched to GraphUtils.
     *  Filling arcs should be OK, framing with a width != 1 is tricky. 
     *  @exception QDException color control error or graphic conversion error
     */

    public void stdArc(int verb, Rectangle r,int startAngle, int angle) throws QDException{
	if (canDraw== false) return ;
	stdColor(verb);
	final Rectangle rect = zoom.transform(r);	
	switch (verb) {
	case QDVerbs.frameVerb: 	
	    final Dimension ps = zoom.transform(pnSize);
	    QDGraphUtils.frameArc(destGraphics,
				  rect,ps,
				  startAngle,angle);
	    break ;
	case QDVerbs.paintVerb:	
	    destGraphics.fillArc(rect.x,rect.y,
				 rect.width,rect.height,
				 startAngle,angle) ;
	    break ;
	} // switch
	lastRect= r ;	
    } // stdRRect
    
    /** Text drawing method
     *  Fonts are simply scaled and fit into the available space
     *  Justification is not implemented for the moment
     *  @exception QDException transform or conversion problem
     */ 

    public void stdText(String text) throws QDException {
	if (canDraw== false) return ;
	stdColor(QDVerbs.txtVerb);
	final Point p = zoom.transform(txLoc);
	if (txDirty) {
	    final int size = zoom.transformTextSize(txSize) ;
	    final Font f = fontMgr.getFont(txFont,txFace, size);
	    destGraphics.setFont(f);
	    txDirty = false;
	} // if Dirty
	final Rectangle rect = QDGraphUtils.textRect(destGraphics,p,text);
	linkMgr.addText(text,rect);
	destGraphics.drawString(text,p.x,p.y);
    } // stdText

    public void stdBits(QDBitSource bitSource) throws QDException {
	if (canDraw== false) return ;
	final Rectangle dest = zoom.transform(bitSource.getDestination());
	try {
	    final Image i = bitSource.getImage() ; 
	    final MediaTracker t = new MediaTracker(destComponent);
	    t.addImage(i,0);
	    t.waitForAll();
	    destGraphics.drawImage(i,dest.x,dest.y,dest.width,dest.height,null);
	} catch (Exception e) {
	    e.printStackTrace();
	    System.err.println(bitSource.toString());
	    // String err_string = e.getMessage();
	    // throw  new QDBitsError(err_string);
	} // catch
    } // stdBits

    /** Comment handling method.
     * The following comments are currently handled/used:
     * <dl compact>
     * <dt>polyStart
     * <dt>polyEnd
     * <dd>polygon drawing acceleration
     * <dt>TextBegin
     * <dt>TextEnd 
     * <dd>text link concatenation
     * </dl>
     */

      public void stdComment(QDComment comment) 
	throws QDException {
	switch (comment.getKind()){
	case QDComment.polyStart: 	
	    stdColor(QDVerbs.frameVerb);
	    colorDirty= false;
	    break ;
	case QDComment.polyEnd:	
	    colorDirty= true;
	    break ;	
	case QDComment.textBegin:
	    linkMgr.setConcat( true);
	    break;
	case QDComment.textEnd:
	    linkMgr.setConcat( false);
	    break;
	} // switch 
    } // stdComment

} // QDAwtPort

/** BitMap related problem
 * @author Matthias Wiesmann
 * @version 1.0
 */

class QDBitsError extends QDException {
    public QDBitsError(String s) {
	super(s);
    } // QDBitsError
    public String toString() {
	return("stdBits Error");
    } // toString
} // QDBitsError
