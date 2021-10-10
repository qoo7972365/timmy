/*     */ package java.security;
/*     */ 
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import java.util.Locale;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyRep
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4757683898830641853L;
/*     */   private static final String PKCS8 = "PKCS#8";
/*     */   private static final String X509 = "X.509";
/*     */   private static final String RAW = "RAW";
/*     */   private Type type;
/*     */   private String algorithm;
/*     */   private String format;
/*     */   private byte[] encoded;
/*     */   
/*     */   public enum Type
/*     */   {
/*  70 */     SECRET,
/*     */ 
/*     */     
/*  73 */     PUBLIC,
/*     */ 
/*     */     
/*  76 */     PRIVATE;
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
/*     */   public KeyRep(Type paramType, String paramString1, String paramString2, byte[] paramArrayOfbyte) {
/* 134 */     if (paramType == null || paramString1 == null || paramString2 == null || paramArrayOfbyte == null)
/*     */     {
/* 136 */       throw new NullPointerException("invalid null input(s)");
/*     */     }
/*     */     
/* 139 */     this.type = paramType;
/* 140 */     this.algorithm = paramString1;
/* 141 */     this.format = paramString2.toUpperCase(Locale.ENGLISH);
/* 142 */     this.encoded = (byte[])paramArrayOfbyte.clone();
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
/*     */   protected Object readResolve() throws ObjectStreamException {
/*     */     try {
/* 171 */       if (this.type == Type.SECRET && "RAW".equals(this.format))
/* 172 */         return new SecretKeySpec(this.encoded, this.algorithm); 
/* 173 */       if (this.type == Type.PUBLIC && "X.509".equals(this.format)) {
/* 174 */         KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
/* 175 */         return keyFactory.generatePublic(new X509EncodedKeySpec(this.encoded));
/* 176 */       }  if (this.type == Type.PRIVATE && "PKCS#8".equals(this.format)) {
/* 177 */         KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
/* 178 */         return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(this.encoded));
/*     */       } 
/* 180 */       throw new NotSerializableException("unrecognized type/format combination: " + this.type + "/" + this.format);
/*     */ 
/*     */     
/*     */     }
/* 184 */     catch (NotSerializableException notSerializableException) {
/* 185 */       throw notSerializableException;
/* 186 */     } catch (Exception exception) {
/* 187 */       NotSerializableException notSerializableException = new NotSerializableException("java.security.Key: [" + this.type + "] [" + this.algorithm + "] [" + this.format + "]");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       notSerializableException.initCause(exception);
/* 193 */       throw notSerializableException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/KeyRep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */