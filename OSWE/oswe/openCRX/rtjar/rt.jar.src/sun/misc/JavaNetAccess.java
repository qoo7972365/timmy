package sun.misc;

import java.net.InetAddress;
import java.net.URLClassLoader;

public interface JavaNetAccess {
  URLClassPath getURLClassPath(URLClassLoader paramURLClassLoader);
  
  String getOriginalHostName(InetAddress paramInetAddress);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/JavaNetAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */