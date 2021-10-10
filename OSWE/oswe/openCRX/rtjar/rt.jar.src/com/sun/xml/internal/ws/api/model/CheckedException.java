package com.sun.xml.internal.ws.api.model;

import com.sun.xml.internal.bind.api.Bridge;

public interface CheckedException {
  SEIModel getOwner();
  
  JavaMethod getParent();
  
  Class getExceptionClass();
  
  Class getDetailBean();
  
  Bridge getBridge();
  
  ExceptionType getExceptionType();
  
  String getMessageName();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/model/CheckedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */