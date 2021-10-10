/*     */ package java.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.Date;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Timestamp
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5502683707821851294L;
/*     */   private Date timestamp;
/*     */   private CertPath signerCertPath;
/*  66 */   private transient int myhash = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp(Date paramDate, CertPath paramCertPath) {
/*  76 */     if (paramDate == null || paramCertPath == null) {
/*  77 */       throw new NullPointerException();
/*     */     }
/*  79 */     this.timestamp = new Date(paramDate.getTime());
/*  80 */     this.signerCertPath = paramCertPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getTimestamp() {
/*  89 */     return new Date(this.timestamp.getTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertPath getSignerCertPath() {
/*  98 */     return this.signerCertPath;
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
/* 109 */     if (this.myhash == -1) {
/* 110 */       this.myhash = this.timestamp.hashCode() + this.signerCertPath.hashCode();
/*     */     }
/* 112 */     return this.myhash;
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
/*     */   public boolean equals(Object paramObject) {
/* 125 */     if (paramObject == null || !(paramObject instanceof Timestamp)) {
/* 126 */       return false;
/*     */     }
/* 128 */     Timestamp timestamp = (Timestamp)paramObject;
/*     */     
/* 130 */     if (this == timestamp) {
/* 131 */       return true;
/*     */     }
/* 133 */     return (this.timestamp.equals(timestamp.getTimestamp()) && this.signerCertPath
/* 134 */       .equals(timestamp.getSignerCertPath()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 144 */     StringBuffer stringBuffer = new StringBuffer();
/* 145 */     stringBuffer.append("(");
/* 146 */     stringBuffer.append("timestamp: " + this.timestamp);
/* 147 */     List<? extends Certificate> list = this.signerCertPath.getCertificates();
/* 148 */     if (!list.isEmpty()) {
/* 149 */       stringBuffer.append("TSA: " + list.get(0));
/*     */     } else {
/* 151 */       stringBuffer.append("TSA: <empty>");
/*     */     } 
/* 153 */     stringBuffer.append(")");
/* 154 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 160 */     paramObjectInputStream.defaultReadObject();
/* 161 */     this.myhash = -1;
/* 162 */     this.timestamp = new Date(this.timestamp.getTime());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/Timestamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */