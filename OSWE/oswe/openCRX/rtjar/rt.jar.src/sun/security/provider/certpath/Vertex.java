/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.x509.AuthorityKeyIdentifierExtension;
/*     */ import sun.security.x509.KeyIdentifier;
/*     */ import sun.security.x509.SubjectKeyIdentifierExtension;
/*     */ import sun.security.x509.X509CertImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vertex
/*     */ {
/*  51 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */   
/*     */   private X509Certificate cert;
/*     */ 
/*     */   
/*     */   private int index;
/*     */   
/*     */   private Throwable throwable;
/*     */ 
/*     */   
/*     */   Vertex(X509Certificate paramX509Certificate) {
/*  63 */     this.cert = paramX509Certificate;
/*  64 */     this.index = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate getCertificate() {
/*  73 */     return this.cert;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/*  84 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setIndex(int paramInt) {
/*  95 */     this.index = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getThrowable() {
/* 105 */     return this.throwable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setThrowable(Throwable paramThrowable) {
/* 115 */     this.throwable = paramThrowable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 125 */     return certToString() + throwableToString() + indexToString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String certToString() {
/* 135 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 137 */     X509CertImpl x509CertImpl = null;
/*     */     try {
/* 139 */       x509CertImpl = X509CertImpl.toImpl(this.cert);
/* 140 */     } catch (CertificateException certificateException) {
/* 141 */       if (debug != null) {
/* 142 */         debug.println("Vertex.certToString() unexpected exception");
/* 143 */         certificateException.printStackTrace();
/*     */       } 
/* 145 */       return stringBuilder.toString();
/*     */     } 
/*     */     
/* 148 */     stringBuilder.append("Issuer:     ")
/* 149 */       .append(x509CertImpl.getIssuerX500Principal()).append("\n");
/* 150 */     stringBuilder.append("Subject:    ")
/* 151 */       .append(x509CertImpl.getSubjectX500Principal()).append("\n");
/* 152 */     stringBuilder.append("SerialNum:  ")
/* 153 */       .append(x509CertImpl.getSerialNumber().toString(16)).append("\n");
/* 154 */     stringBuilder.append("Expires:    ")
/* 155 */       .append(x509CertImpl.getNotAfter().toString()).append("\n");
/* 156 */     boolean[] arrayOfBoolean1 = x509CertImpl.getIssuerUniqueID();
/* 157 */     if (arrayOfBoolean1 != null) {
/* 158 */       stringBuilder.append("IssuerUID:  ");
/* 159 */       for (boolean bool : arrayOfBoolean1) {
/* 160 */         stringBuilder.append(bool ? 1 : 0);
/*     */       }
/* 162 */       stringBuilder.append("\n");
/*     */     } 
/* 164 */     boolean[] arrayOfBoolean2 = x509CertImpl.getSubjectUniqueID();
/* 165 */     if (arrayOfBoolean2 != null) {
/* 166 */       stringBuilder.append("SubjectUID: ");
/* 167 */       for (boolean bool : arrayOfBoolean2) {
/* 168 */         stringBuilder.append(bool ? 1 : 0);
/*     */       }
/* 170 */       stringBuilder.append("\n");
/*     */     } 
/*     */     
/*     */     try {
/* 174 */       SubjectKeyIdentifierExtension subjectKeyIdentifierExtension = x509CertImpl.getSubjectKeyIdentifierExtension();
/* 175 */       if (subjectKeyIdentifierExtension != null) {
/* 176 */         KeyIdentifier keyIdentifier = subjectKeyIdentifierExtension.get("key_id");
/*     */         
/* 178 */         stringBuilder.append("SubjKeyID:  ").append(keyIdentifier.toString());
/*     */       } 
/*     */       
/* 181 */       AuthorityKeyIdentifierExtension authorityKeyIdentifierExtension = x509CertImpl.getAuthorityKeyIdentifierExtension();
/* 182 */       if (authorityKeyIdentifierExtension != null) {
/* 183 */         KeyIdentifier keyIdentifier = (KeyIdentifier)authorityKeyIdentifierExtension.get("key_id");
/*     */         
/* 185 */         stringBuilder.append("AuthKeyID:  ").append(keyIdentifier.toString());
/*     */       } 
/* 187 */     } catch (IOException iOException) {
/* 188 */       if (debug != null) {
/* 189 */         debug.println("Vertex.certToString() unexpected exception");
/* 190 */         iOException.printStackTrace();
/*     */       } 
/*     */     } 
/* 193 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String throwableToString() {
/* 203 */     StringBuilder stringBuilder = new StringBuilder("Exception:  ");
/* 204 */     if (this.throwable != null) {
/* 205 */       stringBuilder.append(this.throwable.toString());
/*     */     } else {
/* 207 */       stringBuilder.append("null");
/* 208 */     }  stringBuilder.append("\n");
/* 209 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String moreToString() {
/* 220 */     StringBuilder stringBuilder = new StringBuilder("Last cert?  ");
/* 221 */     stringBuilder.append((this.index == -1) ? "Yes" : "No");
/* 222 */     stringBuilder.append("\n");
/* 223 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String indexToString() {
/* 233 */     return "Index:      " + this.index + "\n";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/Vertex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */