/*     */ package com.sun.org.apache.xml.internal.security.transforms.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transform;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.params.XPath2FilterContainer;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XPathAPI;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XPathFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ public class TransformXPath2Filter
/*     */   extends TransformSpi
/*     */ {
/*     */   public static final String implementedTransformURI = "http://www.w3.org/2002/06/xmldsig-filter2";
/*     */   
/*     */   protected String engineGetURI() {
/*  72 */     return "http://www.w3.org/2002/06/xmldsig-filter2";
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
/*     */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws TransformationException {
/*     */     try {
/*  86 */       ArrayList<NodeList> arrayList1 = new ArrayList();
/*  87 */       ArrayList<NodeList> arrayList2 = new ArrayList();
/*  88 */       ArrayList<NodeList> arrayList3 = new ArrayList();
/*     */ 
/*     */       
/*  91 */       Element[] arrayOfElement = XMLUtils.selectNodes(paramTransform
/*  92 */           .getElement().getFirstChild(), "http://www.w3.org/2002/06/xmldsig-filter2", "XPath");
/*     */ 
/*     */ 
/*     */       
/*  96 */       if (arrayOfElement.length == 0) {
/*  97 */         Object[] arrayOfObject = { "http://www.w3.org/2002/06/xmldsig-filter2", "XPath" };
/*     */         
/*  99 */         throw new TransformationException("xml.WrongContent", arrayOfObject);
/*     */       } 
/*     */       
/* 102 */       Document document = null;
/* 103 */       if (paramXMLSignatureInput.getSubNode() != null) {
/* 104 */         document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getSubNode());
/*     */       } else {
/* 106 */         document = XMLUtils.getOwnerDocument(paramXMLSignatureInput.getNodeSet());
/*     */       } 
/*     */       
/* 109 */       for (byte b = 0; b < arrayOfElement.length; b++) {
/* 110 */         Element element = arrayOfElement[b];
/*     */ 
/*     */         
/* 113 */         XPath2FilterContainer xPath2FilterContainer = XPath2FilterContainer.newInstance(element, paramXMLSignatureInput.getSourceURI());
/*     */ 
/*     */         
/* 116 */         String str = XMLUtils.getStrFromNode(xPath2FilterContainer.getXPathFilterTextNode());
/*     */         
/* 118 */         XPathFactory xPathFactory = XPathFactory.newInstance();
/* 119 */         XPathAPI xPathAPI = xPathFactory.newXPathAPI();
/*     */ 
/*     */         
/* 122 */         NodeList nodeList = xPathAPI.selectNodeList(document, xPath2FilterContainer
/*     */             
/* 124 */             .getXPathFilterTextNode(), str, xPath2FilterContainer
/*     */             
/* 126 */             .getElement());
/* 127 */         if (xPath2FilterContainer.isIntersect()) {
/* 128 */           arrayList3.add(nodeList);
/* 129 */         } else if (xPath2FilterContainer.isSubtract()) {
/* 130 */           arrayList2.add(nodeList);
/* 131 */         } else if (xPath2FilterContainer.isUnion()) {
/* 132 */           arrayList1.add(nodeList);
/*     */         } 
/*     */       } 
/*     */       
/* 136 */       paramXMLSignatureInput.addNodeFilter(new XPath2NodeFilter(arrayList1, arrayList2, arrayList3));
/*     */ 
/*     */       
/* 139 */       paramXMLSignatureInput.setNodeSet(true);
/* 140 */       return paramXMLSignatureInput;
/* 141 */     } catch (TransformerException transformerException) {
/* 142 */       throw new TransformationException("empty", transformerException);
/* 143 */     } catch (DOMException dOMException) {
/* 144 */       throw new TransformationException("empty", dOMException);
/* 145 */     } catch (CanonicalizationException canonicalizationException) {
/* 146 */       throw new TransformationException("empty", canonicalizationException);
/* 147 */     } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
/* 148 */       throw new TransformationException("empty", invalidCanonicalizerException);
/* 149 */     } catch (XMLSecurityException xMLSecurityException) {
/* 150 */       throw new TransformationException("empty", xMLSecurityException);
/* 151 */     } catch (SAXException sAXException) {
/* 152 */       throw new TransformationException("empty", sAXException);
/* 153 */     } catch (IOException iOException) {
/* 154 */       throw new TransformationException("empty", iOException);
/* 155 */     } catch (ParserConfigurationException parserConfigurationException) {
/* 156 */       throw new TransformationException("empty", parserConfigurationException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/implementations/TransformXPath2Filter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */