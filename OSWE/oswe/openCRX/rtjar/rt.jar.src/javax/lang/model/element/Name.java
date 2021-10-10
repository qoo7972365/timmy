package javax.lang.model.element;

public interface Name extends CharSequence {
  boolean equals(Object paramObject);
  
  int hashCode();
  
  boolean contentEquals(CharSequence paramCharSequence);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/element/Name.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */