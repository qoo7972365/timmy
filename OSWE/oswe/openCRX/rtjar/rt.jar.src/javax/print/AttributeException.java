package javax.print;

import javax.print.attribute.Attribute;

public interface AttributeException {
  Class[] getUnsupportedAttributes();
  
  Attribute[] getUnsupportedValues();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/AttributeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */