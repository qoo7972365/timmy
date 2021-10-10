/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.GOTO_W;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import java.util.Map;
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
/*     */ final class TestSeq
/*     */ {
/*     */   private int _kernelType;
/*  61 */   private Vector _patterns = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private Mode _mode = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private Template _default = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InstructionList _instructionList;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private InstructionHandle _start = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TestSeq(Vector patterns, Mode mode) {
/*  87 */     this(patterns, -2, mode);
/*     */   }
/*     */   
/*     */   public TestSeq(Vector patterns, int kernelType, Mode mode) {
/*  91 */     this._patterns = patterns;
/*  92 */     this._kernelType = kernelType;
/*  93 */     this._mode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 102 */     int count = this._patterns.size();
/* 103 */     StringBuffer result = new StringBuffer();
/*     */     
/* 105 */     for (int i = 0; i < count; i++) {
/*     */       
/* 107 */       LocationPathPattern pattern = this._patterns.elementAt(i);
/*     */       
/* 109 */       if (i == 0) {
/* 110 */         result.append("Testseq for kernel ").append(this._kernelType)
/* 111 */           .append('\n');
/*     */       }
/* 113 */       result.append("   pattern ").append(i).append(": ")
/* 114 */         .append(pattern.toString())
/* 115 */         .append('\n');
/*     */     } 
/* 117 */     return result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InstructionList getInstructionList() {
/* 124 */     return this._instructionList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPriority() {
/* 134 */     Template template = (this._patterns.size() == 0) ? this._default : ((Pattern)this._patterns.elementAt(0)).getTemplate();
/* 135 */     return template.getPriority();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPosition() {
/* 144 */     Template template = (this._patterns.size() == 0) ? this._default : ((Pattern)this._patterns.elementAt(0)).getTemplate();
/* 145 */     return template.getPosition();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reduce() {
/* 154 */     Vector<LocationPathPattern> newPatterns = new Vector();
/*     */     
/* 156 */     int count = this._patterns.size();
/* 157 */     for (int i = 0; i < count; i++) {
/*     */       
/* 159 */       LocationPathPattern pattern = this._patterns.elementAt(i);
/*     */ 
/*     */       
/* 162 */       pattern.reduceKernelPattern();
/*     */ 
/*     */       
/* 165 */       if (pattern.isWildcard()) {
/* 166 */         this._default = pattern.getTemplate();
/*     */         
/*     */         break;
/*     */       } 
/* 170 */       newPatterns.addElement(pattern);
/*     */     } 
/*     */     
/* 173 */     this._patterns = newPatterns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void findTemplates(Map<Template, Object> templates) {
/* 182 */     if (this._default != null) {
/* 183 */       templates.put(this._default, this);
/*     */     }
/* 185 */     for (int i = 0; i < this._patterns.size(); i++) {
/*     */       
/* 187 */       LocationPathPattern pattern = this._patterns.elementAt(i);
/* 188 */       templates.put(pattern.getTemplate(), this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InstructionHandle getTemplateHandle(Template template) {
/* 199 */     return this._mode.getTemplateInstructionHandle(template);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LocationPathPattern getPattern(int n) {
/* 206 */     return this._patterns.elementAt(n);
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
/*     */   public InstructionHandle compile(ClassGenerator classGen, MethodGenerator methodGen, InstructionHandle continuation) {
/* 220 */     if (this._start != null) {
/* 221 */       return this._start;
/*     */     }
/*     */ 
/*     */     
/* 225 */     int count = this._patterns.size();
/* 226 */     if (count == 0) {
/* 227 */       return this._start = getTemplateHandle(this._default);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 232 */     InstructionHandle fail = (this._default == null) ? continuation : getTemplateHandle(this._default);
/*     */ 
/*     */     
/* 235 */     for (int n = count - 1; n >= 0; n--) {
/* 236 */       LocationPathPattern pattern = getPattern(n);
/* 237 */       Template template = pattern.getTemplate();
/* 238 */       InstructionList il = new InstructionList();
/*     */ 
/*     */       
/* 241 */       il.append(methodGen.loadCurrentNode());
/*     */ 
/*     */       
/* 244 */       InstructionList ilist = methodGen.getInstructionList(pattern);
/* 245 */       if (ilist == null) {
/* 246 */         ilist = pattern.compile(classGen, methodGen);
/* 247 */         methodGen.addInstructionList(pattern, ilist);
/*     */       } 
/*     */ 
/*     */       
/* 251 */       InstructionList copyOfilist = ilist.copy();
/*     */       
/* 253 */       FlowList trueList = pattern.getTrueList();
/* 254 */       if (trueList != null) {
/* 255 */         trueList = trueList.copyAndRedirect(ilist, copyOfilist);
/*     */       }
/* 257 */       FlowList falseList = pattern.getFalseList();
/* 258 */       if (falseList != null) {
/* 259 */         falseList = falseList.copyAndRedirect(ilist, copyOfilist);
/*     */       }
/*     */       
/* 262 */       il.append(copyOfilist);
/*     */ 
/*     */       
/* 265 */       InstructionHandle gtmpl = getTemplateHandle(template);
/* 266 */       InstructionHandle success = il.append(new GOTO_W(gtmpl));
/*     */       
/* 268 */       if (trueList != null) {
/* 269 */         trueList.backPatch(success);
/*     */       }
/* 271 */       if (falseList != null) {
/* 272 */         falseList.backPatch(fail);
/*     */       }
/*     */ 
/*     */       
/* 276 */       fail = il.getStart();
/*     */ 
/*     */       
/* 279 */       if (this._instructionList != null) {
/* 280 */         il.append(this._instructionList);
/*     */       }
/*     */ 
/*     */       
/* 284 */       this._instructionList = il;
/*     */     } 
/* 286 */     return this._start = fail;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/TestSeq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */