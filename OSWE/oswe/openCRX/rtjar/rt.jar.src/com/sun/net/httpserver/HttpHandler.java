package com.sun.net.httpserver;

import java.io.IOException;
import jdk.Exported;

@Exported
public interface HttpHandler {
  void handle(HttpExchange paramHttpExchange) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/httpserver/HttpHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */