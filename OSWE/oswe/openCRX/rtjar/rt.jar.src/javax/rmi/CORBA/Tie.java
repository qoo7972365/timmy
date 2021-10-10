package javax.rmi.CORBA;

import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.InvokeHandler;

public interface Tie extends InvokeHandler {
  Object thisObject();
  
  void deactivate() throws NoSuchObjectException;
  
  ORB orb();
  
  void orb(ORB paramORB);
  
  void setTarget(Remote paramRemote);
  
  Remote getTarget();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/rmi/CORBA/Tie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */