package com.sun.xml.internal.ws.transport.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public interface ResourceLoader {
  URL getResource(String paramString) throws MalformedURLException;
  
  URL getCatalogFile() throws MalformedURLException;
  
  Set<String> getResourcePaths(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/ResourceLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */