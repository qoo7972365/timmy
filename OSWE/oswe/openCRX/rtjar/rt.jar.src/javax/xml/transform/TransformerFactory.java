/*     */ package javax.xml.transform;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TransformerFactory
/*     */ {
/*     */   public static TransformerFactory newInstance() throws TransformerFactoryConfigurationError {
/* 106 */     return FactoryFinder.<TransformerFactory>find(TransformerFactory.class, "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
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
/*     */   public static TransformerFactory newInstance(String factoryClassName, ClassLoader classLoader) throws TransformerFactoryConfigurationError {
/* 152 */     return FactoryFinder.<TransformerFactory>newInstance(TransformerFactory.class, factoryClassName, classLoader, false);
/*     */   }
/*     */   
/*     */   public abstract Transformer newTransformer(Source paramSource) throws TransformerConfigurationException;
/*     */   
/*     */   public abstract Transformer newTransformer() throws TransformerConfigurationException;
/*     */   
/*     */   public abstract Templates newTemplates(Source paramSource) throws TransformerConfigurationException;
/*     */   
/*     */   public abstract Source getAssociatedStylesheet(Source paramSource, String paramString1, String paramString2, String paramString3) throws TransformerConfigurationException;
/*     */   
/*     */   public abstract void setURIResolver(URIResolver paramURIResolver);
/*     */   
/*     */   public abstract URIResolver getURIResolver();
/*     */   
/*     */   public abstract void setFeature(String paramString, boolean paramBoolean) throws TransformerConfigurationException;
/*     */   
/*     */   public abstract boolean getFeature(String paramString);
/*     */   
/*     */   public abstract void setAttribute(String paramString, Object paramObject);
/*     */   
/*     */   public abstract Object getAttribute(String paramString);
/*     */   
/*     */   public abstract void setErrorListener(ErrorListener paramErrorListener);
/*     */   
/*     */   public abstract ErrorListener getErrorListener();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/transform/TransformerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */