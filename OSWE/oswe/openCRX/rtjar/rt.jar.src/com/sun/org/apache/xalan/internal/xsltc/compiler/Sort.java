/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.classfile.Field;
/*     */ import com.sun.org.apache.bcel.internal.generic.ALOAD;
/*     */ import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
/*     */ import com.sun.org.apache.bcel.internal.generic.ASTORE;
/*     */ import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.GETFIELD;
/*     */ import com.sun.org.apache.bcel.internal.generic.ILOAD;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*     */ import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.NEW;
/*     */ import com.sun.org.apache.bcel.internal.generic.NOP;
/*     */ import com.sun.org.apache.bcel.internal.generic.PUSH;
/*     */ import com.sun.org.apache.bcel.internal.generic.PUTFIELD;
/*     */ import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;
/*     */ import com.sun.org.apache.bcel.internal.generic.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.CompareGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSortRecordFactGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSortRecordGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*     */ import java.util.ArrayList;
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
/*     */ final class Sort
/*     */   extends Instruction
/*     */   implements Closure
/*     */ {
/*     */   private Expression _select;
/*     */   private AttributeValue _order;
/*     */   private AttributeValue _caseOrder;
/*     */   private AttributeValue _dataType;
/*     */   private AttributeValue _lang;
/*  70 */   private String _className = null;
/*  71 */   private ArrayList<VariableRefBase> _closureVars = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _needsSortRecordFactory = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean inInnerClass() {
/*  81 */     return (this._className != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Closure getParentClosure() {
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInnerClassName() {
/*  96 */     return this._className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addVariable(VariableRefBase variableRef) {
/* 103 */     if (this._closureVars == null) {
/* 104 */       this._closureVars = new ArrayList<>();
/*     */     }
/*     */ 
/*     */     
/* 108 */     if (!this._closureVars.contains(variableRef)) {
/* 109 */       this._closureVars.add(variableRef);
/* 110 */       this._needsSortRecordFactory = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setInnerClassName(String className) {
/* 117 */     this._className = className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseContents(Parser parser) {
/* 125 */     SyntaxTreeNode parent = getParent();
/* 126 */     if (!(parent instanceof ApplyTemplates) && !(parent instanceof ForEach)) {
/*     */       
/* 128 */       reportError(this, parser, "STRAY_SORT_ERR", (String)null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 133 */     this._select = parser.parseExpression(this, "select", "string(.)");
/*     */ 
/*     */     
/* 136 */     String val = getAttribute("order");
/* 137 */     if (val.length() == 0) val = "ascending"; 
/* 138 */     this._order = AttributeValue.create(this, val, parser);
/*     */ 
/*     */     
/* 141 */     val = getAttribute("data-type");
/* 142 */     if (val.length() == 0) {
/*     */       try {
/* 144 */         Type type = this._select.typeCheck(parser.getSymbolTable());
/* 145 */         if (type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.IntType) {
/* 146 */           val = "number";
/*     */         } else {
/* 148 */           val = "text";
/*     */         } 
/* 150 */       } catch (TypeCheckError e) {
/* 151 */         val = "text";
/*     */       } 
/*     */     }
/* 154 */     this._dataType = AttributeValue.create(this, val, parser);
/*     */     
/* 156 */     val = getAttribute("lang");
/* 157 */     this._lang = AttributeValue.create(this, val, parser);
/*     */     
/* 159 */     val = getAttribute("case-order");
/* 160 */     this._caseOrder = AttributeValue.create(this, val, parser);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 168 */     Type tselect = this._select.typeCheck(stable);
/*     */ 
/*     */ 
/*     */     
/* 172 */     if (!(tselect instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringType)) {
/* 173 */       this._select = new CastExpr(this._select, Type.String);
/*     */     }
/*     */     
/* 176 */     this._order.typeCheck(stable);
/* 177 */     this._caseOrder.typeCheck(stable);
/* 178 */     this._dataType.typeCheck(stable);
/* 179 */     this._lang.typeCheck(stable);
/* 180 */     return Type.Void;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateSortType(ClassGenerator classGen, MethodGenerator methodGen) {
/* 189 */     this._dataType.translate(classGen, methodGen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateSortOrder(ClassGenerator classGen, MethodGenerator methodGen) {
/* 194 */     this._order.translate(classGen, methodGen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateCaseOrder(ClassGenerator classGen, MethodGenerator methodGen) {
/* 199 */     this._caseOrder.translate(classGen, methodGen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateLang(ClassGenerator classGen, MethodGenerator methodGen) {
/* 204 */     this._lang.translate(classGen, methodGen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translateSelect(ClassGenerator classGen, MethodGenerator methodGen) {
/* 214 */     this._select.translate(classGen, methodGen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void translateSortIterator(ClassGenerator classGen, MethodGenerator methodGen, Expression nodeSet, Vector<Sort> sortObjects) {
/* 234 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 235 */     InstructionList il = methodGen.getInstructionList();
/*     */ 
/*     */     
/* 238 */     int init = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.dom.SortingIterator", "<init>", "(Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;Lcom/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecordFactory;)V");
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
/* 254 */     LocalVariableGen nodesTemp = methodGen.addLocalVariable("sort_tmp1", 
/* 255 */         Util.getJCRefType("Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;"), (InstructionHandle)null, (InstructionHandle)null);
/*     */ 
/*     */ 
/*     */     
/* 259 */     LocalVariableGen sortRecordFactoryTemp = methodGen.addLocalVariable("sort_tmp2", 
/* 260 */         Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecordFactory;"), (InstructionHandle)null, (InstructionHandle)null);
/*     */ 
/*     */ 
/*     */     
/* 264 */     if (nodeSet == null) {
/* 265 */       int children = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getAxisIterator", "(I)Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;");
/*     */ 
/*     */ 
/*     */       
/* 269 */       il.append(methodGen.loadDOM());
/* 270 */       il.append(new PUSH(cpg, 3));
/* 271 */       il.append(new INVOKEINTERFACE(children, 2));
/*     */     } else {
/*     */       
/* 274 */       nodeSet.translate(classGen, methodGen);
/*     */     } 
/*     */     
/* 277 */     nodesTemp.setStart(il.append(new ASTORE(nodesTemp.getIndex())));
/*     */ 
/*     */ 
/*     */     
/* 281 */     compileSortRecordFactory(sortObjects, classGen, methodGen);
/* 282 */     sortRecordFactoryTemp.setStart(il
/* 283 */         .append(new ASTORE(sortRecordFactoryTemp.getIndex())));
/*     */     
/* 285 */     il.append(new NEW(cpg.addClass("com.sun.org.apache.xalan.internal.xsltc.dom.SortingIterator")));
/* 286 */     il.append(DUP);
/* 287 */     nodesTemp.setEnd(il.append(new ALOAD(nodesTemp.getIndex())));
/* 288 */     sortRecordFactoryTemp.setEnd(il
/* 289 */         .append(new ALOAD(sortRecordFactoryTemp.getIndex())));
/* 290 */     il.append(new INVOKESPECIAL(init));
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
/*     */   public static void compileSortRecordFactory(Vector<Sort> sortObjects, ClassGenerator classGen, MethodGenerator methodGen) {
/* 302 */     String sortRecordClass = compileSortRecord(sortObjects, classGen, methodGen);
/*     */     
/* 304 */     boolean needsSortRecordFactory = false;
/* 305 */     int nsorts = sortObjects.size();
/* 306 */     for (int i = 0; i < nsorts; i++) {
/* 307 */       Sort sort = sortObjects.elementAt(i);
/* 308 */       needsSortRecordFactory |= sort._needsSortRecordFactory;
/*     */     } 
/*     */     
/* 311 */     String sortRecordFactoryClass = "com/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecordFactory";
/* 312 */     if (needsSortRecordFactory)
/*     */     {
/* 314 */       sortRecordFactoryClass = compileSortRecordFactory(sortObjects, classGen, methodGen, sortRecordClass);
/*     */     }
/*     */ 
/*     */     
/* 318 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 319 */     InstructionList il = methodGen.getInstructionList();
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
/* 332 */     LocalVariableGen sortOrderTemp = methodGen.addLocalVariable("sort_order_tmp", 
/* 333 */         Util.getJCRefType("[Ljava/lang/String;"), (InstructionHandle)null, (InstructionHandle)null);
/*     */     
/* 335 */     il.append(new PUSH(cpg, nsorts));
/* 336 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 337 */     for (int level = 0; level < nsorts; level++) {
/* 338 */       Sort sort = sortObjects.elementAt(level);
/* 339 */       il.append(DUP);
/* 340 */       il.append(new PUSH(cpg, level));
/* 341 */       sort.translateSortOrder(classGen, methodGen);
/* 342 */       il.append(AASTORE);
/*     */     } 
/* 344 */     sortOrderTemp.setStart(il.append(new ASTORE(sortOrderTemp.getIndex())));
/*     */ 
/*     */     
/* 347 */     LocalVariableGen sortTypeTemp = methodGen.addLocalVariable("sort_type_tmp", 
/* 348 */         Util.getJCRefType("[Ljava/lang/String;"), (InstructionHandle)null, (InstructionHandle)null);
/*     */     
/* 350 */     il.append(new PUSH(cpg, nsorts));
/* 351 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 352 */     for (int k = 0; k < nsorts; k++) {
/* 353 */       Sort sort = sortObjects.elementAt(k);
/* 354 */       il.append(DUP);
/* 355 */       il.append(new PUSH(cpg, k));
/* 356 */       sort.translateSortType(classGen, methodGen);
/* 357 */       il.append(AASTORE);
/*     */     } 
/* 359 */     sortTypeTemp.setStart(il.append(new ASTORE(sortTypeTemp.getIndex())));
/*     */ 
/*     */     
/* 362 */     LocalVariableGen sortLangTemp = methodGen.addLocalVariable("sort_lang_tmp", 
/* 363 */         Util.getJCRefType("[Ljava/lang/String;"), (InstructionHandle)null, (InstructionHandle)null);
/*     */     
/* 365 */     il.append(new PUSH(cpg, nsorts));
/* 366 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 367 */     for (int m = 0; m < nsorts; m++) {
/* 368 */       Sort sort = sortObjects.elementAt(m);
/* 369 */       il.append(DUP);
/* 370 */       il.append(new PUSH(cpg, m));
/* 371 */       sort.translateLang(classGen, methodGen);
/* 372 */       il.append(AASTORE);
/*     */     } 
/* 374 */     sortLangTemp.setStart(il.append(new ASTORE(sortLangTemp.getIndex())));
/*     */ 
/*     */     
/* 377 */     LocalVariableGen sortCaseOrderTemp = methodGen.addLocalVariable("sort_case_order_tmp", 
/* 378 */         Util.getJCRefType("[Ljava/lang/String;"), (InstructionHandle)null, (InstructionHandle)null);
/*     */     
/* 380 */     il.append(new PUSH(cpg, nsorts));
/* 381 */     il.append(new ANEWARRAY(cpg.addClass("java.lang.String")));
/* 382 */     for (int n = 0; n < nsorts; n++) {
/* 383 */       Sort sort = sortObjects.elementAt(n);
/* 384 */       il.append(DUP);
/* 385 */       il.append(new PUSH(cpg, n));
/* 386 */       sort.translateCaseOrder(classGen, methodGen);
/* 387 */       il.append(AASTORE);
/*     */     } 
/* 389 */     sortCaseOrderTemp.setStart(il
/* 390 */         .append(new ASTORE(sortCaseOrderTemp.getIndex())));
/*     */     
/* 392 */     il.append(new NEW(cpg.addClass(sortRecordFactoryClass)));
/* 393 */     il.append(DUP);
/* 394 */     il.append(methodGen.loadDOM());
/* 395 */     il.append(new PUSH(cpg, sortRecordClass));
/* 396 */     il.append(classGen.loadTranslet());
/*     */     
/* 398 */     sortOrderTemp.setEnd(il.append(new ALOAD(sortOrderTemp.getIndex())));
/* 399 */     sortTypeTemp.setEnd(il.append(new ALOAD(sortTypeTemp.getIndex())));
/* 400 */     sortLangTemp.setEnd(il.append(new ALOAD(sortLangTemp.getIndex())));
/* 401 */     sortCaseOrderTemp.setEnd(il
/* 402 */         .append(new ALOAD(sortCaseOrderTemp.getIndex())));
/*     */     
/* 404 */     il.append(new INVOKESPECIAL(cpg
/* 405 */           .addMethodref(sortRecordFactoryClass, "<init>", "(Lcom/sun/org/apache/xalan/internal/xsltc/DOM;Ljava/lang/String;Lcom/sun/org/apache/xalan/internal/xsltc/Translet;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 415 */     ArrayList<VariableRefBase> dups = new ArrayList<>();
/*     */     
/* 417 */     for (int j = 0; j < nsorts; j++) {
/* 418 */       Sort sort = sortObjects.get(j);
/*     */       
/* 420 */       int length = (sort._closureVars == null) ? 0 : sort._closureVars.size();
/*     */       
/* 422 */       for (int i1 = 0; i1 < length; i1++) {
/* 423 */         VariableRefBase varRef = sort._closureVars.get(i1);
/*     */ 
/*     */         
/* 426 */         if (!dups.contains(varRef)) {
/*     */           
/* 428 */           VariableBase var = varRef.getVariable();
/*     */ 
/*     */           
/* 431 */           il.append(DUP);
/* 432 */           il.append(var.loadInstruction());
/* 433 */           il.append(new PUTFIELD(cpg
/* 434 */                 .addFieldref(sortRecordFactoryClass, var.getEscapedName(), var
/* 435 */                   .getType().toSignature())));
/* 436 */           dups.add(varRef);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String compileSortRecordFactory(Vector<Sort> sortObjects, ClassGenerator classGen, MethodGenerator methodGen, String sortRecordClass) {
/* 445 */     XSLTC xsltc = ((Sort)sortObjects.firstElement()).getXSLTC();
/* 446 */     String className = xsltc.getHelperClassName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 454 */     NodeSortRecordFactGenerator sortRecordFactory = new NodeSortRecordFactGenerator(className, "com/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecordFactory", className + ".java", 49, new String[0], classGen.getStylesheet());
/*     */     
/* 456 */     ConstantPoolGen cpg = sortRecordFactory.getConstantPool();
/*     */ 
/*     */     
/* 459 */     int nsorts = sortObjects.size();
/* 460 */     ArrayList<VariableRefBase> dups = new ArrayList<>();
/*     */     
/* 462 */     for (int j = 0; j < nsorts; j++) {
/* 463 */       Sort sort = sortObjects.get(j);
/*     */       
/* 465 */       int length = (sort._closureVars == null) ? 0 : sort._closureVars.size();
/*     */       
/* 467 */       for (int k = 0; k < length; k++) {
/* 468 */         VariableRefBase varRef = sort._closureVars.get(k);
/*     */ 
/*     */         
/* 471 */         if (!dups.contains(varRef)) {
/*     */           
/* 473 */           VariableBase var = varRef.getVariable();
/* 474 */           sortRecordFactory.addField(new Field(1, cpg
/* 475 */                 .addUtf8(var.getEscapedName()), cpg
/* 476 */                 .addUtf8(var.getType().toSignature()), null, cpg
/* 477 */                 .getConstantPool()));
/* 478 */           dups.add(varRef);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 483 */     Type[] argTypes = new Type[7];
/*     */     
/* 485 */     argTypes[0] = Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/DOM;");
/* 486 */     argTypes[1] = Util.getJCRefType("Ljava/lang/String;");
/* 487 */     argTypes[2] = Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/Translet;");
/* 488 */     argTypes[3] = Util.getJCRefType("[Ljava/lang/String;");
/* 489 */     argTypes[4] = Util.getJCRefType("[Ljava/lang/String;");
/* 490 */     argTypes[5] = Util.getJCRefType("[Ljava/lang/String;");
/* 491 */     argTypes[6] = Util.getJCRefType("[Ljava/lang/String;");
/*     */     
/* 493 */     String[] argNames = new String[7];
/* 494 */     argNames[0] = "document";
/* 495 */     argNames[1] = "className";
/* 496 */     argNames[2] = "translet";
/* 497 */     argNames[3] = "order";
/* 498 */     argNames[4] = "type";
/* 499 */     argNames[5] = "lang";
/* 500 */     argNames[6] = "case_order";
/*     */ 
/*     */     
/* 503 */     InstructionList il = new InstructionList();
/* 504 */     MethodGenerator constructor = new MethodGenerator(1, Type.VOID, argTypes, argNames, "<init>", className, il, cpg);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 511 */     il.append(ALOAD_0);
/* 512 */     il.append(ALOAD_1);
/* 513 */     il.append(ALOAD_2);
/* 514 */     il.append(new ALOAD(3));
/* 515 */     il.append(new ALOAD(4));
/* 516 */     il.append(new ALOAD(5));
/* 517 */     il.append(new ALOAD(6));
/* 518 */     il.append(new ALOAD(7));
/* 519 */     il.append(new INVOKESPECIAL(cpg.addMethodref("com/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecordFactory", "<init>", "(Lcom/sun/org/apache/xalan/internal/xsltc/DOM;Ljava/lang/String;Lcom/sun/org/apache/xalan/internal/xsltc/Translet;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 528 */     il.append(RETURN);
/*     */ 
/*     */     
/* 531 */     il = new InstructionList();
/*     */ 
/*     */     
/* 534 */     MethodGenerator makeNodeSortRecord = new MethodGenerator(1, Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecord;"), new Type[] { Type.INT, Type.INT }, new String[] { "node", "last" }, "makeNodeSortRecord", className, il, cpg);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 541 */     il.append(ALOAD_0);
/* 542 */     il.append(ILOAD_1);
/* 543 */     il.append(ILOAD_2);
/* 544 */     il.append(new INVOKESPECIAL(cpg.addMethodref("com/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecordFactory", "makeNodeSortRecord", "(II)Lcom/sun/org/apache/xalan/internal/xsltc/dom/NodeSortRecord;")));
/*     */     
/* 546 */     il.append(DUP);
/* 547 */     il.append(new CHECKCAST(cpg.addClass(sortRecordClass)));
/*     */ 
/*     */     
/* 550 */     int ndups = dups.size();
/* 551 */     for (int i = 0; i < ndups; i++) {
/* 552 */       VariableRefBase varRef = dups.get(i);
/* 553 */       VariableBase var = varRef.getVariable();
/* 554 */       Type varType = var.getType();
/*     */       
/* 556 */       il.append(DUP);
/*     */ 
/*     */       
/* 559 */       il.append(ALOAD_0);
/* 560 */       il.append(new GETFIELD(cpg
/* 561 */             .addFieldref(className, var
/* 562 */               .getEscapedName(), varType.toSignature())));
/*     */ 
/*     */       
/* 565 */       il.append(new PUTFIELD(cpg
/* 566 */             .addFieldref(sortRecordClass, var
/* 567 */               .getEscapedName(), varType.toSignature())));
/*     */     } 
/* 569 */     il.append(POP);
/* 570 */     il.append(ARETURN);
/*     */     
/* 572 */     constructor.setMaxLocals();
/* 573 */     constructor.setMaxStack();
/* 574 */     sortRecordFactory.addMethod(constructor);
/* 575 */     makeNodeSortRecord.setMaxLocals();
/* 576 */     makeNodeSortRecord.setMaxStack();
/* 577 */     sortRecordFactory.addMethod(makeNodeSortRecord);
/* 578 */     xsltc.dumpClass(sortRecordFactory.getJavaClass());
/*     */     
/* 580 */     return className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String compileSortRecord(Vector<Sort> sortObjects, ClassGenerator classGen, MethodGenerator methodGen) {
/* 589 */     XSLTC xsltc = ((Sort)sortObjects.firstElement()).getXSLTC();
/* 590 */     String className = xsltc.getHelperClassName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 599 */     NodeSortRecordGenerator sortRecord = new NodeSortRecordGenerator(className, "com.sun.org.apache.xalan.internal.xsltc.dom.NodeSortRecord", "sort$0.java", 49, new String[0], classGen.getStylesheet());
/*     */     
/* 601 */     ConstantPoolGen cpg = sortRecord.getConstantPool();
/*     */ 
/*     */     
/* 604 */     int nsorts = sortObjects.size();
/* 605 */     ArrayList<VariableRefBase> dups = new ArrayList<>();
/*     */     
/* 607 */     for (int j = 0; j < nsorts; j++) {
/* 608 */       Sort sort = sortObjects.get(j);
/*     */ 
/*     */       
/* 611 */       sort.setInnerClassName(className);
/*     */ 
/*     */       
/* 614 */       int length = (sort._closureVars == null) ? 0 : sort._closureVars.size();
/* 615 */       for (int i = 0; i < length; i++) {
/* 616 */         VariableRefBase varRef = sort._closureVars.get(i);
/*     */ 
/*     */         
/* 619 */         if (!dups.contains(varRef)) {
/*     */           
/* 621 */           VariableBase var = varRef.getVariable();
/* 622 */           sortRecord.addField(new Field(1, cpg
/* 623 */                 .addUtf8(var.getEscapedName()), cpg
/* 624 */                 .addUtf8(var.getType().toSignature()), null, cpg
/* 625 */                 .getConstantPool()));
/* 626 */           dups.add(varRef);
/*     */         } 
/*     */       } 
/*     */     } 
/* 630 */     MethodGenerator init = compileInit(sortRecord, cpg, className);
/* 631 */     MethodGenerator extract = compileExtract(sortObjects, sortRecord, cpg, className);
/*     */     
/* 633 */     sortRecord.addMethod(init);
/* 634 */     sortRecord.addMethod(extract);
/*     */     
/* 636 */     xsltc.dumpClass(sortRecord.getJavaClass());
/* 637 */     return className;
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
/*     */   private static MethodGenerator compileInit(NodeSortRecordGenerator sortRecord, ConstantPoolGen cpg, String className) {
/* 649 */     InstructionList il = new InstructionList();
/* 650 */     MethodGenerator init = new MethodGenerator(1, Type.VOID, null, null, "<init>", className, il, cpg);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 657 */     il.append(ALOAD_0);
/* 658 */     il.append(new INVOKESPECIAL(cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.dom.NodeSortRecord", "<init>", "()V")));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 663 */     il.append(RETURN);
/*     */     
/* 665 */     return init;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MethodGenerator compileExtract(Vector<Sort> sortObjects, NodeSortRecordGenerator sortRecord, ConstantPoolGen cpg, String className) {
/* 676 */     InstructionList il = new InstructionList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 686 */     CompareGenerator extractMethod = new CompareGenerator(17, Type.STRING, new Type[] { Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/DOM;"), Type.INT, Type.INT, Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/runtime/AbstractTranslet;"), Type.INT }, new String[] { "dom", "current", "level", "translet", "last" }, "extractValueFromDOM", className, il, cpg);
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
/* 698 */     int levels = sortObjects.size();
/* 699 */     int[] match = new int[levels];
/* 700 */     InstructionHandle[] target = new InstructionHandle[levels];
/* 701 */     InstructionHandle tblswitch = null;
/*     */ 
/*     */     
/* 704 */     if (levels > 1) {
/*     */       
/* 706 */       il.append(new ILOAD(extractMethod.getLocalIndex("level")));
/*     */       
/* 708 */       tblswitch = il.append(new NOP());
/*     */     } 
/*     */ 
/*     */     
/* 712 */     for (int level = 0; level < levels; level++) {
/* 713 */       match[level] = level;
/* 714 */       Sort sort = sortObjects.elementAt(level);
/* 715 */       target[level] = il.append(NOP);
/* 716 */       sort.translateSelect(sortRecord, extractMethod);
/* 717 */       il.append(ARETURN);
/*     */     } 
/*     */ 
/*     */     
/* 721 */     if (levels > 1) {
/*     */ 
/*     */       
/* 724 */       InstructionHandle defaultTarget = il.append(new PUSH(cpg, ""));
/* 725 */       il.insert(tblswitch, new TABLESWITCH(match, target, defaultTarget));
/* 726 */       il.append(ARETURN);
/*     */     } 
/*     */     
/* 729 */     return extractMethod;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/Sort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */