/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.KeyException;
/*     */ import java.security.PublicKey;
/*     */ import java.util.List;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMStructure;
/*     */ import javax.xml.crypto.dsig.Transform;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyName;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyValue;
/*     */ import javax.xml.crypto.dsig.keyinfo.PGPData;
/*     */ import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
/*     */ import javax.xml.crypto.dsig.keyinfo.X509Data;
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
/*     */ public final class DOMKeyInfoFactory
/*     */   extends KeyInfoFactory
/*     */ {
/*     */   public KeyInfo newKeyInfo(List paramList) {
/*  52 */     return newKeyInfo(paramList, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyInfo newKeyInfo(List<? extends XMLStructure> paramList, String paramString) {
/*  57 */     return new DOMKeyInfo(paramList, paramString);
/*     */   }
/*     */   
/*     */   public KeyName newKeyName(String paramString) {
/*  61 */     return new DOMKeyName(paramString);
/*     */   }
/*     */   
/*     */   public KeyValue newKeyValue(PublicKey paramPublicKey) throws KeyException {
/*  65 */     String str = paramPublicKey.getAlgorithm();
/*  66 */     if (str.equals("DSA"))
/*  67 */       return new DOMKeyValue.DSA(paramPublicKey); 
/*  68 */     if (str.equals("RSA"))
/*  69 */       return new DOMKeyValue.RSA(paramPublicKey); 
/*  70 */     if (str.equals("EC")) {
/*  71 */       return new DOMKeyValue.EC(paramPublicKey);
/*     */     }
/*  73 */     throw new KeyException("unsupported key algorithm: " + str);
/*     */   }
/*     */ 
/*     */   
/*     */   public PGPData newPGPData(byte[] paramArrayOfbyte) {
/*  78 */     return newPGPData(paramArrayOfbyte, null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public PGPData newPGPData(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, List<? extends XMLStructure> paramList) {
/*  83 */     return new DOMPGPData(paramArrayOfbyte1, paramArrayOfbyte2, paramList);
/*     */   }
/*     */ 
/*     */   
/*     */   public PGPData newPGPData(byte[] paramArrayOfbyte, List<? extends XMLStructure> paramList) {
/*  88 */     return new DOMPGPData(paramArrayOfbyte, paramList);
/*     */   }
/*     */   
/*     */   public RetrievalMethod newRetrievalMethod(String paramString) {
/*  92 */     return newRetrievalMethod(paramString, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RetrievalMethod newRetrievalMethod(String paramString1, String paramString2, List<? extends Transform> paramList) {
/*  98 */     if (paramString1 == null) {
/*  99 */       throw new NullPointerException("uri must not be null");
/*     */     }
/* 101 */     return new DOMRetrievalMethod(paramString1, paramString2, paramList);
/*     */   }
/*     */ 
/*     */   
/*     */   public X509Data newX509Data(List<?> paramList) {
/* 106 */     return new DOMX509Data(paramList);
/*     */   }
/*     */ 
/*     */   
/*     */   public X509IssuerSerial newX509IssuerSerial(String paramString, BigInteger paramBigInteger) {
/* 111 */     return new DOMX509IssuerSerial(paramString, paramBigInteger);
/*     */   }
/*     */   
/*     */   public boolean isFeatureSupported(String paramString) {
/* 115 */     if (paramString == null) {
/* 116 */       throw new NullPointerException();
/*     */     }
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public URIDereferencer getURIDereferencer() {
/* 123 */     return DOMURIDereferencer.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyInfo unmarshalKeyInfo(XMLStructure paramXMLStructure) throws MarshalException {
/* 128 */     if (paramXMLStructure == null) {
/* 129 */       throw new NullPointerException("xmlStructure cannot be null");
/*     */     }
/* 131 */     if (!(paramXMLStructure instanceof DOMStructure)) {
/* 132 */       throw new ClassCastException("xmlStructure must be of type DOMStructure");
/*     */     }
/*     */     
/* 135 */     Node node = ((DOMStructure)paramXMLStructure).getNode();
/* 136 */     node.normalize();
/*     */     
/* 138 */     Element element = null;
/* 139 */     if (node.getNodeType() == 9) {
/* 140 */       element = ((Document)node).getDocumentElement();
/* 141 */     } else if (node.getNodeType() == 1) {
/* 142 */       element = (Element)node;
/*     */     } else {
/* 144 */       throw new MarshalException("xmlStructure does not contain a proper Node");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 149 */     String str = element.getLocalName();
/* 150 */     if (str == null) {
/* 151 */       throw new MarshalException("Document implementation must support DOM Level 2 and be namespace aware");
/*     */     }
/*     */     
/* 154 */     if (str.equals("KeyInfo")) {
/* 155 */       return new DOMKeyInfo(element, new UnmarshalContext(), getProvider());
/*     */     }
/* 157 */     throw new MarshalException("invalid KeyInfo tag: " + str);
/*     */   }
/*     */   
/*     */   private static class UnmarshalContext extends DOMCryptoContext {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMKeyInfoFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */