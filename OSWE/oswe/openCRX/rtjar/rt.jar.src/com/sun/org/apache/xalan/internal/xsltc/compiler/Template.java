/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NamedMethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*     */ import com.sun.org.apache.xml.internal.utils.XML11Char;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
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
/*     */ public final class Template
/*     */   extends TopLevelElement
/*     */ {
/*     */   private QName _name;
/*     */   private QName _mode;
/*     */   private Pattern _pattern;
/*     */   private double _priority;
/*     */   private int _position;
/*     */   private boolean _disabled = false;
/*     */   private boolean _compiled = false;
/*     */   private boolean _simplified = false;
/*     */   private boolean _isSimpleNamedTemplate = false;
/*  61 */   private Vector<Param> _parameters = new Vector<>();
/*     */   
/*     */   public boolean hasParams() {
/*  64 */     return (this._parameters.size() > 0);
/*     */   }
/*     */   
/*     */   public boolean isSimplified() {
/*  68 */     return this._simplified;
/*     */   }
/*     */   
/*     */   public void setSimplified() {
/*  72 */     this._simplified = true;
/*     */   }
/*     */   
/*     */   public boolean isSimpleNamedTemplate() {
/*  76 */     return this._isSimpleNamedTemplate;
/*     */   }
/*     */   
/*     */   public void addParameter(Param param) {
/*  80 */     this._parameters.addElement(param);
/*     */   }
/*     */   
/*     */   public Vector<Param> getParameters() {
/*  84 */     return this._parameters;
/*     */   }
/*     */   
/*     */   public void disable() {
/*  88 */     this._disabled = true;
/*     */   }
/*     */   
/*     */   public boolean disabled() {
/*  92 */     return this._disabled;
/*     */   }
/*     */   
/*     */   public double getPriority() {
/*  96 */     return this._priority;
/*     */   }
/*     */   
/*     */   public int getPosition() {
/* 100 */     return this._position;
/*     */   }
/*     */   
/*     */   public boolean isNamed() {
/* 104 */     return (this._name != null);
/*     */   }
/*     */   
/*     */   public Pattern getPattern() {
/* 108 */     return this._pattern;
/*     */   }
/*     */   
/*     */   public QName getName() {
/* 112 */     return this._name;
/*     */   }
/*     */   
/*     */   public void setName(QName qname) {
/* 116 */     if (this._name == null) this._name = qname; 
/*     */   }
/*     */   
/*     */   public QName getModeName() {
/* 120 */     return this._mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object template) {
/* 127 */     Template other = (Template)template;
/* 128 */     if (this._priority > other._priority)
/* 129 */       return 1; 
/* 130 */     if (this._priority < other._priority)
/* 131 */       return -1; 
/* 132 */     if (this._position > other._position)
/* 133 */       return 1; 
/* 134 */     if (this._position < other._position) {
/* 135 */       return -1;
/*     */     }
/* 137 */     return 0;
/*     */   }
/*     */   
/*     */   public void display(int indent) {
/* 141 */     Util.println('\n');
/* 142 */     indent(indent);
/* 143 */     if (this._name != null) {
/* 144 */       indent(indent);
/* 145 */       Util.println("name = " + this._name);
/*     */     }
/* 147 */     else if (this._pattern != null) {
/* 148 */       indent(indent);
/* 149 */       Util.println("match = " + this._pattern.toString());
/*     */     } 
/* 151 */     if (this._mode != null) {
/* 152 */       indent(indent);
/* 153 */       Util.println("mode = " + this._mode);
/*     */     } 
/* 155 */     displayContents(indent + 4);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean resolveNamedTemplates(Template other, Parser parser) {
/* 160 */     if (other == null) return true;
/*     */     
/* 162 */     SymbolTable stable = parser.getSymbolTable();
/*     */     
/* 164 */     int us = getImportPrecedence();
/* 165 */     int them = other.getImportPrecedence();
/*     */     
/* 167 */     if (us > them) {
/* 168 */       other.disable();
/* 169 */       return true;
/*     */     } 
/* 171 */     if (us < them) {
/* 172 */       stable.addTemplate(other);
/* 173 */       disable();
/* 174 */       return true;
/*     */     } 
/*     */     
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */   
/* 181 */   private Stylesheet _stylesheet = null;
/*     */   
/*     */   public Stylesheet getStylesheet() {
/* 184 */     return this._stylesheet;
/*     */   }
/*     */ 
/*     */   
/*     */   public void parseContents(Parser parser) {
/* 189 */     String name = getAttribute("name");
/* 190 */     String mode = getAttribute("mode");
/* 191 */     String match = getAttribute("match");
/* 192 */     String priority = getAttribute("priority");
/*     */     
/* 194 */     this._stylesheet = super.getStylesheet();
/*     */     
/* 196 */     if (name.length() > 0) {
/* 197 */       if (!XML11Char.isXML11ValidQName(name)) {
/* 198 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
/* 199 */         parser.reportError(3, err);
/*     */       } 
/* 201 */       this._name = parser.getQNameIgnoreDefaultNs(name);
/*     */     } 
/*     */     
/* 204 */     if (mode.length() > 0) {
/* 205 */       if (!XML11Char.isXML11ValidQName(mode)) {
/* 206 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", mode, this);
/* 207 */         parser.reportError(3, err);
/*     */       } 
/* 209 */       this._mode = parser.getQNameIgnoreDefaultNs(mode);
/*     */     } 
/*     */     
/* 212 */     if (match.length() > 0) {
/* 213 */       this._pattern = parser.parsePattern(this, "match", null);
/*     */     }
/*     */     
/* 216 */     if (priority.length() > 0) {
/* 217 */       this._priority = Double.parseDouble(priority);
/*     */     
/*     */     }
/* 220 */     else if (this._pattern != null) {
/* 221 */       this._priority = this._pattern.getPriority();
/*     */     } else {
/* 223 */       this._priority = Double.NaN;
/*     */     } 
/*     */     
/* 226 */     this._position = parser.getTemplateIndex();
/*     */ 
/*     */     
/* 229 */     if (this._name != null) {
/* 230 */       Template other = parser.getSymbolTable().addTemplate(this);
/* 231 */       if (!resolveNamedTemplates(other, parser)) {
/* 232 */         ErrorMsg err = new ErrorMsg("TEMPLATE_REDEF_ERR", this._name, this);
/*     */         
/* 234 */         parser.reportError(3, err);
/*     */       } 
/*     */       
/* 237 */       if (this._pattern == null && this._mode == null) {
/* 238 */         this._isSimpleNamedTemplate = true;
/*     */       }
/*     */     } 
/*     */     
/* 242 */     if (this._parent instanceof Stylesheet) {
/* 243 */       ((Stylesheet)this._parent).addTemplate(this);
/*     */     }
/*     */     
/* 246 */     parser.setTemplate(this);
/* 247 */     parseChildren(parser);
/* 248 */     parser.setTemplate(null);
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
/*     */   public void parseSimplified(Stylesheet stylesheet, Parser parser) {
/* 265 */     this._stylesheet = stylesheet;
/* 266 */     setParent(stylesheet);
/*     */     
/* 268 */     this._name = null;
/* 269 */     this._mode = null;
/* 270 */     this._priority = Double.NaN;
/* 271 */     this._pattern = parser.parsePattern(this, "/");
/*     */     
/* 273 */     List<SyntaxTreeNode> contents = this._stylesheet.getContents();
/* 274 */     SyntaxTreeNode root = contents.get(0);
/*     */     
/* 276 */     if (root instanceof LiteralElement) {
/* 277 */       addElement(root);
/* 278 */       root.setParent(this);
/* 279 */       contents.set(0, this);
/* 280 */       parser.setTemplate(this);
/* 281 */       root.parseContents(parser);
/* 282 */       parser.setTemplate(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 287 */     if (this._pattern != null) {
/* 288 */       this._pattern.typeCheck(stable);
/*     */     }
/*     */     
/* 291 */     return typeCheckContents(stable);
/*     */   }
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 295 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 296 */     InstructionList il = methodGen.getInstructionList();
/*     */     
/* 298 */     if (this._disabled)
/*     */       return; 
/* 300 */     String className = classGen.getClassName();
/*     */     
/* 302 */     if (this._compiled && isNamed()) {
/* 303 */       String methodName = Util.escape(this._name.toString());
/* 304 */       il.append(classGen.loadTranslet());
/* 305 */       il.append(methodGen.loadDOM());
/* 306 */       il.append(methodGen.loadIterator());
/* 307 */       il.append(methodGen.loadHandler());
/* 308 */       il.append(methodGen.loadCurrentNode());
/* 309 */       il.append(new INVOKEVIRTUAL(cpg.addMethodref(className, methodName, "(Lcom/sun/org/apache/xalan/internal/xsltc/DOM;Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;I)V")));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 319 */     if (this._compiled)
/* 320 */       return;  this._compiled = true;
/*     */ 
/*     */     
/* 323 */     if (this._isSimpleNamedTemplate && methodGen instanceof NamedMethodGenerator) {
/* 324 */       int numParams = this._parameters.size();
/* 325 */       NamedMethodGenerator namedMethodGen = (NamedMethodGenerator)methodGen;
/*     */ 
/*     */       
/* 328 */       for (int i = 0; i < numParams; i++) {
/* 329 */         Param param = this._parameters.elementAt(i);
/* 330 */         param.setLoadInstruction(namedMethodGen.loadParameter(i));
/* 331 */         param.setStoreInstruction(namedMethodGen.storeParameter(i));
/*     */       } 
/*     */     } 
/*     */     
/* 335 */     translateContents(classGen, methodGen);
/* 336 */     il.setPositions(true);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/Template.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */