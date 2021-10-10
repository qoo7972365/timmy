package javax.management;

import java.io.Serializable;

public interface NotificationFilter extends Serializable {
  boolean isNotificationEnabled(Notification paramNotification);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/NotificationFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */