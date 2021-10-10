/*     */ package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509Certificate;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509IssuerSerial;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SKI;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SubjectName;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*     */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.security.Key;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.SecretKey;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrivateKeyResolver
/*     */   extends KeyResolverSpi
/*     */ {
/*  39 */   private static Logger log = Logger.getLogger(PrivateKeyResolver.class.getName());
/*     */ 
/*     */   
/*     */   private KeyStore keyStore;
/*     */   
/*     */   private char[] password;
/*     */ 
/*     */   
/*     */   public PrivateKeyResolver(KeyStore paramKeyStore, char[] paramArrayOfchar) {
/*  48 */     this.keyStore = paramKeyStore;
/*  49 */     this.password = paramArrayOfchar;
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
/*     */   public boolean engineCanResolve(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
/*  61 */     if (XMLUtils.elementIsInSignatureSpace(paramElement, "X509Data") || 
/*  62 */       XMLUtils.elementIsInSignatureSpace(paramElement, "KeyName")) {
/*  63 */       return true;
/*     */     }
/*     */     
/*  66 */     return false;
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
/*     */   public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  81 */     return null;
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
/*     */   public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/*  95 */     return null;
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
/*     */   public SecretKey engineResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 111 */     return null;
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
/*     */   public PrivateKey engineLookupAndResolvePrivateKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
/* 126 */     if (log.isLoggable(Level.FINE)) {
/* 127 */       log.log(Level.FINE, "Can I resolve " + paramElement.getTagName() + "?");
/*     */     }
/*     */     
/* 130 */     if (XMLUtils.elementIsInSignatureSpace(paramElement, "X509Data")) {
/* 131 */       PrivateKey privateKey = resolveX509Data(paramElement, paramString);
/* 132 */       if (privateKey != null) {
/* 133 */         return privateKey;
/*     */       }
/* 135 */     } else if (XMLUtils.elementIsInSignatureSpace(paramElement, "KeyName")) {
/* 136 */       log.log(Level.FINE, "Can I resolve KeyName?");
/* 137 */       String str = paramElement.getFirstChild().getNodeValue();
/*     */       
/*     */       try {
/* 140 */         Key key = this.keyStore.getKey(str, this.password);
/* 141 */         if (key instanceof PrivateKey) {
/* 142 */           return (PrivateKey)key;
/*     */         }
/* 144 */       } catch (Exception exception) {
/* 145 */         log.log(Level.FINE, "Cannot recover the key", exception);
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     log.log(Level.FINE, "I can't");
/* 150 */     return null;
/*     */   }
/*     */   
/*     */   private PrivateKey resolveX509Data(Element paramElement, String paramString) {
/* 154 */     log.log(Level.FINE, "Can I resolve X509Data?");
/*     */     
/*     */     try {
/* 157 */       X509Data x509Data = new X509Data(paramElement, paramString);
/*     */       
/* 159 */       int i = x509Data.lengthSKI(); byte b;
/* 160 */       for (b = 0; b < i; b++) {
/* 161 */         XMLX509SKI xMLX509SKI = x509Data.itemSKI(b);
/* 162 */         PrivateKey privateKey = resolveX509SKI(xMLX509SKI);
/* 163 */         if (privateKey != null) {
/* 164 */           return privateKey;
/*     */         }
/*     */       } 
/*     */       
/* 168 */       i = x509Data.lengthIssuerSerial();
/* 169 */       for (b = 0; b < i; b++) {
/* 170 */         XMLX509IssuerSerial xMLX509IssuerSerial = x509Data.itemIssuerSerial(b);
/* 171 */         PrivateKey privateKey = resolveX509IssuerSerial(xMLX509IssuerSerial);
/* 172 */         if (privateKey != null) {
/* 173 */           return privateKey;
/*     */         }
/*     */       } 
/*     */       
/* 177 */       i = x509Data.lengthSubjectName();
/* 178 */       for (b = 0; b < i; b++) {
/* 179 */         XMLX509SubjectName xMLX509SubjectName = x509Data.itemSubjectName(b);
/* 180 */         PrivateKey privateKey = resolveX509SubjectName(xMLX509SubjectName);
/* 181 */         if (privateKey != null) {
/* 182 */           return privateKey;
/*     */         }
/*     */       } 
/*     */       
/* 186 */       i = x509Data.lengthCertificate();
/* 187 */       for (b = 0; b < i; b++) {
/* 188 */         XMLX509Certificate xMLX509Certificate = x509Data.itemCertificate(b);
/* 189 */         PrivateKey privateKey = resolveX509Certificate(xMLX509Certificate);
/* 190 */         if (privateKey != null) {
/* 191 */           return privateKey;
/*     */         }
/*     */       } 
/* 194 */     } catch (XMLSecurityException xMLSecurityException) {
/* 195 */       log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
/* 196 */     } catch (KeyStoreException keyStoreException) {
/* 197 */       log.log(Level.FINE, "KeyStoreException", keyStoreException);
/*     */     } 
/*     */     
/* 200 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrivateKey resolveX509SKI(XMLX509SKI paramXMLX509SKI) throws XMLSecurityException, KeyStoreException {
/* 207 */     log.log(Level.FINE, "Can I resolve X509SKI?");
/*     */     
/* 209 */     Enumeration<String> enumeration = this.keyStore.aliases();
/* 210 */     while (enumeration.hasMoreElements()) {
/* 211 */       String str = enumeration.nextElement();
/* 212 */       if (this.keyStore.isKeyEntry(str)) {
/*     */         
/* 214 */         Certificate certificate = this.keyStore.getCertificate(str);
/* 215 */         if (certificate instanceof X509Certificate) {
/* 216 */           XMLX509SKI xMLX509SKI = new XMLX509SKI(paramXMLX509SKI.getDocument(), (X509Certificate)certificate);
/*     */           
/* 218 */           if (xMLX509SKI.equals(paramXMLX509SKI)) {
/* 219 */             log.log(Level.FINE, "match !!! ");
/*     */             
/*     */             try {
/* 222 */               Key key = this.keyStore.getKey(str, this.password);
/* 223 */               if (key instanceof PrivateKey) {
/* 224 */                 return (PrivateKey)key;
/*     */               }
/* 226 */             } catch (Exception exception) {
/* 227 */               log.log(Level.FINE, "Cannot recover the key", exception);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrivateKey resolveX509IssuerSerial(XMLX509IssuerSerial paramXMLX509IssuerSerial) throws KeyStoreException {
/* 242 */     log.log(Level.FINE, "Can I resolve X509IssuerSerial?");
/*     */     
/* 244 */     Enumeration<String> enumeration = this.keyStore.aliases();
/* 245 */     while (enumeration.hasMoreElements()) {
/* 246 */       String str = enumeration.nextElement();
/* 247 */       if (this.keyStore.isKeyEntry(str)) {
/*     */         
/* 249 */         Certificate certificate = this.keyStore.getCertificate(str);
/* 250 */         if (certificate instanceof X509Certificate) {
/*     */           
/* 252 */           XMLX509IssuerSerial xMLX509IssuerSerial = new XMLX509IssuerSerial(paramXMLX509IssuerSerial.getDocument(), (X509Certificate)certificate);
/*     */           
/* 254 */           if (xMLX509IssuerSerial.equals(paramXMLX509IssuerSerial)) {
/* 255 */             log.log(Level.FINE, "match !!! ");
/*     */             
/*     */             try {
/* 258 */               Key key = this.keyStore.getKey(str, this.password);
/* 259 */               if (key instanceof PrivateKey) {
/* 260 */                 return (PrivateKey)key;
/*     */               }
/* 262 */             } catch (Exception exception) {
/* 263 */               log.log(Level.FINE, "Cannot recover the key", exception);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 271 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrivateKey resolveX509SubjectName(XMLX509SubjectName paramXMLX509SubjectName) throws KeyStoreException {
/* 278 */     log.log(Level.FINE, "Can I resolve X509SubjectName?");
/*     */     
/* 280 */     Enumeration<String> enumeration = this.keyStore.aliases();
/* 281 */     while (enumeration.hasMoreElements()) {
/* 282 */       String str = enumeration.nextElement();
/* 283 */       if (this.keyStore.isKeyEntry(str)) {
/*     */         
/* 285 */         Certificate certificate = this.keyStore.getCertificate(str);
/* 286 */         if (certificate instanceof X509Certificate) {
/*     */           
/* 288 */           XMLX509SubjectName xMLX509SubjectName = new XMLX509SubjectName(paramXMLX509SubjectName.getDocument(), (X509Certificate)certificate);
/*     */           
/* 290 */           if (xMLX509SubjectName.equals(paramXMLX509SubjectName)) {
/* 291 */             log.log(Level.FINE, "match !!! ");
/*     */             
/*     */             try {
/* 294 */               Key key = this.keyStore.getKey(str, this.password);
/* 295 */               if (key instanceof PrivateKey) {
/* 296 */                 return (PrivateKey)key;
/*     */               }
/* 298 */             } catch (Exception exception) {
/* 299 */               log.log(Level.FINE, "Cannot recover the key", exception);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 307 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrivateKey resolveX509Certificate(XMLX509Certificate paramXMLX509Certificate) throws XMLSecurityException, KeyStoreException {
/* 316 */     log.log(Level.FINE, "Can I resolve X509Certificate?");
/* 317 */     byte[] arrayOfByte = paramXMLX509Certificate.getCertificateBytes();
/*     */     
/* 319 */     Enumeration<String> enumeration = this.keyStore.aliases();
/* 320 */     while (enumeration.hasMoreElements()) {
/* 321 */       String str = enumeration.nextElement();
/* 322 */       if (this.keyStore.isKeyEntry(str)) {
/*     */         
/* 324 */         Certificate certificate = this.keyStore.getCertificate(str);
/* 325 */         if (certificate instanceof X509Certificate) {
/* 326 */           byte[] arrayOfByte1 = null;
/*     */           
/*     */           try {
/* 329 */             arrayOfByte1 = certificate.getEncoded();
/* 330 */           } catch (CertificateEncodingException certificateEncodingException) {}
/*     */ 
/*     */           
/* 333 */           if (arrayOfByte1 != null && Arrays.equals(arrayOfByte1, arrayOfByte)) {
/* 334 */             log.log(Level.FINE, "match !!! ");
/*     */             
/*     */             try {
/* 337 */               Key key = this.keyStore.getKey(str, this.password);
/* 338 */               if (key instanceof PrivateKey) {
/* 339 */                 return (PrivateKey)key;
/*     */               }
/*     */             }
/* 342 */             catch (Exception exception) {
/* 343 */               log.log(Level.FINE, "Cannot recover the key", exception);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 351 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/keyresolver/implementations/PrivateKeyResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */