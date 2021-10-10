package javax.swing.text;

public interface Element {
  Document getDocument();
  
  Element getParentElement();
  
  String getName();
  
  AttributeSet getAttributes();
  
  int getStartOffset();
  
  int getEndOffset();
  
  int getElementIndex(int paramInt);
  
  int getElementCount();
  
  Element getElement(int paramInt);
  
  boolean isLeaf();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/Element.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */