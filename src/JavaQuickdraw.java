//
//  JavaQuickdraw.java
//  JavaQuickdraw
//
//  Created by Matthias Wiesmann on 19.07.06.
//  Copyright (c) 2006 JAIST. All rights reserved.
//
//  A simple signed Java applet
//

import java.awt.*;
import java.applet.*;
import javax.swing.*;

public class JavaQuickdraw extends JApplet {
	
    static final String message = "Hello World!";
    private Font font = new Font("serif", Font.ITALIC + Font.BOLD, 36);
	
    public void init() {
        // set the default look and feel
        String laf = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException exc) {
            System.err.println ("Warning: UnsupportedLookAndFeel: " + laf);
        } catch (Exception exc) {
            System.err.println ("Error loading " + laf + ": " + exc);
        }
		getContentPane().setLayout (null);
    }
	
    public void paint (Graphics g) {
        super.paint(g);
        g.setColor(Color.blue);
        g.setFont(font);
        g.drawString(message, 40, 80);
    }
}
