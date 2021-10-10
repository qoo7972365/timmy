/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.DigestInputStream;
/*     */ import java.security.DigestOutputStream;
/*     */ import java.security.Key;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.KeyStoreSpi;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.UnrecoverableEntryException;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import sun.misc.IOUtils;
/*     */ import sun.security.pkcs.EncryptedPrivateKeyInfo;
/*     */ import sun.security.pkcs12.PKCS12KeyStore;
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
/*     */ abstract class JavaKeyStore
/*     */   extends KeyStoreSpi
/*     */ {
/*     */   private static final int MAGIC = -17957139;
/*     */   private static final int VERSION_1 = 1;
/*     */   private static final int VERSION_2 = 2;
/*     */   private final Hashtable<String, Object> entries;
/*     */   
/*     */   public static final class JKS
/*     */     extends JavaKeyStore
/*     */   {
/*     */     String convertAlias(String param1String) {
/*  58 */       return param1String.toLowerCase(Locale.ENGLISH);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class CaseExactJKS
/*     */     extends JavaKeyStore {
/*     */     String convertAlias(String param1String) {
/*  65 */       return param1String;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class DualFormatJKS
/*     */     extends KeyStoreDelegator {
/*     */     public DualFormatJKS() {
/*  72 */       super("JKS", (Class)JavaKeyStore.JKS.class, "PKCS12", (Class)PKCS12KeyStore.class);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class KeyEntry
/*     */   {
/*     */     Date date;
/*     */     
/*     */     byte[] protectedPrivKey;
/*     */     
/*     */     Certificate[] chain;
/*     */ 
/*     */     
/*     */     private KeyEntry() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static class TrustedCertEntry
/*     */   {
/*     */     Date date;
/*     */     
/*     */     Certificate cert;
/*     */     
/*     */     private TrustedCertEntry() {}
/*     */   }
/*     */   
/*     */   JavaKeyStore() {
/* 100 */     this.entries = new Hashtable<>();
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
/*     */   abstract String convertAlias(String paramString);
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
/*     */   public Key engineGetKey(String paramString, char[] paramArrayOfchar) throws NoSuchAlgorithmException, UnrecoverableKeyException {
/* 126 */     Object object = this.entries.get(convertAlias(paramString));
/*     */     
/* 128 */     if (object == null || !(object instanceof KeyEntry)) {
/* 129 */       return null;
/*     */     }
/* 131 */     if (paramArrayOfchar == null) {
/* 132 */       throw new UnrecoverableKeyException("Password must not be null");
/*     */     }
/*     */     
/* 135 */     byte[] arrayOfByte1 = convertToBytes(paramArrayOfchar);
/* 136 */     KeyProtector keyProtector = new KeyProtector(arrayOfByte1);
/* 137 */     byte[] arrayOfByte2 = ((KeyEntry)object).protectedPrivKey;
/*     */     
/*     */     try {
/* 140 */       EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(arrayOfByte2);
/* 141 */       return keyProtector.recover(encryptedPrivateKeyInfo);
/* 142 */     } catch (IOException iOException) {
/* 143 */       throw new UnrecoverableKeyException("Private key not stored as PKCS #8 EncryptedPrivateKeyInfo");
/*     */     }
/*     */     finally {
/*     */       
/* 147 */       Arrays.fill(arrayOfByte1, (byte)0);
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
/*     */   public Certificate[] engineGetCertificateChain(String paramString) {
/* 163 */     Object object = this.entries.get(convertAlias(paramString));
/*     */     
/* 165 */     if (object != null && object instanceof KeyEntry) {
/* 166 */       if (((KeyEntry)object).chain == null) {
/* 167 */         return null;
/*     */       }
/* 169 */       return (Certificate[])((KeyEntry)object).chain.clone();
/*     */     } 
/*     */     
/* 172 */     return null;
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
/*     */   public Certificate engineGetCertificate(String paramString) {
/* 192 */     Object object = this.entries.get(convertAlias(paramString));
/*     */     
/* 194 */     if (object != null) {
/* 195 */       if (object instanceof TrustedCertEntry) {
/* 196 */         return ((TrustedCertEntry)object).cert;
/*     */       }
/* 198 */       if (((KeyEntry)object).chain == null) {
/* 199 */         return null;
/*     */       }
/* 201 */       return ((KeyEntry)object).chain[0];
/*     */     } 
/*     */ 
/*     */     
/* 205 */     return null;
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
/*     */   public Date engineGetCreationDate(String paramString) {
/* 218 */     Object object = this.entries.get(convertAlias(paramString));
/*     */     
/* 220 */     if (object != null) {
/* 221 */       if (object instanceof TrustedCertEntry) {
/* 222 */         return new Date(((TrustedCertEntry)object).date.getTime());
/*     */       }
/* 224 */       return new Date(((KeyEntry)object).date.getTime());
/*     */     } 
/*     */     
/* 227 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void engineSetKeyEntry(String paramString, Key paramKey, char[] paramArrayOfchar, Certificate[] paramArrayOfCertificate) throws KeyStoreException {
/* 258 */     byte[] arrayOfByte = null;
/*     */     
/* 260 */     if (!(paramKey instanceof java.security.PrivateKey)) {
/* 261 */       throw new KeyStoreException("Cannot store non-PrivateKeys");
/*     */     }
/*     */     try {
/* 264 */       synchronized (this.entries) {
/* 265 */         KeyEntry keyEntry = new KeyEntry();
/* 266 */         keyEntry.date = new Date();
/*     */ 
/*     */         
/* 269 */         arrayOfByte = convertToBytes(paramArrayOfchar);
/* 270 */         KeyProtector keyProtector = new KeyProtector(arrayOfByte);
/* 271 */         keyEntry.protectedPrivKey = keyProtector.protect(paramKey);
/*     */ 
/*     */         
/* 274 */         if (paramArrayOfCertificate != null && paramArrayOfCertificate.length != 0) {
/*     */           
/* 276 */           keyEntry.chain = (Certificate[])paramArrayOfCertificate.clone();
/*     */         } else {
/* 278 */           keyEntry.chain = null;
/*     */         } 
/*     */         
/* 281 */         this.entries.put(convertAlias(paramString), keyEntry);
/*     */       } 
/* 283 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 284 */       throw new KeyStoreException("Key protection algorithm not found");
/*     */     } finally {
/* 286 */       if (arrayOfByte != null) {
/* 287 */         Arrays.fill(arrayOfByte, (byte)0);
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void engineSetKeyEntry(String paramString, byte[] paramArrayOfbyte, Certificate[] paramArrayOfCertificate) throws KeyStoreException {
/* 318 */     synchronized (this.entries) {
/*     */ 
/*     */       
/*     */       try {
/* 322 */         new EncryptedPrivateKeyInfo(paramArrayOfbyte);
/* 323 */       } catch (IOException iOException) {
/* 324 */         throw new KeyStoreException("key is not encoded as EncryptedPrivateKeyInfo");
/*     */       } 
/*     */ 
/*     */       
/* 328 */       KeyEntry keyEntry = new KeyEntry();
/* 329 */       keyEntry.date = new Date();
/*     */       
/* 331 */       keyEntry.protectedPrivKey = (byte[])paramArrayOfbyte.clone();
/* 332 */       if (paramArrayOfCertificate != null && paramArrayOfCertificate.length != 0) {
/*     */         
/* 334 */         keyEntry.chain = (Certificate[])paramArrayOfCertificate.clone();
/*     */       } else {
/* 336 */         keyEntry.chain = null;
/*     */       } 
/*     */       
/* 339 */       this.entries.put(convertAlias(paramString), keyEntry);
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
/*     */   public void engineSetCertificateEntry(String paramString, Certificate paramCertificate) throws KeyStoreException {
/* 360 */     synchronized (this.entries) {
/*     */       
/* 362 */       Object object = this.entries.get(convertAlias(paramString));
/* 363 */       if (object != null && object instanceof KeyEntry) {
/* 364 */         throw new KeyStoreException("Cannot overwrite own certificate");
/*     */       }
/*     */ 
/*     */       
/* 368 */       TrustedCertEntry trustedCertEntry = new TrustedCertEntry();
/* 369 */       trustedCertEntry.cert = paramCertificate;
/* 370 */       trustedCertEntry.date = new Date();
/* 371 */       this.entries.put(convertAlias(paramString), trustedCertEntry);
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
/*     */   public void engineDeleteEntry(String paramString) throws KeyStoreException {
/* 385 */     synchronized (this.entries) {
/* 386 */       this.entries.remove(convertAlias(paramString));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> engineAliases() {
/* 396 */     return this.entries.keys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean engineContainsAlias(String paramString) {
/* 407 */     return this.entries.containsKey(convertAlias(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int engineSize() {
/* 416 */     return this.entries.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean engineIsKeyEntry(String paramString) {
/* 427 */     Object object = this.entries.get(convertAlias(paramString));
/* 428 */     if (object != null && object instanceof KeyEntry) {
/* 429 */       return true;
/*     */     }
/* 431 */     return false;
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
/*     */   public boolean engineIsCertificateEntry(String paramString) {
/* 443 */     Object object = this.entries.get(convertAlias(paramString));
/* 444 */     if (object != null && object instanceof TrustedCertEntry) {
/* 445 */       return true;
/*     */     }
/* 447 */     return false;
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
/*     */   
/*     */   public String engineGetCertificateAlias(Certificate paramCertificate) {
/* 470 */     for (Enumeration<String> enumeration = this.entries.keys(); enumeration.hasMoreElements(); ) {
/* 471 */       Certificate certificate; String str = enumeration.nextElement();
/* 472 */       Object object = this.entries.get(str);
/* 473 */       if (object instanceof TrustedCertEntry) {
/* 474 */         certificate = ((TrustedCertEntry)object).cert;
/* 475 */       } else if (((KeyEntry)object).chain != null) {
/* 476 */         certificate = ((KeyEntry)object).chain[0];
/*     */       } else {
/*     */         continue;
/*     */       } 
/* 480 */       if (certificate.equals(paramCertificate)) {
/* 481 */         return str;
/*     */       }
/*     */     } 
/* 484 */     return null;
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
/*     */   public void engineStore(OutputStream paramOutputStream, char[] paramArrayOfchar) throws IOException, NoSuchAlgorithmException, CertificateException {
/* 503 */     synchronized (this.entries) {
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
/* 537 */       if (paramArrayOfchar == null) {
/* 538 */         throw new IllegalArgumentException("password can't be null");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 543 */       MessageDigest messageDigest = getPreKeyedHash(paramArrayOfchar);
/* 544 */       DataOutputStream dataOutputStream = new DataOutputStream(new DigestOutputStream(paramOutputStream, messageDigest));
/*     */ 
/*     */       
/* 547 */       dataOutputStream.writeInt(-17957139);
/*     */       
/* 549 */       dataOutputStream.writeInt(2);
/*     */       
/* 551 */       dataOutputStream.writeInt(this.entries.size());
/*     */       
/* 553 */       for (Enumeration<String> enumeration = this.entries.keys(); enumeration.hasMoreElements(); ) {
/*     */         
/* 555 */         String str = enumeration.nextElement();
/* 556 */         Object object = this.entries.get(str);
/*     */         
/* 558 */         if (object instanceof KeyEntry) {
/*     */           int i;
/*     */           
/* 561 */           dataOutputStream.writeInt(1);
/*     */ 
/*     */           
/* 564 */           dataOutputStream.writeUTF(str);
/*     */ 
/*     */           
/* 567 */           dataOutputStream.writeLong(((KeyEntry)object).date.getTime());
/*     */ 
/*     */           
/* 570 */           dataOutputStream.writeInt(((KeyEntry)object).protectedPrivKey.length);
/* 571 */           dataOutputStream.write(((KeyEntry)object).protectedPrivKey);
/*     */ 
/*     */ 
/*     */           
/* 575 */           if (((KeyEntry)object).chain == null) {
/* 576 */             i = 0;
/*     */           } else {
/* 578 */             i = ((KeyEntry)object).chain.length;
/*     */           } 
/* 580 */           dataOutputStream.writeInt(i);
/* 581 */           for (byte b = 0; b < i; b++) {
/* 582 */             byte[] arrayOfByte2 = ((KeyEntry)object).chain[b].getEncoded();
/* 583 */             dataOutputStream.writeUTF(((KeyEntry)object).chain[b].getType());
/* 584 */             dataOutputStream.writeInt(arrayOfByte2.length);
/* 585 */             dataOutputStream.write(arrayOfByte2);
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 590 */         dataOutputStream.writeInt(2);
/*     */ 
/*     */         
/* 593 */         dataOutputStream.writeUTF(str);
/*     */ 
/*     */         
/* 596 */         dataOutputStream.writeLong(((TrustedCertEntry)object).date.getTime());
/*     */ 
/*     */         
/* 599 */         byte[] arrayOfByte1 = ((TrustedCertEntry)object).cert.getEncoded();
/* 600 */         dataOutputStream.writeUTF(((TrustedCertEntry)object).cert.getType());
/* 601 */         dataOutputStream.writeInt(arrayOfByte1.length);
/* 602 */         dataOutputStream.write(arrayOfByte1);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 611 */       byte[] arrayOfByte = messageDigest.digest();
/*     */       
/* 613 */       dataOutputStream.write(arrayOfByte);
/* 614 */       dataOutputStream.flush();
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
/*     */   
/*     */   public void engineLoad(InputStream paramInputStream, char[] paramArrayOfchar) throws IOException, NoSuchAlgorithmException, CertificateException {
/* 638 */     synchronized (this.entries) {
/*     */       DataInputStream dataInputStream;
/* 640 */       MessageDigest messageDigest = null;
/* 641 */       CertificateFactory certificateFactory = null;
/* 642 */       Hashtable<Object, Object> hashtable = null;
/* 643 */       ByteArrayInputStream byteArrayInputStream = null;
/* 644 */       byte[] arrayOfByte = null;
/*     */       
/* 646 */       if (paramInputStream == null) {
/*     */         return;
/*     */       }
/* 649 */       if (paramArrayOfchar != null) {
/* 650 */         messageDigest = getPreKeyedHash(paramArrayOfchar);
/* 651 */         dataInputStream = new DataInputStream(new DigestInputStream(paramInputStream, messageDigest));
/*     */       } else {
/* 653 */         dataInputStream = new DataInputStream(paramInputStream);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 658 */       int i = dataInputStream.readInt();
/* 659 */       int j = dataInputStream.readInt();
/*     */       
/* 661 */       if (i != -17957139 || (j != 1 && j != 2))
/*     */       {
/* 663 */         throw new IOException("Invalid keystore format");
/*     */       }
/*     */       
/* 666 */       if (j == 1) {
/* 667 */         certificateFactory = CertificateFactory.getInstance("X509");
/*     */       } else {
/*     */         
/* 670 */         hashtable = new Hashtable<>(3);
/*     */       } 
/*     */       
/* 673 */       this.entries.clear();
/* 674 */       int k = dataInputStream.readInt();
/*     */       
/* 676 */       for (byte b = 0; b < k; b++) {
/*     */ 
/*     */ 
/*     */         
/* 680 */         int m = dataInputStream.readInt();
/*     */         
/* 682 */         if (m == 1) {
/*     */           
/* 684 */           KeyEntry keyEntry = new KeyEntry();
/*     */ 
/*     */           
/* 687 */           String str = dataInputStream.readUTF();
/*     */ 
/*     */           
/* 690 */           keyEntry.date = new Date(dataInputStream.readLong());
/*     */ 
/*     */           
/* 693 */           keyEntry
/* 694 */             .protectedPrivKey = IOUtils.readExactlyNBytes(dataInputStream, dataInputStream.readInt());
/*     */ 
/*     */           
/* 697 */           int n = dataInputStream.readInt();
/* 698 */           if (n > 0) {
/* 699 */             ArrayList<Certificate> arrayList = new ArrayList((n > 10) ? 10 : n);
/*     */             
/* 701 */             for (byte b1 = 0; b1 < n; b1++) {
/* 702 */               if (j == 2) {
/*     */ 
/*     */ 
/*     */                 
/* 706 */                 String str1 = dataInputStream.readUTF();
/* 707 */                 if (hashtable.containsKey(str1)) {
/*     */                   
/* 709 */                   certificateFactory = (CertificateFactory)hashtable.get(str1);
/*     */                 } else {
/*     */                   
/* 712 */                   certificateFactory = CertificateFactory.getInstance(str1);
/*     */ 
/*     */                   
/* 715 */                   hashtable.put(str1, certificateFactory);
/*     */                 } 
/*     */               } 
/*     */               
/* 719 */               arrayOfByte = IOUtils.readExactlyNBytes(dataInputStream, dataInputStream.readInt());
/* 720 */               byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/* 721 */               arrayList.add(certificateFactory.generateCertificate(byteArrayInputStream));
/* 722 */               byteArrayInputStream.close();
/*     */             } 
/*     */             
/* 725 */             keyEntry.chain = arrayList.<Certificate>toArray(new Certificate[n]);
/*     */           } 
/*     */ 
/*     */           
/* 729 */           this.entries.put(str, keyEntry);
/*     */         }
/* 731 */         else if (m == 2) {
/*     */           
/* 733 */           TrustedCertEntry trustedCertEntry = new TrustedCertEntry();
/*     */ 
/*     */           
/* 736 */           String str = dataInputStream.readUTF();
/*     */ 
/*     */           
/* 739 */           trustedCertEntry.date = new Date(dataInputStream.readLong());
/*     */ 
/*     */           
/* 742 */           if (j == 2) {
/*     */ 
/*     */ 
/*     */             
/* 746 */             String str1 = dataInputStream.readUTF();
/* 747 */             if (hashtable.containsKey(str1)) {
/*     */               
/* 749 */               certificateFactory = (CertificateFactory)hashtable.get(str1);
/*     */             } else {
/*     */               
/* 752 */               certificateFactory = CertificateFactory.getInstance(str1);
/*     */ 
/*     */               
/* 755 */               hashtable.put(str1, certificateFactory);
/*     */             } 
/*     */           } 
/* 758 */           arrayOfByte = IOUtils.readExactlyNBytes(dataInputStream, dataInputStream.readInt());
/* 759 */           byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/* 760 */           trustedCertEntry.cert = certificateFactory.generateCertificate(byteArrayInputStream);
/* 761 */           byteArrayInputStream.close();
/*     */ 
/*     */           
/* 764 */           this.entries.put(str, trustedCertEntry);
/*     */         } else {
/*     */           
/* 767 */           throw new IOException("Unrecognized keystore entry");
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 776 */       if (paramArrayOfchar != null) {
/*     */         
/* 778 */         byte[] arrayOfByte1 = messageDigest.digest();
/* 779 */         byte[] arrayOfByte2 = IOUtils.readExactlyNBytes(dataInputStream, arrayOfByte1.length);
/* 780 */         if (!MessageDigest.isEqual(arrayOfByte1, arrayOfByte2)) {
/* 781 */           UnrecoverableKeyException unrecoverableKeyException = new UnrecoverableKeyException("Password verification failed");
/*     */           
/* 783 */           throw (IOException)(new IOException("Keystore was tampered with, or password was incorrect"))
/*     */             
/* 785 */             .initCause(unrecoverableKeyException);
/*     */         } 
/*     */       } 
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
/*     */   private MessageDigest getPreKeyedHash(char[] paramArrayOfchar) throws NoSuchAlgorithmException, UnsupportedEncodingException {
/* 799 */     MessageDigest messageDigest = MessageDigest.getInstance("SHA");
/* 800 */     byte[] arrayOfByte = convertToBytes(paramArrayOfchar);
/* 801 */     messageDigest.update(arrayOfByte);
/* 802 */     Arrays.fill(arrayOfByte, (byte)0);
/* 803 */     messageDigest.update("Mighty Aphrodite".getBytes("UTF8"));
/* 804 */     return messageDigest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] convertToBytes(char[] paramArrayOfchar) {
/* 813 */     byte[] arrayOfByte = new byte[paramArrayOfchar.length * 2];
/* 814 */     for (byte b1 = 0, b2 = 0; b1 < paramArrayOfchar.length; b1++) {
/* 815 */       arrayOfByte[b2++] = (byte)(paramArrayOfchar[b1] >> 8);
/* 816 */       arrayOfByte[b2++] = (byte)paramArrayOfchar[b1];
/*     */     } 
/* 818 */     return arrayOfByte;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/JavaKeyStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */