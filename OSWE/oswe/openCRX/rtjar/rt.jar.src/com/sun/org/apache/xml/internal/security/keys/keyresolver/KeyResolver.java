/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.DEREncodedKeyValueResolver;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.DSAKeyValueResolver;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.KeyInfoReferenceResolver;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.RSAKeyValueResolver;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.RetrievalMethodResolver;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.X509CertificateResolver;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.X509DigestResolver;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.X509IssuerSerialResolver;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.X509SKIResolver;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.X509SubjectNameResolver;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*     */ import com.sun.org.apache.xml.internal.security.utils.JavaUtils;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
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
/*     */ public class KeyResolver
/*     */ {
/*  57 */   private static Logger log = Logger.getLogger(KeyResolver.class.getName());
/*     */ 
/*     */   
/*  60 */   private static List<KeyResolver> resolverVector = new CopyOnWriteArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final KeyResolverSpi resolverSpi;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private KeyResolver(KeyResolverSpi paramKeyResolverSpi) {
/*  71 */     this.resolverSpi = paramKeyResolverSpi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int length() {
/*  80 */     return resolverVector.size();
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
/*     */   public static final X509Certificate getX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  96 */     for (KeyResolver keyResolver : resolverVector) {
/*  97 */       if (keyResolver == null) {
/*     */ 
/*     */ 
/*     */         
/* 101 */         Object[] arrayOfObject1 = { (paramElement != null && paramElement.getNodeType() == 1) ? paramElement.getTagName() : "null" };
/*     */ 
/*     */         
/* 104 */         throw new KeyResolverException("utils.resolver.noClass", arrayOfObject1);
/*     */       } 
/* 106 */       if (log.isLoggable(Level.FINE)) {
/* 107 */         log.log(Level.FINE, "check resolvability by class " + keyResolver.getClass());
/*     */       }
/*     */       
/* 110 */       X509Certificate x509Certificate = keyResolver.resolveX509Certificate(paramElement, paramString, paramStorageResolver);
/* 111 */       if (x509Certificate != null) {
/* 112 */         return x509Certificate;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 118 */     Object[] arrayOfObject = { (paramElement != null && paramElement.getNodeType() == 1) ? paramElement.getTagName() : "null" };
/*     */ 
/*     */     
/* 121 */     throw new KeyResolverException("utils.resolver.noClass", arrayOfObject);
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
/*     */   public static final PublicKey getPublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 137 */     for (KeyResolver keyResolver : resolverVector) {
/* 138 */       if (keyResolver == null) {
/*     */ 
/*     */ 
/*     */         
/* 142 */         Object[] arrayOfObject1 = { (paramElement != null && paramElement.getNodeType() == 1) ? paramElement.getTagName() : "null" };
/*     */ 
/*     */         
/* 145 */         throw new KeyResolverException("utils.resolver.noClass", arrayOfObject1);
/*     */       } 
/* 147 */       if (log.isLoggable(Level.FINE)) {
/* 148 */         log.log(Level.FINE, "check resolvability by class " + keyResolver.getClass());
/*     */       }
/*     */       
/* 151 */       PublicKey publicKey = keyResolver.resolvePublicKey(paramElement, paramString, paramStorageResolver);
/* 152 */       if (publicKey != null) {
/* 153 */         return publicKey;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 159 */     Object[] arrayOfObject = { (paramElement != null && paramElement.getNodeType() == 1) ? paramElement.getTagName() : "null" };
/*     */ 
/*     */     
/* 162 */     throw new KeyResolverException("utils.resolver.noClass", arrayOfObject);
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
/*     */   
/*     */   public static void register(String paramString, boolean paramBoolean) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
/* 184 */     JavaUtils.checkRegisterPermission();
/*     */     
/* 186 */     KeyResolverSpi keyResolverSpi = (KeyResolverSpi)Class.forName(paramString).newInstance();
/* 187 */     keyResolverSpi.setGlobalResolver(paramBoolean);
/* 188 */     register(keyResolverSpi, false);
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
/*     */   public static void registerAtStart(String paramString, boolean paramBoolean) {
/*     */     InstantiationException instantiationException;
/* 206 */     JavaUtils.checkRegisterPermission();
/* 207 */     KeyResolverSpi keyResolverSpi = null;
/* 208 */     ClassNotFoundException classNotFoundException = null;
/*     */     try {
/* 210 */       keyResolverSpi = (KeyResolverSpi)Class.forName(paramString).newInstance();
/* 211 */     } catch (ClassNotFoundException classNotFoundException1) {
/* 212 */       classNotFoundException = classNotFoundException1;
/* 213 */     } catch (IllegalAccessException illegalAccessException2) {
/* 214 */       IllegalAccessException illegalAccessException1 = illegalAccessException2;
/* 215 */     } catch (InstantiationException instantiationException1) {
/* 216 */       instantiationException = instantiationException1;
/*     */     } 
/*     */     
/* 219 */     if (instantiationException != null) {
/* 220 */       throw (IllegalArgumentException)(new IllegalArgumentException("Invalid KeyResolver class name"))
/* 221 */         .initCause(instantiationException);
/*     */     }
/* 223 */     keyResolverSpi.setGlobalResolver(paramBoolean);
/* 224 */     register(keyResolverSpi, true);
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
/*     */   public static void register(KeyResolverSpi paramKeyResolverSpi, boolean paramBoolean) {
/* 245 */     JavaUtils.checkRegisterPermission();
/* 246 */     KeyResolver keyResolver = new KeyResolver(paramKeyResolverSpi);
/* 247 */     if (paramBoolean) {
/* 248 */       resolverVector.add(0, keyResolver);
/*     */     } else {
/* 250 */       resolverVector.add(keyResolver);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerClassNames(List<String> paramList) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
/* 272 */     JavaUtils.checkRegisterPermission();
/* 273 */     ArrayList<KeyResolver> arrayList = new ArrayList(paramList.size());
/* 274 */     for (String str : paramList) {
/*     */       
/* 276 */       KeyResolverSpi keyResolverSpi = (KeyResolverSpi)Class.forName(str).newInstance();
/* 277 */       keyResolverSpi.setGlobalResolver(false);
/* 278 */       arrayList.add(new KeyResolver(keyResolverSpi));
/*     */     } 
/* 280 */     resolverVector.addAll(arrayList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerDefaultResolvers() {
/* 288 */     ArrayList<KeyResolver> arrayList = new ArrayList();
/* 289 */     arrayList.add(new KeyResolver(new RSAKeyValueResolver()));
/* 290 */     arrayList.add(new KeyResolver(new DSAKeyValueResolver()));
/* 291 */     arrayList.add(new KeyResolver(new X509CertificateResolver()));
/* 292 */     arrayList.add(new KeyResolver(new X509SKIResolver()));
/* 293 */     arrayList.add(new KeyResolver(new RetrievalMethodResolver()));
/* 294 */     arrayList.add(new KeyResolver(new X509SubjectNameResolver()));
/* 295 */     arrayList.add(new KeyResolver(new X509IssuerSerialResolver()));
/* 296 */     arrayList.add(new KeyResolver(new DEREncodedKeyValueResolver()));
/* 297 */     arrayList.add(new KeyResolver(new KeyInfoReferenceResolver()));
/* 298 */     arrayList.add(new KeyResolver(new X509DigestResolver()));
/*     */     
/* 300 */     resolverVector.addAll(arrayList);
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
/*     */   public PublicKey resolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 316 */     return this.resolverSpi.engineLookupAndResolvePublicKey(paramElement, paramString, paramStorageResolver);
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
/*     */   public X509Certificate resolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 332 */     return this.resolverSpi.engineLookupResolveX509Certificate(paramElement, paramString, paramStorageResolver);
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
/*     */   public SecretKey resolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 345 */     return this.resolverSpi.engineLookupAndResolveSecretKey(paramElement, paramString, paramStorageResolver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String paramString1, String paramString2) {
/* 355 */     this.resolverSpi.engineSetProperty(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getProperty(String paramString) {
/* 365 */     return this.resolverSpi.engineGetProperty(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean understandsProperty(String paramString) {
/* 376 */     return this.resolverSpi.understandsProperty(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String resolverClassName() {
/* 386 */     return this.resolverSpi.getClass().getName();
/*     */   }
/*     */ 
/*     */   
/*     */   static class ResolverIterator
/*     */     implements Iterator<KeyResolverSpi>
/*     */   {
/*     */     List<KeyResolver> res;
/*     */     Iterator<KeyResolver> it;
/*     */     
/*     */     public ResolverIterator(List<KeyResolver> param1List) {
/* 397 */       this.res = param1List;
/* 398 */       this.it = this.res.iterator();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 402 */       return this.it.hasNext();
/*     */     }
/*     */     
/*     */     public KeyResolverSpi next() {
/* 406 */       KeyResolver keyResolver = this.it.next();
/* 407 */       if (keyResolver == null) {
/* 408 */         throw new RuntimeException("utils.resolver.noClass");
/*     */       }
/*     */       
/* 411 */       return keyResolver.resolverSpi;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 415 */       throw new UnsupportedOperationException("Can't remove resolvers using the iterator");
/*     */     }
/*     */   }
/*     */   
/*     */   public static Iterator<KeyResolverSpi> iterator() {
/* 420 */     return new ResolverIterator(resolverVector);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/KeyResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */