//
//  QDBitSource.java
//  JavaQuickdraw
//
//  Created by Matthias Wiesmann on 23.07.06.
//  Copyright 2006 JAIST. All rights reserved.
//

package ch.epfl.lse.jqd.image;

import java.awt.Rectangle ; 
import java.awt.Image ; 

public interface QDBitSource {
    public abstract Rectangle getDestination() ; 
    public abstract Image getImage() ;
} // QDBitSource
