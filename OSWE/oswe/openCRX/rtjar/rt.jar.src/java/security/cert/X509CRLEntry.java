/*     */ package java.security.cert;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.Date;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.x509.X509CRLEntryImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class X509CRLEntry
/*     */   implements X509Extension
/*     */ {
/*     */   public boolean equals(Object paramObject) {
/*  81 */     if (this == paramObject)
/*  82 */       return true; 
/*  83 */     if (!(paramObject instanceof X509CRLEntry))
/*  84 */       return false; 
/*     */     try {
/*  86 */       byte[] arrayOfByte1 = getEncoded();
/*  87 */       byte[] arrayOfByte2 = ((X509CRLEntry)paramObject).getEncoded();
/*     */       
/*  89 */       if (arrayOfByte1.length != arrayOfByte2.length)
/*  90 */         return false; 
/*  91 */       for (byte b = 0; b < arrayOfByte1.length; b++)
/*  92 */       { if (arrayOfByte1[b] != arrayOfByte2[b])
/*  93 */           return false;  } 
/*  94 */     } catch (CRLException cRLException) {
/*  95 */       return false;
/*     */     } 
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     int i = 0;
/*     */     try {
/* 109 */       byte[] arrayOfByte = getEncoded();
/* 110 */       for (byte b = 1; b < arrayOfByte.length; b++) {
/* 111 */         i += arrayOfByte[b] * b;
/*     */       }
/* 113 */     } catch (CRLException cRLException) {
/* 114 */       return i;
/*     */     } 
/* 116 */     return i;
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
/*     */   public abstract byte[] getEncoded() throws CRLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract BigInteger getSerialNumber();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X500Principal getCertificateIssuer() {
/* 151 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Date getRevocationDate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean hasExtensions();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String toString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CRLReason getRevocationReason() {
/* 186 */     if (!hasExtensions()) {
/* 187 */       return null;
/*     */     }
/* 189 */     return X509CRLEntryImpl.getRevocationReason(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/X509CRLEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */