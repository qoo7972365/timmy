package javax.management.remote;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.security.auth.Subject;

public interface JMXConnector extends Closeable {
  public static final String CREDENTIALS = "jmx.remote.credentials";
  
  void connect() throws IOException;
  
  void connect(Map<String, ?> paramMap) throws IOException;
  
  MBeanServerConnection getMBeanServerConnection() throws IOException;
  
  MBeanServerConnection getMBeanServerConnection(Subject paramSubject) throws IOException;
  
  void close() throws IOException;
  
  void addConnectionNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject);
  
  void removeConnectionNotificationListener(NotificationListener paramNotificationListener) throws ListenerNotFoundException;
  
  void removeConnectionNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws ListenerNotFoundException;
  
  String getConnectionId() throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/JMXConnector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */