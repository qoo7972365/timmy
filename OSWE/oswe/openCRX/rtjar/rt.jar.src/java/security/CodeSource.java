/*     */ package java.security;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.SocketPermission;
/*     */ import java.net.URL;
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import sun.misc.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CodeSource
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4977541819976013951L;
/*     */   private URL location;
/*  63 */   private transient CodeSigner[] signers = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private transient Certificate[] certs = null;
/*     */ 
/*     */   
/*     */   private transient SocketPermission sp;
/*     */ 
/*     */   
/*  74 */   private transient CertificateFactory factory = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CodeSource(URL paramURL, Certificate[] paramArrayOfCertificate) {
/*  86 */     this.location = paramURL;
/*     */ 
/*     */     
/*  89 */     if (paramArrayOfCertificate != null) {
/*  90 */       this.certs = (Certificate[])paramArrayOfCertificate.clone();
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
/*     */   public CodeSource(URL paramURL, CodeSigner[] paramArrayOfCodeSigner) {
/* 105 */     this.location = paramURL;
/*     */ 
/*     */     
/* 108 */     if (paramArrayOfCodeSigner != null) {
/* 109 */       this.signers = (CodeSigner[])paramArrayOfCodeSigner.clone();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     if (this.location != null) {
/* 121 */       return this.location.hashCode();
/*     */     }
/* 123 */     return 0;
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
/*     */   public boolean equals(Object paramObject) {
/* 139 */     if (paramObject == this) {
/* 140 */       return true;
/*     */     }
/*     */     
/* 143 */     if (!(paramObject instanceof CodeSource)) {
/* 144 */       return false;
/*     */     }
/* 146 */     CodeSource codeSource = (CodeSource)paramObject;
/*     */ 
/*     */     
/* 149 */     if (this.location == null)
/*     */     
/* 151 */     { if (codeSource.location != null) return false;
/*     */        }
/*     */     
/* 154 */     else if (!this.location.equals(codeSource.location)) { return false; }
/*     */ 
/*     */ 
/*     */     
/* 158 */     return matchCerts(codeSource, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final URL getLocation() {
/* 169 */     return this.location;
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
/*     */   public final Certificate[] getCertificates() {
/* 187 */     if (this.certs != null) {
/* 188 */       return (Certificate[])this.certs.clone();
/*     */     }
/* 190 */     if (this.signers != null) {
/*     */       
/* 192 */       ArrayList<Certificate> arrayList = new ArrayList();
/*     */       
/* 194 */       for (byte b = 0; b < this.signers.length; b++) {
/* 195 */         arrayList.addAll(this.signers[b]
/* 196 */             .getSignerCertPath().getCertificates());
/*     */       }
/* 198 */       this.certs = arrayList.<Certificate>toArray(
/* 199 */           new Certificate[arrayList.size()]);
/* 200 */       return (Certificate[])this.certs.clone();
/*     */     } 
/*     */     
/* 203 */     return null;
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
/*     */   public final CodeSigner[] getCodeSigners() {
/* 221 */     if (this.signers != null) {
/* 222 */       return (CodeSigner[])this.signers.clone();
/*     */     }
/* 224 */     if (this.certs != null) {
/*     */       
/* 226 */       this.signers = convertCertArrayToSignerArray(this.certs);
/* 227 */       return (CodeSigner[])this.signers.clone();
/*     */     } 
/*     */     
/* 230 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean implies(CodeSource paramCodeSource) {
/* 306 */     if (paramCodeSource == null) {
/* 307 */       return false;
/*     */     }
/* 309 */     return (matchCerts(paramCodeSource, false) && matchLocation(paramCodeSource));
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
/*     */   private boolean matchCerts(CodeSource paramCodeSource, boolean paramBoolean) {
/* 325 */     if (this.certs == null && this.signers == null) {
/* 326 */       if (paramBoolean) {
/* 327 */         return (paramCodeSource.certs == null && paramCodeSource.signers == null);
/*     */       }
/* 329 */       return true;
/*     */     } 
/*     */     
/* 332 */     if (this.signers != null && paramCodeSource.signers != null) {
/* 333 */       if (paramBoolean && this.signers.length != paramCodeSource.signers.length) {
/* 334 */         return false;
/*     */       }
/* 336 */       for (byte b = 0; b < this.signers.length; b++) {
/* 337 */         boolean bool = false;
/* 338 */         for (byte b1 = 0; b1 < paramCodeSource.signers.length; b1++) {
/* 339 */           if (this.signers[b].equals(paramCodeSource.signers[b1])) {
/* 340 */             bool = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 344 */         if (!bool) return false; 
/*     */       } 
/* 346 */       return true;
/*     */     } 
/*     */     
/* 349 */     if (this.certs != null && paramCodeSource.certs != null) {
/* 350 */       if (paramBoolean && this.certs.length != paramCodeSource.certs.length) {
/* 351 */         return false;
/*     */       }
/* 353 */       for (byte b = 0; b < this.certs.length; b++) {
/* 354 */         boolean bool = false;
/* 355 */         for (byte b1 = 0; b1 < paramCodeSource.certs.length; b1++) {
/* 356 */           if (this.certs[b].equals(paramCodeSource.certs[b1])) {
/* 357 */             bool = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 361 */         if (!bool) return false; 
/*     */       } 
/* 363 */       return true;
/*     */     } 
/*     */     
/* 366 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchLocation(CodeSource paramCodeSource) {
/* 376 */     if (this.location == null) {
/* 377 */       return true;
/*     */     }
/* 379 */     if (paramCodeSource == null || paramCodeSource.location == null) {
/* 380 */       return false;
/*     */     }
/* 382 */     if (this.location.equals(paramCodeSource.location)) {
/* 383 */       return true;
/*     */     }
/* 385 */     if (!this.location.getProtocol().equalsIgnoreCase(paramCodeSource.location.getProtocol())) {
/* 386 */       return false;
/*     */     }
/* 388 */     int i = this.location.getPort();
/* 389 */     if (i != -1) {
/* 390 */       int j = paramCodeSource.location.getPort();
/*     */       
/* 392 */       int k = (j != -1) ? j : paramCodeSource.location.getDefaultPort();
/* 393 */       if (i != k) {
/* 394 */         return false;
/*     */       }
/*     */     } 
/* 397 */     if (this.location.getFile().endsWith("/-")) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 402 */       String str = this.location.getFile().substring(0, this.location
/* 403 */           .getFile().length() - 1);
/* 404 */       if (!paramCodeSource.location.getFile().startsWith(str))
/* 405 */         return false; 
/* 406 */     } else if (this.location.getFile().endsWith("/*")) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 411 */       int j = paramCodeSource.location.getFile().lastIndexOf('/');
/* 412 */       if (j == -1)
/* 413 */         return false; 
/* 414 */       String str3 = this.location.getFile().substring(0, this.location
/* 415 */           .getFile().length() - 1);
/* 416 */       String str4 = paramCodeSource.location.getFile().substring(0, j + 1);
/* 417 */       if (!str4.equals(str3)) {
/* 418 */         return false;
/*     */       
/*     */       }
/*     */     }
/* 422 */     else if (!paramCodeSource.location.getFile().equals(this.location.getFile()) && 
/* 423 */       !paramCodeSource.location.getFile().equals(this.location.getFile() + "/")) {
/* 424 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 428 */     if (this.location.getRef() != null && 
/* 429 */       !this.location.getRef().equals(paramCodeSource.location.getRef())) {
/* 430 */       return false;
/*     */     }
/*     */     
/* 433 */     String str1 = this.location.getHost();
/* 434 */     String str2 = paramCodeSource.location.getHost();
/* 435 */     if (str1 != null && ((
/* 436 */       !"".equals(str1) && !"localhost".equals(str1)) || (
/* 437 */       !"".equals(str2) && !"localhost".equals(str2))))
/*     */     {
/* 439 */       if (!str1.equals(str2)) {
/* 440 */         if (str2 == null) {
/* 441 */           return false;
/*     */         }
/* 443 */         if (this.sp == null) {
/* 444 */           this.sp = new SocketPermission(str1, "resolve");
/*     */         }
/* 446 */         if (paramCodeSource.sp == null) {
/* 447 */           paramCodeSource.sp = new SocketPermission(str2, "resolve");
/*     */         }
/* 449 */         if (!this.sp.implies(paramCodeSource.sp)) {
/* 450 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 455 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 466 */     StringBuilder stringBuilder = new StringBuilder();
/* 467 */     stringBuilder.append("(");
/* 468 */     stringBuilder.append(this.location);
/*     */     
/* 470 */     if (this.certs != null && this.certs.length > 0) {
/* 471 */       for (byte b = 0; b < this.certs.length; b++) {
/* 472 */         stringBuilder.append(" " + this.certs[b]);
/*     */       }
/*     */     }
/* 475 */     else if (this.signers != null && this.signers.length > 0) {
/* 476 */       for (byte b = 0; b < this.signers.length; b++) {
/* 477 */         stringBuilder.append(" " + this.signers[b]);
/*     */       }
/*     */     } else {
/* 480 */       stringBuilder.append(" <no signer certificates>");
/*     */     } 
/* 482 */     stringBuilder.append(")");
/* 483 */     return stringBuilder.toString();
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 503 */     paramObjectOutputStream.defaultWriteObject();
/*     */ 
/*     */     
/* 506 */     if (this.certs == null || this.certs.length == 0) {
/* 507 */       paramObjectOutputStream.writeInt(0);
/*     */     } else {
/*     */       
/* 510 */       paramObjectOutputStream.writeInt(this.certs.length);
/*     */       
/* 512 */       for (byte b = 0; b < this.certs.length; b++) {
/* 513 */         Certificate certificate = this.certs[b];
/*     */         try {
/* 515 */           paramObjectOutputStream.writeUTF(certificate.getType());
/* 516 */           byte[] arrayOfByte = certificate.getEncoded();
/* 517 */           paramObjectOutputStream.writeInt(arrayOfByte.length);
/* 518 */           paramObjectOutputStream.write(arrayOfByte);
/* 519 */         } catch (CertificateEncodingException certificateEncodingException) {
/* 520 */           throw new IOException(certificateEncodingException.getMessage());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 526 */     if (this.signers != null && this.signers.length > 0) {
/* 527 */       paramObjectOutputStream.writeObject(this.signers);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 538 */     Hashtable<Object, Object> hashtable = null;
/* 539 */     ArrayList<Certificate> arrayList = null;
/*     */     
/* 541 */     paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */     
/* 544 */     int i = paramObjectInputStream.readInt();
/* 545 */     if (i > 0) {
/*     */ 
/*     */       
/* 548 */       hashtable = new Hashtable<>(3);
/* 549 */       arrayList = new ArrayList((i > 20) ? 20 : i);
/* 550 */     } else if (i < 0) {
/* 551 */       throw new IOException("size cannot be negative");
/*     */     } 
/*     */     
/* 554 */     for (byte b = 0; b < i; b++) {
/*     */       CertificateFactory certificateFactory;
/*     */       
/* 557 */       String str = paramObjectInputStream.readUTF();
/* 558 */       if (hashtable.containsKey(str)) {
/*     */         
/* 560 */         certificateFactory = (CertificateFactory)hashtable.get(str);
/*     */       } else {
/*     */         
/*     */         try {
/* 564 */           certificateFactory = CertificateFactory.getInstance(str);
/* 565 */         } catch (CertificateException certificateException) {
/* 566 */           throw new ClassNotFoundException("Certificate factory for " + str + " not found");
/*     */         } 
/*     */ 
/*     */         
/* 570 */         hashtable.put(str, certificateFactory);
/*     */       } 
/*     */       
/* 573 */       byte[] arrayOfByte = IOUtils.readExactlyNBytes(paramObjectInputStream, paramObjectInputStream.readInt());
/* 574 */       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/*     */       try {
/* 576 */         arrayList.add(certificateFactory.generateCertificate(byteArrayInputStream));
/* 577 */       } catch (CertificateException certificateException) {
/* 578 */         throw new IOException(certificateException.getMessage());
/*     */       } 
/* 580 */       byteArrayInputStream.close();
/*     */     } 
/*     */     
/* 583 */     if (arrayList != null) {
/* 584 */       this.certs = arrayList.<Certificate>toArray(new Certificate[i]);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 589 */       this.signers = (CodeSigner[])((CodeSigner[])paramObjectInputStream.readObject()).clone();
/* 590 */     } catch (IOException iOException) {}
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
/*     */   private CodeSigner[] convertCertArrayToSignerArray(Certificate[] paramArrayOfCertificate) {
/* 605 */     if (paramArrayOfCertificate == null) {
/* 606 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 611 */       if (this.factory == null) {
/* 612 */         this.factory = CertificateFactory.getInstance("X.509");
/*     */       }
/*     */ 
/*     */       
/* 616 */       byte b = 0;
/* 617 */       ArrayList<CodeSigner> arrayList = new ArrayList();
/* 618 */       while (b < paramArrayOfCertificate.length) {
/* 619 */         ArrayList<Certificate> arrayList1 = new ArrayList();
/*     */         
/* 621 */         arrayList1.add(paramArrayOfCertificate[b++]);
/* 622 */         byte b1 = b;
/*     */ 
/*     */ 
/*     */         
/* 626 */         while (b1 < paramArrayOfCertificate.length && paramArrayOfCertificate[b1] instanceof X509Certificate && ((X509Certificate)paramArrayOfCertificate[b1])
/*     */           
/* 628 */           .getBasicConstraints() != -1) {
/* 629 */           arrayList1.add(paramArrayOfCertificate[b1]);
/* 630 */           b1++;
/*     */         } 
/* 632 */         b = b1;
/* 633 */         CertPath certPath = this.factory.generateCertPath(arrayList1);
/* 634 */         arrayList.add(new CodeSigner(certPath, null));
/*     */       } 
/*     */       
/* 637 */       if (arrayList.isEmpty()) {
/* 638 */         return null;
/*     */       }
/* 640 */       return arrayList.<CodeSigner>toArray(new CodeSigner[arrayList.size()]);
/*     */     
/*     */     }
/* 643 */     catch (CertificateException certificateException) {
/* 644 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/CodeSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */