/*      */ package sun.security.provider.certpath.ldap;
/*      */ 
/*      */ import com.sun.jndi.ldap.LdapReferralException;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.math.BigInteger;
/*      */ import java.net.URI;
/*      */ import java.security.AccessController;
/*      */ import java.security.InvalidAlgorithmParameterException;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.PublicKey;
/*      */ import java.security.cert.CRL;
/*      */ import java.security.cert.CRLException;
/*      */ import java.security.cert.CRLSelector;
/*      */ import java.security.cert.CertSelector;
/*      */ import java.security.cert.CertStore;
/*      */ import java.security.cert.CertStoreException;
/*      */ import java.security.cert.CertStoreParameters;
/*      */ import java.security.cert.CertStoreSpi;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.CertificateException;
/*      */ import java.security.cert.CertificateFactory;
/*      */ import java.security.cert.LDAPCertStoreParameters;
/*      */ import java.security.cert.X509CRL;
/*      */ import java.security.cert.X509CRLSelector;
/*      */ import java.security.cert.X509CertSelector;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.naming.CompositeName;
/*      */ import javax.naming.InvalidNameException;
/*      */ import javax.naming.NameNotFoundException;
/*      */ import javax.naming.NamingEnumeration;
/*      */ import javax.naming.NamingException;
/*      */ import javax.naming.directory.Attribute;
/*      */ import javax.naming.directory.Attributes;
/*      */ import javax.naming.directory.BasicAttributes;
/*      */ import javax.naming.directory.DirContext;
/*      */ import javax.naming.directory.InitialDirContext;
/*      */ import javax.naming.ldap.LdapContext;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import sun.misc.HexDumpEncoder;
/*      */ import sun.security.action.GetBooleanAction;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.security.provider.certpath.X509CertificatePair;
/*      */ import sun.security.util.Cache;
/*      */ import sun.security.util.Debug;
/*      */ import sun.security.x509.X500Name;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class LDAPCertStore
/*      */   extends CertStoreSpi
/*      */ {
/*  113 */   private static final Debug debug = Debug.getInstance("certpath");
/*      */   
/*      */   private static final boolean DEBUG = false;
/*      */   
/*      */   private static final String USER_CERT = "userCertificate;binary";
/*      */   
/*      */   private static final String CA_CERT = "cACertificate;binary";
/*      */   
/*      */   private static final String CROSS_CERT = "crossCertificatePair;binary";
/*      */   
/*      */   private static final String CRL = "certificateRevocationList;binary";
/*      */   
/*      */   private static final String ARL = "authorityRevocationList;binary";
/*      */   
/*      */   private static final String DELTA_CRL = "deltaRevocationList;binary";
/*  128 */   private static final String[] STRING0 = new String[0];
/*      */   
/*  130 */   private static final byte[][] BB0 = new byte[0][];
/*      */   
/*  132 */   private static final Attributes EMPTY_ATTRIBUTES = new BasicAttributes();
/*      */ 
/*      */   
/*      */   private static final int DEFAULT_CACHE_SIZE = 750;
/*      */ 
/*      */   
/*      */   private static final int DEFAULT_CACHE_LIFETIME = 30;
/*      */   
/*      */   private static final int LIFETIME;
/*      */   
/*      */   private static final String PROP_LIFETIME = "sun.security.certpath.ldap.cache.lifetime";
/*      */   
/*      */   private static final String PROP_DISABLE_APP_RESOURCE_FILES = "sun.security.certpath.ldap.disable.app.resource.files";
/*      */   
/*      */   private CertificateFactory cf;
/*      */   
/*      */   private DirContext ctx;
/*      */ 
/*      */   
/*      */   static {
/*  152 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.security.certpath.ldap.cache.lifetime"));
/*      */     
/*  154 */     if (str != null) {
/*  155 */       LIFETIME = Integer.parseInt(str);
/*      */     } else {
/*  157 */       LIFETIME = 30;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean prefetchCRLs = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Cache<String, byte[][]> valueCache;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  178 */   private int cacheHits = 0;
/*  179 */   private int cacheMisses = 0;
/*  180 */   private int requests = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LDAPCertStore(CertStoreParameters paramCertStoreParameters) throws InvalidAlgorithmParameterException {
/*  193 */     super(paramCertStoreParameters);
/*  194 */     if (!(paramCertStoreParameters instanceof LDAPCertStoreParameters)) {
/*  195 */       throw new InvalidAlgorithmParameterException("parameters must be LDAPCertStoreParameters");
/*      */     }
/*      */     
/*  198 */     LDAPCertStoreParameters lDAPCertStoreParameters = (LDAPCertStoreParameters)paramCertStoreParameters;
/*      */ 
/*      */     
/*  201 */     createInitialDirContext(lDAPCertStoreParameters.getServerName(), lDAPCertStoreParameters.getPort());
/*      */ 
/*      */     
/*      */     try {
/*  205 */       this.cf = CertificateFactory.getInstance("X.509");
/*  206 */     } catch (CertificateException certificateException) {
/*  207 */       throw new InvalidAlgorithmParameterException("unable to create CertificateFactory for X.509");
/*      */     } 
/*      */     
/*  210 */     if (LIFETIME == 0) {
/*  211 */       this.valueCache = Cache.newNullCache();
/*  212 */     } else if (LIFETIME < 0) {
/*  213 */       this.valueCache = Cache.newSoftMemoryCache(750);
/*      */     } else {
/*  215 */       this.valueCache = Cache.newSoftMemoryCache(750, LIFETIME);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  224 */   private static final Cache<LDAPCertStoreParameters, CertStore> certStoreCache = Cache.newSoftMemoryCache(185);
/*      */ 
/*      */   
/*      */   static synchronized CertStore getInstance(LDAPCertStoreParameters paramLDAPCertStoreParameters) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/*  228 */     SecurityManager securityManager = System.getSecurityManager();
/*  229 */     if (securityManager != null) {
/*  230 */       securityManager.checkConnect(paramLDAPCertStoreParameters.getServerName(), paramLDAPCertStoreParameters.getPort());
/*      */     }
/*      */     
/*  233 */     CertStore certStore = certStoreCache.get(paramLDAPCertStoreParameters);
/*  234 */     if (certStore == null) {
/*  235 */       certStore = CertStore.getInstance("LDAP", paramLDAPCertStoreParameters);
/*  236 */       certStoreCache.put(paramLDAPCertStoreParameters, certStore);
/*      */     }
/*  238 */     else if (debug != null) {
/*  239 */       debug.println("LDAPCertStore.getInstance: cache hit");
/*      */     } 
/*      */     
/*  242 */     return certStore;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createInitialDirContext(String paramString, int paramInt) throws InvalidAlgorithmParameterException {
/*  254 */     String str = "ldap://" + paramString + ":" + paramInt;
/*  255 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  256 */     hashtable.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
/*      */     
/*  258 */     hashtable.put("java.naming.provider.url", str);
/*      */ 
/*      */     
/*  261 */     boolean bool = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.security.certpath.ldap.disable.app.resource.files"))).booleanValue();
/*      */     
/*  263 */     if (bool) {
/*  264 */       if (debug != null) {
/*  265 */         debug.println("LDAPCertStore disabling app resource files");
/*      */       }
/*  267 */       hashtable.put("com.sun.naming.disable.app.resource.files", "true");
/*      */     } 
/*      */     
/*      */     try {
/*  271 */       this.ctx = new InitialDirContext(hashtable);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  276 */       Hashtable<?, ?> hashtable1 = this.ctx.getEnvironment();
/*  277 */       if (hashtable1.get("java.naming.referral") == null) {
/*  278 */         this.ctx.addToEnvironment("java.naming.referral", "throw");
/*      */       }
/*  280 */     } catch (NamingException namingException) {
/*  281 */       if (debug != null) {
/*  282 */         debug.println("LDAPCertStore.engineInit about to throw InvalidAlgorithmParameterException");
/*      */         
/*  284 */         namingException.printStackTrace();
/*      */       } 
/*  286 */       InvalidAlgorithmParameterException invalidAlgorithmParameterException = new InvalidAlgorithmParameterException("unable to create InitialDirContext using supplied parameters");
/*      */       
/*  288 */       invalidAlgorithmParameterException.initCause(namingException);
/*  289 */       throw invalidAlgorithmParameterException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class LDAPRequest
/*      */   {
/*      */     private final String name;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Map<String, byte[][]> valueMap;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final List<String> requestedAttributes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     LDAPRequest(String param1String) throws CertStoreException {
/*  316 */       this.name = checkName(param1String);
/*  317 */       this.requestedAttributes = new ArrayList<>(5);
/*      */     }
/*      */     
/*      */     private String checkName(String param1String) throws CertStoreException {
/*  321 */       if (param1String == null) {
/*  322 */         throw new CertStoreException("Name absent");
/*      */       }
/*      */       try {
/*  325 */         if ((new CompositeName(param1String)).size() > 1) {
/*  326 */           throw new CertStoreException("Invalid name: " + param1String);
/*      */         }
/*  328 */       } catch (InvalidNameException invalidNameException) {
/*  329 */         throw new CertStoreException("Invalid name: " + param1String, invalidNameException);
/*      */       } 
/*  331 */       return param1String;
/*      */     }
/*      */     
/*      */     String getName() {
/*  335 */       return this.name;
/*      */     }
/*      */     
/*      */     void addRequestedAttribute(String param1String) {
/*  339 */       if (this.valueMap != null) {
/*  340 */         throw new IllegalStateException("Request already sent");
/*      */       }
/*  342 */       this.requestedAttributes.add(param1String);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     byte[][] getValues(String param1String) throws NamingException {
/*  357 */       String str = this.name + "|" + param1String;
/*  358 */       byte[][] arrayOfByte = (byte[][])LDAPCertStore.this.valueCache.get(str);
/*  359 */       if (arrayOfByte != null) {
/*  360 */         LDAPCertStore.this.cacheHits++;
/*  361 */         return arrayOfByte;
/*      */       } 
/*  363 */       LDAPCertStore.this.cacheMisses++;
/*  364 */       Map<String, byte[][]> map = getValueMap();
/*  365 */       arrayOfByte = map.get(param1String);
/*  366 */       return arrayOfByte;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Map<String, byte[][]> getValueMap() throws NamingException {
/*      */       Attributes attributes;
/*  384 */       if (this.valueMap != null) {
/*  385 */         return this.valueMap;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  394 */       this.valueMap = (Map)new HashMap<>(8);
/*  395 */       String[] arrayOfString = this.requestedAttributes.<String>toArray(LDAPCertStore.STRING0);
/*      */       
/*      */       try {
/*  398 */         attributes = LDAPCertStore.this.ctx.getAttributes(this.name, arrayOfString);
/*  399 */       } catch (LdapReferralException ldapReferralException) {
/*      */         
/*      */         while (true) {
/*      */           try {
/*  403 */             String str1 = (String)ldapReferralException.getReferralInfo();
/*  404 */             URI uRI = new URI(str1);
/*  405 */             if (!uRI.getScheme().equalsIgnoreCase("ldap")) {
/*  406 */               throw new IllegalArgumentException("Not LDAP");
/*      */             }
/*  408 */             String str2 = uRI.getPath();
/*  409 */             if (str2 != null && str2.charAt(0) == '/') {
/*  410 */               str2 = str2.substring(1);
/*      */             }
/*  412 */             checkName(str2);
/*  413 */           } catch (Exception exception) {
/*  414 */             throw new NamingException("Cannot follow referral to " + ldapReferralException
/*  415 */                 .getReferralInfo());
/*      */           } 
/*      */           
/*  418 */           LdapContext ldapContext = (LdapContext)ldapReferralException.getReferralContext();
/*      */ 
/*      */           
/*      */           try {
/*  422 */             attributes = ldapContext.getAttributes(this.name, arrayOfString);
/*      */             break;
/*  424 */           } catch (LdapReferralException ldapReferralException1) {
/*  425 */             ldapReferralException = ldapReferralException1;
/*      */           }
/*      */           finally {
/*      */             
/*  429 */             ldapContext.close();
/*      */           } 
/*      */         } 
/*  432 */       } catch (NameNotFoundException nameNotFoundException) {
/*      */ 
/*      */         
/*  435 */         attributes = LDAPCertStore.EMPTY_ATTRIBUTES;
/*      */       } 
/*  437 */       for (String str : this.requestedAttributes) {
/*  438 */         Attribute attribute = attributes.get(str);
/*  439 */         byte[][] arrayOfByte = getAttributeValues(attribute);
/*  440 */         cacheAttribute(str, arrayOfByte);
/*  441 */         this.valueMap.put(str, arrayOfByte);
/*      */       } 
/*  443 */       return this.valueMap;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void cacheAttribute(String param1String, byte[][] param1ArrayOfbyte) {
/*  450 */       String str = this.name + "|" + param1String;
/*  451 */       LDAPCertStore.this.valueCache.put(str, param1ArrayOfbyte);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private byte[][] getAttributeValues(Attribute param1Attribute) throws NamingException {
/*      */       byte[][] arrayOfByte;
/*  462 */       if (param1Attribute == null) {
/*  463 */         arrayOfByte = LDAPCertStore.BB0;
/*      */       } else {
/*  465 */         arrayOfByte = new byte[param1Attribute.size()][];
/*  466 */         byte b = 0;
/*  467 */         NamingEnumeration<?> namingEnumeration = param1Attribute.getAll();
/*  468 */         while (namingEnumeration.hasMore()) {
/*  469 */           Object object = namingEnumeration.next();
/*  470 */           if (LDAPCertStore.debug != null && 
/*  471 */             object instanceof String) {
/*  472 */             LDAPCertStore.debug.println("LDAPCertStore.getAttrValues() enum.next is a string!: " + object);
/*      */           }
/*      */ 
/*      */           
/*  476 */           byte[] arrayOfByte1 = (byte[])object;
/*  477 */           arrayOfByte[b++] = arrayOfByte1;
/*      */         } 
/*      */       } 
/*  480 */       return arrayOfByte;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Collection<X509Certificate> getCertificates(LDAPRequest paramLDAPRequest, String paramString, X509CertSelector paramX509CertSelector) throws CertStoreException {
/*      */     byte[][] arrayOfByte;
/*      */     try {
/*  502 */       arrayOfByte = paramLDAPRequest.getValues(paramString);
/*  503 */     } catch (NamingException namingException) {
/*  504 */       throw new CertStoreException(namingException);
/*      */     } 
/*      */     
/*  507 */     int i = arrayOfByte.length;
/*  508 */     if (i == 0) {
/*  509 */       return Collections.emptySet();
/*      */     }
/*      */     
/*  512 */     ArrayList<X509Certificate> arrayList = new ArrayList(i);
/*      */     
/*  514 */     for (byte b = 0; b < i; b++) {
/*  515 */       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayOfByte[b]);
/*      */       try {
/*  517 */         Certificate certificate = this.cf.generateCertificate(byteArrayInputStream);
/*  518 */         if (paramX509CertSelector.match(certificate)) {
/*  519 */           arrayList.add((X509Certificate)certificate);
/*      */         }
/*  521 */       } catch (CertificateException certificateException) {
/*  522 */         if (debug != null) {
/*  523 */           debug.println("LDAPCertStore.getCertificates() encountered exception while parsing cert, skipping the bad data: ");
/*      */           
/*  525 */           HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/*  526 */           debug.println("[ " + hexDumpEncoder
/*  527 */               .encodeBuffer(arrayOfByte[b]) + " ]");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  532 */     return arrayList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Collection<X509CertificatePair> getCertPairs(LDAPRequest paramLDAPRequest, String paramString) throws CertStoreException {
/*      */     byte[][] arrayOfByte;
/*      */     try {
/*  550 */       arrayOfByte = paramLDAPRequest.getValues(paramString);
/*  551 */     } catch (NamingException namingException) {
/*  552 */       throw new CertStoreException(namingException);
/*      */     } 
/*      */     
/*  555 */     int i = arrayOfByte.length;
/*  556 */     if (i == 0) {
/*  557 */       return Collections.emptySet();
/*      */     }
/*      */     
/*  560 */     ArrayList<X509CertificatePair> arrayList = new ArrayList(i);
/*      */     
/*  562 */     for (byte b = 0; b < i; b++) {
/*      */       
/*      */       try {
/*  565 */         X509CertificatePair x509CertificatePair = X509CertificatePair.generateCertificatePair(arrayOfByte[b]);
/*  566 */         arrayList.add(x509CertificatePair);
/*  567 */       } catch (CertificateException certificateException) {
/*  568 */         if (debug != null) {
/*  569 */           debug.println("LDAPCertStore.getCertPairs() encountered exception while parsing cert, skipping the bad data: ");
/*      */ 
/*      */           
/*  572 */           HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/*  573 */           debug.println("[ " + hexDumpEncoder
/*  574 */               .encodeBuffer(arrayOfByte[b]) + " ]");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  579 */     return arrayList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Collection<X509Certificate> getMatchingCrossCerts(LDAPRequest paramLDAPRequest, X509CertSelector paramX509CertSelector1, X509CertSelector paramX509CertSelector2) throws CertStoreException {
/*  604 */     Collection<X509CertificatePair> collection = getCertPairs(paramLDAPRequest, "crossCertificatePair;binary");
/*      */ 
/*      */     
/*  607 */     ArrayList<X509Certificate> arrayList = new ArrayList();
/*  608 */     for (X509CertificatePair x509CertificatePair : collection) {
/*      */       
/*  610 */       if (paramX509CertSelector1 != null) {
/*  611 */         X509Certificate x509Certificate = x509CertificatePair.getForward();
/*  612 */         if (x509Certificate != null && paramX509CertSelector1.match(x509Certificate)) {
/*  613 */           arrayList.add(x509Certificate);
/*      */         }
/*      */       } 
/*  616 */       if (paramX509CertSelector2 != null) {
/*  617 */         X509Certificate x509Certificate = x509CertificatePair.getReverse();
/*  618 */         if (x509Certificate != null && paramX509CertSelector2.match(x509Certificate)) {
/*  619 */           arrayList.add(x509Certificate);
/*      */         }
/*      */       } 
/*      */     } 
/*  623 */     return arrayList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Collection<X509Certificate> engineGetCertificates(CertSelector paramCertSelector) throws CertStoreException {
/*  649 */     if (debug != null) {
/*  650 */       debug.println("LDAPCertStore.engineGetCertificates() selector: " + 
/*  651 */           String.valueOf(paramCertSelector));
/*      */     }
/*      */     
/*  654 */     if (paramCertSelector == null) {
/*  655 */       paramCertSelector = new X509CertSelector();
/*      */     }
/*  657 */     if (!(paramCertSelector instanceof X509CertSelector)) {
/*  658 */       throw new CertStoreException("LDAPCertStore needs an X509CertSelector to find certs");
/*      */     }
/*      */     
/*  661 */     X509CertSelector x509CertSelector = (X509CertSelector)paramCertSelector;
/*  662 */     int i = x509CertSelector.getBasicConstraints();
/*  663 */     String str1 = x509CertSelector.getSubjectAsString();
/*  664 */     String str2 = x509CertSelector.getIssuerAsString();
/*  665 */     HashSet<X509Certificate> hashSet = new HashSet();
/*  666 */     if (debug != null) {
/*  667 */       debug.println("LDAPCertStore.engineGetCertificates() basicConstraints: " + i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  676 */     if (str1 != null) {
/*  677 */       if (debug != null) {
/*  678 */         debug.println("LDAPCertStore.engineGetCertificates() subject is not null");
/*      */       }
/*      */       
/*  681 */       LDAPRequest lDAPRequest = new LDAPRequest(str1);
/*  682 */       if (i > -2) {
/*  683 */         lDAPRequest.addRequestedAttribute("crossCertificatePair;binary");
/*  684 */         lDAPRequest.addRequestedAttribute("cACertificate;binary");
/*  685 */         lDAPRequest.addRequestedAttribute("authorityRevocationList;binary");
/*  686 */         if (this.prefetchCRLs) {
/*  687 */           lDAPRequest.addRequestedAttribute("certificateRevocationList;binary");
/*      */         }
/*      */       } 
/*  690 */       if (i < 0) {
/*  691 */         lDAPRequest.addRequestedAttribute("userCertificate;binary");
/*      */       }
/*      */       
/*  694 */       if (i > -2) {
/*  695 */         hashSet.addAll(getMatchingCrossCerts(lDAPRequest, x509CertSelector, null));
/*  696 */         if (debug != null) {
/*  697 */           debug.println("LDAPCertStore.engineGetCertificates() after getMatchingCrossCerts(subject,xsel,null),certs.size(): " + hashSet
/*      */               
/*  699 */               .size());
/*      */         }
/*  701 */         hashSet.addAll(getCertificates(lDAPRequest, "cACertificate;binary", x509CertSelector));
/*  702 */         if (debug != null) {
/*  703 */           debug.println("LDAPCertStore.engineGetCertificates() after getCertificates(subject,CA_CERT,xsel),certs.size(): " + hashSet
/*      */               
/*  705 */               .size());
/*      */         }
/*      */       } 
/*  708 */       if (i < 0) {
/*  709 */         hashSet.addAll(getCertificates(lDAPRequest, "userCertificate;binary", x509CertSelector));
/*  710 */         if (debug != null) {
/*  711 */           debug.println("LDAPCertStore.engineGetCertificates() after getCertificates(subject,USER_CERT, xsel),certs.size(): " + hashSet
/*      */               
/*  713 */               .size());
/*      */         }
/*      */       } 
/*      */     } else {
/*  717 */       if (debug != null) {
/*  718 */         debug
/*  719 */           .println("LDAPCertStore.engineGetCertificates() subject is null");
/*      */       }
/*  721 */       if (i == -2) {
/*  722 */         throw new CertStoreException("need subject to find EE certs");
/*      */       }
/*  724 */       if (str2 == null) {
/*  725 */         throw new CertStoreException("need subject or issuer to find certs");
/*      */       }
/*      */     } 
/*  728 */     if (debug != null) {
/*  729 */       debug.println("LDAPCertStore.engineGetCertificates() about to getMatchingCrossCerts...");
/*      */     }
/*      */     
/*  732 */     if (str2 != null && i > -2) {
/*  733 */       LDAPRequest lDAPRequest = new LDAPRequest(str2);
/*  734 */       lDAPRequest.addRequestedAttribute("crossCertificatePair;binary");
/*  735 */       lDAPRequest.addRequestedAttribute("cACertificate;binary");
/*  736 */       lDAPRequest.addRequestedAttribute("authorityRevocationList;binary");
/*  737 */       if (this.prefetchCRLs) {
/*  738 */         lDAPRequest.addRequestedAttribute("certificateRevocationList;binary");
/*      */       }
/*      */       
/*  741 */       hashSet.addAll(getMatchingCrossCerts(lDAPRequest, null, x509CertSelector));
/*  742 */       if (debug != null) {
/*  743 */         debug.println("LDAPCertStore.engineGetCertificates() after getMatchingCrossCerts(issuer,null,xsel),certs.size(): " + hashSet
/*      */             
/*  745 */             .size());
/*      */       }
/*  747 */       hashSet.addAll(getCertificates(lDAPRequest, "cACertificate;binary", x509CertSelector));
/*  748 */       if (debug != null) {
/*  749 */         debug.println("LDAPCertStore.engineGetCertificates() after getCertificates(issuer,CA_CERT,xsel),certs.size(): " + hashSet
/*      */             
/*  751 */             .size());
/*      */       }
/*      */     } 
/*  754 */     if (debug != null) {
/*  755 */       debug.println("LDAPCertStore.engineGetCertificates() returning certs");
/*      */     }
/*  757 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Collection<X509CRL> getCRLs(LDAPRequest paramLDAPRequest, String paramString, X509CRLSelector paramX509CRLSelector) throws CertStoreException {
/*      */     byte[][] arrayOfByte;
/*      */     try {
/*  777 */       arrayOfByte = paramLDAPRequest.getValues(paramString);
/*  778 */     } catch (NamingException namingException) {
/*  779 */       throw new CertStoreException(namingException);
/*      */     } 
/*      */     
/*  782 */     int i = arrayOfByte.length;
/*  783 */     if (i == 0) {
/*  784 */       return Collections.emptySet();
/*      */     }
/*      */     
/*  787 */     ArrayList<X509CRL> arrayList = new ArrayList(i);
/*      */     
/*  789 */     for (byte b = 0; b < i; b++) {
/*      */       try {
/*  791 */         CRL cRL = this.cf.generateCRL(new ByteArrayInputStream(arrayOfByte[b]));
/*  792 */         if (paramX509CRLSelector.match(cRL)) {
/*  793 */           arrayList.add((X509CRL)cRL);
/*      */         }
/*  795 */       } catch (CRLException cRLException) {
/*  796 */         if (debug != null) {
/*  797 */           debug.println("LDAPCertStore.getCRLs() encountered exception while parsing CRL, skipping the bad data: ");
/*      */           
/*  799 */           HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/*  800 */           debug.println("[ " + hexDumpEncoder.encodeBuffer(arrayOfByte[b]) + " ]");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  805 */     return arrayList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Collection<X509CRL> engineGetCRLs(CRLSelector paramCRLSelector) throws CertStoreException {
/*      */     Collection<Object> collection;
/*  831 */     if (debug != null) {
/*  832 */       debug.println("LDAPCertStore.engineGetCRLs() selector: " + paramCRLSelector);
/*      */     }
/*      */ 
/*      */     
/*  836 */     if (paramCRLSelector == null) {
/*  837 */       paramCRLSelector = new X509CRLSelector();
/*      */     }
/*  839 */     if (!(paramCRLSelector instanceof X509CRLSelector)) {
/*  840 */       throw new CertStoreException("need X509CRLSelector to find CRLs");
/*      */     }
/*  842 */     X509CRLSelector x509CRLSelector = (X509CRLSelector)paramCRLSelector;
/*  843 */     HashSet<X509CRL> hashSet = new HashSet();
/*      */ 
/*      */ 
/*      */     
/*  847 */     X509Certificate x509Certificate = x509CRLSelector.getCertificateChecking();
/*  848 */     if (x509Certificate != null) {
/*  849 */       HashSet<String> hashSet1 = new HashSet();
/*  850 */       X500Principal x500Principal = x509Certificate.getIssuerX500Principal();
/*  851 */       hashSet1.add(x500Principal.getName("RFC2253"));
/*      */     }
/*      */     else {
/*      */       
/*  855 */       collection = x509CRLSelector.getIssuerNames();
/*  856 */       if (collection == null) {
/*  857 */         throw new CertStoreException("need issuerNames or certChecking to find CRLs");
/*      */       }
/*      */     } 
/*      */     
/*  861 */     for (byte[] arrayOfByte : collection) {
/*      */       String str; Collection<X509CRL> collection1;
/*  863 */       if (arrayOfByte instanceof byte[]) {
/*      */         try {
/*  865 */           X500Principal x500Principal = new X500Principal(arrayOfByte);
/*  866 */           str = x500Principal.getName("RFC2253");
/*  867 */         } catch (IllegalArgumentException illegalArgumentException) {
/*      */           continue;
/*      */         } 
/*      */       } else {
/*  871 */         str = (String)arrayOfByte;
/*      */       } 
/*      */       
/*  874 */       Set<?> set = Collections.emptySet();
/*  875 */       if (x509Certificate == null || x509Certificate.getBasicConstraints() != -1) {
/*  876 */         LDAPRequest lDAPRequest = new LDAPRequest(str);
/*  877 */         lDAPRequest.addRequestedAttribute("crossCertificatePair;binary");
/*  878 */         lDAPRequest.addRequestedAttribute("cACertificate;binary");
/*  879 */         lDAPRequest.addRequestedAttribute("authorityRevocationList;binary");
/*  880 */         if (this.prefetchCRLs) {
/*  881 */           lDAPRequest.addRequestedAttribute("certificateRevocationList;binary");
/*      */         }
/*      */         try {
/*  884 */           collection1 = getCRLs(lDAPRequest, "authorityRevocationList;binary", x509CRLSelector);
/*  885 */           if (collection1.isEmpty()) {
/*      */ 
/*      */             
/*  888 */             this.prefetchCRLs = true;
/*      */           } else {
/*  890 */             hashSet.addAll(collection1);
/*      */           } 
/*  892 */         } catch (CertStoreException certStoreException) {
/*  893 */           if (debug != null) {
/*  894 */             debug.println("LDAPCertStore.engineGetCRLs non-fatal error retrieving ARLs:" + certStoreException);
/*      */             
/*  896 */             certStoreException.printStackTrace();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  903 */       if (collection1.isEmpty() || x509Certificate == null) {
/*  904 */         LDAPRequest lDAPRequest = new LDAPRequest(str);
/*  905 */         lDAPRequest.addRequestedAttribute("certificateRevocationList;binary");
/*  906 */         collection1 = getCRLs(lDAPRequest, "certificateRevocationList;binary", x509CRLSelector);
/*  907 */         hashSet.addAll(collection1);
/*      */       } 
/*      */     } 
/*  910 */     return hashSet;
/*      */   }
/*      */ 
/*      */   
/*      */   static LDAPCertStoreParameters getParameters(URI paramURI) {
/*  915 */     String str = paramURI.getHost();
/*  916 */     if (str == null) {
/*  917 */       return new SunLDAPCertStoreParameters();
/*      */     }
/*  919 */     int i = paramURI.getPort();
/*  920 */     return (i == -1) ? new SunLDAPCertStoreParameters(str) : new SunLDAPCertStoreParameters(str, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SunLDAPCertStoreParameters
/*      */     extends LDAPCertStoreParameters
/*      */   {
/*  934 */     private volatile int hashCode = 0;
/*      */     
/*      */     SunLDAPCertStoreParameters(String param1String, int param1Int) {
/*  937 */       super(param1String, param1Int);
/*      */     }
/*      */     SunLDAPCertStoreParameters(String param1String) {
/*  940 */       super(param1String);
/*      */     }
/*      */     
/*      */     SunLDAPCertStoreParameters() {}
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  946 */       if (!(param1Object instanceof LDAPCertStoreParameters)) {
/*  947 */         return false;
/*      */       }
/*  949 */       LDAPCertStoreParameters lDAPCertStoreParameters = (LDAPCertStoreParameters)param1Object;
/*  950 */       return (getPort() == lDAPCertStoreParameters.getPort() && 
/*  951 */         getServerName().equalsIgnoreCase(lDAPCertStoreParameters.getServerName()));
/*      */     }
/*      */     public int hashCode() {
/*  954 */       if (this.hashCode == 0) {
/*  955 */         int i = 17;
/*  956 */         i = 37 * i + getPort();
/*      */         
/*  958 */         i = 37 * i + getServerName().toLowerCase(Locale.ENGLISH).hashCode();
/*  959 */         this.hashCode = i;
/*      */       } 
/*  961 */       return this.hashCode;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class LDAPCertSelector
/*      */     extends X509CertSelector
/*      */   {
/*      */     private X500Principal certSubject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private X509CertSelector selector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private X500Principal subject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     LDAPCertSelector(X509CertSelector param1X509CertSelector, X500Principal param1X500Principal, String param1String) throws IOException {
/*  995 */       this.selector = (param1X509CertSelector == null) ? new X509CertSelector() : param1X509CertSelector;
/*  996 */       this.certSubject = param1X500Principal;
/*  997 */       this.subject = (new X500Name(param1String)).asX500Principal();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public X509Certificate getCertificate() {
/* 1003 */       return this.selector.getCertificate();
/*      */     }
/*      */     public BigInteger getSerialNumber() {
/* 1006 */       return this.selector.getSerialNumber();
/*      */     }
/*      */     public X500Principal getIssuer() {
/* 1009 */       return this.selector.getIssuer();
/*      */     }
/*      */     public String getIssuerAsString() {
/* 1012 */       return this.selector.getIssuerAsString();
/*      */     }
/*      */     public byte[] getIssuerAsBytes() throws IOException {
/* 1015 */       return this.selector.getIssuerAsBytes();
/*      */     }
/*      */     
/*      */     public X500Principal getSubject() {
/* 1019 */       return this.subject;
/*      */     }
/*      */     
/*      */     public String getSubjectAsString() {
/* 1023 */       return this.subject.getName();
/*      */     }
/*      */     
/*      */     public byte[] getSubjectAsBytes() throws IOException {
/* 1027 */       return this.subject.getEncoded();
/*      */     }
/*      */     public byte[] getSubjectKeyIdentifier() {
/* 1030 */       return this.selector.getSubjectKeyIdentifier();
/*      */     }
/*      */     public byte[] getAuthorityKeyIdentifier() {
/* 1033 */       return this.selector.getAuthorityKeyIdentifier();
/*      */     }
/*      */     public Date getCertificateValid() {
/* 1036 */       return this.selector.getCertificateValid();
/*      */     }
/*      */     public Date getPrivateKeyValid() {
/* 1039 */       return this.selector.getPrivateKeyValid();
/*      */     }
/*      */     public String getSubjectPublicKeyAlgID() {
/* 1042 */       return this.selector.getSubjectPublicKeyAlgID();
/*      */     }
/*      */     public PublicKey getSubjectPublicKey() {
/* 1045 */       return this.selector.getSubjectPublicKey();
/*      */     }
/*      */     public boolean[] getKeyUsage() {
/* 1048 */       return this.selector.getKeyUsage();
/*      */     }
/*      */     public Set<String> getExtendedKeyUsage() {
/* 1051 */       return this.selector.getExtendedKeyUsage();
/*      */     }
/*      */     public boolean getMatchAllSubjectAltNames() {
/* 1054 */       return this.selector.getMatchAllSubjectAltNames();
/*      */     }
/*      */     public Collection<List<?>> getSubjectAlternativeNames() {
/* 1057 */       return this.selector.getSubjectAlternativeNames();
/*      */     }
/*      */     public byte[] getNameConstraints() {
/* 1060 */       return this.selector.getNameConstraints();
/*      */     }
/*      */     public int getBasicConstraints() {
/* 1063 */       return this.selector.getBasicConstraints();
/*      */     }
/*      */     public Set<String> getPolicy() {
/* 1066 */       return this.selector.getPolicy();
/*      */     }
/*      */     public Collection<List<?>> getPathToNames() {
/* 1069 */       return this.selector.getPathToNames();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean match(Certificate param1Certificate) {
/* 1075 */       this.selector.setSubject(this.certSubject);
/* 1076 */       boolean bool = this.selector.match(param1Certificate);
/* 1077 */       this.selector.setSubject(this.subject);
/* 1078 */       return bool;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class LDAPCRLSelector
/*      */     extends X509CRLSelector
/*      */   {
/*      */     private X509CRLSelector selector;
/*      */ 
/*      */ 
/*      */     
/*      */     private Collection<X500Principal> certIssuers;
/*      */ 
/*      */ 
/*      */     
/*      */     private Collection<X500Principal> issuers;
/*      */ 
/*      */     
/*      */     private HashSet<Object> issuerNames;
/*      */ 
/*      */ 
/*      */     
/*      */     LDAPCRLSelector(X509CRLSelector param1X509CRLSelector, Collection<X500Principal> param1Collection, String param1String) throws IOException {
/* 1104 */       this.selector = (param1X509CRLSelector == null) ? new X509CRLSelector() : param1X509CRLSelector;
/* 1105 */       this.certIssuers = param1Collection;
/* 1106 */       this.issuerNames = new HashSet();
/* 1107 */       this.issuerNames.add(param1String);
/* 1108 */       this.issuers = new HashSet<>();
/* 1109 */       this.issuers.add((new X500Name(param1String)).asX500Principal());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Collection<X500Principal> getIssuers() {
/* 1115 */       return Collections.unmodifiableCollection(this.issuers);
/*      */     }
/*      */     
/*      */     public Collection<Object> getIssuerNames() {
/* 1119 */       return Collections.unmodifiableCollection(this.issuerNames);
/*      */     }
/*      */     public BigInteger getMinCRL() {
/* 1122 */       return this.selector.getMinCRL();
/*      */     }
/*      */     public BigInteger getMaxCRL() {
/* 1125 */       return this.selector.getMaxCRL();
/*      */     }
/*      */     public Date getDateAndTime() {
/* 1128 */       return this.selector.getDateAndTime();
/*      */     }
/*      */     public X509Certificate getCertificateChecking() {
/* 1131 */       return this.selector.getCertificateChecking();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean match(CRL param1CRL) {
/* 1136 */       this.selector.setIssuers(this.certIssuers);
/* 1137 */       boolean bool = this.selector.match(param1CRL);
/* 1138 */       this.selector.setIssuers(this.issuers);
/* 1139 */       return bool;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/ldap/LDAPCertStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */