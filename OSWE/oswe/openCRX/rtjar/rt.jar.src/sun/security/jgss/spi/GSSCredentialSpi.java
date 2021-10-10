package sun.security.jgss.spi;

import java.security.Provider;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.Oid;

public interface GSSCredentialSpi {
  Provider getProvider();
  
  void dispose() throws GSSException;
  
  GSSNameSpi getName() throws GSSException;
  
  int getInitLifetime() throws GSSException;
  
  int getAcceptLifetime() throws GSSException;
  
  boolean isInitiatorCredential() throws GSSException;
  
  boolean isAcceptorCredential() throws GSSException;
  
  Oid getMechanism();
  
  GSSCredentialSpi impersonate(GSSNameSpi paramGSSNameSpi) throws GSSException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/spi/GSSCredentialSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */