/*     */ package com.sun.org.apache.xerces.internal.jaxp;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.parsers.DOMParser;
/*     */ import com.sun.org.apache.xerces.internal.util.SAXMessageFormatter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
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
/*     */ public class DocumentBuilderFactoryImpl
/*     */   extends DocumentBuilderFactory
/*     */ {
/*     */   private Map<String, Object> attributes;
/*     */   private Map<String, Boolean> features;
/*     */   private Schema grammar;
/*     */   private boolean isXIncludeAware;
/*     */   private boolean fSecureProcess = true;
/*     */   
/*     */   public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
/*  61 */     if (this.grammar != null && this.attributes != null) {
/*  62 */       if (this.attributes.containsKey("http://java.sun.com/xml/jaxp/properties/schemaLanguage")) {
/*  63 */         throw new ParserConfigurationException(
/*  64 */             SAXMessageFormatter.formatMessage(null, "schema-already-specified", new Object[] { "http://java.sun.com/xml/jaxp/properties/schemaLanguage" }));
/*     */       }
/*     */       
/*  67 */       if (this.attributes.containsKey("http://java.sun.com/xml/jaxp/properties/schemaSource")) {
/*  68 */         throw new ParserConfigurationException(
/*  69 */             SAXMessageFormatter.formatMessage(null, "schema-already-specified", new Object[] { "http://java.sun.com/xml/jaxp/properties/schemaSource" }));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  75 */       return new DocumentBuilderImpl(this, this.attributes, this.features, this.fSecureProcess);
/*  76 */     } catch (SAXException se) {
/*     */       
/*  78 */       throw new ParserConfigurationException(se.getMessage());
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
/*     */   public void setAttribute(String name, Object value) throws IllegalArgumentException {
/*  92 */     if (value == null) {
/*  93 */       if (this.attributes != null) {
/*  94 */         this.attributes.remove(name);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 104 */     if (this.attributes == null) {
/* 105 */       this.attributes = new HashMap<>();
/*     */     }
/*     */     
/* 108 */     this.attributes.put(name, value);
/*     */ 
/*     */     
/*     */     try {
/* 112 */       new DocumentBuilderImpl(this, this.attributes, this.features);
/* 113 */     } catch (Exception e) {
/* 114 */       this.attributes.remove(name);
/* 115 */       throw new IllegalArgumentException(e.getMessage());
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
/*     */   public Object getAttribute(String name) throws IllegalArgumentException {
/* 127 */     if (this.attributes != null) {
/* 128 */       Object val = this.attributes.get(name);
/* 129 */       if (val != null) {
/* 130 */         return val;
/*     */       }
/*     */     } 
/*     */     
/* 134 */     DOMParser domParser = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 139 */       domParser = (new DocumentBuilderImpl(this, this.attributes, this.features)).getDOMParser();
/* 140 */       return domParser.getProperty(name);
/* 141 */     } catch (SAXException se1) {
/*     */       
/*     */       try {
/* 144 */         boolean result = domParser.getFeature(name);
/*     */         
/* 146 */         return result ? Boolean.TRUE : Boolean.FALSE;
/* 147 */       } catch (SAXException se2) {
/*     */         
/* 149 */         throw new IllegalArgumentException(se1.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Schema getSchema() {
/* 155 */     return this.grammar;
/*     */   }
/*     */   
/*     */   public void setSchema(Schema grammar) {
/* 159 */     this.grammar = grammar;
/*     */   }
/*     */   
/*     */   public boolean isXIncludeAware() {
/* 163 */     return this.isXIncludeAware;
/*     */   }
/*     */   
/*     */   public void setXIncludeAware(boolean state) {
/* 167 */     this.isXIncludeAware = state;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getFeature(String name) throws ParserConfigurationException {
/* 172 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 173 */       return this.fSecureProcess;
/*     */     }
/*     */     
/* 176 */     if (this.features != null) {
/* 177 */       Boolean val = this.features.get(name);
/* 178 */       if (val != null) {
/* 179 */         return val.booleanValue();
/*     */       }
/*     */     } 
/*     */     try {
/* 183 */       DOMParser domParser = (new DocumentBuilderImpl(this, this.attributes, this.features)).getDOMParser();
/* 184 */       return domParser.getFeature(name);
/*     */     }
/* 186 */     catch (SAXException e) {
/* 187 */       throw new ParserConfigurationException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFeature(String name, boolean value) throws ParserConfigurationException {
/* 193 */     if (this.features == null) {
/* 194 */       this.features = new HashMap<>();
/*     */     }
/*     */     
/* 197 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 198 */       if (System.getSecurityManager() != null && !value) {
/* 199 */         throw new ParserConfigurationException(
/* 200 */             SAXMessageFormatter.formatMessage(null, "jaxp-secureprocessing-feature", null));
/*     */       }
/*     */       
/* 203 */       this.fSecureProcess = value;
/* 204 */       this.features.put(name, value ? Boolean.TRUE : Boolean.FALSE);
/*     */       
/*     */       return;
/*     */     } 
/* 208 */     this.features.put(name, value ? Boolean.TRUE : Boolean.FALSE);
/*     */     
/*     */     try {
/* 211 */       new DocumentBuilderImpl(this, this.attributes, this.features);
/*     */     }
/* 213 */     catch (SAXNotSupportedException e) {
/* 214 */       this.features.remove(name);
/* 215 */       throw new ParserConfigurationException(e.getMessage());
/*     */     }
/* 217 */     catch (SAXNotRecognizedException e) {
/* 218 */       this.features.remove(name);
/* 219 */       throw new ParserConfigurationException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/DocumentBuilderFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */