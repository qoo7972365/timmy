package javax.naming.ldap;

import java.io.Serializable;

public interface ExtendedResponse extends Serializable {
  String getID();
  
  byte[] getEncodedValue();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/ldap/ExtendedResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */