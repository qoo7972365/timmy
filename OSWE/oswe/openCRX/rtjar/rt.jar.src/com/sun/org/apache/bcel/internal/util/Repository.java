package com.sun.org.apache.bcel.internal.util;

import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import java.io.Serializable;

public interface Repository extends Serializable {
  void storeClass(JavaClass paramJavaClass);
  
  void removeClass(JavaClass paramJavaClass);
  
  JavaClass findClass(String paramString);
  
  JavaClass loadClass(String paramString) throws ClassNotFoundException;
  
  JavaClass loadClass(Class paramClass) throws ClassNotFoundException;
  
  void clear();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/util/Repository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */