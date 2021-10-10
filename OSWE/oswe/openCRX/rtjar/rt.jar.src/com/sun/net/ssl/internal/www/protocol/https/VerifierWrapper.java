/*     */ package com.sun.net.ssl.internal.www.protocol.https;
/*     */ 
/*     */ import com.sun.net.ssl.HostnameVerifier;
/*     */ import java.io.IOException;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.SSLPeerUnverifiedException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.HostnameChecker;
/*     */ import sun.security.x509.X500Name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class VerifierWrapper
/*     */   implements HostnameVerifier
/*     */ {
/*     */   private HostnameVerifier verifier;
/*     */   
/*     */   VerifierWrapper(HostnameVerifier paramHostnameVerifier) {
/* 105 */     this.verifier = paramHostnameVerifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verify(String paramString, SSLSession paramSSLSession) {
/*     */     try {
/*     */       String str;
/* 118 */       if (paramSSLSession.getCipherSuite().startsWith("TLS_KRB5")) {
/*     */         
/* 120 */         str = HostnameChecker.getServerName(getPeerPrincipal(paramSSLSession));
/*     */       } else {
/*     */         
/* 123 */         Certificate[] arrayOfCertificate = paramSSLSession.getPeerCertificates();
/* 124 */         if (arrayOfCertificate == null || arrayOfCertificate.length == 0) {
/* 125 */           return false;
/*     */         }
/* 127 */         if (!(arrayOfCertificate[0] instanceof X509Certificate)) {
/* 128 */           return false;
/*     */         }
/* 130 */         X509Certificate x509Certificate = (X509Certificate)arrayOfCertificate[0];
/* 131 */         str = getServername(x509Certificate);
/*     */       } 
/* 133 */       if (str == null) {
/* 134 */         return false;
/*     */       }
/* 136 */       return this.verifier.verify(paramString, str);
/* 137 */     } catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
/* 138 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Principal getPeerPrincipal(SSLSession paramSSLSession) throws SSLPeerUnverifiedException {
/*     */     Principal principal;
/*     */     try {
/* 150 */       principal = paramSSLSession.getPeerPrincipal();
/* 151 */     } catch (AbstractMethodError abstractMethodError) {
/*     */ 
/*     */       
/* 154 */       principal = null;
/*     */     } 
/* 156 */     return principal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getServername(X509Certificate paramX509Certificate) {
/*     */     try {
/* 168 */       Collection<List<?>> collection = paramX509Certificate.getSubjectAlternativeNames();
/* 169 */       if (collection != null) {
/* 170 */         for (List<Integer> list : collection) {
/*     */           
/* 172 */           if (((Integer)list.get(0)).intValue() == 2)
/*     */           {
/* 174 */             return (String)list.get(1);
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 181 */       X500Name x500Name = HostnameChecker.getSubjectX500Name(paramX509Certificate);
/*     */ 
/*     */       
/* 184 */       DerValue derValue = x500Name.findMostSpecificAttribute(X500Name.commonName_oid);
/* 185 */       if (derValue != null) {
/*     */         try {
/* 187 */           return derValue.getAsString();
/*     */         }
/* 189 */         catch (IOException iOException) {}
/*     */       
/*     */       }
/*     */     }
/* 193 */     catch (CertificateException certificateException) {}
/*     */ 
/*     */     
/* 196 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/ssl/internal/www/protocol/https/VerifierWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */