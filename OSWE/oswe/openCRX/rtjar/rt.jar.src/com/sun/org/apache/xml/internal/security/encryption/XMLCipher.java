/*      */ package com.sun.org.apache.xml.internal.security.encryption;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
/*      */ import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
/*      */ import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
/*      */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*      */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*      */ import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
/*      */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
/*      */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*      */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.EncryptedKeyResolver;
/*      */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
/*      */ import com.sun.org.apache.xml.internal.security.transforms.InvalidTransformException;
/*      */ import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
/*      */ import com.sun.org.apache.xml.internal.security.transforms.Transforms;
/*      */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*      */ import com.sun.org.apache.xml.internal.security.utils.ElementProxy;
/*      */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.InputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.security.InvalidAlgorithmParameterException;
/*      */ import java.security.InvalidKeyException;
/*      */ import java.security.Key;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.NoSuchProviderException;
/*      */ import java.security.SecureRandom;
/*      */ import java.security.spec.MGF1ParameterSpec;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.crypto.BadPaddingException;
/*      */ import javax.crypto.Cipher;
/*      */ import javax.crypto.IllegalBlockSizeException;
/*      */ import javax.crypto.NoSuchPaddingException;
/*      */ import javax.crypto.spec.IvParameterSpec;
/*      */ import javax.crypto.spec.OAEPParameterSpec;
/*      */ import javax.crypto.spec.PSource;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.w3c.dom.Text;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLCipher
/*      */ {
/*   88 */   private static Logger log = Logger.getLogger(XMLCipher.class.getName());
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String TRIPLEDES = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String AES_128 = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String AES_256 = "http://www.w3.org/2001/04/xmlenc#aes256-cbc";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String AES_192 = "http://www.w3.org/2001/04/xmlenc#aes192-cbc";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String AES_128_GCM = "http://www.w3.org/2009/xmlenc11#aes128-gcm";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String AES_192_GCM = "http://www.w3.org/2009/xmlenc11#aes192-gcm";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String AES_256_GCM = "http://www.w3.org/2009/xmlenc11#aes256-gcm";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String RSA_v1dot5 = "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String RSA_OAEP = "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String RSA_OAEP_11 = "http://www.w3.org/2009/xmlenc11#rsa-oaep";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String DIFFIE_HELLMAN = "http://www.w3.org/2001/04/xmlenc#dh";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String TRIPLEDES_KeyWrap = "http://www.w3.org/2001/04/xmlenc#kw-tripledes";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String AES_128_KeyWrap = "http://www.w3.org/2001/04/xmlenc#kw-aes128";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String AES_256_KeyWrap = "http://www.w3.org/2001/04/xmlenc#kw-aes256";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String AES_192_KeyWrap = "http://www.w3.org/2001/04/xmlenc#kw-aes192";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String SHA1 = "http://www.w3.org/2000/09/xmldsig#sha1";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String SHA256 = "http://www.w3.org/2001/04/xmlenc#sha256";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String SHA512 = "http://www.w3.org/2001/04/xmlenc#sha512";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String RIPEMD_160 = "http://www.w3.org/2001/04/xmlenc#ripemd160";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String XML_DSIG = "http://www.w3.org/2000/09/xmldsig#";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String N14C_XML = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String N14C_XML_WITH_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String EXCL_XML_N14C = "http://www.w3.org/2001/10/xml-exc-c14n#";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String EXCL_XML_N14C_WITH_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String PHYSICAL_XML_N14C = "http://santuario.apache.org/c14n/physical";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String BASE64_ENCODING = "http://www.w3.org/2000/09/xmldsig#base64";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int ENCRYPT_MODE = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int DECRYPT_MODE = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int UNWRAP_MODE = 4;
/*      */ 
/*      */   
/*      */   public static final int WRAP_MODE = 3;
/*      */ 
/*      */   
/*      */   private static final String ENC_ALGORITHMS = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes128-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes256-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes192-cbc\nhttp://www.w3.org/2001/04/xmlenc#rsa-1_5\nhttp://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p\nhttp://www.w3.org/2009/xmlenc11#rsa-oaep\nhttp://www.w3.org/2001/04/xmlenc#kw-tripledes\nhttp://www.w3.org/2001/04/xmlenc#kw-aes128\nhttp://www.w3.org/2001/04/xmlenc#kw-aes256\nhttp://www.w3.org/2001/04/xmlenc#kw-aes192\nhttp://www.w3.org/2009/xmlenc11#aes128-gcm\nhttp://www.w3.org/2009/xmlenc11#aes192-gcm\nhttp://www.w3.org/2009/xmlenc11#aes256-gcm\n";
/*      */ 
/*      */   
/*      */   private Cipher contextCipher;
/*      */ 
/*      */   
/*  216 */   private int cipherMode = Integer.MIN_VALUE;
/*      */ 
/*      */   
/*  219 */   private String algorithm = null;
/*      */ 
/*      */   
/*  222 */   private String requestedJCEProvider = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private Canonicalizer canon;
/*      */ 
/*      */   
/*      */   private Document contextDocument;
/*      */ 
/*      */   
/*      */   private Factory factory;
/*      */ 
/*      */   
/*      */   private Serializer serializer;
/*      */ 
/*      */   
/*      */   private Key key;
/*      */ 
/*      */   
/*      */   private Key kek;
/*      */ 
/*      */   
/*      */   private EncryptedKey ek;
/*      */ 
/*      */   
/*      */   private EncryptedData ed;
/*      */ 
/*      */   
/*      */   private SecureRandom random;
/*      */ 
/*      */   
/*      */   private boolean secureValidation;
/*      */ 
/*      */   
/*      */   private String digestAlg;
/*      */ 
/*      */   
/*      */   private List<KeyResolverSpi> internalKeyResolvers;
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSerializer(Serializer paramSerializer) {
/*  264 */     this.serializer = paramSerializer;
/*  265 */     paramSerializer.setCanonicalizer(this.canon);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Serializer getSerializer() {
/*  272 */     return this.serializer;
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
/*      */   private XMLCipher(String paramString1, String paramString2, String paramString3, String paramString4) throws XMLEncryptionException {
/*  294 */     if (log.isLoggable(Level.FINE)) {
/*  295 */       log.log(Level.FINE, "Constructing XMLCipher...");
/*      */     }
/*      */     
/*  298 */     this.factory = new Factory();
/*      */     
/*  300 */     this.algorithm = paramString1;
/*  301 */     this.requestedJCEProvider = paramString2;
/*  302 */     this.digestAlg = paramString4;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  308 */       if (paramString3 == null) {
/*      */         
/*  310 */         this.canon = Canonicalizer.getInstance("http://santuario.apache.org/c14n/physical");
/*      */       } else {
/*  312 */         this.canon = Canonicalizer.getInstance(paramString3);
/*      */       } 
/*  314 */     } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
/*  315 */       throw new XMLEncryptionException("empty", invalidCanonicalizerException);
/*      */     } 
/*      */     
/*  318 */     if (this.serializer == null) {
/*  319 */       this.serializer = new DocumentSerializer();
/*      */     }
/*  321 */     this.serializer.setCanonicalizer(this.canon);
/*      */     
/*  323 */     if (paramString1 != null) {
/*  324 */       this.contextCipher = constructCipher(paramString1, paramString4);
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
/*      */   private static boolean isValidEncryptionAlgorithm(String paramString) {
/*  336 */     return (paramString
/*  337 */       .equals("http://www.w3.org/2001/04/xmlenc#tripledes-cbc") || paramString
/*  338 */       .equals("http://www.w3.org/2001/04/xmlenc#aes128-cbc") || paramString
/*  339 */       .equals("http://www.w3.org/2001/04/xmlenc#aes256-cbc") || paramString
/*  340 */       .equals("http://www.w3.org/2001/04/xmlenc#aes192-cbc") || paramString
/*  341 */       .equals("http://www.w3.org/2009/xmlenc11#aes128-gcm") || paramString
/*  342 */       .equals("http://www.w3.org/2009/xmlenc11#aes192-gcm") || paramString
/*  343 */       .equals("http://www.w3.org/2009/xmlenc11#aes256-gcm") || paramString
/*  344 */       .equals("http://www.w3.org/2001/04/xmlenc#rsa-1_5") || paramString
/*  345 */       .equals("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p") || paramString
/*  346 */       .equals("http://www.w3.org/2009/xmlenc11#rsa-oaep") || paramString
/*  347 */       .equals("http://www.w3.org/2001/04/xmlenc#kw-tripledes") || paramString
/*  348 */       .equals("http://www.w3.org/2001/04/xmlenc#kw-aes128") || paramString
/*  349 */       .equals("http://www.w3.org/2001/04/xmlenc#kw-aes256") || paramString
/*  350 */       .equals("http://www.w3.org/2001/04/xmlenc#kw-aes192"));
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
/*      */   private static void validateTransformation(String paramString) {
/*  362 */     if (null == paramString) {
/*  363 */       throw new NullPointerException("Transformation unexpectedly null...");
/*      */     }
/*  365 */     if (!isValidEncryptionAlgorithm(paramString)) {
/*  366 */       log.log(Level.WARNING, "Algorithm non-standard, expected one of http://www.w3.org/2001/04/xmlenc#tripledes-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes128-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes256-cbc\nhttp://www.w3.org/2001/04/xmlenc#aes192-cbc\nhttp://www.w3.org/2001/04/xmlenc#rsa-1_5\nhttp://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p\nhttp://www.w3.org/2009/xmlenc11#rsa-oaep\nhttp://www.w3.org/2001/04/xmlenc#kw-tripledes\nhttp://www.w3.org/2001/04/xmlenc#kw-aes128\nhttp://www.w3.org/2001/04/xmlenc#kw-aes256\nhttp://www.w3.org/2001/04/xmlenc#kw-aes192\nhttp://www.w3.org/2009/xmlenc11#aes128-gcm\nhttp://www.w3.org/2009/xmlenc11#aes192-gcm\nhttp://www.w3.org/2009/xmlenc11#aes256-gcm\n");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static XMLCipher getInstance(String paramString) throws XMLEncryptionException {
/*  399 */     if (log.isLoggable(Level.FINE)) {
/*  400 */       log.log(Level.FINE, "Getting XMLCipher with transformation");
/*      */     }
/*  402 */     validateTransformation(paramString);
/*  403 */     return new XMLCipher(paramString, null, null, null);
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
/*      */   public static XMLCipher getInstance(String paramString1, String paramString2) throws XMLEncryptionException {
/*  421 */     if (log.isLoggable(Level.FINE)) {
/*  422 */       log.log(Level.FINE, "Getting XMLCipher with transformation and c14n algorithm");
/*      */     }
/*  424 */     validateTransformation(paramString1);
/*  425 */     return new XMLCipher(paramString1, null, paramString2, null);
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
/*      */   public static XMLCipher getInstance(String paramString1, String paramString2, String paramString3) throws XMLEncryptionException {
/*  444 */     if (log.isLoggable(Level.FINE)) {
/*  445 */       log.log(Level.FINE, "Getting XMLCipher with transformation and c14n algorithm");
/*      */     }
/*  447 */     validateTransformation(paramString1);
/*  448 */     return new XMLCipher(paramString1, null, paramString2, paramString3);
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
/*      */   public static XMLCipher getProviderInstance(String paramString1, String paramString2) throws XMLEncryptionException {
/*  462 */     if (log.isLoggable(Level.FINE)) {
/*  463 */       log.log(Level.FINE, "Getting XMLCipher with transformation and provider");
/*      */     }
/*  465 */     if (null == paramString2) {
/*  466 */       throw new NullPointerException("Provider unexpectedly null..");
/*      */     }
/*  468 */     validateTransformation(paramString1);
/*  469 */     return new XMLCipher(paramString1, paramString2, null, null);
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
/*      */   public static XMLCipher getProviderInstance(String paramString1, String paramString2, String paramString3) throws XMLEncryptionException {
/*  489 */     if (log.isLoggable(Level.FINE)) {
/*  490 */       log.log(Level.FINE, "Getting XMLCipher with transformation, provider and c14n algorithm");
/*      */     }
/*  492 */     if (null == paramString2) {
/*  493 */       throw new NullPointerException("Provider unexpectedly null..");
/*      */     }
/*  495 */     validateTransformation(paramString1);
/*  496 */     return new XMLCipher(paramString1, paramString2, paramString3, null);
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
/*      */   public static XMLCipher getProviderInstance(String paramString1, String paramString2, String paramString3, String paramString4) throws XMLEncryptionException {
/*  517 */     if (log.isLoggable(Level.FINE)) {
/*  518 */       log.log(Level.FINE, "Getting XMLCipher with transformation, provider and c14n algorithm");
/*      */     }
/*  520 */     if (null == paramString2) {
/*  521 */       throw new NullPointerException("Provider unexpectedly null..");
/*      */     }
/*  523 */     validateTransformation(paramString1);
/*  524 */     return new XMLCipher(paramString1, paramString2, paramString3, paramString4);
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
/*      */   public static XMLCipher getInstance() throws XMLEncryptionException {
/*  537 */     if (log.isLoggable(Level.FINE)) {
/*  538 */       log.log(Level.FINE, "Getting XMLCipher with no arguments");
/*      */     }
/*  540 */     return new XMLCipher(null, null, null, null);
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
/*      */   public static XMLCipher getProviderInstance(String paramString) throws XMLEncryptionException {
/*  557 */     if (log.isLoggable(Level.FINE)) {
/*  558 */       log.log(Level.FINE, "Getting XMLCipher with provider");
/*      */     }
/*  560 */     return new XMLCipher(null, paramString, null, null);
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
/*      */   public void init(int paramInt, Key paramKey) throws XMLEncryptionException {
/*  585 */     if (log.isLoggable(Level.FINE)) {
/*  586 */       log.log(Level.FINE, "Initializing XMLCipher...");
/*      */     }
/*      */     
/*  589 */     this.ek = null;
/*  590 */     this.ed = null;
/*      */     
/*  592 */     switch (paramInt) {
/*      */       
/*      */       case 1:
/*  595 */         if (log.isLoggable(Level.FINE)) {
/*  596 */           log.log(Level.FINE, "opmode = ENCRYPT_MODE");
/*      */         }
/*  598 */         this.ed = createEncryptedData(1, "NO VALUE YET");
/*      */         break;
/*      */       case 2:
/*  601 */         if (log.isLoggable(Level.FINE)) {
/*  602 */           log.log(Level.FINE, "opmode = DECRYPT_MODE");
/*      */         }
/*      */         break;
/*      */       case 3:
/*  606 */         if (log.isLoggable(Level.FINE)) {
/*  607 */           log.log(Level.FINE, "opmode = WRAP_MODE");
/*      */         }
/*  609 */         this.ek = createEncryptedKey(1, "NO VALUE YET");
/*      */         break;
/*      */       case 4:
/*  612 */         if (log.isLoggable(Level.FINE)) {
/*  613 */           log.log(Level.FINE, "opmode = UNWRAP_MODE");
/*      */         }
/*      */         break;
/*      */       default:
/*  617 */         log.log(Level.SEVERE, "Mode unexpectedly invalid");
/*  618 */         throw new XMLEncryptionException("Invalid mode in init");
/*      */     } 
/*      */     
/*  621 */     this.cipherMode = paramInt;
/*  622 */     this.key = paramKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSecureValidation(boolean paramBoolean) {
/*  629 */     this.secureValidation = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerInternalKeyResolver(KeyResolverSpi paramKeyResolverSpi) {
/*  640 */     if (this.internalKeyResolvers == null) {
/*  641 */       this.internalKeyResolvers = new ArrayList<>();
/*      */     }
/*  643 */     this.internalKeyResolvers.add(paramKeyResolverSpi);
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
/*      */   public EncryptedData getEncryptedData() {
/*  657 */     if (log.isLoggable(Level.FINE)) {
/*  658 */       log.log(Level.FINE, "Returning EncryptedData");
/*      */     }
/*  660 */     return this.ed;
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
/*      */   public EncryptedKey getEncryptedKey() {
/*  674 */     if (log.isLoggable(Level.FINE)) {
/*  675 */       log.log(Level.FINE, "Returning EncryptedKey");
/*      */     }
/*  677 */     return this.ek;
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
/*      */   public void setKEK(Key paramKey) {
/*  692 */     this.kek = paramKey;
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
/*      */   public Element martial(EncryptedData paramEncryptedData) {
/*  709 */     return this.factory.toElement(paramEncryptedData);
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
/*      */   public Element martial(Document paramDocument, EncryptedData paramEncryptedData) {
/*  724 */     this.contextDocument = paramDocument;
/*  725 */     return this.factory.toElement(paramEncryptedData);
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
/*      */   public Element martial(EncryptedKey paramEncryptedKey) {
/*  743 */     return this.factory.toElement(paramEncryptedKey);
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
/*      */   public Element martial(Document paramDocument, EncryptedKey paramEncryptedKey) {
/*  758 */     this.contextDocument = paramDocument;
/*  759 */     return this.factory.toElement(paramEncryptedKey);
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
/*      */   public Element martial(ReferenceList paramReferenceList) {
/*  777 */     return this.factory.toElement(paramReferenceList);
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
/*      */   public Element martial(Document paramDocument, ReferenceList paramReferenceList) {
/*  792 */     this.contextDocument = paramDocument;
/*  793 */     return this.factory.toElement(paramReferenceList);
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
/*      */   private Document encryptElement(Element paramElement) throws Exception {
/*  808 */     if (log.isLoggable(Level.FINE)) {
/*  809 */       log.log(Level.FINE, "Encrypting element...");
/*      */     }
/*  811 */     if (null == paramElement) {
/*  812 */       log.log(Level.SEVERE, "Element unexpectedly null...");
/*      */     }
/*  814 */     if (this.cipherMode != 1 && log.isLoggable(Level.FINE)) {
/*  815 */       log.log(Level.FINE, "XMLCipher unexpectedly not in ENCRYPT_MODE...");
/*      */     }
/*      */     
/*  818 */     if (this.algorithm == null) {
/*  819 */       throw new XMLEncryptionException("XMLCipher instance without transformation specified");
/*      */     }
/*  821 */     encryptData(this.contextDocument, paramElement, false);
/*      */     
/*  823 */     Element element = this.factory.toElement(this.ed);
/*      */     
/*  825 */     Node node = paramElement.getParentNode();
/*  826 */     node.replaceChild(element, paramElement);
/*      */     
/*  828 */     return this.contextDocument;
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
/*      */   private Document encryptElementContent(Element paramElement) throws Exception {
/*  846 */     if (log.isLoggable(Level.FINE)) {
/*  847 */       log.log(Level.FINE, "Encrypting element content...");
/*      */     }
/*  849 */     if (null == paramElement) {
/*  850 */       log.log(Level.SEVERE, "Element unexpectedly null...");
/*      */     }
/*  852 */     if (this.cipherMode != 1 && log.isLoggable(Level.FINE)) {
/*  853 */       log.log(Level.FINE, "XMLCipher unexpectedly not in ENCRYPT_MODE...");
/*      */     }
/*      */     
/*  856 */     if (this.algorithm == null) {
/*  857 */       throw new XMLEncryptionException("XMLCipher instance without transformation specified");
/*      */     }
/*  859 */     encryptData(this.contextDocument, paramElement, true);
/*      */     
/*  861 */     Element element = this.factory.toElement(this.ed);
/*      */     
/*  863 */     removeContent(paramElement);
/*  864 */     paramElement.appendChild(element);
/*      */     
/*  866 */     return this.contextDocument;
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
/*      */   public Document doFinal(Document paramDocument1, Document paramDocument2) throws Exception {
/*  879 */     if (log.isLoggable(Level.FINE)) {
/*  880 */       log.log(Level.FINE, "Processing source document...");
/*      */     }
/*  882 */     if (null == paramDocument1) {
/*  883 */       log.log(Level.SEVERE, "Context document unexpectedly null...");
/*      */     }
/*  885 */     if (null == paramDocument2) {
/*  886 */       log.log(Level.SEVERE, "Source document unexpectedly null...");
/*      */     }
/*      */     
/*  889 */     this.contextDocument = paramDocument1;
/*      */     
/*  891 */     Document document = null;
/*      */     
/*  893 */     switch (this.cipherMode) {
/*      */       case 2:
/*  895 */         document = decryptElement(paramDocument2.getDocumentElement());
/*      */       
/*      */       case 1:
/*  898 */         document = encryptElement(paramDocument2.getDocumentElement());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*      */       case 4:
/*  907 */         return document;
/*      */     } 
/*      */     throw new XMLEncryptionException("empty", new IllegalStateException());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document doFinal(Document paramDocument, Element paramElement) throws Exception {
/*  920 */     if (log.isLoggable(Level.FINE)) {
/*  921 */       log.log(Level.FINE, "Processing source element...");
/*      */     }
/*  923 */     if (null == paramDocument) {
/*  924 */       log.log(Level.SEVERE, "Context document unexpectedly null...");
/*      */     }
/*  926 */     if (null == paramElement) {
/*  927 */       log.log(Level.SEVERE, "Source element unexpectedly null...");
/*      */     }
/*      */     
/*  930 */     this.contextDocument = paramDocument;
/*      */     
/*  932 */     Document document = null;
/*      */     
/*  934 */     switch (this.cipherMode) {
/*      */       case 2:
/*  936 */         document = decryptElement(paramElement);
/*      */       
/*      */       case 1:
/*  939 */         document = encryptElement(paramElement);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*      */       case 4:
/*  948 */         return document;
/*      */     } 
/*      */     throw new XMLEncryptionException("empty", new IllegalStateException());
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
/*      */   public Document doFinal(Document paramDocument, Element paramElement, boolean paramBoolean) throws Exception {
/*  965 */     if (log.isLoggable(Level.FINE)) {
/*  966 */       log.log(Level.FINE, "Processing source element...");
/*      */     }
/*  968 */     if (null == paramDocument) {
/*  969 */       log.log(Level.SEVERE, "Context document unexpectedly null...");
/*      */     }
/*  971 */     if (null == paramElement) {
/*  972 */       log.log(Level.SEVERE, "Source element unexpectedly null...");
/*      */     }
/*      */     
/*  975 */     this.contextDocument = paramDocument;
/*      */     
/*  977 */     Document document = null;
/*      */     
/*  979 */     switch (this.cipherMode) {
/*      */       case 2:
/*  981 */         if (paramBoolean) {
/*  982 */           document = decryptElementContent(paramElement);
/*      */         } else {
/*  984 */           document = decryptElement(paramElement);
/*      */         } 
/*      */       
/*      */       case 1:
/*  988 */         if (paramBoolean) {
/*  989 */           document = encryptElementContent(paramElement);
/*      */         } else {
/*  991 */           document = encryptElement(paramElement);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*      */       case 4:
/* 1001 */         return document;
/*      */     } 
/*      */     throw new XMLEncryptionException("empty", new IllegalStateException());
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
/*      */   public EncryptedData encryptData(Document paramDocument, Element paramElement) throws Exception {
/* 1018 */     return encryptData(paramDocument, paramElement, false);
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
/*      */   public EncryptedData encryptData(Document paramDocument, String paramString, InputStream paramInputStream) throws Exception {
/* 1038 */     if (log.isLoggable(Level.FINE)) {
/* 1039 */       log.log(Level.FINE, "Encrypting element...");
/*      */     }
/* 1041 */     if (null == paramDocument) {
/* 1042 */       log.log(Level.SEVERE, "Context document unexpectedly null...");
/*      */     }
/* 1044 */     if (null == paramInputStream) {
/* 1045 */       log.log(Level.SEVERE, "Serialized data unexpectedly null...");
/*      */     }
/* 1047 */     if (this.cipherMode != 1 && log.isLoggable(Level.FINE)) {
/* 1048 */       log.log(Level.FINE, "XMLCipher unexpectedly not in ENCRYPT_MODE...");
/*      */     }
/*      */     
/* 1051 */     return encryptData(paramDocument, null, paramString, paramInputStream);
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
/*      */   public EncryptedData encryptData(Document paramDocument, Element paramElement, boolean paramBoolean) throws Exception {
/* 1071 */     if (log.isLoggable(Level.FINE)) {
/* 1072 */       log.log(Level.FINE, "Encrypting element...");
/*      */     }
/* 1074 */     if (null == paramDocument) {
/* 1075 */       log.log(Level.SEVERE, "Context document unexpectedly null...");
/*      */     }
/* 1077 */     if (null == paramElement) {
/* 1078 */       log.log(Level.SEVERE, "Element unexpectedly null...");
/*      */     }
/* 1080 */     if (this.cipherMode != 1 && log.isLoggable(Level.FINE)) {
/* 1081 */       log.log(Level.FINE, "XMLCipher unexpectedly not in ENCRYPT_MODE...");
/*      */     }
/*      */     
/* 1084 */     if (paramBoolean) {
/* 1085 */       return encryptData(paramDocument, paramElement, "http://www.w3.org/2001/04/xmlenc#Content", null);
/*      */     }
/* 1087 */     return encryptData(paramDocument, paramElement, "http://www.w3.org/2001/04/xmlenc#Element", null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private EncryptedData encryptData(Document paramDocument, Element paramElement, String paramString, InputStream paramInputStream) throws Exception {
/*      */     Cipher cipher;
/* 1094 */     this.contextDocument = paramDocument;
/*      */     
/* 1096 */     if (this.algorithm == null) {
/* 1097 */       throw new XMLEncryptionException("XMLCipher instance without transformation specified");
/*      */     }
/*      */     
/* 1100 */     byte[] arrayOfByte1 = null;
/* 1101 */     if (paramInputStream == null) {
/* 1102 */       if (paramString.equals("http://www.w3.org/2001/04/xmlenc#Content")) {
/* 1103 */         NodeList nodeList = paramElement.getChildNodes();
/* 1104 */         if (null != nodeList) {
/* 1105 */           arrayOfByte1 = this.serializer.serializeToByteArray(nodeList);
/*      */         } else {
/* 1107 */           Object[] arrayOfObject = { "Element has no content." };
/* 1108 */           throw new XMLEncryptionException("empty", arrayOfObject);
/*      */         } 
/*      */       } else {
/* 1111 */         arrayOfByte1 = this.serializer.serializeToByteArray(paramElement);
/*      */       } 
/* 1113 */       if (log.isLoggable(Level.FINE)) {
/* 1114 */         log.log(Level.FINE, "Serialized octets:\n" + new String(arrayOfByte1, "UTF-8"));
/*      */       }
/*      */     } 
/*      */     
/* 1118 */     byte[] arrayOfByte2 = null;
/*      */ 
/*      */ 
/*      */     
/* 1122 */     if (this.contextCipher == null) {
/* 1123 */       cipher = constructCipher(this.algorithm, null);
/*      */     } else {
/* 1125 */       cipher = this.contextCipher;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1131 */       if ("http://www.w3.org/2009/xmlenc11#aes128-gcm".equals(this.algorithm) || "http://www.w3.org/2009/xmlenc11#aes192-gcm".equals(this.algorithm) || "http://www.w3.org/2009/xmlenc11#aes256-gcm"
/* 1132 */         .equals(this.algorithm)) {
/* 1133 */         if (this.random == null) {
/* 1134 */           this.random = SecureRandom.getInstance("SHA1PRNG");
/*      */         }
/* 1136 */         byte[] arrayOfByte = new byte[12];
/* 1137 */         this.random.nextBytes(arrayOfByte);
/* 1138 */         IvParameterSpec ivParameterSpec = new IvParameterSpec(arrayOfByte);
/* 1139 */         cipher.init(this.cipherMode, this.key, ivParameterSpec);
/*      */       } else {
/* 1141 */         cipher.init(this.cipherMode, this.key);
/*      */       } 
/* 1143 */     } catch (InvalidKeyException invalidKeyException) {
/* 1144 */       throw new XMLEncryptionException("empty", invalidKeyException);
/* 1145 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 1146 */       throw new XMLEncryptionException("empty", noSuchAlgorithmException);
/*      */     } 
/*      */     
/*      */     try {
/* 1150 */       if (paramInputStream != null) {
/*      */         
/* 1152 */         byte[] arrayOfByte = new byte[8192];
/* 1153 */         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); int i;
/* 1154 */         while ((i = paramInputStream.read(arrayOfByte)) != -1) {
/* 1155 */           byte[] arrayOfByte5 = cipher.update(arrayOfByte, 0, i);
/* 1156 */           byteArrayOutputStream.write(arrayOfByte5);
/*      */         } 
/* 1158 */         byteArrayOutputStream.write(cipher.doFinal());
/* 1159 */         arrayOfByte2 = byteArrayOutputStream.toByteArray();
/*      */       } else {
/* 1161 */         arrayOfByte2 = cipher.doFinal(arrayOfByte1);
/* 1162 */         if (log.isLoggable(Level.FINE)) {
/* 1163 */           log.log(Level.FINE, "Expected cipher.outputSize = " + 
/* 1164 */               Integer.toString(cipher.getOutputSize(arrayOfByte1.length)));
/*      */         }
/*      */       } 
/* 1167 */       if (log.isLoggable(Level.FINE)) {
/* 1168 */         log.log(Level.FINE, "Actual cipher.outputSize = " + 
/* 1169 */             Integer.toString(arrayOfByte2.length));
/*      */       }
/* 1171 */     } catch (IllegalStateException illegalStateException) {
/* 1172 */       throw new XMLEncryptionException("empty", illegalStateException);
/* 1173 */     } catch (IllegalBlockSizeException illegalBlockSizeException) {
/* 1174 */       throw new XMLEncryptionException("empty", illegalBlockSizeException);
/* 1175 */     } catch (BadPaddingException badPaddingException) {
/* 1176 */       throw new XMLEncryptionException("empty", badPaddingException);
/* 1177 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 1178 */       throw new XMLEncryptionException("empty", unsupportedEncodingException);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1183 */     byte[] arrayOfByte3 = cipher.getIV();
/* 1184 */     byte[] arrayOfByte4 = new byte[arrayOfByte3.length + arrayOfByte2.length];
/* 1185 */     System.arraycopy(arrayOfByte3, 0, arrayOfByte4, 0, arrayOfByte3.length);
/* 1186 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte4, arrayOfByte3.length, arrayOfByte2.length);
/* 1187 */     String str = Base64.encode(arrayOfByte4);
/*      */     
/* 1189 */     if (log.isLoggable(Level.FINE)) {
/* 1190 */       log.log(Level.FINE, "Encrypted octets:\n" + str);
/* 1191 */       log.log(Level.FINE, "Encrypted octets length = " + str.length());
/*      */     } 
/*      */     
/*      */     try {
/* 1195 */       CipherData cipherData = this.ed.getCipherData();
/* 1196 */       CipherValue cipherValue = cipherData.getCipherValue();
/*      */       
/* 1198 */       cipherValue.setValue(str);
/*      */       
/* 1200 */       if (paramString != null) {
/* 1201 */         this.ed.setType((new URI(paramString)).toString());
/*      */       }
/*      */       
/* 1204 */       EncryptionMethod encryptionMethod = this.factory.newEncryptionMethod((new URI(this.algorithm)).toString());
/* 1205 */       encryptionMethod.setDigestAlgorithm(this.digestAlg);
/* 1206 */       this.ed.setEncryptionMethod(encryptionMethod);
/* 1207 */     } catch (URISyntaxException uRISyntaxException) {
/* 1208 */       throw new XMLEncryptionException("empty", uRISyntaxException);
/*      */     } 
/* 1210 */     return this.ed;
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
/*      */   public EncryptedData loadEncryptedData(Document paramDocument, Element paramElement) throws XMLEncryptionException {
/* 1225 */     if (log.isLoggable(Level.FINE)) {
/* 1226 */       log.log(Level.FINE, "Loading encrypted element...");
/*      */     }
/* 1228 */     if (null == paramDocument) {
/* 1229 */       throw new NullPointerException("Context document unexpectedly null...");
/*      */     }
/* 1231 */     if (null == paramElement) {
/* 1232 */       throw new NullPointerException("Element unexpectedly null...");
/*      */     }
/* 1234 */     if (this.cipherMode != 2) {
/* 1235 */       throw new XMLEncryptionException("XMLCipher unexpectedly not in DECRYPT_MODE...");
/*      */     }
/*      */     
/* 1238 */     this.contextDocument = paramDocument;
/* 1239 */     this.ed = this.factory.newEncryptedData(paramElement);
/*      */     
/* 1241 */     return this.ed;
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
/*      */   public EncryptedKey loadEncryptedKey(Document paramDocument, Element paramElement) throws XMLEncryptionException {
/* 1256 */     if (log.isLoggable(Level.FINE)) {
/* 1257 */       log.log(Level.FINE, "Loading encrypted key...");
/*      */     }
/* 1259 */     if (null == paramDocument) {
/* 1260 */       throw new NullPointerException("Context document unexpectedly null...");
/*      */     }
/* 1262 */     if (null == paramElement) {
/* 1263 */       throw new NullPointerException("Element unexpectedly null...");
/*      */     }
/* 1265 */     if (this.cipherMode != 4 && this.cipherMode != 2) {
/* 1266 */       throw new XMLEncryptionException("XMLCipher unexpectedly not in UNWRAP_MODE or DECRYPT_MODE...");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1271 */     this.contextDocument = paramDocument;
/* 1272 */     this.ek = this.factory.newEncryptedKey(paramElement);
/* 1273 */     return this.ek;
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
/*      */   public EncryptedKey loadEncryptedKey(Element paramElement) throws XMLEncryptionException {
/* 1288 */     return loadEncryptedKey(paramElement.getOwnerDocument(), paramElement);
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
/*      */   public EncryptedKey encryptKey(Document paramDocument, Key paramKey) throws XMLEncryptionException {
/* 1301 */     return encryptKey(paramDocument, paramKey, null, null);
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
/*      */   public EncryptedKey encryptKey(Document paramDocument, Key paramKey, String paramString, byte[] paramArrayOfbyte) throws XMLEncryptionException {
/*      */     Cipher cipher;
/* 1321 */     if (log.isLoggable(Level.FINE)) {
/* 1322 */       log.log(Level.FINE, "Encrypting key ...");
/*      */     }
/*      */     
/* 1325 */     if (null == paramKey) {
/* 1326 */       log.log(Level.SEVERE, "Key unexpectedly null...");
/*      */     }
/* 1328 */     if (this.cipherMode != 3) {
/* 1329 */       log.log(Level.FINE, "XMLCipher unexpectedly not in WRAP_MODE...");
/*      */     }
/* 1331 */     if (this.algorithm == null) {
/* 1332 */       throw new XMLEncryptionException("XMLCipher instance without transformation specified");
/*      */     }
/*      */     
/* 1335 */     this.contextDocument = paramDocument;
/*      */     
/* 1337 */     byte[] arrayOfByte = null;
/*      */ 
/*      */     
/* 1340 */     if (this.contextCipher == null) {
/*      */       
/* 1342 */       cipher = constructCipher(this.algorithm, null);
/*      */     } else {
/* 1344 */       cipher = this.contextCipher;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1352 */       OAEPParameterSpec oAEPParameterSpec = constructOAEPParameters(this.algorithm, this.digestAlg, paramString, paramArrayOfbyte);
/*      */ 
/*      */       
/* 1355 */       if (oAEPParameterSpec == null) {
/* 1356 */         cipher.init(3, this.key);
/*      */       } else {
/* 1358 */         cipher.init(3, this.key, oAEPParameterSpec);
/*      */       } 
/* 1360 */       arrayOfByte = cipher.wrap(paramKey);
/* 1361 */     } catch (InvalidKeyException invalidKeyException) {
/* 1362 */       throw new XMLEncryptionException("empty", invalidKeyException);
/* 1363 */     } catch (IllegalBlockSizeException illegalBlockSizeException) {
/* 1364 */       throw new XMLEncryptionException("empty", illegalBlockSizeException);
/* 1365 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/* 1366 */       throw new XMLEncryptionException("empty", invalidAlgorithmParameterException);
/*      */     } 
/*      */     
/* 1369 */     String str = Base64.encode(arrayOfByte);
/* 1370 */     if (log.isLoggable(Level.FINE)) {
/* 1371 */       log.log(Level.FINE, "Encrypted key octets:\n" + str);
/* 1372 */       log.log(Level.FINE, "Encrypted key octets length = " + str.length());
/*      */     } 
/*      */     
/* 1375 */     CipherValue cipherValue = this.ek.getCipherData().getCipherValue();
/* 1376 */     cipherValue.setValue(str);
/*      */     
/*      */     try {
/* 1379 */       EncryptionMethod encryptionMethod = this.factory.newEncryptionMethod((new URI(this.algorithm)).toString());
/* 1380 */       encryptionMethod.setDigestAlgorithm(this.digestAlg);
/* 1381 */       encryptionMethod.setMGFAlgorithm(paramString);
/* 1382 */       encryptionMethod.setOAEPparams(paramArrayOfbyte);
/* 1383 */       this.ek.setEncryptionMethod(encryptionMethod);
/* 1384 */     } catch (URISyntaxException uRISyntaxException) {
/* 1385 */       throw new XMLEncryptionException("empty", uRISyntaxException);
/*      */     } 
/* 1387 */     return this.ek;
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
/*      */   public Key decryptKey(EncryptedKey paramEncryptedKey, String paramString) throws XMLEncryptionException {
/*      */     Cipher cipher;
/*      */     Key key;
/* 1401 */     if (log.isLoggable(Level.FINE)) {
/* 1402 */       log.log(Level.FINE, "Decrypting key from previously loaded EncryptedKey...");
/*      */     }
/*      */     
/* 1405 */     if (this.cipherMode != 4 && log.isLoggable(Level.FINE)) {
/* 1406 */       log.log(Level.FINE, "XMLCipher unexpectedly not in UNWRAP_MODE...");
/*      */     }
/*      */     
/* 1409 */     if (paramString == null) {
/* 1410 */       throw new XMLEncryptionException("Cannot decrypt a key without knowing the algorithm");
/*      */     }
/*      */     
/* 1413 */     if (this.key == null) {
/* 1414 */       if (log.isLoggable(Level.FINE)) {
/* 1415 */         log.log(Level.FINE, "Trying to find a KEK via key resolvers");
/*      */       }
/*      */       
/* 1418 */       KeyInfo keyInfo = paramEncryptedKey.getKeyInfo();
/* 1419 */       if (keyInfo != null) {
/* 1420 */         keyInfo.setSecureValidation(this.secureValidation);
/*      */         try {
/* 1422 */           String str1 = paramEncryptedKey.getEncryptionMethod().getAlgorithm();
/* 1423 */           String str2 = JCEMapper.getJCEKeyAlgorithmFromURI(str1);
/* 1424 */           if ("RSA".equals(str2)) {
/* 1425 */             this.key = keyInfo.getPrivateKey();
/*      */           } else {
/* 1427 */             this.key = keyInfo.getSecretKey();
/*      */           }
/*      */         
/* 1430 */         } catch (Exception exception) {
/* 1431 */           if (log.isLoggable(Level.FINE)) {
/* 1432 */             log.log(Level.FINE, exception.getMessage(), exception);
/*      */           }
/*      */         } 
/*      */       } 
/* 1436 */       if (this.key == null) {
/* 1437 */         log.log(Level.SEVERE, "XMLCipher::decryptKey called without a KEK and cannot resolve");
/* 1438 */         throw new XMLEncryptionException("Unable to decrypt without a KEK");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1443 */     XMLCipherInput xMLCipherInput = new XMLCipherInput(paramEncryptedKey);
/* 1444 */     xMLCipherInput.setSecureValidation(this.secureValidation);
/* 1445 */     byte[] arrayOfByte = xMLCipherInput.getBytes();
/*      */     
/* 1447 */     String str = JCEMapper.getJCEKeyAlgorithmFromURI(paramString);
/* 1448 */     if (log.isLoggable(Level.FINE)) {
/* 1449 */       log.log(Level.FINE, "JCE Key Algorithm: " + str);
/*      */     }
/*      */ 
/*      */     
/* 1453 */     if (this.contextCipher == null) {
/*      */ 
/*      */       
/* 1456 */       cipher = constructCipher(paramEncryptedKey
/* 1457 */           .getEncryptionMethod().getAlgorithm(), paramEncryptedKey
/* 1458 */           .getEncryptionMethod().getDigestAlgorithm());
/*      */     } else {
/*      */       
/* 1461 */       cipher = this.contextCipher;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1467 */       EncryptionMethod encryptionMethod = paramEncryptedKey.getEncryptionMethod();
/*      */       
/* 1469 */       OAEPParameterSpec oAEPParameterSpec = constructOAEPParameters(encryptionMethod
/* 1470 */           .getAlgorithm(), encryptionMethod.getDigestAlgorithm(), encryptionMethod
/* 1471 */           .getMGFAlgorithm(), encryptionMethod.getOAEPparams());
/*      */       
/* 1473 */       if (oAEPParameterSpec == null) {
/* 1474 */         cipher.init(4, this.key);
/*      */       } else {
/* 1476 */         cipher.init(4, this.key, oAEPParameterSpec);
/*      */       } 
/* 1478 */       key = cipher.unwrap(arrayOfByte, str, 3);
/* 1479 */     } catch (InvalidKeyException invalidKeyException) {
/* 1480 */       throw new XMLEncryptionException("empty", invalidKeyException);
/* 1481 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 1482 */       throw new XMLEncryptionException("empty", noSuchAlgorithmException);
/* 1483 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/* 1484 */       throw new XMLEncryptionException("empty", invalidAlgorithmParameterException);
/*      */     } 
/* 1486 */     if (log.isLoggable(Level.FINE)) {
/* 1487 */       log.log(Level.FINE, "Decryption of key type " + paramString + " OK");
/*      */     }
/*      */     
/* 1490 */     return key;
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
/*      */   private OAEPParameterSpec constructOAEPParameters(String paramString1, String paramString2, String paramString3, byte[] paramArrayOfbyte) {
/* 1502 */     if ("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(paramString1) || "http://www.w3.org/2009/xmlenc11#rsa-oaep"
/* 1503 */       .equals(paramString1)) {
/*      */       
/* 1505 */       String str = "SHA-1";
/* 1506 */       if (paramString2 != null) {
/* 1507 */         str = JCEMapper.translateURItoJCEID(paramString2);
/*      */       }
/*      */       
/* 1510 */       PSource.PSpecified pSpecified = PSource.PSpecified.DEFAULT;
/* 1511 */       if (paramArrayOfbyte != null) {
/* 1512 */         pSpecified = new PSource.PSpecified(paramArrayOfbyte);
/*      */       }
/*      */       
/* 1515 */       MGF1ParameterSpec mGF1ParameterSpec = new MGF1ParameterSpec("SHA-1");
/* 1516 */       if ("http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(paramString1)) {
/* 1517 */         if ("http://www.w3.org/2009/xmlenc11#mgf1sha256".equals(paramString3)) {
/* 1518 */           mGF1ParameterSpec = new MGF1ParameterSpec("SHA-256");
/* 1519 */         } else if ("http://www.w3.org/2009/xmlenc11#mgf1sha384".equals(paramString3)) {
/* 1520 */           mGF1ParameterSpec = new MGF1ParameterSpec("SHA-384");
/* 1521 */         } else if ("http://www.w3.org/2009/xmlenc11#mgf1sha512".equals(paramString3)) {
/* 1522 */           mGF1ParameterSpec = new MGF1ParameterSpec("SHA-512");
/*      */         } 
/*      */       }
/* 1525 */       return new OAEPParameterSpec(str, "MGF1", mGF1ParameterSpec, pSpecified);
/*      */     } 
/*      */     
/* 1528 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Cipher constructCipher(String paramString1, String paramString2) throws XMLEncryptionException {
/*      */     Cipher cipher;
/* 1535 */     String str = JCEMapper.translateURItoJCEID(paramString1);
/* 1536 */     if (log.isLoggable(Level.FINE)) {
/* 1537 */       log.log(Level.FINE, "JCE Algorithm = " + str);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1542 */       if (this.requestedJCEProvider == null) {
/* 1543 */         cipher = Cipher.getInstance(str);
/*      */       } else {
/* 1545 */         cipher = Cipher.getInstance(str, this.requestedJCEProvider);
/*      */       } 
/* 1547 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*      */ 
/*      */       
/* 1550 */       if ("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(paramString1) && (paramString2 == null || "http://www.w3.org/2000/09/xmldsig#sha1"
/*      */         
/* 1552 */         .equals(paramString2))) {
/*      */         try {
/* 1554 */           if (this.requestedJCEProvider == null) {
/* 1555 */             cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
/*      */           } else {
/* 1557 */             cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding", this.requestedJCEProvider);
/*      */           } 
/* 1559 */         } catch (Exception exception) {
/* 1560 */           throw new XMLEncryptionException("empty", exception);
/*      */         } 
/*      */       } else {
/* 1563 */         throw new XMLEncryptionException("empty", noSuchAlgorithmException);
/*      */       } 
/* 1565 */     } catch (NoSuchProviderException noSuchProviderException) {
/* 1566 */       throw new XMLEncryptionException("empty", noSuchProviderException);
/* 1567 */     } catch (NoSuchPaddingException noSuchPaddingException) {
/* 1568 */       throw new XMLEncryptionException("empty", noSuchPaddingException);
/*      */     } 
/*      */     
/* 1571 */     return cipher;
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
/*      */   public Key decryptKey(EncryptedKey paramEncryptedKey) throws XMLEncryptionException {
/* 1586 */     return decryptKey(paramEncryptedKey, this.ed.getEncryptionMethod().getAlgorithm());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void removeContent(Node paramNode) {
/* 1595 */     while (paramNode.hasChildNodes()) {
/* 1596 */       paramNode.removeChild(paramNode.getFirstChild());
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
/*      */   private Document decryptElement(Element paramElement) throws XMLEncryptionException {
/* 1608 */     if (log.isLoggable(Level.FINE)) {
/* 1609 */       log.log(Level.FINE, "Decrypting element...");
/*      */     }
/*      */     
/* 1612 */     if (this.cipherMode != 2) {
/* 1613 */       log.log(Level.SEVERE, "XMLCipher unexpectedly not in DECRYPT_MODE...");
/*      */     }
/*      */     
/* 1616 */     byte[] arrayOfByte = decryptToByteArray(paramElement);
/*      */     
/* 1618 */     if (log.isLoggable(Level.FINE)) {
/* 1619 */       log.log(Level.FINE, "Decrypted octets:\n" + new String(arrayOfByte));
/*      */     }
/*      */     
/* 1622 */     Node node1 = paramElement.getParentNode();
/* 1623 */     Node node2 = this.serializer.deserialize(arrayOfByte, node1);
/*      */ 
/*      */     
/* 1626 */     if (node1 != null && 9 == node1.getNodeType()) {
/*      */       
/* 1628 */       this.contextDocument.removeChild(this.contextDocument.getDocumentElement());
/* 1629 */       this.contextDocument.appendChild(node2);
/* 1630 */     } else if (node1 != null) {
/* 1631 */       node1.replaceChild(node2, paramElement);
/*      */     } 
/*      */     
/* 1634 */     return this.contextDocument;
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
/*      */   private Document decryptElementContent(Element paramElement) throws XMLEncryptionException {
/* 1648 */     Element element = (Element)paramElement.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptedData").item(0);
/*      */     
/* 1650 */     if (null == element) {
/* 1651 */       throw new XMLEncryptionException("No EncryptedData child element.");
/*      */     }
/*      */     
/* 1654 */     return decryptElement(element);
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
/*      */   public byte[] decryptToByteArray(Element paramElement) throws XMLEncryptionException {
/*      */     Cipher cipher;
/* 1669 */     if (log.isLoggable(Level.FINE)) {
/* 1670 */       log.log(Level.FINE, "Decrypting to ByteArray...");
/*      */     }
/*      */     
/* 1673 */     if (this.cipherMode != 2) {
/* 1674 */       log.log(Level.SEVERE, "XMLCipher unexpectedly not in DECRYPT_MODE...");
/*      */     }
/*      */     
/* 1677 */     EncryptedData encryptedData = this.factory.newEncryptedData(paramElement);
/*      */     
/* 1679 */     if (this.key == null) {
/* 1680 */       KeyInfo keyInfo = encryptedData.getKeyInfo();
/* 1681 */       if (keyInfo != null) {
/*      */         
/*      */         try {
/* 1684 */           String str = encryptedData.getEncryptionMethod().getAlgorithm();
/* 1685 */           EncryptedKeyResolver encryptedKeyResolver = new EncryptedKeyResolver(str, this.kek);
/* 1686 */           if (this.internalKeyResolvers != null) {
/* 1687 */             int j = this.internalKeyResolvers.size();
/* 1688 */             for (byte b = 0; b < j; b++) {
/* 1689 */               encryptedKeyResolver.registerInternalKeyResolver(this.internalKeyResolvers.get(b));
/*      */             }
/*      */           } 
/* 1692 */           keyInfo.registerInternalKeyResolver((KeyResolverSpi)encryptedKeyResolver);
/* 1693 */           keyInfo.setSecureValidation(this.secureValidation);
/* 1694 */           this.key = keyInfo.getSecretKey();
/* 1695 */         } catch (KeyResolverException keyResolverException) {
/* 1696 */           if (log.isLoggable(Level.FINE)) {
/* 1697 */             log.log(Level.FINE, keyResolverException.getMessage(), keyResolverException);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 1702 */       if (this.key == null) {
/* 1703 */         log.log(Level.SEVERE, "XMLCipher::decryptElement called without a key and unable to resolve");
/*      */ 
/*      */         
/* 1706 */         throw new XMLEncryptionException("encryption.nokey");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1711 */     XMLCipherInput xMLCipherInput = new XMLCipherInput(encryptedData);
/* 1712 */     xMLCipherInput.setSecureValidation(this.secureValidation);
/* 1713 */     byte[] arrayOfByte1 = xMLCipherInput.getBytes();
/*      */ 
/*      */ 
/*      */     
/* 1717 */     String str1 = JCEMapper.translateURItoJCEID(encryptedData.getEncryptionMethod().getAlgorithm());
/* 1718 */     if (log.isLoggable(Level.FINE)) {
/* 1719 */       log.log(Level.FINE, "JCE Algorithm = " + str1);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1724 */       if (this.requestedJCEProvider == null) {
/* 1725 */         cipher = Cipher.getInstance(str1);
/*      */       } else {
/* 1727 */         cipher = Cipher.getInstance(str1, this.requestedJCEProvider);
/*      */       } 
/* 1729 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 1730 */       throw new XMLEncryptionException("empty", noSuchAlgorithmException);
/* 1731 */     } catch (NoSuchProviderException noSuchProviderException) {
/* 1732 */       throw new XMLEncryptionException("empty", noSuchProviderException);
/* 1733 */     } catch (NoSuchPaddingException noSuchPaddingException) {
/* 1734 */       throw new XMLEncryptionException("empty", noSuchPaddingException);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1742 */     int i = cipher.getBlockSize();
/* 1743 */     String str2 = encryptedData.getEncryptionMethod().getAlgorithm();
/* 1744 */     if ("http://www.w3.org/2009/xmlenc11#aes128-gcm".equals(str2) || "http://www.w3.org/2009/xmlenc11#aes192-gcm".equals(str2) || "http://www.w3.org/2009/xmlenc11#aes256-gcm".equals(str2)) {
/* 1745 */       i = 12;
/*      */     }
/* 1747 */     byte[] arrayOfByte2 = new byte[i];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1754 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
/* 1755 */     IvParameterSpec ivParameterSpec = new IvParameterSpec(arrayOfByte2);
/*      */     
/*      */     try {
/* 1758 */       cipher.init(this.cipherMode, this.key, ivParameterSpec);
/* 1759 */     } catch (InvalidKeyException invalidKeyException) {
/* 1760 */       throw new XMLEncryptionException("empty", invalidKeyException);
/* 1761 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/* 1762 */       throw new XMLEncryptionException("empty", invalidAlgorithmParameterException);
/*      */     } 
/*      */     
/*      */     try {
/* 1766 */       return cipher.doFinal(arrayOfByte1, i, arrayOfByte1.length - i);
/* 1767 */     } catch (IllegalBlockSizeException illegalBlockSizeException) {
/* 1768 */       throw new XMLEncryptionException("empty", illegalBlockSizeException);
/* 1769 */     } catch (BadPaddingException badPaddingException) {
/* 1770 */       throw new XMLEncryptionException("empty", badPaddingException);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EncryptedData createEncryptedData(int paramInt, String paramString) throws XMLEncryptionException {
/*      */     CipherReference cipherReference;
/*      */     CipherValue cipherValue;
/* 1813 */     EncryptedData encryptedData = null;
/* 1814 */     CipherData cipherData = null;
/*      */     
/* 1816 */     switch (paramInt) {
/*      */       case 2:
/* 1818 */         cipherReference = this.factory.newCipherReference(paramString);
/* 1819 */         cipherData = this.factory.newCipherData(paramInt);
/* 1820 */         cipherData.setCipherReference(cipherReference);
/* 1821 */         encryptedData = this.factory.newEncryptedData(cipherData);
/*      */         break;
/*      */       case 1:
/* 1824 */         cipherValue = this.factory.newCipherValue(paramString);
/* 1825 */         cipherData = this.factory.newCipherData(paramInt);
/* 1826 */         cipherData.setCipherValue(cipherValue);
/* 1827 */         encryptedData = this.factory.newEncryptedData(cipherData);
/*      */         break;
/*      */     } 
/* 1830 */     return encryptedData;
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
/*      */   public EncryptedKey createEncryptedKey(int paramInt, String paramString) throws XMLEncryptionException {
/*      */     CipherReference cipherReference;
/*      */     CipherValue cipherValue;
/* 1868 */     EncryptedKey encryptedKey = null;
/* 1869 */     CipherData cipherData = null;
/*      */     
/* 1871 */     switch (paramInt) {
/*      */       case 2:
/* 1873 */         cipherReference = this.factory.newCipherReference(paramString);
/* 1874 */         cipherData = this.factory.newCipherData(paramInt);
/* 1875 */         cipherData.setCipherReference(cipherReference);
/* 1876 */         encryptedKey = this.factory.newEncryptedKey(cipherData);
/*      */         break;
/*      */       case 1:
/* 1879 */         cipherValue = this.factory.newCipherValue(paramString);
/* 1880 */         cipherData = this.factory.newCipherData(paramInt);
/* 1881 */         cipherData.setCipherValue(cipherValue);
/* 1882 */         encryptedKey = this.factory.newEncryptedKey(cipherData);
/*      */         break;
/*      */     } 
/* 1885 */     return encryptedKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AgreementMethod createAgreementMethod(String paramString) {
/* 1895 */     return this.factory.newAgreementMethod(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CipherData createCipherData(int paramInt) {
/* 1906 */     return this.factory.newCipherData(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CipherReference createCipherReference(String paramString) {
/* 1916 */     return this.factory.newCipherReference(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CipherValue createCipherValue(String paramString) {
/* 1926 */     return this.factory.newCipherValue(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EncryptionMethod createEncryptionMethod(String paramString) {
/* 1936 */     return this.factory.newEncryptionMethod(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EncryptionProperties createEncryptionProperties() {
/* 1944 */     return this.factory.newEncryptionProperties();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EncryptionProperty createEncryptionProperty() {
/* 1952 */     return this.factory.newEncryptionProperty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceList createReferenceList(int paramInt) {
/* 1961 */     return this.factory.newReferenceList(paramInt);
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
/*      */   public Transforms createTransforms() {
/* 1973 */     return this.factory.newTransforms();
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
/*      */   public Transforms createTransforms(Document paramDocument) {
/* 1987 */     return this.factory.newTransforms(paramDocument);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class Factory
/*      */   {
/*      */     private Factory() {}
/*      */ 
/*      */ 
/*      */     
/*      */     AgreementMethod newAgreementMethod(String param1String) {
/* 2000 */       return new AgreementMethodImpl(param1String);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CipherData newCipherData(int param1Int) {
/* 2009 */       return new CipherDataImpl(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CipherReference newCipherReference(String param1String) {
/* 2017 */       return new CipherReferenceImpl(param1String);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CipherValue newCipherValue(String param1String) {
/* 2025 */       return new CipherValueImpl(param1String);
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
/*      */     EncryptedData newEncryptedData(CipherData param1CipherData) {
/* 2039 */       return new EncryptedDataImpl(param1CipherData);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EncryptedKey newEncryptedKey(CipherData param1CipherData) {
/* 2047 */       return new EncryptedKeyImpl(param1CipherData);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EncryptionMethod newEncryptionMethod(String param1String) {
/* 2055 */       return new EncryptionMethodImpl(param1String);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EncryptionProperties newEncryptionProperties() {
/* 2062 */       return new EncryptionPropertiesImpl();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EncryptionProperty newEncryptionProperty() {
/* 2069 */       return new EncryptionPropertyImpl();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ReferenceList newReferenceList(int param1Int) {
/* 2077 */       return new ReferenceListImpl(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Transforms newTransforms() {
/* 2084 */       return new TransformsImpl();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Transforms newTransforms(Document param1Document) {
/* 2092 */       return new TransformsImpl(param1Document);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CipherData newCipherData(Element param1Element) throws XMLEncryptionException {
/* 2101 */       if (null == param1Element) {
/* 2102 */         throw new NullPointerException("element is null");
/*      */       }
/*      */       
/* 2105 */       byte b = 0;
/* 2106 */       Element element = null;
/* 2107 */       if (param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue")
/*      */         
/* 2109 */         .getLength() > 0) {
/*      */         
/* 2111 */         b = 1;
/*      */ 
/*      */         
/* 2114 */         element = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").item(0);
/* 2115 */       } else if (param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherReference")
/*      */         
/* 2117 */         .getLength() > 0) {
/* 2118 */         b = 2;
/*      */ 
/*      */         
/* 2121 */         element = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherReference").item(0);
/*      */       } 
/*      */       
/* 2124 */       CipherData cipherData = newCipherData(b);
/* 2125 */       if (b == 1) {
/* 2126 */         cipherData.setCipherValue(newCipherValue(element));
/* 2127 */       } else if (b == 2) {
/* 2128 */         cipherData.setCipherReference(newCipherReference(element));
/*      */       } 
/*      */       
/* 2131 */       return cipherData;
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
/*      */     CipherReference newCipherReference(Element param1Element) throws XMLEncryptionException {
/* 2143 */       Attr attr = param1Element.getAttributeNodeNS((String)null, "URI");
/* 2144 */       CipherReferenceImpl cipherReferenceImpl = new CipherReferenceImpl(attr);
/*      */ 
/*      */ 
/*      */       
/* 2148 */       NodeList nodeList = param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "Transforms");
/*      */       
/* 2150 */       Element element = (Element)nodeList.item(0);
/*      */       
/* 2152 */       if (element != null) {
/* 2153 */         if (XMLCipher.log.isLoggable(Level.FINE)) {
/* 2154 */           XMLCipher.log.log(Level.FINE, "Creating a DSIG based Transforms element");
/*      */         }
/*      */         try {
/* 2157 */           cipherReferenceImpl.setTransforms(new TransformsImpl(element));
/* 2158 */         } catch (XMLSignatureException xMLSignatureException) {
/* 2159 */           throw new XMLEncryptionException("empty", xMLSignatureException);
/* 2160 */         } catch (InvalidTransformException invalidTransformException) {
/* 2161 */           throw new XMLEncryptionException("empty", invalidTransformException);
/* 2162 */         } catch (XMLSecurityException xMLSecurityException) {
/* 2163 */           throw new XMLEncryptionException("empty", xMLSecurityException);
/*      */         } 
/*      */       } 
/*      */       
/* 2167 */       return cipherReferenceImpl;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CipherValue newCipherValue(Element param1Element) {
/* 2175 */       String str = XMLUtils.getFullTextChildrenFromElement(param1Element);
/*      */       
/* 2177 */       return newCipherValue(str);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EncryptedData newEncryptedData(Element param1Element) throws XMLEncryptionException {
/* 2187 */       EncryptedData encryptedData = null;
/*      */ 
/*      */       
/* 2190 */       NodeList nodeList = param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherData");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2197 */       Element element1 = (Element)nodeList.item(nodeList.getLength() - 1);
/*      */       
/* 2199 */       CipherData cipherData = newCipherData(element1);
/*      */       
/* 2201 */       encryptedData = newEncryptedData(cipherData);
/*      */       
/* 2203 */       encryptedData.setId(param1Element.getAttributeNS((String)null, "Id"));
/* 2204 */       encryptedData.setType(param1Element.getAttributeNS((String)null, "Type"));
/* 2205 */       encryptedData.setMimeType(param1Element.getAttributeNS((String)null, "MimeType"));
/* 2206 */       encryptedData.setEncoding(param1Element.getAttributeNS((String)null, "Encoding"));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2211 */       Element element2 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod").item(0);
/* 2212 */       if (null != element2) {
/* 2213 */         encryptedData.setEncryptionMethod(newEncryptionMethod(element2));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2221 */       Element element3 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "KeyInfo").item(0);
/* 2222 */       if (null != element3) {
/* 2223 */         KeyInfo keyInfo = newKeyInfo(element3);
/* 2224 */         encryptedData.setKeyInfo(keyInfo);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2231 */       Element element4 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties").item(0);
/* 2232 */       if (null != element4) {
/* 2233 */         encryptedData.setEncryptionProperties(
/* 2234 */             newEncryptionProperties(element4));
/*      */       }
/*      */ 
/*      */       
/* 2238 */       return encryptedData;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EncryptedKey newEncryptedKey(Element param1Element) throws XMLEncryptionException {
/* 2247 */       EncryptedKey encryptedKey = null;
/*      */       
/* 2249 */       NodeList nodeList = param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherData");
/*      */ 
/*      */       
/* 2252 */       Element element1 = (Element)nodeList.item(nodeList.getLength() - 1);
/*      */       
/* 2254 */       CipherData cipherData = newCipherData(element1);
/* 2255 */       encryptedKey = newEncryptedKey(cipherData);
/*      */       
/* 2257 */       encryptedKey.setId(param1Element.getAttributeNS((String)null, "Id"));
/* 2258 */       encryptedKey.setType(param1Element.getAttributeNS((String)null, "Type"));
/* 2259 */       encryptedKey.setMimeType(param1Element.getAttributeNS((String)null, "MimeType"));
/* 2260 */       encryptedKey.setEncoding(param1Element.getAttributeNS((String)null, "Encoding"));
/* 2261 */       encryptedKey.setRecipient(param1Element.getAttributeNS((String)null, "Recipient"));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2266 */       Element element2 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod").item(0);
/* 2267 */       if (null != element2) {
/* 2268 */         encryptedKey.setEncryptionMethod(newEncryptionMethod(element2));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2273 */       Element element3 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "KeyInfo").item(0);
/* 2274 */       if (null != element3) {
/* 2275 */         KeyInfo keyInfo = newKeyInfo(element3);
/* 2276 */         encryptedKey.setKeyInfo(keyInfo);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2283 */       Element element4 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties").item(0);
/* 2284 */       if (null != element4) {
/* 2285 */         encryptedKey.setEncryptionProperties(
/* 2286 */             newEncryptionProperties(element4));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2293 */       Element element5 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "ReferenceList").item(0);
/* 2294 */       if (null != element5) {
/* 2295 */         encryptedKey.setReferenceList(newReferenceList(element5));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2301 */       Element element6 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CarriedKeyName").item(0);
/* 2302 */       if (null != element6) {
/* 2303 */         encryptedKey.setCarriedName(element6.getFirstChild().getNodeValue());
/*      */       }
/*      */       
/* 2306 */       return encryptedKey;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     KeyInfo newKeyInfo(Element param1Element) throws XMLEncryptionException {
/*      */       try {
/* 2316 */         KeyInfo keyInfo = new KeyInfo(param1Element, null);
/* 2317 */         keyInfo.setSecureValidation(XMLCipher.this.secureValidation);
/* 2318 */         if (XMLCipher.this.internalKeyResolvers != null) {
/* 2319 */           int i = XMLCipher.this.internalKeyResolvers.size();
/* 2320 */           for (byte b = 0; b < i; b++) {
/* 2321 */             keyInfo.registerInternalKeyResolver(XMLCipher.this.internalKeyResolvers.get(b));
/*      */           }
/*      */         } 
/* 2324 */         return keyInfo;
/* 2325 */       } catch (XMLSecurityException xMLSecurityException) {
/* 2326 */         throw new XMLEncryptionException("Error loading Key Info", xMLSecurityException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EncryptionMethod newEncryptionMethod(Element param1Element) {
/* 2335 */       String str = param1Element.getAttributeNS((String)null, "Algorithm");
/* 2336 */       EncryptionMethod encryptionMethod = newEncryptionMethod(str);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2341 */       Element element1 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "KeySize").item(0);
/* 2342 */       if (null != element1) {
/* 2343 */         encryptionMethod.setKeySize(
/* 2344 */             Integer.valueOf(element1
/* 2345 */               .getFirstChild().getNodeValue()).intValue());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2351 */       Element element2 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "OAEPparams").item(0);
/* 2352 */       if (null != element2) {
/*      */         try {
/* 2354 */           String str1 = element2.getFirstChild().getNodeValue();
/* 2355 */           encryptionMethod.setOAEPparams(Base64.decode(str1.getBytes("UTF-8")));
/* 2356 */         } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 2357 */           throw new RuntimeException("UTF-8 not supported", unsupportedEncodingException);
/* 2358 */         } catch (Base64DecodingException base64DecodingException) {
/* 2359 */           throw new RuntimeException("BASE-64 decoding error", base64DecodingException);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2365 */       Element element3 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "DigestMethod").item(0);
/* 2366 */       if (element3 != null) {
/* 2367 */         String str1 = element3.getAttributeNS((String)null, "Algorithm");
/* 2368 */         encryptionMethod.setDigestAlgorithm(str1);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2373 */       Element element4 = (Element)param1Element.getElementsByTagNameNS("http://www.w3.org/2009/xmlenc11#", "MGF").item(0);
/* 2374 */       if (element4 != null && !"http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(XMLCipher.this.algorithm)) {
/* 2375 */         String str1 = element4.getAttributeNS((String)null, "Algorithm");
/* 2376 */         encryptionMethod.setMGFAlgorithm(str1);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2382 */       return encryptionMethod;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EncryptionProperties newEncryptionProperties(Element param1Element) {
/* 2390 */       EncryptionProperties encryptionProperties = newEncryptionProperties();
/*      */       
/* 2392 */       encryptionProperties.setId(param1Element.getAttributeNS((String)null, "Id"));
/*      */ 
/*      */       
/* 2395 */       NodeList nodeList = param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperty");
/*      */ 
/*      */       
/* 2398 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/* 2399 */         Node node = nodeList.item(b);
/* 2400 */         if (null != node) {
/* 2401 */           encryptionProperties.addEncryptionProperty(newEncryptionProperty((Element)node));
/*      */         }
/*      */       } 
/*      */       
/* 2405 */       return encryptionProperties;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EncryptionProperty newEncryptionProperty(Element param1Element) {
/* 2413 */       EncryptionProperty encryptionProperty = newEncryptionProperty();
/*      */       
/* 2415 */       encryptionProperty.setTarget(param1Element.getAttributeNS((String)null, "Target"));
/* 2416 */       encryptionProperty.setId(param1Element.getAttributeNS((String)null, "Id"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2423 */       return encryptionProperty;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ReferenceList newReferenceList(Element param1Element) {
/* 2431 */       byte b2, b1 = 0;
/* 2432 */       if (null != param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "DataReference")
/*      */         
/* 2434 */         .item(0)) {
/* 2435 */         b1 = 1;
/* 2436 */       } else if (null != param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "KeyReference")
/*      */         
/* 2438 */         .item(0)) {
/* 2439 */         b1 = 2;
/*      */       } 
/*      */       
/* 2442 */       ReferenceListImpl referenceListImpl = new ReferenceListImpl(b1);
/* 2443 */       NodeList nodeList = null;
/* 2444 */       switch (b1) {
/*      */         
/*      */         case 1:
/* 2447 */           nodeList = param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "DataReference");
/*      */ 
/*      */           
/* 2450 */           for (b2 = 0; b2 < nodeList.getLength(); b2++) {
/* 2451 */             String str = ((Element)nodeList.item(b2)).getAttribute("URI");
/* 2452 */             referenceListImpl.add(referenceListImpl.newDataReference(str));
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 2:
/* 2457 */           nodeList = param1Element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "KeyReference");
/*      */ 
/*      */           
/* 2460 */           for (b2 = 0; b2 < nodeList.getLength(); b2++) {
/* 2461 */             String str = ((Element)nodeList.item(b2)).getAttribute("URI");
/* 2462 */             referenceListImpl.add(referenceListImpl.newKeyReference(str));
/*      */           } 
/*      */           break;
/*      */       } 
/* 2466 */       return referenceListImpl;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Element toElement(EncryptedData param1EncryptedData) {
/* 2474 */       return ((EncryptedDataImpl)param1EncryptedData).toElement();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Element toElement(EncryptedKey param1EncryptedKey) {
/* 2482 */       return ((EncryptedKeyImpl)param1EncryptedKey).toElement();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Element toElement(ReferenceList param1ReferenceList) {
/* 2490 */       return ((ReferenceListImpl)param1ReferenceList).toElement();
/*      */     }
/*      */     
/*      */     private class AgreementMethodImpl implements AgreementMethod {
/* 2494 */       private byte[] kaNonce = null;
/* 2495 */       private List<Element> agreementMethodInformation = null;
/* 2496 */       private KeyInfo originatorKeyInfo = null;
/* 2497 */       private KeyInfo recipientKeyInfo = null;
/* 2498 */       private String algorithmURI = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AgreementMethodImpl(String param2String) {
/* 2504 */         this.agreementMethodInformation = new LinkedList<>();
/* 2505 */         URI uRI = null;
/*      */         try {
/* 2507 */           uRI = new URI(param2String);
/* 2508 */         } catch (URISyntaxException uRISyntaxException) {
/* 2509 */           throw (IllegalArgumentException)(new IllegalArgumentException())
/* 2510 */             .initCause(uRISyntaxException);
/*      */         } 
/* 2512 */         this.algorithmURI = uRI.toString();
/*      */       }
/*      */ 
/*      */       
/*      */       public byte[] getKANonce() {
/* 2517 */         return this.kaNonce;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setKANonce(byte[] param2ArrayOfbyte) {
/* 2522 */         this.kaNonce = param2ArrayOfbyte;
/*      */       }
/*      */ 
/*      */       
/*      */       public Iterator<Element> getAgreementMethodInformation() {
/* 2527 */         return this.agreementMethodInformation.iterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public void addAgreementMethodInformation(Element param2Element) {
/* 2532 */         this.agreementMethodInformation.add(param2Element);
/*      */       }
/*      */ 
/*      */       
/*      */       public void revoveAgreementMethodInformation(Element param2Element) {
/* 2537 */         this.agreementMethodInformation.remove(param2Element);
/*      */       }
/*      */ 
/*      */       
/*      */       public KeyInfo getOriginatorKeyInfo() {
/* 2542 */         return this.originatorKeyInfo;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setOriginatorKeyInfo(KeyInfo param2KeyInfo) {
/* 2547 */         this.originatorKeyInfo = param2KeyInfo;
/*      */       }
/*      */ 
/*      */       
/*      */       public KeyInfo getRecipientKeyInfo() {
/* 2552 */         return this.recipientKeyInfo;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setRecipientKeyInfo(KeyInfo param2KeyInfo) {
/* 2557 */         this.recipientKeyInfo = param2KeyInfo;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getAlgorithm() {
/* 2562 */         return this.algorithmURI;
/*      */       }
/*      */     }
/*      */     
/*      */     private class CipherDataImpl
/*      */       implements CipherData
/*      */     {
/*      */       private static final String valueMessage = "Data type is reference type.";
/*      */       private static final String referenceMessage = "Data type is value type.";
/* 2571 */       private CipherValue cipherValue = null;
/* 2572 */       private CipherReference cipherReference = null;
/* 2573 */       private int cipherType = Integer.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public CipherDataImpl(int param2Int) {
/* 2579 */         this.cipherType = param2Int;
/*      */       }
/*      */ 
/*      */       
/*      */       public CipherValue getCipherValue() {
/* 2584 */         return this.cipherValue;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setCipherValue(CipherValue param2CipherValue) throws XMLEncryptionException {
/* 2590 */         if (this.cipherType == 2) {
/* 2591 */           throw new XMLEncryptionException("empty", new UnsupportedOperationException("Data type is reference type."));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2596 */         this.cipherValue = param2CipherValue;
/*      */       }
/*      */ 
/*      */       
/*      */       public CipherReference getCipherReference() {
/* 2601 */         return this.cipherReference;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setCipherReference(CipherReference param2CipherReference) throws XMLEncryptionException {
/* 2607 */         if (this.cipherType == 1) {
/* 2608 */           throw new XMLEncryptionException("empty", new UnsupportedOperationException("Data type is value type."));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2613 */         this.cipherReference = param2CipherReference;
/*      */       }
/*      */ 
/*      */       
/*      */       public int getDataType() {
/* 2618 */         return this.cipherType;
/*      */       }
/*      */ 
/*      */       
/*      */       Element toElement() {
/* 2623 */         Element element = XMLUtils.createElementInEncryptionSpace(XMLCipher.this
/* 2624 */             .contextDocument, "CipherData");
/*      */         
/* 2626 */         if (this.cipherType == 1) {
/* 2627 */           element.appendChild(((XMLCipher.Factory.CipherValueImpl)this.cipherValue).toElement());
/* 2628 */         } else if (this.cipherType == 2) {
/* 2629 */           element.appendChild(((XMLCipher.Factory.CipherReferenceImpl)this.cipherReference).toElement());
/*      */         } 
/*      */         
/* 2632 */         return element;
/*      */       }
/*      */     }
/*      */     
/*      */     private class CipherReferenceImpl implements CipherReference {
/* 2637 */       private String referenceURI = null;
/* 2638 */       private Transforms referenceTransforms = null;
/* 2639 */       private Attr referenceNode = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public CipherReferenceImpl(String param2String) {
/* 2646 */         this.referenceURI = param2String;
/* 2647 */         this.referenceNode = null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public CipherReferenceImpl(Attr param2Attr) {
/* 2654 */         this.referenceURI = param2Attr.getNodeValue();
/* 2655 */         this.referenceNode = param2Attr;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getURI() {
/* 2660 */         return this.referenceURI;
/*      */       }
/*      */ 
/*      */       
/*      */       public Attr getURIAsAttr() {
/* 2665 */         return this.referenceNode;
/*      */       }
/*      */ 
/*      */       
/*      */       public Transforms getTransforms() {
/* 2670 */         return this.referenceTransforms;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setTransforms(Transforms param2Transforms) {
/* 2675 */         this.referenceTransforms = param2Transforms;
/*      */       }
/*      */ 
/*      */       
/*      */       Element toElement() {
/* 2680 */         Element element = XMLUtils.createElementInEncryptionSpace(XMLCipher.this
/* 2681 */             .contextDocument, "CipherReference");
/*      */         
/* 2683 */         element.setAttributeNS((String)null, "URI", this.referenceURI);
/* 2684 */         if (null != this.referenceTransforms) {
/* 2685 */           element.appendChild(((XMLCipher.Factory.TransformsImpl)this.referenceTransforms).toElement());
/*      */         }
/*      */         
/* 2688 */         return element;
/*      */       }
/*      */     }
/*      */     
/*      */     private class CipherValueImpl implements CipherValue {
/* 2693 */       private String cipherValue = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public CipherValueImpl(String param2String) {
/* 2699 */         this.cipherValue = param2String;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getValue() {
/* 2704 */         return this.cipherValue;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setValue(String param2String) {
/* 2709 */         this.cipherValue = param2String;
/*      */       }
/*      */ 
/*      */       
/*      */       Element toElement() {
/* 2714 */         Element element = XMLUtils.createElementInEncryptionSpace(XMLCipher.this
/* 2715 */             .contextDocument, "CipherValue");
/*      */         
/* 2717 */         element.appendChild(XMLCipher.this.contextDocument.createTextNode(this.cipherValue));
/*      */         
/* 2719 */         return element;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private class EncryptedDataImpl
/*      */       extends EncryptedTypeImpl
/*      */       implements EncryptedData
/*      */     {
/*      */       public EncryptedDataImpl(CipherData param2CipherData) {
/* 2729 */         super(param2CipherData);
/*      */       }
/*      */ 
/*      */       
/*      */       Element toElement() {
/* 2734 */         Element element = ElementProxy.createElementForFamily(XMLCipher.this
/* 2735 */             .contextDocument, "http://www.w3.org/2001/04/xmlenc#", "EncryptedData");
/*      */ 
/*      */ 
/*      */         
/* 2739 */         if (null != getId()) {
/* 2740 */           element.setAttributeNS((String)null, "Id", getId());
/*      */         }
/* 2742 */         if (null != getType()) {
/* 2743 */           element.setAttributeNS((String)null, "Type", getType());
/*      */         }
/* 2745 */         if (null != getMimeType()) {
/* 2746 */           element.setAttributeNS((String)null, "MimeType", 
/* 2747 */               getMimeType());
/*      */         }
/*      */         
/* 2750 */         if (null != getEncoding()) {
/* 2751 */           element.setAttributeNS((String)null, "Encoding", 
/* 2752 */               getEncoding());
/*      */         }
/*      */         
/* 2755 */         if (null != getEncryptionMethod()) {
/* 2756 */           element.appendChild(((XMLCipher.Factory.EncryptionMethodImpl)
/* 2757 */               getEncryptionMethod()).toElement());
/*      */         }
/*      */         
/* 2760 */         if (null != getKeyInfo()) {
/* 2761 */           element.appendChild(getKeyInfo().getElement().cloneNode(true));
/*      */         }
/*      */         
/* 2764 */         element.appendChild(((XMLCipher.Factory.CipherDataImpl)getCipherData()).toElement());
/* 2765 */         if (null != getEncryptionProperties()) {
/* 2766 */           element.appendChild(((XMLCipher.Factory.EncryptionPropertiesImpl)
/* 2767 */               getEncryptionProperties()).toElement());
/*      */         }
/*      */         
/* 2770 */         return element;
/*      */       }
/*      */     }
/*      */     
/*      */     private class EncryptedKeyImpl extends EncryptedTypeImpl implements EncryptedKey {
/* 2775 */       private String keyRecipient = null;
/* 2776 */       private ReferenceList referenceList = null;
/* 2777 */       private String carriedName = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public EncryptedKeyImpl(CipherData param2CipherData) {
/* 2783 */         super(param2CipherData);
/*      */       }
/*      */ 
/*      */       
/*      */       public String getRecipient() {
/* 2788 */         return this.keyRecipient;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setRecipient(String param2String) {
/* 2793 */         this.keyRecipient = param2String;
/*      */       }
/*      */ 
/*      */       
/*      */       public ReferenceList getReferenceList() {
/* 2798 */         return this.referenceList;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setReferenceList(ReferenceList param2ReferenceList) {
/* 2803 */         this.referenceList = param2ReferenceList;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getCarriedName() {
/* 2808 */         return this.carriedName;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setCarriedName(String param2String) {
/* 2813 */         this.carriedName = param2String;
/*      */       }
/*      */ 
/*      */       
/*      */       Element toElement() {
/* 2818 */         Element element = ElementProxy.createElementForFamily(XMLCipher.this
/* 2819 */             .contextDocument, "http://www.w3.org/2001/04/xmlenc#", "EncryptedKey");
/*      */ 
/*      */ 
/*      */         
/* 2823 */         if (null != getId()) {
/* 2824 */           element.setAttributeNS((String)null, "Id", getId());
/*      */         }
/* 2826 */         if (null != getType()) {
/* 2827 */           element.setAttributeNS((String)null, "Type", getType());
/*      */         }
/* 2829 */         if (null != getMimeType()) {
/* 2830 */           element.setAttributeNS((String)null, "MimeType", 
/* 2831 */               getMimeType());
/*      */         }
/*      */         
/* 2834 */         if (null != getEncoding()) {
/* 2835 */           element.setAttributeNS((String)null, "Encoding", getEncoding());
/*      */         }
/* 2837 */         if (null != getRecipient()) {
/* 2838 */           element.setAttributeNS((String)null, "Recipient", 
/* 2839 */               getRecipient());
/*      */         }
/*      */         
/* 2842 */         if (null != getEncryptionMethod()) {
/* 2843 */           element.appendChild(((XMLCipher.Factory.EncryptionMethodImpl)
/* 2844 */               getEncryptionMethod()).toElement());
/*      */         }
/* 2846 */         if (null != getKeyInfo()) {
/* 2847 */           element.appendChild(getKeyInfo().getElement().cloneNode(true));
/*      */         }
/* 2849 */         element.appendChild(((XMLCipher.Factory.CipherDataImpl)getCipherData()).toElement());
/* 2850 */         if (null != getEncryptionProperties()) {
/* 2851 */           element.appendChild(((XMLCipher.Factory.EncryptionPropertiesImpl)
/* 2852 */               getEncryptionProperties()).toElement());
/*      */         }
/* 2854 */         if (this.referenceList != null && !this.referenceList.isEmpty()) {
/* 2855 */           element.appendChild(((XMLCipher.Factory.ReferenceListImpl)getReferenceList()).toElement());
/*      */         }
/* 2857 */         if (null != this.carriedName) {
/*      */           
/* 2859 */           Element element1 = ElementProxy.createElementForFamily(XMLCipher.this
/* 2860 */               .contextDocument, "http://www.w3.org/2001/04/xmlenc#", "CarriedKeyName");
/*      */ 
/*      */ 
/*      */           
/* 2864 */           Text text = XMLCipher.this.contextDocument.createTextNode(this.carriedName);
/* 2865 */           element1.appendChild(text);
/* 2866 */           element.appendChild(element1);
/*      */         } 
/*      */         
/* 2869 */         return element;
/*      */       }
/*      */     }
/*      */     
/*      */     private abstract class EncryptedTypeImpl {
/* 2874 */       private String id = null;
/* 2875 */       private String type = null;
/* 2876 */       private String mimeType = null;
/* 2877 */       private String encoding = null;
/* 2878 */       private EncryptionMethod encryptionMethod = null;
/* 2879 */       private KeyInfo keyInfo = null;
/* 2880 */       private CipherData cipherData = null;
/* 2881 */       private EncryptionProperties encryptionProperties = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected EncryptedTypeImpl(CipherData param2CipherData) {
/* 2888 */         this.cipherData = param2CipherData;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String getId() {
/* 2896 */         return this.id;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setId(String param2String) {
/* 2904 */         this.id = param2String;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String getType() {
/* 2912 */         return this.type;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setType(String param2String) {
/* 2920 */         if (param2String == null || param2String.length() == 0) {
/* 2921 */           this.type = null;
/*      */         } else {
/* 2923 */           URI uRI = null;
/*      */           try {
/* 2925 */             uRI = new URI(param2String);
/* 2926 */           } catch (URISyntaxException uRISyntaxException) {
/* 2927 */             throw (IllegalArgumentException)(new IllegalArgumentException())
/* 2928 */               .initCause(uRISyntaxException);
/*      */           } 
/* 2930 */           this.type = uRI.toString();
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String getMimeType() {
/* 2939 */         return this.mimeType;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setMimeType(String param2String) {
/* 2946 */         this.mimeType = param2String;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String getEncoding() {
/* 2954 */         return this.encoding;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setEncoding(String param2String) {
/* 2962 */         if (param2String == null || param2String.length() == 0) {
/* 2963 */           this.encoding = null;
/*      */         } else {
/* 2965 */           URI uRI = null;
/*      */           try {
/* 2967 */             uRI = new URI(param2String);
/* 2968 */           } catch (URISyntaxException uRISyntaxException) {
/* 2969 */             throw (IllegalArgumentException)(new IllegalArgumentException())
/* 2970 */               .initCause(uRISyntaxException);
/*      */           } 
/* 2972 */           this.encoding = uRI.toString();
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public EncryptionMethod getEncryptionMethod() {
/* 2981 */         return this.encryptionMethod;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setEncryptionMethod(EncryptionMethod param2EncryptionMethod) {
/* 2989 */         this.encryptionMethod = param2EncryptionMethod;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public KeyInfo getKeyInfo() {
/* 2997 */         return this.keyInfo;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setKeyInfo(KeyInfo param2KeyInfo) {
/* 3005 */         this.keyInfo = param2KeyInfo;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public CipherData getCipherData() {
/* 3013 */         return this.cipherData;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public EncryptionProperties getEncryptionProperties() {
/* 3021 */         return this.encryptionProperties;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setEncryptionProperties(EncryptionProperties param2EncryptionProperties) {
/* 3029 */         this.encryptionProperties = param2EncryptionProperties;
/*      */       }
/*      */     }
/*      */     
/*      */     private class EncryptionMethodImpl implements EncryptionMethod {
/* 3034 */       private String algorithm = null;
/* 3035 */       private int keySize = Integer.MIN_VALUE;
/* 3036 */       private byte[] oaepParams = null;
/* 3037 */       private List<Element> encryptionMethodInformation = null;
/* 3038 */       private String digestAlgorithm = null;
/* 3039 */       private String mgfAlgorithm = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public EncryptionMethodImpl(String param2String) {
/* 3046 */         URI uRI = null;
/*      */         try {
/* 3048 */           uRI = new URI(param2String);
/* 3049 */         } catch (URISyntaxException uRISyntaxException) {
/* 3050 */           throw (IllegalArgumentException)(new IllegalArgumentException())
/* 3051 */             .initCause(uRISyntaxException);
/*      */         } 
/* 3053 */         this.algorithm = uRI.toString();
/* 3054 */         this.encryptionMethodInformation = new LinkedList<>();
/*      */       }
/*      */ 
/*      */       
/*      */       public String getAlgorithm() {
/* 3059 */         return this.algorithm;
/*      */       }
/*      */ 
/*      */       
/*      */       public int getKeySize() {
/* 3064 */         return this.keySize;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setKeySize(int param2Int) {
/* 3069 */         this.keySize = param2Int;
/*      */       }
/*      */ 
/*      */       
/*      */       public byte[] getOAEPparams() {
/* 3074 */         return this.oaepParams;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setOAEPparams(byte[] param2ArrayOfbyte) {
/* 3079 */         this.oaepParams = param2ArrayOfbyte;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setDigestAlgorithm(String param2String) {
/* 3084 */         this.digestAlgorithm = param2String;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getDigestAlgorithm() {
/* 3089 */         return this.digestAlgorithm;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setMGFAlgorithm(String param2String) {
/* 3094 */         this.mgfAlgorithm = param2String;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getMGFAlgorithm() {
/* 3099 */         return this.mgfAlgorithm;
/*      */       }
/*      */ 
/*      */       
/*      */       public Iterator<Element> getEncryptionMethodInformation() {
/* 3104 */         return this.encryptionMethodInformation.iterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public void addEncryptionMethodInformation(Element param2Element) {
/* 3109 */         this.encryptionMethodInformation.add(param2Element);
/*      */       }
/*      */ 
/*      */       
/*      */       public void removeEncryptionMethodInformation(Element param2Element) {
/* 3114 */         this.encryptionMethodInformation.remove(param2Element);
/*      */       }
/*      */ 
/*      */       
/*      */       Element toElement() {
/* 3119 */         Element element = XMLUtils.createElementInEncryptionSpace(XMLCipher.this
/* 3120 */             .contextDocument, "EncryptionMethod");
/*      */         
/* 3122 */         element.setAttributeNS((String)null, "Algorithm", this.algorithm);
/* 3123 */         if (this.keySize > 0) {
/* 3124 */           element.appendChild(
/* 3125 */               XMLUtils.createElementInEncryptionSpace(XMLCipher.this
/* 3126 */                 .contextDocument, "KeySize")
/* 3127 */               .appendChild(XMLCipher.this.contextDocument.createTextNode(String.valueOf(this.keySize))));
/*      */         }
/* 3129 */         if (null != this.oaepParams) {
/*      */           
/* 3131 */           Element element1 = XMLUtils.createElementInEncryptionSpace(XMLCipher.this
/* 3132 */               .contextDocument, "OAEPparams");
/*      */           
/* 3134 */           element1.appendChild(XMLCipher.this.contextDocument.createTextNode(Base64.encode(this.oaepParams)));
/* 3135 */           element.appendChild(element1);
/*      */         } 
/* 3137 */         if (this.digestAlgorithm != null) {
/*      */           
/* 3139 */           Element element1 = XMLUtils.createElementInSignatureSpace(XMLCipher.this.contextDocument, "DigestMethod");
/* 3140 */           element1.setAttributeNS((String)null, "Algorithm", this.digestAlgorithm);
/* 3141 */           element.appendChild(element1);
/*      */         } 
/* 3143 */         if (this.mgfAlgorithm != null) {
/*      */           
/* 3145 */           Element element1 = XMLUtils.createElementInEncryption11Space(XMLCipher.this
/* 3146 */               .contextDocument, "MGF");
/*      */           
/* 3148 */           element1.setAttributeNS((String)null, "Algorithm", this.mgfAlgorithm);
/* 3149 */           element1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + 
/*      */               
/* 3151 */               ElementProxy.getDefaultPrefix("http://www.w3.org/2009/xmlenc11#"), "http://www.w3.org/2009/xmlenc11#");
/*      */ 
/*      */           
/* 3154 */           element.appendChild(element1);
/*      */         } 
/* 3156 */         Iterator<Element> iterator = this.encryptionMethodInformation.iterator();
/* 3157 */         while (iterator.hasNext()) {
/* 3158 */           element.appendChild(iterator.next());
/*      */         }
/*      */         
/* 3161 */         return element;
/*      */       }
/*      */     }
/*      */     
/*      */     private class EncryptionPropertiesImpl implements EncryptionProperties {
/* 3166 */       private String id = null;
/* 3167 */       private List<EncryptionProperty> encryptionProperties = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public EncryptionPropertiesImpl() {
/* 3173 */         this.encryptionProperties = new LinkedList<>();
/*      */       }
/*      */ 
/*      */       
/*      */       public String getId() {
/* 3178 */         return this.id;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setId(String param2String) {
/* 3183 */         this.id = param2String;
/*      */       }
/*      */ 
/*      */       
/*      */       public Iterator<EncryptionProperty> getEncryptionProperties() {
/* 3188 */         return this.encryptionProperties.iterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public void addEncryptionProperty(EncryptionProperty param2EncryptionProperty) {
/* 3193 */         this.encryptionProperties.add(param2EncryptionProperty);
/*      */       }
/*      */ 
/*      */       
/*      */       public void removeEncryptionProperty(EncryptionProperty param2EncryptionProperty) {
/* 3198 */         this.encryptionProperties.remove(param2EncryptionProperty);
/*      */       }
/*      */ 
/*      */       
/*      */       Element toElement() {
/* 3203 */         Element element = XMLUtils.createElementInEncryptionSpace(XMLCipher.this
/* 3204 */             .contextDocument, "EncryptionProperties");
/*      */         
/* 3206 */         if (null != this.id) {
/* 3207 */           element.setAttributeNS((String)null, "Id", this.id);
/*      */         }
/* 3209 */         Iterator<EncryptionProperty> iterator = getEncryptionProperties();
/* 3210 */         while (iterator.hasNext()) {
/* 3211 */           element.appendChild(((XMLCipher.Factory.EncryptionPropertyImpl)iterator.next()).toElement());
/*      */         }
/*      */         
/* 3214 */         return element;
/*      */       }
/*      */     }
/*      */     
/*      */     private class EncryptionPropertyImpl implements EncryptionProperty {
/* 3219 */       private String target = null;
/* 3220 */       private String id = null;
/* 3221 */       private Map<String, String> attributeMap = new HashMap<>();
/* 3222 */       private List<Element> encryptionInformation = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public EncryptionPropertyImpl() {
/* 3228 */         this.encryptionInformation = new LinkedList<>();
/*      */       }
/*      */ 
/*      */       
/*      */       public String getTarget() {
/* 3233 */         return this.target;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setTarget(String param2String) {
/* 3238 */         if (param2String == null || param2String.length() == 0) {
/* 3239 */           this.target = null;
/* 3240 */         } else if (param2String.startsWith("#")) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3245 */           this.target = param2String;
/*      */         } else {
/* 3247 */           URI uRI = null;
/*      */           try {
/* 3249 */             uRI = new URI(param2String);
/* 3250 */           } catch (URISyntaxException uRISyntaxException) {
/* 3251 */             throw (IllegalArgumentException)(new IllegalArgumentException())
/* 3252 */               .initCause(uRISyntaxException);
/*      */           } 
/* 3254 */           this.target = uRI.toString();
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*      */       public String getId() {
/* 3260 */         return this.id;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setId(String param2String) {
/* 3265 */         this.id = param2String;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getAttribute(String param2String) {
/* 3270 */         return this.attributeMap.get(param2String);
/*      */       }
/*      */ 
/*      */       
/*      */       public void setAttribute(String param2String1, String param2String2) {
/* 3275 */         this.attributeMap.put(param2String1, param2String2);
/*      */       }
/*      */ 
/*      */       
/*      */       public Iterator<Element> getEncryptionInformation() {
/* 3280 */         return this.encryptionInformation.iterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public void addEncryptionInformation(Element param2Element) {
/* 3285 */         this.encryptionInformation.add(param2Element);
/*      */       }
/*      */ 
/*      */       
/*      */       public void removeEncryptionInformation(Element param2Element) {
/* 3290 */         this.encryptionInformation.remove(param2Element);
/*      */       }
/*      */ 
/*      */       
/*      */       Element toElement() {
/* 3295 */         Element element = XMLUtils.createElementInEncryptionSpace(XMLCipher.this
/* 3296 */             .contextDocument, "EncryptionProperty");
/*      */         
/* 3298 */         if (null != this.target) {
/* 3299 */           element.setAttributeNS((String)null, "Target", this.target);
/*      */         }
/* 3301 */         if (null != this.id) {
/* 3302 */           element.setAttributeNS((String)null, "Id", this.id);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 3307 */         return element;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private class TransformsImpl
/*      */       extends Transforms
/*      */       implements Transforms
/*      */     {
/*      */       public TransformsImpl() {
/* 3318 */         super(XMLCipher.this.contextDocument);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public TransformsImpl(Document param2Document) {
/* 3326 */         if (param2Document == null) {
/* 3327 */           throw new RuntimeException("Document is null");
/*      */         }
/*      */         
/* 3330 */         this.doc = param2Document;
/* 3331 */         this
/* 3332 */           .constructionElement = createElementForFamilyLocal(this.doc, 
/* 3333 */             getBaseNamespace(), getBaseLocalName());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public TransformsImpl(Element param2Element) throws XMLSignatureException, InvalidTransformException, XMLSecurityException, TransformationException {
/* 3348 */         super(param2Element, "");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Element toElement() {
/* 3356 */         if (this.doc == null) {
/* 3357 */           this.doc = XMLCipher.this.contextDocument;
/*      */         }
/*      */         
/* 3360 */         return getElement();
/*      */       }
/*      */ 
/*      */       
/*      */       public Transforms getDSTransforms() {
/* 3365 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public String getBaseNamespace() {
/* 3371 */         return "http://www.w3.org/2001/04/xmlenc#";
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private class ReferenceListImpl
/*      */       implements ReferenceList
/*      */     {
/*      */       private Class<?> sentry;
/*      */       
/*      */       private List<Reference> references;
/*      */       
/*      */       public ReferenceListImpl(int param2Int) {
/* 3384 */         if (param2Int == 1) {
/* 3385 */           this.sentry = DataReference.class;
/* 3386 */         } else if (param2Int == 2) {
/* 3387 */           this.sentry = KeyReference.class;
/*      */         } else {
/* 3389 */           throw new IllegalArgumentException();
/*      */         } 
/* 3391 */         this.references = new LinkedList<>();
/*      */       }
/*      */ 
/*      */       
/*      */       public void add(Reference param2Reference) {
/* 3396 */         if (!param2Reference.getClass().equals(this.sentry)) {
/* 3397 */           throw new IllegalArgumentException();
/*      */         }
/* 3399 */         this.references.add(param2Reference);
/*      */       }
/*      */ 
/*      */       
/*      */       public void remove(Reference param2Reference) {
/* 3404 */         if (!param2Reference.getClass().equals(this.sentry)) {
/* 3405 */           throw new IllegalArgumentException();
/*      */         }
/* 3407 */         this.references.remove(param2Reference);
/*      */       }
/*      */ 
/*      */       
/*      */       public int size() {
/* 3412 */         return this.references.size();
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isEmpty() {
/* 3417 */         return this.references.isEmpty();
/*      */       }
/*      */ 
/*      */       
/*      */       public Iterator<Reference> getReferences() {
/* 3422 */         return this.references.iterator();
/*      */       }
/*      */ 
/*      */       
/*      */       Element toElement() {
/* 3427 */         Element element = ElementProxy.createElementForFamily(XMLCipher.this
/* 3428 */             .contextDocument, "http://www.w3.org/2001/04/xmlenc#", "ReferenceList");
/*      */ 
/*      */ 
/*      */         
/* 3432 */         Iterator<Reference> iterator = this.references.iterator();
/* 3433 */         while (iterator.hasNext()) {
/* 3434 */           Reference reference = iterator.next();
/* 3435 */           element.appendChild(((ReferenceImpl)reference).toElement());
/*      */         } 
/* 3437 */         return element;
/*      */       }
/*      */ 
/*      */       
/*      */       public Reference newDataReference(String param2String) {
/* 3442 */         return new DataReference(param2String);
/*      */       }
/*      */ 
/*      */       
/*      */       public Reference newKeyReference(String param2String) {
/* 3447 */         return new KeyReference(param2String);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       private abstract class ReferenceImpl
/*      */         implements Reference
/*      */       {
/*      */         private String uri;
/*      */         
/*      */         private List<Element> referenceInformation;
/*      */ 
/*      */         
/*      */         ReferenceImpl(String param3String) {
/* 3461 */           this.uri = param3String;
/* 3462 */           this.referenceInformation = new LinkedList<>();
/*      */         }
/*      */ 
/*      */         
/*      */         public abstract String getType();
/*      */ 
/*      */         
/*      */         public String getURI() {
/* 3470 */           return this.uri;
/*      */         }
/*      */ 
/*      */         
/*      */         public Iterator<Element> getElementRetrievalInformation() {
/* 3475 */           return this.referenceInformation.iterator();
/*      */         }
/*      */ 
/*      */         
/*      */         public void setURI(String param3String) {
/* 3480 */           this.uri = param3String;
/*      */         }
/*      */ 
/*      */         
/*      */         public void removeElementRetrievalInformation(Element param3Element) {
/* 3485 */           this.referenceInformation.remove(param3Element);
/*      */         }
/*      */ 
/*      */         
/*      */         public void addElementRetrievalInformation(Element param3Element) {
/* 3490 */           this.referenceInformation.add(param3Element);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public Element toElement() {
/* 3497 */           String str = getType();
/*      */           
/* 3499 */           Element element = ElementProxy.createElementForFamily(XMLCipher.this
/* 3500 */               .contextDocument, "http://www.w3.org/2001/04/xmlenc#", str);
/*      */ 
/*      */ 
/*      */           
/* 3504 */           element.setAttribute("URI", this.uri);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3510 */           return element;
/*      */         }
/*      */       }
/*      */       
/*      */       private class DataReference
/*      */         extends ReferenceImpl {
/*      */         DataReference(String param3String) {
/* 3517 */           super(param3String);
/*      */         }
/*      */ 
/*      */         
/*      */         public String getType() {
/* 3522 */           return "DataReference";
/*      */         }
/*      */       }
/*      */       
/*      */       private class KeyReference
/*      */         extends ReferenceImpl {
/*      */         KeyReference(String param3String) {
/* 3529 */           super(param3String);
/*      */         }
/*      */ 
/*      */         
/*      */         public String getType() {
/* 3534 */           return "KeyReference";
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/encryption/XMLCipher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */