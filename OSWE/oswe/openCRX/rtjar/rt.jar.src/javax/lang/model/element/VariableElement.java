package javax.lang.model.element;

public interface VariableElement extends Element {
  Object getConstantValue();
  
  Name getSimpleName();
  
  Element getEnclosingElement();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/element/VariableElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */