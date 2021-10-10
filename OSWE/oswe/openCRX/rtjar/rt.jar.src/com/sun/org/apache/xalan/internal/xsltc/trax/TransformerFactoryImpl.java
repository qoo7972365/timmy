/*      */ package com.sun.org.apache.xalan.internal.xsltc.trax;
/*      */ 
/*      */ import com.sun.org.apache.xalan.internal.XalanConstants;
/*      */ import com.sun.org.apache.xalan.internal.utils.FeaturePropertyBase;
/*      */ import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
/*      */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xalan.internal.utils.XMLSecurityPropertyManager;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.SourceLoader;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.XSLTC;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.dom.XSLTCDTMManager;
/*      */ import com.sun.org.apache.xml.internal.utils.StopParseException;
/*      */ import com.sun.org.apache.xml.internal.utils.StylesheetPIHandler;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FilenameFilter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import java.util.zip.ZipEntry;
/*      */ import java.util.zip.ZipFile;
/*      */ import javax.xml.transform.ErrorListener;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.Templates;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerConfigurationException;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.URIResolver;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import javax.xml.transform.sax.SAXSource;
/*      */ import javax.xml.transform.sax.SAXTransformerFactory;
/*      */ import javax.xml.transform.sax.TemplatesHandler;
/*      */ import javax.xml.transform.sax.TransformerHandler;
/*      */ import jdk.xml.internal.JdkXmlFeatures;
/*      */ import jdk.xml.internal.JdkXmlUtils;
/*      */ import org.w3c.dom.Node;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.XMLFilter;
/*      */ import org.xml.sax.XMLReader;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TransformerFactoryImpl
/*      */   extends SAXTransformerFactory
/*      */   implements SourceLoader, ErrorListener
/*      */ {
/*      */   public static final String TRANSLET_NAME = "translet-name";
/*      */   public static final String DESTINATION_DIRECTORY = "destination-directory";
/*      */   public static final String PACKAGE_NAME = "package-name";
/*      */   public static final String JAR_NAME = "jar-name";
/*      */   public static final String GENERATE_TRANSLET = "generate-translet";
/*      */   public static final String AUTO_TRANSLET = "auto-translet";
/*      */   public static final String USE_CLASSPATH = "use-classpath";
/*      */   public static final String DEBUG = "debug";
/*      */   public static final String ENABLE_INLINING = "enable-inlining";
/*      */   public static final String INDENT_NUMBER = "indent-number";
/*  102 */   private ErrorListener _errorListener = this;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  107 */   private URIResolver _uriResolver = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String DEFAULT_TRANSLET_NAME = "GregorSamsa";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  124 */   private String _transletName = "GregorSamsa";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  129 */   private String _destinationDirectory = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  134 */   private String _packageName = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  139 */   private String _jarFileName = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  145 */   private Map<Source, PIParamWrapper> _piParams = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private static class PIParamWrapper
/*      */   {
/*  151 */     public String _media = null;
/*  152 */     public String _title = null;
/*  153 */     public String _charset = null;
/*      */     
/*      */     public PIParamWrapper(String media, String title, String charset) {
/*  156 */       this._media = media;
/*  157 */       this._title = title;
/*  158 */       this._charset = charset;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _debug = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _enableInlining = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _generateTranslet = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _autoTranslet = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _useClasspath = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  195 */   private int _indentNumber = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _isNotSecureProcessing = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _isSecureMode = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _overrideDefaultParser;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  216 */   private String _accessExternalStylesheet = "all";
/*      */ 
/*      */ 
/*      */   
/*  220 */   private String _accessExternalDTD = "all";
/*      */   
/*      */   private XMLSecurityPropertyManager _xmlSecurityPropertyMgr;
/*      */   
/*      */   private XMLSecurityManager _xmlSecurityManager;
/*      */   
/*      */   private final JdkXmlFeatures _xmlFeatures;
/*  227 */   private ClassLoader _extensionClassLoader = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<String, Class> _xsltcExtensionFunctions;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TransformerFactoryImpl() {
/*  241 */     if (System.getSecurityManager() != null) {
/*  242 */       this._isSecureMode = true;
/*  243 */       this._isNotSecureProcessing = false;
/*      */     } 
/*      */     
/*  246 */     this._xmlFeatures = new JdkXmlFeatures(!this._isNotSecureProcessing);
/*  247 */     this._overrideDefaultParser = this._xmlFeatures.getFeature(JdkXmlFeatures.XmlFeature.JDK_OVERRIDE_PARSER);
/*      */     
/*  249 */     this._xmlSecurityPropertyMgr = new XMLSecurityPropertyManager();
/*  250 */     this._accessExternalDTD = this._xmlSecurityPropertyMgr.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD);
/*      */     
/*  252 */     this._accessExternalStylesheet = this._xmlSecurityPropertyMgr.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_STYLESHEET);
/*      */ 
/*      */ 
/*      */     
/*  256 */     this._xmlSecurityManager = new XMLSecurityManager(true);
/*      */     
/*  258 */     this._xsltcExtensionFunctions = null;
/*      */   }
/*      */   
/*      */   public Map<String, Class> getExternalExtensionsMap() {
/*  262 */     return this._xsltcExtensionFunctions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setErrorListener(ErrorListener listener) throws IllegalArgumentException {
/*  278 */     if (listener == null) {
/*  279 */       ErrorMsg err = new ErrorMsg("ERROR_LISTENER_NULL_ERR", "TransformerFactory");
/*      */       
/*  281 */       throw new IllegalArgumentException(err.toString());
/*      */     } 
/*  283 */     this._errorListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ErrorListener getErrorListener() {
/*  294 */     return this._errorListener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getAttribute(String name) throws IllegalArgumentException {
/*  310 */     if (name.equals("translet-name")) {
/*  311 */       return this._transletName;
/*      */     }
/*  313 */     if (name.equals("generate-translet")) {
/*  314 */       return new Boolean(this._generateTranslet);
/*      */     }
/*  316 */     if (name.equals("auto-translet")) {
/*  317 */       return new Boolean(this._autoTranslet);
/*      */     }
/*  319 */     if (name.equals("enable-inlining")) {
/*  320 */       if (this._enableInlining) {
/*  321 */         return Boolean.TRUE;
/*      */       }
/*  323 */       return Boolean.FALSE;
/*  324 */     }  if (name.equals("http://apache.org/xml/properties/security-manager"))
/*  325 */       return this._xmlSecurityManager; 
/*  326 */     if (name.equals("jdk.xml.transform.extensionClassLoader")) {
/*  327 */       return this._extensionClassLoader;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  332 */     String propertyValue = (this._xmlSecurityManager != null) ? this._xmlSecurityManager.getLimitAsString(name) : null;
/*  333 */     if (propertyValue != null) {
/*  334 */       return propertyValue;
/*      */     }
/*      */     
/*  337 */     propertyValue = (this._xmlSecurityPropertyMgr != null) ? this._xmlSecurityPropertyMgr.getValue(name) : null;
/*  338 */     if (propertyValue != null) {
/*  339 */       return propertyValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  344 */     ErrorMsg err = new ErrorMsg("JAXP_INVALID_ATTR_ERR", name);
/*  345 */     throw new IllegalArgumentException(err.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttribute(String name, Object value) throws IllegalArgumentException {
/*  362 */     if (name.equals("translet-name") && value instanceof String) {
/*  363 */       this._transletName = (String)value;
/*      */       return;
/*      */     } 
/*  366 */     if (name.equals("destination-directory") && value instanceof String) {
/*  367 */       this._destinationDirectory = (String)value;
/*      */       return;
/*      */     } 
/*  370 */     if (name.equals("package-name") && value instanceof String) {
/*  371 */       this._packageName = (String)value;
/*      */       return;
/*      */     } 
/*  374 */     if (name.equals("jar-name") && value instanceof String) {
/*  375 */       this._jarFileName = (String)value;
/*      */       return;
/*      */     } 
/*  378 */     if (name.equals("generate-translet")) {
/*  379 */       if (value instanceof Boolean) {
/*  380 */         this._generateTranslet = ((Boolean)value).booleanValue();
/*      */         return;
/*      */       } 
/*  383 */       if (value instanceof String) {
/*  384 */         this._generateTranslet = ((String)value).equalsIgnoreCase("true");
/*      */         
/*      */         return;
/*      */       } 
/*  388 */     } else if (name.equals("auto-translet")) {
/*  389 */       if (value instanceof Boolean) {
/*  390 */         this._autoTranslet = ((Boolean)value).booleanValue();
/*      */         return;
/*      */       } 
/*  393 */       if (value instanceof String) {
/*  394 */         this._autoTranslet = ((String)value).equalsIgnoreCase("true");
/*      */         
/*      */         return;
/*      */       } 
/*  398 */     } else if (name.equals("use-classpath")) {
/*  399 */       if (value instanceof Boolean) {
/*  400 */         this._useClasspath = ((Boolean)value).booleanValue();
/*      */         return;
/*      */       } 
/*  403 */       if (value instanceof String) {
/*  404 */         this._useClasspath = ((String)value).equalsIgnoreCase("true");
/*      */         
/*      */         return;
/*      */       } 
/*  408 */     } else if (name.equals("debug")) {
/*  409 */       if (value instanceof Boolean) {
/*  410 */         this._debug = ((Boolean)value).booleanValue();
/*      */         return;
/*      */       } 
/*  413 */       if (value instanceof String) {
/*  414 */         this._debug = ((String)value).equalsIgnoreCase("true");
/*      */         
/*      */         return;
/*      */       } 
/*  418 */     } else if (name.equals("enable-inlining")) {
/*  419 */       if (value instanceof Boolean) {
/*  420 */         this._enableInlining = ((Boolean)value).booleanValue();
/*      */         return;
/*      */       } 
/*  423 */       if (value instanceof String) {
/*  424 */         this._enableInlining = ((String)value).equalsIgnoreCase("true");
/*      */         
/*      */         return;
/*      */       } 
/*  428 */     } else if (name.equals("indent-number")) {
/*  429 */       if (value instanceof String) {
/*      */         try {
/*  431 */           this._indentNumber = Integer.parseInt((String)value);
/*      */           
/*      */           return;
/*  434 */         } catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */       
/*      */       }
/*  438 */       else if (value instanceof Integer) {
/*  439 */         this._indentNumber = ((Integer)value).intValue();
/*      */         
/*      */         return;
/*      */       } 
/*  443 */     } else if (name.equals("jdk.xml.transform.extensionClassLoader")) {
/*  444 */       if (value instanceof ClassLoader) {
/*  445 */         this._extensionClassLoader = (ClassLoader)value;
/*      */         return;
/*      */       } 
/*  448 */       ErrorMsg errorMsg = new ErrorMsg("JAXP_INVALID_ATTR_VALUE_ERR", "Extension Functions ClassLoader");
/*      */       
/*  450 */       throw new IllegalArgumentException(errorMsg.toString());
/*      */     } 
/*      */ 
/*      */     
/*  454 */     if (this._xmlSecurityManager != null && this._xmlSecurityManager
/*  455 */       .setLimit(name, XMLSecurityManager.State.APIPROPERTY, value)) {
/*      */       return;
/*      */     }
/*      */     
/*  459 */     if (this._xmlSecurityPropertyMgr != null && this._xmlSecurityPropertyMgr
/*  460 */       .setValue(name, FeaturePropertyBase.State.APIPROPERTY, value)) {
/*  461 */       this._accessExternalDTD = this._xmlSecurityPropertyMgr.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD);
/*      */       
/*  463 */       this._accessExternalStylesheet = this._xmlSecurityPropertyMgr.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_STYLESHEET);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  469 */     ErrorMsg err = new ErrorMsg("JAXP_INVALID_ATTR_ERR", name);
/*      */     
/*  471 */     throw new IllegalArgumentException(err.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFeature(String name, boolean value) throws TransformerConfigurationException {
/*  500 */     if (name == null) {
/*  501 */       ErrorMsg err = new ErrorMsg("JAXP_SET_FEATURE_NULL_NAME");
/*  502 */       throw new NullPointerException(err.toString());
/*      */     } 
/*      */     
/*  505 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/*  506 */       if (this._isSecureMode && !value) {
/*  507 */         ErrorMsg err = new ErrorMsg("JAXP_SECUREPROCESSING_FEATURE");
/*  508 */         throw new TransformerConfigurationException(err.toString());
/*      */       } 
/*  510 */       this._isNotSecureProcessing = !value;
/*  511 */       this._xmlSecurityManager.setSecureProcessing(value);
/*      */ 
/*      */       
/*  514 */       if (value && XalanConstants.IS_JDK8_OR_ABOVE) {
/*  515 */         this._xmlSecurityPropertyMgr.setValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD, FeaturePropertyBase.State.FSP, "");
/*      */         
/*  517 */         this._xmlSecurityPropertyMgr.setValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_STYLESHEET, FeaturePropertyBase.State.FSP, "");
/*      */         
/*  519 */         this._accessExternalDTD = this._xmlSecurityPropertyMgr.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD);
/*      */         
/*  521 */         this._accessExternalStylesheet = this._xmlSecurityPropertyMgr.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_STYLESHEET);
/*      */       } 
/*      */ 
/*      */       
/*  525 */       if (value && this._xmlFeatures != null) {
/*  526 */         this._xmlFeatures.setFeature(JdkXmlFeatures.XmlFeature.ENABLE_EXTENSION_FUNCTION, JdkXmlFeatures.State.FSP, false);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  531 */       if (name.equals("http://www.oracle.com/feature/use-service-mechanism"))
/*      */       {
/*  533 */         if (this._isSecureMode) {
/*      */           return;
/*      */         }
/*      */       }
/*  537 */       if (this._xmlFeatures != null && this._xmlFeatures
/*  538 */         .setFeature(name, JdkXmlFeatures.State.APIPROPERTY, Boolean.valueOf(value))) {
/*  539 */         if (name.equals("jdk.xml.overrideDefaultParser") || name
/*  540 */           .equals("http://www.oracle.com/feature/use-service-mechanism")) {
/*  541 */           this._overrideDefaultParser = this._xmlFeatures.getFeature(JdkXmlFeatures.XmlFeature.JDK_OVERRIDE_PARSER);
/*      */         }
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  548 */       ErrorMsg err = new ErrorMsg("JAXP_UNSUPPORTED_FEATURE", name);
/*  549 */       throw new TransformerConfigurationException(err.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFeature(String name) {
/*  565 */     String[] features = { "http://javax.xml.transform.dom.DOMSource/feature", "http://javax.xml.transform.dom.DOMResult/feature", "http://javax.xml.transform.sax.SAXSource/feature", "http://javax.xml.transform.sax.SAXResult/feature", "http://javax.xml.transform.stax.StAXSource/feature", "http://javax.xml.transform.stax.StAXResult/feature", "http://javax.xml.transform.stream.StreamSource/feature", "http://javax.xml.transform.stream.StreamResult/feature", "http://javax.xml.transform.sax.SAXTransformerFactory/feature", "http://javax.xml.transform.sax.SAXTransformerFactory/feature/xmlfilter", "http://www.oracle.com/feature/use-service-mechanism" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  580 */     if (name == null) {
/*  581 */       ErrorMsg err = new ErrorMsg("JAXP_GET_FEATURE_NULL_NAME");
/*  582 */       throw new NullPointerException(err.toString());
/*      */     } 
/*      */ 
/*      */     
/*  586 */     for (int i = 0; i < features.length; i++) {
/*  587 */       if (name.equals(features[i])) {
/*  588 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  592 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/*  593 */       return !this._isNotSecureProcessing;
/*      */     }
/*      */ 
/*      */     
/*  597 */     int index = this._xmlFeatures.getIndex(name);
/*  598 */     if (index > -1) {
/*  599 */       return this._xmlFeatures.getFeature(index);
/*      */     }
/*      */ 
/*      */     
/*  603 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean overrideDefaultParser() {
/*  609 */     return this._overrideDefaultParser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JdkXmlFeatures getJdkXmlFeatures() {
/*  616 */     return this._xmlFeatures;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URIResolver getURIResolver() {
/*  629 */     return this._uriResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setURIResolver(URIResolver resolver) {
/*  644 */     this._uriResolver = resolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Source getAssociatedStylesheet(Source source, String media, String title, String charset) throws TransformerConfigurationException {
/*  668 */     XMLReader reader = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  674 */     StylesheetPIHandler _stylesheetPIHandler = new StylesheetPIHandler(null, media, title, charset);
/*      */ 
/*      */     
/*      */     try {
/*  678 */       if (source instanceof DOMSource) {
/*  679 */         DOMSource domsrc = (DOMSource)source;
/*  680 */         String baseId = domsrc.getSystemId();
/*  681 */         Node node = domsrc.getNode();
/*  682 */         DOM2SAX dom2sax = new DOM2SAX(node);
/*      */         
/*  684 */         _stylesheetPIHandler.setBaseId(baseId);
/*      */         
/*  686 */         dom2sax.setContentHandler(_stylesheetPIHandler);
/*  687 */         dom2sax.parse();
/*      */       } else {
/*  689 */         if (source instanceof SAXSource) {
/*  690 */           reader = ((SAXSource)source).getXMLReader();
/*      */         }
/*  692 */         InputSource isource = SAXSource.sourceToInputSource(source);
/*  693 */         String baseId = isource.getSystemId();
/*      */         
/*  695 */         if (reader == null) {
/*  696 */           reader = JdkXmlUtils.getXMLReader(this._overrideDefaultParser, !this._isNotSecureProcessing);
/*      */         }
/*      */ 
/*      */         
/*  700 */         _stylesheetPIHandler.setBaseId(baseId);
/*  701 */         reader.setContentHandler(_stylesheetPIHandler);
/*  702 */         reader.parse(isource);
/*      */       } 
/*      */ 
/*      */       
/*  706 */       if (this._uriResolver != null) {
/*  707 */         _stylesheetPIHandler.setURIResolver(this._uriResolver);
/*      */       }
/*      */     }
/*  710 */     catch (StopParseException stopParseException) {
/*      */     
/*  712 */     } catch (SAXException se) {
/*  713 */       throw new TransformerConfigurationException("getAssociatedStylesheets failed", se);
/*      */     }
/*  715 */     catch (IOException ioe) {
/*  716 */       throw new TransformerConfigurationException("getAssociatedStylesheets failed", ioe);
/*      */     } 
/*      */ 
/*      */     
/*  720 */     return _stylesheetPIHandler.getAssociatedStylesheet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Transformer newTransformer() throws TransformerConfigurationException {
/*  735 */     TransformerImpl result = new TransformerImpl(new Properties(), this._indentNumber, this);
/*      */     
/*  737 */     if (this._uriResolver != null) {
/*  738 */       result.setURIResolver(this._uriResolver);
/*      */     }
/*      */     
/*  741 */     if (!this._isNotSecureProcessing) {
/*  742 */       result.setSecureProcessing(true);
/*      */     }
/*  744 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Transformer newTransformer(Source source) throws TransformerConfigurationException {
/*  761 */     Templates templates = newTemplates(source);
/*  762 */     Transformer transformer = templates.newTransformer();
/*  763 */     if (this._uriResolver != null) {
/*  764 */       transformer.setURIResolver(this._uriResolver);
/*      */     }
/*  766 */     return transformer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void passWarningsToListener(ArrayList<ErrorMsg> messages) throws TransformerException {
/*  775 */     if (this._errorListener == null || messages == null) {
/*      */       return;
/*      */     }
/*      */     
/*  779 */     int count = messages.size();
/*  780 */     for (int pos = 0; pos < count; pos++) {
/*  781 */       ErrorMsg msg = messages.get(pos);
/*      */       
/*  783 */       if (msg.isWarningError()) {
/*  784 */         this._errorListener.error(new TransformerConfigurationException(msg
/*  785 */               .toString()));
/*      */       } else {
/*  787 */         this._errorListener.warning(new TransformerConfigurationException(msg
/*  788 */               .toString()));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void passErrorsToListener(ArrayList<ErrorMsg> messages) {
/*      */     try {
/*  797 */       if (this._errorListener == null || messages == null) {
/*      */         return;
/*      */       }
/*      */       
/*  801 */       int count = messages.size();
/*  802 */       for (int pos = 0; pos < count; pos++) {
/*  803 */         String message = ((ErrorMsg)messages.get(pos)).toString();
/*  804 */         this._errorListener.error(new TransformerException(message));
/*      */       }
/*      */     
/*  807 */     } catch (TransformerException transformerException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Templates newTemplates(Source source) throws TransformerConfigurationException {
/*  828 */     if (this._useClasspath) {
/*  829 */       String str = getTransletBaseName(source);
/*      */       
/*  831 */       if (this._packageName != null) {
/*  832 */         str = this._packageName + "." + str;
/*      */       }
/*      */       try {
/*  835 */         Class<?> clazz = ObjectFactory.findProviderClass(str, true);
/*  836 */         resetTransientAttributes();
/*      */         
/*  838 */         return new TemplatesImpl(new Class[] { clazz }, str, null, this._indentNumber, this);
/*      */       }
/*  840 */       catch (ClassNotFoundException cnfe) {
/*  841 */         ErrorMsg err = new ErrorMsg("CLASS_NOT_FOUND_ERR", str);
/*  842 */         throw new TransformerConfigurationException(err.toString());
/*      */       }
/*  844 */       catch (Exception e) {
/*      */ 
/*      */         
/*  847 */         ErrorMsg err = new ErrorMsg(new ErrorMsg("RUNTIME_ERROR_KEY") + e.getMessage());
/*  848 */         throw new TransformerConfigurationException(err.toString());
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  854 */     if (this._autoTranslet) {
/*      */       byte[][] arrayOfByte;
/*  856 */       String transletClassName = getTransletBaseName(source);
/*      */       
/*  858 */       if (this._packageName != null) {
/*  859 */         transletClassName = this._packageName + "." + transletClassName;
/*      */       }
/*  861 */       if (this._jarFileName != null) {
/*  862 */         arrayOfByte = getBytecodesFromJar(source, transletClassName);
/*      */       } else {
/*  864 */         arrayOfByte = getBytecodesFromClasses(source, transletClassName);
/*      */       } 
/*  866 */       if (arrayOfByte != null) {
/*  867 */         if (this._debug) {
/*  868 */           if (this._jarFileName != null) {
/*  869 */             System.err.println(new ErrorMsg("TRANSFORM_WITH_JAR_STR", transletClassName, this._jarFileName));
/*      */           } else {
/*      */             
/*  872 */             System.err.println(new ErrorMsg("TRANSFORM_WITH_TRANSLET_STR", transletClassName));
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  878 */         resetTransientAttributes();
/*  879 */         return new TemplatesImpl(arrayOfByte, transletClassName, null, this._indentNumber, this);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  884 */     XSLTC xsltc = new XSLTC(this._xmlFeatures);
/*  885 */     if (this._debug) xsltc.setDebug(true); 
/*  886 */     if (this._enableInlining) {
/*  887 */       xsltc.setTemplateInlining(true);
/*      */     } else {
/*  889 */       xsltc.setTemplateInlining(false);
/*      */     } 
/*  891 */     if (!this._isNotSecureProcessing) xsltc.setSecureProcessing(true); 
/*  892 */     xsltc.setProperty("http://javax.xml.XMLConstants/property/accessExternalStylesheet", this._accessExternalStylesheet);
/*  893 */     xsltc.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", this._accessExternalDTD);
/*  894 */     xsltc.setProperty("http://apache.org/xml/properties/security-manager", this._xmlSecurityManager);
/*  895 */     xsltc.setProperty("jdk.xml.transform.extensionClassLoader", this._extensionClassLoader);
/*  896 */     xsltc.init();
/*  897 */     if (!this._isNotSecureProcessing) {
/*  898 */       this._xsltcExtensionFunctions = xsltc.getExternalExtensionFunctions();
/*      */     }
/*  900 */     if (this._uriResolver != null) {
/*  901 */       xsltc.setSourceLoader(this);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  906 */     if (this._piParams != null && this._piParams.get(source) != null) {
/*      */       
/*  908 */       PIParamWrapper p = this._piParams.get(source);
/*      */       
/*  910 */       if (p != null) {
/*  911 */         xsltc.setPIParameters(p._media, p._title, p._charset);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  916 */     int outputType = 2;
/*  917 */     if (this._generateTranslet || this._autoTranslet) {
/*      */       
/*  919 */       xsltc.setClassName(getTransletBaseName(source));
/*      */       
/*  921 */       if (this._destinationDirectory != null) {
/*  922 */         xsltc.setDestDirectory(this._destinationDirectory);
/*      */       } else {
/*  924 */         String xslName = getStylesheetFileName(source);
/*  925 */         if (xslName != null) {
/*  926 */           File xslFile = new File(xslName);
/*  927 */           String xslDir = xslFile.getParent();
/*      */           
/*  929 */           if (xslDir != null) {
/*  930 */             xsltc.setDestDirectory(xslDir);
/*      */           }
/*      */         } 
/*      */       } 
/*  934 */       if (this._packageName != null) {
/*  935 */         xsltc.setPackageName(this._packageName);
/*      */       }
/*  937 */       if (this._jarFileName != null) {
/*  938 */         xsltc.setJarFileName(this._jarFileName);
/*  939 */         outputType = 5;
/*      */       } else {
/*      */         
/*  942 */         outputType = 4;
/*      */       } 
/*      */     } 
/*      */     
/*  946 */     InputSource input = Util.getInputSource(xsltc, source);
/*  947 */     byte[][] bytecodes = xsltc.compile((String)null, input, outputType);
/*  948 */     String transletName = xsltc.getClassName();
/*      */ 
/*      */     
/*  951 */     if ((this._generateTranslet || this._autoTranslet) && bytecodes != null && this._jarFileName != null) {
/*      */       
/*      */       try {
/*  954 */         xsltc.outputToJar();
/*      */       }
/*  956 */       catch (IOException iOException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  961 */     resetTransientAttributes();
/*      */ 
/*      */     
/*  964 */     if (this._errorListener != this) {
/*      */       try {
/*  966 */         passWarningsToListener(xsltc.getWarnings());
/*      */       }
/*  968 */       catch (TransformerException e) {
/*  969 */         throw new TransformerConfigurationException(e);
/*      */       } 
/*      */     } else {
/*      */       
/*  973 */       xsltc.printWarnings();
/*      */     } 
/*      */ 
/*      */     
/*  977 */     if (bytecodes == null) {
/*  978 */       ErrorMsg err; TransformerConfigurationException exc; ArrayList<ErrorMsg> errs = xsltc.getErrors();
/*      */       
/*  980 */       if (errs != null) {
/*  981 */         err = errs.get(errs.size() - 1);
/*      */       } else {
/*  983 */         err = new ErrorMsg("JAXP_COMPILE_ERR");
/*      */       } 
/*  985 */       Throwable cause = err.getCause();
/*      */       
/*  987 */       if (cause != null) {
/*  988 */         exc = new TransformerConfigurationException(cause.getMessage(), cause);
/*      */       } else {
/*  990 */         exc = new TransformerConfigurationException(err.toString());
/*      */       } 
/*      */ 
/*      */       
/*  994 */       if (this._errorListener != null) {
/*  995 */         passErrorsToListener(xsltc.getErrors());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1001 */           this._errorListener.fatalError(exc);
/* 1002 */         } catch (TransformerException transformerException) {}
/*      */       }
/*      */       else {
/*      */         
/* 1006 */         xsltc.printErrors();
/*      */       } 
/* 1008 */       throw exc;
/*      */     } 
/*      */     
/* 1011 */     return new TemplatesImpl(bytecodes, transletName, xsltc
/* 1012 */         .getOutputProperties(), this._indentNumber, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TemplatesHandler newTemplatesHandler() throws TransformerConfigurationException {
/* 1027 */     TemplatesHandlerImpl handler = new TemplatesHandlerImpl(this._indentNumber, this);
/*      */     
/* 1029 */     if (this._uriResolver != null) {
/* 1030 */       handler.setURIResolver(this._uriResolver);
/*      */     }
/* 1032 */     return handler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TransformerHandler newTransformerHandler() throws TransformerConfigurationException {
/* 1047 */     Transformer transformer = newTransformer();
/* 1048 */     if (this._uriResolver != null) {
/* 1049 */       transformer.setURIResolver(this._uriResolver);
/*      */     }
/* 1051 */     return new TransformerHandlerImpl((TransformerImpl)transformer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TransformerHandler newTransformerHandler(Source src) throws TransformerConfigurationException {
/* 1068 */     Transformer transformer = newTransformer(src);
/* 1069 */     if (this._uriResolver != null) {
/* 1070 */       transformer.setURIResolver(this._uriResolver);
/*      */     }
/* 1072 */     return new TransformerHandlerImpl((TransformerImpl)transformer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TransformerHandler newTransformerHandler(Templates templates) throws TransformerConfigurationException {
/* 1089 */     Transformer transformer = templates.newTransformer();
/* 1090 */     TransformerImpl internal = (TransformerImpl)transformer;
/* 1091 */     return new TransformerHandlerImpl(internal);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLFilter newXMLFilter(Source src) throws TransformerConfigurationException {
/* 1107 */     Templates templates = newTemplates(src);
/* 1108 */     if (templates == null) return null; 
/* 1109 */     return newXMLFilter(templates);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLFilter newXMLFilter(Templates templates) throws TransformerConfigurationException {
/*      */     try {
/* 1126 */       return new TrAXFilter(templates);
/*      */     }
/* 1128 */     catch (TransformerConfigurationException e1) {
/* 1129 */       if (this._errorListener != null) {
/*      */         try {
/* 1131 */           this._errorListener.fatalError(e1);
/* 1132 */           return null;
/*      */         }
/* 1134 */         catch (TransformerException e2) {
/* 1135 */           new TransformerConfigurationException(e2);
/*      */         } 
/*      */       }
/* 1138 */       throw e1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void error(TransformerException e) throws TransformerException {
/* 1157 */     Throwable wrapped = e.getException();
/* 1158 */     if (wrapped != null) {
/* 1159 */       System.err.println(new ErrorMsg("ERROR_PLUS_WRAPPED_MSG", e
/* 1160 */             .getMessageAndLocation(), wrapped
/* 1161 */             .getMessage()));
/*      */     } else {
/* 1163 */       System.err.println(new ErrorMsg("ERROR_MSG", e
/* 1164 */             .getMessageAndLocation()));
/*      */     } 
/* 1166 */     throw e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fatalError(TransformerException e) throws TransformerException {
/* 1186 */     Throwable wrapped = e.getException();
/* 1187 */     if (wrapped != null) {
/* 1188 */       System.err.println(new ErrorMsg("FATAL_ERR_PLUS_WRAPPED_MSG", e
/* 1189 */             .getMessageAndLocation(), wrapped
/* 1190 */             .getMessage()));
/*      */     } else {
/* 1192 */       System.err.println(new ErrorMsg("FATAL_ERR_MSG", e
/* 1193 */             .getMessageAndLocation()));
/*      */     } 
/* 1195 */     throw e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void warning(TransformerException e) throws TransformerException {
/* 1215 */     Throwable wrapped = e.getException();
/* 1216 */     if (wrapped != null) {
/* 1217 */       System.err.println(new ErrorMsg("WARNING_PLUS_WRAPPED_MSG", e
/* 1218 */             .getMessageAndLocation(), wrapped
/* 1219 */             .getMessage()));
/*      */     } else {
/* 1221 */       System.err.println(new ErrorMsg("WARNING_MSG", e
/* 1222 */             .getMessageAndLocation()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputSource loadSource(String href, String context, XSLTC xsltc) {
/*      */     try {
/* 1238 */       if (this._uriResolver != null) {
/* 1239 */         Source source = this._uriResolver.resolve(href, context);
/* 1240 */         if (source != null) {
/* 1241 */           return Util.getInputSource(xsltc, source);
/*      */         }
/*      */       }
/*      */     
/* 1245 */     } catch (TransformerException e) {
/*      */       
/* 1247 */       ErrorMsg msg = new ErrorMsg("INVALID_URI_ERR", href + "\n" + e.getMessage(), this);
/* 1248 */       xsltc.getParser().reportError(2, msg);
/*      */     } 
/*      */     
/* 1251 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetTransientAttributes() {
/* 1258 */     this._transletName = "GregorSamsa";
/* 1259 */     this._destinationDirectory = null;
/* 1260 */     this._packageName = null;
/* 1261 */     this._jarFileName = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[][] getBytecodesFromClasses(Source source, String fullClassName) {
/*      */     String transletName;
/* 1274 */     if (fullClassName == null) {
/* 1275 */       return (byte[][])null;
/*      */     }
/* 1277 */     String xslFileName = getStylesheetFileName(source);
/* 1278 */     File xslFile = null;
/* 1279 */     if (xslFileName != null) {
/* 1280 */       xslFile = new File(xslFileName);
/*      */     }
/*      */ 
/*      */     
/* 1284 */     int lastDotIndex = fullClassName.lastIndexOf('.');
/* 1285 */     if (lastDotIndex > 0) {
/* 1286 */       transletName = fullClassName.substring(lastDotIndex + 1);
/*      */     } else {
/* 1288 */       transletName = fullClassName;
/*      */     } 
/*      */     
/* 1291 */     String transletPath = fullClassName.replace('.', '/');
/* 1292 */     if (this._destinationDirectory != null) {
/* 1293 */       transletPath = this._destinationDirectory + "/" + transletPath + ".class";
/*      */     
/*      */     }
/* 1296 */     else if (xslFile != null && xslFile.getParent() != null) {
/* 1297 */       transletPath = xslFile.getParent() + "/" + transletPath + ".class";
/*      */     } else {
/* 1299 */       transletPath = transletPath + ".class";
/*      */     } 
/*      */ 
/*      */     
/* 1303 */     File transletFile = new File(transletPath);
/* 1304 */     if (!transletFile.exists()) {
/* 1305 */       return (byte[][])null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1311 */     if (xslFile != null && xslFile.exists()) {
/* 1312 */       long xslTimestamp = xslFile.lastModified();
/* 1313 */       long transletTimestamp = transletFile.lastModified();
/* 1314 */       if (transletTimestamp < xslTimestamp) {
/* 1315 */         return (byte[][])null;
/*      */       }
/*      */     } 
/*      */     
/* 1319 */     Vector<byte[]> bytecodes = new Vector();
/* 1320 */     int fileLength = (int)transletFile.length();
/* 1321 */     if (fileLength > 0) {
/*      */       FileInputStream input;
/*      */       try {
/* 1324 */         input = new FileInputStream(transletFile);
/*      */       }
/* 1326 */       catch (FileNotFoundException e) {
/* 1327 */         return (byte[][])null;
/*      */       } 
/*      */       
/* 1330 */       byte[] bytes = new byte[fileLength];
/*      */       try {
/* 1332 */         readFromInputStream(bytes, input, fileLength);
/* 1333 */         input.close();
/*      */       }
/* 1335 */       catch (IOException e) {
/* 1336 */         return (byte[][])null;
/*      */       } 
/*      */       
/* 1339 */       bytecodes.addElement(bytes);
/*      */     } else {
/*      */       
/* 1342 */       return (byte[][])null;
/*      */     } 
/*      */     
/* 1345 */     String str1 = transletFile.getParent();
/* 1346 */     if (str1 == null) {
/* 1347 */       str1 = SecuritySupport.getSystemProperty("user.dir");
/*      */     }
/* 1349 */     File transletParentFile = new File(str1);
/*      */ 
/*      */     
/* 1352 */     final String transletAuxPrefix = transletName + "$";
/* 1353 */     File[] auxfiles = transletParentFile.listFiles(new FilenameFilter()
/*      */         {
/*      */           public boolean accept(File dir, String name)
/*      */           {
/* 1357 */             return (name.endsWith(".class") && name.startsWith(transletAuxPrefix));
/*      */           }
/*      */         });
/*      */ 
/*      */     
/* 1362 */     for (int i = 0; i < auxfiles.length; i++) {
/*      */       
/* 1364 */       File auxfile = auxfiles[i];
/* 1365 */       int auxlength = (int)auxfile.length();
/* 1366 */       if (auxlength > 0) {
/* 1367 */         FileInputStream auxinput = null;
/*      */         try {
/* 1369 */           auxinput = new FileInputStream(auxfile);
/*      */         }
/* 1371 */         catch (FileNotFoundException e) {}
/*      */ 
/*      */ 
/*      */         
/* 1375 */         byte[] bytes = new byte[auxlength];
/*      */         
/*      */         try {
/* 1378 */           readFromInputStream(bytes, auxinput, auxlength);
/* 1379 */           auxinput.close();
/*      */         }
/* 1381 */         catch (IOException e) {}
/*      */ 
/*      */ 
/*      */         
/* 1385 */         bytecodes.addElement(bytes);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1390 */     int count = bytecodes.size();
/* 1391 */     if (count > 0) {
/* 1392 */       byte[][] result = new byte[count][1];
/* 1393 */       for (int j = 0; j < count; j++) {
/* 1394 */         result[j] = bytecodes.elementAt(j);
/*      */       }
/*      */       
/* 1397 */       return result;
/*      */     } 
/*      */     
/* 1400 */     return (byte[][])null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[][] getBytecodesFromJar(Source source, String fullClassName) {
/*      */     String jarPath;
/*      */     ZipFile jarFile;
/* 1412 */     String xslFileName = getStylesheetFileName(source);
/* 1413 */     File xslFile = null;
/* 1414 */     if (xslFileName != null) {
/* 1415 */       xslFile = new File(xslFileName);
/*      */     }
/*      */ 
/*      */     
/* 1419 */     if (this._destinationDirectory != null) {
/* 1420 */       jarPath = this._destinationDirectory + "/" + this._jarFileName;
/*      */     }
/* 1422 */     else if (xslFile != null && xslFile.getParent() != null) {
/* 1423 */       jarPath = xslFile.getParent() + "/" + this._jarFileName;
/*      */     } else {
/* 1425 */       jarPath = this._jarFileName;
/*      */     } 
/*      */ 
/*      */     
/* 1429 */     File file = new File(jarPath);
/* 1430 */     if (!file.exists()) {
/* 1431 */       return (byte[][])null;
/*      */     }
/*      */ 
/*      */     
/* 1435 */     if (xslFile != null && xslFile.exists()) {
/* 1436 */       long xslTimestamp = xslFile.lastModified();
/* 1437 */       long transletTimestamp = file.lastModified();
/* 1438 */       if (transletTimestamp < xslTimestamp) {
/* 1439 */         return (byte[][])null;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 1445 */       jarFile = new ZipFile(file);
/*      */     }
/* 1447 */     catch (IOException e) {
/* 1448 */       return (byte[][])null;
/*      */     } 
/*      */     
/* 1451 */     String transletPath = fullClassName.replace('.', '/');
/* 1452 */     String transletAuxPrefix = transletPath + "$";
/* 1453 */     String transletFullName = transletPath + ".class";
/*      */     
/* 1455 */     Vector<byte[]> bytecodes = new Vector();
/*      */ 
/*      */ 
/*      */     
/* 1459 */     Enumeration<? extends ZipEntry> entries = jarFile.entries();
/* 1460 */     while (entries.hasMoreElements()) {
/*      */       
/* 1462 */       ZipEntry entry = entries.nextElement();
/* 1463 */       String entryName = entry.getName();
/* 1464 */       if (entry.getSize() > 0L && (entryName
/* 1465 */         .equals(transletFullName) || (entryName
/* 1466 */         .endsWith(".class") && entryName
/* 1467 */         .startsWith(transletAuxPrefix)))) {
/*      */         
/*      */         try {
/* 1470 */           InputStream input = jarFile.getInputStream(entry);
/* 1471 */           int size = (int)entry.getSize();
/* 1472 */           byte[] bytes = new byte[size];
/* 1473 */           readFromInputStream(bytes, input, size);
/* 1474 */           input.close();
/* 1475 */           bytecodes.addElement(bytes);
/*      */         }
/* 1477 */         catch (IOException e) {
/* 1478 */           return (byte[][])null;
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1484 */     int count = bytecodes.size();
/* 1485 */     if (count > 0) {
/* 1486 */       byte[][] result = new byte[count][1];
/* 1487 */       for (int i = 0; i < count; i++) {
/* 1488 */         result[i] = bytecodes.elementAt(i);
/*      */       }
/*      */       
/* 1491 */       return result;
/*      */     } 
/*      */     
/* 1494 */     return (byte[][])null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readFromInputStream(byte[] bytes, InputStream input, int size) throws IOException {
/* 1507 */     int n = 0;
/* 1508 */     int offset = 0;
/* 1509 */     int length = size;
/* 1510 */     while (length > 0 && (n = input.read(bytes, offset, length)) > 0) {
/* 1511 */       offset += n;
/* 1512 */       length -= n;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getTransletBaseName(Source source) {
/* 1529 */     String transletBaseName = null;
/* 1530 */     if (!this._transletName.equals("GregorSamsa")) {
/* 1531 */       return this._transletName;
/*      */     }
/* 1533 */     String systemId = source.getSystemId();
/* 1534 */     if (systemId != null) {
/* 1535 */       String baseName = Util.baseName(systemId);
/* 1536 */       if (baseName != null) {
/* 1537 */         baseName = Util.noExtName(baseName);
/* 1538 */         transletBaseName = Util.toJavaName(baseName);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1543 */     return (transletBaseName != null) ? transletBaseName : "GregorSamsa";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getStylesheetFileName(Source source) {
/* 1555 */     String systemId = source.getSystemId();
/* 1556 */     if (systemId != null) {
/* 1557 */       URL url; File file = new File(systemId);
/* 1558 */       if (file.exists()) {
/* 1559 */         return systemId;
/*      */       }
/*      */       
/*      */       try {
/* 1563 */         url = new URL(systemId);
/*      */       }
/* 1565 */       catch (MalformedURLException e) {
/* 1566 */         return null;
/*      */       } 
/*      */       
/* 1569 */       if ("file".equals(url.getProtocol())) {
/* 1570 */         return url.getFile();
/*      */       }
/* 1572 */       return null;
/*      */     } 
/*      */ 
/*      */     
/* 1576 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final XSLTCDTMManager createNewDTMManagerInstance() {
/* 1583 */     return XSLTCDTMManager.createNewDTMManagerInstance();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/trax/TransformerFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */