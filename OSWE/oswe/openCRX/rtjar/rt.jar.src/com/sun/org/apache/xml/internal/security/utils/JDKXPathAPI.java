/*     */ package com.sun.org.apache.xml.internal.security.utils;
/*     */ 
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.xpath.XPath;
/*     */ import javax.xml.xpath.XPathConstants;
/*     */ import javax.xml.xpath.XPathExpression;
/*     */ import javax.xml.xpath.XPathExpressionException;
/*     */ import javax.xml.xpath.XPathFactory;
/*     */ import javax.xml.xpath.XPathFactoryConfigurationException;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDKXPathAPI
/*     */   implements XPathAPI
/*     */ {
/*     */   private XPathFactory xpf;
/*     */   private String xpathStr;
/*     */   private XPathExpression xpathExpression;
/*     */   
/*     */   public NodeList selectNodeList(Node paramNode1, Node paramNode2, String paramString, Node paramNode3) throws TransformerException {
/*  63 */     if (!paramString.equals(this.xpathStr) || this.xpathExpression == null) {
/*  64 */       if (this.xpf == null) {
/*  65 */         this.xpf = XPathFactory.newInstance();
/*     */         try {
/*  67 */           this.xpf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE.booleanValue());
/*  68 */         } catch (XPathFactoryConfigurationException xPathFactoryConfigurationException) {
/*  69 */           throw new TransformerException("empty", xPathFactoryConfigurationException);
/*     */         } 
/*     */       } 
/*  72 */       XPath xPath = this.xpf.newXPath();
/*  73 */       xPath.setNamespaceContext(new DOMNamespaceContext(paramNode3));
/*  74 */       this.xpathStr = paramString;
/*     */       try {
/*  76 */         this.xpathExpression = xPath.compile(this.xpathStr);
/*  77 */       } catch (XPathExpressionException xPathExpressionException) {
/*  78 */         throw new TransformerException("empty", xPathExpressionException);
/*     */       } 
/*     */     } 
/*     */     try {
/*  82 */       return (NodeList)this.xpathExpression.evaluate(paramNode1, XPathConstants.NODESET);
/*  83 */     } catch (XPathExpressionException xPathExpressionException) {
/*  84 */       throw new TransformerException("empty", xPathExpressionException);
/*     */     } 
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
/*     */   public boolean evaluate(Node paramNode1, Node paramNode2, String paramString, Node paramNode3) throws TransformerException {
/*  97 */     if (!paramString.equals(this.xpathStr) || this.xpathExpression == null) {
/*  98 */       if (this.xpf == null) {
/*  99 */         this.xpf = XPathFactory.newInstance();
/*     */         try {
/* 101 */           this.xpf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE.booleanValue());
/* 102 */         } catch (XPathFactoryConfigurationException xPathFactoryConfigurationException) {
/* 103 */           throw new TransformerException("empty", xPathFactoryConfigurationException);
/*     */         } 
/*     */       } 
/* 106 */       XPath xPath = this.xpf.newXPath();
/* 107 */       xPath.setNamespaceContext(new DOMNamespaceContext(paramNode3));
/* 108 */       this.xpathStr = paramString;
/*     */       try {
/* 110 */         this.xpathExpression = xPath.compile(this.xpathStr);
/* 111 */       } catch (XPathExpressionException xPathExpressionException) {
/* 112 */         throw new TransformerException("empty", xPathExpressionException);
/*     */       } 
/*     */     } 
/*     */     try {
/* 116 */       Boolean bool = (Boolean)this.xpathExpression.evaluate(paramNode1, XPathConstants.BOOLEAN);
/* 117 */       return bool.booleanValue();
/* 118 */     } catch (XPathExpressionException xPathExpressionException) {
/* 119 */       throw new TransformerException("empty", xPathExpressionException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 127 */     this.xpathStr = null;
/* 128 */     this.xpathExpression = null;
/* 129 */     this.xpf = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/JDKXPathAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */