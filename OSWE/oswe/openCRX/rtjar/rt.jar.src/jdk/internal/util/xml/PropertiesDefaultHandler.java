/*     */ package jdk.internal.util.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.InvalidPropertiesFormatException;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import jdk.internal.org.xml.sax.Attributes;
/*     */ import jdk.internal.org.xml.sax.InputSource;
/*     */ import jdk.internal.org.xml.sax.SAXException;
/*     */ import jdk.internal.org.xml.sax.SAXParseException;
/*     */ import jdk.internal.org.xml.sax.helpers.DefaultHandler;
/*     */ import jdk.internal.util.xml.impl.SAXParserImpl;
/*     */ import jdk.internal.util.xml.impl.XMLStreamWriterImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertiesDefaultHandler
/*     */   extends DefaultHandler
/*     */ {
/*     */   private static final String ELEMENT_ROOT = "properties";
/*     */   private static final String ELEMENT_COMMENT = "comment";
/*     */   private static final String ELEMENT_ENTRY = "entry";
/*     */   private static final String ATTR_KEY = "key";
/*     */   private static final String PROPS_DTD_DECL = "<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">";
/*     */   private static final String PROPS_DTD_URI = "http://java.sun.com/dtd/properties.dtd";
/*     */   private static final String PROPS_DTD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!-- DTD for properties --><!ELEMENT properties ( comment?, entry* ) ><!ATTLIST properties version CDATA #FIXED \"1.0\"><!ELEMENT comment (#PCDATA) ><!ELEMENT entry (#PCDATA) ><!ATTLIST entry  key CDATA #REQUIRED>";
/*     */   private static final String EXTERNAL_XML_VERSION = "1.0";
/*     */   private Properties properties;
/*     */   static final String ALLOWED_ELEMENTS = "properties, comment, entry";
/*     */   static final String ALLOWED_COMMENT = "comment";
/*     */   
/*     */   public void load(Properties paramProperties, InputStream paramInputStream) throws IOException, InvalidPropertiesFormatException, UnsupportedEncodingException {
/*  78 */     this.properties = paramProperties;
/*     */     
/*     */     try {
/*  81 */       SAXParserImpl sAXParserImpl = new SAXParserImpl();
/*  82 */       sAXParserImpl.parse(paramInputStream, this);
/*  83 */     } catch (SAXException sAXException) {
/*  84 */       throw new InvalidPropertiesFormatException(sAXException);
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
/*     */   public void store(Properties paramProperties, OutputStream paramOutputStream, String paramString1, String paramString2) throws IOException {
/*     */     try {
/* 101 */       XMLStreamWriterImpl xMLStreamWriterImpl = new XMLStreamWriterImpl(paramOutputStream, paramString2);
/* 102 */       xMLStreamWriterImpl.writeStartDocument();
/* 103 */       xMLStreamWriterImpl.writeDTD("<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">");
/* 104 */       xMLStreamWriterImpl.writeStartElement("properties");
/* 105 */       if (paramString1 != null && paramString1.length() > 0) {
/* 106 */         xMLStreamWriterImpl.writeStartElement("comment");
/* 107 */         xMLStreamWriterImpl.writeCharacters(paramString1);
/* 108 */         xMLStreamWriterImpl.writeEndElement();
/*     */       } 
/*     */       
/* 111 */       synchronized (paramProperties) {
/* 112 */         for (Map.Entry<Object, Object> entry : paramProperties.entrySet()) {
/* 113 */           Object object1 = entry.getKey();
/* 114 */           Object object2 = entry.getValue();
/* 115 */           if (object1 instanceof String && object2 instanceof String) {
/* 116 */             xMLStreamWriterImpl.writeStartElement("entry");
/* 117 */             xMLStreamWriterImpl.writeAttribute("key", (String)object1);
/* 118 */             xMLStreamWriterImpl.writeCharacters((String)object2);
/* 119 */             xMLStreamWriterImpl.writeEndElement();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 124 */       xMLStreamWriterImpl.writeEndElement();
/* 125 */       xMLStreamWriterImpl.writeEndDocument();
/* 126 */       xMLStreamWriterImpl.close();
/* 127 */     } catch (XMLStreamException xMLStreamException) {
/* 128 */       if (xMLStreamException.getCause() instanceof UnsupportedEncodingException) {
/* 129 */         throw (UnsupportedEncodingException)xMLStreamException.getCause();
/*     */       }
/* 131 */       throw new IOException(xMLStreamException);
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
/* 143 */   StringBuffer buf = new StringBuffer();
/*     */   boolean sawComment = false;
/*     */   boolean validEntry = false;
/* 146 */   int rootElem = 0;
/*     */   
/*     */   String key;
/*     */   
/*     */   String rootElm;
/*     */ 
/*     */   
/*     */   public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes) throws SAXException {
/* 154 */     if (this.rootElem < 2) {
/* 155 */       this.rootElem++;
/*     */     }
/*     */     
/* 158 */     if (this.rootElm == null) {
/* 159 */       fatalError(new SAXParseException("An XML properties document must contain the DOCTYPE declaration as defined by java.util.Properties.", null));
/*     */     }
/*     */ 
/*     */     
/* 163 */     if (this.rootElem == 1 && !this.rootElm.equals(paramString3)) {
/* 164 */       fatalError(new SAXParseException("Document root element \"" + paramString3 + "\", must match DOCTYPE root \"" + this.rootElm + "\"", null));
/*     */     }
/*     */     
/* 167 */     if (!"properties, comment, entry".contains(paramString3)) {
/* 168 */       fatalError(new SAXParseException("Element type \"" + paramString3 + "\" must be declared.", null));
/*     */     }
/* 170 */     if (paramString3.equals("entry")) {
/* 171 */       this.validEntry = true;
/* 172 */       this.key = paramAttributes.getValue("key");
/* 173 */       if (this.key == null) {
/* 174 */         fatalError(new SAXParseException("Attribute \"key\" is required and must be specified for element type \"entry\"", null));
/*     */       }
/* 176 */     } else if (paramString3.equals("comment")) {
/* 177 */       if (this.sawComment) {
/* 178 */         fatalError(new SAXParseException("Only one comment element may be allowed. The content of element type \"properties\" must match \"(comment?,entry*)\"", null));
/*     */       }
/*     */       
/* 181 */       this.sawComment = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void characters(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException {
/* 187 */     if (this.validEntry) {
/* 188 */       this.buf.append(paramArrayOfchar, paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void endElement(String paramString1, String paramString2, String paramString3) throws SAXException {
/* 194 */     if (!"properties, comment, entry".contains(paramString3)) {
/* 195 */       fatalError(new SAXParseException("Element: " + paramString3 + " is invalid, must match  \"(comment?,entry*)\".", null));
/*     */     }
/*     */     
/* 198 */     if (this.validEntry) {
/* 199 */       this.properties.setProperty(this.key, this.buf.toString());
/* 200 */       this.buf.delete(0, this.buf.length());
/* 201 */       this.validEntry = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void notationDecl(String paramString1, String paramString2, String paramString3) throws SAXException {
/* 207 */     this.rootElm = paramString1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputSource resolveEntity(String paramString1, String paramString2) throws SAXException, IOException {
/* 214 */     if (paramString2.equals("http://java.sun.com/dtd/properties.dtd")) {
/*     */       
/* 216 */       InputSource inputSource = new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!-- DTD for properties --><!ELEMENT properties ( comment?, entry* ) ><!ATTLIST properties version CDATA #FIXED \"1.0\"><!ELEMENT comment (#PCDATA) ><!ELEMENT entry (#PCDATA) ><!ATTLIST entry  key CDATA #REQUIRED>"));
/* 217 */       inputSource.setSystemId("http://java.sun.com/dtd/properties.dtd");
/* 218 */       return inputSource;
/*     */     } 
/* 220 */     throw new SAXException("Invalid system identifier: " + paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(SAXParseException paramSAXParseException) throws SAXException {
/* 226 */     throw paramSAXParseException;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fatalError(SAXParseException paramSAXParseException) throws SAXException {
/* 231 */     throw paramSAXParseException;
/*     */   }
/*     */ 
/*     */   
/*     */   public void warning(SAXParseException paramSAXParseException) throws SAXException {
/* 236 */     throw paramSAXParseException;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/util/xml/PropertiesDefaultHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */