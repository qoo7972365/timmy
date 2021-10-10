package com.sun.xml.internal.ws.server;

import com.sun.xml.internal.ws.api.pipe.Tube;
import com.sun.xml.internal.ws.api.server.WSEndpoint;

public interface EndpointAwareTube extends Tube {
  void setEndpoint(WSEndpoint<?> paramWSEndpoint);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/EndpointAwareTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */