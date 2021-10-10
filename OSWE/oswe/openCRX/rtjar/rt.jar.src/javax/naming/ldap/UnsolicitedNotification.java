package javax.naming.ldap;

import javax.naming.NamingException;

public interface UnsolicitedNotification extends ExtendedResponse, HasControls {
  String[] getReferrals();
  
  NamingException getException();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/ldap/UnsolicitedNotification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */