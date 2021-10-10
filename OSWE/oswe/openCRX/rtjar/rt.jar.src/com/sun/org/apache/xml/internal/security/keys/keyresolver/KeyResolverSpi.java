/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public abstract class KeyResolverSpi
/*     */ {
/*  48 */   protected Map<String, String> properties = null;
/*     */ 
/*     */   
/*     */   protected boolean globalResolver = false;
/*     */ 
/*     */   
/*     */   protected boolean secureValidation;
/*     */ 
/*     */   
/*     */   public void setSecureValidation(boolean paramBoolean) {
/*  58 */     this.secureValidation = paramBoolean;
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
/*  70 */     throw new UnsupportedOperationException();
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
/*     */   public PublicKey engineResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  86 */     throw new UnsupportedOperationException();
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
/*     */   public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 102 */     KeyResolverSpi keyResolverSpi = cloneIfNeeded();
/* 103 */     if (!keyResolverSpi.engineCanResolve(paramElement, paramString, paramStorageResolver)) {
/* 104 */       return null;
/*     */     }
/* 106 */     return keyResolverSpi.engineResolvePublicKey(paramElement, paramString, paramStorageResolver);
/*     */   }
/*     */   
/*     */   private KeyResolverSpi cloneIfNeeded() throws KeyResolverException {
/* 110 */     KeyResolverSpi keyResolverSpi = this;
/* 111 */     if (this.globalResolver) {
/*     */       try {
/* 113 */         keyResolverSpi = (KeyResolverSpi)getClass().newInstance();
/* 114 */       } catch (InstantiationException instantiationException) {
/* 115 */         throw new KeyResolverException("", instantiationException);
/* 116 */       } catch (IllegalAccessException illegalAccessException) {
/* 117 */         throw new KeyResolverException("", illegalAccessException);
/*     */       } 
/*     */     }
/* 120 */     return keyResolverSpi;
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
/*     */   public X509Certificate engineResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 136 */     throw new UnsupportedOperationException();
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
/*     */   public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 152 */     KeyResolverSpi keyResolverSpi = cloneIfNeeded();
/* 153 */     if (!keyResolverSpi.engineCanResolve(paramElement, paramString, paramStorageResolver)) {
/* 154 */       return null;
/*     */     }
/* 156 */     return keyResolverSpi.engineResolveX509Certificate(paramElement, paramString, paramStorageResolver);
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
/* 172 */     throw new UnsupportedOperationException();
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
/*     */   public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 188 */     KeyResolverSpi keyResolverSpi = cloneIfNeeded();
/* 189 */     if (!keyResolverSpi.engineCanResolve(paramElement, paramString, paramStorageResolver)) {
/* 190 */       return null;
/*     */     }
/* 192 */     return keyResolverSpi.engineResolveSecretKey(paramElement, paramString, paramStorageResolver);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrivateKey engineLookupAndResolvePrivateKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 213 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void engineSetProperty(String paramString1, String paramString2) {
/* 223 */     if (this.properties == null) {
/* 224 */       this.properties = new HashMap<>();
/*     */     }
/* 226 */     this.properties.put(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String engineGetProperty(String paramString) {
/* 236 */     if (this.properties == null) {
/* 237 */       return null;
/*     */     }
/*     */     
/* 240 */     return this.properties.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean understandsProperty(String paramString) {
/* 250 */     if (this.properties == null) {
/* 251 */       return false;
/*     */     }
/*     */     
/* 254 */     return (this.properties.get(paramString) != null);
/*     */   }
/*     */   
/*     */   public void setGlobalResolver(boolean paramBoolean) {
/* 258 */     this.globalResolver = paramBoolean;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/KeyResolverSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */