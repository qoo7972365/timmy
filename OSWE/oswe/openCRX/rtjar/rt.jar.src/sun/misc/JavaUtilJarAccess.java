package sun.misc;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public interface JavaUtilJarAccess {
  boolean jarFileHasClassPathAttribute(JarFile paramJarFile) throws IOException;
  
  CodeSource[] getCodeSources(JarFile paramJarFile, URL paramURL);
  
  CodeSource getCodeSource(JarFile paramJarFile, URL paramURL, String paramString);
  
  Enumeration<String> entryNames(JarFile paramJarFile, CodeSource[] paramArrayOfCodeSource);
  
  Enumeration<JarEntry> entries2(JarFile paramJarFile);
  
  void setEagerValidation(JarFile paramJarFile, boolean paramBoolean);
  
  List<Object> getManifestDigests(JarFile paramJarFile);
  
  Attributes getTrustedAttributes(Manifest paramManifest, String paramString);
  
  void ensureInitialization(JarFile paramJarFile);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/JavaUtilJarAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */