package javax.xml.ws.handler;

import java.util.List;

public interface HandlerResolver {
  List<Handler> getHandlerChain(PortInfo paramPortInfo);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/ws/handler/HandlerResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */