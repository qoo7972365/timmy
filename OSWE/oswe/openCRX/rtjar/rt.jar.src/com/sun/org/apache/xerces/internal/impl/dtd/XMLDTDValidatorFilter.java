package com.sun.org.apache.xerces.internal.impl.dtd;

import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentFilter;

public interface XMLDTDValidatorFilter extends XMLDocumentFilter {
  boolean hasGrammar();
  
  boolean validate();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/dtd/XMLDTDValidatorFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */