//
//  EOFOpCode.java
//  JavaQuickdraw
//
//  Created by Matthias Wiesmann on 23.07.06.
//  Copyright 2006 JAIST. All rights reserved.
//

package ch.epfl.lse.jqd.opcode;

public class QDEOFOpCode extends QDNoOp {

    protected final static String NAME = "End of File" ; 
    public final static QDEOFOpCode instance = new QDEOFOpCode() ; 

    public String toString() { return NAME ; }
    
} // QDNoOp
