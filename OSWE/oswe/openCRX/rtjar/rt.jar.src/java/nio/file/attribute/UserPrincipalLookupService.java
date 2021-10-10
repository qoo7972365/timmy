package java.nio.file.attribute;

import java.io.IOException;

public abstract class UserPrincipalLookupService {
  public abstract UserPrincipal lookupPrincipalByName(String paramString) throws IOException;
  
  public abstract GroupPrincipal lookupPrincipalByGroupName(String paramString) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/attribute/UserPrincipalLookupService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */