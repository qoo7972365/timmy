/*     */ package java.security;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public abstract class Signer
/*     */   extends Identity
/*     */ {
/*     */   private static final long serialVersionUID = -1763464102261361480L;
/*     */   private PrivateKey privateKey;
/*     */   
/*     */   protected Signer() {}
/*     */   
/*     */   public Signer(String paramString) {
/*  74 */     super(paramString);
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
/*     */   public Signer(String paramString, IdentityScope paramIdentityScope) throws KeyManagementException {
/*  89 */     super(paramString, paramIdentityScope);
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
/*     */   public PrivateKey getPrivateKey() {
/* 109 */     check("getSignerPrivateKey");
/* 110 */     return this.privateKey;
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
/*     */   public final void setKeyPair(KeyPair paramKeyPair) throws InvalidParameterException, KeyException {
/* 134 */     check("setSignerKeyPair");
/* 135 */     final PublicKey pub = paramKeyPair.getPublic();
/* 136 */     PrivateKey privateKey = paramKeyPair.getPrivate();
/*     */     
/* 138 */     if (publicKey == null || privateKey == null) {
/* 139 */       throw new InvalidParameterException();
/*     */     }
/*     */     try {
/* 142 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */           {
/*     */             public Void run() throws KeyManagementException {
/* 145 */               Signer.this.setPublicKey(pub);
/* 146 */               return null;
/*     */             }
/*     */           });
/* 149 */     } catch (PrivilegedActionException privilegedActionException) {
/* 150 */       throw (KeyManagementException)privilegedActionException.getException();
/*     */     } 
/* 152 */     this.privateKey = privateKey;
/*     */   }
/*     */   
/*     */   String printKeys() {
/* 156 */     String str = "";
/* 157 */     PublicKey publicKey = getPublicKey();
/* 158 */     if (publicKey != null && this.privateKey != null) {
/* 159 */       str = "\tpublic and private keys initialized";
/*     */     } else {
/*     */       
/* 162 */       str = "\tno keys";
/*     */     } 
/* 164 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 173 */     return "[Signer]" + super.toString();
/*     */   }
/*     */   
/*     */   private static void check(String paramString) {
/* 177 */     SecurityManager securityManager = System.getSecurityManager();
/* 178 */     if (securityManager != null)
/* 179 */       securityManager.checkSecurityAccess(paramString); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/Signer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */