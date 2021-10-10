/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ import java.security.cert.CRL;
/*     */ import java.security.cert.CRLException;
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactorySpi;
/*     */ import java.security.cert.X509CRL;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import sun.security.pkcs.PKCS7;
/*     */ import sun.security.pkcs.ParsingException;
/*     */ import sun.security.provider.certpath.X509CertPath;
/*     */ import sun.security.provider.certpath.X509CertificatePair;
/*     */ import sun.security.util.Cache;
/*     */ import sun.security.util.Pem;
/*     */ import sun.security.x509.X509CRLImpl;
/*     */ import sun.security.x509.X509CertImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X509Factory
/*     */   extends CertificateFactorySpi
/*     */ {
/*     */   public static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
/*     */   public static final String END_CERT = "-----END CERTIFICATE-----";
/*     */   private static final int ENC_MAX_LENGTH = 4194304;
/*  70 */   private static final Cache<Object, X509CertImpl> certCache = Cache.newSoftMemoryCache(750);
/*     */   
/*  72 */   private static final Cache<Object, X509CRLImpl> crlCache = Cache.newSoftMemoryCache(750);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Certificate engineGenerateCertificate(InputStream paramInputStream) throws CertificateException {
/*  89 */     if (paramInputStream == null) {
/*     */       
/*  91 */       certCache.clear();
/*  92 */       X509CertificatePair.clearCache();
/*  93 */       throw new CertificateException("Missing input stream");
/*     */     } 
/*     */     try {
/*  96 */       byte[] arrayOfByte = readOneBlock(paramInputStream);
/*  97 */       if (arrayOfByte != null) {
/*  98 */         X509CertImpl x509CertImpl = getFromCache(certCache, arrayOfByte);
/*  99 */         if (x509CertImpl != null) {
/* 100 */           return x509CertImpl;
/*     */         }
/* 102 */         x509CertImpl = new X509CertImpl(arrayOfByte);
/* 103 */         addToCache(certCache, x509CertImpl.getEncodedInternal(), x509CertImpl);
/* 104 */         return x509CertImpl;
/*     */       } 
/* 106 */       throw new IOException("Empty input");
/*     */     }
/* 108 */     catch (IOException iOException) {
/* 109 */       throw new CertificateException("Could not parse certificate: " + iOException
/* 110 */           .toString(), iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int readFully(InputStream paramInputStream, ByteArrayOutputStream paramByteArrayOutputStream, int paramInt) throws IOException {
/* 120 */     int i = 0;
/* 121 */     byte[] arrayOfByte = new byte[2048];
/* 122 */     while (paramInt > 0) {
/* 123 */       int j = paramInputStream.read(arrayOfByte, 0, (paramInt < 2048) ? paramInt : 2048);
/* 124 */       if (j <= 0) {
/*     */         break;
/*     */       }
/* 127 */       paramByteArrayOutputStream.write(arrayOfByte, 0, j);
/* 128 */       i += j;
/* 129 */       paramInt -= j;
/*     */     } 
/* 131 */     return i;
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
/*     */   public static synchronized X509CertImpl intern(X509Certificate paramX509Certificate) throws CertificateException {
/*     */     byte[] arrayOfByte;
/* 155 */     if (paramX509Certificate == null) {
/* 156 */       return null;
/*     */     }
/* 158 */     boolean bool = paramX509Certificate instanceof X509CertImpl;
/*     */     
/* 160 */     if (bool) {
/* 161 */       arrayOfByte = ((X509CertImpl)paramX509Certificate).getEncodedInternal();
/*     */     } else {
/* 163 */       arrayOfByte = paramX509Certificate.getEncoded();
/*     */     } 
/* 165 */     X509CertImpl x509CertImpl = getFromCache(certCache, arrayOfByte);
/* 166 */     if (x509CertImpl != null) {
/* 167 */       return x509CertImpl;
/*     */     }
/* 169 */     if (bool) {
/* 170 */       x509CertImpl = (X509CertImpl)paramX509Certificate;
/*     */     } else {
/* 172 */       x509CertImpl = new X509CertImpl(arrayOfByte);
/* 173 */       arrayOfByte = x509CertImpl.getEncodedInternal();
/*     */     } 
/* 175 */     addToCache(certCache, arrayOfByte, x509CertImpl);
/* 176 */     return x509CertImpl;
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
/*     */   public static synchronized X509CRLImpl intern(X509CRL paramX509CRL) throws CRLException {
/*     */     byte[] arrayOfByte;
/* 191 */     if (paramX509CRL == null) {
/* 192 */       return null;
/*     */     }
/* 194 */     boolean bool = paramX509CRL instanceof X509CRLImpl;
/*     */     
/* 196 */     if (bool) {
/* 197 */       arrayOfByte = ((X509CRLImpl)paramX509CRL).getEncodedInternal();
/*     */     } else {
/* 199 */       arrayOfByte = paramX509CRL.getEncoded();
/*     */     } 
/* 201 */     X509CRLImpl x509CRLImpl = getFromCache(crlCache, arrayOfByte);
/* 202 */     if (x509CRLImpl != null) {
/* 203 */       return x509CRLImpl;
/*     */     }
/* 205 */     if (bool) {
/* 206 */       x509CRLImpl = (X509CRLImpl)paramX509CRL;
/*     */     } else {
/* 208 */       x509CRLImpl = new X509CRLImpl(arrayOfByte);
/* 209 */       arrayOfByte = x509CRLImpl.getEncodedInternal();
/*     */     } 
/* 211 */     addToCache(crlCache, arrayOfByte, x509CRLImpl);
/* 212 */     return x509CRLImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized <K, V> V getFromCache(Cache<K, V> paramCache, byte[] paramArrayOfbyte) {
/* 220 */     Cache.EqualByteArray equalByteArray = new Cache.EqualByteArray(paramArrayOfbyte);
/* 221 */     return paramCache.get(equalByteArray);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized <V> void addToCache(Cache<Object, V> paramCache, byte[] paramArrayOfbyte, V paramV) {
/* 229 */     if (paramArrayOfbyte.length > 4194304) {
/*     */       return;
/*     */     }
/* 232 */     Cache.EqualByteArray equalByteArray = new Cache.EqualByteArray(paramArrayOfbyte);
/* 233 */     paramCache.put(equalByteArray, paramV);
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
/*     */   public CertPath engineGenerateCertPath(InputStream paramInputStream) throws CertificateException {
/* 251 */     if (paramInputStream == null) {
/* 252 */       throw new CertificateException("Missing input stream");
/*     */     }
/*     */     try {
/* 255 */       byte[] arrayOfByte = readOneBlock(paramInputStream);
/* 256 */       if (arrayOfByte != null) {
/* 257 */         return new X509CertPath(new ByteArrayInputStream(arrayOfByte));
/*     */       }
/* 259 */       throw new IOException("Empty input");
/*     */     }
/* 261 */     catch (IOException iOException) {
/* 262 */       throw new CertificateException(iOException.getMessage());
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
/*     */   public CertPath engineGenerateCertPath(InputStream paramInputStream, String paramString) throws CertificateException {
/* 283 */     if (paramInputStream == null) {
/* 284 */       throw new CertificateException("Missing input stream");
/*     */     }
/*     */     try {
/* 287 */       byte[] arrayOfByte = readOneBlock(paramInputStream);
/* 288 */       if (arrayOfByte != null) {
/* 289 */         return new X509CertPath(new ByteArrayInputStream(arrayOfByte), paramString);
/*     */       }
/* 291 */       throw new IOException("Empty input");
/*     */     }
/* 293 */     catch (IOException iOException) {
/* 294 */       throw new CertificateException(iOException.getMessage());
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
/*     */   public CertPath engineGenerateCertPath(List<? extends Certificate> paramList) throws CertificateException {
/* 317 */     return new X509CertPath(paramList);
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
/*     */   public Iterator<String> engineGetCertPathEncodings() {
/* 334 */     return X509CertPath.getEncodingsStatic();
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
/*     */   public Collection<? extends Certificate> engineGenerateCertificates(InputStream paramInputStream) throws CertificateException {
/* 352 */     if (paramInputStream == null) {
/* 353 */       throw new CertificateException("Missing input stream");
/*     */     }
/*     */     try {
/* 356 */       return parseX509orPKCS7Cert(paramInputStream);
/* 357 */     } catch (IOException iOException) {
/* 358 */       throw new CertificateException(iOException);
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
/*     */   public CRL engineGenerateCRL(InputStream paramInputStream) throws CRLException {
/* 378 */     if (paramInputStream == null) {
/*     */       
/* 380 */       crlCache.clear();
/* 381 */       throw new CRLException("Missing input stream");
/*     */     } 
/*     */     try {
/* 384 */       byte[] arrayOfByte = readOneBlock(paramInputStream);
/* 385 */       if (arrayOfByte != null) {
/* 386 */         X509CRLImpl x509CRLImpl = getFromCache(crlCache, arrayOfByte);
/* 387 */         if (x509CRLImpl != null) {
/* 388 */           return x509CRLImpl;
/*     */         }
/* 390 */         x509CRLImpl = new X509CRLImpl(arrayOfByte);
/* 391 */         addToCache(crlCache, x509CRLImpl.getEncodedInternal(), x509CRLImpl);
/* 392 */         return x509CRLImpl;
/*     */       } 
/* 394 */       throw new IOException("Empty input");
/*     */     }
/* 396 */     catch (IOException iOException) {
/* 397 */       throw new CRLException(iOException.getMessage());
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
/*     */   public Collection<? extends CRL> engineGenerateCRLs(InputStream paramInputStream) throws CRLException {
/* 416 */     if (paramInputStream == null) {
/* 417 */       throw new CRLException("Missing input stream");
/*     */     }
/*     */     try {
/* 420 */       return parseX509orPKCS7CRL(paramInputStream);
/* 421 */     } catch (IOException iOException) {
/* 422 */       throw new CRLException(iOException.getMessage());
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
/*     */   private Collection<? extends Certificate> parseX509orPKCS7Cert(InputStream paramInputStream) throws CertificateException, IOException {
/* 437 */     PushbackInputStream pushbackInputStream = new PushbackInputStream(paramInputStream);
/* 438 */     ArrayList<X509CertImpl> arrayList = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 444 */     int i = pushbackInputStream.read();
/* 445 */     if (i == -1) {
/* 446 */       return new ArrayList<>(0);
/*     */     }
/* 448 */     pushbackInputStream.unread(i);
/* 449 */     byte[] arrayOfByte = readOneBlock(pushbackInputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 455 */     if (arrayOfByte == null) {
/* 456 */       throw new CertificateException("No certificate data found");
/*     */     }
/*     */     
/*     */     try {
/* 460 */       PKCS7 pKCS7 = new PKCS7(arrayOfByte);
/* 461 */       X509Certificate[] arrayOfX509Certificate = pKCS7.getCertificates();
/*     */       
/* 463 */       if (arrayOfX509Certificate != null) {
/* 464 */         return Arrays.asList((Certificate[])arrayOfX509Certificate);
/*     */       }
/*     */       
/* 467 */       return new ArrayList<>(0);
/*     */     }
/* 469 */     catch (ParsingException parsingException) {
/* 470 */       while (arrayOfByte != null) {
/* 471 */         arrayList.add(new X509CertImpl(arrayOfByte));
/* 472 */         arrayOfByte = readOneBlock(pushbackInputStream);
/*     */       } 
/*     */       
/* 475 */       return (Collection)arrayList;
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
/*     */   private Collection<? extends CRL> parseX509orPKCS7CRL(InputStream paramInputStream) throws CRLException, IOException {
/* 489 */     PushbackInputStream pushbackInputStream = new PushbackInputStream(paramInputStream);
/* 490 */     ArrayList<X509CRLImpl> arrayList = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 496 */     int i = pushbackInputStream.read();
/* 497 */     if (i == -1) {
/* 498 */       return new ArrayList<>(0);
/*     */     }
/* 500 */     pushbackInputStream.unread(i);
/* 501 */     byte[] arrayOfByte = readOneBlock(pushbackInputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 507 */     if (arrayOfByte == null) {
/* 508 */       throw new CRLException("No CRL data found");
/*     */     }
/*     */     
/*     */     try {
/* 512 */       PKCS7 pKCS7 = new PKCS7(arrayOfByte);
/* 513 */       X509CRL[] arrayOfX509CRL = pKCS7.getCRLs();
/*     */       
/* 515 */       if (arrayOfX509CRL != null) {
/* 516 */         return Arrays.asList((CRL[])arrayOfX509CRL);
/*     */       }
/*     */       
/* 519 */       return new ArrayList<>(0);
/*     */     }
/* 521 */     catch (ParsingException parsingException) {
/* 522 */       while (arrayOfByte != null) {
/* 523 */         arrayList.add(new X509CRLImpl(arrayOfByte));
/* 524 */         arrayOfByte = readOneBlock(pushbackInputStream);
/*     */       } 
/*     */       
/* 527 */       return (Collection)arrayList;
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
/*     */   private static byte[] readOneBlock(InputStream paramInputStream) throws IOException {
/*     */     byte b3;
/* 545 */     int i = paramInputStream.read();
/* 546 */     if (i == -1) {
/* 547 */       return null;
/*     */     }
/* 549 */     if (i == 48) {
/* 550 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
/* 551 */       byteArrayOutputStream.write(i);
/* 552 */       readBERInternal(paramInputStream, byteArrayOutputStream, i);
/* 553 */       return byteArrayOutputStream.toByteArray();
/*     */     } 
/*     */     
/* 556 */     char[] arrayOfChar = new char[2048];
/* 557 */     byte b1 = 0;
/*     */ 
/*     */     
/* 560 */     byte b2 = (i == 45) ? 1 : 0;
/* 561 */     int j = (i == 45) ? -1 : i;
/*     */     do {
/* 563 */       b3 = paramInputStream.read();
/* 564 */       if (b3 == -1)
/*     */       {
/*     */         
/* 567 */         return null;
/*     */       }
/* 569 */       if (b3 == 45) {
/* 570 */         b2++;
/*     */       } else {
/* 572 */         b2 = 0;
/* 573 */         j = b3;
/*     */       } 
/* 575 */     } while (b2 != 5 || (j != -1 && j != 13 && j != 10));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 582 */     StringBuilder stringBuilder1 = new StringBuilder("-----");
/*     */     while (true) {
/* 584 */       int k = paramInputStream.read();
/* 585 */       if (k == -1) {
/* 586 */         throw new IOException("Incomplete data");
/*     */       }
/* 588 */       if (k == 10) {
/* 589 */         b3 = 10;
/*     */         break;
/*     */       } 
/* 592 */       if (k == 13) {
/* 593 */         k = paramInputStream.read();
/* 594 */         if (k == -1) {
/* 595 */           throw new IOException("Incomplete data");
/*     */         }
/* 597 */         if (k == 10) {
/* 598 */           byte b = 10; break;
/*     */         } 
/* 600 */         b3 = 13;
/* 601 */         arrayOfChar[b1++] = (char)k;
/*     */         
/*     */         break;
/*     */       } 
/* 605 */       stringBuilder1.append((char)k);
/*     */     } 
/*     */ 
/*     */     
/*     */     while (true) {
/* 610 */       int k = paramInputStream.read();
/* 611 */       if (k == -1) {
/* 612 */         throw new IOException("Incomplete data");
/*     */       }
/* 614 */       if (k != 45) {
/* 615 */         arrayOfChar[b1++] = (char)k;
/* 616 */         if (b1 >= arrayOfChar.length) {
/* 617 */           arrayOfChar = Arrays.copyOf(arrayOfChar, arrayOfChar.length + 1024);
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*     */       break;
/*     */     } 
/* 625 */     StringBuilder stringBuilder2 = new StringBuilder("-");
/*     */     while (true) {
/* 627 */       int k = paramInputStream.read();
/*     */ 
/*     */       
/* 630 */       if (k == -1 || k == b3 || k == 10) {
/*     */         break;
/*     */       }
/* 633 */       if (k != 13) stringBuilder2.append((char)k);
/*     */     
/*     */     } 
/* 636 */     checkHeaderFooter(stringBuilder1.toString(), stringBuilder2.toString());
/*     */     
/* 638 */     return Pem.decode(new String(arrayOfChar, 0, b1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkHeaderFooter(String paramString1, String paramString2) throws IOException {
/* 644 */     if (paramString1.length() < 16 || !paramString1.startsWith("-----BEGIN ") || 
/* 645 */       !paramString1.endsWith("-----")) {
/* 646 */       throw new IOException("Illegal header: " + paramString1);
/*     */     }
/* 648 */     if (paramString2.length() < 14 || !paramString2.startsWith("-----END ") || 
/* 649 */       !paramString2.endsWith("-----")) {
/* 650 */       throw new IOException("Illegal footer: " + paramString2);
/*     */     }
/* 652 */     String str1 = paramString1.substring(11, paramString1.length() - 5);
/* 653 */     String str2 = paramString2.substring(9, paramString2.length() - 5);
/* 654 */     if (!str1.equals(str2)) {
/* 655 */       throw new IOException("Header and footer do not match: " + paramString1 + " " + paramString2);
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
/*     */   private static int readBERInternal(InputStream paramInputStream, ByteArrayOutputStream paramByteArrayOutputStream, int paramInt) throws IOException {
/* 673 */     if (paramInt == -1) {
/* 674 */       paramInt = paramInputStream.read();
/* 675 */       if (paramInt == -1) {
/* 676 */         throw new IOException("BER/DER tag info absent");
/*     */       }
/* 678 */       if ((paramInt & 0x1F) == 31) {
/* 679 */         throw new IOException("Multi octets tag not supported");
/*     */       }
/* 681 */       paramByteArrayOutputStream.write(paramInt);
/*     */     } 
/*     */     
/* 684 */     int i = paramInputStream.read();
/* 685 */     if (i == -1) {
/* 686 */       throw new IOException("BER/DER length info absent");
/*     */     }
/* 688 */     paramByteArrayOutputStream.write(i);
/*     */ 
/*     */ 
/*     */     
/* 692 */     if (i == 128) {
/* 693 */       int j; if ((paramInt & 0x20) != 32) {
/* 694 */         throw new IOException("Non constructed encoding must have definite length");
/*     */       }
/*     */       
/*     */       do {
/* 698 */         j = readBERInternal(paramInputStream, paramByteArrayOutputStream, -1);
/* 699 */       } while (j != 0);
/*     */     } else {
/*     */       int j;
/*     */ 
/*     */       
/* 704 */       if (i < 128) {
/* 705 */         j = i;
/* 706 */       } else if (i == 129) {
/* 707 */         j = paramInputStream.read();
/* 708 */         if (j == -1) {
/* 709 */           throw new IOException("Incomplete BER/DER length info");
/*     */         }
/* 711 */         paramByteArrayOutputStream.write(j);
/* 712 */       } else if (i == 130) {
/* 713 */         int k = paramInputStream.read();
/* 714 */         int m = paramInputStream.read();
/* 715 */         if (m == -1) {
/* 716 */           throw new IOException("Incomplete BER/DER length info");
/*     */         }
/* 718 */         paramByteArrayOutputStream.write(k);
/* 719 */         paramByteArrayOutputStream.write(m);
/* 720 */         j = k << 8 | m;
/* 721 */       } else if (i == 131) {
/* 722 */         int k = paramInputStream.read();
/* 723 */         int m = paramInputStream.read();
/* 724 */         int n = paramInputStream.read();
/* 725 */         if (n == -1) {
/* 726 */           throw new IOException("Incomplete BER/DER length info");
/*     */         }
/* 728 */         paramByteArrayOutputStream.write(k);
/* 729 */         paramByteArrayOutputStream.write(m);
/* 730 */         paramByteArrayOutputStream.write(n);
/* 731 */         j = k << 16 | m << 8 | n;
/* 732 */       } else if (i == 132) {
/* 733 */         int k = paramInputStream.read();
/* 734 */         int m = paramInputStream.read();
/* 735 */         int n = paramInputStream.read();
/* 736 */         int i1 = paramInputStream.read();
/* 737 */         if (i1 == -1) {
/* 738 */           throw new IOException("Incomplete BER/DER length info");
/*     */         }
/* 740 */         if (k > 127) {
/* 741 */           throw new IOException("Invalid BER/DER data (a little huge?)");
/*     */         }
/* 743 */         paramByteArrayOutputStream.write(k);
/* 744 */         paramByteArrayOutputStream.write(m);
/* 745 */         paramByteArrayOutputStream.write(n);
/* 746 */         paramByteArrayOutputStream.write(i1);
/* 747 */         j = k << 24 | m << 16 | n << 8 | i1;
/*     */       } else {
/*     */         
/* 750 */         throw new IOException("Invalid BER/DER data (too huge?)");
/*     */       } 
/* 752 */       if (readFully(paramInputStream, paramByteArrayOutputStream, j) != j) {
/* 753 */         throw new IOException("Incomplete BER/DER data");
/*     */       }
/*     */     } 
/* 756 */     return paramInt;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/X509Factory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */