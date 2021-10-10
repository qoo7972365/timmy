/*      */ package sun.security.pkcs12;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.security.AccessController;
/*      */ import java.security.AlgorithmParameters;
/*      */ import java.security.InvalidAlgorithmParameterException;
/*      */ import java.security.Key;
/*      */ import java.security.KeyFactory;
/*      */ import java.security.KeyStore;
/*      */ import java.security.KeyStoreException;
/*      */ import java.security.KeyStoreSpi;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.PKCS12Attribute;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.SecureRandom;
/*      */ import java.security.Security;
/*      */ import java.security.UnrecoverableEntryException;
/*      */ import java.security.UnrecoverableKeyException;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.CertificateException;
/*      */ import java.security.cert.CertificateFactory;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.security.spec.AlgorithmParameterSpec;
/*      */ import java.security.spec.InvalidParameterSpecException;
/*      */ import java.security.spec.KeySpec;
/*      */ import java.security.spec.PKCS8EncodedKeySpec;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.crypto.Cipher;
/*      */ import javax.crypto.Mac;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.crypto.SecretKeyFactory;
/*      */ import javax.crypto.spec.PBEKeySpec;
/*      */ import javax.crypto.spec.PBEParameterSpec;
/*      */ import javax.crypto.spec.SecretKeySpec;
/*      */ import javax.security.auth.DestroyFailedException;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import sun.security.pkcs.ContentInfo;
/*      */ import sun.security.pkcs.EncryptedPrivateKeyInfo;
/*      */ import sun.security.util.Debug;
/*      */ import sun.security.util.DerInputStream;
/*      */ import sun.security.util.DerOutputStream;
/*      */ import sun.security.util.DerValue;
/*      */ import sun.security.util.ObjectIdentifier;
/*      */ import sun.security.x509.AlgorithmId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class PKCS12KeyStore
/*      */   extends KeyStoreSpi
/*      */ {
/*      */   public static final int VERSION_3 = 3;
/*  136 */   private static final String[] KEY_PROTECTION_ALGORITHM = new String[] { "keystore.pkcs12.keyProtectionAlgorithm", "keystore.PKCS12.keyProtectionAlgorithm" };
/*      */ 
/*      */   
/*      */   private static final int MAX_ITERATION_COUNT = 5000000;
/*      */   
/*      */   private static final int PBE_ITERATION_COUNT = 50000;
/*      */   
/*      */   private static final int MAC_ITERATION_COUNT = 100000;
/*      */   
/*      */   private static final int SALT_LEN = 20;
/*      */   
/*  147 */   private static final String[] CORE_ATTRIBUTES = new String[] { "1.2.840.113549.1.9.20", "1.2.840.113549.1.9.21", "2.16.840.1.113894.746875.1.1" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  153 */   private static final Debug debug = Debug.getInstance("pkcs12");
/*      */   
/*  155 */   private static final int[] keyBag = new int[] { 1, 2, 840, 113549, 1, 12, 10, 1, 2 };
/*  156 */   private static final int[] certBag = new int[] { 1, 2, 840, 113549, 1, 12, 10, 1, 3 };
/*  157 */   private static final int[] secretBag = new int[] { 1, 2, 840, 113549, 1, 12, 10, 1, 5 };
/*      */   
/*  159 */   private static final int[] pkcs9Name = new int[] { 1, 2, 840, 113549, 1, 9, 20 };
/*  160 */   private static final int[] pkcs9KeyId = new int[] { 1, 2, 840, 113549, 1, 9, 21 };
/*      */   
/*  162 */   private static final int[] pkcs9certType = new int[] { 1, 2, 840, 113549, 1, 9, 22, 1 };
/*      */   
/*  164 */   private static final int[] pbeWithSHAAnd40BitRC2CBC = new int[] { 1, 2, 840, 113549, 1, 12, 1, 6 };
/*      */   
/*  166 */   private static final int[] pbeWithSHAAnd3KeyTripleDESCBC = new int[] { 1, 2, 840, 113549, 1, 12, 1, 3 };
/*      */   
/*  168 */   private static final int[] pbes2 = new int[] { 1, 2, 840, 113549, 1, 5, 13 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  174 */   private static final int[] TrustedKeyUsage = new int[] { 2, 16, 840, 1, 113894, 746875, 1, 1 };
/*      */   
/*  176 */   private static final int[] AnyExtendedKeyUsage = new int[] { 2, 5, 29, 37, 0 };
/*      */   
/*      */   private static ObjectIdentifier PKCS8ShroudedKeyBag_OID;
/*      */   
/*      */   private static ObjectIdentifier CertBag_OID;
/*      */   private static ObjectIdentifier SecretBag_OID;
/*      */   private static ObjectIdentifier PKCS9FriendlyName_OID;
/*      */   private static ObjectIdentifier PKCS9LocalKeyId_OID;
/*      */   private static ObjectIdentifier PKCS9CertType_OID;
/*      */   private static ObjectIdentifier pbeWithSHAAnd40BitRC2CBC_OID;
/*      */   private static ObjectIdentifier pbeWithSHAAnd3KeyTripleDESCBC_OID;
/*      */   private static ObjectIdentifier pbes2_OID;
/*      */   private static ObjectIdentifier TrustedKeyUsage_OID;
/*      */   private static ObjectIdentifier[] AnyUsage;
/*  190 */   private int counter = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  195 */   private int privateKeyCount = 0;
/*      */ 
/*      */   
/*  198 */   private int secretKeyCount = 0;
/*      */ 
/*      */   
/*  201 */   private int certificateCount = 0;
/*      */   
/*      */   private SecureRandom random;
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/*  208 */       PKCS8ShroudedKeyBag_OID = new ObjectIdentifier(keyBag);
/*  209 */       CertBag_OID = new ObjectIdentifier(certBag);
/*  210 */       SecretBag_OID = new ObjectIdentifier(secretBag);
/*  211 */       PKCS9FriendlyName_OID = new ObjectIdentifier(pkcs9Name);
/*  212 */       PKCS9LocalKeyId_OID = new ObjectIdentifier(pkcs9KeyId);
/*  213 */       PKCS9CertType_OID = new ObjectIdentifier(pkcs9certType);
/*  214 */       pbeWithSHAAnd40BitRC2CBC_OID = new ObjectIdentifier(pbeWithSHAAnd40BitRC2CBC);
/*      */       
/*  216 */       pbeWithSHAAnd3KeyTripleDESCBC_OID = new ObjectIdentifier(pbeWithSHAAnd3KeyTripleDESCBC);
/*      */       
/*  218 */       pbes2_OID = new ObjectIdentifier(pbes2);
/*  219 */       TrustedKeyUsage_OID = new ObjectIdentifier(TrustedKeyUsage);
/*  220 */       AnyUsage = new ObjectIdentifier[] { new ObjectIdentifier(AnyExtendedKeyUsage) };
/*      */     }
/*  222 */     catch (IOException iOException) {}
/*      */   }
/*      */   
/*      */   private static class Entry {
/*      */     Date date;
/*      */     String alias;
/*      */     byte[] keyId;
/*      */     Set<KeyStore.Entry.Attribute> attributes;
/*      */     
/*      */     private Entry() {}
/*      */   }
/*      */   
/*      */   private static class KeyEntry extends Entry {
/*      */     private KeyEntry() {}
/*      */   }
/*      */   
/*      */   private static class PrivateKeyEntry extends KeyEntry {
/*      */     byte[] protectedPrivKey;
/*      */     Certificate[] chain;
/*      */     
/*      */     private PrivateKeyEntry() {}
/*      */   }
/*      */   
/*      */   private static class SecretKeyEntry extends KeyEntry {
/*      */     byte[] protectedSecretKey;
/*      */     
/*      */     private SecretKeyEntry() {}
/*      */   }
/*      */   
/*      */   private static class CertEntry extends Entry {
/*      */     final X509Certificate cert;
/*      */     ObjectIdentifier[] trustedKeyUsage;
/*      */     
/*      */     CertEntry(X509Certificate param1X509Certificate, byte[] param1ArrayOfbyte, String param1String) {
/*  256 */       this(param1X509Certificate, param1ArrayOfbyte, param1String, (ObjectIdentifier[])null, (Set<? extends KeyStore.Entry.Attribute>)null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     CertEntry(X509Certificate param1X509Certificate, byte[] param1ArrayOfbyte, String param1String, ObjectIdentifier[] param1ArrayOfObjectIdentifier, Set<? extends KeyStore.Entry.Attribute> param1Set) {
/*  262 */       this.date = new Date();
/*  263 */       this.cert = param1X509Certificate;
/*  264 */       this.keyId = param1ArrayOfbyte;
/*  265 */       this.alias = param1String;
/*  266 */       this.trustedKeyUsage = param1ArrayOfObjectIdentifier;
/*  267 */       this.attributes = new HashSet<>();
/*  268 */       if (param1Set != null) {
/*  269 */         this.attributes.addAll(param1Set);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  279 */   private Map<String, Entry> entries = Collections.synchronizedMap(new LinkedHashMap<>());
/*      */   
/*  281 */   private ArrayList<KeyEntry> keyList = new ArrayList<>();
/*  282 */   private LinkedHashMap<X500Principal, X509Certificate> certsMap = new LinkedHashMap<>();
/*      */   
/*  284 */   private ArrayList<CertEntry> certEntries = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Key engineGetKey(String paramString, char[] paramArrayOfchar) throws NoSuchAlgorithmException, UnrecoverableKeyException {
/*      */     SecretKey secretKey;
/*      */     byte[] arrayOfByte2;
/*      */     AlgorithmParameters algorithmParameters;
/*      */     ObjectIdentifier objectIdentifier;
/*  304 */     Entry entry = this.entries.get(paramString.toLowerCase(Locale.ENGLISH));
/*  305 */     PrivateKey privateKey = null;
/*      */     
/*  307 */     if (entry == null || !(entry instanceof KeyEntry)) {
/*  308 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  312 */     byte[] arrayOfByte1 = null;
/*  313 */     if (entry instanceof PrivateKeyEntry) {
/*  314 */       arrayOfByte1 = ((PrivateKeyEntry)entry).protectedPrivKey;
/*  315 */     } else if (entry instanceof SecretKeyEntry) {
/*  316 */       arrayOfByte1 = ((SecretKeyEntry)entry).protectedSecretKey;
/*      */     } else {
/*  318 */       throw new UnrecoverableKeyException("Error locating key");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  327 */       EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(arrayOfByte1);
/*      */       
/*  329 */       arrayOfByte2 = encryptedPrivateKeyInfo.getEncryptedData();
/*      */ 
/*      */       
/*  332 */       DerValue derValue = new DerValue(encryptedPrivateKeyInfo.getAlgorithm().encode());
/*  333 */       DerInputStream derInputStream = derValue.toDerInputStream();
/*  334 */       objectIdentifier = derInputStream.getOID();
/*  335 */       algorithmParameters = parseAlgParameters(objectIdentifier, derInputStream);
/*      */     }
/*  337 */     catch (IOException iOException) {
/*  338 */       UnrecoverableKeyException unrecoverableKeyException = new UnrecoverableKeyException("Private key not stored as PKCS#8 EncryptedPrivateKeyInfo: " + iOException);
/*      */ 
/*      */       
/*  341 */       unrecoverableKeyException.initCause(iOException);
/*  342 */       throw unrecoverableKeyException;
/*      */     } 
/*      */     
/*      */     try {
/*      */       byte[] arrayOfByte;
/*  347 */       int i = 0;
/*      */       
/*  349 */       if (algorithmParameters != null) {
/*      */         PBEParameterSpec pBEParameterSpec;
/*      */         try {
/*  352 */           pBEParameterSpec = algorithmParameters.<PBEParameterSpec>getParameterSpec(PBEParameterSpec.class);
/*  353 */         } catch (InvalidParameterSpecException invalidParameterSpecException) {
/*  354 */           throw new IOException("Invalid PBE algorithm parameters");
/*      */         } 
/*  356 */         i = pBEParameterSpec.getIterationCount();
/*      */         
/*  358 */         if (i > 5000000) {
/*  359 */           throw new IOException("PBE iteration count too large");
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/*      */         try {
/*  367 */           SecretKey secretKey1 = getPBEKey(paramArrayOfchar);
/*  368 */           Cipher cipher = Cipher.getInstance(
/*  369 */               mapPBEParamsToAlgorithm(objectIdentifier, algorithmParameters));
/*  370 */           cipher.init(2, secretKey1, algorithmParameters);
/*  371 */           arrayOfByte = cipher.doFinal(arrayOfByte2);
/*      */         }
/*  373 */         catch (Exception exception) {
/*  374 */           if (paramArrayOfchar.length == 0) {
/*      */ 
/*      */             
/*  377 */             paramArrayOfchar = new char[1];
/*      */             continue;
/*      */           } 
/*  380 */           throw exception;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */ 
/*      */       
/*  388 */       DerValue derValue = new DerValue(arrayOfByte);
/*  389 */       DerInputStream derInputStream = derValue.toDerInputStream();
/*  390 */       int j = derInputStream.getInteger();
/*  391 */       DerValue[] arrayOfDerValue = derInputStream.getSequence(2);
/*  392 */       AlgorithmId algorithmId = new AlgorithmId(arrayOfDerValue[0].getOID());
/*  393 */       String str = algorithmId.getName();
/*      */ 
/*      */       
/*  396 */       if (entry instanceof PrivateKeyEntry) {
/*  397 */         KeyFactory keyFactory = KeyFactory.getInstance(str);
/*  398 */         PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(arrayOfByte);
/*  399 */         privateKey = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
/*      */         
/*  401 */         if (debug != null) {
/*  402 */           debug.println("Retrieved a protected private key at alias '" + paramString + "' (" + (new AlgorithmId(objectIdentifier))
/*      */               
/*  404 */               .getName() + " iterations: " + i + ")");
/*      */         
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  410 */         byte[] arrayOfByte3 = derInputStream.getOctetString();
/*  411 */         SecretKeySpec secretKeySpec = new SecretKeySpec(arrayOfByte3, str);
/*      */ 
/*      */ 
/*      */         
/*  415 */         if (str.startsWith("PBE")) {
/*      */           
/*  417 */           SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(str);
/*      */           
/*  419 */           KeySpec keySpec = secretKeyFactory.getKeySpec(secretKeySpec, PBEKeySpec.class);
/*  420 */           secretKey = secretKeyFactory.generateSecret(keySpec);
/*      */         } else {
/*  422 */           secretKey = secretKeySpec;
/*      */         } 
/*      */         
/*  425 */         if (debug != null) {
/*  426 */           debug.println("Retrieved a protected secret key at alias '" + paramString + "' (" + (new AlgorithmId(objectIdentifier))
/*      */               
/*  428 */               .getName() + " iterations: " + i + ")");
/*      */         }
/*      */       }
/*      */     
/*  432 */     } catch (Exception exception) {
/*      */ 
/*      */       
/*  435 */       UnrecoverableKeyException unrecoverableKeyException = new UnrecoverableKeyException("Get Key failed: " + exception.getMessage());
/*  436 */       unrecoverableKeyException.initCause(exception);
/*  437 */       throw unrecoverableKeyException;
/*      */     } 
/*  439 */     return secretKey;
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
/*      */   public Certificate[] engineGetCertificateChain(String paramString) {
/*  454 */     Entry entry = this.entries.get(paramString.toLowerCase(Locale.ENGLISH));
/*  455 */     if (entry != null && entry instanceof PrivateKeyEntry) {
/*  456 */       if (((PrivateKeyEntry)entry).chain == null) {
/*  457 */         return null;
/*      */       }
/*      */       
/*  460 */       if (debug != null) {
/*  461 */         debug.println("Retrieved a " + ((PrivateKeyEntry)entry).chain.length + "-certificate chain at alias '" + paramString + "'");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  466 */       return (Certificate[])((PrivateKeyEntry)entry).chain.clone();
/*      */     } 
/*      */     
/*  469 */     return null;
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
/*      */   public Certificate engineGetCertificate(String paramString) {
/*  489 */     Entry entry = this.entries.get(paramString.toLowerCase(Locale.ENGLISH));
/*  490 */     if (entry == null) {
/*  491 */       return null;
/*      */     }
/*  493 */     if (entry instanceof CertEntry && ((CertEntry)entry).trustedKeyUsage != null) {
/*      */ 
/*      */       
/*  496 */       if (debug != null) {
/*  497 */         if (Arrays.equals((Object[])AnyUsage, (Object[])((CertEntry)entry).trustedKeyUsage)) {
/*      */           
/*  499 */           debug.println("Retrieved a certificate at alias '" + paramString + "' (trusted for any purpose)");
/*      */         } else {
/*      */           
/*  502 */           debug.println("Retrieved a certificate at alias '" + paramString + "' (trusted for limited purposes)");
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  507 */       return ((CertEntry)entry).cert;
/*      */     } 
/*  509 */     if (entry instanceof PrivateKeyEntry) {
/*  510 */       if (((PrivateKeyEntry)entry).chain == null) {
/*  511 */         return null;
/*      */       }
/*      */       
/*  514 */       if (debug != null) {
/*  515 */         debug.println("Retrieved a certificate at alias '" + paramString + "'");
/*      */       }
/*      */ 
/*      */       
/*  519 */       return ((PrivateKeyEntry)entry).chain[0];
/*      */     } 
/*      */ 
/*      */     
/*  523 */     return null;
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
/*      */   public Date engineGetCreationDate(String paramString) {
/*  536 */     Entry entry = this.entries.get(paramString.toLowerCase(Locale.ENGLISH));
/*  537 */     if (entry != null) {
/*  538 */       return new Date(entry.date.getTime());
/*      */     }
/*  540 */     return null;
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
/*      */   public synchronized void engineSetKeyEntry(String paramString, Key paramKey, char[] paramArrayOfchar, Certificate[] paramArrayOfCertificate) throws KeyStoreException {
/*  570 */     KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(paramArrayOfchar);
/*      */ 
/*      */     
/*      */     try {
/*  574 */       setKeyEntry(paramString, paramKey, passwordProtection, paramArrayOfCertificate, null);
/*      */     } finally {
/*      */       
/*      */       try {
/*  578 */         passwordProtection.destroy();
/*  579 */       } catch (DestroyFailedException destroyFailedException) {}
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
/*      */   private void setKeyEntry(String paramString, Key paramKey, KeyStore.PasswordProtection paramPasswordProtection, Certificate[] paramArrayOfCertificate, Set<KeyStore.Entry.Attribute> paramSet) throws KeyStoreException {
/*      */     try {
/*      */       SecretKeyEntry secretKeyEntry;
/*  596 */       if (paramKey instanceof PrivateKey) {
/*  597 */         PrivateKeyEntry privateKeyEntry2 = new PrivateKeyEntry();
/*  598 */         privateKeyEntry2.date = new Date();
/*      */         
/*  600 */         if (paramKey.getFormat().equals("PKCS#8") || paramKey
/*  601 */           .getFormat().equals("PKCS8")) {
/*      */           
/*  603 */           if (debug != null) {
/*  604 */             debug.println("Setting a protected private key at alias '" + paramString + "'");
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  610 */           privateKeyEntry2
/*  611 */             .protectedPrivKey = encryptPrivateKey(paramKey.getEncoded(), paramPasswordProtection);
/*      */         } else {
/*  613 */           throw new KeyStoreException("Private key is not encodedas PKCS#8");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  618 */         if (paramArrayOfCertificate != null) {
/*      */           
/*  620 */           if (paramArrayOfCertificate.length > 1 && !validateChain(paramArrayOfCertificate)) {
/*  621 */             throw new KeyStoreException("Certificate chain is not valid");
/*      */           }
/*  623 */           privateKeyEntry2.chain = (Certificate[])paramArrayOfCertificate.clone();
/*  624 */           this.certificateCount += paramArrayOfCertificate.length;
/*      */           
/*  626 */           if (debug != null) {
/*  627 */             debug.println("Setting a " + paramArrayOfCertificate.length + "-certificate chain at alias '" + paramString + "'");
/*      */           }
/*      */         } 
/*      */         
/*  631 */         this.privateKeyCount++;
/*  632 */         PrivateKeyEntry privateKeyEntry1 = privateKeyEntry2;
/*      */       }
/*  634 */       else if (paramKey instanceof SecretKey) {
/*  635 */         SecretKeyEntry secretKeyEntry1 = new SecretKeyEntry();
/*  636 */         secretKeyEntry1.date = new Date();
/*      */ 
/*      */         
/*  639 */         DerOutputStream derOutputStream1 = new DerOutputStream();
/*  640 */         DerOutputStream derOutputStream2 = new DerOutputStream();
/*  641 */         derOutputStream2.putInteger(0);
/*  642 */         AlgorithmId algorithmId = AlgorithmId.get(paramKey.getAlgorithm());
/*  643 */         algorithmId.encode(derOutputStream2);
/*  644 */         derOutputStream2.putOctetString(paramKey.getEncoded());
/*  645 */         derOutputStream1.write((byte)48, derOutputStream2);
/*      */ 
/*      */         
/*  648 */         secretKeyEntry1
/*  649 */           .protectedSecretKey = encryptPrivateKey(derOutputStream1.toByteArray(), paramPasswordProtection);
/*      */         
/*  651 */         if (debug != null) {
/*  652 */           debug.println("Setting a protected secret key at alias '" + paramString + "'");
/*      */         }
/*      */         
/*  655 */         this.secretKeyCount++;
/*  656 */         secretKeyEntry = secretKeyEntry1;
/*      */       } else {
/*      */         
/*  659 */         throw new KeyStoreException("Unsupported Key type");
/*      */       } 
/*      */       
/*  662 */       secretKeyEntry.attributes = new HashSet<>();
/*  663 */       if (paramSet != null) {
/*  664 */         secretKeyEntry.attributes.addAll(paramSet);
/*      */       }
/*      */       
/*  667 */       secretKeyEntry.keyId = ("Time " + secretKeyEntry.date.getTime()).getBytes("UTF8");
/*      */       
/*  669 */       secretKeyEntry.alias = paramString.toLowerCase(Locale.ENGLISH);
/*      */       
/*  671 */       this.entries.put(paramString.toLowerCase(Locale.ENGLISH), secretKeyEntry);
/*      */     }
/*  673 */     catch (Exception exception) {
/*  674 */       throw new KeyStoreException("Key protection  algorithm not found: " + exception, exception);
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
/*      */   public synchronized void engineSetKeyEntry(String paramString, byte[] paramArrayOfbyte, Certificate[] paramArrayOfCertificate) throws KeyStoreException {
/*      */     try {
/*  709 */       new EncryptedPrivateKeyInfo(paramArrayOfbyte);
/*  710 */     } catch (IOException iOException) {
/*  711 */       throw new KeyStoreException("Private key is not stored as PKCS#8 EncryptedPrivateKeyInfo: " + iOException, iOException);
/*      */     } 
/*      */ 
/*      */     
/*  715 */     PrivateKeyEntry privateKeyEntry = new PrivateKeyEntry();
/*  716 */     privateKeyEntry.date = new Date();
/*      */     
/*  718 */     if (debug != null) {
/*  719 */       debug.println("Setting a protected private key at alias '" + paramString + "'");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  725 */       privateKeyEntry.keyId = ("Time " + privateKeyEntry.date.getTime()).getBytes("UTF8");
/*  726 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*      */ 
/*      */ 
/*      */     
/*  730 */     privateKeyEntry.alias = paramString.toLowerCase(Locale.ENGLISH);
/*      */     
/*  732 */     privateKeyEntry.protectedPrivKey = (byte[])paramArrayOfbyte.clone();
/*  733 */     if (paramArrayOfCertificate != null) {
/*      */       
/*  735 */       if (paramArrayOfCertificate.length > 1 && !validateChain(paramArrayOfCertificate)) {
/*  736 */         throw new KeyStoreException("Certificate chain is not valid");
/*      */       }
/*      */       
/*  739 */       privateKeyEntry.chain = (Certificate[])paramArrayOfCertificate.clone();
/*  740 */       this.certificateCount += paramArrayOfCertificate.length;
/*      */       
/*  742 */       if (debug != null) {
/*  743 */         debug.println("Setting a " + privateKeyEntry.chain.length + "-certificate chain at alias '" + paramString + "'");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  749 */     this.privateKeyCount++;
/*  750 */     this.entries.put(paramString.toLowerCase(Locale.ENGLISH), privateKeyEntry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] getSalt() {
/*  760 */     byte[] arrayOfByte = new byte[20];
/*  761 */     if (this.random == null) {
/*  762 */       this.random = new SecureRandom();
/*      */     }
/*  764 */     this.random.nextBytes(arrayOfByte);
/*  765 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AlgorithmParameters getPBEAlgorithmParameters(String paramString) throws IOException {
/*  774 */     AlgorithmParameters algorithmParameters = null;
/*      */ 
/*      */ 
/*      */     
/*  778 */     PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(getSalt(), 50000);
/*      */     try {
/*  780 */       algorithmParameters = AlgorithmParameters.getInstance(paramString);
/*  781 */       algorithmParameters.init(pBEParameterSpec);
/*  782 */     } catch (Exception exception) {
/*  783 */       throw new IOException("getPBEAlgorithmParameters failed: " + exception
/*  784 */           .getMessage(), exception);
/*      */     } 
/*  786 */     return algorithmParameters;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AlgorithmParameters parseAlgParameters(ObjectIdentifier paramObjectIdentifier, DerInputStream paramDerInputStream) throws IOException {
/*  795 */     AlgorithmParameters algorithmParameters = null;
/*      */     try {
/*      */       DerValue derValue;
/*  798 */       if (paramDerInputStream.available() == 0) {
/*  799 */         derValue = null;
/*      */       } else {
/*  801 */         derValue = paramDerInputStream.getDerValue();
/*  802 */         if (derValue.tag == 5) {
/*  803 */           derValue = null;
/*      */         }
/*      */       } 
/*  806 */       if (derValue != null) {
/*  807 */         if (paramObjectIdentifier.equals(pbes2_OID)) {
/*  808 */           algorithmParameters = AlgorithmParameters.getInstance("PBES2");
/*      */         } else {
/*  810 */           algorithmParameters = AlgorithmParameters.getInstance("PBE");
/*      */         } 
/*  812 */         algorithmParameters.init(derValue.toByteArray());
/*      */       } 
/*  814 */     } catch (Exception exception) {
/*  815 */       throw new IOException("parseAlgParameters failed: " + exception
/*  816 */           .getMessage(), exception);
/*      */     } 
/*  818 */     return algorithmParameters;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SecretKey getPBEKey(char[] paramArrayOfchar) throws IOException {
/*  826 */     SecretKey secretKey = null;
/*      */     
/*      */     try {
/*  829 */       PBEKeySpec pBEKeySpec = new PBEKeySpec(paramArrayOfchar);
/*  830 */       SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBE");
/*  831 */       secretKey = secretKeyFactory.generateSecret(pBEKeySpec);
/*  832 */       pBEKeySpec.clearPassword();
/*  833 */     } catch (Exception exception) {
/*  834 */       throw new IOException("getSecretKey failed: " + exception
/*  835 */           .getMessage(), exception);
/*      */     } 
/*  837 */     return secretKey;
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
/*      */   private byte[] encryptPrivateKey(byte[] paramArrayOfbyte, KeyStore.PasswordProtection paramPasswordProtection) throws IOException, NoSuchAlgorithmException, UnrecoverableKeyException {
/*  853 */     byte[] arrayOfByte = null;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*      */       AlgorithmParameters algorithmParameters;
/*      */ 
/*      */       
/*  861 */       String str = paramPasswordProtection.getProtectionAlgorithm();
/*  862 */       if (str != null) {
/*      */         
/*  864 */         AlgorithmParameterSpec algorithmParameterSpec = paramPasswordProtection.getProtectionParameters();
/*  865 */         if (algorithmParameterSpec != null) {
/*  866 */           algorithmParameters = AlgorithmParameters.getInstance(str);
/*  867 */           algorithmParameters.init(algorithmParameterSpec);
/*      */         } else {
/*  869 */           algorithmParameters = getPBEAlgorithmParameters(str);
/*      */         } 
/*      */       } else {
/*      */         
/*  873 */         str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */             {
/*      */               public String run()
/*      */               {
/*  877 */                 String str = Security.getProperty(PKCS12KeyStore
/*  878 */                     .KEY_PROTECTION_ALGORITHM[0]);
/*  879 */                 if (str == null) {
/*  880 */                   str = Security.getProperty(PKCS12KeyStore
/*  881 */                       .KEY_PROTECTION_ALGORITHM[1]);
/*      */                 }
/*  883 */                 return str;
/*      */               }
/*      */             });
/*  886 */         if (str == null || str.isEmpty()) {
/*  887 */           str = "PBEWithSHA1AndDESede";
/*      */         }
/*  889 */         algorithmParameters = getPBEAlgorithmParameters(str);
/*      */       } 
/*      */       
/*  892 */       ObjectIdentifier objectIdentifier = mapPBEAlgorithmToOID(str);
/*  893 */       if (objectIdentifier == null) {
/*  894 */         throw new IOException("PBE algorithm '" + str + " 'is not supported for key entry protection");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  899 */       SecretKey secretKey = getPBEKey(paramPasswordProtection.getPassword());
/*  900 */       Cipher cipher = Cipher.getInstance(str);
/*  901 */       cipher.init(1, secretKey, algorithmParameters);
/*  902 */       byte[] arrayOfByte1 = cipher.doFinal(paramArrayOfbyte);
/*  903 */       AlgorithmId algorithmId = new AlgorithmId(objectIdentifier, cipher.getParameters());
/*      */       
/*  905 */       if (debug != null) {
/*  906 */         debug.println("  (Cipher algorithm: " + cipher.getAlgorithm() + ")");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  912 */       EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(algorithmId, arrayOfByte1);
/*      */       
/*  914 */       arrayOfByte = encryptedPrivateKeyInfo.getEncoded();
/*  915 */     } catch (Exception exception) {
/*      */ 
/*      */       
/*  918 */       UnrecoverableKeyException unrecoverableKeyException = new UnrecoverableKeyException("Encrypt Private Key failed: " + exception.getMessage());
/*  919 */       unrecoverableKeyException.initCause(exception);
/*  920 */       throw unrecoverableKeyException;
/*      */     } 
/*      */     
/*  923 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ObjectIdentifier mapPBEAlgorithmToOID(String paramString) throws NoSuchAlgorithmException {
/*  932 */     if (paramString.toLowerCase(Locale.ENGLISH).startsWith("pbewithhmacsha")) {
/*  933 */       return pbes2_OID;
/*      */     }
/*  935 */     return AlgorithmId.get(paramString).getOID();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String mapPBEParamsToAlgorithm(ObjectIdentifier paramObjectIdentifier, AlgorithmParameters paramAlgorithmParameters) throws NoSuchAlgorithmException {
/*  944 */     if (paramObjectIdentifier.equals(pbes2_OID) && paramAlgorithmParameters != null) {
/*  945 */       return paramAlgorithmParameters.toString();
/*      */     }
/*  947 */     return paramObjectIdentifier.toString();
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
/*      */   public synchronized void engineSetCertificateEntry(String paramString, Certificate paramCertificate) throws KeyStoreException {
/*  967 */     setCertEntry(paramString, paramCertificate, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setCertEntry(String paramString, Certificate paramCertificate, Set<KeyStore.Entry.Attribute> paramSet) throws KeyStoreException {
/*  976 */     Entry entry = this.entries.get(paramString.toLowerCase(Locale.ENGLISH));
/*  977 */     if (entry != null && entry instanceof KeyEntry) {
/*  978 */       throw new KeyStoreException("Cannot overwrite own certificate");
/*      */     }
/*      */     
/*  981 */     CertEntry certEntry = new CertEntry((X509Certificate)paramCertificate, null, paramString, AnyUsage, paramSet);
/*      */ 
/*      */     
/*  984 */     this.certificateCount++;
/*  985 */     this.entries.put(paramString.toLowerCase(Locale.ENGLISH), certEntry);
/*      */     
/*  987 */     if (debug != null) {
/*  988 */       debug.println("Setting a trusted certificate at alias '" + paramString + "'");
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
/*      */   public synchronized void engineDeleteEntry(String paramString) throws KeyStoreException {
/* 1003 */     if (debug != null) {
/* 1004 */       debug.println("Removing entry at alias '" + paramString + "'");
/*      */     }
/*      */     
/* 1007 */     Entry entry = this.entries.get(paramString.toLowerCase(Locale.ENGLISH));
/* 1008 */     if (entry instanceof PrivateKeyEntry) {
/* 1009 */       PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry)entry;
/* 1010 */       if (privateKeyEntry.chain != null) {
/* 1011 */         this.certificateCount -= privateKeyEntry.chain.length;
/*      */       }
/* 1013 */       this.privateKeyCount--;
/* 1014 */     } else if (entry instanceof CertEntry) {
/* 1015 */       this.certificateCount--;
/* 1016 */     } else if (entry instanceof SecretKeyEntry) {
/* 1017 */       this.secretKeyCount--;
/*      */     } 
/* 1019 */     this.entries.remove(paramString.toLowerCase(Locale.ENGLISH));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration<String> engineAliases() {
/* 1028 */     return Collections.enumeration(this.entries.keySet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean engineContainsAlias(String paramString) {
/* 1039 */     return this.entries.containsKey(paramString.toLowerCase(Locale.ENGLISH));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int engineSize() {
/* 1048 */     return this.entries.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean engineIsKeyEntry(String paramString) {
/* 1059 */     Entry entry = this.entries.get(paramString.toLowerCase(Locale.ENGLISH));
/* 1060 */     if (entry != null && entry instanceof KeyEntry) {
/* 1061 */       return true;
/*      */     }
/* 1063 */     return false;
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
/*      */   public boolean engineIsCertificateEntry(String paramString) {
/* 1075 */     Entry entry = this.entries.get(paramString.toLowerCase(Locale.ENGLISH));
/* 1076 */     if (entry != null && entry instanceof CertEntry && ((CertEntry)entry).trustedKeyUsage != null)
/*      */     {
/* 1078 */       return true;
/*      */     }
/* 1080 */     return false;
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
/*      */   public boolean engineEntryInstanceOf(String paramString, Class<? extends KeyStore.Entry> paramClass) {
/* 1103 */     if (paramClass == KeyStore.TrustedCertificateEntry.class) {
/* 1104 */       return engineIsCertificateEntry(paramString);
/*      */     }
/*      */     
/* 1107 */     Entry entry = this.entries.get(paramString.toLowerCase(Locale.ENGLISH));
/* 1108 */     if (paramClass == KeyStore.PrivateKeyEntry.class) {
/* 1109 */       return (entry != null && entry instanceof PrivateKeyEntry);
/*      */     }
/* 1111 */     if (paramClass == KeyStore.SecretKeyEntry.class) {
/* 1112 */       return (entry != null && entry instanceof SecretKeyEntry);
/*      */     }
/* 1114 */     return false;
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
/*      */   public String engineGetCertificateAlias(Certificate paramCertificate) {
/* 1134 */     Certificate certificate = null;
/*      */     
/* 1136 */     for (Enumeration<String> enumeration = engineAliases(); enumeration.hasMoreElements(); ) {
/* 1137 */       String str = enumeration.nextElement();
/* 1138 */       Entry entry = this.entries.get(str);
/* 1139 */       if (entry instanceof PrivateKeyEntry) {
/* 1140 */         if (((PrivateKeyEntry)entry).chain != null) {
/* 1141 */           certificate = ((PrivateKeyEntry)entry).chain[0];
/*      */         }
/* 1143 */       } else if (entry instanceof CertEntry && ((CertEntry)entry).trustedKeyUsage != null) {
/*      */         
/* 1145 */         certificate = ((CertEntry)entry).cert;
/*      */       } else {
/*      */         continue;
/*      */       } 
/* 1149 */       if (certificate != null && certificate.equals(paramCertificate)) {
/* 1150 */         return str;
/*      */       }
/*      */     } 
/* 1153 */     return null;
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
/*      */   public synchronized void engineStore(OutputStream paramOutputStream, char[] paramArrayOfchar) throws IOException, NoSuchAlgorithmException, CertificateException {
/* 1173 */     if (paramArrayOfchar == null) {
/* 1174 */       throw new IllegalArgumentException("password can't be null");
/*      */     }
/*      */ 
/*      */     
/* 1178 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/*      */ 
/*      */     
/* 1181 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 1182 */     derOutputStream2.putInteger(3);
/* 1183 */     byte[] arrayOfByte1 = derOutputStream2.toByteArray();
/* 1184 */     derOutputStream1.write(arrayOfByte1);
/*      */ 
/*      */     
/* 1187 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/*      */ 
/*      */     
/* 1190 */     DerOutputStream derOutputStream4 = new DerOutputStream();
/*      */ 
/*      */     
/* 1193 */     if (this.privateKeyCount > 0 || this.secretKeyCount > 0) {
/*      */       
/* 1195 */       if (debug != null) {
/* 1196 */         debug.println("Storing " + (this.privateKeyCount + this.secretKeyCount) + " protected key(s) in a PKCS#7 data");
/*      */       }
/*      */ 
/*      */       
/* 1200 */       byte[] arrayOfByte = createSafeContent();
/* 1201 */       ContentInfo contentInfo1 = new ContentInfo(arrayOfByte);
/* 1202 */       contentInfo1.encode(derOutputStream4);
/*      */     } 
/*      */ 
/*      */     
/* 1206 */     if (this.certificateCount > 0) {
/*      */       
/* 1208 */       if (debug != null) {
/* 1209 */         debug.println("Storing " + this.certificateCount + " certificate(s) in a PKCS#7 encryptedData");
/*      */       }
/*      */ 
/*      */       
/* 1213 */       byte[] arrayOfByte = createEncryptedData(paramArrayOfchar);
/* 1214 */       ContentInfo contentInfo1 = new ContentInfo(ContentInfo.ENCRYPTED_DATA_OID, new DerValue(arrayOfByte));
/*      */ 
/*      */       
/* 1217 */       contentInfo1.encode(derOutputStream4);
/*      */     } 
/*      */ 
/*      */     
/* 1221 */     DerOutputStream derOutputStream5 = new DerOutputStream();
/* 1222 */     derOutputStream5.write((byte)48, derOutputStream4);
/* 1223 */     byte[] arrayOfByte2 = derOutputStream5.toByteArray();
/*      */ 
/*      */     
/* 1226 */     ContentInfo contentInfo = new ContentInfo(arrayOfByte2);
/* 1227 */     contentInfo.encode(derOutputStream3);
/* 1228 */     byte[] arrayOfByte3 = derOutputStream3.toByteArray();
/* 1229 */     derOutputStream1.write(arrayOfByte3);
/*      */ 
/*      */     
/* 1232 */     byte[] arrayOfByte4 = calculateMac(paramArrayOfchar, arrayOfByte2);
/* 1233 */     derOutputStream1.write(arrayOfByte4);
/*      */ 
/*      */     
/* 1236 */     DerOutputStream derOutputStream6 = new DerOutputStream();
/* 1237 */     derOutputStream6.write((byte)48, derOutputStream1);
/* 1238 */     byte[] arrayOfByte5 = derOutputStream6.toByteArray();
/* 1239 */     paramOutputStream.write(arrayOfByte5);
/* 1240 */     paramOutputStream.flush();
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
/*      */   public KeyStore.Entry engineGetEntry(String paramString, KeyStore.ProtectionParameter paramProtectionParameter) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException {
/* 1273 */     if (!engineContainsAlias(paramString)) {
/* 1274 */       return null;
/*      */     }
/*      */     
/* 1277 */     Entry entry = this.entries.get(paramString.toLowerCase(Locale.ENGLISH));
/* 1278 */     if (paramProtectionParameter == null) {
/* 1279 */       if (engineIsCertificateEntry(paramString)) {
/* 1280 */         if (entry instanceof CertEntry && ((CertEntry)entry).trustedKeyUsage != null) {
/*      */ 
/*      */           
/* 1283 */           if (debug != null) {
/* 1284 */             debug.println("Retrieved a trusted certificate at alias '" + paramString + "'");
/*      */           }
/*      */ 
/*      */           
/* 1288 */           return new KeyStore.TrustedCertificateEntry(((CertEntry)entry).cert, 
/* 1289 */               getAttributes(entry));
/*      */         } 
/*      */       } else {
/* 1292 */         throw new UnrecoverableKeyException("requested entry requires a password");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1297 */     if (paramProtectionParameter instanceof KeyStore.PasswordProtection) {
/* 1298 */       if (engineIsCertificateEntry(paramString)) {
/* 1299 */         throw new UnsupportedOperationException("trusted certificate entries are not password-protected");
/*      */       }
/* 1301 */       if (engineIsKeyEntry(paramString)) {
/* 1302 */         KeyStore.PasswordProtection passwordProtection = (KeyStore.PasswordProtection)paramProtectionParameter;
/*      */         
/* 1304 */         char[] arrayOfChar = passwordProtection.getPassword();
/*      */         
/* 1306 */         Key key = engineGetKey(paramString, arrayOfChar);
/* 1307 */         if (key instanceof PrivateKey) {
/* 1308 */           Certificate[] arrayOfCertificate = engineGetCertificateChain(paramString);
/*      */           
/* 1310 */           return new KeyStore.PrivateKeyEntry((PrivateKey)key, arrayOfCertificate, 
/* 1311 */               getAttributes(entry));
/*      */         } 
/* 1313 */         if (key instanceof SecretKey)
/*      */         {
/* 1315 */           return new KeyStore.SecretKeyEntry((SecretKey)key, 
/* 1316 */               getAttributes(entry));
/*      */         }
/* 1318 */       } else if (!engineIsKeyEntry(paramString)) {
/* 1319 */         throw new UnsupportedOperationException("untrusted certificate entries are not password-protected");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1325 */     throw new UnsupportedOperationException();
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
/*      */   public synchronized void engineSetEntry(String paramString, KeyStore.Entry paramEntry, KeyStore.ProtectionParameter paramProtectionParameter) throws KeyStoreException {
/* 1351 */     if (paramProtectionParameter != null && !(paramProtectionParameter instanceof KeyStore.PasswordProtection))
/*      */     {
/* 1353 */       throw new KeyStoreException("unsupported protection parameter");
/*      */     }
/* 1355 */     KeyStore.PasswordProtection passwordProtection = null;
/* 1356 */     if (paramProtectionParameter != null) {
/* 1357 */       passwordProtection = (KeyStore.PasswordProtection)paramProtectionParameter;
/*      */     }
/*      */ 
/*      */     
/* 1361 */     if (paramEntry instanceof KeyStore.TrustedCertificateEntry) {
/* 1362 */       if (paramProtectionParameter != null && passwordProtection.getPassword() != null)
/*      */       {
/* 1364 */         throw new KeyStoreException("trusted certificate entries are not password-protected");
/*      */       }
/*      */       
/* 1367 */       KeyStore.TrustedCertificateEntry trustedCertificateEntry = (KeyStore.TrustedCertificateEntry)paramEntry;
/*      */       
/* 1369 */       setCertEntry(paramString, trustedCertificateEntry.getTrustedCertificate(), trustedCertificateEntry
/* 1370 */           .getAttributes());
/*      */       
/*      */       return;
/*      */     } 
/* 1374 */     if (paramEntry instanceof KeyStore.PrivateKeyEntry) {
/* 1375 */       if (passwordProtection == null || passwordProtection.getPassword() == null)
/*      */       {
/* 1377 */         throw new KeyStoreException("non-null password required to create PrivateKeyEntry");
/*      */       }
/*      */       
/* 1380 */       KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)paramEntry;
/* 1381 */       setKeyEntry(paramString, privateKeyEntry.getPrivateKey(), passwordProtection, privateKeyEntry
/* 1382 */           .getCertificateChain(), privateKeyEntry.getAttributes());
/*      */       
/*      */       return;
/*      */     } 
/* 1386 */     if (paramEntry instanceof KeyStore.SecretKeyEntry) {
/* 1387 */       if (passwordProtection == null || passwordProtection.getPassword() == null)
/*      */       {
/* 1389 */         throw new KeyStoreException("non-null password required to create SecretKeyEntry");
/*      */       }
/*      */       
/* 1392 */       KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry)paramEntry;
/* 1393 */       setKeyEntry(paramString, secretKeyEntry.getSecretKey(), passwordProtection, (Certificate[])null, secretKeyEntry
/* 1394 */           .getAttributes());
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1400 */     throw new KeyStoreException("unsupported entry type: " + paramEntry
/* 1401 */         .getClass().getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Set<KeyStore.Entry.Attribute> getAttributes(Entry paramEntry) {
/* 1409 */     if (paramEntry.attributes == null) {
/* 1410 */       paramEntry.attributes = new HashSet<>();
/*      */     }
/*      */ 
/*      */     
/* 1414 */     paramEntry.attributes.add(new PKCS12Attribute(PKCS9FriendlyName_OID
/* 1415 */           .toString(), paramEntry.alias));
/*      */ 
/*      */     
/* 1418 */     byte[] arrayOfByte = paramEntry.keyId;
/* 1419 */     if (arrayOfByte != null) {
/* 1420 */       paramEntry.attributes.add(new PKCS12Attribute(PKCS9LocalKeyId_OID
/* 1421 */             .toString(), Debug.toString(arrayOfByte)));
/*      */     }
/*      */ 
/*      */     
/* 1425 */     if (paramEntry instanceof CertEntry) {
/* 1426 */       ObjectIdentifier[] arrayOfObjectIdentifier = ((CertEntry)paramEntry).trustedKeyUsage;
/*      */       
/* 1428 */       if (arrayOfObjectIdentifier != null) {
/* 1429 */         if (arrayOfObjectIdentifier.length == 1) {
/* 1430 */           paramEntry.attributes.add(new PKCS12Attribute(TrustedKeyUsage_OID
/* 1431 */                 .toString(), arrayOfObjectIdentifier[0]
/* 1432 */                 .toString()));
/*      */         } else {
/* 1434 */           paramEntry.attributes.add(new PKCS12Attribute(TrustedKeyUsage_OID
/* 1435 */                 .toString(), 
/* 1436 */                 Arrays.toString((Object[])arrayOfObjectIdentifier)));
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1441 */     return paramEntry.attributes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] generateHash(byte[] paramArrayOfbyte) throws IOException {
/* 1449 */     byte[] arrayOfByte = null;
/*      */     
/*      */     try {
/* 1452 */       MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
/* 1453 */       messageDigest.update(paramArrayOfbyte);
/* 1454 */       arrayOfByte = messageDigest.digest();
/* 1455 */     } catch (Exception exception) {
/* 1456 */       throw new IOException("generateHash failed: " + exception, exception);
/*      */     } 
/* 1458 */     return arrayOfByte;
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
/*      */   private byte[] calculateMac(char[] paramArrayOfchar, byte[] paramArrayOfbyte) throws IOException {
/* 1471 */     byte[] arrayOfByte = null;
/* 1472 */     String str = "SHA1";
/*      */ 
/*      */     
/*      */     try {
/* 1476 */       byte[] arrayOfByte1 = getSalt();
/*      */ 
/*      */       
/* 1479 */       Mac mac = Mac.getInstance("HmacPBESHA1");
/* 1480 */       PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(arrayOfByte1, 100000);
/*      */       
/* 1482 */       SecretKey secretKey = getPBEKey(paramArrayOfchar);
/* 1483 */       mac.init(secretKey, pBEParameterSpec);
/* 1484 */       mac.update(paramArrayOfbyte);
/* 1485 */       byte[] arrayOfByte2 = mac.doFinal();
/*      */ 
/*      */       
/* 1488 */       MacData macData = new MacData(str, arrayOfByte2, arrayOfByte1, 100000);
/*      */       
/* 1490 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 1491 */       derOutputStream.write(macData.getEncoded());
/* 1492 */       arrayOfByte = derOutputStream.toByteArray();
/* 1493 */     } catch (Exception exception) {
/* 1494 */       throw new IOException("calculateMac failed: " + exception, exception);
/*      */     } 
/* 1496 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean validateChain(Certificate[] paramArrayOfCertificate) {
/* 1505 */     for (byte b = 0; b < paramArrayOfCertificate.length - 1; b++) {
/*      */       
/* 1507 */       X500Principal x500Principal1 = ((X509Certificate)paramArrayOfCertificate[b]).getIssuerX500Principal();
/*      */       
/* 1509 */       X500Principal x500Principal2 = ((X509Certificate)paramArrayOfCertificate[b + 1]).getSubjectX500Principal();
/* 1510 */       if (!x500Principal1.equals(x500Principal2)) {
/* 1511 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1517 */     HashSet hashSet = new HashSet(Arrays.asList((Object[])paramArrayOfCertificate));
/* 1518 */     return (hashSet.size() == paramArrayOfCertificate.length);
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
/*      */   
/*      */   private byte[] getBagAttributes(String paramString, byte[] paramArrayOfbyte, Set<KeyStore.Entry.Attribute> paramSet) throws IOException {
/* 1559 */     return getBagAttributes(paramString, paramArrayOfbyte, null, paramSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] getBagAttributes(String paramString, byte[] paramArrayOfbyte, ObjectIdentifier[] paramArrayOfObjectIdentifier, Set<KeyStore.Entry.Attribute> paramSet) throws IOException {
/* 1566 */     byte[] arrayOfByte1 = null;
/* 1567 */     byte[] arrayOfByte2 = null;
/* 1568 */     byte[] arrayOfByte3 = null;
/*      */ 
/*      */     
/* 1571 */     if (paramString == null && paramArrayOfbyte == null && arrayOfByte3 == null) {
/* 1572 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1576 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/*      */ 
/*      */     
/* 1579 */     if (paramString != null) {
/* 1580 */       DerOutputStream derOutputStream3 = new DerOutputStream();
/* 1581 */       derOutputStream3.putOID(PKCS9FriendlyName_OID);
/* 1582 */       DerOutputStream derOutputStream4 = new DerOutputStream();
/* 1583 */       DerOutputStream derOutputStream5 = new DerOutputStream();
/* 1584 */       derOutputStream4.putBMPString(paramString);
/* 1585 */       derOutputStream3.write((byte)49, derOutputStream4);
/* 1586 */       derOutputStream5.write((byte)48, derOutputStream3);
/* 1587 */       arrayOfByte2 = derOutputStream5.toByteArray();
/*      */     } 
/*      */ 
/*      */     
/* 1591 */     if (paramArrayOfbyte != null) {
/* 1592 */       DerOutputStream derOutputStream3 = new DerOutputStream();
/* 1593 */       derOutputStream3.putOID(PKCS9LocalKeyId_OID);
/* 1594 */       DerOutputStream derOutputStream4 = new DerOutputStream();
/* 1595 */       DerOutputStream derOutputStream5 = new DerOutputStream();
/* 1596 */       derOutputStream4.putOctetString(paramArrayOfbyte);
/* 1597 */       derOutputStream3.write((byte)49, derOutputStream4);
/* 1598 */       derOutputStream5.write((byte)48, derOutputStream3);
/* 1599 */       arrayOfByte1 = derOutputStream5.toByteArray();
/*      */     } 
/*      */ 
/*      */     
/* 1603 */     if (paramArrayOfObjectIdentifier != null) {
/* 1604 */       DerOutputStream derOutputStream3 = new DerOutputStream();
/* 1605 */       derOutputStream3.putOID(TrustedKeyUsage_OID);
/* 1606 */       DerOutputStream derOutputStream4 = new DerOutputStream();
/* 1607 */       DerOutputStream derOutputStream5 = new DerOutputStream();
/* 1608 */       for (ObjectIdentifier objectIdentifier : paramArrayOfObjectIdentifier) {
/* 1609 */         derOutputStream4.putOID(objectIdentifier);
/*      */       }
/* 1611 */       derOutputStream3.write((byte)49, derOutputStream4);
/* 1612 */       derOutputStream5.write((byte)48, derOutputStream3);
/* 1613 */       arrayOfByte3 = derOutputStream5.toByteArray();
/*      */     } 
/*      */     
/* 1616 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 1617 */     if (arrayOfByte2 != null) {
/* 1618 */       derOutputStream2.write(arrayOfByte2);
/*      */     }
/* 1620 */     if (arrayOfByte1 != null) {
/* 1621 */       derOutputStream2.write(arrayOfByte1);
/*      */     }
/* 1623 */     if (arrayOfByte3 != null) {
/* 1624 */       derOutputStream2.write(arrayOfByte3);
/*      */     }
/*      */     
/* 1627 */     if (paramSet != null) {
/* 1628 */       for (KeyStore.Entry.Attribute attribute : paramSet) {
/* 1629 */         String str = attribute.getName();
/*      */         
/* 1631 */         if (CORE_ATTRIBUTES[0].equals(str) || CORE_ATTRIBUTES[1]
/* 1632 */           .equals(str) || CORE_ATTRIBUTES[2]
/* 1633 */           .equals(str)) {
/*      */           continue;
/*      */         }
/* 1636 */         derOutputStream2.write(((PKCS12Attribute)attribute).getEncoded());
/*      */       } 
/*      */     }
/*      */     
/* 1640 */     derOutputStream1.write((byte)49, derOutputStream2);
/* 1641 */     return derOutputStream1.toByteArray();
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
/*      */   private byte[] createEncryptedData(char[] paramArrayOfchar) throws CertificateException, IOException {
/* 1653 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 1654 */     for (Enumeration<String> enumeration = engineAliases(); enumeration.hasMoreElements(); ) {
/*      */       Certificate[] arrayOfCertificate;
/* 1656 */       String str = enumeration.nextElement();
/* 1657 */       Entry entry = this.entries.get(str);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1662 */       if (entry instanceof PrivateKeyEntry) {
/* 1663 */         PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry)entry;
/* 1664 */         if (privateKeyEntry.chain != null) {
/* 1665 */           arrayOfCertificate = privateKeyEntry.chain;
/*      */         } else {
/* 1667 */           arrayOfCertificate = new Certificate[0];
/*      */         } 
/* 1669 */       } else if (entry instanceof CertEntry) {
/* 1670 */         arrayOfCertificate = new Certificate[] { ((CertEntry)entry).cert };
/*      */       } else {
/* 1672 */         arrayOfCertificate = new Certificate[0];
/*      */       } 
/*      */       
/* 1675 */       for (byte b = 0; b < arrayOfCertificate.length; b++) {
/*      */         
/* 1677 */         DerOutputStream derOutputStream5 = new DerOutputStream();
/* 1678 */         derOutputStream5.putOID(CertBag_OID);
/*      */ 
/*      */         
/* 1681 */         DerOutputStream derOutputStream6 = new DerOutputStream();
/* 1682 */         derOutputStream6.putOID(PKCS9CertType_OID);
/*      */ 
/*      */         
/* 1685 */         DerOutputStream derOutputStream7 = new DerOutputStream();
/* 1686 */         X509Certificate x509Certificate = (X509Certificate)arrayOfCertificate[b];
/* 1687 */         derOutputStream7.putOctetString(x509Certificate.getEncoded());
/* 1688 */         derOutputStream6.write(DerValue.createTag(-128, true, (byte)0), derOutputStream7);
/*      */ 
/*      */ 
/*      */         
/* 1692 */         DerOutputStream derOutputStream8 = new DerOutputStream();
/* 1693 */         derOutputStream8.write((byte)48, derOutputStream6);
/* 1694 */         byte[] arrayOfByte3 = derOutputStream8.toByteArray();
/*      */ 
/*      */         
/* 1697 */         DerOutputStream derOutputStream9 = new DerOutputStream();
/* 1698 */         derOutputStream9.write(arrayOfByte3);
/*      */         
/* 1700 */         derOutputStream5.write(DerValue.createTag(-128, true, (byte)0), derOutputStream9);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1706 */         byte[] arrayOfByte4 = null;
/* 1707 */         if (b == 0) {
/*      */           
/* 1709 */           if (entry instanceof KeyEntry) {
/* 1710 */             KeyEntry keyEntry = (KeyEntry)entry;
/*      */             
/* 1712 */             arrayOfByte4 = getBagAttributes(keyEntry.alias, keyEntry.keyId, keyEntry.attributes);
/*      */           } else {
/*      */             
/* 1715 */             CertEntry certEntry = (CertEntry)entry;
/*      */             
/* 1717 */             arrayOfByte4 = getBagAttributes(certEntry.alias, certEntry.keyId, certEntry.trustedKeyUsage, certEntry.attributes);
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1728 */           arrayOfByte4 = getBagAttributes(x509Certificate
/* 1729 */               .getSubjectX500Principal().getName(), null, entry.attributes);
/*      */         } 
/*      */         
/* 1732 */         if (arrayOfByte4 != null) {
/* 1733 */           derOutputStream5.write(arrayOfByte4);
/*      */         }
/*      */ 
/*      */         
/* 1737 */         derOutputStream1.write((byte)48, derOutputStream5);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1742 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 1743 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 1744 */     byte[] arrayOfByte1 = derOutputStream2.toByteArray();
/*      */ 
/*      */     
/* 1747 */     byte[] arrayOfByte2 = encryptContent(arrayOfByte1, paramArrayOfchar);
/*      */ 
/*      */     
/* 1750 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/* 1751 */     DerOutputStream derOutputStream4 = new DerOutputStream();
/* 1752 */     derOutputStream3.putInteger(0);
/* 1753 */     derOutputStream3.write(arrayOfByte2);
/* 1754 */     derOutputStream4.write((byte)48, derOutputStream3);
/* 1755 */     return derOutputStream4.toByteArray();
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
/*      */   private byte[] createSafeContent() throws CertificateException, IOException {
/* 1768 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 1769 */     for (Enumeration<String> enumeration = engineAliases(); enumeration.hasMoreElements(); ) {
/*      */       
/* 1771 */       String str = enumeration.nextElement();
/* 1772 */       Entry entry = this.entries.get(str);
/* 1773 */       if (entry == null || !(entry instanceof KeyEntry)) {
/*      */         continue;
/*      */       }
/* 1776 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 1777 */       KeyEntry keyEntry = (KeyEntry)entry;
/*      */ 
/*      */       
/* 1780 */       if (keyEntry instanceof PrivateKeyEntry) {
/*      */         
/* 1782 */         derOutputStream.putOID(PKCS8ShroudedKeyBag_OID);
/*      */ 
/*      */         
/* 1785 */         byte[] arrayOfByte1 = ((PrivateKeyEntry)keyEntry).protectedPrivKey;
/* 1786 */         EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = null;
/*      */         try {
/* 1788 */           encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(arrayOfByte1);
/*      */         }
/* 1790 */         catch (IOException iOException) {
/* 1791 */           throw new IOException("Private key not stored as PKCS#8 EncryptedPrivateKeyInfo" + iOException
/*      */               
/* 1793 */               .getMessage());
/*      */         } 
/*      */ 
/*      */         
/* 1797 */         DerOutputStream derOutputStream3 = new DerOutputStream();
/* 1798 */         derOutputStream3.write(encryptedPrivateKeyInfo.getEncoded());
/* 1799 */         derOutputStream.write(DerValue.createTag(-128, true, (byte)0), derOutputStream3);
/*      */ 
/*      */       
/*      */       }
/* 1803 */       else if (keyEntry instanceof SecretKeyEntry) {
/*      */         
/* 1805 */         derOutputStream.putOID(SecretBag_OID);
/*      */ 
/*      */         
/* 1808 */         DerOutputStream derOutputStream3 = new DerOutputStream();
/* 1809 */         derOutputStream3.putOID(PKCS8ShroudedKeyBag_OID);
/*      */ 
/*      */         
/* 1812 */         DerOutputStream derOutputStream4 = new DerOutputStream();
/* 1813 */         derOutputStream4.putOctetString(((SecretKeyEntry)keyEntry).protectedSecretKey);
/*      */         
/* 1815 */         derOutputStream3.write(DerValue.createTag(-128, true, (byte)0), derOutputStream4);
/*      */ 
/*      */ 
/*      */         
/* 1819 */         DerOutputStream derOutputStream5 = new DerOutputStream();
/* 1820 */         derOutputStream5.write((byte)48, derOutputStream3);
/* 1821 */         byte[] arrayOfByte1 = derOutputStream5.toByteArray();
/*      */ 
/*      */         
/* 1824 */         DerOutputStream derOutputStream6 = new DerOutputStream();
/* 1825 */         derOutputStream6.write(arrayOfByte1);
/*      */ 
/*      */         
/* 1828 */         derOutputStream.write(DerValue.createTag(-128, true, (byte)0), derOutputStream6);
/*      */       } else {
/*      */         continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1836 */       byte[] arrayOfByte = getBagAttributes(str, entry.keyId, entry.attributes);
/* 1837 */       derOutputStream.write(arrayOfByte);
/*      */ 
/*      */       
/* 1840 */       derOutputStream1.write((byte)48, derOutputStream);
/*      */     } 
/*      */ 
/*      */     
/* 1844 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 1845 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 1846 */     return derOutputStream2.toByteArray();
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
/*      */   private byte[] encryptContent(byte[] paramArrayOfbyte, char[] paramArrayOfchar) throws IOException {
/* 1862 */     byte[] arrayOfByte1 = null;
/*      */ 
/*      */ 
/*      */     
/* 1866 */     AlgorithmParameters algorithmParameters = getPBEAlgorithmParameters("PBEWithSHA1AndRC2_40");
/* 1867 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 1868 */     AlgorithmId algorithmId = new AlgorithmId(pbeWithSHAAnd40BitRC2CBC_OID, algorithmParameters);
/*      */     
/* 1870 */     algorithmId.encode(derOutputStream1);
/* 1871 */     byte[] arrayOfByte2 = derOutputStream1.toByteArray();
/*      */ 
/*      */     
/*      */     try {
/* 1875 */       SecretKey secretKey = getPBEKey(paramArrayOfchar);
/* 1876 */       Cipher cipher = Cipher.getInstance("PBEWithSHA1AndRC2_40");
/* 1877 */       cipher.init(1, secretKey, algorithmParameters);
/* 1878 */       arrayOfByte1 = cipher.doFinal(paramArrayOfbyte);
/*      */       
/* 1880 */       if (debug != null) {
/* 1881 */         debug.println("  (Cipher algorithm: " + cipher.getAlgorithm() + ")");
/*      */       
/*      */       }
/*      */     }
/* 1885 */     catch (Exception exception) {
/* 1886 */       throw new IOException("Failed to encrypt safe contents entry: " + exception, exception);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1891 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 1892 */     derOutputStream2.putOID(ContentInfo.DATA_OID);
/* 1893 */     derOutputStream2.write(arrayOfByte2);
/*      */ 
/*      */     
/* 1896 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/* 1897 */     derOutputStream3.putOctetString(arrayOfByte1);
/* 1898 */     derOutputStream2.writeImplicit(DerValue.createTag(-128, false, (byte)0), derOutputStream3);
/*      */ 
/*      */ 
/*      */     
/* 1902 */     DerOutputStream derOutputStream4 = new DerOutputStream();
/* 1903 */     derOutputStream4.write((byte)48, derOutputStream2);
/* 1904 */     return derOutputStream4.toByteArray();
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
/*      */   public synchronized void engineLoad(InputStream paramInputStream, char[] paramArrayOfchar) throws IOException, NoSuchAlgorithmException, CertificateException {
/*      */     byte[] arrayOfByte;
/* 1928 */     Object object1 = null;
/* 1929 */     Object object2 = null;
/* 1930 */     Object object3 = null;
/*      */     
/* 1932 */     if (paramInputStream == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1936 */     this.counter = 0;
/*      */     
/* 1938 */     DerValue derValue = new DerValue(paramInputStream);
/* 1939 */     DerInputStream derInputStream1 = derValue.toDerInputStream();
/* 1940 */     int i = derInputStream1.getInteger();
/*      */     
/* 1942 */     if (i != 3) {
/* 1943 */       throw new IOException("PKCS12 keystore not in version 3 format");
/*      */     }
/*      */     
/* 1946 */     this.entries.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1952 */     ContentInfo contentInfo = new ContentInfo(derInputStream1);
/* 1953 */     ObjectIdentifier objectIdentifier = contentInfo.getContentType();
/*      */     
/* 1955 */     if (objectIdentifier.equals(ContentInfo.DATA_OID)) {
/* 1956 */       arrayOfByte = contentInfo.getData();
/*      */     } else {
/* 1958 */       throw new IOException("public key protected PKCS12 not supported");
/*      */     } 
/*      */     
/* 1961 */     DerInputStream derInputStream2 = new DerInputStream(arrayOfByte);
/* 1962 */     DerValue[] arrayOfDerValue = derInputStream2.getSequence(2);
/* 1963 */     int j = arrayOfDerValue.length;
/*      */ 
/*      */     
/* 1966 */     this.privateKeyCount = 0;
/* 1967 */     this.secretKeyCount = 0;
/* 1968 */     this.certificateCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1973 */     byte b1 = 0; while (true) { byte[] arrayOfByte1; if (b1 < j)
/*      */       
/*      */       { 
/*      */         
/* 1977 */         byte[] arrayOfByte2 = null;
/*      */         
/* 1979 */         DerInputStream derInputStream3 = new DerInputStream(arrayOfDerValue[b1].toByteArray());
/* 1980 */         ContentInfo contentInfo1 = new ContentInfo(derInputStream3);
/* 1981 */         objectIdentifier = contentInfo1.getContentType();
/* 1982 */         arrayOfByte1 = null;
/* 1983 */         if (objectIdentifier.equals(ContentInfo.DATA_OID))
/*      */         
/* 1985 */         { if (debug != null) {
/* 1986 */             debug.println("Loading PKCS#7 data");
/*      */           }
/*      */           
/* 1989 */           arrayOfByte1 = contentInfo1.getData(); }
/* 1990 */         else { if (objectIdentifier.equals(ContentInfo.ENCRYPTED_DATA_OID))
/* 1991 */           { if (paramArrayOfchar == null)
/*      */             
/* 1993 */             { if (debug != null) {
/* 1994 */                 debug.println("Warning: skipping PKCS#7 encryptedData - no password was supplied");
/*      */               
/*      */               }
/*      */                }
/*      */             
/*      */             else
/*      */             
/* 2001 */             { DerInputStream derInputStream4 = contentInfo1.getContent().toDerInputStream();
/* 2002 */               int k = derInputStream4.getInteger();
/* 2003 */               DerValue[] arrayOfDerValue1 = derInputStream4.getSequence(2);
/* 2004 */               ObjectIdentifier objectIdentifier1 = arrayOfDerValue1[0].getOID();
/* 2005 */               arrayOfByte2 = arrayOfDerValue1[1].toByteArray();
/* 2006 */               if (!arrayOfDerValue1[2].isContextSpecific((byte)0)) {
/* 2007 */                 throw new IOException("encrypted content not present!");
/*      */               }
/* 2009 */               byte b = 4;
/* 2010 */               if (arrayOfDerValue1[2].isConstructed())
/* 2011 */                 b = (byte)(b | 0x20); 
/* 2012 */               arrayOfDerValue1[2].resetTag(b);
/* 2013 */               arrayOfByte1 = arrayOfDerValue1[2].getOctetString();
/*      */ 
/*      */               
/* 2016 */               DerInputStream derInputStream5 = arrayOfDerValue1[1].toDerInputStream();
/* 2017 */               ObjectIdentifier objectIdentifier2 = derInputStream5.getOID();
/* 2018 */               AlgorithmParameters algorithmParameters = parseAlgParameters(objectIdentifier2, derInputStream5);
/*      */ 
/*      */               
/* 2021 */               int m = 0;
/*      */               
/* 2023 */               if (algorithmParameters != null) {
/*      */                 PBEParameterSpec pBEParameterSpec;
/*      */                 try {
/* 2026 */                   pBEParameterSpec = algorithmParameters.<PBEParameterSpec>getParameterSpec(PBEParameterSpec.class);
/* 2027 */                 } catch (InvalidParameterSpecException invalidParameterSpecException) {
/* 2028 */                   throw new IOException("Invalid PBE algorithm parameters");
/*      */                 } 
/*      */                 
/* 2031 */                 m = pBEParameterSpec.getIterationCount();
/*      */                 
/* 2033 */                 if (m > 5000000) {
/* 2034 */                   throw new IOException("PBE iteration count too large");
/*      */                 }
/*      */               } 
/*      */               
/* 2038 */               if (debug != null) {
/* 2039 */                 debug.println("Loading PKCS#7 encryptedData (" + (new AlgorithmId(objectIdentifier2))
/* 2040 */                     .getName() + " iterations: " + m + ")");
/*      */               }
/*      */ 
/*      */ 
/*      */               
/*      */               while (true) {
/*      */                 try {
/* 2047 */                   SecretKey secretKey = getPBEKey(paramArrayOfchar);
/* 2048 */                   Cipher cipher = Cipher.getInstance(objectIdentifier2.toString());
/* 2049 */                   cipher.init(2, secretKey, algorithmParameters);
/* 2050 */                   arrayOfByte1 = cipher.doFinal(arrayOfByte1);
/*      */                 }
/* 2052 */                 catch (Exception exception) {
/* 2053 */                   if (paramArrayOfchar.length == 0) {
/*      */ 
/*      */                     
/* 2056 */                     paramArrayOfchar = new char[1];
/*      */                     continue;
/*      */                   } 
/* 2059 */                   throw new IOException("keystore password was incorrect", new UnrecoverableKeyException("failed to decrypt safe contents entry: " + exception));
/*      */                 } 
/*      */ 
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */ 
/*      */               
/* 2068 */               derInputStream4 = new DerInputStream(arrayOfByte1);
/* 2069 */               loadSafeContents(derInputStream4, paramArrayOfchar); }  } else { throw new IOException("public key protected PKCS12 not supported"); }  b1++; }  } else { break; }  DerInputStream derInputStream = new DerInputStream(arrayOfByte1); loadSafeContents(derInputStream, paramArrayOfchar); }
/*      */ 
/*      */ 
/*      */     
/* 2073 */     if (paramArrayOfchar != null && derInputStream1.available() > 0) {
/* 2074 */       MacData macData = new MacData(derInputStream1);
/* 2075 */       int k = macData.getIterations();
/*      */       
/*      */       try {
/* 2078 */         if (k > 5000000) {
/* 2079 */           throw new InvalidAlgorithmParameterException("MAC iteration count too large: " + k);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2084 */         String str = macData.getDigestAlgName().toUpperCase(Locale.ENGLISH);
/*      */ 
/*      */         
/* 2087 */         str = str.replace("-", "");
/*      */ 
/*      */         
/* 2090 */         Mac mac = Mac.getInstance("HmacPBE" + str);
/*      */         
/* 2092 */         PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(macData.getSalt(), k);
/* 2093 */         SecretKey secretKey = getPBEKey(paramArrayOfchar);
/* 2094 */         mac.init(secretKey, pBEParameterSpec);
/* 2095 */         mac.update(arrayOfByte);
/* 2096 */         byte[] arrayOfByte1 = mac.doFinal();
/*      */         
/* 2098 */         if (debug != null) {
/* 2099 */           debug.println("Checking keystore integrity (" + mac
/* 2100 */               .getAlgorithm() + " iterations: " + k + ")");
/*      */         }
/*      */         
/* 2103 */         if (!MessageDigest.isEqual(macData.getDigest(), arrayOfByte1)) {
/* 2104 */           throw new UnrecoverableKeyException("Failed PKCS12 integrity checking");
/*      */         }
/*      */       }
/* 2107 */       catch (Exception exception) {
/* 2108 */         throw new IOException("Integrity check failed: " + exception, exception);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2116 */     PrivateKeyEntry[] arrayOfPrivateKeyEntry = this.keyList.<PrivateKeyEntry>toArray(new PrivateKeyEntry[this.keyList.size()]);
/* 2117 */     for (byte b2 = 0; b2 < arrayOfPrivateKeyEntry.length; b2++) {
/* 2118 */       PrivateKeyEntry privateKeyEntry = arrayOfPrivateKeyEntry[b2];
/* 2119 */       if (privateKeyEntry.keyId != null) {
/* 2120 */         ArrayList<X509Certificate> arrayList = new ArrayList();
/*      */         
/* 2122 */         X509Certificate x509Certificate = findMatchedCertificate(privateKeyEntry);
/*      */ 
/*      */         
/* 2125 */         label108: while (x509Certificate != null) {
/*      */           
/* 2127 */           if (!arrayList.isEmpty())
/* 2128 */             for (X509Certificate x509Certificate1 : arrayList) {
/* 2129 */               if (x509Certificate.equals(x509Certificate1)) {
/* 2130 */                 if (debug != null) {
/* 2131 */                   debug.println("Loop detected in certificate chain. Skip adding repeated cert to chain. Subject: " + x509Certificate
/*      */ 
/*      */                       
/* 2134 */                       .getSubjectX500Principal()
/* 2135 */                       .toString());
/*      */                   break label108;
/*      */                 } 
/*      */                 break label108;
/*      */               } 
/*      */             }  
/* 2141 */           arrayList.add(x509Certificate);
/* 2142 */           X500Principal x500Principal = x509Certificate.getIssuerX500Principal();
/* 2143 */           if (x500Principal.equals(x509Certificate.getSubjectX500Principal())) {
/*      */             break;
/*      */           }
/* 2146 */           x509Certificate = this.certsMap.get(x500Principal);
/*      */         } 
/*      */         
/* 2149 */         if (arrayList.size() > 0) {
/* 2150 */           privateKeyEntry.chain = arrayList.<Certificate>toArray(new Certificate[arrayList.size()]);
/*      */         }
/*      */       } 
/*      */     } 
/* 2154 */     if (debug != null) {
/* 2155 */       if (this.privateKeyCount > 0) {
/* 2156 */         debug.println("Loaded " + this.privateKeyCount + " protected private key(s)");
/*      */       }
/*      */       
/* 2159 */       if (this.secretKeyCount > 0) {
/* 2160 */         debug.println("Loaded " + this.secretKeyCount + " protected secret key(s)");
/*      */       }
/*      */       
/* 2163 */       if (this.certificateCount > 0) {
/* 2164 */         debug.println("Loaded " + this.certificateCount + " certificate(s)");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2169 */     this.certEntries.clear();
/* 2170 */     this.certsMap.clear();
/* 2171 */     this.keyList.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private X509Certificate findMatchedCertificate(PrivateKeyEntry paramPrivateKeyEntry) {
/* 2180 */     CertEntry certEntry1 = null;
/* 2181 */     CertEntry certEntry2 = null;
/* 2182 */     for (CertEntry certEntry : this.certEntries) {
/* 2183 */       if (Arrays.equals(paramPrivateKeyEntry.keyId, certEntry.keyId)) {
/* 2184 */         certEntry1 = certEntry;
/* 2185 */         if (paramPrivateKeyEntry.alias.equalsIgnoreCase(certEntry.alias))
/*      */         {
/* 2187 */           return certEntry.cert; }  continue;
/*      */       } 
/* 2189 */       if (paramPrivateKeyEntry.alias.equalsIgnoreCase(certEntry.alias)) {
/* 2190 */         certEntry2 = certEntry;
/*      */       }
/*      */     } 
/*      */     
/* 2194 */     if (certEntry1 != null) return certEntry1.cert; 
/* 2195 */     if (certEntry2 != null) return certEntry2.cert; 
/* 2196 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadSafeContents(DerInputStream paramDerInputStream, char[] paramArrayOfchar) throws IOException, NoSuchAlgorithmException, CertificateException {
/* 2202 */     DerValue[] arrayOfDerValue = paramDerInputStream.getSequence(2);
/* 2203 */     int i = arrayOfDerValue.length;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2208 */     for (byte b = 0; b < i; b++) {
/*      */       SecretKeyEntry secretKeyEntry;
/*      */       
/*      */       Object object;
/* 2212 */       PrivateKeyEntry privateKeyEntry = null;
/*      */       
/* 2214 */       DerInputStream derInputStream = arrayOfDerValue[b].toDerInputStream();
/* 2215 */       ObjectIdentifier objectIdentifier = derInputStream.getOID();
/* 2216 */       DerValue derValue = derInputStream.getDerValue();
/* 2217 */       if (!derValue.isContextSpecific((byte)0)) {
/* 2218 */         throw new IOException("unsupported PKCS12 bag value type " + derValue.tag);
/*      */       }
/*      */       
/* 2221 */       derValue = derValue.data.getDerValue();
/* 2222 */       if (objectIdentifier.equals(PKCS8ShroudedKeyBag_OID)) {
/* 2223 */         object = new PrivateKeyEntry();
/* 2224 */         ((PrivateKeyEntry)object).protectedPrivKey = derValue.toByteArray();
/* 2225 */         privateKeyEntry = (PrivateKeyEntry)object;
/* 2226 */         this.privateKeyCount++;
/* 2227 */       } else if (objectIdentifier.equals(CertBag_OID)) {
/* 2228 */         object = new DerInputStream(derValue.toByteArray());
/* 2229 */         DerValue[] arrayOfDerValue1 = object.getSequence(2);
/* 2230 */         ObjectIdentifier objectIdentifier1 = arrayOfDerValue1[0].getOID();
/* 2231 */         if (!arrayOfDerValue1[1].isContextSpecific((byte)0)) {
/* 2232 */           throw new IOException("unsupported PKCS12 cert value type " + (arrayOfDerValue1[1]).tag);
/*      */         }
/*      */         
/* 2235 */         DerValue derValue1 = (arrayOfDerValue1[1]).data.getDerValue();
/* 2236 */         CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
/*      */ 
/*      */         
/* 2239 */         X509Certificate x509Certificate2 = (X509Certificate)certificateFactory.generateCertificate(new ByteArrayInputStream(derValue1.getOctetString()));
/* 2240 */         X509Certificate x509Certificate1 = x509Certificate2;
/* 2241 */         this.certificateCount++;
/* 2242 */       } else if (objectIdentifier.equals(SecretBag_OID)) {
/* 2243 */         object = new DerInputStream(derValue.toByteArray());
/* 2244 */         DerValue[] arrayOfDerValue1 = object.getSequence(2);
/* 2245 */         ObjectIdentifier objectIdentifier1 = arrayOfDerValue1[0].getOID();
/* 2246 */         if (!arrayOfDerValue1[1].isContextSpecific((byte)0)) {
/* 2247 */           throw new IOException("unsupported PKCS12 secret value type " + (arrayOfDerValue1[1]).tag);
/*      */         }
/*      */ 
/*      */         
/* 2251 */         DerValue derValue1 = (arrayOfDerValue1[1]).data.getDerValue();
/* 2252 */         SecretKeyEntry secretKeyEntry1 = new SecretKeyEntry();
/* 2253 */         secretKeyEntry1.protectedSecretKey = derValue1.getOctetString();
/* 2254 */         secretKeyEntry = secretKeyEntry1;
/* 2255 */         this.secretKeyCount++;
/*      */       
/*      */       }
/* 2258 */       else if (debug != null) {
/* 2259 */         debug.println("Unsupported PKCS12 bag type: " + objectIdentifier);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 2265 */         object = derInputStream.getSet(3);
/* 2266 */       } catch (IOException iOException) {
/*      */ 
/*      */ 
/*      */         
/* 2270 */         object = null;
/*      */       } 
/*      */       
/* 2273 */       String str = null;
/* 2274 */       byte[] arrayOfByte = null;
/* 2275 */       ObjectIdentifier[] arrayOfObjectIdentifier = null;
/* 2276 */       HashSet<PKCS12Attribute> hashSet = new HashSet();
/*      */       
/* 2278 */       if (object != null) {
/* 2279 */         for (byte b1 = 0; b1 < object.length; b1++) {
/* 2280 */           DerValue[] arrayOfDerValue2; byte[] arrayOfByte1 = object[b1].toByteArray();
/* 2281 */           DerInputStream derInputStream1 = new DerInputStream(arrayOfByte1);
/* 2282 */           DerValue[] arrayOfDerValue1 = derInputStream1.getSequence(2);
/* 2283 */           ObjectIdentifier objectIdentifier1 = arrayOfDerValue1[0].getOID();
/*      */           
/* 2285 */           DerInputStream derInputStream2 = new DerInputStream(arrayOfDerValue1[1].toByteArray());
/*      */           
/*      */           try {
/* 2288 */             arrayOfDerValue2 = derInputStream2.getSet(1);
/* 2289 */           } catch (IOException iOException) {
/* 2290 */             throw new IOException("Attribute " + objectIdentifier1 + " should have a value " + iOException
/* 2291 */                 .getMessage());
/*      */           } 
/* 2293 */           if (objectIdentifier1.equals(PKCS9FriendlyName_OID)) {
/* 2294 */             str = arrayOfDerValue2[0].getBMPString();
/* 2295 */           } else if (objectIdentifier1.equals(PKCS9LocalKeyId_OID)) {
/* 2296 */             arrayOfByte = arrayOfDerValue2[0].getOctetString();
/* 2297 */           } else if (objectIdentifier1
/* 2298 */             .equals(TrustedKeyUsage_OID)) {
/* 2299 */             arrayOfObjectIdentifier = new ObjectIdentifier[arrayOfDerValue2.length];
/* 2300 */             for (byte b2 = 0; b2 < arrayOfDerValue2.length; b2++) {
/* 2301 */               arrayOfObjectIdentifier[b2] = arrayOfDerValue2[b2].getOID();
/*      */             }
/*      */           } else {
/* 2304 */             hashSet.add(new PKCS12Attribute(arrayOfByte1));
/*      */           } 
/*      */         } 
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
/* 2318 */       if (secretKeyEntry instanceof KeyEntry) {
/* 2319 */         SecretKeyEntry secretKeyEntry1 = secretKeyEntry;
/*      */         
/* 2321 */         if (secretKeyEntry instanceof PrivateKeyEntry && 
/* 2322 */           arrayOfByte == null)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 2327 */           if (this.privateKeyCount == 1) {
/* 2328 */             arrayOfByte = "01".getBytes("UTF8");
/*      */           } else {
/*      */             continue;
/*      */           } 
/*      */         }
/*      */         
/* 2334 */         secretKeyEntry1.keyId = arrayOfByte;
/*      */         
/* 2336 */         String str1 = new String(arrayOfByte, "UTF8");
/* 2337 */         Date date = null;
/* 2338 */         if (str1.startsWith("Time ")) {
/*      */           
/*      */           try {
/* 2341 */             date = new Date(Long.parseLong(str1.substring(5)));
/* 2342 */           } catch (Exception exception) {
/* 2343 */             date = null;
/*      */           } 
/*      */         }
/* 2346 */         if (date == null) {
/* 2347 */           date = new Date();
/*      */         }
/* 2349 */         secretKeyEntry1.date = date;
/*      */         
/* 2351 */         if (secretKeyEntry instanceof PrivateKeyEntry) {
/* 2352 */           this.keyList.add(secretKeyEntry1);
/*      */         }
/* 2354 */         if (secretKeyEntry1.attributes == null) {
/* 2355 */           secretKeyEntry1.attributes = new HashSet<>();
/*      */         }
/* 2357 */         secretKeyEntry1.attributes.addAll((Collection)hashSet);
/* 2358 */         if (str == null) {
/* 2359 */           str = getUnfriendlyName();
/*      */         }
/* 2361 */         secretKeyEntry1.alias = str;
/* 2362 */         this.entries.put(str.toLowerCase(Locale.ENGLISH), secretKeyEntry1); continue;
/*      */       } 
/* 2364 */       if (secretKeyEntry instanceof X509Certificate) {
/* 2365 */         X509Certificate x509Certificate = (X509Certificate)secretKeyEntry;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2370 */         if (arrayOfByte == null && this.privateKeyCount == 1)
/*      */         {
/* 2372 */           if (b == 0) {
/* 2373 */             arrayOfByte = "01".getBytes("UTF8");
/*      */           }
/*      */         }
/*      */         
/* 2377 */         if (arrayOfObjectIdentifier != null) {
/* 2378 */           if (str == null) {
/* 2379 */             str = getUnfriendlyName();
/*      */           }
/* 2381 */           CertEntry certEntry = new CertEntry(x509Certificate, arrayOfByte, str, arrayOfObjectIdentifier, (Set)hashSet);
/*      */ 
/*      */           
/* 2384 */           this.entries.put(str.toLowerCase(Locale.ENGLISH), certEntry);
/*      */         } else {
/* 2386 */           this.certEntries.add(new CertEntry(x509Certificate, arrayOfByte, str));
/*      */         } 
/* 2388 */         X500Principal x500Principal = x509Certificate.getSubjectX500Principal();
/* 2389 */         if (x500Principal != null && 
/* 2390 */           !this.certsMap.containsKey(x500Principal)) {
/* 2391 */           this.certsMap.put(x500Principal, x509Certificate);
/*      */         }
/*      */       } 
/*      */       continue;
/*      */     } 
/*      */   }
/*      */   
/*      */   private String getUnfriendlyName() {
/* 2399 */     this.counter++;
/* 2400 */     return String.valueOf(this.counter);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs12/PKCS12KeyStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */