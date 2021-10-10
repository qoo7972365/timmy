package javax.lang.model.type;

import java.util.List;

public interface IntersectionType extends TypeMirror {
  List<? extends TypeMirror> getBounds();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/type/IntersectionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */