/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SKI;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X509SKIResolver
/*     */   extends KeyResolverSpi
/*     */ {
/*  44 */   private static Logger log = Logger.getLogger(X509SKIResolver.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  61 */     X509Certificate x509Certificate = engineLookupResolveX509Certificate(paramElement, paramString, paramStorageResolver);
/*     */     
/*  63 */     if (x509Certificate != null) {
/*  64 */       return x509Certificate.getPublicKey();
/*     */     }
/*     */     
/*  67 */     return null;
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
/*     */   public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  82 */     if (log.isLoggable(Level.FINE)) {
/*  83 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName() + "?");
/*     */     }
/*  85 */     if (!XMLUtils.elementIsInSignatureSpace(paramElement, "X509Data")) {
/*  86 */       if (log.isLoggable(Level.FINE)) {
/*  87 */         log.log(Level.FINE, "I can't");
/*     */       }
/*  89 */       return null;
/*     */     } 
/*     */     
/*  92 */     XMLX509SKI[] arrayOfXMLX509SKI = null;
/*     */     
/*  94 */     Element[] arrayOfElement = null;
/*  95 */     arrayOfElement = XMLUtils.selectDsNodes(paramElement.getFirstChild(), "X509SKI");
/*     */     
/*  97 */     if (arrayOfElement == null || arrayOfElement.length <= 0) {
/*  98 */       if (log.isLoggable(Level.FINE)) {
/*  99 */         log.log(Level.FINE, "I can't");
/*     */       }
/* 101 */       return null;
/*     */     } 
/*     */     try {
/* 104 */       if (paramStorageResolver == null) {
/* 105 */         Object[] arrayOfObject = { "X509SKI" };
/* 106 */         KeyResolverException keyResolverException = new KeyResolverException("KeyResolver.needStorageResolver", arrayOfObject);
/*     */ 
/*     */         
/* 109 */         if (log.isLoggable(Level.FINE)) {
/* 110 */           log.log(Level.FINE, "", keyResolverException);
/*     */         }
/*     */         
/* 113 */         throw keyResolverException;
/*     */       } 
/*     */       
/* 116 */       arrayOfXMLX509SKI = new XMLX509SKI[arrayOfElement.length];
/*     */       
/* 118 */       for (byte b = 0; b < arrayOfElement.length; b++) {
/* 119 */         arrayOfXMLX509SKI[b] = new XMLX509SKI(arrayOfElement[b], paramString);
/*     */       }
/*     */       
/* 122 */       Iterator<Certificate> iterator = paramStorageResolver.getIterator();
/* 123 */       while (iterator.hasNext()) {
/* 124 */         X509Certificate x509Certificate = (X509Certificate)iterator.next();
/* 125 */         XMLX509SKI xMLX509SKI = new XMLX509SKI(paramElement.getOwnerDocument(), x509Certificate);
/*     */         
/* 127 */         for (byte b1 = 0; b1 < arrayOfXMLX509SKI.length; b1++) {
/* 128 */           if (xMLX509SKI.equals(arrayOfXMLX509SKI[b1])) {
/* 129 */             if (log.isLoggable(Level.FINE)) {
/* 130 */               log.log(Level.FINE, "Return PublicKey from " + x509Certificate.getSubjectX500Principal().getName());
/*     */             }
/*     */             
/* 133 */             return x509Certificate;
/*     */           } 
/*     */         } 
/*     */       } 
/* 137 */     } catch (XMLSecurityException xMLSecurityException) {
/* 138 */       throw new KeyResolverException("empty", xMLSecurityException);
/*     */     } 
/*     */     
/* 141 */     return null;
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
/*     */   public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 155 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/X509SKIResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */