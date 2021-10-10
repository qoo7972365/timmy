/*      */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*      */ 
/*      */ import com.sun.java_cup.internal.runtime.Symbol;
/*      */ import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
/*      */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodType;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*      */ import com.sun.org.apache.xml.internal.serializer.utils.SystemIDResolver;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.StringReader;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Stack;
/*      */ import java.util.StringTokenizer;
/*      */ import jdk.xml.internal.JdkXmlUtils;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.Locator;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.XMLReader;
/*      */ import org.xml.sax.helpers.AttributesImpl;
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
/*      */ public class Parser
/*      */   implements Constants, ContentHandler
/*      */ {
/*      */   private static final String XSL = "xsl";
/*      */   private static final String TRANSLET = "translet";
/*   66 */   private Locator _locator = null;
/*      */   
/*      */   private XSLTC _xsltc;
/*      */   
/*      */   private XPathParser _xpathParser;
/*      */   
/*      */   private ArrayList<ErrorMsg> _errors;
/*      */   
/*      */   private ArrayList<ErrorMsg> _warnings;
/*      */   
/*      */   private Map<String, String> _instructionClasses;
/*      */   private Map<String, String[]> _instructionAttrs;
/*      */   private Map<String, QName> _qNames;
/*      */   private Map<String, Map<String, QName>> _namespaces;
/*      */   private QName _useAttributeSets;
/*      */   private QName _excludeResultPrefixes;
/*      */   private QName _extensionElementPrefixes;
/*      */   private Map<String, Object> _variableScope;
/*      */   private Stylesheet _currentStylesheet;
/*      */   private SymbolTable _symbolTable;
/*      */   private Output _output;
/*      */   private Template _template;
/*      */   private boolean _rootNamespaceDef;
/*      */   private SyntaxTreeNode _root;
/*      */   private String _target;
/*      */   private int _currentImportPrecedence;
/*      */   private boolean _overrideDefaultParser;
/*      */   private String _PImedia;
/*      */   private String _PItitle;
/*      */   private String _PIcharset;
/*      */   private int _templateIndex;
/*      */   private boolean versionIsOne;
/*      */   private Stack<SyntaxTreeNode> _parentStack;
/*      */   private Map<String, String> _prefixMapping;
/*      */   
/*      */   public void init() {
/*  102 */     this._qNames = new HashMap<>(512);
/*  103 */     this._namespaces = new HashMap<>();
/*  104 */     this._instructionClasses = new HashMap<>();
/*  105 */     this._instructionAttrs = (Map)new HashMap<>();
/*  106 */     this._variableScope = new HashMap<>();
/*  107 */     this._template = null;
/*  108 */     this._errors = new ArrayList<>();
/*  109 */     this._warnings = new ArrayList<>();
/*  110 */     this._symbolTable = new SymbolTable();
/*  111 */     this._xpathParser = new XPathParser(this);
/*  112 */     this._currentStylesheet = null;
/*  113 */     this._output = null;
/*  114 */     this._root = null;
/*  115 */     this._rootNamespaceDef = false;
/*  116 */     this._currentImportPrecedence = 1;
/*      */     
/*  118 */     initStdClasses();
/*  119 */     initInstructionAttrs();
/*  120 */     initExtClasses();
/*  121 */     initSymbolTable();
/*      */     
/*  123 */     this
/*  124 */       ._useAttributeSets = getQName("http://www.w3.org/1999/XSL/Transform", "xsl", "use-attribute-sets");
/*  125 */     this
/*  126 */       ._excludeResultPrefixes = getQName("http://www.w3.org/1999/XSL/Transform", "xsl", "exclude-result-prefixes");
/*  127 */     this
/*  128 */       ._extensionElementPrefixes = getQName("http://www.w3.org/1999/XSL/Transform", "xsl", "extension-element-prefixes");
/*      */   }
/*      */   
/*      */   public void setOutput(Output output) {
/*  132 */     if (this._output != null) {
/*  133 */       if (this._output.getImportPrecedence() <= output.getImportPrecedence()) {
/*  134 */         output.mergeOutput(this._output);
/*  135 */         this._output.disable();
/*  136 */         this._output = output;
/*      */       } else {
/*      */         
/*  139 */         output.disable();
/*      */       } 
/*      */     } else {
/*      */       
/*  143 */       this._output = output;
/*      */     } 
/*      */   }
/*      */   
/*      */   public Output getOutput() {
/*  148 */     return this._output;
/*      */   }
/*      */   
/*      */   public Properties getOutputProperties() {
/*  152 */     return getTopLevelStylesheet().getOutputProperties();
/*      */   }
/*      */   
/*      */   public void addVariable(Variable var) {
/*  156 */     addVariableOrParam(var);
/*      */   }
/*      */   
/*      */   public void addParameter(Param param) {
/*  160 */     addVariableOrParam(param);
/*      */   }
/*      */   
/*      */   private void addVariableOrParam(VariableBase var) {
/*  164 */     Object existing = this._variableScope.get(var.getName().getStringRep());
/*  165 */     if (existing != null) {
/*  166 */       if (existing instanceof Stack) {
/*      */         
/*  168 */         Stack<VariableBase> stack = (Stack<VariableBase>)existing;
/*  169 */         stack.push(var);
/*      */       }
/*  171 */       else if (existing instanceof VariableBase) {
/*  172 */         Stack<VariableBase> stack = new Stack<>();
/*  173 */         stack.push((VariableBase)existing);
/*  174 */         stack.push(var);
/*  175 */         this._variableScope.put(var.getName().getStringRep(), stack);
/*      */       } 
/*      */     } else {
/*      */       
/*  179 */       this._variableScope.put(var.getName().getStringRep(), var);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void removeVariable(QName name) {
/*  184 */     Object existing = this._variableScope.get(name.getStringRep());
/*  185 */     if (existing instanceof Stack) {
/*      */       
/*  187 */       Stack<VariableBase> stack = (Stack<VariableBase>)existing;
/*  188 */       if (!stack.isEmpty()) stack.pop(); 
/*  189 */       if (!stack.isEmpty())
/*      */         return; 
/*  191 */     }  this._variableScope.remove(name.getStringRep());
/*      */   }
/*      */   
/*      */   public VariableBase lookupVariable(QName name) {
/*  195 */     Object existing = this._variableScope.get(name.getStringRep());
/*  196 */     if (existing instanceof VariableBase) {
/*  197 */       return (VariableBase)existing;
/*      */     }
/*  199 */     if (existing instanceof Stack) {
/*      */       
/*  201 */       Stack<VariableBase> stack = (Stack<VariableBase>)existing;
/*  202 */       return stack.peek();
/*      */     } 
/*  204 */     return null;
/*      */   }
/*      */   
/*      */   public void setXSLTC(XSLTC xsltc) {
/*  208 */     this._xsltc = xsltc;
/*      */   }
/*      */   
/*      */   public XSLTC getXSLTC() {
/*  212 */     return this._xsltc;
/*      */   }
/*      */   
/*      */   public int getCurrentImportPrecedence() {
/*  216 */     return this._currentImportPrecedence;
/*      */   }
/*      */   
/*      */   public int getNextImportPrecedence() {
/*  220 */     return ++this._currentImportPrecedence;
/*      */   }
/*      */   
/*      */   public void setCurrentStylesheet(Stylesheet stylesheet) {
/*  224 */     this._currentStylesheet = stylesheet;
/*      */   }
/*      */   
/*      */   public Stylesheet getCurrentStylesheet() {
/*  228 */     return this._currentStylesheet;
/*      */   }
/*      */   
/*      */   public Stylesheet getTopLevelStylesheet() {
/*  232 */     return this._xsltc.getStylesheet();
/*      */   }
/*      */ 
/*      */   
/*      */   public QName getQNameSafe(String stringRep) {
/*  237 */     int colon = stringRep.lastIndexOf(':');
/*  238 */     if (colon != -1) {
/*  239 */       String prefix = stringRep.substring(0, colon);
/*  240 */       String localname = stringRep.substring(colon + 1);
/*  241 */       String namespace = null;
/*      */ 
/*      */       
/*  244 */       if (!prefix.equals("xmlns")) {
/*  245 */         namespace = this._symbolTable.lookupNamespace(prefix);
/*  246 */         if (namespace == null) namespace = ""; 
/*      */       } 
/*  248 */       return getQName(namespace, prefix, localname);
/*      */     } 
/*      */ 
/*      */     
/*  252 */     String uri = stringRep.equals("xmlns") ? null : this._symbolTable.lookupNamespace("");
/*  253 */     return getQName(uri, (String)null, stringRep);
/*      */   }
/*      */ 
/*      */   
/*      */   public QName getQName(String stringRep) {
/*  258 */     return getQName(stringRep, true, false);
/*      */   }
/*      */   
/*      */   public QName getQNameIgnoreDefaultNs(String stringRep) {
/*  262 */     return getQName(stringRep, true, true);
/*      */   }
/*      */   
/*      */   public QName getQName(String stringRep, boolean reportError) {
/*  266 */     return getQName(stringRep, reportError, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private QName getQName(String stringRep, boolean reportError, boolean ignoreDefaultNs) {
/*  273 */     int colon = stringRep.lastIndexOf(':');
/*  274 */     if (colon != -1) {
/*  275 */       String prefix = stringRep.substring(0, colon);
/*  276 */       String localname = stringRep.substring(colon + 1);
/*  277 */       String namespace = null;
/*      */ 
/*      */       
/*  280 */       if (!prefix.equals("xmlns")) {
/*  281 */         namespace = this._symbolTable.lookupNamespace(prefix);
/*  282 */         if (namespace == null && reportError) {
/*  283 */           int line = getLineNumber();
/*  284 */           ErrorMsg err = new ErrorMsg("NAMESPACE_UNDEF_ERR", line, prefix);
/*      */           
/*  286 */           reportError(3, err);
/*      */         } 
/*      */       } 
/*  289 */       return getQName(namespace, prefix, localname);
/*      */     } 
/*      */     
/*  292 */     if (stringRep.equals("xmlns")) {
/*  293 */       ignoreDefaultNs = true;
/*      */     }
/*      */     
/*  296 */     String defURI = ignoreDefaultNs ? null : this._symbolTable.lookupNamespace("");
/*  297 */     return getQName(defURI, (String)null, stringRep);
/*      */   }
/*      */ 
/*      */   
/*      */   public QName getQName(String namespace, String prefix, String localname) {
/*  302 */     if (namespace == null || namespace.equals("")) {
/*  303 */       QName qName = this._qNames.get(localname);
/*  304 */       if (qName == null) {
/*  305 */         qName = new QName(null, prefix, localname);
/*  306 */         this._qNames.put(localname, qName);
/*      */       } 
/*  308 */       return qName;
/*      */     } 
/*      */     
/*  311 */     Map<String, QName> space = this._namespaces.get(namespace);
/*      */     
/*  313 */     String lexicalQName = (prefix == null || prefix.length() == 0) ? localname : (prefix + ':' + localname);
/*      */ 
/*      */ 
/*      */     
/*  317 */     if (space == null) {
/*  318 */       QName qName = new QName(namespace, prefix, localname);
/*  319 */       this._namespaces.put(namespace, space = new HashMap<>());
/*  320 */       space.put(lexicalQName, qName);
/*  321 */       return qName;
/*      */     } 
/*      */     
/*  324 */     QName name = space.get(lexicalQName);
/*  325 */     if (name == null) {
/*  326 */       name = new QName(namespace, prefix, localname);
/*  327 */       space.put(lexicalQName, name);
/*      */     } 
/*  329 */     return name;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public QName getQName(String scope, String name) {
/*  335 */     return getQName(scope + name);
/*      */   }
/*      */   
/*      */   public QName getQName(QName scope, QName name) {
/*  339 */     return getQName(scope.toString() + name.toString());
/*      */   }
/*      */   
/*      */   public QName getUseAttributeSets() {
/*  343 */     return this._useAttributeSets;
/*      */   }
/*      */   
/*      */   public QName getExtensionElementPrefixes() {
/*  347 */     return this._extensionElementPrefixes;
/*      */   }
/*      */   
/*      */   public QName getExcludeResultPrefixes() {
/*  351 */     return this._excludeResultPrefixes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stylesheet makeStylesheet(SyntaxTreeNode element) throws CompilerException {
/*      */     try {
/*      */       Stylesheet stylesheet;
/*  364 */       if (element instanceof Stylesheet) {
/*  365 */         stylesheet = (Stylesheet)element;
/*      */       } else {
/*      */         
/*  368 */         stylesheet = new Stylesheet();
/*  369 */         stylesheet.setSimplified();
/*  370 */         stylesheet.addElement(element);
/*  371 */         stylesheet.setAttributes((AttributesImpl)element.getAttributes());
/*      */ 
/*      */         
/*  374 */         if (element.lookupNamespace("") == null) {
/*  375 */           element.addPrefixMapping("", "");
/*      */         }
/*      */       } 
/*  378 */       stylesheet.setParser(this);
/*  379 */       return stylesheet;
/*      */     }
/*  381 */     catch (ClassCastException e) {
/*  382 */       ErrorMsg err = new ErrorMsg("NOT_STYLESHEET_ERR", element);
/*  383 */       throw new CompilerException(err.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void createAST(Stylesheet stylesheet) {
/*      */     try {
/*  392 */       if (stylesheet != null) {
/*  393 */         stylesheet.parseContents(this);
/*  394 */         Iterator<SyntaxTreeNode> elements = stylesheet.elements();
/*  395 */         while (elements.hasNext()) {
/*  396 */           SyntaxTreeNode child = elements.next();
/*  397 */           if (child instanceof Text) {
/*  398 */             int l = getLineNumber();
/*  399 */             ErrorMsg err = new ErrorMsg("ILLEGAL_TEXT_NODE_ERR", l, null);
/*      */             
/*  401 */             reportError(3, err);
/*      */           } 
/*      */         } 
/*  404 */         if (!errorsFound()) {
/*  405 */           stylesheet.typeCheck(this._symbolTable);
/*      */         }
/*      */       }
/*      */     
/*  409 */     } catch (TypeCheckError e) {
/*  410 */       reportError(3, new ErrorMsg("JAXP_COMPILE_ERR", e));
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
/*      */   public SyntaxTreeNode parse(XMLReader reader, InputSource input) {
/*      */     try {
/*  423 */       reader.setContentHandler(this);
/*  424 */       reader.parse(input);
/*      */       
/*  426 */       return getStylesheet(this._root);
/*      */     }
/*  428 */     catch (IOException e) {
/*  429 */       if (this._xsltc.debug()) e.printStackTrace(); 
/*  430 */       reportError(3, new ErrorMsg("JAXP_COMPILE_ERR", e));
/*      */     }
/*  432 */     catch (SAXException e) {
/*  433 */       Throwable ex = e.getException();
/*  434 */       if (this._xsltc.debug()) {
/*  435 */         e.printStackTrace();
/*  436 */         if (ex != null) ex.printStackTrace(); 
/*      */       } 
/*  438 */       reportError(3, new ErrorMsg("JAXP_COMPILE_ERR", e));
/*      */     }
/*  440 */     catch (CompilerException e) {
/*  441 */       if (this._xsltc.debug()) e.printStackTrace(); 
/*  442 */       reportError(3, new ErrorMsg("JAXP_COMPILE_ERR", e));
/*      */     }
/*  444 */     catch (Exception e) {
/*  445 */       if (this._xsltc.debug()) e.printStackTrace(); 
/*  446 */       reportError(3, new ErrorMsg("JAXP_COMPILE_ERR", e));
/*      */     } 
/*  448 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SyntaxTreeNode parse(InputSource input) {
/*  457 */     XMLReader reader = JdkXmlUtils.getXMLReader(this._overrideDefaultParser, this._xsltc
/*  458 */         .isSecureProcessing());
/*      */     
/*  460 */     JdkXmlUtils.setXMLReaderPropertyIfSupport(reader, "http://javax.xml.XMLConstants/property/accessExternalDTD", this._xsltc
/*  461 */         .getProperty("http://javax.xml.XMLConstants/property/accessExternalDTD"), true);
/*      */     
/*  463 */     String lastProperty = "";
/*      */     
/*      */     try {
/*  466 */       XMLSecurityManager securityManager = (XMLSecurityManager)this._xsltc.getProperty("http://apache.org/xml/properties/security-manager");
/*  467 */       for (XMLSecurityManager.Limit limit : XMLSecurityManager.Limit.values()) {
/*  468 */         lastProperty = limit.apiProperty();
/*  469 */         reader.setProperty(lastProperty, securityManager.getLimitValueAsString(limit));
/*      */       } 
/*  471 */       if (securityManager.printEntityCountInfo()) {
/*  472 */         lastProperty = "http://www.oracle.com/xml/jaxp/properties/getEntityCountInfo";
/*  473 */         reader.setProperty("http://www.oracle.com/xml/jaxp/properties/getEntityCountInfo", "yes");
/*      */       } 
/*  475 */     } catch (SAXException se) {
/*  476 */       XMLSecurityManager.printWarning(reader.getClass().getName(), lastProperty, se);
/*      */     } 
/*      */     
/*  479 */     return parse(reader, input);
/*      */   }
/*      */   
/*      */   public SyntaxTreeNode getDocumentRoot() {
/*  483 */     return this._root;
/*      */   }
/*      */   protected void setPIParameters(String media, String title, String charset) { this._PImedia = media; this._PItitle = title; this._PIcharset = charset; }
/*  486 */   private SyntaxTreeNode getStylesheet(SyntaxTreeNode root) throws CompilerException { if (this._target == null) { if (!this._rootNamespaceDef) { ErrorMsg msg = new ErrorMsg("MISSING_XSLT_URI_ERR"); throw new CompilerException(msg.toString()); }  return root; }  if (this._target.charAt(0) == '#') { SyntaxTreeNode element = findStylesheet(root, this._target.substring(1)); if (element == null) { ErrorMsg msg = new ErrorMsg("MISSING_XSLT_TARGET_ERR", this._target, root); throw new CompilerException(msg.toString()); }  return element; }  try { String path = this._target; if (path.indexOf(":") == -1) path = "file:" + path;  path = SystemIDResolver.getAbsoluteURI(path); String accessError = SecuritySupport.checkAccess(path, (String)this._xsltc.getProperty("http://javax.xml.XMLConstants/property/accessExternalStylesheet"), "all"); if (accessError != null) { ErrorMsg msg = new ErrorMsg("ACCESSING_XSLT_TARGET_ERR", SecuritySupport.sanitizePath(this._target), accessError, root); throw new CompilerException(msg.toString()); }  } catch (IOException ex) { throw new CompilerException(ex); }  return loadExternalStylesheet(this._target); } private SyntaxTreeNode findStylesheet(SyntaxTreeNode root, String href) { if (root == null) return null;  if (root instanceof Stylesheet) { String id = root.getAttribute("id"); if (id.equals(href)) return root;  }  List<SyntaxTreeNode> children = root.getContents(); if (children != null) { int count = children.size(); for (int i = 0; i < count; i++) { SyntaxTreeNode child = children.get(i); SyntaxTreeNode node = findStylesheet(child, href); if (node != null) return node;  }  }  return null; } private SyntaxTreeNode loadExternalStylesheet(String location) throws CompilerException { InputSource source; if ((new File(location)).exists()) { source = new InputSource("file:" + location); } else { source = new InputSource(location); }  SyntaxTreeNode external = parse(source); return external; } private void initAttrTable(String elementName, String[] attrs) { this._instructionAttrs.put(getQName("http://www.w3.org/1999/XSL/Transform", "xsl", elementName).getStringRep(), attrs); } private void initInstructionAttrs() { initAttrTable("template", new String[] { "match", "name", "priority", "mode" }); initAttrTable("stylesheet", new String[] { "id", "version", "extension-element-prefixes", "exclude-result-prefixes" }); initAttrTable("transform", new String[] { "id", "version", "extension-element-prefixes", "exclude-result-prefixes" }); initAttrTable("text", new String[] { "disable-output-escaping" }); initAttrTable("if", new String[] { "test" }); initAttrTable("choose", new String[0]); initAttrTable("when", new String[] { "test" }); initAttrTable("otherwise", new String[0]); initAttrTable("for-each", new String[] { "select" }); initAttrTable("message", new String[] { "terminate" }); initAttrTable("number", new String[] { "level", "count", "from", "value", "format", "lang", "letter-value", "grouping-separator", "grouping-size" }); initAttrTable("comment", new String[0]); initAttrTable("copy", new String[] { "use-attribute-sets" }); initAttrTable("copy-of", new String[] { "select" }); initAttrTable("param", new String[] { "name", "select" }); initAttrTable("with-param", new String[] { "name", "select" }); initAttrTable("variable", new String[] { "name", "select" }); initAttrTable("output", new String[] { "method", "version", "encoding", "omit-xml-declaration", "standalone", "doctype-public", "doctype-system", "cdata-section-elements", "indent", "media-type" }); initAttrTable("sort", new String[] { "select", "order", "case-order", "lang", "data-type" }); initAttrTable("key", new String[] { "name", "match", "use" }); initAttrTable("fallback", new String[0]); initAttrTable("attribute", new String[] { "name", "namespace" }); initAttrTable("attribute-set", new String[] { "name", "use-attribute-sets" }); initAttrTable("value-of", new String[] { "select", "disable-output-escaping" }); initAttrTable("element", new String[] { "name", "namespace", "use-attribute-sets" }); initAttrTable("call-template", new String[] { "name" }); initAttrTable("apply-templates", new String[] { "select", "mode" }); initAttrTable("apply-imports", new String[0]); initAttrTable("decimal-format", new String[] { "name", "decimal-separator", "grouping-separator", "infinity", "minus-sign", "NaN", "percent", "per-mille", "zero-digit", "digit", "pattern-separator" }); initAttrTable("import", new String[] { "href" }); initAttrTable("include", new String[] { "href" }); initAttrTable("strip-space", new String[] { "elements" }); initAttrTable("preserve-space", new String[] { "elements" }); initAttrTable("processing-instruction", new String[] { "name" }); initAttrTable("namespace-alias", new String[] { "stylesheet-prefix", "result-prefix" }); } private void initStdClasses() { initStdClass("template", "Template"); initStdClass("stylesheet", "Stylesheet"); initStdClass("transform", "Stylesheet"); initStdClass("text", "Text"); initStdClass("if", "If"); initStdClass("choose", "Choose"); initStdClass("when", "When"); initStdClass("otherwise", "Otherwise"); initStdClass("for-each", "ForEach"); initStdClass("message", "Message"); initStdClass("number", "Number"); initStdClass("comment", "Comment"); initStdClass("copy", "Copy"); initStdClass("copy-of", "CopyOf"); initStdClass("param", "Param"); initStdClass("with-param", "WithParam"); initStdClass("variable", "Variable"); initStdClass("output", "Output"); initStdClass("sort", "Sort"); initStdClass("key", "Key"); initStdClass("fallback", "Fallback"); initStdClass("attribute", "XslAttribute"); initStdClass("attribute-set", "AttributeSet"); initStdClass("value-of", "ValueOf"); initStdClass("element", "XslElement"); initStdClass("call-template", "CallTemplate"); initStdClass("apply-templates", "ApplyTemplates"); initStdClass("apply-imports", "ApplyImports"); initStdClass("decimal-format", "DecimalFormatting"); initStdClass("import", "Import"); initStdClass("include", "Include"); initStdClass("strip-space", "Whitespace"); initStdClass("preserve-space", "Whitespace"); initStdClass("processing-instruction", "ProcessingInstruction"); initStdClass("namespace-alias", "NamespaceAlias"); } private void initStdClass(String elementName, String className) { this._instructionClasses.put(getQName("http://www.w3.org/1999/XSL/Transform", "xsl", elementName).getStringRep(), "com.sun.org.apache.xalan.internal.xsltc.compiler." + className); } public Parser(XSLTC xsltc, boolean useOverrideDefaultParser) { this._PImedia = null;
/*  487 */     this._PItitle = null;
/*  488 */     this._PIcharset = null;
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
/*  908 */     this._templateIndex = 0;
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
/*  926 */     this.versionIsOne = true;
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
/* 1212 */     this._parentStack = null;
/* 1213 */     this._prefixMapping = null; this._xsltc = xsltc; this._overrideDefaultParser = useOverrideDefaultParser; }
/*      */   public boolean elementSupported(String namespace, String localName) { return (this._instructionClasses.get(getQName(namespace, "xsl", localName).getStringRep()) != null); }
/*      */   public boolean functionSupported(String fname) { return (this._symbolTable.lookupPrimop(fname) != null); }
/*      */   private void initExtClasses() { initExtClass("output", "TransletOutput"); initExtClass("http://xml.apache.org/xalan/redirect", "write", "TransletOutput"); }
/*      */   private void initExtClass(String elementName, String className) { this._instructionClasses.put(getQName("http://xml.apache.org/xalan/xsltc", "translet", elementName).getStringRep(), "com.sun.org.apache.xalan.internal.xsltc.compiler." + className); }
/*      */   private void initExtClass(String namespace, String elementName, String className) { this._instructionClasses.put(getQName(namespace, "translet", elementName).getStringRep(), "com.sun.org.apache.xalan.internal.xsltc.compiler." + className); }
/* 1219 */   private void initSymbolTable() { MethodType I_V = new MethodType(Type.Int, Type.Void); MethodType I_R = new MethodType(Type.Int, Type.Real); MethodType I_S = new MethodType(Type.Int, Type.String); MethodType I_D = new MethodType(Type.Int, Type.NodeSet); MethodType R_I = new MethodType(Type.Real, Type.Int); MethodType R_V = new MethodType(Type.Real, Type.Void); MethodType R_R = new MethodType(Type.Real, Type.Real); MethodType R_D = new MethodType(Type.Real, Type.NodeSet); MethodType R_O = new MethodType(Type.Real, Type.Reference); MethodType I_I = new MethodType(Type.Int, Type.Int); MethodType D_O = new MethodType(Type.NodeSet, Type.Reference); MethodType D_V = new MethodType(Type.NodeSet, Type.Void); MethodType D_S = new MethodType(Type.NodeSet, Type.String); MethodType D_D = new MethodType(Type.NodeSet, Type.NodeSet); MethodType A_V = new MethodType(Type.Node, Type.Void); MethodType S_V = new MethodType(Type.String, Type.Void); MethodType S_S = new MethodType(Type.String, Type.String); MethodType S_A = new MethodType(Type.String, Type.Node); MethodType S_D = new MethodType(Type.String, Type.NodeSet); MethodType S_O = new MethodType(Type.String, Type.Reference); MethodType B_O = new MethodType(Type.Boolean, Type.Reference); MethodType B_V = new MethodType(Type.Boolean, Type.Void); MethodType B_B = new MethodType(Type.Boolean, Type.Boolean); MethodType B_S = new MethodType(Type.Boolean, Type.String); MethodType D_X = new MethodType(Type.NodeSet, Type.Object); MethodType R_RR = new MethodType(Type.Real, Type.Real, Type.Real); MethodType I_II = new MethodType(Type.Int, Type.Int, Type.Int); MethodType B_RR = new MethodType(Type.Boolean, Type.Real, Type.Real); MethodType B_II = new MethodType(Type.Boolean, Type.Int, Type.Int); MethodType S_SS = new MethodType(Type.String, Type.String, Type.String); MethodType S_DS = new MethodType(Type.String, Type.Real, Type.String); MethodType S_SR = new MethodType(Type.String, Type.String, Type.Real); MethodType O_SO = new MethodType(Type.Reference, Type.String, Type.Reference); MethodType D_SS = new MethodType(Type.NodeSet, Type.String, Type.String); MethodType D_SD = new MethodType(Type.NodeSet, Type.String, Type.NodeSet); MethodType B_BB = new MethodType(Type.Boolean, Type.Boolean, Type.Boolean); MethodType B_SS = new MethodType(Type.Boolean, Type.String, Type.String); MethodType S_SD = new MethodType(Type.String, Type.String, Type.NodeSet); MethodType S_DSS = new MethodType(Type.String, Type.Real, Type.String, Type.String); MethodType S_SRR = new MethodType(Type.String, Type.String, Type.Real, Type.Real); MethodType S_SSS = new MethodType(Type.String, Type.String, Type.String, Type.String); this._symbolTable.addPrimop("current", A_V); this._symbolTable.addPrimop("last", I_V); this._symbolTable.addPrimop("position", I_V); this._symbolTable.addPrimop("true", B_V); this._symbolTable.addPrimop("false", B_V); this._symbolTable.addPrimop("not", B_B); this._symbolTable.addPrimop("name", S_V); this._symbolTable.addPrimop("name", S_A); this._symbolTable.addPrimop("generate-id", S_V); this._symbolTable.addPrimop("generate-id", S_A); this._symbolTable.addPrimop("ceiling", R_R); this._symbolTable.addPrimop("floor", R_R); this._symbolTable.addPrimop("round", R_R); this._symbolTable.addPrimop("contains", B_SS); this._symbolTable.addPrimop("number", R_O); this._symbolTable.addPrimop("number", R_V); this._symbolTable.addPrimop("boolean", B_O); this._symbolTable.addPrimop("string", S_O); this._symbolTable.addPrimop("string", S_V); this._symbolTable.addPrimop("translate", S_SSS); this._symbolTable.addPrimop("string-length", I_V); this._symbolTable.addPrimop("string-length", I_S); this._symbolTable.addPrimop("starts-with", B_SS); this._symbolTable.addPrimop("format-number", S_DS); this._symbolTable.addPrimop("format-number", S_DSS); this._symbolTable.addPrimop("unparsed-entity-uri", S_S); this._symbolTable.addPrimop("key", D_SS); this._symbolTable.addPrimop("key", D_SD); this._symbolTable.addPrimop("id", D_S); this._symbolTable.addPrimop("id", D_D); this._symbolTable.addPrimop("namespace-uri", S_V); this._symbolTable.addPrimop("function-available", B_S); this._symbolTable.addPrimop("element-available", B_S); this._symbolTable.addPrimop("document", D_S); this._symbolTable.addPrimop("document", D_V); this._symbolTable.addPrimop("count", I_D); this._symbolTable.addPrimop("sum", R_D); this._symbolTable.addPrimop("local-name", S_V); this._symbolTable.addPrimop("local-name", S_D); this._symbolTable.addPrimop("namespace-uri", S_V); this._symbolTable.addPrimop("namespace-uri", S_D); this._symbolTable.addPrimop("substring", S_SR); this._symbolTable.addPrimop("substring", S_SRR); this._symbolTable.addPrimop("substring-after", S_SS); this._symbolTable.addPrimop("substring-before", S_SS); this._symbolTable.addPrimop("normalize-space", S_V); this._symbolTable.addPrimop("normalize-space", S_S); this._symbolTable.addPrimop("system-property", S_S); this._symbolTable.addPrimop("nodeset", D_O); this._symbolTable.addPrimop("objectType", S_O); this._symbolTable.addPrimop("cast", O_SO); this._symbolTable.addPrimop("+", R_RR); this._symbolTable.addPrimop("-", R_RR); this._symbolTable.addPrimop("*", R_RR); this._symbolTable.addPrimop("/", R_RR); this._symbolTable.addPrimop("%", R_RR); this._symbolTable.addPrimop("+", I_II); this._symbolTable.addPrimop("-", I_II); this._symbolTable.addPrimop("*", I_II); this._symbolTable.addPrimop("<", B_RR); this._symbolTable.addPrimop("<=", B_RR); this._symbolTable.addPrimop(">", B_RR); this._symbolTable.addPrimop(">=", B_RR); this._symbolTable.addPrimop("<", B_II); this._symbolTable.addPrimop("<=", B_II); this._symbolTable.addPrimop(">", B_II); this._symbolTable.addPrimop(">=", B_II); this._symbolTable.addPrimop("<", B_BB); this._symbolTable.addPrimop("<=", B_BB); this._symbolTable.addPrimop(">", B_BB); this._symbolTable.addPrimop(">=", B_BB); this._symbolTable.addPrimop("or", B_BB); this._symbolTable.addPrimop("and", B_BB); this._symbolTable.addPrimop("u-", R_R); this._symbolTable.addPrimop("u-", I_I); } public SymbolTable getSymbolTable() { return this._symbolTable; } public void startDocument() { this._root = null;
/* 1220 */     this._target = null;
/* 1221 */     this._prefixMapping = null;
/* 1222 */     this._parentStack = new Stack<>(); } public Template getTemplate() { return this._template; } public void setTemplate(Template template) { this._template = template; } public int getTemplateIndex() { return this._templateIndex++; } public SyntaxTreeNode makeInstance(String uri, String prefix, String local, Attributes attributes) { SyntaxTreeNode node = null; QName qname = getQName(uri, prefix, local); String className = this._instructionClasses.get(qname.getStringRep()); if (className != null) { try { Class<?> clazz = ObjectFactory.findProviderClass(className, true); node = (SyntaxTreeNode)clazz.newInstance(); node.setQName(qname); node.setParser(this); if (this._locator != null) node.setLineNumber(getLineNumber());  if (node instanceof Stylesheet) this._xsltc.setStylesheet((Stylesheet)node);  checkForSuperfluousAttributes(node, attributes); } catch (ClassNotFoundException e) { ErrorMsg err = new ErrorMsg("CLASS_NOT_FOUND_ERR", node); reportError(3, err); } catch (Exception e) { ErrorMsg err = new ErrorMsg("INTERNAL_ERR", e.getMessage(), node); reportError(2, err); }  } else { if (uri != null) if (uri.equals("http://www.w3.org/1999/XSL/Transform")) { node = new UnsupportedElement(uri, prefix, local, false); UnsupportedElement element = (UnsupportedElement)node; ErrorMsg msg = new ErrorMsg("UNSUPPORTED_XSL_ERR", getLineNumber(), local); element.setErrorMessage(msg); if (this.versionIsOne) reportError(1, msg);  } else if (uri.equals("http://xml.apache.org/xalan/xsltc")) { node = new UnsupportedElement(uri, prefix, local, true); UnsupportedElement element = (UnsupportedElement)node; ErrorMsg msg = new ErrorMsg("UNSUPPORTED_EXT_ERR", getLineNumber(), local); element.setErrorMessage(msg); } else { Stylesheet sheet = this._xsltc.getStylesheet(); if (sheet != null && sheet.isExtension(uri) && sheet != this._parentStack.peek()) { node = new UnsupportedElement(uri, prefix, local, true); UnsupportedElement elem = (UnsupportedElement)node; ErrorMsg msg = new ErrorMsg("UNSUPPORTED_EXT_ERR", getLineNumber(), prefix + ":" + local); elem.setErrorMessage(msg); }  }   if (node == null) { node = new LiteralElement(); node.setLineNumber(getLineNumber()); }  }  if (node != null && node instanceof LiteralElement) ((LiteralElement)node).setQName(qname);  return node; }
/*      */   private void checkForSuperfluousAttributes(SyntaxTreeNode node, Attributes attrs) { QName qname = node.getQName(); boolean isStylesheet = node instanceof Stylesheet; String[] legal = this._instructionAttrs.get(qname.getStringRep()); if (this.versionIsOne && legal != null) { int n = attrs.getLength(); for (int i = 0; i < n; i++) { String attrQName = attrs.getQName(i); if (isStylesheet && attrQName.equals("version")) this.versionIsOne = attrs.getValue(i).equals("1.0");  if (!attrQName.startsWith("xml") && attrQName.indexOf(':') <= 0) { int j; for (j = 0; j < legal.length && !attrQName.equalsIgnoreCase(legal[j]); j++); if (j == legal.length) { ErrorMsg err = new ErrorMsg("ILLEGAL_ATTRIBUTE_ERR", attrQName, node); err.setWarningError(true); reportError(4, err); }  }  }  }  }
/*      */   public Expression parseExpression(SyntaxTreeNode parent, String exp) { return (Expression)parseTopLevel(parent, "<EXPRESSION>" + exp, null); }
/*      */   public Expression parseExpression(SyntaxTreeNode parent, String attr, String def) { String exp = parent.getAttribute(attr); if (exp.length() == 0 && def != null) exp = def;  return (Expression)parseTopLevel(parent, "<EXPRESSION>" + exp, exp); }
/*      */   public Pattern parsePattern(SyntaxTreeNode parent, String pattern) { return (Pattern)parseTopLevel(parent, "<PATTERN>" + pattern, pattern); }
/*      */   public Pattern parsePattern(SyntaxTreeNode parent, String attr, String def) { String pattern = parent.getAttribute(attr); if (pattern.length() == 0 && def != null) pattern = def;  return (Pattern)parseTopLevel(parent, "<PATTERN>" + pattern, pattern); }
/*      */   private SyntaxTreeNode parseTopLevel(SyntaxTreeNode parent, String text, String expression) { int line = getLineNumber(); try { this._xpathParser.setScanner(new XPathLexer(new StringReader(text))); Symbol result = this._xpathParser.parse(expression, line); if (result != null) { SyntaxTreeNode node = (SyntaxTreeNode)result.value; if (node != null) { node.setParser(this); node.setParent(parent); node.setLineNumber(line); return node; }  }  reportError(3, new ErrorMsg("XPATH_PARSER_ERR", expression, parent)); } catch (Exception e) { if (this._xsltc.debug()) e.printStackTrace();  reportError(3, new ErrorMsg("XPATH_PARSER_ERR", expression, parent)); }  SyntaxTreeNode.Dummy.setParser(this); return SyntaxTreeNode.Dummy; }
/*      */   public boolean errorsFound() { return (this._errors.size() > 0); }
/*      */   public void printErrors() { int size = this._errors.size(); if (size > 0) { System.err.println(new ErrorMsg("COMPILER_ERROR_KEY")); for (int i = 0; i < size; i++) System.err.println("  " + this._errors.get(i));  }  }
/*      */   public void printWarnings() { int size = this._warnings.size(); if (size > 0) { System.err.println(new ErrorMsg("COMPILER_WARNING_KEY")); for (int i = 0; i < size; i++) System.err.println("  " + this._warnings.get(i));  }  }
/*      */   public void reportError(int category, ErrorMsg error) { switch (category) { case 0: this._errors.add(error); break;case 1: this._errors.add(error); break;case 2: this._errors.add(error); break;case 3: this._errors.add(error); break;case 4: this._warnings.add(error); break; }  }
/*      */   public ArrayList<ErrorMsg> getErrors() { return this._errors; }
/*      */   public ArrayList<ErrorMsg> getWarnings() { return this._warnings; }
/*      */   public void endDocument() {}
/* 1236 */   public void startPrefixMapping(String prefix, String uri) { if (this._prefixMapping == null) {
/* 1237 */       this._prefixMapping = new HashMap<>();
/*      */     }
/* 1239 */     this._prefixMapping.put(prefix, uri); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endPrefixMapping(String prefix) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startElement(String uri, String localname, String qname, Attributes attributes) throws SAXException {
/* 1256 */     int col = qname.lastIndexOf(':');
/* 1257 */     String prefix = (col == -1) ? null : qname.substring(0, col);
/*      */     
/* 1259 */     SyntaxTreeNode element = makeInstance(uri, prefix, localname, attributes);
/*      */     
/* 1261 */     if (element == null) {
/* 1262 */       ErrorMsg err = new ErrorMsg("ELEMENT_PARSE_ERR", prefix + ':' + localname);
/*      */       
/* 1264 */       throw new SAXException(err.toString());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1269 */     if (this._root == null) {
/* 1270 */       if (this._prefixMapping == null || 
/* 1271 */         !this._prefixMapping.containsValue("http://www.w3.org/1999/XSL/Transform")) {
/* 1272 */         this._rootNamespaceDef = false;
/*      */       } else {
/* 1274 */         this._rootNamespaceDef = true;
/* 1275 */       }  this._root = element;
/*      */     } else {
/*      */       
/* 1278 */       SyntaxTreeNode parent = this._parentStack.peek();
/* 1279 */       parent.addElement(element);
/* 1280 */       element.setParent(parent);
/*      */     } 
/* 1282 */     element.setAttributes(new AttributesImpl(attributes));
/* 1283 */     element.setPrefixMapping(this._prefixMapping);
/*      */     
/* 1285 */     if (element instanceof Stylesheet) {
/*      */ 
/*      */ 
/*      */       
/* 1289 */       getSymbolTable().setCurrentNode(element);
/* 1290 */       ((Stylesheet)element).declareExtensionPrefixes(this);
/*      */     } 
/*      */     
/* 1293 */     this._prefixMapping = null;
/* 1294 */     this._parentStack.push(element);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endElement(String uri, String localname, String qname) {
/* 1301 */     this._parentStack.pop();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void characters(char[] ch, int start, int length) {
/* 1308 */     String string = new String(ch, start, length);
/* 1309 */     SyntaxTreeNode parent = this._parentStack.peek();
/*      */     
/* 1311 */     if (string.length() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1315 */     if (parent instanceof Text) {
/* 1316 */       ((Text)parent).setText(string);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1321 */     if (parent instanceof Stylesheet)
/*      */       return; 
/* 1323 */     SyntaxTreeNode bro = parent.lastChild();
/* 1324 */     if (bro != null && bro instanceof Text) {
/* 1325 */       Text text = (Text)bro;
/* 1326 */       if (!text.isTextElement() && (
/* 1327 */         length > 1 || ch[0] < 'Ā')) {
/* 1328 */         text.setText(string);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/* 1335 */     parent.addElement(new Text(string));
/*      */   }
/*      */   
/*      */   private String getTokenValue(String token) {
/* 1339 */     int start = token.indexOf('"');
/* 1340 */     int stop = token.lastIndexOf('"');
/* 1341 */     return token.substring(start + 1, stop);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processingInstruction(String name, String value) {
/* 1350 */     if (this._target == null && name.equals("xml-stylesheet")) {
/*      */       
/* 1352 */       String href = null;
/* 1353 */       String media = null;
/* 1354 */       String title = null;
/* 1355 */       String charset = null;
/*      */ 
/*      */       
/* 1358 */       StringTokenizer tokens = new StringTokenizer(value);
/* 1359 */       while (tokens.hasMoreElements()) {
/* 1360 */         String token = (String)tokens.nextElement();
/* 1361 */         if (token.startsWith("href")) {
/* 1362 */           href = getTokenValue(token); continue;
/* 1363 */         }  if (token.startsWith("media")) {
/* 1364 */           media = getTokenValue(token); continue;
/* 1365 */         }  if (token.startsWith("title")) {
/* 1366 */           title = getTokenValue(token); continue;
/* 1367 */         }  if (token.startsWith("charset")) {
/* 1368 */           charset = getTokenValue(token);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1373 */       if ((this._PImedia == null || this._PImedia.equals(media)) && (this._PItitle == null || this._PImedia
/* 1374 */         .equals(title)) && (this._PIcharset == null || this._PImedia
/* 1375 */         .equals(charset))) {
/* 1376 */         this._target = href;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ignorableWhitespace(char[] ch, int start, int length) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void skippedEntity(String name) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDocumentLocator(Locator locator) {
/* 1396 */     this._locator = locator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getLineNumber() {
/* 1404 */     int line = 0;
/* 1405 */     if (this._locator != null)
/* 1406 */       line = this._locator.getLineNumber(); 
/* 1407 */     return line;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/Parser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */