/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.PKIXCertPathChecker;
/*     */ import java.security.cert.PKIXReason;
/*     */ import java.security.cert.PolicyNode;
/*     */ import java.security.cert.PolicyQualifierInfo;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.x509.CertificatePoliciesExtension;
/*     */ import sun.security.x509.CertificatePolicyMap;
/*     */ import sun.security.x509.InhibitAnyPolicyExtension;
/*     */ import sun.security.x509.PKIXExtensions;
/*     */ import sun.security.x509.PolicyConstraintsExtension;
/*     */ import sun.security.x509.PolicyInformation;
/*     */ import sun.security.x509.PolicyMappingsExtension;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class PolicyChecker
/*     */   extends PKIXCertPathChecker
/*     */ {
/*     */   private final Set<String> initPolicies;
/*     */   private final int certPathLen;
/*     */   private final boolean expPolicyRequired;
/*     */   private final boolean polMappingInhibited;
/*     */   private final boolean anyPolicyInhibited;
/*     */   private final boolean rejectPolicyQualifiers;
/*     */   private PolicyNodeImpl rootNode;
/*     */   private int explicitPolicy;
/*     */   private int policyMapping;
/*     */   private int inhibitAnyPolicy;
/*     */   private int certIndex;
/*     */   private Set<String> supportedExts;
/*  74 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String ANY_POLICY = "2.5.29.32.0";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PolicyChecker(Set<String> paramSet, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, PolicyNodeImpl paramPolicyNodeImpl) {
/*  93 */     if (paramSet.isEmpty()) {
/*     */ 
/*     */       
/*  96 */       this.initPolicies = new HashSet<>(1);
/*  97 */       this.initPolicies.add("2.5.29.32.0");
/*     */     } else {
/*  99 */       this.initPolicies = new HashSet<>(paramSet);
/*     */     } 
/* 101 */     this.certPathLen = paramInt;
/* 102 */     this.expPolicyRequired = paramBoolean1;
/* 103 */     this.polMappingInhibited = paramBoolean2;
/* 104 */     this.anyPolicyInhibited = paramBoolean3;
/* 105 */     this.rejectPolicyQualifiers = paramBoolean4;
/* 106 */     this.rootNode = paramPolicyNodeImpl;
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
/*     */   public void init(boolean paramBoolean) throws CertPathValidatorException {
/* 120 */     if (paramBoolean) {
/* 121 */       throw new CertPathValidatorException("forward checking not supported");
/*     */     }
/*     */ 
/*     */     
/* 125 */     this.certIndex = 1;
/* 126 */     this.explicitPolicy = this.expPolicyRequired ? 0 : (this.certPathLen + 1);
/* 127 */     this.policyMapping = this.polMappingInhibited ? 0 : (this.certPathLen + 1);
/* 128 */     this.inhibitAnyPolicy = this.anyPolicyInhibited ? 0 : (this.certPathLen + 1);
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
/*     */   public boolean isForwardCheckingSupported() {
/* 141 */     return false;
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
/*     */   public Set<String> getSupportedExtensions() {
/* 156 */     if (this.supportedExts == null) {
/* 157 */       this.supportedExts = new HashSet<>(4);
/* 158 */       this.supportedExts.add(PKIXExtensions.CertificatePolicies_Id.toString());
/* 159 */       this.supportedExts.add(PKIXExtensions.PolicyMappings_Id.toString());
/* 160 */       this.supportedExts.add(PKIXExtensions.PolicyConstraints_Id.toString());
/* 161 */       this.supportedExts.add(PKIXExtensions.InhibitAnyPolicy_Id.toString());
/* 162 */       this.supportedExts = Collections.unmodifiableSet(this.supportedExts);
/*     */     } 
/* 164 */     return this.supportedExts;
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
/*     */   public void check(Certificate paramCertificate, Collection<String> paramCollection) throws CertPathValidatorException {
/* 180 */     checkPolicy((X509Certificate)paramCertificate);
/*     */     
/* 182 */     if (paramCollection != null && !paramCollection.isEmpty()) {
/* 183 */       paramCollection.remove(PKIXExtensions.CertificatePolicies_Id.toString());
/* 184 */       paramCollection.remove(PKIXExtensions.PolicyMappings_Id.toString());
/* 185 */       paramCollection.remove(PKIXExtensions.PolicyConstraints_Id.toString());
/* 186 */       paramCollection.remove(PKIXExtensions.InhibitAnyPolicy_Id.toString());
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
/*     */   private void checkPolicy(X509Certificate paramX509Certificate) throws CertPathValidatorException {
/* 200 */     String str = "certificate policies";
/* 201 */     if (debug != null) {
/* 202 */       debug.println("PolicyChecker.checkPolicy() ---checking " + str + "...");
/*     */       
/* 204 */       debug.println("PolicyChecker.checkPolicy() certIndex = " + this.certIndex);
/*     */       
/* 206 */       debug.println("PolicyChecker.checkPolicy() BEFORE PROCESSING: explicitPolicy = " + this.explicitPolicy);
/*     */       
/* 208 */       debug.println("PolicyChecker.checkPolicy() BEFORE PROCESSING: policyMapping = " + this.policyMapping);
/*     */       
/* 210 */       debug.println("PolicyChecker.checkPolicy() BEFORE PROCESSING: inhibitAnyPolicy = " + this.inhibitAnyPolicy);
/*     */       
/* 212 */       debug.println("PolicyChecker.checkPolicy() BEFORE PROCESSING: policyTree = " + this.rootNode);
/*     */     } 
/*     */ 
/*     */     
/* 216 */     X509CertImpl x509CertImpl = null;
/*     */     try {
/* 218 */       x509CertImpl = X509CertImpl.toImpl(paramX509Certificate);
/* 219 */     } catch (CertificateException certificateException) {
/* 220 */       throw new CertPathValidatorException(certificateException);
/*     */     } 
/*     */     
/* 223 */     boolean bool = (this.certIndex == this.certPathLen) ? true : false;
/*     */     
/* 225 */     this.rootNode = processPolicies(this.certIndex, this.initPolicies, this.explicitPolicy, this.policyMapping, this.inhibitAnyPolicy, this.rejectPolicyQualifiers, this.rootNode, x509CertImpl, bool);
/*     */ 
/*     */ 
/*     */     
/* 229 */     if (!bool) {
/* 230 */       this.explicitPolicy = mergeExplicitPolicy(this.explicitPolicy, x509CertImpl, bool);
/*     */       
/* 232 */       this.policyMapping = mergePolicyMapping(this.policyMapping, x509CertImpl);
/* 233 */       this.inhibitAnyPolicy = mergeInhibitAnyPolicy(this.inhibitAnyPolicy, x509CertImpl);
/*     */     } 
/*     */ 
/*     */     
/* 237 */     this.certIndex++;
/*     */     
/* 239 */     if (debug != null) {
/* 240 */       debug.println("PolicyChecker.checkPolicy() AFTER PROCESSING: explicitPolicy = " + this.explicitPolicy);
/*     */       
/* 242 */       debug.println("PolicyChecker.checkPolicy() AFTER PROCESSING: policyMapping = " + this.policyMapping);
/*     */       
/* 244 */       debug.println("PolicyChecker.checkPolicy() AFTER PROCESSING: inhibitAnyPolicy = " + this.inhibitAnyPolicy);
/*     */       
/* 246 */       debug.println("PolicyChecker.checkPolicy() AFTER PROCESSING: policyTree = " + this.rootNode);
/*     */       
/* 248 */       debug.println("PolicyChecker.checkPolicy() " + str + " verified");
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
/*     */   static int mergeExplicitPolicy(int paramInt, X509CertImpl paramX509CertImpl, boolean paramBoolean) throws CertPathValidatorException {
/* 270 */     if (paramInt > 0 && !X509CertImpl.isSelfIssued(paramX509CertImpl)) {
/* 271 */       paramInt--;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 276 */       PolicyConstraintsExtension policyConstraintsExtension = paramX509CertImpl.getPolicyConstraintsExtension();
/* 277 */       if (policyConstraintsExtension == null) {
/* 278 */         return paramInt;
/*     */       }
/* 280 */       int i = policyConstraintsExtension.get("require").intValue();
/* 281 */       if (debug != null) {
/* 282 */         debug.println("PolicyChecker.mergeExplicitPolicy() require Index from cert = " + i);
/*     */       }
/*     */       
/* 285 */       if (!paramBoolean) {
/* 286 */         if (i != -1 && (
/* 287 */           paramInt == -1 || i < paramInt)) {
/* 288 */           paramInt = i;
/*     */         
/*     */         }
/*     */       }
/* 292 */       else if (i == 0) {
/* 293 */         paramInt = i;
/*     */       } 
/* 295 */     } catch (IOException iOException) {
/* 296 */       if (debug != null) {
/* 297 */         debug.println("PolicyChecker.mergeExplicitPolicy unexpected exception");
/*     */         
/* 299 */         iOException.printStackTrace();
/*     */       } 
/* 301 */       throw new CertPathValidatorException(iOException);
/*     */     } 
/*     */     
/* 304 */     return paramInt;
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
/*     */   static int mergePolicyMapping(int paramInt, X509CertImpl paramX509CertImpl) throws CertPathValidatorException {
/* 323 */     if (paramInt > 0 && !X509CertImpl.isSelfIssued(paramX509CertImpl)) {
/* 324 */       paramInt--;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 329 */       PolicyConstraintsExtension policyConstraintsExtension = paramX509CertImpl.getPolicyConstraintsExtension();
/* 330 */       if (policyConstraintsExtension == null) {
/* 331 */         return paramInt;
/*     */       }
/*     */       
/* 334 */       int i = policyConstraintsExtension.get("inhibit").intValue();
/* 335 */       if (debug != null) {
/* 336 */         debug.println("PolicyChecker.mergePolicyMapping() inhibit Index from cert = " + i);
/*     */       }
/*     */       
/* 339 */       if (i != -1 && (
/* 340 */         paramInt == -1 || i < paramInt)) {
/* 341 */         paramInt = i;
/*     */       }
/*     */     }
/* 344 */     catch (IOException iOException) {
/* 345 */       if (debug != null) {
/* 346 */         debug.println("PolicyChecker.mergePolicyMapping unexpected exception");
/*     */         
/* 348 */         iOException.printStackTrace();
/*     */       } 
/* 350 */       throw new CertPathValidatorException(iOException);
/*     */     } 
/*     */     
/* 353 */     return paramInt;
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
/*     */   static int mergeInhibitAnyPolicy(int paramInt, X509CertImpl paramX509CertImpl) throws CertPathValidatorException {
/* 371 */     if (paramInt > 0 && !X509CertImpl.isSelfIssued(paramX509CertImpl)) {
/* 372 */       paramInt--;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 377 */       InhibitAnyPolicyExtension inhibitAnyPolicyExtension = (InhibitAnyPolicyExtension)paramX509CertImpl.getExtension(PKIXExtensions.InhibitAnyPolicy_Id);
/* 378 */       if (inhibitAnyPolicyExtension == null) {
/* 379 */         return paramInt;
/*     */       }
/*     */       
/* 382 */       int i = inhibitAnyPolicyExtension.get("skip_certs").intValue();
/* 383 */       if (debug != null) {
/* 384 */         debug.println("PolicyChecker.mergeInhibitAnyPolicy() skipCerts Index from cert = " + i);
/*     */       }
/*     */       
/* 387 */       if (i != -1 && 
/* 388 */         i < paramInt) {
/* 389 */         paramInt = i;
/*     */       }
/*     */     }
/* 392 */     catch (IOException iOException) {
/* 393 */       if (debug != null) {
/* 394 */         debug.println("PolicyChecker.mergeInhibitAnyPolicy unexpected exception");
/*     */         
/* 396 */         iOException.printStackTrace();
/*     */       } 
/* 398 */       throw new CertPathValidatorException(iOException);
/*     */     } 
/*     */     
/* 401 */     return paramInt;
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
/*     */   static PolicyNodeImpl processPolicies(int paramInt1, Set<String> paramSet, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, PolicyNodeImpl paramPolicyNodeImpl, X509CertImpl paramX509CertImpl, boolean paramBoolean2) throws CertPathValidatorException {
/* 431 */     boolean bool = false;
/*     */     
/* 433 */     PolicyNodeImpl policyNodeImpl = null;
/* 434 */     Set<PolicyQualifierInfo> set = new HashSet();
/*     */     
/* 436 */     if (paramPolicyNodeImpl == null) {
/* 437 */       policyNodeImpl = null;
/*     */     } else {
/* 439 */       policyNodeImpl = paramPolicyNodeImpl.copyTree();
/*     */     } 
/*     */ 
/*     */     
/* 443 */     CertificatePoliciesExtension certificatePoliciesExtension = paramX509CertImpl.getCertificatePoliciesExtension();
/*     */ 
/*     */     
/* 446 */     if (certificatePoliciesExtension != null && policyNodeImpl != null) {
/* 447 */       List<PolicyInformation> list; bool = certificatePoliciesExtension.isCritical();
/* 448 */       if (debug != null) {
/* 449 */         debug.println("PolicyChecker.processPolicies() policiesCritical = " + bool);
/*     */       }
/*     */       
/*     */       try {
/* 453 */         list = certificatePoliciesExtension.get("policies");
/* 454 */       } catch (IOException iOException) {
/* 455 */         throw new CertPathValidatorException("Exception while retrieving policyOIDs", iOException);
/*     */       } 
/*     */ 
/*     */       
/* 459 */       if (debug != null) {
/* 460 */         debug.println("PolicyChecker.processPolicies() rejectPolicyQualifiers = " + paramBoolean1);
/*     */       }
/*     */       
/* 463 */       boolean bool1 = false;
/*     */ 
/*     */       
/* 466 */       for (PolicyInformation policyInformation : list) {
/*     */         
/* 468 */         String str = policyInformation.getPolicyIdentifier().getIdentifier().toString();
/*     */         
/* 470 */         if (str.equals("2.5.29.32.0")) {
/* 471 */           bool1 = true;
/* 472 */           set = policyInformation.getPolicyQualifiers();
/*     */           continue;
/*     */         } 
/* 475 */         if (debug != null) {
/* 476 */           debug.println("PolicyChecker.processPolicies() processing policy: " + str);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 481 */         Set<PolicyQualifierInfo> set1 = policyInformation.getPolicyQualifiers();
/*     */ 
/*     */ 
/*     */         
/* 485 */         if (!set1.isEmpty() && paramBoolean1 && bool)
/*     */         {
/* 487 */           throw new CertPathValidatorException("critical policy qualifiers present in certificate", null, null, -1, PKIXReason.INVALID_POLICY);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 493 */         boolean bool2 = processParents(paramInt1, bool, paramBoolean1, policyNodeImpl, str, set1, false);
/*     */ 
/*     */ 
/*     */         
/* 497 */         if (!bool2)
/*     */         {
/* 499 */           processParents(paramInt1, bool, paramBoolean1, policyNodeImpl, str, set1, true);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 507 */       if (bool1 && (
/* 508 */         paramInt4 > 0 || (!paramBoolean2 && 
/* 509 */         X509CertImpl.isSelfIssued(paramX509CertImpl)))) {
/* 510 */         if (debug != null) {
/* 511 */           debug.println("PolicyChecker.processPolicies() processing policy: 2.5.29.32.0");
/*     */         }
/*     */         
/* 514 */         processParents(paramInt1, bool, paramBoolean1, policyNodeImpl, "2.5.29.32.0", set, true);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 521 */       policyNodeImpl.prune(paramInt1);
/* 522 */       if (!policyNodeImpl.getChildren().hasNext()) {
/* 523 */         policyNodeImpl = null;
/*     */       }
/* 525 */     } else if (certificatePoliciesExtension == null) {
/* 526 */       if (debug != null) {
/* 527 */         debug.println("PolicyChecker.processPolicies() no policies present in cert");
/*     */       }
/*     */       
/* 530 */       policyNodeImpl = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 536 */     if (policyNodeImpl != null && 
/* 537 */       !paramBoolean2)
/*     */     {
/* 539 */       policyNodeImpl = processPolicyMappings(paramX509CertImpl, paramInt1, paramInt3, policyNodeImpl, bool, set);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 548 */     if (policyNodeImpl != null && !paramSet.contains("2.5.29.32.0") && certificatePoliciesExtension != null) {
/*     */       
/* 550 */       policyNodeImpl = removeInvalidNodes(policyNodeImpl, paramInt1, paramSet, certificatePoliciesExtension);
/*     */ 
/*     */ 
/*     */       
/* 554 */       if (policyNodeImpl != null && paramBoolean2)
/*     */       {
/* 556 */         policyNodeImpl = rewriteLeafNodes(paramInt1, paramSet, policyNodeImpl);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 561 */     if (paramBoolean2)
/*     */     {
/* 563 */       paramInt2 = mergeExplicitPolicy(paramInt2, paramX509CertImpl, paramBoolean2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 571 */     if (paramInt2 == 0 && policyNodeImpl == null) {
/* 572 */       throw new CertPathValidatorException("non-null policy tree required and policy tree is null", null, null, -1, PKIXReason.INVALID_POLICY);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 577 */     return policyNodeImpl;
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
/*     */   private static PolicyNodeImpl rewriteLeafNodes(int paramInt, Set<String> paramSet, PolicyNodeImpl paramPolicyNodeImpl) {
/* 597 */     Set<PolicyNodeImpl> set = paramPolicyNodeImpl.getPolicyNodesValid(paramInt, "2.5.29.32.0");
/* 598 */     if (set.isEmpty()) {
/* 599 */       return paramPolicyNodeImpl;
/*     */     }
/* 601 */     PolicyNodeImpl policyNodeImpl1 = set.iterator().next();
/* 602 */     PolicyNodeImpl policyNodeImpl2 = (PolicyNodeImpl)policyNodeImpl1.getParent();
/* 603 */     policyNodeImpl2.deleteChild(policyNodeImpl1);
/*     */     
/* 605 */     HashSet<String> hashSet = new HashSet<>(paramSet);
/* 606 */     for (PolicyNodeImpl policyNodeImpl : paramPolicyNodeImpl.getPolicyNodes(paramInt)) {
/* 607 */       hashSet.remove(policyNodeImpl.getValidPolicy());
/*     */     }
/* 609 */     if (hashSet.isEmpty()) {
/*     */ 
/*     */       
/* 612 */       paramPolicyNodeImpl.prune(paramInt);
/* 613 */       if (!paramPolicyNodeImpl.getChildren().hasNext()) {
/* 614 */         paramPolicyNodeImpl = null;
/*     */       }
/*     */     } else {
/* 617 */       boolean bool = policyNodeImpl1.isCritical();
/*     */       
/* 619 */       Set<PolicyQualifierInfo> set1 = policyNodeImpl1.getPolicyQualifiers();
/* 620 */       for (String str : hashSet) {
/* 621 */         Set<String> set2 = Collections.singleton(str);
/* 622 */         PolicyNodeImpl policyNodeImpl = new PolicyNodeImpl(policyNodeImpl2, str, set1, bool, set2, false);
/*     */       } 
/*     */     } 
/*     */     
/* 626 */     return paramPolicyNodeImpl;
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
/*     */   private static boolean processParents(int paramInt, boolean paramBoolean1, boolean paramBoolean2, PolicyNodeImpl paramPolicyNodeImpl, String paramString, Set<PolicyQualifierInfo> paramSet, boolean paramBoolean3) throws CertPathValidatorException {
/* 659 */     boolean bool = false;
/*     */     
/* 661 */     if (debug != null) {
/* 662 */       debug.println("PolicyChecker.processParents(): matchAny = " + paramBoolean3);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 667 */     Set<PolicyNodeImpl> set = paramPolicyNodeImpl.getPolicyNodesExpected(paramInt - 1, paramString, paramBoolean3);
/*     */ 
/*     */ 
/*     */     
/* 671 */     for (PolicyNodeImpl policyNodeImpl1 : set) {
/* 672 */       if (debug != null) {
/* 673 */         debug.println("PolicyChecker.processParents() found parent:\n" + policyNodeImpl1
/* 674 */             .asString());
/*     */       }
/* 676 */       bool = true;
/* 677 */       String str = policyNodeImpl1.getValidPolicy();
/*     */       
/* 679 */       PolicyNodeImpl policyNodeImpl2 = null;
/* 680 */       HashSet<String> hashSet = null;
/*     */       
/* 682 */       if (paramString.equals("2.5.29.32.0")) {
/*     */         
/* 684 */         Set<String> set1 = policyNodeImpl1.getExpectedPolicies();
/*     */         
/* 686 */         for (String str1 : set1) {
/*     */ 
/*     */           
/* 689 */           Iterator<PolicyNodeImpl> iterator = policyNodeImpl1.getChildren();
/* 690 */           while (iterator.hasNext()) {
/* 691 */             PolicyNodeImpl policyNodeImpl = iterator.next();
/* 692 */             String str2 = policyNodeImpl.getValidPolicy();
/* 693 */             if (str1.equals(str2) && 
/* 694 */               debug != null) {
/* 695 */               debug.println(str2 + " in parent's expected policy set already appears in child node");
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 702 */           HashSet<String> hashSet1 = new HashSet();
/* 703 */           hashSet1.add(str1);
/*     */           
/* 705 */           policyNodeImpl2 = new PolicyNodeImpl(policyNodeImpl1, str1, paramSet, paramBoolean1, hashSet1, false);
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 710 */       hashSet = new HashSet();
/* 711 */       hashSet.add(paramString);
/*     */       
/* 713 */       policyNodeImpl2 = new PolicyNodeImpl(policyNodeImpl1, paramString, paramSet, paramBoolean1, hashSet, false);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 719 */     return bool;
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
/*     */   private static PolicyNodeImpl processPolicyMappings(X509CertImpl paramX509CertImpl, int paramInt1, int paramInt2, PolicyNodeImpl paramPolicyNodeImpl, boolean paramBoolean, Set<PolicyQualifierInfo> paramSet) throws CertPathValidatorException {
/* 744 */     PolicyMappingsExtension policyMappingsExtension = paramX509CertImpl.getPolicyMappingsExtension();
/*     */     
/* 746 */     if (policyMappingsExtension == null) {
/* 747 */       return paramPolicyNodeImpl;
/*     */     }
/* 749 */     if (debug != null) {
/* 750 */       debug.println("PolicyChecker.processPolicyMappings() inside policyMapping check");
/*     */     }
/*     */     
/* 753 */     List<CertificatePolicyMap> list = null;
/*     */     try {
/* 755 */       list = policyMappingsExtension.get("map");
/* 756 */     } catch (IOException iOException) {
/* 757 */       if (debug != null) {
/* 758 */         debug.println("PolicyChecker.processPolicyMappings() mapping exception");
/*     */         
/* 760 */         iOException.printStackTrace();
/*     */       } 
/* 762 */       throw new CertPathValidatorException("Exception while checking mapping", iOException);
/*     */     } 
/*     */ 
/*     */     
/* 766 */     boolean bool = false;
/* 767 */     for (CertificatePolicyMap certificatePolicyMap : list) {
/*     */       
/* 769 */       String str1 = certificatePolicyMap.getIssuerIdentifier().getIdentifier().toString();
/*     */       
/* 771 */       String str2 = certificatePolicyMap.getSubjectIdentifier().getIdentifier().toString();
/* 772 */       if (debug != null) {
/* 773 */         debug.println("PolicyChecker.processPolicyMappings() issuerDomain = " + str1);
/*     */         
/* 775 */         debug.println("PolicyChecker.processPolicyMappings() subjectDomain = " + str2);
/*     */       } 
/*     */ 
/*     */       
/* 779 */       if (str1.equals("2.5.29.32.0")) {
/* 780 */         throw new CertPathValidatorException("encountered an issuerDomainPolicy of ANY_POLICY", null, null, -1, PKIXReason.INVALID_POLICY);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 785 */       if (str2.equals("2.5.29.32.0")) {
/* 786 */         throw new CertPathValidatorException("encountered a subjectDomainPolicy of ANY_POLICY", null, null, -1, PKIXReason.INVALID_POLICY);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 792 */       Set<PolicyNodeImpl> set = paramPolicyNodeImpl.getPolicyNodesValid(paramInt1, str1);
/* 793 */       if (!set.isEmpty()) {
/* 794 */         for (PolicyNodeImpl policyNodeImpl : set) {
/* 795 */           if (paramInt2 > 0 || paramInt2 == -1) {
/* 796 */             policyNodeImpl.addExpectedPolicy(str2); continue;
/* 797 */           }  if (paramInt2 == 0) {
/*     */             
/* 799 */             PolicyNodeImpl policyNodeImpl1 = (PolicyNodeImpl)policyNodeImpl.getParent();
/* 800 */             if (debug != null) {
/* 801 */               debug.println("PolicyChecker.processPolicyMappings() before deleting: policy tree = " + paramPolicyNodeImpl);
/*     */             }
/*     */             
/* 804 */             policyNodeImpl1.deleteChild(policyNodeImpl);
/* 805 */             bool = true;
/* 806 */             if (debug != null) {
/* 807 */               debug.println("PolicyChecker.processPolicyMappings() after deleting: policy tree = " + paramPolicyNodeImpl);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         continue;
/*     */       } 
/* 813 */       if (paramInt2 > 0 || paramInt2 == -1) {
/*     */         
/* 815 */         Set<PolicyNodeImpl> set1 = paramPolicyNodeImpl.getPolicyNodesValid(paramInt1, "2.5.29.32.0");
/* 816 */         for (PolicyNodeImpl policyNodeImpl1 : set1) {
/*     */           
/* 818 */           PolicyNodeImpl policyNodeImpl2 = (PolicyNodeImpl)policyNodeImpl1.getParent();
/*     */           
/* 820 */           HashSet<String> hashSet = new HashSet();
/* 821 */           hashSet.add(str2);
/*     */           
/* 823 */           PolicyNodeImpl policyNodeImpl3 = new PolicyNodeImpl(policyNodeImpl2, str1, paramSet, paramBoolean, hashSet, true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 831 */     if (bool) {
/* 832 */       paramPolicyNodeImpl.prune(paramInt1);
/* 833 */       if (!paramPolicyNodeImpl.getChildren().hasNext()) {
/* 834 */         if (debug != null)
/* 835 */           debug.println("setting rootNode to null"); 
/* 836 */         paramPolicyNodeImpl = null;
/*     */       } 
/*     */     } 
/*     */     
/* 840 */     return paramPolicyNodeImpl;
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
/*     */   private static PolicyNodeImpl removeInvalidNodes(PolicyNodeImpl paramPolicyNodeImpl, int paramInt, Set<String> paramSet, CertificatePoliciesExtension paramCertificatePoliciesExtension) throws CertPathValidatorException {
/* 860 */     List<PolicyInformation> list = null;
/*     */     try {
/* 862 */       list = paramCertificatePoliciesExtension.get("policies");
/* 863 */     } catch (IOException iOException) {
/* 864 */       throw new CertPathValidatorException("Exception while retrieving policyOIDs", iOException);
/*     */     } 
/*     */ 
/*     */     
/* 868 */     boolean bool = false;
/* 869 */     for (PolicyInformation policyInformation : list) {
/*     */       
/* 871 */       String str = policyInformation.getPolicyIdentifier().getIdentifier().toString();
/*     */       
/* 873 */       if (debug != null) {
/* 874 */         debug.println("PolicyChecker.processPolicies() processing policy second time: " + str);
/*     */       }
/*     */ 
/*     */       
/* 878 */       Set<PolicyNodeImpl> set = paramPolicyNodeImpl.getPolicyNodesValid(paramInt, str);
/* 879 */       for (PolicyNodeImpl policyNodeImpl1 : set) {
/* 880 */         PolicyNodeImpl policyNodeImpl2 = (PolicyNodeImpl)policyNodeImpl1.getParent();
/* 881 */         if (policyNodeImpl2.getValidPolicy().equals("2.5.29.32.0") && 
/* 882 */           !paramSet.contains(str) && 
/* 883 */           !str.equals("2.5.29.32.0")) {
/* 884 */           if (debug != null) {
/* 885 */             debug.println("PolicyChecker.processPolicies() before deleting: policy tree = " + paramPolicyNodeImpl);
/*     */           }
/* 887 */           policyNodeImpl2.deleteChild(policyNodeImpl1);
/* 888 */           bool = true;
/* 889 */           if (debug != null) {
/* 890 */             debug.println("PolicyChecker.processPolicies() after deleting: policy tree = " + paramPolicyNodeImpl);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 897 */     if (bool) {
/* 898 */       paramPolicyNodeImpl.prune(paramInt);
/* 899 */       if (!paramPolicyNodeImpl.getChildren().hasNext()) {
/* 900 */         paramPolicyNodeImpl = null;
/*     */       }
/*     */     } 
/*     */     
/* 904 */     return paramPolicyNodeImpl;
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
/*     */   PolicyNode getPolicyTree() {
/* 916 */     if (this.rootNode == null) {
/* 917 */       return null;
/*     */     }
/* 919 */     PolicyNodeImpl policyNodeImpl = this.rootNode.copyTree();
/* 920 */     policyNodeImpl.setImmutable();
/* 921 */     return policyNodeImpl;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/PolicyChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */