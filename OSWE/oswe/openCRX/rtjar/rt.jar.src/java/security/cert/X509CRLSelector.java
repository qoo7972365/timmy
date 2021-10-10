/*     */ package java.security.cert;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.x509.CRLNumberExtension;
/*     */ import sun.security.x509.X500Name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X509CRLSelector
/*     */   implements CRLSelector
/*     */ {
/*     */   static {
/*  76 */     CertPathHelperImpl.initialize();
/*     */   }
/*     */   
/*  79 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */   private HashSet<Object> issuerNames;
/*     */   private HashSet<X500Principal> issuerX500Principals;
/*     */   private BigInteger minCRL;
/*     */   private BigInteger maxCRL;
/*     */   private Date dateAndTime;
/*     */   private X509Certificate certChecking;
/*  86 */   private long skew = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssuers(Collection<X500Principal> paramCollection) {
/* 122 */     if (paramCollection == null || paramCollection.isEmpty()) {
/* 123 */       this.issuerNames = null;
/* 124 */       this.issuerX500Principals = null;
/*     */     } else {
/*     */       
/* 127 */       this.issuerX500Principals = new HashSet<>(paramCollection);
/* 128 */       this.issuerNames = new HashSet();
/* 129 */       for (X500Principal x500Principal : this.issuerX500Principals) {
/* 130 */         this.issuerNames.add(x500Principal.getEncoded());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssuerNames(Collection<?> paramCollection) throws IOException {
/* 198 */     if (paramCollection == null || paramCollection.size() == 0) {
/* 199 */       this.issuerNames = null;
/* 200 */       this.issuerX500Principals = null;
/*     */     } else {
/* 202 */       HashSet<Object> hashSet = cloneAndCheckIssuerNames(paramCollection);
/*     */       
/* 204 */       this.issuerX500Principals = parseIssuerNames(hashSet);
/* 205 */       this.issuerNames = hashSet;
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
/*     */   public void addIssuer(X500Principal paramX500Principal) {
/* 223 */     addIssuerNameInternal(paramX500Principal.getEncoded(), paramX500Principal);
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
/*     */   public void addIssuerName(String paramString) throws IOException {
/* 247 */     addIssuerNameInternal(paramString, (new X500Name(paramString)).asX500Principal());
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
/*     */   public void addIssuerName(byte[] paramArrayOfbyte) throws IOException {
/* 276 */     addIssuerNameInternal(paramArrayOfbyte.clone(), (new X500Name(paramArrayOfbyte)).asX500Principal());
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
/*     */   private void addIssuerNameInternal(Object paramObject, X500Principal paramX500Principal) {
/* 290 */     if (this.issuerNames == null) {
/* 291 */       this.issuerNames = new HashSet();
/*     */     }
/* 293 */     if (this.issuerX500Principals == null) {
/* 294 */       this.issuerX500Principals = new HashSet<>();
/*     */     }
/* 296 */     this.issuerNames.add(paramObject);
/* 297 */     this.issuerX500Principals.add(paramX500Principal);
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
/*     */   private static HashSet<Object> cloneAndCheckIssuerNames(Collection<?> paramCollection) throws IOException {
/* 314 */     HashSet<Object> hashSet = new HashSet();
/* 315 */     Iterator<?> iterator = paramCollection.iterator();
/* 316 */     while (iterator.hasNext()) {
/* 317 */       Object object = iterator.next();
/* 318 */       if (!(object instanceof byte[]) && !(object instanceof String))
/*     */       {
/* 320 */         throw new IOException("name not byte array or String"); } 
/* 321 */       if (object instanceof byte[]) {
/* 322 */         hashSet.add(((byte[])object).clone()); continue;
/*     */       } 
/* 324 */       hashSet.add(object);
/*     */     } 
/* 326 */     return hashSet;
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
/*     */   private static HashSet<Object> cloneIssuerNames(Collection<Object> paramCollection) {
/*     */     try {
/* 346 */       return cloneAndCheckIssuerNames(paramCollection);
/* 347 */     } catch (IOException iOException) {
/* 348 */       throw new RuntimeException(iOException);
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
/*     */   private static HashSet<X500Principal> parseIssuerNames(Collection<Object> paramCollection) throws IOException {
/* 366 */     HashSet<X500Principal> hashSet = new HashSet();
/* 367 */     for (String str : paramCollection) {
/*     */       
/* 369 */       if (str instanceof String) {
/* 370 */         hashSet.add((new X500Name(str)).asX500Principal()); continue;
/*     */       } 
/*     */       try {
/* 373 */         hashSet.add(new X500Principal((byte[])str));
/* 374 */       } catch (IllegalArgumentException illegalArgumentException) {
/* 375 */         throw (IOException)(new IOException("Invalid name")).initCause(illegalArgumentException);
/*     */       } 
/*     */     } 
/*     */     
/* 379 */     return hashSet;
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
/*     */   public void setMinCRLNumber(BigInteger paramBigInteger) {
/* 391 */     this.minCRL = paramBigInteger;
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
/*     */   public void setMaxCRLNumber(BigInteger paramBigInteger) {
/* 403 */     this.maxCRL = paramBigInteger;
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
/*     */   public void setDateAndTime(Date paramDate) {
/* 422 */     if (paramDate == null) {
/* 423 */       this.dateAndTime = null;
/*     */     } else {
/* 425 */       this.dateAndTime = new Date(paramDate.getTime());
/* 426 */     }  this.skew = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setDateAndTime(Date paramDate, long paramLong) {
/* 434 */     this
/* 435 */       .dateAndTime = (paramDate == null) ? null : new Date(paramDate.getTime());
/* 436 */     this.skew = paramLong;
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
/*     */   public void setCertificateChecking(X509Certificate paramX509Certificate) {
/* 451 */     this.certChecking = paramX509Certificate;
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
/*     */   public Collection<X500Principal> getIssuers() {
/* 469 */     if (this.issuerX500Principals == null) {
/* 470 */       return null;
/*     */     }
/* 472 */     return Collections.unmodifiableCollection(this.issuerX500Principals);
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
/*     */   public Collection<Object> getIssuerNames() {
/* 499 */     if (this.issuerNames == null) {
/* 500 */       return null;
/*     */     }
/* 502 */     return cloneIssuerNames(this.issuerNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getMinCRL() {
/* 513 */     return this.minCRL;
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
/*     */   public BigInteger getMaxCRL() {
/* 525 */     return this.maxCRL;
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
/*     */   public Date getDateAndTime() {
/* 543 */     if (this.dateAndTime == null)
/* 544 */       return null; 
/* 545 */     return (Date)this.dateAndTime.clone();
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
/*     */   public X509Certificate getCertificateChecking() {
/* 559 */     return this.certChecking;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 569 */     StringBuffer stringBuffer = new StringBuffer();
/* 570 */     stringBuffer.append("X509CRLSelector: [\n");
/* 571 */     if (this.issuerNames != null) {
/* 572 */       stringBuffer.append("  IssuerNames:\n");
/* 573 */       Iterator<String> iterator = this.issuerNames.iterator();
/* 574 */       while (iterator.hasNext())
/* 575 */         stringBuffer.append("    " + iterator.next() + "\n"); 
/*     */     } 
/* 577 */     if (this.minCRL != null)
/* 578 */       stringBuffer.append("  minCRLNumber: " + this.minCRL + "\n"); 
/* 579 */     if (this.maxCRL != null)
/* 580 */       stringBuffer.append("  maxCRLNumber: " + this.maxCRL + "\n"); 
/* 581 */     if (this.dateAndTime != null)
/* 582 */       stringBuffer.append("  dateAndTime: " + this.dateAndTime + "\n"); 
/* 583 */     if (this.certChecking != null)
/* 584 */       stringBuffer.append("  Certificate being checked: " + this.certChecking + "\n"); 
/* 585 */     stringBuffer.append("]");
/* 586 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean match(CRL paramCRL) {
/* 597 */     if (!(paramCRL instanceof X509CRL)) {
/* 598 */       return false;
/*     */     }
/* 600 */     X509CRL x509CRL = (X509CRL)paramCRL;
/*     */ 
/*     */     
/* 603 */     if (this.issuerNames != null) {
/* 604 */       X500Principal x500Principal = x509CRL.getIssuerX500Principal();
/* 605 */       Iterator<X500Principal> iterator = this.issuerX500Principals.iterator();
/* 606 */       boolean bool = false;
/* 607 */       while (!bool && iterator.hasNext()) {
/* 608 */         if (((X500Principal)iterator.next()).equals(x500Principal)) {
/* 609 */           bool = true;
/*     */         }
/*     */       } 
/* 612 */       if (!bool) {
/* 613 */         if (debug != null) {
/* 614 */           debug.println("X509CRLSelector.match: issuer DNs don't match");
/*     */         }
/*     */         
/* 617 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 621 */     if (this.minCRL != null || this.maxCRL != null) {
/*     */       BigInteger bigInteger;
/* 623 */       byte[] arrayOfByte = x509CRL.getExtensionValue("2.5.29.20");
/* 624 */       if (arrayOfByte == null && 
/* 625 */         debug != null) {
/* 626 */         debug.println("X509CRLSelector.match: no CRLNumber");
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 631 */         DerInputStream derInputStream = new DerInputStream(arrayOfByte);
/* 632 */         byte[] arrayOfByte1 = derInputStream.getOctetString();
/* 633 */         CRLNumberExtension cRLNumberExtension = new CRLNumberExtension(Boolean.FALSE, arrayOfByte1);
/*     */         
/* 635 */         bigInteger = cRLNumberExtension.get("value");
/* 636 */       } catch (IOException iOException) {
/* 637 */         if (debug != null) {
/* 638 */           debug.println("X509CRLSelector.match: exception in decoding CRL number");
/*     */         }
/*     */         
/* 641 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 645 */       if (this.minCRL != null && 
/* 646 */         bigInteger.compareTo(this.minCRL) < 0) {
/* 647 */         if (debug != null) {
/* 648 */           debug.println("X509CRLSelector.match: CRLNumber too small");
/*     */         }
/* 650 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 655 */       if (this.maxCRL != null && 
/* 656 */         bigInteger.compareTo(this.maxCRL) > 0) {
/* 657 */         if (debug != null) {
/* 658 */           debug.println("X509CRLSelector.match: CRLNumber too large");
/*     */         }
/* 660 */         return false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 667 */     if (this.dateAndTime != null) {
/* 668 */       Date date1 = x509CRL.getThisUpdate();
/* 669 */       Date date2 = x509CRL.getNextUpdate();
/* 670 */       if (date2 == null) {
/* 671 */         if (debug != null) {
/* 672 */           debug.println("X509CRLSelector.match: nextUpdate null");
/*     */         }
/* 674 */         return false;
/*     */       } 
/* 676 */       Date date3 = this.dateAndTime;
/* 677 */       Date date4 = this.dateAndTime;
/* 678 */       if (this.skew > 0L) {
/* 679 */         date3 = new Date(this.dateAndTime.getTime() + this.skew);
/* 680 */         date4 = new Date(this.dateAndTime.getTime() - this.skew);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 686 */       if (date4.after(date2) || date3
/* 687 */         .before(date1)) {
/* 688 */         if (debug != null) {
/* 689 */           debug.println("X509CRLSelector.match: update out-of-range");
/*     */         }
/* 691 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 695 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 705 */       X509CRLSelector x509CRLSelector = (X509CRLSelector)super.clone();
/* 706 */       if (this.issuerNames != null) {
/* 707 */         x509CRLSelector.issuerNames = new HashSet(this.issuerNames);
/*     */         
/* 709 */         x509CRLSelector.issuerX500Principals = new HashSet<>(this.issuerX500Principals);
/*     */       } 
/*     */       
/* 712 */       return x509CRLSelector;
/* 713 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 715 */       throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/X509CRLSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */