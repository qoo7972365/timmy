/*     */ package javax.security.auth.x500;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.NotActiveException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.Principal;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ResourcesMgr;
/*     */ import sun.security.x509.X500Name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class X500Principal
/*     */   implements Principal, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -500463348111345721L;
/*     */   public static final String RFC1779 = "RFC1779";
/*     */   public static final String RFC2253 = "RFC2253";
/*     */   public static final String CANONICAL = "CANONICAL";
/*     */   private transient X500Name thisX500Name;
/*     */   
/*     */   X500Principal(X500Name paramX500Name) {
/*  96 */     this.thisX500Name = paramX500Name;
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
/*     */   public X500Principal(String paramString) {
/* 128 */     this(paramString, Collections.emptyMap());
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
/*     */   
/*     */   public X500Principal(String paramString, Map<String, String> paramMap) {
/* 169 */     if (paramString == null) {
/* 170 */       throw new NullPointerException(
/*     */           
/* 172 */           ResourcesMgr.getString("provided.null.name"));
/*     */     }
/* 174 */     if (paramMap == null) {
/* 175 */       throw new NullPointerException(
/*     */           
/* 177 */           ResourcesMgr.getString("provided.null.keyword.map"));
/*     */     }
/*     */     
/*     */     try {
/* 181 */       this.thisX500Name = new X500Name(paramString, paramMap);
/* 182 */     } catch (Exception exception) {
/* 183 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("improperly specified input name: " + paramString);
/*     */       
/* 185 */       illegalArgumentException.initCause(exception);
/* 186 */       throw illegalArgumentException;
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
/*     */   public X500Principal(byte[] paramArrayOfbyte) {
/*     */     try {
/* 226 */       this.thisX500Name = new X500Name(paramArrayOfbyte);
/* 227 */     } catch (Exception exception) {
/* 228 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("improperly specified input name");
/*     */       
/* 230 */       illegalArgumentException.initCause(exception);
/* 231 */       throw illegalArgumentException;
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
/*     */   public X500Principal(InputStream paramInputStream) {
/* 254 */     if (paramInputStream == null) {
/* 255 */       throw new NullPointerException("provided null input stream");
/*     */     }
/*     */     
/*     */     try {
/* 259 */       if (paramInputStream.markSupported())
/* 260 */         paramInputStream.mark(paramInputStream.available() + 1); 
/* 261 */       DerValue derValue = new DerValue(paramInputStream);
/* 262 */       this.thisX500Name = new X500Name(derValue.data);
/* 263 */     } catch (Exception exception) {
/* 264 */       if (paramInputStream.markSupported()) {
/*     */         try {
/* 266 */           paramInputStream.reset();
/* 267 */         } catch (IOException iOException) {
/* 268 */           IllegalArgumentException illegalArgumentException1 = new IllegalArgumentException("improperly specified input stream and unable to reset input stream");
/*     */ 
/*     */           
/* 271 */           illegalArgumentException1.initCause(exception);
/* 272 */           throw illegalArgumentException1;
/*     */         } 
/*     */       }
/* 275 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("improperly specified input stream");
/*     */       
/* 277 */       illegalArgumentException.initCause(exception);
/* 278 */       throw illegalArgumentException;
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
/*     */   public String getName() {
/* 292 */     return getName("RFC2253");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName(String paramString) {
/* 358 */     if (paramString != null) {
/* 359 */       if (paramString.equalsIgnoreCase("RFC1779"))
/* 360 */         return this.thisX500Name.getRFC1779Name(); 
/* 361 */       if (paramString.equalsIgnoreCase("RFC2253"))
/* 362 */         return this.thisX500Name.getRFC2253Name(); 
/* 363 */       if (paramString.equalsIgnoreCase("CANONICAL")) {
/* 364 */         return this.thisX500Name.getRFC2253CanonicalName();
/*     */       }
/*     */     } 
/* 367 */     throw new IllegalArgumentException("invalid format specified");
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
/*     */   public String getName(String paramString, Map<String, String> paramMap) {
/* 405 */     if (paramMap == null) {
/* 406 */       throw new NullPointerException(
/*     */           
/* 408 */           ResourcesMgr.getString("provided.null.OID.map"));
/*     */     }
/* 410 */     if (paramString != null) {
/* 411 */       if (paramString.equalsIgnoreCase("RFC1779"))
/* 412 */         return this.thisX500Name.getRFC1779Name(paramMap); 
/* 413 */       if (paramString.equalsIgnoreCase("RFC2253")) {
/* 414 */         return this.thisX500Name.getRFC2253Name(paramMap);
/*     */       }
/*     */     } 
/* 417 */     throw new IllegalArgumentException("invalid format specified");
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
/*     */   public byte[] getEncoded() {
/*     */     try {
/* 433 */       return this.thisX500Name.getEncoded();
/* 434 */     } catch (IOException iOException) {
/* 435 */       throw new RuntimeException("unable to get encoding", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 446 */     return this.thisX500Name.toString();
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
/*     */   public boolean equals(Object paramObject) {
/* 468 */     if (this == paramObject) {
/* 469 */       return true;
/*     */     }
/* 471 */     if (!(paramObject instanceof X500Principal)) {
/* 472 */       return false;
/*     */     }
/* 474 */     X500Principal x500Principal = (X500Principal)paramObject;
/* 475 */     return this.thisX500Name.equals(x500Principal.thisX500Name);
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
/*     */   public int hashCode() {
/* 487 */     return this.thisX500Name.hashCode();
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 499 */     paramObjectOutputStream.writeObject(this.thisX500Name.getEncodedInternal());
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, NotActiveException, ClassNotFoundException {
/* 511 */     this.thisX500Name = new X500Name((byte[])paramObjectInputStream.readObject());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/x500/X500Principal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */