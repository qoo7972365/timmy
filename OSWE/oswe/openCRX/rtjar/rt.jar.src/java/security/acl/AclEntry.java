package java.security.acl;

import java.security.Principal;
import java.util.Enumeration;

public interface AclEntry extends Cloneable {
  boolean setPrincipal(Principal paramPrincipal);
  
  Principal getPrincipal();
  
  void setNegativePermissions();
  
  boolean isNegative();
  
  boolean addPermission(Permission paramPermission);
  
  boolean removePermission(Permission paramPermission);
  
  boolean checkPermission(Permission paramPermission);
  
  Enumeration<Permission> permissions();
  
  String toString();
  
  Object clone();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/acl/AclEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */