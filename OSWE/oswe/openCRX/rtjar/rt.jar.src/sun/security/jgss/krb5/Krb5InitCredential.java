/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.Provider;
/*     */ import java.util.Date;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.kerberos.KerberosPrincipal;
/*     */ import javax.security.auth.kerberos.KerberosTicket;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.GSSCaller;
/*     */ import sun.security.jgss.spi.GSSCredentialSpi;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KerberosSecrets;
/*     */ import sun.security.krb5.KrbException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Krb5InitCredential
/*     */   extends KerberosTicket
/*     */   implements Krb5CredElement
/*     */ {
/*     */   private static final long serialVersionUID = 7723415700837898232L;
/*     */   private Krb5NameElement name;
/*     */   private Credentials krb5Credentials;
/*     */   public KerberosTicket proxyTicket;
/*     */   
/*     */   private Krb5InitCredential(Krb5NameElement paramKrb5NameElement, byte[] paramArrayOfbyte1, KerberosPrincipal paramKerberosPrincipal1, KerberosPrincipal paramKerberosPrincipal2, KerberosPrincipal paramKerberosPrincipal3, KerberosPrincipal paramKerberosPrincipal4, byte[] paramArrayOfbyte2, int paramInt, boolean[] paramArrayOfboolean, Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4, InetAddress[] paramArrayOfInetAddress) throws GSSException {
/*  75 */     super(paramArrayOfbyte1, paramKerberosPrincipal1, paramKerberosPrincipal3, paramArrayOfbyte2, paramInt, paramArrayOfboolean, paramDate1, paramDate2, paramDate3, paramDate4, paramArrayOfInetAddress);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     KerberosSecrets.getJavaxSecurityAuthKerberosAccess()
/*  87 */       .kerberosTicketSetClientAlias(this, paramKerberosPrincipal2);
/*  88 */     KerberosSecrets.getJavaxSecurityAuthKerberosAccess()
/*  89 */       .kerberosTicketSetServerAlias(this, paramKerberosPrincipal4);
/*  90 */     this.name = paramKrb5NameElement;
/*     */ 
/*     */     
/*     */     try {
/*  94 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 100 */         .krb5Credentials = new Credentials(paramArrayOfbyte1, paramKerberosPrincipal1.getName(), (paramKerberosPrincipal2 != null) ? paramKerberosPrincipal2.getName() : null, paramKerberosPrincipal3.getName(), (paramKerberosPrincipal4 != null) ? paramKerberosPrincipal4.getName() : null, paramArrayOfbyte2, paramInt, paramArrayOfboolean, paramDate1, paramDate2, paramDate3, paramDate4, paramArrayOfInetAddress);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 109 */     catch (KrbException krbException) {
/* 110 */       throw new GSSException(13, -1, krbException
/* 111 */           .getMessage());
/* 112 */     } catch (IOException iOException) {
/* 113 */       throw new GSSException(13, -1, iOException
/* 114 */           .getMessage());
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
/*     */   private Krb5InitCredential(Krb5NameElement paramKrb5NameElement, Credentials paramCredentials, byte[] paramArrayOfbyte1, KerberosPrincipal paramKerberosPrincipal1, KerberosPrincipal paramKerberosPrincipal2, KerberosPrincipal paramKerberosPrincipal3, KerberosPrincipal paramKerberosPrincipal4, byte[] paramArrayOfbyte2, int paramInt, boolean[] paramArrayOfboolean, Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4, InetAddress[] paramArrayOfInetAddress) throws GSSException {
/* 135 */     super(paramArrayOfbyte1, paramKerberosPrincipal1, paramKerberosPrincipal3, paramArrayOfbyte2, paramInt, paramArrayOfboolean, paramDate1, paramDate2, paramDate3, paramDate4, paramArrayOfInetAddress);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     KerberosSecrets.getJavaxSecurityAuthKerberosAccess()
/* 147 */       .kerberosTicketSetClientAlias(this, paramKerberosPrincipal2);
/* 148 */     KerberosSecrets.getJavaxSecurityAuthKerberosAccess()
/* 149 */       .kerberosTicketSetServerAlias(this, paramKerberosPrincipal4);
/* 150 */     this.name = paramKrb5NameElement;
/*     */ 
/*     */     
/* 153 */     this.krb5Credentials = paramCredentials;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Krb5InitCredential getInstance(GSSCaller paramGSSCaller, Krb5NameElement paramKrb5NameElement, int paramInt) throws GSSException {
/* 160 */     KerberosTicket kerberosTicket = getTgt(paramGSSCaller, paramKrb5NameElement, paramInt);
/* 161 */     if (kerberosTicket == null) {
/* 162 */       throw new GSSException(13, -1, "Failed to find any Kerberos tgt");
/*     */     }
/*     */     
/* 165 */     if (paramKrb5NameElement == null) {
/* 166 */       String str = kerberosTicket.getClient().getName();
/* 167 */       paramKrb5NameElement = Krb5NameElement.getInstance(str, Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     KerberosPrincipal kerberosPrincipal1 = KerberosSecrets.getJavaxSecurityAuthKerberosAccess().kerberosTicketGetClientAlias(kerberosTicket);
/*     */ 
/*     */     
/* 176 */     KerberosPrincipal kerberosPrincipal2 = KerberosSecrets.getJavaxSecurityAuthKerberosAccess().kerberosTicketGetServerAlias(kerberosTicket);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     Krb5InitCredential krb5InitCredential = new Krb5InitCredential(paramKrb5NameElement, kerberosTicket.getEncoded(), kerberosTicket.getClient(), kerberosPrincipal1, kerberosTicket.getServer(), kerberosPrincipal2, kerberosTicket.getSessionKey().getEncoded(), kerberosTicket.getSessionKeyType(), kerberosTicket.getFlags(), kerberosTicket.getAuthTime(), kerberosTicket.getStartTime(), kerberosTicket.getEndTime(), kerberosTicket.getRenewTill(), kerberosTicket.getClientAddresses());
/* 191 */     krb5InitCredential
/* 192 */       .proxyTicket = KerberosSecrets.getJavaxSecurityAuthKerberosAccess().kerberosTicketGetProxy(kerberosTicket);
/* 193 */     return krb5InitCredential;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Krb5InitCredential getInstance(Krb5NameElement paramKrb5NameElement, Credentials paramCredentials) throws GSSException {
/* 200 */     EncryptionKey encryptionKey = paramCredentials.getSessionKey();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     PrincipalName principalName1 = paramCredentials.getClient();
/* 208 */     PrincipalName principalName2 = paramCredentials.getClientAlias();
/* 209 */     PrincipalName principalName3 = paramCredentials.getServer();
/* 210 */     PrincipalName principalName4 = paramCredentials.getServerAlias();
/*     */     
/* 212 */     KerberosPrincipal kerberosPrincipal1 = null;
/* 213 */     KerberosPrincipal kerberosPrincipal2 = null;
/* 214 */     KerberosPrincipal kerberosPrincipal3 = null;
/* 215 */     KerberosPrincipal kerberosPrincipal4 = null;
/*     */     
/* 217 */     Krb5NameElement krb5NameElement = null;
/*     */     
/* 219 */     if (principalName1 != null) {
/* 220 */       String str = principalName1.getName();
/* 221 */       krb5NameElement = Krb5NameElement.getInstance(str, Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL);
/*     */       
/* 223 */       kerberosPrincipal1 = new KerberosPrincipal(str);
/*     */     } 
/*     */     
/* 226 */     if (principalName2 != null) {
/* 227 */       kerberosPrincipal2 = new KerberosPrincipal(principalName2.getName());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 232 */     if (principalName3 != null)
/*     */     {
/* 234 */       kerberosPrincipal3 = new KerberosPrincipal(principalName3.getName(), 2);
/*     */     }
/*     */ 
/*     */     
/* 238 */     if (principalName4 != null) {
/* 239 */       kerberosPrincipal4 = new KerberosPrincipal(principalName4.getName());
/*     */     }
/*     */     
/* 242 */     return new Krb5InitCredential(krb5NameElement, paramCredentials, paramCredentials
/*     */         
/* 244 */         .getEncoded(), kerberosPrincipal1, kerberosPrincipal2, kerberosPrincipal3, kerberosPrincipal4, encryptionKey
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 249 */         .getBytes(), encryptionKey
/* 250 */         .getEType(), paramCredentials
/* 251 */         .getFlags(), paramCredentials
/* 252 */         .getAuthTime(), paramCredentials
/* 253 */         .getStartTime(), paramCredentials
/* 254 */         .getEndTime(), paramCredentials
/* 255 */         .getRenewTill(), paramCredentials
/* 256 */         .getClientAddresses());
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
/* 267 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInitLifetime() throws GSSException {
/* 277 */     Date date = getEndTime();
/* 278 */     if (date == null) {
/* 279 */       return 0;
/*     */     }
/*     */     
/* 282 */     long l = date.getTime() - System.currentTimeMillis();
/* 283 */     return (int)(l / 1000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAcceptLifetime() throws GSSException {
/* 293 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isInitiatorCredential() throws GSSException {
/* 297 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isAcceptorCredential() throws GSSException {
/* 301 */     return false;
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
/* 312 */     return Krb5MechFactory.GSS_KRB5_MECH_OID;
/*     */   }
/*     */   
/*     */   public final Provider getProvider() {
/* 316 */     return Krb5MechFactory.PROVIDER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Credentials getKrb5Credentials() {
/* 325 */     return this.krb5Credentials;
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
/*     */   public void dispose() throws GSSException {
/*     */     try {
/* 338 */       destroy();
/* 339 */     } catch (DestroyFailedException destroyFailedException) {
/*     */ 
/*     */       
/* 342 */       GSSException gSSException = new GSSException(11, -1, "Could not destroy credentials - " + destroyFailedException.getMessage());
/* 343 */       gSSException.initCause(destroyFailedException);
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
/*     */   private static KerberosTicket getTgt(GSSCaller paramGSSCaller, Krb5NameElement paramKrb5NameElement, int paramInt) throws GSSException {
/*     */     final String clientPrincipal;
/* 360 */     if (paramKrb5NameElement != null) {
/* 361 */       str = paramKrb5NameElement.getKrb5PrincipalName().getName();
/*     */     } else {
/* 363 */       str = null;
/*     */     } 
/*     */     
/* 366 */     final AccessControlContext acc = AccessController.getContext();
/*     */     
/*     */     try {
/* 369 */       final GSSCaller realCaller = (paramGSSCaller == GSSCaller.CALLER_UNKNOWN) ? GSSCaller.CALLER_INITIATE : paramGSSCaller;
/*     */ 
/*     */       
/* 372 */       return AccessController.<KerberosTicket>doPrivileged(new PrivilegedExceptionAction<KerberosTicket>()
/*     */           {
/*     */             
/*     */             public KerberosTicket run() throws Exception
/*     */             {
/* 377 */               return Krb5Util.getInitialTicket(realCaller, clientPrincipal, acc);
/*     */             }
/*     */           });
/*     */     }
/* 381 */     catch (PrivilegedActionException privilegedActionException) {
/*     */ 
/*     */ 
/*     */       
/* 385 */       GSSException gSSException = new GSSException(13, -1, "Attempt to obtain new INITIATE credentials failed! (" + privilegedActionException.getMessage() + ")");
/* 386 */       gSSException.initCause(privilegedActionException.getException());
/* 387 */       throw gSSException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSCredentialSpi impersonate(GSSNameSpi paramGSSNameSpi) throws GSSException {
/*     */     try {
/* 394 */       Krb5NameElement krb5NameElement = (Krb5NameElement)paramGSSNameSpi;
/* 395 */       Credentials credentials = Credentials.acquireS4U2selfCreds(krb5NameElement
/* 396 */           .getKrb5PrincipalName(), this.krb5Credentials);
/* 397 */       return new Krb5ProxyCredential(this, krb5NameElement, credentials.getTicket());
/* 398 */     } catch (IOException|KrbException iOException) {
/* 399 */       GSSException gSSException = new GSSException(11, -1, "Attempt to obtain S4U2self credentials failed!");
/*     */ 
/*     */       
/* 402 */       gSSException.initCause(iOException);
/* 403 */       throw gSSException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/Krb5InitCredential.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */