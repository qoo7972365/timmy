package com.sun.nio.sctp;

import jdk.Exported;

@Exported
public abstract class ShutdownNotification implements Notification {
  public abstract Association association();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/nio/sctp/ShutdownNotification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */