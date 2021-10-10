/*     */ package sun.security.jgss;
/*     */ 
/*     */ import com.sun.security.auth.callback.TextCallbackHandler;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.Security;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.kerberos.KerberosKey;
/*     */ import javax.security.auth.kerberos.KerberosPrincipal;
/*     */ import javax.security.auth.kerberos.KerberosTicket;
/*     */ import javax.security.auth.login.LoginContext;
/*     */ import javax.security.auth.login.LoginException;
/*     */ import org.ietf.jgss.GSSCredential;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.net.www.protocol.http.spnego.NegotiateCallbackHandler;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.jgss.krb5.Krb5NameElement;
/*     */ import sun.security.jgss.spi.GSSCredentialSpi;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.jgss.spnego.SpNegoCredElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GSSUtil
/*     */ {
/*  59 */   public static final Oid GSS_KRB5_MECH_OID = createOid("1.2.840.113554.1.2.2");
/*     */   
/*  61 */   public static final Oid GSS_KRB5_MECH_OID2 = createOid("1.3.5.1.5.2");
/*     */   
/*  63 */   public static final Oid GSS_KRB5_MECH_OID_MS = createOid("1.2.840.48018.1.2.2");
/*     */ 
/*     */   
/*  66 */   public static final Oid GSS_SPNEGO_MECH_OID = createOid("1.3.6.1.5.5.2");
/*     */ 
/*     */   
/*  69 */   public static final Oid NT_GSS_KRB5_PRINCIPAL = createOid("1.2.840.113554.1.2.2.1");
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String DEFAULT_HANDLER = "auth.login.defaultCallbackHandler";
/*     */ 
/*     */ 
/*     */   
/*  77 */   static final boolean DEBUG = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.security.jgss.debug")))
/*  78 */     .booleanValue();
/*     */ 
/*     */   
/*     */   static void debug(String paramString) {
/*  82 */     if (DEBUG) {
/*  83 */       assert paramString != null;
/*  84 */       System.out.println(paramString);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Oid createOid(String paramString) {
/*     */     try {
/*  93 */       return new Oid(paramString);
/*  94 */     } catch (GSSException gSSException) {
/*  95 */       debug("Ignored invalid OID: " + paramString);
/*  96 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isSpNegoMech(Oid paramOid) {
/* 101 */     return GSS_SPNEGO_MECH_OID.equals(paramOid);
/*     */   }
/*     */   
/*     */   public static boolean isKerberosMech(Oid paramOid) {
/* 105 */     return (GSS_KRB5_MECH_OID.equals(paramOid) || GSS_KRB5_MECH_OID2
/* 106 */       .equals(paramOid) || GSS_KRB5_MECH_OID_MS
/* 107 */       .equals(paramOid));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getMechStr(Oid paramOid) {
/* 112 */     if (isSpNegoMech(paramOid))
/* 113 */       return "SPNEGO"; 
/* 114 */     if (isKerberosMech(paramOid)) {
/* 115 */       return "Kerberos V5";
/*     */     }
/* 117 */     return paramOid.toString();
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
/*     */   public static Subject getSubject(GSSName paramGSSName, GSSCredential paramGSSCredential) {
/* 129 */     HashSet<Object> hashSet = null;
/* 130 */     HashSet<?> hashSet1 = new HashSet();
/*     */     
/* 132 */     Set<GSSCredentialSpi> set = null;
/*     */     
/* 134 */     HashSet<KerberosPrincipal> hashSet2 = new HashSet();
/*     */ 
/*     */     
/* 137 */     if (paramGSSName instanceof GSSNameImpl) {
/*     */       
/*     */       try {
/* 140 */         GSSNameSpi gSSNameSpi = ((GSSNameImpl)paramGSSName).getElement(GSS_KRB5_MECH_OID);
/* 141 */         String str = gSSNameSpi.toString();
/* 142 */         if (gSSNameSpi instanceof Krb5NameElement)
/*     */         {
/* 144 */           str = ((Krb5NameElement)gSSNameSpi).getKrb5PrincipalName().getName();
/*     */         }
/* 146 */         KerberosPrincipal kerberosPrincipal = new KerberosPrincipal(str);
/* 147 */         hashSet2.add(kerberosPrincipal);
/* 148 */       } catch (GSSException gSSException) {
/* 149 */         debug("Skipped name " + paramGSSName + " due to " + gSSException);
/*     */       } 
/*     */     }
/*     */     
/* 153 */     if (paramGSSCredential instanceof GSSCredentialImpl) {
/* 154 */       set = ((GSSCredentialImpl)paramGSSCredential).getElements();
/* 155 */       hashSet = new HashSet(set.size());
/* 156 */       populateCredentials(hashSet, set);
/*     */     } else {
/* 158 */       hashSet = new HashSet();
/*     */     } 
/* 160 */     debug("Created Subject with the following");
/* 161 */     debug("principals=" + hashSet2);
/* 162 */     debug("public creds=" + hashSet1);
/* 163 */     debug("private creds=" + hashSet);
/*     */     
/* 165 */     return new Subject(false, (Set)hashSet2, hashSet1, hashSet);
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
/*     */   private static void populateCredentials(Set<Object> paramSet, Set<?> paramSet1) {
/* 184 */     Iterator<?> iterator = paramSet1.iterator();
/* 185 */     while (iterator.hasNext()) {
/*     */       
/* 187 */       Object object = iterator.next();
/*     */ 
/*     */       
/* 190 */       if (object instanceof SpNegoCredElement) {
/* 191 */         object = ((SpNegoCredElement)object).getInternalCred();
/*     */       }
/*     */       
/* 194 */       if (object instanceof KerberosTicket) {
/*     */         
/* 196 */         if (!object.getClass().getName().equals("javax.security.auth.kerberos.KerberosTicket")) {
/* 197 */           KerberosTicket kerberosTicket = (KerberosTicket)object;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 208 */           object = new KerberosTicket(kerberosTicket.getEncoded(), kerberosTicket.getClient(), kerberosTicket.getServer(), kerberosTicket.getSessionKey().getEncoded(), kerberosTicket.getSessionKeyType(), kerberosTicket.getFlags(), kerberosTicket.getAuthTime(), kerberosTicket.getStartTime(), kerberosTicket.getEndTime(), kerberosTicket.getRenewTill(), kerberosTicket.getClientAddresses());
/*     */         } 
/* 210 */         paramSet.add(object); continue;
/* 211 */       }  if (object instanceof KerberosKey) {
/*     */         
/* 213 */         if (!object.getClass().getName().equals("javax.security.auth.kerberos.KerberosKey")) {
/* 214 */           KerberosKey kerberosKey = (KerberosKey)object;
/*     */ 
/*     */ 
/*     */           
/* 218 */           object = new KerberosKey(kerberosKey.getPrincipal(), kerberosKey.getEncoded(), kerberosKey.getKeyType(), kerberosKey.getVersionNumber());
/*     */         } 
/* 220 */         paramSet.add(object);
/*     */         continue;
/*     */       } 
/* 223 */       debug("Skipped cred element: " + object);
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
/*     */   public static Subject login(GSSCaller paramGSSCaller, Oid paramOid) throws LoginException {
/*     */     TextCallbackHandler textCallbackHandler;
/* 238 */     NegotiateCallbackHandler negotiateCallbackHandler = null;
/* 239 */     if (paramGSSCaller instanceof HttpCaller) {
/*     */       
/* 241 */       negotiateCallbackHandler = new NegotiateCallbackHandler(((HttpCaller)paramGSSCaller).info());
/*     */     } else {
/*     */       
/* 244 */       String str = Security.getProperty("auth.login.defaultCallbackHandler");
/*     */       
/* 246 */       if (str != null && str.length() != 0) {
/* 247 */         negotiateCallbackHandler = null;
/*     */       } else {
/* 249 */         textCallbackHandler = new TextCallbackHandler();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 256 */     LoginContext loginContext = new LoginContext("", null, textCallbackHandler, new LoginConfigImpl(paramGSSCaller, paramOid));
/*     */     
/* 258 */     loginContext.login();
/* 259 */     return loginContext.getSubject();
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
/*     */   public static boolean useSubjectCredsOnly(GSSCaller paramGSSCaller) {
/* 273 */     String str = GetPropertyAction.privilegedGetProperty("javax.security.auth.useSubjectCredsOnly");
/*     */ 
/*     */ 
/*     */     
/* 277 */     if (paramGSSCaller instanceof HttpCaller)
/*     */     {
/* 279 */       return "true".equalsIgnoreCase(str);
/*     */     }
/*     */     
/* 282 */     return !"false".equalsIgnoreCase(str);
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
/*     */   public static boolean useMSInterop() {
/* 298 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.security.spnego.msinterop", "true"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     return !str.equalsIgnoreCase("false");
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
/*     */   public static <T extends GSSCredentialSpi> Vector<T> searchSubject(final GSSNameSpi name, final Oid mech, final boolean initiate, final Class<? extends T> credCls) {
/* 320 */     debug("Search Subject for " + getMechStr(mech) + (initiate ? " INIT" : " ACCEPT") + " cred (" + ((name == null) ? "<<DEF>>" : name
/*     */         
/* 322 */         .toString()) + ", " + credCls
/* 323 */         .getName() + ")");
/* 324 */     final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */     
/*     */     try {
/* 328 */       return AccessController.<Vector>doPrivileged((PrivilegedExceptionAction)new PrivilegedExceptionAction<Vector<Vector<T>>>() {
/*     */             public Vector<T> run() throws Exception {
/* 330 */               Subject subject = Subject.getSubject(acc);
/* 331 */               Vector<T> vector = null;
/* 332 */               if (subject != null)
/* 333 */               { vector = new Vector();
/*     */ 
/*     */                 
/* 336 */                 Iterator<GSSCredentialImpl> iterator = subject.<GSSCredentialImpl>getPrivateCredentials(GSSCredentialImpl.class).iterator();
/* 337 */                 while (iterator.hasNext()) {
/* 338 */                   GSSCredentialImpl gSSCredentialImpl = iterator.next();
/* 339 */                   GSSUtil.debug("...Found cred" + gSSCredentialImpl);
/*     */                   
/*     */                   try {
/* 342 */                     GSSCredentialSpi gSSCredentialSpi = gSSCredentialImpl.getElement(mech, initiate);
/* 343 */                     GSSUtil.debug("......Found element: " + gSSCredentialSpi);
/* 344 */                     if (gSSCredentialSpi.getClass().equals(credCls) && (name == null || name
/*     */                       
/* 346 */                       .equals(gSSCredentialSpi.getName()))) {
/* 347 */                       vector.add(credCls.cast(gSSCredentialSpi)); continue;
/*     */                     } 
/* 349 */                     GSSUtil.debug("......Discard element");
/*     */                   }
/* 351 */                   catch (GSSException gSSException) {
/* 352 */                     GSSUtil.debug("...Discard cred (" + gSSException + ")");
/*     */                   } 
/*     */                 }  }
/* 355 */               else { GSSUtil.debug("No Subject"); }
/* 356 */                return vector;
/*     */             }
/*     */           });
/*     */     }
/* 360 */     catch (PrivilegedActionException privilegedActionException) {
/* 361 */       debug("Unexpected exception when searching Subject:");
/* 362 */       if (DEBUG) privilegedActionException.printStackTrace(); 
/* 363 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/GSSUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */