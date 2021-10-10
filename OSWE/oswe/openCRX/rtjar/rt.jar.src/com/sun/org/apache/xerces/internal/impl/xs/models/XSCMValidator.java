package com.sun.org.apache.xerces.internal.impl.xs.models;

import com.sun.org.apache.xerces.internal.impl.xs.SubstitutionGroupHandler;
import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaException;
import com.sun.org.apache.xerces.internal.xni.QName;
import java.util.ArrayList;
import java.util.Vector;

public interface XSCMValidator {
  public static final short FIRST_ERROR = -1;
  
  public static final short SUBSEQUENT_ERROR = -2;
  
  int[] startContentModel();
  
  Object oneTransition(QName paramQName, int[] paramArrayOfint, SubstitutionGroupHandler paramSubstitutionGroupHandler);
  
  boolean endContentModel(int[] paramArrayOfint);
  
  boolean checkUniqueParticleAttribution(SubstitutionGroupHandler paramSubstitutionGroupHandler) throws XMLSchemaException;
  
  Vector whatCanGoHere(int[] paramArrayOfint);
  
  ArrayList checkMinMaxBounds();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/models/XSCMValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */