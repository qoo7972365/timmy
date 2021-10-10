package com.sun.xml.internal.ws.api.server;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.api.pipe.Codec;

public interface EndpointAwareCodec extends Codec {
  void setEndpoint(@NotNull WSEndpoint paramWSEndpoint);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/EndpointAwareCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */