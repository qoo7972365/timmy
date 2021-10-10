/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xml.internal.utils.SystemIDResolver;
/*     */ import java.util.Iterator;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.XMLReader;
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
/*     */ final class Include
/*     */   extends TopLevelElement
/*     */ {
/*  47 */   private Stylesheet _included = null;
/*     */   
/*     */   public Stylesheet getIncludedStylesheet() {
/*  50 */     return this._included;
/*     */   }
/*     */   
/*     */   public void parseContents(Parser parser) {
/*  54 */     XSLTC xsltc = parser.getXSLTC();
/*  55 */     Stylesheet context = parser.getCurrentStylesheet();
/*     */     
/*  57 */     String docToLoad = getAttribute("href"); try {
/*     */       SyntaxTreeNode root;
/*  59 */       if (context.checkForLoop(docToLoad)) {
/*  60 */         ErrorMsg msg = new ErrorMsg("CIRCULAR_INCLUDE_ERR", docToLoad, this);
/*     */         
/*  62 */         parser.reportError(2, msg);
/*     */         
/*     */         return;
/*     */       } 
/*  66 */       InputSource input = null;
/*  67 */       XMLReader reader = null;
/*  68 */       String currLoadedDoc = context.getSystemId();
/*  69 */       SourceLoader loader = context.getSourceLoader();
/*     */ 
/*     */       
/*  72 */       if (loader != null) {
/*  73 */         input = loader.loadSource(docToLoad, currLoadedDoc, xsltc);
/*  74 */         if (input != null) {
/*  75 */           docToLoad = input.getSystemId();
/*  76 */           reader = xsltc.getXMLReader();
/*  77 */         } else if (parser.errorsFound()) {
/*     */           return;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  83 */       if (input == null) {
/*  84 */         docToLoad = SystemIDResolver.getAbsoluteURI(docToLoad, currLoadedDoc);
/*  85 */         String accessError = SecuritySupport.checkAccess(docToLoad, (String)xsltc
/*  86 */             .getProperty("http://javax.xml.XMLConstants/property/accessExternalStylesheet"), "all");
/*     */ 
/*     */         
/*  89 */         if (accessError != null) {
/*     */           
/*  91 */           ErrorMsg msg = new ErrorMsg("ACCESSING_XSLT_TARGET_ERR", SecuritySupport.sanitizePath(docToLoad), accessError, this);
/*     */           
/*  93 */           parser.reportError(2, msg);
/*     */           return;
/*     */         } 
/*  96 */         input = new InputSource(docToLoad);
/*     */       } 
/*     */ 
/*     */       
/* 100 */       if (input == null) {
/* 101 */         ErrorMsg msg = new ErrorMsg("FILE_NOT_FOUND_ERR", docToLoad, this);
/*     */         
/* 103 */         parser.reportError(2, msg);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 108 */       if (reader != null) {
/* 109 */         root = parser.parse(reader, input);
/*     */       } else {
/*     */         
/* 112 */         root = parser.parse(input);
/*     */       } 
/*     */       
/* 115 */       if (root == null)
/* 116 */         return;  this._included = parser.makeStylesheet(root);
/* 117 */       if (this._included == null)
/*     */         return; 
/* 119 */       this._included.setSourceLoader(loader);
/* 120 */       this._included.setSystemId(docToLoad);
/* 121 */       this._included.setParentStylesheet(context);
/* 122 */       this._included.setIncludingStylesheet(context);
/* 123 */       this._included.setTemplateInlining(context.getTemplateInlining());
/*     */ 
/*     */ 
/*     */       
/* 127 */       int precedence = context.getImportPrecedence();
/* 128 */       this._included.setImportPrecedence(precedence);
/* 129 */       parser.setCurrentStylesheet(this._included);
/* 130 */       this._included.parseContents(parser);
/*     */       
/* 132 */       Iterator<SyntaxTreeNode> elements = this._included.elements();
/* 133 */       Stylesheet topStylesheet = parser.getTopLevelStylesheet();
/* 134 */       while (elements.hasNext()) {
/* 135 */         SyntaxTreeNode element = elements.next();
/* 136 */         if (element instanceof TopLevelElement) {
/* 137 */           if (element instanceof Variable) {
/* 138 */             topStylesheet.addVariable((Variable)element); continue;
/*     */           } 
/* 140 */           if (element instanceof Param) {
/* 141 */             topStylesheet.addParam((Param)element);
/*     */             continue;
/*     */           } 
/* 144 */           topStylesheet.addElement(element);
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 149 */     } catch (Exception e) {
/* 150 */       e.printStackTrace();
/*     */     } finally {
/*     */       
/* 153 */       parser.setCurrentStylesheet(context);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 158 */     return Type.Void;
/*     */   }
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/Include.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */