package java.security.acl;

import java.security.Principal;

public interface Owner {
  boolean addOwner(Principal paramPrincipal1, Principal paramPrincipal2) throws NotOwnerException;
  
  boolean deleteOwner(Principal paramPrincipal1, Principal paramPrincipal2) throws NotOwnerException, LastOwnerException;
  
  boolean isOwner(Principal paramPrincipal);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/acl/Owner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */