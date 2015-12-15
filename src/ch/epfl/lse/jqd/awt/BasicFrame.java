//                              -*- Mode: Java -*- 
// BasicFrame.java --- 
// Author          : Matthias Wiesmann
// Created On      : Fri Sep 25 15:17:45 1998
// Last Modified By: Matthias Wiesmann
// Last Modified On: Tue Nov 21 15:18:57 2000
// Update Count    : 31
// Status          : Working
// 

package ch.epfl.lse.jqd.awt;

import java.awt.Frame; 
import java.awt.MenuBar; 
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;

import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.Graphics;

import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** This frame is a minimal implementation of a window.
 *  It  builds a simple frame, with a default menu. 
 * The item are the following:
 * <dl compact>
 * <dt>print
 * <dd>builds a print job and calls the 
 *     <code>print</code> with the right graphics
 * <dt>quit
 * <dd>calls <code>System.exit(0)</code>
 * </dl>
 * Both method <code>doPrint</code> and <code>doQuit</code>
 * can be overidden to obtain more advanced behaviour. 
 * 
 * @author Matthias Wiesmann
 * @version 1.1
 */

public abstract class BasicFrame extends Frame implements ActionListener
{
   

    /** File menu label */
    protected static String FILE_MENU = "file" ;
    /** Open Item */
    protected static String OPEN_ITEM = "open" ;
    /** Print item */
    protected static String PRINT_ITEM = "print" ;
    /** Quit item */
    protected static String QUIT_ITEM = "quit" ;
    /** Quit keyboard Shortcut */
    protected final static MenuShortcut quit_shortcut = 
    new MenuShortcut('q');
    /** Print keyboard Shortcut */
    protected final static MenuShortcut print_shortcut = 
    new MenuShortcut('p');

    /** Constructor 
     * Sets up the frame and inserts a basic menu and the appropriate 
     * event handlers.
     * @param title the title of the frame
     */
    public BasicFrame(String title) 
	{ 
	    super(title) ; 
	    setMenuBar(buildMenus());
	    // enabling window events 
	    enableEvents(WindowEvent.WINDOW_CLOSING);
	} // BasicFrame

    /** Builds the menu bar 
     * @return the menu bar
     */ 
    protected MenuBar buildMenus()
	{
	    // Setting up the quit item
	    MenuItem quit_item = new MenuItem(QUIT_ITEM);
	    quit_item.addActionListener(this); 
	    quit_item.setShortcut(quit_shortcut);
	    quit_item.setEnabled( true);
	    // Setting up the print item
	    MenuItem print_item = new MenuItem(PRINT_ITEM);
	    print_item.addActionListener(this);
	    print_item.setShortcut(print_shortcut);
	    print_item.setEnabled( true);
	    // Setting up the file menu
	    Menu file_menu = new Menu(FILE_MENU);
	    // adding the items
	    file_menu.add(print_item);
	    file_menu.addSeparator();
	    file_menu.add(quit_item);
	    // attaching it to the menubar
	    MenuBar menu_bar = new MenuBar();
	    menu_bar.add(file_menu);
	    return menu_bar ;
	} // buildMenus


    /** Processses windows events, 
     * only closing events are handled and dispateched on
     * the <code>doQuit</code> method
     * @param e the event 
     */
    public void processWindowEvent(WindowEvent e)
	{
	    super.processWindowEvent(e);
	    int id = e.getID();
	    switch (id)
		{
		case WindowEvent.WINDOW_CLOSING : 
		    dispose();
		    doQuit();
		    break;
		} // switch 
	}//processWindowEvent

    /** This method is called when the <code>quit</code> item 
     *  is selected. <br>
     *  This version simply quits the java virtual machine
     */ 

    public void doQuit()
	{
	    System.exit(0);
	} // doQuit

    /** This method is called when the <code>print</code> item
     *  is selected. <br>
     *  This version builds a printJob and prints the component in i.
     */
    
    public void doPrint()
	{
	    Toolkit toolkit = Toolkit.getDefaultToolkit();
	    PrintJob job = toolkit.getPrintJob(this,getTitle(),null);
	    if (job== null) return ;
	    Graphics graph = job.getGraphics();
	    print(graph);
	    graph.dispose();
	    job.end();
	} // doPrint

    /** Tracks actions, more precisely menu events.
     * The quit menu event is dispateched to the <code>doQuit</code>
     * method
     * @param evt the event
     */ 

    public void actionPerformed(ActionEvent evt)
	{
	    final String action = evt.getActionCommand();
	    if (action == null) return ;
	    if (action.equals(QUIT_ITEM)) doQuit(); 
	    if (action.equals(PRINT_ITEM)) doPrint();
	} // actionPerformed
    
    public String toString()
	{
	    return ("Basic Java Frame"+super.toString());
	} //toString

} // BasicFrame
