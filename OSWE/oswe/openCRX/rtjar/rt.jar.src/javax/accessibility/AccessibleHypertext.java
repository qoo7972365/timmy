package javax.accessibility;

public interface AccessibleHypertext extends AccessibleText {
  int getLinkCount();
  
  AccessibleHyperlink getLink(int paramInt);
  
  int getLinkIndex(int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/accessibility/AccessibleHypertext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */