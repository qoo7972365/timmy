package com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;

public interface MessageHandler {
  void handleInput(Message paramMessage) throws IOException;
  
  void handleInput(RequestMessage_1_0 paramRequestMessage_1_0) throws IOException;
  
  void handleInput(RequestMessage_1_1 paramRequestMessage_1_1) throws IOException;
  
  void handleInput(RequestMessage_1_2 paramRequestMessage_1_2) throws IOException;
  
  void handleInput(ReplyMessage_1_0 paramReplyMessage_1_0) throws IOException;
  
  void handleInput(ReplyMessage_1_1 paramReplyMessage_1_1) throws IOException;
  
  void handleInput(ReplyMessage_1_2 paramReplyMessage_1_2) throws IOException;
  
  void handleInput(LocateRequestMessage_1_0 paramLocateRequestMessage_1_0) throws IOException;
  
  void handleInput(LocateRequestMessage_1_1 paramLocateRequestMessage_1_1) throws IOException;
  
  void handleInput(LocateRequestMessage_1_2 paramLocateRequestMessage_1_2) throws IOException;
  
  void handleInput(LocateReplyMessage_1_0 paramLocateReplyMessage_1_0) throws IOException;
  
  void handleInput(LocateReplyMessage_1_1 paramLocateReplyMessage_1_1) throws IOException;
  
  void handleInput(LocateReplyMessage_1_2 paramLocateReplyMessage_1_2) throws IOException;
  
  void handleInput(FragmentMessage_1_1 paramFragmentMessage_1_1) throws IOException;
  
  void handleInput(FragmentMessage_1_2 paramFragmentMessage_1_2) throws IOException;
  
  void handleInput(CancelRequestMessage paramCancelRequestMessage) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/giopmsgheaders/MessageHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */