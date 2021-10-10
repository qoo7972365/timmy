/*     */ package java.security.cert;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CertPath
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6068470306649138683L;
/*     */   private String type;
/*     */   
/*     */   protected CertPath(String paramString) {
/* 136 */     this.type = paramString;
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
/*     */   public String getType() {
/* 149 */     return this.type;
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
/*     */   public abstract Iterator<String> getEncodings();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 179 */     if (this == paramObject) {
/* 180 */       return true;
/*     */     }
/* 182 */     if (!(paramObject instanceof CertPath)) {
/* 183 */       return false;
/*     */     }
/* 185 */     CertPath certPath = (CertPath)paramObject;
/* 186 */     if (!certPath.getType().equals(this.type)) {
/* 187 */       return false;
/*     */     }
/* 189 */     List<? extends Certificate> list1 = getCertificates();
/* 190 */     List<? extends Certificate> list2 = certPath.getCertificates();
/* 191 */     return list1.equals(list2);
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
/*     */   public int hashCode() {
/* 210 */     int i = this.type.hashCode();
/* 211 */     i = 31 * i + getCertificates().hashCode();
/* 212 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 223 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 225 */     Iterator<? extends Certificate> iterator = getCertificates().iterator();
/*     */     
/* 227 */     stringBuffer.append("\n" + this.type + " Cert Path: length = " + 
/* 228 */         getCertificates().size() + ".\n");
/* 229 */     stringBuffer.append("[\n");
/* 230 */     byte b = 1;
/* 231 */     while (iterator.hasNext()) {
/* 232 */       stringBuffer.append("=========================================================Certificate " + b + " start.\n");
/*     */       
/* 234 */       Certificate certificate = iterator.next();
/* 235 */       stringBuffer.append(certificate.toString());
/* 236 */       stringBuffer.append("\n=========================================================Certificate " + b + " end.\n\n\n");
/*     */       
/* 238 */       b++;
/*     */     } 
/*     */     
/* 241 */     stringBuffer.append("\n]");
/* 242 */     return stringBuffer.toString();
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
/*     */   public abstract byte[] getEncoded() throws CertificateEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract byte[] getEncoded(String paramString) throws CertificateEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract List<? extends Certificate> getCertificates();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object writeReplace() throws ObjectStreamException {
/*     */     try {
/* 287 */       return new CertPathRep(this.type, getEncoded());
/* 288 */     } catch (CertificateException certificateException) {
/* 289 */       NotSerializableException notSerializableException = new NotSerializableException("java.security.cert.CertPath: " + this.type);
/*     */ 
/*     */       
/* 292 */       notSerializableException.initCause(certificateException);
/* 293 */       throw notSerializableException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class CertPathRep
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 3015633072427920915L;
/*     */ 
/*     */ 
/*     */     
/*     */     private String type;
/*     */ 
/*     */ 
/*     */     
/*     */     private byte[] data;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected CertPathRep(String param1String, byte[] param1ArrayOfbyte) {
/* 318 */       this.type = param1String;
/* 319 */       this.data = param1ArrayOfbyte;
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
/*     */     protected Object readResolve() throws ObjectStreamException {
/*     */       try {
/* 332 */         CertificateFactory certificateFactory = CertificateFactory.getInstance(this.type);
/* 333 */         return certificateFactory.generateCertPath(new ByteArrayInputStream(this.data));
/* 334 */       } catch (CertificateException certificateException) {
/* 335 */         NotSerializableException notSerializableException = new NotSerializableException("java.security.cert.CertPath: " + this.type);
/*     */ 
/*     */         
/* 338 */         notSerializableException.initCause(certificateException);
/* 339 */         throw notSerializableException;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/CertPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */