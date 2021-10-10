/*     */ package sun.util.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.IllegalCharsetNameException;
/*     */ import java.util.InvalidPropertiesFormatException;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import sun.util.spi.XmlPropertiesProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlatformXmlPropertiesProvider
/*     */   extends XmlPropertiesProvider
/*     */ {
/*     */   private static final String PROPS_DTD_URI = "http://java.sun.com/dtd/properties.dtd";
/*     */   private static final String PROPS_DTD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!-- DTD for properties --><!ELEMENT properties ( comment?, entry* ) ><!ATTLIST properties version CDATA #FIXED \"1.0\"><!ELEMENT comment (#PCDATA) ><!ELEMENT entry (#PCDATA) ><!ATTLIST entry  key CDATA #REQUIRED>";
/*     */   private static final String EXTERNAL_XML_VERSION = "1.0";
/*     */   
/*     */   public void load(Properties paramProperties, InputStream paramInputStream) throws IOException, InvalidPropertiesFormatException {
/*  76 */     Document document = null;
/*     */     try {
/*  78 */       document = getLoadingDoc(paramInputStream);
/*  79 */     } catch (SAXException sAXException) {
/*  80 */       throw new InvalidPropertiesFormatException(sAXException);
/*     */     } 
/*  82 */     Element element = document.getDocumentElement();
/*  83 */     String str = element.getAttribute("version");
/*  84 */     if (str.compareTo("1.0") > 0) {
/*  85 */       throw new InvalidPropertiesFormatException("Exported Properties file format version " + str + " is not supported. This java installation can read versions " + "1.0" + " or older. You may need to install a newer version of JDK.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  90 */     importProperties(paramProperties, element);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static Document getLoadingDoc(InputStream paramInputStream) throws SAXException, IOException {
/*  96 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  97 */     documentBuilderFactory.setIgnoringElementContentWhitespace(true);
/*  98 */     documentBuilderFactory.setValidating(true);
/*  99 */     documentBuilderFactory.setCoalescing(true);
/* 100 */     documentBuilderFactory.setIgnoringComments(true);
/*     */     try {
/* 102 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 103 */       documentBuilder.setEntityResolver(new Resolver());
/* 104 */       documentBuilder.setErrorHandler(new EH());
/* 105 */       InputSource inputSource = new InputSource(paramInputStream);
/* 106 */       return documentBuilder.parse(inputSource);
/* 107 */     } catch (ParserConfigurationException parserConfigurationException) {
/* 108 */       throw new Error(parserConfigurationException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void importProperties(Properties paramProperties, Element paramElement) {
/* 113 */     NodeList nodeList = paramElement.getChildNodes();
/* 114 */     int i = nodeList.getLength();
/*     */     
/* 116 */     byte b1 = (i > 0 && nodeList.item(0).getNodeName().equals("comment")) ? 1 : 0;
/* 117 */     for (byte b2 = b1; b2 < i; b2++) {
/* 118 */       Element element = (Element)nodeList.item(b2);
/* 119 */       if (element.hasAttribute("key")) {
/* 120 */         Node node = element.getFirstChild();
/* 121 */         String str = (node == null) ? "" : node.getNodeValue();
/* 122 */         paramProperties.setProperty(element.getAttribute("key"), str);
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
/*     */   public void store(Properties paramProperties, OutputStream paramOutputStream, String paramString1, String paramString2) throws IOException {
/*     */     try {
/* 135 */       Charset.forName(paramString2);
/* 136 */     } catch (IllegalCharsetNameException|java.nio.charset.UnsupportedCharsetException illegalCharsetNameException) {
/* 137 */       throw new UnsupportedEncodingException(paramString2);
/*     */     } 
/* 139 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 140 */     DocumentBuilder documentBuilder = null;
/*     */     try {
/* 142 */       documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 143 */     } catch (ParserConfigurationException parserConfigurationException) {
/*     */       assert false;
/*     */     } 
/* 146 */     Document document = documentBuilder.newDocument();
/*     */     
/* 148 */     Element element = (Element)document.appendChild(document.createElement("properties"));
/*     */     
/* 150 */     if (paramString1 != null) {
/* 151 */       Element element1 = (Element)element.appendChild(document
/* 152 */           .createElement("comment"));
/* 153 */       element1.appendChild(document.createTextNode(paramString1));
/*     */     } 
/*     */     
/* 156 */     synchronized (paramProperties) {
/* 157 */       for (Map.Entry<Object, Object> entry : paramProperties.entrySet()) {
/* 158 */         Object object1 = entry.getKey();
/* 159 */         Object object2 = entry.getValue();
/* 160 */         if (object1 instanceof String && object2 instanceof String) {
/* 161 */           Element element1 = (Element)element.appendChild(document
/* 162 */               .createElement("entry"));
/* 163 */           element1.setAttribute("key", (String)object1);
/* 164 */           element1.appendChild(document.createTextNode((String)object2));
/*     */         } 
/*     */       } 
/*     */     } 
/* 168 */     emitDocument(document, paramOutputStream, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void emitDocument(Document paramDocument, OutputStream paramOutputStream, String paramString) throws IOException {
/* 174 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 175 */     Transformer transformer = null;
/*     */     try {
/* 177 */       transformer = transformerFactory.newTransformer();
/* 178 */       transformer.setOutputProperty("doctype-system", "http://java.sun.com/dtd/properties.dtd");
/* 179 */       transformer.setOutputProperty("indent", "yes");
/* 180 */       transformer.setOutputProperty("method", "xml");
/* 181 */       transformer.setOutputProperty("encoding", paramString);
/* 182 */     } catch (TransformerConfigurationException transformerConfigurationException) {
/*     */       assert false;
/*     */     } 
/* 185 */     DOMSource dOMSource = new DOMSource(paramDocument);
/* 186 */     StreamResult streamResult = new StreamResult(paramOutputStream);
/*     */     try {
/* 188 */       transformer.transform(dOMSource, streamResult);
/* 189 */     } catch (TransformerException transformerException) {
/* 190 */       throw new IOException(transformerException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class Resolver implements EntityResolver {
/*     */     private Resolver() {}
/*     */     
/*     */     public InputSource resolveEntity(String param1String1, String param1String2) throws SAXException {
/* 198 */       if (param1String2.equals("http://java.sun.com/dtd/properties.dtd")) {
/*     */         
/* 200 */         InputSource inputSource = new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!-- DTD for properties --><!ELEMENT properties ( comment?, entry* ) ><!ATTLIST properties version CDATA #FIXED \"1.0\"><!ELEMENT comment (#PCDATA) ><!ELEMENT entry (#PCDATA) ><!ATTLIST entry  key CDATA #REQUIRED>"));
/* 201 */         inputSource.setSystemId("http://java.sun.com/dtd/properties.dtd");
/* 202 */         return inputSource;
/*     */       } 
/* 204 */       throw new SAXException("Invalid system identifier: " + param1String2);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EH implements ErrorHandler {
/*     */     public void error(SAXParseException param1SAXParseException) throws SAXException {
/* 210 */       throw param1SAXParseException;
/*     */     } private EH() {}
/*     */     public void fatalError(SAXParseException param1SAXParseException) throws SAXException {
/* 213 */       throw param1SAXParseException;
/*     */     }
/*     */     public void warning(SAXParseException param1SAXParseException) throws SAXException {
/* 216 */       throw param1SAXParseException;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/xml/PlatformXmlPropertiesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */