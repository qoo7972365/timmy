/*    */ package sun.security.validator;
/*    */ 
/*    */ import java.security.KeyStore;
/*    */ import java.security.KeyStoreException;
/*    */ import java.security.cert.Certificate;
/*    */ import java.security.cert.X509Certificate;
/*    */ import java.util.Collections;
/*    */ import java.util.Enumeration;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
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
/*    */ public final class TrustStoreUtil
/*    */ {
/*    */   public static Set<X509Certificate> getTrustedCerts(KeyStore paramKeyStore) {
/* 54 */     HashSet<X509Certificate> hashSet = new HashSet();
/*    */     try {
/* 56 */       for (Enumeration<String> enumeration = paramKeyStore.aliases(); enumeration.hasMoreElements(); ) {
/* 57 */         String str = enumeration.nextElement();
/* 58 */         if (paramKeyStore.isCertificateEntry(str)) {
/* 59 */           Certificate certificate = paramKeyStore.getCertificate(str);
/* 60 */           if (certificate instanceof X509Certificate)
/* 61 */             hashSet.add((X509Certificate)certificate);  continue;
/*    */         } 
/* 63 */         if (paramKeyStore.isKeyEntry(str)) {
/* 64 */           Certificate[] arrayOfCertificate = paramKeyStore.getCertificateChain(str);
/* 65 */           if (arrayOfCertificate != null && arrayOfCertificate.length > 0 && arrayOfCertificate[0] instanceof X509Certificate)
/*    */           {
/* 67 */             hashSet.add((X509Certificate)arrayOfCertificate[0]);
/*    */           }
/*    */         } 
/*    */       } 
/* 71 */     } catch (KeyStoreException keyStoreException) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 77 */     return Collections.unmodifiableSet(hashSet);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/validator/TrustStoreUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */