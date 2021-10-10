/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DOMX509IssuerSerial
/*     */   extends DOMStructure
/*     */   implements X509IssuerSerial
/*     */ {
/*     */   private final String issuerName;
/*     */   private final BigInteger serialNumber;
/*     */   
/*     */   public DOMX509IssuerSerial(String paramString, BigInteger paramBigInteger) {
/*  66 */     if (paramString == null) {
/*  67 */       throw new NullPointerException("issuerName cannot be null");
/*     */     }
/*  69 */     if (paramBigInteger == null) {
/*  70 */       throw new NullPointerException("serialNumber cannot be null");
/*     */     }
/*     */     
/*  73 */     new X500Principal(paramString);
/*  74 */     this.issuerName = paramString;
/*  75 */     this.serialNumber = paramBigInteger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMX509IssuerSerial(Element paramElement) throws MarshalException {
/*  84 */     Element element1 = DOMUtils.getFirstChildElement(paramElement, "X509IssuerName");
/*     */     
/*  86 */     Element element2 = DOMUtils.getNextSiblingElement(element1, "X509SerialNumber");
/*     */     
/*  88 */     this.issuerName = element1.getFirstChild().getNodeValue();
/*  89 */     this.serialNumber = new BigInteger(element2.getFirstChild().getNodeValue());
/*     */   }
/*     */   
/*     */   public String getIssuerName() {
/*  93 */     return this.issuerName;
/*     */   }
/*     */   
/*     */   public BigInteger getSerialNumber() {
/*  97 */     return this.serialNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 103 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/*     */     
/* 105 */     Element element1 = DOMUtils.createElement(document, "X509IssuerSerial", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     
/* 107 */     Element element2 = DOMUtils.createElement(document, "X509IssuerName", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     
/* 109 */     Element element3 = DOMUtils.createElement(document, "X509SerialNumber", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     
/* 111 */     element2.appendChild(document.createTextNode(this.issuerName));
/* 112 */     element3.appendChild(document.createTextNode(this.serialNumber.toString()));
/* 113 */     element1.appendChild(element2);
/* 114 */     element1.appendChild(element3);
/* 115 */     paramNode.appendChild(element1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 120 */     if (this == paramObject) {
/* 121 */       return true;
/*     */     }
/* 123 */     if (!(paramObject instanceof X509IssuerSerial)) {
/* 124 */       return false;
/*     */     }
/* 126 */     X509IssuerSerial x509IssuerSerial = (X509IssuerSerial)paramObject;
/* 127 */     return (this.issuerName.equals(x509IssuerSerial.getIssuerName()) && this.serialNumber
/* 128 */       .equals(x509IssuerSerial.getSerialNumber()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 133 */     int i = 17;
/* 134 */     i = 31 * i + this.issuerName.hashCode();
/* 135 */     i = 31 * i + this.serialNumber.hashCode();
/*     */     
/* 137 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMX509IssuerSerial.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */