/*      */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*      */ 
/*      */ import com.sun.org.apache.bcel.internal.generic.ALOAD;
/*      */ import com.sun.org.apache.bcel.internal.generic.BranchHandle;
/*      */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*      */ import com.sun.org.apache.bcel.internal.generic.DUP;
/*      */ import com.sun.org.apache.bcel.internal.generic.GOTO_W;
/*      */ import com.sun.org.apache.bcel.internal.generic.IFLT;
/*      */ import com.sun.org.apache.bcel.internal.generic.ILOAD;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
/*      */ import com.sun.org.apache.bcel.internal.generic.ISTORE;
/*      */ import com.sun.org.apache.bcel.internal.generic.Instruction;
/*      */ import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
/*      */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*      */ import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
/*      */ import com.sun.org.apache.bcel.internal.generic.SWITCH;
/*      */ import com.sun.org.apache.bcel.internal.generic.TargetLostException;
/*      */ import com.sun.org.apache.bcel.internal.generic.Type;
/*      */ import com.sun.org.apache.bcel.internal.util.InstructionFinder;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NamedMethodGenerator;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class Mode
/*      */   implements Constants
/*      */ {
/*      */   private final QName _name;
/*      */   private final Stylesheet _stylesheet;
/*      */   private final String _methodName;
/*      */   private Vector _templates;
/*   91 */   private Vector _childNodeGroup = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   96 */   private TestSeq _childNodeTestSeq = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  101 */   private Vector _attribNodeGroup = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  106 */   private TestSeq _attribNodeTestSeq = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   private Vector _idxGroup = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  116 */   private TestSeq _idxTestSeq = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector[] _patternGroups;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TestSeq[] _testSeq;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  132 */   private Map<Template, Object> _neededTemplates = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  137 */   private Map<Template, Mode> _namedTemplates = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  142 */   private Map<Template, InstructionHandle> _templateIHs = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  147 */   private Map<Template, InstructionList> _templateILs = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  152 */   private LocationPathPattern _rootPattern = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  158 */   private Map<Integer, Integer> _importLevels = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  163 */   private Map<String, Key> _keys = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int _currentIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Mode(QName name, Stylesheet stylesheet, String suffix) {
/*  179 */     this._name = name;
/*  180 */     this._stylesheet = stylesheet;
/*  181 */     this._methodName = "applyTemplates" + suffix;
/*  182 */     this._templates = new Vector();
/*  183 */     this._patternGroups = new Vector[32];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String functionName() {
/*  194 */     return this._methodName;
/*      */   }
/*      */   
/*      */   public String functionName(int min, int max) {
/*  198 */     if (this._importLevels == null) {
/*  199 */       this._importLevels = new HashMap<>();
/*      */     }
/*  201 */     this._importLevels.put(Integer.valueOf(max), Integer.valueOf(min));
/*  202 */     return this._methodName + '_' + max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getClassName() {
/*  209 */     return this._stylesheet.getClassName();
/*      */   }
/*      */   
/*      */   public Stylesheet getStylesheet() {
/*  213 */     return this._stylesheet;
/*      */   }
/*      */   
/*      */   public void addTemplate(Template template) {
/*  217 */     this._templates.addElement(template);
/*      */   }
/*      */   
/*      */   private Vector quicksort(Vector templates, int p, int r) {
/*  221 */     if (p < r) {
/*  222 */       int q = partition(templates, p, r);
/*  223 */       quicksort(templates, p, q);
/*  224 */       quicksort(templates, q + 1, r);
/*      */     } 
/*  226 */     return templates;
/*      */   }
/*      */   
/*      */   private int partition(Vector<Template> templates, int p, int r) {
/*  230 */     Template x = templates.elementAt(p);
/*  231 */     int i = p - 1;
/*  232 */     int j = r + 1;
/*      */     while (true) {
/*  234 */       if (x.compareTo(templates.elementAt(--j)) > 0)
/*  235 */         continue;  while (x.compareTo(templates.elementAt(++i)) < 0);
/*  236 */       if (i < j) {
/*  237 */         templates.set(j, templates.set(i, templates.elementAt(j))); continue;
/*      */       }  break;
/*      */     } 
/*  240 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPatterns(Map<String, Key> keys) {
/*  249 */     this._keys = keys;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  261 */     this._templates = quicksort(this._templates, 0, this._templates.size() - 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  274 */     Enumeration<Template> templates = this._templates.elements();
/*  275 */     while (templates.hasMoreElements()) {
/*      */       
/*  277 */       Template template = templates.nextElement();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  284 */       if (template.isNamed() && !template.disabled()) {
/*  285 */         this._namedTemplates.put(template, this);
/*      */       }
/*      */ 
/*      */       
/*  289 */       Pattern pattern = template.getPattern();
/*  290 */       if (pattern != null) {
/*  291 */         flattenAlternative(pattern, template, keys);
/*      */       }
/*      */     } 
/*  294 */     prepareTestSequences();
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
/*      */   private void flattenAlternative(Pattern pattern, Template template, Map<String, Key> keys) {
/*  308 */     if (pattern instanceof IdKeyPattern) {
/*  309 */       IdKeyPattern idkey = (IdKeyPattern)pattern;
/*  310 */       idkey.setTemplate(template);
/*  311 */       if (this._idxGroup == null) this._idxGroup = new Vector(); 
/*  312 */       this._idxGroup.add(pattern);
/*      */     
/*      */     }
/*  315 */     else if (pattern instanceof AlternativePattern) {
/*  316 */       AlternativePattern alt = (AlternativePattern)pattern;
/*  317 */       flattenAlternative(alt.getLeft(), template, keys);
/*  318 */       flattenAlternative(alt.getRight(), template, keys);
/*      */     
/*      */     }
/*  321 */     else if (pattern instanceof LocationPathPattern) {
/*  322 */       LocationPathPattern lpp = (LocationPathPattern)pattern;
/*  323 */       lpp.setTemplate(template);
/*  324 */       addPatternToGroup(lpp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addPatternToGroup(LocationPathPattern lpp) {
/*  334 */     if (lpp instanceof IdKeyPattern) {
/*  335 */       addPattern(-1, lpp);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  340 */       StepPattern kernel = lpp.getKernelPattern();
/*  341 */       if (kernel != null) {
/*  342 */         addPattern(kernel.getNodeType(), lpp);
/*      */       }
/*  344 */       else if (this._rootPattern == null || lpp
/*  345 */         .noSmallerThan(this._rootPattern)) {
/*  346 */         this._rootPattern = lpp;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addPattern(int kernelType, LocationPathPattern pattern) {
/*      */     Vector<LocationPathPattern> patterns;
/*  356 */     int oldLength = this._patternGroups.length;
/*  357 */     if (kernelType >= oldLength) {
/*  358 */       Vector[] newGroups = new Vector[kernelType * 2];
/*  359 */       System.arraycopy(this._patternGroups, 0, newGroups, 0, oldLength);
/*  360 */       this._patternGroups = newGroups;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  366 */     if (kernelType == -1) {
/*  367 */       if (pattern.getAxis() == 2) {
/*  368 */         patterns = (this._attribNodeGroup == null) ? (this._attribNodeGroup = new Vector(2)) : this._attribNodeGroup;
/*      */       }
/*      */       else {
/*      */         
/*  372 */         patterns = (this._childNodeGroup == null) ? (this._childNodeGroup = new Vector(2)) : this._childNodeGroup;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  377 */       patterns = (this._patternGroups[kernelType] == null) ? (this._patternGroups[kernelType] = new Vector(2)) : this._patternGroups[kernelType];
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  382 */     if (patterns.size() == 0) {
/*  383 */       patterns.addElement(pattern);
/*      */     } else {
/*      */       
/*  386 */       boolean inserted = false;
/*  387 */       for (int i = 0; i < patterns.size(); i++) {
/*      */         
/*  389 */         LocationPathPattern lppToCompare = patterns.elementAt(i);
/*      */         
/*  391 */         if (pattern.noSmallerThan(lppToCompare)) {
/*  392 */           inserted = true;
/*  393 */           patterns.insertElementAt(pattern, i);
/*      */           break;
/*      */         } 
/*      */       } 
/*  397 */       if (!inserted) {
/*  398 */         patterns.addElement(pattern);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void completeTestSequences(int nodeType, Vector<LocationPathPattern> patterns) {
/*  408 */     if (patterns != null) {
/*  409 */       if (this._patternGroups[nodeType] == null) {
/*  410 */         this._patternGroups[nodeType] = patterns;
/*      */       } else {
/*      */         
/*  413 */         int m = patterns.size();
/*  414 */         for (int j = 0; j < m; j++) {
/*  415 */           addPattern(nodeType, patterns
/*  416 */               .elementAt(j));
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void prepareTestSequences() {
/*  428 */     Vector starGroup = this._patternGroups[1];
/*  429 */     Vector atStarGroup = this._patternGroups[2];
/*      */ 
/*      */     
/*  432 */     completeTestSequences(3, this._childNodeGroup);
/*      */ 
/*      */     
/*  435 */     completeTestSequences(1, this._childNodeGroup);
/*      */ 
/*      */     
/*  438 */     completeTestSequences(7, this._childNodeGroup);
/*      */ 
/*      */     
/*  441 */     completeTestSequences(8, this._childNodeGroup);
/*      */ 
/*      */     
/*  444 */     completeTestSequences(2, this._attribNodeGroup);
/*      */     
/*  446 */     Vector<String> names = this._stylesheet.getXSLTC().getNamesIndex();
/*  447 */     if (starGroup != null || atStarGroup != null || this._childNodeGroup != null || this._attribNodeGroup != null) {
/*      */ 
/*      */       
/*  450 */       int j = this._patternGroups.length;
/*      */ 
/*      */       
/*  453 */       for (int k = 14; k < j; k++) {
/*  454 */         if (this._patternGroups[k] != null) {
/*      */           
/*  456 */           String name = names.elementAt(k - 14);
/*      */           
/*  458 */           if (isAttributeName(name)) {
/*      */             
/*  460 */             completeTestSequences(k, atStarGroup);
/*      */ 
/*      */             
/*  463 */             completeTestSequences(k, this._attribNodeGroup);
/*      */           }
/*      */           else {
/*      */             
/*  467 */             completeTestSequences(k, starGroup);
/*      */ 
/*      */             
/*  470 */             completeTestSequences(k, this._childNodeGroup);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  475 */     this._testSeq = new TestSeq[14 + names.size()];
/*      */     
/*  477 */     int n = this._patternGroups.length;
/*  478 */     for (int i = 0; i < n; i++) {
/*  479 */       Vector patterns = this._patternGroups[i];
/*  480 */       if (patterns != null) {
/*  481 */         TestSeq testSeq = new TestSeq(patterns, i, this);
/*      */         
/*  483 */         testSeq.reduce();
/*  484 */         this._testSeq[i] = testSeq;
/*  485 */         testSeq.findTemplates(this._neededTemplates);
/*      */       } 
/*      */     } 
/*      */     
/*  489 */     if (this._childNodeGroup != null && this._childNodeGroup.size() > 0) {
/*  490 */       this._childNodeTestSeq = new TestSeq(this._childNodeGroup, -1, this);
/*  491 */       this._childNodeTestSeq.reduce();
/*  492 */       this._childNodeTestSeq.findTemplates(this._neededTemplates);
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
/*  503 */     if (this._idxGroup != null && this._idxGroup.size() > 0) {
/*  504 */       this._idxTestSeq = new TestSeq(this._idxGroup, this);
/*  505 */       this._idxTestSeq.reduce();
/*  506 */       this._idxTestSeq.findTemplates(this._neededTemplates);
/*      */     } 
/*      */     
/*  509 */     if (this._rootPattern != null)
/*      */     {
/*  511 */       this._neededTemplates.put(this._rootPattern.getTemplate(), this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void compileNamedTemplate(Template template, ClassGenerator classGen) {
/*  517 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  518 */     InstructionList il = new InstructionList();
/*  519 */     String methodName = Util.escape(template.getName().toString());
/*      */     
/*  521 */     int numParams = 0;
/*  522 */     if (template.isSimpleNamedTemplate()) {
/*  523 */       Vector<Param> parameters = template.getParameters();
/*  524 */       numParams = parameters.size();
/*      */     } 
/*      */ 
/*      */     
/*  528 */     Type[] types = new Type[4 + numParams];
/*      */     
/*  530 */     String[] names = new String[4 + numParams];
/*  531 */     types[0] = Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/DOM;");
/*  532 */     types[1] = Util.getJCRefType("Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;");
/*  533 */     types[2] = Util.getJCRefType("Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;");
/*  534 */     types[3] = Type.INT;
/*  535 */     names[0] = "document";
/*  536 */     names[1] = "iterator";
/*  537 */     names[2] = "handler";
/*  538 */     names[3] = "node";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  543 */     for (int i = 4; i < 4 + numParams; i++) {
/*  544 */       types[i] = Util.getJCRefType("Ljava/lang/Object;");
/*  545 */       names[i] = "param" + String.valueOf(i - 4);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  552 */     NamedMethodGenerator methodGen = new NamedMethodGenerator(1, Type.VOID, types, names, methodName, getClassName(), il, cpg);
/*      */     
/*  554 */     il.append(template.compile(classGen, methodGen));
/*  555 */     il.append(RETURN);
/*      */     
/*  557 */     classGen.addMethod(methodGen);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void compileTemplates(ClassGenerator classGen, MethodGenerator methodGen, InstructionHandle next) {
/*  564 */     Set<Template> templates = this._namedTemplates.keySet();
/*  565 */     for (Template template : templates) {
/*  566 */       compileNamedTemplate(template, classGen);
/*      */     }
/*      */     
/*  569 */     templates = this._neededTemplates.keySet();
/*  570 */     for (Template template : templates) {
/*  571 */       if (template.hasContents()) {
/*      */         
/*  573 */         InstructionList til = template.compile(classGen, methodGen);
/*  574 */         til.append(new GOTO_W(next));
/*  575 */         this._templateILs.put(template, til);
/*  576 */         this._templateIHs.put(template, til.getStart());
/*      */         
/*      */         continue;
/*      */       } 
/*  580 */       this._templateIHs.put(template, next);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void appendTemplateCode(InstructionList body) {
/*  586 */     for (Template template : this._neededTemplates.keySet()) {
/*  587 */       InstructionList iList = this._templateILs.get(template);
/*  588 */       if (iList != null) {
/*  589 */         body.append(iList);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void appendTestSequences(InstructionList body) {
/*  596 */     int n = this._testSeq.length;
/*  597 */     for (int i = 0; i < n; i++) {
/*  598 */       TestSeq testSeq = this._testSeq[i];
/*  599 */       if (testSeq != null) {
/*  600 */         InstructionList il = testSeq.getInstructionList();
/*  601 */         if (il != null) {
/*  602 */           body.append(il);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void compileGetChildren(ClassGenerator classGen, MethodGenerator methodGen, int node) {
/*  611 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  612 */     InstructionList il = methodGen.getInstructionList();
/*  613 */     int git = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getChildren", "(I)Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;");
/*      */ 
/*      */     
/*  616 */     il.append(methodGen.loadDOM());
/*  617 */     il.append(new ILOAD(node));
/*  618 */     il.append(new INVOKEINTERFACE(git, 2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InstructionList compileDefaultRecursion(ClassGenerator classGen, MethodGenerator methodGen, InstructionHandle next) {
/*  627 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  628 */     InstructionList il = new InstructionList();
/*  629 */     String applyTemplatesSig = classGen.getApplyTemplatesSig();
/*  630 */     int git = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getChildren", "(I)Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;");
/*      */ 
/*      */     
/*  633 */     int applyTemplates = cpg.addMethodref(getClassName(), 
/*  634 */         functionName(), applyTemplatesSig);
/*      */     
/*  636 */     il.append(classGen.loadTranslet());
/*  637 */     il.append(methodGen.loadDOM());
/*      */     
/*  639 */     il.append(methodGen.loadDOM());
/*  640 */     il.append(new ILOAD(this._currentIndex));
/*  641 */     il.append(new INVOKEINTERFACE(git, 2));
/*  642 */     il.append(methodGen.loadHandler());
/*  643 */     il.append(new INVOKEVIRTUAL(applyTemplates));
/*  644 */     il.append(new GOTO_W(next));
/*  645 */     return il;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InstructionList compileDefaultText(ClassGenerator classGen, MethodGenerator methodGen, InstructionHandle next) {
/*  655 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  656 */     InstructionList il = new InstructionList();
/*      */     
/*  658 */     int chars = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "characters", "(ILcom/sun/org/apache/xml/internal/serializer/SerializationHandler;)V");
/*      */ 
/*      */     
/*  661 */     il.append(methodGen.loadDOM());
/*  662 */     il.append(new ILOAD(this._currentIndex));
/*  663 */     il.append(methodGen.loadHandler());
/*  664 */     il.append(new INVOKEINTERFACE(chars, 3));
/*  665 */     il.append(new GOTO_W(next));
/*  666 */     return il;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InstructionList compileNamespaces(ClassGenerator classGen, MethodGenerator methodGen, boolean[] isNamespace, boolean[] isAttribute, boolean attrFlag, InstructionHandle defaultTarget) {
/*  675 */     XSLTC xsltc = classGen.getParser().getXSLTC();
/*  676 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*      */ 
/*      */     
/*  679 */     Vector namespaces = xsltc.getNamespaceIndex();
/*  680 */     Vector<String> names = xsltc.getNamesIndex();
/*  681 */     int namespaceCount = namespaces.size() + 1;
/*  682 */     int namesCount = names.size();
/*      */     
/*  684 */     InstructionList il = new InstructionList();
/*  685 */     int[] types = new int[namespaceCount];
/*  686 */     InstructionHandle[] targets = new InstructionHandle[types.length];
/*      */     
/*  688 */     if (namespaceCount > 0) {
/*  689 */       boolean compiled = false;
/*      */       
/*      */       int i;
/*  692 */       for (i = 0; i < namespaceCount; i++) {
/*  693 */         targets[i] = defaultTarget;
/*  694 */         types[i] = i;
/*      */       } 
/*      */ 
/*      */       
/*  698 */       for (i = 14; i < 14 + namesCount; i++) {
/*  699 */         if (isNamespace[i] && isAttribute[i] == attrFlag) {
/*  700 */           String name = names.elementAt(i - 14);
/*  701 */           String namespace = name.substring(0, name.lastIndexOf(':'));
/*  702 */           int type = xsltc.registerNamespace(namespace);
/*      */           
/*  704 */           if (i < this._testSeq.length && this._testSeq[i] != null) {
/*      */             
/*  706 */             targets[type] = this._testSeq[i]
/*  707 */               .compile(classGen, methodGen, defaultTarget);
/*      */ 
/*      */             
/*  710 */             compiled = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  716 */       if (!compiled) return null;
/*      */ 
/*      */       
/*  719 */       int getNS = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getNamespaceType", "(I)I");
/*      */ 
/*      */       
/*  722 */       il.append(methodGen.loadDOM());
/*  723 */       il.append(new ILOAD(this._currentIndex));
/*  724 */       il.append(new INVOKEINTERFACE(getNS, 2));
/*  725 */       il.append(new SWITCH(types, targets, defaultTarget));
/*  726 */       return il;
/*      */     } 
/*      */     
/*  729 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void compileApplyTemplates(ClassGenerator classGen) {
/*  738 */     XSLTC xsltc = classGen.getParser().getXSLTC();
/*  739 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  740 */     Vector<String> names = xsltc.getNamesIndex();
/*      */ 
/*      */     
/*  743 */     Type[] argTypes = new Type[3];
/*      */     
/*  745 */     argTypes[0] = Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/DOM;");
/*  746 */     argTypes[1] = Util.getJCRefType("Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;");
/*  747 */     argTypes[2] = Util.getJCRefType("Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;");
/*      */     
/*  749 */     String[] argNames = new String[3];
/*  750 */     argNames[0] = "document";
/*  751 */     argNames[1] = "iterator";
/*  752 */     argNames[2] = "handler";
/*      */     
/*  754 */     InstructionList mainIL = new InstructionList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  760 */     MethodGenerator methodGen = new MethodGenerator(17, Type.VOID, argTypes, argNames, functionName(), getClassName(), mainIL, classGen.getConstantPool());
/*  761 */     methodGen.addException("com.sun.org.apache.xalan.internal.xsltc.TransletException");
/*      */ 
/*      */     
/*  764 */     mainIL.append(NOP);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  769 */     LocalVariableGen current = methodGen.addLocalVariable2("current", Type.INT, (InstructionHandle)null);
/*      */ 
/*      */     
/*  772 */     this._currentIndex = current.getIndex();
/*      */ 
/*      */ 
/*      */     
/*  776 */     InstructionList body = new InstructionList();
/*  777 */     body.append(NOP);
/*      */ 
/*      */ 
/*      */     
/*  781 */     InstructionList ilLoop = new InstructionList();
/*  782 */     ilLoop.append(methodGen.loadIterator());
/*  783 */     ilLoop.append(methodGen.nextNode());
/*  784 */     ilLoop.append(DUP);
/*  785 */     ilLoop.append(new ISTORE(this._currentIndex));
/*      */ 
/*      */ 
/*      */     
/*  789 */     BranchHandle ifeq = ilLoop.append(new IFLT(null));
/*  790 */     BranchHandle loop = ilLoop.append(new GOTO_W(null));
/*  791 */     ifeq.setTarget(ilLoop.append(RETURN));
/*  792 */     InstructionHandle ihLoop = ilLoop.getStart();
/*      */     
/*  794 */     current.setStart(mainIL.append(new GOTO_W(ihLoop)));
/*      */ 
/*      */     
/*  797 */     current.setEnd(loop);
/*      */ 
/*      */ 
/*      */     
/*  801 */     InstructionList ilRecurse = compileDefaultRecursion(classGen, methodGen, ihLoop);
/*  802 */     InstructionHandle ihRecurse = ilRecurse.getStart();
/*      */ 
/*      */ 
/*      */     
/*  806 */     InstructionList ilText = compileDefaultText(classGen, methodGen, ihLoop);
/*  807 */     InstructionHandle ihText = ilText.getStart();
/*      */ 
/*      */     
/*  810 */     int[] types = new int[14 + names.size()];
/*  811 */     for (int i = 0; i < types.length; i++) {
/*  812 */       types[i] = i;
/*      */     }
/*      */ 
/*      */     
/*  816 */     boolean[] isAttribute = new boolean[types.length];
/*  817 */     boolean[] isNamespace = new boolean[types.length];
/*  818 */     for (int j = 0; j < names.size(); j++) {
/*  819 */       String name = names.elementAt(j);
/*  820 */       isAttribute[j + 14] = isAttributeName(name);
/*  821 */       isNamespace[j + 14] = isNamespaceName(name);
/*      */     } 
/*      */ 
/*      */     
/*  825 */     compileTemplates(classGen, methodGen, ihLoop);
/*      */ 
/*      */     
/*  828 */     TestSeq elemTest = this._testSeq[1];
/*  829 */     InstructionHandle ihElem = ihRecurse;
/*  830 */     if (elemTest != null) {
/*  831 */       ihElem = elemTest.compile(classGen, methodGen, ihRecurse);
/*      */     }
/*      */     
/*  834 */     TestSeq attrTest = this._testSeq[2];
/*  835 */     InstructionHandle ihAttr = ihText;
/*  836 */     if (attrTest != null) {
/*  837 */       ihAttr = attrTest.compile(classGen, methodGen, ihAttr);
/*      */     }
/*      */     
/*  840 */     InstructionList ilKey = null;
/*  841 */     if (this._idxTestSeq != null) {
/*  842 */       loop.setTarget(this._idxTestSeq.compile(classGen, methodGen, body.getStart()));
/*  843 */       ilKey = this._idxTestSeq.getInstructionList();
/*      */     } else {
/*      */       
/*  846 */       loop.setTarget(body.getStart());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  851 */     if (this._childNodeTestSeq != null) {
/*      */       
/*  853 */       double nodePrio = this._childNodeTestSeq.getPriority();
/*  854 */       int nodePos = this._childNodeTestSeq.getPosition();
/*  855 */       double elemPrio = -1.7976931348623157E308D;
/*  856 */       int elemPos = Integer.MIN_VALUE;
/*      */       
/*  858 */       if (elemTest != null) {
/*  859 */         elemPrio = elemTest.getPriority();
/*  860 */         elemPos = elemTest.getPosition();
/*      */       } 
/*  862 */       if (elemPrio == Double.NaN || elemPrio < nodePrio || (elemPrio == nodePrio && elemPos < nodePos))
/*      */       {
/*      */         
/*  865 */         ihElem = this._childNodeTestSeq.compile(classGen, methodGen, ihLoop);
/*      */       }
/*      */ 
/*      */       
/*  869 */       TestSeq textTest = this._testSeq[3];
/*  870 */       double textPrio = -1.7976931348623157E308D;
/*  871 */       int textPos = Integer.MIN_VALUE;
/*      */       
/*  873 */       if (textTest != null) {
/*  874 */         textPrio = textTest.getPriority();
/*  875 */         textPos = textTest.getPosition();
/*      */       } 
/*  877 */       if (textPrio == Double.NaN || textPrio < nodePrio || (textPrio == nodePrio && textPos < nodePos)) {
/*      */ 
/*      */         
/*  880 */         ihText = this._childNodeTestSeq.compile(classGen, methodGen, ihLoop);
/*  881 */         this._testSeq[3] = this._childNodeTestSeq;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  886 */     InstructionHandle elemNamespaceHandle = ihElem;
/*  887 */     InstructionList nsElem = compileNamespaces(classGen, methodGen, isNamespace, isAttribute, false, ihElem);
/*      */ 
/*      */     
/*  890 */     if (nsElem != null) elemNamespaceHandle = nsElem.getStart();
/*      */ 
/*      */     
/*  893 */     InstructionHandle attrNamespaceHandle = ihAttr;
/*  894 */     InstructionList nsAttr = compileNamespaces(classGen, methodGen, isNamespace, isAttribute, true, ihAttr);
/*      */ 
/*      */     
/*  897 */     if (nsAttr != null) attrNamespaceHandle = nsAttr.getStart();
/*      */ 
/*      */     
/*  900 */     InstructionHandle[] targets = new InstructionHandle[types.length];
/*  901 */     for (int k = 14; k < targets.length; k++) {
/*  902 */       TestSeq testSeq = this._testSeq[k];
/*      */       
/*  904 */       if (isNamespace[k]) {
/*  905 */         if (isAttribute[k]) {
/*  906 */           targets[k] = attrNamespaceHandle;
/*      */         } else {
/*  908 */           targets[k] = elemNamespaceHandle;
/*      */         }
/*      */       
/*  911 */       } else if (testSeq != null) {
/*  912 */         if (isAttribute[k]) {
/*  913 */           targets[k] = testSeq.compile(classGen, methodGen, attrNamespaceHandle);
/*      */         } else {
/*      */           
/*  916 */           targets[k] = testSeq.compile(classGen, methodGen, elemNamespaceHandle);
/*      */         } 
/*      */       } else {
/*      */         
/*  920 */         targets[k] = ihLoop;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  926 */     targets[0] = (this._rootPattern != null) ? 
/*  927 */       getTemplateInstructionHandle(this._rootPattern.getTemplate()) : ihRecurse;
/*      */ 
/*      */ 
/*      */     
/*  931 */     targets[9] = (this._rootPattern != null) ? 
/*  932 */       getTemplateInstructionHandle(this._rootPattern.getTemplate()) : ihRecurse;
/*      */ 
/*      */ 
/*      */     
/*  936 */     targets[3] = (this._testSeq[3] != null) ? this._testSeq[3]
/*  937 */       .compile(classGen, methodGen, ihText) : ihText;
/*      */ 
/*      */ 
/*      */     
/*  941 */     targets[13] = ihLoop;
/*      */ 
/*      */     
/*  944 */     targets[1] = elemNamespaceHandle;
/*      */ 
/*      */     
/*  947 */     targets[2] = attrNamespaceHandle;
/*      */ 
/*      */     
/*  950 */     InstructionHandle ihPI = ihLoop;
/*  951 */     if (this._childNodeTestSeq != null) ihPI = ihElem; 
/*  952 */     if (this._testSeq[7] != null) {
/*  953 */       targets[7] = this._testSeq[7]
/*      */         
/*  955 */         .compile(classGen, methodGen, ihPI);
/*      */     } else {
/*  957 */       targets[7] = ihPI;
/*      */     } 
/*      */     
/*  960 */     InstructionHandle ihComment = ihLoop;
/*  961 */     if (this._childNodeTestSeq != null) ihComment = ihElem; 
/*  962 */     targets[8] = (this._testSeq[8] != null) ? this._testSeq[8]
/*  963 */       .compile(classGen, methodGen, ihComment) : ihComment;
/*      */ 
/*      */ 
/*      */     
/*  967 */     targets[4] = ihLoop;
/*      */ 
/*      */     
/*  970 */     targets[11] = ihLoop;
/*      */ 
/*      */     
/*  973 */     targets[10] = ihLoop;
/*      */ 
/*      */     
/*  976 */     targets[6] = ihLoop;
/*      */ 
/*      */     
/*  979 */     targets[5] = ihLoop;
/*      */ 
/*      */     
/*  982 */     targets[12] = ihLoop;
/*      */ 
/*      */ 
/*      */     
/*  986 */     for (int m = 14; m < targets.length; m++) {
/*  987 */       TestSeq testSeq = this._testSeq[m];
/*      */       
/*  989 */       if (testSeq == null || isNamespace[m]) {
/*  990 */         if (isAttribute[m]) {
/*  991 */           targets[m] = attrNamespaceHandle;
/*      */         } else {
/*  993 */           targets[m] = elemNamespaceHandle;
/*      */         }
/*      */       
/*      */       }
/*  997 */       else if (isAttribute[m]) {
/*  998 */         targets[m] = testSeq.compile(classGen, methodGen, attrNamespaceHandle);
/*      */       } else {
/*      */         
/* 1001 */         targets[m] = testSeq.compile(classGen, methodGen, elemNamespaceHandle);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1006 */     if (ilKey != null) body.insert(ilKey);
/*      */ 
/*      */     
/* 1009 */     int getType = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getExpandedTypeID", "(I)I");
/*      */ 
/*      */     
/* 1012 */     body.append(methodGen.loadDOM());
/* 1013 */     body.append(new ILOAD(this._currentIndex));
/* 1014 */     body.append(new INVOKEINTERFACE(getType, 2));
/*      */ 
/*      */     
/* 1017 */     InstructionHandle disp = body.append(new SWITCH(types, targets, ihLoop));
/*      */ 
/*      */     
/* 1020 */     appendTestSequences(body);
/*      */     
/* 1022 */     appendTemplateCode(body);
/*      */ 
/*      */     
/* 1025 */     if (nsElem != null) body.append(nsElem);
/*      */     
/* 1027 */     if (nsAttr != null) body.append(nsAttr);
/*      */ 
/*      */     
/* 1030 */     body.append(ilRecurse);
/*      */     
/* 1032 */     body.append(ilText);
/*      */ 
/*      */     
/* 1035 */     mainIL.append(body);
/*      */     
/* 1037 */     mainIL.append(ilLoop);
/*      */     
/* 1039 */     peepHoleOptimization(methodGen);
/* 1040 */     classGen.addMethod(methodGen);
/*      */ 
/*      */     
/* 1043 */     if (this._importLevels != null) {
/* 1044 */       for (Map.Entry<Integer, Integer> entry : this._importLevels.entrySet()) {
/* 1045 */         compileApplyImports(classGen, ((Integer)entry.getValue()).intValue(), ((Integer)entry.getKey()).intValue());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void compileTemplateCalls(ClassGenerator classGen, MethodGenerator methodGen, InstructionHandle next, int min, int max) {
/* 1053 */     for (Template template : this._neededTemplates.keySet()) {
/* 1054 */       int prec = template.getImportPrecedence();
/* 1055 */       if (prec >= min && prec < max) {
/* 1056 */         if (template.hasContents()) {
/* 1057 */           InstructionList til = template.compile(classGen, methodGen);
/* 1058 */           til.append(new GOTO_W(next));
/* 1059 */           this._templateILs.put(template, til);
/* 1060 */           this._templateIHs.put(template, til.getStart());
/*      */           
/*      */           continue;
/*      */         } 
/* 1064 */         this._templateIHs.put(template, next);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void compileApplyImports(ClassGenerator classGen, int min, int max) {
/* 1072 */     XSLTC xsltc = classGen.getParser().getXSLTC();
/* 1073 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 1074 */     Vector<String> names = xsltc.getNamesIndex();
/*      */ 
/*      */     
/* 1077 */     this._namedTemplates = new HashMap<>();
/* 1078 */     this._neededTemplates = new HashMap<>();
/* 1079 */     this._templateIHs = new HashMap<>();
/* 1080 */     this._templateILs = new HashMap<>();
/* 1081 */     this._patternGroups = new Vector[32];
/* 1082 */     this._rootPattern = null;
/*      */ 
/*      */     
/* 1085 */     Vector oldTemplates = this._templates;
/*      */ 
/*      */     
/* 1088 */     this._templates = new Vector();
/* 1089 */     Enumeration<Template> templates = oldTemplates.elements();
/* 1090 */     while (templates.hasMoreElements()) {
/* 1091 */       Template template = templates.nextElement();
/* 1092 */       int prec = template.getImportPrecedence();
/* 1093 */       if (prec >= min && prec < max) addTemplate(template);
/*      */     
/*      */     } 
/*      */     
/* 1097 */     processPatterns(this._keys);
/*      */ 
/*      */     
/* 1100 */     Type[] argTypes = new Type[4];
/*      */     
/* 1102 */     argTypes[0] = Util.getJCRefType("Lcom/sun/org/apache/xalan/internal/xsltc/DOM;");
/* 1103 */     argTypes[1] = Util.getJCRefType("Lcom/sun/org/apache/xml/internal/dtm/DTMAxisIterator;");
/* 1104 */     argTypes[2] = Util.getJCRefType("Lcom/sun/org/apache/xml/internal/serializer/SerializationHandler;");
/* 1105 */     argTypes[3] = Type.INT;
/*      */     
/* 1107 */     String[] argNames = new String[4];
/* 1108 */     argNames[0] = "document";
/* 1109 */     argNames[1] = "iterator";
/* 1110 */     argNames[2] = "handler";
/* 1111 */     argNames[3] = "node";
/*      */     
/* 1113 */     InstructionList mainIL = new InstructionList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1119 */     MethodGenerator methodGen = new MethodGenerator(17, Type.VOID, argTypes, argNames, functionName() + '_' + max, getClassName(), mainIL, classGen.getConstantPool());
/* 1120 */     methodGen.addException("com.sun.org.apache.xalan.internal.xsltc.TransletException");
/*      */ 
/*      */ 
/*      */     
/* 1124 */     LocalVariableGen current = methodGen.addLocalVariable2("current", Type.INT, (InstructionHandle)null);
/*      */ 
/*      */     
/* 1127 */     this._currentIndex = current.getIndex();
/*      */     
/* 1129 */     mainIL.append(new ILOAD(methodGen.getLocalIndex("node")));
/* 1130 */     current.setStart(mainIL.append(new ISTORE(this._currentIndex)));
/*      */ 
/*      */ 
/*      */     
/* 1134 */     InstructionList body = new InstructionList();
/* 1135 */     body.append(NOP);
/*      */ 
/*      */ 
/*      */     
/* 1139 */     InstructionList ilLoop = new InstructionList();
/* 1140 */     ilLoop.append(RETURN);
/* 1141 */     InstructionHandle ihLoop = ilLoop.getStart();
/*      */ 
/*      */ 
/*      */     
/* 1145 */     InstructionList ilRecurse = compileDefaultRecursion(classGen, methodGen, ihLoop);
/* 1146 */     InstructionHandle ihRecurse = ilRecurse.getStart();
/*      */ 
/*      */ 
/*      */     
/* 1150 */     InstructionList ilText = compileDefaultText(classGen, methodGen, ihLoop);
/* 1151 */     InstructionHandle ihText = ilText.getStart();
/*      */ 
/*      */     
/* 1154 */     int[] types = new int[14 + names.size()];
/* 1155 */     for (int i = 0; i < types.length; i++) {
/* 1156 */       types[i] = i;
/*      */     }
/*      */     
/* 1159 */     boolean[] isAttribute = new boolean[types.length];
/* 1160 */     boolean[] isNamespace = new boolean[types.length];
/* 1161 */     for (int j = 0; j < names.size(); j++) {
/* 1162 */       String name = names.elementAt(j);
/* 1163 */       isAttribute[j + 14] = isAttributeName(name);
/* 1164 */       isNamespace[j + 14] = isNamespaceName(name);
/*      */     } 
/*      */ 
/*      */     
/* 1168 */     compileTemplateCalls(classGen, methodGen, ihLoop, min, max);
/*      */ 
/*      */     
/* 1171 */     TestSeq elemTest = this._testSeq[1];
/* 1172 */     InstructionHandle ihElem = ihRecurse;
/* 1173 */     if (elemTest != null) {
/* 1174 */       ihElem = elemTest.compile(classGen, methodGen, ihLoop);
/*      */     }
/*      */ 
/*      */     
/* 1178 */     TestSeq attrTest = this._testSeq[2];
/* 1179 */     InstructionHandle ihAttr = ihLoop;
/* 1180 */     if (attrTest != null) {
/* 1181 */       ihAttr = attrTest.compile(classGen, methodGen, ihAttr);
/*      */     }
/*      */ 
/*      */     
/* 1185 */     InstructionList ilKey = null;
/* 1186 */     if (this._idxTestSeq != null) {
/* 1187 */       ilKey = this._idxTestSeq.getInstructionList();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1192 */     if (this._childNodeTestSeq != null) {
/*      */       
/* 1194 */       double nodePrio = this._childNodeTestSeq.getPriority();
/* 1195 */       int nodePos = this._childNodeTestSeq.getPosition();
/* 1196 */       double elemPrio = -1.7976931348623157E308D;
/* 1197 */       int elemPos = Integer.MIN_VALUE;
/*      */       
/* 1199 */       if (elemTest != null) {
/* 1200 */         elemPrio = elemTest.getPriority();
/* 1201 */         elemPos = elemTest.getPosition();
/*      */       } 
/*      */       
/* 1204 */       if (elemPrio == Double.NaN || elemPrio < nodePrio || (elemPrio == nodePrio && elemPos < nodePos))
/*      */       {
/*      */         
/* 1207 */         ihElem = this._childNodeTestSeq.compile(classGen, methodGen, ihLoop);
/*      */       }
/*      */ 
/*      */       
/* 1211 */       TestSeq textTest = this._testSeq[3];
/* 1212 */       double textPrio = -1.7976931348623157E308D;
/* 1213 */       int textPos = Integer.MIN_VALUE;
/*      */       
/* 1215 */       if (textTest != null) {
/* 1216 */         textPrio = textTest.getPriority();
/* 1217 */         textPos = textTest.getPosition();
/*      */       } 
/*      */       
/* 1220 */       if (textPrio == Double.NaN || textPrio < nodePrio || (textPrio == nodePrio && textPos < nodePos)) {
/*      */ 
/*      */         
/* 1223 */         ihText = this._childNodeTestSeq.compile(classGen, methodGen, ihLoop);
/* 1224 */         this._testSeq[3] = this._childNodeTestSeq;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1229 */     InstructionHandle elemNamespaceHandle = ihElem;
/* 1230 */     InstructionList nsElem = compileNamespaces(classGen, methodGen, isNamespace, isAttribute, false, ihElem);
/*      */ 
/*      */     
/* 1233 */     if (nsElem != null) elemNamespaceHandle = nsElem.getStart();
/*      */ 
/*      */     
/* 1236 */     InstructionList nsAttr = compileNamespaces(classGen, methodGen, isNamespace, isAttribute, true, ihAttr);
/*      */ 
/*      */     
/* 1239 */     InstructionHandle attrNamespaceHandle = ihAttr;
/* 1240 */     if (nsAttr != null) attrNamespaceHandle = nsAttr.getStart();
/*      */ 
/*      */     
/* 1243 */     InstructionHandle[] targets = new InstructionHandle[types.length];
/* 1244 */     for (int k = 14; k < targets.length; k++) {
/* 1245 */       TestSeq testSeq = this._testSeq[k];
/*      */       
/* 1247 */       if (isNamespace[k]) {
/* 1248 */         if (isAttribute[k]) {
/* 1249 */           targets[k] = attrNamespaceHandle;
/*      */         } else {
/* 1251 */           targets[k] = elemNamespaceHandle;
/*      */         }
/*      */       
/* 1254 */       } else if (testSeq != null) {
/* 1255 */         if (isAttribute[k]) {
/* 1256 */           targets[k] = testSeq.compile(classGen, methodGen, attrNamespaceHandle);
/*      */         } else {
/*      */           
/* 1259 */           targets[k] = testSeq.compile(classGen, methodGen, elemNamespaceHandle);
/*      */         } 
/*      */       } else {
/*      */         
/* 1263 */         targets[k] = ihLoop;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1268 */     targets[0] = (this._rootPattern != null) ? 
/* 1269 */       getTemplateInstructionHandle(this._rootPattern.getTemplate()) : ihRecurse;
/*      */ 
/*      */     
/* 1272 */     targets[9] = (this._rootPattern != null) ? 
/* 1273 */       getTemplateInstructionHandle(this._rootPattern.getTemplate()) : ihRecurse;
/*      */ 
/*      */ 
/*      */     
/* 1277 */     targets[3] = (this._testSeq[3] != null) ? this._testSeq[3]
/* 1278 */       .compile(classGen, methodGen, ihText) : ihText;
/*      */ 
/*      */ 
/*      */     
/* 1282 */     targets[13] = ihLoop;
/*      */ 
/*      */     
/* 1285 */     targets[1] = elemNamespaceHandle;
/*      */ 
/*      */     
/* 1288 */     targets[2] = attrNamespaceHandle;
/*      */ 
/*      */     
/* 1291 */     InstructionHandle ihPI = ihLoop;
/* 1292 */     if (this._childNodeTestSeq != null) ihPI = ihElem; 
/* 1293 */     if (this._testSeq[7] != null) {
/* 1294 */       targets[7] = this._testSeq[7]
/*      */         
/* 1296 */         .compile(classGen, methodGen, ihPI);
/*      */     } else {
/*      */       
/* 1299 */       targets[7] = ihPI;
/*      */     } 
/*      */ 
/*      */     
/* 1303 */     InstructionHandle ihComment = ihLoop;
/* 1304 */     if (this._childNodeTestSeq != null) ihComment = ihElem; 
/* 1305 */     targets[8] = (this._testSeq[8] != null) ? this._testSeq[8]
/* 1306 */       .compile(classGen, methodGen, ihComment) : ihComment;
/*      */ 
/*      */ 
/*      */     
/* 1310 */     targets[4] = ihLoop;
/*      */ 
/*      */     
/* 1313 */     targets[11] = ihLoop;
/*      */ 
/*      */     
/* 1316 */     targets[10] = ihLoop;
/*      */ 
/*      */     
/* 1319 */     targets[6] = ihLoop;
/*      */ 
/*      */     
/* 1322 */     targets[5] = ihLoop;
/*      */ 
/*      */     
/* 1325 */     targets[12] = ihLoop;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1330 */     for (int m = 14; m < targets.length; m++) {
/* 1331 */       TestSeq testSeq = this._testSeq[m];
/*      */       
/* 1333 */       if (testSeq == null || isNamespace[m]) {
/* 1334 */         if (isAttribute[m]) {
/* 1335 */           targets[m] = attrNamespaceHandle;
/*      */         } else {
/* 1337 */           targets[m] = elemNamespaceHandle;
/*      */         }
/*      */       
/*      */       }
/* 1341 */       else if (isAttribute[m]) {
/* 1342 */         targets[m] = testSeq.compile(classGen, methodGen, attrNamespaceHandle);
/*      */       } else {
/*      */         
/* 1345 */         targets[m] = testSeq.compile(classGen, methodGen, elemNamespaceHandle);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1350 */     if (ilKey != null) body.insert(ilKey);
/*      */ 
/*      */     
/* 1353 */     int getType = cpg.addInterfaceMethodref("com.sun.org.apache.xalan.internal.xsltc.DOM", "getExpandedTypeID", "(I)I");
/*      */ 
/*      */     
/* 1356 */     body.append(methodGen.loadDOM());
/* 1357 */     body.append(new ILOAD(this._currentIndex));
/* 1358 */     body.append(new INVOKEINTERFACE(getType, 2));
/*      */ 
/*      */     
/* 1361 */     InstructionHandle disp = body.append(new SWITCH(types, targets, ihLoop));
/*      */ 
/*      */     
/* 1364 */     appendTestSequences(body);
/*      */     
/* 1366 */     appendTemplateCode(body);
/*      */ 
/*      */     
/* 1369 */     if (nsElem != null) body.append(nsElem);
/*      */     
/* 1371 */     if (nsAttr != null) body.append(nsAttr);
/*      */ 
/*      */     
/* 1374 */     body.append(ilRecurse);
/*      */     
/* 1376 */     body.append(ilText);
/*      */ 
/*      */     
/* 1379 */     mainIL.append(body);
/*      */ 
/*      */     
/* 1382 */     current.setEnd(body.getEnd());
/*      */ 
/*      */     
/* 1385 */     mainIL.append(ilLoop);
/*      */     
/* 1387 */     peepHoleOptimization(methodGen);
/* 1388 */     classGen.addMethod(methodGen);
/*      */ 
/*      */     
/* 1391 */     this._templates = oldTemplates;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void peepHoleOptimization(MethodGenerator methodGen) {
/* 1398 */     InstructionList il = methodGen.getInstructionList();
/* 1399 */     InstructionFinder find = new InstructionFinder(il);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1407 */     String pattern = "loadinstruction pop";
/*      */     
/* 1409 */     for (Iterator<InstructionHandle[]> iterator3 = find.search(pattern); iterator3.hasNext(); ) {
/* 1410 */       InstructionHandle[] match = iterator3.next();
/*      */       try {
/* 1412 */         if (!match[0].hasTargeters() && !match[1].hasTargeters()) {
/* 1413 */           il.delete(match[0], match[1]);
/*      */         }
/*      */       }
/* 1416 */       catch (TargetLostException targetLostException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1425 */     pattern = "iload iload swap istore";
/* 1426 */     for (Iterator<InstructionHandle[]> iterator2 = find.search(pattern); iterator2.hasNext(); ) {
/* 1427 */       InstructionHandle[] match = iterator2.next();
/*      */       
/*      */       try {
/* 1430 */         ILOAD iload1 = (ILOAD)match[0].getInstruction();
/*      */         
/* 1432 */         ILOAD iload2 = (ILOAD)match[1].getInstruction();
/*      */         
/* 1434 */         ISTORE istore = (ISTORE)match[3].getInstruction();
/*      */         
/* 1436 */         if (!match[1].hasTargeters() && 
/* 1437 */           !match[2].hasTargeters() && 
/* 1438 */           !match[3].hasTargeters() && iload1
/* 1439 */           .getIndex() == iload2.getIndex() && iload2
/* 1440 */           .getIndex() == istore.getIndex())
/*      */         {
/* 1442 */           il.delete(match[1], match[3]);
/*      */         }
/*      */       }
/* 1445 */       catch (TargetLostException targetLostException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1454 */     pattern = "loadinstruction loadinstruction swap";
/* 1455 */     for (Iterator<InstructionHandle[]> iterator1 = find.search(pattern); iterator1.hasNext(); ) {
/* 1456 */       InstructionHandle[] match = iterator1.next();
/*      */       try {
/* 1458 */         if (!match[0].hasTargeters() && 
/* 1459 */           !match[1].hasTargeters() && 
/* 1460 */           !match[2].hasTargeters())
/*      */         {
/* 1462 */           Instruction load_m = match[1].getInstruction();
/* 1463 */           il.insert(match[0], load_m);
/* 1464 */           il.delete(match[1], match[2]);
/*      */         }
/*      */       
/* 1467 */       } catch (TargetLostException targetLostException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1476 */     pattern = "aload aload";
/* 1477 */     for (Iterator<InstructionHandle[]> iter = find.search(pattern); iter.hasNext(); ) {
/* 1478 */       InstructionHandle[] match = iter.next();
/*      */       try {
/* 1480 */         if (!match[1].hasTargeters()) {
/*      */           
/* 1482 */           ALOAD aload1 = (ALOAD)match[0].getInstruction();
/*      */           
/* 1484 */           ALOAD aload2 = (ALOAD)match[1].getInstruction();
/*      */           
/* 1486 */           if (aload1.getIndex() == aload2.getIndex()) {
/* 1487 */             il.insert(match[1], new DUP());
/* 1488 */             il.delete(match[1]);
/*      */           }
/*      */         
/*      */         } 
/* 1492 */       } catch (TargetLostException targetLostException) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public InstructionHandle getTemplateInstructionHandle(Template template) {
/* 1499 */     return this._templateIHs.get(template);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAttributeName(String qname) {
/* 1506 */     int col = qname.lastIndexOf(':') + 1;
/* 1507 */     return (qname.charAt(col) == '@');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isNamespaceName(String qname) {
/* 1515 */     int col = qname.lastIndexOf(':');
/* 1516 */     return (col > -1 && qname.charAt(qname.length() - 1) == '*');
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/Mode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */