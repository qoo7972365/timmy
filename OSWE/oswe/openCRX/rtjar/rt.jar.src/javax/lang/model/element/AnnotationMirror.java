package javax.lang.model.element;

import java.util.Map;
import javax.lang.model.type.DeclaredType;

public interface AnnotationMirror {
  DeclaredType getAnnotationType();
  
  Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValues();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/element/AnnotationMirror.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */