/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509Certificate;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.SecretKey;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X509CertificateResolver
/*     */   extends KeyResolverSpi
/*     */ {
/*  47 */   private static Logger log = Logger.getLogger(X509CertificateResolver.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  63 */     X509Certificate x509Certificate = engineLookupResolveX509Certificate(paramElement, paramString, paramStorageResolver);
/*     */     
/*  65 */     if (x509Certificate != null) {
/*  66 */       return x509Certificate.getPublicKey();
/*     */     }
/*     */     
/*  69 */     return null;
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
/*     */   public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*     */     try {
/*  87 */       Element[] arrayOfElement = XMLUtils.selectDsNodes(paramElement.getFirstChild(), "X509Certificate");
/*  88 */       if (arrayOfElement == null || arrayOfElement.length == 0) {
/*     */         
/*  90 */         Element element = XMLUtils.selectDsNode(paramElement.getFirstChild(), "X509Data", 0);
/*  91 */         if (element != null) {
/*  92 */           return engineLookupResolveX509Certificate(element, paramString, paramStorageResolver);
/*     */         }
/*  94 */         return null;
/*     */       } 
/*     */ 
/*     */       
/*  98 */       for (byte b = 0; b < arrayOfElement.length; b++) {
/*  99 */         XMLX509Certificate xMLX509Certificate = new XMLX509Certificate(arrayOfElement[b], paramString);
/* 100 */         X509Certificate x509Certificate = xMLX509Certificate.getX509Certificate();
/* 101 */         if (x509Certificate != null) {
/* 102 */           return x509Certificate;
/*     */         }
/*     */       } 
/* 105 */       return null;
/* 106 */     } catch (XMLSecurityException xMLSecurityException) {
/* 107 */       if (log.isLoggable(Level.FINE)) {
/* 108 */         log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
/*     */       }
/* 110 */       throw new KeyResolverException("generic.EmptyMessage", xMLSecurityException);
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
/*     */   public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 124 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/X509CertificateResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */