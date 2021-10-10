package javax.swing.text;

import java.util.Enumeration;

public interface MutableAttributeSet extends AttributeSet {
  void addAttribute(Object paramObject1, Object paramObject2);
  
  void addAttributes(AttributeSet paramAttributeSet);
  
  void removeAttribute(Object paramObject);
  
  void removeAttributes(Enumeration<?> paramEnumeration);
  
  void removeAttributes(AttributeSet paramAttributeSet);
  
  void setResolveParent(AttributeSet paramAttributeSet);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/MutableAttributeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */