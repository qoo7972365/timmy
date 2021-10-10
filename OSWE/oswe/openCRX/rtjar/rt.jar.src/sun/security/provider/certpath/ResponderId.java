/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.PublicKey;
/*     */ import java.util.Arrays;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.x509.KeyIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ResponderId
/*     */ {
/*     */   private Type type;
/*     */   private X500Principal responderName;
/*     */   private KeyIdentifier responderKeyId;
/*     */   private byte[] encodedRid;
/*     */   
/*     */   public enum Type
/*     */   {
/*  68 */     BY_NAME(1, "byName"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     BY_KEY(2, "byKey");
/*     */     
/*     */     private final int tagNumber;
/*     */     private final String ridTypeName;
/*     */     
/*     */     Type(int param1Int1, String param1String1) {
/*  81 */       this.tagNumber = param1Int1;
/*  82 */       this.ridTypeName = param1String1;
/*     */     }
/*     */     
/*     */     public int value() {
/*  86 */       return this.tagNumber;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  91 */       return this.ridTypeName;
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
/*     */   public ResponderId(X500Principal paramX500Principal) throws IOException {
/* 111 */     this.responderName = paramX500Principal;
/* 112 */     this.responderKeyId = null;
/* 113 */     this.encodedRid = principalToBytes();
/* 114 */     this.type = Type.BY_NAME;
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
/*     */   public ResponderId(PublicKey paramPublicKey) throws IOException {
/* 128 */     this.responderKeyId = new KeyIdentifier(paramPublicKey);
/* 129 */     this.responderName = null;
/* 130 */     this.encodedRid = keyIdToBytes();
/* 131 */     this.type = Type.BY_KEY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResponderId(byte[] paramArrayOfbyte) throws IOException {
/* 142 */     DerValue derValue = new DerValue(paramArrayOfbyte);
/*     */     
/* 144 */     if (derValue.isContextSpecific((byte)Type.BY_NAME.value()) && derValue
/* 145 */       .isConstructed()) {
/*     */ 
/*     */       
/* 148 */       this.responderName = new X500Principal(derValue.getDataBytes());
/* 149 */       this.encodedRid = principalToBytes();
/* 150 */       this.type = Type.BY_NAME;
/* 151 */     } else if (derValue.isContextSpecific((byte)Type.BY_KEY.value()) && derValue
/* 152 */       .isConstructed()) {
/*     */ 
/*     */       
/* 155 */       this
/* 156 */         .responderKeyId = new KeyIdentifier(new DerValue(derValue.getDataBytes()));
/* 157 */       this.encodedRid = keyIdToBytes();
/* 158 */       this.type = Type.BY_KEY;
/*     */     } else {
/* 160 */       throw new IOException("Invalid ResponderId content");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncoded() {
/* 171 */     return (byte[])this.encodedRid.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getType() {
/* 181 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/* 191 */     return this.encodedRid.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X500Principal getResponderName() {
/* 202 */     return this.responderName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyIdentifier getKeyIdentifier() {
/* 213 */     return this.responderKeyId;
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
/*     */   public boolean equals(Object paramObject) {
/* 229 */     if (paramObject == null) {
/* 230 */       return false;
/*     */     }
/*     */     
/* 233 */     if (this == paramObject) {
/* 234 */       return true;
/*     */     }
/*     */     
/* 237 */     if (paramObject instanceof ResponderId) {
/* 238 */       ResponderId responderId = (ResponderId)paramObject;
/* 239 */       return Arrays.equals(this.encodedRid, responderId.getEncoded());
/*     */     } 
/*     */     
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 252 */     return Arrays.hashCode(this.encodedRid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 262 */     StringBuilder stringBuilder = new StringBuilder();
/* 263 */     switch (this.type)
/*     */     { case BY_NAME:
/* 265 */         stringBuilder.append(this.type).append(": ").append(this.responderName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 276 */         return stringBuilder.toString();case BY_KEY: stringBuilder.append(this.type).append(": "); for (byte b : this.responderKeyId.getIdentifier()) { stringBuilder.append(String.format("%02X", new Object[] { Byte.valueOf(b) })); }  return stringBuilder.toString(); }  stringBuilder.append("Unknown ResponderId Type: ").append(this.type); return stringBuilder.toString();
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
/*     */   private byte[] principalToBytes() throws IOException {
/* 290 */     DerValue derValue = new DerValue(DerValue.createTag(-128, true, (byte)Type.BY_NAME.value()), this.responderName.getEncoded());
/* 291 */     return derValue.toByteArray();
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
/*     */   private byte[] keyIdToBytes() throws IOException {
/* 305 */     DerValue derValue1 = new DerValue((byte)4, this.responderKeyId.getIdentifier());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 310 */     DerValue derValue2 = new DerValue(DerValue.createTag(-128, true, (byte)Type.BY_KEY.value()), derValue1.toByteArray());
/*     */     
/* 312 */     return derValue2.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/ResponderId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */