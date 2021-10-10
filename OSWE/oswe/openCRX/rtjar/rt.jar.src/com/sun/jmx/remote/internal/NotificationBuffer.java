package com.sun.jmx.remote.internal;

import javax.management.remote.NotificationResult;

public interface NotificationBuffer {
  NotificationResult fetchNotifications(NotificationBufferFilter paramNotificationBufferFilter, long paramLong1, long paramLong2, int paramInt) throws InterruptedException;
  
  void dispose();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/internal/NotificationBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */