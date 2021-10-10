/*    */ package sun.security.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.security.AccessController;
/*    */ import java.security.KeyStore;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.security.cert.X509Certificate;
/*    */ import java.util.Collections;
/*    */ import java.util.Enumeration;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import sun.security.x509.X509CertImpl;
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
/*    */ public class AnchorCertificates
/*    */ {
/* 47 */   private static final Debug debug = Debug.getInstance("certpath");
/*    */   private static final String HASH = "SHA-256";
/* 49 */   private static Set<String> certs = Collections.emptySet();
/*    */   
/*    */   static {
/* 52 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*    */         {
/*    */           public Void run() {
/* 55 */             File file = new File(System.getProperty("java.home"), "lib/security/cacerts");
/*    */ 
/*    */             
/*    */             try {
/* 59 */               KeyStore keyStore = KeyStore.getInstance("JKS");
/* 60 */               try (FileInputStream null = new FileInputStream(file)) {
/* 61 */                 keyStore.load(fileInputStream, null);
/* 62 */                 AnchorCertificates.certs = new HashSet();
/* 63 */                 Enumeration<String> enumeration = keyStore.aliases();
/*    */                 
/* 65 */                 while (enumeration.hasMoreElements()) {
/* 66 */                   String str = enumeration.nextElement();
/*    */                   
/* 68 */                   if (str.contains(" [jdk")) {
/*    */                     
/* 70 */                     X509Certificate x509Certificate = (X509Certificate)keyStore.getCertificate(str);
/* 71 */                     AnchorCertificates.certs.add(X509CertImpl.getFingerprint("SHA-256", x509Certificate));
/*    */                   } 
/*    */                 } 
/*    */               } 
/* 75 */             } catch (Exception exception) {
/* 76 */               if (AnchorCertificates.debug != null) {
/* 77 */                 AnchorCertificates.debug.println("Error parsing cacerts");
/* 78 */                 exception.printStackTrace();
/*    */               } 
/*    */             } 
/* 81 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean contains(X509Certificate paramX509Certificate) {
/* 93 */     String str = X509CertImpl.getFingerprint("SHA-256", paramX509Certificate);
/* 94 */     boolean bool = certs.contains(str);
/* 95 */     if (bool && debug != null) {
/* 96 */       debug.println("AnchorCertificate.contains: matched " + paramX509Certificate
/* 97 */           .getSubjectDN());
/*    */     }
/* 99 */     return bool;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/AnchorCertificates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */