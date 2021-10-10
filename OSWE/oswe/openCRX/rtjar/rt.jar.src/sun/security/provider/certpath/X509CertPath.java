/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import sun.security.pkcs.ContentInfo;
/*     */ import sun.security.pkcs.PKCS7;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X509CertPath
/*     */   extends CertPath
/*     */ {
/*     */   private static final long serialVersionUID = 4989800333263052980L;
/*     */   private List<X509Certificate> certs;
/*     */   private static final String COUNT_ENCODING = "count";
/*     */   private static final String PKCS7_ENCODING = "PKCS7";
/*     */   private static final String PKIPATH_ENCODING = "PkiPath";
/*     */   private static final Collection<String> encodingList;
/*     */   
/*     */   static {
/*  86 */     ArrayList<String> arrayList = new ArrayList(2);
/*  87 */     arrayList.add("PkiPath");
/*  88 */     arrayList.add("PKCS7");
/*  89 */     encodingList = Collections.unmodifiableCollection(arrayList);
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
/*     */   public X509CertPath(List<? extends Certificate> paramList) throws CertificateException {
/* 105 */     super("X.509");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     for (Certificate certificate : paramList) {
/* 115 */       if (!(certificate instanceof X509Certificate)) {
/* 116 */         throw new CertificateException("List is not all X509Certificates: " + certificate
/*     */             
/* 118 */             .getClass().getName());
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     this.certs = Collections.unmodifiableList((List)new ArrayList<>(paramList));
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
/*     */   public X509CertPath(InputStream paramInputStream) throws CertificateException {
/* 139 */     this(paramInputStream, "PkiPath");
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
/*     */   public X509CertPath(InputStream paramInputStream, String paramString) throws CertificateException {
/* 154 */     super("X.509");
/*     */     
/* 156 */     switch (paramString) {
/*     */       case "PkiPath":
/* 158 */         this.certs = parsePKIPATH(paramInputStream);
/*     */         return;
/*     */       case "PKCS7":
/* 161 */         this.certs = parsePKCS7(paramInputStream);
/*     */         return;
/*     */     } 
/* 164 */     throw new CertificateException("unsupported encoding");
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
/*     */   private static List<X509Certificate> parsePKIPATH(InputStream paramInputStream) throws CertificateException {
/* 178 */     ArrayList<X509Certificate> arrayList = null;
/* 179 */     CertificateFactory certificateFactory = null;
/*     */     
/* 181 */     if (paramInputStream == null) {
/* 182 */       throw new CertificateException("input stream is null");
/*     */     }
/*     */     
/*     */     try {
/* 186 */       DerInputStream derInputStream = new DerInputStream(readAllBytes(paramInputStream));
/* 187 */       DerValue[] arrayOfDerValue = derInputStream.getSequence(3);
/* 188 */       if (arrayOfDerValue.length == 0) {
/* 189 */         return Collections.emptyList();
/*     */       }
/*     */       
/* 192 */       certificateFactory = CertificateFactory.getInstance("X.509");
/* 193 */       arrayList = new ArrayList(arrayOfDerValue.length);
/*     */ 
/*     */       
/* 196 */       for (int i = arrayOfDerValue.length - 1; i >= 0; i--) {
/* 197 */         arrayList.add((X509Certificate)certificateFactory
/* 198 */             .generateCertificate(new ByteArrayInputStream(arrayOfDerValue[i].toByteArray())));
/*     */       }
/*     */       
/* 201 */       return Collections.unmodifiableList(arrayList);
/*     */     }
/* 203 */     catch (IOException iOException) {
/* 204 */       throw new CertificateException("IOException parsing PkiPath data: " + iOException, iOException);
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
/*     */   private static List<X509Certificate> parsePKCS7(InputStream paramInputStream) throws CertificateException {
/*     */     List<X509Certificate> list;
/* 221 */     if (paramInputStream == null) {
/* 222 */       throw new CertificateException("input stream is null");
/*     */     }
/*     */     
/*     */     try {
/* 226 */       if (!paramInputStream.markSupported())
/*     */       {
/*     */         
/* 229 */         paramInputStream = new ByteArrayInputStream(readAllBytes(paramInputStream));
/*     */       }
/* 231 */       PKCS7 pKCS7 = new PKCS7(paramInputStream);
/*     */       
/* 233 */       X509Certificate[] arrayOfX509Certificate = pKCS7.getCertificates();
/*     */       
/* 235 */       if (arrayOfX509Certificate != null) {
/* 236 */         list = Arrays.asList(arrayOfX509Certificate);
/*     */       } else {
/*     */         
/* 239 */         list = new ArrayList(0);
/*     */       } 
/* 241 */     } catch (IOException iOException) {
/* 242 */       throw new CertificateException("IOException parsing PKCS7 data: " + iOException);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     return Collections.unmodifiableList(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] readAllBytes(InputStream paramInputStream) throws IOException {
/* 259 */     byte[] arrayOfByte = new byte[8192];
/* 260 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
/*     */     int i;
/* 262 */     while ((i = paramInputStream.read(arrayOfByte)) != -1) {
/* 263 */       byteArrayOutputStream.write(arrayOfByte, 0, i);
/*     */     }
/* 265 */     return byteArrayOutputStream.toByteArray();
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
/*     */   public byte[] getEncoded() throws CertificateEncodingException {
/* 278 */     return encodePKIPATH();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] encodePKIPATH() throws CertificateEncodingException {
/* 289 */     ListIterator<X509Certificate> listIterator = this.certs.listIterator(this.certs.size());
/*     */     try {
/* 291 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/*     */ 
/*     */       
/* 294 */       while (listIterator.hasPrevious()) {
/* 295 */         X509Certificate x509Certificate = listIterator.previous();
/*     */         
/* 297 */         if (this.certs.lastIndexOf(x509Certificate) != this.certs.indexOf(x509Certificate)) {
/* 298 */           throw new CertificateEncodingException("Duplicate Certificate");
/*     */         }
/*     */ 
/*     */         
/* 302 */         byte[] arrayOfByte = x509Certificate.getEncoded();
/* 303 */         derOutputStream1.write(arrayOfByte);
/*     */       } 
/*     */ 
/*     */       
/* 307 */       DerOutputStream derOutputStream2 = new DerOutputStream();
/* 308 */       derOutputStream2.write((byte)48, derOutputStream1);
/* 309 */       return derOutputStream2.toByteArray();
/*     */     }
/* 311 */     catch (IOException iOException) {
/* 312 */       throw new CertificateEncodingException("IOException encoding PkiPath data: " + iOException, iOException);
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
/*     */   private byte[] encodePKCS7() throws CertificateEncodingException {
/* 326 */     PKCS7 pKCS7 = new PKCS7(new sun.security.x509.AlgorithmId[0], new ContentInfo(ContentInfo.DATA_OID, null), this.certs.<X509Certificate>toArray(new X509Certificate[this.certs.size()]), new sun.security.pkcs.SignerInfo[0]);
/*     */     
/* 328 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */     try {
/* 330 */       pKCS7.encodeSignedData(derOutputStream);
/* 331 */     } catch (IOException iOException) {
/* 332 */       throw new CertificateEncodingException(iOException.getMessage());
/*     */     } 
/* 334 */     return derOutputStream.toByteArray();
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
/*     */   public byte[] getEncoded(String paramString) throws CertificateEncodingException {
/* 349 */     switch (paramString) {
/*     */       case "PkiPath":
/* 351 */         return encodePKIPATH();
/*     */       case "PKCS7":
/* 353 */         return encodePKCS7();
/*     */     } 
/* 355 */     throw new CertificateEncodingException("unsupported encoding");
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
/*     */   public static Iterator<String> getEncodingsStatic() {
/* 367 */     return encodingList.iterator();
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
/*     */   public Iterator<String> getEncodings() {
/* 383 */     return getEncodingsStatic();
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
/*     */   public List<X509Certificate> getCertificates() {
/* 395 */     return this.certs;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/X509CertPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */