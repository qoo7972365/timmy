/*     */ package com.sun.org.apache.xml.internal.security.keys.storage;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.implementations.KeyStoreResolver;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.implementations.SingleCertificateResolver;
/*     */ import java.security.KeyStore;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StorageResolver
/*     */ {
/*  43 */   private static Logger log = Logger.getLogger(StorageResolver.class.getName());
/*     */ 
/*     */   
/*  46 */   private List<StorageResolverSpi> storageResolvers = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorageResolver() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorageResolver(StorageResolverSpi paramStorageResolverSpi) {
/*  60 */     add(paramStorageResolverSpi);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(StorageResolverSpi paramStorageResolverSpi) {
/*  69 */     if (this.storageResolvers == null) {
/*  70 */       this.storageResolvers = new ArrayList<>();
/*     */     }
/*  72 */     this.storageResolvers.add(paramStorageResolverSpi);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorageResolver(KeyStore paramKeyStore) {
/*  81 */     add(paramKeyStore);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(KeyStore paramKeyStore) {
/*     */     try {
/*  91 */       add(new KeyStoreResolver(paramKeyStore));
/*  92 */     } catch (StorageResolverException storageResolverException) {
/*  93 */       log.log(Level.SEVERE, "Could not add KeyStore because of: ", storageResolverException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorageResolver(X509Certificate paramX509Certificate) {
/* 103 */     add(paramX509Certificate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(X509Certificate paramX509Certificate) {
/* 112 */     add(new SingleCertificateResolver(paramX509Certificate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Certificate> getIterator() {
/* 120 */     return new StorageResolverIterator(this.storageResolvers.iterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class StorageResolverIterator
/*     */     implements Iterator<Certificate>
/*     */   {
/* 130 */     Iterator<StorageResolverSpi> resolvers = null;
/*     */ 
/*     */     
/* 133 */     Iterator<Certificate> currentResolver = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StorageResolverIterator(Iterator<StorageResolverSpi> param1Iterator) {
/* 141 */       this.resolvers = param1Iterator;
/* 142 */       this.currentResolver = findNextResolver();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 147 */       if (this.currentResolver == null) {
/* 148 */         return false;
/*     */       }
/*     */       
/* 151 */       if (this.currentResolver.hasNext()) {
/* 152 */         return true;
/*     */       }
/*     */       
/* 155 */       this.currentResolver = findNextResolver();
/* 156 */       return (this.currentResolver != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public Certificate next() {
/* 161 */       if (hasNext()) {
/* 162 */         return this.currentResolver.next();
/*     */       }
/*     */       
/* 165 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 172 */       throw new UnsupportedOperationException("Can't remove keys from KeyStore");
/*     */     }
/*     */ 
/*     */     
/*     */     private Iterator<Certificate> findNextResolver() {
/* 177 */       while (this.resolvers.hasNext()) {
/* 178 */         StorageResolverSpi storageResolverSpi = this.resolvers.next();
/* 179 */         Iterator<Certificate> iterator = storageResolverSpi.getIterator();
/* 180 */         if (iterator.hasNext()) {
/* 181 */           return iterator;
/*     */         }
/*     */       } 
/*     */       
/* 185 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/storage/StorageResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */