package javax.accessibility;

public interface AccessibleTableModelChange {
  public static final int INSERT = 1;
  
  public static final int UPDATE = 0;
  
  public static final int DELETE = -1;
  
  int getType();
  
  int getFirstRow();
  
  int getLastRow();
  
  int getFirstColumn();
  
  int getLastColumn();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/accessibility/AccessibleTableModelChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */