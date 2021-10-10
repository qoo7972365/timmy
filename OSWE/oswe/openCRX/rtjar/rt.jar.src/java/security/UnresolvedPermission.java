/*     */ package java.security;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import sun.misc.IOUtils;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UnresolvedPermission
/*     */   extends Permission
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4821973115467008846L;
/* 113 */   private static final Debug debug = Debug.getInstance("policy,access", "UnresolvedPermission");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String actions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Certificate[] certs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnresolvedPermission(String paramString1, String paramString2, String paramString3, Certificate[] paramArrayOfCertificate) {
/* 161 */     super(paramString1);
/*     */     
/* 163 */     if (paramString1 == null) {
/* 164 */       throw new NullPointerException("type can't be null");
/*     */     }
/* 166 */     this.type = paramString1;
/* 167 */     this.name = paramString2;
/* 168 */     this.actions = paramString3;
/* 169 */     if (paramArrayOfCertificate != null) {
/*     */       byte b;
/* 171 */       for (b = 0; b < paramArrayOfCertificate.length; b++) {
/* 172 */         if (!(paramArrayOfCertificate[b] instanceof X509Certificate)) {
/*     */ 
/*     */           
/* 175 */           this.certs = (Certificate[])paramArrayOfCertificate.clone();
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 180 */       if (this.certs == null) {
/*     */ 
/*     */         
/* 183 */         b = 0;
/* 184 */         byte b1 = 0;
/* 185 */         while (b < paramArrayOfCertificate.length) {
/* 186 */           b1++;
/* 187 */           while (b + 1 < paramArrayOfCertificate.length && ((X509Certificate)paramArrayOfCertificate[b])
/* 188 */             .getIssuerDN().equals(((X509Certificate)paramArrayOfCertificate[b + 1])
/* 189 */               .getSubjectDN())) {
/* 190 */             b++;
/*     */           }
/* 192 */           b++;
/*     */         } 
/* 194 */         if (b1 == paramArrayOfCertificate.length)
/*     */         {
/*     */           
/* 197 */           this.certs = (Certificate[])paramArrayOfCertificate.clone();
/*     */         }
/*     */         
/* 200 */         if (this.certs == null) {
/*     */           
/* 202 */           ArrayList<Certificate> arrayList = new ArrayList();
/*     */           
/* 204 */           b = 0;
/* 205 */           while (b < paramArrayOfCertificate.length) {
/* 206 */             arrayList.add(paramArrayOfCertificate[b]);
/* 207 */             while (b + 1 < paramArrayOfCertificate.length && ((X509Certificate)paramArrayOfCertificate[b])
/* 208 */               .getIssuerDN().equals(((X509Certificate)paramArrayOfCertificate[b + 1])
/* 209 */                 .getSubjectDN())) {
/* 210 */               b++;
/*     */             }
/* 212 */             b++;
/*     */           } 
/* 214 */           this
/* 215 */             .certs = new Certificate[arrayList.size()];
/* 216 */           arrayList.toArray(this.certs);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 223 */   private static final Class[] PARAMS0 = new Class[0];
/* 224 */   private static final Class[] PARAMS1 = new Class[] { String.class };
/* 225 */   private static final Class[] PARAMS2 = new Class[] { String.class, String.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Permission resolve(Permission paramPermission, Certificate[] paramArrayOfCertificate) {
/* 232 */     if (this.certs != null) {
/*     */       
/* 234 */       if (paramArrayOfCertificate == null) {
/* 235 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 240 */       for (byte b = 0; b < this.certs.length; b++) {
/* 241 */         boolean bool = false;
/* 242 */         for (byte b1 = 0; b1 < paramArrayOfCertificate.length; b1++) {
/* 243 */           if (this.certs[b].equals(paramArrayOfCertificate[b1])) {
/* 244 */             bool = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 248 */         if (!bool) return null; 
/*     */       } 
/*     */     } 
/*     */     try {
/* 252 */       Class<?> clazz = paramPermission.getClass();
/*     */       
/* 254 */       if (this.name == null && this.actions == null) {
/*     */         try {
/* 256 */           Constructor<?> constructor1 = clazz.getConstructor(PARAMS0);
/* 257 */           return (Permission)constructor1.newInstance(new Object[0]);
/* 258 */         } catch (NoSuchMethodException noSuchMethodException) {
/*     */           try {
/* 260 */             Constructor<?> constructor1 = clazz.getConstructor(PARAMS1);
/* 261 */             return (Permission)constructor1.newInstance(new Object[] { this.name });
/*     */           }
/* 263 */           catch (NoSuchMethodException noSuchMethodException1) {
/* 264 */             Constructor<?> constructor1 = clazz.getConstructor(PARAMS2);
/* 265 */             return (Permission)constructor1.newInstance(new Object[] { this.name, this.actions });
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 270 */       if (this.name != null && this.actions == null) {
/*     */         try {
/* 272 */           Constructor<?> constructor1 = clazz.getConstructor(PARAMS1);
/* 273 */           return (Permission)constructor1.newInstance(new Object[] { this.name });
/*     */         }
/* 275 */         catch (NoSuchMethodException noSuchMethodException) {
/* 276 */           Constructor<?> constructor1 = clazz.getConstructor(PARAMS2);
/* 277 */           return (Permission)constructor1.newInstance(new Object[] { this.name, this.actions });
/*     */         } 
/*     */       }
/*     */       
/* 281 */       Constructor<?> constructor = clazz.getConstructor(PARAMS2);
/* 282 */       return (Permission)constructor.newInstance(new Object[] { this.name, this.actions });
/*     */ 
/*     */     
/*     */     }
/* 286 */     catch (NoSuchMethodException noSuchMethodException) {
/* 287 */       if (debug != null) {
/* 288 */         debug.println("NoSuchMethodException:\n  could not find proper constructor for " + this.type);
/*     */         
/* 290 */         noSuchMethodException.printStackTrace();
/*     */       } 
/* 292 */       return null;
/* 293 */     } catch (Exception exception) {
/* 294 */       if (debug != null) {
/* 295 */         debug.println("unable to instantiate " + this.name);
/* 296 */         exception.printStackTrace();
/*     */       } 
/* 298 */       return null;
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
/*     */   public boolean implies(Permission paramPermission) {
/* 312 */     return false;
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
/*     */   public boolean equals(Object paramObject) {
/* 332 */     if (paramObject == this) {
/* 333 */       return true;
/*     */     }
/* 335 */     if (!(paramObject instanceof UnresolvedPermission))
/* 336 */       return false; 
/* 337 */     UnresolvedPermission unresolvedPermission = (UnresolvedPermission)paramObject;
/*     */ 
/*     */     
/* 340 */     if (!this.type.equals(unresolvedPermission.type)) {
/* 341 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 345 */     if (this.name == null) {
/* 346 */       if (unresolvedPermission.name != null) {
/* 347 */         return false;
/*     */       }
/* 349 */     } else if (!this.name.equals(unresolvedPermission.name)) {
/* 350 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 354 */     if (this.actions == null) {
/* 355 */       if (unresolvedPermission.actions != null) {
/* 356 */         return false;
/*     */       }
/*     */     }
/* 359 */     else if (!this.actions.equals(unresolvedPermission.actions)) {
/* 360 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 365 */     if ((this.certs == null && unresolvedPermission.certs != null) || (this.certs != null && unresolvedPermission.certs == null) || (this.certs != null && unresolvedPermission.certs != null && this.certs.length != unresolvedPermission.certs.length))
/*     */     {
/*     */ 
/*     */       
/* 369 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     byte b;
/*     */     
/* 375 */     for (b = 0; this.certs != null && b < this.certs.length; b++) {
/* 376 */       boolean bool = false;
/* 377 */       for (byte b1 = 0; b1 < unresolvedPermission.certs.length; b1++) {
/* 378 */         if (this.certs[b].equals(unresolvedPermission.certs[b1])) {
/* 379 */           bool = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 383 */       if (!bool) return false;
/*     */     
/*     */     } 
/* 386 */     for (b = 0; unresolvedPermission.certs != null && b < unresolvedPermission.certs.length; b++) {
/* 387 */       boolean bool = false;
/* 388 */       for (byte b1 = 0; b1 < this.certs.length; b1++) {
/* 389 */         if (unresolvedPermission.certs[b].equals(this.certs[b1])) {
/* 390 */           bool = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 394 */       if (!bool) return false; 
/*     */     } 
/* 396 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 406 */     int i = this.type.hashCode();
/* 407 */     if (this.name != null)
/* 408 */       i ^= this.name.hashCode(); 
/* 409 */     if (this.actions != null)
/* 410 */       i ^= this.actions.hashCode(); 
/* 411 */     return i;
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
/*     */   public String getActions() {
/* 426 */     return "";
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
/*     */   public String getUnresolvedType() {
/* 439 */     return this.type;
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
/*     */   public String getUnresolvedName() {
/* 453 */     return this.name;
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
/*     */   public String getUnresolvedActions() {
/* 467 */     return this.actions;
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
/*     */   public Certificate[] getUnresolvedCerts() {
/* 481 */     return (this.certs == null) ? null : (Certificate[])this.certs.clone();
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
/* 492 */     return "(unresolved " + this.type + " " + this.name + " " + this.actions + ")";
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
/*     */   public PermissionCollection newPermissionCollection() {
/* 504 */     return new UnresolvedPermissionCollection();
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 526 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 528 */     if (this.certs == null || this.certs.length == 0) {
/* 529 */       paramObjectOutputStream.writeInt(0);
/*     */     } else {
/*     */       
/* 532 */       paramObjectOutputStream.writeInt(this.certs.length);
/*     */       
/* 534 */       for (byte b = 0; b < this.certs.length; b++) {
/* 535 */         Certificate certificate = this.certs[b];
/*     */         try {
/* 537 */           paramObjectOutputStream.writeUTF(certificate.getType());
/* 538 */           byte[] arrayOfByte = certificate.getEncoded();
/* 539 */           paramObjectOutputStream.writeInt(arrayOfByte.length);
/* 540 */           paramObjectOutputStream.write(arrayOfByte);
/* 541 */         } catch (CertificateEncodingException certificateEncodingException) {
/* 542 */           throw new IOException(certificateEncodingException.getMessage());
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 555 */     Hashtable<Object, Object> hashtable = null;
/* 556 */     ArrayList<Certificate> arrayList = null;
/*     */     
/* 558 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 560 */     if (this.type == null) {
/* 561 */       throw new NullPointerException("type can't be null");
/*     */     }
/*     */     
/* 564 */     int i = paramObjectInputStream.readInt();
/* 565 */     if (i > 0) {
/*     */ 
/*     */       
/* 568 */       hashtable = new Hashtable<>(3);
/* 569 */       arrayList = new ArrayList((i > 20) ? 20 : i);
/* 570 */     } else if (i < 0) {
/* 571 */       throw new IOException("size cannot be negative");
/*     */     } 
/*     */     
/* 574 */     for (byte b = 0; b < i; b++) {
/*     */       CertificateFactory certificateFactory;
/*     */       
/* 577 */       String str = paramObjectInputStream.readUTF();
/* 578 */       if (hashtable.containsKey(str)) {
/*     */         
/* 580 */         certificateFactory = (CertificateFactory)hashtable.get(str);
/*     */       } else {
/*     */         
/*     */         try {
/* 584 */           certificateFactory = CertificateFactory.getInstance(str);
/* 585 */         } catch (CertificateException certificateException) {
/* 586 */           throw new ClassNotFoundException("Certificate factory for " + str + " not found");
/*     */         } 
/*     */ 
/*     */         
/* 590 */         hashtable.put(str, certificateFactory);
/*     */       } 
/*     */       
/* 593 */       byte[] arrayOfByte = IOUtils.readExactlyNBytes(paramObjectInputStream, paramObjectInputStream.readInt());
/* 594 */       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/*     */       try {
/* 596 */         arrayList.add(certificateFactory.generateCertificate(byteArrayInputStream));
/* 597 */       } catch (CertificateException certificateException) {
/* 598 */         throw new IOException(certificateException.getMessage());
/*     */       } 
/* 600 */       byteArrayInputStream.close();
/*     */     } 
/* 602 */     if (arrayList != null)
/* 603 */       this.certs = arrayList.<Certificate>toArray(new Certificate[i]); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/UnresolvedPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */