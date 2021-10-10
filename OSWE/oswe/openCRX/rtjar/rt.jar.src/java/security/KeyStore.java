/*      */ package java.security;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.CertificateException;
/*      */ import java.security.spec.AlgorithmParameterSpec;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Set;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.security.auth.DestroyFailedException;
/*      */ import javax.security.auth.Destroyable;
/*      */ import javax.security.auth.callback.Callback;
/*      */ import javax.security.auth.callback.CallbackHandler;
/*      */ import javax.security.auth.callback.PasswordCallback;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class KeyStore
/*      */ {
/*  183 */   private static final Debug pdebug = Debug.getInstance("provider", "Provider");
/*  184 */   private static final boolean skipDebug = (
/*  185 */     Debug.isOn("engine=") && !Debug.isOn("keystore"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String KEYSTORE_TYPE = "keystore.type";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String type;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Provider provider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private KeyStoreSpi keyStoreSpi;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean initialized = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class PasswordProtection
/*      */     implements ProtectionParameter, Destroyable
/*      */   {
/*      */     private final char[] password;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final String protectionAlgorithm;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final AlgorithmParameterSpec protectionParameters;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private volatile boolean destroyed = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PasswordProtection(char[] param1ArrayOfchar) {
/*  263 */       this.password = (param1ArrayOfchar == null) ? null : (char[])param1ArrayOfchar.clone();
/*  264 */       this.protectionAlgorithm = null;
/*  265 */       this.protectionParameters = null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PasswordProtection(char[] param1ArrayOfchar, String param1String, AlgorithmParameterSpec param1AlgorithmParameterSpec) {
/*  292 */       if (param1String == null) {
/*  293 */         throw new NullPointerException("invalid null input");
/*      */       }
/*  295 */       this.password = (param1ArrayOfchar == null) ? null : (char[])param1ArrayOfchar.clone();
/*  296 */       this.protectionAlgorithm = param1String;
/*  297 */       this.protectionParameters = param1AlgorithmParameterSpec;
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
/*      */ 
/*      */ 
/*      */     
/*      */     public String getProtectionAlgorithm() {
/*  317 */       return this.protectionAlgorithm;
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
/*      */     public AlgorithmParameterSpec getProtectionParameters() {
/*  329 */       return this.protectionParameters;
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
/*      */     public synchronized char[] getPassword() {
/*  346 */       if (this.destroyed) {
/*  347 */         throw new IllegalStateException("password has been cleared");
/*      */       }
/*  349 */       return this.password;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized void destroy() throws DestroyFailedException {
/*  359 */       this.destroyed = true;
/*  360 */       if (this.password != null) {
/*  361 */         Arrays.fill(this.password, ' ');
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized boolean isDestroyed() {
/*  371 */       return this.destroyed;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class CallbackHandlerProtection
/*      */     implements ProtectionParameter
/*      */   {
/*      */     private final CallbackHandler handler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CallbackHandlerProtection(CallbackHandler param1CallbackHandler) {
/*  393 */       if (param1CallbackHandler == null) {
/*  394 */         throw new NullPointerException("handler must not be null");
/*      */       }
/*  396 */       this.handler = param1CallbackHandler;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CallbackHandler getCallbackHandler() {
/*  405 */       return this.handler;
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
/*      */   public static interface Entry
/*      */   {
/*      */     default Set<Attribute> getAttributes() {
/*  427 */       return Collections.emptySet();
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
/*      */     public static interface Attribute
/*      */     {
/*      */       String getName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       String getValue();
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
/*      */   public static final class PrivateKeyEntry
/*      */     implements Entry
/*      */   {
/*      */     private final PrivateKey privKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Certificate[] chain;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Set<KeyStore.Entry.Attribute> attributes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PrivateKeyEntry(PrivateKey param1PrivateKey, Certificate[] param1ArrayOfCertificate) {
/*  491 */       this(param1PrivateKey, param1ArrayOfCertificate, Collections.emptySet());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PrivateKeyEntry(PrivateKey param1PrivateKey, Certificate[] param1ArrayOfCertificate, Set<KeyStore.Entry.Attribute> param1Set) {
/*  523 */       if (param1PrivateKey == null || param1ArrayOfCertificate == null || param1Set == null) {
/*  524 */         throw new NullPointerException("invalid null input");
/*      */       }
/*  526 */       if (param1ArrayOfCertificate.length == 0) {
/*  527 */         throw new IllegalArgumentException("invalid zero-length input chain");
/*      */       }
/*      */ 
/*      */       
/*  531 */       Certificate[] arrayOfCertificate = (Certificate[])param1ArrayOfCertificate.clone();
/*  532 */       String str = arrayOfCertificate[0].getType();
/*  533 */       for (byte b = 1; b < arrayOfCertificate.length; b++) {
/*  534 */         if (!str.equals(arrayOfCertificate[b].getType())) {
/*  535 */           throw new IllegalArgumentException("chain does not contain certificates of the same type");
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  541 */       if (!param1PrivateKey.getAlgorithm().equals(arrayOfCertificate[0].getPublicKey().getAlgorithm())) {
/*  542 */         throw new IllegalArgumentException("private key algorithm does not match algorithm of public key in end entity certificate (at index 0)");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  547 */       this.privKey = param1PrivateKey;
/*      */       
/*  549 */       if (arrayOfCertificate[0] instanceof java.security.cert.X509Certificate && !(arrayOfCertificate instanceof java.security.cert.X509Certificate[])) {
/*      */ 
/*      */         
/*  552 */         this.chain = (Certificate[])new java.security.cert.X509Certificate[arrayOfCertificate.length];
/*  553 */         System.arraycopy(arrayOfCertificate, 0, this.chain, 0, arrayOfCertificate.length);
/*      */       } else {
/*      */         
/*  556 */         this.chain = arrayOfCertificate;
/*      */       } 
/*      */       
/*  559 */       this
/*  560 */         .attributes = Collections.unmodifiableSet(new HashSet<>(param1Set));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PrivateKey getPrivateKey() {
/*  569 */       return this.privKey;
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
/*      */     public Certificate[] getCertificateChain() {
/*  584 */       return (Certificate[])this.chain.clone();
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
/*      */     public Certificate getCertificate() {
/*  598 */       return this.chain[0];
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
/*      */     public Set<KeyStore.Entry.Attribute> getAttributes() {
/*  611 */       return this.attributes;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  619 */       StringBuilder stringBuilder = new StringBuilder();
/*  620 */       stringBuilder.append("Private key entry and certificate chain with " + this.chain.length + " elements:\r\n");
/*      */       
/*  622 */       for (Certificate certificate : this.chain) {
/*  623 */         stringBuilder.append(certificate);
/*  624 */         stringBuilder.append("\r\n");
/*      */       } 
/*  626 */       return stringBuilder.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class SecretKeyEntry
/*      */     implements Entry
/*      */   {
/*      */     private final SecretKey sKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Set<KeyStore.Entry.Attribute> attributes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SecretKeyEntry(SecretKey param1SecretKey) {
/*  651 */       if (param1SecretKey == null) {
/*  652 */         throw new NullPointerException("invalid null input");
/*      */       }
/*  654 */       this.sKey = param1SecretKey;
/*  655 */       this.attributes = Collections.emptySet();
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
/*      */ 
/*      */ 
/*      */     
/*      */     public SecretKeyEntry(SecretKey param1SecretKey, Set<KeyStore.Entry.Attribute> param1Set) {
/*  675 */       if (param1SecretKey == null || param1Set == null) {
/*  676 */         throw new NullPointerException("invalid null input");
/*      */       }
/*  678 */       this.sKey = param1SecretKey;
/*  679 */       this
/*  680 */         .attributes = Collections.unmodifiableSet(new HashSet<>(param1Set));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SecretKey getSecretKey() {
/*  689 */       return this.sKey;
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
/*      */     public Set<KeyStore.Entry.Attribute> getAttributes() {
/*  702 */       return this.attributes;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  710 */       return "Secret key entry with algorithm " + this.sKey.getAlgorithm();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class TrustedCertificateEntry
/*      */     implements Entry
/*      */   {
/*      */     private final Certificate cert;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Set<KeyStore.Entry.Attribute> attributes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TrustedCertificateEntry(Certificate param1Certificate) {
/*  735 */       if (param1Certificate == null) {
/*  736 */         throw new NullPointerException("invalid null input");
/*      */       }
/*  738 */       this.cert = param1Certificate;
/*  739 */       this.attributes = Collections.emptySet();
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
/*      */ 
/*      */ 
/*      */     
/*      */     public TrustedCertificateEntry(Certificate param1Certificate, Set<KeyStore.Entry.Attribute> param1Set) {
/*  759 */       if (param1Certificate == null || param1Set == null) {
/*  760 */         throw new NullPointerException("invalid null input");
/*      */       }
/*  762 */       this.cert = param1Certificate;
/*  763 */       this
/*  764 */         .attributes = Collections.unmodifiableSet(new HashSet<>(param1Set));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Certificate getTrustedCertificate() {
/*  773 */       return this.cert;
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
/*      */     public Set<KeyStore.Entry.Attribute> getAttributes() {
/*  786 */       return this.attributes;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  794 */       return "Trusted certificate entry:\r\n" + this.cert.toString();
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
/*      */   protected KeyStore(KeyStoreSpi paramKeyStoreSpi, Provider paramProvider, String paramString) {
/*  808 */     this.keyStoreSpi = paramKeyStoreSpi;
/*  809 */     this.provider = paramProvider;
/*  810 */     this.type = paramString;
/*      */     
/*  812 */     if (!skipDebug && pdebug != null) {
/*  813 */       pdebug.println("KeyStore." + paramString.toUpperCase() + " type from: " + this.provider
/*  814 */           .getName());
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
/*      */   public static KeyStore getInstance(String paramString) throws KeyStoreException {
/*      */     try {
/*  848 */       Object[] arrayOfObject = Security.getImpl(paramString, "KeyStore", (String)null);
/*  849 */       return new KeyStore((KeyStoreSpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString);
/*  850 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  851 */       throw new KeyStoreException(paramString + " not found", noSuchAlgorithmException);
/*  852 */     } catch (NoSuchProviderException noSuchProviderException) {
/*  853 */       throw new KeyStoreException(paramString + " not found", noSuchProviderException);
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
/*      */   public static KeyStore getInstance(String paramString1, String paramString2) throws KeyStoreException, NoSuchProviderException {
/*  893 */     if (paramString2 == null || paramString2.length() == 0)
/*  894 */       throw new IllegalArgumentException("missing provider"); 
/*      */     try {
/*  896 */       Object[] arrayOfObject = Security.getImpl(paramString1, "KeyStore", paramString2);
/*  897 */       return new KeyStore((KeyStoreSpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString1);
/*  898 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  899 */       throw new KeyStoreException(paramString1 + " not found", noSuchAlgorithmException);
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
/*      */   public static KeyStore getInstance(String paramString, Provider paramProvider) throws KeyStoreException {
/*  934 */     if (paramProvider == null)
/*  935 */       throw new IllegalArgumentException("missing provider"); 
/*      */     try {
/*  937 */       Object[] arrayOfObject = Security.getImpl(paramString, "KeyStore", paramProvider);
/*  938 */       return new KeyStore((KeyStoreSpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString);
/*  939 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  940 */       throw new KeyStoreException(paramString + " not found", noSuchAlgorithmException);
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
/*      */   public static final String getDefaultType() {
/*  965 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*      */           public String run() {
/*  967 */             return Security.getProperty("keystore.type");
/*      */           }
/*      */         });
/*  970 */     if (str == null) {
/*  971 */       str = "jks";
/*      */     }
/*  973 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Provider getProvider() {
/*  983 */     return this.provider;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getType() {
/*  993 */     return this.type;
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
/*      */   public final Key getKey(String paramString, char[] paramArrayOfchar) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
/* 1020 */     if (!this.initialized) {
/* 1021 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1023 */     return this.keyStoreSpi.engineGetKey(paramString, paramArrayOfchar);
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
/*      */   public final Certificate[] getCertificateChain(String paramString) throws KeyStoreException {
/* 1045 */     if (!this.initialized) {
/* 1046 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1048 */     return this.keyStoreSpi.engineGetCertificateChain(paramString);
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
/*      */   public final Certificate getCertificate(String paramString) throws KeyStoreException {
/* 1078 */     if (!this.initialized) {
/* 1079 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1081 */     return this.keyStoreSpi.engineGetCertificate(paramString);
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
/*      */   public final Date getCreationDate(String paramString) throws KeyStoreException {
/* 1098 */     if (!this.initialized) {
/* 1099 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1101 */     return this.keyStoreSpi.engineGetCreationDate(paramString);
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
/*      */   public final void setKeyEntry(String paramString, Key paramKey, char[] paramArrayOfchar, Certificate[] paramArrayOfCertificate) throws KeyStoreException {
/* 1131 */     if (!this.initialized) {
/* 1132 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1134 */     if (paramKey instanceof PrivateKey && (paramArrayOfCertificate == null || paramArrayOfCertificate.length == 0))
/*      */     {
/* 1136 */       throw new IllegalArgumentException("Private key must be accompanied by certificate chain");
/*      */     }
/*      */ 
/*      */     
/* 1140 */     this.keyStoreSpi.engineSetKeyEntry(paramString, paramKey, paramArrayOfchar, paramArrayOfCertificate);
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
/*      */   public final void setKeyEntry(String paramString, byte[] paramArrayOfbyte, Certificate[] paramArrayOfCertificate) throws KeyStoreException {
/* 1171 */     if (!this.initialized) {
/* 1172 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1174 */     this.keyStoreSpi.engineSetKeyEntry(paramString, paramArrayOfbyte, paramArrayOfCertificate);
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
/*      */   public final void setCertificateEntry(String paramString, Certificate paramCertificate) throws KeyStoreException {
/* 1198 */     if (!this.initialized) {
/* 1199 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1201 */     this.keyStoreSpi.engineSetCertificateEntry(paramString, paramCertificate);
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
/*      */   public final void deleteEntry(String paramString) throws KeyStoreException {
/* 1215 */     if (!this.initialized) {
/* 1216 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1218 */     this.keyStoreSpi.engineDeleteEntry(paramString);
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
/*      */   public final Enumeration<String> aliases() throws KeyStoreException {
/* 1232 */     if (!this.initialized) {
/* 1233 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1235 */     return this.keyStoreSpi.engineAliases();
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
/*      */   public final boolean containsAlias(String paramString) throws KeyStoreException {
/* 1251 */     if (!this.initialized) {
/* 1252 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1254 */     return this.keyStoreSpi.engineContainsAlias(paramString);
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
/*      */   public final int size() throws KeyStoreException {
/* 1268 */     if (!this.initialized) {
/* 1269 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1271 */     return this.keyStoreSpi.engineSize();
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
/*      */   public final boolean isKeyEntry(String paramString) throws KeyStoreException {
/* 1291 */     if (!this.initialized) {
/* 1292 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1294 */     return this.keyStoreSpi.engineIsKeyEntry(paramString);
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
/*      */   public final boolean isCertificateEntry(String paramString) throws KeyStoreException {
/* 1314 */     if (!this.initialized) {
/* 1315 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1317 */     return this.keyStoreSpi.engineIsCertificateEntry(paramString);
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
/*      */   public final String getCertificateAlias(Certificate paramCertificate) throws KeyStoreException {
/* 1349 */     if (!this.initialized) {
/* 1350 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1352 */     return this.keyStoreSpi.engineGetCertificateAlias(paramCertificate);
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
/*      */   public final void store(OutputStream paramOutputStream, char[] paramArrayOfchar) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
/* 1374 */     if (!this.initialized) {
/* 1375 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1377 */     this.keyStoreSpi.engineStore(paramOutputStream, paramArrayOfchar);
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
/*      */   public final void store(LoadStoreParameter paramLoadStoreParameter) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
/* 1403 */     if (!this.initialized) {
/* 1404 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1406 */     this.keyStoreSpi.engineStore(paramLoadStoreParameter);
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
/*      */   public final void load(InputStream paramInputStream, char[] paramArrayOfchar) throws IOException, NoSuchAlgorithmException, CertificateException {
/* 1445 */     this.keyStoreSpi.engineLoad(paramInputStream, paramArrayOfchar);
/* 1446 */     this.initialized = true;
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
/*      */   public final void load(LoadStoreParameter paramLoadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
/* 1479 */     this.keyStoreSpi.engineLoad(paramLoadStoreParameter);
/* 1480 */     this.initialized = true;
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
/*      */   public final Entry getEntry(String paramString, ProtectionParameter paramProtectionParameter) throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {
/* 1515 */     if (paramString == null) {
/* 1516 */       throw new NullPointerException("invalid null input");
/*      */     }
/* 1518 */     if (!this.initialized) {
/* 1519 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1521 */     return this.keyStoreSpi.engineGetEntry(paramString, paramProtectionParameter);
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
/*      */   public final void setEntry(String paramString, Entry paramEntry, ProtectionParameter paramProtectionParameter) throws KeyStoreException {
/* 1551 */     if (paramString == null || paramEntry == null) {
/* 1552 */       throw new NullPointerException("invalid null input");
/*      */     }
/* 1554 */     if (!this.initialized) {
/* 1555 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1557 */     this.keyStoreSpi.engineSetEntry(paramString, paramEntry, paramProtectionParameter);
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
/*      */   public final boolean entryInstanceOf(String paramString, Class<? extends Entry> paramClass) throws KeyStoreException {
/* 1586 */     if (paramString == null || paramClass == null) {
/* 1587 */       throw new NullPointerException("invalid null input");
/*      */     }
/* 1589 */     if (!this.initialized) {
/* 1590 */       throw new KeyStoreException("Uninitialized keystore");
/*      */     }
/* 1592 */     return this.keyStoreSpi.engineEntryInstanceOf(paramString, paramClass);
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
/*      */   public static abstract class Builder
/*      */   {
/*      */     static final int MAX_CALLBACK_TRIES = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract KeyStore getKeyStore() throws KeyStoreException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract KeyStore.ProtectionParameter getProtectionParameter(String param1String) throws KeyStoreException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static Builder newInstance(final KeyStore keyStore, final KeyStore.ProtectionParameter protectionParameter) {
/* 1671 */       if (keyStore == null || protectionParameter == null) {
/* 1672 */         throw new NullPointerException();
/*      */       }
/* 1674 */       if (!keyStore.initialized) {
/* 1675 */         throw new IllegalArgumentException("KeyStore not initialized");
/*      */       }
/* 1677 */       return new Builder() {
/*      */           private volatile boolean getCalled;
/*      */           
/*      */           public KeyStore getKeyStore() {
/* 1681 */             this.getCalled = true;
/* 1682 */             return keyStore;
/*      */           }
/*      */ 
/*      */           
/*      */           public KeyStore.ProtectionParameter getProtectionParameter(String param2String) {
/* 1687 */             if (param2String == null) {
/* 1688 */               throw new NullPointerException();
/*      */             }
/* 1690 */             if (!this.getCalled) {
/* 1691 */               throw new IllegalStateException("getKeyStore() must be called first");
/*      */             }
/*      */             
/* 1694 */             return protectionParameter;
/*      */           }
/*      */         };
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static Builder newInstance(String param1String, Provider param1Provider, File param1File, KeyStore.ProtectionParameter param1ProtectionParameter) {
/* 1744 */       if (param1String == null || param1File == null || param1ProtectionParameter == null) {
/* 1745 */         throw new NullPointerException();
/*      */       }
/* 1747 */       if (!(param1ProtectionParameter instanceof KeyStore.PasswordProtection) && !(param1ProtectionParameter instanceof KeyStore.CallbackHandlerProtection))
/*      */       {
/* 1749 */         throw new IllegalArgumentException("Protection must be PasswordProtection or CallbackHandlerProtection");
/*      */       }
/*      */ 
/*      */       
/* 1753 */       if (!param1File.isFile()) {
/* 1754 */         throw new IllegalArgumentException("File does not exist or it does not refer to a normal file: " + param1File);
/*      */       }
/*      */ 
/*      */       
/* 1758 */       return new FileBuilder(param1String, param1Provider, param1File, param1ProtectionParameter, 
/* 1759 */           AccessController.getContext());
/*      */     }
/*      */ 
/*      */     
/*      */     private static final class FileBuilder
/*      */       extends Builder
/*      */     {
/*      */       private final String type;
/*      */       
/*      */       private final Provider provider;
/*      */       
/*      */       private final File file;
/*      */       private KeyStore.ProtectionParameter protection;
/*      */       private KeyStore.ProtectionParameter keyProtection;
/*      */       private final AccessControlContext context;
/*      */       private KeyStore keyStore;
/*      */       private Throwable oldException;
/*      */       
/*      */       FileBuilder(String param2String, Provider param2Provider, File param2File, KeyStore.ProtectionParameter param2ProtectionParameter, AccessControlContext param2AccessControlContext) {
/* 1778 */         this.type = param2String;
/* 1779 */         this.provider = param2Provider;
/* 1780 */         this.file = param2File;
/* 1781 */         this.protection = param2ProtectionParameter;
/* 1782 */         this.context = param2AccessControlContext;
/*      */       }
/*      */ 
/*      */       
/*      */       public synchronized KeyStore getKeyStore() throws KeyStoreException {
/* 1787 */         if (this.keyStore != null) {
/* 1788 */           return this.keyStore;
/*      */         }
/* 1790 */         if (this.oldException != null) {
/* 1791 */           throw new KeyStoreException("Previous KeyStore instantiation failed", this.oldException);
/*      */         }
/*      */ 
/*      */         
/* 1795 */         PrivilegedExceptionAction<KeyStore> privilegedExceptionAction = new PrivilegedExceptionAction<KeyStore>()
/*      */           {
/*      */             public KeyStore run() throws Exception {
/* 1798 */               if (!(KeyStore.Builder.FileBuilder.this.protection instanceof KeyStore.CallbackHandlerProtection)) {
/* 1799 */                 return run0();
/*      */               }
/*      */ 
/*      */               
/* 1803 */               byte b = 0;
/*      */               while (true) {
/* 1805 */                 b++;
/*      */                 try {
/* 1807 */                   return run0();
/* 1808 */                 } catch (IOException iOException) {
/* 1809 */                   if (b < 3 && iOException
/* 1810 */                     .getCause() instanceof UnrecoverableKeyException)
/*      */                     continue;  break;
/*      */                 } 
/* 1813 */               }  throw iOException;
/*      */             }
/*      */ 
/*      */             
/*      */             public KeyStore run0() throws Exception {
/*      */               KeyStore keyStore;
/* 1819 */               if (KeyStore.Builder.FileBuilder.this.provider == null) {
/* 1820 */                 keyStore = KeyStore.getInstance(KeyStore.Builder.FileBuilder.this.type);
/*      */               } else {
/* 1822 */                 keyStore = KeyStore.getInstance(KeyStore.Builder.FileBuilder.this.type, KeyStore.Builder.FileBuilder.this.provider);
/*      */               } 
/* 1824 */               FileInputStream fileInputStream = null;
/* 1825 */               char[] arrayOfChar = null;
/*      */               try {
/* 1827 */                 fileInputStream = new FileInputStream(KeyStore.Builder.FileBuilder.this.file);
/* 1828 */                 if (KeyStore.Builder.FileBuilder.this.protection instanceof KeyStore.PasswordProtection) {
/*      */                   
/* 1830 */                   arrayOfChar = ((KeyStore.PasswordProtection)KeyStore.Builder.FileBuilder.this.protection).getPassword();
/* 1831 */                   KeyStore.Builder.FileBuilder.this.keyProtection = KeyStore.Builder.FileBuilder.this.protection;
/*      */                 }
/*      */                 else {
/*      */                   
/* 1835 */                   CallbackHandler callbackHandler = ((KeyStore.CallbackHandlerProtection)KeyStore.Builder.FileBuilder.this.protection).getCallbackHandler();
/*      */                   
/* 1837 */                   PasswordCallback passwordCallback = new PasswordCallback("Password for keystore " + KeyStore.Builder.FileBuilder.this.file.getName(), false);
/*      */                   
/* 1839 */                   callbackHandler.handle(new Callback[] { passwordCallback });
/* 1840 */                   arrayOfChar = passwordCallback.getPassword();
/* 1841 */                   if (arrayOfChar == null) {
/* 1842 */                     throw new KeyStoreException("No password provided");
/*      */                   }
/*      */                   
/* 1845 */                   passwordCallback.clearPassword();
/* 1846 */                   KeyStore.Builder.FileBuilder.this.keyProtection = new KeyStore.PasswordProtection(arrayOfChar);
/*      */                 } 
/* 1848 */                 keyStore.load(fileInputStream, arrayOfChar);
/* 1849 */                 return keyStore;
/*      */               } finally {
/* 1851 */                 if (fileInputStream != null) {
/* 1852 */                   fileInputStream.close();
/*      */                 }
/*      */               } 
/*      */             }
/*      */           };
/*      */         try {
/* 1858 */           this.keyStore = AccessController.<KeyStore>doPrivileged(privilegedExceptionAction, this.context);
/* 1859 */           return this.keyStore;
/* 1860 */         } catch (PrivilegedActionException privilegedActionException) {
/* 1861 */           this.oldException = privilegedActionException.getCause();
/* 1862 */           throw new KeyStoreException("KeyStore instantiation failed", this.oldException);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public synchronized KeyStore.ProtectionParameter getProtectionParameter(String param2String) {
/* 1869 */         if (param2String == null) {
/* 1870 */           throw new NullPointerException();
/*      */         }
/* 1872 */         if (this.keyStore == null) {
/* 1873 */           throw new IllegalStateException("getKeyStore() must be called first");
/*      */         }
/*      */         
/* 1876 */         return this.keyProtection;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static Builder newInstance(final String type, final Provider provider, final KeyStore.ProtectionParameter protection) {
/* 1909 */       if (type == null || protection == null) {
/* 1910 */         throw new NullPointerException();
/*      */       }
/* 1912 */       final AccessControlContext context = AccessController.getContext();
/* 1913 */       return new Builder() {
/*      */           private volatile boolean getCalled;
/*      */           private IOException oldException;
/*      */           
/* 1917 */           private final PrivilegedExceptionAction<KeyStore> action = new PrivilegedExceptionAction<KeyStore>()
/*      */             {
/*      */               public KeyStore run() throws Exception
/*      */               {
/*      */                 KeyStore keyStore;
/* 1922 */                 if (provider == null) {
/* 1923 */                   keyStore = KeyStore.getInstance(type);
/*      */                 } else {
/* 1925 */                   keyStore = KeyStore.getInstance(type, provider);
/*      */                 } 
/* 1927 */                 KeyStore.SimpleLoadStoreParameter simpleLoadStoreParameter = new KeyStore.SimpleLoadStoreParameter(protection);
/* 1928 */                 if (!(protection instanceof KeyStore.CallbackHandlerProtection)) {
/* 1929 */                   keyStore.load(simpleLoadStoreParameter);
/*      */                 }
/*      */                 else {
/*      */                   
/* 1933 */                   byte b = 0;
/*      */                   while (true) {
/* 1935 */                     b++;
/*      */                     try {
/* 1937 */                       keyStore.load(simpleLoadStoreParameter);
/*      */                     }
/* 1939 */                     catch (IOException iOException) {
/* 1940 */                       if (iOException.getCause() instanceof UnrecoverableKeyException) {
/* 1941 */                         if (b < 3) {
/*      */                           continue;
/*      */                         }
/* 1944 */                         KeyStore.Builder.null.this.oldException = iOException;
/*      */                       } 
/*      */                       
/* 1947 */                       throw iOException;
/*      */                     }  break;
/*      */                   } 
/*      */                 } 
/* 1951 */                 KeyStore.Builder.null.this.getCalled = true;
/* 1952 */                 return keyStore;
/*      */               }
/*      */             };
/*      */ 
/*      */           
/*      */           public synchronized KeyStore getKeyStore() throws KeyStoreException {
/* 1958 */             if (this.oldException != null) {
/* 1959 */               throw new KeyStoreException("Previous KeyStore instantiation failed", this.oldException);
/*      */             }
/*      */ 
/*      */             
/*      */             try {
/* 1964 */               return AccessController.<KeyStore>doPrivileged(this.action, context);
/* 1965 */             } catch (PrivilegedActionException privilegedActionException) {
/* 1966 */               Throwable throwable = privilegedActionException.getCause();
/* 1967 */               throw new KeyStoreException("KeyStore instantiation failed", throwable);
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public KeyStore.ProtectionParameter getProtectionParameter(String param2String) {
/* 1974 */             if (param2String == null) {
/* 1975 */               throw new NullPointerException();
/*      */             }
/* 1977 */             if (!this.getCalled) {
/* 1978 */               throw new IllegalStateException("getKeyStore() must be called first");
/*      */             }
/*      */             
/* 1981 */             return protection;
/*      */           }
/*      */         };
/*      */     }
/*      */   }
/*      */   
/*      */   static class SimpleLoadStoreParameter
/*      */     implements LoadStoreParameter
/*      */   {
/*      */     private final KeyStore.ProtectionParameter protection;
/*      */     
/*      */     SimpleLoadStoreParameter(KeyStore.ProtectionParameter param1ProtectionParameter) {
/* 1993 */       this.protection = param1ProtectionParameter;
/*      */     }
/*      */     
/*      */     public KeyStore.ProtectionParameter getProtectionParameter() {
/* 1997 */       return this.protection;
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface ProtectionParameter {}
/*      */   
/*      */   public static interface LoadStoreParameter {
/*      */     KeyStore.ProtectionParameter getProtectionParameter();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/KeyStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */