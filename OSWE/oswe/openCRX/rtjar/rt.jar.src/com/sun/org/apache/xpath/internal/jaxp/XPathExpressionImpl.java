/*     */ package com.sun.org.apache.xpath.internal.jaxp;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.res.XSLMessages;
/*     */ import com.sun.org.apache.xpath.internal.XPath;
/*     */ import com.sun.org.apache.xpath.internal.XPathContext;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.xpath.XPathConstants;
/*     */ import javax.xml.xpath.XPathExpression;
/*     */ import javax.xml.xpath.XPathExpressionException;
/*     */ import javax.xml.xpath.XPathFunctionException;
/*     */ import javax.xml.xpath.XPathFunctionResolver;
/*     */ import javax.xml.xpath.XPathVariableResolver;
/*     */ import jdk.xml.internal.JdkXmlFeatures;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.traversal.NodeIterator;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XPathExpressionImpl
/*     */   implements XPathExpression
/*     */ {
/*     */   private XPathFunctionResolver functionResolver;
/*     */   private XPathVariableResolver variableResolver;
/*     */   private JAXPPrefixResolver prefixResolver;
/*     */   private XPath xpath;
/*     */   private boolean featureSecureProcessing = false;
/*     */   boolean overrideDefaultParser;
/*     */   private final JdkXmlFeatures featureManager;
/*     */   
/*     */   protected XPathExpressionImpl() {
/*  69 */     this(null, null, null, null, false, new JdkXmlFeatures(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XPathExpressionImpl(XPath xpath, JAXPPrefixResolver prefixResolver, XPathFunctionResolver functionResolver, XPathVariableResolver variableResolver) {
/*  76 */     this(xpath, prefixResolver, functionResolver, variableResolver, false, new JdkXmlFeatures(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XPathExpressionImpl(XPath xpath, JAXPPrefixResolver prefixResolver, XPathFunctionResolver functionResolver, XPathVariableResolver variableResolver, boolean featureSecureProcessing, JdkXmlFeatures featureManager) {
/*  84 */     this.xpath = xpath;
/*  85 */     this.prefixResolver = prefixResolver;
/*  86 */     this.functionResolver = functionResolver;
/*  87 */     this.variableResolver = variableResolver;
/*  88 */     this.featureSecureProcessing = featureSecureProcessing;
/*  89 */     this.featureManager = featureManager;
/*  90 */     this.overrideDefaultParser = featureManager.getFeature(JdkXmlFeatures.XmlFeature.JDK_OVERRIDE_PARSER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXPath(XPath xpath) {
/*  95 */     this.xpath = xpath;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object eval(Object item, QName returnType) throws TransformerException {
/* 100 */     XObject resultObject = eval(item);
/* 101 */     return getResultAsType(resultObject, returnType);
/*     */   }
/*     */ 
/*     */   
/*     */   private XObject eval(Object contextItem) throws TransformerException {
/* 106 */     XPathContext xpathSupport = null;
/* 107 */     if (this.functionResolver != null) {
/* 108 */       JAXPExtensionsProvider jep = new JAXPExtensionsProvider(this.functionResolver, this.featureSecureProcessing, this.featureManager);
/*     */       
/* 110 */       xpathSupport = new XPathContext(jep);
/*     */     } else {
/* 112 */       xpathSupport = new XPathContext();
/*     */     } 
/*     */     
/* 115 */     xpathSupport.setVarStack(new JAXPVariableStack(this.variableResolver));
/* 116 */     XObject xobj = null;
/*     */     
/* 118 */     Node contextNode = (Node)contextItem;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     if (contextNode == null) {
/* 124 */       xobj = this.xpath.execute(xpathSupport, -1, this.prefixResolver);
/*     */     } else {
/* 126 */       xobj = this.xpath.execute(xpathSupport, contextNode, this.prefixResolver);
/*     */     } 
/* 128 */     return xobj;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Object evaluate(Object item, QName returnType) throws XPathExpressionException {
/* 166 */     if (returnType == null) {
/*     */       
/* 168 */       String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "returnType" });
/*     */ 
/*     */       
/* 171 */       throw new NullPointerException(fmsg);
/*     */     } 
/*     */ 
/*     */     
/* 175 */     if (!isSupported(returnType)) {
/* 176 */       String fmsg = XSLMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[] { returnType
/*     */             
/* 178 */             .toString() });
/* 179 */       throw new IllegalArgumentException(fmsg);
/*     */     } 
/*     */     try {
/* 182 */       return eval(item, returnType);
/* 183 */     } catch (NullPointerException npe) {
/*     */ 
/*     */ 
/*     */       
/* 187 */       throw new XPathExpressionException(npe);
/* 188 */     } catch (TransformerException te) {
/* 189 */       Throwable nestedException = te.getException();
/* 190 */       if (nestedException instanceof XPathFunctionException) {
/* 191 */         throw (XPathFunctionException)nestedException;
/*     */       }
/*     */ 
/*     */       
/* 195 */       throw new XPathExpressionException(te);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String evaluate(Object item) throws XPathExpressionException {
/* 227 */     return (String)evaluate(item, XPathConstants.STRING);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 232 */   static DocumentBuilderFactory dbf = null;
/* 233 */   static DocumentBuilder db = null;
/* 234 */   static Document d = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object evaluate(InputSource source, QName returnType) throws XPathExpressionException {
/* 272 */     if (source == null || returnType == null) {
/* 273 */       String fmsg = XSLMessages.createXPATHMessage("ER_SOURCE_RETURN_TYPE_CANNOT_BE_NULL", null);
/*     */ 
/*     */       
/* 276 */       throw new NullPointerException(fmsg);
/*     */     } 
/*     */ 
/*     */     
/* 280 */     if (!isSupported(returnType)) {
/* 281 */       String fmsg = XSLMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[] { returnType
/*     */             
/* 283 */             .toString() });
/* 284 */       throw new IllegalArgumentException(fmsg);
/*     */     } 
/*     */     try {
/* 287 */       if (dbf == null) {
/* 288 */         dbf = JdkXmlUtils.getDOMFactory(this.overrideDefaultParser);
/*     */       }
/* 290 */       db = dbf.newDocumentBuilder();
/* 291 */       Document document = db.parse(source);
/* 292 */       return eval(document, returnType);
/* 293 */     } catch (Exception e) {
/* 294 */       throw new XPathExpressionException(e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String evaluate(InputSource source) throws XPathExpressionException {
/* 321 */     return (String)evaluate(source, XPathConstants.STRING);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isSupported(QName returnType) {
/* 326 */     if (returnType.equals(XPathConstants.STRING) || returnType
/* 327 */       .equals(XPathConstants.NUMBER) || returnType
/* 328 */       .equals(XPathConstants.BOOLEAN) || returnType
/* 329 */       .equals(XPathConstants.NODE) || returnType
/* 330 */       .equals(XPathConstants.NODESET))
/*     */     {
/* 332 */       return true;
/*     */     }
/* 334 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object getResultAsType(XObject resultObject, QName returnType) throws TransformerException {
/* 340 */     if (returnType.equals(XPathConstants.STRING)) {
/* 341 */       return resultObject.str();
/*     */     }
/*     */     
/* 344 */     if (returnType.equals(XPathConstants.NUMBER)) {
/* 345 */       return new Double(resultObject.num());
/*     */     }
/*     */     
/* 348 */     if (returnType.equals(XPathConstants.BOOLEAN)) {
/* 349 */       return new Boolean(resultObject.bool());
/*     */     }
/*     */     
/* 352 */     if (returnType.equals(XPathConstants.NODESET)) {
/* 353 */       return resultObject.nodelist();
/*     */     }
/*     */     
/* 356 */     if (returnType.equals(XPathConstants.NODE)) {
/* 357 */       NodeIterator ni = resultObject.nodeset();
/*     */       
/* 359 */       return ni.nextNode();
/*     */     } 
/*     */ 
/*     */     
/* 363 */     String fmsg = XSLMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[] { returnType
/*     */           
/* 365 */           .toString() });
/* 366 */     throw new IllegalArgumentException(fmsg);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/jaxp/XPathExpressionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */