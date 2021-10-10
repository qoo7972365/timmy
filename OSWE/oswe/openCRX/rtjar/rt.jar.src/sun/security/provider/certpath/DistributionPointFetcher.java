/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.CRL;
/*     */ import java.security.cert.CRLException;
/*     */ import java.security.cert.CertPathBuilder;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.CertStore;
/*     */ import java.security.cert.CertStoreException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.PKIXBuilderParameters;
/*     */ import java.security.cert.PKIXCertPathBuilderResult;
/*     */ import java.security.cert.TrustAnchor;
/*     */ import java.security.cert.X509CRL;
/*     */ import java.security.cert.X509CRLSelector;
/*     */ import java.security.cert.X509CertSelector;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.x509.AuthorityKeyIdentifierExtension;
/*     */ import sun.security.x509.CRLDistributionPointsExtension;
/*     */ import sun.security.x509.DistributionPoint;
/*     */ import sun.security.x509.DistributionPointName;
/*     */ import sun.security.x509.GeneralName;
/*     */ import sun.security.x509.GeneralNameInterface;
/*     */ import sun.security.x509.GeneralNames;
/*     */ import sun.security.x509.IssuingDistributionPointExtension;
/*     */ import sun.security.x509.KeyIdentifier;
/*     */ import sun.security.x509.PKIXExtensions;
/*     */ import sun.security.x509.RDN;
/*     */ import sun.security.x509.ReasonFlags;
/*     */ import sun.security.x509.SerialNumber;
/*     */ import sun.security.x509.URIName;
/*     */ import sun.security.x509.X500Name;
/*     */ import sun.security.x509.X509CRLImpl;
/*     */ import sun.security.x509.X509CertImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DistributionPointFetcher
/*     */ {
/*  55 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */   
/*  57 */   private static final boolean[] ALL_REASONS = new boolean[] { true, true, true, true, true, true, true, true, true };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Collection<X509CRL> getCRLs(X509CRLSelector paramX509CRLSelector, boolean paramBoolean, PublicKey paramPublicKey, String paramString1, List<CertStore> paramList, boolean[] paramArrayOfboolean, Set<TrustAnchor> paramSet, Date paramDate, String paramString2) throws CertStoreException {
/*  75 */     return getCRLs(paramX509CRLSelector, paramBoolean, paramPublicKey, null, paramString1, paramList, paramArrayOfboolean, paramSet, paramDate, paramString2);
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
/*     */   public static Collection<X509CRL> getCRLs(X509CRLSelector paramX509CRLSelector, boolean paramBoolean, PublicKey paramPublicKey, String paramString, List<CertStore> paramList, boolean[] paramArrayOfboolean, Set<TrustAnchor> paramSet, Date paramDate) throws CertStoreException {
/*  93 */     return getCRLs(paramX509CRLSelector, paramBoolean, paramPublicKey, null, paramString, paramList, paramArrayOfboolean, paramSet, paramDate, "generic");
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
/*     */   public static Collection<X509CRL> getCRLs(X509CRLSelector paramX509CRLSelector, boolean paramBoolean, PublicKey paramPublicKey, X509Certificate paramX509Certificate, String paramString1, List<CertStore> paramList, boolean[] paramArrayOfboolean, Set<TrustAnchor> paramSet, Date paramDate, String paramString2) throws CertStoreException {
/* 113 */     X509Certificate x509Certificate = paramX509CRLSelector.getCertificateChecking();
/* 114 */     if (x509Certificate == null) {
/* 115 */       return Collections.emptySet();
/*     */     }
/*     */     try {
/* 118 */       X509CertImpl x509CertImpl = X509CertImpl.toImpl(x509Certificate);
/* 119 */       if (debug != null) {
/* 120 */         debug.println("DistributionPointFetcher.getCRLs: Checking CRLDPs for " + x509CertImpl
/* 121 */             .getSubjectX500Principal());
/*     */       }
/*     */       
/* 124 */       CRLDistributionPointsExtension cRLDistributionPointsExtension = x509CertImpl.getCRLDistributionPointsExtension();
/* 125 */       if (cRLDistributionPointsExtension == null) {
/* 126 */         if (debug != null) {
/* 127 */           debug.println("No CRLDP ext");
/*     */         }
/* 129 */         return Collections.emptySet();
/*     */       } 
/*     */       
/* 132 */       List<DistributionPoint> list = cRLDistributionPointsExtension.get("points");
/* 133 */       HashSet<X509CRL> hashSet = new HashSet();
/* 134 */       Iterator<DistributionPoint> iterator = list.iterator();
/* 135 */       while (iterator.hasNext() && !Arrays.equals(paramArrayOfboolean, ALL_REASONS)) {
/* 136 */         DistributionPoint distributionPoint = iterator.next();
/* 137 */         Collection<X509CRL> collection = getCRLs(paramX509CRLSelector, x509CertImpl, distributionPoint, paramArrayOfboolean, paramBoolean, paramPublicKey, paramX509Certificate, paramString1, paramList, paramSet, paramDate, paramString2);
/*     */ 
/*     */         
/* 140 */         hashSet.addAll(collection);
/*     */       } 
/* 142 */       if (debug != null) {
/* 143 */         debug.println("Returning " + hashSet.size() + " CRLs");
/*     */       }
/* 145 */       return hashSet;
/* 146 */     } catch (CertificateException|IOException certificateException) {
/* 147 */       return Collections.emptySet();
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
/*     */   private static Collection<X509CRL> getCRLs(X509CRLSelector paramX509CRLSelector, X509CertImpl paramX509CertImpl, DistributionPoint paramDistributionPoint, boolean[] paramArrayOfboolean, boolean paramBoolean, PublicKey paramPublicKey, X509Certificate paramX509Certificate, String paramString1, List<CertStore> paramList, Set<TrustAnchor> paramSet, Date paramDate, String paramString2) throws CertStoreException {
/* 168 */     GeneralNames generalNames = paramDistributionPoint.getFullName();
/* 169 */     if (generalNames == null) {
/*     */       
/* 171 */       RDN rDN = paramDistributionPoint.getRelativeName();
/* 172 */       if (rDN == null) {
/* 173 */         return Collections.emptySet();
/*     */       }
/*     */       try {
/* 176 */         GeneralNames generalNames1 = paramDistributionPoint.getCRLIssuer();
/* 177 */         if (generalNames1 == null) {
/*     */           
/* 179 */           generalNames = getFullNames((X500Name)paramX509CertImpl.getIssuerDN(), rDN);
/*     */         } else {
/*     */           
/* 182 */           if (generalNames1.size() != 1) {
/* 183 */             return Collections.emptySet();
/*     */           }
/*     */           
/* 186 */           generalNames = getFullNames((X500Name)generalNames1.get(0).getName(), rDN);
/*     */         }
/*     */       
/* 189 */       } catch (IOException iOException) {
/* 190 */         return Collections.emptySet();
/*     */       } 
/*     */     } 
/* 193 */     ArrayList<X509CRL> arrayList1 = new ArrayList();
/* 194 */     CertStoreException certStoreException = null;
/* 195 */     for (Iterator<GeneralName> iterator = generalNames.iterator(); iterator.hasNext();) {
/*     */       try {
/* 197 */         GeneralName generalName = iterator.next();
/* 198 */         if (generalName.getType() == 4) {
/* 199 */           X500Name x500Name = (X500Name)generalName.getName();
/* 200 */           arrayList1.addAll(
/* 201 */               getCRLs(x500Name, paramX509CertImpl.getIssuerX500Principal(), paramList)); continue;
/*     */         } 
/* 203 */         if (generalName.getType() == 6) {
/* 204 */           URIName uRIName = (URIName)generalName.getName();
/* 205 */           X509CRL x509CRL = getCRL(uRIName);
/* 206 */           if (x509CRL != null) {
/* 207 */             arrayList1.add(x509CRL);
/*     */           }
/*     */         } 
/* 210 */       } catch (CertStoreException certStoreException1) {
/* 211 */         certStoreException = certStoreException1;
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     if (arrayList1.isEmpty() && certStoreException != null) {
/* 216 */       throw certStoreException;
/*     */     }
/*     */     
/* 219 */     ArrayList<X509CRL> arrayList2 = new ArrayList(2);
/* 220 */     for (X509CRL x509CRL : arrayList1) {
/*     */ 
/*     */       
/*     */       try {
/* 224 */         paramX509CRLSelector.setIssuerNames(null);
/* 225 */         if (paramX509CRLSelector.match(x509CRL) && verifyCRL(paramX509CertImpl, paramDistributionPoint, x509CRL, paramArrayOfboolean, paramBoolean, paramPublicKey, paramX509Certificate, paramString1, paramSet, paramList, paramDate, paramString2))
/*     */         {
/*     */           
/* 228 */           arrayList2.add(x509CRL);
/*     */         }
/* 230 */       } catch (IOException|CRLException iOException) {
/*     */         
/* 232 */         if (debug != null) {
/* 233 */           debug.println("Exception verifying CRL: " + iOException.getMessage());
/* 234 */           iOException.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/* 238 */     return arrayList2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static X509CRL getCRL(URIName paramURIName) throws CertStoreException {
/* 245 */     URI uRI = paramURIName.getURI();
/* 246 */     if (debug != null) {
/* 247 */       debug.println("Trying to fetch CRL from DP " + uRI);
/*     */     }
/* 249 */     CertStore certStore = null;
/*     */     
/*     */     try {
/* 252 */       certStore = URICertStore.getInstance(new URICertStore.URICertStoreParameters(uRI));
/* 253 */     } catch (InvalidAlgorithmParameterException|java.security.NoSuchAlgorithmException invalidAlgorithmParameterException) {
/*     */       
/* 255 */       if (debug != null) {
/* 256 */         debug.println("Can't create URICertStore: " + invalidAlgorithmParameterException.getMessage());
/*     */       }
/* 258 */       return null;
/*     */     } 
/*     */     
/* 261 */     Collection<? extends CRL> collection = certStore.getCRLs(null);
/* 262 */     if (collection.isEmpty()) {
/* 263 */       return null;
/*     */     }
/* 265 */     return collection.iterator().next();
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
/*     */   private static Collection<X509CRL> getCRLs(X500Name paramX500Name, X500Principal paramX500Principal, List<CertStore> paramList) throws CertStoreException {
/* 282 */     if (debug != null) {
/* 283 */       debug.println("Trying to fetch CRL from DP " + paramX500Name);
/*     */     }
/* 285 */     X509CRLSelector x509CRLSelector = new X509CRLSelector();
/* 286 */     x509CRLSelector.addIssuer(paramX500Name.asX500Principal());
/* 287 */     x509CRLSelector.addIssuer(paramX500Principal);
/* 288 */     ArrayList<X509CRL> arrayList = new ArrayList();
/* 289 */     PKIX.CertStoreTypeException certStoreTypeException = null;
/* 290 */     for (CertStore certStore : paramList) {
/*     */       try {
/* 292 */         for (CRL cRL : certStore.getCRLs(x509CRLSelector)) {
/* 293 */           arrayList.add((X509CRL)cRL);
/*     */         }
/* 295 */       } catch (CertStoreException certStoreException) {
/* 296 */         if (debug != null) {
/* 297 */           debug.println("Exception while retrieving CRLs: " + certStoreException);
/*     */           
/* 299 */           certStoreException.printStackTrace();
/*     */         } 
/* 301 */         certStoreTypeException = new PKIX.CertStoreTypeException(certStore.getType(), certStoreException);
/*     */       } 
/*     */     } 
/*     */     
/* 305 */     if (arrayList.isEmpty() && certStoreTypeException != null) {
/* 306 */       throw certStoreTypeException;
/*     */     }
/* 308 */     return arrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean verifyCRL(X509CertImpl paramX509CertImpl, DistributionPoint paramDistributionPoint, X509CRL paramX509CRL, boolean[] paramArrayOfboolean, boolean paramBoolean, PublicKey paramPublicKey, X509Certificate paramX509Certificate, String paramString1, Set<TrustAnchor> paramSet, List<CertStore> paramList, Date paramDate, String paramString2) throws CRLException, IOException {
/* 338 */     if (debug != null) {
/* 339 */       debug.println("DistributionPointFetcher.verifyCRL: checking revocation status for\n  SN: " + 
/*     */           
/* 341 */           Debug.toHexString(paramX509CertImpl.getSerialNumber()) + "\n  Subject: " + paramX509CertImpl
/* 342 */           .getSubjectX500Principal() + "\n  Issuer: " + paramX509CertImpl
/* 343 */           .getIssuerX500Principal());
/*     */     }
/*     */     
/* 346 */     boolean bool1 = false;
/* 347 */     X509CRLImpl x509CRLImpl = X509CRLImpl.toImpl(paramX509CRL);
/*     */     
/* 349 */     IssuingDistributionPointExtension issuingDistributionPointExtension = x509CRLImpl.getIssuingDistributionPointExtension();
/* 350 */     X500Name x500Name1 = (X500Name)paramX509CertImpl.getIssuerDN();
/* 351 */     X500Name x500Name2 = (X500Name)x509CRLImpl.getIssuerDN();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 357 */     GeneralNames generalNames = paramDistributionPoint.getCRLIssuer();
/* 358 */     X500Name x500Name3 = null;
/* 359 */     if (generalNames != null)
/* 360 */     { if (issuingDistributionPointExtension == null || ((Boolean)issuingDistributionPointExtension
/*     */         
/* 362 */         .get("indirect_crl"))
/* 363 */         .equals(Boolean.FALSE)) {
/* 364 */         return false;
/*     */       }
/* 366 */       boolean bool = false;
/* 367 */       Iterator<GeneralName> iterator = generalNames.iterator();
/* 368 */       while (!bool && iterator.hasNext()) {
/* 369 */         GeneralNameInterface generalNameInterface = ((GeneralName)iterator.next()).getName();
/* 370 */         if (x500Name2.equals(generalNameInterface) == true) {
/* 371 */           x500Name3 = (X500Name)generalNameInterface;
/* 372 */           bool = true;
/*     */         } 
/*     */       } 
/* 375 */       if (!bool) {
/* 376 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 381 */       if (issues(paramX509CertImpl, x509CRLImpl, paramString1)) {
/*     */         
/* 383 */         paramPublicKey = paramX509CertImpl.getPublicKey();
/*     */       } else {
/* 385 */         bool1 = true;
/*     */       }  }
/* 387 */     else { if (!x500Name2.equals(x500Name1)) {
/* 388 */         if (debug != null) {
/* 389 */           debug.println("crl issuer does not equal cert issuer.\ncrl issuer: " + x500Name2 + "\ncert issuer: " + x500Name1);
/*     */         }
/*     */ 
/*     */         
/* 393 */         return false;
/*     */       } 
/*     */       
/* 396 */       KeyIdentifier keyIdentifier1 = paramX509CertImpl.getAuthKeyId();
/* 397 */       KeyIdentifier keyIdentifier2 = x509CRLImpl.getAuthKeyId();
/*     */       
/* 399 */       if (keyIdentifier1 == null || keyIdentifier2 == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 404 */         if (issues(paramX509CertImpl, x509CRLImpl, paramString1))
/*     */         {
/* 406 */           paramPublicKey = paramX509CertImpl.getPublicKey();
/*     */         }
/* 408 */       } else if (!keyIdentifier1.equals(keyIdentifier2)) {
/*     */ 
/*     */         
/* 411 */         if (issues(paramX509CertImpl, x509CRLImpl, paramString1)) {
/*     */           
/* 413 */           paramPublicKey = paramX509CertImpl.getPublicKey();
/*     */         } else {
/* 415 */           bool1 = true;
/*     */         } 
/*     */       }  }
/*     */ 
/*     */     
/* 420 */     if (!bool1 && !paramBoolean)
/*     */     {
/* 422 */       return false;
/*     */     }
/*     */     
/* 425 */     if (issuingDistributionPointExtension != null) {
/*     */       
/* 427 */       DistributionPointName distributionPointName = (DistributionPointName)issuingDistributionPointExtension.get("point");
/* 428 */       if (distributionPointName != null) {
/* 429 */         GeneralNames generalNames1 = distributionPointName.getFullName();
/* 430 */         if (generalNames1 == null) {
/* 431 */           RDN rDN = distributionPointName.getRelativeName();
/* 432 */           if (rDN == null) {
/* 433 */             if (debug != null) {
/* 434 */               debug.println("IDP must be relative or full DN");
/*     */             }
/* 436 */             return false;
/*     */           } 
/* 438 */           if (debug != null) {
/* 439 */             debug.println("IDP relativeName:" + rDN);
/*     */           }
/* 441 */           generalNames1 = getFullNames(x500Name2, rDN);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 446 */         if (paramDistributionPoint.getFullName() != null || paramDistributionPoint
/* 447 */           .getRelativeName() != null) {
/* 448 */           GeneralNames generalNames2 = paramDistributionPoint.getFullName();
/* 449 */           if (generalNames2 == null) {
/* 450 */             RDN rDN = paramDistributionPoint.getRelativeName();
/* 451 */             if (rDN == null) {
/* 452 */               if (debug != null) {
/* 453 */                 debug.println("DP must be relative or full DN");
/*     */               }
/* 455 */               return false;
/*     */             } 
/* 457 */             if (debug != null) {
/* 458 */               debug.println("DP relativeName:" + rDN);
/*     */             }
/* 460 */             if (bool1) {
/* 461 */               if (generalNames.size() != 1) {
/*     */ 
/*     */                 
/* 464 */                 if (debug != null) {
/* 465 */                   debug.println("must only be one CRL issuer when relative name present");
/*     */                 }
/*     */                 
/* 468 */                 return false;
/*     */               } 
/*     */               
/* 471 */               generalNames2 = getFullNames(x500Name3, rDN);
/*     */             } else {
/* 473 */               generalNames2 = getFullNames(x500Name1, rDN);
/*     */             } 
/*     */           } 
/* 476 */           boolean bool3 = false;
/* 477 */           Iterator<GeneralName> iterator = generalNames1.iterator();
/* 478 */           while (!bool3 && iterator.hasNext()) {
/* 479 */             GeneralNameInterface generalNameInterface = ((GeneralName)iterator.next()).getName();
/* 480 */             if (debug != null) {
/* 481 */               debug.println("idpName: " + generalNameInterface);
/*     */             }
/* 483 */             Iterator<GeneralName> iterator1 = generalNames2.iterator();
/* 484 */             while (!bool3 && iterator1.hasNext()) {
/* 485 */               GeneralNameInterface generalNameInterface1 = ((GeneralName)iterator1.next()).getName();
/* 486 */               if (debug != null) {
/* 487 */                 debug.println("pointName: " + generalNameInterface1);
/*     */               }
/* 489 */               bool3 = generalNameInterface.equals(generalNameInterface1);
/*     */             } 
/*     */           } 
/* 492 */           if (!bool3) {
/* 493 */             if (debug != null) {
/* 494 */               debug.println("IDP name does not match DP name");
/*     */             }
/* 496 */             return false;
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 505 */           boolean bool3 = false;
/* 506 */           Iterator<GeneralName> iterator = generalNames.iterator();
/* 507 */           while (!bool3 && iterator.hasNext()) {
/* 508 */             GeneralNameInterface generalNameInterface = ((GeneralName)iterator.next()).getName();
/* 509 */             Iterator<GeneralName> iterator1 = generalNames1.iterator();
/* 510 */             while (!bool3 && iterator1.hasNext()) {
/* 511 */               GeneralNameInterface generalNameInterface1 = ((GeneralName)iterator1.next()).getName();
/* 512 */               bool3 = generalNameInterface.equals(generalNameInterface1);
/*     */             } 
/*     */           } 
/* 515 */           if (!bool3) {
/* 516 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 524 */       Boolean bool = (Boolean)issuingDistributionPointExtension.get("only_user_certs");
/* 525 */       if (bool.equals(Boolean.TRUE) && paramX509CertImpl.getBasicConstraints() != -1) {
/* 526 */         if (debug != null) {
/* 527 */           debug.println("cert must be a EE cert");
/*     */         }
/* 529 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 535 */       bool = (Boolean)issuingDistributionPointExtension.get("only_ca_certs");
/* 536 */       if (bool.equals(Boolean.TRUE) && paramX509CertImpl.getBasicConstraints() == -1) {
/* 537 */         if (debug != null) {
/* 538 */           debug.println("cert must be a CA cert");
/*     */         }
/* 540 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 546 */       bool = (Boolean)issuingDistributionPointExtension.get("only_attribute_certs");
/* 547 */       if (bool.equals(Boolean.TRUE)) {
/* 548 */         if (debug != null) {
/* 549 */           debug.println("cert must not be an AA cert");
/*     */         }
/* 551 */         return false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 556 */     boolean[] arrayOfBoolean1 = new boolean[9];
/* 557 */     ReasonFlags reasonFlags = null;
/* 558 */     if (issuingDistributionPointExtension != null)
/*     */     {
/* 560 */       reasonFlags = (ReasonFlags)issuingDistributionPointExtension.get("reasons");
/*     */     }
/*     */     
/* 563 */     boolean[] arrayOfBoolean2 = paramDistributionPoint.getReasonFlags();
/* 564 */     if (reasonFlags != null) {
/* 565 */       if (arrayOfBoolean2 != null) {
/*     */ 
/*     */         
/* 568 */         boolean[] arrayOfBoolean = reasonFlags.getFlags();
/* 569 */         for (byte b = 0; b < arrayOfBoolean1.length; b++) {
/* 570 */           arrayOfBoolean1[b] = (b < arrayOfBoolean.length && arrayOfBoolean[b] && b < arrayOfBoolean2.length && arrayOfBoolean2[b]);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 578 */         arrayOfBoolean1 = (boolean[])reasonFlags.getFlags().clone();
/*     */       } 
/* 580 */     } else if (issuingDistributionPointExtension == null || reasonFlags == null) {
/* 581 */       if (arrayOfBoolean2 != null) {
/*     */         
/* 583 */         arrayOfBoolean1 = (boolean[])arrayOfBoolean2.clone();
/*     */       } else {
/*     */         
/* 586 */         Arrays.fill(arrayOfBoolean1, true);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 592 */     boolean bool2 = false;
/* 593 */     for (byte b1 = 0; b1 < arrayOfBoolean1.length && !bool2; b1++) {
/* 594 */       if (arrayOfBoolean1[b1] && (b1 >= paramArrayOfboolean.length || !paramArrayOfboolean[b1]))
/*     */       {
/*     */         
/* 597 */         bool2 = true;
/*     */       }
/*     */     } 
/* 600 */     if (!bool2) {
/* 601 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 607 */     if (bool1) {
/* 608 */       X509CertSelector x509CertSelector = new X509CertSelector();
/* 609 */       x509CertSelector.setSubject(x500Name2.asX500Principal());
/* 610 */       boolean[] arrayOfBoolean = { false, false, false, false, false, false, true };
/* 611 */       x509CertSelector.setKeyUsage(arrayOfBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 624 */       AuthorityKeyIdentifierExtension authorityKeyIdentifierExtension = x509CRLImpl.getAuthKeyIdExtension();
/* 625 */       if (authorityKeyIdentifierExtension != null) {
/* 626 */         byte[] arrayOfByte = authorityKeyIdentifierExtension.getEncodedKeyIdentifier();
/* 627 */         if (arrayOfByte != null) {
/* 628 */           x509CertSelector.setSubjectKeyIdentifier(arrayOfByte);
/*     */         }
/*     */         
/* 631 */         SerialNumber serialNumber = (SerialNumber)authorityKeyIdentifierExtension.get("serial_number");
/*     */         
/* 633 */         if (serialNumber != null) {
/* 634 */           x509CertSelector.setSerialNumber(serialNumber.getNumber());
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 644 */       HashSet<TrustAnchor> hashSet = new HashSet<>(paramSet);
/*     */       
/* 646 */       if (paramPublicKey != null) {
/*     */         TrustAnchor trustAnchor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 653 */         if (paramX509Certificate != null) {
/* 654 */           trustAnchor = new TrustAnchor(paramX509Certificate, null);
/*     */         } else {
/* 656 */           X500Principal x500Principal = paramX509CertImpl.getIssuerX500Principal();
/* 657 */           trustAnchor = new TrustAnchor(x500Principal, paramPublicKey, null);
/*     */         } 
/* 659 */         hashSet.add(trustAnchor);
/*     */       } 
/*     */       
/* 662 */       PKIXBuilderParameters pKIXBuilderParameters = null;
/*     */       try {
/* 664 */         pKIXBuilderParameters = new PKIXBuilderParameters(hashSet, x509CertSelector);
/* 665 */       } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/* 666 */         throw new CRLException(invalidAlgorithmParameterException);
/*     */       } 
/* 668 */       pKIXBuilderParameters.setCertStores(paramList);
/* 669 */       pKIXBuilderParameters.setSigProvider(paramString1);
/* 670 */       pKIXBuilderParameters.setDate(paramDate);
/*     */       try {
/* 672 */         CertPathBuilder certPathBuilder = CertPathBuilder.getInstance("PKIX");
/*     */         
/* 674 */         PKIXCertPathBuilderResult pKIXCertPathBuilderResult = (PKIXCertPathBuilderResult)certPathBuilder.build(pKIXBuilderParameters);
/* 675 */         paramPublicKey = pKIXCertPathBuilderResult.getPublicKey();
/* 676 */       } catch (GeneralSecurityException generalSecurityException) {
/* 677 */         throw new CRLException(generalSecurityException);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 683 */       AlgorithmChecker.check(paramPublicKey, paramX509CRL, paramString2);
/* 684 */     } catch (CertPathValidatorException certPathValidatorException) {
/* 685 */       if (debug != null) {
/* 686 */         debug.println("CRL signature algorithm check failed: " + certPathValidatorException);
/*     */       }
/* 688 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 693 */       paramX509CRL.verify(paramPublicKey, paramString1);
/* 694 */     } catch (GeneralSecurityException generalSecurityException) {
/* 695 */       if (debug != null) {
/* 696 */         debug.println("CRL signature failed to verify");
/*     */       }
/* 698 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 702 */     Set<String> set = paramX509CRL.getCriticalExtensionOIDs();
/*     */     
/* 704 */     if (set != null) {
/* 705 */       set.remove(PKIXExtensions.IssuingDistributionPoint_Id.toString());
/* 706 */       if (!set.isEmpty()) {
/* 707 */         if (debug != null) {
/* 708 */           debug.println("Unrecognized critical extension(s) in CRL: " + set);
/*     */           
/* 710 */           for (String str : set) {
/* 711 */             debug.println(str);
/*     */           }
/*     */         } 
/* 714 */         return false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 719 */     for (byte b2 = 0; b2 < paramArrayOfboolean.length; b2++) {
/* 720 */       paramArrayOfboolean[b2] = (paramArrayOfboolean[b2] || (b2 < arrayOfBoolean1.length && arrayOfBoolean1[b2]));
/*     */     }
/*     */ 
/*     */     
/* 724 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static GeneralNames getFullNames(X500Name paramX500Name, RDN paramRDN) throws IOException {
/* 734 */     ArrayList<RDN> arrayList = new ArrayList<>(paramX500Name.rdns());
/* 735 */     arrayList.add(paramRDN);
/* 736 */     X500Name x500Name = new X500Name(arrayList.<RDN>toArray(new RDN[0]));
/* 737 */     GeneralNames generalNames = new GeneralNames();
/* 738 */     generalNames.add(new GeneralName(x500Name));
/* 739 */     return generalNames;
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
/*     */   private static boolean issues(X509CertImpl paramX509CertImpl, X509CRLImpl paramX509CRLImpl, String paramString) throws IOException {
/* 752 */     boolean bool = false;
/*     */     
/* 754 */     AdaptableX509CertSelector adaptableX509CertSelector = new AdaptableX509CertSelector();
/*     */ 
/*     */ 
/*     */     
/* 758 */     boolean[] arrayOfBoolean = paramX509CertImpl.getKeyUsage();
/* 759 */     if (arrayOfBoolean != null) {
/* 760 */       arrayOfBoolean[6] = true;
/* 761 */       adaptableX509CertSelector.setKeyUsage(arrayOfBoolean);
/*     */     } 
/*     */ 
/*     */     
/* 765 */     X500Principal x500Principal = paramX509CRLImpl.getIssuerX500Principal();
/* 766 */     adaptableX509CertSelector.setSubject(x500Principal);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 776 */     AuthorityKeyIdentifierExtension authorityKeyIdentifierExtension = paramX509CRLImpl.getAuthKeyIdExtension();
/* 777 */     adaptableX509CertSelector.setSkiAndSerialNumber(authorityKeyIdentifierExtension);
/*     */     
/* 779 */     bool = adaptableX509CertSelector.match(paramX509CertImpl);
/*     */ 
/*     */     
/* 782 */     if (bool && (authorityKeyIdentifierExtension == null || paramX509CertImpl
/* 783 */       .getAuthorityKeyIdentifierExtension() == null)) {
/*     */       try {
/* 785 */         paramX509CRLImpl.verify(paramX509CertImpl.getPublicKey(), paramString);
/* 786 */         bool = true;
/* 787 */       } catch (GeneralSecurityException generalSecurityException) {
/* 788 */         bool = false;
/*     */       } 
/*     */     }
/*     */     
/* 792 */     return bool;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/DistributionPointFetcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */