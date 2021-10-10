/*    */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*    */ import com.sun.org.apache.xml.internal.security.keys.content.DEREncodedKeyValue;
/*    */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
/*    */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*    */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*    */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*    */ import java.security.PrivateKey;
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
/*    */ public class DEREncodedKeyValueResolver
/*    */   extends KeyResolverSpi
/*    */ {
/* 32 */   private static Logger log = Logger.getLogger(DEREncodedKeyValueResolver.class.getName());
/*    */ 
/*    */   
/*    */   public boolean engineCanResolve(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 36 */     return XMLUtils.elementIsInSignature11Space(paramElement, "DEREncodedKeyValue");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 43 */     if (log.isLoggable(Level.FINE)) {
/* 44 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName());
/*    */     }
/*    */     
/* 47 */     if (!engineCanResolve(paramElement, paramString, paramStorageResolver)) {
/* 48 */       return null;
/*    */     }
/*    */     
/*    */     try {
/* 52 */       DEREncodedKeyValue dEREncodedKeyValue = new DEREncodedKeyValue(paramElement, paramString);
/* 53 */       return dEREncodedKeyValue.getPublicKey();
/* 54 */     } catch (XMLSecurityException xMLSecurityException) {
/* 55 */       if (log.isLoggable(Level.FINE)) {
/* 56 */         log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
/*    */       }
/*    */ 
/*    */       
/* 60 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 66 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 72 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public PrivateKey engineLookupAndResolvePrivateKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 78 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/DEREncodedKeyValueResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */