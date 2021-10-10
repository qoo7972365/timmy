/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.ProviderException;
/*     */ import java.security.SecureRandomSpi;
/*     */ import java.util.Arrays;
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
/*     */ public final class NativePRNG
/*     */   extends SecureRandomSpi
/*     */ {
/*     */   private static final long serialVersionUID = -6599091113397072932L;
/*  80 */   private static final Debug debug = Debug.getInstance("provider");
/*     */   
/*     */   private static final String NAME_RANDOM = "/dev/random";
/*     */   
/*     */   private static final String NAME_URANDOM = "/dev/urandom";
/*     */ 
/*     */   
/*     */   private enum Variant
/*     */   {
/*  89 */     MIXED, BLOCKING, NONBLOCKING;
/*     */   }
/*     */ 
/*     */   
/*  93 */   private static final RandomIO INSTANCE = initIO(Variant.MIXED);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static URL getEgdUrl() {
/*     */     URL uRL;
/* 103 */     String str = SunEntries.getSeedSource();
/*     */ 
/*     */     
/* 106 */     if (str.length() != 0) {
/* 107 */       if (debug != null) {
/* 108 */         debug.println("NativePRNG egdUrl: " + str);
/*     */       }
/*     */       try {
/* 111 */         uRL = new URL(str);
/* 112 */         if (!uRL.getProtocol().equalsIgnoreCase("file")) {
/* 113 */           return null;
/*     */         }
/* 115 */       } catch (MalformedURLException malformedURLException) {
/* 116 */         return null;
/*     */       } 
/*     */     } else {
/* 119 */       uRL = null;
/*     */     } 
/*     */     
/* 122 */     return uRL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RandomIO initIO(final Variant v) {
/* 129 */     return AccessController.<RandomIO>doPrivileged(new PrivilegedAction<RandomIO>()
/*     */         {
/*     */           public NativePRNG.RandomIO run()
/*     */           {
/*     */             File file1;
/*     */             File file2;
/*     */             URL uRL;
/*     */             File file3;
/* 137 */             switch (v) {
/*     */               
/*     */               case MIXED:
/* 140 */                 file3 = null;
/*     */                 
/* 142 */                 if ((uRL = NativePRNG.getEgdUrl()) != null) {
/*     */                   try {
/* 144 */                     file3 = SunEntries.getDeviceFile(uRL);
/* 145 */                   } catch (IOException iOException) {}
/*     */                 }
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 151 */                 if (file3 != null && file3.canRead()) {
/* 152 */                   File file = file3;
/*     */                 } else {
/*     */                   
/* 155 */                   File file = new File("/dev/random");
/*     */                 } 
/* 157 */                 file2 = new File("/dev/urandom");
/*     */                 break;
/*     */               
/*     */               case BLOCKING:
/* 161 */                 file1 = new File("/dev/random");
/* 162 */                 file2 = new File("/dev/random");
/*     */                 break;
/*     */               
/*     */               case NONBLOCKING:
/* 166 */                 file1 = new File("/dev/urandom");
/* 167 */                 file2 = new File("/dev/urandom");
/*     */                 break;
/*     */ 
/*     */               
/*     */               default:
/* 172 */                 return null;
/*     */             } 
/*     */             
/* 175 */             if (NativePRNG.debug != null) {
/* 176 */               NativePRNG.debug.println("NativePRNG." + v + " seedFile: " + file1 + " nextFile: " + file2);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 181 */             if (!file1.canRead() || !file2.canRead()) {
/* 182 */               if (NativePRNG.debug != null) {
/* 183 */                 NativePRNG.debug.println("NativePRNG." + v + " Couldn't read Files.");
/*     */               }
/*     */               
/* 186 */               return null;
/*     */             } 
/*     */             
/*     */             try {
/* 190 */               return new NativePRNG.RandomIO(file1, file2);
/* 191 */             } catch (Exception exception) {
/* 192 */               return null;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isAvailable() {
/* 200 */     return (INSTANCE != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NativePRNG() {
/* 206 */     if (INSTANCE == null) {
/* 207 */       throw new AssertionError("NativePRNG not available");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineSetSeed(byte[] paramArrayOfbyte) {
/* 214 */     INSTANCE.implSetSeed(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineNextBytes(byte[] paramArrayOfbyte) {
/* 220 */     INSTANCE.implNextBytes(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] engineGenerateSeed(int paramInt) {
/* 226 */     return INSTANCE.implGenerateSeed(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Blocking
/*     */     extends SecureRandomSpi
/*     */   {
/*     */     private static final long serialVersionUID = -6396183145759983347L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 244 */     private static final NativePRNG.RandomIO INSTANCE = NativePRNG.initIO(NativePRNG.Variant.BLOCKING);
/*     */ 
/*     */     
/*     */     static boolean isAvailable() {
/* 248 */       return (INSTANCE != null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Blocking() {
/* 254 */       if (INSTANCE == null) {
/* 255 */         throw new AssertionError("NativePRNG$Blocking not available");
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void engineSetSeed(byte[] param1ArrayOfbyte) {
/* 262 */       INSTANCE.implSetSeed(param1ArrayOfbyte);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void engineNextBytes(byte[] param1ArrayOfbyte) {
/* 268 */       INSTANCE.implNextBytes(param1ArrayOfbyte);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected byte[] engineGenerateSeed(int param1Int) {
/* 274 */       return INSTANCE.implGenerateSeed(param1Int);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class NonBlocking
/*     */     extends SecureRandomSpi
/*     */   {
/*     */     private static final long serialVersionUID = -1102062982994105487L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 293 */     private static final NativePRNG.RandomIO INSTANCE = NativePRNG.initIO(NativePRNG.Variant.NONBLOCKING);
/*     */ 
/*     */     
/*     */     static boolean isAvailable() {
/* 297 */       return (INSTANCE != null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public NonBlocking() {
/* 303 */       if (INSTANCE == null) {
/* 304 */         throw new AssertionError("NativePRNG$NonBlocking not available");
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void engineSetSeed(byte[] param1ArrayOfbyte) {
/* 312 */       INSTANCE.implSetSeed(param1ArrayOfbyte);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void engineNextBytes(byte[] param1ArrayOfbyte) {
/* 318 */       INSTANCE.implNextBytes(param1ArrayOfbyte);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected byte[] engineGenerateSeed(int param1Int) {
/* 324 */       return INSTANCE.implGenerateSeed(param1Int);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class RandomIO
/*     */   {
/*     */     private static final long MAX_BUFFER_TIME = 100L;
/*     */ 
/*     */     
/*     */     private static final int MAX_BUFFER_SIZE = 65536;
/*     */ 
/*     */     
/*     */     private static final int MIN_BUFFER_SIZE = 32;
/*     */ 
/*     */     
/* 341 */     private int bufferSize = 256;
/*     */ 
/*     */     
/*     */     File seedFile;
/*     */ 
/*     */     
/*     */     private final InputStream seedIn;
/*     */ 
/*     */     
/*     */     private final InputStream nextIn;
/*     */ 
/*     */     
/*     */     private OutputStream seedOut;
/*     */ 
/*     */     
/*     */     private boolean seedOutInitialized;
/*     */ 
/*     */     
/*     */     private volatile SecureRandom mixRandom;
/*     */ 
/*     */     
/*     */     private byte[] nextBuffer;
/*     */     
/*     */     private int buffered;
/*     */     
/*     */     private long lastRead;
/*     */     
/* 368 */     private int change_buffer = 0;
/*     */ 
/*     */     
/*     */     private static final int REQ_LIMIT_INC = 1000;
/*     */ 
/*     */     
/*     */     private static final int REQ_LIMIT_DEC = -100;
/*     */ 
/*     */     
/* 377 */     private final Object LOCK_GET_BYTES = new Object();
/*     */ 
/*     */     
/* 380 */     private final Object LOCK_GET_SEED = new Object();
/*     */ 
/*     */     
/* 383 */     private final Object LOCK_SET_SEED = new Object();
/*     */ 
/*     */     
/*     */     private RandomIO(File param1File1, File param1File2) throws IOException {
/* 387 */       this.seedFile = param1File1;
/* 388 */       this.seedIn = new FileInputStream(param1File1);
/* 389 */       this.nextIn = new FileInputStream(param1File2);
/* 390 */       this.nextBuffer = new byte[this.bufferSize];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private SecureRandom getMixRandom() {
/* 396 */       SecureRandom secureRandom = this.mixRandom;
/* 397 */       if (secureRandom == null) {
/* 398 */         synchronized (this.LOCK_GET_BYTES) {
/* 399 */           secureRandom = this.mixRandom;
/* 400 */           if (secureRandom == null) {
/* 401 */             secureRandom = new SecureRandom();
/*     */             try {
/* 403 */               byte[] arrayOfByte = new byte[20];
/* 404 */               readFully(this.nextIn, arrayOfByte);
/* 405 */               secureRandom.engineSetSeed(arrayOfByte);
/* 406 */             } catch (IOException iOException) {
/* 407 */               throw new ProviderException("init failed", iOException);
/*     */             } 
/* 409 */             this.mixRandom = secureRandom;
/*     */           } 
/*     */         } 
/*     */       }
/* 413 */       return secureRandom;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static void readFully(InputStream param1InputStream, byte[] param1ArrayOfbyte) throws IOException {
/* 421 */       int i = param1ArrayOfbyte.length;
/* 422 */       int j = 0;
/* 423 */       while (i > 0) {
/* 424 */         int k = param1InputStream.read(param1ArrayOfbyte, j, i);
/* 425 */         if (k <= 0) {
/* 426 */           throw new EOFException("File(s) closed?");
/*     */         }
/* 428 */         j += k;
/* 429 */         i -= k;
/*     */       } 
/* 431 */       if (i > 0) {
/* 432 */         throw new IOException("Could not read from file(s)");
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private byte[] implGenerateSeed(int param1Int) {
/* 438 */       synchronized (this.LOCK_GET_SEED) {
/*     */         
/* 440 */         byte[] arrayOfByte = new byte[param1Int];
/* 441 */         readFully(this.seedIn, arrayOfByte);
/* 442 */         return arrayOfByte;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void implSetSeed(byte[] param1ArrayOfbyte) {
/* 453 */       synchronized (this.LOCK_SET_SEED) {
/* 454 */         if (!this.seedOutInitialized) {
/* 455 */           this.seedOutInitialized = true;
/* 456 */           this.seedOut = AccessController.<OutputStream>doPrivileged(new PrivilegedAction<OutputStream>()
/*     */               {
/*     */                 public OutputStream run()
/*     */                 {
/*     */                   try {
/* 461 */                     return new FileOutputStream(NativePRNG.RandomIO.this.seedFile, true);
/* 462 */                   } catch (Exception exception) {
/* 463 */                     return null;
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/* 468 */         if (this.seedOut != null) {
/*     */           try {
/* 470 */             this.seedOut.write(param1ArrayOfbyte);
/* 471 */           } catch (IOException iOException) {}
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 476 */         getMixRandom().engineSetSeed(param1ArrayOfbyte);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void ensureBufferValid() throws IOException {
/* 483 */       long l = System.currentTimeMillis();
/* 484 */       int i = 0;
/*     */ 
/*     */       
/* 487 */       if (this.buffered > 0) {
/* 488 */         if (l - this.lastRead < 100L) {
/*     */           return;
/*     */         }
/*     */         
/* 492 */         this.change_buffer--;
/*     */       }
/*     */       else {
/*     */         
/* 496 */         this.change_buffer++;
/*     */       } 
/*     */ 
/*     */       
/* 500 */       if (this.change_buffer > 1000) {
/* 501 */         i = this.nextBuffer.length * 2;
/* 502 */       } else if (this.change_buffer < -100) {
/* 503 */         i = this.nextBuffer.length / 2;
/*     */       } 
/*     */ 
/*     */       
/* 507 */       if (i > 0) {
/* 508 */         if (i <= 65536 && i >= 32) {
/*     */           
/* 510 */           this.nextBuffer = new byte[i];
/* 511 */           if (NativePRNG.debug != null) {
/* 512 */             NativePRNG.debug.println("Buffer size changed to " + i);
/*     */           
/*     */           }
/*     */         }
/* 516 */         else if (NativePRNG.debug != null) {
/* 517 */           NativePRNG.debug.println("Buffer reached limit: " + this.nextBuffer.length);
/*     */         } 
/*     */ 
/*     */         
/* 521 */         this.change_buffer = 0;
/*     */       } 
/*     */ 
/*     */       
/* 525 */       this.lastRead = l;
/* 526 */       readFully(this.nextIn, this.nextBuffer);
/* 527 */       this.buffered = this.nextBuffer.length;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void implNextBytes(byte[] param1ArrayOfbyte) {
/*     */       try {
/* 535 */         getMixRandom().engineNextBytes(param1ArrayOfbyte);
/* 536 */         int i = param1ArrayOfbyte.length;
/* 537 */         byte b = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 543 */         while (i > 0) {
/* 544 */           int j; byte[] arrayOfByte; synchronized (this.LOCK_GET_BYTES) {
/* 545 */             ensureBufferValid();
/* 546 */             int k = this.nextBuffer.length - this.buffered;
/* 547 */             if (i > this.buffered) {
/* 548 */               j = this.buffered;
/* 549 */               this.buffered = 0;
/*     */             } else {
/* 551 */               j = i;
/* 552 */               this.buffered -= j;
/*     */             } 
/* 554 */             arrayOfByte = Arrays.copyOfRange(this.nextBuffer, k, k + j);
/*     */           } 
/*     */           
/* 557 */           byte b1 = 0;
/* 558 */           while (j > b1) {
/* 559 */             param1ArrayOfbyte[b] = (byte)(param1ArrayOfbyte[b] ^ arrayOfByte[b1]);
/* 560 */             b++;
/* 561 */             b1++;
/*     */           } 
/* 563 */           i -= j;
/*     */         } 
/* 565 */       } catch (IOException iOException) {
/* 566 */         throw new ProviderException("nextBytes() failed", iOException);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/NativePRNG.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */