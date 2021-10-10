package java.rmi.server;

import java.io.Externalizable;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteRef extends Externalizable {
  public static final long serialVersionUID = 3632638527362204081L;
  
  public static final String packagePrefix = "sun.rmi.server";
  
  Object invoke(Remote paramRemote, Method paramMethod, Object[] paramArrayOfObject, long paramLong) throws Exception;
  
  @Deprecated
  RemoteCall newCall(RemoteObject paramRemoteObject, Operation[] paramArrayOfOperation, int paramInt, long paramLong) throws RemoteException;
  
  @Deprecated
  void invoke(RemoteCall paramRemoteCall) throws Exception;
  
  @Deprecated
  void done(RemoteCall paramRemoteCall) throws RemoteException;
  
  String getRefClass(ObjectOutput paramObjectOutput);
  
  int remoteHashCode();
  
  boolean remoteEquals(RemoteRef paramRemoteRef);
  
  String remoteToString();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/server/RemoteRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */