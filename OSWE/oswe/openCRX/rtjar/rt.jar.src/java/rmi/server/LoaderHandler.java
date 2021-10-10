package java.rmi.server;

import java.net.MalformedURLException;
import java.net.URL;

@Deprecated
public interface LoaderHandler {
  public static final String packagePrefix = "sun.rmi.server";
  
  @Deprecated
  Class<?> loadClass(String paramString) throws MalformedURLException, ClassNotFoundException;
  
  @Deprecated
  Class<?> loadClass(URL paramURL, String paramString) throws MalformedURLException, ClassNotFoundException;
  
  @Deprecated
  Object getSecurityContext(ClassLoader paramClassLoader);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/server/LoaderHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */