/*      */ package jdk.internal.org.objectweb.asm.util;
/*      */ 
/*      */ import java.io.FileInputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import jdk.internal.org.objectweb.asm.Attribute;
/*      */ import jdk.internal.org.objectweb.asm.ClassReader;
/*      */ import jdk.internal.org.objectweb.asm.Handle;
/*      */ import jdk.internal.org.objectweb.asm.Label;
/*      */ import jdk.internal.org.objectweb.asm.Type;
/*      */ import jdk.internal.org.objectweb.asm.TypePath;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ASMifier
/*      */   extends Printer
/*      */ {
/*      */   protected final String name;
/*      */   protected final int id;
/*      */   protected Map<Label, String> labelNames;
/*      */   private static final int ACCESS_CLASS = 262144;
/*      */   private static final int ACCESS_FIELD = 524288;
/*      */   private static final int ACCESS_INNER = 1048576;
/*      */   
/*      */   public ASMifier() {
/*  121 */     this(327680, "cw", 0);
/*  122 */     if (getClass() != ASMifier.class) {
/*  123 */       throw new IllegalStateException();
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
/*      */   protected ASMifier(int paramInt1, String paramString, int paramInt2) {
/*  140 */     super(paramInt1);
/*  141 */     this.name = paramString;
/*  142 */     this.id = paramInt2;
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
/*      */   public static void main(String[] paramArrayOfString) throws Exception {
/*      */     ClassReader classReader;
/*  158 */     boolean bool1 = false;
/*  159 */     byte b = 2;
/*      */     
/*  161 */     boolean bool2 = true;
/*  162 */     if (paramArrayOfString.length < 1 || paramArrayOfString.length > 2) {
/*  163 */       bool2 = false;
/*      */     }
/*  165 */     if (bool2 && "-debug".equals(paramArrayOfString[0])) {
/*  166 */       bool1 = true;
/*  167 */       b = 0;
/*  168 */       if (paramArrayOfString.length != 2) {
/*  169 */         bool2 = false;
/*      */       }
/*      */     } 
/*  172 */     if (!bool2) {
/*  173 */       System.err
/*  174 */         .println("Prints the ASM code to generate the given class.");
/*  175 */       System.err.println("Usage: ASMifier [-debug] <fully qualified class name or class file name>");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  180 */     if (paramArrayOfString[bool1].endsWith(".class") || paramArrayOfString[bool1].indexOf('\\') > -1 || paramArrayOfString[bool1]
/*  181 */       .indexOf('/') > -1) {
/*  182 */       classReader = new ClassReader(new FileInputStream(paramArrayOfString[bool1]));
/*      */     } else {
/*  184 */       classReader = new ClassReader(paramArrayOfString[bool1]);
/*      */     } 
/*  186 */     classReader.accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(System.out)), b);
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
/*      */   public void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*      */     String str;
/*  199 */     int i = paramString1.lastIndexOf('/');
/*  200 */     if (i == -1) {
/*  201 */       str = paramString1;
/*      */     } else {
/*  203 */       this.text.add("package asm." + paramString1.substring(0, i).replace('/', '.') + ";\n");
/*      */       
/*  205 */       str = paramString1.substring(i + 1);
/*      */     } 
/*  207 */     this.text.add("import java.util.*;\n");
/*  208 */     this.text.add("import jdk.internal.org.objectweb.asm.*;\n");
/*  209 */     this.text.add("public class " + str + "Dump implements Opcodes {\n\n");
/*  210 */     this.text.add("public static byte[] dump () throws Exception {\n\n");
/*  211 */     this.text.add("ClassWriter cw = new ClassWriter(0);\n");
/*  212 */     this.text.add("FieldVisitor fv;\n");
/*  213 */     this.text.add("MethodVisitor mv;\n");
/*  214 */     this.text.add("AnnotationVisitor av0;\n\n");
/*      */     
/*  216 */     this.buf.setLength(0);
/*  217 */     this.buf.append("cw.visit(");
/*  218 */     switch (paramInt1) {
/*      */       case 196653:
/*  220 */         this.buf.append("V1_1");
/*      */         break;
/*      */       case 46:
/*  223 */         this.buf.append("V1_2");
/*      */         break;
/*      */       case 47:
/*  226 */         this.buf.append("V1_3");
/*      */         break;
/*      */       case 48:
/*  229 */         this.buf.append("V1_4");
/*      */         break;
/*      */       case 49:
/*  232 */         this.buf.append("V1_5");
/*      */         break;
/*      */       case 50:
/*  235 */         this.buf.append("V1_6");
/*      */         break;
/*      */       case 51:
/*  238 */         this.buf.append("V1_7");
/*      */         break;
/*      */       default:
/*  241 */         this.buf.append(paramInt1);
/*      */         break;
/*      */     } 
/*  244 */     this.buf.append(", ");
/*  245 */     appendAccess(paramInt2 | 0x40000);
/*  246 */     this.buf.append(", ");
/*  247 */     appendConstant(paramString1);
/*  248 */     this.buf.append(", ");
/*  249 */     appendConstant(paramString2);
/*  250 */     this.buf.append(", ");
/*  251 */     appendConstant(paramString3);
/*  252 */     this.buf.append(", ");
/*  253 */     if (paramArrayOfString != null && paramArrayOfString.length > 0) {
/*  254 */       this.buf.append("new String[] {");
/*  255 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  256 */         this.buf.append((b == 0) ? " " : ", ");
/*  257 */         appendConstant(paramArrayOfString[b]);
/*      */       } 
/*  259 */       this.buf.append(" }");
/*      */     } else {
/*  261 */       this.buf.append("null");
/*      */     } 
/*  263 */     this.buf.append(");\n\n");
/*  264 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitSource(String paramString1, String paramString2) {
/*  269 */     this.buf.setLength(0);
/*  270 */     this.buf.append("cw.visitSource(");
/*  271 */     appendConstant(paramString1);
/*  272 */     this.buf.append(", ");
/*  273 */     appendConstant(paramString2);
/*  274 */     this.buf.append(");\n\n");
/*  275 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitOuterClass(String paramString1, String paramString2, String paramString3) {
/*  281 */     this.buf.setLength(0);
/*  282 */     this.buf.append("cw.visitOuterClass(");
/*  283 */     appendConstant(paramString1);
/*  284 */     this.buf.append(", ");
/*  285 */     appendConstant(paramString2);
/*  286 */     this.buf.append(", ");
/*  287 */     appendConstant(paramString3);
/*  288 */     this.buf.append(");\n\n");
/*  289 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitClassAnnotation(String paramString, boolean paramBoolean) {
/*  295 */     return visitAnnotation(paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitClassTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/*  301 */     return visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitClassAttribute(Attribute paramAttribute) {
/*  306 */     visitAttribute(paramAttribute);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInnerClass(String paramString1, String paramString2, String paramString3, int paramInt) {
/*  312 */     this.buf.setLength(0);
/*  313 */     this.buf.append("cw.visitInnerClass(");
/*  314 */     appendConstant(paramString1);
/*  315 */     this.buf.append(", ");
/*  316 */     appendConstant(paramString2);
/*  317 */     this.buf.append(", ");
/*  318 */     appendConstant(paramString3);
/*  319 */     this.buf.append(", ");
/*  320 */     appendAccess(paramInt | 0x100000);
/*  321 */     this.buf.append(");\n\n");
/*  322 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitField(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject) {
/*  328 */     this.buf.setLength(0);
/*  329 */     this.buf.append("{\n");
/*  330 */     this.buf.append("fv = cw.visitField(");
/*  331 */     appendAccess(paramInt | 0x80000);
/*  332 */     this.buf.append(", ");
/*  333 */     appendConstant(paramString1);
/*  334 */     this.buf.append(", ");
/*  335 */     appendConstant(paramString2);
/*  336 */     this.buf.append(", ");
/*  337 */     appendConstant(paramString3);
/*  338 */     this.buf.append(", ");
/*  339 */     appendConstant(paramObject);
/*  340 */     this.buf.append(");\n");
/*  341 */     this.text.add(this.buf.toString());
/*  342 */     ASMifier aSMifier = createASMifier("fv", 0);
/*  343 */     this.text.add(aSMifier.getText());
/*  344 */     this.text.add("}\n");
/*  345 */     return aSMifier;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*  351 */     this.buf.setLength(0);
/*  352 */     this.buf.append("{\n");
/*  353 */     this.buf.append("mv = cw.visitMethod(");
/*  354 */     appendAccess(paramInt);
/*  355 */     this.buf.append(", ");
/*  356 */     appendConstant(paramString1);
/*  357 */     this.buf.append(", ");
/*  358 */     appendConstant(paramString2);
/*  359 */     this.buf.append(", ");
/*  360 */     appendConstant(paramString3);
/*  361 */     this.buf.append(", ");
/*  362 */     if (paramArrayOfString != null && paramArrayOfString.length > 0) {
/*  363 */       this.buf.append("new String[] {");
/*  364 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  365 */         this.buf.append((b == 0) ? " " : ", ");
/*  366 */         appendConstant(paramArrayOfString[b]);
/*      */       } 
/*  368 */       this.buf.append(" }");
/*      */     } else {
/*  370 */       this.buf.append("null");
/*      */     } 
/*  372 */     this.buf.append(");\n");
/*  373 */     this.text.add(this.buf.toString());
/*  374 */     ASMifier aSMifier = createASMifier("mv", 0);
/*  375 */     this.text.add(aSMifier.getText());
/*  376 */     this.text.add("}\n");
/*  377 */     return aSMifier;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitClassEnd() {
/*  382 */     this.text.add("cw.visitEnd();\n\n");
/*  383 */     this.text.add("return cw.toByteArray();\n");
/*  384 */     this.text.add("}\n");
/*  385 */     this.text.add("}\n");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visit(String paramString, Object paramObject) {
/*  394 */     this.buf.setLength(0);
/*  395 */     this.buf.append("av").append(this.id).append(".visit(");
/*  396 */     appendConstant(this.buf, paramString);
/*  397 */     this.buf.append(", ");
/*  398 */     appendConstant(this.buf, paramObject);
/*  399 */     this.buf.append(");\n");
/*  400 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitEnum(String paramString1, String paramString2, String paramString3) {
/*  406 */     this.buf.setLength(0);
/*  407 */     this.buf.append("av").append(this.id).append(".visitEnum(");
/*  408 */     appendConstant(this.buf, paramString1);
/*  409 */     this.buf.append(", ");
/*  410 */     appendConstant(this.buf, paramString2);
/*  411 */     this.buf.append(", ");
/*  412 */     appendConstant(this.buf, paramString3);
/*  413 */     this.buf.append(");\n");
/*  414 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public ASMifier visitAnnotation(String paramString1, String paramString2) {
/*  419 */     this.buf.setLength(0);
/*  420 */     this.buf.append("{\n");
/*  421 */     this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
/*  422 */     this.buf.append(this.id).append(".visitAnnotation(");
/*  423 */     appendConstant(this.buf, paramString1);
/*  424 */     this.buf.append(", ");
/*  425 */     appendConstant(this.buf, paramString2);
/*  426 */     this.buf.append(");\n");
/*  427 */     this.text.add(this.buf.toString());
/*  428 */     ASMifier aSMifier = createASMifier("av", this.id + 1);
/*  429 */     this.text.add(aSMifier.getText());
/*  430 */     this.text.add("}\n");
/*  431 */     return aSMifier;
/*      */   }
/*      */ 
/*      */   
/*      */   public ASMifier visitArray(String paramString) {
/*  436 */     this.buf.setLength(0);
/*  437 */     this.buf.append("{\n");
/*  438 */     this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
/*  439 */     this.buf.append(this.id).append(".visitArray(");
/*  440 */     appendConstant(this.buf, paramString);
/*  441 */     this.buf.append(");\n");
/*  442 */     this.text.add(this.buf.toString());
/*  443 */     ASMifier aSMifier = createASMifier("av", this.id + 1);
/*  444 */     this.text.add(aSMifier.getText());
/*  445 */     this.text.add("}\n");
/*  446 */     return aSMifier;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitAnnotationEnd() {
/*  451 */     this.buf.setLength(0);
/*  452 */     this.buf.append("av").append(this.id).append(".visitEnd();\n");
/*  453 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitFieldAnnotation(String paramString, boolean paramBoolean) {
/*  463 */     return visitAnnotation(paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitFieldTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/*  469 */     return visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitFieldAttribute(Attribute paramAttribute) {
/*  474 */     visitAttribute(paramAttribute);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitFieldEnd() {
/*  479 */     this.buf.setLength(0);
/*  480 */     this.buf.append(this.name).append(".visitEnd();\n");
/*  481 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitParameter(String paramString, int paramInt) {
/*  490 */     this.buf.setLength(0);
/*  491 */     this.buf.append(this.name).append(".visitParameter(");
/*  492 */     appendString(this.buf, paramString);
/*  493 */     this.buf.append(", ");
/*  494 */     appendAccess(paramInt);
/*  495 */     this.text.add(this.buf.append(");\n").toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public ASMifier visitAnnotationDefault() {
/*  500 */     this.buf.setLength(0);
/*  501 */     this.buf.append("{\n").append("av0 = ").append(this.name)
/*  502 */       .append(".visitAnnotationDefault();\n");
/*  503 */     this.text.add(this.buf.toString());
/*  504 */     ASMifier aSMifier = createASMifier("av", 0);
/*  505 */     this.text.add(aSMifier.getText());
/*  506 */     this.text.add("}\n");
/*  507 */     return aSMifier;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitMethodAnnotation(String paramString, boolean paramBoolean) {
/*  513 */     return visitAnnotation(paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitMethodTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/*  519 */     return visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitParameterAnnotation(int paramInt, String paramString, boolean paramBoolean) {
/*  525 */     this.buf.setLength(0);
/*  526 */     this.buf.append("{\n").append("av0 = ").append(this.name)
/*  527 */       .append(".visitParameterAnnotation(").append(paramInt)
/*  528 */       .append(", ");
/*  529 */     appendConstant(paramString);
/*  530 */     this.buf.append(", ").append(paramBoolean).append(");\n");
/*  531 */     this.text.add(this.buf.toString());
/*  532 */     ASMifier aSMifier = createASMifier("av", 0);
/*  533 */     this.text.add(aSMifier.getText());
/*  534 */     this.text.add("}\n");
/*  535 */     return aSMifier;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMethodAttribute(Attribute paramAttribute) {
/*  540 */     visitAttribute(paramAttribute);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitCode() {
/*  545 */     this.text.add(this.name + ".visitCode();\n");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFrame(int paramInt1, int paramInt2, Object[] paramArrayOfObject1, int paramInt3, Object[] paramArrayOfObject2) {
/*  551 */     this.buf.setLength(0);
/*  552 */     switch (paramInt1) {
/*      */       case -1:
/*      */       case 0:
/*  555 */         declareFrameTypes(paramInt2, paramArrayOfObject1);
/*  556 */         declareFrameTypes(paramInt3, paramArrayOfObject2);
/*  557 */         if (paramInt1 == -1) {
/*  558 */           this.buf.append(this.name).append(".visitFrame(Opcodes.F_NEW, ");
/*      */         } else {
/*  560 */           this.buf.append(this.name).append(".visitFrame(Opcodes.F_FULL, ");
/*      */         } 
/*  562 */         this.buf.append(paramInt2).append(", new Object[] {");
/*  563 */         appendFrameTypes(paramInt2, paramArrayOfObject1);
/*  564 */         this.buf.append("}, ").append(paramInt3).append(", new Object[] {");
/*  565 */         appendFrameTypes(paramInt3, paramArrayOfObject2);
/*  566 */         this.buf.append('}');
/*      */         break;
/*      */       case 1:
/*  569 */         declareFrameTypes(paramInt2, paramArrayOfObject1);
/*  570 */         this.buf.append(this.name).append(".visitFrame(Opcodes.F_APPEND,")
/*  571 */           .append(paramInt2).append(", new Object[] {");
/*  572 */         appendFrameTypes(paramInt2, paramArrayOfObject1);
/*  573 */         this.buf.append("}, 0, null");
/*      */         break;
/*      */       case 2:
/*  576 */         this.buf.append(this.name).append(".visitFrame(Opcodes.F_CHOP,")
/*  577 */           .append(paramInt2).append(", null, 0, null");
/*      */         break;
/*      */       case 3:
/*  580 */         this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME, 0, null, 0, null");
/*      */         break;
/*      */       
/*      */       case 4:
/*  584 */         declareFrameTypes(1, paramArrayOfObject2);
/*  585 */         this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {");
/*      */         
/*  587 */         appendFrameTypes(1, paramArrayOfObject2);
/*  588 */         this.buf.append('}');
/*      */         break;
/*      */     } 
/*  591 */     this.buf.append(");\n");
/*  592 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitInsn(int paramInt) {
/*  597 */     this.buf.setLength(0);
/*  598 */     this.buf.append(this.name).append(".visitInsn(").append(OPCODES[paramInt])
/*  599 */       .append(");\n");
/*  600 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIntInsn(int paramInt1, int paramInt2) {
/*  605 */     this.buf.setLength(0);
/*  606 */     this.buf.append(this.name)
/*  607 */       .append(".visitIntInsn(")
/*  608 */       .append(OPCODES[paramInt1])
/*  609 */       .append(", ")
/*  610 */       .append((paramInt1 == 188) ? TYPES[paramInt2] : 
/*  611 */         Integer.toString(paramInt2)).append(");\n");
/*  612 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitVarInsn(int paramInt1, int paramInt2) {
/*  617 */     this.buf.setLength(0);
/*  618 */     this.buf.append(this.name).append(".visitVarInsn(").append(OPCODES[paramInt1])
/*  619 */       .append(", ").append(paramInt2).append(");\n");
/*  620 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitTypeInsn(int paramInt, String paramString) {
/*  625 */     this.buf.setLength(0);
/*  626 */     this.buf.append(this.name).append(".visitTypeInsn(").append(OPCODES[paramInt])
/*  627 */       .append(", ");
/*  628 */     appendConstant(paramString);
/*  629 */     this.buf.append(");\n");
/*  630 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/*  636 */     this.buf.setLength(0);
/*  637 */     this.buf.append(this.name).append(".visitFieldInsn(")
/*  638 */       .append(OPCODES[paramInt]).append(", ");
/*  639 */     appendConstant(paramString1);
/*  640 */     this.buf.append(", ");
/*  641 */     appendConstant(paramString2);
/*  642 */     this.buf.append(", ");
/*  643 */     appendConstant(paramString3);
/*  644 */     this.buf.append(");\n");
/*  645 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/*  652 */     if (this.api >= 327680) {
/*  653 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
/*      */       return;
/*      */     } 
/*  656 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, (paramInt == 185));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/*  663 */     if (this.api < 327680) {
/*  664 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*      */       return;
/*      */     } 
/*  667 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   private void doVisitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/*  672 */     this.buf.setLength(0);
/*  673 */     this.buf.append(this.name).append(".visitMethodInsn(")
/*  674 */       .append(OPCODES[paramInt]).append(", ");
/*  675 */     appendConstant(paramString1);
/*  676 */     this.buf.append(", ");
/*  677 */     appendConstant(paramString2);
/*  678 */     this.buf.append(", ");
/*  679 */     appendConstant(paramString3);
/*  680 */     this.buf.append(", ");
/*  681 */     this.buf.append(paramBoolean ? "true" : "false");
/*  682 */     this.buf.append(");\n");
/*  683 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/*  689 */     this.buf.setLength(0);
/*  690 */     this.buf.append(this.name).append(".visitInvokeDynamicInsn(");
/*  691 */     appendConstant(paramString1);
/*  692 */     this.buf.append(", ");
/*  693 */     appendConstant(paramString2);
/*  694 */     this.buf.append(", ");
/*  695 */     appendConstant(paramHandle);
/*  696 */     this.buf.append(", new Object[]{");
/*  697 */     for (byte b = 0; b < paramVarArgs.length; b++) {
/*  698 */       appendConstant(paramVarArgs[b]);
/*  699 */       if (b != paramVarArgs.length - 1) {
/*  700 */         this.buf.append(", ");
/*      */       }
/*      */     } 
/*  703 */     this.buf.append("});\n");
/*  704 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitJumpInsn(int paramInt, Label paramLabel) {
/*  709 */     this.buf.setLength(0);
/*  710 */     declareLabel(paramLabel);
/*  711 */     this.buf.append(this.name).append(".visitJumpInsn(").append(OPCODES[paramInt])
/*  712 */       .append(", ");
/*  713 */     appendLabel(paramLabel);
/*  714 */     this.buf.append(");\n");
/*  715 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLabel(Label paramLabel) {
/*  720 */     this.buf.setLength(0);
/*  721 */     declareLabel(paramLabel);
/*  722 */     this.buf.append(this.name).append(".visitLabel(");
/*  723 */     appendLabel(paramLabel);
/*  724 */     this.buf.append(");\n");
/*  725 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLdcInsn(Object paramObject) {
/*  730 */     this.buf.setLength(0);
/*  731 */     this.buf.append(this.name).append(".visitLdcInsn(");
/*  732 */     appendConstant(paramObject);
/*  733 */     this.buf.append(");\n");
/*  734 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIincInsn(int paramInt1, int paramInt2) {
/*  739 */     this.buf.setLength(0);
/*  740 */     this.buf.append(this.name).append(".visitIincInsn(").append(paramInt1).append(", ")
/*  741 */       .append(paramInt2).append(");\n");
/*  742 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTableSwitchInsn(int paramInt1, int paramInt2, Label paramLabel, Label... paramVarArgs) {
/*  748 */     this.buf.setLength(0); byte b;
/*  749 */     for (b = 0; b < paramVarArgs.length; b++) {
/*  750 */       declareLabel(paramVarArgs[b]);
/*      */     }
/*  752 */     declareLabel(paramLabel);
/*      */     
/*  754 */     this.buf.append(this.name).append(".visitTableSwitchInsn(").append(paramInt1)
/*  755 */       .append(", ").append(paramInt2).append(", ");
/*  756 */     appendLabel(paramLabel);
/*  757 */     this.buf.append(", new Label[] {");
/*  758 */     for (b = 0; b < paramVarArgs.length; b++) {
/*  759 */       this.buf.append((b == 0) ? " " : ", ");
/*  760 */       appendLabel(paramVarArgs[b]);
/*      */     } 
/*  762 */     this.buf.append(" });\n");
/*  763 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLookupSwitchInsn(Label paramLabel, int[] paramArrayOfint, Label[] paramArrayOfLabel) {
/*  769 */     this.buf.setLength(0); byte b;
/*  770 */     for (b = 0; b < paramArrayOfLabel.length; b++) {
/*  771 */       declareLabel(paramArrayOfLabel[b]);
/*      */     }
/*  773 */     declareLabel(paramLabel);
/*      */     
/*  775 */     this.buf.append(this.name).append(".visitLookupSwitchInsn(");
/*  776 */     appendLabel(paramLabel);
/*  777 */     this.buf.append(", new int[] {");
/*  778 */     for (b = 0; b < paramArrayOfint.length; b++) {
/*  779 */       this.buf.append((b == 0) ? " " : ", ").append(paramArrayOfint[b]);
/*      */     }
/*  781 */     this.buf.append(" }, new Label[] {");
/*  782 */     for (b = 0; b < paramArrayOfLabel.length; b++) {
/*  783 */       this.buf.append((b == 0) ? " " : ", ");
/*  784 */       appendLabel(paramArrayOfLabel[b]);
/*      */     } 
/*  786 */     this.buf.append(" });\n");
/*  787 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMultiANewArrayInsn(String paramString, int paramInt) {
/*  792 */     this.buf.setLength(0);
/*  793 */     this.buf.append(this.name).append(".visitMultiANewArrayInsn(");
/*  794 */     appendConstant(paramString);
/*  795 */     this.buf.append(", ").append(paramInt).append(");\n");
/*  796 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitInsnAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/*  802 */     return visitTypeAnnotation("visitInsnAnnotation", paramInt, paramTypePath, paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTryCatchBlock(Label paramLabel1, Label paramLabel2, Label paramLabel3, String paramString) {
/*  809 */     this.buf.setLength(0);
/*  810 */     declareLabel(paramLabel1);
/*  811 */     declareLabel(paramLabel2);
/*  812 */     declareLabel(paramLabel3);
/*  813 */     this.buf.append(this.name).append(".visitTryCatchBlock(");
/*  814 */     appendLabel(paramLabel1);
/*  815 */     this.buf.append(", ");
/*  816 */     appendLabel(paramLabel2);
/*  817 */     this.buf.append(", ");
/*  818 */     appendLabel(paramLabel3);
/*  819 */     this.buf.append(", ");
/*  820 */     appendConstant(paramString);
/*  821 */     this.buf.append(");\n");
/*  822 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitTryCatchAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/*  828 */     return visitTypeAnnotation("visitTryCatchAnnotation", paramInt, paramTypePath, paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLocalVariable(String paramString1, String paramString2, String paramString3, Label paramLabel1, Label paramLabel2, int paramInt) {
/*  836 */     this.buf.setLength(0);
/*  837 */     this.buf.append(this.name).append(".visitLocalVariable(");
/*  838 */     appendConstant(paramString1);
/*  839 */     this.buf.append(", ");
/*  840 */     appendConstant(paramString2);
/*  841 */     this.buf.append(", ");
/*  842 */     appendConstant(paramString3);
/*  843 */     this.buf.append(", ");
/*  844 */     appendLabel(paramLabel1);
/*  845 */     this.buf.append(", ");
/*  846 */     appendLabel(paramLabel2);
/*  847 */     this.buf.append(", ").append(paramInt).append(");\n");
/*  848 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitLocalVariableAnnotation(int paramInt, TypePath paramTypePath, Label[] paramArrayOfLabel1, Label[] paramArrayOfLabel2, int[] paramArrayOfint, String paramString, boolean paramBoolean) {
/*  855 */     this.buf.setLength(0);
/*  856 */     this.buf.append("{\n").append("av0 = ").append(this.name)
/*  857 */       .append(".visitLocalVariableAnnotation(");
/*  858 */     this.buf.append(paramInt);
/*  859 */     this.buf.append(", TypePath.fromString(\"").append(paramTypePath).append("\"), ");
/*  860 */     this.buf.append("new Label[] {"); byte b;
/*  861 */     for (b = 0; b < paramArrayOfLabel1.length; b++) {
/*  862 */       this.buf.append((b == 0) ? " " : ", ");
/*  863 */       appendLabel(paramArrayOfLabel1[b]);
/*      */     } 
/*  865 */     this.buf.append(" }, new Label[] {");
/*  866 */     for (b = 0; b < paramArrayOfLabel2.length; b++) {
/*  867 */       this.buf.append((b == 0) ? " " : ", ");
/*  868 */       appendLabel(paramArrayOfLabel2[b]);
/*      */     } 
/*  870 */     this.buf.append(" }, new int[] {");
/*  871 */     for (b = 0; b < paramArrayOfint.length; b++) {
/*  872 */       this.buf.append((b == 0) ? " " : ", ").append(paramArrayOfint[b]);
/*      */     }
/*  874 */     this.buf.append(" }, ");
/*  875 */     appendConstant(paramString);
/*  876 */     this.buf.append(", ").append(paramBoolean).append(");\n");
/*  877 */     this.text.add(this.buf.toString());
/*  878 */     ASMifier aSMifier = createASMifier("av", 0);
/*  879 */     this.text.add(aSMifier.getText());
/*  880 */     this.text.add("}\n");
/*  881 */     return aSMifier;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLineNumber(int paramInt, Label paramLabel) {
/*  886 */     this.buf.setLength(0);
/*  887 */     this.buf.append(this.name).append(".visitLineNumber(").append(paramInt).append(", ");
/*  888 */     appendLabel(paramLabel);
/*  889 */     this.buf.append(");\n");
/*  890 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMaxs(int paramInt1, int paramInt2) {
/*  895 */     this.buf.setLength(0);
/*  896 */     this.buf.append(this.name).append(".visitMaxs(").append(paramInt1).append(", ")
/*  897 */       .append(paramInt2).append(");\n");
/*  898 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMethodEnd() {
/*  903 */     this.buf.setLength(0);
/*  904 */     this.buf.append(this.name).append(".visitEnd();\n");
/*  905 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitAnnotation(String paramString, boolean paramBoolean) {
/*  913 */     this.buf.setLength(0);
/*  914 */     this.buf.append("{\n").append("av0 = ").append(this.name)
/*  915 */       .append(".visitAnnotation(");
/*  916 */     appendConstant(paramString);
/*  917 */     this.buf.append(", ").append(paramBoolean).append(");\n");
/*  918 */     this.text.add(this.buf.toString());
/*  919 */     ASMifier aSMifier = createASMifier("av", 0);
/*  920 */     this.text.add(aSMifier.getText());
/*  921 */     this.text.add("}\n");
/*  922 */     return aSMifier;
/*      */   }
/*      */ 
/*      */   
/*      */   public ASMifier visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/*  927 */     return visitTypeAnnotation("visitTypeAnnotation", paramInt, paramTypePath, paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ASMifier visitTypeAnnotation(String paramString1, int paramInt, TypePath paramTypePath, String paramString2, boolean paramBoolean) {
/*  933 */     this.buf.setLength(0);
/*  934 */     this.buf.append("{\n").append("av0 = ").append(this.name).append(".")
/*  935 */       .append(paramString1).append("(");
/*  936 */     this.buf.append(paramInt);
/*  937 */     this.buf.append(", TypePath.fromString(\"").append(paramTypePath).append("\"), ");
/*  938 */     appendConstant(paramString2);
/*  939 */     this.buf.append(", ").append(paramBoolean).append(");\n");
/*  940 */     this.text.add(this.buf.toString());
/*  941 */     ASMifier aSMifier = createASMifier("av", 0);
/*  942 */     this.text.add(aSMifier.getText());
/*  943 */     this.text.add("}\n");
/*  944 */     return aSMifier;
/*      */   }
/*      */   
/*      */   public void visitAttribute(Attribute paramAttribute) {
/*  948 */     this.buf.setLength(0);
/*  949 */     this.buf.append("// ATTRIBUTE ").append(paramAttribute.type).append('\n');
/*  950 */     if (paramAttribute instanceof ASMifiable) {
/*  951 */       if (this.labelNames == null) {
/*  952 */         this.labelNames = new HashMap<>();
/*      */       }
/*  954 */       this.buf.append("{\n");
/*  955 */       ((ASMifiable)paramAttribute).asmify(this.buf, "attr", this.labelNames);
/*  956 */       this.buf.append(this.name).append(".visitAttribute(attr);\n");
/*  957 */       this.buf.append("}\n");
/*      */     } 
/*  959 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ASMifier createASMifier(String paramString, int paramInt) {
/*  967 */     return new ASMifier(327680, paramString, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void appendAccess(int paramInt) {
/*  978 */     boolean bool = true;
/*  979 */     if ((paramInt & 0x1) != 0) {
/*  980 */       this.buf.append("ACC_PUBLIC");
/*  981 */       bool = false;
/*      */     } 
/*  983 */     if ((paramInt & 0x2) != 0) {
/*  984 */       this.buf.append("ACC_PRIVATE");
/*  985 */       bool = false;
/*      */     } 
/*  987 */     if ((paramInt & 0x4) != 0) {
/*  988 */       this.buf.append("ACC_PROTECTED");
/*  989 */       bool = false;
/*      */     } 
/*  991 */     if ((paramInt & 0x10) != 0) {
/*  992 */       if (!bool) {
/*  993 */         this.buf.append(" + ");
/*      */       }
/*  995 */       this.buf.append("ACC_FINAL");
/*  996 */       bool = false;
/*      */     } 
/*  998 */     if ((paramInt & 0x8) != 0) {
/*  999 */       if (!bool) {
/* 1000 */         this.buf.append(" + ");
/*      */       }
/* 1002 */       this.buf.append("ACC_STATIC");
/* 1003 */       bool = false;
/*      */     } 
/* 1005 */     if ((paramInt & 0x20) != 0) {
/* 1006 */       if (!bool) {
/* 1007 */         this.buf.append(" + ");
/*      */       }
/* 1009 */       if ((paramInt & 0x40000) == 0) {
/* 1010 */         this.buf.append("ACC_SYNCHRONIZED");
/*      */       } else {
/* 1012 */         this.buf.append("ACC_SUPER");
/*      */       } 
/* 1014 */       bool = false;
/*      */     } 
/* 1016 */     if ((paramInt & 0x40) != 0 && (paramInt & 0x80000) != 0) {
/*      */       
/* 1018 */       if (!bool) {
/* 1019 */         this.buf.append(" + ");
/*      */       }
/* 1021 */       this.buf.append("ACC_VOLATILE");
/* 1022 */       bool = false;
/*      */     } 
/* 1024 */     if ((paramInt & 0x40) != 0 && (paramInt & 0x40000) == 0 && (paramInt & 0x80000) == 0) {
/*      */       
/* 1026 */       if (!bool) {
/* 1027 */         this.buf.append(" + ");
/*      */       }
/* 1029 */       this.buf.append("ACC_BRIDGE");
/* 1030 */       bool = false;
/*      */     } 
/* 1032 */     if ((paramInt & 0x80) != 0 && (paramInt & 0x40000) == 0 && (paramInt & 0x80000) == 0) {
/*      */       
/* 1034 */       if (!bool) {
/* 1035 */         this.buf.append(" + ");
/*      */       }
/* 1037 */       this.buf.append("ACC_VARARGS");
/* 1038 */       bool = false;
/*      */     } 
/* 1040 */     if ((paramInt & 0x80) != 0 && (paramInt & 0x80000) != 0) {
/*      */       
/* 1042 */       if (!bool) {
/* 1043 */         this.buf.append(" + ");
/*      */       }
/* 1045 */       this.buf.append("ACC_TRANSIENT");
/* 1046 */       bool = false;
/*      */     } 
/* 1048 */     if ((paramInt & 0x100) != 0 && (paramInt & 0x40000) == 0 && (paramInt & 0x80000) == 0) {
/*      */       
/* 1050 */       if (!bool) {
/* 1051 */         this.buf.append(" + ");
/*      */       }
/* 1053 */       this.buf.append("ACC_NATIVE");
/* 1054 */       bool = false;
/*      */     } 
/* 1056 */     if ((paramInt & 0x4000) != 0 && ((paramInt & 0x40000) != 0 || (paramInt & 0x80000) != 0 || (paramInt & 0x100000) != 0)) {
/*      */ 
/*      */       
/* 1059 */       if (!bool) {
/* 1060 */         this.buf.append(" + ");
/*      */       }
/* 1062 */       this.buf.append("ACC_ENUM");
/* 1063 */       bool = false;
/*      */     } 
/* 1065 */     if ((paramInt & 0x2000) != 0 && ((paramInt & 0x40000) != 0 || (paramInt & 0x100000) != 0)) {
/*      */       
/* 1067 */       if (!bool) {
/* 1068 */         this.buf.append(" + ");
/*      */       }
/* 1070 */       this.buf.append("ACC_ANNOTATION");
/* 1071 */       bool = false;
/*      */     } 
/* 1073 */     if ((paramInt & 0x400) != 0) {
/* 1074 */       if (!bool) {
/* 1075 */         this.buf.append(" + ");
/*      */       }
/* 1077 */       this.buf.append("ACC_ABSTRACT");
/* 1078 */       bool = false;
/*      */     } 
/* 1080 */     if ((paramInt & 0x200) != 0) {
/* 1081 */       if (!bool) {
/* 1082 */         this.buf.append(" + ");
/*      */       }
/* 1084 */       this.buf.append("ACC_INTERFACE");
/* 1085 */       bool = false;
/*      */     } 
/* 1087 */     if ((paramInt & 0x800) != 0) {
/* 1088 */       if (!bool) {
/* 1089 */         this.buf.append(" + ");
/*      */       }
/* 1091 */       this.buf.append("ACC_STRICT");
/* 1092 */       bool = false;
/*      */     } 
/* 1094 */     if ((paramInt & 0x1000) != 0) {
/* 1095 */       if (!bool) {
/* 1096 */         this.buf.append(" + ");
/*      */       }
/* 1098 */       this.buf.append("ACC_SYNTHETIC");
/* 1099 */       bool = false;
/*      */     } 
/* 1101 */     if ((paramInt & 0x20000) != 0) {
/* 1102 */       if (!bool) {
/* 1103 */         this.buf.append(" + ");
/*      */       }
/* 1105 */       this.buf.append("ACC_DEPRECATED");
/* 1106 */       bool = false;
/*      */     } 
/* 1108 */     if ((paramInt & 0x8000) != 0) {
/* 1109 */       if (!bool) {
/* 1110 */         this.buf.append(" + ");
/*      */       }
/* 1112 */       this.buf.append("ACC_MANDATED");
/* 1113 */       bool = false;
/*      */     } 
/* 1115 */     if (bool) {
/* 1116 */       this.buf.append('0');
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
/*      */   protected void appendConstant(Object paramObject) {
/* 1129 */     appendConstant(this.buf, paramObject);
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
/*      */   static void appendConstant(StringBuffer paramStringBuffer, Object paramObject) {
/* 1143 */     if (paramObject == null) {
/* 1144 */       paramStringBuffer.append("null");
/* 1145 */     } else if (paramObject instanceof String) {
/* 1146 */       appendString(paramStringBuffer, (String)paramObject);
/* 1147 */     } else if (paramObject instanceof Type) {
/* 1148 */       paramStringBuffer.append("Type.getType(\"");
/* 1149 */       paramStringBuffer.append(((Type)paramObject).getDescriptor());
/* 1150 */       paramStringBuffer.append("\")");
/* 1151 */     } else if (paramObject instanceof Handle) {
/* 1152 */       paramStringBuffer.append("new Handle(");
/* 1153 */       Handle handle = (Handle)paramObject;
/* 1154 */       paramStringBuffer.append("Opcodes.").append(HANDLE_TAG[handle.getTag()])
/* 1155 */         .append(", \"");
/* 1156 */       paramStringBuffer.append(handle.getOwner()).append("\", \"");
/* 1157 */       paramStringBuffer.append(handle.getName()).append("\", \"");
/* 1158 */       paramStringBuffer.append(handle.getDesc()).append("\")");
/* 1159 */     } else if (paramObject instanceof Byte) {
/* 1160 */       paramStringBuffer.append("new Byte((byte)").append(paramObject).append(')');
/* 1161 */     } else if (paramObject instanceof Boolean) {
/* 1162 */       paramStringBuffer.append(((Boolean)paramObject).booleanValue() ? "Boolean.TRUE" : "Boolean.FALSE");
/*      */     }
/* 1164 */     else if (paramObject instanceof Short) {
/* 1165 */       paramStringBuffer.append("new Short((short)").append(paramObject).append(')');
/* 1166 */     } else if (paramObject instanceof Character) {
/* 1167 */       char c = ((Character)paramObject).charValue();
/* 1168 */       paramStringBuffer.append("new Character((char)").append(c).append(')');
/* 1169 */     } else if (paramObject instanceof Integer) {
/* 1170 */       paramStringBuffer.append("new Integer(").append(paramObject).append(')');
/* 1171 */     } else if (paramObject instanceof Float) {
/* 1172 */       paramStringBuffer.append("new Float(\"").append(paramObject).append("\")");
/* 1173 */     } else if (paramObject instanceof Long) {
/* 1174 */       paramStringBuffer.append("new Long(").append(paramObject).append("L)");
/* 1175 */     } else if (paramObject instanceof Double) {
/* 1176 */       paramStringBuffer.append("new Double(\"").append(paramObject).append("\")");
/* 1177 */     } else if (paramObject instanceof byte[]) {
/* 1178 */       byte[] arrayOfByte = (byte[])paramObject;
/* 1179 */       paramStringBuffer.append("new byte[] {");
/* 1180 */       for (byte b = 0; b < arrayOfByte.length; b++) {
/* 1181 */         paramStringBuffer.append((b == 0) ? "" : ",").append(arrayOfByte[b]);
/*      */       }
/* 1183 */       paramStringBuffer.append('}');
/* 1184 */     } else if (paramObject instanceof boolean[]) {
/* 1185 */       boolean[] arrayOfBoolean = (boolean[])paramObject;
/* 1186 */       paramStringBuffer.append("new boolean[] {");
/* 1187 */       for (byte b = 0; b < arrayOfBoolean.length; b++) {
/* 1188 */         paramStringBuffer.append((b == 0) ? "" : ",").append(arrayOfBoolean[b]);
/*      */       }
/* 1190 */       paramStringBuffer.append('}');
/* 1191 */     } else if (paramObject instanceof short[]) {
/* 1192 */       short[] arrayOfShort = (short[])paramObject;
/* 1193 */       paramStringBuffer.append("new short[] {");
/* 1194 */       for (byte b = 0; b < arrayOfShort.length; b++) {
/* 1195 */         paramStringBuffer.append((b == 0) ? "" : ",").append("(short)").append(arrayOfShort[b]);
/*      */       }
/* 1197 */       paramStringBuffer.append('}');
/* 1198 */     } else if (paramObject instanceof char[]) {
/* 1199 */       char[] arrayOfChar = (char[])paramObject;
/* 1200 */       paramStringBuffer.append("new char[] {");
/* 1201 */       for (byte b = 0; b < arrayOfChar.length; b++) {
/* 1202 */         paramStringBuffer.append((b == 0) ? "" : ",").append("(char)")
/* 1203 */           .append(arrayOfChar[b]);
/*      */       }
/* 1205 */       paramStringBuffer.append('}');
/* 1206 */     } else if (paramObject instanceof int[]) {
/* 1207 */       int[] arrayOfInt = (int[])paramObject;
/* 1208 */       paramStringBuffer.append("new int[] {");
/* 1209 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/* 1210 */         paramStringBuffer.append((b == 0) ? "" : ",").append(arrayOfInt[b]);
/*      */       }
/* 1212 */       paramStringBuffer.append('}');
/* 1213 */     } else if (paramObject instanceof long[]) {
/* 1214 */       long[] arrayOfLong = (long[])paramObject;
/* 1215 */       paramStringBuffer.append("new long[] {");
/* 1216 */       for (byte b = 0; b < arrayOfLong.length; b++) {
/* 1217 */         paramStringBuffer.append((b == 0) ? "" : ",").append(arrayOfLong[b]).append('L');
/*      */       }
/* 1219 */       paramStringBuffer.append('}');
/* 1220 */     } else if (paramObject instanceof float[]) {
/* 1221 */       float[] arrayOfFloat = (float[])paramObject;
/* 1222 */       paramStringBuffer.append("new float[] {");
/* 1223 */       for (byte b = 0; b < arrayOfFloat.length; b++) {
/* 1224 */         paramStringBuffer.append((b == 0) ? "" : ",").append(arrayOfFloat[b]).append('f');
/*      */       }
/* 1226 */       paramStringBuffer.append('}');
/* 1227 */     } else if (paramObject instanceof double[]) {
/* 1228 */       double[] arrayOfDouble = (double[])paramObject;
/* 1229 */       paramStringBuffer.append("new double[] {");
/* 1230 */       for (byte b = 0; b < arrayOfDouble.length; b++) {
/* 1231 */         paramStringBuffer.append((b == 0) ? "" : ",").append(arrayOfDouble[b]).append('d');
/*      */       }
/* 1233 */       paramStringBuffer.append('}');
/*      */     } 
/*      */   }
/*      */   
/*      */   private void declareFrameTypes(int paramInt, Object[] paramArrayOfObject) {
/* 1238 */     for (byte b = 0; b < paramInt; b++) {
/* 1239 */       if (paramArrayOfObject[b] instanceof Label) {
/* 1240 */         declareLabel((Label)paramArrayOfObject[b]);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void appendFrameTypes(int paramInt, Object[] paramArrayOfObject) {
/* 1246 */     for (byte b = 0; b < paramInt; b++) {
/* 1247 */       if (b > 0) {
/* 1248 */         this.buf.append(", ");
/*      */       }
/* 1250 */       if (paramArrayOfObject[b] instanceof String) {
/* 1251 */         appendConstant(paramArrayOfObject[b]);
/* 1252 */       } else if (paramArrayOfObject[b] instanceof Integer) {
/* 1253 */         switch (((Integer)paramArrayOfObject[b]).intValue()) {
/*      */           case 0:
/* 1255 */             this.buf.append("Opcodes.TOP");
/*      */             break;
/*      */           case 1:
/* 1258 */             this.buf.append("Opcodes.INTEGER");
/*      */             break;
/*      */           case 2:
/* 1261 */             this.buf.append("Opcodes.FLOAT");
/*      */             break;
/*      */           case 3:
/* 1264 */             this.buf.append("Opcodes.DOUBLE");
/*      */             break;
/*      */           case 4:
/* 1267 */             this.buf.append("Opcodes.LONG");
/*      */             break;
/*      */           case 5:
/* 1270 */             this.buf.append("Opcodes.NULL");
/*      */             break;
/*      */           case 6:
/* 1273 */             this.buf.append("Opcodes.UNINITIALIZED_THIS");
/*      */             break;
/*      */         } 
/*      */       } else {
/* 1277 */         appendLabel((Label)paramArrayOfObject[b]);
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
/*      */   protected void declareLabel(Label paramLabel) {
/* 1291 */     if (this.labelNames == null) {
/* 1292 */       this.labelNames = new HashMap<>();
/*      */     }
/* 1294 */     String str = this.labelNames.get(paramLabel);
/* 1295 */     if (str == null) {
/* 1296 */       str = "l" + this.labelNames.size();
/* 1297 */       this.labelNames.put(paramLabel, str);
/* 1298 */       this.buf.append("Label ").append(str).append(" = new Label();\n");
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
/*      */   protected void appendLabel(Label paramLabel) {
/* 1311 */     this.buf.append(this.labelNames.get(paramLabel));
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/ASMifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */