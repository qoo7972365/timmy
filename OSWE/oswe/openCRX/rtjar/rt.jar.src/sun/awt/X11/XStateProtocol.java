package sun.awt.X11;

public interface XStateProtocol {
  boolean supportsState(int paramInt);
  
  void setState(XWindowPeer paramXWindowPeer, int paramInt);
  
  int getState(XWindowPeer paramXWindowPeer);
  
  boolean isStateChange(XPropertyEvent paramXPropertyEvent);
  
  void unshadeKludge(XWindowPeer paramXWindowPeer);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XStateProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */