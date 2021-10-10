/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.cert.CRL;
/*     */ import java.security.cert.CRLSelector;
/*     */ import java.security.cert.CertSelector;
/*     */ import java.security.cert.CertStoreException;
/*     */ import java.security.cert.CertStoreParameters;
/*     */ import java.security.cert.CertStoreSpi;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CollectionCertStoreParameters;
/*     */ import java.security.cert.X509CRL;
/*     */ import java.security.cert.X509CRLSelector;
/*     */ import java.security.cert.X509CertSelector;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndexedCollectionCertStore
/*     */   extends CertStoreSpi
/*     */ {
/*     */   private Map<X500Principal, Object> certSubjects;
/*     */   private Map<X500Principal, Object> crlIssuers;
/*     */   private Set<Certificate> otherCertificates;
/*     */   private Set<CRL> otherCRLs;
/*     */   
/*     */   public IndexedCollectionCertStore(CertStoreParameters paramCertStoreParameters) throws InvalidAlgorithmParameterException {
/* 123 */     super(paramCertStoreParameters);
/* 124 */     if (!(paramCertStoreParameters instanceof CollectionCertStoreParameters)) {
/* 125 */       throw new InvalidAlgorithmParameterException("parameters must be CollectionCertStoreParameters");
/*     */     }
/*     */     
/* 128 */     Collection<?> collection = ((CollectionCertStoreParameters)paramCertStoreParameters).getCollection();
/* 129 */     if (collection == null) {
/* 130 */       throw new InvalidAlgorithmParameterException("Collection must not be null");
/*     */     }
/*     */     
/* 133 */     buildIndex(collection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildIndex(Collection<?> paramCollection) {
/* 141 */     this.certSubjects = new HashMap<>();
/* 142 */     this.crlIssuers = new HashMap<>();
/* 143 */     this.otherCertificates = null;
/* 144 */     this.otherCRLs = null;
/* 145 */     for (Object object : paramCollection) {
/* 146 */       if (object instanceof X509Certificate) {
/* 147 */         indexCertificate((X509Certificate)object); continue;
/* 148 */       }  if (object instanceof X509CRL) {
/* 149 */         indexCRL((X509CRL)object); continue;
/* 150 */       }  if (object instanceof Certificate) {
/* 151 */         if (this.otherCertificates == null) {
/* 152 */           this.otherCertificates = new HashSet<>();
/*     */         }
/* 154 */         this.otherCertificates.add((Certificate)object); continue;
/* 155 */       }  if (object instanceof CRL) {
/* 156 */         if (this.otherCRLs == null) {
/* 157 */           this.otherCRLs = new HashSet<>();
/*     */         }
/* 159 */         this.otherCRLs.add((CRL)object);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 164 */     if (this.otherCertificates == null) {
/* 165 */       this.otherCertificates = Collections.emptySet();
/*     */     }
/* 167 */     if (this.otherCRLs == null) {
/* 168 */       this.otherCRLs = Collections.emptySet();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void indexCertificate(X509Certificate paramX509Certificate) {
/* 176 */     X500Principal x500Principal = paramX509Certificate.getSubjectX500Principal();
/* 177 */     Object object = this.certSubjects.put(x500Principal, paramX509Certificate);
/* 178 */     if (object != null) {
/* 179 */       if (object instanceof X509Certificate) {
/* 180 */         if (paramX509Certificate.equals(object)) {
/*     */           return;
/*     */         }
/* 183 */         ArrayList<X509Certificate> arrayList = new ArrayList(2);
/* 184 */         arrayList.add(paramX509Certificate);
/* 185 */         arrayList.add((X509Certificate)object);
/* 186 */         this.certSubjects.put(x500Principal, arrayList);
/*     */       } else {
/*     */         
/* 189 */         List<X509Certificate> list = (List)object;
/* 190 */         if (!list.contains(paramX509Certificate)) {
/* 191 */           list.add(paramX509Certificate);
/*     */         }
/* 193 */         this.certSubjects.put(x500Principal, list);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void indexCRL(X509CRL paramX509CRL) {
/* 202 */     X500Principal x500Principal = paramX509CRL.getIssuerX500Principal();
/* 203 */     Object object = this.crlIssuers.put(x500Principal, paramX509CRL);
/* 204 */     if (object != null) {
/* 205 */       if (object instanceof X509CRL) {
/* 206 */         if (paramX509CRL.equals(object)) {
/*     */           return;
/*     */         }
/* 209 */         ArrayList<X509CRL> arrayList = new ArrayList(2);
/* 210 */         arrayList.add(paramX509CRL);
/* 211 */         arrayList.add((X509CRL)object);
/* 212 */         this.crlIssuers.put(x500Principal, arrayList);
/*     */       }
/*     */       else {
/*     */         
/* 216 */         List<X509CRL> list = (List)object;
/* 217 */         if (!list.contains(paramX509CRL)) {
/* 218 */           list.add(paramX509CRL);
/*     */         }
/* 220 */         this.crlIssuers.put(x500Principal, list);
/*     */       } 
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
/*     */   public Collection<? extends Certificate> engineGetCertificates(CertSelector paramCertSelector) throws CertStoreException {
/*     */     X500Principal x500Principal;
/* 242 */     if (paramCertSelector == null) {
/* 243 */       HashSet<Certificate> hashSet1 = new HashSet();
/* 244 */       matchX509Certs(new X509CertSelector(), hashSet1);
/* 245 */       hashSet1.addAll(this.otherCertificates);
/* 246 */       return hashSet1;
/*     */     } 
/*     */     
/* 249 */     if (!(paramCertSelector instanceof X509CertSelector)) {
/* 250 */       HashSet<Certificate> hashSet1 = new HashSet();
/* 251 */       matchX509Certs(paramCertSelector, hashSet1);
/* 252 */       for (Certificate certificate : this.otherCertificates) {
/* 253 */         if (paramCertSelector.match(certificate)) {
/* 254 */           hashSet1.add(certificate);
/*     */         }
/*     */       } 
/* 257 */       return hashSet1;
/*     */     } 
/*     */     
/* 260 */     if (this.certSubjects.isEmpty()) {
/* 261 */       return Collections.emptySet();
/*     */     }
/* 263 */     X509CertSelector x509CertSelector = (X509CertSelector)paramCertSelector;
/*     */ 
/*     */     
/* 266 */     X509Certificate x509Certificate = x509CertSelector.getCertificate();
/* 267 */     if (x509Certificate != null) {
/* 268 */       x500Principal = x509Certificate.getSubjectX500Principal();
/*     */     } else {
/* 270 */       x500Principal = x509CertSelector.getSubject();
/*     */     } 
/* 272 */     if (x500Principal != null) {
/*     */       
/* 274 */       Object object = this.certSubjects.get(x500Principal);
/* 275 */       if (object == null) {
/* 276 */         return Collections.emptySet();
/*     */       }
/* 278 */       if (object instanceof X509Certificate) {
/* 279 */         X509Certificate x509Certificate1 = (X509Certificate)object;
/* 280 */         if (x509CertSelector.match(x509Certificate1)) {
/* 281 */           return Collections.singleton(x509Certificate1);
/*     */         }
/* 283 */         return Collections.emptySet();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 288 */       List list = (List)object;
/* 289 */       HashSet<X509Certificate> hashSet1 = new HashSet(16);
/* 290 */       for (X509Certificate x509Certificate1 : list) {
/* 291 */         if (x509CertSelector.match(x509Certificate1)) {
/* 292 */           hashSet1.add(x509Certificate1);
/*     */         }
/*     */       } 
/* 295 */       return (Collection)hashSet1;
/*     */     } 
/*     */ 
/*     */     
/* 299 */     HashSet<Certificate> hashSet = new HashSet(16);
/* 300 */     matchX509Certs(x509CertSelector, hashSet);
/* 301 */     return hashSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void matchX509Certs(CertSelector paramCertSelector, Collection<Certificate> paramCollection) {
/* 311 */     for (X509Certificate x509Certificate : this.certSubjects.values()) {
/* 312 */       if (x509Certificate instanceof X509Certificate) {
/* 313 */         X509Certificate x509Certificate1 = x509Certificate;
/* 314 */         if (paramCertSelector.match(x509Certificate1)) {
/* 315 */           paramCollection.add(x509Certificate1);
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/* 320 */       List list = (List)x509Certificate;
/* 321 */       for (X509Certificate x509Certificate1 : list) {
/* 322 */         if (paramCertSelector.match(x509Certificate1)) {
/* 323 */           paramCollection.add(x509Certificate1);
/*     */         }
/*     */       } 
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
/*     */   public Collection<CRL> engineGetCRLs(CRLSelector paramCRLSelector) throws CertStoreException {
/* 346 */     if (paramCRLSelector == null) {
/* 347 */       HashSet<CRL> hashSet1 = new HashSet();
/* 348 */       matchX509CRLs(new X509CRLSelector(), hashSet1);
/* 349 */       hashSet1.addAll(this.otherCRLs);
/* 350 */       return hashSet1;
/*     */     } 
/*     */     
/* 353 */     if (!(paramCRLSelector instanceof X509CRLSelector)) {
/* 354 */       HashSet<CRL> hashSet1 = new HashSet();
/* 355 */       matchX509CRLs(paramCRLSelector, hashSet1);
/* 356 */       for (CRL cRL : this.otherCRLs) {
/* 357 */         if (paramCRLSelector.match(cRL)) {
/* 358 */           hashSet1.add(cRL);
/*     */         }
/*     */       } 
/* 361 */       return hashSet1;
/*     */     } 
/*     */     
/* 364 */     if (this.crlIssuers.isEmpty()) {
/* 365 */       return Collections.emptySet();
/*     */     }
/* 367 */     X509CRLSelector x509CRLSelector = (X509CRLSelector)paramCRLSelector;
/*     */     
/* 369 */     Collection<X500Principal> collection = x509CRLSelector.getIssuers();
/* 370 */     if (collection != null) {
/* 371 */       HashSet<X509CRL> hashSet1 = new HashSet(16);
/* 372 */       for (X500Principal x500Principal : collection) {
/* 373 */         Object object = this.crlIssuers.get(x500Principal);
/* 374 */         if (object == null)
/*     */           continue; 
/* 376 */         if (object instanceof X509CRL) {
/* 377 */           X509CRL x509CRL = (X509CRL)object;
/* 378 */           if (x509CRLSelector.match(x509CRL)) {
/* 379 */             hashSet1.add(x509CRL);
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/* 384 */         List list = (List)object;
/* 385 */         for (X509CRL x509CRL : list) {
/* 386 */           if (x509CRLSelector.match(x509CRL)) {
/* 387 */             hashSet1.add(x509CRL);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 392 */       return (Collection)hashSet1;
/*     */     } 
/*     */     
/* 395 */     HashSet<CRL> hashSet = new HashSet(16);
/* 396 */     matchX509CRLs(x509CRLSelector, hashSet);
/* 397 */     return hashSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void matchX509CRLs(CRLSelector paramCRLSelector, Collection<CRL> paramCollection) {
/* 405 */     for (X509CRL x509CRL : this.crlIssuers.values()) {
/* 406 */       if (x509CRL instanceof X509CRL) {
/* 407 */         X509CRL x509CRL1 = x509CRL;
/* 408 */         if (paramCRLSelector.match(x509CRL1)) {
/* 409 */           paramCollection.add(x509CRL1);
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/* 414 */       List list = (List)x509CRL;
/* 415 */       for (X509CRL x509CRL1 : list) {
/* 416 */         if (paramCRLSelector.match(x509CRL1))
/* 417 */           paramCollection.add(x509CRL1); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/IndexedCollectionCertStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */