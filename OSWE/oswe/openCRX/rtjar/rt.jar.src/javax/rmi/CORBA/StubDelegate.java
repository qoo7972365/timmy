package javax.rmi.CORBA;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import org.omg.CORBA.ORB;

public interface StubDelegate {
  int hashCode(Stub paramStub);
  
  boolean equals(Stub paramStub, Object paramObject);
  
  String toString(Stub paramStub);
  
  void connect(Stub paramStub, ORB paramORB) throws RemoteException;
  
  void readObject(Stub paramStub, ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException;
  
  void writeObject(Stub paramStub, ObjectOutputStream paramObjectOutputStream) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/rmi/CORBA/StubDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */