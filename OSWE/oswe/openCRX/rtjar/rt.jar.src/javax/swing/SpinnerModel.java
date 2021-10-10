package javax.swing;

import javax.swing.event.ChangeListener;

public interface SpinnerModel {
  Object getValue();
  
  void setValue(Object paramObject);
  
  Object getNextValue();
  
  Object getPreviousValue();
  
  void addChangeListener(ChangeListener paramChangeListener);
  
  void removeChangeListener(ChangeListener paramChangeListener);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/SpinnerModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */