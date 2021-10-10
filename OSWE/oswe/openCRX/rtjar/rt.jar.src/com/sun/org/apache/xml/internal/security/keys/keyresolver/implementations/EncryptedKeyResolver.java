/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.XMLEncryptionException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.security.Key;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptedKeyResolver
/*     */   extends KeyResolverSpi
/*     */ {
/*  58 */   private static Logger log = Logger.getLogger(EncryptedKeyResolver.class.getName());
/*     */ 
/*     */   
/*     */   private Key kek;
/*     */ 
/*     */   
/*     */   private String algorithm;
/*     */   
/*     */   private List<KeyResolverSpi> internalKeyResolvers;
/*     */ 
/*     */   
/*     */   public EncryptedKeyResolver(String paramString) {
/*  70 */     this.kek = null;
/*  71 */     this.algorithm = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedKeyResolver(String paramString, Key paramKey) {
/*  80 */     this.algorithm = paramString;
/*  81 */     this.kek = paramKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerInternalKeyResolver(KeyResolverSpi paramKeyResolverSpi) {
/*  91 */     if (this.internalKeyResolvers == null) {
/*  92 */       this.internalKeyResolvers = new ArrayList<>();
/*     */     }
/*  94 */     this.internalKeyResolvers.add(paramKeyResolverSpi);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/* 115 */     if (log.isLoggable(Level.FINE)) {
/* 116 */       log.log(Level.FINE, "EncryptedKeyResolver - Can I resolve " + paramElement.getTagName());
/*     */     }
/*     */     
/* 119 */     if (paramElement == null) {
/* 120 */       return null;
/*     */     }
/*     */     
/* 123 */     SecretKey secretKey = null;
/*     */     
/* 125 */     boolean bool = XMLUtils.elementIsInEncryptionSpace(paramElement, "EncryptedKey");
/* 126 */     if (bool) {
/* 127 */       if (log.isLoggable(Level.FINE)) {
/* 128 */         log.log(Level.FINE, "Passed an Encrypted Key");
/*     */       }
/*     */       try {
/* 131 */         XMLCipher xMLCipher = XMLCipher.getInstance();
/* 132 */         xMLCipher.init(4, this.kek);
/* 133 */         if (this.internalKeyResolvers != null) {
/* 134 */           int i = this.internalKeyResolvers.size();
/* 135 */           for (byte b = 0; b < i; b++) {
/* 136 */             xMLCipher.registerInternalKeyResolver(this.internalKeyResolvers.get(b));
/*     */           }
/*     */         } 
/* 139 */         EncryptedKey encryptedKey = xMLCipher.loadEncryptedKey(paramElement);
/* 140 */         secretKey = (SecretKey)xMLCipher.decryptKey(encryptedKey, this.algorithm);
/* 141 */       } catch (XMLEncryptionException xMLEncryptionException) {
/* 142 */         if (log.isLoggable(Level.FINE)) {
/* 143 */           log.log(Level.FINE, xMLEncryptionException.getMessage(), (Throwable)xMLEncryptionException);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     return secretKey;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/EncryptedKeyResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */