/*     */ package java.nio.file;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.nio.file.attribute.PosixFilePermission;
/*     */ import java.nio.file.attribute.PosixFilePermissions;
/*     */ import java.security.AccessController;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
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
/*     */ class TempFileHelper
/*     */ {
/*  50 */   private static final Path tmpdir = Paths.get(AccessController.<String>doPrivileged(new GetPropertyAction("java.io.tmpdir")), new String[0]);
/*     */ 
/*     */   
/*  53 */   private static final boolean isPosix = FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
/*     */ 
/*     */   
/*  56 */   private static final SecureRandom random = new SecureRandom();
/*     */   private static Path generatePath(String paramString1, String paramString2, Path paramPath) {
/*  58 */     long l = random.nextLong();
/*  59 */     l = (l == Long.MIN_VALUE) ? 0L : Math.abs(l);
/*  60 */     Path path = paramPath.getFileSystem().getPath(paramString1 + Long.toString(l) + paramString2, new String[0]);
/*     */     
/*  62 */     if (path.getParent() != null)
/*  63 */       throw new IllegalArgumentException("Invalid prefix or suffix"); 
/*  64 */     return paramPath.resolve(path);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class PosixPermissions
/*     */   {
/*  70 */     static final FileAttribute<Set<PosixFilePermission>> filePermissions = PosixFilePermissions.asFileAttribute(EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE));
/*     */     
/*  72 */     static final FileAttribute<Set<PosixFilePermission>> dirPermissions = PosixFilePermissions.asFileAttribute(
/*  73 */         EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE));
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
/*     */   private static Path create(Path paramPath, String paramString1, String paramString2, boolean paramBoolean, FileAttribute<?>[] paramArrayOfFileAttribute) throws IOException {
/*     */     FileAttribute[] arrayOfFileAttribute;
/*  87 */     if (paramString1 == null)
/*  88 */       paramString1 = ""; 
/*  89 */     if (paramString2 == null)
/*  90 */       paramString2 = paramBoolean ? "" : ".tmp"; 
/*  91 */     if (paramPath == null) {
/*  92 */       paramPath = tmpdir;
/*     */     }
/*     */ 
/*     */     
/*  96 */     if (isPosix && paramPath.getFileSystem() == FileSystems.getDefault()) {
/*  97 */       if (paramArrayOfFileAttribute.length == 0) {
/*     */         
/*  99 */         arrayOfFileAttribute = new FileAttribute[1];
/* 100 */         arrayOfFileAttribute[0] = paramBoolean ? PosixPermissions.dirPermissions : PosixPermissions.filePermissions;
/*     */       }
/*     */       else {
/*     */         
/* 104 */         boolean bool = false;
/* 105 */         for (byte b = 0; b < arrayOfFileAttribute.length; b++) {
/* 106 */           if (arrayOfFileAttribute[b].name().equals("posix:permissions")) {
/* 107 */             bool = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 111 */         if (!bool) {
/* 112 */           FileAttribute[] arrayOfFileAttribute1 = new FileAttribute[arrayOfFileAttribute.length + 1];
/* 113 */           System.arraycopy(arrayOfFileAttribute, 0, arrayOfFileAttribute1, 0, arrayOfFileAttribute.length);
/* 114 */           arrayOfFileAttribute = arrayOfFileAttribute1;
/* 115 */           arrayOfFileAttribute[arrayOfFileAttribute.length - 1] = paramBoolean ? PosixPermissions.dirPermissions : PosixPermissions.filePermissions;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     SecurityManager securityManager = System.getSecurityManager();
/*     */     while (true) {
/*     */       Path path;
/*     */       try {
/* 127 */         path = generatePath(paramString1, paramString2, paramPath);
/* 128 */       } catch (InvalidPathException invalidPathException) {
/*     */         
/* 130 */         if (securityManager != null)
/* 131 */           throw new IllegalArgumentException("Invalid prefix or suffix"); 
/* 132 */         throw invalidPathException;
/*     */       } 
/*     */       try {
/* 135 */         if (paramBoolean) {
/* 136 */           return Files.createDirectory(path, (FileAttribute<?>[])arrayOfFileAttribute);
/*     */         }
/* 138 */         return Files.createFile(path, (FileAttribute<?>[])arrayOfFileAttribute);
/*     */       }
/* 140 */       catch (SecurityException securityException) {
/*     */         
/* 142 */         if (paramPath == tmpdir && securityManager != null)
/* 143 */           throw new SecurityException("Unable to create temporary file or directory"); 
/* 144 */         throw securityException;
/* 145 */       } catch (FileAlreadyExistsException fileAlreadyExistsException) {}
/*     */     } 
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
/*     */   static Path createTempFile(Path paramPath, String paramString1, String paramString2, FileAttribute<?>[] paramArrayOfFileAttribute) throws IOException {
/* 161 */     return create(paramPath, paramString1, paramString2, false, paramArrayOfFileAttribute);
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
/*     */   static Path createTempDirectory(Path paramPath, String paramString, FileAttribute<?>[] paramArrayOfFileAttribute) throws IOException {
/* 173 */     return create(paramPath, paramString, null, true, paramArrayOfFileAttribute);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/TempFileHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */