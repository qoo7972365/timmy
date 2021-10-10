package sun.awt.X11;

import java.awt.Graphics;

public interface XAbstractMenuItem {
  int getWidth(Graphics paramGraphics);
  
  int getShortcutWidth(Graphics paramGraphics);
  
  String getLabel();
  
  int getHeight(Graphics paramGraphics);
  
  void paint(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean);
  
  void setMenuPeer(XMenuPeer paramXMenuPeer);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XAbstractMenuItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */