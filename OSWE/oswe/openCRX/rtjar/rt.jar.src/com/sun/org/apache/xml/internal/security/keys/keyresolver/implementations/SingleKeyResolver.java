/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
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
/*     */ public class SingleKeyResolver
/*     */   extends KeyResolverSpi
/*     */ {
/*  25 */   private static Logger log = Logger.getLogger(SingleKeyResolver.class.getName());
/*     */ 
/*     */   
/*     */   private String keyName;
/*     */   
/*     */   private PublicKey publicKey;
/*     */   
/*     */   private PrivateKey privateKey;
/*     */   
/*     */   private SecretKey secretKey;
/*     */ 
/*     */   
/*     */   public SingleKeyResolver(String paramString, PublicKey paramPublicKey) {
/*  38 */     this.keyName = paramString;
/*  39 */     this.publicKey = paramPublicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SingleKeyResolver(String paramString, PrivateKey paramPrivateKey) {
/*  48 */     this.keyName = paramString;
/*  49 */     this.privateKey = paramPrivateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SingleKeyResolver(String paramString, SecretKey paramSecretKey) {
/*  58 */     this.keyName = paramString;
/*  59 */     this.secretKey = paramSecretKey;
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
/*  71 */     return XMLUtils.elementIsInSignatureSpace(paramElement, "KeyName");
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
/*  86 */     if (log.isLoggable(Level.FINE)) {
/*  87 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName() + "?");
/*     */     }
/*     */     
/*  90 */     if (this.publicKey != null && 
/*  91 */       XMLUtils.elementIsInSignatureSpace(paramElement, "KeyName")) {
/*  92 */       String str = paramElement.getFirstChild().getNodeValue();
/*  93 */       if (this.keyName.equals(str)) {
/*  94 */         return this.publicKey;
/*     */       }
/*     */     } 
/*     */     
/*  98 */     log.log(Level.FINE, "I can't");
/*  99 */     return null;
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
/* 113 */     return null;
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
/* 129 */     if (log.isLoggable(Level.FINE)) {
/* 130 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName() + "?");
/*     */     }
/*     */     
/* 133 */     if (this.secretKey != null && 
/* 134 */       XMLUtils.elementIsInSignatureSpace(paramElement, "KeyName")) {
/* 135 */       String str = paramElement.getFirstChild().getNodeValue();
/* 136 */       if (this.keyName.equals(str)) {
/* 137 */         return this.secretKey;
/*     */       }
/*     */     } 
/*     */     
/* 141 */     log.log(Level.FINE, "I can't");
/* 142 */     return null;
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
/* 157 */     if (log.isLoggable(Level.FINE)) {
/* 158 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName() + "?");
/*     */     }
/*     */     
/* 161 */     if (this.privateKey != null && 
/* 162 */       XMLUtils.elementIsInSignatureSpace(paramElement, "KeyName")) {
/* 163 */       String str = paramElement.getFirstChild().getNodeValue();
/* 164 */       if (this.keyName.equals(str)) {
/* 165 */         return this.privateKey;
/*     */       }
/*     */     } 
/*     */     
/* 169 */     log.log(Level.FINE, "I can't");
/* 170 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/SingleKeyResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */