package sun.security.util;

public interface PermissionFactory<T extends java.security.Permission> {
  T newPermission(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/PermissionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */