/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.URL;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.security.AccessController;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ import sun.security.util.Debug;
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
/*     */ abstract class SeedGenerator
/*     */ {
/*     */   private static SeedGenerator instance;
/*  85 */   private static final Debug debug = Debug.getInstance("provider");
/*     */ 
/*     */   
/*     */   static {
/*  89 */     String str = SunEntries.getSeedSource();
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
/* 102 */     if (str.equals("file:/dev/random") || str
/* 103 */       .equals("file:/dev/urandom")) {
/*     */       try {
/* 105 */         instance = new NativeSeedGenerator(str);
/* 106 */         if (debug != null) {
/* 107 */           debug.println("Using operating system seed generator" + str);
/*     */         }
/*     */       }
/* 110 */       catch (IOException iOException) {
/* 111 */         if (debug != null) {
/* 112 */           debug.println("Failed to use operating system seed generator: " + iOException
/* 113 */               .toString());
/*     */         }
/*     */       } 
/* 116 */     } else if (str.length() != 0) {
/*     */       try {
/* 118 */         instance = new URLSeedGenerator(str);
/* 119 */         if (debug != null) {
/* 120 */           debug.println("Using URL seed generator reading from " + str);
/*     */         }
/*     */       }
/* 123 */       catch (IOException iOException) {
/* 124 */         if (debug != null) {
/* 125 */           debug.println("Failed to create seed generator with " + str + ": " + iOException
/* 126 */               .toString());
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 132 */     if (instance == null) {
/* 133 */       if (debug != null) {
/* 134 */         debug.println("Using default threaded seed generator");
/*     */       }
/* 136 */       instance = new ThreadedSeedGenerator();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void generateSeed(byte[] paramArrayOfbyte) {
/* 144 */     instance.getSeedBytes(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] getSystemEntropy() {
/*     */     final MessageDigest md;
/*     */     try {
/* 157 */       messageDigest = MessageDigest.getInstance("SHA");
/* 158 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 159 */       throw new InternalError("internal error: SHA-1 not available.", noSuchAlgorithmException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 164 */     byte b = (byte)(int)System.currentTimeMillis();
/* 165 */     messageDigest.update(b);
/*     */ 
/*     */     
/* 168 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           
/*     */           public Void run()
/*     */           {
/*     */             try {
/* 174 */               Properties properties = System.getProperties();
/* 175 */               Enumeration<?> enumeration = properties.propertyNames();
/* 176 */               while (enumeration.hasMoreElements()) {
/* 177 */                 String str = (String)enumeration.nextElement();
/* 178 */                 md.update(str.getBytes());
/* 179 */                 md.update(properties.getProperty(str).getBytes());
/*     */               } 
/*     */ 
/*     */               
/* 183 */               SeedGenerator.addNetworkAdapterInfo(md);
/*     */ 
/*     */               
/* 186 */               File file = new File(properties.getProperty("java.io.tmpdir"));
/* 187 */               byte b = 0;
/*     */ 
/*     */               
/* 190 */               try (DirectoryStream<Path> null = Files.newDirectoryStream(file.toPath())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 196 */                 Random random = new Random();
/* 197 */                 for (Path path : directoryStream) {
/* 198 */                   if (b < 'Ȁ' || random.nextBoolean()) {
/* 199 */                     md.update(path.getFileName()
/* 200 */                         .toString().getBytes());
/*     */                   }
/* 202 */                   if (b++ > 'Ѐ') {
/*     */                     break;
/*     */                   }
/*     */                 } 
/*     */               } 
/* 207 */             } catch (Exception exception) {
/* 208 */               md.update((byte)exception.hashCode());
/*     */             } 
/*     */ 
/*     */             
/* 212 */             Runtime runtime = Runtime.getRuntime();
/* 213 */             byte[] arrayOfByte = SeedGenerator.longToByteArray(runtime.totalMemory());
/* 214 */             md.update(arrayOfByte, 0, arrayOfByte.length);
/* 215 */             arrayOfByte = SeedGenerator.longToByteArray(runtime.freeMemory());
/* 216 */             md.update(arrayOfByte, 0, arrayOfByte.length);
/*     */             
/* 218 */             return null;
/*     */           }
/*     */         });
/* 221 */     return messageDigest.digest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addNetworkAdapterInfo(MessageDigest paramMessageDigest) {
/*     */     try {
/* 233 */       Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
/* 234 */       while (enumeration.hasMoreElements()) {
/* 235 */         NetworkInterface networkInterface = enumeration.nextElement();
/* 236 */         paramMessageDigest.update(networkInterface.toString().getBytes());
/* 237 */         if (!networkInterface.isVirtual()) {
/* 238 */           byte[] arrayOfByte = networkInterface.getHardwareAddress();
/* 239 */           if (arrayOfByte != null) {
/* 240 */             paramMessageDigest.update(arrayOfByte);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 245 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] longToByteArray(long paramLong) {
/* 254 */     byte[] arrayOfByte = new byte[8];
/*     */     
/* 256 */     for (byte b = 0; b < 8; b++) {
/* 257 */       arrayOfByte[b] = (byte)(int)paramLong;
/* 258 */       paramLong >>= 8L;
/*     */     } 
/*     */     
/* 261 */     return arrayOfByte;
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
/*     */   abstract void getSeedBytes(byte[] paramArrayOfbyte);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ThreadedSeedGenerator
/*     */     extends SeedGenerator
/*     */     implements Runnable
/*     */   {
/* 287 */     private byte[] pool = new byte[20];
/* 288 */     private int start = this.end = 0;
/*     */     private int end;
/*     */     
/*     */     ThreadedSeedGenerator() {
/*     */       try {
/* 293 */         MessageDigest messageDigest = MessageDigest.getInstance("SHA");
/* 294 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 295 */         throw new InternalError("internal error: SHA-1 not available.", noSuchAlgorithmException);
/*     */       } 
/*     */ 
/*     */       
/* 299 */       final ThreadGroup[] finalsg = new ThreadGroup[1];
/*     */       
/* 301 */       Thread thread = AccessController.<Thread>doPrivileged(new PrivilegedAction<Thread>()
/*     */           {
/*     */             public Thread run()
/*     */             {
/* 305 */               ThreadGroup threadGroup2 = Thread.currentThread().getThreadGroup(); ThreadGroup threadGroup1;
/* 306 */               while ((threadGroup1 = threadGroup2.getParent()) != null) {
/* 307 */                 threadGroup2 = threadGroup1;
/*     */               }
/* 309 */               finalsg[0] = new ThreadGroup(threadGroup2, "SeedGenerator ThreadGroup");
/*     */               
/* 311 */               Thread thread = new Thread(finalsg[0], SeedGenerator.ThreadedSeedGenerator.this, "SeedGenerator Thread");
/*     */ 
/*     */               
/* 314 */               thread.setPriority(1);
/* 315 */               thread.setDaemon(true);
/* 316 */               return thread;
/*     */             }
/*     */           });
/* 319 */       this.seedGroup = arrayOfThreadGroup[0];
/* 320 */       thread.start();
/*     */     }
/*     */ 
/*     */     
/*     */     private int count;
/*     */     
/*     */     ThreadGroup seedGroup;
/*     */ 
/*     */     
/*     */     public final void run() {
/*     */       try {
/*     */         while (true) {
/* 332 */           synchronized (this) {
/* 333 */             while (this.count >= this.pool.length) {
/* 334 */               wait();
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 339 */           byte b1 = 0;
/*     */ 
/*     */           
/* 342 */           byte b = 0; int i = b;
/* 343 */           for (; i < 64000 && b < 6; b++) {
/*     */ 
/*     */             
/*     */             try {
/* 347 */               BogusThread bogusThread = new BogusThread();
/* 348 */               Thread thread = new Thread(this.seedGroup, bogusThread, "SeedGenerator Thread");
/*     */               
/* 350 */               thread.start();
/* 351 */             } catch (Exception exception) {
/* 352 */               throw new InternalError("internal error: SeedGenerator thread creation error.", exception);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 358 */             byte b2 = 0;
/* 359 */             long l = System.currentTimeMillis() + 250L;
/* 360 */             while (System.currentTimeMillis() < l) {
/* 361 */               synchronized (this) {  }
/* 362 */                b2++;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 367 */             b1 = (byte)(b1 ^ rndTab[b2 % 255]);
/* 368 */             i += b2;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 373 */           synchronized (this) {
/* 374 */             this.pool[this.end] = b1;
/* 375 */             this.end++;
/* 376 */             this.count++;
/* 377 */             if (this.end >= this.pool.length) {
/* 378 */               this.end = 0;
/*     */             }
/*     */             
/* 381 */             notifyAll();
/*     */           } 
/*     */         } 
/* 384 */       } catch (Exception exception) {
/* 385 */         throw new InternalError("internal error: SeedGenerator thread generated an exception.", exception);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void getSeedBytes(byte[] param1ArrayOfbyte) {
/* 392 */       for (byte b = 0; b < param1ArrayOfbyte.length; b++) {
/* 393 */         param1ArrayOfbyte[b] = getSeedByte();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     byte getSeedByte() {
/*     */       byte b;
/*     */       try {
/* 402 */         synchronized (this) {
/* 403 */           while (this.count <= 0) {
/* 404 */             wait();
/*     */           }
/*     */         } 
/* 407 */       } catch (Exception exception) {
/* 408 */         if (this.count <= 0) {
/* 409 */           throw new InternalError("internal error: SeedGenerator thread generated an exception.", exception);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 414 */       synchronized (this) {
/*     */         
/* 416 */         b = this.pool[this.start];
/* 417 */         this.pool[this.start] = 0;
/* 418 */         this.start++;
/* 419 */         this.count--;
/* 420 */         if (this.start == this.pool.length) {
/* 421 */           this.start = 0;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 426 */         notifyAll();
/*     */       } 
/*     */       
/* 429 */       return b;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 436 */     private static byte[] rndTab = new byte[] { 56, 30, -107, -6, -86, 25, -83, 75, -12, -64, 5, Byte.MIN_VALUE, 78, 21, 16, 32, 70, -81, 37, -51, -43, -46, -108, 87, 29, 17, -55, 22, -11, -111, -115, 84, -100, 108, -45, -15, -98, 72, -33, -28, 31, -52, -37, -117, -97, -27, 93, -123, 47, 126, -80, -62, -93, -79, 61, -96, -65, -5, -47, -119, 14, 89, 81, -118, -88, 20, 67, -126, -113, 60, -102, 55, 110, 28, 85, 121, 122, -58, 2, 45, 43, 24, -9, 103, -13, 102, -68, -54, -101, -104, 19, 13, -39, -26, -103, 62, 77, 51, 44, 111, 73, 18, -127, -82, 4, -30, 11, -99, -74, 40, -89, 42, -76, -77, -94, -35, -69, 35, 120, 76, 33, -73, -7, 82, -25, -10, 88, 125, -112, 58, 83, 95, 6, 10, 98, -34, 80, 15, -91, 86, -19, 52, -17, 117, 49, -63, 118, -90, 36, -116, -40, -71, 97, -53, -109, -85, 109, -16, -3, 104, -95, 68, 54, 34, 26, 114, -1, 106, -121, 3, 66, 0, 100, -84, 57, 107, 119, -42, 112, -61, 1, 48, 38, 12, -56, -57, 39, -106, -72, 41, 7, 71, -29, -59, -8, -38, 79, -31, 124, -124, 8, 91, 116, 99, -4, 9, -36, -78, 63, -49, -67, -87, 59, 101, -32, 92, 94, 53, -41, 115, -66, -70, -122, 50, -50, -22, -20, -18, -21, 23, -2, -48, 96, 65, -105, 123, -14, -110, 69, -24, -120, -75, 74, Byte.MAX_VALUE, -60, 113, 90, -114, 105, 46, 27, -125, -23, -44, 64 };
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
/*     */     private static class BogusThread
/*     */       implements Runnable
/*     */     {
/*     */       private BogusThread() {}
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
/*     */       public final void run() {
/*     */         try {
/* 474 */           for (byte b = 0; b < 5; b++) {
/* 475 */             Thread.sleep(50L);
/*     */           }
/*     */         }
/* 478 */         catch (Exception exception) {}
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class URLSeedGenerator
/*     */     extends SeedGenerator
/*     */   {
/*     */     private String deviceName;
/*     */ 
/*     */     
/*     */     private InputStream seedStream;
/*     */ 
/*     */ 
/*     */     
/*     */     URLSeedGenerator(String param1String) throws IOException {
/* 496 */       if (param1String == null) {
/* 497 */         throw new IOException("No random source specified");
/*     */       }
/* 499 */       this.deviceName = param1String;
/* 500 */       init();
/*     */     }
/*     */     
/*     */     private void init() throws IOException {
/* 504 */       final URL device = new URL(this.deviceName);
/*     */       try {
/* 506 */         this
/* 507 */           .seedStream = AccessController.<InputStream>doPrivileged(new PrivilegedExceptionAction<InputStream>()
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               public InputStream run() throws IOException
/*     */               {
/* 518 */                 if (device.getProtocol().equalsIgnoreCase("file")) {
/*     */                   
/* 520 */                   File file = SunEntries.getDeviceFile(device);
/* 521 */                   return new FileInputStream(file);
/*     */                 } 
/* 523 */                 return device.openStream();
/*     */               }
/*     */             });
/*     */       }
/* 527 */       catch (Exception exception) {
/* 528 */         throw new IOException("Failed to open " + this.deviceName, exception
/* 529 */             .getCause());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     void getSeedBytes(byte[] param1ArrayOfbyte) {
/* 535 */       int i = param1ArrayOfbyte.length;
/* 536 */       int j = 0;
/*     */       try {
/* 538 */         while (j < i) {
/* 539 */           int k = this.seedStream.read(param1ArrayOfbyte, j, i - j);
/*     */           
/* 541 */           if (k < 0) {
/* 542 */             throw new InternalError("URLSeedGenerator " + this.deviceName + " reached end of file");
/*     */           }
/*     */ 
/*     */           
/* 546 */           j += k;
/*     */         } 
/* 548 */       } catch (IOException iOException) {
/* 549 */         throw new InternalError("URLSeedGenerator " + this.deviceName + " generated exception: " + iOException
/* 550 */             .getMessage(), iOException);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/SeedGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */