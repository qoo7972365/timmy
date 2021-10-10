/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.security.Key;
/*     */ import java.security.KeyStore;
/*     */ import java.security.PrivateKey;
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
/*     */ public class SecretKeyResolver
/*     */   extends KeyResolverSpi
/*     */ {
/*  28 */   private static Logger log = Logger.getLogger(SecretKeyResolver.class.getName());
/*     */ 
/*     */   
/*     */   private KeyStore keyStore;
/*     */   
/*     */   private char[] password;
/*     */ 
/*     */   
/*     */   public SecretKeyResolver(KeyStore paramKeyStore, char[] paramArrayOfchar) {
/*  37 */     this.keyStore = paramKeyStore;
/*  38 */     this.password = paramArrayOfchar;
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
/*     */   public boolean engineCanResolve(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/*  50 */     return XMLUtils.elementIsInSignatureSpace(paramElement, "KeyName");
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
/*     */   public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  65 */     return null;
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
/*     */   public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  79 */     return null;
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
/*     */   public SecretKey engineResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  95 */     if (log.isLoggable(Level.FINE)) {
/*  96 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName() + "?");
/*     */     }
/*     */     
/*  99 */     if (XMLUtils.elementIsInSignatureSpace(paramElement, "KeyName")) {
/* 100 */       String str = paramElement.getFirstChild().getNodeValue();
/*     */       try {
/* 102 */         Key key = this.keyStore.getKey(str, this.password);
/* 103 */         if (key instanceof SecretKey) {
/* 104 */           return (SecretKey)key;
/*     */         }
/* 106 */       } catch (Exception exception) {
/* 107 */         log.log(Level.FINE, "Cannot recover the key", exception);
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     log.log(Level.FINE, "I can't");
/* 112 */     return null;
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
/*     */   public PrivateKey engineLookupAndResolvePrivateKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 127 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/SecretKeyResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */