package com.sun.jmx.remote.security;

import javax.management.Notification;
import javax.management.ObjectName;
import javax.security.auth.Subject;

public interface NotificationAccessController {
  void addNotificationListener(String paramString, ObjectName paramObjectName, Subject paramSubject) throws SecurityException;
  
  void removeNotificationListener(String paramString, ObjectName paramObjectName, Subject paramSubject) throws SecurityException;
  
  void fetchNotification(String paramString, ObjectName paramObjectName, Notification paramNotification, Subject paramSubject) throws SecurityException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/security/NotificationAccessController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */