/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.CertStore;
/*     */ import java.security.cert.CertStoreException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.PKIXCertPathChecker;
/*     */ import java.security.cert.PKIXReason;
/*     */ import java.security.cert.TrustAnchor;
/*     */ import java.security.cert.X509CertSelector;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.x509.AccessDescription;
/*     */ import sun.security.x509.AuthorityInfoAccessExtension;
/*     */ import sun.security.x509.AuthorityKeyIdentifierExtension;
/*     */ import sun.security.x509.PKIXExtensions;
/*     */ import sun.security.x509.X500Name;
/*     */ import sun.security.x509.X509CertImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ForwardBuilder
/*     */   extends Builder
/*     */ {
/*  65 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */   
/*     */   private final Set<X509Certificate> trustedCerts;
/*     */   
/*     */   private final Set<X500Principal> trustedSubjectDNs;
/*     */   
/*     */   private final Set<TrustAnchor> trustAnchors;
/*     */   
/*     */   private X509CertSelector eeSelector;
/*     */   
/*     */   private AdaptableX509CertSelector caSelector;
/*     */   private X509CertSelector caTargetSelector;
/*     */   TrustAnchor trustAnchor;
/*     */   private boolean searchAllCertStores = true;
/*     */   
/*     */   ForwardBuilder(PKIX.BuilderParams paramBuilderParams, boolean paramBoolean) {
/*  81 */     super(paramBuilderParams);
/*     */ 
/*     */     
/*  84 */     this.trustAnchors = paramBuilderParams.trustAnchors();
/*  85 */     this.trustedCerts = new HashSet<>(this.trustAnchors.size());
/*  86 */     this.trustedSubjectDNs = new HashSet<>(this.trustAnchors.size());
/*  87 */     for (TrustAnchor trustAnchor : this.trustAnchors) {
/*  88 */       X509Certificate x509Certificate = trustAnchor.getTrustedCert();
/*  89 */       if (x509Certificate != null) {
/*  90 */         this.trustedCerts.add(x509Certificate);
/*  91 */         this.trustedSubjectDNs.add(x509Certificate.getSubjectX500Principal()); continue;
/*     */       } 
/*  93 */       this.trustedSubjectDNs.add(trustAnchor.getCA());
/*     */     } 
/*     */     
/*  96 */     this.searchAllCertStores = paramBoolean;
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
/*     */   Collection<X509Certificate> getMatchingCerts(State paramState, List<CertStore> paramList) throws CertStoreException, CertificateException, IOException {
/* 113 */     if (debug != null) {
/* 114 */       debug.println("ForwardBuilder.getMatchingCerts()...");
/*     */     }
/*     */     
/* 117 */     ForwardState forwardState = (ForwardState)paramState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     PKIXCertComparator pKIXCertComparator = new PKIXCertComparator(this.trustedSubjectDNs, forwardState.cert);
/*     */     
/* 126 */     TreeSet<X509Certificate> treeSet = new TreeSet(pKIXCertComparator);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     if (forwardState.isInitial()) {
/* 132 */       getMatchingEECerts(forwardState, paramList, treeSet);
/*     */     }
/* 134 */     getMatchingCACerts(forwardState, paramList, treeSet);
/*     */     
/* 136 */     return treeSet;
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
/*     */   private void getMatchingEECerts(ForwardState paramForwardState, List<CertStore> paramList, Collection<X509Certificate> paramCollection) throws IOException {
/* 148 */     if (debug != null) {
/* 149 */       debug.println("ForwardBuilder.getMatchingEECerts()...");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     if (this.eeSelector == null) {
/* 160 */       this.eeSelector = (X509CertSelector)this.targetCertConstraints.clone();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 165 */       this.eeSelector.setCertificateValid(this.buildParams.date());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 170 */       if (this.buildParams.explicitPolicyRequired()) {
/* 171 */         this.eeSelector.setPolicy(getMatchingPolicies());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 176 */       this.eeSelector.setBasicConstraints(-2);
/*     */     } 
/*     */ 
/*     */     
/* 180 */     addMatchingCerts(this.eeSelector, paramList, paramCollection, this.searchAllCertStores);
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
/*     */   private void getMatchingCACerts(ForwardState paramForwardState, List<CertStore> paramList, Collection<X509Certificate> paramCollection) throws IOException {
/* 192 */     if (debug != null) {
/* 193 */       debug.println("ForwardBuilder.getMatchingCACerts()...");
/*     */     }
/* 195 */     int i = paramCollection.size();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     X509CertSelector x509CertSelector = null;
/*     */     
/* 203 */     if (paramForwardState.isInitial()) {
/* 204 */       if (this.targetCertConstraints.getBasicConstraints() == -2) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 212 */       if (debug != null) {
/* 213 */         debug.println("ForwardBuilder.getMatchingCACerts(): the target is a CA");
/*     */       }
/*     */ 
/*     */       
/* 217 */       if (this.caTargetSelector == null) {
/* 218 */         this
/* 219 */           .caTargetSelector = (X509CertSelector)this.targetCertConstraints.clone();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 231 */         if (this.buildParams.explicitPolicyRequired()) {
/* 232 */           this.caTargetSelector.setPolicy(getMatchingPolicies());
/*     */         }
/*     */       } 
/* 235 */       x509CertSelector = this.caTargetSelector;
/*     */     } else {
/*     */       
/* 238 */       if (this.caSelector == null) {
/* 239 */         this.caSelector = new AdaptableX509CertSelector();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 251 */         if (this.buildParams.explicitPolicyRequired()) {
/* 252 */           this.caSelector.setPolicy(getMatchingPolicies());
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 258 */       this.caSelector.setSubject(paramForwardState.issuerDN);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 266 */       CertPathHelper.setPathToNames(this.caSelector, paramForwardState.subjectNamesTraversed);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 271 */       this.caSelector.setValidityPeriod(paramForwardState.cert.getNotBefore(), paramForwardState.cert
/* 272 */           .getNotAfter());
/*     */       
/* 274 */       x509CertSelector = this.caSelector;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 283 */     x509CertSelector.setBasicConstraints(-1);
/*     */     
/* 285 */     for (X509Certificate x509Certificate : this.trustedCerts) {
/* 286 */       if (x509CertSelector.match(x509Certificate)) {
/* 287 */         if (debug != null) {
/* 288 */           debug.println("ForwardBuilder.getMatchingCACerts: found matching trust anchor.\n  SN: " + 
/*     */ 
/*     */               
/* 291 */               Debug.toHexString(x509Certificate.getSerialNumber()) + "\n  Subject: " + x509Certificate
/*     */               
/* 293 */               .getSubjectX500Principal() + "\n  Issuer: " + x509Certificate
/*     */               
/* 295 */               .getIssuerX500Principal());
/*     */         }
/* 297 */         if (paramCollection.add(x509Certificate) && !this.searchAllCertStores) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     x509CertSelector.setCertificateValid(this.buildParams.date());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     x509CertSelector.setBasicConstraints(paramForwardState.traversedCACerts);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     if (paramForwardState.isInitial() || this.buildParams
/* 323 */       .maxPathLength() == -1 || this.buildParams
/* 324 */       .maxPathLength() > paramForwardState.traversedCACerts)
/*     */     {
/* 326 */       if (addMatchingCerts(x509CertSelector, paramList, paramCollection, this.searchAllCertStores) && !this.searchAllCertStores) {
/*     */         return;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 333 */     if (!paramForwardState.isInitial() && Builder.USE_AIA) {
/*     */ 
/*     */       
/* 336 */       AuthorityInfoAccessExtension authorityInfoAccessExtension = paramForwardState.cert.getAuthorityInfoAccessExtension();
/* 337 */       if (authorityInfoAccessExtension != null) {
/* 338 */         getCerts(authorityInfoAccessExtension, paramCollection);
/*     */       }
/*     */     } 
/*     */     
/* 342 */     if (debug != null) {
/* 343 */       int j = paramCollection.size() - i;
/* 344 */       debug.println("ForwardBuilder.getMatchingCACerts: found " + j + " CA certs");
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
/*     */   private boolean getCerts(AuthorityInfoAccessExtension paramAuthorityInfoAccessExtension, Collection<X509Certificate> paramCollection) {
/* 359 */     if (!Builder.USE_AIA) {
/* 360 */       return false;
/*     */     }
/* 362 */     List<AccessDescription> list = paramAuthorityInfoAccessExtension.getAccessDescriptions();
/* 363 */     if (list == null || list.isEmpty()) {
/* 364 */       return false;
/*     */     }
/*     */     
/* 367 */     boolean bool = false;
/* 368 */     for (AccessDescription accessDescription : list) {
/* 369 */       CertStore certStore = URICertStore.getInstance(accessDescription);
/* 370 */       if (certStore != null) {
/*     */         try {
/* 372 */           if (paramCollection.addAll(certStore
/* 373 */               .getCertificates(this.caSelector))) {
/* 374 */             bool = true;
/* 375 */             if (!this.searchAllCertStores) {
/* 376 */               return true;
/*     */             }
/*     */           } 
/* 379 */         } catch (CertStoreException certStoreException) {
/* 380 */           if (debug != null) {
/* 381 */             debug.println("exception getting certs from CertStore:");
/* 382 */             certStoreException.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 387 */     return bool;
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
/*     */   static class PKIXCertComparator
/*     */     implements Comparator<X509Certificate>
/*     */   {
/*     */     static final String METHOD_NME = "PKIXCertComparator.compare()";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Set<X500Principal> trustedSubjectDNs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final X509CertSelector certSkidSelector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     PKIXCertComparator(Set<X500Principal> param1Set, X509CertImpl param1X509CertImpl) throws IOException {
/* 439 */       this.trustedSubjectDNs = param1Set;
/* 440 */       this.certSkidSelector = getSelector(param1X509CertImpl);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private X509CertSelector getSelector(X509CertImpl param1X509CertImpl) throws IOException {
/* 449 */       if (param1X509CertImpl != null) {
/*     */         
/* 451 */         AuthorityKeyIdentifierExtension authorityKeyIdentifierExtension = param1X509CertImpl.getAuthorityKeyIdentifierExtension();
/* 452 */         if (authorityKeyIdentifierExtension != null) {
/* 453 */           byte[] arrayOfByte = authorityKeyIdentifierExtension.getEncodedKeyIdentifier();
/* 454 */           if (arrayOfByte != null) {
/* 455 */             X509CertSelector x509CertSelector = new X509CertSelector();
/* 456 */             x509CertSelector.setSubjectKeyIdentifier(arrayOfByte);
/* 457 */             return x509CertSelector;
/*     */           } 
/*     */         } 
/*     */       } 
/* 461 */       return null;
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
/*     */ 
/*     */     
/*     */     public int compare(X509Certificate param1X509Certificate1, X509Certificate param1X509Certificate2) {
/* 483 */       if (param1X509Certificate1.equals(param1X509Certificate2)) return 0;
/*     */ 
/*     */       
/* 486 */       if (this.certSkidSelector != null) {
/* 487 */         if (this.certSkidSelector.match(param1X509Certificate1)) {
/* 488 */           return -1;
/*     */         }
/* 490 */         if (this.certSkidSelector.match(param1X509Certificate2)) {
/* 491 */           return 1;
/*     */         }
/*     */       } 
/*     */       
/* 495 */       X500Principal x500Principal1 = param1X509Certificate1.getIssuerX500Principal();
/* 496 */       X500Principal x500Principal2 = param1X509Certificate2.getIssuerX500Principal();
/* 497 */       X500Name x500Name1 = X500Name.asX500Name(x500Principal1);
/* 498 */       X500Name x500Name2 = X500Name.asX500Name(x500Principal2);
/*     */       
/* 500 */       if (ForwardBuilder.debug != null) {
/* 501 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() o1 Issuer:  " + x500Principal1);
/* 502 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() o2 Issuer:  " + x500Principal2);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 508 */       if (ForwardBuilder.debug != null) {
/* 509 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() MATCH TRUSTED SUBJECT TEST...");
/*     */       }
/*     */       
/* 512 */       boolean bool1 = this.trustedSubjectDNs.contains(x500Principal1);
/* 513 */       boolean bool2 = this.trustedSubjectDNs.contains(x500Principal2);
/* 514 */       if (ForwardBuilder.debug != null) {
/* 515 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() m1: " + bool1);
/* 516 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() m2: " + bool2);
/*     */       } 
/* 518 */       if (bool1 && bool2)
/* 519 */         return -1; 
/* 520 */       if (bool1)
/* 521 */         return -1; 
/* 522 */       if (bool2) {
/* 523 */         return 1;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 529 */       if (ForwardBuilder.debug != null) {
/* 530 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() NAMING DESCENDANT TEST...");
/*     */       }
/* 532 */       for (X500Principal x500Principal : this.trustedSubjectDNs) {
/* 533 */         X500Name x500Name = X500Name.asX500Name(x500Principal);
/*     */         
/* 535 */         int k = Builder.distance(x500Name, x500Name1, -1);
/*     */         
/* 537 */         int m = Builder.distance(x500Name, x500Name2, -1);
/* 538 */         if (ForwardBuilder.debug != null) {
/* 539 */           ForwardBuilder.debug.println("PKIXCertComparator.compare() distanceTto1: " + k);
/* 540 */           ForwardBuilder.debug.println("PKIXCertComparator.compare() distanceTto2: " + m);
/*     */         } 
/* 542 */         if (k > 0 || m > 0) {
/* 543 */           if (k == m)
/* 544 */             return -1; 
/* 545 */           if (k > 0 && m <= 0)
/* 546 */             return -1; 
/* 547 */           if (k <= 0 && m > 0)
/* 548 */             return 1; 
/* 549 */           if (k < m) {
/* 550 */             return -1;
/*     */           }
/* 552 */           return 1;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 560 */       if (ForwardBuilder.debug != null) {
/* 561 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() NAMING ANCESTOR TEST...");
/*     */       }
/* 563 */       for (X500Principal x500Principal : this.trustedSubjectDNs) {
/* 564 */         X500Name x500Name = X500Name.asX500Name(x500Principal);
/*     */ 
/*     */         
/* 567 */         int k = Builder.distance(x500Name, x500Name1, 2147483647);
/*     */         
/* 569 */         int m = Builder.distance(x500Name, x500Name2, 2147483647);
/* 570 */         if (ForwardBuilder.debug != null) {
/* 571 */           ForwardBuilder.debug.println("PKIXCertComparator.compare() distanceTto1: " + k);
/* 572 */           ForwardBuilder.debug.println("PKIXCertComparator.compare() distanceTto2: " + m);
/*     */         } 
/* 574 */         if (k < 0 || m < 0) {
/* 575 */           if (k == m)
/* 576 */             return -1; 
/* 577 */           if (k < 0 && m >= 0)
/* 578 */             return -1; 
/* 579 */           if (k >= 0 && m < 0)
/* 580 */             return 1; 
/* 581 */           if (k > m) {
/* 582 */             return -1;
/*     */           }
/* 584 */           return 1;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 593 */       if (ForwardBuilder.debug != null) {
/* 594 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() SAME NAMESPACE AS TRUSTED TEST...");
/*     */       }
/* 596 */       for (X500Principal x500Principal : this.trustedSubjectDNs) {
/* 597 */         X500Name x500Name5 = X500Name.asX500Name(x500Principal);
/* 598 */         X500Name x500Name6 = x500Name5.commonAncestor(x500Name1);
/* 599 */         X500Name x500Name7 = x500Name5.commonAncestor(x500Name2);
/* 600 */         if (ForwardBuilder.debug != null) {
/* 601 */           ForwardBuilder.debug.println("PKIXCertComparator.compare() tAo1: " + String.valueOf(x500Name6));
/* 602 */           ForwardBuilder.debug.println("PKIXCertComparator.compare() tAo2: " + String.valueOf(x500Name7));
/*     */         } 
/* 604 */         if (x500Name6 != null || x500Name7 != null) {
/* 605 */           if (x500Name6 != null && x500Name7 != null) {
/*     */             
/* 607 */             int k = Builder.hops(x500Name5, x500Name1, 2147483647);
/*     */             
/* 609 */             int m = Builder.hops(x500Name5, x500Name2, 2147483647);
/* 610 */             if (ForwardBuilder.debug != null) {
/* 611 */               ForwardBuilder.debug.println("PKIXCertComparator.compare() hopsTto1: " + k);
/* 612 */               ForwardBuilder.debug.println("PKIXCertComparator.compare() hopsTto2: " + m);
/*     */             } 
/* 614 */             if (k == m)
/* 615 */               continue;  if (k > m) {
/* 616 */               return 1;
/*     */             }
/* 618 */             return -1;
/*     */           } 
/* 620 */           if (x500Name6 == null) {
/* 621 */             return 1;
/*     */           }
/* 623 */           return -1;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 632 */       if (ForwardBuilder.debug != null) {
/* 633 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() CERT ISSUER/SUBJECT COMPARISON TEST...");
/*     */       }
/* 635 */       X500Principal x500Principal3 = param1X509Certificate1.getSubjectX500Principal();
/* 636 */       X500Principal x500Principal4 = param1X509Certificate2.getSubjectX500Principal();
/* 637 */       X500Name x500Name3 = X500Name.asX500Name(x500Principal3);
/* 638 */       X500Name x500Name4 = X500Name.asX500Name(x500Principal4);
/*     */       
/* 640 */       if (ForwardBuilder.debug != null) {
/* 641 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() o1 Subject: " + x500Principal3);
/* 642 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() o2 Subject: " + x500Principal4);
/*     */       } 
/*     */       
/* 645 */       int i = Builder.distance(x500Name3, x500Name1, 2147483647);
/*     */       
/* 647 */       int j = Builder.distance(x500Name4, x500Name2, 2147483647);
/* 648 */       if (ForwardBuilder.debug != null) {
/* 649 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() distanceStoI1: " + i);
/* 650 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() distanceStoI2: " + j);
/*     */       } 
/* 652 */       if (j > i)
/* 653 */         return -1; 
/* 654 */       if (j < i) {
/* 655 */         return 1;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 660 */       if (ForwardBuilder.debug != null) {
/* 661 */         ForwardBuilder.debug.println("PKIXCertComparator.compare() no tests matched; RETURN 0");
/*     */       }
/* 663 */       return -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void verifyCert(X509Certificate paramX509Certificate, State paramState, List<X509Certificate> paramList) throws GeneralSecurityException {
/* 697 */     if (debug != null) {
/* 698 */       debug.println("ForwardBuilder.verifyCert(SN: " + 
/* 699 */           Debug.toHexString(paramX509Certificate.getSerialNumber()) + "\n  Issuer: " + paramX509Certificate
/* 700 */           .getIssuerX500Principal() + ")\n  Subject: " + paramX509Certificate
/* 701 */           .getSubjectX500Principal() + ")");
/*     */     }
/*     */     
/* 704 */     ForwardState forwardState = (ForwardState)paramState;
/*     */ 
/*     */     
/* 707 */     forwardState.untrustedChecker.check(paramX509Certificate, Collections.emptySet());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 713 */     if (paramList != null) {
/* 714 */       for (X509Certificate x509Certificate : paramList) {
/* 715 */         if (paramX509Certificate.equals(x509Certificate)) {
/* 716 */           if (debug != null) {
/* 717 */             debug.println("loop detected!!");
/*     */           }
/* 719 */           throw new CertPathValidatorException("loop detected");
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 725 */     boolean bool = this.trustedCerts.contains(paramX509Certificate);
/*     */ 
/*     */     
/* 728 */     if (!bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 734 */       Set<String> set = paramX509Certificate.getCriticalExtensionOIDs();
/* 735 */       if (set == null) {
/* 736 */         set = Collections.emptySet();
/*     */       }
/* 738 */       for (PKIXCertPathChecker pKIXCertPathChecker : forwardState.forwardCheckers) {
/* 739 */         pKIXCertPathChecker.check(paramX509Certificate, set);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 748 */       for (PKIXCertPathChecker pKIXCertPathChecker : this.buildParams.certPathCheckers()) {
/* 749 */         if (!pKIXCertPathChecker.isForwardCheckingSupported()) {
/* 750 */           Set<String> set1 = pKIXCertPathChecker.getSupportedExtensions();
/* 751 */           if (set1 != null) {
/* 752 */             set.removeAll(set1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 761 */       if (!set.isEmpty()) {
/* 762 */         set.remove(PKIXExtensions.BasicConstraints_Id.toString());
/* 763 */         set.remove(PKIXExtensions.NameConstraints_Id.toString());
/* 764 */         set.remove(PKIXExtensions.CertificatePolicies_Id.toString());
/* 765 */         set.remove(PKIXExtensions.PolicyMappings_Id.toString());
/* 766 */         set.remove(PKIXExtensions.PolicyConstraints_Id.toString());
/* 767 */         set.remove(PKIXExtensions.InhibitAnyPolicy_Id.toString());
/* 768 */         set.remove(PKIXExtensions.SubjectAlternativeName_Id.toString());
/* 769 */         set.remove(PKIXExtensions.KeyUsage_Id.toString());
/* 770 */         set.remove(PKIXExtensions.ExtendedKeyUsage_Id.toString());
/*     */         
/* 772 */         if (!set.isEmpty()) {
/* 773 */           throw new CertPathValidatorException("Unrecognized critical extension(s)", null, null, -1, PKIXReason.UNRECOGNIZED_CRIT_EXT);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 783 */     if (forwardState.isInitial()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 788 */     if (!bool) {
/*     */       
/* 790 */       if (paramX509Certificate.getBasicConstraints() == -1) {
/* 791 */         throw new CertificateException("cert is NOT a CA cert");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 797 */       KeyChecker.verifyCAKeyUsage(paramX509Certificate);
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
/* 811 */     if (!forwardState.keyParamsNeeded()) {
/* 812 */       forwardState.cert.verify(paramX509Certificate.getPublicKey(), this.buildParams
/* 813 */           .sigProvider());
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
/*     */   boolean isPathCompleted(X509Certificate paramX509Certificate) {
/* 833 */     ArrayList<TrustAnchor> arrayList = new ArrayList();
/*     */     
/* 835 */     for (TrustAnchor trustAnchor : this.trustAnchors) {
/* 836 */       if (trustAnchor.getTrustedCert() != null) {
/* 837 */         if (paramX509Certificate.equals(trustAnchor.getTrustedCert())) {
/* 838 */           this.trustAnchor = trustAnchor;
/* 839 */           return true;
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 844 */       X500Principal x500Principal = trustAnchor.getCA();
/* 845 */       PublicKey publicKey = trustAnchor.getCAPublicKey();
/*     */       
/* 847 */       if (x500Principal != null && publicKey != null && x500Principal
/* 848 */         .equals(paramX509Certificate.getSubjectX500Principal()) && 
/* 849 */         publicKey.equals(paramX509Certificate.getPublicKey())) {
/*     */         
/* 851 */         this.trustAnchor = trustAnchor;
/* 852 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 856 */       arrayList.add(trustAnchor);
/*     */     } 
/*     */     
/* 859 */     for (TrustAnchor trustAnchor : arrayList) {
/* 860 */       X500Principal x500Principal = trustAnchor.getCA();
/* 861 */       PublicKey publicKey = trustAnchor.getCAPublicKey();
/*     */       
/* 863 */       if (x500Principal == null || 
/* 864 */         !x500Principal.equals(paramX509Certificate.getIssuerX500Principal())) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 869 */       if (PKIX.isDSAPublicKeyWithoutParams(publicKey)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 877 */         paramX509Certificate.verify(publicKey, this.buildParams.sigProvider());
/* 878 */       } catch (InvalidKeyException invalidKeyException) {
/* 879 */         if (debug != null) {
/* 880 */           debug.println("ForwardBuilder.isPathCompleted() invalid DSA key found");
/*     */         }
/*     */         
/*     */         continue;
/* 884 */       } catch (GeneralSecurityException generalSecurityException) {
/* 885 */         if (debug != null) {
/* 886 */           debug.println("ForwardBuilder.isPathCompleted() unexpected exception");
/*     */           
/* 888 */           generalSecurityException.printStackTrace();
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 893 */       this.trustAnchor = trustAnchor;
/* 894 */       return true;
/*     */     } 
/*     */     
/* 897 */     return false;
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
/*     */   void addCertToPath(X509Certificate paramX509Certificate, LinkedList<X509Certificate> paramLinkedList) {
/* 909 */     paramLinkedList.addFirst(paramX509Certificate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeFinalCertFromPath(LinkedList<X509Certificate> paramLinkedList) {
/* 918 */     paramLinkedList.removeFirst();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/ForwardBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */