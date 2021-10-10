package com.oracle.webservices.internal.api.databinding;

import java.lang.reflect.Method;

public interface JavaCallInfo {
  Method getMethod();
  
  Object[] getParameters();
  
  Object getReturnValue();
  
  void setReturnValue(Object paramObject);
  
  Throwable getException();
  
  void setException(Throwable paramThrowable);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/webservices/internal/api/databinding/JavaCallInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */