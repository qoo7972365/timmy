/*     */ package com.sun.org.apache.xml.internal.security.keys.content.x509;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Signature11ElementProxy;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.cert.X509Certificate;
/*     */ import org.w3c.dom.Attr;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLX509Digest
/*     */   extends Signature11ElementProxy
/*     */   implements XMLX509DataContent
/*     */ {
/*     */   public XMLX509Digest(Element paramElement, String paramString) throws XMLSecurityException {
/*  51 */     super(paramElement, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLX509Digest(Document paramDocument, byte[] paramArrayOfbyte, String paramString) {
/*  62 */     super(paramDocument);
/*  63 */     addBase64Text(paramArrayOfbyte);
/*  64 */     this.constructionElement.setAttributeNS(null, "Algorithm", paramString);
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
/*     */   public XMLX509Digest(Document paramDocument, X509Certificate paramX509Certificate, String paramString) throws XMLSecurityException {
/*  76 */     super(paramDocument);
/*  77 */     addBase64Text(getDigestBytesFromCert(paramX509Certificate, paramString));
/*  78 */     this.constructionElement.setAttributeNS(null, "Algorithm", paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attr getAlgorithmAttr() {
/*  87 */     return this.constructionElement.getAttributeNodeNS(null, "Algorithm");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAlgorithm() {
/*  96 */     return getAlgorithmAttr().getNodeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getDigestBytes() throws XMLSecurityException {
/* 106 */     return getBytesFromTextChild();
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
/*     */   public static byte[] getDigestBytesFromCert(X509Certificate paramX509Certificate, String paramString) throws XMLSecurityException {
/* 119 */     String str = JCEMapper.translateURItoJCEID(paramString);
/* 120 */     if (str == null) {
/* 121 */       Object[] arrayOfObject = { paramString };
/* 122 */       throw new XMLSecurityException("XMLX509Digest.UnknownDigestAlgorithm", arrayOfObject);
/*     */     } 
/*     */     
/*     */     try {
/* 126 */       MessageDigest messageDigest = MessageDigest.getInstance(str);
/* 127 */       return messageDigest.digest(paramX509Certificate.getEncoded());
/* 128 */     } catch (Exception exception) {
/* 129 */       Object[] arrayOfObject = { str };
/* 130 */       throw new XMLSecurityException("XMLX509Digest.FailedDigest", arrayOfObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 137 */     return "X509Digest";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/content/x509/XMLX509Digest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */