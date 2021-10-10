/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.FileStore;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.PathMatcher;
/*     */ import java.nio.file.attribute.GroupPrincipal;
/*     */ import java.nio.file.attribute.UserPrincipal;
/*     */ import java.nio.file.attribute.UserPrincipalLookupService;
/*     */ import java.nio.file.spi.FileSystemProvider;
/*     */ import java.security.AccessController;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.regex.Pattern;
/*     */ import sun.security.action.GetPropertyAction;
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
/*     */ abstract class UnixFileSystem
/*     */   extends FileSystem
/*     */ {
/*     */   private final UnixFileSystemProvider provider;
/*     */   private final byte[] defaultDirectory;
/*     */   private final boolean needToResolveAgainstDefaultDirectory;
/*     */   private final UnixPath rootDirectory;
/*     */   private static final String GLOB_SYNTAX = "glob";
/*     */   private static final String REGEX_SYNTAX = "regex";
/*     */   
/*     */   UnixFileSystem(UnixFileSystemProvider paramUnixFileSystemProvider, String paramString) {
/*  51 */     this.provider = paramUnixFileSystemProvider;
/*  52 */     this.defaultDirectory = Util.toBytes(UnixPath.normalizeAndCheck(paramString));
/*  53 */     if (this.defaultDirectory[0] != 47) {
/*  54 */       throw new RuntimeException("default directory must be absolute");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.nio.fs.chdirAllowed", "false"));
/*     */ 
/*     */     
/*  63 */     boolean bool = (str.length() == 0) ? true : Boolean.valueOf(str).booleanValue();
/*  64 */     if (bool) {
/*  65 */       this.needToResolveAgainstDefaultDirectory = true;
/*     */     } else {
/*  67 */       byte[] arrayOfByte = UnixNativeDispatcher.getcwd();
/*  68 */       boolean bool1 = (arrayOfByte.length == this.defaultDirectory.length) ? true : false;
/*  69 */       if (bool1) {
/*  70 */         for (byte b = 0; b < arrayOfByte.length; b++) {
/*  71 */           if (arrayOfByte[b] != this.defaultDirectory[b]) {
/*  72 */             bool1 = false;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*  77 */       this.needToResolveAgainstDefaultDirectory = !bool1;
/*     */     } 
/*     */ 
/*     */     
/*  81 */     this.rootDirectory = new UnixPath(this, "/");
/*     */   }
/*     */ 
/*     */   
/*     */   byte[] defaultDirectory() {
/*  86 */     return this.defaultDirectory;
/*     */   }
/*     */   
/*     */   boolean needToResolveAgainstDefaultDirectory() {
/*  90 */     return this.needToResolveAgainstDefaultDirectory;
/*     */   }
/*     */   
/*     */   UnixPath rootDirectory() {
/*  94 */     return this.rootDirectory;
/*     */   }
/*     */   
/*     */   boolean isSolaris() {
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   static List<String> standardFileAttributeViews() {
/* 102 */     return Arrays.asList(new String[] { "basic", "posix", "unix", "owner" });
/*     */   }
/*     */ 
/*     */   
/*     */   public final FileSystemProvider provider() {
/* 107 */     return this.provider;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getSeparator() {
/* 112 */     return "/";
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isOpen() {
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isReadOnly() {
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void close() throws IOException {
/* 127 */     throw new UnsupportedOperationException();
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
/*     */ 
/*     */   
/*     */   void copyNonPosixAttributes(int paramInt1, int paramInt2) {}
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
/*     */   public final Iterable<Path> getRootDirectories() {
/* 154 */     final List<?> allowedList = Collections.unmodifiableList(Arrays.asList((Object[])new Path[] { this.rootDirectory }));
/* 155 */     return new Iterable<Path>() {
/*     */         public Iterator<Path> iterator() {
/*     */           try {
/* 158 */             SecurityManager securityManager = System.getSecurityManager();
/* 159 */             if (securityManager != null)
/* 160 */               securityManager.checkRead(UnixFileSystem.this.rootDirectory.toString()); 
/* 161 */             return allowedList.iterator();
/* 162 */           } catch (SecurityException securityException) {
/* 163 */             List<?> list = Collections.emptyList();
/* 164 */             return (Iterator)list.iterator();
/*     */           } 
/*     */         }
/*     */       };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class FileStoreIterator
/*     */     implements Iterator<FileStore>
/*     */   {
/* 189 */     private final Iterator<UnixMountEntry> entries = UnixFileSystem.this.getMountEntries().iterator();
/*     */     private FileStore next;
/*     */     
/*     */     private FileStore readNext() {
/* 193 */       assert Thread.holdsLock(this);
/*     */       while (true) {
/* 195 */         if (!this.entries.hasNext())
/* 196 */           return null; 
/* 197 */         UnixMountEntry unixMountEntry = this.entries.next();
/*     */ 
/*     */         
/* 200 */         if (unixMountEntry.isIgnored()) {
/*     */           continue;
/*     */         }
/*     */         
/* 204 */         SecurityManager securityManager = System.getSecurityManager();
/* 205 */         if (securityManager != null) {
/*     */           try {
/* 207 */             securityManager.checkRead(Util.toString(unixMountEntry.dir()));
/* 208 */           } catch (SecurityException securityException) {
/*     */             continue;
/*     */           } 
/*     */         }
/*     */         try {
/* 213 */           return UnixFileSystem.this.getFileStore(unixMountEntry);
/* 214 */         } catch (IOException iOException) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized boolean hasNext() {
/* 222 */       if (this.next != null)
/* 223 */         return true; 
/* 224 */       this.next = readNext();
/* 225 */       return (this.next != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized FileStore next() {
/* 230 */       if (this.next == null)
/* 231 */         this.next = readNext(); 
/* 232 */       if (this.next == null) {
/* 233 */         throw new NoSuchElementException();
/*     */       }
/* 235 */       FileStore fileStore = this.next;
/* 236 */       this.next = null;
/* 237 */       return fileStore;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 243 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final Iterable<FileStore> getFileStores() {
/* 249 */     SecurityManager securityManager = System.getSecurityManager();
/* 250 */     if (securityManager != null) {
/*     */       try {
/* 252 */         securityManager.checkPermission(new RuntimePermission("getFileStoreAttributes"));
/* 253 */       } catch (SecurityException securityException) {
/* 254 */         return Collections.emptyList();
/*     */       } 
/*     */     }
/* 257 */     return new Iterable<FileStore>() {
/*     */         public Iterator<FileStore> iterator() {
/* 259 */           return new UnixFileSystem.FileStoreIterator();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final Path getPath(String paramString, String... paramVarArgs) {
/*     */     String str;
/* 267 */     if (paramVarArgs.length == 0) {
/* 268 */       str = paramString;
/*     */     } else {
/* 270 */       StringBuilder stringBuilder = new StringBuilder();
/* 271 */       stringBuilder.append(paramString);
/* 272 */       for (String str1 : paramVarArgs) {
/* 273 */         if (str1.length() > 0) {
/* 274 */           if (stringBuilder.length() > 0)
/* 275 */             stringBuilder.append('/'); 
/* 276 */           stringBuilder.append(str1);
/*     */         } 
/*     */       } 
/* 279 */       str = stringBuilder.toString();
/*     */     } 
/* 281 */     return new UnixPath(this, str);
/*     */   }
/*     */   
/*     */   public PathMatcher getPathMatcher(String paramString) {
/*     */     String str3;
/* 286 */     int i = paramString.indexOf(':');
/* 287 */     if (i <= 0 || i == paramString.length())
/* 288 */       throw new IllegalArgumentException(); 
/* 289 */     String str1 = paramString.substring(0, i);
/* 290 */     String str2 = paramString.substring(i + 1);
/*     */ 
/*     */     
/* 293 */     if (str1.equals("glob")) {
/* 294 */       str3 = Globs.toUnixRegexPattern(str2);
/*     */     }
/* 296 */     else if (str1.equals("regex")) {
/* 297 */       str3 = str2;
/*     */     } else {
/* 299 */       throw new UnsupportedOperationException("Syntax '" + str1 + "' not recognized");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     final Pattern pattern = compilePathMatchPattern(str3);
/*     */     
/* 307 */     return new PathMatcher()
/*     */       {
/*     */         public boolean matches(Path param1Path) {
/* 310 */           return pattern.matcher(param1Path.toString()).matches();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final UserPrincipalLookupService getUserPrincipalLookupService() {
/* 320 */     return LookupService.instance;
/*     */   }
/*     */   
/*     */   private static class LookupService {
/* 324 */     static final UserPrincipalLookupService instance = new UserPrincipalLookupService()
/*     */       {
/*     */ 
/*     */         
/*     */         public UserPrincipal lookupPrincipalByName(String param2String) throws IOException
/*     */         {
/* 330 */           return UnixUserPrincipals.lookupUser(param2String);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public GroupPrincipal lookupPrincipalByGroupName(String param2String) throws IOException {
/* 337 */           return UnixUserPrincipals.lookupGroup(param2String);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Pattern compilePathMatchPattern(String paramString) {
/* 345 */     return Pattern.compile(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   char[] normalizeNativePath(char[] paramArrayOfchar) {
/* 352 */     return paramArrayOfchar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String normalizeJavaPath(String paramString) {
/* 359 */     return paramString;
/*     */   }
/*     */   
/*     */   abstract Iterable<UnixMountEntry> getMountEntries();
/*     */   
/*     */   abstract FileStore getFileStore(UnixMountEntry paramUnixMountEntry) throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixFileSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */