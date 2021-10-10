/*    */ package java.util.jar;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.security.CodeSource;
/*    */ import java.util.Enumeration;
/*    */ import java.util.List;
/*    */ import sun.misc.JavaUtilJarAccess;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class JavaUtilJarAccessImpl
/*    */   implements JavaUtilJarAccess
/*    */ {
/*    */   public boolean jarFileHasClassPathAttribute(JarFile paramJarFile) throws IOException {
/* 37 */     return paramJarFile.hasClassPathAttribute();
/*    */   }
/*    */   
/*    */   public CodeSource[] getCodeSources(JarFile paramJarFile, URL paramURL) {
/* 41 */     return paramJarFile.getCodeSources(paramURL);
/*    */   }
/*    */   
/*    */   public CodeSource getCodeSource(JarFile paramJarFile, URL paramURL, String paramString) {
/* 45 */     return paramJarFile.getCodeSource(paramURL, paramString);
/*    */   }
/*    */   
/*    */   public Enumeration<String> entryNames(JarFile paramJarFile, CodeSource[] paramArrayOfCodeSource) {
/* 49 */     return paramJarFile.entryNames(paramArrayOfCodeSource);
/*    */   }
/*    */   
/*    */   public Enumeration<JarEntry> entries2(JarFile paramJarFile) {
/* 53 */     return paramJarFile.entries2();
/*    */   }
/*    */   
/*    */   public void setEagerValidation(JarFile paramJarFile, boolean paramBoolean) {
/* 57 */     paramJarFile.setEagerValidation(paramBoolean);
/*    */   }
/*    */   
/*    */   public List<Object> getManifestDigests(JarFile paramJarFile) {
/* 61 */     return paramJarFile.getManifestDigests();
/*    */   }
/*    */   
/*    */   public Attributes getTrustedAttributes(Manifest paramManifest, String paramString) {
/* 65 */     return paramManifest.getTrustedAttributes(paramString);
/*    */   }
/*    */   
/*    */   public void ensureInitialization(JarFile paramJarFile) {
/* 69 */     paramJarFile.ensureInitialization();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/jar/JavaUtilJarAccessImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */