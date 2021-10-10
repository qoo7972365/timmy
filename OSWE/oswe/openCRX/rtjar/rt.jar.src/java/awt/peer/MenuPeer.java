package java.awt.peer;

import java.awt.MenuItem;

public interface MenuPeer extends MenuItemPeer {
  void addSeparator();
  
  void addItem(MenuItem paramMenuItem);
  
  void delItem(int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/peer/MenuPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */