package com.sun.corba.se.spi.legacy.connection;

import com.sun.corba.se.spi.ior.IOR;
import com.sun.corba.se.spi.transport.SocketInfo;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.omg.CORBA.ORB;

public interface ORBSocketFactory {
  public static final String IIOP_CLEAR_TEXT = "IIOP_CLEAR_TEXT";
  
  ServerSocket createServerSocket(String paramString, int paramInt) throws IOException;
  
  SocketInfo getEndPointInfo(ORB paramORB, IOR paramIOR, SocketInfo paramSocketInfo);
  
  Socket createSocket(SocketInfo paramSocketInfo) throws IOException, GetEndPointInfoAgainException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/legacy/connection/ORBSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */