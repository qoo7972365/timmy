package com.sun.xml.internal.messaging.saaj.packaging.mime;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeBodyPart;
import javax.activation.DataSource;

public interface MultipartDataSource extends DataSource {
  int getCount();
  
  MimeBodyPart getBodyPart(int paramInt) throws MessagingException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/packaging/mime/MultipartDataSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */