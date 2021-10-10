/*     */ package com.sun.org.apache.xml.internal.utils;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*     */ import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
/*     */ import java.util.HashMap;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLReaderManager
/*     */ {
/*  40 */   private static final XMLReaderManager m_singletonManager = new XMLReaderManager();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String property = "org.xml.sax.driver";
/*     */ 
/*     */ 
/*     */   
/*     */   private ThreadLocal<ReaderWrapper> m_readers;
/*     */ 
/*     */   
/*     */   private boolean m_overrideDefaultParser;
/*     */ 
/*     */   
/*     */   private HashMap m_inUse;
/*     */ 
/*     */   
/*     */   private boolean _secureProcessing;
/*     */ 
/*     */   
/*  60 */   private String _accessExternalDTD = "all";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLSecurityManager _xmlSecurityManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLReaderManager getInstance(boolean overrideDefaultParser) {
/*  74 */     m_singletonManager.setOverrideDefaultParser(overrideDefaultParser);
/*  75 */     return m_singletonManager;
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
/*     */   public synchronized XMLReader getXMLReader() throws SAXException {
/*  87 */     if (this.m_readers == null)
/*     */     {
/*     */       
/*  90 */       this.m_readers = new ThreadLocal<>();
/*     */     }
/*     */     
/*  93 */     if (this.m_inUse == null) {
/*  94 */       this.m_inUse = new HashMap<>();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     ReaderWrapper rw = this.m_readers.get();
/* 107 */     boolean threadHasReader = (rw != null);
/* 108 */     XMLReader reader = threadHasReader ? rw.reader : null;
/* 109 */     String factory = SecuritySupport.getSystemProperty("org.xml.sax.driver");
/* 110 */     if (threadHasReader && this.m_inUse.get(reader) != Boolean.TRUE && rw.overrideDefaultParser == this.m_overrideDefaultParser && (factory == null || reader
/*     */       
/* 112 */       .getClass().getName().equals(factory))) {
/* 113 */       this.m_inUse.put(reader, Boolean.TRUE);
/*     */     } else {
/* 115 */       reader = JdkXmlUtils.getXMLReader(this.m_overrideDefaultParser, this._secureProcessing);
/*     */ 
/*     */       
/* 118 */       if (!threadHasReader) {
/* 119 */         this.m_readers.set(new ReaderWrapper(reader, this.m_overrideDefaultParser));
/* 120 */         this.m_inUse.put(reader, Boolean.TRUE);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 126 */       reader.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", this._accessExternalDTD);
/* 127 */     } catch (SAXException se) {
/* 128 */       XMLSecurityManager.printWarning(reader.getClass().getName(), "http://javax.xml.XMLConstants/property/accessExternalDTD", se);
/*     */     } 
/*     */ 
/*     */     
/* 132 */     String lastProperty = "";
/*     */     try {
/* 134 */       if (this._xmlSecurityManager != null) {
/* 135 */         for (XMLSecurityManager.Limit limit : XMLSecurityManager.Limit.values()) {
/* 136 */           lastProperty = limit.apiProperty();
/* 137 */           reader.setProperty(lastProperty, this._xmlSecurityManager
/* 138 */               .getLimitValueAsString(limit));
/*     */         } 
/* 140 */         if (this._xmlSecurityManager.printEntityCountInfo()) {
/* 141 */           lastProperty = "http://www.oracle.com/xml/jaxp/properties/getEntityCountInfo";
/* 142 */           reader.setProperty("http://www.oracle.com/xml/jaxp/properties/getEntityCountInfo", "yes");
/*     */         } 
/*     */       } 
/* 145 */     } catch (SAXException se) {
/* 146 */       XMLSecurityManager.printWarning(reader.getClass().getName(), lastProperty, se);
/*     */     } 
/*     */     
/* 149 */     return reader;
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
/*     */   public synchronized void releaseXMLReader(XMLReader reader) {
/* 161 */     ReaderWrapper rw = this.m_readers.get();
/* 162 */     if (rw.reader == reader && reader != null) {
/* 163 */       this.m_inUse.remove(reader);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean overrideDefaultParser() {
/* 170 */     return this.m_overrideDefaultParser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOverrideDefaultParser(boolean flag) {
/* 177 */     this.m_overrideDefaultParser = flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(String name, boolean value) {
/* 184 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 185 */       this._secureProcessing = value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) {
/* 193 */     if (name.equals("http://javax.xml.XMLConstants/property/accessExternalDTD"))
/* 194 */       return this._accessExternalDTD; 
/* 195 */     if (name.equals("http://apache.org/xml/properties/security-manager")) {
/* 196 */       return this._xmlSecurityManager;
/*     */     }
/* 198 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) {
/* 205 */     if (name.equals("http://javax.xml.XMLConstants/property/accessExternalDTD")) {
/* 206 */       this._accessExternalDTD = (String)value;
/* 207 */     } else if (name.equals("http://apache.org/xml/properties/security-manager")) {
/* 208 */       this._xmlSecurityManager = (XMLSecurityManager)value;
/*     */     } 
/*     */   }
/*     */   
/*     */   class ReaderWrapper {
/*     */     XMLReader reader;
/*     */     boolean overrideDefaultParser;
/*     */     
/*     */     public ReaderWrapper(XMLReader reader, boolean overrideDefaultParser) {
/* 217 */       this.reader = reader;
/* 218 */       this.overrideDefaultParser = overrideDefaultParser;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/utils/XMLReaderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */