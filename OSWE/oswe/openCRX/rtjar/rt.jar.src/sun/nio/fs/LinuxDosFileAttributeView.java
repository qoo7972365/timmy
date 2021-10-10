/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.DosFileAttributeView;
/*     */ import java.nio.file.attribute.DosFileAttributes;
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import sun.misc.Unsafe;
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
/*     */ class LinuxDosFileAttributeView
/*     */   extends UnixFileAttributeViews.Basic
/*     */   implements DosFileAttributeView
/*     */ {
/*  46 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   
/*     */   private static final String READONLY_NAME = "readonly";
/*     */   
/*     */   private static final String ARCHIVE_NAME = "archive";
/*     */   private static final String SYSTEM_NAME = "system";
/*     */   private static final String HIDDEN_NAME = "hidden";
/*     */   private static final String DOS_XATTR_NAME = "user.DOSATTRIB";
/*  54 */   private static final byte[] DOS_XATTR_NAME_AS_BYTES = Util.toBytes("user.DOSATTRIB");
/*     */   
/*     */   private static final int DOS_XATTR_READONLY = 1;
/*     */   
/*     */   private static final int DOS_XATTR_HIDDEN = 2;
/*     */   
/*     */   private static final int DOS_XATTR_SYSTEM = 4;
/*     */   
/*     */   private static final int DOS_XATTR_ARCHIVE = 32;
/*  63 */   private static final Set<String> dosAttributeNames = Util.newSet(basicAttributeNames, new String[] { "readonly", "archive", "system", "hidden" });
/*     */   
/*     */   LinuxDosFileAttributeView(UnixPath paramUnixPath, boolean paramBoolean) {
/*  66 */     super(paramUnixPath, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  71 */     return "dos";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(String paramString, Object paramObject) throws IOException {
/*  78 */     if (paramString.equals("readonly")) {
/*  79 */       setReadOnly(((Boolean)paramObject).booleanValue());
/*     */       return;
/*     */     } 
/*  82 */     if (paramString.equals("archive")) {
/*  83 */       setArchive(((Boolean)paramObject).booleanValue());
/*     */       return;
/*     */     } 
/*  86 */     if (paramString.equals("system")) {
/*  87 */       setSystem(((Boolean)paramObject).booleanValue());
/*     */       return;
/*     */     } 
/*  90 */     if (paramString.equals("hidden")) {
/*  91 */       setHidden(((Boolean)paramObject).booleanValue());
/*     */       return;
/*     */     } 
/*  94 */     super.setAttribute(paramString, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> readAttributes(String[] paramArrayOfString) throws IOException {
/* 102 */     AbstractBasicFileAttributeView.AttributesBuilder attributesBuilder = AbstractBasicFileAttributeView.AttributesBuilder.create(dosAttributeNames, paramArrayOfString);
/* 103 */     DosFileAttributes dosFileAttributes = readAttributes();
/* 104 */     addRequestedBasicAttributes(dosFileAttributes, attributesBuilder);
/* 105 */     if (attributesBuilder.match("readonly"))
/* 106 */       attributesBuilder.add("readonly", Boolean.valueOf(dosFileAttributes.isReadOnly())); 
/* 107 */     if (attributesBuilder.match("archive"))
/* 108 */       attributesBuilder.add("archive", Boolean.valueOf(dosFileAttributes.isArchive())); 
/* 109 */     if (attributesBuilder.match("system"))
/* 110 */       attributesBuilder.add("system", Boolean.valueOf(dosFileAttributes.isSystem())); 
/* 111 */     if (attributesBuilder.match("hidden"))
/* 112 */       attributesBuilder.add("hidden", Boolean.valueOf(dosFileAttributes.isHidden())); 
/* 113 */     return attributesBuilder.unmodifiableMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public DosFileAttributes readAttributes() throws IOException {
/* 118 */     this.file.checkRead();
/*     */     
/* 120 */     int i = this.file.openForAttributeAccess(this.followLinks);
/*     */     try {
/* 122 */       final UnixFileAttributes attrs = UnixFileAttributes.get(i);
/* 123 */       final int dosAttribute = getDosAttribute(i);
/*     */       
/* 125 */       return new DosFileAttributes()
/*     */         {
/*     */           public FileTime lastModifiedTime() {
/* 128 */             return attrs.lastModifiedTime();
/*     */           }
/*     */           
/*     */           public FileTime lastAccessTime() {
/* 132 */             return attrs.lastAccessTime();
/*     */           }
/*     */           
/*     */           public FileTime creationTime() {
/* 136 */             return attrs.creationTime();
/*     */           }
/*     */           
/*     */           public boolean isRegularFile() {
/* 140 */             return attrs.isRegularFile();
/*     */           }
/*     */           
/*     */           public boolean isDirectory() {
/* 144 */             return attrs.isDirectory();
/*     */           }
/*     */           
/*     */           public boolean isSymbolicLink() {
/* 148 */             return attrs.isSymbolicLink();
/*     */           }
/*     */           
/*     */           public boolean isOther() {
/* 152 */             return attrs.isOther();
/*     */           }
/*     */           
/*     */           public long size() {
/* 156 */             return attrs.size();
/*     */           }
/*     */           
/*     */           public Object fileKey() {
/* 160 */             return attrs.fileKey();
/*     */           }
/*     */           
/*     */           public boolean isReadOnly() {
/* 164 */             return ((dosAttribute & 0x1) != 0);
/*     */           }
/*     */           
/*     */           public boolean isHidden() {
/* 168 */             return ((dosAttribute & 0x2) != 0);
/*     */           }
/*     */           
/*     */           public boolean isArchive() {
/* 172 */             return ((dosAttribute & 0x20) != 0);
/*     */           }
/*     */           
/*     */           public boolean isSystem() {
/* 176 */             return ((dosAttribute & 0x4) != 0);
/*     */           }
/*     */         };
/*     */     }
/* 180 */     catch (UnixException unixException) {
/* 181 */       unixException.rethrowAsIOException(this.file);
/* 182 */       return null;
/*     */     } finally {
/* 184 */       UnixNativeDispatcher.close(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReadOnly(boolean paramBoolean) throws IOException {
/* 190 */     updateDosAttribute(1, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHidden(boolean paramBoolean) throws IOException {
/* 195 */     updateDosAttribute(2, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setArchive(boolean paramBoolean) throws IOException {
/* 200 */     updateDosAttribute(32, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSystem(boolean paramBoolean) throws IOException {
/* 205 */     updateDosAttribute(4, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getDosAttribute(int paramInt) throws UnixException {
/* 214 */     NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(24);
/*     */     
/*     */     try {
/* 217 */       int i = LinuxNativeDispatcher.fgetxattr(paramInt, DOS_XATTR_NAME_AS_BYTES, nativeBuffer.address(), 24);
/*     */       
/* 219 */       if (i > 0) {
/*     */         
/* 221 */         if (unsafe.getByte(nativeBuffer.address() + i - 1L) == 0) {
/* 222 */           i--;
/*     */         }
/*     */         
/* 225 */         byte[] arrayOfByte = new byte[i];
/* 226 */         unsafe.copyMemory(null, nativeBuffer.address(), arrayOfByte, Unsafe.ARRAY_BYTE_BASE_OFFSET, i);
/*     */         
/* 228 */         String str = Util.toString(arrayOfByte);
/*     */ 
/*     */         
/* 231 */         if (str.length() >= 3 && str.startsWith("0x")) {
/*     */           try {
/* 233 */             return Integer.parseInt(str.substring(2), 16);
/* 234 */           } catch (NumberFormatException numberFormatException) {}
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 239 */       throw new UnixException("Value of user.DOSATTRIB attribute is invalid");
/* 240 */     } catch (UnixException unixException) {
/*     */       
/* 242 */       if (unixException.errno() == 61)
/* 243 */         return 0; 
/* 244 */       throw unixException;
/*     */     } finally {
/* 246 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateDosAttribute(int paramInt, boolean paramBoolean) throws IOException {
/* 254 */     this.file.checkWrite();
/*     */     
/* 256 */     int i = this.file.openForAttributeAccess(this.followLinks);
/*     */     try {
/* 258 */       int j = getDosAttribute(i);
/* 259 */       int k = j;
/* 260 */       if (paramBoolean) {
/* 261 */         k |= paramInt;
/*     */       } else {
/* 263 */         k &= paramInt ^ 0xFFFFFFFF;
/*     */       } 
/* 265 */       if (k != j) {
/* 266 */         byte[] arrayOfByte = Util.toBytes("0x" + Integer.toHexString(k));
/* 267 */         NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(arrayOfByte);
/*     */         try {
/* 269 */           LinuxNativeDispatcher.fsetxattr(i, DOS_XATTR_NAME_AS_BYTES, nativeBuffer
/* 270 */               .address(), arrayOfByte.length + 1);
/*     */         } finally {
/* 272 */           nativeBuffer.release();
/*     */         } 
/*     */       } 
/* 275 */     } catch (UnixException unixException) {
/* 276 */       unixException.rethrowAsIOException(this.file);
/*     */     } finally {
/* 278 */       UnixNativeDispatcher.close(i);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/LinuxDosFileAttributeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */