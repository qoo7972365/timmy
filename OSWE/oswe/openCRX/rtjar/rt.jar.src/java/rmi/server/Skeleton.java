package java.rmi.server;

import java.rmi.Remote;

@Deprecated
public interface Skeleton {
  @Deprecated
  void dispatch(Remote paramRemote, RemoteCall paramRemoteCall, int paramInt, long paramLong) throws Exception;
  
  @Deprecated
  Operation[] getOperations();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/server/Skeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */