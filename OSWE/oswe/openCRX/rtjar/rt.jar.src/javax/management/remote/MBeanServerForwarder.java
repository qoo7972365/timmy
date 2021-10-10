package javax.management.remote;

import javax.management.MBeanServer;

public interface MBeanServerForwarder extends MBeanServer {
  MBeanServer getMBeanServer();
  
  void setMBeanServer(MBeanServer paramMBeanServer);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/MBeanServerForwarder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */