/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.cert.CertStore;
/*     */ import java.security.cert.CertStoreException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509CertSelector;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.x509.GeneralNameInterface;
/*     */ import sun.security.x509.GeneralNames;
/*     */ import sun.security.x509.GeneralSubtrees;
/*     */ import sun.security.x509.NameConstraintsExtension;
/*     */ import sun.security.x509.SubjectAlternativeNameExtension;
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
/*     */ public abstract class Builder
/*     */ {
/*  56 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */   
/*     */   private Set<String> matchingPolicies;
/*     */ 
/*     */   
/*     */   final PKIX.BuilderParams buildParams;
/*     */ 
/*     */   
/*     */   final X509CertSelector targetCertConstraints;
/*     */   
/*  67 */   static final boolean USE_AIA = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("com.sun.security.enableAIAcaIssuers"))).booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Builder(PKIX.BuilderParams paramBuilderParams) {
/*  75 */     this.buildParams = paramBuilderParams;
/*  76 */     this
/*  77 */       .targetCertConstraints = (X509CertSelector)paramBuilderParams.targetCertConstraints();
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
/*     */   abstract Collection<X509Certificate> getMatchingCerts(State paramState, List<CertStore> paramList) throws CertStoreException, CertificateException, IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void verifyCert(X509Certificate paramX509Certificate, State paramState, List<X509Certificate> paramList) throws GeneralSecurityException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract boolean isPathCompleted(X509Certificate paramX509Certificate);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void addCertToPath(X509Certificate paramX509Certificate, LinkedList<X509Certificate> paramLinkedList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void removeFinalCertFromPath(LinkedList<X509Certificate> paramLinkedList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int distance(GeneralNameInterface paramGeneralNameInterface1, GeneralNameInterface paramGeneralNameInterface2, int paramInt) {
/* 146 */     switch (paramGeneralNameInterface1.constrains(paramGeneralNameInterface2)) {
/*     */       case -1:
/* 148 */         if (debug != null) {
/* 149 */           debug.println("Builder.distance(): Names are different types");
/*     */         }
/* 151 */         return paramInt;
/*     */       case 3:
/* 153 */         if (debug != null) {
/* 154 */           debug.println("Builder.distance(): Names are same type but in different subtrees");
/*     */         }
/*     */         
/* 157 */         return paramInt;
/*     */       case 0:
/* 159 */         return 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/*     */       case 1:
/* 169 */         return paramGeneralNameInterface2.subtreeDepth() - paramGeneralNameInterface1.subtreeDepth();
/*     */     } 
/*     */     return paramInt;
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
/*     */   static int hops(GeneralNameInterface paramGeneralNameInterface1, GeneralNameInterface paramGeneralNameInterface2, int paramInt) {
/* 192 */     int i = paramGeneralNameInterface1.constrains(paramGeneralNameInterface2);
/* 193 */     switch (i) {
/*     */       case -1:
/* 195 */         if (debug != null) {
/* 196 */           debug.println("Builder.hops(): Names are different types");
/*     */         }
/* 198 */         return paramInt;
/*     */       
/*     */       case 3:
/*     */         break;
/*     */       
/*     */       case 0:
/* 204 */         return 0;
/*     */       
/*     */       case 2:
/* 207 */         return paramGeneralNameInterface2.subtreeDepth() - paramGeneralNameInterface1.subtreeDepth();
/*     */       
/*     */       case 1:
/* 210 */         return paramGeneralNameInterface2.subtreeDepth() - paramGeneralNameInterface1.subtreeDepth();
/*     */       default:
/* 212 */         return paramInt;
/*     */     } 
/*     */ 
/*     */     
/* 216 */     if (paramGeneralNameInterface1.getType() != 4) {
/* 217 */       if (debug != null) {
/* 218 */         debug.println("Builder.hops(): hopDistance not implemented for this name type");
/*     */       }
/*     */       
/* 221 */       return paramInt;
/*     */     } 
/* 223 */     X500Name x500Name1 = (X500Name)paramGeneralNameInterface1;
/* 224 */     X500Name x500Name2 = (X500Name)paramGeneralNameInterface2;
/* 225 */     X500Name x500Name3 = x500Name1.commonAncestor(x500Name2);
/* 226 */     if (x500Name3 == null) {
/* 227 */       if (debug != null) {
/* 228 */         debug.println("Builder.hops(): Names are in different namespaces");
/*     */       }
/*     */       
/* 231 */       return paramInt;
/*     */     } 
/* 233 */     int j = x500Name3.subtreeDepth();
/* 234 */     int k = x500Name1.subtreeDepth();
/* 235 */     int m = x500Name2.subtreeDepth();
/* 236 */     return k + m - 2 * j;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int targetDistance(NameConstraintsExtension paramNameConstraintsExtension, X509Certificate paramX509Certificate, GeneralNameInterface paramGeneralNameInterface) throws IOException {
/*     */     X509CertImpl x509CertImpl;
/* 285 */     if (paramNameConstraintsExtension != null && !paramNameConstraintsExtension.verify(paramX509Certificate)) {
/* 286 */       throw new IOException("certificate does not satisfy existing name constraints");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 292 */       x509CertImpl = X509CertImpl.toImpl(paramX509Certificate);
/* 293 */     } catch (CertificateException certificateException) {
/* 294 */       throw new IOException("Invalid certificate", certificateException);
/*     */     } 
/*     */     
/* 297 */     X500Name x500Name = X500Name.asX500Name(x509CertImpl.getSubjectX500Principal());
/* 298 */     if (x500Name.equals(paramGeneralNameInterface))
/*     */     {
/* 300 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 304 */     SubjectAlternativeNameExtension subjectAlternativeNameExtension = x509CertImpl.getSubjectAlternativeNameExtension();
/* 305 */     if (subjectAlternativeNameExtension != null) {
/* 306 */       GeneralNames generalNames = subjectAlternativeNameExtension.get("subject_name");
/*     */ 
/*     */       
/* 309 */       if (generalNames != null) {
/* 310 */         byte b1; int j; for (b1 = 0, j = generalNames.size(); b1 < j; b1++) {
/* 311 */           GeneralNameInterface generalNameInterface = generalNames.get(b1).getName();
/* 312 */           if (generalNameInterface.equals(paramGeneralNameInterface)) {
/* 313 */             return 0;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 323 */     NameConstraintsExtension nameConstraintsExtension = x509CertImpl.getNameConstraintsExtension();
/* 324 */     if (nameConstraintsExtension == null) {
/* 325 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 329 */     if (paramNameConstraintsExtension != null) {
/* 330 */       paramNameConstraintsExtension.merge(nameConstraintsExtension);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 335 */       paramNameConstraintsExtension = (NameConstraintsExtension)nameConstraintsExtension.clone();
/*     */     } 
/*     */     
/* 338 */     if (debug != null) {
/* 339 */       debug.println("Builder.targetDistance() merged constraints: " + 
/* 340 */           String.valueOf(paramNameConstraintsExtension));
/*     */     }
/*     */ 
/*     */     
/* 344 */     GeneralSubtrees generalSubtrees1 = paramNameConstraintsExtension.get("permitted_subtrees");
/*     */     
/* 346 */     GeneralSubtrees generalSubtrees2 = paramNameConstraintsExtension.get("excluded_subtrees");
/* 347 */     if (generalSubtrees1 != null) {
/* 348 */       generalSubtrees1.reduce(generalSubtrees2);
/*     */     }
/* 350 */     if (debug != null) {
/* 351 */       debug.println("Builder.targetDistance() reduced constraints: " + generalSubtrees1);
/*     */     }
/*     */ 
/*     */     
/* 355 */     if (!paramNameConstraintsExtension.verify(paramGeneralNameInterface)) {
/* 356 */       throw new IOException("New certificate not allowed to sign certificate for target");
/*     */     }
/*     */ 
/*     */     
/* 360 */     if (generalSubtrees1 == null)
/*     */     {
/* 362 */       return -1; }  byte b;
/*     */     int i;
/* 364 */     for (b = 0, i = generalSubtrees1.size(); b < i; b++) {
/* 365 */       GeneralNameInterface generalNameInterface = generalSubtrees1.get(b).getName().getName();
/* 366 */       int j = distance(generalNameInterface, paramGeneralNameInterface, -1);
/* 367 */       if (j >= 0) {
/* 368 */         return j + 1;
/*     */       }
/*     */     } 
/*     */     
/* 372 */     return -1;
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
/*     */   Set<String> getMatchingPolicies() {
/* 396 */     if (this.matchingPolicies != null) {
/* 397 */       Set<String> set = this.buildParams.initialPolicies();
/* 398 */       if (!set.isEmpty() && 
/* 399 */         !set.contains("2.5.29.32.0") && this.buildParams
/* 400 */         .policyMappingInhibited()) {
/*     */         
/* 402 */         this.matchingPolicies = new HashSet<>(set);
/* 403 */         this.matchingPolicies.add("2.5.29.32.0");
/*     */       }
/*     */       else {
/*     */         
/* 407 */         this.matchingPolicies = Collections.emptySet();
/*     */       } 
/*     */     } 
/* 410 */     return this.matchingPolicies;
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
/*     */   boolean addMatchingCerts(X509CertSelector paramX509CertSelector, Collection<CertStore> paramCollection, Collection<X509Certificate> paramCollection1, boolean paramBoolean) {
/* 432 */     X509Certificate x509Certificate = paramX509CertSelector.getCertificate();
/* 433 */     if (x509Certificate != null) {
/*     */       
/* 435 */       if (paramX509CertSelector.match(x509Certificate) && 
/* 436 */         !X509CertImpl.isSelfSigned(x509Certificate, this.buildParams.sigProvider())) {
/* 437 */         if (debug != null) {
/* 438 */           debug.println("Builder.addMatchingCerts: adding target cert\n  SN: " + 
/*     */               
/* 440 */               Debug.toHexString(x509Certificate
/* 441 */                 .getSerialNumber()) + "\n  Subject: " + x509Certificate
/* 442 */               .getSubjectX500Principal() + "\n  Issuer: " + x509Certificate
/* 443 */               .getIssuerX500Principal());
/*     */         }
/* 445 */         return paramCollection1.add(x509Certificate);
/*     */       } 
/* 447 */       return false;
/*     */     } 
/* 449 */     boolean bool = false;
/* 450 */     for (CertStore certStore : paramCollection) {
/*     */       
/*     */       try {
/* 453 */         Collection<? extends Certificate> collection = certStore.getCertificates(paramX509CertSelector);
/* 454 */         for (Certificate certificate : collection) {
/*     */           
/* 456 */           if (!X509CertImpl.isSelfSigned((X509Certificate)certificate, this.buildParams.sigProvider()) && 
/* 457 */             paramCollection1.add((X509Certificate)certificate)) {
/* 458 */             bool = true;
/*     */           }
/*     */         } 
/*     */         
/* 462 */         if (!paramBoolean && bool) {
/* 463 */           return true;
/*     */         }
/* 465 */       } catch (CertStoreException certStoreException) {
/*     */ 
/*     */         
/* 468 */         if (debug != null) {
/* 469 */           debug.println("Builder.addMatchingCerts, non-fatal exception retrieving certs: " + certStoreException);
/*     */           
/* 471 */           certStoreException.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/* 475 */     return bool;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/Builder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */