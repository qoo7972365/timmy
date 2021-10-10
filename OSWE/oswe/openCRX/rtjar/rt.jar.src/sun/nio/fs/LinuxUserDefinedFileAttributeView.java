/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.file.FileSystemException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.nio.ch.DirectBuffer;
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
/*     */ class LinuxUserDefinedFileAttributeView
/*     */   extends AbstractUserDefinedFileAttributeView
/*     */ {
/*  44 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   
/*     */   private static final String USER_NAMESPACE = "user.";
/*     */   
/*     */   private static final int XATTR_NAME_MAX = 255;
/*     */   private final UnixPath file;
/*     */   private final boolean followLinks;
/*     */   
/*     */   private byte[] nameAsBytes(UnixPath paramUnixPath, String paramString) throws IOException {
/*  53 */     if (paramString == null)
/*  54 */       throw new NullPointerException("'name' is null"); 
/*  55 */     paramString = "user." + paramString;
/*  56 */     byte[] arrayOfByte = Util.toBytes(paramString);
/*  57 */     if (arrayOfByte.length > 255) {
/*  58 */       throw new FileSystemException(paramUnixPath.getPathForExceptionMessage(), null, "'" + paramString + "' is too big");
/*     */     }
/*     */     
/*  61 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> asList(long paramLong, int paramInt) {
/*  66 */     ArrayList<String> arrayList = new ArrayList();
/*  67 */     int i = 0;
/*  68 */     byte b = 0;
/*  69 */     while (b < paramInt) {
/*  70 */       if (unsafe.getByte(paramLong + b) == 0) {
/*  71 */         int j = b - i;
/*  72 */         byte[] arrayOfByte = new byte[j];
/*  73 */         unsafe.copyMemory(null, paramLong + i, arrayOfByte, Unsafe.ARRAY_BYTE_BASE_OFFSET, j);
/*     */         
/*  75 */         String str = Util.toString(arrayOfByte);
/*  76 */         if (str.startsWith("user.")) {
/*  77 */           str = str.substring("user.".length());
/*  78 */           arrayList.add(str);
/*     */         } 
/*  80 */         i = b + 1;
/*     */       } 
/*  82 */       b++;
/*     */     } 
/*  84 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LinuxUserDefinedFileAttributeView(UnixPath paramUnixPath, boolean paramBoolean) {
/*  91 */     this.file = paramUnixPath;
/*  92 */     this.followLinks = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> list() throws IOException {
/*  97 */     if (System.getSecurityManager() != null) {
/*  98 */       checkAccess(this.file.getPathForPermissionCheck(), true, false);
/*     */     }
/* 100 */     int i = this.file.openForAttributeAccess(this.followLinks);
/* 101 */     NativeBuffer nativeBuffer = null;
/*     */     try {
/* 103 */       int j = 1024;
/* 104 */       nativeBuffer = NativeBuffers.getNativeBuffer(j);
/*     */       while (true) {
/*     */         try {
/* 107 */           int k = LinuxNativeDispatcher.flistxattr(i, nativeBuffer.address(), j);
/* 108 */           List<String> list = asList(nativeBuffer.address(), k);
/* 109 */           return Collections.unmodifiableList(list);
/* 110 */         } catch (UnixException unixException) {
/*     */           
/* 112 */           if (unixException.errno() == 34 && j < 32768) {
/* 113 */             nativeBuffer.release();
/* 114 */             j *= 2;
/* 115 */             nativeBuffer = null;
/* 116 */             nativeBuffer = NativeBuffers.getNativeBuffer(j); continue;
/*     */           }  break;
/*     */         } 
/* 119 */       }  throw new FileSystemException(this.file.getPathForExceptionMessage(), null, "Unable to get list of extended attributes: " + unixException
/*     */           
/* 121 */           .getMessage());
/*     */     }
/*     */     finally {
/*     */       
/* 125 */       if (nativeBuffer != null)
/* 126 */         nativeBuffer.release(); 
/* 127 */       LinuxNativeDispatcher.close(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int size(String paramString) throws IOException {
/* 133 */     if (System.getSecurityManager() != null) {
/* 134 */       checkAccess(this.file.getPathForPermissionCheck(), true, false);
/*     */     }
/* 136 */     int i = this.file.openForAttributeAccess(this.followLinks);
/*     */     
/*     */     try {
/* 139 */       return LinuxNativeDispatcher.fgetxattr(i, nameAsBytes(this.file, paramString), 0L, 0);
/* 140 */     } catch (UnixException unixException) {
/* 141 */       throw new FileSystemException(this.file.getPathForExceptionMessage(), null, "Unable to get size of extended attribute '" + paramString + "': " + unixException
/*     */           
/* 143 */           .getMessage());
/*     */     } finally {
/* 145 */       LinuxNativeDispatcher.close(i);
/*     */     } 
/*     */   }
/*     */   public int read(String paramString, ByteBuffer paramByteBuffer) throws IOException {
/*     */     NativeBuffer nativeBuffer;
/*     */     long l;
/* 151 */     if (System.getSecurityManager() != null) {
/* 152 */       checkAccess(this.file.getPathForPermissionCheck(), true, false);
/*     */     }
/* 154 */     if (paramByteBuffer.isReadOnly())
/* 155 */       throw new IllegalArgumentException("Read-only buffer"); 
/* 156 */     int i = paramByteBuffer.position();
/* 157 */     int j = paramByteBuffer.limit();
/* 158 */     assert i <= j;
/* 159 */     boolean bool = (i <= j) ? (j - i) : false;
/*     */ 
/*     */ 
/*     */     
/* 163 */     if (paramByteBuffer instanceof DirectBuffer) {
/* 164 */       nativeBuffer = null;
/* 165 */       l = ((DirectBuffer)paramByteBuffer).address() + i;
/*     */     } else {
/*     */       
/* 168 */       nativeBuffer = NativeBuffers.getNativeBuffer(bool);
/* 169 */       l = nativeBuffer.address();
/*     */     } 
/*     */     
/* 172 */     int k = this.file.openForAttributeAccess(this.followLinks);
/*     */     try {
/*     */       try {
/* 175 */         int m = LinuxNativeDispatcher.fgetxattr(k, nameAsBytes(this.file, paramString), l, bool);
/*     */ 
/*     */         
/* 178 */         if (!bool) {
/* 179 */           if (m > 0)
/* 180 */             throw new UnixException(34); 
/* 181 */           return 0;
/*     */         } 
/*     */ 
/*     */         
/* 185 */         if (nativeBuffer != null) {
/* 186 */           int n = paramByteBuffer.arrayOffset() + i + Unsafe.ARRAY_BYTE_BASE_OFFSET;
/* 187 */           unsafe.copyMemory(null, l, paramByteBuffer.array(), n, m);
/*     */         } 
/* 189 */         paramByteBuffer.position(i + m);
/* 190 */         return m;
/* 191 */       } catch (UnixException unixException) {
/*     */         
/* 193 */         String str = (unixException.errno() == 34) ? "Insufficient space in buffer" : unixException.getMessage();
/* 194 */         throw new FileSystemException(this.file.getPathForExceptionMessage(), null, "Error reading extended attribute '" + paramString + "': " + str);
/*     */       } finally {
/*     */         
/* 197 */         LinuxNativeDispatcher.close(k);
/*     */       } 
/*     */     } finally {
/* 200 */       if (nativeBuffer != null)
/* 201 */         nativeBuffer.release(); 
/*     */     } 
/*     */   }
/*     */   public int write(String paramString, ByteBuffer paramByteBuffer) throws IOException {
/*     */     NativeBuffer nativeBuffer;
/*     */     long l;
/* 207 */     if (System.getSecurityManager() != null) {
/* 208 */       checkAccess(this.file.getPathForPermissionCheck(), false, true);
/*     */     }
/* 210 */     int i = paramByteBuffer.position();
/* 211 */     int j = paramByteBuffer.limit();
/* 212 */     assert i <= j;
/* 213 */     byte b = (i <= j) ? (j - i) : 0;
/*     */ 
/*     */ 
/*     */     
/* 217 */     if (paramByteBuffer instanceof DirectBuffer) {
/* 218 */       nativeBuffer = null;
/* 219 */       l = ((DirectBuffer)paramByteBuffer).address() + i;
/*     */     } else {
/*     */       
/* 222 */       nativeBuffer = NativeBuffers.getNativeBuffer(b);
/* 223 */       l = nativeBuffer.address();
/*     */       
/* 225 */       if (paramByteBuffer.hasArray()) {
/*     */         
/* 227 */         int m = paramByteBuffer.arrayOffset() + i + Unsafe.ARRAY_BYTE_BASE_OFFSET;
/* 228 */         unsafe.copyMemory(paramByteBuffer.array(), m, null, l, b);
/*     */       } else {
/*     */         
/* 231 */         byte[] arrayOfByte = new byte[b];
/* 232 */         paramByteBuffer.get(arrayOfByte);
/* 233 */         paramByteBuffer.position(i);
/* 234 */         unsafe.copyMemory(arrayOfByte, Unsafe.ARRAY_BYTE_BASE_OFFSET, null, l, b);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 239 */     int k = this.file.openForAttributeAccess(this.followLinks);
/*     */     try {
/*     */       try {
/* 242 */         LinuxNativeDispatcher.fsetxattr(k, nameAsBytes(this.file, paramString), l, b);
/* 243 */         paramByteBuffer.position(i + b);
/* 244 */         return b;
/* 245 */       } catch (UnixException unixException) {
/* 246 */         throw new FileSystemException(this.file.getPathForExceptionMessage(), null, "Error writing extended attribute '" + paramString + "': " + unixException
/*     */             
/* 248 */             .getMessage());
/*     */       } finally {
/* 250 */         LinuxNativeDispatcher.close(k);
/*     */       } 
/*     */     } finally {
/* 253 */       if (nativeBuffer != null) {
/* 254 */         nativeBuffer.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 260 */     if (System.getSecurityManager() != null) {
/* 261 */       checkAccess(this.file.getPathForPermissionCheck(), false, true);
/*     */     }
/* 263 */     int i = this.file.openForAttributeAccess(this.followLinks);
/*     */     try {
/* 265 */       LinuxNativeDispatcher.fremovexattr(i, nameAsBytes(this.file, paramString));
/* 266 */     } catch (UnixException unixException) {
/* 267 */       throw new FileSystemException(this.file.getPathForExceptionMessage(), null, "Unable to delete extended attribute '" + paramString + "': " + unixException
/* 268 */           .getMessage());
/*     */     } finally {
/* 270 */       LinuxNativeDispatcher.close(i);
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
/*     */   static void copyExtendedAttributes(int paramInt1, int paramInt2) {
/* 283 */     NativeBuffer nativeBuffer = null;
/*     */ 
/*     */     
/*     */     try {
/* 287 */       int i = 1024;
/* 288 */       nativeBuffer = NativeBuffers.getNativeBuffer(i);
/*     */       while (true) {
/*     */         try {
/* 291 */           i = LinuxNativeDispatcher.flistxattr(paramInt1, nativeBuffer.address(), i);
/*     */         }
/* 293 */         catch (UnixException unixException) {
/*     */           
/* 295 */           if (unixException.errno() == 34 && i < 32768) {
/* 296 */             nativeBuffer.release();
/* 297 */             i *= 2;
/* 298 */             nativeBuffer = null;
/* 299 */             nativeBuffer = NativeBuffers.getNativeBuffer(i);
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/* 309 */       long l = nativeBuffer.address();
/* 310 */       int j = 0;
/* 311 */       byte b = 0;
/* 312 */       while (b < i) {
/* 313 */         if (unsafe.getByte(l + b) == 0) {
/*     */ 
/*     */ 
/*     */           
/* 317 */           int k = b - j;
/* 318 */           byte[] arrayOfByte = new byte[k];
/* 319 */           unsafe.copyMemory(null, l + j, arrayOfByte, Unsafe.ARRAY_BYTE_BASE_OFFSET, k);
/*     */           
/*     */           try {
/* 322 */             copyExtendedAttribute(paramInt1, arrayOfByte, paramInt2);
/* 323 */           } catch (UnixException unixException) {}
/*     */ 
/*     */           
/* 326 */           j = b + 1;
/*     */         } 
/* 328 */         b++;
/*     */       } 
/*     */     } finally {
/*     */       
/* 332 */       if (nativeBuffer != null) {
/* 333 */         nativeBuffer.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void copyExtendedAttribute(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws UnixException {
/* 340 */     int i = LinuxNativeDispatcher.fgetxattr(paramInt1, paramArrayOfbyte, 0L, 0);
/* 341 */     NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(i);
/*     */     try {
/* 343 */       long l = nativeBuffer.address();
/* 344 */       i = LinuxNativeDispatcher.fgetxattr(paramInt1, paramArrayOfbyte, l, i);
/* 345 */       LinuxNativeDispatcher.fsetxattr(paramInt2, paramArrayOfbyte, l, i);
/*     */     } finally {
/* 347 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/LinuxUserDefinedFileAttributeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */