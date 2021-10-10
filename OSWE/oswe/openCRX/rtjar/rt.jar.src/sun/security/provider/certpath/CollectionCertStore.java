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
/*     */ import java.util.Collection;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CollectionCertStore
/*     */   extends CertStoreSpi
/*     */ {
/*     */   private Collection<?> coll;
/*     */   
/*     */   public CollectionCertStore(CertStoreParameters paramCertStoreParameters) throws InvalidAlgorithmParameterException {
/*  97 */     super(paramCertStoreParameters);
/*  98 */     if (!(paramCertStoreParameters instanceof CollectionCertStoreParameters)) {
/*  99 */       throw new InvalidAlgorithmParameterException("parameters must be CollectionCertStoreParameters");
/*     */     }
/* 101 */     this.coll = ((CollectionCertStoreParameters)paramCertStoreParameters).getCollection();
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
/*     */   public Collection<Certificate> engineGetCertificates(CertSelector paramCertSelector) throws CertStoreException {
/* 119 */     if (this.coll == null) {
/* 120 */       throw new CertStoreException("Collection is null");
/*     */     }
/*     */     
/* 123 */     for (byte b = 0; b < 10; b++) {
/*     */       try {
/* 125 */         HashSet<Certificate> hashSet = new HashSet();
/* 126 */         if (paramCertSelector != null) {
/* 127 */           for (Object object : this.coll) {
/* 128 */             if (object instanceof Certificate && paramCertSelector
/* 129 */               .match((Certificate)object))
/* 130 */               hashSet.add((Certificate)object); 
/*     */           } 
/*     */         } else {
/* 133 */           for (Object object : this.coll) {
/* 134 */             if (object instanceof Certificate)
/* 135 */               hashSet.add((Certificate)object); 
/*     */           } 
/*     */         } 
/* 138 */         return hashSet;
/* 139 */       } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */     } 
/* 141 */     throw new ConcurrentModificationException("Too many ConcurrentModificationExceptions");
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
/* 161 */     if (this.coll == null) {
/* 162 */       throw new CertStoreException("Collection is null");
/*     */     }
/*     */     
/* 165 */     for (byte b = 0; b < 10; b++) {
/*     */       try {
/* 167 */         HashSet<CRL> hashSet = new HashSet();
/* 168 */         if (paramCRLSelector != null) {
/* 169 */           for (Object object : this.coll) {
/* 170 */             if (object instanceof CRL && paramCRLSelector.match((CRL)object))
/* 171 */               hashSet.add((CRL)object); 
/*     */           } 
/*     */         } else {
/* 174 */           for (Object object : this.coll) {
/* 175 */             if (object instanceof CRL)
/* 176 */               hashSet.add((CRL)object); 
/*     */           } 
/*     */         } 
/* 179 */         return hashSet;
/* 180 */       } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */     } 
/* 182 */     throw new ConcurrentModificationException("Too many ConcurrentModificationExceptions");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/CollectionCertStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */