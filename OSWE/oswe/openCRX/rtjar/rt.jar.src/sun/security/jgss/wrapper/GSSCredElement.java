/*     */ package sun.security.jgss.wrapper;
/*     */ 
/*     */ import java.security.Provider;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.GSSUtil;
/*     */ import sun.security.jgss.spi.GSSCredentialSpi;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GSSCredElement
/*     */   implements GSSCredentialSpi
/*     */ {
/*     */   private int usage;
/*     */   long pCred;
/*  43 */   private GSSNameElement name = null;
/*     */   
/*     */   private GSSLibStub cStub;
/*     */   
/*     */   void doServicePermCheck() throws GSSException {
/*  48 */     if (GSSUtil.isKerberosMech(this.cStub.getMech()) && 
/*  49 */       System.getSecurityManager() != null) {
/*  50 */       if (isInitiatorCredential()) {
/*  51 */         String str = Krb5Util.getTGSName(this.name);
/*  52 */         Krb5Util.checkServicePermission(str, "initiate");
/*     */       } 
/*  54 */       if (isAcceptorCredential() && this.name != GSSNameElement.DEF_ACCEPTOR) {
/*     */         
/*  56 */         String str = this.name.getKrbName();
/*  57 */         Krb5Util.checkServicePermission(str, "accept");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GSSCredElement(long paramLong, GSSNameElement paramGSSNameElement, Oid paramOid) throws GSSException {
/*  66 */     this.pCred = paramLong;
/*  67 */     this.cStub = GSSLibStub.getInstance(paramOid);
/*  68 */     this.usage = 1;
/*  69 */     this.name = paramGSSNameElement;
/*     */   }
/*     */ 
/*     */   
/*     */   GSSCredElement(GSSNameElement paramGSSNameElement, int paramInt1, int paramInt2, GSSLibStub paramGSSLibStub) throws GSSException {
/*  74 */     this.cStub = paramGSSLibStub;
/*  75 */     this.usage = paramInt2;
/*     */     
/*  77 */     if (paramGSSNameElement != null) {
/*  78 */       this.name = paramGSSNameElement;
/*  79 */       doServicePermCheck();
/*  80 */       this.pCred = this.cStub.acquireCred(this.name.pName, paramInt1, paramInt2);
/*     */     } else {
/*  82 */       this.pCred = this.cStub.acquireCred(0L, paramInt1, paramInt2);
/*  83 */       this.name = new GSSNameElement(this.cStub.getCredName(this.pCred), this.cStub);
/*  84 */       doServicePermCheck();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Provider getProvider() {
/*  89 */     return SunNativeProvider.INSTANCE;
/*     */   }
/*     */   
/*     */   public void dispose() throws GSSException {
/*  93 */     this.name = null;
/*  94 */     if (this.pCred != 0L) {
/*  95 */       this.pCred = this.cStub.releaseCred(this.pCred);
/*     */     }
/*     */   }
/*     */   
/*     */   public GSSNameElement getName() throws GSSException {
/* 100 */     return (this.name == GSSNameElement.DEF_ACCEPTOR) ? null : this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInitLifetime() throws GSSException {
/* 105 */     if (isInitiatorCredential())
/* 106 */       return this.cStub.getCredTime(this.pCred); 
/* 107 */     return 0;
/*     */   }
/*     */   
/*     */   public int getAcceptLifetime() throws GSSException {
/* 111 */     if (isAcceptorCredential())
/* 112 */       return this.cStub.getCredTime(this.pCred); 
/* 113 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isInitiatorCredential() {
/* 117 */     return (this.usage != 2);
/*     */   }
/*     */   
/*     */   public boolean isAcceptorCredential() {
/* 121 */     return (this.usage != 1);
/*     */   }
/*     */   
/*     */   public Oid getMechanism() {
/* 125 */     return this.cStub.getMech();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 130 */     return "N/A";
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 134 */     dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSCredentialSpi impersonate(GSSNameSpi paramGSSNameSpi) throws GSSException {
/* 139 */     throw new GSSException(11, -1, "Not supported yet");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/wrapper/GSSCredElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */