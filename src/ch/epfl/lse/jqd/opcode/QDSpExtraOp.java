//
//  QDSpExtraOp.java
//  JavaQuickdraw
//
//  Created by Matthias Wiesmann on 19.07.06.
//  Copyright 2006 JAIST. All rights reserved.
//

package ch.epfl.lse.jqd.opcode;

import ch.epfl.lse.jqd.basics.*;
import ch.epfl.lse.jqd.utils.*;

import java.io.IOException ; 
import ch.epfl.lse.jqd.io.QDInputStream;


public class QDSpExtraOp implements QDOpCode {
    
    protected double space_extra ; 
    
    public int read(QDInputStream stream) throws IOException {
	space_extra = stream.readFixed() ; 
	return stream.FIXED_SIZE ; 
    } // read
    
    public void	execute(QDPort thePort) {
	thePort.spExtra = space_extra ; 
	thePort.txOperation();
    } // execute 
    
}
