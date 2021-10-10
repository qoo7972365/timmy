/*      */ package java.io;
/*      */ 
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.nio.file.FileSystems;
/*      */ import java.nio.file.Path;
/*      */ import java.security.AccessController;
/*      */ import java.security.SecureRandom;
/*      */ import java.util.ArrayList;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class File
/*      */   implements Serializable, Comparable<File>
/*      */ {
/*  156 */   private static final FileSystem fs = DefaultFileSystem.getFileSystem();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String path;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private enum PathStatus
/*      */   {
/*  170 */     INVALID, CHECKED;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  175 */   private transient PathStatus status = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient int prefixLength;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean isInvalid() {
/*  186 */     if (this.status == null) {
/*  187 */       this.status = (this.path.indexOf(false) < 0) ? PathStatus.CHECKED : PathStatus.INVALID;
/*      */     }
/*      */     
/*  190 */     return (this.status == PathStatus.INVALID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getPrefixLength() {
/*  204 */     return this.prefixLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  215 */   public static final char separatorChar = fs.getSeparator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  222 */   public static final String separator = "" + separatorChar;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  234 */   public static final char pathSeparatorChar = fs.getPathSeparator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  241 */   public static final String pathSeparator = "" + pathSeparatorChar;
/*      */   
/*      */   private static final long PATH_OFFSET;
/*      */   private static final long PREFIX_LENGTH_OFFSET;
/*      */   private static final Unsafe UNSAFE;
/*      */   private static final long serialVersionUID = 301077366599181567L;
/*      */   private volatile transient Path filePath;
/*      */   
/*      */   private File(String paramString, int paramInt) {
/*  250 */     this.path = paramString;
/*  251 */     this.prefixLength = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private File(String paramString, File paramFile) {
/*  260 */     assert paramFile.path != null;
/*  261 */     assert !paramFile.path.equals("");
/*  262 */     this.path = fs.resolve(paramFile.path, paramString);
/*  263 */     this.prefixLength = paramFile.prefixLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File(String paramString) {
/*  276 */     if (paramString == null) {
/*  277 */       throw new NullPointerException();
/*      */     }
/*  279 */     this.path = fs.normalize(paramString);
/*  280 */     this.prefixLength = fs.prefixLength(this.path);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File(String paramString1, String paramString2) {
/*  316 */     if (paramString2 == null) {
/*  317 */       throw new NullPointerException();
/*      */     }
/*  319 */     if (paramString1 != null) {
/*  320 */       if (paramString1.equals("")) {
/*  321 */         this.path = fs.resolve(fs.getDefaultParent(), fs
/*  322 */             .normalize(paramString2));
/*      */       } else {
/*  324 */         this.path = fs.resolve(fs.normalize(paramString1), fs
/*  325 */             .normalize(paramString2));
/*      */       } 
/*      */     } else {
/*  328 */       this.path = fs.normalize(paramString2);
/*      */     } 
/*  330 */     this.prefixLength = fs.prefixLength(this.path);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File(File paramFile, String paramString) {
/*  359 */     if (paramString == null) {
/*  360 */       throw new NullPointerException();
/*      */     }
/*  362 */     if (paramFile != null) {
/*  363 */       if (paramFile.path.equals("")) {
/*  364 */         this.path = fs.resolve(fs.getDefaultParent(), fs
/*  365 */             .normalize(paramString));
/*      */       } else {
/*  367 */         this.path = fs.resolve(paramFile.path, fs
/*  368 */             .normalize(paramString));
/*      */       } 
/*      */     } else {
/*  371 */       this.path = fs.normalize(paramString);
/*      */     } 
/*  373 */     this.prefixLength = fs.prefixLength(this.path);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File(URI paramURI) {
/*  415 */     if (!paramURI.isAbsolute())
/*  416 */       throw new IllegalArgumentException("URI is not absolute"); 
/*  417 */     if (paramURI.isOpaque())
/*  418 */       throw new IllegalArgumentException("URI is not hierarchical"); 
/*  419 */     String str1 = paramURI.getScheme();
/*  420 */     if (str1 == null || !str1.equalsIgnoreCase("file"))
/*  421 */       throw new IllegalArgumentException("URI scheme is not \"file\""); 
/*  422 */     if (paramURI.getAuthority() != null)
/*  423 */       throw new IllegalArgumentException("URI has an authority component"); 
/*  424 */     if (paramURI.getFragment() != null)
/*  425 */       throw new IllegalArgumentException("URI has a fragment component"); 
/*  426 */     if (paramURI.getQuery() != null)
/*  427 */       throw new IllegalArgumentException("URI has a query component"); 
/*  428 */     String str2 = paramURI.getPath();
/*  429 */     if (str2.equals("")) {
/*  430 */       throw new IllegalArgumentException("URI path component is empty");
/*      */     }
/*      */     
/*  433 */     str2 = fs.fromURIPath(str2);
/*  434 */     if (separatorChar != '/')
/*  435 */       str2 = str2.replace('/', separatorChar); 
/*  436 */     this.path = fs.normalize(str2);
/*  437 */     this.prefixLength = fs.prefixLength(this.path);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  454 */     int i = this.path.lastIndexOf(separatorChar);
/*  455 */     if (i < this.prefixLength) return this.path.substring(this.prefixLength); 
/*  456 */     return this.path.substring(i + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getParent() {
/*  473 */     int i = this.path.lastIndexOf(separatorChar);
/*  474 */     if (i < this.prefixLength) {
/*  475 */       if (this.prefixLength > 0 && this.path.length() > this.prefixLength)
/*  476 */         return this.path.substring(0, this.prefixLength); 
/*  477 */       return null;
/*      */     } 
/*  479 */     return this.path.substring(0, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getParentFile() {
/*  499 */     String str = getParent();
/*  500 */     if (str == null) return null; 
/*  501 */     return new File(str, this.prefixLength);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPath() {
/*  512 */     return this.path;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAbsolute() {
/*  529 */     return fs.isAbsolute(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAbsolutePath() {
/*  556 */     return fs.resolve(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getAbsoluteFile() {
/*  572 */     String str = getAbsolutePath();
/*  573 */     return new File(str, fs.prefixLength(str));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCanonicalPath() throws IOException {
/*  615 */     if (isInvalid()) {
/*  616 */       throw new IOException("Invalid file path");
/*      */     }
/*  618 */     return fs.canonicalize(fs.resolve(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getCanonicalFile() throws IOException {
/*  643 */     String str = getCanonicalPath();
/*  644 */     return new File(str, fs.prefixLength(str));
/*      */   }
/*      */   
/*      */   private static String slashify(String paramString, boolean paramBoolean) {
/*  648 */     String str = paramString;
/*  649 */     if (separatorChar != '/')
/*  650 */       str = str.replace(separatorChar, '/'); 
/*  651 */     if (!str.startsWith("/"))
/*  652 */       str = "/" + str; 
/*  653 */     if (!str.endsWith("/") && paramBoolean)
/*  654 */       str = str + "/"; 
/*  655 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public URL toURL() throws MalformedURLException {
/*  683 */     if (isInvalid()) {
/*  684 */       throw new MalformedURLException("Invalid file path");
/*      */     }
/*  686 */     return new URL("file", "", slashify(getAbsolutePath(), isDirectory()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URI toURI() {
/*      */     try {
/*  731 */       File file = getAbsoluteFile();
/*  732 */       String str = slashify(file.getPath(), file.isDirectory());
/*  733 */       if (str.startsWith("//"))
/*  734 */         str = "//" + str; 
/*  735 */       return new URI("file", null, str, null);
/*  736 */     } catch (URISyntaxException uRISyntaxException) {
/*  737 */       throw new Error(uRISyntaxException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRead() {
/*  761 */     SecurityManager securityManager = System.getSecurityManager();
/*  762 */     if (securityManager != null) {
/*  763 */       securityManager.checkRead(this.path);
/*      */     }
/*  765 */     if (isInvalid()) {
/*  766 */       return false;
/*      */     }
/*  768 */     return fs.checkAccess(this, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canWrite() {
/*  789 */     SecurityManager securityManager = System.getSecurityManager();
/*  790 */     if (securityManager != null) {
/*  791 */       securityManager.checkWrite(this.path);
/*      */     }
/*  793 */     if (isInvalid()) {
/*  794 */       return false;
/*      */     }
/*  796 */     return fs.checkAccess(this, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean exists() {
/*  812 */     SecurityManager securityManager = System.getSecurityManager();
/*  813 */     if (securityManager != null) {
/*  814 */       securityManager.checkRead(this.path);
/*      */     }
/*  816 */     if (isInvalid()) {
/*  817 */       return false;
/*      */     }
/*  819 */     return ((fs.getBooleanAttributes(this) & 0x1) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDirectory() {
/*  842 */     SecurityManager securityManager = System.getSecurityManager();
/*  843 */     if (securityManager != null) {
/*  844 */       securityManager.checkRead(this.path);
/*      */     }
/*  846 */     if (isInvalid()) {
/*  847 */       return false;
/*      */     }
/*  849 */     return ((fs.getBooleanAttributes(this) & 0x4) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFile() {
/*  875 */     SecurityManager securityManager = System.getSecurityManager();
/*  876 */     if (securityManager != null) {
/*  877 */       securityManager.checkRead(this.path);
/*      */     }
/*  879 */     if (isInvalid()) {
/*  880 */       return false;
/*      */     }
/*  882 */     return ((fs.getBooleanAttributes(this) & 0x2) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isHidden() {
/*  904 */     SecurityManager securityManager = System.getSecurityManager();
/*  905 */     if (securityManager != null) {
/*  906 */       securityManager.checkRead(this.path);
/*      */     }
/*  908 */     if (isInvalid()) {
/*  909 */       return false;
/*      */     }
/*  911 */     return ((fs.getBooleanAttributes(this) & 0x8) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long lastModified() {
/*  936 */     SecurityManager securityManager = System.getSecurityManager();
/*  937 */     if (securityManager != null) {
/*  938 */       securityManager.checkRead(this.path);
/*      */     }
/*  940 */     if (isInvalid()) {
/*  941 */       return 0L;
/*      */     }
/*  943 */     return fs.getLastModifiedTime(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long length() {
/*  967 */     SecurityManager securityManager = System.getSecurityManager();
/*  968 */     if (securityManager != null) {
/*  969 */       securityManager.checkRead(this.path);
/*      */     }
/*  971 */     if (isInvalid()) {
/*  972 */       return 0L;
/*      */     }
/*  974 */     return fs.getLength(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean createNewFile() throws IOException {
/* 1007 */     SecurityManager securityManager = System.getSecurityManager();
/* 1008 */     if (securityManager != null) securityManager.checkWrite(this.path); 
/* 1009 */     if (isInvalid()) {
/* 1010 */       throw new IOException("Invalid file path");
/*      */     }
/* 1012 */     return fs.createFileExclusively(this.path);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean delete() {
/* 1034 */     SecurityManager securityManager = System.getSecurityManager();
/* 1035 */     if (securityManager != null) {
/* 1036 */       securityManager.checkDelete(this.path);
/*      */     }
/* 1038 */     if (isInvalid()) {
/* 1039 */       return false;
/*      */     }
/* 1041 */     return fs.delete(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteOnExit() {
/* 1072 */     SecurityManager securityManager = System.getSecurityManager();
/* 1073 */     if (securityManager != null) {
/* 1074 */       securityManager.checkDelete(this.path);
/*      */     }
/* 1076 */     if (isInvalid()) {
/*      */       return;
/*      */     }
/* 1079 */     DeleteOnExitHook.add(this.path);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] list() {
/* 1115 */     SecurityManager securityManager = System.getSecurityManager();
/* 1116 */     if (securityManager != null) {
/* 1117 */       securityManager.checkRead(this.path);
/*      */     }
/* 1119 */     if (isInvalid()) {
/* 1120 */       return null;
/*      */     }
/* 1122 */     return fs.list(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] list(FilenameFilter paramFilenameFilter) {
/* 1155 */     String[] arrayOfString = list();
/* 1156 */     if (arrayOfString == null || paramFilenameFilter == null) {
/* 1157 */       return arrayOfString;
/*      */     }
/* 1159 */     ArrayList<String> arrayList = new ArrayList();
/* 1160 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 1161 */       if (paramFilenameFilter.accept(this, arrayOfString[b])) {
/* 1162 */         arrayList.add(arrayOfString[b]);
/*      */       }
/*      */     } 
/* 1165 */     return arrayList.<String>toArray(new String[arrayList.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File[] listFiles() {
/* 1207 */     String[] arrayOfString = list();
/* 1208 */     if (arrayOfString == null) return null; 
/* 1209 */     int i = arrayOfString.length;
/* 1210 */     File[] arrayOfFile = new File[i];
/* 1211 */     for (byte b = 0; b < i; b++) {
/* 1212 */       arrayOfFile[b] = new File(arrayOfString[b], this);
/*      */     }
/* 1214 */     return arrayOfFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File[] listFiles(FilenameFilter paramFilenameFilter) {
/* 1248 */     String[] arrayOfString = list();
/* 1249 */     if (arrayOfString == null) return null; 
/* 1250 */     ArrayList<File> arrayList = new ArrayList();
/* 1251 */     for (String str : arrayOfString) {
/* 1252 */       if (paramFilenameFilter == null || paramFilenameFilter.accept(this, str))
/* 1253 */         arrayList.add(new File(str, this)); 
/* 1254 */     }  return arrayList.<File>toArray(new File[arrayList.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File[] listFiles(FileFilter paramFileFilter) {
/* 1286 */     String[] arrayOfString = list();
/* 1287 */     if (arrayOfString == null) return null; 
/* 1288 */     ArrayList<File> arrayList = new ArrayList();
/* 1289 */     for (String str : arrayOfString) {
/* 1290 */       File file = new File(str, this);
/* 1291 */       if (paramFileFilter == null || paramFileFilter.accept(file))
/* 1292 */         arrayList.add(file); 
/*      */     } 
/* 1294 */     return arrayList.<File>toArray(new File[arrayList.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean mkdir() {
/* 1309 */     SecurityManager securityManager = System.getSecurityManager();
/* 1310 */     if (securityManager != null) {
/* 1311 */       securityManager.checkWrite(this.path);
/*      */     }
/* 1313 */     if (isInvalid()) {
/* 1314 */       return false;
/*      */     }
/* 1316 */     return fs.createDirectory(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean mkdirs() {
/* 1340 */     if (exists()) {
/* 1341 */       return false;
/*      */     }
/* 1343 */     if (mkdir()) {
/* 1344 */       return true;
/*      */     }
/* 1346 */     File file1 = null;
/*      */     try {
/* 1348 */       file1 = getCanonicalFile();
/* 1349 */     } catch (IOException iOException) {
/* 1350 */       return false;
/*      */     } 
/*      */     
/* 1353 */     File file2 = file1.getParentFile();
/* 1354 */     return (file2 != null && (file2.mkdirs() || file2.exists()) && file1
/* 1355 */       .mkdir());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean renameTo(File paramFile) {
/* 1386 */     SecurityManager securityManager = System.getSecurityManager();
/* 1387 */     if (securityManager != null) {
/* 1388 */       securityManager.checkWrite(this.path);
/* 1389 */       securityManager.checkWrite(paramFile.path);
/*      */     } 
/* 1391 */     if (paramFile == null) {
/* 1392 */       throw new NullPointerException();
/*      */     }
/* 1394 */     if (isInvalid() || paramFile.isInvalid()) {
/* 1395 */       return false;
/*      */     }
/* 1397 */     return fs.rename(this, paramFile);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setLastModified(long paramLong) {
/* 1427 */     if (paramLong < 0L) throw new IllegalArgumentException("Negative time"); 
/* 1428 */     SecurityManager securityManager = System.getSecurityManager();
/* 1429 */     if (securityManager != null) {
/* 1430 */       securityManager.checkWrite(this.path);
/*      */     }
/* 1432 */     if (isInvalid()) {
/* 1433 */       return false;
/*      */     }
/* 1435 */     return fs.setLastModifiedTime(this, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setReadOnly() {
/* 1458 */     SecurityManager securityManager = System.getSecurityManager();
/* 1459 */     if (securityManager != null) {
/* 1460 */       securityManager.checkWrite(this.path);
/*      */     }
/* 1462 */     if (isInvalid()) {
/* 1463 */       return false;
/*      */     }
/* 1465 */     return fs.setReadOnly(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setWritable(boolean paramBoolean1, boolean paramBoolean2) {
/* 1501 */     SecurityManager securityManager = System.getSecurityManager();
/* 1502 */     if (securityManager != null) {
/* 1503 */       securityManager.checkWrite(this.path);
/*      */     }
/* 1505 */     if (isInvalid()) {
/* 1506 */       return false;
/*      */     }
/* 1508 */     return fs.setPermission(this, 2, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setWritable(boolean paramBoolean) {
/* 1539 */     return setWritable(paramBoolean, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setReadable(boolean paramBoolean1, boolean paramBoolean2) {
/* 1578 */     SecurityManager securityManager = System.getSecurityManager();
/* 1579 */     if (securityManager != null) {
/* 1580 */       securityManager.checkWrite(this.path);
/*      */     }
/* 1582 */     if (isInvalid()) {
/* 1583 */       return false;
/*      */     }
/* 1585 */     return fs.setPermission(this, 4, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setReadable(boolean paramBoolean) {
/* 1619 */     return setReadable(paramBoolean, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setExecutable(boolean paramBoolean1, boolean paramBoolean2) {
/* 1658 */     SecurityManager securityManager = System.getSecurityManager();
/* 1659 */     if (securityManager != null) {
/* 1660 */       securityManager.checkWrite(this.path);
/*      */     }
/* 1662 */     if (isInvalid()) {
/* 1663 */       return false;
/*      */     }
/* 1665 */     return fs.setPermission(this, 1, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setExecutable(boolean paramBoolean) {
/* 1699 */     return setExecutable(paramBoolean, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canExecute() {
/* 1720 */     SecurityManager securityManager = System.getSecurityManager();
/* 1721 */     if (securityManager != null) {
/* 1722 */       securityManager.checkExec(this.path);
/*      */     }
/* 1724 */     if (isInvalid()) {
/* 1725 */       return false;
/*      */     }
/* 1727 */     return fs.checkAccess(this, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static File[] listRoots() {
/* 1776 */     return fs.listRoots();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getTotalSpace() {
/* 1798 */     SecurityManager securityManager = System.getSecurityManager();
/* 1799 */     if (securityManager != null) {
/* 1800 */       securityManager.checkPermission(new RuntimePermission("getFileSystemAttributes"));
/* 1801 */       securityManager.checkRead(this.path);
/*      */     } 
/* 1803 */     if (isInvalid()) {
/* 1804 */       return 0L;
/*      */     }
/* 1806 */     return fs.getSpace(this, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getFreeSpace() {
/* 1836 */     SecurityManager securityManager = System.getSecurityManager();
/* 1837 */     if (securityManager != null) {
/* 1838 */       securityManager.checkPermission(new RuntimePermission("getFileSystemAttributes"));
/* 1839 */       securityManager.checkRead(this.path);
/*      */     } 
/* 1841 */     if (isInvalid()) {
/* 1842 */       return 0L;
/*      */     }
/* 1844 */     return fs.getSpace(this, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getUsableSpace() {
/* 1877 */     SecurityManager securityManager = System.getSecurityManager();
/* 1878 */     if (securityManager != null) {
/* 1879 */       securityManager.checkPermission(new RuntimePermission("getFileSystemAttributes"));
/* 1880 */       securityManager.checkRead(this.path);
/*      */     } 
/* 1882 */     if (isInvalid()) {
/* 1883 */       return 0L;
/*      */     }
/* 1885 */     return fs.getSpace(this, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TempDirectory
/*      */   {
/* 1894 */     private static final File tmpdir = new File(
/* 1895 */         AccessController.<String>doPrivileged(new GetPropertyAction("java.io.tmpdir")));
/*      */     static File location() {
/* 1897 */       return tmpdir;
/*      */     }
/*      */ 
/*      */     
/* 1901 */     private static final SecureRandom random = new SecureRandom();
/*      */ 
/*      */     
/*      */     static File generateFile(String param1String1, String param1String2, File param1File) throws IOException {
/* 1905 */       long l = random.nextLong();
/* 1906 */       if (l == Long.MIN_VALUE) {
/* 1907 */         l = 0L;
/*      */       } else {
/* 1909 */         l = Math.abs(l);
/*      */       } 
/*      */ 
/*      */       
/* 1913 */       param1String1 = (new File(param1String1)).getName();
/*      */       
/* 1915 */       String str = param1String1 + Long.toString(l) + param1String2;
/* 1916 */       File file = new File(param1File, str);
/* 1917 */       if (!str.equals(file.getName()) || file.isInvalid()) {
/* 1918 */         if (System.getSecurityManager() != null) {
/* 1919 */           throw new IOException("Unable to create temporary file");
/*      */         }
/* 1921 */         throw new IOException("Unable to create temporary file, " + file);
/*      */       } 
/* 1923 */       return file;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static File createTempFile(String paramString1, String paramString2, File paramFile) throws IOException {
/*      */     File file2;
/* 2000 */     if (paramString1.length() < 3)
/* 2001 */       throw new IllegalArgumentException("Prefix string too short"); 
/* 2002 */     if (paramString2 == null) {
/* 2003 */       paramString2 = ".tmp";
/*      */     }
/*      */     
/* 2006 */     File file1 = (paramFile != null) ? paramFile : TempDirectory.location();
/* 2007 */     SecurityManager securityManager = System.getSecurityManager();
/*      */     
/*      */     do {
/* 2010 */       file2 = TempDirectory.generateFile(paramString1, paramString2, file1);
/*      */       
/* 2012 */       if (securityManager == null)
/*      */         continue;  try {
/* 2014 */         securityManager.checkWrite(file2.getPath());
/* 2015 */       } catch (SecurityException securityException) {
/*      */         
/* 2017 */         if (paramFile == null)
/* 2018 */           throw new SecurityException("Unable to create temporary file"); 
/* 2019 */         throw securityException;
/*      */       }
/*      */     
/* 2022 */     } while ((fs.getBooleanAttributes(file2) & 0x1) != 0);
/*      */     
/* 2024 */     if (!fs.createFileExclusively(file2.getPath())) {
/* 2025 */       throw new IOException("Unable to create temporary file");
/*      */     }
/* 2027 */     return file2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static File createTempFile(String paramString1, String paramString2) throws IOException {
/* 2070 */     return createTempFile(paramString1, paramString2, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(File paramFile) {
/* 2093 */     return fs.compare(this, paramFile);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/* 2111 */     if (paramObject != null && paramObject instanceof File) {
/* 2112 */       return (compareTo((File)paramObject) == 0);
/*      */     }
/* 2114 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2132 */     return fs.hashCode(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2142 */     return getPath();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 2155 */     paramObjectOutputStream.defaultWriteObject();
/* 2156 */     paramObjectOutputStream.writeChar(separatorChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 2168 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 2169 */     String str1 = (String)getField.get("path", (Object)null);
/* 2170 */     char c = paramObjectInputStream.readChar();
/* 2171 */     if (c != separatorChar)
/* 2172 */       str1 = str1.replace(c, separatorChar); 
/* 2173 */     String str2 = fs.normalize(str1);
/* 2174 */     UNSAFE.putObject(this, PATH_OFFSET, str2);
/* 2175 */     UNSAFE.putIntVolatile(this, PREFIX_LENGTH_OFFSET, fs.prefixLength(str2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/* 2183 */       Unsafe unsafe = Unsafe.getUnsafe();
/* 2184 */       PATH_OFFSET = unsafe.objectFieldOffset(File.class
/* 2185 */           .getDeclaredField("path"));
/* 2186 */       PREFIX_LENGTH_OFFSET = unsafe.objectFieldOffset(File.class
/* 2187 */           .getDeclaredField("prefixLength"));
/* 2188 */       UNSAFE = unsafe;
/* 2189 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 2190 */       throw new Error(reflectiveOperationException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Path toPath() {
/* 2229 */     Path path = this.filePath;
/* 2230 */     if (path == null) {
/* 2231 */       synchronized (this) {
/* 2232 */         path = this.filePath;
/* 2233 */         if (path == null) {
/* 2234 */           path = FileSystems.getDefault().getPath(this.path, new String[0]);
/* 2235 */           this.filePath = path;
/*      */         } 
/*      */       } 
/*      */     }
/* 2239 */     return path;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/File.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */