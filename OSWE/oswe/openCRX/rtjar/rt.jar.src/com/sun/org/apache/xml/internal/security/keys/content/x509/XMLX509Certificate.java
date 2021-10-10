/*     */ package com.sun.org.apache.xml.internal.security.keys.content.x509;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.logging.Level;
/*     */ import org.w3c.dom.Document;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLX509Certificate
/*     */   extends SignatureElementProxy
/*     */   implements XMLX509DataContent
/*     */ {
/*     */   public static final String JCA_CERT_ID = "X.509";
/*     */   
/*     */   public XMLX509Certificate(Element paramElement, String paramString) throws XMLSecurityException {
/*  51 */     super(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLX509Certificate(Document paramDocument, byte[] paramArrayOfbyte) {
/*  61 */     super(paramDocument);
/*     */     
/*  63 */     addBase64Text(paramArrayOfbyte);
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
/*     */   public XMLX509Certificate(Document paramDocument, X509Certificate paramX509Certificate) throws XMLSecurityException {
/*  75 */     super(paramDocument);
/*     */     
/*     */     try {
/*  78 */       addBase64Text(paramX509Certificate.getEncoded());
/*  79 */     } catch (CertificateEncodingException certificateEncodingException) {
/*  80 */       throw new XMLSecurityException("empty", certificateEncodingException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getCertificateBytes() throws XMLSecurityException {
/*  91 */     return getBytesFromTextChild();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate getX509Certificate() throws XMLSecurityException {
/*     */     try {
/* 102 */       byte[] arrayOfByte = getCertificateBytes();
/*     */       
/* 104 */       CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
/*     */       
/* 106 */       X509Certificate x509Certificate = (X509Certificate)certificateFactory.generateCertificate(new ByteArrayInputStream(arrayOfByte));
/*     */ 
/*     */ 
/*     */       
/* 110 */       if (x509Certificate != null) {
/* 111 */         return x509Certificate;
/*     */       }
/*     */       
/* 114 */       return null;
/* 115 */     } catch (CertificateException certificateException) {
/* 116 */       throw new XMLSecurityException("empty", certificateException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey getPublicKey() throws XMLSecurityException {
/* 127 */     X509Certificate x509Certificate = getX509Certificate();
/*     */     
/* 129 */     if (x509Certificate != null) {
/* 130 */       return x509Certificate.getPublicKey();
/*     */     }
/*     */     
/* 133 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 138 */     if (!(paramObject instanceof XMLX509Certificate)) {
/* 139 */       return false;
/*     */     }
/* 141 */     XMLX509Certificate xMLX509Certificate = (XMLX509Certificate)paramObject;
/*     */     try {
/* 143 */       return Arrays.equals(xMLX509Certificate.getCertificateBytes(), getCertificateBytes());
/* 144 */     } catch (XMLSecurityException xMLSecurityException) {
/* 145 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 150 */     int i = 17;
/*     */     try {
/* 152 */       byte[] arrayOfByte = getCertificateBytes();
/* 153 */       for (byte b = 0; b < arrayOfByte.length; b++) {
/* 154 */         i = 31 * i + arrayOfByte[b];
/*     */       }
/* 156 */     } catch (XMLSecurityException xMLSecurityException) {
/* 157 */       if (log.isLoggable(Level.FINE)) {
/* 158 */         log.log(Level.FINE, xMLSecurityException.getMessage(), xMLSecurityException);
/*     */       }
/*     */     } 
/* 161 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 166 */     return "X509Certificate";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/x509/XMLX509Certificate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */