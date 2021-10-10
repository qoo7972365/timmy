/*     */ package com.sun.org.apache.xml.internal.security.transforms.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transform;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.Text;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformBase64Decode
/*     */   extends TransformSpi
/*     */ {
/*     */   public static final String implementedTransformURI = "http://www.w3.org/2000/09/xmldsig#base64";
/*     */   
/*     */   protected String engineGetURI() {
/*  85 */     return "http://www.w3.org/2000/09/xmldsig#base64";
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
/*     */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, Transform paramTransform) throws IOException, CanonicalizationException, TransformationException {
/* 101 */     return enginePerformTransform(paramXMLSignatureInput, null, paramTransform);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws IOException, CanonicalizationException, TransformationException {
/*     */     try {
/* 108 */       if (paramXMLSignatureInput.isElement()) {
/* 109 */         Node node = paramXMLSignatureInput.getSubNode();
/* 110 */         if (paramXMLSignatureInput.getSubNode().getNodeType() == 3) {
/* 111 */           node = node.getParentNode();
/*     */         }
/* 113 */         StringBuilder stringBuilder = new StringBuilder();
/* 114 */         traverseElement((Element)node, stringBuilder);
/* 115 */         if (paramOutputStream == null) {
/* 116 */           byte[] arrayOfByte = Base64.decode(stringBuilder.toString());
/* 117 */           return new XMLSignatureInput(arrayOfByte);
/*     */         } 
/* 119 */         Base64.decode(stringBuilder.toString(), paramOutputStream);
/* 120 */         XMLSignatureInput xMLSignatureInput = new XMLSignatureInput((byte[])null);
/* 121 */         xMLSignatureInput.setOutputStream(paramOutputStream);
/* 122 */         return xMLSignatureInput;
/*     */       } 
/*     */       
/* 125 */       if (paramXMLSignatureInput.isOctetStream() || paramXMLSignatureInput.isNodeSet()) {
/* 126 */         if (paramOutputStream == null) {
/* 127 */           byte[] arrayOfByte1 = paramXMLSignatureInput.getBytes();
/* 128 */           byte[] arrayOfByte2 = Base64.decode(arrayOfByte1);
/* 129 */           return new XMLSignatureInput(arrayOfByte2);
/*     */         } 
/* 131 */         if (paramXMLSignatureInput.isByteArray() || paramXMLSignatureInput.isNodeSet()) {
/* 132 */           Base64.decode(paramXMLSignatureInput.getBytes(), paramOutputStream);
/*     */         } else {
/* 134 */           Base64.decode(new BufferedInputStream(paramXMLSignatureInput.getOctetStreamReal()), paramOutputStream);
/*     */         } 
/* 136 */         XMLSignatureInput xMLSignatureInput = new XMLSignatureInput((byte[])null);
/* 137 */         xMLSignatureInput.setOutputStream(paramOutputStream);
/* 138 */         return xMLSignatureInput;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 144 */         DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 145 */         documentBuilderFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE.booleanValue());
/*     */         
/* 147 */         Document document = documentBuilderFactory.newDocumentBuilder().parse(paramXMLSignatureInput.getOctetStream());
/*     */         
/* 149 */         Element element = document.getDocumentElement();
/* 150 */         StringBuilder stringBuilder = new StringBuilder();
/* 151 */         traverseElement(element, stringBuilder);
/* 152 */         byte[] arrayOfByte = Base64.decode(stringBuilder.toString());
/* 153 */         return new XMLSignatureInput(arrayOfByte);
/* 154 */       } catch (ParserConfigurationException parserConfigurationException) {
/* 155 */         throw new TransformationException("c14n.Canonicalizer.Exception", parserConfigurationException);
/* 156 */       } catch (SAXException sAXException) {
/* 157 */         throw new TransformationException("SAX exception", sAXException);
/*     */       } 
/* 159 */     } catch (Base64DecodingException base64DecodingException) {
/* 160 */       throw new TransformationException("Base64Decoding", base64DecodingException);
/*     */     } 
/*     */   }
/*     */   
/*     */   void traverseElement(Element paramElement, StringBuilder paramStringBuilder) {
/* 165 */     Node node = paramElement.getFirstChild();
/* 166 */     while (node != null) {
/* 167 */       switch (node.getNodeType()) {
/*     */         case 1:
/* 169 */           traverseElement((Element)node, paramStringBuilder);
/*     */           break;
/*     */         case 3:
/* 172 */           paramStringBuilder.append(((Text)node).getData()); break;
/*     */       } 
/* 174 */       node = node.getNextSibling();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/implementations/TransformBase64Decode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */