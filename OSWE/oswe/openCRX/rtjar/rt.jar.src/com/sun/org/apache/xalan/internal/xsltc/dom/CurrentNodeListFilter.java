package com.sun.org.apache.xalan.internal.xsltc.dom;

import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;

public interface CurrentNodeListFilter {
  boolean test(int paramInt1, int paramInt2, int paramInt3, int paramInt4, AbstractTranslet paramAbstractTranslet, DTMAxisIterator paramDTMAxisIterator);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/dom/CurrentNodeListFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */