package com.sun.xml.internal.ws.api.message;

import com.sun.istack.internal.Nullable;

public interface AttachmentSet extends Iterable<Attachment> {
  @Nullable
  Attachment get(String paramString);
  
  boolean isEmpty();
  
  void add(Attachment paramAttachment);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/AttachmentSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */