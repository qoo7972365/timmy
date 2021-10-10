package com.sun.xml.internal.ws.api.message;

import com.sun.istack.internal.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataHandler;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;

public interface Attachment {
  @NotNull
  String getContentId();
  
  String getContentType();
  
  byte[] asByteArray();
  
  DataHandler asDataHandler();
  
  Source asSource();
  
  InputStream asInputStream();
  
  void writeTo(OutputStream paramOutputStream) throws IOException;
  
  void writeTo(SOAPMessage paramSOAPMessage) throws SOAPException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/Attachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */