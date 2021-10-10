package javax.lang.model.element;

import java.util.List;

public interface Parameterizable extends Element {
  List<? extends TypeParameterElement> getTypeParameters();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/element/Parameterizable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */