/*      */ package java.security;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.security.spec.AlgorithmParameterSpec;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import javax.crypto.BadPaddingException;
/*      */ import javax.crypto.Cipher;
/*      */ import javax.crypto.IllegalBlockSizeException;
/*      */ import javax.crypto.NoSuchPaddingException;
/*      */ import sun.security.jca.GetInstance;
/*      */ import sun.security.jca.ServiceId;
/*      */ import sun.security.util.Debug;
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
/*      */ public abstract class Signature
/*      */   extends SignatureSpi
/*      */ {
/*  121 */   private static final Debug debug = Debug.getInstance("jca", "Signature");
/*      */ 
/*      */   
/*  124 */   private static final Debug pdebug = Debug.getInstance("provider", "Provider");
/*  125 */   private static final boolean skipDebug = (
/*  126 */     Debug.isOn("engine=") && !Debug.isOn("signature"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String algorithm;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Provider provider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int UNINITIALIZED = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int SIGN = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int VERIFY = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  159 */   protected int state = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String RSA_SIGNATURE = "NONEwithRSA";
/*      */ 
/*      */   
/*      */   private static final String RSA_CIPHER = "RSA/ECB/PKCS1Padding";
/*      */ 
/*      */ 
/*      */   
/*      */   protected Signature(String paramString) {
/*  171 */     this.algorithm = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  181 */   private static final List<ServiceId> rsaIds = Arrays.asList(new ServiceId[] { new ServiceId("Signature", "NONEwithRSA"), new ServiceId("Cipher", "RSA/ECB/PKCS1Padding"), new ServiceId("Cipher", "RSA/ECB"), new ServiceId("Cipher", "RSA//PKCS1Padding"), new ServiceId("Cipher", "RSA") });
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
/*      */   public static Signature getInstance(String paramString) throws NoSuchAlgorithmException {
/*      */     List<Provider.Service> list;
/*  221 */     if (paramString.equalsIgnoreCase("NONEwithRSA")) {
/*  222 */       list = GetInstance.getServices(rsaIds);
/*      */     } else {
/*  224 */       list = GetInstance.getServices("Signature", paramString);
/*      */     } 
/*  226 */     Iterator<Provider.Service> iterator = list.iterator();
/*  227 */     if (!iterator.hasNext()) {
/*  228 */       throw new NoSuchAlgorithmException(paramString + " Signature not available");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  234 */       Provider.Service service = iterator.next();
/*  235 */       if (isSpi(service)) {
/*  236 */         return new Delegate(service, iterator, paramString);
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/*  241 */         GetInstance.Instance instance = GetInstance.getInstance(service, SignatureSpi.class);
/*  242 */         return getInstance(instance, paramString);
/*  243 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException2) {
/*  244 */         NoSuchAlgorithmException noSuchAlgorithmException1 = noSuchAlgorithmException2;
/*      */ 
/*      */         
/*  247 */         if (!iterator.hasNext())
/*  248 */           throw noSuchAlgorithmException1; 
/*      */       } 
/*      */     } 
/*      */   } private static Signature getInstance(GetInstance.Instance paramInstance, String paramString) {
/*      */     Signature signature;
/*  253 */     if (paramInstance.impl instanceof Signature) {
/*  254 */       signature = (Signature)paramInstance.impl;
/*  255 */       signature.algorithm = paramString;
/*      */     } else {
/*  257 */       SignatureSpi signatureSpi = (SignatureSpi)paramInstance.impl;
/*  258 */       signature = new Delegate(signatureSpi, paramString);
/*      */     } 
/*  260 */     signature.provider = paramInstance.provider;
/*  261 */     return signature;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  267 */   private static final Map<String, Boolean> signatureInfo = new ConcurrentHashMap<>(); static {
/*  268 */     Boolean bool = Boolean.TRUE;
/*      */     
/*  270 */     signatureInfo.put("sun.security.provider.DSA$RawDSA", bool);
/*  271 */     signatureInfo.put("sun.security.provider.DSA$SHA1withDSA", bool);
/*  272 */     signatureInfo.put("sun.security.rsa.RSASignature$MD2withRSA", bool);
/*  273 */     signatureInfo.put("sun.security.rsa.RSASignature$MD5withRSA", bool);
/*  274 */     signatureInfo.put("sun.security.rsa.RSASignature$SHA1withRSA", bool);
/*  275 */     signatureInfo.put("sun.security.rsa.RSASignature$SHA256withRSA", bool);
/*  276 */     signatureInfo.put("sun.security.rsa.RSASignature$SHA384withRSA", bool);
/*  277 */     signatureInfo.put("sun.security.rsa.RSASignature$SHA512withRSA", bool);
/*  278 */     signatureInfo.put("com.sun.net.ssl.internal.ssl.RSASignature", bool);
/*  279 */     signatureInfo.put("sun.security.pkcs11.P11Signature", bool);
/*      */   }
/*      */   
/*      */   private static boolean isSpi(Provider.Service paramService) {
/*  283 */     if (paramService.getType().equals("Cipher"))
/*      */     {
/*  285 */       return true;
/*      */     }
/*  287 */     String str = paramService.getClassName();
/*  288 */     Boolean bool = signatureInfo.get(str);
/*  289 */     if (bool == null) {
/*      */       try {
/*  291 */         Object object = paramService.newInstance(null);
/*      */ 
/*      */ 
/*      */         
/*  295 */         boolean bool1 = (object instanceof SignatureSpi && !(object instanceof Signature)) ? true : false;
/*      */         
/*  297 */         if (debug != null && !bool1) {
/*  298 */           debug.println("Not a SignatureSpi " + str);
/*  299 */           debug.println("Delayed provider selection may not be available for algorithm " + paramService
/*  300 */               .getAlgorithm());
/*      */         } 
/*  302 */         bool = Boolean.valueOf(bool1);
/*  303 */         signatureInfo.put(str, bool);
/*  304 */       } catch (Exception exception) {
/*      */         
/*  306 */         return false;
/*      */       } 
/*      */     }
/*  309 */     return bool.booleanValue();
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
/*      */   public static Signature getInstance(String paramString1, String paramString2) throws NoSuchAlgorithmException, NoSuchProviderException {
/*  348 */     if (paramString1.equalsIgnoreCase("NONEwithRSA")) {
/*      */       
/*  350 */       if (paramString2 == null || paramString2.length() == 0) {
/*  351 */         throw new IllegalArgumentException("missing provider");
/*      */       }
/*  353 */       Provider provider = Security.getProvider(paramString2);
/*  354 */       if (provider == null) {
/*  355 */         throw new NoSuchProviderException("no such provider: " + paramString2);
/*      */       }
/*      */       
/*  358 */       return getInstanceRSA(provider);
/*      */     } 
/*      */     
/*  361 */     GetInstance.Instance instance = GetInstance.getInstance("Signature", SignatureSpi.class, paramString1, paramString2);
/*  362 */     return getInstance(instance, paramString1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Signature getInstance(String paramString, Provider paramProvider) throws NoSuchAlgorithmException {
/*  396 */     if (paramString.equalsIgnoreCase("NONEwithRSA")) {
/*      */       
/*  398 */       if (paramProvider == null) {
/*  399 */         throw new IllegalArgumentException("missing provider");
/*      */       }
/*  401 */       return getInstanceRSA(paramProvider);
/*      */     } 
/*      */     
/*  404 */     GetInstance.Instance instance = GetInstance.getInstance("Signature", SignatureSpi.class, paramString, paramProvider);
/*  405 */     return getInstance(instance, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Signature getInstanceRSA(Provider paramProvider) throws NoSuchAlgorithmException {
/*  413 */     Provider.Service service = paramProvider.getService("Signature", "NONEwithRSA");
/*  414 */     if (service != null) {
/*  415 */       GetInstance.Instance instance = GetInstance.getInstance(service, SignatureSpi.class);
/*  416 */       return getInstance(instance, "NONEwithRSA");
/*      */     } 
/*      */     
/*      */     try {
/*  420 */       Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", paramProvider);
/*  421 */       return new Delegate(new CipherAdapter(cipher), "NONEwithRSA");
/*  422 */     } catch (GeneralSecurityException generalSecurityException) {
/*      */ 
/*      */       
/*  425 */       throw new NoSuchAlgorithmException("no such algorithm: NONEwithRSA for provider " + paramProvider
/*  426 */           .getName(), generalSecurityException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Provider getProvider() {
/*  436 */     chooseFirstProvider();
/*  437 */     return this.provider;
/*      */   }
/*      */   
/*      */   private String getProviderName() {
/*  441 */     return (this.provider == null) ? "(no provider)" : this.provider.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void chooseFirstProvider() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void initVerify(PublicKey paramPublicKey) throws InvalidKeyException {
/*  460 */     engineInitVerify(paramPublicKey);
/*  461 */     this.state = 3;
/*      */     
/*  463 */     if (!skipDebug && pdebug != null) {
/*  464 */       pdebug.println("Signature." + this.algorithm + " verification algorithm from: " + 
/*  465 */           getProviderName());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void initVerify(Certificate paramCertificate) throws InvalidKeyException {
/*  492 */     if (paramCertificate instanceof X509Certificate) {
/*      */ 
/*      */ 
/*      */       
/*  496 */       X509Certificate x509Certificate = (X509Certificate)paramCertificate;
/*  497 */       Set<String> set = x509Certificate.getCriticalExtensionOIDs();
/*      */       
/*  499 */       if (set != null && !set.isEmpty() && set
/*  500 */         .contains("2.5.29.15")) {
/*  501 */         boolean[] arrayOfBoolean = x509Certificate.getKeyUsage();
/*      */         
/*  503 */         if (arrayOfBoolean != null && !arrayOfBoolean[0]) {
/*  504 */           throw new InvalidKeyException("Wrong key usage");
/*      */         }
/*      */       } 
/*      */     } 
/*  508 */     PublicKey publicKey = paramCertificate.getPublicKey();
/*  509 */     engineInitVerify(publicKey);
/*  510 */     this.state = 3;
/*      */     
/*  512 */     if (!skipDebug && pdebug != null) {
/*  513 */       pdebug.println("Signature." + this.algorithm + " verification algorithm from: " + 
/*  514 */           getProviderName());
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
/*      */   public final void initSign(PrivateKey paramPrivateKey) throws InvalidKeyException {
/*  530 */     engineInitSign(paramPrivateKey);
/*  531 */     this.state = 2;
/*      */     
/*  533 */     if (!skipDebug && pdebug != null) {
/*  534 */       pdebug.println("Signature." + this.algorithm + " signing algorithm from: " + 
/*  535 */           getProviderName());
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
/*      */   public final void initSign(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom) throws InvalidKeyException {
/*  553 */     engineInitSign(paramPrivateKey, paramSecureRandom);
/*  554 */     this.state = 2;
/*      */     
/*  556 */     if (!skipDebug && pdebug != null) {
/*  557 */       pdebug.println("Signature." + this.algorithm + " signing algorithm from: " + 
/*  558 */           getProviderName());
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
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] sign() throws SignatureException {
/*  581 */     if (this.state == 2) {
/*  582 */       return engineSign();
/*      */     }
/*  584 */     throw new SignatureException("object not initialized for signing");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int sign(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException {
/*  618 */     if (paramArrayOfbyte == null) {
/*  619 */       throw new IllegalArgumentException("No output buffer given");
/*      */     }
/*  621 */     if (paramInt1 < 0 || paramInt2 < 0) {
/*  622 */       throw new IllegalArgumentException("offset or len is less than 0");
/*      */     }
/*  624 */     if (paramArrayOfbyte.length - paramInt1 < paramInt2) {
/*  625 */       throw new IllegalArgumentException("Output buffer too small for specified offset and length");
/*      */     }
/*      */     
/*  628 */     if (this.state != 2) {
/*  629 */       throw new SignatureException("object not initialized for signing");
/*      */     }
/*      */     
/*  632 */     return engineSign(paramArrayOfbyte, paramInt1, paramInt2);
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
/*      */   public final boolean verify(byte[] paramArrayOfbyte) throws SignatureException {
/*  654 */     if (this.state == 3) {
/*  655 */       return engineVerify(paramArrayOfbyte);
/*      */     }
/*  657 */     throw new SignatureException("object not initialized for verification");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean verify(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException {
/*  691 */     if (this.state == 3) {
/*  692 */       if (paramArrayOfbyte == null) {
/*  693 */         throw new IllegalArgumentException("signature is null");
/*      */       }
/*  695 */       if (paramInt1 < 0 || paramInt2 < 0) {
/*  696 */         throw new IllegalArgumentException("offset or length is less than 0");
/*      */       }
/*      */       
/*  699 */       if (paramArrayOfbyte.length - paramInt1 < paramInt2) {
/*  700 */         throw new IllegalArgumentException("signature too small for specified offset and length");
/*      */       }
/*      */ 
/*      */       
/*  704 */       return engineVerify(paramArrayOfbyte, paramInt1, paramInt2);
/*      */     } 
/*  706 */     throw new SignatureException("object not initialized for verification");
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
/*      */   public final void update(byte paramByte) throws SignatureException {
/*  719 */     if (this.state == 3 || this.state == 2) {
/*  720 */       engineUpdate(paramByte);
/*      */     } else {
/*  722 */       throw new SignatureException("object not initialized for signature or verification");
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
/*      */   public final void update(byte[] paramArrayOfbyte) throws SignatureException {
/*  737 */     update(paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*      */   public final void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException {
/*  753 */     if (this.state == 2 || this.state == 3) {
/*  754 */       if (paramArrayOfbyte == null) {
/*  755 */         throw new IllegalArgumentException("data is null");
/*      */       }
/*  757 */       if (paramInt1 < 0 || paramInt2 < 0) {
/*  758 */         throw new IllegalArgumentException("off or len is less than 0");
/*      */       }
/*  760 */       if (paramArrayOfbyte.length - paramInt1 < paramInt2) {
/*  761 */         throw new IllegalArgumentException("data too small for specified offset and length");
/*      */       }
/*      */       
/*  764 */       engineUpdate(paramArrayOfbyte, paramInt1, paramInt2);
/*      */     } else {
/*  766 */       throw new SignatureException("object not initialized for signature or verification");
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
/*      */   public final void update(ByteBuffer paramByteBuffer) throws SignatureException {
/*  785 */     if (this.state != 2 && this.state != 3) {
/*  786 */       throw new SignatureException("object not initialized for signature or verification");
/*      */     }
/*      */     
/*  789 */     if (paramByteBuffer == null) {
/*  790 */       throw new NullPointerException();
/*      */     }
/*  792 */     engineUpdate(paramByteBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getAlgorithm() {
/*  801 */     return this.algorithm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  812 */     String str = "";
/*  813 */     switch (this.state) {
/*      */       case 0:
/*  815 */         str = "<not initialized>";
/*      */         break;
/*      */       case 3:
/*  818 */         str = "<initialized for verifying>";
/*      */         break;
/*      */       case 2:
/*  821 */         str = "<initialized for signing>";
/*      */         break;
/*      */     } 
/*  824 */     return "Signature object: " + getAlgorithm() + str;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final void setParameter(String paramString, Object paramObject) throws InvalidParameterException {
/*  855 */     engineSetParameter(paramString, paramObject);
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
/*      */   public final void setParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/*  870 */     engineSetParameter(paramAlgorithmParameterSpec);
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
/*      */   public final AlgorithmParameters getParameters() {
/*  889 */     return engineGetParameters();
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
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final Object getParameter(String paramString) throws InvalidParameterException {
/*  918 */     return engineGetParameter(paramString);
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
/*      */   public Object clone() throws CloneNotSupportedException {
/*  930 */     if (this instanceof Cloneable) {
/*  931 */       return super.clone();
/*      */     }
/*  933 */     throw new CloneNotSupportedException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Delegate
/*      */     extends Signature
/*      */   {
/*      */     private SignatureSpi sigSpi;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Object lock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Provider.Service firstService;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Iterator<Provider.Service> serviceIterator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Delegate(SignatureSpi param1SignatureSpi, String param1String) {
/*  971 */       super(param1String);
/*  972 */       this.sigSpi = param1SignatureSpi;
/*  973 */       this.lock = null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     Delegate(Provider.Service param1Service, Iterator<Provider.Service> param1Iterator, String param1String) {
/*  979 */       super(param1String);
/*  980 */       this.firstService = param1Service;
/*  981 */       this.serviceIterator = param1Iterator;
/*  982 */       this.lock = new Object();
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
/*      */     public Object clone() throws CloneNotSupportedException {
/*  994 */       chooseFirstProvider();
/*  995 */       if (this.sigSpi instanceof Cloneable) {
/*  996 */         SignatureSpi signatureSpi = (SignatureSpi)this.sigSpi.clone();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1001 */         Delegate delegate = new Delegate(signatureSpi, this.algorithm);
/* 1002 */         delegate.provider = this.provider;
/* 1003 */         return delegate;
/*      */       } 
/* 1005 */       throw new CloneNotSupportedException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static SignatureSpi newInstance(Provider.Service param1Service) throws NoSuchAlgorithmException {
/* 1011 */       if (param1Service.getType().equals("Cipher")) {
/*      */         
/*      */         try {
/* 1014 */           Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", param1Service.getProvider());
/* 1015 */           return new Signature.CipherAdapter(cipher);
/* 1016 */         } catch (NoSuchPaddingException noSuchPaddingException) {
/* 1017 */           throw new NoSuchAlgorithmException(noSuchPaddingException);
/*      */         } 
/*      */       }
/* 1020 */       Object object = param1Service.newInstance(null);
/* 1021 */       if (!(object instanceof SignatureSpi)) {
/* 1022 */         throw new NoSuchAlgorithmException("Not a SignatureSpi: " + object
/* 1023 */             .getClass().getName());
/*      */       }
/* 1025 */       return (SignatureSpi)object;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1030 */     private static int warnCount = 10;
/*      */     
/*      */     private static final int I_PUB = 1;
/*      */     
/*      */     private static final int I_PRIV = 2;
/*      */     private static final int I_PRIV_SR = 3;
/*      */     
/*      */     void chooseFirstProvider() {
/* 1038 */       if (this.sigSpi != null) {
/*      */         return;
/*      */       }
/* 1041 */       synchronized (this.lock) {
/* 1042 */         if (this.sigSpi != null) {
/*      */           return;
/*      */         }
/* 1045 */         if (Signature.debug != null) {
/* 1046 */           int i = --warnCount;
/* 1047 */           if (i >= 0) {
/* 1048 */             Signature.debug.println("Signature.init() not first method called, disabling delayed provider selection");
/*      */             
/* 1050 */             if (i == 0) {
/* 1051 */               Signature.debug.println("Further warnings of this type will be suppressed");
/*      */             }
/*      */             
/* 1054 */             (new Exception("Debug call trace")).printStackTrace();
/*      */           } 
/*      */         } 
/* 1057 */         NoSuchAlgorithmException noSuchAlgorithmException = null;
/* 1058 */         while (this.firstService != null || this.serviceIterator.hasNext()) {
/*      */           Provider.Service service;
/* 1060 */           if (this.firstService != null) {
/* 1061 */             service = this.firstService;
/* 1062 */             this.firstService = null;
/*      */           } else {
/* 1064 */             service = this.serviceIterator.next();
/*      */           } 
/* 1066 */           if (!Signature.isSpi(service)) {
/*      */             continue;
/*      */           }
/*      */           try {
/* 1070 */             this.sigSpi = newInstance(service);
/* 1071 */             this.provider = service.getProvider();
/*      */             
/* 1073 */             this.firstService = null;
/* 1074 */             this.serviceIterator = null;
/*      */             return;
/* 1076 */           } catch (NoSuchAlgorithmException noSuchAlgorithmException1) {
/* 1077 */             noSuchAlgorithmException = noSuchAlgorithmException1;
/*      */           } 
/*      */         } 
/* 1080 */         ProviderException providerException = new ProviderException("Could not construct SignatureSpi instance");
/*      */         
/* 1082 */         if (noSuchAlgorithmException != null) {
/* 1083 */           providerException.initCause(noSuchAlgorithmException);
/*      */         }
/* 1085 */         throw providerException;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void chooseProvider(int param1Int, Key param1Key, SecureRandom param1SecureRandom) throws InvalidKeyException {
/* 1091 */       synchronized (this.lock) {
/* 1092 */         if (this.sigSpi != null) {
/* 1093 */           init(this.sigSpi, param1Int, param1Key, param1SecureRandom);
/*      */           return;
/*      */         } 
/* 1096 */         Exception exception = null;
/* 1097 */         while (this.firstService != null || this.serviceIterator.hasNext()) {
/*      */           Provider.Service service;
/* 1099 */           if (this.firstService != null) {
/* 1100 */             service = this.firstService;
/* 1101 */             this.firstService = null;
/*      */           } else {
/* 1103 */             service = this.serviceIterator.next();
/*      */           } 
/*      */           
/* 1106 */           if (!service.supportsParameter(param1Key)) {
/*      */             continue;
/*      */           }
/*      */           
/* 1110 */           if (!Signature.isSpi(service)) {
/*      */             continue;
/*      */           }
/*      */           try {
/* 1114 */             SignatureSpi signatureSpi = newInstance(service);
/* 1115 */             init(signatureSpi, param1Int, param1Key, param1SecureRandom);
/* 1116 */             this.provider = service.getProvider();
/* 1117 */             this.sigSpi = signatureSpi;
/* 1118 */             this.firstService = null;
/* 1119 */             this.serviceIterator = null;
/*      */             return;
/* 1121 */           } catch (Exception exception1) {
/*      */ 
/*      */ 
/*      */             
/* 1125 */             if (exception == null) {
/* 1126 */               exception = exception1;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 1131 */         if (exception instanceof InvalidKeyException) {
/* 1132 */           throw (InvalidKeyException)exception;
/*      */         }
/* 1134 */         if (exception instanceof RuntimeException) {
/* 1135 */           throw (RuntimeException)exception;
/*      */         }
/* 1137 */         String str = (param1Key != null) ? param1Key.getClass().getName() : "(null)";
/* 1138 */         throw new InvalidKeyException("No installed provider supports this key: " + str, exception);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void init(SignatureSpi param1SignatureSpi, int param1Int, Key param1Key, SecureRandom param1SecureRandom) throws InvalidKeyException {
/* 1150 */       switch (param1Int) {
/*      */         case 1:
/* 1152 */           param1SignatureSpi.engineInitVerify((PublicKey)param1Key);
/*      */           return;
/*      */         case 2:
/* 1155 */           param1SignatureSpi.engineInitSign((PrivateKey)param1Key);
/*      */           return;
/*      */         case 3:
/* 1158 */           param1SignatureSpi.engineInitSign((PrivateKey)param1Key, param1SecureRandom);
/*      */           return;
/*      */       } 
/* 1161 */       throw new AssertionError("Internal error: " + param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void engineInitVerify(PublicKey param1PublicKey) throws InvalidKeyException {
/* 1167 */       if (this.sigSpi != null) {
/* 1168 */         this.sigSpi.engineInitVerify(param1PublicKey);
/*      */       } else {
/* 1170 */         chooseProvider(1, param1PublicKey, null);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void engineInitSign(PrivateKey param1PrivateKey) throws InvalidKeyException {
/* 1176 */       if (this.sigSpi != null) {
/* 1177 */         this.sigSpi.engineInitSign(param1PrivateKey);
/*      */       } else {
/* 1179 */         chooseProvider(2, param1PrivateKey, null);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void engineInitSign(PrivateKey param1PrivateKey, SecureRandom param1SecureRandom) throws InvalidKeyException {
/* 1185 */       if (this.sigSpi != null) {
/* 1186 */         this.sigSpi.engineInitSign(param1PrivateKey, param1SecureRandom);
/*      */       } else {
/* 1188 */         chooseProvider(3, param1PrivateKey, param1SecureRandom);
/*      */       } 
/*      */     }
/*      */     
/*      */     protected void engineUpdate(byte param1Byte) throws SignatureException {
/* 1193 */       chooseFirstProvider();
/* 1194 */       this.sigSpi.engineUpdate(param1Byte);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void engineUpdate(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws SignatureException {
/* 1199 */       chooseFirstProvider();
/* 1200 */       this.sigSpi.engineUpdate(param1ArrayOfbyte, param1Int1, param1Int2);
/*      */     }
/*      */     
/*      */     protected void engineUpdate(ByteBuffer param1ByteBuffer) {
/* 1204 */       chooseFirstProvider();
/* 1205 */       this.sigSpi.engineUpdate(param1ByteBuffer);
/*      */     }
/*      */     
/*      */     protected byte[] engineSign() throws SignatureException {
/* 1209 */       chooseFirstProvider();
/* 1210 */       return this.sigSpi.engineSign();
/*      */     }
/*      */ 
/*      */     
/*      */     protected int engineSign(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws SignatureException {
/* 1215 */       chooseFirstProvider();
/* 1216 */       return this.sigSpi.engineSign(param1ArrayOfbyte, param1Int1, param1Int2);
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean engineVerify(byte[] param1ArrayOfbyte) throws SignatureException {
/* 1221 */       chooseFirstProvider();
/* 1222 */       return this.sigSpi.engineVerify(param1ArrayOfbyte);
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean engineVerify(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws SignatureException {
/* 1227 */       chooseFirstProvider();
/* 1228 */       return this.sigSpi.engineVerify(param1ArrayOfbyte, param1Int1, param1Int2);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void engineSetParameter(String param1String, Object param1Object) throws InvalidParameterException {
/* 1233 */       chooseFirstProvider();
/* 1234 */       this.sigSpi.engineSetParameter(param1String, param1Object);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void engineSetParameter(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 1239 */       chooseFirstProvider();
/* 1240 */       this.sigSpi.engineSetParameter(param1AlgorithmParameterSpec);
/*      */     }
/*      */ 
/*      */     
/*      */     protected Object engineGetParameter(String param1String) throws InvalidParameterException {
/* 1245 */       chooseFirstProvider();
/* 1246 */       return this.sigSpi.engineGetParameter(param1String);
/*      */     }
/*      */     
/*      */     protected AlgorithmParameters engineGetParameters() {
/* 1250 */       chooseFirstProvider();
/* 1251 */       return this.sigSpi.engineGetParameters();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class CipherAdapter
/*      */     extends SignatureSpi
/*      */   {
/*      */     private final Cipher cipher;
/*      */     
/*      */     private ByteArrayOutputStream data;
/*      */     
/*      */     CipherAdapter(Cipher param1Cipher) {
/* 1264 */       this.cipher = param1Cipher;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void engineInitVerify(PublicKey param1PublicKey) throws InvalidKeyException {
/* 1269 */       this.cipher.init(2, param1PublicKey);
/* 1270 */       if (this.data == null) {
/* 1271 */         this.data = new ByteArrayOutputStream(128);
/*      */       } else {
/* 1273 */         this.data.reset();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void engineInitSign(PrivateKey param1PrivateKey) throws InvalidKeyException {
/* 1279 */       this.cipher.init(1, param1PrivateKey);
/* 1280 */       this.data = null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void engineInitSign(PrivateKey param1PrivateKey, SecureRandom param1SecureRandom) throws InvalidKeyException {
/* 1285 */       this.cipher.init(1, param1PrivateKey, param1SecureRandom);
/* 1286 */       this.data = null;
/*      */     }
/*      */     
/*      */     protected void engineUpdate(byte param1Byte) throws SignatureException {
/* 1290 */       engineUpdate(new byte[] { param1Byte }, 0, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void engineUpdate(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws SignatureException {
/* 1295 */       if (this.data != null) {
/* 1296 */         this.data.write(param1ArrayOfbyte, param1Int1, param1Int2);
/*      */         return;
/*      */       } 
/* 1299 */       byte[] arrayOfByte = this.cipher.update(param1ArrayOfbyte, param1Int1, param1Int2);
/* 1300 */       if (arrayOfByte != null && arrayOfByte.length != 0) {
/* 1301 */         throw new SignatureException("Cipher unexpectedly returned data");
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected byte[] engineSign() throws SignatureException {
/*      */       try {
/* 1308 */         return this.cipher.doFinal();
/* 1309 */       } catch (IllegalBlockSizeException illegalBlockSizeException) {
/* 1310 */         throw new SignatureException("doFinal() failed", illegalBlockSizeException);
/* 1311 */       } catch (BadPaddingException badPaddingException) {
/* 1312 */         throw new SignatureException("doFinal() failed", badPaddingException);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean engineVerify(byte[] param1ArrayOfbyte) throws SignatureException {
/*      */       try {
/* 1319 */         byte[] arrayOfByte1 = this.cipher.doFinal(param1ArrayOfbyte);
/* 1320 */         byte[] arrayOfByte2 = this.data.toByteArray();
/* 1321 */         this.data.reset();
/* 1322 */         return MessageDigest.isEqual(arrayOfByte1, arrayOfByte2);
/* 1323 */       } catch (BadPaddingException badPaddingException) {
/*      */ 
/*      */         
/* 1326 */         return false;
/* 1327 */       } catch (IllegalBlockSizeException illegalBlockSizeException) {
/* 1328 */         throw new SignatureException("doFinal() failed", illegalBlockSizeException);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void engineSetParameter(String param1String, Object param1Object) throws InvalidParameterException {
/* 1334 */       throw new InvalidParameterException("Parameters not supported");
/*      */     }
/*      */ 
/*      */     
/*      */     protected Object engineGetParameter(String param1String) throws InvalidParameterException {
/* 1339 */       throw new InvalidParameterException("Parameters not supported");
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/Signature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */