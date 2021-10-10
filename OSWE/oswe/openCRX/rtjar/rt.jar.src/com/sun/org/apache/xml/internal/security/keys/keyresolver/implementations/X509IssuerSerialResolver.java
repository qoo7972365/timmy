/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509IssuerSerial;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
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
/*     */ public class X509IssuerSerialResolver
/*     */   extends KeyResolverSpi
/*     */ {
/*  44 */   private static Logger log = Logger.getLogger(X509IssuerSerialResolver.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  53 */     X509Certificate x509Certificate = engineLookupResolveX509Certificate(paramElement, paramString, paramStorageResolver);
/*     */     
/*  55 */     if (x509Certificate != null) {
/*  56 */       return x509Certificate.getPublicKey();
/*     */     }
/*     */     
/*  59 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  66 */     if (log.isLoggable(Level.FINE)) {
/*  67 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName() + "?");
/*     */     }
/*     */     
/*  70 */     X509Data x509Data = null;
/*     */     try {
/*  72 */       x509Data = new X509Data(paramElement, paramString);
/*  73 */     } catch (XMLSignatureException xMLSignatureException) {
/*  74 */       if (log.isLoggable(Level.FINE)) {
/*  75 */         log.log(Level.FINE, "I can't");
/*     */       }
/*  77 */       return null;
/*  78 */     } catch (XMLSecurityException xMLSecurityException) {
/*  79 */       if (log.isLoggable(Level.FINE)) {
/*  80 */         log.log(Level.FINE, "I can't");
/*     */       }
/*  82 */       return null;
/*     */     } 
/*     */     
/*  85 */     if (!x509Data.containsIssuerSerial()) {
/*  86 */       return null;
/*     */     }
/*     */     try {
/*  89 */       if (paramStorageResolver == null) {
/*  90 */         Object[] arrayOfObject = { "X509IssuerSerial" };
/*  91 */         KeyResolverException keyResolverException = new KeyResolverException("KeyResolver.needStorageResolver", arrayOfObject);
/*     */ 
/*     */         
/*  94 */         if (log.isLoggable(Level.FINE)) {
/*  95 */           log.log(Level.FINE, "", keyResolverException);
/*     */         }
/*  97 */         throw keyResolverException;
/*     */       } 
/*     */       
/* 100 */       int i = x509Data.lengthIssuerSerial();
/*     */       
/* 102 */       Iterator<Certificate> iterator = paramStorageResolver.getIterator();
/* 103 */       while (iterator.hasNext()) {
/* 104 */         X509Certificate x509Certificate = (X509Certificate)iterator.next();
/* 105 */         XMLX509IssuerSerial xMLX509IssuerSerial = new XMLX509IssuerSerial(paramElement.getOwnerDocument(), x509Certificate);
/*     */         
/* 107 */         if (log.isLoggable(Level.FINE)) {
/* 108 */           log.log(Level.FINE, "Found Certificate Issuer: " + xMLX509IssuerSerial.getIssuerName());
/* 109 */           log.log(Level.FINE, "Found Certificate Serial: " + xMLX509IssuerSerial.getSerialNumber().toString());
/*     */         } 
/*     */         
/* 112 */         for (byte b = 0; b < i; b++) {
/* 113 */           XMLX509IssuerSerial xMLX509IssuerSerial1 = x509Data.itemIssuerSerial(b);
/*     */           
/* 115 */           if (log.isLoggable(Level.FINE)) {
/* 116 */             log.log(Level.FINE, "Found Element Issuer:     " + xMLX509IssuerSerial1
/* 117 */                 .getIssuerName());
/* 118 */             log.log(Level.FINE, "Found Element Serial:     " + xMLX509IssuerSerial1
/* 119 */                 .getSerialNumber().toString());
/*     */           } 
/*     */           
/* 122 */           if (xMLX509IssuerSerial.equals(xMLX509IssuerSerial1)) {
/* 123 */             if (log.isLoggable(Level.FINE)) {
/* 124 */               log.log(Level.FINE, "match !!! ");
/*     */             }
/* 126 */             return x509Certificate;
/*     */           } 
/* 128 */           if (log.isLoggable(Level.FINE)) {
/* 129 */             log.log(Level.FINE, "no match...");
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 134 */       return null;
/* 135 */     } catch (XMLSecurityException xMLSecurityException) {
/* 136 */       if (log.isLoggable(Level.FINE)) {
/* 137 */         log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
/*     */       }
/*     */       
/* 140 */       throw new KeyResolverException("generic.EmptyMessage", xMLSecurityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 148 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/X509IssuerSerialResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */