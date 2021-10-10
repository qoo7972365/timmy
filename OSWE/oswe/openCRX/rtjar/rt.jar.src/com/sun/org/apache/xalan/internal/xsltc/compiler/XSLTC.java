/*      */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*      */ 
/*      */ import com.sun.org.apache.bcel.internal.classfile.JavaClass;
/*      */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import java.util.jar.Attributes;
/*      */ import java.util.jar.JarEntry;
/*      */ import java.util.jar.JarOutputStream;
/*      */ import java.util.jar.Manifest;
/*      */ import jdk.xml.internal.JdkXmlFeatures;
/*      */ import org.xml.sax.InputSource;
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
/*      */ public final class XSLTC
/*      */ {
/*      */   private Parser _parser;
/*   66 */   private XMLReader _reader = null;
/*      */ 
/*      */   
/*   69 */   private SourceLoader _loader = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private Stylesheet _stylesheet;
/*      */ 
/*      */   
/*   76 */   private int _modeSerial = 1;
/*   77 */   private int _stylesheetSerial = 1;
/*   78 */   private int _stepPatternSerial = 1;
/*   79 */   private int _helperClassSerial = 0;
/*   80 */   private int _attributeSetSerial = 0;
/*      */   
/*      */   private int[] _numberFieldIndexes;
/*      */   
/*      */   private int _nextGType;
/*      */   
/*      */   private Vector _namesIndex;
/*      */   
/*      */   private Map<String, Integer> _elements;
/*      */   
/*      */   private Map<String, Integer> _attributes;
/*      */   
/*      */   private int _nextNSType;
/*      */   
/*      */   private Vector _namespaceIndex;
/*      */   
/*      */   private Map<String, Integer> _namespaces;
/*      */   
/*      */   private Map<String, Integer> _namespacePrefixes;
/*      */   
/*      */   private Vector m_characterData;
/*      */   
/*      */   public static final int FILE_OUTPUT = 0;
/*      */   
/*      */   public static final int JAR_OUTPUT = 1;
/*      */   
/*      */   public static final int BYTEARRAY_OUTPUT = 2;
/*      */   public static final int CLASSLOADER_OUTPUT = 3;
/*      */   public static final int BYTEARRAY_AND_FILE_OUTPUT = 4;
/*      */   public static final int BYTEARRAY_AND_JAR_OUTPUT = 5;
/*      */   private boolean _debug = false;
/*  111 */   private String _jarFileName = null;
/*  112 */   private String _className = null;
/*  113 */   private String _packageName = null;
/*  114 */   private File _destDir = null;
/*  115 */   private int _outputType = 0;
/*      */ 
/*      */   
/*      */   private Vector _classes;
/*      */ 
/*      */   
/*      */   private Vector _bcelClasses;
/*      */ 
/*      */   
/*      */   private boolean _callsNodeset = false;
/*      */ 
/*      */   
/*      */   private boolean _multiDocument = false;
/*      */ 
/*      */   
/*      */   private boolean _hasIdCall = false;
/*      */ 
/*      */   
/*      */   private boolean _templateInlining = false;
/*      */ 
/*      */   
/*      */   private boolean _isSecureProcessing = false;
/*      */ 
/*      */   
/*      */   private boolean _overrideDefaultParser;
/*      */   
/*  141 */   private String _accessExternalStylesheet = "all";
/*      */ 
/*      */ 
/*      */   
/*  145 */   private String _accessExternalDTD = "all";
/*      */ 
/*      */ 
/*      */   
/*      */   private XMLSecurityManager _xmlSecurityManager;
/*      */ 
/*      */ 
/*      */   
/*      */   private final JdkXmlFeatures _xmlFeatures;
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassLoader _extensionClassLoader;
/*      */ 
/*      */ 
/*      */   
/*      */   private final Map<String, Class> _externalExtensionFunctions;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSLTC(JdkXmlFeatures featureManager) {
/*  167 */     this._overrideDefaultParser = featureManager.getFeature(JdkXmlFeatures.XmlFeature.JDK_OVERRIDE_PARSER);
/*      */     
/*  169 */     this._parser = new Parser(this, this._overrideDefaultParser);
/*  170 */     this._xmlFeatures = featureManager;
/*  171 */     this._extensionClassLoader = null;
/*  172 */     this._externalExtensionFunctions = (Map)new HashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSecureProcessing(boolean flag) {
/*  179 */     this._isSecureProcessing = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSecureProcessing() {
/*  186 */     return this._isSecureProcessing;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFeature(JdkXmlFeatures.XmlFeature name) {
/*  195 */     return this._xmlFeatures.getFeature(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getProperty(String name) {
/*  202 */     if (name.equals("http://javax.xml.XMLConstants/property/accessExternalStylesheet")) {
/*  203 */       return this._accessExternalStylesheet;
/*      */     }
/*  205 */     if (name.equals("http://javax.xml.XMLConstants/property/accessExternalDTD"))
/*  206 */       return this._accessExternalDTD; 
/*  207 */     if (name.equals("http://apache.org/xml/properties/security-manager"))
/*  208 */       return this._xmlSecurityManager; 
/*  209 */     if (name.equals("jdk.xml.transform.extensionClassLoader")) {
/*  210 */       return this._extensionClassLoader;
/*      */     }
/*  212 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(String name, Object value) {
/*  219 */     if (name.equals("http://javax.xml.XMLConstants/property/accessExternalStylesheet")) {
/*  220 */       this._accessExternalStylesheet = (String)value;
/*      */     }
/*  222 */     else if (name.equals("http://javax.xml.XMLConstants/property/accessExternalDTD")) {
/*  223 */       this._accessExternalDTD = (String)value;
/*  224 */     } else if (name.equals("http://apache.org/xml/properties/security-manager")) {
/*  225 */       this._xmlSecurityManager = (XMLSecurityManager)value;
/*  226 */     } else if (name.equals("jdk.xml.transform.extensionClassLoader")) {
/*  227 */       this._extensionClassLoader = (ClassLoader)value;
/*      */ 
/*      */       
/*  230 */       this._externalExtensionFunctions.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Parser getParser() {
/*  238 */     return this._parser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutputType(int type) {
/*  245 */     this._outputType = type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Properties getOutputProperties() {
/*  252 */     return this._parser.getOutputProperties();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void init() {
/*  259 */     reset();
/*  260 */     this._reader = null;
/*  261 */     this._classes = new Vector();
/*  262 */     this._bcelClasses = new Vector();
/*      */   }
/*      */   
/*      */   private void setExternalExtensionFunctions(String name, Class clazz) {
/*  266 */     if (this._isSecureProcessing && clazz != null && !this._externalExtensionFunctions.containsKey(name)) {
/*  267 */       this._externalExtensionFunctions.put(name, clazz);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Class loadExternalFunction(String name) throws ClassNotFoundException {
/*  277 */     Class<?> loaded = null;
/*      */     
/*  279 */     if (this._externalExtensionFunctions.containsKey(name)) {
/*  280 */       loaded = this._externalExtensionFunctions.get(name);
/*  281 */     } else if (this._extensionClassLoader != null) {
/*  282 */       loaded = Class.forName(name, true, this._extensionClassLoader);
/*  283 */       setExternalExtensionFunctions(name, loaded);
/*      */     } 
/*  285 */     if (loaded == null) {
/*  286 */       throw new ClassNotFoundException(name);
/*      */     }
/*      */     
/*  289 */     return loaded;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Class> getExternalExtensionFunctions() {
/*  297 */     return Collections.unmodifiableMap((Map)this._externalExtensionFunctions);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void reset() {
/*  304 */     this._nextGType = 14;
/*  305 */     this._elements = new HashMap<>();
/*  306 */     this._attributes = new HashMap<>();
/*  307 */     this._namespaces = new HashMap<>();
/*  308 */     this._namespaces.put("", new Integer(this._nextNSType));
/*  309 */     this._namesIndex = new Vector(128);
/*  310 */     this._namespaceIndex = new Vector(32);
/*  311 */     this._namespacePrefixes = new HashMap<>();
/*  312 */     this._stylesheet = null;
/*  313 */     this._parser.init();
/*      */     
/*  315 */     this._modeSerial = 1;
/*  316 */     this._stylesheetSerial = 1;
/*  317 */     this._stepPatternSerial = 1;
/*  318 */     this._helperClassSerial = 0;
/*  319 */     this._attributeSetSerial = 0;
/*  320 */     this._multiDocument = false;
/*  321 */     this._hasIdCall = false;
/*  322 */     this._numberFieldIndexes = new int[] { -1, -1, -1 };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  327 */     this._externalExtensionFunctions.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSourceLoader(SourceLoader loader) {
/*  336 */     this._loader = loader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTemplateInlining(boolean templateInlining) {
/*  346 */     this._templateInlining = templateInlining;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTemplateInlining() {
/*  352 */     return this._templateInlining;
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
/*      */   public void setPIParameters(String media, String title, String charset) {
/*  365 */     this._parser.setPIParameters(media, title, charset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compile(URL url) {
/*      */     try {
/*  375 */       InputStream stream = url.openStream();
/*  376 */       InputSource input = new InputSource(stream);
/*  377 */       input.setSystemId(url.toString());
/*  378 */       return compile(input, this._className);
/*      */     }
/*  380 */     catch (IOException e) {
/*  381 */       this._parser.reportError(2, new ErrorMsg("JAXP_COMPILE_ERR", e));
/*  382 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compile(URL url, String name) {
/*      */     try {
/*  394 */       InputStream stream = url.openStream();
/*  395 */       InputSource input = new InputSource(stream);
/*  396 */       input.setSystemId(url.toString());
/*  397 */       return compile(input, name);
/*      */     }
/*  399 */     catch (IOException e) {
/*  400 */       this._parser.reportError(2, new ErrorMsg("JAXP_COMPILE_ERR", e));
/*  401 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compile(InputStream stream, String name) {
/*  412 */     InputSource input = new InputSource(stream);
/*  413 */     input.setSystemId(name);
/*  414 */     return compile(input, name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compile(InputSource input, String name) {
/*      */     try {
/*  426 */       reset();
/*      */ 
/*      */       
/*  429 */       String systemId = null;
/*  430 */       if (input != null) {
/*  431 */         systemId = input.getSystemId();
/*      */       }
/*      */ 
/*      */       
/*  435 */       if (this._className == null) {
/*  436 */         if (name != null) {
/*  437 */           setClassName(name);
/*      */         }
/*  439 */         else if (systemId != null && !systemId.equals("")) {
/*  440 */           setClassName(Util.baseName(systemId));
/*      */         } 
/*      */ 
/*      */         
/*  444 */         if (this._className == null || this._className.length() == 0) {
/*  445 */           setClassName("GregorSamsa");
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  450 */       SyntaxTreeNode element = null;
/*  451 */       if (this._reader == null) {
/*  452 */         element = this._parser.parse(input);
/*      */       } else {
/*      */         
/*  455 */         element = this._parser.parse(this._reader, input);
/*      */       } 
/*      */ 
/*      */       
/*  459 */       if (!this._parser.errorsFound() && element != null) {
/*      */         
/*  461 */         this._stylesheet = this._parser.makeStylesheet(element);
/*  462 */         this._stylesheet.setSourceLoader(this._loader);
/*  463 */         this._stylesheet.setSystemId(systemId);
/*  464 */         this._stylesheet.setParentStylesheet(null);
/*  465 */         this._stylesheet.setTemplateInlining(this._templateInlining);
/*  466 */         this._parser.setCurrentStylesheet(this._stylesheet);
/*      */ 
/*      */         
/*  469 */         this._parser.createAST(this._stylesheet);
/*      */       } 
/*      */       
/*  472 */       if (!this._parser.errorsFound() && this._stylesheet != null) {
/*  473 */         this._stylesheet.setCallsNodeset(this._callsNodeset);
/*  474 */         this._stylesheet.setMultiDocument(this._multiDocument);
/*  475 */         this._stylesheet.setHasIdCall(this._hasIdCall);
/*      */ 
/*      */         
/*  478 */         synchronized (getClass()) {
/*  479 */           this._stylesheet.translate();
/*      */         }
/*      */       
/*      */       } 
/*  483 */     } catch (Exception e) {
/*  484 */       e.printStackTrace();
/*  485 */       this._parser.reportError(2, new ErrorMsg("JAXP_COMPILE_ERR", e));
/*      */     }
/*  487 */     catch (Error e) {
/*  488 */       if (this._debug) e.printStackTrace(); 
/*  489 */       this._parser.reportError(2, new ErrorMsg("JAXP_COMPILE_ERR", e));
/*      */     } finally {
/*      */       
/*  492 */       this._reader = null;
/*      */     } 
/*  494 */     return !this._parser.errorsFound();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compile(Vector stylesheets) {
/*  504 */     int count = stylesheets.size();
/*      */ 
/*      */     
/*  507 */     if (count == 0) return true;
/*      */ 
/*      */ 
/*      */     
/*  511 */     if (count == 1) {
/*  512 */       Object url = stylesheets.firstElement();
/*  513 */       if (url instanceof URL) {
/*  514 */         return compile((URL)url);
/*      */       }
/*  516 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  520 */     Enumeration urls = stylesheets.elements();
/*  521 */     while (urls.hasMoreElements()) {
/*  522 */       this._className = null;
/*  523 */       Object url = urls.nextElement();
/*  524 */       if (url instanceof URL && 
/*  525 */         !compile((URL)url)) return false;
/*      */     
/*      */     } 
/*      */     
/*  529 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[][] getBytecodes() {
/*  537 */     int count = this._classes.size();
/*  538 */     byte[][] result = new byte[count][1];
/*  539 */     for (int i = 0; i < count; i++)
/*  540 */       result[i] = this._classes.elementAt(i); 
/*  541 */     return result;
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
/*      */   public byte[][] compile(String name, InputSource input, int outputType) {
/*  553 */     this._outputType = outputType;
/*  554 */     if (compile(input, name)) {
/*  555 */       return getBytecodes();
/*      */     }
/*  557 */     return (byte[][])null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[][] compile(String name, InputSource input) {
/*  568 */     return compile(name, input, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setXMLReader(XMLReader reader) {
/*  576 */     this._reader = reader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLReader getXMLReader() {
/*  583 */     return this._reader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList<ErrorMsg> getErrors() {
/*  591 */     return this._parser.getErrors();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList<ErrorMsg> getWarnings() {
/*  599 */     return this._parser.getWarnings();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printErrors() {
/*  606 */     this._parser.printErrors();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printWarnings() {
/*  613 */     this._parser.printWarnings();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setMultiDocument(boolean flag) {
/*  621 */     this._multiDocument = flag;
/*      */   }
/*      */   
/*      */   public boolean isMultiDocument() {
/*  625 */     return this._multiDocument;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setCallsNodeset(boolean flag) {
/*  633 */     if (flag) setMultiDocument(flag); 
/*  634 */     this._callsNodeset = flag;
/*      */   }
/*      */   
/*      */   public boolean callsNodeset() {
/*  638 */     return this._callsNodeset;
/*      */   }
/*      */   
/*      */   protected void setHasIdCall(boolean flag) {
/*  642 */     this._hasIdCall = flag;
/*      */   }
/*      */   
/*      */   public boolean hasIdCall() {
/*  646 */     return this._hasIdCall;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClassName(String className) {
/*  656 */     String base = Util.baseName(className);
/*  657 */     String noext = Util.noExtName(base);
/*  658 */     String name = Util.toJavaName(noext);
/*      */     
/*  660 */     if (this._packageName == null) {
/*  661 */       this._className = name;
/*      */     } else {
/*  663 */       this._className = this._packageName + '.' + name;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClassName() {
/*  670 */     return this._className;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String classFileName(String className) {
/*  678 */     return className.replace('.', File.separatorChar) + ".class";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private File getOutputFile(String className) {
/*  685 */     if (this._destDir != null) {
/*  686 */       return new File(this._destDir, classFileName(className));
/*      */     }
/*  688 */     return new File(classFileName(className));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setDestDirectory(String dstDirName) {
/*  696 */     File dir = new File(dstDirName);
/*  697 */     if (SecuritySupport.getFileExists(dir) || dir.mkdirs()) {
/*  698 */       this._destDir = dir;
/*  699 */       return true;
/*      */     } 
/*      */     
/*  702 */     this._destDir = null;
/*  703 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPackageName(String packageName) {
/*  711 */     this._packageName = packageName;
/*  712 */     if (this._className != null) setClassName(this._className);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setJarFileName(String jarFileName) {
/*  720 */     String JAR_EXT = ".jar";
/*  721 */     if (jarFileName.endsWith(".jar")) {
/*  722 */       this._jarFileName = jarFileName;
/*      */     } else {
/*  724 */       this._jarFileName = jarFileName + ".jar";
/*  725 */     }  this._outputType = 1;
/*      */   }
/*      */   
/*      */   public String getJarFileName() {
/*  729 */     return this._jarFileName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStylesheet(Stylesheet stylesheet) {
/*  736 */     if (this._stylesheet == null) this._stylesheet = stylesheet;
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Stylesheet getStylesheet() {
/*  743 */     return this._stylesheet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int registerAttribute(QName name) {
/*  751 */     Integer code = this._attributes.get(name.toString());
/*  752 */     if (code == null) {
/*  753 */       code = Integer.valueOf(this._nextGType++);
/*  754 */       this._attributes.put(name.toString(), code);
/*  755 */       String uri = name.getNamespace();
/*  756 */       String local = "@" + name.getLocalPart();
/*  757 */       if (uri != null && !uri.equals("")) {
/*  758 */         this._namesIndex.addElement(uri + ":" + local);
/*      */       } else {
/*  760 */         this._namesIndex.addElement(local);
/*  761 */       }  if (name.getLocalPart().equals("*")) {
/*  762 */         registerNamespace(name.getNamespace());
/*      */       }
/*      */     } 
/*  765 */     return code.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int registerElement(QName name) {
/*  774 */     Integer code = this._elements.get(name.toString());
/*  775 */     if (code == null) {
/*  776 */       this._elements.put(name.toString(), code = Integer.valueOf(this._nextGType++));
/*  777 */       this._namesIndex.addElement(name.toString());
/*      */     } 
/*  779 */     if (name.getLocalPart().equals("*")) {
/*  780 */       registerNamespace(name.getNamespace());
/*      */     }
/*  782 */     return code.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int registerNamespacePrefix(QName name) {
/*  792 */     Integer code = this._namespacePrefixes.get(name.toString());
/*  793 */     if (code == null) {
/*  794 */       code = Integer.valueOf(this._nextGType++);
/*  795 */       this._namespacePrefixes.put(name.toString(), code);
/*  796 */       String uri = name.getNamespace();
/*  797 */       if (uri != null && !uri.equals("")) {
/*      */         
/*  799 */         this._namesIndex.addElement("?");
/*      */       } else {
/*  801 */         this._namesIndex.addElement("?" + name.getLocalPart());
/*      */       } 
/*      */     } 
/*  804 */     return code.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int registerNamespace(String namespaceURI) {
/*  812 */     Integer code = this._namespaces.get(namespaceURI);
/*  813 */     if (code == null) {
/*  814 */       code = Integer.valueOf(this._nextNSType++);
/*  815 */       this._namespaces.put(namespaceURI, code);
/*  816 */       this._namespaceIndex.addElement(namespaceURI);
/*      */     } 
/*  818 */     return code.intValue();
/*      */   }
/*      */   
/*      */   public int nextModeSerial() {
/*  822 */     return this._modeSerial++;
/*      */   }
/*      */   
/*      */   public int nextStylesheetSerial() {
/*  826 */     return this._stylesheetSerial++;
/*      */   }
/*      */   
/*      */   public int nextStepPatternSerial() {
/*  830 */     return this._stepPatternSerial++;
/*      */   }
/*      */   
/*      */   public int[] getNumberFieldIndexes() {
/*  834 */     return this._numberFieldIndexes;
/*      */   }
/*      */   
/*      */   public int nextHelperClassSerial() {
/*  838 */     return this._helperClassSerial++;
/*      */   }
/*      */   
/*      */   public int nextAttributeSetSerial() {
/*  842 */     return this._attributeSetSerial++;
/*      */   }
/*      */   
/*      */   public Vector getNamesIndex() {
/*  846 */     return this._namesIndex;
/*      */   }
/*      */   
/*      */   public Vector getNamespaceIndex() {
/*  850 */     return this._namespaceIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHelperClassName() {
/*  858 */     return getClassName() + '$' + this._helperClassSerial++;
/*      */   }
/*      */ 
/*      */   
/*      */   public void dumpClass(JavaClass clazz) {
/*  863 */     if (this._outputType == 0 || this._outputType == 4) {
/*      */ 
/*      */       
/*  866 */       File outFile = getOutputFile(clazz.getClassName());
/*  867 */       String parentDir = outFile.getParent();
/*  868 */       if (parentDir != null) {
/*  869 */         File parentFile = new File(parentDir);
/*  870 */         if (!SecuritySupport.getFileExists(parentFile))
/*  871 */           parentFile.mkdirs(); 
/*      */       } 
/*      */     } 
/*      */     try {
/*      */       ByteArrayOutputStream out;
/*  876 */       switch (this._outputType) {
/*      */         case 0:
/*  878 */           clazz.dump(new BufferedOutputStream(new FileOutputStream(
/*      */ 
/*      */                   
/*  881 */                   getOutputFile(clazz.getClassName()))));
/*      */           break;
/*      */         case 1:
/*  884 */           this._bcelClasses.addElement(clazz);
/*      */           break;
/*      */         case 2:
/*      */         case 3:
/*      */         case 4:
/*      */         case 5:
/*  890 */           out = new ByteArrayOutputStream(2048);
/*  891 */           clazz.dump(out);
/*  892 */           this._classes.addElement(out.toByteArray());
/*      */           
/*  894 */           if (this._outputType == 4) {
/*  895 */             clazz.dump(new BufferedOutputStream(new FileOutputStream(
/*  896 */                     getOutputFile(clazz.getClassName())))); break;
/*  897 */           }  if (this._outputType == 5) {
/*  898 */             this._bcelClasses.addElement(clazz);
/*      */           }
/*      */           break;
/*      */       } 
/*      */     
/*  903 */     } catch (Exception e) {
/*  904 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String entryName(File f) throws IOException {
/*  912 */     return f.getName().replace(File.separatorChar, '/');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void outputToJar() throws IOException {
/*  920 */     Manifest manifest = new Manifest();
/*  921 */     Attributes atrs = manifest.getMainAttributes();
/*  922 */     atrs.put(Attributes.Name.MANIFEST_VERSION, "1.2");
/*      */     
/*  924 */     Map<String, Attributes> map = manifest.getEntries();
/*      */     
/*  926 */     Enumeration<JavaClass> classes = this._bcelClasses.elements();
/*  927 */     String now = (new Date()).toString();
/*  928 */     Attributes.Name dateAttr = new Attributes.Name("Date");
/*      */     
/*  930 */     while (classes.hasMoreElements()) {
/*  931 */       JavaClass clazz = classes.nextElement();
/*  932 */       String className = clazz.getClassName().replace('.', '/');
/*  933 */       Attributes attr = new Attributes();
/*  934 */       attr.put(dateAttr, now);
/*  935 */       map.put(className + ".class", attr);
/*      */     } 
/*      */     
/*  938 */     File jarFile = new File(this._destDir, this._jarFileName);
/*  939 */     JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFile), manifest);
/*      */     
/*  941 */     classes = this._bcelClasses.elements();
/*  942 */     while (classes.hasMoreElements()) {
/*  943 */       JavaClass clazz = classes.nextElement();
/*  944 */       String className = clazz.getClassName().replace('.', '/');
/*  945 */       jos.putNextEntry(new JarEntry(className + ".class"));
/*  946 */       ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
/*  947 */       clazz.dump(out);
/*  948 */       out.writeTo(jos);
/*      */     } 
/*  950 */     jos.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDebug(boolean debug) {
/*  957 */     this._debug = debug;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean debug() {
/*  964 */     return this._debug;
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
/*      */   public String getCharacterData(int index) {
/*  977 */     return ((StringBuffer)this.m_characterData.elementAt(index)).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCharacterDataCount() {
/*  985 */     return (this.m_characterData != null) ? this.m_characterData.size() : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int addCharacterData(String newData) {
/*      */     StringBuffer currData;
/*  997 */     if (this.m_characterData == null) {
/*  998 */       this.m_characterData = new Vector();
/*  999 */       currData = new StringBuffer();
/* 1000 */       this.m_characterData.addElement(currData);
/*      */     } else {
/*      */       
/* 1003 */       currData = this.m_characterData.elementAt(this.m_characterData.size() - 1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1010 */     if (newData.length() + currData.length() > 21845) {
/* 1011 */       currData = new StringBuffer();
/* 1012 */       this.m_characterData.addElement(currData);
/*      */     } 
/*      */     
/* 1015 */     int newDataOffset = currData.length();
/* 1016 */     currData.append(newData);
/*      */     
/* 1018 */     return newDataOffset;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/XSLTC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */