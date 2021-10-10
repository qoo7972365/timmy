/*     */ package com.sun.org.apache.xerces.internal.jaxp.validation;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*     */ import java.io.IOException;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactoryConfigurationError;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import javax.xml.transform.sax.SAXTransformerFactory;
/*     */ import javax.xml.transform.sax.TransformerHandler;
/*     */ import jdk.xml.internal.JdkXmlUtils;
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
/*     */ public final class StAXValidatorHelper
/*     */   implements ValidatorHelper
/*     */ {
/*     */   private XMLSchemaValidatorComponentManager fComponentManager;
/*  59 */   private Transformer identityTransformer1 = null;
/*  60 */   private TransformerHandler identityTransformer2 = null;
/*  61 */   private ValidatorHandlerImpl handler = null;
/*     */ 
/*     */   
/*     */   public StAXValidatorHelper(XMLSchemaValidatorComponentManager componentManager) {
/*  65 */     this.fComponentManager = componentManager;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(Source source, Result result) throws SAXException, IOException {
/*  71 */     if (result == null || result instanceof javax.xml.transform.stax.StAXResult) {
/*     */       
/*  73 */       if (this.identityTransformer1 == null) {
/*     */         try {
/*  75 */           SAXTransformerFactory tf = JdkXmlUtils.getSAXTransformFactory(this.fComponentManager
/*  76 */               .getFeature("jdk.xml.overrideDefaultParser"));
/*     */ 
/*     */           
/*  79 */           XMLSecurityManager securityManager = (XMLSecurityManager)this.fComponentManager.getProperty("http://apache.org/xml/properties/security-manager");
/*  80 */           if (securityManager != null) {
/*  81 */             for (XMLSecurityManager.Limit limit : XMLSecurityManager.Limit.values()) {
/*  82 */               if (securityManager.isSet(limit.ordinal())) {
/*  83 */                 tf.setAttribute(limit.apiProperty(), securityManager
/*  84 */                     .getLimitValueAsString(limit));
/*     */               }
/*     */             } 
/*  87 */             if (securityManager.printEntityCountInfo()) {
/*  88 */               tf.setAttribute("http://www.oracle.com/xml/jaxp/properties/getEntityCountInfo", "yes");
/*     */             }
/*     */           } 
/*     */           
/*  92 */           this.identityTransformer1 = tf.newTransformer();
/*  93 */           this.identityTransformer2 = tf.newTransformerHandler();
/*  94 */         } catch (TransformerConfigurationException e) {
/*     */           
/*  96 */           throw new TransformerFactoryConfigurationError(e);
/*     */         } 
/*     */       }
/*     */       
/* 100 */       this.handler = new ValidatorHandlerImpl(this.fComponentManager);
/* 101 */       if (result != null) {
/* 102 */         this.handler.setContentHandler(this.identityTransformer2);
/* 103 */         this.identityTransformer2.setResult(result);
/*     */       } 
/*     */       
/*     */       try {
/* 107 */         this.identityTransformer1.transform(source, new SAXResult(this.handler));
/* 108 */       } catch (TransformerException e) {
/* 109 */         if (e.getException() instanceof SAXException)
/* 110 */           throw (SAXException)e.getException(); 
/* 111 */         throw new SAXException(e);
/*     */       } finally {
/* 113 */         this.handler.setContentHandler(null);
/*     */       } 
/*     */       return;
/*     */     } 
/* 117 */     throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceResultMismatch", new Object[] { source
/*     */             
/* 119 */             .getClass().getName(), result.getClass().getName() }));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/validation/StAXValidatorHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */