package javax.swing;

import javax.swing.event.ChangeListener;

public interface BoundedRangeModel {
  int getMinimum();
  
  void setMinimum(int paramInt);
  
  int getMaximum();
  
  void setMaximum(int paramInt);
  
  int getValue();
  
  void setValue(int paramInt);
  
  void setValueIsAdjusting(boolean paramBoolean);
  
  boolean getValueIsAdjusting();
  
  int getExtent();
  
  void setExtent(int paramInt);
  
  void setRangeProperties(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean);
  
  void addChangeListener(ChangeListener paramChangeListener);
  
  void removeChangeListener(ChangeListener paramChangeListener);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/BoundedRangeModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */