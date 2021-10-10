/*     */ package com.sun.org.apache.xerces.internal.jaxp;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.SAXMessageFormatter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import javax.xml.validation.Schema;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAXParserFactoryImpl
/*     */   extends SAXParserFactory
/*     */ {
/*     */   private static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
/*     */   private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
/*     */   private static final String XINCLUDE_FEATURE = "http://apache.org/xml/features/xinclude";
/*     */   private Map<String, Boolean> features;
/*     */   private Schema grammar;
/*     */   private boolean isXIncludeAware;
/*     */   private boolean fSecureProcess = true;
/*     */   
/*     */   public SAXParser newSAXParser() throws ParserConfigurationException {
/*     */     SAXParser saxParserImpl;
/*     */     try {
/*  79 */       saxParserImpl = new SAXParserImpl(this, this.features, this.fSecureProcess);
/*  80 */     } catch (SAXException se) {
/*     */       
/*  82 */       throw new ParserConfigurationException(se.getMessage());
/*     */     } 
/*  84 */     return saxParserImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SAXParserImpl newSAXParserImpl() throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
/*     */     SAXParserImpl saxParserImpl;
/*     */     try {
/*  96 */       saxParserImpl = new SAXParserImpl(this, this.features);
/*  97 */     } catch (SAXNotSupportedException e) {
/*  98 */       throw e;
/*  99 */     } catch (SAXNotRecognizedException e) {
/* 100 */       throw e;
/* 101 */     } catch (SAXException se) {
/* 102 */       throw new ParserConfigurationException(se.getMessage());
/*     */     } 
/* 104 */     return saxParserImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(String name, boolean value) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
/* 114 */     if (name == null) {
/* 115 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 118 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 119 */       if (System.getSecurityManager() != null && !value) {
/* 120 */         throw new ParserConfigurationException(
/* 121 */             SAXMessageFormatter.formatMessage(null, "jaxp-secureprocessing-feature", null));
/*     */       }
/*     */       
/* 124 */       this.fSecureProcess = value;
/* 125 */       putInFeatures(name, value);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 131 */     putInFeatures(name, value);
/*     */     
/*     */     try {
/* 134 */       newSAXParserImpl();
/* 135 */     } catch (SAXNotSupportedException e) {
/* 136 */       this.features.remove(name);
/* 137 */       throw e;
/* 138 */     } catch (SAXNotRecognizedException e) {
/* 139 */       this.features.remove(name);
/* 140 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFeature(String name) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
/* 151 */     if (name == null) {
/* 152 */       throw new NullPointerException();
/*     */     }
/* 154 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 155 */       return this.fSecureProcess;
/*     */     }
/*     */ 
/*     */     
/* 159 */     return newSAXParserImpl().getXMLReader().getFeature(name);
/*     */   }
/*     */   
/*     */   public Schema getSchema() {
/* 163 */     return this.grammar;
/*     */   }
/*     */   
/*     */   public void setSchema(Schema grammar) {
/* 167 */     this.grammar = grammar;
/*     */   }
/*     */   
/*     */   public boolean isXIncludeAware() {
/* 171 */     return getFromFeatures("http://apache.org/xml/features/xinclude");
/*     */   }
/*     */   
/*     */   public void setXIncludeAware(boolean state) {
/* 175 */     putInFeatures("http://apache.org/xml/features/xinclude", state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValidating(boolean validating) {
/* 180 */     putInFeatures("http://xml.org/sax/features/validation", validating);
/*     */   }
/*     */   
/*     */   public boolean isValidating() {
/* 184 */     return getFromFeatures("http://xml.org/sax/features/validation");
/*     */   }
/*     */   
/*     */   private void putInFeatures(String name, boolean value) {
/* 188 */     if (this.features == null) {
/* 189 */       this.features = new HashMap<>();
/*     */     }
/* 191 */     this.features.put(name, value ? Boolean.TRUE : Boolean.FALSE);
/*     */   }
/*     */   
/*     */   private boolean getFromFeatures(String name) {
/* 195 */     if (this.features == null) {
/* 196 */       return false;
/*     */     }
/*     */     
/* 199 */     Boolean value = this.features.get(name);
/* 200 */     return (value == null) ? false : value.booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNamespaceAware() {
/* 205 */     return getFromFeatures("http://xml.org/sax/features/namespaces");
/*     */   }
/*     */   
/*     */   public void setNamespaceAware(boolean awareness) {
/* 209 */     putInFeatures("http://xml.org/sax/features/namespaces", awareness);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/SAXParserFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */