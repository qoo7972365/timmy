package java.rmi.server;

import java.io.IOException;
import java.net.Socket;

public interface RMIClientSocketFactory {
  Socket createSocket(String paramString, int paramInt) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/server/RMIClientSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */