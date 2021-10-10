package javax.print.attribute;

import java.io.Serializable;

public interface Attribute extends Serializable {
  Class<? extends Attribute> getCategory();
  
  String getName();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/Attribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */