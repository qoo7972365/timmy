/*     */ package com.sun.org.apache.xpath.internal.jaxp;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.res.XSLMessages;
/*     */ import com.sun.org.apache.xpath.internal.XPath;
/*     */ import com.sun.org.apache.xpath.internal.XPathContext;
/*     */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*     */ import java.io.IOException;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.xpath.XPath;
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
/*     */ public class XPathImpl
/*     */   implements XPath
/*     */ {
/*     */   private XPathVariableResolver variableResolver;
/*     */   private XPathFunctionResolver functionResolver;
/*     */   private XPathVariableResolver origVariableResolver;
/*     */   private XPathFunctionResolver origFunctionResolver;
/*  67 */   private NamespaceContext namespaceContext = null;
/*     */   
/*     */   private JAXPPrefixResolver prefixResolver;
/*     */   
/*     */   private boolean featureSecureProcessing = false;
/*     */   
/*     */   private boolean overrideDefaultParser = true;
/*     */   private final JdkXmlFeatures featureManager;
/*     */   
/*     */   XPathImpl(XPathVariableResolver vr, XPathFunctionResolver fr) {
/*  77 */     this(vr, fr, false, new JdkXmlFeatures(false));
/*     */   }
/*     */ 
/*     */   
/*     */   XPathImpl(XPathVariableResolver vr, XPathFunctionResolver fr, boolean featureSecureProcessing, JdkXmlFeatures featureManager) {
/*  82 */     this.origVariableResolver = this.variableResolver = vr;
/*  83 */     this.origFunctionResolver = this.functionResolver = fr;
/*  84 */     this.featureSecureProcessing = featureSecureProcessing;
/*  85 */     this.featureManager = featureManager;
/*  86 */     this.overrideDefaultParser = featureManager.getFeature(JdkXmlFeatures.XmlFeature.JDK_OVERRIDE_PARSER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXPathVariableResolver(XPathVariableResolver resolver) {
/*  96 */     if (resolver == null) {
/*  97 */       String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "XPathVariableResolver" });
/*     */ 
/*     */       
/* 100 */       throw new NullPointerException(fmsg);
/*     */     } 
/* 102 */     this.variableResolver = resolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XPathVariableResolver getXPathVariableResolver() {
/* 111 */     return this.variableResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXPathFunctionResolver(XPathFunctionResolver resolver) {
/* 120 */     if (resolver == null) {
/* 121 */       String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "XPathFunctionResolver" });
/*     */ 
/*     */       
/* 124 */       throw new NullPointerException(fmsg);
/*     */     } 
/* 126 */     this.functionResolver = resolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XPathFunctionResolver getXPathFunctionResolver() {
/* 135 */     return this.functionResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext nsContext) {
/* 144 */     if (nsContext == null) {
/* 145 */       String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "NamespaceContext" });
/*     */ 
/*     */       
/* 148 */       throw new NullPointerException(fmsg);
/*     */     } 
/* 150 */     this.namespaceContext = nsContext;
/* 151 */     this.prefixResolver = new JAXPPrefixResolver(nsContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 160 */     return this.namespaceContext;
/*     */   }
/*     */   
/* 163 */   private static Document d = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DocumentBuilder getParser() {
/*     */     try {
/* 178 */       DocumentBuilderFactory dbf = JdkXmlUtils.getDOMFactory(this.overrideDefaultParser);
/* 179 */       return dbf.newDocumentBuilder();
/* 180 */     } catch (ParserConfigurationException e) {
/*     */       
/* 182 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private XObject eval(String expression, Object contextItem) throws TransformerException {
/* 189 */     XPath xpath = new XPath(expression, null, this.prefixResolver, 0);
/*     */     
/* 191 */     XPathContext xpathSupport = null;
/* 192 */     if (this.functionResolver != null) {
/* 193 */       JAXPExtensionsProvider jep = new JAXPExtensionsProvider(this.functionResolver, this.featureSecureProcessing, this.featureManager);
/*     */       
/* 195 */       xpathSupport = new XPathContext(jep);
/*     */     } else {
/* 197 */       xpathSupport = new XPathContext();
/*     */     } 
/*     */     
/* 200 */     XObject xobj = null;
/*     */     
/* 202 */     xpathSupport.setVarStack(new JAXPVariableStack(this.variableResolver));
/*     */ 
/*     */     
/* 205 */     if (contextItem instanceof Node) {
/* 206 */       xobj = xpath.execute(xpathSupport, (Node)contextItem, this.prefixResolver);
/*     */     } else {
/*     */       
/* 209 */       xobj = xpath.execute(xpathSupport, -1, this.prefixResolver);
/*     */     } 
/*     */     
/* 212 */     return xobj;
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
/*     */   public Object evaluate(String expression, Object item, QName returnType) throws XPathExpressionException {
/* 248 */     if (expression == null) {
/* 249 */       String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "XPath expression" });
/*     */ 
/*     */       
/* 252 */       throw new NullPointerException(fmsg);
/*     */     } 
/* 254 */     if (returnType == null) {
/* 255 */       String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "returnType" });
/*     */ 
/*     */       
/* 258 */       throw new NullPointerException(fmsg);
/*     */     } 
/*     */ 
/*     */     
/* 262 */     if (!isSupported(returnType)) {
/* 263 */       String fmsg = XSLMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[] { returnType
/*     */             
/* 265 */             .toString() });
/* 266 */       throw new IllegalArgumentException(fmsg);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 271 */       XObject resultObject = eval(expression, item);
/* 272 */       return getResultAsType(resultObject, returnType);
/* 273 */     } catch (NullPointerException npe) {
/*     */ 
/*     */ 
/*     */       
/* 277 */       throw new XPathExpressionException(npe);
/* 278 */     } catch (TransformerException te) {
/* 279 */       Throwable nestedException = te.getException();
/* 280 */       if (nestedException instanceof XPathFunctionException) {
/* 281 */         throw (XPathFunctionException)nestedException;
/*     */       }
/*     */ 
/*     */       
/* 285 */       throw new XPathExpressionException(te);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSupported(QName returnType) {
/* 292 */     if (returnType.equals(XPathConstants.STRING) || returnType
/* 293 */       .equals(XPathConstants.NUMBER) || returnType
/* 294 */       .equals(XPathConstants.BOOLEAN) || returnType
/* 295 */       .equals(XPathConstants.NODE) || returnType
/* 296 */       .equals(XPathConstants.NODESET))
/*     */     {
/* 298 */       return true;
/*     */     }
/* 300 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object getResultAsType(XObject resultObject, QName returnType) throws TransformerException {
/* 306 */     if (returnType.equals(XPathConstants.STRING)) {
/* 307 */       return resultObject.str();
/*     */     }
/*     */     
/* 310 */     if (returnType.equals(XPathConstants.NUMBER)) {
/* 311 */       return new Double(resultObject.num());
/*     */     }
/*     */     
/* 314 */     if (returnType.equals(XPathConstants.BOOLEAN)) {
/* 315 */       return new Boolean(resultObject.bool());
/*     */     }
/*     */     
/* 318 */     if (returnType.equals(XPathConstants.NODESET)) {
/* 319 */       return resultObject.nodelist();
/*     */     }
/*     */     
/* 322 */     if (returnType.equals(XPathConstants.NODE)) {
/* 323 */       NodeIterator ni = resultObject.nodeset();
/*     */       
/* 325 */       return ni.nextNode();
/*     */     } 
/* 327 */     String fmsg = XSLMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[] { returnType
/*     */           
/* 329 */           .toString() });
/* 330 */     throw new IllegalArgumentException(fmsg);
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
/*     */   public String evaluate(String expression, Object item) throws XPathExpressionException {
/* 361 */     return (String)evaluate(expression, item, XPathConstants.STRING);
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
/*     */   public XPathExpression compile(String expression) throws XPathExpressionException {
/* 383 */     if (expression == null) {
/* 384 */       String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "XPath expression" });
/*     */ 
/*     */       
/* 387 */       throw new NullPointerException(fmsg);
/*     */     } 
/*     */     try {
/* 390 */       XPath xpath = new XPath(expression, null, this.prefixResolver, 0);
/*     */ 
/*     */       
/* 393 */       XPathExpressionImpl ximpl = new XPathExpressionImpl(xpath, this.prefixResolver, this.functionResolver, this.variableResolver, this.featureSecureProcessing, this.featureManager);
/*     */ 
/*     */       
/* 396 */       return ximpl;
/* 397 */     } catch (TransformerException te) {
/* 398 */       throw new XPathExpressionException(te);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object evaluate(String expression, InputSource source, QName returnType) throws XPathExpressionException {
/* 434 */     if (source == null) {
/* 435 */       String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "source" });
/*     */ 
/*     */       
/* 438 */       throw new NullPointerException(fmsg);
/*     */     } 
/* 440 */     if (expression == null) {
/* 441 */       String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "XPath expression" });
/*     */ 
/*     */       
/* 444 */       throw new NullPointerException(fmsg);
/*     */     } 
/* 446 */     if (returnType == null) {
/* 447 */       String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "returnType" });
/*     */ 
/*     */       
/* 450 */       throw new NullPointerException(fmsg);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 455 */     if (!isSupported(returnType)) {
/* 456 */       String fmsg = XSLMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[] { returnType
/*     */             
/* 458 */             .toString() });
/* 459 */       throw new IllegalArgumentException(fmsg);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 464 */       Document document = getParser().parse(source);
/*     */       
/* 466 */       XObject resultObject = eval(expression, document);
/* 467 */       return getResultAsType(resultObject, returnType);
/* 468 */     } catch (SAXException e) {
/* 469 */       throw new XPathExpressionException(e);
/* 470 */     } catch (IOException e) {
/* 471 */       throw new XPathExpressionException(e);
/* 472 */     } catch (TransformerException te) {
/* 473 */       Throwable nestedException = te.getException();
/* 474 */       if (nestedException instanceof XPathFunctionException) {
/* 475 */         throw (XPathFunctionException)nestedException;
/*     */       }
/* 477 */       throw new XPathExpressionException(te);
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
/*     */ 
/*     */   
/*     */   public String evaluate(String expression, InputSource source) throws XPathExpressionException {
/* 511 */     return (String)evaluate(expression, source, XPathConstants.STRING);
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
/*     */   public void reset() {
/* 530 */     this.variableResolver = this.origVariableResolver;
/* 531 */     this.functionResolver = this.origFunctionResolver;
/* 532 */     this.namespaceContext = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/jaxp/XPathImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */