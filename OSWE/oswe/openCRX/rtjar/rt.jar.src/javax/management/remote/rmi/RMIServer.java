package javax.management.remote.rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServer extends Remote {
  String getVersion() throws RemoteException;
  
  RMIConnection newClient(Object paramObject) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/rmi/RMIServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */