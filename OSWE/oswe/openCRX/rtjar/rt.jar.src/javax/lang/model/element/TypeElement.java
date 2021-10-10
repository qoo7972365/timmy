package javax.lang.model.element;

import java.util.List;
import javax.lang.model.type.TypeMirror;

public interface TypeElement extends Element, Parameterizable, QualifiedNameable {
  List<? extends Element> getEnclosedElements();
  
  NestingKind getNestingKind();
  
  Name getQualifiedName();
  
  Name getSimpleName();
  
  TypeMirror getSuperclass();
  
  List<? extends TypeMirror> getInterfaces();
  
  List<? extends TypeParameterElement> getTypeParameters();
  
  Element getEnclosingElement();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/element/TypeElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */