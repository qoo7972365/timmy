/*     */ package com.sun.org.apache.xpath.internal.jaxp;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.res.XSLMessages;
/*     */ import javax.xml.xpath.XPath;
/*     */ import javax.xml.xpath.XPathFactory;
/*     */ import javax.xml.xpath.XPathFactoryConfigurationException;
/*     */ import javax.xml.xpath.XPathFunctionResolver;
/*     */ import javax.xml.xpath.XPathVariableResolver;
/*     */ import jdk.xml.internal.JdkXmlFeatures;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XPathFactoryImpl
/*     */   extends XPathFactory
/*     */ {
/*     */   private static final String CLASS_NAME = "XPathFactoryImpl";
/*  50 */   private XPathFunctionResolver xPathFunctionResolver = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private XPathVariableResolver xPathVariableResolver = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _isNotSecureProcessing = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _isSecureMode = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final JdkXmlFeatures _featureManager;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XPathFactoryImpl() {
/*  76 */     if (System.getSecurityManager() != null) {
/*  77 */       this._isSecureMode = true;
/*  78 */       this._isNotSecureProcessing = false;
/*     */     } 
/*  80 */     this._featureManager = new JdkXmlFeatures(!this._isNotSecureProcessing);
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
/*     */   
/*     */   public boolean isObjectModelSupported(String objectModel) {
/*  97 */     if (objectModel == null) {
/*  98 */       String fmsg = XSLMessages.createXPATHMessage("ER_OBJECT_MODEL_NULL", new Object[] {
/*     */             
/* 100 */             getClass().getName()
/*     */           });
/* 102 */       throw new NullPointerException(fmsg);
/*     */     } 
/*     */     
/* 105 */     if (objectModel.length() == 0) {
/* 106 */       String fmsg = XSLMessages.createXPATHMessage("ER_OBJECT_MODEL_EMPTY", new Object[] {
/*     */             
/* 108 */             getClass().getName() });
/* 109 */       throw new IllegalArgumentException(fmsg);
/*     */     } 
/*     */ 
/*     */     
/* 113 */     if (objectModel.equals("http://java.sun.com/jaxp/xpath/dom")) {
/* 114 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XPath newXPath() {
/* 128 */     return new XPathImpl(this.xPathVariableResolver, this.xPathFunctionResolver, !this._isNotSecureProcessing, this._featureManager);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(String name, boolean value) throws XPathFactoryConfigurationException {
/* 163 */     if (name == null) {
/* 164 */       String str = XSLMessages.createXPATHMessage("ER_FEATURE_NAME_NULL", new Object[] { "XPathFactoryImpl", new Boolean(value) });
/*     */ 
/*     */       
/* 167 */       throw new NullPointerException(str);
/*     */     } 
/*     */ 
/*     */     
/* 171 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 172 */       if (this._isSecureMode && !value) {
/* 173 */         String str = XSLMessages.createXPATHMessage("ER_SECUREPROCESSING_FEATURE", new Object[] { name, "XPathFactoryImpl", new Boolean(value) });
/*     */ 
/*     */         
/* 176 */         throw new XPathFactoryConfigurationException(str);
/*     */       } 
/*     */       
/* 179 */       this._isNotSecureProcessing = !value;
/* 180 */       if (value && this._featureManager != null) {
/* 181 */         this._featureManager.setFeature(JdkXmlFeatures.XmlFeature.ENABLE_EXTENSION_FUNCTION, JdkXmlFeatures.State.FSP, false);
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 188 */     if (name.equals("http://www.oracle.com/feature/use-service-mechanism"))
/*     */     {
/* 190 */       if (this._isSecureMode) {
/*     */         return;
/*     */       }
/*     */     }
/* 194 */     if (this._featureManager != null && this._featureManager
/* 195 */       .setFeature(name, JdkXmlFeatures.State.APIPROPERTY, Boolean.valueOf(value))) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 200 */     String fmsg = XSLMessages.createXPATHMessage("ER_FEATURE_UNKNOWN", new Object[] { name, "XPathFactoryImpl", 
/*     */           
/* 202 */           Boolean.valueOf(value) });
/* 203 */     throw new XPathFactoryConfigurationException(fmsg);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFeature(String name) throws XPathFactoryConfigurationException {
/* 233 */     if (name == null) {
/* 234 */       String str = XSLMessages.createXPATHMessage("ER_GETTING_NULL_FEATURE", new Object[] { "XPathFactoryImpl" });
/*     */ 
/*     */       
/* 237 */       throw new NullPointerException(str);
/*     */     } 
/*     */ 
/*     */     
/* 241 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 242 */       return !this._isNotSecureProcessing;
/*     */     }
/*     */ 
/*     */     
/* 246 */     int index = this._featureManager.getIndex(name);
/* 247 */     if (index > -1) {
/* 248 */       return this._featureManager.getFeature(index);
/*     */     }
/*     */ 
/*     */     
/* 252 */     String fmsg = XSLMessages.createXPATHMessage("ER_GETTING_UNKNOWN_FEATURE", new Object[] { name, "XPathFactoryImpl" });
/*     */ 
/*     */ 
/*     */     
/* 256 */     throw new XPathFactoryConfigurationException(fmsg);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXPathFunctionResolver(XPathFunctionResolver resolver) {
/* 276 */     if (resolver == null) {
/* 277 */       String fmsg = XSLMessages.createXPATHMessage("ER_NULL_XPATH_FUNCTION_RESOLVER", new Object[] { "XPathFactoryImpl" });
/*     */ 
/*     */       
/* 280 */       throw new NullPointerException(fmsg);
/*     */     } 
/*     */     
/* 283 */     this.xPathFunctionResolver = resolver;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXPathVariableResolver(XPathVariableResolver resolver) {
/* 302 */     if (resolver == null) {
/* 303 */       String fmsg = XSLMessages.createXPATHMessage("ER_NULL_XPATH_VARIABLE_RESOLVER", new Object[] { "XPathFactoryImpl" });
/*     */ 
/*     */       
/* 306 */       throw new NullPointerException(fmsg);
/*     */     } 
/*     */     
/* 309 */     this.xPathVariableResolver = resolver;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/jaxp/XPathFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */