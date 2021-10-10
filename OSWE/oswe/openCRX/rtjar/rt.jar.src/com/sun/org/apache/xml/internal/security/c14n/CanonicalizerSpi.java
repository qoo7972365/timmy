/*     */ package com.sun.org.apache.xml.internal.security.c14n;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Set;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CanonicalizerSpi
/*     */ {
/*     */   protected boolean reset = false;
/*     */   
/*     */   public byte[] engineCanonicalize(byte[] paramArrayOfbyte) throws ParserConfigurationException, IOException, SAXException, CanonicalizationException {
/*  64 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
/*  65 */     InputSource inputSource = new InputSource(byteArrayInputStream);
/*  66 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  67 */     documentBuilderFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE.booleanValue());
/*     */ 
/*     */     
/*  70 */     documentBuilderFactory.setNamespaceAware(true);
/*     */     
/*  72 */     DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*     */     
/*  74 */     Document document = documentBuilder.parse(inputSource);
/*  75 */     return engineCanonicalizeSubTree(document);
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
/*     */   public byte[] engineCanonicalizeXPathNodeSet(NodeList paramNodeList) throws CanonicalizationException {
/*  87 */     return engineCanonicalizeXPathNodeSet(
/*  88 */         XMLUtils.convertNodelistToSet(paramNodeList));
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
/*     */   public byte[] engineCanonicalizeXPathNodeSet(NodeList paramNodeList, String paramString) throws CanonicalizationException {
/* 102 */     return engineCanonicalizeXPathNodeSet(
/* 103 */         XMLUtils.convertNodelistToSet(paramNodeList), paramString);
/*     */   }
/*     */   
/*     */   public abstract String engineGetURI();
/*     */   
/*     */   public abstract boolean engineGetIncludeComments();
/*     */   
/*     */   public abstract byte[] engineCanonicalizeXPathNodeSet(Set<Node> paramSet) throws CanonicalizationException;
/*     */   
/*     */   public abstract byte[] engineCanonicalizeXPathNodeSet(Set<Node> paramSet, String paramString) throws CanonicalizationException;
/*     */   
/*     */   public abstract byte[] engineCanonicalizeSubTree(Node paramNode) throws CanonicalizationException;
/*     */   
/*     */   public abstract byte[] engineCanonicalizeSubTree(Node paramNode, String paramString) throws CanonicalizationException;
/*     */   
/*     */   public abstract void setWriter(OutputStream paramOutputStream);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/CanonicalizerSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */