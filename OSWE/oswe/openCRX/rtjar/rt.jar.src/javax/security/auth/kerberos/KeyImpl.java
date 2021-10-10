/*     */ package javax.security.auth.kerberos;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.Destroyable;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class KeyImpl
/*     */   implements SecretKey, Destroyable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7889313790214321193L;
/*     */   private transient byte[] keyBytes;
/*     */   private transient int keyType;
/*     */   private volatile transient boolean destroyed = false;
/*     */   
/*     */   public KeyImpl(byte[] paramArrayOfbyte, int paramInt) {
/*  69 */     this.keyBytes = (byte[])paramArrayOfbyte.clone();
/*  70 */     this.keyType = paramInt;
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
/*     */   public KeyImpl(KerberosPrincipal paramKerberosPrincipal, char[] paramArrayOfchar, String paramString) {
/*     */     try {
/*  88 */       PrincipalName principalName = new PrincipalName(paramKerberosPrincipal.getName());
/*     */       
/*  90 */       EncryptionKey encryptionKey = new EncryptionKey(paramArrayOfchar, principalName.getSalt(), paramString);
/*  91 */       this.keyBytes = encryptionKey.getBytes();
/*  92 */       this.keyType = encryptionKey.getEType();
/*  93 */     } catch (KrbException krbException) {
/*  94 */       throw new IllegalArgumentException(krbException.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getKeyType() {
/* 102 */     if (this.destroyed)
/* 103 */       throw new IllegalStateException("This key is no longer valid"); 
/* 104 */     return this.keyType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getAlgorithm() {
/* 112 */     return getAlgorithmName(this.keyType);
/*     */   }
/*     */   
/*     */   private String getAlgorithmName(int paramInt) {
/* 116 */     if (this.destroyed) {
/* 117 */       throw new IllegalStateException("This key is no longer valid");
/*     */     }
/* 119 */     switch (paramInt) {
/*     */       case 1:
/*     */       case 3:
/* 122 */         return "DES";
/*     */       
/*     */       case 16:
/* 125 */         return "DESede";
/*     */       
/*     */       case 23:
/* 128 */         return "ArcFourHmac";
/*     */       
/*     */       case 17:
/* 131 */         return "AES128";
/*     */       
/*     */       case 18:
/* 134 */         return "AES256";
/*     */       
/*     */       case 0:
/* 137 */         return "NULL";
/*     */     } 
/*     */     
/* 140 */     throw new IllegalArgumentException("Unsupported encryption type: " + paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getFormat() {
/* 146 */     if (this.destroyed)
/* 147 */       throw new IllegalStateException("This key is no longer valid"); 
/* 148 */     return "RAW";
/*     */   }
/*     */   
/*     */   public final byte[] getEncoded() {
/* 152 */     if (this.destroyed)
/* 153 */       throw new IllegalStateException("This key is no longer valid"); 
/* 154 */     return (byte[])this.keyBytes.clone();
/*     */   }
/*     */   
/*     */   public void destroy() throws DestroyFailedException {
/* 158 */     if (!this.destroyed) {
/* 159 */       this.destroyed = true;
/* 160 */       Arrays.fill(this.keyBytes, (byte)0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isDestroyed() {
/* 165 */     return this.destroyed;
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 179 */     if (this.destroyed) {
/* 180 */       throw new IOException("This key is no longer valid");
/*     */     }
/*     */     
/*     */     try {
/* 184 */       paramObjectOutputStream.writeObject((new EncryptionKey(this.keyType, this.keyBytes)).asn1Encode());
/* 185 */     } catch (Asn1Exception asn1Exception) {
/* 186 */       throw new IOException(asn1Exception.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*     */     try {
/* 194 */       EncryptionKey encryptionKey = new EncryptionKey(new DerValue((byte[])paramObjectInputStream.readObject()));
/* 195 */       this.keyType = encryptionKey.getEType();
/* 196 */       this.keyBytes = encryptionKey.getBytes();
/* 197 */     } catch (Asn1Exception asn1Exception) {
/* 198 */       throw new IOException(asn1Exception.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 203 */     HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/* 204 */     return "EncryptionKey: keyType=" + this.keyType + " keyBytes (hex dump)=" + ((this.keyBytes == null || this.keyBytes.length == 0) ? " Empty Key" : ('\n' + hexDumpEncoder
/*     */ 
/*     */ 
/*     */       
/* 208 */       .encodeBuffer(this.keyBytes) + '\n'));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 215 */     int i = 17;
/* 216 */     if (isDestroyed()) {
/* 217 */       return i;
/*     */     }
/* 219 */     i = 37 * i + Arrays.hashCode(this.keyBytes);
/* 220 */     return 37 * i + this.keyType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 225 */     if (paramObject == this) {
/* 226 */       return true;
/*     */     }
/* 228 */     if (!(paramObject instanceof KeyImpl)) {
/* 229 */       return false;
/*     */     }
/*     */     
/* 232 */     KeyImpl keyImpl = (KeyImpl)paramObject;
/* 233 */     if (isDestroyed() || keyImpl.isDestroyed()) {
/* 234 */       return false;
/*     */     }
/*     */     
/* 237 */     if (this.keyType != keyImpl.getKeyType() || 
/* 238 */       !Arrays.equals(this.keyBytes, keyImpl.getEncoded())) {
/* 239 */       return false;
/*     */     }
/*     */     
/* 242 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/kerberos/KeyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */