package java.awt;

import java.io.Serializable;

class FocusManager implements Serializable {
  Container focusRoot;
  
  Component focusOwner;
  
  static final long serialVersionUID = 2491878825643557906L;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/FocusManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */