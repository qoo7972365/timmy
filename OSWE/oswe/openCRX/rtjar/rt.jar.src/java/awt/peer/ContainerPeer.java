package java.awt.peer;

import java.awt.Insets;

public interface ContainerPeer extends ComponentPeer {
  Insets getInsets();
  
  void beginValidate();
  
  void endValidate();
  
  void beginLayout();
  
  void endLayout();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/peer/ContainerPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */