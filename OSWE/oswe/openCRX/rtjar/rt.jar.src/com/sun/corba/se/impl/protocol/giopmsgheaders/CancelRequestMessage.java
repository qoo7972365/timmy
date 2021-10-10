package com.sun.corba.se.impl.protocol.giopmsgheaders;

public interface CancelRequestMessage extends Message {
  public static final int CANCEL_REQ_MSG_SIZE = 4;
  
  int getRequestId();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/giopmsgheaders/CancelRequestMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */