package ch.epfl.lse.jqd.awt;

import ch.epfl.lse.jqd.basics.QDPicture;

import java.applet.Applet;
import java.applet.AppletContext;
import java.net.URL;
import java.io.InputStream;

import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Properties;

/** This applet displays a Macintosh Picture file 
 * This version support the Java 1.1 event model. 
 * @author Matthias Wiesmann
 * @version 1.1
 */ 


public class QDApplet extends Applet
    implements MouseListener
{
    /** Name of param containing the relative path of the pict file */
    protected final static String PICT_NAME = "PictFile" ;
    /** Name of param containing the relative path of the link file */ 
    protected final static String LINK_NAME = "LinkFile" ; 

    /** the canvas, where the actual drawing occurs */
    protected QDCanvas canvas ; 
    /** the QuickDraw Picture to display */
    protected QDPicture pict ;
    /** the list of links */
    protected Properties links ; 

    protected final static String LOAD_TEXT = "loading: " ;
    protected final static String DONE_TEXT = "done" ;

    /** Loads the links
     *  the link file is specified by the 
     *  <code>LinkFile</code> property
     */

    protected void loadLinks()
	throws Exception {
	    links = new Properties();
	    final String link_name = getParameter(LINK_NAME);
	    if (link_name== null) return ;
	    final URL base = getDocumentBase();
	    final URL link_url = new URL(base,link_name);
	    InputStream stream = link_url.openStream();
	    showStatus(LOAD_TEXT+link_name);
	    links.load(stream);
	    showStatus(DONE_TEXT);
	} // loadLinks
    
    
    protected URL pictUrl() throws Exception {
	final String pict_name = getParameter(PICT_NAME);  
	if (pict_name==null) { throw new Exception(PICT_NAME + "is null"); }
	if (pict_name.startsWith("http://")) {
	    return new URL(pict_name); 
	} else {
	    final URL base = getDocumentBase();
	    return new URL(base,pict_name); 
	} 
    } // pictUrl
    
    /** Loads the picture 
     *  the picture file is specified by the 
     *  <code>PictFile</code> property
     */ 

    protected void loadPicture() throws Exception {
	final URL pict_url = pictUrl(); 
	showStatus(LOAD_TEXT+pict_url);
	pict=new QDPicture(pict_url);
	showStatus(DONE_TEXT);
    } // loadPicture

    /** Loads the data of the Applet 
     *  @see #loadPicture
     *  @see #loadLinks
     */ 

    public void load()
	throws Exception {
	   loadPicture();
	loadLinks();
	} // load

    /** jumps the browser to the link 
     *  @param link the link to jump to
     */

    protected void playLink(String link) {
	if (link== null) return ;
	try {
	    final URL url = new URL(link);
	    final AppletContext context = getAppletContext();
	    context.showDocument(url);
	} catch(Exception e) {
	    showStatus(e.toString()); 
	} // catch 
    } // playLink 

    /** checks if a link is know for the given text
     * @param text the text to check 
     */ 

    protected void checkLink(String text) {
	    final String link = links.getProperty(text);
	    showStatus(text+"->"+link);
	    if (link!=null) playLink(link);
	} // checkLink

    /** checks if the click occurs on one link
     *  @see #checkLink
     *  @param e the mouse event
     */ 

    public void mouseClicked(MouseEvent e) {
	    final Point p = e.getPoint();
	    final String text = canvas.getText(p);
	    if (text== null) return ;
	    checkLink(text);
	} // mouseClicked

    public void mouseEntered(MouseEvent e) {}
    
    public void mouseExited(MouseEvent e) {} 
    
    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {} 

    public void init() {
	try {
	    load();
	    canvas = new QDCanvas(pict);
	    canvas.setBounds(this.getBounds());
	    canvas.addMouseListener(this);
	    add(canvas);
	} catch (Exception e) {
	    e.printStackTrace();
	} // try / catch 
    } // 
} // QDApplet
