/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.CertPathBuilderException;
/*     */ import java.security.cert.CertPathBuilderResult;
/*     */ import java.security.cert.CertPathBuilderSpi;
/*     */ import java.security.cert.CertPathChecker;
/*     */ import java.security.cert.CertPathParameters;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.CertSelector;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.PKIXCertPathBuilderResult;
/*     */ import java.security.cert.PKIXCertPathChecker;
/*     */ import java.security.cert.PKIXReason;
/*     */ import java.security.cert.PolicyNode;
/*     */ import java.security.cert.TrustAnchor;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.x509.PKIXExtensions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SunCertPathBuilder
/*     */   extends CertPathBuilderSpi
/*     */ {
/*  69 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */   
/*     */   private PKIX.BuilderParams buildParams;
/*     */ 
/*     */   
/*     */   private CertificateFactory cf;
/*     */   
/*     */   private boolean pathCompleted = false;
/*     */   
/*     */   private PolicyNode policyTreeResult;
/*     */   
/*     */   private TrustAnchor trustAnchor;
/*     */   
/*     */   private PublicKey finalPublicKey;
/*     */ 
/*     */   
/*     */   public SunCertPathBuilder() throws CertPathBuilderException {
/*     */     try {
/*  88 */       this.cf = CertificateFactory.getInstance("X.509");
/*  89 */     } catch (CertificateException certificateException) {
/*  90 */       throw new CertPathBuilderException(certificateException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CertPathChecker engineGetRevocationChecker() {
/*  96 */     return new RevocationChecker();
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
/*     */   public CertPathBuilderResult engineBuild(CertPathParameters paramCertPathParameters) throws CertPathBuilderException, InvalidAlgorithmParameterException {
/* 121 */     if (debug != null) {
/* 122 */       debug.println("SunCertPathBuilder.engineBuild(" + paramCertPathParameters + ")");
/*     */     }
/*     */     
/* 125 */     this.buildParams = PKIX.checkBuilderParams(paramCertPathParameters);
/* 126 */     return build();
/*     */   }
/*     */   
/*     */   private PKIXCertPathBuilderResult build() throws CertPathBuilderException {
/* 130 */     ArrayList<List<Vertex>> arrayList = new ArrayList();
/* 131 */     PKIXCertPathBuilderResult pKIXCertPathBuilderResult = buildCertPath(false, arrayList);
/* 132 */     if (pKIXCertPathBuilderResult == null) {
/* 133 */       if (debug != null) {
/* 134 */         debug.println("SunCertPathBuilder.engineBuild: 2nd pass; try building again searching all certstores");
/*     */       }
/*     */ 
/*     */       
/* 138 */       arrayList.clear();
/* 139 */       pKIXCertPathBuilderResult = buildCertPath(true, arrayList);
/* 140 */       if (pKIXCertPathBuilderResult == null) {
/* 141 */         throw new SunCertPathBuilderException("unable to find valid certification path to requested target", new AdjacencyList(arrayList));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 146 */     return pKIXCertPathBuilderResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PKIXCertPathBuilderResult buildCertPath(boolean paramBoolean, List<List<Vertex>> paramList) throws CertPathBuilderException {
/* 154 */     this.pathCompleted = false;
/* 155 */     this.trustAnchor = null;
/* 156 */     this.finalPublicKey = null;
/* 157 */     this.policyTreeResult = null;
/* 158 */     LinkedList<X509Certificate> linkedList = new LinkedList();
/*     */     try {
/* 160 */       buildForward(paramList, linkedList, paramBoolean);
/* 161 */     } catch (GeneralSecurityException|IOException generalSecurityException) {
/* 162 */       if (debug != null) {
/* 163 */         debug.println("SunCertPathBuilder.engineBuild() exception in build");
/*     */         
/* 165 */         generalSecurityException.printStackTrace();
/*     */       } 
/* 167 */       throw new SunCertPathBuilderException("unable to find valid certification path to requested target", generalSecurityException, new AdjacencyList(paramList));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 174 */       if (this.pathCompleted) {
/* 175 */         if (debug != null) {
/* 176 */           debug.println("SunCertPathBuilder.engineBuild() pathCompleted");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 182 */         Collections.reverse(linkedList);
/*     */         
/* 184 */         return new SunCertPathBuilderResult(this.cf
/* 185 */             .generateCertPath((List)linkedList), this.trustAnchor, this.policyTreeResult, this.finalPublicKey, new AdjacencyList(paramList));
/*     */       }
/*     */     
/*     */     }
/* 189 */     catch (CertificateException certificateException) {
/* 190 */       if (debug != null) {
/* 191 */         debug.println("SunCertPathBuilder.engineBuild() exception in wrap-up");
/*     */         
/* 193 */         certificateException.printStackTrace();
/*     */       } 
/* 195 */       throw new SunCertPathBuilderException("unable to find valid certification path to requested target", certificateException, new AdjacencyList(paramList));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 200 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildForward(List<List<Vertex>> paramList, LinkedList<X509Certificate> paramLinkedList, boolean paramBoolean) throws GeneralSecurityException, IOException {
/* 211 */     if (debug != null) {
/* 212 */       debug.println("SunCertPathBuilder.buildForward()...");
/*     */     }
/*     */ 
/*     */     
/* 216 */     ForwardState forwardState = new ForwardState();
/* 217 */     forwardState.initState(this.buildParams.certPathCheckers());
/*     */ 
/*     */     
/* 220 */     paramList.clear();
/* 221 */     paramList.add(new LinkedList<>());
/*     */     
/* 223 */     forwardState.untrustedChecker = new UntrustedChecker();
/*     */     
/* 225 */     depthFirstSearchForward(this.buildParams.targetSubject(), forwardState, new ForwardBuilder(this.buildParams, paramBoolean), paramList, paramLinkedList);
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
/*     */   private void depthFirstSearchForward(X500Principal paramX500Principal, ForwardState paramForwardState, ForwardBuilder paramForwardBuilder, List<List<Vertex>> paramList, LinkedList<X509Certificate> paramLinkedList) throws GeneralSecurityException, IOException {
/* 253 */     if (debug != null) {
/* 254 */       debug.println("SunCertPathBuilder.depthFirstSearchForward(" + paramX500Principal + ", " + paramForwardState
/* 255 */           .toString() + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 263 */     Collection<X509Certificate> collection = paramForwardBuilder.getMatchingCerts(paramForwardState, this.buildParams.certStores());
/* 264 */     List<Vertex> list = addVertices(collection, paramList);
/* 265 */     if (debug != null) {
/* 266 */       debug.println("SunCertPathBuilder.depthFirstSearchForward(): certs.size=" + list
/* 267 */           .size());
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
/* 278 */     label121: for (Vertex vertex : list) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 286 */       ForwardState forwardState = (ForwardState)paramForwardState.clone();
/* 287 */       X509Certificate x509Certificate = vertex.getCertificate();
/*     */       
/*     */       try {
/* 290 */         paramForwardBuilder.verifyCert(x509Certificate, forwardState, paramLinkedList);
/* 291 */       } catch (GeneralSecurityException generalSecurityException) {
/* 292 */         if (debug != null) {
/* 293 */           debug.println("SunCertPathBuilder.depthFirstSearchForward(): validation failed: " + generalSecurityException);
/*     */           
/* 295 */           generalSecurityException.printStackTrace();
/*     */         } 
/* 297 */         vertex.setThrowable(generalSecurityException);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 310 */       if (paramForwardBuilder.isPathCompleted(x509Certificate)) {
/*     */         
/* 312 */         if (debug != null) {
/* 313 */           debug.println("SunCertPathBuilder.depthFirstSearchForward(): commencing final verification");
/*     */         }
/*     */         
/* 316 */         ArrayList<X509Certificate> arrayList = new ArrayList<>(paramLinkedList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 324 */         if (paramForwardBuilder.trustAnchor.getTrustedCert() == null) {
/* 325 */           arrayList.add(0, x509Certificate);
/*     */         }
/*     */ 
/*     */         
/* 329 */         Set<String> set = Collections.singleton("2.5.29.32.0");
/*     */         
/* 331 */         PolicyNodeImpl policyNodeImpl = new PolicyNodeImpl(null, "2.5.29.32.0", null, false, set, false);
/*     */ 
/*     */         
/* 334 */         ArrayList<PolicyChecker> arrayList1 = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 341 */         PolicyChecker policyChecker = new PolicyChecker(this.buildParams.initialPolicies(), arrayList.size(), this.buildParams.explicitPolicyRequired(), this.buildParams.policyMappingInhibited(), this.buildParams.anyPolicyInhibited(), this.buildParams.policyQualifiersRejected(), policyNodeImpl);
/*     */         
/* 343 */         arrayList1.add(policyChecker);
/*     */ 
/*     */         
/* 346 */         arrayList1.add(new AlgorithmChecker(paramForwardBuilder.trustAnchor, this.buildParams
/* 347 */               .date(), this.buildParams.variant()));
/*     */         
/* 349 */         BasicChecker basicChecker = null;
/* 350 */         if (forwardState.keyParamsNeeded()) {
/* 351 */           PublicKey publicKey = x509Certificate.getPublicKey();
/* 352 */           if (paramForwardBuilder.trustAnchor.getTrustedCert() == null) {
/* 353 */             publicKey = paramForwardBuilder.trustAnchor.getCAPublicKey();
/* 354 */             if (debug != null) {
/* 355 */               debug.println("SunCertPathBuilder.depthFirstSearchForward using buildParams public key: " + publicKey
/*     */ 
/*     */                   
/* 358 */                   .toString());
/*     */             }
/*     */           } 
/* 361 */           TrustAnchor trustAnchor = new TrustAnchor(x509Certificate.getSubjectX500Principal(), publicKey, null);
/*     */ 
/*     */ 
/*     */           
/* 365 */           basicChecker = new BasicChecker(trustAnchor, this.buildParams.date(), this.buildParams.sigProvider(), true);
/*     */           
/* 367 */           arrayList1.add(basicChecker);
/*     */         } 
/*     */         
/* 370 */         this.buildParams.setCertPath(this.cf.generateCertPath((List)arrayList));
/*     */         
/* 372 */         boolean bool = false;
/* 373 */         List<PKIXCertPathChecker> list1 = this.buildParams.certPathCheckers();
/* 374 */         for (PKIXCertPathChecker pKIXCertPathChecker : list1) {
/* 375 */           if (pKIXCertPathChecker instanceof java.security.cert.PKIXRevocationChecker) {
/* 376 */             if (bool) {
/* 377 */               throw new CertPathValidatorException("Only one PKIXRevocationChecker can be specified");
/*     */             }
/*     */             
/* 380 */             bool = true;
/*     */             
/* 382 */             if (pKIXCertPathChecker instanceof RevocationChecker) {
/* 383 */               ((RevocationChecker)pKIXCertPathChecker).init(paramForwardBuilder.trustAnchor, this.buildParams);
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 390 */         if (this.buildParams.revocationEnabled() && !bool) {
/* 391 */           arrayList1.add(new RevocationChecker(paramForwardBuilder.trustAnchor, this.buildParams));
/*     */         }
/*     */ 
/*     */         
/* 395 */         arrayList1.addAll(list1);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 400 */         for (byte b = 0; b < arrayList.size(); b++) {
/* 401 */           X509Certificate x509Certificate1 = arrayList.get(b);
/* 402 */           if (debug != null) {
/* 403 */             debug.println("current subject = " + x509Certificate1
/* 404 */                 .getSubjectX500Principal());
/*     */           }
/* 406 */           Set<String> set1 = x509Certificate1.getCriticalExtensionOIDs();
/* 407 */           if (set1 == null) {
/* 408 */             set1 = Collections.emptySet();
/*     */           }
/*     */           
/* 411 */           for (PKIXCertPathChecker pKIXCertPathChecker : arrayList1) {
/* 412 */             if (!pKIXCertPathChecker.isForwardCheckingSupported()) {
/* 413 */               if (b == 0) {
/* 414 */                 pKIXCertPathChecker.init(false);
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 419 */                 if (pKIXCertPathChecker instanceof AlgorithmChecker) {
/* 420 */                   ((AlgorithmChecker)pKIXCertPathChecker)
/* 421 */                     .trySetTrustAnchor(paramForwardBuilder.trustAnchor);
/*     */                 }
/*     */               } 
/*     */               
/*     */               try {
/* 426 */                 pKIXCertPathChecker.check(x509Certificate1, set1);
/* 427 */               } catch (CertPathValidatorException certPathValidatorException) {
/* 428 */                 if (debug != null) {
/* 429 */                   debug
/* 430 */                     .println("SunCertPathBuilder.depthFirstSearchForward(): final verification failed: " + certPathValidatorException);
/*     */                 }
/*     */ 
/*     */                 
/* 434 */                 if (this.buildParams.targetCertConstraints().match(x509Certificate1) && certPathValidatorException
/* 435 */                   .getReason() == CertPathValidatorException.BasicReason.REVOKED) {
/* 436 */                   throw certPathValidatorException;
/*     */                 }
/* 438 */                 vertex.setThrowable(certPathValidatorException);
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 continue label121;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 451 */           for (PKIXCertPathChecker pKIXCertPathChecker : this.buildParams.certPathCheckers()) {
/*     */             
/* 453 */             if (pKIXCertPathChecker.isForwardCheckingSupported()) {
/*     */               
/* 455 */               Set<String> set2 = pKIXCertPathChecker.getSupportedExtensions();
/* 456 */               if (set2 != null) {
/* 457 */                 set1.removeAll(set2);
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/* 462 */           if (!set1.isEmpty()) {
/* 463 */             set1.remove(PKIXExtensions.BasicConstraints_Id.toString());
/* 464 */             set1.remove(PKIXExtensions.NameConstraints_Id.toString());
/* 465 */             set1.remove(PKIXExtensions.CertificatePolicies_Id.toString());
/* 466 */             set1.remove(PKIXExtensions.PolicyMappings_Id.toString());
/* 467 */             set1.remove(PKIXExtensions.PolicyConstraints_Id.toString());
/* 468 */             set1.remove(PKIXExtensions.InhibitAnyPolicy_Id.toString());
/* 469 */             set1.remove(PKIXExtensions.SubjectAlternativeName_Id
/* 470 */                 .toString());
/* 471 */             set1.remove(PKIXExtensions.KeyUsage_Id.toString());
/* 472 */             set1.remove(PKIXExtensions.ExtendedKeyUsage_Id.toString());
/*     */             
/* 474 */             if (!set1.isEmpty()) {
/* 475 */               throw new CertPathValidatorException("unrecognized critical extension(s)", null, null, -1, PKIXReason.UNRECOGNIZED_CRIT_EXT);
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 481 */         if (debug != null) {
/* 482 */           debug.println("SunCertPathBuilder.depthFirstSearchForward(): final verification succeeded - path completed!");
/*     */         }
/* 484 */         this.pathCompleted = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 491 */         if (paramForwardBuilder.trustAnchor.getTrustedCert() == null) {
/* 492 */           paramForwardBuilder.addCertToPath(x509Certificate, paramLinkedList);
/*     */         }
/* 494 */         this.trustAnchor = paramForwardBuilder.trustAnchor;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 499 */         if (basicChecker != null) {
/* 500 */           this.finalPublicKey = basicChecker.getPublicKey();
/*     */         } else {
/*     */           Certificate certificate;
/* 503 */           if (paramLinkedList.isEmpty()) {
/* 504 */             certificate = paramForwardBuilder.trustAnchor.getTrustedCert();
/*     */           } else {
/* 506 */             certificate = paramLinkedList.getLast();
/*     */           } 
/* 508 */           this.finalPublicKey = certificate.getPublicKey();
/*     */         } 
/*     */         
/* 511 */         this.policyTreeResult = policyChecker.getPolicyTree();
/*     */         return;
/*     */       } 
/* 514 */       paramForwardBuilder.addCertToPath(x509Certificate, paramLinkedList);
/*     */ 
/*     */ 
/*     */       
/* 518 */       forwardState.updateState(x509Certificate);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 524 */       paramList.add(new LinkedList<>());
/* 525 */       vertex.setIndex(paramList.size() - 1);
/*     */ 
/*     */       
/* 528 */       depthFirstSearchForward(x509Certificate.getIssuerX500Principal(), forwardState, paramForwardBuilder, paramList, paramLinkedList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 534 */       if (this.pathCompleted) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 543 */       if (debug != null) {
/* 544 */         debug.println("SunCertPathBuilder.depthFirstSearchForward(): backtracking");
/*     */       }
/* 546 */       paramForwardBuilder.removeFinalCertFromPath(paramLinkedList);
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
/*     */   private static List<Vertex> addVertices(Collection<X509Certificate> paramCollection, List<List<Vertex>> paramList) {
/* 558 */     List<Vertex> list = paramList.get(paramList.size() - 1);
/*     */     
/* 560 */     for (X509Certificate x509Certificate : paramCollection) {
/* 561 */       Vertex vertex = new Vertex(x509Certificate);
/* 562 */       list.add(vertex);
/*     */     } 
/*     */     
/* 565 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean anchorIsTarget(TrustAnchor paramTrustAnchor, CertSelector paramCertSelector) {
/* 575 */     X509Certificate x509Certificate = paramTrustAnchor.getTrustedCert();
/* 576 */     if (x509Certificate != null) {
/* 577 */       return paramCertSelector.match(x509Certificate);
/*     */     }
/* 579 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/SunCertPathBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */