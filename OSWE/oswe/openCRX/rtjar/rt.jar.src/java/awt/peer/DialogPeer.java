package java.awt.peer;

import java.awt.Window;
import java.util.List;

public interface DialogPeer extends WindowPeer {
  void setTitle(String paramString);
  
  void setResizable(boolean paramBoolean);
  
  void blockWindows(List<Window> paramList);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/peer/DialogPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */