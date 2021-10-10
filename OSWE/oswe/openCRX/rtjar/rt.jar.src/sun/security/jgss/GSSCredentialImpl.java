/*     */ package sun.security.jgss;
/*     */ 
/*     */ import com.sun.security.jgss.ExtendedGSSCredential;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import org.ietf.jgss.GSSCredential;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.Oid;
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
/*     */ public class GSSCredentialImpl
/*     */   implements ExtendedGSSCredential
/*     */ {
/*  36 */   private GSSManagerImpl gssManager = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean destroyed = false;
/*     */ 
/*     */ 
/*     */   
/*  45 */   private Hashtable<SearchKey, GSSCredentialSpi> hashtable = null;
/*     */ 
/*     */   
/*  48 */   private GSSCredentialSpi tempCred = null;
/*     */ 
/*     */   
/*     */   GSSCredentialImpl(GSSManagerImpl paramGSSManagerImpl, int paramInt) throws GSSException {
/*  52 */     this(paramGSSManagerImpl, (GSSName)null, 0, (Oid[])null, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GSSCredentialImpl(GSSManagerImpl paramGSSManagerImpl, GSSName paramGSSName, int paramInt1, Oid paramOid, int paramInt2) throws GSSException {
/*  59 */     if (paramOid == null) paramOid = ProviderList.DEFAULT_MECH_OID;
/*     */     
/*  61 */     init(paramGSSManagerImpl);
/*  62 */     add(paramGSSName, paramInt1, paramInt1, paramOid, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   GSSCredentialImpl(GSSManagerImpl paramGSSManagerImpl, GSSName paramGSSName, int paramInt1, Oid[] paramArrayOfOid, int paramInt2) throws GSSException {
/*  68 */     init(paramGSSManagerImpl);
/*  69 */     boolean bool = false;
/*  70 */     if (paramArrayOfOid == null) {
/*  71 */       paramArrayOfOid = paramGSSManagerImpl.getMechs();
/*  72 */       bool = true;
/*     */     } 
/*     */     
/*  75 */     for (byte b = 0; b < paramArrayOfOid.length; b++) {
/*     */       try {
/*  77 */         add(paramGSSName, paramInt1, paramInt1, paramArrayOfOid[b], paramInt2);
/*  78 */       } catch (GSSException gSSException) {
/*  79 */         if (bool) {
/*     */           
/*  81 */           GSSUtil.debug("Ignore " + gSSException + " while acquring cred for " + paramArrayOfOid[b]);
/*     */         } else {
/*     */           
/*  84 */           throw gSSException;
/*     */         } 
/*     */       } 
/*  87 */     }  if (this.hashtable.size() == 0 || paramInt2 != getUsage()) {
/*  88 */       throw new GSSException(13);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSCredentialImpl(GSSManagerImpl paramGSSManagerImpl, GSSCredentialSpi paramGSSCredentialSpi) throws GSSException {
/*  95 */     init(paramGSSManagerImpl);
/*  96 */     byte b = 2;
/*  97 */     if (paramGSSCredentialSpi.isInitiatorCredential()) {
/*  98 */       if (paramGSSCredentialSpi.isAcceptorCredential()) {
/*  99 */         b = 0;
/*     */       } else {
/* 101 */         b = 1;
/*     */       } 
/*     */     }
/* 104 */     SearchKey searchKey = new SearchKey(paramGSSCredentialSpi.getMechanism(), b);
/*     */     
/* 106 */     this.tempCred = paramGSSCredentialSpi;
/* 107 */     this.hashtable.put(searchKey, this.tempCred);
/*     */     
/* 109 */     if (!GSSUtil.isSpNegoMech(paramGSSCredentialSpi.getMechanism())) {
/* 110 */       searchKey = new SearchKey(GSSUtil.GSS_SPNEGO_MECH_OID, b);
/* 111 */       this.hashtable.put(searchKey, new SpNegoCredElement(paramGSSCredentialSpi));
/*     */     } 
/*     */   }
/*     */   
/*     */   void init(GSSManagerImpl paramGSSManagerImpl) {
/* 116 */     this.gssManager = paramGSSManagerImpl;
/* 117 */     this
/* 118 */       .hashtable = new Hashtable<>((paramGSSManagerImpl.getMechs()).length);
/*     */   }
/*     */   
/*     */   public void dispose() throws GSSException {
/* 122 */     if (!this.destroyed) {
/*     */       
/* 124 */       Enumeration<GSSCredentialSpi> enumeration = this.hashtable.elements();
/* 125 */       while (enumeration.hasMoreElements()) {
/* 126 */         GSSCredentialSpi gSSCredentialSpi = enumeration.nextElement();
/* 127 */         gSSCredentialSpi.dispose();
/*     */       } 
/* 129 */       this.destroyed = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public GSSCredential impersonate(GSSName paramGSSName) throws GSSException {
/* 134 */     if (this.destroyed) {
/* 135 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */     
/* 138 */     Oid oid = this.tempCred.getMechanism();
/*     */     
/* 140 */     GSSNameSpi gSSNameSpi = (paramGSSName == null) ? null : ((GSSNameImpl)paramGSSName).getElement(oid);
/* 141 */     GSSCredentialSpi gSSCredentialSpi = this.tempCred.impersonate(gSSNameSpi);
/* 142 */     return (gSSCredentialSpi == null) ? null : new GSSCredentialImpl(this.gssManager, gSSCredentialSpi);
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSName getName() throws GSSException {
/* 147 */     if (this.destroyed) {
/* 148 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */     
/* 151 */     return GSSNameImpl.wrapElement(this.gssManager, this.tempCred.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSName getName(Oid paramOid) throws GSSException {
/* 156 */     if (this.destroyed) {
/* 157 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */ 
/*     */     
/* 161 */     SearchKey searchKey = null;
/* 162 */     GSSCredentialSpi gSSCredentialSpi = null;
/*     */     
/* 164 */     if (paramOid == null) paramOid = ProviderList.DEFAULT_MECH_OID;
/*     */     
/* 166 */     searchKey = new SearchKey(paramOid, 1);
/* 167 */     gSSCredentialSpi = this.hashtable.get(searchKey);
/*     */     
/* 169 */     if (gSSCredentialSpi == null) {
/* 170 */       searchKey = new SearchKey(paramOid, 2);
/* 171 */       gSSCredentialSpi = this.hashtable.get(searchKey);
/*     */     } 
/*     */     
/* 174 */     if (gSSCredentialSpi == null) {
/* 175 */       searchKey = new SearchKey(paramOid, 0);
/* 176 */       gSSCredentialSpi = this.hashtable.get(searchKey);
/*     */     } 
/*     */     
/* 179 */     if (gSSCredentialSpi == null) {
/* 180 */       throw new GSSExceptionImpl(2, paramOid);
/*     */     }
/*     */     
/* 183 */     return GSSNameImpl.wrapElement(this.gssManager, gSSCredentialSpi.getName());
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
/*     */   public int getRemainingLifetime() throws GSSException {
/* 195 */     if (this.destroyed) {
/* 196 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     int i = 0, j = 0, k = 0;
/* 203 */     int m = Integer.MAX_VALUE;
/*     */     
/* 205 */     Enumeration<SearchKey> enumeration = this.hashtable.keys();
/* 206 */     while (enumeration.hasMoreElements()) {
/* 207 */       SearchKey searchKey = enumeration.nextElement();
/* 208 */       GSSCredentialSpi gSSCredentialSpi = this.hashtable.get(searchKey);
/* 209 */       if (searchKey.getUsage() == 1) {
/* 210 */         i = gSSCredentialSpi.getInitLifetime();
/* 211 */       } else if (searchKey.getUsage() == 2) {
/* 212 */         i = gSSCredentialSpi.getAcceptLifetime();
/*     */       } else {
/* 214 */         j = gSSCredentialSpi.getInitLifetime();
/* 215 */         k = gSSCredentialSpi.getAcceptLifetime();
/* 216 */         i = (j < k) ? j : k;
/*     */       } 
/*     */ 
/*     */       
/* 220 */       if (m > i) {
/* 221 */         m = i;
/*     */       }
/*     */     } 
/* 224 */     return m;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRemainingInitLifetime(Oid paramOid) throws GSSException {
/* 229 */     if (this.destroyed) {
/* 230 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */ 
/*     */     
/* 234 */     GSSCredentialSpi gSSCredentialSpi = null;
/* 235 */     SearchKey searchKey = null;
/* 236 */     boolean bool = false;
/* 237 */     int i = 0;
/*     */     
/* 239 */     if (paramOid == null) paramOid = ProviderList.DEFAULT_MECH_OID;
/*     */     
/* 241 */     searchKey = new SearchKey(paramOid, 1);
/* 242 */     gSSCredentialSpi = this.hashtable.get(searchKey);
/*     */     
/* 244 */     if (gSSCredentialSpi != null) {
/* 245 */       bool = true;
/* 246 */       if (i < gSSCredentialSpi.getInitLifetime()) {
/* 247 */         i = gSSCredentialSpi.getInitLifetime();
/*     */       }
/*     */     } 
/* 250 */     searchKey = new SearchKey(paramOid, 0);
/* 251 */     gSSCredentialSpi = this.hashtable.get(searchKey);
/*     */     
/* 253 */     if (gSSCredentialSpi != null) {
/* 254 */       bool = true;
/* 255 */       if (i < gSSCredentialSpi.getInitLifetime()) {
/* 256 */         i = gSSCredentialSpi.getInitLifetime();
/*     */       }
/*     */     } 
/* 259 */     if (!bool) {
/* 260 */       throw new GSSExceptionImpl(2, paramOid);
/*     */     }
/*     */     
/* 263 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRemainingAcceptLifetime(Oid paramOid) throws GSSException {
/* 269 */     if (this.destroyed) {
/* 270 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */ 
/*     */     
/* 274 */     GSSCredentialSpi gSSCredentialSpi = null;
/* 275 */     SearchKey searchKey = null;
/* 276 */     boolean bool = false;
/* 277 */     int i = 0;
/*     */     
/* 279 */     if (paramOid == null) paramOid = ProviderList.DEFAULT_MECH_OID;
/*     */     
/* 281 */     searchKey = new SearchKey(paramOid, 2);
/* 282 */     gSSCredentialSpi = this.hashtable.get(searchKey);
/*     */     
/* 284 */     if (gSSCredentialSpi != null) {
/* 285 */       bool = true;
/* 286 */       if (i < gSSCredentialSpi.getAcceptLifetime()) {
/* 287 */         i = gSSCredentialSpi.getAcceptLifetime();
/*     */       }
/*     */     } 
/* 290 */     searchKey = new SearchKey(paramOid, 0);
/* 291 */     gSSCredentialSpi = this.hashtable.get(searchKey);
/*     */     
/* 293 */     if (gSSCredentialSpi != null) {
/* 294 */       bool = true;
/* 295 */       if (i < gSSCredentialSpi.getAcceptLifetime()) {
/* 296 */         i = gSSCredentialSpi.getAcceptLifetime();
/*     */       }
/*     */     } 
/* 299 */     if (!bool) {
/* 300 */       throw new GSSExceptionImpl(2, paramOid);
/*     */     }
/*     */     
/* 303 */     return i;
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
/*     */   public int getUsage() throws GSSException {
/* 315 */     if (this.destroyed) {
/* 316 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 321 */     boolean bool1 = false;
/* 322 */     boolean bool2 = false;
/*     */     
/* 324 */     Enumeration<SearchKey> enumeration = this.hashtable.keys();
/* 325 */     while (enumeration.hasMoreElements()) {
/* 326 */       SearchKey searchKey = enumeration.nextElement();
/* 327 */       if (searchKey.getUsage() == 1) {
/* 328 */         bool1 = true; continue;
/* 329 */       }  if (searchKey.getUsage() == 2) {
/* 330 */         bool2 = true; continue;
/*     */       } 
/* 332 */       return 0;
/*     */     } 
/* 334 */     if (bool1) {
/* 335 */       if (bool2) {
/* 336 */         return 0;
/*     */       }
/* 338 */       return 1;
/*     */     } 
/* 340 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUsage(Oid paramOid) throws GSSException {
/* 345 */     if (this.destroyed) {
/* 346 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */ 
/*     */     
/* 350 */     GSSCredentialSpi gSSCredentialSpi = null;
/* 351 */     SearchKey searchKey = null;
/* 352 */     boolean bool1 = false;
/* 353 */     boolean bool2 = false;
/*     */     
/* 355 */     if (paramOid == null) paramOid = ProviderList.DEFAULT_MECH_OID;
/*     */     
/* 357 */     searchKey = new SearchKey(paramOid, 1);
/* 358 */     gSSCredentialSpi = this.hashtable.get(searchKey);
/*     */     
/* 360 */     if (gSSCredentialSpi != null) {
/* 361 */       bool1 = true;
/*     */     }
/*     */     
/* 364 */     searchKey = new SearchKey(paramOid, 2);
/* 365 */     gSSCredentialSpi = this.hashtable.get(searchKey);
/*     */     
/* 367 */     if (gSSCredentialSpi != null) {
/* 368 */       bool2 = true;
/*     */     }
/*     */     
/* 371 */     searchKey = new SearchKey(paramOid, 0);
/* 372 */     gSSCredentialSpi = this.hashtable.get(searchKey);
/*     */     
/* 374 */     if (gSSCredentialSpi != null) {
/* 375 */       bool1 = true;
/* 376 */       bool2 = true;
/*     */     } 
/*     */     
/* 379 */     if (bool1 && bool2)
/* 380 */       return 0; 
/* 381 */     if (bool1)
/* 382 */       return 1; 
/* 383 */     if (bool2) {
/* 384 */       return 2;
/*     */     }
/* 386 */     throw new GSSExceptionImpl(2, paramOid);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Oid[] getMechs() throws GSSException {
/* 392 */     if (this.destroyed) {
/* 393 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */     
/* 396 */     Vector<Oid> vector = new Vector(this.hashtable.size());
/*     */     
/* 398 */     Enumeration<SearchKey> enumeration = this.hashtable.keys();
/* 399 */     while (enumeration.hasMoreElements()) {
/* 400 */       SearchKey searchKey = enumeration.nextElement();
/* 401 */       vector.addElement(searchKey.getMech());
/*     */     } 
/* 403 */     return vector.<Oid>toArray(new Oid[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(GSSName paramGSSName, int paramInt1, int paramInt2, Oid paramOid, int paramInt3) throws GSSException {
/* 409 */     if (this.destroyed) {
/* 410 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */     
/* 413 */     if (paramOid == null) paramOid = ProviderList.DEFAULT_MECH_OID;
/*     */     
/* 415 */     SearchKey searchKey = new SearchKey(paramOid, paramInt3);
/* 416 */     if (this.hashtable.containsKey(searchKey)) {
/* 417 */       throw new GSSExceptionImpl(17, "Duplicate element found: " + 
/*     */           
/* 419 */           getElementStr(paramOid, paramInt3));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 425 */     GSSNameSpi gSSNameSpi = (paramGSSName == null) ? null : ((GSSNameImpl)paramGSSName).getElement(paramOid);
/*     */     
/* 427 */     this.tempCred = this.gssManager.getCredentialElement(gSSNameSpi, paramInt1, paramInt2, paramOid, paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 446 */     if (this.tempCred != null) {
/* 447 */       if (paramInt3 == 0 && (
/* 448 */         !this.tempCred.isAcceptorCredential() || 
/* 449 */         !this.tempCred.isInitiatorCredential())) {
/*     */         boolean bool;
/*     */         
/*     */         byte b;
/*     */         
/* 454 */         if (!this.tempCred.isInitiatorCredential()) {
/* 455 */           bool = true;
/* 456 */           b = 1;
/*     */         } else {
/* 458 */           bool = true;
/* 459 */           b = 2;
/*     */         } 
/*     */         
/* 462 */         searchKey = new SearchKey(paramOid, bool);
/* 463 */         this.hashtable.put(searchKey, this.tempCred);
/*     */         
/* 465 */         this.tempCred = this.gssManager.getCredentialElement(gSSNameSpi, paramInt1, paramInt2, paramOid, b);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 471 */         searchKey = new SearchKey(paramOid, b);
/* 472 */         this.hashtable.put(searchKey, this.tempCred);
/*     */       } else {
/* 474 */         this.hashtable.put(searchKey, this.tempCred);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 481 */     if (this.destroyed) {
/* 482 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */ 
/*     */     
/* 486 */     if (this == paramObject) {
/* 487 */       return true;
/*     */     }
/*     */     
/* 490 */     if (!(paramObject instanceof GSSCredentialImpl)) {
/* 491 */       return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 510 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 521 */     if (this.destroyed) {
/* 522 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 532 */     return 1;
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
/*     */   public GSSCredentialSpi getElement(Oid paramOid, boolean paramBoolean) throws GSSException {
/*     */     GSSCredentialSpi gSSCredentialSpi;
/* 548 */     if (this.destroyed) {
/* 549 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 556 */     if (paramOid == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 561 */       paramOid = ProviderList.DEFAULT_MECH_OID;
/* 562 */       SearchKey searchKey = new SearchKey(paramOid, paramBoolean ? 1 : 2);
/*     */       
/* 564 */       gSSCredentialSpi = this.hashtable.get(searchKey);
/* 565 */       if (gSSCredentialSpi == null) {
/* 566 */         searchKey = new SearchKey(paramOid, 0);
/* 567 */         gSSCredentialSpi = this.hashtable.get(searchKey);
/* 568 */         if (gSSCredentialSpi == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 573 */           Object[] arrayOfObject = this.hashtable.entrySet().toArray();
/* 574 */           for (byte b = 0; b < arrayOfObject.length; b++) {
/*     */             
/* 576 */             gSSCredentialSpi = ((Map.Entry<?, GSSCredentialSpi>)arrayOfObject[b]).getValue();
/* 577 */             if (gSSCredentialSpi.isInitiatorCredential() == paramBoolean)
/*     */               break; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       SearchKey searchKey;
/* 584 */       if (paramBoolean) {
/* 585 */         searchKey = new SearchKey(paramOid, 1);
/*     */       } else {
/* 587 */         searchKey = new SearchKey(paramOid, 2);
/*     */       } 
/* 589 */       gSSCredentialSpi = this.hashtable.get(searchKey);
/*     */       
/* 591 */       if (gSSCredentialSpi == null) {
/* 592 */         searchKey = new SearchKey(paramOid, 0);
/* 593 */         gSSCredentialSpi = this.hashtable.get(searchKey);
/*     */       } 
/*     */     } 
/*     */     
/* 597 */     if (gSSCredentialSpi == null) {
/* 598 */       throw new GSSExceptionImpl(13, "No credential found for: " + 
/*     */           
/* 600 */           getElementStr(paramOid, paramBoolean ? 1 : 2));
/*     */     }
/* 602 */     return gSSCredentialSpi;
/*     */   }
/*     */ 
/*     */   
/*     */   Set<GSSCredentialSpi> getElements() {
/* 607 */     HashSet<GSSCredentialSpi> hashSet = new HashSet(this.hashtable.size());
/* 608 */     Enumeration<GSSCredentialSpi> enumeration = this.hashtable.elements();
/* 609 */     while (enumeration.hasMoreElements()) {
/* 610 */       GSSCredentialSpi gSSCredentialSpi = enumeration.nextElement();
/* 611 */       hashSet.add(gSSCredentialSpi);
/*     */     } 
/* 613 */     return hashSet;
/*     */   }
/*     */   
/*     */   private static String getElementStr(Oid paramOid, int paramInt) {
/* 617 */     String str = paramOid.toString();
/* 618 */     if (paramInt == 1) {
/*     */       
/* 620 */       str = str.concat(" usage: Initiate");
/* 621 */     } else if (paramInt == 2) {
/*     */       
/* 623 */       str = str.concat(" usage: Accept");
/*     */     } else {
/*     */       
/* 626 */       str = str.concat(" usage: Initiate and Accept");
/*     */     } 
/* 628 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 633 */     if (this.destroyed) {
/* 634 */       throw new IllegalStateException("This credential is no longer valid");
/*     */     }
/*     */ 
/*     */     
/* 638 */     GSSCredentialSpi gSSCredentialSpi = null;
/* 639 */     StringBuffer stringBuffer = new StringBuffer("[GSSCredential: ");
/* 640 */     Object[] arrayOfObject = this.hashtable.entrySet().toArray();
/* 641 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/*     */       try {
/* 643 */         stringBuffer.append('\n');
/*     */         
/* 645 */         gSSCredentialSpi = ((Map.Entry<?, GSSCredentialSpi>)arrayOfObject[b]).getValue();
/* 646 */         stringBuffer.append(gSSCredentialSpi.getName());
/* 647 */         stringBuffer.append(' ');
/* 648 */         stringBuffer.append(gSSCredentialSpi.getMechanism());
/* 649 */         stringBuffer.append(gSSCredentialSpi.isInitiatorCredential() ? " Initiate" : "");
/*     */         
/* 651 */         stringBuffer.append(gSSCredentialSpi.isAcceptorCredential() ? " Accept" : "");
/*     */         
/* 653 */         stringBuffer.append(" [");
/* 654 */         stringBuffer.append(gSSCredentialSpi.getClass());
/* 655 */         stringBuffer.append(']');
/* 656 */       } catch (GSSException gSSException) {}
/*     */     } 
/*     */ 
/*     */     
/* 660 */     stringBuffer.append(']');
/* 661 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   static class SearchKey {
/* 665 */     private Oid mechOid = null;
/* 666 */     private int usage = 0;
/*     */     
/*     */     public SearchKey(Oid param1Oid, int param1Int) {
/* 669 */       this.mechOid = param1Oid;
/* 670 */       this.usage = param1Int;
/*     */     }
/*     */     public Oid getMech() {
/* 673 */       return this.mechOid;
/*     */     }
/*     */     public int getUsage() {
/* 676 */       return this.usage;
/*     */     }
/*     */     public boolean equals(Object param1Object) {
/* 679 */       if (!(param1Object instanceof SearchKey))
/* 680 */         return false; 
/* 681 */       SearchKey searchKey = (SearchKey)param1Object;
/* 682 */       return (this.mechOid.equals(searchKey.mechOid) && this.usage == searchKey.usage);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 686 */       return this.mechOid.hashCode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/GSSCredentialImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */