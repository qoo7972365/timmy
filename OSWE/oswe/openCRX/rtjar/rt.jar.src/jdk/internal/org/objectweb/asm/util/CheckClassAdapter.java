/*      */ package jdk.internal.org.objectweb.asm.util;
/*      */ 
/*      */ import java.io.FileInputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import jdk.internal.org.objectweb.asm.AnnotationVisitor;
/*      */ import jdk.internal.org.objectweb.asm.Attribute;
/*      */ import jdk.internal.org.objectweb.asm.ClassReader;
/*      */ import jdk.internal.org.objectweb.asm.ClassVisitor;
/*      */ import jdk.internal.org.objectweb.asm.FieldVisitor;
/*      */ import jdk.internal.org.objectweb.asm.Label;
/*      */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*      */ import jdk.internal.org.objectweb.asm.Type;
/*      */ import jdk.internal.org.objectweb.asm.TypePath;
/*      */ import jdk.internal.org.objectweb.asm.tree.ClassNode;
/*      */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*      */ import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
/*      */ import jdk.internal.org.objectweb.asm.tree.analysis.Analyzer;
/*      */ import jdk.internal.org.objectweb.asm.tree.analysis.BasicValue;
/*      */ import jdk.internal.org.objectweb.asm.tree.analysis.Frame;
/*      */ import jdk.internal.org.objectweb.asm.tree.analysis.SimpleVerifier;
/*      */ import jdk.internal.org.objectweb.asm.tree.analysis.Value;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CheckClassAdapter
/*      */   extends ClassVisitor
/*      */ {
/*      */   private int version;
/*      */   private boolean start;
/*      */   private boolean source;
/*      */   private boolean outer;
/*      */   private boolean end;
/*      */   private Map<Label, Integer> labels;
/*      */   private boolean checkDataFlow;
/*      */   
/*      */   public static void main(String[] paramArrayOfString) throws Exception {
/*      */     ClassReader classReader;
/*  206 */     if (paramArrayOfString.length != 1) {
/*  207 */       System.err.println("Verifies the given class.");
/*  208 */       System.err.println("Usage: CheckClassAdapter <fully qualified class name or class file name>");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  213 */     if (paramArrayOfString[0].endsWith(".class")) {
/*  214 */       classReader = new ClassReader(new FileInputStream(paramArrayOfString[0]));
/*      */     } else {
/*  216 */       classReader = new ClassReader(paramArrayOfString[0]);
/*      */     } 
/*      */     
/*  219 */     verify(classReader, false, new PrintWriter(System.err));
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
/*      */   public static void verify(ClassReader paramClassReader, ClassLoader paramClassLoader, boolean paramBoolean, PrintWriter paramPrintWriter) {
/*  240 */     ClassNode classNode = new ClassNode();
/*  241 */     paramClassReader.accept(new CheckClassAdapter(classNode, false), 2);
/*      */ 
/*      */     
/*  244 */     Type type = (classNode.superName == null) ? null : Type.getObjectType(classNode.superName);
/*  245 */     List<MethodNode> list = classNode.methods;
/*      */     
/*  247 */     ArrayList<Type> arrayList = new ArrayList();
/*  248 */     for (Iterator<String> iterator = classNode.interfaces.iterator(); iterator.hasNext();) {
/*  249 */       arrayList.add(Type.getObjectType(iterator.next()));
/*      */     }
/*      */     
/*  252 */     for (byte b = 0; b < list.size(); b++) {
/*  253 */       MethodNode methodNode = list.get(b);
/*      */       
/*  255 */       SimpleVerifier simpleVerifier = new SimpleVerifier(Type.getObjectType(classNode.name), type, arrayList, ((classNode.access & 0x200) != 0));
/*      */       
/*  257 */       Analyzer<Value> analyzer = new Analyzer<>(simpleVerifier);
/*  258 */       if (paramClassLoader != null) {
/*  259 */         simpleVerifier.setClassLoader(paramClassLoader);
/*      */       }
/*      */       try {
/*  262 */         analyzer.analyze(classNode.name, methodNode);
/*  263 */         if (!paramBoolean) {
/*      */           continue;
/*      */         }
/*  266 */       } catch (Exception exception) {
/*  267 */         exception.printStackTrace(paramPrintWriter);
/*      */       } 
/*  269 */       printAnalyzerResult(methodNode, (Analyzer)analyzer, paramPrintWriter); continue;
/*      */     } 
/*  271 */     paramPrintWriter.flush();
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
/*      */   public static void verify(ClassReader paramClassReader, boolean paramBoolean, PrintWriter paramPrintWriter) {
/*  288 */     verify(paramClassReader, null, paramBoolean, paramPrintWriter);
/*      */   }
/*      */ 
/*      */   
/*      */   static void printAnalyzerResult(MethodNode paramMethodNode, Analyzer<BasicValue> paramAnalyzer, PrintWriter paramPrintWriter) {
/*  293 */     Frame[] arrayOfFrame = (Frame[])paramAnalyzer.getFrames();
/*  294 */     Textifier textifier = new Textifier();
/*  295 */     TraceMethodVisitor traceMethodVisitor = new TraceMethodVisitor(textifier);
/*      */     
/*  297 */     paramPrintWriter.println(paramMethodNode.name + paramMethodNode.desc); byte b;
/*  298 */     for (b = 0; b < paramMethodNode.instructions.size(); b++) {
/*  299 */       paramMethodNode.instructions.get(b).accept(traceMethodVisitor);
/*      */       
/*  301 */       StringBuilder stringBuilder = new StringBuilder();
/*  302 */       Frame<BasicValue> frame = arrayOfFrame[b];
/*  303 */       if (frame == null) {
/*  304 */         stringBuilder.append('?');
/*      */       } else {
/*  306 */         byte b1; for (b1 = 0; b1 < frame.getLocals(); b1++) {
/*  307 */           stringBuilder.append(getShortName(((BasicValue)frame.getLocal(b1)).toString()))
/*  308 */             .append(' ');
/*      */         }
/*  310 */         stringBuilder.append(" : ");
/*  311 */         for (b1 = 0; b1 < frame.getStackSize(); b1++) {
/*  312 */           stringBuilder.append(getShortName(((BasicValue)frame.getStack(b1)).toString()))
/*  313 */             .append(' ');
/*      */         }
/*      */       } 
/*  316 */       while (stringBuilder.length() < paramMethodNode.maxStack + paramMethodNode.maxLocals + 1) {
/*  317 */         stringBuilder.append(' ');
/*      */       }
/*  319 */       paramPrintWriter.print(Integer.toString(b + 100000).substring(1));
/*  320 */       paramPrintWriter.print(" " + stringBuilder + " : " + textifier.text.get(textifier.text.size() - 1));
/*      */     } 
/*  322 */     for (b = 0; b < paramMethodNode.tryCatchBlocks.size(); b++) {
/*  323 */       ((TryCatchBlockNode)paramMethodNode.tryCatchBlocks.get(b)).accept(traceMethodVisitor);
/*  324 */       paramPrintWriter.print(" " + textifier.text.get(textifier.text.size() - 1));
/*      */     } 
/*  326 */     paramPrintWriter.println();
/*      */   }
/*      */   
/*      */   private static String getShortName(String paramString) {
/*  330 */     int i = paramString.lastIndexOf('/');
/*  331 */     int j = paramString.length();
/*  332 */     if (paramString.charAt(j - 1) == ';') {
/*  333 */       j--;
/*      */     }
/*  335 */     return (i == -1) ? paramString : paramString.substring(i + 1, j);
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
/*      */   public CheckClassAdapter(ClassVisitor paramClassVisitor) {
/*  347 */     this(paramClassVisitor, true);
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
/*      */   public CheckClassAdapter(ClassVisitor paramClassVisitor, boolean paramBoolean) {
/*  366 */     this(327680, paramClassVisitor, paramBoolean);
/*  367 */     if (getClass() != CheckClassAdapter.class) {
/*  368 */       throw new IllegalStateException();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CheckClassAdapter(int paramInt, ClassVisitor paramClassVisitor, boolean paramBoolean) {
/*  388 */     super(paramInt, paramClassVisitor);
/*  389 */     this.labels = new HashMap<>();
/*  390 */     this.checkDataFlow = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*  401 */     if (this.start) {
/*  402 */       throw new IllegalStateException("visit must be called only once");
/*      */     }
/*  404 */     this.start = true;
/*  405 */     checkState();
/*  406 */     checkAccess(paramInt2, 423473);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  411 */     if (paramString1 == null || !paramString1.endsWith("package-info")) {
/*  412 */       CheckMethodAdapter.checkInternalName(paramString1, "class name");
/*      */     }
/*  414 */     if ("java/lang/Object".equals(paramString1)) {
/*  415 */       if (paramString3 != null) {
/*  416 */         throw new IllegalArgumentException("The super class name of the Object class must be 'null'");
/*      */       }
/*      */     } else {
/*      */       
/*  420 */       CheckMethodAdapter.checkInternalName(paramString3, "super class name");
/*      */     } 
/*  422 */     if (paramString2 != null) {
/*  423 */       checkClassSignature(paramString2);
/*      */     }
/*  425 */     if ((paramInt2 & 0x200) != 0 && 
/*  426 */       !"java/lang/Object".equals(paramString3)) {
/*  427 */       throw new IllegalArgumentException("The super class name of interfaces must be 'java/lang/Object'");
/*      */     }
/*      */ 
/*      */     
/*  431 */     if (paramArrayOfString != null) {
/*  432 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  433 */         CheckMethodAdapter.checkInternalName(paramArrayOfString[b], "interface name at index " + b);
/*      */       }
/*      */     }
/*      */     
/*  437 */     this.version = paramInt1;
/*  438 */     super.visit(paramInt1, paramInt2, paramString1, paramString2, paramString3, paramArrayOfString);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitSource(String paramString1, String paramString2) {
/*  443 */     checkState();
/*  444 */     if (this.source) {
/*  445 */       throw new IllegalStateException("visitSource can be called only once.");
/*      */     }
/*      */     
/*  448 */     this.source = true;
/*  449 */     super.visitSource(paramString1, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitOuterClass(String paramString1, String paramString2, String paramString3) {
/*  455 */     checkState();
/*  456 */     if (this.outer) {
/*  457 */       throw new IllegalStateException("visitOuterClass can be called only once.");
/*      */     }
/*      */     
/*  460 */     this.outer = true;
/*  461 */     if (paramString1 == null) {
/*  462 */       throw new IllegalArgumentException("Illegal outer class owner");
/*      */     }
/*  464 */     if (paramString3 != null) {
/*  465 */       CheckMethodAdapter.checkMethodDesc(paramString3);
/*      */     }
/*  467 */     super.visitOuterClass(paramString1, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInnerClass(String paramString1, String paramString2, String paramString3, int paramInt) {
/*  473 */     checkState();
/*  474 */     CheckMethodAdapter.checkInternalName(paramString1, "class name");
/*  475 */     if (paramString2 != null) {
/*  476 */       CheckMethodAdapter.checkInternalName(paramString2, "outer class name");
/*      */     }
/*  478 */     if (paramString3 != null) {
/*  479 */       byte b = 0;
/*  480 */       while (b < paramString3.length() && 
/*  481 */         Character.isDigit(paramString3.charAt(b))) {
/*  482 */         b++;
/*      */       }
/*  484 */       if (b == 0 || b < paramString3.length()) {
/*  485 */         CheckMethodAdapter.checkIdentifier(paramString3, b, -1, "inner class name");
/*      */       }
/*      */     } 
/*      */     
/*  489 */     checkAccess(paramInt, 30239);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  494 */     super.visitInnerClass(paramString1, paramString2, paramString3, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVisitor visitField(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject) {
/*  500 */     checkState();
/*  501 */     checkAccess(paramInt, 413919);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  506 */     CheckMethodAdapter.checkUnqualifiedName(this.version, paramString1, "field name");
/*  507 */     CheckMethodAdapter.checkDesc(paramString2, false);
/*  508 */     if (paramString3 != null) {
/*  509 */       checkFieldSignature(paramString3);
/*      */     }
/*  511 */     if (paramObject != null) {
/*  512 */       CheckMethodAdapter.checkConstant(paramObject);
/*      */     }
/*      */     
/*  515 */     FieldVisitor fieldVisitor = super.visitField(paramInt, paramString1, paramString2, paramString3, paramObject);
/*  516 */     return new CheckFieldAdapter(fieldVisitor);
/*      */   }
/*      */ 
/*      */   
/*      */   public MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*      */     CheckMethodAdapter checkMethodAdapter;
/*  522 */     checkState();
/*  523 */     checkAccess(paramInt, 400895);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  529 */     if (!"<init>".equals(paramString1) && !"<clinit>".equals(paramString1)) {
/*  530 */       CheckMethodAdapter.checkMethodIdentifier(this.version, paramString1, "method name");
/*      */     }
/*      */     
/*  533 */     CheckMethodAdapter.checkMethodDesc(paramString2);
/*  534 */     if (paramString3 != null) {
/*  535 */       checkMethodSignature(paramString3);
/*      */     }
/*  537 */     if (paramArrayOfString != null) {
/*  538 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  539 */         CheckMethodAdapter.checkInternalName(paramArrayOfString[b], "exception name at index " + b);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*  544 */     if (this.checkDataFlow) {
/*  545 */       checkMethodAdapter = new CheckMethodAdapter(paramInt, paramString1, paramString2, super.visitMethod(paramInt, paramString1, paramString2, paramString3, paramArrayOfString), this.labels);
/*      */     } else {
/*      */       
/*  548 */       checkMethodAdapter = new CheckMethodAdapter(super.visitMethod(paramInt, paramString1, paramString2, paramString3, paramArrayOfString), this.labels);
/*      */     } 
/*      */     
/*  551 */     checkMethodAdapter.version = this.version;
/*  552 */     return checkMethodAdapter;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/*  558 */     checkState();
/*  559 */     CheckMethodAdapter.checkDesc(paramString, false);
/*  560 */     return new CheckAnnotationAdapter(super.visitAnnotation(paramString, paramBoolean));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/*  566 */     checkState();
/*  567 */     int i = paramInt >>> 24;
/*  568 */     if (i != 0 && i != 17 && i != 16)
/*      */     {
/*      */       
/*  571 */       throw new IllegalArgumentException("Invalid type reference sort 0x" + 
/*  572 */           Integer.toHexString(i));
/*      */     }
/*  574 */     checkTypeRefAndPath(paramInt, paramTypePath);
/*  575 */     CheckMethodAdapter.checkDesc(paramString, false);
/*  576 */     return new CheckAnnotationAdapter(super.visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitAttribute(Attribute paramAttribute) {
/*  582 */     checkState();
/*  583 */     if (paramAttribute == null) {
/*  584 */       throw new IllegalArgumentException("Invalid attribute (must not be null)");
/*      */     }
/*      */     
/*  587 */     super.visitAttribute(paramAttribute);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitEnd() {
/*  592 */     checkState();
/*  593 */     this.end = true;
/*  594 */     super.visitEnd();
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
/*      */   private void checkState() {
/*  606 */     if (!this.start) {
/*  607 */       throw new IllegalStateException("Cannot visit member before visit has been called.");
/*      */     }
/*      */     
/*  610 */     if (this.end) {
/*  611 */       throw new IllegalStateException("Cannot visit member after visitEnd has been called.");
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkAccess(int paramInt1, int paramInt2) {
/*  627 */     if ((paramInt1 & (paramInt2 ^ 0xFFFFFFFF)) != 0) {
/*  628 */       throw new IllegalArgumentException("Invalid access flags: " + paramInt1);
/*      */     }
/*      */     
/*  631 */     byte b1 = ((paramInt1 & 0x1) == 0) ? 0 : 1;
/*  632 */     byte b2 = ((paramInt1 & 0x2) == 0) ? 0 : 1;
/*  633 */     byte b3 = ((paramInt1 & 0x4) == 0) ? 0 : 1;
/*  634 */     if (b1 + b2 + b3 > 1) {
/*  635 */       throw new IllegalArgumentException("public private and protected are mutually exclusive: " + paramInt1);
/*      */     }
/*      */ 
/*      */     
/*  639 */     byte b4 = ((paramInt1 & 0x10) == 0) ? 0 : 1;
/*  640 */     byte b5 = ((paramInt1 & 0x400) == 0) ? 0 : 1;
/*  641 */     if (b4 + b5 > 1) {
/*  642 */       throw new IllegalArgumentException("final and abstract are mutually exclusive: " + paramInt1);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkClassSignature(String paramString) {
/*  657 */     int i = 0;
/*  658 */     if (getChar(paramString, 0) == '<') {
/*  659 */       i = checkFormalTypeParameters(paramString, i);
/*      */     }
/*  661 */     i = checkClassTypeSignature(paramString, i);
/*  662 */     while (getChar(paramString, i) == 'L') {
/*  663 */       i = checkClassTypeSignature(paramString, i);
/*      */     }
/*  665 */     if (i != paramString.length()) {
/*  666 */       throw new IllegalArgumentException(paramString + ": error at index " + i);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkMethodSignature(String paramString) {
/*  682 */     int i = 0;
/*  683 */     if (getChar(paramString, 0) == '<') {
/*  684 */       i = checkFormalTypeParameters(paramString, i);
/*      */     }
/*  686 */     i = checkChar('(', paramString, i);
/*  687 */     while ("ZCBSIFJDL[T".indexOf(getChar(paramString, i)) != -1) {
/*  688 */       i = checkTypeSignature(paramString, i);
/*      */     }
/*  690 */     i = checkChar(')', paramString, i);
/*  691 */     if (getChar(paramString, i) == 'V') {
/*  692 */       i++;
/*      */     } else {
/*  694 */       i = checkTypeSignature(paramString, i);
/*      */     } 
/*  696 */     while (getChar(paramString, i) == '^') {
/*  697 */       i++;
/*  698 */       if (getChar(paramString, i) == 'L') {
/*  699 */         i = checkClassTypeSignature(paramString, i); continue;
/*      */       } 
/*  701 */       i = checkTypeVariableSignature(paramString, i);
/*      */     } 
/*      */     
/*  704 */     if (i != paramString.length()) {
/*  705 */       throw new IllegalArgumentException(paramString + ": error at index " + i);
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
/*      */   public static void checkFieldSignature(String paramString) {
/*  717 */     int i = checkFieldTypeSignature(paramString, 0);
/*  718 */     if (i != paramString.length()) {
/*  719 */       throw new IllegalArgumentException(paramString + ": error at index " + i);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkTypeRefAndPath(int paramInt, TypePath paramTypePath) {
/*  735 */     int i = 0;
/*  736 */     switch (paramInt >>> 24) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 22:
/*  740 */         i = -65536;
/*      */         break;
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/*      */       case 64:
/*      */       case 65:
/*      */       case 67:
/*      */       case 68:
/*      */       case 69:
/*      */       case 70:
/*  751 */         i = -16777216;
/*      */         break;
/*      */       case 16:
/*      */       case 17:
/*      */       case 18:
/*      */       case 23:
/*      */       case 66:
/*  758 */         i = -256;
/*      */         break;
/*      */       case 71:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/*      */       case 75:
/*  765 */         i = -16776961;
/*      */         break;
/*      */       default:
/*  768 */         throw new IllegalArgumentException("Invalid type reference sort 0x" + 
/*  769 */             Integer.toHexString(paramInt >>> 24));
/*      */     } 
/*  771 */     if ((paramInt & (i ^ 0xFFFFFFFF)) != 0) {
/*  772 */       throw new IllegalArgumentException("Invalid type reference 0x" + 
/*  773 */           Integer.toHexString(paramInt));
/*      */     }
/*  775 */     if (paramTypePath != null) {
/*  776 */       for (byte b = 0; b < paramTypePath.getLength(); b++) {
/*  777 */         int j = paramTypePath.getStep(b);
/*  778 */         if (j != 0 && j != 1 && j != 3 && j != 2)
/*      */         {
/*      */ 
/*      */           
/*  782 */           throw new IllegalArgumentException("Invalid type path step " + b + " in " + paramTypePath);
/*      */         }
/*      */         
/*  785 */         if (j != 3 && paramTypePath
/*  786 */           .getStepArgument(b) != 0) {
/*  787 */           throw new IllegalArgumentException("Invalid type path step argument for step " + b + " in " + paramTypePath);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int checkFormalTypeParameters(String paramString, int paramInt) {
/*  808 */     paramInt = checkChar('<', paramString, paramInt);
/*  809 */     paramInt = checkFormalTypeParameter(paramString, paramInt);
/*  810 */     while (getChar(paramString, paramInt) != '>') {
/*  811 */       paramInt = checkFormalTypeParameter(paramString, paramInt);
/*      */     }
/*  813 */     return paramInt + 1;
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
/*      */   private static int checkFormalTypeParameter(String paramString, int paramInt) {
/*  829 */     paramInt = checkIdentifier(paramString, paramInt);
/*  830 */     paramInt = checkChar(':', paramString, paramInt);
/*  831 */     if ("L[T".indexOf(getChar(paramString, paramInt)) != -1) {
/*  832 */       paramInt = checkFieldTypeSignature(paramString, paramInt);
/*      */     }
/*  834 */     while (getChar(paramString, paramInt) == ':') {
/*  835 */       paramInt = checkFieldTypeSignature(paramString, paramInt + 1);
/*      */     }
/*  837 */     return paramInt;
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
/*      */   private static int checkFieldTypeSignature(String paramString, int paramInt) {
/*  856 */     switch (getChar(paramString, paramInt)) {
/*      */       case 'L':
/*  858 */         return checkClassTypeSignature(paramString, paramInt);
/*      */       case '[':
/*  860 */         return checkTypeSignature(paramString, paramInt + 1);
/*      */     } 
/*  862 */     return checkTypeVariableSignature(paramString, paramInt);
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
/*      */   private static int checkClassTypeSignature(String paramString, int paramInt) {
/*  880 */     paramInt = checkChar('L', paramString, paramInt);
/*  881 */     paramInt = checkIdentifier(paramString, paramInt);
/*  882 */     while (getChar(paramString, paramInt) == '/') {
/*  883 */       paramInt = checkIdentifier(paramString, paramInt + 1);
/*      */     }
/*  885 */     if (getChar(paramString, paramInt) == '<') {
/*  886 */       paramInt = checkTypeArguments(paramString, paramInt);
/*      */     }
/*  888 */     while (getChar(paramString, paramInt) == '.') {
/*  889 */       paramInt = checkIdentifier(paramString, paramInt + 1);
/*  890 */       if (getChar(paramString, paramInt) == '<') {
/*  891 */         paramInt = checkTypeArguments(paramString, paramInt);
/*      */       }
/*      */     } 
/*  894 */     return checkChar(';', paramString, paramInt);
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
/*      */   private static int checkTypeArguments(String paramString, int paramInt) {
/*  910 */     paramInt = checkChar('<', paramString, paramInt);
/*  911 */     paramInt = checkTypeArgument(paramString, paramInt);
/*  912 */     while (getChar(paramString, paramInt) != '>') {
/*  913 */       paramInt = checkTypeArgument(paramString, paramInt);
/*      */     }
/*  915 */     return paramInt + 1;
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
/*      */   private static int checkTypeArgument(String paramString, int paramInt) {
/*  931 */     char c = getChar(paramString, paramInt);
/*  932 */     if (c == '*')
/*  933 */       return paramInt + 1; 
/*  934 */     if (c == '+' || c == '-') {
/*  935 */       paramInt++;
/*      */     }
/*  937 */     return checkFieldTypeSignature(paramString, paramInt);
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
/*      */   private static int checkTypeVariableSignature(String paramString, int paramInt) {
/*  954 */     paramInt = checkChar('T', paramString, paramInt);
/*  955 */     paramInt = checkIdentifier(paramString, paramInt);
/*  956 */     return checkChar(';', paramString, paramInt);
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
/*      */   private static int checkTypeSignature(String paramString, int paramInt) {
/*  972 */     switch (getChar(paramString, paramInt)) {
/*      */       case 'B':
/*      */       case 'C':
/*      */       case 'D':
/*      */       case 'F':
/*      */       case 'I':
/*      */       case 'J':
/*      */       case 'S':
/*      */       case 'Z':
/*  981 */         return paramInt + 1;
/*      */     } 
/*  983 */     return checkFieldTypeSignature(paramString, paramInt);
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
/*      */   private static int checkIdentifier(String paramString, int paramInt) {
/*  997 */     if (!Character.isJavaIdentifierStart(getChar(paramString, paramInt))) {
/*  998 */       throw new IllegalArgumentException(paramString + ": identifier expected at index " + paramInt);
/*      */     }
/*      */     
/* 1001 */     paramInt++;
/* 1002 */     while (Character.isJavaIdentifierPart(getChar(paramString, paramInt))) {
/* 1003 */       paramInt++;
/*      */     }
/* 1005 */     return paramInt;
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
/*      */   private static int checkChar(char paramChar, String paramString, int paramInt) {
/* 1018 */     if (getChar(paramString, paramInt) == paramChar) {
/* 1019 */       return paramInt + 1;
/*      */     }
/* 1021 */     throw new IllegalArgumentException(paramString + ": '" + paramChar + "' expected at index " + paramInt);
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
/*      */   private static char getChar(String paramString, int paramInt) {
/* 1036 */     return (paramInt < paramString.length()) ? paramString.charAt(paramInt) : Character.MIN_VALUE;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/CheckClassAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */