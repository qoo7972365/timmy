package javax.management;

import java.util.EventListener;

public interface NotificationListener extends EventListener {
  void handleNotification(Notification paramNotification, Object paramObject);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/NotificationListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */