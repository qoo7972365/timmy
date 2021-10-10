/*     */ package javax.xml.xpath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XPathFactory
/*     */ {
/*     */   public static final String DEFAULT_PROPERTY_NAME = "javax.xml.xpath.XPathFactory";
/*     */   public static final String DEFAULT_OBJECT_MODEL_URI = "http://java.sun.com/jaxp/xpath/dom";
/*  66 */   private static SecuritySupport ss = new SecuritySupport();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XPathFactory newInstance() {
/*     */     try {
/*  96 */       return newInstance("http://java.sun.com/jaxp/xpath/dom");
/*  97 */     } catch (XPathFactoryConfigurationException xpathFactoryConfigurationException) {
/*  98 */       throw new RuntimeException("XPathFactory#newInstance() failed to create an XPathFactory for the default object model: http://java.sun.com/jaxp/xpath/dom with the XPathFactoryConfigurationException: " + xpathFactoryConfigurationException
/*     */ 
/*     */ 
/*     */           
/* 102 */           .toString());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XPathFactory newInstance(String uri) throws XPathFactoryConfigurationException {
/* 173 */     if (uri == null) {
/* 174 */       throw new NullPointerException("XPathFactory#newInstance(String uri) cannot be called with uri == null");
/*     */     }
/*     */ 
/*     */     
/* 178 */     if (uri.length() == 0) {
/* 179 */       throw new IllegalArgumentException("XPathFactory#newInstance(String uri) cannot be called with uri == \"\"");
/*     */     }
/*     */ 
/*     */     
/* 183 */     ClassLoader classLoader = ss.getContextClassLoader();
/*     */     
/* 185 */     if (classLoader == null)
/*     */     {
/* 187 */       classLoader = XPathFactory.class.getClassLoader();
/*     */     }
/*     */     
/* 190 */     XPathFactory xpathFactory = (new XPathFactoryFinder(classLoader)).newFactory(uri);
/*     */     
/* 192 */     if (xpathFactory == null) {
/* 193 */       throw new XPathFactoryConfigurationException("No XPathFactory implementation found for the object model: " + uri);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 198 */     return xpathFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XPathFactory newInstance(String uri, String factoryClassName, ClassLoader classLoader) throws XPathFactoryConfigurationException {
/* 249 */     ClassLoader cl = classLoader;
/*     */     
/* 251 */     if (uri == null) {
/* 252 */       throw new NullPointerException("XPathFactory#newInstance(String uri) cannot be called with uri == null");
/*     */     }
/*     */ 
/*     */     
/* 256 */     if (uri.length() == 0) {
/* 257 */       throw new IllegalArgumentException("XPathFactory#newInstance(String uri) cannot be called with uri == \"\"");
/*     */     }
/*     */ 
/*     */     
/* 261 */     if (cl == null) {
/* 262 */       cl = ss.getContextClassLoader();
/*     */     }
/*     */     
/* 265 */     XPathFactory f = (new XPathFactoryFinder(cl)).createInstance(factoryClassName);
/*     */     
/* 267 */     if (f == null) {
/* 268 */       throw new XPathFactoryConfigurationException("No XPathFactory implementation found for the object model: " + uri);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 273 */     if (f.isObjectModelSupported(uri)) {
/* 274 */       return f;
/*     */     }
/* 276 */     throw new XPathFactoryConfigurationException("Factory " + factoryClassName + " doesn't support given " + uri + " object model");
/*     */   }
/*     */   
/*     */   public abstract boolean isObjectModelSupported(String paramString);
/*     */   
/*     */   public abstract void setFeature(String paramString, boolean paramBoolean) throws XPathFactoryConfigurationException;
/*     */   
/*     */   public abstract boolean getFeature(String paramString) throws XPathFactoryConfigurationException;
/*     */   
/*     */   public abstract void setXPathVariableResolver(XPathVariableResolver paramXPathVariableResolver);
/*     */   
/*     */   public abstract void setXPathFunctionResolver(XPathFunctionResolver paramXPathFunctionResolver);
/*     */   
/*     */   public abstract XPath newXPath();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/xpath/XPathFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */