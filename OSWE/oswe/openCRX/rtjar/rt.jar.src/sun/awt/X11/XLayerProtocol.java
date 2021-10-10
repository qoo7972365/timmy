package sun.awt.X11;

public interface XLayerProtocol {
  public static final int LAYER_NORMAL = 0;
  
  public static final int LAYER_ALWAYS_ON_TOP = 1;
  
  boolean supportsLayer(int paramInt);
  
  void setLayer(XWindowPeer paramXWindowPeer, int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XLayerProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */