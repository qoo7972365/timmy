/*     */ package com.sun.org.apache.xml.internal.security.keys.content.x509;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class XMLX509SKI
/*     */   extends SignatureElementProxy
/*     */   implements XMLX509DataContent
/*     */ {
/*  45 */   private static Logger log = Logger.getLogger(XMLX509SKI.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String SKI_OID = "2.5.29.14";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLX509SKI(Document paramDocument, byte[] paramArrayOfbyte) {
/*  65 */     super(paramDocument);
/*  66 */     addBase64Text(paramArrayOfbyte);
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
/*     */   public XMLX509SKI(Document paramDocument, X509Certificate paramX509Certificate) throws XMLSecurityException {
/*  78 */     super(paramDocument);
/*  79 */     addBase64Text(getSKIBytesFromCert(paramX509Certificate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLX509SKI(Element paramElement, String paramString) throws XMLSecurityException {
/*  90 */     super(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getSKIBytes() throws XMLSecurityException {
/* 100 */     return getBytesFromTextChild();
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
/*     */   public static byte[] getSKIBytesFromCert(X509Certificate paramX509Certificate) throws XMLSecurityException {
/* 115 */     if (paramX509Certificate.getVersion() < 3) {
/* 116 */       Object[] arrayOfObject = { Integer.valueOf(paramX509Certificate.getVersion()) };
/* 117 */       throw new XMLSecurityException("certificate.noSki.lowVersion", arrayOfObject);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue("2.5.29.14");
/* 127 */     if (arrayOfByte1 == null) {
/* 128 */       throw new XMLSecurityException("certificate.noSki.null");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length - 4];
/*     */     
/* 139 */     System.arraycopy(arrayOfByte1, 4, arrayOfByte2, 0, arrayOfByte2.length);
/*     */     
/* 141 */     if (log.isLoggable(Level.FINE)) {
/* 142 */       log.log(Level.FINE, "Base64 of SKI is " + Base64.encode(arrayOfByte2));
/*     */     }
/*     */     
/* 145 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 150 */     if (!(paramObject instanceof XMLX509SKI)) {
/* 151 */       return false;
/*     */     }
/*     */     
/* 154 */     XMLX509SKI xMLX509SKI = (XMLX509SKI)paramObject;
/*     */     
/*     */     try {
/* 157 */       return Arrays.equals(xMLX509SKI.getSKIBytes(), getSKIBytes());
/* 158 */     } catch (XMLSecurityException xMLSecurityException) {
/* 159 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 164 */     int i = 17;
/*     */     try {
/* 166 */       byte[] arrayOfByte = getSKIBytes();
/* 167 */       for (byte b = 0; b < arrayOfByte.length; b++) {
/* 168 */         i = 31 * i + arrayOfByte[b];
/*     */       }
/* 170 */     } catch (XMLSecurityException xMLSecurityException) {
/* 171 */       if (log.isLoggable(Level.FINE)) {
/* 172 */         log.log(Level.FINE, xMLSecurityException.getMessage(), xMLSecurityException);
/*     */       }
/*     */     } 
/* 175 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 181 */     return "X509SKI";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/x509/XMLX509SKI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */