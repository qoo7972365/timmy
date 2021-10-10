package javax.lang.model.type;

import javax.lang.model.AnnotatedConstruct;

public interface TypeMirror extends AnnotatedConstruct {
  TypeKind getKind();
  
  boolean equals(Object paramObject);
  
  int hashCode();
  
  String toString();
  
  <R, P> R accept(TypeVisitor<R, P> paramTypeVisitor, P paramP);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/type/TypeMirror.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */