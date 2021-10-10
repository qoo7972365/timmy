package sun.awt.X11;

public interface XModalityProtocol {
  boolean setModal(XDialogPeer paramXDialogPeer, boolean paramBoolean);
  
  boolean isBlocked(XDialogPeer paramXDialogPeer, XWindowPeer paramXWindowPeer);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XModalityProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */