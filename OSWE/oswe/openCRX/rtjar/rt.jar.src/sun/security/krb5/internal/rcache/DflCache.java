/*     */ package sun.security.krb5.internal.rcache;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.SeekableByteChannel;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.nio.file.attribute.PosixFilePermission;
/*     */ import java.security.AccessController;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.KrbApErrException;
/*     */ import sun.security.krb5.internal.ReplayCache;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DflCache
/*     */   extends ReplayCache
/*     */ {
/*     */   private static final int KRB5_RV_VNO = 1281;
/*     */   private static final int EXCESSREPS = 30;
/*     */   private final String source;
/*     */   private static int uid;
/*     */   
/*     */   static {
/*     */     try {
/* 112 */       Class<?> clazz = Class.forName("com.sun.security.auth.module.UnixSystem");
/* 113 */       uid = (int)((Long)clazz
/* 114 */         .getMethod("getUid", new Class[0]).invoke(clazz.newInstance(), new Object[0])).longValue();
/* 115 */     } catch (Exception exception) {
/* 116 */       uid = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DflCache(String paramString) {
/* 121 */     this.source = paramString;
/*     */   }
/*     */   
/*     */   private static String defaultPath() {
/* 125 */     return AccessController.<String>doPrivileged(new GetPropertyAction("java.io.tmpdir"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String defaultFile(String paramString) {
/* 131 */     int i = paramString.indexOf('/');
/* 132 */     if (i == -1)
/*     */     {
/* 134 */       i = paramString.indexOf('@');
/*     */     }
/* 136 */     if (i != -1)
/*     */     {
/* 138 */       paramString = paramString.substring(0, i);
/*     */     }
/* 140 */     if (uid != -1) {
/* 141 */       paramString = paramString + "_" + uid;
/*     */     }
/* 143 */     return paramString;
/*     */   }
/*     */   private static Path getFileName(String paramString1, String paramString2) {
/*     */     String str1;
/*     */     String str2;
/* 148 */     if (paramString1.equals("dfl")) {
/* 149 */       str1 = defaultPath();
/* 150 */       str2 = defaultFile(paramString2);
/* 151 */     } else if (paramString1.startsWith("dfl:")) {
/* 152 */       paramString1 = paramString1.substring(4);
/* 153 */       int i = paramString1.lastIndexOf('/');
/* 154 */       int j = paramString1.lastIndexOf('\\');
/* 155 */       if (j > i) i = j; 
/* 156 */       if (i == -1) {
/*     */         
/* 158 */         str1 = defaultPath();
/* 159 */         str2 = paramString1;
/* 160 */       } else if ((new File(paramString1)).isDirectory()) {
/*     */         
/* 162 */         str1 = paramString1;
/* 163 */         str2 = defaultFile(paramString2);
/*     */       } else {
/*     */         
/* 166 */         str1 = null;
/* 167 */         str2 = paramString1;
/*     */       } 
/*     */     } else {
/* 170 */       throw new IllegalArgumentException();
/*     */     } 
/* 172 */     return (new File(str1, str2)).toPath();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkAndStore(KerberosTime paramKerberosTime, AuthTimeWithHash paramAuthTimeWithHash) throws KrbApErrException {
/*     */     try {
/* 179 */       checkAndStore0(paramKerberosTime, paramAuthTimeWithHash);
/* 180 */     } catch (IOException iOException) {
/* 181 */       KrbApErrException krbApErrException = new KrbApErrException(60);
/* 182 */       krbApErrException.initCause(iOException);
/* 183 */       throw krbApErrException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void checkAndStore0(KerberosTime paramKerberosTime, AuthTimeWithHash paramAuthTimeWithHash) throws IOException, KrbApErrException {
/* 189 */     Path path = getFileName(this.source, paramAuthTimeWithHash.server);
/* 190 */     int i = 0;
/* 191 */     try (Storage null = new Storage()) {
/*     */       try {
/* 193 */         i = storage.loadAndCheck(path, paramAuthTimeWithHash, paramKerberosTime);
/* 194 */       } catch (IOException iOException) {
/*     */         
/* 196 */         Storage.create(path);
/* 197 */         i = storage.loadAndCheck(path, paramAuthTimeWithHash, paramKerberosTime);
/*     */       } 
/* 199 */       storage.append(paramAuthTimeWithHash);
/*     */     } 
/* 201 */     if (i > 30)
/* 202 */       Storage.expunge(path, paramKerberosTime); 
/*     */   }
/*     */   
/*     */   private static class Storage implements Closeable {
/*     */     SeekableByteChannel chan;
/*     */     
/*     */     private Storage() {}
/*     */     
/*     */     private static void create(Path param1Path) throws IOException {
/* 211 */       SeekableByteChannel seekableByteChannel = createNoClose(param1Path); Throwable throwable = null;
/*     */       
/* 213 */       if (seekableByteChannel != null) if (throwable != null) { try { seekableByteChannel.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  } else { seekableByteChannel.close(); }
/* 214 */           makeMine(param1Path);
/*     */     }
/*     */ 
/*     */     
/*     */     private static void makeMine(Path param1Path) throws IOException {
/*     */       try {
/* 220 */         HashSet<PosixFilePermission> hashSet = new HashSet();
/* 221 */         hashSet.add(PosixFilePermission.OWNER_READ);
/* 222 */         hashSet.add(PosixFilePermission.OWNER_WRITE);
/* 223 */         Files.setPosixFilePermissions(param1Path, hashSet);
/* 224 */       } catch (UnsupportedOperationException unsupportedOperationException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static SeekableByteChannel createNoClose(Path param1Path) throws IOException {
/* 231 */       SeekableByteChannel seekableByteChannel = Files.newByteChannel(param1Path, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE });
/*     */ 
/*     */ 
/*     */       
/* 235 */       ByteBuffer byteBuffer = ByteBuffer.allocate(6);
/* 236 */       byteBuffer.putShort((short)1281);
/* 237 */       byteBuffer.order(ByteOrder.nativeOrder());
/* 238 */       byteBuffer.putInt(KerberosTime.getDefaultSkew());
/* 239 */       byteBuffer.flip();
/* 240 */       seekableByteChannel.write(byteBuffer);
/* 241 */       return seekableByteChannel;
/*     */     }
/*     */ 
/*     */     
/*     */     private static void expunge(Path param1Path, KerberosTime param1KerberosTime) throws IOException {
/* 246 */       Path path = Files.createTempFile(param1Path.getParent(), "rcache", null, (FileAttribute<?>[])new FileAttribute[0]);
/* 247 */       try(SeekableByteChannel null = Files.newByteChannel(param1Path, new OpenOption[0]); 
/* 248 */           SeekableByteChannel null = createNoClose(path)) {
/* 249 */         long l = (param1KerberosTime.getSeconds() - readHeader(seekableByteChannel));
/*     */       } 
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
/* 262 */       makeMine(path);
/* 263 */       Files.move(path, param1Path, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int loadAndCheck(Path param1Path, AuthTimeWithHash param1AuthTimeWithHash, KerberosTime param1KerberosTime) throws IOException, KrbApErrException {
/* 273 */       byte b = 0;
/* 274 */       if (Files.isSymbolicLink(param1Path)) {
/* 275 */         throw new IOException("Symlink not accepted");
/*     */       }
/*     */       
/*     */       try {
/* 279 */         Set<PosixFilePermission> set = Files.getPosixFilePermissions(param1Path, new java.nio.file.LinkOption[0]);
/* 280 */         if (DflCache.uid != -1 && (
/* 281 */           (Integer)Files.getAttribute(param1Path, "unix:uid", new java.nio.file.LinkOption[0])).intValue() != DflCache.uid) {
/* 282 */           throw new IOException("Not mine");
/*     */         }
/* 284 */         if (set.contains(PosixFilePermission.GROUP_READ) || set
/* 285 */           .contains(PosixFilePermission.GROUP_WRITE) || set
/* 286 */           .contains(PosixFilePermission.GROUP_EXECUTE) || set
/* 287 */           .contains(PosixFilePermission.OTHERS_READ) || set
/* 288 */           .contains(PosixFilePermission.OTHERS_WRITE) || set
/* 289 */           .contains(PosixFilePermission.OTHERS_EXECUTE)) {
/* 290 */           throw new IOException("Accessible by someone else");
/*     */         }
/* 292 */       } catch (UnsupportedOperationException unsupportedOperationException) {}
/*     */ 
/*     */       
/* 295 */       this.chan = Files.newByteChannel(param1Path, new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.READ });
/*     */ 
/*     */       
/* 298 */       long l1 = (param1KerberosTime.getSeconds() - readHeader(this.chan));
/*     */       
/* 300 */       long l2 = 0L;
/* 301 */       boolean bool = false;
/*     */       try {
/*     */         while (true) {
/* 304 */           l2 = this.chan.position();
/* 305 */           AuthTime authTime = AuthTime.readFrom(this.chan);
/* 306 */           if (authTime instanceof AuthTimeWithHash) {
/* 307 */             if (param1AuthTimeWithHash.equals(authTime))
/*     */             {
/* 309 */               throw new KrbApErrException(34); } 
/* 310 */             if (param1AuthTimeWithHash.isSameIgnoresHash(authTime))
/*     */             {
/*     */               
/* 313 */               bool = true;
/*     */             }
/*     */           }
/* 316 */           else if (param1AuthTimeWithHash.isSameIgnoresHash(authTime)) {
/*     */ 
/*     */             
/* 319 */             if (!bool) {
/* 320 */               throw new KrbApErrException(34);
/*     */             }
/*     */           } 
/*     */           
/* 324 */           if (authTime.ctime < l1) {
/* 325 */             b++; continue;
/*     */           } 
/* 327 */           b--;
/*     */         } 
/* 329 */       } catch (BufferUnderflowException bufferUnderflowException) {
/*     */         
/* 331 */         this.chan.position(l2);
/*     */ 
/*     */ 
/*     */         
/* 335 */         return b;
/*     */       } 
/*     */     }
/*     */     
/*     */     private static int readHeader(SeekableByteChannel param1SeekableByteChannel) throws IOException {
/* 340 */       ByteBuffer byteBuffer = ByteBuffer.allocate(6);
/* 341 */       param1SeekableByteChannel.read(byteBuffer);
/* 342 */       if (byteBuffer.getShort(0) != 1281) {
/* 343 */         throw new IOException("Not correct rcache version");
/*     */       }
/* 345 */       byteBuffer.order(ByteOrder.nativeOrder());
/* 346 */       return byteBuffer.getInt(2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void append(AuthTimeWithHash param1AuthTimeWithHash) throws IOException {
/* 353 */       ByteBuffer byteBuffer = ByteBuffer.wrap(param1AuthTimeWithHash.encode(true));
/* 354 */       this.chan.write(byteBuffer);
/* 355 */       byteBuffer = ByteBuffer.wrap(param1AuthTimeWithHash.encode(false));
/* 356 */       this.chan.write(byteBuffer);
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 361 */       if (this.chan != null) this.chan.close(); 
/* 362 */       this.chan = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/rcache/DflCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */