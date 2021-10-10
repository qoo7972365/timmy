/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.net.URLConnection;
/*     */ import java.security.AccessController;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.cert.CRLSelector;
/*     */ import java.security.cert.CertSelector;
/*     */ import java.security.cert.CertStore;
/*     */ import java.security.cert.CertStoreException;
/*     */ import java.security.cert.CertStoreParameters;
/*     */ import java.security.cert.CertStoreSpi;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509CRL;
/*     */ import java.security.cert.X509CRLSelector;
/*     */ import java.security.cert.X509CertSelector;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Locale;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ import sun.security.util.Cache;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.x509.AccessDescription;
/*     */ import sun.security.x509.GeneralNameInterface;
/*     */ import sun.security.x509.URIName;
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
/*     */ class URICertStore
/*     */   extends CertStoreSpi
/*     */ {
/*  91 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int CHECK_INTERVAL = 30000;
/*     */ 
/*     */   
/*     */   private static final int CACHE_SIZE = 185;
/*     */ 
/*     */   
/*     */   private final CertificateFactory factory;
/*     */ 
/*     */   
/* 104 */   private Collection<X509Certificate> certs = Collections.emptySet();
/*     */ 
/*     */   
/*     */   private X509CRL crl;
/*     */ 
/*     */   
/*     */   private long lastChecked;
/*     */ 
/*     */   
/*     */   private long lastModified;
/*     */ 
/*     */   
/*     */   private URI uri;
/*     */ 
/*     */   
/*     */   private boolean ldap = false;
/*     */ 
/*     */   
/*     */   private CertStoreHelper ldapHelper;
/*     */ 
/*     */   
/*     */   private CertStore ldapCertStore;
/*     */ 
/*     */   
/*     */   private String ldapPath;
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_CRL_CONNECT_TIMEOUT = 15000;
/*     */ 
/*     */   
/* 134 */   private static final int CRL_CONNECT_TIMEOUT = initializeTimeout();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int initializeTimeout() {
/* 142 */     Integer integer = AccessController.<Integer>doPrivileged(new GetIntegerAction("com.sun.security.crl.timeout"));
/*     */     
/* 144 */     if (integer == null || integer.intValue() < 0) {
/* 145 */       return 15000;
/*     */     }
/*     */ 
/*     */     
/* 149 */     return integer.intValue() * 1000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   URICertStore(CertStoreParameters paramCertStoreParameters) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
/* 159 */     super(paramCertStoreParameters);
/* 160 */     if (!(paramCertStoreParameters instanceof URICertStoreParameters)) {
/* 161 */       throw new InvalidAlgorithmParameterException("params must be instanceof URICertStoreParameters");
/*     */     }
/*     */     
/* 164 */     this.uri = ((URICertStoreParameters)paramCertStoreParameters).uri;
/*     */     
/* 166 */     if (this.uri.getScheme().toLowerCase(Locale.ENGLISH).equals("ldap")) {
/* 167 */       this.ldap = true;
/* 168 */       this.ldapHelper = CertStoreHelper.getInstance("LDAP");
/* 169 */       this.ldapCertStore = this.ldapHelper.getCertStore(this.uri);
/* 170 */       this.ldapPath = this.uri.getPath();
/*     */       
/* 172 */       if (this.ldapPath.charAt(0) == '/') {
/* 173 */         this.ldapPath = this.ldapPath.substring(1);
/*     */       }
/*     */     } 
/*     */     try {
/* 177 */       this.factory = CertificateFactory.getInstance("X.509");
/* 178 */     } catch (CertificateException certificateException) {
/* 179 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   private static final Cache<URICertStoreParameters, CertStore> certStoreCache = Cache.newSoftMemoryCache(185);
/*     */   
/*     */   static synchronized CertStore getInstance(URICertStoreParameters paramURICertStoreParameters) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/* 191 */     if (debug != null) {
/* 192 */       debug.println("CertStore URI:" + paramURICertStoreParameters.uri);
/*     */     }
/* 194 */     CertStore certStore = certStoreCache.get(paramURICertStoreParameters);
/* 195 */     if (certStore == null) {
/* 196 */       certStore = new UCS(new URICertStore(paramURICertStoreParameters), null, "URI", paramURICertStoreParameters);
/* 197 */       certStoreCache.put(paramURICertStoreParameters, certStore);
/*     */     }
/* 199 */     else if (debug != null) {
/* 200 */       debug.println("URICertStore.getInstance: cache hit");
/*     */     } 
/*     */     
/* 203 */     return certStore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static CertStore getInstance(AccessDescription paramAccessDescription) {
/* 211 */     if (!paramAccessDescription.getAccessMethod().equals(AccessDescription.Ad_CAISSUERS_Id))
/*     */     {
/* 213 */       return null;
/*     */     }
/* 215 */     GeneralNameInterface generalNameInterface = paramAccessDescription.getAccessLocation().getName();
/* 216 */     if (!(generalNameInterface instanceof URIName)) {
/* 217 */       return null;
/*     */     }
/* 219 */     URI uRI = ((URIName)generalNameInterface).getURI();
/*     */     try {
/* 221 */       return 
/* 222 */         getInstance(new URICertStoreParameters(uRI));
/* 223 */     } catch (Exception exception) {
/* 224 */       if (debug != null) {
/* 225 */         debug.println("exception creating CertStore: " + exception);
/* 226 */         exception.printStackTrace();
/*     */       } 
/* 228 */       return null;
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
/*     */   
/*     */   public synchronized Collection<X509Certificate> engineGetCertificates(CertSelector paramCertSelector) throws CertStoreException {
/* 251 */     if (this.ldap) {
/* 252 */       X509CertSelector x509CertSelector = (X509CertSelector)paramCertSelector;
/*     */       try {
/* 254 */         x509CertSelector = this.ldapHelper.wrap(x509CertSelector, x509CertSelector.getSubject(), this.ldapPath);
/* 255 */       } catch (IOException iOException) {
/* 256 */         throw new CertStoreException(iOException);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 261 */       return (Collection)this.ldapCertStore
/* 262 */         .getCertificates(x509CertSelector);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 268 */     long l = System.currentTimeMillis();
/* 269 */     if (l - this.lastChecked < 30000L) {
/* 270 */       if (debug != null) {
/* 271 */         debug.println("Returning certificates from cache");
/*     */       }
/* 273 */       return getMatchingCerts(this.certs, paramCertSelector);
/*     */     } 
/* 275 */     this.lastChecked = l;
/*     */     try {
/* 277 */       URLConnection uRLConnection = this.uri.toURL().openConnection();
/* 278 */       if (this.lastModified != 0L) {
/* 279 */         uRLConnection.setIfModifiedSince(this.lastModified);
/*     */       }
/* 281 */       long l1 = this.lastModified;
/* 282 */       try (InputStream null = uRLConnection.getInputStream()) {
/* 283 */         this.lastModified = uRLConnection.getLastModified();
/* 284 */         if (l1 != 0L) {
/* 285 */           if (l1 == this.lastModified) {
/* 286 */             if (debug != null) {
/* 287 */               debug.println("Not modified, using cached copy");
/*     */             }
/* 289 */             return getMatchingCerts(this.certs, paramCertSelector);
/* 290 */           }  if (uRLConnection instanceof HttpURLConnection) {
/*     */             
/* 292 */             HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
/* 293 */             if (httpURLConnection.getResponseCode() == 304) {
/*     */               
/* 295 */               if (debug != null) {
/* 296 */                 debug.println("Not modified, using cached copy");
/*     */               }
/* 298 */               return getMatchingCerts(this.certs, paramCertSelector);
/*     */             } 
/*     */           } 
/*     */         } 
/* 302 */         if (debug != null) {
/* 303 */           debug.println("Downloading new certificates...");
/*     */         }
/*     */         
/* 306 */         this
/* 307 */           .certs = (Collection)this.factory.generateCertificates(inputStream);
/*     */       } 
/* 309 */       return getMatchingCerts(this.certs, paramCertSelector);
/* 310 */     } catch (IOException|CertificateException iOException) {
/* 311 */       if (debug != null) {
/* 312 */         debug.println("Exception fetching certificates:");
/* 313 */         iOException.printStackTrace();
/*     */       } 
/*     */ 
/*     */       
/* 317 */       this.lastModified = 0L;
/* 318 */       this.certs = Collections.emptySet();
/* 319 */       return this.certs;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Collection<X509Certificate> getMatchingCerts(Collection<X509Certificate> paramCollection, CertSelector paramCertSelector) {
/* 330 */     if (paramCertSelector == null) {
/* 331 */       return paramCollection;
/*     */     }
/* 333 */     ArrayList<X509Certificate> arrayList = new ArrayList(paramCollection.size());
/* 334 */     for (X509Certificate x509Certificate : paramCollection) {
/* 335 */       if (paramCertSelector.match(x509Certificate)) {
/* 336 */         arrayList.add(x509Certificate);
/*     */       }
/*     */     } 
/* 339 */     return arrayList;
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
/*     */   public synchronized Collection<X509CRL> engineGetCRLs(CRLSelector paramCRLSelector) throws CertStoreException {
/* 361 */     if (this.ldap) {
/* 362 */       X509CRLSelector x509CRLSelector = (X509CRLSelector)paramCRLSelector;
/*     */       try {
/* 364 */         x509CRLSelector = this.ldapHelper.wrap(x509CRLSelector, (Collection<X500Principal>)null, this.ldapPath);
/* 365 */       } catch (IOException iOException) {
/* 366 */         throw new CertStoreException(iOException);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 372 */         return (Collection)this.ldapCertStore.getCRLs(x509CRLSelector);
/* 373 */       } catch (CertStoreException certStoreException) {
/* 374 */         throw new PKIX.CertStoreTypeException("LDAP", certStoreException);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 381 */     long l = System.currentTimeMillis();
/* 382 */     if (l - this.lastChecked < 30000L) {
/* 383 */       if (debug != null) {
/* 384 */         debug.println("Returning CRL from cache");
/*     */       }
/* 386 */       return getMatchingCRLs(this.crl, paramCRLSelector);
/*     */     } 
/* 388 */     this.lastChecked = l;
/*     */     try {
/* 390 */       URLConnection uRLConnection = this.uri.toURL().openConnection();
/* 391 */       if (this.lastModified != 0L) {
/* 392 */         uRLConnection.setIfModifiedSince(this.lastModified);
/*     */       }
/* 394 */       long l1 = this.lastModified;
/* 395 */       uRLConnection.setConnectTimeout(CRL_CONNECT_TIMEOUT);
/* 396 */       try (InputStream null = uRLConnection.getInputStream()) {
/* 397 */         this.lastModified = uRLConnection.getLastModified();
/* 398 */         if (l1 != 0L) {
/* 399 */           if (l1 == this.lastModified) {
/* 400 */             if (debug != null) {
/* 401 */               debug.println("Not modified, using cached copy");
/*     */             }
/* 403 */             return getMatchingCRLs(this.crl, paramCRLSelector);
/* 404 */           }  if (uRLConnection instanceof HttpURLConnection) {
/*     */             
/* 406 */             HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
/* 407 */             if (httpURLConnection.getResponseCode() == 304) {
/*     */               
/* 409 */               if (debug != null) {
/* 410 */                 debug.println("Not modified, using cached copy");
/*     */               }
/* 412 */               return getMatchingCRLs(this.crl, paramCRLSelector);
/*     */             } 
/*     */           } 
/*     */         } 
/* 416 */         if (debug != null) {
/* 417 */           debug.println("Downloading new CRL...");
/*     */         }
/* 419 */         this.crl = (X509CRL)this.factory.generateCRL(inputStream);
/*     */       } 
/* 421 */       return getMatchingCRLs(this.crl, paramCRLSelector);
/* 422 */     } catch (IOException|java.security.cert.CRLException iOException) {
/* 423 */       if (debug != null) {
/* 424 */         debug.println("Exception fetching CRL:");
/* 425 */         iOException.printStackTrace();
/*     */       } 
/*     */       
/* 428 */       this.lastModified = 0L;
/* 429 */       this.crl = null;
/* 430 */       throw new PKIX.CertStoreTypeException("URI", new CertStoreException(iOException));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Collection<X509CRL> getMatchingCRLs(X509CRL paramX509CRL, CRLSelector paramCRLSelector) {
/* 441 */     if (paramCRLSelector == null || (paramX509CRL != null && paramCRLSelector.match(paramX509CRL))) {
/* 442 */       return Collections.singletonList(paramX509CRL);
/*     */     }
/* 444 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   static class URICertStoreParameters
/*     */     implements CertStoreParameters
/*     */   {
/*     */     private final URI uri;
/*     */     
/* 453 */     private volatile int hashCode = 0;
/*     */     URICertStoreParameters(URI param1URI) {
/* 455 */       this.uri = param1URI;
/*     */     }
/*     */     public boolean equals(Object param1Object) {
/* 458 */       if (!(param1Object instanceof URICertStoreParameters)) {
/* 459 */         return false;
/*     */       }
/* 461 */       URICertStoreParameters uRICertStoreParameters = (URICertStoreParameters)param1Object;
/* 462 */       return this.uri.equals(uRICertStoreParameters.uri);
/*     */     }
/*     */     public int hashCode() {
/* 465 */       if (this.hashCode == 0) {
/* 466 */         int i = 17;
/* 467 */         i = 37 * i + this.uri.hashCode();
/* 468 */         this.hashCode = i;
/*     */       } 
/* 470 */       return this.hashCode;
/*     */     }
/*     */     public Object clone() {
/*     */       try {
/* 474 */         return super.clone();
/* 475 */       } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */         
/* 477 */         throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class UCS
/*     */     extends CertStore
/*     */   {
/*     */     protected UCS(CertStoreSpi param1CertStoreSpi, Provider param1Provider, String param1String, CertStoreParameters param1CertStoreParameters) {
/* 488 */       super(param1CertStoreSpi, param1Provider, param1String, param1CertStoreParameters);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/URICertStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */