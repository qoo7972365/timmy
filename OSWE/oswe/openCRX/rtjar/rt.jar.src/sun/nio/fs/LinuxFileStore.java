/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.attribute.DosFileAttributeView;
/*     */ import java.nio.file.attribute.FileAttributeView;
/*     */ import java.nio.file.attribute.PosixFileAttributeView;
/*     */ import java.nio.file.attribute.UserDefinedFileAttributeView;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ class LinuxFileStore
/*     */   extends UnixFileStore
/*     */ {
/*     */   private volatile boolean xattrChecked;
/*     */   private volatile boolean xattrEnabled;
/*     */   
/*     */   LinuxFileStore(UnixPath paramUnixPath) throws IOException {
/*  44 */     super(paramUnixPath);
/*     */   }
/*     */   
/*     */   LinuxFileStore(UnixFileSystem paramUnixFileSystem, UnixMountEntry paramUnixMountEntry) throws IOException {
/*  48 */     super(paramUnixFileSystem, paramUnixMountEntry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   UnixMountEntry findMountEntry() throws IOException {
/*  57 */     LinuxFileSystem linuxFileSystem = (LinuxFileSystem)file().getFileSystem();
/*     */ 
/*     */     
/*  60 */     UnixPath unixPath1 = null;
/*     */     try {
/*  62 */       byte[] arrayOfByte1 = UnixNativeDispatcher.realpath(file());
/*  63 */       unixPath1 = new UnixPath(linuxFileSystem, arrayOfByte1);
/*  64 */     } catch (UnixException unixException) {
/*  65 */       unixException.rethrowAsIOException(file());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  70 */     List<UnixMountEntry> list = linuxFileSystem.getMountEntries("/proc/mounts");
/*  71 */     UnixPath unixPath2 = unixPath1.getParent();
/*  72 */     while (unixPath2 != null) {
/*  73 */       UnixFileAttributes unixFileAttributes = null;
/*     */       try {
/*  75 */         unixFileAttributes = UnixFileAttributes.get(unixPath2, true);
/*  76 */       } catch (UnixException unixException) {
/*  77 */         unixException.rethrowAsIOException(unixPath2);
/*     */       } 
/*  79 */       if (unixFileAttributes.dev() != dev()) {
/*     */ 
/*     */         
/*  82 */         byte[] arrayOfByte1 = unixPath1.asByteArray();
/*  83 */         for (UnixMountEntry unixMountEntry : list) {
/*  84 */           if (Arrays.equals(arrayOfByte1, unixMountEntry.dir()))
/*  85 */             return unixMountEntry; 
/*     */         } 
/*     */       } 
/*  88 */       unixPath1 = unixPath2;
/*  89 */       unixPath2 = unixPath2.getParent();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  94 */     byte[] arrayOfByte = unixPath1.asByteArray();
/*  95 */     for (UnixMountEntry unixMountEntry : list) {
/*  96 */       if (Arrays.equals(arrayOfByte, unixMountEntry.dir())) {
/*  97 */         return unixMountEntry;
/*     */       }
/*     */     } 
/* 100 */     throw new IOException("Mount point not found");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isExtendedAttributesEnabled(UnixPath paramUnixPath) {
/*     */     try {
/* 107 */       int i = paramUnixPath.openForAttributeAccess(false);
/*     */       
/*     */       try {
/* 110 */         byte[] arrayOfByte = Util.toBytes("user.java");
/* 111 */         LinuxNativeDispatcher.fgetxattr(i, arrayOfByte, 0L, 0);
/* 112 */         return true;
/* 113 */       } catch (UnixException unixException) {
/*     */         
/* 115 */         if (unixException.errno() == 61)
/* 116 */           return true; 
/*     */       } finally {
/* 118 */         UnixNativeDispatcher.close(i);
/*     */       } 
/* 120 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsFileAttributeView(Class<? extends FileAttributeView> paramClass) {
/* 130 */     if (paramClass == DosFileAttributeView.class || paramClass == UserDefinedFileAttributeView.class) {
/*     */ 
/*     */ 
/*     */       
/* 134 */       UnixFileStore.FeatureStatus featureStatus = checkIfFeaturePresent("user_xattr");
/* 135 */       if (featureStatus == UnixFileStore.FeatureStatus.PRESENT)
/* 136 */         return true; 
/* 137 */       if (featureStatus == UnixFileStore.FeatureStatus.NOT_PRESENT) {
/* 138 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 142 */       if (entry().hasOption("user_xattr")) {
/* 143 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 147 */       if (entry().fstype().equals("ext3") || entry().fstype().equals("ext4")) {
/* 148 */         return false;
/*     */       }
/*     */       
/* 151 */       if (!this.xattrChecked) {
/* 152 */         UnixPath unixPath = new UnixPath(file().getFileSystem(), entry().dir());
/* 153 */         this.xattrEnabled = isExtendedAttributesEnabled(unixPath);
/* 154 */         this.xattrChecked = true;
/*     */       } 
/* 156 */       return this.xattrEnabled;
/*     */     } 
/*     */     
/* 159 */     if (paramClass == PosixFileAttributeView.class && entry().fstype().equals("vfat"))
/* 160 */       return false; 
/* 161 */     return super.supportsFileAttributeView(paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportsFileAttributeView(String paramString) {
/* 166 */     if (paramString.equals("dos"))
/* 167 */       return supportsFileAttributeView((Class)DosFileAttributeView.class); 
/* 168 */     if (paramString.equals("user"))
/* 169 */       return supportsFileAttributeView((Class)UserDefinedFileAttributeView.class); 
/* 170 */     return super.supportsFileAttributeView(paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/LinuxFileStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */