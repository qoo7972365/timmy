package javax.accessibility;

public interface AccessibleSelection {
  int getAccessibleSelectionCount();
  
  Accessible getAccessibleSelection(int paramInt);
  
  boolean isAccessibleChildSelected(int paramInt);
  
  void addAccessibleSelection(int paramInt);
  
  void removeAccessibleSelection(int paramInt);
  
  void clearAccessibleSelection();
  
  void selectAllAccessibleSelection();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/accessibility/AccessibleSelection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */