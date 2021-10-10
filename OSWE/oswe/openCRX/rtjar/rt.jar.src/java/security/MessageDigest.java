/*     */ package java.security;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MessageDigest
/*     */   extends MessageDigestSpi
/*     */ {
/* 109 */   private static final Debug pdebug = Debug.getInstance("provider", "Provider");
/* 110 */   private static final boolean skipDebug = (
/* 111 */     Debug.isOn("engine=") && !Debug.isOn("messagedigest"));
/*     */   
/*     */   private String algorithm;
/*     */   
/*     */   private static final int INITIAL = 0;
/*     */   
/*     */   private static final int IN_PROGRESS = 1;
/* 118 */   private int state = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Provider provider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MessageDigest(String paramString) {
/* 133 */     this.algorithm = paramString;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MessageDigest getInstance(String paramString) throws NoSuchAlgorithmException {
/*     */     try {
/*     */       MessageDigest messageDigest;
/* 167 */       Object[] arrayOfObject = Security.getImpl(paramString, "MessageDigest", (String)null);
/*     */       
/* 169 */       if (arrayOfObject[0] instanceof MessageDigest) {
/* 170 */         messageDigest = (MessageDigest)arrayOfObject[0];
/*     */       } else {
/* 172 */         messageDigest = new Delegate((MessageDigestSpi)arrayOfObject[0], paramString);
/*     */       } 
/* 174 */       messageDigest.provider = (Provider)arrayOfObject[1];
/*     */       
/* 176 */       if (!skipDebug && pdebug != null) {
/* 177 */         pdebug.println("MessageDigest." + paramString + " algorithm from: " + messageDigest.provider
/* 178 */             .getName());
/*     */       }
/*     */       
/* 181 */       return messageDigest;
/*     */     }
/* 183 */     catch (NoSuchProviderException noSuchProviderException) {
/* 184 */       throw new NoSuchAlgorithmException(paramString + " not found");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MessageDigest getInstance(String paramString1, String paramString2) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 225 */     if (paramString2 == null || paramString2.length() == 0)
/* 226 */       throw new IllegalArgumentException("missing provider"); 
/* 227 */     Object[] arrayOfObject = Security.getImpl(paramString1, "MessageDigest", paramString2);
/* 228 */     if (arrayOfObject[0] instanceof MessageDigest) {
/* 229 */       MessageDigest messageDigest = (MessageDigest)arrayOfObject[0];
/* 230 */       messageDigest.provider = (Provider)arrayOfObject[1];
/* 231 */       return messageDigest;
/*     */     } 
/* 233 */     Delegate delegate = new Delegate((MessageDigestSpi)arrayOfObject[0], paramString1);
/*     */     
/* 235 */     delegate.provider = (Provider)arrayOfObject[1];
/* 236 */     return delegate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MessageDigest getInstance(String paramString, Provider paramProvider) throws NoSuchAlgorithmException {
/* 273 */     if (paramProvider == null)
/* 274 */       throw new IllegalArgumentException("missing provider"); 
/* 275 */     Object[] arrayOfObject = Security.getImpl(paramString, "MessageDigest", paramProvider);
/* 276 */     if (arrayOfObject[0] instanceof MessageDigest) {
/* 277 */       MessageDigest messageDigest = (MessageDigest)arrayOfObject[0];
/* 278 */       messageDigest.provider = (Provider)arrayOfObject[1];
/* 279 */       return messageDigest;
/*     */     } 
/* 281 */     Delegate delegate = new Delegate((MessageDigestSpi)arrayOfObject[0], paramString);
/*     */     
/* 283 */     delegate.provider = (Provider)arrayOfObject[1];
/* 284 */     return delegate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Provider getProvider() {
/* 294 */     return this.provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(byte paramByte) {
/* 303 */     engineUpdate(paramByte);
/* 304 */     this.state = 1;
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
/*     */   public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 319 */     if (paramArrayOfbyte == null) {
/* 320 */       throw new IllegalArgumentException("No input buffer given");
/*     */     }
/* 322 */     if (paramArrayOfbyte.length - paramInt1 < paramInt2) {
/* 323 */       throw new IllegalArgumentException("Input buffer too short");
/*     */     }
/* 325 */     engineUpdate(paramArrayOfbyte, paramInt1, paramInt2);
/* 326 */     this.state = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(byte[] paramArrayOfbyte) {
/* 335 */     engineUpdate(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/* 336 */     this.state = 1;
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
/*     */   public final void update(ByteBuffer paramByteBuffer) {
/* 350 */     if (paramByteBuffer == null) {
/* 351 */       throw new NullPointerException();
/*     */     }
/* 353 */     engineUpdate(paramByteBuffer);
/* 354 */     this.state = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] digest() {
/* 365 */     byte[] arrayOfByte = engineDigest();
/* 366 */     this.state = 0;
/* 367 */     return arrayOfByte;
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
/*     */   public int digest(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws DigestException {
/* 385 */     if (paramArrayOfbyte == null) {
/* 386 */       throw new IllegalArgumentException("No output buffer given");
/*     */     }
/* 388 */     if (paramArrayOfbyte.length - paramInt1 < paramInt2) {
/* 389 */       throw new IllegalArgumentException("Output buffer too small for specified offset and length");
/*     */     }
/*     */     
/* 392 */     int i = engineDigest(paramArrayOfbyte, paramInt1, paramInt2);
/* 393 */     this.state = 0;
/* 394 */     return i;
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
/*     */   public byte[] digest(byte[] paramArrayOfbyte) {
/* 410 */     update(paramArrayOfbyte);
/* 411 */     return digest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 418 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 419 */     PrintStream printStream = new PrintStream(byteArrayOutputStream);
/* 420 */     printStream.print(this.algorithm + " Message Digest from " + this.provider.getName() + ", ");
/* 421 */     switch (this.state) {
/*     */       case 0:
/* 423 */         printStream.print("<initialized>");
/*     */         break;
/*     */       case 1:
/* 426 */         printStream.print("<in progress>");
/*     */         break;
/*     */     } 
/* 429 */     printStream.println();
/* 430 */     return byteArrayOutputStream.toString();
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
/*     */   public static boolean isEqual(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 443 */     if (paramArrayOfbyte1 == paramArrayOfbyte2) return true; 
/* 444 */     if (paramArrayOfbyte1 == null || paramArrayOfbyte2 == null) {
/* 445 */       return false;
/*     */     }
/* 447 */     if (paramArrayOfbyte1.length != paramArrayOfbyte2.length) {
/* 448 */       return false;
/*     */     }
/*     */     
/* 451 */     int i = 0;
/*     */     
/* 453 */     for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
/* 454 */       i |= paramArrayOfbyte1[b] ^ paramArrayOfbyte2[b];
/*     */     }
/* 456 */     return (i == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 463 */     engineReset();
/* 464 */     this.state = 0;
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
/*     */   public final String getAlgorithm() {
/* 479 */     return this.algorithm;
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
/*     */   public final int getDigestLength() {
/* 492 */     int i = engineGetDigestLength();
/* 493 */     if (i == 0) {
/*     */       try {
/* 495 */         MessageDigest messageDigest = (MessageDigest)clone();
/* 496 */         byte[] arrayOfByte = messageDigest.digest();
/* 497 */         return arrayOfByte.length;
/* 498 */       } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 499 */         return i;
/*     */       } 
/*     */     }
/* 502 */     return i;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 514 */     if (this instanceof Cloneable) {
/* 515 */       return super.clone();
/*     */     }
/* 517 */     throw new CloneNotSupportedException();
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
/*     */   static class Delegate
/*     */     extends MessageDigest
/*     */   {
/*     */     private MessageDigestSpi digestSpi;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Delegate(MessageDigestSpi param1MessageDigestSpi, String param1String) {
/* 545 */       super(param1String);
/* 546 */       this.digestSpi = param1MessageDigestSpi;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object clone() throws CloneNotSupportedException {
/* 558 */       if (this.digestSpi instanceof Cloneable) {
/*     */         
/* 560 */         MessageDigestSpi messageDigestSpi = (MessageDigestSpi)this.digestSpi.clone();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 566 */         Delegate delegate = new Delegate(messageDigestSpi, this.algorithm);
/* 567 */         delegate.provider = this.provider;
/* 568 */         delegate.state = this.state;
/* 569 */         return delegate;
/*     */       } 
/* 571 */       throw new CloneNotSupportedException();
/*     */     }
/*     */ 
/*     */     
/*     */     protected int engineGetDigestLength() {
/* 576 */       return this.digestSpi.engineGetDigestLength();
/*     */     }
/*     */     
/*     */     protected void engineUpdate(byte param1Byte) {
/* 580 */       this.digestSpi.engineUpdate(param1Byte);
/*     */     }
/*     */     
/*     */     protected void engineUpdate(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/* 584 */       this.digestSpi.engineUpdate(param1ArrayOfbyte, param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     protected void engineUpdate(ByteBuffer param1ByteBuffer) {
/* 588 */       this.digestSpi.engineUpdate(param1ByteBuffer);
/*     */     }
/*     */     
/*     */     protected byte[] engineDigest() {
/* 592 */       return this.digestSpi.engineDigest();
/*     */     }
/*     */ 
/*     */     
/*     */     protected int engineDigest(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws DigestException {
/* 597 */       return this.digestSpi.engineDigest(param1ArrayOfbyte, param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     protected void engineReset() {
/* 601 */       this.digestSpi.engineReset();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/MessageDigest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */