/*     */ package com.sun.org.apache.xml.internal.security.keys.storage.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverSpi;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyStoreResolver
/*     */   extends StorageResolverSpi
/*     */ {
/*  42 */   private KeyStore keyStore = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyStoreResolver(KeyStore paramKeyStore) throws StorageResolverException {
/*  51 */     this.keyStore = paramKeyStore;
/*     */     
/*     */     try {
/*  54 */       paramKeyStore.aliases();
/*  55 */     } catch (KeyStoreException keyStoreException) {
/*  56 */       throw new StorageResolverException("generic.EmptyMessage", keyStoreException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Certificate> getIterator() {
/*  62 */     return new KeyStoreIterator(this.keyStore);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class KeyStoreIterator
/*     */     implements Iterator<Certificate>
/*     */   {
/*  71 */     KeyStore keyStore = null;
/*     */ 
/*     */     
/*  74 */     Enumeration<String> aliases = null;
/*     */ 
/*     */     
/*  77 */     Certificate nextCert = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyStoreIterator(KeyStore param1KeyStore) {
/*     */       try {
/*  86 */         this.keyStore = param1KeyStore;
/*  87 */         this.aliases = this.keyStore.aliases();
/*  88 */       } catch (KeyStoreException keyStoreException) {
/*     */         
/*  90 */         this.aliases = new Enumeration<String>() {
/*     */             public boolean hasMoreElements() {
/*  92 */               return false;
/*     */             }
/*     */             public String nextElement() {
/*  95 */               return null;
/*     */             }
/*     */           };
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 103 */       if (this.nextCert == null) {
/* 104 */         this.nextCert = findNextCert();
/*     */       }
/*     */       
/* 107 */       return (this.nextCert != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public Certificate next() {
/* 112 */       if (this.nextCert == null) {
/*     */         
/* 114 */         this.nextCert = findNextCert();
/*     */         
/* 116 */         if (this.nextCert == null) {
/* 117 */           throw new NoSuchElementException();
/*     */         }
/*     */       } 
/*     */       
/* 121 */       Certificate certificate = this.nextCert;
/* 122 */       this.nextCert = null;
/* 123 */       return certificate;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 130 */       throw new UnsupportedOperationException("Can't remove keys from KeyStore");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Certificate findNextCert() {
/* 136 */       while (this.aliases.hasMoreElements()) {
/* 137 */         String str = this.aliases.nextElement();
/*     */         try {
/* 139 */           Certificate certificate = this.keyStore.getCertificate(str);
/* 140 */           if (certificate != null) {
/* 141 */             return certificate;
/*     */           }
/* 143 */         } catch (KeyStoreException keyStoreException) {
/* 144 */           return null;
/*     */         } 
/*     */       } 
/*     */       
/* 148 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/storage/implementations/KeyStoreResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */