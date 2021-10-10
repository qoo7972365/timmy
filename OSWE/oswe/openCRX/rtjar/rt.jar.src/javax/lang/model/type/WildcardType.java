package javax.lang.model.type;

public interface WildcardType extends TypeMirror {
  TypeMirror getExtendsBound();
  
  TypeMirror getSuperBound();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/type/WildcardType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */