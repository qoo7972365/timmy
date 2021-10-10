/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import javax.security.auth.kerberos.KeyTab;
/*     */ import sun.security.jgss.krb5.Krb5Util;
/*     */ import sun.security.krb5.internal.HostAddresses;
/*     */ import sun.security.krb5.internal.KDCOptions;
/*     */ import sun.security.krb5.internal.KRBError;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.PAData;
/*     */ import sun.security.krb5.internal.ccache.Credentials;
/*     */ import sun.security.krb5.internal.crypto.EType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class KrbAsReqBuilder
/*     */ {
/*     */   private KDCOptions options;
/*     */   private PrincipalName cname;
/*     */   private PrincipalName refCname;
/*     */   private PrincipalName sname;
/*     */   private KerberosTime from;
/*     */   private KerberosTime till;
/*     */   private KerberosTime rtime;
/*     */   private HostAddresses addresses;
/*     */   private final char[] password;
/*     */   private final KeyTab ktab;
/*     */   private PAData[] paList;
/*     */   private KrbAsReq req;
/*     */   private KrbAsRep rep;
/*     */   private State state;
/*     */   
/*     */   private enum State
/*     */   {
/*  94 */     INIT,
/*  95 */     REQ_OK,
/*  96 */     DESTROYED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(PrincipalName paramPrincipalName) throws KrbException {
/* 103 */     this.cname = paramPrincipalName;
/* 104 */     this.refCname = paramPrincipalName;
/* 105 */     this.state = State.INIT;
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
/*     */   public KrbAsReqBuilder(PrincipalName paramPrincipalName, KeyTab paramKeyTab) throws KrbException {
/* 121 */     init(paramPrincipalName);
/* 122 */     this.ktab = paramKeyTab;
/* 123 */     this.password = null;
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
/*     */   public KrbAsReqBuilder(PrincipalName paramPrincipalName, char[] paramArrayOfchar) throws KrbException {
/* 139 */     init(paramPrincipalName);
/* 140 */     this.password = (char[])paramArrayOfchar.clone();
/* 141 */     this.ktab = null;
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
/*     */   public EncryptionKey[] getKeys(boolean paramBoolean) throws KrbException {
/* 157 */     checkState(paramBoolean ? State.REQ_OK : State.INIT, "Cannot get keys");
/* 158 */     if (this.password != null) {
/* 159 */       int[] arrayOfInt = EType.getDefaults("default_tkt_enctypes");
/* 160 */       EncryptionKey[] arrayOfEncryptionKey = new EncryptionKey[arrayOfInt.length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 178 */       String str = null; try {
/*     */         byte b;
/* 180 */         for (b = 0; b < arrayOfInt.length; b++) {
/*     */ 
/*     */           
/* 183 */           PAData.SaltAndParams saltAndParams = PAData.getSaltAndParams(arrayOfInt[b], this.paList);
/* 184 */           if (saltAndParams != null) {
/*     */ 
/*     */             
/* 187 */             if (arrayOfInt[b] != 23 && saltAndParams.salt != null)
/*     */             {
/* 189 */               str = saltAndParams.salt;
/*     */             }
/* 191 */             arrayOfEncryptionKey[b] = EncryptionKey.acquireSecretKey(this.cname, this.password, arrayOfInt[b], saltAndParams);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 198 */         if (str == null) str = this.cname.getSalt(); 
/* 199 */         for (b = 0; b < arrayOfInt.length; b++)
/*     */         {
/* 201 */           if (arrayOfEncryptionKey[b] == null) {
/* 202 */             arrayOfEncryptionKey[b] = EncryptionKey.acquireSecretKey(this.password, str, arrayOfInt[b], (byte[])null);
/*     */           
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 208 */       catch (IOException iOException) {
/* 209 */         KrbException krbException = new KrbException(909);
/* 210 */         krbException.initCause(iOException);
/* 211 */         throw krbException;
/*     */       } 
/* 213 */       return arrayOfEncryptionKey;
/*     */     } 
/* 215 */     throw new IllegalStateException("Required password not provided");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOptions(KDCOptions paramKDCOptions) {
/* 225 */     checkState(State.INIT, "Cannot specify options");
/* 226 */     this.options = paramKDCOptions;
/*     */   }
/*     */   
/*     */   public void setTill(KerberosTime paramKerberosTime) {
/* 230 */     checkState(State.INIT, "Cannot specify till");
/* 231 */     this.till = paramKerberosTime;
/*     */   }
/*     */   
/*     */   public void setRTime(KerberosTime paramKerberosTime) {
/* 235 */     checkState(State.INIT, "Cannot specify rtime");
/* 236 */     this.rtime = paramKerberosTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTarget(PrincipalName paramPrincipalName) {
/* 245 */     checkState(State.INIT, "Cannot specify target");
/* 246 */     this.sname = paramPrincipalName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAddresses(HostAddresses paramHostAddresses) {
/* 255 */     checkState(State.INIT, "Cannot specify addresses");
/* 256 */     this.addresses = paramHostAddresses;
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
/*     */   private KrbAsReq build(EncryptionKey paramEncryptionKey, ReferralsState paramReferralsState) throws KrbException, IOException {
/*     */     int[] arrayOfInt;
/* 269 */     PAData[] arrayOfPAData = null;
/*     */     
/* 271 */     if (this.password != null) {
/* 272 */       arrayOfInt = EType.getDefaults("default_tkt_enctypes");
/*     */     } else {
/* 274 */       EncryptionKey[] arrayOfEncryptionKey = Krb5Util.keysFromJavaxKeyTab(this.ktab, this.cname);
/* 275 */       arrayOfInt = EType.getDefaults("default_tkt_enctypes", arrayOfEncryptionKey);
/*     */       
/* 277 */       for (EncryptionKey encryptionKey : arrayOfEncryptionKey) encryptionKey.destroy(); 
/*     */     } 
/* 279 */     this.options = (this.options == null) ? new KDCOptions() : this.options;
/* 280 */     if (paramReferralsState.isEnabled()) {
/* 281 */       this.options.set(15, true);
/* 282 */       arrayOfPAData = new PAData[] { new PAData(149, new byte[0]) };
/*     */     } else {
/*     */       
/* 285 */       this.options.set(15, false);
/*     */     } 
/* 287 */     return new KrbAsReq(paramEncryptionKey, this.options, this.refCname, this.sname, this.from, this.till, this.rtime, arrayOfInt, this.addresses, arrayOfPAData);
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
/*     */   private KrbAsReqBuilder resolve() throws KrbException, Asn1Exception, IOException {
/* 307 */     if (this.ktab != null) {
/* 308 */       this.rep.decryptUsingKeyTab(this.ktab, this.req, this.cname);
/*     */     } else {
/* 310 */       this.rep.decryptUsingPassword(this.password, this.req, this.cname);
/*     */     } 
/* 312 */     if (this.rep.getPA() != null) {
/* 313 */       if (this.paList == null || this.paList.length == 0) {
/* 314 */         this.paList = this.rep.getPA();
/*     */       } else {
/* 316 */         int i = (this.rep.getPA()).length;
/* 317 */         if (i > 0) {
/* 318 */           int j = this.paList.length;
/* 319 */           this.paList = Arrays.<PAData>copyOf(this.paList, this.paList.length + i);
/* 320 */           System.arraycopy(this.rep.getPA(), 0, this.paList, j, i);
/*     */         } 
/*     */       } 
/*     */     }
/* 324 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private KrbAsReqBuilder send() throws KrbException, IOException {
/* 333 */     boolean bool = false;
/* 334 */     KdcComm kdcComm = null;
/* 335 */     EncryptionKey encryptionKey = null;
/* 336 */     ReferralsState referralsState = new ReferralsState();
/*     */     while (true) {
/* 338 */       if (referralsState.refreshComm()) {
/* 339 */         kdcComm = new KdcComm(this.refCname.getRealmAsString());
/*     */       }
/*     */       try {
/* 342 */         this.req = build(encryptionKey, referralsState);
/* 343 */         this.rep = new KrbAsRep(kdcComm.send(this.req.encoding()));
/* 344 */         return this;
/* 345 */       } catch (KrbException krbException) {
/* 346 */         if (!bool && (krbException
/* 347 */           .returnCode() == 24 || krbException
/* 348 */           .returnCode() == 25)) {
/* 349 */           if (Krb5.DEBUG) {
/* 350 */             System.out.println("KrbAsReqBuilder: PREAUTH FAILED/REQ, re-send AS-REQ");
/*     */           }
/*     */           
/* 353 */           bool = true;
/* 354 */           KRBError kRBError = krbException.getError();
/* 355 */           int i = PAData.getPreferredEType(kRBError.getPA(), 
/* 356 */               EType.getDefaults("default_tkt_enctypes")[0]);
/* 357 */           if (this.password == null) {
/* 358 */             EncryptionKey[] arrayOfEncryptionKey = Krb5Util.keysFromJavaxKeyTab(this.ktab, this.cname);
/* 359 */             encryptionKey = EncryptionKey.findKey(i, arrayOfEncryptionKey);
/* 360 */             if (encryptionKey != null) encryptionKey = (EncryptionKey)encryptionKey.clone(); 
/* 361 */             for (EncryptionKey encryptionKey1 : arrayOfEncryptionKey) encryptionKey1.destroy(); 
/*     */           } else {
/* 363 */             encryptionKey = EncryptionKey.acquireSecretKey(this.cname, this.password, i, 
/*     */ 
/*     */                 
/* 366 */                 PAData.getSaltAndParams(i, kRBError
/* 367 */                   .getPA()));
/*     */           } 
/* 369 */           this.paList = kRBError.getPA(); continue;
/*     */         } 
/* 371 */         if (referralsState.handleError(krbException)) {
/* 372 */           encryptionKey = null;
/* 373 */           bool = false; continue;
/*     */         }  break;
/*     */       } 
/* 376 */     }  throw krbException;
/*     */   }
/*     */ 
/*     */   
/*     */   private final class ReferralsState
/*     */   {
/*     */     private boolean enabled;
/*     */     
/*     */     private int count;
/*     */     private boolean refreshComm;
/*     */     
/*     */     ReferralsState() throws KrbException {
/* 388 */       if (Config.DISABLE_REFERRALS) {
/* 389 */         if (KrbAsReqBuilder.this.refCname.getNameType() == 10) {
/* 390 */           throw new KrbException("NT-ENTERPRISE principals only allowed when referrals are enabled.");
/*     */         }
/*     */         
/* 393 */         this.enabled = false;
/*     */       } else {
/* 395 */         this.enabled = true;
/*     */       } 
/* 397 */       this.refreshComm = true;
/*     */     }
/*     */     
/*     */     boolean handleError(KrbException param1KrbException) throws RealmException {
/* 401 */       if (this.enabled) {
/* 402 */         if (param1KrbException.returnCode() == 68) {
/* 403 */           Realm realm = param1KrbException.getError().getClientRealm();
/* 404 */           if ((KrbAsReqBuilder.this.req.getMessage()).reqBody.kdcOptions.get(15) && realm != null && realm
/* 405 */             .toString().length() > 0 && this.count < Config.MAX_REFERRALS) {
/*     */             
/* 407 */             KrbAsReqBuilder.this.refCname = new PrincipalName(KrbAsReqBuilder.this.refCname.getNameType(), KrbAsReqBuilder.this
/* 408 */                 .refCname.getNameStrings(), realm);
/* 409 */             this.refreshComm = true;
/* 410 */             this.count++;
/* 411 */             return true;
/*     */           } 
/*     */         } 
/* 414 */         if (this.count < Config.MAX_REFERRALS && KrbAsReqBuilder.this
/* 415 */           .refCname.getNameType() != 10) {
/*     */ 
/*     */           
/* 418 */           this.enabled = false;
/* 419 */           return true;
/*     */         } 
/*     */       } 
/* 422 */       return false;
/*     */     }
/*     */     
/*     */     boolean refreshComm() {
/* 426 */       boolean bool = this.refreshComm;
/* 427 */       this.refreshComm = false;
/* 428 */       return bool;
/*     */     }
/*     */     
/*     */     boolean isEnabled() {
/* 432 */       return this.enabled;
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
/*     */   public KrbAsReqBuilder action() throws KrbException, Asn1Exception, IOException {
/* 445 */     checkState(State.INIT, "Cannot call action");
/* 446 */     this.state = State.REQ_OK;
/* 447 */     return send().resolve();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials getCreds() {
/* 454 */     checkState(State.REQ_OK, "Cannot retrieve creds");
/* 455 */     return this.rep.getCreds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials getCCreds() {
/* 462 */     checkState(State.REQ_OK, "Cannot retrieve CCreds");
/* 463 */     return this.rep.getCCreds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 470 */     this.state = State.DESTROYED;
/* 471 */     if (this.password != null) {
/* 472 */       Arrays.fill(this.password, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkState(State paramState, String paramString) {
/* 483 */     if (this.state != paramState)
/* 484 */       throw new IllegalStateException(paramString + " at " + paramState + " state"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KrbAsReqBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */