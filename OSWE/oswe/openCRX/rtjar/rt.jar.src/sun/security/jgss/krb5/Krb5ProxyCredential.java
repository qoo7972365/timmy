/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.security.Provider;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.kerberos.KerberosTicket;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.GSSCaller;
/*     */ import sun.security.jgss.spi.GSSCredentialSpi;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Krb5ProxyCredential
/*     */   implements Krb5CredElement
/*     */ {
/*     */   public final Krb5InitCredential self;
/*     */   private final Krb5NameElement client;
/*     */   public final Ticket tkt;
/*     */   
/*     */   Krb5ProxyCredential(Krb5InitCredential paramKrb5InitCredential, Krb5NameElement paramKrb5NameElement, Ticket paramTicket) {
/*  61 */     this.self = paramKrb5InitCredential;
/*  62 */     this.tkt = paramTicket;
/*  63 */     this.client = paramKrb5NameElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Krb5NameElement getName() throws GSSException {
/*  69 */     return this.client;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInitLifetime() throws GSSException {
/*  76 */     return this.self.getInitLifetime();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAcceptLifetime() throws GSSException {
/*  81 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInitiatorCredential() throws GSSException {
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAcceptorCredential() throws GSSException {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Oid getMechanism() {
/*  96 */     return Krb5MechFactory.GSS_KRB5_MECH_OID;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Provider getProvider() {
/* 101 */     return Krb5MechFactory.PROVIDER;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() throws GSSException {
/*     */     try {
/* 107 */       this.self.destroy();
/* 108 */     } catch (DestroyFailedException destroyFailedException) {
/*     */ 
/*     */       
/* 111 */       GSSException gSSException = new GSSException(11, -1, "Could not destroy credentials - " + destroyFailedException.getMessage());
/* 112 */       gSSException.initCause(destroyFailedException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSCredentialSpi impersonate(GSSNameSpi paramGSSNameSpi) throws GSSException {
/* 119 */     throw new GSSException(11, -1, "Only an initiate credentials can impersonate");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Krb5CredElement tryImpersonation(GSSCaller paramGSSCaller, Krb5InitCredential paramKrb5InitCredential) throws GSSException {
/*     */     try {
/* 128 */       KerberosTicket kerberosTicket = paramKrb5InitCredential.proxyTicket;
/* 129 */       if (kerberosTicket != null) {
/* 130 */         Credentials credentials = Krb5Util.ticketToCreds(kerberosTicket);
/* 131 */         return new Krb5ProxyCredential(paramKrb5InitCredential, 
/* 132 */             Krb5NameElement.getInstance(credentials.getClient()), credentials
/* 133 */             .getTicket());
/*     */       } 
/* 135 */       return paramKrb5InitCredential;
/*     */     }
/* 137 */     catch (KrbException|java.io.IOException krbException) {
/* 138 */       throw new GSSException(9, -1, "Cannot create proxy credential");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/Krb5ProxyCredential.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */