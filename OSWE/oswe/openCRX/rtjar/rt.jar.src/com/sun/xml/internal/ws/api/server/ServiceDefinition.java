package com.sun.xml.internal.ws.api.server;

import com.sun.istack.internal.NotNull;

public interface ServiceDefinition extends Iterable<SDDocument> {
  @NotNull
  SDDocument getPrimary();
  
  void addFilter(@NotNull SDDocumentFilter paramSDDocumentFilter);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/ServiceDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */