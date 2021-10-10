/*     */ package com.sun.org.apache.xml.internal.security.encryption;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentFragment;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.InputSource;
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
/*     */ public class DocumentSerializer
/*     */   extends AbstractSerializer
/*     */ {
/*     */   protected DocumentBuilderFactory dbf;
/*     */   
/*     */   public Node deserialize(byte[] paramArrayOfbyte, Node paramNode) throws XMLEncryptionException {
/*  55 */     byte[] arrayOfByte = createContext(paramArrayOfbyte, paramNode);
/*  56 */     return deserialize(paramNode, new InputSource(new ByteArrayInputStream(arrayOfByte)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node deserialize(String paramString, Node paramNode) throws XMLEncryptionException {
/*  66 */     String str = createContext(paramString, paramNode);
/*  67 */     return deserialize(paramNode, new InputSource(new StringReader(str)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Node deserialize(Node paramNode, InputSource paramInputSource) throws XMLEncryptionException {
/*     */     try {
/*  78 */       if (this.dbf == null) {
/*  79 */         this.dbf = DocumentBuilderFactory.newInstance();
/*  80 */         this.dbf.setNamespaceAware(true);
/*  81 */         this.dbf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE.booleanValue());
/*  82 */         this.dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);
/*  83 */         this.dbf.setValidating(false);
/*     */       } 
/*  85 */       DocumentBuilder documentBuilder = this.dbf.newDocumentBuilder();
/*  86 */       Document document1 = documentBuilder.parse(paramInputSource);
/*     */       
/*  88 */       Document document2 = null;
/*  89 */       if (9 == paramNode.getNodeType()) {
/*  90 */         document2 = (Document)paramNode;
/*     */       } else {
/*  92 */         document2 = paramNode.getOwnerDocument();
/*     */       } 
/*     */ 
/*     */       
/*  96 */       Element element = (Element)document2.importNode(document1.getDocumentElement(), true);
/*  97 */       DocumentFragment documentFragment = document2.createDocumentFragment();
/*  98 */       Node node = element.getFirstChild();
/*  99 */       while (node != null) {
/* 100 */         element.removeChild(node);
/* 101 */         documentFragment.appendChild(node);
/* 102 */         node = element.getFirstChild();
/*     */       } 
/* 104 */       return documentFragment;
/* 105 */     } catch (SAXException sAXException) {
/* 106 */       throw new XMLEncryptionException("empty", sAXException);
/* 107 */     } catch (ParserConfigurationException parserConfigurationException) {
/* 108 */       throw new XMLEncryptionException("empty", parserConfigurationException);
/* 109 */     } catch (IOException iOException) {
/* 110 */       throw new XMLEncryptionException("empty", iOException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/encryption/DocumentSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */