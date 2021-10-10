/*     */ package javax.security.auth.kerberos;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.security.AccessControlException;
/*     */ import java.util.Objects;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KerberosSecrets;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.RealmException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class KeyTab
/*     */ {
/*     */   private final File file;
/*     */   private final KerberosPrincipal princ;
/*     */   private final boolean bound;
/*     */   
/*     */   static {
/* 100 */     KerberosSecrets.setJavaxSecurityAuthKerberosAccess(new JavaxSecurityAuthKerberosAccessImpl());
/*     */   }
/*     */ 
/*     */   
/*     */   private KeyTab(KerberosPrincipal paramKerberosPrincipal, File paramFile, boolean paramBoolean) {
/* 105 */     this.princ = paramKerberosPrincipal;
/* 106 */     this.file = paramFile;
/* 107 */     this.bound = paramBoolean;
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
/*     */   public static KeyTab getInstance(File paramFile) {
/* 124 */     if (paramFile == null) {
/* 125 */       throw new NullPointerException("file must be non null");
/*     */     }
/* 127 */     return new KeyTab(null, paramFile, true);
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
/*     */   public static KeyTab getUnboundInstance(File paramFile) {
/* 142 */     if (paramFile == null) {
/* 143 */       throw new NullPointerException("file must be non null");
/*     */     }
/* 145 */     return new KeyTab(null, paramFile, false);
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
/*     */   public static KeyTab getInstance(KerberosPrincipal paramKerberosPrincipal, File paramFile) {
/* 161 */     if (paramKerberosPrincipal == null) {
/* 162 */       throw new NullPointerException("princ must be non null");
/*     */     }
/* 164 */     if (paramFile == null) {
/* 165 */       throw new NullPointerException("file must be non null");
/*     */     }
/* 167 */     return new KeyTab(paramKerberosPrincipal, paramFile, true);
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
/*     */   public static KeyTab getInstance() {
/* 183 */     return new KeyTab(null, null, true);
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
/*     */   public static KeyTab getUnboundInstance() {
/* 196 */     return new KeyTab(null, null, false);
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
/*     */   public static KeyTab getInstance(KerberosPrincipal paramKerberosPrincipal) {
/* 212 */     if (paramKerberosPrincipal == null) {
/* 213 */       throw new NullPointerException("princ must be non null");
/*     */     }
/* 215 */     return new KeyTab(paramKerberosPrincipal, null, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   sun.security.krb5.internal.ktab.KeyTab takeSnapshot() {
/*     */     try {
/* 222 */       return sun.security.krb5.internal.ktab.KeyTab.getInstance(this.file);
/* 223 */     } catch (AccessControlException accessControlException1) {
/* 224 */       if (this.file != null)
/*     */       {
/* 226 */         throw accessControlException1;
/*     */       }
/* 228 */       AccessControlException accessControlException2 = new AccessControlException("Access to default keytab denied (modified exception)");
/*     */       
/* 230 */       accessControlException2.setStackTrace(accessControlException1.getStackTrace());
/* 231 */       throw accessControlException2;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosKey[] getKeys(KerberosPrincipal paramKerberosPrincipal) {
/*     */     try {
/* 279 */       if (this.princ != null && !paramKerberosPrincipal.equals(this.princ)) {
/* 280 */         return new KerberosKey[0];
/*     */       }
/* 282 */       PrincipalName principalName = new PrincipalName(paramKerberosPrincipal.getName());
/* 283 */       EncryptionKey[] arrayOfEncryptionKey = takeSnapshot().readServiceKeys(principalName);
/* 284 */       KerberosKey[] arrayOfKerberosKey = new KerberosKey[arrayOfEncryptionKey.length];
/* 285 */       for (byte b = 0; b < arrayOfKerberosKey.length; b++) {
/* 286 */         Integer integer = arrayOfEncryptionKey[b].getKeyVersionNumber();
/* 287 */         arrayOfKerberosKey[b] = new KerberosKey(paramKerberosPrincipal, arrayOfEncryptionKey[b]
/*     */             
/* 289 */             .getBytes(), arrayOfEncryptionKey[b]
/* 290 */             .getEType(), (integer == null) ? 0 : integer
/* 291 */             .intValue());
/* 292 */         arrayOfEncryptionKey[b].destroy();
/*     */       } 
/* 294 */       return arrayOfKerberosKey;
/* 295 */     } catch (RealmException realmException) {
/* 296 */       return new KerberosKey[0];
/*     */     } 
/*     */   }
/*     */   
/*     */   EncryptionKey[] getEncryptionKeys(PrincipalName paramPrincipalName) {
/* 301 */     return takeSnapshot().readServiceKeys(paramPrincipalName);
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
/*     */   public boolean exists() {
/* 316 */     return !takeSnapshot().isMissing();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 320 */     String str = (this.file == null) ? "Default keytab" : this.file.toString();
/* 321 */     if (!this.bound) return str; 
/* 322 */     if (this.princ == null) return str + " for someone"; 
/* 323 */     return str + " for " + this.princ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 332 */     return Objects.hash(new Object[] { this.file, this.princ, Boolean.valueOf(this.bound) });
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
/* 345 */     if (paramObject == this) {
/* 346 */       return true;
/*     */     }
/* 348 */     if (!(paramObject instanceof KeyTab)) {
/* 349 */       return false;
/*     */     }
/*     */     
/* 352 */     KeyTab keyTab = (KeyTab)paramObject;
/* 353 */     return (Objects.equals(keyTab.princ, this.princ) && 
/* 354 */       Objects.equals(keyTab.file, this.file) && this.bound == keyTab.bound);
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
/*     */   public KerberosPrincipal getPrincipal() {
/* 369 */     return this.princ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBound() {
/* 378 */     return this.bound;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/kerberos/KeyTab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */