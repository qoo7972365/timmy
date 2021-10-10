/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedList;
/*     */ import sun.security.krb5.Config;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.KrbTgsReq;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.Realm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CredentialsUtil
/*     */ {
/*  46 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Credentials acquireS4U2selfCreds(PrincipalName paramPrincipalName, Credentials paramCredentials) throws KrbException, IOException {
/*  57 */     String str1 = paramPrincipalName.getRealmString();
/*  58 */     String str2 = paramCredentials.getClient().getRealmString();
/*  59 */     if (!str1.equals(str2))
/*     */     {
/*  61 */       throw new KrbException("Cross realm impersonation not supported");
/*     */     }
/*  63 */     if (!paramCredentials.isForwardable()) {
/*  64 */       throw new KrbException("S4U2self needs a FORWARDABLE ticket");
/*     */     }
/*  66 */     Credentials credentials = serviceCreds(KDCOptions.with(new int[] { 1 }, ), paramCredentials, paramCredentials
/*  67 */         .getClient(), paramCredentials.getClient(), null, new PAData[] { new PAData(129, (new PAForUserEnc(paramPrincipalName, paramCredentials
/*     */ 
/*     */               
/*  70 */               .getSessionKey())).asn1Encode()) });
/*  71 */     if (!credentials.getClient().equals(paramPrincipalName)) {
/*  72 */       throw new KrbException("S4U2self request not honored by KDC");
/*     */     }
/*  74 */     if (!credentials.isForwardable()) {
/*  75 */       throw new KrbException("S4U2self ticket must be FORWARDABLE");
/*     */     }
/*  77 */     return credentials;
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
/*     */   public static Credentials acquireS4U2proxyCreds(String paramString, Ticket paramTicket, PrincipalName paramPrincipalName, Credentials paramCredentials) throws KrbException, IOException {
/*  92 */     Credentials credentials = serviceCreds(KDCOptions.with(new int[] { 14, 1 }, ), paramCredentials, paramCredentials
/*     */         
/*  94 */         .getClient(), new PrincipalName(paramString), new Ticket[] { paramTicket }, null);
/*     */     
/*  96 */     if (!credentials.getClient().equals(paramPrincipalName)) {
/*  97 */       throw new KrbException("S4U2proxy request not honored by KDC");
/*     */     }
/*  99 */     return credentials;
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
/*     */   public static Credentials acquireServiceCreds(String paramString, Credentials paramCredentials) throws KrbException, IOException {
/* 116 */     PrincipalName principalName = new PrincipalName(paramString, 3);
/*     */     
/* 118 */     return serviceCreds(principalName, paramCredentials);
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
/*     */   private static Credentials getTGTforRealm(String paramString1, String paramString2, Credentials paramCredentials, boolean[] paramArrayOfboolean) throws KrbException {
/* 136 */     String[] arrayOfString = Realm.getRealmsList(paramString1, paramString2);
/*     */     
/* 138 */     int i = 0, j = 0;
/* 139 */     Credentials credentials1 = null, credentials2 = null, credentials3 = null;
/* 140 */     PrincipalName principalName = null;
/* 141 */     String str = null;
/*     */     
/* 143 */     paramArrayOfboolean[0] = true;
/* 144 */     for (credentials1 = paramCredentials, i = 0; i < arrayOfString.length; ) {
/* 145 */       principalName = PrincipalName.tgsService(paramString2, arrayOfString[i]);
/*     */       
/* 147 */       if (DEBUG) {
/* 148 */         System.out.println(">>> Credentials acquireServiceCreds: main loop: [" + i + "] tempService=" + principalName);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 154 */         credentials2 = serviceCreds(principalName, credentials1);
/* 155 */       } catch (Exception exception) {
/* 156 */         credentials2 = null;
/*     */       } 
/*     */       
/* 159 */       if (credentials2 == null) {
/* 160 */         if (DEBUG) {
/* 161 */           System.out.println(">>> Credentials acquireServiceCreds: no tgt; searching thru capath");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 168 */         credentials2 = null; j = i + 1;
/* 169 */         for (; credentials2 == null && j < arrayOfString.length; j++) {
/* 170 */           principalName = PrincipalName.tgsService(arrayOfString[j], arrayOfString[i]);
/* 171 */           if (DEBUG) {
/* 172 */             System.out.println(">>> Credentials acquireServiceCreds: inner loop: [" + j + "] tempService=" + principalName);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 178 */             credentials2 = serviceCreds(principalName, credentials1);
/* 179 */           } catch (Exception exception) {
/* 180 */             credentials2 = null;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 185 */       if (credentials2 == null) {
/* 186 */         if (DEBUG) {
/* 187 */           System.out.println(">>> Credentials acquireServiceCreds: no tgt; cannot get creds");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 197 */       str = credentials2.getServer().getInstanceComponent();
/* 198 */       if (paramArrayOfboolean[0] && !credentials2.checkDelegate()) {
/* 199 */         if (DEBUG) {
/* 200 */           System.out.println(">>> Credentials acquireServiceCreds: global OK-AS-DELEGATE turned off at " + credentials2
/*     */               
/* 202 */               .getServer());
/*     */         }
/* 204 */         paramArrayOfboolean[0] = false;
/*     */       } 
/*     */       
/* 207 */       if (DEBUG) {
/* 208 */         System.out.println(">>> Credentials acquireServiceCreds: got tgt");
/*     */       }
/*     */ 
/*     */       
/* 212 */       if (str.equals(paramString2)) {
/*     */         
/* 214 */         credentials3 = credentials2;
/*     */ 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 223 */       for (j = i + 1; j < arrayOfString.length && 
/* 224 */         !str.equals(arrayOfString[j]); j++);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 229 */       if (j < arrayOfString.length) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 234 */         i = j;
/* 235 */         credentials1 = credentials2;
/*     */         
/* 237 */         if (DEBUG) {
/* 238 */           System.out.println(">>> Credentials acquireServiceCreds: continuing with main loop counter reset to " + i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     return credentials3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Credentials serviceCreds(PrincipalName paramPrincipalName, Credentials paramCredentials) throws KrbException, IOException {
/* 263 */     return serviceCreds(new KDCOptions(), paramCredentials, paramCredentials
/* 264 */         .getClient(), paramPrincipalName, null, null);
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
/*     */   private static Credentials serviceCreds(KDCOptions paramKDCOptions, Credentials paramCredentials, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, Ticket[] paramArrayOfTicket, PAData[] paramArrayOfPAData) throws KrbException, IOException {
/* 278 */     if (!Config.DISABLE_REFERRALS) {
/*     */       try {
/* 280 */         return serviceCredsReferrals(paramKDCOptions, paramCredentials, paramPrincipalName1, paramPrincipalName2, paramArrayOfTicket, paramArrayOfPAData);
/*     */       }
/* 282 */       catch (KrbException krbException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 287 */     return serviceCredsSingle(paramKDCOptions, paramCredentials, paramPrincipalName1, paramCredentials
/* 288 */         .getClientAlias(), paramPrincipalName2, paramPrincipalName2, paramArrayOfTicket, paramArrayOfPAData);
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
/*     */   private static Credentials serviceCredsReferrals(KDCOptions paramKDCOptions, Credentials paramCredentials, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, Ticket[] paramArrayOfTicket, PAData[] paramArrayOfPAData) throws KrbException, IOException {
/* 301 */     paramKDCOptions = new KDCOptions(paramKDCOptions.toBooleanArray());
/* 302 */     paramKDCOptions.set(15, true);
/* 303 */     PrincipalName principalName1 = paramPrincipalName2;
/* 304 */     PrincipalName principalName2 = paramPrincipalName2;
/* 305 */     Credentials credentials = null;
/* 306 */     boolean bool = false;
/* 307 */     LinkedList<String> linkedList = new LinkedList();
/* 308 */     PrincipalName principalName3 = paramCredentials.getClientAlias();
/* 309 */     while (linkedList.size() <= Config.MAX_REFERRALS) {
/*     */       
/* 311 */       ReferralsCache.ReferralCacheEntry referralCacheEntry = ReferralsCache.get(paramPrincipalName1, paramPrincipalName2, principalName2.getRealmString());
/* 312 */       String str = null;
/* 313 */       if (referralCacheEntry == null) {
/* 314 */         credentials = serviceCredsSingle(paramKDCOptions, paramCredentials, paramPrincipalName1, principalName3, principalName2, principalName1, paramArrayOfTicket, paramArrayOfPAData);
/*     */ 
/*     */         
/* 317 */         PrincipalName principalName = credentials.getServer();
/* 318 */         if (!principalName2.equals(principalName)) {
/* 319 */           String[] arrayOfString = principalName.getNameStrings();
/* 320 */           if (arrayOfString.length == 2 && arrayOfString[0]
/* 321 */             .equals("krbtgt") && 
/*     */             
/* 323 */             !principalName2.getRealmAsString().equals(arrayOfString[1])) {
/*     */ 
/*     */             
/* 326 */             ReferralsCache.put(paramPrincipalName1, paramPrincipalName2, principalName.getRealmString(), arrayOfString[1], credentials);
/*     */             
/* 328 */             str = arrayOfString[1];
/* 329 */             bool = true;
/* 330 */             paramCredentials = credentials;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 334 */         str = referralCacheEntry.getToRealm();
/* 335 */         paramCredentials = referralCacheEntry.getCreds();
/* 336 */         bool = true;
/*     */       } 
/* 338 */       if (bool) {
/* 339 */         if (linkedList.contains(str))
/*     */         {
/* 341 */           return null;
/*     */         }
/*     */         
/* 344 */         principalName2 = new PrincipalName(principalName2.getNameString(), principalName2.getNameType(), str);
/* 345 */         linkedList.add(str);
/* 346 */         bool = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 351 */     return credentials;
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
/*     */   private static Credentials serviceCredsSingle(KDCOptions paramKDCOptions, Credentials paramCredentials, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, PrincipalName paramPrincipalName3, PrincipalName paramPrincipalName4, Ticket[] paramArrayOfTicket, PAData[] paramArrayOfPAData) throws KrbException, IOException {
/* 367 */     Credentials credentials = null;
/* 368 */     boolean[] arrayOfBoolean = { true };
/* 369 */     String[] arrayOfString = paramCredentials.getServer().getNameStrings();
/* 370 */     String str1 = arrayOfString[1];
/* 371 */     String str2 = paramPrincipalName3.getRealmString();
/* 372 */     if (!str2.equals(str1)) {
/*     */       
/* 374 */       if (DEBUG) {
/* 375 */         System.out.println(">>> serviceCredsSingle: cross-realm authentication");
/*     */         
/* 377 */         System.out.println(">>> serviceCredsSingle: obtaining credentials from " + str1 + " to " + str2);
/*     */       } 
/*     */ 
/*     */       
/* 381 */       Credentials credentials1 = getTGTforRealm(str1, str2, paramCredentials, arrayOfBoolean);
/*     */       
/* 383 */       if (credentials1 == null) {
/* 384 */         throw new KrbApErrException(63, "No service creds");
/*     */       }
/*     */       
/* 387 */       if (DEBUG) {
/* 388 */         System.out.println(">>> Cross-realm TGT Credentials serviceCredsSingle: ");
/*     */         
/* 390 */         Credentials.printDebug(credentials1);
/*     */       } 
/* 392 */       paramCredentials = credentials1;
/* 393 */       paramPrincipalName1 = paramCredentials.getClient();
/* 394 */     } else if (DEBUG) {
/* 395 */       System.out.println(">>> Credentials serviceCredsSingle: same realm");
/*     */     } 
/*     */     
/* 398 */     KrbTgsReq krbTgsReq = new KrbTgsReq(paramKDCOptions, paramCredentials, paramPrincipalName1, paramPrincipalName2, paramPrincipalName3, paramPrincipalName4, paramArrayOfTicket, paramArrayOfPAData);
/*     */     
/* 400 */     credentials = krbTgsReq.sendAndGetCreds();
/* 401 */     if (credentials != null) {
/* 402 */       if (DEBUG) {
/* 403 */         System.out.println(">>> TGS credentials serviceCredsSingle:");
/* 404 */         Credentials.printDebug(credentials);
/*     */       } 
/* 406 */       if (!arrayOfBoolean[0]) {
/* 407 */         credentials.resetDelegate();
/*     */       }
/*     */     } 
/* 410 */     return credentials;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/CredentialsUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */