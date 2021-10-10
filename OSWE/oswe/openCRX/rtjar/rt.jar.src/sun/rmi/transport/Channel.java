package sun.rmi.transport;

import java.rmi.RemoteException;

public interface Channel {
  Connection newConnection() throws RemoteException;
  
  Endpoint getEndpoint();
  
  void free(Connection paramConnection, boolean paramBoolean) throws RemoteException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/Channel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */