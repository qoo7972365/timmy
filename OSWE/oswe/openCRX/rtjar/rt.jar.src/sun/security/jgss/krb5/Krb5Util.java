/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.kerberos.KerberosPrincipal;
/*     */ import javax.security.auth.kerberos.KerberosTicket;
/*     */ import javax.security.auth.kerberos.KeyTab;
/*     */ import javax.security.auth.login.LoginException;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.security.jgss.GSSCaller;
/*     */ import sun.security.jgss.GSSUtil;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KerberosSecrets;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.internal.ktab.KeyTab;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Krb5Util
/*     */ {
/*  53 */   static final boolean DEBUG = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.security.krb5.debug")))
/*     */     
/*  55 */     .booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KerberosTicket getTicketFromSubjectAndTgs(GSSCaller paramGSSCaller, String paramString1, String paramString2, String paramString3, AccessControlContext paramAccessControlContext) throws LoginException, KrbException, IOException {
/*     */     boolean bool;
/*  81 */     Subject subject1 = Subject.getSubject(paramAccessControlContext);
/*  82 */     KerberosTicket kerberosTicket1 = SubjectComber.<KerberosTicket>find(subject1, paramString2, paramString1, KerberosTicket.class);
/*     */ 
/*     */     
/*  85 */     if (kerberosTicket1 != null) {
/*  86 */       return kerberosTicket1;
/*     */     }
/*     */     
/*  89 */     Subject subject2 = null;
/*  90 */     if (!GSSUtil.useSubjectCredsOnly(paramGSSCaller)) {
/*     */       
/*     */       try {
/*  93 */         subject2 = GSSUtil.login(paramGSSCaller, GSSUtil.GSS_KRB5_MECH_OID);
/*  94 */         kerberosTicket1 = SubjectComber.<KerberosTicket>find(subject2, paramString2, paramString1, KerberosTicket.class);
/*     */         
/*  96 */         if (kerberosTicket1 != null) {
/*  97 */           return kerberosTicket1;
/*     */         }
/*  99 */       } catch (LoginException loginException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     KerberosTicket kerberosTicket2 = SubjectComber.<KerberosTicket>find(subject1, paramString3, paramString1, KerberosTicket.class);
/*     */ 
/*     */ 
/*     */     
/* 113 */     if (kerberosTicket2 == null && subject2 != null) {
/*     */       
/* 115 */       kerberosTicket2 = SubjectComber.<KerberosTicket>find(subject2, paramString3, paramString1, KerberosTicket.class);
/*     */       
/* 117 */       bool = false;
/*     */     } else {
/* 119 */       bool = true;
/*     */     } 
/*     */ 
/*     */     
/* 123 */     if (kerberosTicket2 != null) {
/* 124 */       Credentials credentials1 = ticketToCreds(kerberosTicket2);
/* 125 */       Credentials credentials2 = Credentials.acquireServiceCreds(paramString2, credentials1);
/*     */       
/* 127 */       if (credentials2 != null) {
/* 128 */         kerberosTicket1 = credsToTicket(credentials2);
/*     */ 
/*     */         
/* 131 */         if (bool && subject1 != null && !subject1.isReadOnly()) {
/* 132 */           subject1.getPrivateCredentials().add(kerberosTicket1);
/*     */         }
/*     */       } 
/*     */     } 
/* 136 */     return kerberosTicket1;
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
/*     */   static KerberosTicket getServiceTicket(GSSCaller paramGSSCaller, String paramString1, String paramString2, AccessControlContext paramAccessControlContext) throws LoginException {
/* 148 */     Subject subject = Subject.getSubject(paramAccessControlContext);
/*     */     
/* 150 */     return SubjectComber.<KerberosTicket>find(subject, paramString2, paramString1, KerberosTicket.class);
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
/*     */   static KerberosTicket getInitialTicket(GSSCaller paramGSSCaller, String paramString, AccessControlContext paramAccessControlContext) throws LoginException {
/* 168 */     Subject subject = Subject.getSubject(paramAccessControlContext);
/*     */     
/* 170 */     KerberosTicket kerberosTicket = SubjectComber.<KerberosTicket>find(subject, null, paramString, KerberosTicket.class);
/*     */ 
/*     */ 
/*     */     
/* 174 */     if (kerberosTicket == null && !GSSUtil.useSubjectCredsOnly(paramGSSCaller)) {
/* 175 */       Subject subject1 = GSSUtil.login(paramGSSCaller, GSSUtil.GSS_KRB5_MECH_OID);
/* 176 */       kerberosTicket = SubjectComber.<KerberosTicket>find(subject1, null, paramString, KerberosTicket.class);
/*     */     } 
/*     */     
/* 179 */     return kerberosTicket;
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
/*     */   public static Subject getSubject(GSSCaller paramGSSCaller, AccessControlContext paramAccessControlContext) throws LoginException {
/* 197 */     Subject subject = Subject.getSubject(paramAccessControlContext);
/*     */ 
/*     */     
/* 200 */     if (subject == null && !GSSUtil.useSubjectCredsOnly(paramGSSCaller)) {
/* 201 */       subject = GSSUtil.login(paramGSSCaller, GSSUtil.GSS_KRB5_MECH_OID);
/*     */     }
/* 203 */     return subject;
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
/*     */   public static ServiceCreds getServiceCreds(GSSCaller paramGSSCaller, String paramString, AccessControlContext paramAccessControlContext) throws LoginException {
/* 217 */     Subject subject = Subject.getSubject(paramAccessControlContext);
/* 218 */     ServiceCreds serviceCreds = null;
/* 219 */     if (subject != null) {
/* 220 */       serviceCreds = ServiceCreds.getInstance(subject, paramString);
/*     */     }
/* 222 */     if (serviceCreds == null && !GSSUtil.useSubjectCredsOnly(paramGSSCaller)) {
/* 223 */       Subject subject1 = GSSUtil.login(paramGSSCaller, GSSUtil.GSS_KRB5_MECH_OID);
/* 224 */       serviceCreds = ServiceCreds.getInstance(subject1, paramString);
/*     */     } 
/* 226 */     return serviceCreds;
/*     */   }
/*     */   
/*     */   public static KerberosTicket credsToTicket(Credentials paramCredentials) {
/* 230 */     EncryptionKey encryptionKey = paramCredentials.getSessionKey();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     KerberosTicket kerberosTicket = new KerberosTicket(paramCredentials.getEncoded(), new KerberosPrincipal(paramCredentials.getClient().getName()), new KerberosPrincipal(paramCredentials.getServer().getName(), 2), encryptionKey.getBytes(), encryptionKey.getEType(), paramCredentials.getFlags(), paramCredentials.getAuthTime(), paramCredentials.getStartTime(), paramCredentials.getEndTime(), paramCredentials.getRenewTill(), paramCredentials.getClientAddresses());
/* 244 */     PrincipalName principalName1 = paramCredentials.getClientAlias();
/* 245 */     PrincipalName principalName2 = paramCredentials.getServerAlias();
/* 246 */     if (principalName1 != null) {
/* 247 */       KerberosSecrets.getJavaxSecurityAuthKerberosAccess()
/* 248 */         .kerberosTicketSetClientAlias(kerberosTicket, new KerberosPrincipal(principalName1
/* 249 */             .getName(), principalName1.getNameType()));
/*     */     }
/* 251 */     if (principalName2 != null) {
/* 252 */       KerberosSecrets.getJavaxSecurityAuthKerberosAccess()
/* 253 */         .kerberosTicketSetServerAlias(kerberosTicket, new KerberosPrincipal(principalName2
/* 254 */             .getName(), principalName2.getNameType()));
/*     */     }
/* 256 */     return kerberosTicket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Credentials ticketToCreds(KerberosTicket paramKerberosTicket) throws KrbException, IOException {
/* 263 */     KerberosPrincipal kerberosPrincipal1 = KerberosSecrets.getJavaxSecurityAuthKerberosAccess().kerberosTicketGetClientAlias(paramKerberosTicket);
/*     */ 
/*     */     
/* 266 */     KerberosPrincipal kerberosPrincipal2 = KerberosSecrets.getJavaxSecurityAuthKerberosAccess().kerberosTicketGetServerAlias(paramKerberosTicket);
/* 267 */     return new Credentials(paramKerberosTicket
/* 268 */         .getEncoded(), paramKerberosTicket
/* 269 */         .getClient().getName(), (kerberosPrincipal1 != null) ? kerberosPrincipal1
/* 270 */         .getName() : null, paramKerberosTicket
/* 271 */         .getServer().getName(), (kerberosPrincipal2 != null) ? kerberosPrincipal2
/* 272 */         .getName() : null, paramKerberosTicket
/* 273 */         .getSessionKey().getEncoded(), paramKerberosTicket
/* 274 */         .getSessionKeyType(), paramKerberosTicket
/* 275 */         .getFlags(), paramKerberosTicket
/* 276 */         .getAuthTime(), paramKerberosTicket
/* 277 */         .getStartTime(), paramKerberosTicket
/* 278 */         .getEndTime(), paramKerberosTicket
/* 279 */         .getRenewTill(), paramKerberosTicket
/* 280 */         .getClientAddresses());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyTab snapshotFromJavaxKeyTab(KeyTab paramKeyTab) {
/* 290 */     return KerberosSecrets.getJavaxSecurityAuthKerberosAccess()
/* 291 */       .keyTabTakeSnapshot(paramKeyTab);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncryptionKey[] keysFromJavaxKeyTab(KeyTab paramKeyTab, PrincipalName paramPrincipalName) {
/* 302 */     return snapshotFromJavaxKeyTab(paramKeyTab).readServiceKeys(paramPrincipalName);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/Krb5Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */