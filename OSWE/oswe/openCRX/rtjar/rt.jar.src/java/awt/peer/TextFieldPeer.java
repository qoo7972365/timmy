package java.awt.peer;

import java.awt.Dimension;

public interface TextFieldPeer extends TextComponentPeer {
  void setEchoChar(char paramChar);
  
  Dimension getPreferredSize(int paramInt);
  
  Dimension getMinimumSize(int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/peer/TextFieldPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */