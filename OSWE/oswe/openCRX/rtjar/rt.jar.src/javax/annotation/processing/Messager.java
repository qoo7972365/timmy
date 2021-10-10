package javax.annotation.processing;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

public interface Messager {
  void printMessage(Diagnostic.Kind paramKind, CharSequence paramCharSequence);
  
  void printMessage(Diagnostic.Kind paramKind, CharSequence paramCharSequence, Element paramElement);
  
  void printMessage(Diagnostic.Kind paramKind, CharSequence paramCharSequence, Element paramElement, AnnotationMirror paramAnnotationMirror);
  
  void printMessage(Diagnostic.Kind paramKind, CharSequence paramCharSequence, Element paramElement, AnnotationMirror paramAnnotationMirror, AnnotationValue paramAnnotationValue);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/annotation/processing/Messager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */