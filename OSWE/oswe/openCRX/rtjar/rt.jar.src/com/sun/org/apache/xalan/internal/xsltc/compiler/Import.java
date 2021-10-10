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
/*     */ final class Import
/*     */   extends TopLevelElement
/*     */ {
/*  47 */   private Stylesheet _imported = null;
/*     */   
/*     */   public Stylesheet getImportedStylesheet() {
/*  50 */     return this._imported;
/*     */   }
/*     */   
/*     */   public void parseContents(Parser parser) {
/*  54 */     XSLTC xsltc = parser.getXSLTC();
/*  55 */     Stylesheet context = parser.getCurrentStylesheet();
/*     */     try {
/*     */       SyntaxTreeNode root;
/*  58 */       String docToLoad = getAttribute("href");
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
/* 116 */         return;  this._imported = parser.makeStylesheet(root);
/* 117 */       if (this._imported == null)
/*     */         return; 
/* 119 */       this._imported.setSourceLoader(loader);
/* 120 */       this._imported.setSystemId(docToLoad);
/* 121 */       this._imported.setParentStylesheet(context);
/* 122 */       this._imported.setImportingStylesheet(context);
/* 123 */       this._imported.setTemplateInlining(context.getTemplateInlining());
/*     */ 
/*     */       
/* 126 */       int currPrecedence = parser.getCurrentImportPrecedence();
/* 127 */       int nextPrecedence = parser.getNextImportPrecedence();
/* 128 */       this._imported.setImportPrecedence(currPrecedence);
/* 129 */       context.setImportPrecedence(nextPrecedence);
/* 130 */       parser.setCurrentStylesheet(this._imported);
/* 131 */       this._imported.parseContents(parser);
/*     */       
/* 133 */       Iterator<SyntaxTreeNode> elements = this._imported.elements();
/* 134 */       Stylesheet topStylesheet = parser.getTopLevelStylesheet();
/* 135 */       while (elements.hasNext()) {
/* 136 */         SyntaxTreeNode element = elements.next();
/* 137 */         if (element instanceof TopLevelElement) {
/* 138 */           if (element instanceof Variable) {
/* 139 */             topStylesheet.addVariable((Variable)element); continue;
/*     */           } 
/* 141 */           if (element instanceof Param) {
/* 142 */             topStylesheet.addParam((Param)element);
/*     */             continue;
/*     */           } 
/* 145 */           topStylesheet.addElement(element);
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 150 */     } catch (Exception e) {
/* 151 */       e.printStackTrace();
/*     */     } finally {
/*     */       
/* 154 */       parser.setCurrentStylesheet(context);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 159 */     return Type.Void;
/*     */   }
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/Import.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */