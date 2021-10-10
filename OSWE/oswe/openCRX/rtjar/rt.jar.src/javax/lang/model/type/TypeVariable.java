package javax.lang.model.type;

import javax.lang.model.element.Element;

public interface TypeVariable extends ReferenceType {
  Element asElement();
  
  TypeMirror getUpperBound();
  
  TypeMirror getLowerBound();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/type/TypeVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */