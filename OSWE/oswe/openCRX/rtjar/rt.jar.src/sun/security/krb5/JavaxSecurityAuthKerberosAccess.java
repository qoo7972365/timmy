package sun.security.krb5;

import javax.security.auth.kerberos.KerberosPrincipal;
import javax.security.auth.kerberos.KerberosTicket;
import javax.security.auth.kerberos.KeyTab;
import sun.security.krb5.internal.ktab.KeyTab;

public interface JavaxSecurityAuthKerberosAccess {
  KeyTab keyTabTakeSnapshot(KeyTab paramKeyTab);
  
  KerberosPrincipal kerberosTicketGetClientAlias(KerberosTicket paramKerberosTicket);
  
  void kerberosTicketSetClientAlias(KerberosTicket paramKerberosTicket, KerberosPrincipal paramKerberosPrincipal);
  
  KerberosPrincipal kerberosTicketGetServerAlias(KerberosTicket paramKerberosTicket);
  
  void kerberosTicketSetServerAlias(KerberosTicket paramKerberosTicket, KerberosPrincipal paramKerberosPrincipal);
  
  KerberosTicket kerberosTicketGetProxy(KerberosTicket paramKerberosTicket);
  
  void kerberosTicketSetProxy(KerberosTicket paramKerberosTicket1, KerberosTicket paramKerberosTicket2);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/JavaxSecurityAuthKerberosAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */