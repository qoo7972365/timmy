/*     */ package javax.security.auth.kerberos;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.Destroyable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KerberosKey
/*     */   implements SecretKey, Destroyable
/*     */ {
/*     */   private static final long serialVersionUID = -4625402278148246993L;
/*     */   private KerberosPrincipal principal;
/*     */   private int versionNum;
/*     */   private KeyImpl key;
/*     */   private transient boolean destroyed = false;
/*     */   
/*     */   public KerberosKey(KerberosPrincipal paramKerberosPrincipal, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 110 */     this.principal = paramKerberosPrincipal;
/* 111 */     this.versionNum = paramInt2;
/* 112 */     this.key = new KeyImpl(paramArrayOfbyte, paramInt1);
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
/*     */   public KerberosKey(KerberosPrincipal paramKerberosPrincipal, char[] paramArrayOfchar, String paramString) {
/* 130 */     this.principal = paramKerberosPrincipal;
/*     */     
/* 132 */     this.key = new KeyImpl(paramKerberosPrincipal, paramArrayOfchar, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final KerberosPrincipal getPrincipal() {
/* 141 */     if (this.destroyed)
/* 142 */       throw new IllegalStateException("This key is no longer valid"); 
/* 143 */     return this.principal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getVersionNumber() {
/* 152 */     if (this.destroyed)
/* 153 */       throw new IllegalStateException("This key is no longer valid"); 
/* 154 */     return this.versionNum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getKeyType() {
/* 163 */     if (this.destroyed)
/* 164 */       throw new IllegalStateException("This key is no longer valid"); 
/* 165 */     return this.key.getKeyType();
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
/*     */   public final String getAlgorithm() {
/* 184 */     if (this.destroyed)
/* 185 */       throw new IllegalStateException("This key is no longer valid"); 
/* 186 */     return this.key.getAlgorithm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getFormat() {
/* 195 */     if (this.destroyed)
/* 196 */       throw new IllegalStateException("This key is no longer valid"); 
/* 197 */     return this.key.getFormat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] getEncoded() {
/* 206 */     if (this.destroyed)
/* 207 */       throw new IllegalStateException("This key is no longer valid"); 
/* 208 */     return this.key.getEncoded();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() throws DestroyFailedException {
/* 219 */     if (!this.destroyed) {
/* 220 */       this.key.destroy();
/* 221 */       this.principal = null;
/* 222 */       this.destroyed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDestroyed() {
/* 229 */     return this.destroyed;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 233 */     if (this.destroyed) {
/* 234 */       return "Destroyed Principal";
/*     */     }
/* 236 */     return "Kerberos Principal " + this.principal.toString() + "Key Version " + this.versionNum + "key " + this.key
/*     */       
/* 238 */       .toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 248 */     int i = 17;
/* 249 */     if (isDestroyed()) {
/* 250 */       return i;
/*     */     }
/* 252 */     i = 37 * i + Arrays.hashCode(getEncoded());
/* 253 */     i = 37 * i + getKeyType();
/* 254 */     if (this.principal != null) {
/* 255 */       i = 37 * i + this.principal.hashCode();
/*     */     }
/* 257 */     return i * 37 + this.versionNum;
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
/*     */   public boolean equals(Object paramObject) {
/* 274 */     if (paramObject == this) {
/* 275 */       return true;
/*     */     }
/* 277 */     if (!(paramObject instanceof KerberosKey)) {
/* 278 */       return false;
/*     */     }
/*     */     
/* 281 */     KerberosKey kerberosKey = (KerberosKey)paramObject;
/* 282 */     if (isDestroyed() || kerberosKey.isDestroyed()) {
/* 283 */       return false;
/*     */     }
/*     */     
/* 286 */     if (this.versionNum != kerberosKey.getVersionNumber() || 
/* 287 */       getKeyType() != kerberosKey.getKeyType() || 
/* 288 */       !Arrays.equals(getEncoded(), kerberosKey.getEncoded())) {
/* 289 */       return false;
/*     */     }
/*     */     
/* 292 */     if (this.principal == null) {
/* 293 */       if (kerberosKey.getPrincipal() != null) {
/* 294 */         return false;
/*     */       }
/*     */     }
/* 297 */     else if (!this.principal.equals(kerberosKey.getPrincipal())) {
/* 298 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 302 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/kerberos/KerberosKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */