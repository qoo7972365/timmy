/*     */ package jdk.xml.internal;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
/*     */ import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
/*     */ import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.sax.SAXTransformerFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.helpers.XMLReaderFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JdkXmlUtils
/*     */ {
/*     */   private static final String DOM_FACTORY_ID = "javax.xml.parsers.DocumentBuilderFactory";
/*     */   private static final String SAX_FACTORY_ID = "javax.xml.parsers.SAXParserFactory";
/*     */   private static final String SAX_DRIVER = "org.xml.sax.driver";
/*     */   public static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
/*     */   public static final String NAMESPACE_PREFIXES_FEATURE = "http://xml.org/sax/features/namespace-prefixes";
/*     */   public static final String OVERRIDE_PARSER = "jdk.xml.overrideDefaultParser";
/*  66 */   public static final boolean OVERRIDE_PARSER_DEFAULT = ((Boolean)SecuritySupport.<Boolean>getJAXPSystemProperty(Boolean.class, "jdk.xml.overrideDefaultParser", "false")).booleanValue();
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String FEATURE_TRUE = "true";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String FEATURE_FALSE = "false";
/*     */ 
/*     */ 
/*     */   
/*  78 */   private static final SAXParserFactory defaultSAXFactory = getSAXFactory(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getValue(Object value, int defValue) {
/*  88 */     if (value == null) {
/*  89 */       return defValue;
/*     */     }
/*     */     
/*  92 */     if (value instanceof Number)
/*  93 */       return ((Number)value).intValue(); 
/*  94 */     if (value instanceof String) {
/*  95 */       return Integer.parseInt(String.valueOf(value));
/*     */     }
/*  97 */     throw new IllegalArgumentException("Unexpected class: " + value
/*  98 */         .getClass());
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
/*     */   public static void setXMLReaderPropertyIfSupport(XMLReader reader, String property, Object value, boolean warn) {
/*     */     try {
/* 115 */       reader.setProperty(property, value);
/* 116 */     } catch (SAXNotRecognizedException|org.xml.sax.SAXNotSupportedException e) {
/* 117 */       if (warn) {
/* 118 */         XMLSecurityManager.printWarning(reader.getClass().getName(), property, e);
/*     */       }
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
/*     */   public static XMLReader getXMLReader(boolean overrideDefaultParser, boolean secureProcessing) {
/* 138 */     XMLReader reader = null;
/* 139 */     String spSAXDriver = SecuritySupport.getSystemProperty("org.xml.sax.driver");
/* 140 */     if (spSAXDriver != null) {
/* 141 */       reader = getXMLReaderWXMLReaderFactory();
/* 142 */     } else if (overrideDefaultParser) {
/* 143 */       reader = getXMLReaderWSAXFactory(overrideDefaultParser);
/*     */     } 
/*     */     
/* 146 */     if (reader != null) {
/* 147 */       if (secureProcessing) {
/*     */         try {
/* 149 */           reader.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", secureProcessing);
/* 150 */         } catch (SAXException e) {
/* 151 */           XMLSecurityManager.printWarning(reader.getClass().getName(), "http://javax.xml.XMLConstants/feature/secure-processing", e);
/*     */         } 
/*     */       }
/*     */       
/*     */       try {
/* 156 */         reader.setFeature("http://xml.org/sax/features/namespaces", true);
/* 157 */         reader.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
/* 158 */       } catch (SAXException sAXException) {}
/*     */ 
/*     */       
/* 161 */       return reader;
/*     */     } 
/*     */ 
/*     */     
/* 165 */     SAXParserFactory saxFactory = defaultSAXFactory;
/*     */     
/*     */     try {
/* 168 */       reader = saxFactory.newSAXParser().getXMLReader();
/* 169 */     } catch (ParserConfigurationException|SAXException parserConfigurationException) {}
/*     */ 
/*     */     
/* 172 */     return reader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document getDOMDocument() {
/*     */     try {
/* 182 */       DocumentBuilderFactory dbf = getDOMFactory(false);
/* 183 */       return dbf.newDocumentBuilder().newDocument();
/* 184 */     } catch (ParserConfigurationException parserConfigurationException) {
/*     */ 
/*     */       
/* 187 */       return null;
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
/*     */   public static DocumentBuilderFactory getDOMFactory(boolean overrideDefaultParser) {
/* 200 */     boolean override = overrideDefaultParser;
/* 201 */     String spDOMFactory = SecuritySupport.getJAXPSystemProperty("javax.xml.parsers.DocumentBuilderFactory");
/*     */     
/* 203 */     if (spDOMFactory != null && System.getSecurityManager() == null) {
/* 204 */       override = true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 209 */     DocumentBuilderFactory dbf = !override ? new DocumentBuilderFactoryImpl() : DocumentBuilderFactory.newInstance();
/* 210 */     dbf.setNamespaceAware(true);
/*     */     
/* 212 */     dbf.setValidating(false);
/* 213 */     return dbf;
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
/*     */   public static SAXParserFactory getSAXFactory(boolean overrideDefaultParser) {
/* 226 */     boolean override = overrideDefaultParser;
/* 227 */     String spSAXFactory = SecuritySupport.getJAXPSystemProperty("javax.xml.parsers.SAXParserFactory");
/* 228 */     if (spSAXFactory != null && System.getSecurityManager() == null) {
/* 229 */       override = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     SAXParserFactory factory = !override ? new SAXParserFactoryImpl() : SAXParserFactory.newInstance();
/* 236 */     factory.setNamespaceAware(true);
/* 237 */     return factory;
/*     */   }
/*     */ 
/*     */   
/*     */   public static SAXTransformerFactory getSAXTransformFactory(boolean overrideDefaultParser) {
/* 242 */     SAXTransformerFactory tf = overrideDefaultParser ? (SAXTransformerFactory)SAXTransformerFactory.newInstance() : new TransformerFactoryImpl();
/*     */     
/*     */     try {
/* 245 */       tf.setFeature("jdk.xml.overrideDefaultParser", overrideDefaultParser);
/* 246 */     } catch (TransformerConfigurationException transformerConfigurationException) {}
/*     */ 
/*     */     
/* 249 */     return tf;
/*     */   }
/*     */   
/*     */   private static XMLReader getXMLReaderWSAXFactory(boolean overrideDefaultParser) {
/* 253 */     SAXParserFactory saxFactory = getSAXFactory(overrideDefaultParser);
/*     */     try {
/* 255 */       return saxFactory.newSAXParser().getXMLReader();
/* 256 */     } catch (ParserConfigurationException|SAXException ex) {
/* 257 */       return getXMLReaderWXMLReaderFactory();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static XMLReader getXMLReaderWXMLReaderFactory() {
/*     */     try {
/* 264 */       return XMLReaderFactory.createXMLReader();
/* 265 */     } catch (SAXException sAXException) {
/*     */       
/* 267 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/xml/internal/JdkXmlUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */