/*     */ package java.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.cert.CertPath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CodeSigner
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6819288105193937581L;
/*     */   private CertPath signerCertPath;
/*     */   private Timestamp timestamp;
/*  60 */   private transient int myhash = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CodeSigner(CertPath paramCertPath, Timestamp paramTimestamp) {
/*  74 */     if (paramCertPath == null) {
/*  75 */       throw new NullPointerException();
/*     */     }
/*  77 */     this.signerCertPath = paramCertPath;
/*  78 */     this.timestamp = paramTimestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertPath getSignerCertPath() {
/*  87 */     return this.signerCertPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp getTimestamp() {
/*  96 */     return this.timestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     if (this.myhash == -1) {
/* 108 */       if (this.timestamp == null) {
/* 109 */         this.myhash = this.signerCertPath.hashCode();
/*     */       } else {
/* 111 */         this.myhash = this.signerCertPath.hashCode() + this.timestamp.hashCode();
/*     */       } 
/*     */     }
/* 114 */     return this.myhash;
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
/*     */   public boolean equals(Object paramObject) {
/* 128 */     if (paramObject == null || !(paramObject instanceof CodeSigner)) {
/* 129 */       return false;
/*     */     }
/* 131 */     CodeSigner codeSigner = (CodeSigner)paramObject;
/*     */     
/* 133 */     if (this == codeSigner) {
/* 134 */       return true;
/*     */     }
/* 136 */     Timestamp timestamp = codeSigner.getTimestamp();
/* 137 */     if (this.timestamp == null) {
/* 138 */       if (timestamp != null) {
/* 139 */         return false;
/*     */       }
/*     */     }
/* 142 */     else if (timestamp == null || 
/* 143 */       !this.timestamp.equals(timestamp)) {
/* 144 */       return false;
/*     */     } 
/*     */     
/* 147 */     return this.signerCertPath.equals(codeSigner.getSignerCertPath());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 157 */     StringBuffer stringBuffer = new StringBuffer();
/* 158 */     stringBuffer.append("(");
/* 159 */     stringBuffer.append("Signer: " + this.signerCertPath.getCertificates().get(0));
/* 160 */     if (this.timestamp != null) {
/* 161 */       stringBuffer.append("timestamp: " + this.timestamp);
/*     */     }
/* 163 */     stringBuffer.append(")");
/* 164 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 170 */     paramObjectInputStream.defaultReadObject();
/* 171 */     this.myhash = -1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/CodeSigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */