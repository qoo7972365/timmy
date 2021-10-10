package javax.rmi.CORBA;

import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PortableRemoteObjectDelegate {
  void exportObject(Remote paramRemote) throws RemoteException;
  
  Remote toStub(Remote paramRemote) throws NoSuchObjectException;
  
  void unexportObject(Remote paramRemote) throws NoSuchObjectException;
  
  Object narrow(Object paramObject, Class paramClass) throws ClassCastException;
  
  void connect(Remote paramRemote1, Remote paramRemote2) throws RemoteException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/rmi/CORBA/PortableRemoteObjectDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */