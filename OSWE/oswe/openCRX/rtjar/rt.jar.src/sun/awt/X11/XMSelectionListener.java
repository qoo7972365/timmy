package sun.awt.X11;

public interface XMSelectionListener {
  void ownerChanged(int paramInt, XMSelection paramXMSelection, long paramLong1, long paramLong2, long paramLong3);
  
  void ownerDeath(int paramInt, XMSelection paramXMSelection, long paramLong);
  
  void selectionChanged(int paramInt, XMSelection paramXMSelection, long paramLong, XPropertyEvent paramXPropertyEvent);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XMSelectionListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */