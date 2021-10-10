/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SubjectName;
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
/*     */ public class X509SubjectNameResolver
/*     */   extends KeyResolverSpi
/*     */ {
/*  44 */   private static Logger log = Logger.getLogger(X509SubjectNameResolver.class.getName());
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
/*  85 */     Element[] arrayOfElement = null;
/*  86 */     XMLX509SubjectName[] arrayOfXMLX509SubjectName = null;
/*     */     
/*  88 */     if (!XMLUtils.elementIsInSignatureSpace(paramElement, "X509Data")) {
/*  89 */       if (log.isLoggable(Level.FINE)) {
/*  90 */         log.log(Level.FINE, "I can't");
/*     */       }
/*  92 */       return null;
/*     */     } 
/*     */     
/*  95 */     arrayOfElement = XMLUtils.selectDsNodes(paramElement.getFirstChild(), "X509SubjectName");
/*     */     
/*  97 */     if (arrayOfElement == null || arrayOfElement.length <= 0) {
/*     */       
/*  99 */       if (log.isLoggable(Level.FINE)) {
/* 100 */         log.log(Level.FINE, "I can't");
/*     */       }
/* 102 */       return null;
/*     */     } 
/*     */     
/*     */     try {
/* 106 */       if (paramStorageResolver == null) {
/* 107 */         Object[] arrayOfObject = { "X509SubjectName" };
/* 108 */         KeyResolverException keyResolverException = new KeyResolverException("KeyResolver.needStorageResolver", arrayOfObject);
/*     */ 
/*     */         
/* 111 */         if (log.isLoggable(Level.FINE)) {
/* 112 */           log.log(Level.FINE, "", keyResolverException);
/*     */         }
/*     */         
/* 115 */         throw keyResolverException;
/*     */       } 
/*     */       
/* 118 */       arrayOfXMLX509SubjectName = new XMLX509SubjectName[arrayOfElement.length];
/*     */       
/* 120 */       for (byte b = 0; b < arrayOfElement.length; b++) {
/* 121 */         arrayOfXMLX509SubjectName[b] = new XMLX509SubjectName(arrayOfElement[b], paramString);
/*     */       }
/*     */       
/* 124 */       Iterator<Certificate> iterator = paramStorageResolver.getIterator();
/* 125 */       while (iterator.hasNext()) {
/* 126 */         X509Certificate x509Certificate = (X509Certificate)iterator.next();
/*     */         
/* 128 */         XMLX509SubjectName xMLX509SubjectName = new XMLX509SubjectName(paramElement.getOwnerDocument(), x509Certificate);
/*     */         
/* 130 */         if (log.isLoggable(Level.FINE)) {
/* 131 */           log.log(Level.FINE, "Found Certificate SN: " + xMLX509SubjectName.getSubjectName());
/*     */         }
/*     */         
/* 134 */         for (byte b1 = 0; b1 < arrayOfXMLX509SubjectName.length; b1++) {
/* 135 */           if (log.isLoggable(Level.FINE)) {
/* 136 */             log.log(Level.FINE, "Found Element SN:     " + arrayOfXMLX509SubjectName[b1]
/* 137 */                 .getSubjectName());
/*     */           }
/*     */           
/* 140 */           if (xMLX509SubjectName.equals(arrayOfXMLX509SubjectName[b1])) {
/* 141 */             if (log.isLoggable(Level.FINE)) {
/* 142 */               log.log(Level.FINE, "match !!! ");
/*     */             }
/*     */             
/* 145 */             return x509Certificate;
/*     */           } 
/* 147 */           if (log.isLoggable(Level.FINE)) {
/* 148 */             log.log(Level.FINE, "no match...");
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 153 */       return null;
/* 154 */     } catch (XMLSecurityException xMLSecurityException) {
/* 155 */       if (log.isLoggable(Level.FINE)) {
/* 156 */         log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
/*     */       }
/*     */       
/* 159 */       throw new KeyResolverException("generic.EmptyMessage", xMLSecurityException);
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
/*     */   
/*     */   public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 174 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/X509SubjectNameResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */