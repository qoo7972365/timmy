package com.sun.istack.internal.localization;

public interface Localizable {
  public static final String NOT_LOCALIZABLE = "\000";
  
  String getKey();
  
  Object[] getArguments();
  
  String getResourceBundleName();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/istack/internal/localization/Localizable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */