/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509Digest;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
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
/*     */ public class X509DigestResolver
/*     */   extends KeyResolverSpi
/*     */ {
/*  35 */   private static Logger log = Logger.getLogger(X509DigestResolver.class.getName());
/*     */ 
/*     */   
/*     */   public boolean engineCanResolve(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/*  39 */     if (XMLUtils.elementIsInSignatureSpace(paramElement, "X509Data")) {
/*     */       try {
/*  41 */         X509Data x509Data = new X509Data(paramElement, paramString);
/*  42 */         return x509Data.containsDigest();
/*  43 */       } catch (XMLSecurityException xMLSecurityException) {
/*  44 */         return false;
/*     */       } 
/*     */     }
/*  47 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  55 */     X509Certificate x509Certificate = engineLookupResolveX509Certificate(paramElement, paramString, paramStorageResolver);
/*     */     
/*  57 */     if (x509Certificate != null) {
/*  58 */       return x509Certificate.getPublicKey();
/*     */     }
/*     */     
/*  61 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  68 */     if (log.isLoggable(Level.FINE)) {
/*  69 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName());
/*     */     }
/*     */     
/*  72 */     if (!engineCanResolve(paramElement, paramString, paramStorageResolver)) {
/*  73 */       return null;
/*     */     }
/*     */     
/*     */     try {
/*  77 */       return resolveCertificate(paramElement, paramString, paramStorageResolver);
/*  78 */     } catch (XMLSecurityException xMLSecurityException) {
/*  79 */       if (log.isLoggable(Level.FINE)) {
/*  80 */         log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
/*     */       }
/*     */ 
/*     */       
/*  84 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  90 */     return null;
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
/*     */   private X509Certificate resolveCertificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws XMLSecurityException {
/* 105 */     XMLX509Digest[] arrayOfXMLX509Digest = null;
/*     */     
/* 107 */     Element[] arrayOfElement = XMLUtils.selectDs11Nodes(paramElement.getFirstChild(), "X509Digest");
/*     */     
/* 109 */     if (arrayOfElement == null || arrayOfElement.length <= 0) {
/* 110 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 114 */       checkStorage(paramStorageResolver);
/*     */       
/* 116 */       arrayOfXMLX509Digest = new XMLX509Digest[arrayOfElement.length];
/*     */       
/* 118 */       for (byte b = 0; b < arrayOfElement.length; b++) {
/* 119 */         arrayOfXMLX509Digest[b] = new XMLX509Digest(arrayOfElement[b], paramString);
/*     */       }
/*     */       
/* 122 */       Iterator<Certificate> iterator = paramStorageResolver.getIterator();
/* 123 */       while (iterator.hasNext()) {
/* 124 */         X509Certificate x509Certificate = (X509Certificate)iterator.next();
/*     */         
/* 126 */         for (byte b1 = 0; b1 < arrayOfXMLX509Digest.length; b1++) {
/* 127 */           XMLX509Digest xMLX509Digest = arrayOfXMLX509Digest[b1];
/* 128 */           byte[] arrayOfByte = XMLX509Digest.getDigestBytesFromCert(x509Certificate, xMLX509Digest.getAlgorithm());
/*     */           
/* 130 */           if (Arrays.equals(xMLX509Digest.getDigestBytes(), arrayOfByte)) {
/* 131 */             if (log.isLoggable(Level.FINE)) {
/* 132 */               log.log(Level.FINE, "Found certificate with: " + x509Certificate.getSubjectX500Principal().getName());
/*     */             }
/* 134 */             return x509Certificate;
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } 
/* 140 */     } catch (XMLSecurityException xMLSecurityException) {
/* 141 */       throw new KeyResolverException("empty", xMLSecurityException);
/*     */     } 
/*     */     
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkStorage(StorageResolver paramStorageResolver) throws KeyResolverException {
/* 154 */     if (paramStorageResolver == null) {
/* 155 */       Object[] arrayOfObject = { "X509Digest" };
/* 156 */       KeyResolverException keyResolverException = new KeyResolverException("KeyResolver.needStorageResolver", arrayOfObject);
/* 157 */       if (log.isLoggable(Level.FINE)) {
/* 158 */         log.log(Level.FINE, "", keyResolverException);
/*     */       }
/* 160 */       throw keyResolverException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/X509DigestResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */