package java.awt;

import java.awt.event.ItemListener;

public interface ItemSelectable {
  Object[] getSelectedObjects();
  
  void addItemListener(ItemListener paramItemListener);
  
  void removeItemListener(ItemListener paramItemListener);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/ItemSelectable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */