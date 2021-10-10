/*     */ package com.sun.org.apache.xml.internal.security.keys.storage.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SKI;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverSpi;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateExpiredException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.CertificateNotYetValidException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertsInFilesystemDirectoryResolver
/*     */   extends StorageResolverSpi
/*     */ {
/*  52 */   private static Logger log = Logger.getLogger(CertsInFilesystemDirectoryResolver.class
/*  53 */       .getName());
/*     */ 
/*     */ 
/*     */   
/*  57 */   private String merlinsCertificatesDir = null;
/*     */ 
/*     */   
/*  60 */   private List<X509Certificate> certs = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertsInFilesystemDirectoryResolver(String paramString) throws StorageResolverException {
/*  68 */     this.merlinsCertificatesDir = paramString;
/*     */     
/*  70 */     readCertsFromHarddrive();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readCertsFromHarddrive() throws StorageResolverException {
/*  80 */     File file = new File(this.merlinsCertificatesDir);
/*  81 */     ArrayList<String> arrayList = new ArrayList();
/*  82 */     String[] arrayOfString = file.list();
/*     */     
/*  84 */     for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*  85 */       String str = arrayOfString[b1];
/*     */       
/*  87 */       if (str.endsWith(".crt")) {
/*  88 */         arrayList.add(arrayOfString[b1]);
/*     */       }
/*     */     } 
/*     */     
/*  92 */     CertificateFactory certificateFactory = null;
/*     */     
/*     */     try {
/*  95 */       certificateFactory = CertificateFactory.getInstance("X.509");
/*  96 */     } catch (CertificateException certificateException) {
/*  97 */       throw new StorageResolverException("empty", certificateException);
/*     */     } 
/*     */     
/* 100 */     if (certificateFactory == null) {
/* 101 */       throw new StorageResolverException("empty");
/*     */     }
/*     */     
/* 104 */     for (byte b2 = 0; b2 < arrayList.size(); b2++) {
/* 105 */       String str1 = file.getAbsolutePath() + File.separator + (String)arrayList.get(b2);
/* 106 */       File file1 = new File(str1);
/* 107 */       boolean bool = false;
/* 108 */       String str2 = null;
/*     */       
/* 110 */       FileInputStream fileInputStream = null;
/*     */       try {
/* 112 */         fileInputStream = new FileInputStream(file1);
/*     */         
/* 114 */         X509Certificate x509Certificate = (X509Certificate)certificateFactory.generateCertificate(fileInputStream);
/*     */ 
/*     */         
/* 117 */         x509Certificate.checkValidity();
/* 118 */         this.certs.add(x509Certificate);
/*     */         
/* 120 */         str2 = x509Certificate.getSubjectX500Principal().getName();
/* 121 */         bool = true;
/* 122 */       } catch (FileNotFoundException fileNotFoundException) {
/* 123 */         if (log.isLoggable(Level.FINE)) {
/* 124 */           log.log(Level.FINE, "Could not add certificate from file " + str1, fileNotFoundException);
/*     */         }
/* 126 */       } catch (CertificateNotYetValidException certificateNotYetValidException) {
/* 127 */         if (log.isLoggable(Level.FINE)) {
/* 128 */           log.log(Level.FINE, "Could not add certificate from file " + str1, certificateNotYetValidException);
/*     */         }
/* 130 */       } catch (CertificateExpiredException certificateExpiredException) {
/* 131 */         if (log.isLoggable(Level.FINE)) {
/* 132 */           log.log(Level.FINE, "Could not add certificate from file " + str1, certificateExpiredException);
/*     */         }
/* 134 */       } catch (CertificateException certificateException) {
/* 135 */         if (log.isLoggable(Level.FINE)) {
/* 136 */           log.log(Level.FINE, "Could not add certificate from file " + str1, certificateException);
/*     */         }
/*     */       } finally {
/*     */         try {
/* 140 */           if (fileInputStream != null) {
/* 141 */             fileInputStream.close();
/*     */           }
/* 143 */         } catch (IOException iOException) {
/* 144 */           if (log.isLoggable(Level.FINE)) {
/* 145 */             log.log(Level.FINE, "Could not add certificate from file " + str1, iOException);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 150 */       if (bool && log.isLoggable(Level.FINE)) {
/* 151 */         log.log(Level.FINE, "Added certificate: " + str2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Certificate> getIterator() {
/* 158 */     return new FilesystemIterator(this.certs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FilesystemIterator
/*     */     implements Iterator<Certificate>
/*     */   {
/* 167 */     List<X509Certificate> certs = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FilesystemIterator(List<X509Certificate> param1List) {
/* 178 */       this.certs = param1List;
/* 179 */       this.i = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 184 */       return (this.i < this.certs.size());
/*     */     }
/*     */ 
/*     */     
/*     */     public Certificate next() {
/* 189 */       return this.certs.get(this.i++);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 197 */       throw new UnsupportedOperationException("Can't remove keys from KeyStore");
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
/*     */   public static void main(String[] paramArrayOfString) throws Exception {
/* 209 */     CertsInFilesystemDirectoryResolver certsInFilesystemDirectoryResolver = new CertsInFilesystemDirectoryResolver("data/ie/baltimore/merlin-examples/merlin-xmldsig-eighteen/certs");
/*     */ 
/*     */ 
/*     */     
/* 213 */     for (Iterator<Certificate> iterator = certsInFilesystemDirectoryResolver.getIterator(); iterator.hasNext(); ) {
/* 214 */       X509Certificate x509Certificate = (X509Certificate)iterator.next();
/*     */       
/* 216 */       byte[] arrayOfByte = XMLX509SKI.getSKIBytesFromCert(x509Certificate);
/*     */       
/* 218 */       System.out.println();
/* 219 */       System.out.println("Base64(SKI())=                 \"" + 
/* 220 */           Base64.encode(arrayOfByte) + "\"");
/* 221 */       System.out.println("cert.getSerialNumber()=        \"" + x509Certificate
/* 222 */           .getSerialNumber().toString() + "\"");
/* 223 */       System.out.println("cert.getSubjectX500Principal().getName()= \"" + x509Certificate
/* 224 */           .getSubjectX500Principal().getName() + "\"");
/* 225 */       System.out.println("cert.getIssuerX500Principal().getName()=  \"" + x509Certificate
/* 226 */           .getIssuerX500Principal().getName() + "\"");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/storage/implementations/CertsInFilesystemDirectoryResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */