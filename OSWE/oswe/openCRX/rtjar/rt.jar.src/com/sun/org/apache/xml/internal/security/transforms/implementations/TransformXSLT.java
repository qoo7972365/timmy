/*     */ package com.sun.org.apache.xml.internal.security.transforms.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transform;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformXSLT
/*     */   extends TransformSpi
/*     */ {
/*     */   public static final String implementedTransformURI = "http://www.w3.org/TR/1999/REC-xslt-19991116";
/*     */   static final String XSLTSpecNS = "http://www.w3.org/1999/XSL/Transform";
/*     */   static final String defaultXSLTSpecNSprefix = "xslt";
/*     */   static final String XSLTSTYLESHEET = "stylesheet";
/*  68 */   private static Logger log = Logger.getLogger(TransformXSLT.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String engineGetURI() {
/*  76 */     return "http://www.w3.org/TR/1999/REC-xslt-19991116";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws IOException, TransformationException {
/*     */     try {
/*  83 */       Element element1 = paramTransform.getElement();
/*     */ 
/*     */       
/*  86 */       Element element2 = XMLUtils.selectNode(element1.getFirstChild(), "http://www.w3.org/1999/XSL/Transform", "stylesheet", 0);
/*     */       
/*  88 */       if (element2 == null) {
/*  89 */         Object[] arrayOfObject = { "xslt:stylesheet", "Transform" };
/*     */         
/*  91 */         throw new TransformationException("xml.WrongContent", arrayOfObject);
/*     */       } 
/*     */       
/*  94 */       TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*     */       
/*  96 */       transformerFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE.booleanValue());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 105 */       StreamSource streamSource1 = new StreamSource(new ByteArrayInputStream(paramXMLSignatureInput.getBytes()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 117 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 118 */       Transformer transformer2 = transformerFactory.newTransformer();
/* 119 */       DOMSource dOMSource = new DOMSource(element2);
/* 120 */       StreamResult streamResult2 = new StreamResult(byteArrayOutputStream);
/*     */       
/* 122 */       transformer2.transform(dOMSource, streamResult2);
/*     */ 
/*     */       
/* 125 */       StreamSource streamSource2 = new StreamSource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
/*     */ 
/*     */       
/* 128 */       Transformer transformer1 = transformerFactory.newTransformer(streamSource2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 136 */         transformer1.setOutputProperty("{http://xml.apache.org/xalan}line-separator", "\n");
/* 137 */       } catch (Exception exception) {
/* 138 */         log.log(Level.WARNING, "Unable to set Xalan line-separator property: " + exception.getMessage());
/*     */       } 
/*     */       
/* 141 */       if (paramOutputStream == null) {
/* 142 */         ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
/* 143 */         StreamResult streamResult = new StreamResult(byteArrayOutputStream1);
/* 144 */         transformer1.transform(streamSource1, streamResult);
/* 145 */         return new XMLSignatureInput(byteArrayOutputStream1.toByteArray());
/*     */       } 
/* 147 */       StreamResult streamResult1 = new StreamResult(paramOutputStream);
/*     */       
/* 149 */       transformer1.transform(streamSource1, streamResult1);
/* 150 */       XMLSignatureInput xMLSignatureInput = new XMLSignatureInput((byte[])null);
/* 151 */       xMLSignatureInput.setOutputStream(paramOutputStream);
/* 152 */       return xMLSignatureInput;
/* 153 */     } catch (XMLSecurityException xMLSecurityException) {
/* 154 */       Object[] arrayOfObject = { xMLSecurityException.getMessage() };
/*     */       
/* 156 */       throw new TransformationException("generic.EmptyMessage", arrayOfObject, xMLSecurityException);
/* 157 */     } catch (TransformerConfigurationException transformerConfigurationException) {
/* 158 */       Object[] arrayOfObject = { transformerConfigurationException.getMessage() };
/*     */       
/* 160 */       throw new TransformationException("generic.EmptyMessage", arrayOfObject, transformerConfigurationException);
/* 161 */     } catch (TransformerException transformerException) {
/* 162 */       Object[] arrayOfObject = { transformerException.getMessage() };
/*     */       
/* 164 */       throw new TransformationException("generic.EmptyMessage", arrayOfObject, transformerException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/implementations/TransformXSLT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */