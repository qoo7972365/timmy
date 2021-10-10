package com.sun.nio.sctp;

import jdk.Exported;

@Exported
public interface NotificationHandler<T> {
  HandlerResult handleNotification(Notification paramNotification, T paramT);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/nio/sctp/NotificationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */