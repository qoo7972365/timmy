/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.ProviderMismatchException;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.nio.file.attribute.GroupPrincipal;
/*     */ import java.nio.file.attribute.PosixFileAttributeView;
/*     */ import java.nio.file.attribute.PosixFileAttributes;
/*     */ import java.nio.file.attribute.PosixFilePermission;
/*     */ import java.nio.file.attribute.UserPrincipal;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ class UnixFileAttributeViews
/*     */ {
/*     */   static class Basic
/*     */     extends AbstractBasicFileAttributeView
/*     */   {
/*     */     protected final UnixPath file;
/*     */     protected final boolean followLinks;
/*     */     
/*     */     Basic(UnixPath param1UnixPath, boolean param1Boolean) {
/*  43 */       this.file = param1UnixPath;
/*  44 */       this.followLinks = param1Boolean;
/*     */     }
/*     */ 
/*     */     
/*     */     public BasicFileAttributes readAttributes() throws IOException {
/*  49 */       this.file.checkRead();
/*     */       
/*     */       try {
/*  52 */         UnixFileAttributes unixFileAttributes = UnixFileAttributes.get(this.file, this.followLinks);
/*  53 */         return unixFileAttributes.asBasicFileAttributes();
/*  54 */       } catch (UnixException unixException) {
/*  55 */         unixException.rethrowAsIOException(this.file);
/*  56 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setTimes(FileTime param1FileTime1, FileTime param1FileTime2, FileTime param1FileTime3) throws IOException {
/*  66 */       if (param1FileTime1 == null && param1FileTime2 == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  72 */       this.file.checkWrite();
/*     */       
/*  74 */       int i = this.file.openForAttributeAccess(this.followLinks);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  79 */         if (param1FileTime1 == null || param1FileTime2 == null) {
/*     */           try {
/*  81 */             UnixFileAttributes unixFileAttributes = UnixFileAttributes.get(i);
/*  82 */             if (param1FileTime1 == null)
/*  83 */               param1FileTime1 = unixFileAttributes.lastModifiedTime(); 
/*  84 */             if (param1FileTime2 == null)
/*  85 */               param1FileTime2 = unixFileAttributes.lastAccessTime(); 
/*  86 */           } catch (UnixException unixException) {
/*  87 */             unixException.rethrowAsIOException(this.file);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*  92 */         long l1 = param1FileTime1.to(TimeUnit.MICROSECONDS);
/*  93 */         long l2 = param1FileTime2.to(TimeUnit.MICROSECONDS);
/*     */         
/*  95 */         boolean bool = false;
/*     */         try {
/*  97 */           if (UnixNativeDispatcher.futimesSupported()) {
/*  98 */             UnixNativeDispatcher.futimes(i, l2, l1);
/*     */           } else {
/* 100 */             UnixNativeDispatcher.utimes(this.file, l2, l1);
/*     */           } 
/* 102 */         } catch (UnixException unixException) {
/*     */ 
/*     */           
/* 105 */           if (unixException.errno() == 22 && (l1 < 0L || l2 < 0L)) {
/*     */             
/* 107 */             bool = true;
/*     */           } else {
/* 109 */             unixException.rethrowAsIOException(this.file);
/*     */           } 
/*     */         } 
/* 112 */         if (bool) {
/* 113 */           if (l1 < 0L) l1 = 0L; 
/* 114 */           if (l2 < 0L) l2 = 0L; 
/*     */           try {
/* 116 */             if (UnixNativeDispatcher.futimesSupported()) {
/* 117 */               UnixNativeDispatcher.futimes(i, l2, l1);
/*     */             } else {
/* 119 */               UnixNativeDispatcher.utimes(this.file, l2, l1);
/*     */             } 
/* 121 */           } catch (UnixException unixException) {
/* 122 */             unixException.rethrowAsIOException(this.file);
/*     */           } 
/*     */         } 
/*     */       } finally {
/* 126 */         UnixNativeDispatcher.close(i);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Posix
/*     */     extends Basic
/*     */     implements PosixFileAttributeView
/*     */   {
/*     */     private static final String PERMISSIONS_NAME = "permissions";
/*     */     private static final String OWNER_NAME = "owner";
/*     */     private static final String GROUP_NAME = "group";
/* 138 */     static final Set<String> posixAttributeNames = Util.newSet(basicAttributeNames, new String[] { "permissions", "owner", "group" });
/*     */     
/*     */     Posix(UnixPath param1UnixPath, boolean param1Boolean) {
/* 141 */       super(param1UnixPath, param1Boolean);
/*     */     }
/*     */     
/*     */     final void checkReadExtended() {
/* 145 */       SecurityManager securityManager = System.getSecurityManager();
/* 146 */       if (securityManager != null) {
/* 147 */         this.file.checkRead();
/* 148 */         securityManager.checkPermission(new RuntimePermission("accessUserInformation"));
/*     */       } 
/*     */     }
/*     */     
/*     */     final void checkWriteExtended() {
/* 153 */       SecurityManager securityManager = System.getSecurityManager();
/* 154 */       if (securityManager != null) {
/* 155 */         this.file.checkWrite();
/* 156 */         securityManager.checkPermission(new RuntimePermission("accessUserInformation"));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String name() {
/* 162 */       return "posix";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setAttribute(String param1String, Object param1Object) throws IOException {
/* 170 */       if (param1String.equals("permissions")) {
/* 171 */         setPermissions((Set<PosixFilePermission>)param1Object);
/*     */         return;
/*     */       } 
/* 174 */       if (param1String.equals("owner")) {
/* 175 */         setOwner((UserPrincipal)param1Object);
/*     */         return;
/*     */       } 
/* 178 */       if (param1String.equals("group")) {
/* 179 */         setGroup((GroupPrincipal)param1Object);
/*     */         return;
/*     */       } 
/* 182 */       super.setAttribute(param1String, param1Object);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final void addRequestedPosixAttributes(PosixFileAttributes param1PosixFileAttributes, AbstractBasicFileAttributeView.AttributesBuilder param1AttributesBuilder) {
/* 192 */       addRequestedBasicAttributes(param1PosixFileAttributes, param1AttributesBuilder);
/* 193 */       if (param1AttributesBuilder.match("permissions"))
/* 194 */         param1AttributesBuilder.add("permissions", param1PosixFileAttributes.permissions()); 
/* 195 */       if (param1AttributesBuilder.match("owner"))
/* 196 */         param1AttributesBuilder.add("owner", param1PosixFileAttributes.owner()); 
/* 197 */       if (param1AttributesBuilder.match("group")) {
/* 198 */         param1AttributesBuilder.add("group", param1PosixFileAttributes.group());
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<String, Object> readAttributes(String[] param1ArrayOfString) throws IOException {
/* 206 */       AbstractBasicFileAttributeView.AttributesBuilder attributesBuilder = AbstractBasicFileAttributeView.AttributesBuilder.create(posixAttributeNames, param1ArrayOfString);
/* 207 */       UnixFileAttributes unixFileAttributes = readAttributes();
/* 208 */       addRequestedPosixAttributes(unixFileAttributes, attributesBuilder);
/* 209 */       return attributesBuilder.unmodifiableMap();
/*     */     }
/*     */ 
/*     */     
/*     */     public UnixFileAttributes readAttributes() throws IOException {
/* 214 */       checkReadExtended();
/*     */       try {
/* 216 */         return UnixFileAttributes.get(this.file, this.followLinks);
/* 217 */       } catch (UnixException unixException) {
/* 218 */         unixException.rethrowAsIOException(this.file);
/* 219 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     final void setMode(int param1Int) throws IOException {
/* 225 */       checkWriteExtended();
/*     */       try {
/* 227 */         if (this.followLinks) {
/* 228 */           UnixNativeDispatcher.chmod(this.file, param1Int);
/*     */         } else {
/* 230 */           int i = this.file.openForAttributeAccess(false);
/*     */           try {
/* 232 */             UnixNativeDispatcher.fchmod(i, param1Int);
/*     */           } finally {
/* 234 */             UnixNativeDispatcher.close(i);
/*     */           } 
/*     */         } 
/* 237 */       } catch (UnixException unixException) {
/* 238 */         unixException.rethrowAsIOException(this.file);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     final void setOwners(int param1Int1, int param1Int2) throws IOException {
/* 244 */       checkWriteExtended();
/*     */       try {
/* 246 */         if (this.followLinks) {
/* 247 */           UnixNativeDispatcher.chown(this.file, param1Int1, param1Int2);
/*     */         } else {
/* 249 */           UnixNativeDispatcher.lchown(this.file, param1Int1, param1Int2);
/*     */         } 
/* 251 */       } catch (UnixException unixException) {
/* 252 */         unixException.rethrowAsIOException(this.file);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPermissions(Set<PosixFilePermission> param1Set) throws IOException {
/* 260 */       setMode(UnixFileModeAttribute.toUnixMode(param1Set));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setOwner(UserPrincipal param1UserPrincipal) throws IOException {
/* 267 */       if (param1UserPrincipal == null)
/* 268 */         throw new NullPointerException("'owner' is null"); 
/* 269 */       if (!(param1UserPrincipal instanceof UnixUserPrincipals.User))
/* 270 */         throw new ProviderMismatchException(); 
/* 271 */       if (param1UserPrincipal instanceof UnixUserPrincipals.Group)
/* 272 */         throw new IOException("'owner' parameter can't be a group"); 
/* 273 */       int i = ((UnixUserPrincipals.User)param1UserPrincipal).uid();
/* 274 */       setOwners(i, -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public UserPrincipal getOwner() throws IOException {
/* 279 */       return readAttributes().owner();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setGroup(GroupPrincipal param1GroupPrincipal) throws IOException {
/* 286 */       if (param1GroupPrincipal == null)
/* 287 */         throw new NullPointerException("'owner' is null"); 
/* 288 */       if (!(param1GroupPrincipal instanceof UnixUserPrincipals.Group))
/* 289 */         throw new ProviderMismatchException(); 
/* 290 */       int i = ((UnixUserPrincipals.Group)param1GroupPrincipal).gid();
/* 291 */       setOwners(-1, i);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Unix
/*     */     extends Posix
/*     */   {
/*     */     private static final String MODE_NAME = "mode";
/*     */     private static final String INO_NAME = "ino";
/*     */     private static final String DEV_NAME = "dev";
/*     */     private static final String RDEV_NAME = "rdev";
/*     */     private static final String NLINK_NAME = "nlink";
/*     */     private static final String UID_NAME = "uid";
/*     */     private static final String GID_NAME = "gid";
/*     */     private static final String CTIME_NAME = "ctime";
/* 307 */     static final Set<String> unixAttributeNames = Util.newSet(posixAttributeNames, new String[] { "mode", "ino", "dev", "rdev", "nlink", "uid", "gid", "ctime" });
/*     */ 
/*     */ 
/*     */     
/*     */     Unix(UnixPath param1UnixPath, boolean param1Boolean) {
/* 312 */       super(param1UnixPath, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     public String name() {
/* 317 */       return "unix";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setAttribute(String param1String, Object param1Object) throws IOException {
/* 324 */       if (param1String.equals("mode")) {
/* 325 */         setMode(((Integer)param1Object).intValue());
/*     */         return;
/*     */       } 
/* 328 */       if (param1String.equals("uid")) {
/* 329 */         setOwners(((Integer)param1Object).intValue(), -1);
/*     */         return;
/*     */       } 
/* 332 */       if (param1String.equals("gid")) {
/* 333 */         setOwners(-1, ((Integer)param1Object).intValue());
/*     */         return;
/*     */       } 
/* 336 */       super.setAttribute(param1String, param1Object);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<String, Object> readAttributes(String[] param1ArrayOfString) throws IOException {
/* 344 */       AbstractBasicFileAttributeView.AttributesBuilder attributesBuilder = AbstractBasicFileAttributeView.AttributesBuilder.create(unixAttributeNames, param1ArrayOfString);
/* 345 */       UnixFileAttributes unixFileAttributes = readAttributes();
/* 346 */       addRequestedPosixAttributes(unixFileAttributes, attributesBuilder);
/* 347 */       if (attributesBuilder.match("mode"))
/* 348 */         attributesBuilder.add("mode", Integer.valueOf(unixFileAttributes.mode())); 
/* 349 */       if (attributesBuilder.match("ino"))
/* 350 */         attributesBuilder.add("ino", Long.valueOf(unixFileAttributes.ino())); 
/* 351 */       if (attributesBuilder.match("dev"))
/* 352 */         attributesBuilder.add("dev", Long.valueOf(unixFileAttributes.dev())); 
/* 353 */       if (attributesBuilder.match("rdev"))
/* 354 */         attributesBuilder.add("rdev", Long.valueOf(unixFileAttributes.rdev())); 
/* 355 */       if (attributesBuilder.match("nlink"))
/* 356 */         attributesBuilder.add("nlink", Integer.valueOf(unixFileAttributes.nlink())); 
/* 357 */       if (attributesBuilder.match("uid"))
/* 358 */         attributesBuilder.add("uid", Integer.valueOf(unixFileAttributes.uid())); 
/* 359 */       if (attributesBuilder.match("gid"))
/* 360 */         attributesBuilder.add("gid", Integer.valueOf(unixFileAttributes.gid())); 
/* 361 */       if (attributesBuilder.match("ctime"))
/* 362 */         attributesBuilder.add("ctime", unixFileAttributes.ctime()); 
/* 363 */       return attributesBuilder.unmodifiableMap();
/*     */     }
/*     */   }
/*     */   
/*     */   static Basic createBasicView(UnixPath paramUnixPath, boolean paramBoolean) {
/* 368 */     return new Basic(paramUnixPath, paramBoolean);
/*     */   }
/*     */   
/*     */   static Posix createPosixView(UnixPath paramUnixPath, boolean paramBoolean) {
/* 372 */     return new Posix(paramUnixPath, paramBoolean);
/*     */   }
/*     */   
/*     */   static Unix createUnixView(UnixPath paramUnixPath, boolean paramBoolean) {
/* 376 */     return new Unix(paramUnixPath, paramBoolean);
/*     */   }
/*     */   
/*     */   static FileOwnerAttributeViewImpl createOwnerView(UnixPath paramUnixPath, boolean paramBoolean) {
/* 380 */     return new FileOwnerAttributeViewImpl(createPosixView(paramUnixPath, paramBoolean));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixFileAttributeViews.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */