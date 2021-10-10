package com.sun.nio.sctp;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import jdk.Exported;

@Exported
public abstract class SendFailedNotification implements Notification {
  public abstract Association association();
  
  public abstract SocketAddress address();
  
  public abstract ByteBuffer buffer();
  
  public abstract int errorCode();
  
  public abstract int streamNumber();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/nio/sctp/SendFailedNotification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */