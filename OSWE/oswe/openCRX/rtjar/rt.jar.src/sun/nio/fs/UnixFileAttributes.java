/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.nio.file.attribute.GroupPrincipal;
/*     */ import java.nio.file.attribute.PosixFileAttributes;
/*     */ import java.nio.file.attribute.PosixFilePermission;
/*     */ import java.nio.file.attribute.UserPrincipal;
/*     */ import java.util.HashSet;
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
/*     */ class UnixFileAttributes
/*     */   implements PosixFileAttributes
/*     */ {
/*     */   private int st_mode;
/*     */   private long st_ino;
/*     */   private long st_dev;
/*     */   private long st_rdev;
/*     */   private int st_nlink;
/*     */   private int st_uid;
/*     */   private int st_gid;
/*     */   private long st_size;
/*     */   private long st_atime_sec;
/*     */   private long st_atime_nsec;
/*     */   private long st_mtime_sec;
/*     */   private long st_mtime_nsec;
/*     */   private long st_ctime_sec;
/*     */   private long st_ctime_nsec;
/*     */   private long st_birthtime_sec;
/*     */   private volatile UserPrincipal owner;
/*     */   private volatile GroupPrincipal group;
/*     */   private volatile UnixFileKey key;
/*     */   
/*     */   static UnixFileAttributes get(UnixPath paramUnixPath, boolean paramBoolean) throws UnixException {
/*  68 */     UnixFileAttributes unixFileAttributes = new UnixFileAttributes();
/*  69 */     if (paramBoolean) {
/*  70 */       UnixNativeDispatcher.stat(paramUnixPath, unixFileAttributes);
/*     */     } else {
/*  72 */       UnixNativeDispatcher.lstat(paramUnixPath, unixFileAttributes);
/*     */     } 
/*  74 */     return unixFileAttributes;
/*     */   }
/*     */ 
/*     */   
/*     */   static UnixFileAttributes get(int paramInt) throws UnixException {
/*  79 */     UnixFileAttributes unixFileAttributes = new UnixFileAttributes();
/*  80 */     UnixNativeDispatcher.fstat(paramInt, unixFileAttributes);
/*  81 */     return unixFileAttributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static UnixFileAttributes get(int paramInt, UnixPath paramUnixPath, boolean paramBoolean) throws UnixException {
/*  88 */     UnixFileAttributes unixFileAttributes = new UnixFileAttributes();
/*  89 */     boolean bool = paramBoolean ? false : true;
/*  90 */     UnixNativeDispatcher.fstatat(paramInt, paramUnixPath.asByteArray(), bool, unixFileAttributes);
/*  91 */     return unixFileAttributes;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isSameFile(UnixFileAttributes paramUnixFileAttributes) {
/*  96 */     return (this.st_ino == paramUnixFileAttributes.st_ino && this.st_dev == paramUnixFileAttributes.st_dev);
/*     */   }
/*     */   
/*     */   int mode() {
/* 100 */     return this.st_mode;
/* 101 */   } long ino() { return this.st_ino; }
/* 102 */   long dev() { return this.st_dev; }
/* 103 */   long rdev() { return this.st_rdev; }
/* 104 */   int nlink() { return this.st_nlink; }
/* 105 */   int uid() { return this.st_uid; } int gid() {
/* 106 */     return this.st_gid;
/*     */   }
/*     */   private static FileTime toFileTime(long paramLong1, long paramLong2) {
/* 109 */     if (paramLong2 == 0L) {
/* 110 */       return FileTime.from(paramLong1, TimeUnit.SECONDS);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 115 */     long l = paramLong1 * 1000000L + paramLong2 / 1000L;
/* 116 */     return FileTime.from(l, TimeUnit.MICROSECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   FileTime ctime() {
/* 121 */     return toFileTime(this.st_ctime_sec, this.st_ctime_nsec);
/*     */   }
/*     */   
/*     */   boolean isDevice() {
/* 125 */     int i = this.st_mode & 0xF000;
/* 126 */     return (i == 8192 || i == 24576 || i == 4096);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileTime lastModifiedTime() {
/* 133 */     return toFileTime(this.st_mtime_sec, this.st_mtime_nsec);
/*     */   }
/*     */ 
/*     */   
/*     */   public FileTime lastAccessTime() {
/* 138 */     return toFileTime(this.st_atime_sec, this.st_atime_nsec);
/*     */   }
/*     */ 
/*     */   
/*     */   public FileTime creationTime() {
/* 143 */     if (UnixNativeDispatcher.birthtimeSupported()) {
/* 144 */       return FileTime.from(this.st_birthtime_sec, TimeUnit.SECONDS);
/*     */     }
/*     */     
/* 147 */     return lastModifiedTime();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRegularFile() {
/* 153 */     return ((this.st_mode & 0xF000) == 32768);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirectory() {
/* 158 */     return ((this.st_mode & 0xF000) == 16384);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSymbolicLink() {
/* 163 */     return ((this.st_mode & 0xF000) == 40960);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOther() {
/* 168 */     int i = this.st_mode & 0xF000;
/* 169 */     return (i != 32768 && i != 16384 && i != 40960);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long size() {
/* 176 */     return this.st_size;
/*     */   }
/*     */ 
/*     */   
/*     */   public UnixFileKey fileKey() {
/* 181 */     if (this.key == null) {
/* 182 */       synchronized (this) {
/* 183 */         if (this.key == null) {
/* 184 */           this.key = new UnixFileKey(this.st_dev, this.st_ino);
/*     */         }
/*     */       } 
/*     */     }
/* 188 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public UserPrincipal owner() {
/* 193 */     if (this.owner == null) {
/* 194 */       synchronized (this) {
/* 195 */         if (this.owner == null) {
/* 196 */           this.owner = UnixUserPrincipals.fromUid(this.st_uid);
/*     */         }
/*     */       } 
/*     */     }
/* 200 */     return this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public GroupPrincipal group() {
/* 205 */     if (this.group == null) {
/* 206 */       synchronized (this) {
/* 207 */         if (this.group == null) {
/* 208 */           this.group = UnixUserPrincipals.fromGid(this.st_gid);
/*     */         }
/*     */       } 
/*     */     }
/* 212 */     return this.group;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<PosixFilePermission> permissions() {
/* 217 */     int i = this.st_mode & 0x1FF;
/* 218 */     HashSet<PosixFilePermission> hashSet = new HashSet();
/*     */     
/* 220 */     if ((i & 0x100) > 0)
/* 221 */       hashSet.add(PosixFilePermission.OWNER_READ); 
/* 222 */     if ((i & 0x80) > 0)
/* 223 */       hashSet.add(PosixFilePermission.OWNER_WRITE); 
/* 224 */     if ((i & 0x40) > 0) {
/* 225 */       hashSet.add(PosixFilePermission.OWNER_EXECUTE);
/*     */     }
/* 227 */     if ((i & 0x20) > 0)
/* 228 */       hashSet.add(PosixFilePermission.GROUP_READ); 
/* 229 */     if ((i & 0x10) > 0)
/* 230 */       hashSet.add(PosixFilePermission.GROUP_WRITE); 
/* 231 */     if ((i & 0x8) > 0) {
/* 232 */       hashSet.add(PosixFilePermission.GROUP_EXECUTE);
/*     */     }
/* 234 */     if ((i & 0x4) > 0)
/* 235 */       hashSet.add(PosixFilePermission.OTHERS_READ); 
/* 236 */     if ((i & 0x2) > 0)
/* 237 */       hashSet.add(PosixFilePermission.OTHERS_WRITE); 
/* 238 */     if ((i & 0x1) > 0) {
/* 239 */       hashSet.add(PosixFilePermission.OTHERS_EXECUTE);
/*     */     }
/* 241 */     return hashSet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   BasicFileAttributes asBasicFileAttributes() {
/* 247 */     return UnixAsBasicFileAttributes.wrap(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static UnixFileAttributes toUnixFileAttributes(BasicFileAttributes paramBasicFileAttributes) {
/* 253 */     if (paramBasicFileAttributes instanceof UnixFileAttributes)
/* 254 */       return (UnixFileAttributes)paramBasicFileAttributes; 
/* 255 */     if (paramBasicFileAttributes instanceof UnixAsBasicFileAttributes) {
/* 256 */       return ((UnixAsBasicFileAttributes)paramBasicFileAttributes).unwrap();
/*     */     }
/* 258 */     return null;
/*     */   }
/*     */   
/*     */   private static class UnixAsBasicFileAttributes
/*     */     implements BasicFileAttributes {
/*     */     private final UnixFileAttributes attrs;
/*     */     
/*     */     private UnixAsBasicFileAttributes(UnixFileAttributes param1UnixFileAttributes) {
/* 266 */       this.attrs = param1UnixFileAttributes;
/*     */     }
/*     */     
/*     */     static UnixAsBasicFileAttributes wrap(UnixFileAttributes param1UnixFileAttributes) {
/* 270 */       return new UnixAsBasicFileAttributes(param1UnixFileAttributes);
/*     */     }
/*     */     
/*     */     UnixFileAttributes unwrap() {
/* 274 */       return this.attrs;
/*     */     }
/*     */ 
/*     */     
/*     */     public FileTime lastModifiedTime() {
/* 279 */       return this.attrs.lastModifiedTime();
/*     */     }
/*     */     
/*     */     public FileTime lastAccessTime() {
/* 283 */       return this.attrs.lastAccessTime();
/*     */     }
/*     */     
/*     */     public FileTime creationTime() {
/* 287 */       return this.attrs.creationTime();
/*     */     }
/*     */     
/*     */     public boolean isRegularFile() {
/* 291 */       return this.attrs.isRegularFile();
/*     */     }
/*     */     
/*     */     public boolean isDirectory() {
/* 295 */       return this.attrs.isDirectory();
/*     */     }
/*     */     
/*     */     public boolean isSymbolicLink() {
/* 299 */       return this.attrs.isSymbolicLink();
/*     */     }
/*     */     
/*     */     public boolean isOther() {
/* 303 */       return this.attrs.isOther();
/*     */     }
/*     */     
/*     */     public long size() {
/* 307 */       return this.attrs.size();
/*     */     }
/*     */     
/*     */     public Object fileKey() {
/* 311 */       return this.attrs.fileKey();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixFileAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */