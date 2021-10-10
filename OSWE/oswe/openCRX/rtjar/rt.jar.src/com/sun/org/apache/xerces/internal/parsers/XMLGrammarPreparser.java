/*     */ package com.sun.org.apache.xerces.internal.parsers;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import com.sun.org.apache.xerces.internal.utils.ObjectFactory;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
/*     */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarLoader;
/*     */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLGrammarPreparser
/*     */ {
/*     */   private static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
/*     */   protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*     */   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*     */   protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
/*     */   protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
/*     */   protected static final String GRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
/*     */   private static final Map<String, String> KNOWN_LOADERS;
/*     */   
/*     */   static {
/*  88 */     Map<String, String> loaders = new HashMap<>();
/*  89 */     loaders.put("http://www.w3.org/2001/XMLSchema", "com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaLoader");
/*     */     
/*  91 */     loaders.put("http://www.w3.org/TR/REC-xml", "com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDLoader");
/*     */     
/*  93 */     KNOWN_LOADERS = Collections.unmodifiableMap(loaders);
/*     */   }
/*     */ 
/*     */   
/*  97 */   private static final String[] RECOGNIZED_PROPERTIES = new String[] { "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/error-handler", "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/grammar-pool" };
/*     */ 
/*     */ 
/*     */   
/*     */   protected SymbolTable fSymbolTable;
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLErrorReporter fErrorReporter;
/*     */ 
/*     */   
/*     */   protected XMLEntityResolver fEntityResolver;
/*     */ 
/*     */   
/*     */   protected XMLGrammarPool fGrammarPool;
/*     */ 
/*     */   
/*     */   protected Locale fLocale;
/*     */ 
/*     */   
/*     */   private Map<String, XMLGrammarLoader> fLoaders;
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLGrammarPreparser() {
/* 122 */     this(new SymbolTable());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLGrammarPreparser(SymbolTable symbolTable) {
/* 131 */     this.fSymbolTable = symbolTable;
/*     */     
/* 133 */     this.fLoaders = new HashMap<>();
/* 134 */     this.fErrorReporter = new XMLErrorReporter();
/* 135 */     setLocale(Locale.getDefault());
/* 136 */     this.fEntityResolver = new XMLEntityManager();
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
/*     */   public boolean registerPreparser(String grammarType, XMLGrammarLoader loader) {
/* 156 */     if (loader == null) {
/* 157 */       if (KNOWN_LOADERS.containsKey(grammarType)) {
/*     */         
/* 159 */         String loaderName = KNOWN_LOADERS.get(grammarType);
/*     */         try {
/* 161 */           XMLGrammarLoader gl = (XMLGrammarLoader)ObjectFactory.newInstance(loaderName, true);
/* 162 */           this.fLoaders.put(grammarType, gl);
/* 163 */         } catch (Exception e) {
/* 164 */           return false;
/*     */         } 
/* 166 */         return true;
/*     */       } 
/* 168 */       return false;
/*     */     } 
/*     */     
/* 171 */     this.fLoaders.put(grammarType, loader);
/* 172 */     return true;
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
/*     */   public Grammar preparseGrammar(String type, XMLInputSource is) throws XNIException, IOException {
/* 193 */     if (this.fLoaders.containsKey(type)) {
/* 194 */       XMLGrammarLoader gl = this.fLoaders.get(type);
/*     */       
/* 196 */       gl.setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
/* 197 */       gl.setProperty("http://apache.org/xml/properties/internal/entity-resolver", this.fEntityResolver);
/* 198 */       gl.setProperty("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
/*     */       
/* 200 */       if (this.fGrammarPool != null) {
/*     */         try {
/* 202 */           gl.setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarPool);
/* 203 */         } catch (Exception exception) {}
/*     */       }
/*     */ 
/*     */       
/* 207 */       return gl.loadGrammar(is);
/*     */     } 
/* 209 */     return null;
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
/*     */   public void setLocale(Locale locale) {
/* 221 */     this.fLocale = locale;
/* 222 */     this.fErrorReporter.setLocale(locale);
/*     */   }
/*     */ 
/*     */   
/*     */   public Locale getLocale() {
/* 227 */     return this.fLocale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorHandler(XMLErrorHandler errorHandler) {
/* 237 */     this.fErrorReporter.setProperty("http://apache.org/xml/properties/internal/error-handler", errorHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLErrorHandler getErrorHandler() {
/* 242 */     return this.fErrorReporter.getErrorHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntityResolver(XMLEntityResolver entityResolver) {
/* 251 */     this.fEntityResolver = entityResolver;
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLEntityResolver getEntityResolver() {
/* 256 */     return this.fEntityResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGrammarPool(XMLGrammarPool grammarPool) {
/* 265 */     this.fGrammarPool = grammarPool;
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLGrammarPool getGrammarPool() {
/* 270 */     return this.fGrammarPool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLGrammarLoader getLoader(String type) {
/* 276 */     return this.fLoaders.get(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(String featureId, boolean value) {
/* 285 */     for (Map.Entry<String, XMLGrammarLoader> entry : this.fLoaders.entrySet()) {
/*     */       try {
/* 287 */         XMLGrammarLoader gl = entry.getValue();
/* 288 */         gl.setFeature(featureId, value);
/* 289 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 295 */     if (featureId.equals("http://apache.org/xml/features/continue-after-fatal-error")) {
/* 296 */       this.fErrorReporter.setFeature("http://apache.org/xml/features/continue-after-fatal-error", value);
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
/*     */   public void setProperty(String propId, Object value) {
/* 308 */     for (Map.Entry<String, XMLGrammarLoader> entry : this.fLoaders.entrySet()) {
/*     */       try {
/* 310 */         XMLGrammarLoader gl = entry.getValue();
/* 311 */         gl.setProperty(propId, value);
/* 312 */       } catch (Exception exception) {}
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
/*     */   public boolean getFeature(String type, String featureId) {
/* 325 */     XMLGrammarLoader gl = this.fLoaders.get(type);
/* 326 */     return gl.getFeature(featureId);
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
/*     */   public Object getProperty(String type, String propertyId) {
/* 338 */     XMLGrammarLoader gl = this.fLoaders.get(type);
/* 339 */     return gl.getProperty(propertyId);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/parsers/XMLGrammarPreparser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */