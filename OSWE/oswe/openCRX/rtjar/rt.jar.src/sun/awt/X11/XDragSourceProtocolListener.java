package sun.awt.X11;

interface XDragSourceProtocolListener {
  void handleDragReply(int paramInt);
  
  void handleDragReply(int paramInt1, int paramInt2, int paramInt3);
  
  void handleDragReply(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void handleDragFinished();
  
  void handleDragFinished(boolean paramBoolean);
  
  void handleDragFinished(boolean paramBoolean, int paramInt);
  
  void handleDragFinished(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3);
  
  void cleanup(long paramLong);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XDragSourceProtocolListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */