/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.DSAKeyValue;
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
/*     */ public class DSAKeyValueResolver
/*     */   extends KeyResolverSpi
/*     */ {
/*  40 */   private static Logger log = Logger.getLogger(DSAKeyValueResolver.class.getName());
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
/*     */   public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/*  54 */     if (paramElement == null) {
/*  55 */       return null;
/*     */     }
/*  57 */     Element element = null;
/*     */     
/*  59 */     boolean bool = XMLUtils.elementIsInSignatureSpace(paramElement, "KeyValue");
/*  60 */     if (bool) {
/*     */       
/*  62 */       element = XMLUtils.selectDsNode(paramElement.getFirstChild(), "DSAKeyValue", 0);
/*  63 */     } else if (XMLUtils.elementIsInSignatureSpace(paramElement, "DSAKeyValue")) {
/*     */ 
/*     */       
/*  66 */       element = paramElement;
/*     */     } 
/*     */     
/*  69 */     if (element == null) {
/*  70 */       return null;
/*     */     }
/*     */     
/*     */     try {
/*  74 */       DSAKeyValue dSAKeyValue = new DSAKeyValue(element, paramString);
/*  75 */       return dSAKeyValue.getPublicKey();
/*     */     
/*     */     }
/*  78 */     catch (XMLSecurityException xMLSecurityException) {
/*  79 */       if (log.isLoggable(Level.FINE)) {
/*  80 */         log.log(Level.FINE, xMLSecurityException.getMessage(), xMLSecurityException);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  85 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 100 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/DSAKeyValueResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */