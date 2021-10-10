package com.sun.net.httpserver;

import javax.net.ssl.SSLSession;
import jdk.Exported;

@Exported
public abstract class HttpsExchange extends HttpExchange {
  public abstract SSLSession getSSLSession();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/httpserver/HttpsExchange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */