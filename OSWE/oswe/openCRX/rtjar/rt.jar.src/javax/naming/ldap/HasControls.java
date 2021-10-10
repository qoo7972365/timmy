package javax.naming.ldap;

import javax.naming.NamingException;

public interface HasControls {
  Control[] getControls() throws NamingException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/ldap/HasControls.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */