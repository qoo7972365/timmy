/*     */ package com.sun.org.apache.xml.internal.security.keys.content.x509;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.RFC2253Parser;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.math.BigInteger;
/*     */ import java.security.cert.X509Certificate;
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
/*     */ public class XMLX509IssuerSerial
/*     */   extends SignatureElementProxy
/*     */   implements XMLX509DataContent
/*     */ {
/*  40 */   private static Logger log = Logger.getLogger(XMLX509IssuerSerial.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLX509IssuerSerial(Element paramElement, String paramString) throws XMLSecurityException {
/*  50 */     super(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLX509IssuerSerial(Document paramDocument, String paramString, BigInteger paramBigInteger) {
/*  61 */     super(paramDocument);
/*  62 */     XMLUtils.addReturnToElement(this.constructionElement);
/*  63 */     addTextElement(paramString, "X509IssuerName");
/*  64 */     addTextElement(paramBigInteger.toString(), "X509SerialNumber");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLX509IssuerSerial(Document paramDocument, String paramString1, String paramString2) {
/*  75 */     this(paramDocument, paramString1, new BigInteger(paramString2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLX509IssuerSerial(Document paramDocument, String paramString, int paramInt) {
/*  86 */     this(paramDocument, paramString, new BigInteger(Integer.toString(paramInt)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLX509IssuerSerial(Document paramDocument, X509Certificate paramX509Certificate) {
/*  96 */     this(paramDocument, paramX509Certificate
/*     */         
/*  98 */         .getIssuerX500Principal().getName(), paramX509Certificate
/*  99 */         .getSerialNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getSerialNumber() {
/* 110 */     String str = getTextFromChildElement("X509SerialNumber", "http://www.w3.org/2000/09/xmldsig#");
/* 111 */     if (log.isLoggable(Level.FINE)) {
/* 112 */       log.log(Level.FINE, "X509SerialNumber text: " + str);
/*     */     }
/*     */     
/* 115 */     return new BigInteger(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSerialNumberInteger() {
/* 124 */     return getSerialNumber().intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIssuerName() {
/* 133 */     return RFC2253Parser.normalize(
/* 134 */         getTextFromChildElement("X509IssuerName", "http://www.w3.org/2000/09/xmldsig#"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 140 */     if (!(paramObject instanceof XMLX509IssuerSerial)) {
/* 141 */       return false;
/*     */     }
/*     */     
/* 144 */     XMLX509IssuerSerial xMLX509IssuerSerial = (XMLX509IssuerSerial)paramObject;
/*     */     
/* 146 */     return (getSerialNumber().equals(xMLX509IssuerSerial.getSerialNumber()) && 
/* 147 */       getIssuerName().equals(xMLX509IssuerSerial.getIssuerName()));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 151 */     int i = 17;
/* 152 */     i = 31 * i + getSerialNumber().hashCode();
/* 153 */     i = 31 * i + getIssuerName().hashCode();
/* 154 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 159 */     return "X509IssuerSerial";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/x509/XMLX509IssuerSerial.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */