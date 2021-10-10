/*     */ package sun.net.www.protocol.jar;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.Map;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.Manifest;
/*     */ import java.util.zip.ZipEntry;
/*     */ import sun.net.www.ParseUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class URLJarFile
/*     */   extends JarFile
/*     */ {
/*  52 */   private static URLJarFileCallBack callback = null;
/*     */ 
/*     */   
/*  55 */   private URLJarFileCloseController closeController = null;
/*     */   
/*  57 */   private static int BUF_SIZE = 2048;
/*     */   
/*     */   private Manifest superMan;
/*     */   private Attributes superAttr;
/*     */   private Map<String, Attributes> superEntries;
/*     */   
/*     */   static JarFile getJarFile(URL paramURL) throws IOException {
/*  64 */     return getJarFile(paramURL, null);
/*     */   }
/*     */   
/*     */   static JarFile getJarFile(URL paramURL, URLJarFileCloseController paramURLJarFileCloseController) throws IOException {
/*  68 */     if (isFileURL(paramURL)) {
/*  69 */       return new URLJarFile(paramURL, paramURLJarFileCloseController);
/*     */     }
/*  71 */     return retrieve(paramURL, paramURLJarFileCloseController);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URLJarFile(File paramFile) throws IOException {
/*  80 */     this(paramFile, (URLJarFileCloseController)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URLJarFile(File paramFile, URLJarFileCloseController paramURLJarFileCloseController) throws IOException {
/*  88 */     super(paramFile, true, 5);
/*  89 */     this.closeController = paramURLJarFileCloseController;
/*     */   }
/*     */   
/*     */   private URLJarFile(URL paramURL, URLJarFileCloseController paramURLJarFileCloseController) throws IOException {
/*  93 */     super(ParseUtil.decode(paramURL.getFile()));
/*  94 */     this.closeController = paramURLJarFileCloseController;
/*     */   }
/*     */   
/*     */   private static boolean isFileURL(URL paramURL) {
/*  98 */     if (paramURL.getProtocol().equalsIgnoreCase("file")) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       String str = paramURL.getHost();
/* 104 */       if (str == null || str.equals("") || str.equals("~") || str
/* 105 */         .equalsIgnoreCase("localhost"))
/* 106 */         return true; 
/*     */     } 
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws IOException {
/* 115 */     close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZipEntry getEntry(String paramString) {
/* 128 */     ZipEntry zipEntry = super.getEntry(paramString);
/* 129 */     if (zipEntry != null) {
/* 130 */       if (zipEntry instanceof JarEntry) {
/* 131 */         return new URLJarFileEntry((JarEntry)zipEntry);
/*     */       }
/* 133 */       throw new InternalError(getClass() + " returned unexpected entry type " + zipEntry
/*     */           
/* 135 */           .getClass());
/*     */     } 
/* 137 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Manifest getManifest() throws IOException {
/* 142 */     if (!isSuperMan()) {
/* 143 */       return null;
/*     */     }
/*     */     
/* 146 */     Manifest manifest = new Manifest();
/* 147 */     Attributes attributes = manifest.getMainAttributes();
/* 148 */     attributes.putAll((Map<?, ?>)this.superAttr.clone());
/*     */ 
/*     */     
/* 151 */     if (this.superEntries != null) {
/* 152 */       Map<String, Attributes> map = manifest.getEntries();
/* 153 */       for (String str : this.superEntries.keySet()) {
/* 154 */         Attributes attributes1 = this.superEntries.get(str);
/* 155 */         map.put(str, (Attributes)attributes1.clone());
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     return manifest;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 164 */     if (this.closeController != null) {
/* 165 */       this.closeController.close(this);
/*     */     }
/* 167 */     super.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized boolean isSuperMan() throws IOException {
/* 173 */     if (this.superMan == null) {
/* 174 */       this.superMan = super.getManifest();
/*     */     }
/*     */     
/* 177 */     if (this.superMan != null) {
/* 178 */       this.superAttr = this.superMan.getMainAttributes();
/* 179 */       this.superEntries = this.superMan.getEntries();
/* 180 */       return true;
/*     */     } 
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JarFile retrieve(URL paramURL) throws IOException {
/* 190 */     return retrieve(paramURL, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JarFile retrieve(URL paramURL, final URLJarFileCloseController closeController) throws IOException {
/* 203 */     if (callback != null)
/*     */     {
/* 205 */       return callback.retrieve(paramURL);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     JarFile jarFile = null;
/*     */ 
/*     */     
/* 214 */     try (InputStream null = paramURL.openConnection().getInputStream()) {
/* 215 */       jarFile = AccessController.<JarFile>doPrivileged(new PrivilegedExceptionAction<JarFile>()
/*     */           {
/*     */             public JarFile run() throws IOException {
/* 218 */               Path path = Files.createTempFile("jar_cache", null, (FileAttribute<?>[])new FileAttribute[0]);
/*     */               try {
/* 220 */                 Files.copy(in, path, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/* 221 */                 URLJarFile uRLJarFile = new URLJarFile(path.toFile(), closeController);
/* 222 */                 path.toFile().deleteOnExit();
/* 223 */                 return uRLJarFile;
/* 224 */               } catch (Throwable throwable) {
/*     */                 try {
/* 226 */                   Files.delete(path);
/* 227 */                 } catch (IOException iOException) {
/* 228 */                   throwable.addSuppressed(iOException);
/*     */                 } 
/* 230 */                 throw throwable;
/*     */               } 
/*     */             }
/*     */           });
/* 234 */     } catch (PrivilegedActionException privilegedActionException) {
/* 235 */       throw (IOException)privilegedActionException.getException();
/*     */     } 
/*     */     
/* 238 */     return jarFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setCallBack(URLJarFileCallBack paramURLJarFileCallBack) {
/* 248 */     callback = paramURLJarFileCallBack;
/*     */   }
/*     */   
/*     */   private class URLJarFileEntry
/*     */     extends JarEntry {
/*     */     private JarEntry je;
/*     */     
/*     */     URLJarFileEntry(JarEntry param1JarEntry) {
/* 256 */       super(param1JarEntry);
/* 257 */       this.je = param1JarEntry;
/*     */     }
/*     */     
/*     */     public Attributes getAttributes() throws IOException {
/* 261 */       if (URLJarFile.this.isSuperMan()) {
/* 262 */         Map map = URLJarFile.this.superEntries;
/* 263 */         if (map != null) {
/* 264 */           Attributes attributes = (Attributes)map.get(getName());
/* 265 */           if (attributes != null)
/* 266 */             return (Attributes)attributes.clone(); 
/*     */         } 
/*     */       } 
/* 269 */       return null;
/*     */     }
/*     */     
/*     */     public Certificate[] getCertificates() {
/* 273 */       Certificate[] arrayOfCertificate = this.je.getCertificates();
/* 274 */       return (arrayOfCertificate == null) ? null : (Certificate[])arrayOfCertificate.clone();
/*     */     }
/*     */     
/*     */     public CodeSigner[] getCodeSigners() {
/* 278 */       CodeSigner[] arrayOfCodeSigner = this.je.getCodeSigners();
/* 279 */       return (arrayOfCodeSigner == null) ? null : (CodeSigner[])arrayOfCodeSigner.clone();
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface URLJarFileCloseController {
/*     */     void close(JarFile param1JarFile);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/jar/URLJarFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */