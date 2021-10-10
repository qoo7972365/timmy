package org.omg.CORBA.portable;

import org.omg.CORBA.SystemException;

public interface InvokeHandler {
  OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) throws SystemException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/portable/InvokeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */