package com.sun.org.apache.xerces.internal.xni.parser;

import com.sun.org.apache.xerces.internal.util.FeatureState;
import com.sun.org.apache.xerces.internal.util.PropertyState;

public interface XMLComponentManager {
  boolean getFeature(String paramString) throws XMLConfigurationException;
  
  boolean getFeature(String paramString, boolean paramBoolean);
  
  Object getProperty(String paramString) throws XMLConfigurationException;
  
  Object getProperty(String paramString, Object paramObject);
  
  FeatureState getFeatureState(String paramString);
  
  PropertyState getPropertyState(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xni/parser/XMLComponentManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */