/*     */ package java.security.cert;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.misc.IOUtils;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ import sun.security.x509.Extension;
/*     */ import sun.security.x509.InvalidityDateExtension;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificateRevokedException
/*     */   extends CertificateException
/*     */ {
/*     */   private static final long serialVersionUID = 7839996631571608627L;
/*     */   private Date revocationDate;
/*     */   private final CRLReason reason;
/*     */   private final X500Principal authority;
/*     */   private transient Map<String, Extension> extensions;
/*     */   
/*     */   public CertificateRevokedException(Date paramDate, CRLReason paramCRLReason, X500Principal paramX500Principal, Map<String, Extension> paramMap) {
/*  91 */     if (paramDate == null || paramCRLReason == null || paramX500Principal == null || paramMap == null)
/*     */     {
/*  93 */       throw new NullPointerException();
/*     */     }
/*  95 */     this.revocationDate = new Date(paramDate.getTime());
/*  96 */     this.reason = paramCRLReason;
/*  97 */     this.authority = paramX500Principal;
/*     */     
/*  99 */     this.extensions = Collections.checkedMap(new HashMap<>(), String.class, Extension.class);
/*     */     
/* 101 */     this.extensions.putAll(paramMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getRevocationDate() {
/* 112 */     return (Date)this.revocationDate.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CRLReason getRevocationReason() {
/* 121 */     return this.reason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X500Principal getAuthorityName() {
/* 132 */     return this.authority;
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
/*     */   public Date getInvalidityDate() {
/* 149 */     Extension extension = getExtensions().get("2.5.29.24");
/* 150 */     if (extension == null) {
/* 151 */       return null;
/*     */     }
/*     */     try {
/* 154 */       Date date = InvalidityDateExtension.toImpl(extension).get("DATE");
/* 155 */       return new Date(date.getTime());
/* 156 */     } catch (IOException iOException) {
/* 157 */       return null;
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
/*     */   public Map<String, Extension> getExtensions() {
/* 172 */     return Collections.unmodifiableMap(this.extensions);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 177 */     return "Certificate has been revoked, reason: " + this.reason + ", revocation date: " + this.revocationDate + ", authority: " + this.authority + ", extension OIDs: " + this.extensions
/*     */ 
/*     */       
/* 180 */       .keySet();
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 195 */     paramObjectOutputStream.defaultWriteObject();
/*     */ 
/*     */     
/* 198 */     paramObjectOutputStream.writeInt(this.extensions.size());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     for (Map.Entry<String, Extension> entry : this.extensions.entrySet()) {
/* 206 */       Extension extension = (Extension)entry.getValue();
/* 207 */       paramObjectOutputStream.writeObject(extension.getId());
/* 208 */       paramObjectOutputStream.writeBoolean(extension.isCritical());
/* 209 */       byte[] arrayOfByte = extension.getValue();
/* 210 */       paramObjectOutputStream.writeInt(arrayOfByte.length);
/* 211 */       paramObjectOutputStream.write(arrayOfByte);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 222 */     paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */     
/* 225 */     this.revocationDate = new Date(this.revocationDate.getTime());
/*     */ 
/*     */ 
/*     */     
/* 229 */     int i = paramObjectInputStream.readInt();
/* 230 */     if (i == 0)
/* 231 */     { this.extensions = Collections.emptyMap(); }
/* 232 */     else { if (i < 0) {
/* 233 */         throw new IOException("size cannot be negative");
/*     */       }
/* 235 */       this.extensions = new HashMap<>((i > 20) ? 20 : i); }
/*     */ 
/*     */ 
/*     */     
/* 239 */     for (byte b = 0; b < i; b++) {
/* 240 */       String str = (String)paramObjectInputStream.readObject();
/* 241 */       boolean bool = paramObjectInputStream.readBoolean();
/* 242 */       byte[] arrayOfByte = IOUtils.readExactlyNBytes(paramObjectInputStream, paramObjectInputStream.readInt());
/*     */       
/* 244 */       Extension extension = Extension.newExtension(new ObjectIdentifier(str), bool, arrayOfByte);
/* 245 */       this.extensions.put(str, extension);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/CertificateRevokedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */