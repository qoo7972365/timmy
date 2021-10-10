package javax.management;

public interface NotificationBroadcaster {
  void addNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws IllegalArgumentException;
  
  void removeNotificationListener(NotificationListener paramNotificationListener) throws ListenerNotFoundException;
  
  MBeanNotificationInfo[] getNotificationInfo();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/NotificationBroadcaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */