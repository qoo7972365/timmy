/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.Instruction;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.bcel.internal.generic.NEW;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.StringTokenizer;
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
/*     */ final class AttributeValueTemplate
/*     */   extends AttributeValue
/*     */ {
/*     */   static final int OUT_EXPR = 0;
/*     */   static final int IN_EXPR = 1;
/*     */   static final int IN_EXPR_SQUOTES = 2;
/*     */   static final int IN_EXPR_DQUOTES = 3;
/*     */   static final String DELIMITER = "￾";
/*     */   
/*     */   public AttributeValueTemplate(String value, Parser parser, SyntaxTreeNode parent) {
/*  57 */     setParent(parent);
/*  58 */     setParser(parser);
/*     */     
/*     */     try {
/*  61 */       parseAVTemplate(value, parser);
/*     */     }
/*  63 */     catch (NoSuchElementException e) {
/*  64 */       reportError(parent, parser, "ATTR_VAL_TEMPLATE_ERR", value);
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
/*     */   private void parseAVTemplate(String text, Parser parser) {
/*  76 */     StringTokenizer tokenizer = new StringTokenizer(text, "{}\"'", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     String t = null;
/*  85 */     String lookahead = null;
/*  86 */     StringBuffer buffer = new StringBuffer();
/*  87 */     int state = 0;
/*     */     
/*  89 */     while (tokenizer.hasMoreTokens()) {
/*     */       
/*  91 */       if (lookahead != null) {
/*  92 */         t = lookahead;
/*  93 */         lookahead = null;
/*     */       } else {
/*     */         
/*  96 */         t = tokenizer.nextToken();
/*     */       } 
/*     */       
/*  99 */       if (t.length() == 1) {
/* 100 */         switch (t.charAt(0)) {
/*     */           case '{':
/* 102 */             switch (state) {
/*     */               case 0:
/* 104 */                 lookahead = tokenizer.nextToken();
/* 105 */                 if (lookahead.equals("{")) {
/* 106 */                   buffer.append(lookahead);
/* 107 */                   lookahead = null;
/*     */                   continue;
/*     */                 } 
/* 110 */                 buffer.append("￾");
/* 111 */                 state = 1;
/*     */                 continue;
/*     */               
/*     */               case 1:
/*     */               case 2:
/*     */               case 3:
/* 117 */                 reportError(getParent(), parser, "ATTR_VAL_TEMPLATE_ERR", text);
/*     */                 continue;
/*     */             } 
/*     */             
/*     */             continue;
/*     */           case '}':
/* 123 */             switch (state) {
/*     */               case 0:
/* 125 */                 lookahead = tokenizer.nextToken();
/* 126 */                 if (lookahead.equals("}")) {
/* 127 */                   buffer.append(lookahead);
/* 128 */                   lookahead = null;
/*     */                   continue;
/*     */                 } 
/* 131 */                 reportError(getParent(), parser, "ATTR_VAL_TEMPLATE_ERR", text);
/*     */                 continue;
/*     */ 
/*     */               
/*     */               case 1:
/* 136 */                 buffer.append("￾");
/* 137 */                 state = 0;
/*     */                 continue;
/*     */               case 2:
/*     */               case 3:
/* 141 */                 buffer.append(t);
/*     */                 continue;
/*     */             } 
/*     */             continue;
/*     */           case '\'':
/* 146 */             switch (state) {
/*     */               case 1:
/* 148 */                 state = 2;
/*     */                 break;
/*     */               case 2:
/* 151 */                 state = 1;
/*     */                 break;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 157 */             buffer.append(t);
/*     */             continue;
/*     */           case '"':
/* 160 */             switch (state) {
/*     */               case 1:
/* 162 */                 state = 3;
/*     */                 break;
/*     */               case 3:
/* 165 */                 state = 1;
/*     */                 break;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 171 */             buffer.append(t);
/*     */             continue;
/*     */         } 
/* 174 */         buffer.append(t);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 179 */       buffer.append(t);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 184 */     if (state != 0) {
/* 185 */       reportError(getParent(), parser, "ATTR_VAL_TEMPLATE_ERR", text);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     tokenizer = new StringTokenizer(buffer.toString(), "￾", true);
/*     */     
/* 194 */     while (tokenizer.hasMoreTokens()) {
/* 195 */       t = tokenizer.nextToken();
/*     */       
/* 197 */       if (t.equals("￾")) {
/* 198 */         addElement(parser.parseExpression(this, tokenizer.nextToken()));
/* 199 */         tokenizer.nextToken();
/*     */         continue;
/*     */       } 
/* 202 */       addElement(new LiteralExpr(t));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 208 */     List<SyntaxTreeNode> contents = getContents();
/* 209 */     int n = contents.size();
/* 210 */     for (int i = 0; i < n; i++) {
/* 211 */       Expression exp = (Expression)contents.get(i);
/* 212 */       if (!exp.typeCheck(stable).identicalTo(Type.String)) {
/* 213 */         contents.set(i, new CastExpr(exp, Type.String));
/*     */       }
/*     */     } 
/* 216 */     return this._type = Type.String;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 220 */     StringBuffer buffer = new StringBuffer("AVT:[");
/* 221 */     int count = elementCount();
/* 222 */     for (int i = 0; i < count; i++) {
/* 223 */       buffer.append(elementAt(i).toString());
/* 224 */       if (i < count - 1)
/* 225 */         buffer.append(' '); 
/*     */     } 
/* 227 */     return buffer.append(']').toString();
/*     */   }
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 231 */     if (elementCount() == 1) {
/* 232 */       Expression exp = (Expression)elementAt(0);
/* 233 */       exp.translate(classGen, methodGen);
/*     */     } else {
/*     */       
/* 236 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 237 */       InstructionList il = methodGen.getInstructionList();
/* 238 */       int initBuffer = cpg.addMethodref("java.lang.StringBuffer", "<init>", "()V");
/*     */ 
/*     */       
/* 241 */       Instruction append = new INVOKEVIRTUAL(cpg.addMethodref("java.lang.StringBuffer", "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 246 */       int toString = cpg.addMethodref("java.lang.StringBuffer", "toString", "()Ljava/lang/String;");
/*     */ 
/*     */       
/* 249 */       il.append(new NEW(cpg.addClass("java.lang.StringBuffer")));
/* 250 */       il.append(DUP);
/* 251 */       il.append(new INVOKESPECIAL(initBuffer));
/*     */       
/* 253 */       Iterator<SyntaxTreeNode> elements = elements();
/* 254 */       while (elements.hasNext()) {
/* 255 */         Expression exp = (Expression)elements.next();
/* 256 */         exp.translate(classGen, methodGen);
/* 257 */         il.append(append);
/*     */       } 
/* 259 */       il.append(new INVOKEVIRTUAL(toString));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/AttributeValueTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */