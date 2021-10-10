/*    */ package com.sun.org.apache.xml.internal.security.keys.storage.implementations;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverSpi;
/*    */ import java.security.cert.Certificate;
/*    */ import java.security.cert.X509Certificate;
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SingleCertificateResolver
/*    */   extends StorageResolverSpi
/*    */ {
/* 39 */   private X509Certificate certificate = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SingleCertificateResolver(X509Certificate paramX509Certificate) {
/* 45 */     this.certificate = paramX509Certificate;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<Certificate> getIterator() {
/* 50 */     return new InternalIterator(this.certificate);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static class InternalIterator
/*    */     implements Iterator<Certificate>
/*    */   {
/*    */     boolean alreadyReturned = false;
/*    */ 
/*    */     
/* 62 */     X509Certificate certificate = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public InternalIterator(X509Certificate param1X509Certificate) {
/* 70 */       this.certificate = param1X509Certificate;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean hasNext() {
/* 75 */       return !this.alreadyReturned;
/*    */     }
/*    */ 
/*    */     
/*    */     public Certificate next() {
/* 80 */       if (this.alreadyReturned) {
/* 81 */         throw new NoSuchElementException();
/*    */       }
/* 83 */       this.alreadyReturned = true;
/* 84 */       return this.certificate;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void remove() {
/* 91 */       throw new UnsupportedOperationException("Can't remove keys from KeyStore");
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/storage/implementations/SingleCertificateResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */