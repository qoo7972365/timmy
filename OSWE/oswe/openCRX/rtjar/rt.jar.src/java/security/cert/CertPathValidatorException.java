/*     */ package java.security.cert;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.GeneralSecurityException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertPathValidatorException
/*     */   extends GeneralSecurityException
/*     */ {
/*     */   private static final long serialVersionUID = -3083180014971893139L;
/*  70 */   private int index = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CertPath certPath;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private Reason reason = BasicReason.UNSPECIFIED;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertPathValidatorException() {
/*  88 */     this(null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertPathValidatorException(String paramString) {
/*  99 */     this(paramString, null);
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
/*     */   public CertPathValidatorException(Throwable paramThrowable) {
/* 116 */     this((paramThrowable == null) ? null : paramThrowable.toString(), paramThrowable);
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
/*     */   public CertPathValidatorException(String paramString, Throwable paramThrowable) {
/* 129 */     this(paramString, paramThrowable, null, -1);
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
/*     */   public CertPathValidatorException(String paramString, Throwable paramThrowable, CertPath paramCertPath, int paramInt) {
/* 151 */     this(paramString, paramThrowable, paramCertPath, paramInt, BasicReason.UNSPECIFIED);
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
/*     */   public CertPathValidatorException(String paramString, Throwable paramThrowable, CertPath paramCertPath, int paramInt, Reason paramReason) {
/* 177 */     super(paramString, paramThrowable);
/* 178 */     if (paramCertPath == null && paramInt != -1) {
/* 179 */       throw new IllegalArgumentException();
/*     */     }
/* 181 */     if (paramInt < -1 || (paramCertPath != null && paramInt >= paramCertPath
/* 182 */       .getCertificates().size())) {
/* 183 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 185 */     if (paramReason == null) {
/* 186 */       throw new NullPointerException("reason can't be null");
/*     */     }
/* 188 */     this.certPath = paramCertPath;
/* 189 */     this.index = paramInt;
/* 190 */     this.reason = paramReason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertPath getCertPath() {
/* 201 */     return this.certPath;
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
/*     */   public int getIndex() {
/* 213 */     return this.index;
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
/*     */   public Reason getReason() {
/* 228 */     return this.reason;
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 233 */     paramObjectInputStream.defaultReadObject();
/* 234 */     if (this.reason == null) {
/* 235 */       this.reason = BasicReason.UNSPECIFIED;
/*     */     }
/* 237 */     if (this.certPath == null && this.index != -1) {
/* 238 */       throw new InvalidObjectException("certpath is null and index != -1");
/*     */     }
/* 240 */     if (this.index < -1 || (this.certPath != null && this.index >= this.certPath
/* 241 */       .getCertificates().size())) {
/* 242 */       throw new InvalidObjectException("index out of range");
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
/*     */   public enum BasicReason
/*     */     implements Reason
/*     */   {
/* 264 */     UNSPECIFIED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     EXPIRED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 274 */     NOT_YET_VALID,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     REVOKED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     UNDETERMINED_REVOCATION_STATUS,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     INVALID_SIGNATURE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     ALGORITHM_CONSTRAINED;
/*     */   }
/*     */   
/*     */   public static interface Reason extends Serializable {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/CertPathValidatorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */