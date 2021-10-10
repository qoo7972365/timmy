package sun.awt.X11;

import java.awt.Component;

interface XScrollbarClient {
  void notifyValue(XScrollbar paramXScrollbar, int paramInt1, int paramInt2, boolean paramBoolean);
  
  Component getEventSource();
  
  void repaintScrollbarRequest(XScrollbar paramXScrollbar);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XScrollbarClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */