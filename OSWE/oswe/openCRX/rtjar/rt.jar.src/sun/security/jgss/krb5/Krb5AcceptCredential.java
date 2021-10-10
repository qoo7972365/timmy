/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.Provider;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.GSSCaller;
/*     */ import sun.security.jgss.spi.GSSCredentialSpi;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Krb5AcceptCredential
/*     */   implements Krb5CredElement
/*     */ {
/*     */   private final Krb5NameElement name;
/*     */   private final ServiceCreds screds;
/*     */   
/*     */   private Krb5AcceptCredential(Krb5NameElement paramKrb5NameElement, ServiceCreds paramServiceCreds) {
/*  58 */     this.name = paramKrb5NameElement;
/*  59 */     this.screds = paramServiceCreds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Krb5AcceptCredential getInstance(final GSSCaller caller, Krb5NameElement paramKrb5NameElement) throws GSSException {
/*  66 */     final String serverPrinc = (paramKrb5NameElement == null) ? null : paramKrb5NameElement.getKrb5PrincipalName().getName();
/*  67 */     final AccessControlContext acc = AccessController.getContext();
/*     */     
/*  69 */     ServiceCreds serviceCreds = null;
/*     */     try {
/*  71 */       serviceCreds = AccessController.<ServiceCreds>doPrivileged(new PrivilegedExceptionAction<ServiceCreds>()
/*     */           {
/*     */             public ServiceCreds run() throws Exception {
/*  74 */               return Krb5Util.getServiceCreds((caller == GSSCaller.CALLER_UNKNOWN) ? GSSCaller.CALLER_ACCEPT : caller, serverPrinc, acc);
/*     */             }
/*     */           });
/*     */     }
/*  78 */     catch (PrivilegedActionException privilegedActionException) {
/*  79 */       GSSException gSSException = new GSSException(13, -1, "Attempt to obtain new ACCEPT credentials failed!");
/*     */ 
/*     */       
/*  82 */       gSSException.initCause(privilegedActionException.getException());
/*  83 */       throw gSSException;
/*     */     } 
/*     */     
/*  86 */     if (serviceCreds == null) {
/*  87 */       throw new GSSException(13, -1, "Failed to find any Kerberos credentails");
/*     */     }
/*     */     
/*  90 */     if (paramKrb5NameElement == null) {
/*  91 */       String str1 = serviceCreds.getName();
/*  92 */       if (str1 != null) {
/*  93 */         paramKrb5NameElement = Krb5NameElement.getInstance(str1, Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  98 */     return new Krb5AcceptCredential(paramKrb5NameElement, serviceCreds);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final GSSNameSpi getName() throws GSSException {
/* 109 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInitLifetime() throws GSSException {
/* 119 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAcceptLifetime() throws GSSException {
/* 129 */     return Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public boolean isInitiatorCredential() throws GSSException {
/* 133 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isAcceptorCredential() throws GSSException {
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Oid getMechanism() {
/* 148 */     return Krb5MechFactory.GSS_KRB5_MECH_OID;
/*     */   }
/*     */   
/*     */   public final Provider getProvider() {
/* 152 */     return Krb5MechFactory.PROVIDER;
/*     */   }
/*     */   
/*     */   public EncryptionKey[] getKrb5EncryptionKeys(PrincipalName paramPrincipalName) {
/* 156 */     return this.screds.getEKeys(paramPrincipalName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() throws GSSException {
/*     */     try {
/* 164 */       destroy();
/* 165 */     } catch (DestroyFailedException destroyFailedException) {
/*     */ 
/*     */       
/* 168 */       GSSException gSSException = new GSSException(11, -1, "Could not destroy credentials - " + destroyFailedException.getMessage());
/* 169 */       gSSException.initCause(destroyFailedException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() throws DestroyFailedException {
/* 178 */     this.screds.destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSCredentialSpi impersonate(GSSNameSpi paramGSSNameSpi) throws GSSException {
/* 188 */     Credentials credentials = this.screds.getInitCred();
/* 189 */     if (credentials != null) {
/* 190 */       return Krb5InitCredential.getInstance(this.name, credentials)
/* 191 */         .impersonate(paramGSSNameSpi);
/*     */     }
/* 193 */     throw new GSSException(11, -1, "Only an initiate credentials can impersonate");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/Krb5AcceptCredential.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */