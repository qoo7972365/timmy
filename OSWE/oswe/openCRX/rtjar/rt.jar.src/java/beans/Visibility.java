package java.beans;

public interface Visibility {
  boolean needsGui();
  
  void dontUseGui();
  
  void okToUseGui();
  
  boolean avoidingGui();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/Visibility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */