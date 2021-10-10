/*      */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*      */ 
/*      */ import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
/*      */ import com.sun.org.apache.bcel.internal.generic.BasicType;
/*      */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*      */ import com.sun.org.apache.bcel.internal.generic.FieldGen;
/*      */ import com.sun.org.apache.bcel.internal.generic.GETFIELD;
/*      */ import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
/*      */ import com.sun.org.apache.bcel.internal.generic.ISTORE;
/*      */ import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
/*      */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*      */ import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
/*      */ import com.sun.org.apache.bcel.internal.generic.NEW;
/*      */ import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
/*      */ import com.sun.org.apache.bcel.internal.generic.PUSH;
/*      */ import com.sun.org.apache.bcel.internal.generic.PUTFIELD;
/*      */ import com.sun.org.apache.bcel.internal.generic.PUTSTATIC;
/*      */ import com.sun.org.apache.bcel.internal.generic.TargetLostException;
/*      */ import com.sun.org.apache.bcel.internal.generic.Type;
/*      */ import com.sun.org.apache.bcel.internal.util.InstructionFinder;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*      */ import com.sun.org.apache.xml.internal.utils.SystemIDResolver;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Stylesheet
/*      */   extends SyntaxTreeNode
/*      */ {
/*      */   private String _version;
/*      */   private QName _name;
/*      */   private String _systemId;
/*      */   private Stylesheet _parentStylesheet;
/*   94 */   private Vector _globals = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   99 */   private Boolean _hasLocalParams = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String _className;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  109 */   private final Vector _templates = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  115 */   private Vector _allValidTemplates = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  120 */   private int _nextModeSerial = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  125 */   private final Map<String, Mode> _modes = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Mode _defaultMode;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  135 */   private final Map<String, String> _extensions = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  141 */   public Stylesheet _importedFrom = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  147 */   public Stylesheet _includedFrom = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  152 */   private Vector _includedStylesheets = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  157 */   private int _importPrecedence = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  163 */   private int _minimumDescendantPrecedence = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  168 */   private Map<String, Key> _keys = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  174 */   private SourceLoader _loader = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _numberFormattingUsed = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _simplified = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _multiDocument = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _callsNodeset = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _hasIdCall = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _templateInlining = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  211 */   private Output _lastOutputElement = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  216 */   private Properties _outputProperties = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  222 */   private int _outputMethod = 0;
/*      */   
/*      */   public static final int UNKNOWN_OUTPUT = 0;
/*      */   
/*      */   public static final int XML_OUTPUT = 1;
/*      */   
/*      */   public static final int HTML_OUTPUT = 2;
/*      */   
/*      */   public static final int TEXT_OUTPUT = 3;
/*      */ 
/*      */   
/*      */   public int getOutputMethod() {
/*  234 */     return this._outputMethod;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkOutputMethod() {
/*  241 */     if (this._lastOutputElement != null) {
/*  242 */       String method = this._lastOutputElement.getOutputMethod();
/*  243 */       if (method != null)
/*  244 */         if (method.equals("xml")) {
/*  245 */           this._outputMethod = 1;
/*  246 */         } else if (method.equals("html")) {
/*  247 */           this._outputMethod = 2;
/*  248 */         } else if (method.equals("text")) {
/*  249 */           this._outputMethod = 3;
/*      */         }  
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getTemplateInlining() {
/*  255 */     return this._templateInlining;
/*      */   }
/*      */   
/*      */   public void setTemplateInlining(boolean flag) {
/*  259 */     this._templateInlining = flag;
/*      */   }
/*      */   
/*      */   public boolean isSimplified() {
/*  263 */     return this._simplified;
/*      */   }
/*      */   
/*      */   public void setSimplified() {
/*  267 */     this._simplified = true;
/*      */   }
/*      */   
/*      */   public void setHasIdCall(boolean flag) {
/*  271 */     this._hasIdCall = flag;
/*      */   }
/*      */   
/*      */   public void setOutputProperty(String key, String value) {
/*  275 */     if (this._outputProperties == null) {
/*  276 */       this._outputProperties = new Properties();
/*      */     }
/*  278 */     this._outputProperties.setProperty(key, value);
/*      */   }
/*      */   
/*      */   public void setOutputProperties(Properties props) {
/*  282 */     this._outputProperties = props;
/*      */   }
/*      */   
/*      */   public Properties getOutputProperties() {
/*  286 */     return this._outputProperties;
/*      */   }
/*      */   
/*      */   public Output getLastOutputElement() {
/*  290 */     return this._lastOutputElement;
/*      */   }
/*      */   
/*      */   public void setMultiDocument(boolean flag) {
/*  294 */     this._multiDocument = flag;
/*      */   }
/*      */   
/*      */   public boolean isMultiDocument() {
/*  298 */     return this._multiDocument;
/*      */   }
/*      */   
/*      */   public void setCallsNodeset(boolean flag) {
/*  302 */     if (flag) setMultiDocument(flag); 
/*  303 */     this._callsNodeset = flag;
/*      */   }
/*      */   
/*      */   public boolean callsNodeset() {
/*  307 */     return this._callsNodeset;
/*      */   }
/*      */   
/*      */   public void numberFormattingUsed() {
/*  311 */     this._numberFormattingUsed = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  318 */     Stylesheet parent = getParentStylesheet();
/*  319 */     if (null != parent) parent.numberFormattingUsed();
/*      */   
/*      */   }
/*      */   
/*      */   public void setImportPrecedence(int precedence) {
/*  324 */     this._importPrecedence = precedence;
/*      */ 
/*      */     
/*  327 */     Iterator<SyntaxTreeNode> elements = elements();
/*  328 */     while (elements.hasNext()) {
/*  329 */       SyntaxTreeNode child = elements.next();
/*  330 */       if (child instanceof Include) {
/*  331 */         Stylesheet included = ((Include)child).getIncludedStylesheet();
/*  332 */         if (included != null && included._includedFrom == this) {
/*  333 */           included.setImportPrecedence(precedence);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  339 */     if (this._importedFrom != null) {
/*  340 */       if (this._importedFrom.getImportPrecedence() < precedence) {
/*  341 */         Parser parser = getParser();
/*  342 */         int nextPrecedence = parser.getNextImportPrecedence();
/*  343 */         this._importedFrom.setImportPrecedence(nextPrecedence);
/*      */       }
/*      */     
/*      */     }
/*  347 */     else if (this._includedFrom != null && 
/*  348 */       this._includedFrom.getImportPrecedence() != precedence) {
/*  349 */       this._includedFrom.setImportPrecedence(precedence);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getImportPrecedence() {
/*  354 */     return this._importPrecedence;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinimumDescendantPrecedence() {
/*  363 */     if (this._minimumDescendantPrecedence == -1) {
/*      */       
/*  365 */       int min = getImportPrecedence();
/*      */ 
/*      */ 
/*      */       
/*  369 */       int inclImpCount = (this._includedStylesheets != null) ? this._includedStylesheets.size() : 0;
/*      */ 
/*      */       
/*  372 */       for (int i = 0; i < inclImpCount; i++) {
/*      */         
/*  374 */         int prec = ((Stylesheet)this._includedStylesheets.elementAt(i)).getMinimumDescendantPrecedence();
/*      */         
/*  376 */         if (prec < min) {
/*  377 */           min = prec;
/*      */         }
/*      */       } 
/*      */       
/*  381 */       this._minimumDescendantPrecedence = min;
/*      */     } 
/*  383 */     return this._minimumDescendantPrecedence;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean checkForLoop(String systemId) {
/*  388 */     if (this._systemId != null && this._systemId.equals(systemId)) {
/*  389 */       return true;
/*      */     }
/*      */     
/*  392 */     if (this._parentStylesheet != null) {
/*  393 */       return this._parentStylesheet.checkForLoop(systemId);
/*      */     }
/*  395 */     return false;
/*      */   }
/*      */   
/*      */   public void setParser(Parser parser) {
/*  399 */     super.setParser(parser);
/*  400 */     this._name = makeStylesheetName("__stylesheet_");
/*      */   }
/*      */   
/*      */   public void setParentStylesheet(Stylesheet parent) {
/*  404 */     this._parentStylesheet = parent;
/*      */   }
/*      */   
/*      */   public Stylesheet getParentStylesheet() {
/*  408 */     return this._parentStylesheet;
/*      */   }
/*      */   
/*      */   public void setImportingStylesheet(Stylesheet parent) {
/*  412 */     this._importedFrom = parent;
/*  413 */     parent.addIncludedStylesheet(this);
/*      */   }
/*      */   
/*      */   public void setIncludingStylesheet(Stylesheet parent) {
/*  417 */     this._includedFrom = parent;
/*  418 */     parent.addIncludedStylesheet(this);
/*      */   }
/*      */   
/*      */   public void addIncludedStylesheet(Stylesheet child) {
/*  422 */     if (this._includedStylesheets == null) {
/*  423 */       this._includedStylesheets = new Vector();
/*      */     }
/*  425 */     this._includedStylesheets.addElement(child);
/*      */   }
/*      */   
/*      */   public void setSystemId(String systemId) {
/*  429 */     if (systemId != null) {
/*  430 */       this._systemId = SystemIDResolver.getAbsoluteURI(systemId);
/*      */     }
/*      */   }
/*      */   
/*      */   public String getSystemId() {
/*  435 */     return this._systemId;
/*      */   }
/*      */   
/*      */   public void setSourceLoader(SourceLoader loader) {
/*  439 */     this._loader = loader;
/*      */   }
/*      */   
/*      */   public SourceLoader getSourceLoader() {
/*  443 */     return this._loader;
/*      */   }
/*      */   
/*      */   private QName makeStylesheetName(String prefix) {
/*  447 */     return getParser().getQName(prefix + getXSLTC().nextStylesheetSerial());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasGlobals() {
/*  454 */     return (this._globals.size() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasLocalParams() {
/*  463 */     if (this._hasLocalParams == null) {
/*  464 */       Vector<Template> templates = getAllValidTemplates();
/*  465 */       int n = templates.size();
/*  466 */       for (int i = 0; i < n; i++) {
/*  467 */         Template template = templates.elementAt(i);
/*  468 */         if (template.hasParams()) {
/*  469 */           this._hasLocalParams = Boolean.TRUE;
/*  470 */           return true;
/*      */         } 
/*      */       } 
/*  473 */       this._hasLocalParams = Boolean.FALSE;
/*  474 */       return false;
/*      */     } 
/*      */     
/*  477 */     return this._hasLocalParams.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addPrefixMapping(String prefix, String uri) {
/*  487 */     if (prefix.equals("") && uri.equals("http://www.w3.org/1999/xhtml"))
/*  488 */       return;  super.addPrefixMapping(prefix, uri);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void extensionURI(String prefixes, SymbolTable stable) {
/*  495 */     if (prefixes != null) {
/*  496 */       StringTokenizer tokens = new StringTokenizer(prefixes);
/*  497 */       while (tokens.hasMoreTokens()) {
/*  498 */         String prefix = tokens.nextToken();
/*  499 */         String uri = lookupNamespace(prefix);
/*  500 */         if (uri != null) {
/*  501 */           this._extensions.put(uri, prefix);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isExtension(String uri) {
/*  508 */     return (this._extensions.get(uri) != null);
/*      */   }
/*      */   
/*      */   public void declareExtensionPrefixes(Parser parser) {
/*  512 */     SymbolTable stable = parser.getSymbolTable();
/*  513 */     String extensionPrefixes = getAttribute("extension-element-prefixes");
/*  514 */     extensionURI(extensionPrefixes, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void parseContents(Parser parser) {
/*  523 */     SymbolTable stable = parser.getSymbolTable();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  537 */     addPrefixMapping("xml", "http://www.w3.org/XML/1998/namespace");
/*      */ 
/*      */     
/*  540 */     Stylesheet sheet = stable.addStylesheet(this._name, this);
/*  541 */     if (sheet != null) {
/*      */       
/*  543 */       ErrorMsg err = new ErrorMsg("MULTIPLE_STYLESHEET_ERR", this);
/*  544 */       parser.reportError(3, err);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  552 */     if (this._simplified) {
/*  553 */       stable.excludeURI("http://www.w3.org/1999/XSL/Transform");
/*  554 */       Template template = new Template();
/*  555 */       template.parseSimplified(this, parser);
/*      */     }
/*      */     else {
/*      */       
/*  559 */       parseOwnChildren(parser);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void parseOwnChildren(Parser parser) {
/*  567 */     SymbolTable stable = parser.getSymbolTable();
/*  568 */     String excludePrefixes = getAttribute("exclude-result-prefixes");
/*  569 */     String extensionPrefixes = getAttribute("extension-element-prefixes");
/*      */ 
/*      */     
/*  572 */     stable.pushExcludedNamespacesContext();
/*  573 */     stable.excludeURI("http://www.w3.org/1999/XSL/Transform");
/*  574 */     stable.excludeNamespaces(excludePrefixes);
/*  575 */     stable.excludeNamespaces(extensionPrefixes);
/*      */     
/*  577 */     List<SyntaxTreeNode> contents = getContents();
/*  578 */     int count = contents.size();
/*      */     
/*      */     int i;
/*      */     
/*  582 */     for (i = 0; i < count; i++) {
/*  583 */       SyntaxTreeNode child = contents.get(i);
/*  584 */       if (child instanceof VariableBase || child instanceof NamespaceAlias) {
/*      */         
/*  586 */         parser.getSymbolTable().setCurrentNode(child);
/*  587 */         child.parseContents(parser);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  592 */     for (i = 0; i < count; i++) {
/*  593 */       SyntaxTreeNode child = contents.get(i);
/*  594 */       if (!(child instanceof VariableBase) && !(child instanceof NamespaceAlias)) {
/*      */         
/*  596 */         parser.getSymbolTable().setCurrentNode(child);
/*  597 */         child.parseContents(parser);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  602 */       if (!this._templateInlining && child instanceof Template) {
/*  603 */         Template template = (Template)child;
/*  604 */         String name = "template$dot$" + template.getPosition();
/*  605 */         template.setName(parser.getQName(name));
/*      */       } 
/*      */     } 
/*      */     
/*  609 */     stable.popExcludedNamespacesContext();
/*      */   }
/*      */   
/*      */   public void processModes() {
/*  613 */     if (this._defaultMode == null)
/*  614 */       this._defaultMode = new Mode(null, this, ""); 
/*  615 */     this._defaultMode.processPatterns(this._keys);
/*  616 */     for (Mode mode : this._modes.values()) {
/*  617 */       mode.processPatterns(this._keys);
/*      */     }
/*      */   }
/*      */   
/*      */   private void compileModes(ClassGenerator classGen) {
/*  622 */     this._defaultMode.compileApplyTemplates(classGen);
/*  623 */     for (Mode mode : this._modes.values()) {
/*  624 */       mode.compileApplyTemplates(classGen);
/*      */     }
/*      */   }
/*      */   
/*      */   public Mode getMode(QName modeName) {
/*  629 */     if (modeName == null) {
/*  630 */       if (this._defaultMode == null) {
/*  631 */         this._defaultMode = new Mode(null, this, "");
/*      */       }
/*  633 */       return this._defaultMode;
/*      */     } 
/*      */     
/*  636 */     Mode mode = this._modes.get(modeName.getStringRep());
/*  637 */     if (mode == null) {
/*  638 */       String suffix = Integer.toString(this._nextModeSerial++);
/*  639 */       this._modes.put(modeName.getStringRep(), mode = new Mode(modeName, this, suffix));
/*      */     } 
/*  641 */     return mode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/*  649 */     int count = this._globals.size();
/*  650 */     for (int i = 0; i < count; i++) {
/*  651 */       VariableBase var = this._globals.elementAt(i);
/*  652 */       var.typeCheck(stable);
/*      */     } 
/*  654 */     return typeCheckContents(stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/*  661 */     translate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addDOMField(ClassGenerator classGen) {
/*  668 */     FieldGen fgen = new FieldGen(1, Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/DOM;"), "_dom", classGen.getConstantPool());
/*  669 */     classGen.addField(fgen.getField());
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
/*      */   private void addStaticField(ClassGenerator classGen, String type, String name) {
/*  681 */     FieldGen fgen = new FieldGen(12, Util.getJCRefType(type), name, classGen.getConstantPool());
/*  682 */     classGen.addField(fgen.getField());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void translate() {
/*  690 */     this._className = getXSLTC().getClassName();
/*      */ 
/*      */     
/*  693 */     ClassGenerator classGen = new ClassGenerator(this._className, "com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "", 33, null, this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  700 */     addDOMField(classGen);
/*      */ 
/*      */ 
/*      */     
/*  704 */     compileTransform(classGen);
/*      */ 
/*      */     
/*  707 */     Iterator<SyntaxTreeNode> elements = elements();
/*  708 */     while (elements.hasNext()) {
/*  709 */       SyntaxTreeNode element = elements.next();
/*      */       
/*  711 */       if (element instanceof Template) {
/*      */         
/*  713 */         Template template = (Template)element;
/*      */         
/*  715 */         getMode(template.getModeName()).addTemplate(template);
/*      */         continue;
/*      */       } 
/*  718 */       if (element instanceof AttributeSet) {
/*  719 */         ((AttributeSet)element).translate(classGen, null); continue;
/*      */       } 
/*  721 */       if (element instanceof Output) {
/*      */         
/*  723 */         Output output = (Output)element;
/*  724 */         if (output.enabled()) this._lastOutputElement = output;
/*      */       
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  733 */     checkOutputMethod();
/*  734 */     processModes();
/*  735 */     compileModes(classGen);
/*  736 */     compileStaticInitializer(classGen);
/*  737 */     compileConstructor(classGen, this._lastOutputElement);
/*      */     
/*  739 */     if (!getParser().errorsFound()) {
/*  740 */       getXSLTC().dumpClass(classGen.getJavaClass());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void compileStaticInitializer(ClassGenerator classGen) {
/*  751 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  752 */     InstructionList il = new InstructionList();
/*      */     
/*  754 */     MethodGenerator staticConst = new MethodGenerator(9, Type.VOID, null, null, "<clinit>", this._className, il, cpg);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  760 */     addStaticField(classGen, "[Ljava/lang/String;", "_sNamesArray");
/*  761 */     addStaticField(classGen, "[Ljava/lang/String;", "_sUrisArray");
/*  762 */     addStaticField(classGen, "[I", "_sTypesArray");
/*  763 */     addStaticField(classGen, "[Ljava/lang/String;", "_sNamespaceArray");
/*      */ 
/*      */     
/*  766 */     int charDataFieldCount = getXSLTC().getCharacterDataCount();
/*  767 */     for (int i = 0; i < charDataFieldCount; i++) {
/*  768 */       addStaticField(classGen, "[C", "_scharData" + i);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  773 */     Vector<String> namesIndex = getXSLTC().getNamesIndex();
/*  774 */     int size = namesIndex.size();
/*  775 */     String[] namesArray = new String[size];
/*  776 */     String[] urisArray = new String[size];
/*  777 */     int[] typesArray = new int[size];
/*      */ 
/*      */     
/*  780 */     for (int j = 0; j < size; j++) {
/*  781 */       String encodedName = namesIndex.elementAt(j); int index;
/*  782 */       if ((index = encodedName.lastIndexOf(':')) > -1) {
/*  783 */         urisArray[j] = encodedName.substring(0, index);
/*      */       }
/*      */       
/*  786 */       index++;
/*  787 */       if (encodedName.charAt(index) == '@') {
/*  788 */         typesArray[j] = 2;
/*  789 */         index++;
/*  790 */       } else if (encodedName.charAt(index) == '?') {
/*  791 */         typesArray[j] = 13;
/*  792 */         index++;
/*      */       } else {
/*  794 */         typesArray[j] = 1;
/*      */       } 
/*      */       
/*  797 */       if (index == 0) {
/*  798 */         namesArray[j] = encodedName;
/*      */       } else {
/*      */         
/*  801 */         namesArray[j] = encodedName.substring(index);
/*      */       } 
/*      */     } 
/*      */     
/*  805 */     staticConst.markChunkStart();
/*  806 */     il.append(new PUSH(cpg, size));
/*  807 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/*  808 */     int namesArrayRef = cpg.addFieldref(this._className, "_sNamesArray", "[Ljava/lang/String;");
/*      */ 
/*      */     
/*  811 */     il.append(new PUTSTATIC(namesArrayRef));
/*  812 */     staticConst.markChunkEnd();
/*      */     
/*  814 */     for (int k = 0; k < size; k++) {
/*  815 */       String name = namesArray[k];
/*  816 */       staticConst.markChunkStart();
/*  817 */       il.append(new GETSTATIC(namesArrayRef));
/*  818 */       il.append(new PUSH(cpg, k));
/*  819 */       il.append(new PUSH(cpg, name));
/*  820 */       il.append(AASTORE);
/*  821 */       staticConst.markChunkEnd();
/*      */     } 
/*      */     
/*  824 */     staticConst.markChunkStart();
/*  825 */     il.append(new PUSH(cpg, size));
/*  826 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/*  827 */     int urisArrayRef = cpg.addFieldref(this._className, "_sUrisArray", "[Ljava/lang/String;");
/*      */ 
/*      */     
/*  830 */     il.append(new PUTSTATIC(urisArrayRef));
/*  831 */     staticConst.markChunkEnd();
/*      */     
/*  833 */     for (int m = 0; m < size; m++) {
/*  834 */       String uri = urisArray[m];
/*  835 */       staticConst.markChunkStart();
/*  836 */       il.append(new GETSTATIC(urisArrayRef));
/*  837 */       il.append(new PUSH(cpg, m));
/*  838 */       il.append(new PUSH(cpg, uri));
/*  839 */       il.append(AASTORE);
/*  840 */       staticConst.markChunkEnd();
/*      */     } 
/*      */     
/*  843 */     staticConst.markChunkStart();
/*  844 */     il.append(new PUSH(cpg, size));
/*  845 */     il.append(new NEWARRAY(BasicType.INT));
/*  846 */     int typesArrayRef = cpg.addFieldref(this._className, "_sTypesArray", "[I");
/*      */ 
/*      */     
/*  849 */     il.append(new PUTSTATIC(typesArrayRef));
/*  850 */     staticConst.markChunkEnd();
/*      */     
/*  852 */     for (int n = 0; n < size; n++) {
/*  853 */       int nodeType = typesArray[n];
/*  854 */       staticConst.markChunkStart();
/*  855 */       il.append(new GETSTATIC(typesArrayRef));
/*  856 */       il.append(new PUSH(cpg, n));
/*  857 */       il.append(new PUSH(cpg, nodeType));
/*  858 */       il.append(IASTORE);
/*      */     } 
/*      */ 
/*      */     
/*  862 */     Vector<String> namespaces = getXSLTC().getNamespaceIndex();
/*  863 */     staticConst.markChunkStart();
/*  864 */     il.append(new PUSH(cpg, namespaces.size()));
/*  865 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/*  866 */     int namespaceArrayRef = cpg.addFieldref(this._className, "_sNamespaceArray", "[Ljava/lang/String;");
/*      */ 
/*      */     
/*  869 */     il.append(new PUTSTATIC(namespaceArrayRef));
/*  870 */     staticConst.markChunkEnd();
/*      */     
/*  872 */     for (int i1 = 0; i1 < namespaces.size(); i1++) {
/*  873 */       String ns = namespaces.elementAt(i1);
/*  874 */       staticConst.markChunkStart();
/*  875 */       il.append(new GETSTATIC(namespaceArrayRef));
/*  876 */       il.append(new PUSH(cpg, i1));
/*  877 */       il.append(new PUSH(cpg, ns));
/*  878 */       il.append(AASTORE);
/*  879 */       staticConst.markChunkEnd();
/*      */     } 
/*      */ 
/*      */     
/*  883 */     int charDataCount = getXSLTC().getCharacterDataCount();
/*  884 */     int toCharArray = cpg.addMethodref("java.lang.String", "toCharArray", "()[C");
/*  885 */     for (int i2 = 0; i2 < charDataCount; i2++) {
/*  886 */       staticConst.markChunkStart();
/*  887 */       il.append(new PUSH(cpg, getXSLTC().getCharacterData(i2)));
/*  888 */       il.append(new INVOKEVIRTUAL(toCharArray));
/*  889 */       il.append(new PUTSTATIC(cpg.addFieldref(this._className, "_scharData" + i2, "[C")));
/*      */ 
/*      */       
/*  892 */       staticConst.markChunkEnd();
/*      */     } 
/*      */     
/*  895 */     il.append(RETURN);
/*      */     
/*  897 */     classGen.addMethod(staticConst);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void compileConstructor(ClassGenerator classGen, Output output) {
/*  906 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  907 */     InstructionList il = new InstructionList();
/*      */     
/*  909 */     MethodGenerator constructor = new MethodGenerator(1, Type.VOID, null, null, "<init>", this._className, il, cpg);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  916 */     il.append(classGen.loadTranslet());
/*  917 */     il.append(new INVOKESPECIAL(cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "<init>", "()V")));
/*      */ 
/*      */     
/*  920 */     constructor.markChunkStart();
/*  921 */     il.append(classGen.loadTranslet());
/*  922 */     il.append(new GETSTATIC(cpg.addFieldref(this._className, "_sNamesArray", "[Ljava/lang/String;")));
/*      */ 
/*      */     
/*  925 */     il.append(new PUTFIELD(cpg.addFieldref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "namesArray", "[Ljava/lang/String;")));
/*      */ 
/*      */ 
/*      */     
/*  929 */     il.append(classGen.loadTranslet());
/*  930 */     il.append(new GETSTATIC(cpg.addFieldref(this._className, "_sUrisArray", "[Ljava/lang/String;")));
/*      */ 
/*      */     
/*  933 */     il.append(new PUTFIELD(cpg.addFieldref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "urisArray", "[Ljava/lang/String;")));
/*      */ 
/*      */     
/*  936 */     constructor.markChunkEnd();
/*      */     
/*  938 */     constructor.markChunkStart();
/*  939 */     il.append(classGen.loadTranslet());
/*  940 */     il.append(new GETSTATIC(cpg.addFieldref(this._className, "_sTypesArray", "[I")));
/*      */ 
/*      */     
/*  943 */     il.append(new PUTFIELD(cpg.addFieldref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "typesArray", "[I")));
/*      */ 
/*      */     
/*  946 */     constructor.markChunkEnd();
/*      */     
/*  948 */     constructor.markChunkStart();
/*  949 */     il.append(classGen.loadTranslet());
/*  950 */     il.append(new GETSTATIC(cpg.addFieldref(this._className, "_sNamespaceArray", "[Ljava/lang/String;")));
/*      */ 
/*      */     
/*  953 */     il.append(new PUTFIELD(cpg.addFieldref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "namespaceArray", "[Ljava/lang/String;")));
/*      */ 
/*      */     
/*  956 */     constructor.markChunkEnd();
/*      */     
/*  958 */     constructor.markChunkStart();
/*  959 */     il.append(classGen.loadTranslet());
/*  960 */     il.append(new PUSH(cpg, 101));
/*  961 */     il.append(new PUTFIELD(cpg.addFieldref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "transletVersion", "I")));
/*      */ 
/*      */     
/*  964 */     constructor.markChunkEnd();
/*      */     
/*  966 */     if (this._hasIdCall) {
/*  967 */       constructor.markChunkStart();
/*  968 */       il.append(classGen.loadTranslet());
/*  969 */       il.append(new PUSH(cpg, Boolean.TRUE));
/*  970 */       il.append(new PUTFIELD(cpg.addFieldref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "_hasIdCall", "Z")));
/*      */ 
/*      */       
/*  973 */       constructor.markChunkEnd();
/*      */     } 
/*      */ 
/*      */     
/*  977 */     if (output != null) {
/*      */       
/*  979 */       constructor.markChunkStart();
/*  980 */       output.translate(classGen, constructor);
/*  981 */       constructor.markChunkEnd();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  986 */     if (this._numberFormattingUsed) {
/*  987 */       constructor.markChunkStart();
/*  988 */       DecimalFormatting.translateDefaultDFS(classGen, constructor);
/*  989 */       constructor.markChunkEnd();
/*      */     } 
/*      */     
/*  992 */     il.append(RETURN);
/*      */     
/*  994 */     classGen.addMethod(constructor);
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
/*      */   private String compileTopLevel(ClassGenerator classGen) {
/* 1011 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1016 */     Type[] argTypes = { Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/DOM;"), Util.getJCRefType("Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;"), Util.getJCRefType("Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;") };
/*      */ 
/*      */     
/* 1019 */     String[] argNames = { "document", "iterator", "handler" };
/*      */ 
/*      */ 
/*      */     
/* 1023 */     InstructionList il = new InstructionList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1030 */     MethodGenerator toplevel = new MethodGenerator(1, Type.VOID, argTypes, argNames, "topLevel", this._className, il, classGen.getConstantPool());
/*      */     
/* 1032 */     toplevel.addException("com.sun.org.apache.xalan.internal.xsltc.TransletException");
/*      */ 
/*      */ 
/*      */     
/* 1036 */     LocalVariableGen current = toplevel.addLocalVariable("current", Type.INT, (InstructionHandle)null, (InstructionHandle)null);
/*      */ 
/*      */ 
/*      */     
/* 1040 */     int setFilter = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "setFilter", "(Lcom/sun/org/apache/xalan/internal/xsltc/StripFilter;)V");
/*      */ 
/*      */ 
/*      */     
/* 1044 */     int gitr = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getIterator", "()Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;");
/*      */ 
/*      */     
/* 1047 */     il.append(toplevel.loadDOM());
/* 1048 */     il.append(new INVOKEINTERFACE(gitr, 1));
/* 1049 */     il.append(toplevel.nextNode());
/* 1050 */     current.setStart(il.append(new ISTORE(current.getIndex())));
/*      */ 
/*      */     
/* 1053 */     Vector<SyntaxTreeNode> varDepElements = new Vector(this._globals);
/* 1054 */     Iterator<SyntaxTreeNode> elements = elements();
/* 1055 */     while (elements.hasNext()) {
/* 1056 */       SyntaxTreeNode element = elements.next();
/* 1057 */       if (element instanceof Key) {
/* 1058 */         varDepElements.add(element);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1063 */     varDepElements = resolveDependencies(varDepElements);
/*      */ 
/*      */     
/* 1066 */     int count = varDepElements.size();
/* 1067 */     for (int i = 0; i < count; i++) {
/* 1068 */       TopLevelElement tle = (TopLevelElement)varDepElements.elementAt(i);
/* 1069 */       tle.translate(classGen, toplevel);
/* 1070 */       if (tle instanceof Key) {
/* 1071 */         Key key = (Key)tle;
/* 1072 */         this._keys.put(key.getName(), key);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1077 */     Vector whitespaceRules = new Vector();
/* 1078 */     elements = elements();
/* 1079 */     while (elements.hasNext()) {
/* 1080 */       SyntaxTreeNode element = elements.next();
/*      */       
/* 1082 */       if (element instanceof DecimalFormatting) {
/* 1083 */         ((DecimalFormatting)element).translate(classGen, toplevel);
/*      */         continue;
/*      */       } 
/* 1086 */       if (element instanceof Whitespace) {
/* 1087 */         whitespaceRules.addAll(((Whitespace)element).getRules());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1092 */     if (whitespaceRules.size() > 0) {
/* 1093 */       Whitespace.translateRules(whitespaceRules, classGen);
/*      */     }
/*      */     
/* 1096 */     if (classGen.containsMethod("stripSpace", "(Lcom/sun/org/apache/xalan/internal/xsltc/DOM;II)Z") != null) {
/* 1097 */       il.append(toplevel.loadDOM());
/* 1098 */       il.append(classGen.loadTranslet());
/* 1099 */       il.append(new INVOKEINTERFACE(setFilter, 2));
/*      */     } 
/*      */     
/* 1102 */     il.append(RETURN);
/*      */ 
/*      */     
/* 1105 */     classGen.addMethod(toplevel);
/*      */     
/* 1107 */     return "(Lcom/sun/org/apache/xalan/internal/xsltc/DOM;Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;)V";
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
/*      */   private Vector resolveDependencies(Vector<TopLevelElement> input) {
/* 1131 */     Vector<TopLevelElement> result = new Vector();
/* 1132 */     while (input.size() > 0) {
/* 1133 */       boolean changed = false;
/* 1134 */       for (int i = 0; i < input.size(); ) {
/* 1135 */         TopLevelElement vde = input.elementAt(i);
/* 1136 */         Vector<?> dep = vde.getDependencies();
/* 1137 */         if (dep == null || result.containsAll(dep)) {
/* 1138 */           result.addElement(vde);
/* 1139 */           input.remove(i);
/* 1140 */           changed = true;
/*      */           continue;
/*      */         } 
/* 1143 */         i++;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1148 */       if (!changed) {
/*      */         
/* 1150 */         ErrorMsg err = new ErrorMsg("CIRCULAR_VARIABLE_ERR", input.toString(), this);
/* 1151 */         getParser().reportError(3, err);
/* 1152 */         return result;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1164 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String compileBuildKeys(ClassGenerator classGen) {
/* 1174 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1179 */     Type[] argTypes = { Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/DOM;"), Util.getJCRefType("Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;"), Util.getJCRefType("Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;"), Type.INT };
/*      */ 
/*      */ 
/*      */     
/* 1183 */     String[] argNames = { "document", "iterator", "handler", "current" };
/*      */ 
/*      */ 
/*      */     
/* 1187 */     InstructionList il = new InstructionList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1194 */     MethodGenerator buildKeys = new MethodGenerator(1, Type.VOID, argTypes, argNames, "buildKeys", this._className, il, classGen.getConstantPool());
/*      */     
/* 1196 */     buildKeys.addException("com.sun.org.apache.xalan.internal.xsltc.TransletException");
/*      */     
/* 1198 */     Iterator<SyntaxTreeNode> elements = elements();
/* 1199 */     while (elements.hasNext()) {
/*      */       
/* 1201 */       SyntaxTreeNode element = elements.next();
/* 1202 */       if (element instanceof Key) {
/* 1203 */         Key key = (Key)element;
/* 1204 */         key.translate(classGen, buildKeys);
/* 1205 */         this._keys.put(key.getName(), key);
/*      */       } 
/*      */     } 
/*      */     
/* 1209 */     il.append(RETURN);
/*      */ 
/*      */     
/* 1212 */     buildKeys.stripAttributes(true);
/* 1213 */     buildKeys.setMaxLocals();
/* 1214 */     buildKeys.setMaxStack();
/* 1215 */     buildKeys.removeNOPs();
/*      */     
/* 1217 */     classGen.addMethod(buildKeys.getMethod());
/*      */     
/* 1219 */     return "(Lcom/sun/org/apache/xalan/internal/xsltc/DOM;Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;I)V";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void compileTransform(ClassGenerator classGen) {
/* 1228 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1234 */     Type[] argTypes = new Type[3];
/*      */     
/* 1236 */     argTypes[0] = Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/DOM;");
/* 1237 */     argTypes[1] = Util.getJCRefType("Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;");
/* 1238 */     argTypes[2] = Util.getJCRefType("Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;");
/*      */     
/* 1240 */     String[] argNames = new String[3];
/* 1241 */     argNames[0] = "document";
/* 1242 */     argNames[1] = "iterator";
/* 1243 */     argNames[2] = "handler";
/*      */     
/* 1245 */     InstructionList il = new InstructionList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1253 */     MethodGenerator transf = new MethodGenerator(1, Type.VOID, argTypes, argNames, "transform", this._className, il, classGen.getConstantPool());
/* 1254 */     transf.addException("com.sun.org.apache.xalan.internal.xsltc.TransletException");
/*      */ 
/*      */     
/* 1257 */     int check = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary", "resetPrefixIndex", "()V");
/* 1258 */     il.append(new INVOKESTATIC(check));
/*      */ 
/*      */ 
/*      */     
/* 1262 */     LocalVariableGen current = transf.addLocalVariable("current", Type.INT, (InstructionHandle)null, (InstructionHandle)null);
/*      */ 
/*      */     
/* 1265 */     String applyTemplatesSig = classGen.getApplyTemplatesSig();
/* 1266 */     int applyTemplates = cpg.addMethodref(getClassName(), "applyTemplates", applyTemplatesSig);
/*      */ 
/*      */     
/* 1269 */     int domField = cpg.addFieldref(getClassName(), "_dom", "Lcom/sun/org/apache/xalan/internal/xsltc/DOM;");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1274 */     il.append(classGen.loadTranslet());
/*      */ 
/*      */     
/* 1277 */     if (isMultiDocument()) {
/* 1278 */       il.append(new NEW(cpg.addClass("com.sun.org.apache.xalan.internal.xsltc.dom.MultiDOM")));
/* 1279 */       il.append(DUP);
/*      */     } 
/*      */     
/* 1282 */     il.append(classGen.loadTranslet());
/* 1283 */     il.append(transf.loadDOM());
/* 1284 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "makeDOMAdapter", "(Lcom/sun/org/apache/xalan/internal/xsltc/DOM;)Lcom/sun/org/apache/xalan/internal/xsltc/dom/DOMAdapter;")));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1290 */     if (isMultiDocument()) {
/* 1291 */       int init = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.dom.MultiDOM", "<init>", "(Lcom/sun/org/apache/xalan/internal/xsltc/DOM;)V");
/*      */ 
/*      */       
/* 1294 */       il.append(new INVOKESPECIAL(init));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1299 */     il.append(new PUTFIELD(domField));
/*      */ 
/*      */     
/* 1302 */     int gitr = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getIterator", "()Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;");
/*      */ 
/*      */     
/* 1305 */     il.append(transf.loadDOM());
/* 1306 */     il.append(new INVOKEINTERFACE(gitr, 1));
/* 1307 */     il.append(transf.nextNode());
/* 1308 */     current.setStart(il.append(new ISTORE(current.getIndex())));
/*      */ 
/*      */     
/* 1311 */     il.append(classGen.loadTranslet());
/* 1312 */     il.append(transf.loadHandler());
/* 1313 */     int index = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "transferOutputSettings", "(Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;)V");
/*      */ 
/*      */     
/* 1316 */     il.append(new INVOKEVIRTUAL(index));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1324 */     String keySig = compileBuildKeys(classGen);
/* 1325 */     int keyIdx = cpg.addMethodref(getClassName(), "buildKeys", keySig);
/*      */ 
/*      */ 
/*      */     
/* 1329 */     Iterator<SyntaxTreeNode> toplevel = elements();
/* 1330 */     if (this._globals.size() > 0 || toplevel.hasNext()) {
/*      */       
/* 1332 */       String topLevelSig = compileTopLevel(classGen);
/*      */       
/* 1334 */       int topLevelIdx = cpg.addMethodref(getClassName(), "topLevel", topLevelSig);
/*      */ 
/*      */ 
/*      */       
/* 1338 */       il.append(classGen.loadTranslet());
/* 1339 */       il.append(classGen.loadTranslet());
/* 1340 */       il.append(new GETFIELD(domField));
/* 1341 */       il.append(transf.loadIterator());
/* 1342 */       il.append(transf.loadHandler());
/* 1343 */       il.append(new INVOKEVIRTUAL(topLevelIdx));
/*      */     } 
/*      */ 
/*      */     
/* 1347 */     il.append(transf.loadHandler());
/* 1348 */     il.append(transf.startDocument());
/*      */ 
/*      */     
/* 1351 */     il.append(classGen.loadTranslet());
/*      */     
/* 1353 */     il.append(classGen.loadTranslet());
/* 1354 */     il.append(new GETFIELD(domField));
/*      */     
/* 1356 */     il.append(transf.loadIterator());
/* 1357 */     il.append(transf.loadHandler());
/* 1358 */     il.append(new INVOKEVIRTUAL(applyTemplates));
/*      */     
/* 1360 */     il.append(transf.loadHandler());
/* 1361 */     il.append(transf.endDocument());
/*      */     
/* 1363 */     il.append(RETURN);
/*      */ 
/*      */     
/* 1366 */     classGen.addMethod(transf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void peepHoleOptimization(MethodGenerator methodGen) {
/* 1374 */     String pattern = "`aload'`pop'`instruction'";
/* 1375 */     InstructionList il = methodGen.getInstructionList();
/* 1376 */     InstructionFinder find = new InstructionFinder(il);
/* 1377 */     for (Iterator<InstructionHandle[]> iter = find.search("`aload'`pop'`instruction'"); iter.hasNext(); ) {
/* 1378 */       InstructionHandle[] match = iter.next();
/*      */       try {
/* 1380 */         il.delete(match[0], match[1]);
/*      */       }
/* 1382 */       catch (TargetLostException targetLostException) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int addParam(Param param) {
/* 1389 */     this._globals.addElement(param);
/* 1390 */     return this._globals.size() - 1;
/*      */   }
/*      */   
/*      */   public int addVariable(Variable global) {
/* 1394 */     this._globals.addElement(global);
/* 1395 */     return this._globals.size() - 1;
/*      */   }
/*      */   
/*      */   public void display(int indent) {
/* 1399 */     indent(indent);
/* 1400 */     Util.println("Stylesheet");
/* 1401 */     displayContents(indent + 4);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNamespace(String prefix) {
/* 1406 */     return lookupNamespace(prefix);
/*      */   }
/*      */   
/*      */   public String getClassName() {
/* 1410 */     return this._className;
/*      */   }
/*      */   
/*      */   public Vector getTemplates() {
/* 1414 */     return this._templates;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vector getAllValidTemplates() {
/* 1419 */     if (this._includedStylesheets == null) {
/* 1420 */       return this._templates;
/*      */     }
/*      */ 
/*      */     
/* 1424 */     if (this._allValidTemplates == null) {
/* 1425 */       Vector templates = new Vector();
/* 1426 */       templates.addAll(this._templates);
/* 1427 */       int size = this._includedStylesheets.size();
/* 1428 */       for (int i = 0; i < size; i++) {
/* 1429 */         Stylesheet included = this._includedStylesheets.elementAt(i);
/* 1430 */         templates.addAll(included.getAllValidTemplates());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1435 */       if (this._parentStylesheet != null) {
/* 1436 */         return templates;
/*      */       }
/* 1438 */       this._allValidTemplates = templates;
/*      */     } 
/*      */     
/* 1441 */     return this._allValidTemplates;
/*      */   }
/*      */   
/*      */   protected void addTemplate(Template template) {
/* 1445 */     this._templates.addElement(template);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/Stylesheet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */