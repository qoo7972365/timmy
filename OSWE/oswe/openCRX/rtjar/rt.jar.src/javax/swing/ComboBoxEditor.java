package javax.swing;

import java.awt.Component;
import java.awt.event.ActionListener;

public interface ComboBoxEditor {
  Component getEditorComponent();
  
  void setItem(Object paramObject);
  
  Object getItem();
  
  void selectAll();
  
  void addActionListener(ActionListener paramActionListener);
  
  void removeActionListener(ActionListener paramActionListener);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/ComboBoxEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */