/*     */ package java.util.prefs;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.StringReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.DOMImplementation;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentType;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XmlSupport
/*     */ {
/*     */   private static final String PREFS_DTD_URI = "http://java.sun.com/dtd/preferences.dtd";
/*     */   private static final String PREFS_DTD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!-- DTD for preferences --><!ELEMENT preferences (root) ><!ATTLIST preferences EXTERNAL_XML_VERSION CDATA \"0.0\"  ><!ELEMENT root (map, node*) ><!ATTLIST root          type (system|user) #REQUIRED ><!ELEMENT node (map, node*) ><!ATTLIST node          name CDATA #REQUIRED ><!ELEMENT map (entry*) ><!ATTLIST map  MAP_XML_VERSION CDATA \"0.0\"  ><!ELEMENT entry EMPTY ><!ATTLIST entry          key CDATA #REQUIRED          value CDATA #REQUIRED >";
/*     */   private static final String EXTERNAL_XML_VERSION = "1.0";
/*     */   private static final String MAP_XML_VERSION = "1.0";
/*     */   
/*     */   static void export(OutputStream paramOutputStream, Preferences paramPreferences, boolean paramBoolean) throws IOException, BackingStoreException {
/*  99 */     if (((AbstractPreferences)paramPreferences).isRemoved())
/* 100 */       throw new IllegalStateException("Node has been removed"); 
/* 101 */     Document document = createPrefsDoc("preferences");
/* 102 */     Element element1 = document.getDocumentElement();
/* 103 */     element1.setAttribute("EXTERNAL_XML_VERSION", "1.0");
/*     */     
/* 105 */     Element element2 = (Element)element1.appendChild(document.createElement("root"));
/* 106 */     element2.setAttribute("type", paramPreferences.isUserNode() ? "user" : "system");
/*     */ 
/*     */     
/* 109 */     ArrayList<Preferences> arrayList = new ArrayList();
/*     */     
/* 111 */     for (Preferences preferences1 = paramPreferences, preferences2 = preferences1.parent(); preferences2 != null; 
/* 112 */       preferences1 = preferences2, preferences2 = preferences1.parent()) {
/* 113 */       arrayList.add(preferences1);
/*     */     }
/* 115 */     Element element3 = element2;
/* 116 */     for (int i = arrayList.size() - 1; i >= 0; i--) {
/* 117 */       element3.appendChild(document.createElement("map"));
/* 118 */       element3 = (Element)element3.appendChild(document.createElement("node"));
/* 119 */       element3.setAttribute("name", ((Preferences)arrayList.get(i)).name());
/*     */     } 
/* 121 */     putPreferencesInXml(element3, document, paramPreferences, paramBoolean);
/*     */     
/* 123 */     writeDoc(document, paramOutputStream);
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
/*     */   private static void putPreferencesInXml(Element paramElement, Document paramDocument, Preferences paramPreferences, boolean paramBoolean) throws BackingStoreException {
/* 141 */     Preferences[] arrayOfPreferences = null;
/* 142 */     String[] arrayOfString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     synchronized (((AbstractPreferences)paramPreferences).lock) {
/*     */ 
/*     */       
/* 150 */       if (((AbstractPreferences)paramPreferences).isRemoved()) {
/* 151 */         paramElement.getParentNode().removeChild(paramElement);
/*     */         
/*     */         return;
/*     */       } 
/* 155 */       String[] arrayOfString1 = paramPreferences.keys();
/* 156 */       Element element = (Element)paramElement.appendChild(paramDocument.createElement("map")); byte b;
/* 157 */       for (b = 0; b < arrayOfString1.length; b++) {
/*     */         
/* 159 */         Element element1 = (Element)element.appendChild(paramDocument.createElement("entry"));
/* 160 */         element1.setAttribute("key", arrayOfString1[b]);
/*     */         
/* 162 */         element1.setAttribute("value", paramPreferences.get(arrayOfString1[b], null));
/*     */       } 
/*     */       
/* 165 */       if (paramBoolean) {
/*     */         
/* 167 */         arrayOfString = paramPreferences.childrenNames();
/* 168 */         arrayOfPreferences = new Preferences[arrayOfString.length];
/* 169 */         for (b = 0; b < arrayOfString.length; b++) {
/* 170 */           arrayOfPreferences[b] = paramPreferences.node(arrayOfString[b]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     if (paramBoolean) {
/* 176 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*     */         
/* 178 */         Element element = (Element)paramElement.appendChild(paramDocument.createElement("node"));
/* 179 */         element.setAttribute("name", arrayOfString[b]);
/* 180 */         putPreferencesInXml(element, paramDocument, arrayOfPreferences[b], paramBoolean);
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
/*     */   static void importPreferences(InputStream paramInputStream) throws IOException, InvalidPreferencesFormatException {
/*     */     try {
/* 199 */       Document document = loadPrefsDoc(paramInputStream);
/*     */       
/* 201 */       String str = document.getDocumentElement().getAttribute("EXTERNAL_XML_VERSION");
/* 202 */       if (str.compareTo("1.0") > 0) {
/* 203 */         throw new InvalidPreferencesFormatException("Exported preferences file format version " + str + " is not supported. This java installation can read versions " + "1.0" + " or older. You may need to install a newer version of JDK.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 210 */       Element element = (Element)document.getDocumentElement().getChildNodes().item(0);
/*     */ 
/*     */       
/* 213 */       Preferences preferences = element.getAttribute("type").equals("user") ? Preferences.userRoot() : Preferences.systemRoot();
/* 214 */       ImportSubtree(preferences, element);
/* 215 */     } catch (SAXException sAXException) {
/* 216 */       throw new InvalidPreferencesFormatException(sAXException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Document createPrefsDoc(String paramString) {
/*     */     try {
/* 226 */       DOMImplementation dOMImplementation = DocumentBuilderFactory.newInstance().newDocumentBuilder().getDOMImplementation();
/* 227 */       DocumentType documentType = dOMImplementation.createDocumentType(paramString, null, "http://java.sun.com/dtd/preferences.dtd");
/* 228 */       return dOMImplementation.createDocument(null, paramString, documentType);
/* 229 */     } catch (ParserConfigurationException parserConfigurationException) {
/* 230 */       throw new AssertionError(parserConfigurationException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Document loadPrefsDoc(InputStream paramInputStream) throws SAXException, IOException {
/* 241 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 242 */     documentBuilderFactory.setIgnoringElementContentWhitespace(true);
/* 243 */     documentBuilderFactory.setValidating(true);
/* 244 */     documentBuilderFactory.setCoalescing(true);
/* 245 */     documentBuilderFactory.setIgnoringComments(true);
/*     */     try {
/* 247 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 248 */       documentBuilder.setEntityResolver(new Resolver());
/* 249 */       documentBuilder.setErrorHandler(new EH());
/* 250 */       return documentBuilder.parse(new InputSource(paramInputStream));
/* 251 */     } catch (ParserConfigurationException parserConfigurationException) {
/* 252 */       throw new AssertionError(parserConfigurationException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final void writeDoc(Document paramDocument, OutputStream paramOutputStream) throws IOException {
/*     */     try {
/* 263 */       TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*     */       try {
/* 265 */         transformerFactory.setAttribute("indent-number", new Integer(2));
/* 266 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*     */ 
/*     */ 
/*     */       
/* 270 */       Transformer transformer = transformerFactory.newTransformer();
/* 271 */       transformer.setOutputProperty("doctype-system", paramDocument.getDoctype().getSystemId());
/* 272 */       transformer.setOutputProperty("indent", "yes");
/*     */ 
/*     */ 
/*     */       
/* 276 */       transformer.transform(new DOMSource(paramDocument), new StreamResult(new BufferedWriter(new OutputStreamWriter(paramOutputStream, "UTF-8"))));
/*     */     }
/* 278 */     catch (TransformerException transformerException) {
/* 279 */       throw new AssertionError(transformerException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void ImportSubtree(Preferences paramPreferences, Element paramElement) {
/*     */     Preferences[] arrayOfPreferences;
/* 289 */     NodeList nodeList = paramElement.getChildNodes();
/* 290 */     int i = nodeList.getLength();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     synchronized (((AbstractPreferences)paramPreferences).lock) {
/*     */       
/* 301 */       if (((AbstractPreferences)paramPreferences).isRemoved()) {
/*     */         return;
/*     */       }
/*     */       
/* 305 */       Element element = (Element)nodeList.item(0);
/* 306 */       ImportPrefs(paramPreferences, element);
/* 307 */       arrayOfPreferences = new Preferences[i - 1];
/*     */ 
/*     */       
/* 310 */       for (byte b1 = 1; b1 < i; b1++) {
/* 311 */         Element element1 = (Element)nodeList.item(b1);
/* 312 */         arrayOfPreferences[b1 - 1] = paramPreferences.node(element1.getAttribute("name"));
/*     */       } 
/*     */     } 
/*     */     
/* 316 */     for (byte b = 1; b < i; b++) {
/* 317 */       ImportSubtree(arrayOfPreferences[b - 1], (Element)nodeList.item(b));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void ImportPrefs(Preferences paramPreferences, Element paramElement) {
/* 326 */     NodeList nodeList = paramElement.getChildNodes(); byte b; int i;
/* 327 */     for (b = 0, i = nodeList.getLength(); b < i; b++) {
/* 328 */       Element element = (Element)nodeList.item(b);
/* 329 */       paramPreferences.put(element.getAttribute("key"), element
/* 330 */           .getAttribute("value"));
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
/*     */   static void exportMap(OutputStream paramOutputStream, Map<String, String> paramMap) throws IOException {
/* 343 */     Document document = createPrefsDoc("map");
/* 344 */     Element element = document.getDocumentElement();
/* 345 */     element.setAttribute("MAP_XML_VERSION", "1.0");
/*     */     
/* 347 */     for (Map.Entry<String, String> entry : paramMap.entrySet()) {
/*     */ 
/*     */       
/* 350 */       Element element1 = (Element)element.appendChild(document.createElement("entry"));
/* 351 */       element1.setAttribute("key", (String)entry.getKey());
/* 352 */       element1.setAttribute("value", (String)entry.getValue());
/*     */     } 
/*     */     
/* 355 */     writeDoc(document, paramOutputStream);
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
/*     */   static void importMap(InputStream paramInputStream, Map<String, String> paramMap) throws IOException, InvalidPreferencesFormatException {
/*     */     try {
/* 375 */       Document document = loadPrefsDoc(paramInputStream);
/* 376 */       Element element = document.getDocumentElement();
/*     */       
/* 378 */       String str = element.getAttribute("MAP_XML_VERSION");
/* 379 */       if (str.compareTo("1.0") > 0) {
/* 380 */         throw new InvalidPreferencesFormatException("Preferences map file format version " + str + " is not supported. This java installation can read versions " + "1.0" + " or older. You may need to install a newer version of JDK.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 386 */       NodeList nodeList = element.getChildNodes(); byte b; int i;
/* 387 */       for (b = 0, i = nodeList.getLength(); b < i; b++) {
/* 388 */         Element element1 = (Element)nodeList.item(b);
/* 389 */         paramMap.put(element1.getAttribute("key"), element1.getAttribute("value"));
/*     */       } 
/* 391 */     } catch (SAXException sAXException) {
/* 392 */       throw new InvalidPreferencesFormatException(sAXException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class Resolver implements EntityResolver {
/*     */     private Resolver() {}
/*     */     
/*     */     public InputSource resolveEntity(String param1String1, String param1String2) throws SAXException {
/* 400 */       if (param1String2.equals("http://java.sun.com/dtd/preferences.dtd")) {
/*     */         
/* 402 */         InputSource inputSource = new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!-- DTD for preferences --><!ELEMENT preferences (root) ><!ATTLIST preferences EXTERNAL_XML_VERSION CDATA \"0.0\"  ><!ELEMENT root (map, node*) ><!ATTLIST root          type (system|user) #REQUIRED ><!ELEMENT node (map, node*) ><!ATTLIST node          name CDATA #REQUIRED ><!ELEMENT map (entry*) ><!ATTLIST map  MAP_XML_VERSION CDATA \"0.0\"  ><!ELEMENT entry EMPTY ><!ATTLIST entry          key CDATA #REQUIRED          value CDATA #REQUIRED >"));
/* 403 */         inputSource.setSystemId("http://java.sun.com/dtd/preferences.dtd");
/* 404 */         return inputSource;
/*     */       } 
/* 406 */       throw new SAXException("Invalid system identifier: " + param1String2);
/*     */     } }
/*     */   
/*     */   private static class EH implements ErrorHandler { private EH() {}
/*     */     
/*     */     public void error(SAXParseException param1SAXParseException) throws SAXException {
/* 412 */       throw param1SAXParseException;
/*     */     }
/*     */     public void fatalError(SAXParseException param1SAXParseException) throws SAXException {
/* 415 */       throw param1SAXParseException;
/*     */     }
/*     */     public void warning(SAXParseException param1SAXParseException) throws SAXException {
/* 418 */       throw param1SAXParseException;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/prefs/XmlSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */