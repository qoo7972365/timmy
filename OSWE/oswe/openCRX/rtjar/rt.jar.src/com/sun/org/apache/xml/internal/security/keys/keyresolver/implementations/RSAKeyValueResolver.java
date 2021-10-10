/*    */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*    */ import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.RSAKeyValue;
/*    */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*    */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*    */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*    */ import java.security.PublicKey;
/*    */ import java.security.cert.X509Certificate;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import javax.crypto.SecretKey;
/*    */ import org.w3c.dom.Element;
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
/*    */ public class RSAKeyValueResolver
/*    */   extends KeyResolverSpi
/*    */ {
/* 41 */   private static Logger log = Logger.getLogger(RSAKeyValueResolver.class.getName());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 48 */     if (log.isLoggable(Level.FINE)) {
/* 49 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName());
/*    */     }
/* 51 */     if (paramElement == null) {
/* 52 */       return null;
/*    */     }
/*    */     
/* 55 */     boolean bool = XMLUtils.elementIsInSignatureSpace(paramElement, "KeyValue");
/* 56 */     Element element = null;
/* 57 */     if (bool) {
/*    */       
/* 59 */       element = XMLUtils.selectDsNode(paramElement.getFirstChild(), "RSAKeyValue", 0);
/* 60 */     } else if (XMLUtils.elementIsInSignatureSpace(paramElement, "RSAKeyValue")) {
/*    */ 
/*    */       
/* 63 */       element = paramElement;
/*    */     } 
/*    */     
/* 66 */     if (element == null) {
/* 67 */       return null;
/*    */     }
/*    */     
/*    */     try {
/* 71 */       RSAKeyValue rSAKeyValue = new RSAKeyValue(element, paramString);
/*    */       
/* 73 */       return rSAKeyValue.getPublicKey();
/* 74 */     } catch (XMLSecurityException xMLSecurityException) {
/* 75 */       if (log.isLoggable(Level.FINE)) {
/* 76 */         log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
/*    */       }
/*    */ 
/*    */       
/* 80 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 87 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 94 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/RSAKeyValueResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */