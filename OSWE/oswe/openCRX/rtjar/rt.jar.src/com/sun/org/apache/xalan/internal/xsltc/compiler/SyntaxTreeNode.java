/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
/*     */ import com.sun.org.apache.bcel.internal.generic.BasicType;
/*     */ import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.DUP_X1;
/*     */ import com.sun.org.apache.bcel.internal.generic.GETFIELD;
/*     */ import com.sun.org.apache.bcel.internal.generic.ICONST;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.bcel.internal.generic.NEW;
/*     */ import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
/*     */ import com.sun.org.apache.bcel.internal.generic.PUSH;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.helpers.AttributesImpl;
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
/*     */ public abstract class SyntaxTreeNode
/*     */   implements Constants
/*     */ {
/*     */   private Parser _parser;
/*     */   protected SyntaxTreeNode _parent;
/*     */   private Stylesheet _stylesheet;
/*     */   private Template _template;
/*  70 */   private final List<SyntaxTreeNode> _contents = new ArrayList<>(2);
/*     */   
/*     */   protected QName _qname;
/*     */   
/*     */   private int _line;
/*  75 */   protected AttributesImpl _attributes = null;
/*  76 */   private Map<String, String> _prefixMapping = null;
/*     */ 
/*     */   
/*  79 */   protected static final SyntaxTreeNode Dummy = new AbsolutePathPattern(null);
/*     */   
/*     */   protected static final int IndentIncrement = 4;
/*     */   
/*  83 */   private static final char[] _spaces = "                                                       "
/*  84 */     .toCharArray();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SyntaxTreeNode() {
/*  91 */     this._line = 0;
/*  92 */     this._qname = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SyntaxTreeNode(int line) {
/* 100 */     this._line = line;
/* 101 */     this._qname = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SyntaxTreeNode(String uri, String prefix, String local) {
/* 111 */     this._line = 0;
/* 112 */     setQName(uri, prefix, local);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setLineNumber(int line) {
/* 120 */     this._line = line;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getLineNumber() {
/* 130 */     if (this._line > 0) return this._line; 
/* 131 */     SyntaxTreeNode parent = getParent();
/* 132 */     return (parent != null) ? parent.getLineNumber() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setQName(QName qname) {
/* 140 */     this._qname = qname;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setQName(String uri, String prefix, String localname) {
/* 150 */     this._qname = new QName(uri, prefix, localname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected QName getQName() {
/* 158 */     return this._qname;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAttributes(AttributesImpl attributes) {
/* 167 */     this._attributes = attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getAttribute(String qname) {
/* 176 */     if (this._attributes == null) {
/* 177 */       return "";
/*     */     }
/* 179 */     String value = this._attributes.getValue(qname);
/* 180 */     return (value == null || value.equals("")) ? "" : value;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getAttribute(String prefix, String localName) {
/* 185 */     return getAttribute(prefix + ':' + localName);
/*     */   }
/*     */   
/*     */   protected boolean hasAttribute(String qname) {
/* 189 */     return (this._attributes != null && this._attributes.getValue(qname) != null);
/*     */   }
/*     */   
/*     */   protected void addAttribute(String qname, String value) {
/* 193 */     int index = this._attributes.getIndex(qname);
/* 194 */     if (index != -1) {
/* 195 */       this._attributes.setAttribute(index, "", Util.getLocalName(qname), qname, "CDATA", value);
/*     */     }
/*     */     else {
/*     */       
/* 199 */       this._attributes.addAttribute("", Util.getLocalName(qname), qname, "CDATA", value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Attributes getAttributes() {
/* 210 */     return this._attributes;
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
/*     */   protected void setPrefixMapping(Map<String, String> mapping) {
/* 222 */     this._prefixMapping = mapping;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Map<String, String> getPrefixMapping() {
/* 233 */     return this._prefixMapping;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addPrefixMapping(String prefix, String uri) {
/* 242 */     if (this._prefixMapping == null)
/* 243 */       this._prefixMapping = new HashMap<>(); 
/* 244 */     this._prefixMapping.put(prefix, uri);
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
/*     */   protected String lookupNamespace(String prefix) {
/* 257 */     String uri = null;
/*     */ 
/*     */     
/* 260 */     if (this._prefixMapping != null) {
/* 261 */       uri = this._prefixMapping.get(prefix);
/*     */     }
/* 263 */     if (uri == null && this._parent != null) {
/* 264 */       uri = this._parent.lookupNamespace(prefix);
/* 265 */       if (prefix == "" && uri == null) {
/* 266 */         uri = "";
/*     */       }
/*     */     } 
/* 269 */     return uri;
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
/*     */   protected String lookupPrefix(String uri) {
/* 284 */     String prefix = null;
/*     */ 
/*     */     
/* 287 */     if (this._prefixMapping != null && this._prefixMapping
/* 288 */       .containsValue(uri)) {
/* 289 */       for (Map.Entry<String, String> entry : this._prefixMapping.entrySet()) {
/* 290 */         prefix = entry.getKey();
/* 291 */         String mapsTo = entry.getValue();
/* 292 */         if (mapsTo.equals(uri)) return prefix;
/*     */       
/*     */       }
/*     */     
/* 296 */     } else if (this._parent != null) {
/* 297 */       prefix = this._parent.lookupPrefix(uri);
/* 298 */       if (uri == "" && prefix == null)
/* 299 */         prefix = ""; 
/*     */     } 
/* 301 */     return prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setParser(Parser parser) {
/* 310 */     this._parser = parser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Parser getParser() {
/* 318 */     return this._parser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setParent(SyntaxTreeNode parent) {
/* 328 */     if (this._parent == null) this._parent = parent;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final SyntaxTreeNode getParent() {
/* 336 */     return this._parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean isDummy() {
/* 344 */     return (this == Dummy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getImportPrecedence() {
/* 353 */     Stylesheet stylesheet = getStylesheet();
/* 354 */     if (stylesheet == null) return Integer.MIN_VALUE; 
/* 355 */     return stylesheet.getImportPrecedence();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stylesheet getStylesheet() {
/* 364 */     if (this._stylesheet == null) {
/* 365 */       SyntaxTreeNode parent = this;
/* 366 */       while (parent != null) {
/* 367 */         if (parent instanceof Stylesheet)
/* 368 */           return (Stylesheet)parent; 
/* 369 */         parent = parent.getParent();
/*     */       } 
/* 371 */       this._stylesheet = (Stylesheet)parent;
/*     */     } 
/* 373 */     return this._stylesheet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Template getTemplate() {
/* 383 */     if (this._template == null) {
/* 384 */       SyntaxTreeNode parent = this;
/* 385 */       while (parent != null && !(parent instanceof Template))
/* 386 */         parent = parent.getParent(); 
/* 387 */       this._template = (Template)parent;
/*     */     } 
/* 389 */     return this._template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final XSLTC getXSLTC() {
/* 397 */     return this._parser.getXSLTC();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final SymbolTable getSymbolTable() {
/* 405 */     return (this._parser == null) ? null : this._parser.getSymbolTable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseContents(Parser parser) {
/* 416 */     parseChildren(parser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void parseChildren(Parser parser) {
/* 426 */     List<QName> locals = null;
/*     */     
/* 428 */     for (SyntaxTreeNode child : this._contents) {
/* 429 */       parser.getSymbolTable().setCurrentNode(child);
/* 430 */       child.parseContents(parser);
/*     */       
/* 432 */       QName varOrParamName = updateScope(parser, child);
/* 433 */       if (varOrParamName != null) {
/* 434 */         if (locals == null) {
/* 435 */           locals = new ArrayList<>(2);
/*     */         }
/* 437 */         locals.add(varOrParamName);
/*     */       } 
/*     */     } 
/*     */     
/* 441 */     parser.getSymbolTable().setCurrentNode(this);
/*     */ 
/*     */     
/* 444 */     if (locals != null) {
/* 445 */       for (QName varOrParamName : locals) {
/* 446 */         parser.removeVariable(varOrParamName);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected QName updateScope(Parser parser, SyntaxTreeNode node) {
/* 456 */     if (node instanceof Variable) {
/* 457 */       Variable var = (Variable)node;
/* 458 */       parser.addVariable(var);
/* 459 */       return var.getName();
/*     */     } 
/* 461 */     if (node instanceof Param) {
/* 462 */       Param param = (Param)node;
/* 463 */       parser.addParameter(param);
/* 464 */       return param.getName();
/*     */     } 
/*     */     
/* 467 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Type typeCheck(SymbolTable paramSymbolTable) throws TypeCheckError;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Type typeCheckContents(SymbolTable stable) throws TypeCheckError {
/* 483 */     for (SyntaxTreeNode item : this._contents) {
/* 484 */       item.typeCheck(stable);
/*     */     }
/* 486 */     return Type.Void;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void translate(ClassGenerator paramClassGenerator, MethodGenerator paramMethodGenerator);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void translateContents(ClassGenerator classGen, MethodGenerator methodGen) {
/* 505 */     int n = elementCount();
/*     */     
/* 507 */     for (SyntaxTreeNode item : this._contents) {
/* 508 */       methodGen.markChunkStart();
/* 509 */       item.translate(classGen, methodGen);
/* 510 */       methodGen.markChunkEnd();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 518 */     for (int i = 0; i < n; i++) {
/* 519 */       if (this._contents.get(i) instanceof VariableBase) {
/* 520 */         VariableBase var = (VariableBase)this._contents.get(i);
/* 521 */         var.unmapRegister(classGen, methodGen);
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
/*     */   private boolean isSimpleRTF(SyntaxTreeNode node) {
/* 536 */     List<SyntaxTreeNode> contents = node.getContents();
/* 537 */     for (SyntaxTreeNode item : contents) {
/* 538 */       if (!isTextElement(item, false)) {
/* 539 */         return false;
/*     */       }
/*     */     } 
/* 542 */     return true;
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
/*     */   private boolean isAdaptiveRTF(SyntaxTreeNode node) {
/* 556 */     List<SyntaxTreeNode> contents = node.getContents();
/* 557 */     for (SyntaxTreeNode item : contents) {
/* 558 */       if (!isTextElement(item, true)) {
/* 559 */         return false;
/*     */       }
/*     */     } 
/* 562 */     return true;
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
/*     */   
/*     */   private boolean isTextElement(SyntaxTreeNode node, boolean doExtendedCheck) {
/* 582 */     if (node instanceof ValueOf || node instanceof Number || node instanceof Text)
/*     */     {
/*     */       
/* 585 */       return true;
/*     */     }
/* 587 */     if (node instanceof If) {
/* 588 */       return doExtendedCheck ? isAdaptiveRTF(node) : isSimpleRTF(node);
/*     */     }
/* 590 */     if (node instanceof Choose) {
/* 591 */       List<SyntaxTreeNode> contents = node.getContents();
/* 592 */       for (SyntaxTreeNode item : contents) {
/* 593 */         if (item instanceof Text || ((item instanceof When || item instanceof Otherwise) && ((doExtendedCheck && 
/*     */           
/* 595 */           isAdaptiveRTF(item)) || (!doExtendedCheck && 
/* 596 */           isSimpleRTF(item))))) {
/*     */           continue;
/*     */         }
/* 599 */         return false;
/*     */       } 
/* 601 */       return true;
/*     */     } 
/* 603 */     if (doExtendedCheck && (node instanceof CallTemplate || node instanceof ApplyTemplates))
/*     */     {
/*     */       
/* 606 */       return true;
/*     */     }
/* 608 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void compileResultTree(ClassGenerator classGen, MethodGenerator methodGen) {
/* 619 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 620 */     InstructionList il = methodGen.getInstructionList();
/* 621 */     Stylesheet stylesheet = classGen.getStylesheet();
/*     */     
/* 623 */     boolean isSimple = isSimpleRTF(this);
/* 624 */     boolean isAdaptive = false;
/* 625 */     if (!isSimple) {
/* 626 */       isAdaptive = isAdaptiveRTF(this);
/*     */     }
/*     */     
/* 629 */     int rtfType = isSimple ? 0 : (isAdaptive ? 1 : 2);
/*     */ 
/*     */ 
/*     */     
/* 633 */     il.append(methodGen.loadHandler());
/*     */     
/* 635 */     String DOM_CLASS = classGen.getDOMClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 641 */     il.append(methodGen.loadDOM());
/* 642 */     int index = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getResultTreeFrag", "(IIZ)Lcom/sun/org/apache/xalan/internal/xsltc/DOM;");
/*     */ 
/*     */     
/* 645 */     il.append(new PUSH(cpg, 32));
/* 646 */     il.append(new PUSH(cpg, rtfType));
/* 647 */     il.append(new PUSH(cpg, stylesheet.callsNodeset()));
/* 648 */     il.append(new INVOKEINTERFACE(index, 4));
/*     */     
/* 650 */     il.append(DUP);
/*     */ 
/*     */     
/* 653 */     index = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getOutputDomBuilder", "()Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;");
/*     */ 
/*     */ 
/*     */     
/* 657 */     il.append(new INVOKEINTERFACE(index, 1));
/* 658 */     il.append(DUP);
/* 659 */     il.append(methodGen.storeHandler());
/*     */ 
/*     */     
/* 662 */     il.append(methodGen.startDocument());
/*     */ 
/*     */     
/* 665 */     translateContents(classGen, methodGen);
/*     */ 
/*     */     
/* 668 */     il.append(methodGen.loadHandler());
/* 669 */     il.append(methodGen.endDocument());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 674 */     if (stylesheet.callsNodeset() && 
/* 675 */       !DOM_CLASS.equals("com/sun/org/apache/xalan/internal/xsltc/DOM")) {
/*     */       
/* 677 */       index = cpg.addMethodref("com/sun/org/apache/xalan/internal/xsltc/dom/DOMAdapter", "<init>", "(Lcom/sun/org/apache/xalan/internal/xsltc/DOM;[Ljava/lang/String;[Ljava/lang/String;[I[Ljava/lang/String;)V");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 684 */       il.append(new NEW(cpg.addClass("com/sun/org/apache/xalan/internal/xsltc/dom/DOMAdapter")));
/* 685 */       il.append(new DUP_X1());
/* 686 */       il.append(SWAP);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 692 */       if (!stylesheet.callsNodeset()) {
/* 693 */         il.append(new ICONST(0));
/* 694 */         il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 695 */         il.append(DUP);
/* 696 */         il.append(DUP);
/* 697 */         il.append(new ICONST(0));
/* 698 */         il.append(new NEWARRAY(BasicType.INT));
/* 699 */         il.append(SWAP);
/* 700 */         il.append(new INVOKESPECIAL(index));
/*     */       }
/*     */       else {
/*     */         
/* 704 */         il.append(ALOAD_0);
/* 705 */         il.append(new GETFIELD(cpg.addFieldref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "namesArray", "[Ljava/lang/String;")));
/*     */ 
/*     */         
/* 708 */         il.append(ALOAD_0);
/* 709 */         il.append(new GETFIELD(cpg.addFieldref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "urisArray", "[Ljava/lang/String;")));
/*     */ 
/*     */         
/* 712 */         il.append(ALOAD_0);
/* 713 */         il.append(new GETFIELD(cpg.addFieldref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "typesArray", "[I")));
/*     */ 
/*     */         
/* 716 */         il.append(ALOAD_0);
/* 717 */         il.append(new GETFIELD(cpg.addFieldref("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet", "namespaceArray", "[Ljava/lang/String;")));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 722 */         il.append(new INVOKESPECIAL(index));
/*     */ 
/*     */         
/* 725 */         il.append(DUP);
/* 726 */         il.append(methodGen.loadDOM());
/* 727 */         il.append(new CHECKCAST(cpg.addClass(classGen.getDOMClass())));
/* 728 */         il.append(SWAP);
/* 729 */         index = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.dom.MultiDOM", "addDOMAdapter", "(Lcom/sun/org/apache/xalan/internal/xsltc/dom/DOMAdapter;)I");
/*     */ 
/*     */         
/* 732 */         il.append(new INVOKEVIRTUAL(index));
/* 733 */         il.append(POP);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 738 */     il.append(SWAP);
/* 739 */     il.append(methodGen.storeHandler());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contextDependent() {
/* 750 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean dependentContents() {
/* 759 */     for (SyntaxTreeNode item : this._contents) {
/* 760 */       if (item.contextDependent()) {
/* 761 */         return true;
/*     */       }
/*     */     } 
/* 764 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void addElement(SyntaxTreeNode element) {
/* 772 */     this._contents.add(element);
/* 773 */     element.setParent(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setFirstElement(SyntaxTreeNode element) {
/* 782 */     this._contents.add(0, element);
/* 783 */     element.setParent(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void removeElement(SyntaxTreeNode element) {
/* 791 */     this._contents.remove(element);
/* 792 */     element.setParent((SyntaxTreeNode)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final List<SyntaxTreeNode> getContents() {
/* 800 */     return this._contents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean hasContents() {
/* 808 */     return (elementCount() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int elementCount() {
/* 816 */     return this._contents.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Iterator<SyntaxTreeNode> elements() {
/* 824 */     return this._contents.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final SyntaxTreeNode elementAt(int pos) {
/* 833 */     return this._contents.get(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final SyntaxTreeNode lastChild() {
/* 841 */     if (this._contents.isEmpty()) return null; 
/* 842 */     return this._contents.get(this._contents.size() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void display(int indent) {
/* 852 */     displayContents(indent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void displayContents(int indent) {
/* 861 */     for (SyntaxTreeNode item : this._contents) {
/* 862 */       item.display(indent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void indent(int indent) {
/* 871 */     System.out.print(new String(_spaces, 0, indent));
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
/*     */   protected void reportError(SyntaxTreeNode element, Parser parser, String errorCode, String message) {
/* 884 */     ErrorMsg error = new ErrorMsg(errorCode, message, element);
/* 885 */     parser.reportError(3, error);
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
/*     */   protected void reportWarning(SyntaxTreeNode element, Parser parser, String errorCode, String message) {
/* 898 */     ErrorMsg error = new ErrorMsg(errorCode, message, element);
/* 899 */     parser.reportError(4, error);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/SyntaxTreeNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */