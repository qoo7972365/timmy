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
/*      */ import jdk.internal.org.objectweb.asm.TypeReference;
/*      */ import jdk.internal.org.objectweb.asm.signature.SignatureReader;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Textifier
/*      */   extends Printer
/*      */ {
/*      */   public static final int INTERNAL_NAME = 0;
/*      */   public static final int FIELD_DESCRIPTOR = 1;
/*      */   public static final int FIELD_SIGNATURE = 2;
/*      */   public static final int METHOD_DESCRIPTOR = 3;
/*      */   public static final int METHOD_SIGNATURE = 4;
/*      */   public static final int CLASS_SIGNATURE = 5;
/*      */   public static final int TYPE_DECLARATION = 6;
/*      */   public static final int CLASS_DECLARATION = 7;
/*      */   public static final int PARAMETERS_DECLARATION = 8;
/*      */   public static final int HANDLE_DESCRIPTOR = 9;
/*  147 */   protected String tab = "  ";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  152 */   protected String tab2 = "    ";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  157 */   protected String tab3 = "      ";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  162 */   protected String ltab = "   ";
/*      */ 
/*      */ 
/*      */   
/*      */   protected Map<Label, String> labelNames;
/*      */ 
/*      */ 
/*      */   
/*      */   private int access;
/*      */ 
/*      */ 
/*      */   
/*  174 */   private int valueNumber = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier() {
/*  185 */     this(327680);
/*  186 */     if (getClass() != Textifier.class) {
/*  187 */       throw new IllegalStateException();
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
/*      */   protected Textifier(int paramInt) {
/*  199 */     super(paramInt);
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
/*      */   public static void main(String[] paramArrayOfString) throws Exception {
/*      */     ClassReader classReader;
/*  214 */     boolean bool1 = false;
/*  215 */     byte b = 2;
/*      */     
/*  217 */     boolean bool2 = true;
/*  218 */     if (paramArrayOfString.length < 1 || paramArrayOfString.length > 2) {
/*  219 */       bool2 = false;
/*      */     }
/*  221 */     if (bool2 && "-debug".equals(paramArrayOfString[0])) {
/*  222 */       bool1 = true;
/*  223 */       b = 0;
/*  224 */       if (paramArrayOfString.length != 2) {
/*  225 */         bool2 = false;
/*      */       }
/*      */     } 
/*  228 */     if (!bool2) {
/*  229 */       System.err
/*  230 */         .println("Prints a disassembled view of the given class.");
/*  231 */       System.err.println("Usage: Textifier [-debug] <fully qualified class name or class file name>");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  236 */     if (paramArrayOfString[bool1].endsWith(".class") || paramArrayOfString[bool1].indexOf('\\') > -1 || paramArrayOfString[bool1]
/*  237 */       .indexOf('/') > -1) {
/*  238 */       classReader = new ClassReader(new FileInputStream(paramArrayOfString[bool1]));
/*      */     } else {
/*  240 */       classReader = new ClassReader(paramArrayOfString[bool1]);
/*      */     } 
/*  242 */     classReader.accept(new TraceClassVisitor(new PrintWriter(System.out)), b);
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
/*  253 */     this.access = paramInt2;
/*  254 */     int i = paramInt1 & 0xFFFF;
/*  255 */     int j = paramInt1 >>> 16;
/*  256 */     this.buf.setLength(0);
/*  257 */     this.buf.append("// class version ").append(i).append('.').append(j)
/*  258 */       .append(" (").append(paramInt1).append(")\n");
/*  259 */     if ((paramInt2 & 0x20000) != 0) {
/*  260 */       this.buf.append("// DEPRECATED\n");
/*      */     }
/*  262 */     this.buf.append("// access flags 0x")
/*  263 */       .append(Integer.toHexString(paramInt2).toUpperCase()).append('\n');
/*      */     
/*  265 */     appendDescriptor(5, paramString2);
/*  266 */     if (paramString2 != null) {
/*  267 */       TraceSignatureVisitor traceSignatureVisitor = new TraceSignatureVisitor(paramInt2);
/*  268 */       SignatureReader signatureReader = new SignatureReader(paramString2);
/*  269 */       signatureReader.accept(traceSignatureVisitor);
/*  270 */       this.buf.append("// declaration: ").append(paramString1)
/*  271 */         .append(traceSignatureVisitor.getDeclaration()).append('\n');
/*      */     } 
/*      */     
/*  274 */     appendAccess(paramInt2 & 0xFFFFFFDF);
/*  275 */     if ((paramInt2 & 0x2000) != 0) {
/*  276 */       this.buf.append("@interface ");
/*  277 */     } else if ((paramInt2 & 0x200) != 0) {
/*  278 */       this.buf.append("interface ");
/*  279 */     } else if ((paramInt2 & 0x4000) == 0) {
/*  280 */       this.buf.append("class ");
/*      */     } 
/*  282 */     appendDescriptor(0, paramString1);
/*      */     
/*  284 */     if (paramString3 != null && !"java/lang/Object".equals(paramString3)) {
/*  285 */       this.buf.append(" extends ");
/*  286 */       appendDescriptor(0, paramString3);
/*  287 */       this.buf.append(' ');
/*      */     } 
/*  289 */     if (paramArrayOfString != null && paramArrayOfString.length > 0) {
/*  290 */       this.buf.append(" implements ");
/*  291 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  292 */         appendDescriptor(0, paramArrayOfString[b]);
/*  293 */         this.buf.append(' ');
/*      */       } 
/*      */     } 
/*  296 */     this.buf.append(" {\n\n");
/*      */     
/*  298 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitSource(String paramString1, String paramString2) {
/*  303 */     this.buf.setLength(0);
/*  304 */     if (paramString1 != null) {
/*  305 */       this.buf.append(this.tab).append("// compiled from: ").append(paramString1)
/*  306 */         .append('\n');
/*      */     }
/*  308 */     if (paramString2 != null) {
/*  309 */       this.buf.append(this.tab).append("// debug info: ").append(paramString2)
/*  310 */         .append('\n');
/*      */     }
/*  312 */     if (this.buf.length() > 0) {
/*  313 */       this.text.add(this.buf.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitOuterClass(String paramString1, String paramString2, String paramString3) {
/*  320 */     this.buf.setLength(0);
/*  321 */     this.buf.append(this.tab).append("OUTERCLASS ");
/*  322 */     appendDescriptor(0, paramString1);
/*  323 */     this.buf.append(' ');
/*  324 */     if (paramString2 != null) {
/*  325 */       this.buf.append(paramString2).append(' ');
/*      */     }
/*  327 */     appendDescriptor(3, paramString3);
/*  328 */     this.buf.append('\n');
/*  329 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitClassAnnotation(String paramString, boolean paramBoolean) {
/*  335 */     this.text.add("\n");
/*  336 */     return visitAnnotation(paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitClassTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/*  342 */     this.text.add("\n");
/*  343 */     return visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitClassAttribute(Attribute paramAttribute) {
/*  348 */     this.text.add("\n");
/*  349 */     visitAttribute(paramAttribute);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInnerClass(String paramString1, String paramString2, String paramString3, int paramInt) {
/*  355 */     this.buf.setLength(0);
/*  356 */     this.buf.append(this.tab).append("// access flags 0x");
/*  357 */     this.buf.append(
/*  358 */         Integer.toHexString(paramInt & 0xFFFFFFDF).toUpperCase())
/*  359 */       .append('\n');
/*  360 */     this.buf.append(this.tab);
/*  361 */     appendAccess(paramInt);
/*  362 */     this.buf.append("INNERCLASS ");
/*  363 */     appendDescriptor(0, paramString1);
/*  364 */     this.buf.append(' ');
/*  365 */     appendDescriptor(0, paramString2);
/*  366 */     this.buf.append(' ');
/*  367 */     appendDescriptor(0, paramString3);
/*  368 */     this.buf.append('\n');
/*  369 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitField(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject) {
/*  375 */     this.buf.setLength(0);
/*  376 */     this.buf.append('\n');
/*  377 */     if ((paramInt & 0x20000) != 0) {
/*  378 */       this.buf.append(this.tab).append("// DEPRECATED\n");
/*      */     }
/*  380 */     this.buf.append(this.tab).append("// access flags 0x")
/*  381 */       .append(Integer.toHexString(paramInt).toUpperCase()).append('\n');
/*  382 */     if (paramString3 != null) {
/*  383 */       this.buf.append(this.tab);
/*  384 */       appendDescriptor(2, paramString3);
/*      */       
/*  386 */       TraceSignatureVisitor traceSignatureVisitor = new TraceSignatureVisitor(0);
/*  387 */       SignatureReader signatureReader = new SignatureReader(paramString3);
/*  388 */       signatureReader.acceptType(traceSignatureVisitor);
/*  389 */       this.buf.append(this.tab).append("// declaration: ")
/*  390 */         .append(traceSignatureVisitor.getDeclaration()).append('\n');
/*      */     } 
/*      */     
/*  393 */     this.buf.append(this.tab);
/*  394 */     appendAccess(paramInt);
/*      */     
/*  396 */     appendDescriptor(1, paramString2);
/*  397 */     this.buf.append(' ').append(paramString1);
/*  398 */     if (paramObject != null) {
/*  399 */       this.buf.append(" = ");
/*  400 */       if (paramObject instanceof String) {
/*  401 */         this.buf.append('"').append(paramObject).append('"');
/*      */       } else {
/*  403 */         this.buf.append(paramObject);
/*      */       } 
/*      */     } 
/*      */     
/*  407 */     this.buf.append('\n');
/*  408 */     this.text.add(this.buf.toString());
/*      */     
/*  410 */     Textifier textifier = createTextifier();
/*  411 */     this.text.add(textifier.getText());
/*  412 */     return textifier;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*  418 */     this.buf.setLength(0);
/*  419 */     this.buf.append('\n');
/*  420 */     if ((paramInt & 0x20000) != 0) {
/*  421 */       this.buf.append(this.tab).append("// DEPRECATED\n");
/*      */     }
/*  423 */     this.buf.append(this.tab).append("// access flags 0x")
/*  424 */       .append(Integer.toHexString(paramInt).toUpperCase()).append('\n');
/*      */     
/*  426 */     if (paramString3 != null) {
/*  427 */       this.buf.append(this.tab);
/*  428 */       appendDescriptor(4, paramString3);
/*      */       
/*  430 */       TraceSignatureVisitor traceSignatureVisitor = new TraceSignatureVisitor(0);
/*  431 */       SignatureReader signatureReader = new SignatureReader(paramString3);
/*  432 */       signatureReader.accept(traceSignatureVisitor);
/*  433 */       String str1 = traceSignatureVisitor.getDeclaration();
/*  434 */       String str2 = traceSignatureVisitor.getReturnType();
/*  435 */       String str3 = traceSignatureVisitor.getExceptions();
/*      */       
/*  437 */       this.buf.append(this.tab).append("// declaration: ").append(str2)
/*  438 */         .append(' ').append(paramString1).append(str1);
/*  439 */       if (str3 != null) {
/*  440 */         this.buf.append(" throws ").append(str3);
/*      */       }
/*  442 */       this.buf.append('\n');
/*      */     } 
/*      */     
/*  445 */     this.buf.append(this.tab);
/*  446 */     appendAccess(paramInt & 0xFFFFFFBF);
/*  447 */     if ((paramInt & 0x100) != 0) {
/*  448 */       this.buf.append("native ");
/*      */     }
/*  450 */     if ((paramInt & 0x80) != 0) {
/*  451 */       this.buf.append("varargs ");
/*      */     }
/*  453 */     if ((paramInt & 0x40) != 0) {
/*  454 */       this.buf.append("bridge ");
/*      */     }
/*  456 */     if ((this.access & 0x200) != 0 && (paramInt & 0x400) == 0 && (paramInt & 0x8) == 0)
/*      */     {
/*      */       
/*  459 */       this.buf.append("default ");
/*      */     }
/*      */     
/*  462 */     this.buf.append(paramString1);
/*  463 */     appendDescriptor(3, paramString2);
/*  464 */     if (paramArrayOfString != null && paramArrayOfString.length > 0) {
/*  465 */       this.buf.append(" throws ");
/*  466 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  467 */         appendDescriptor(0, paramArrayOfString[b]);
/*  468 */         this.buf.append(' ');
/*      */       } 
/*      */     } 
/*      */     
/*  472 */     this.buf.append('\n');
/*  473 */     this.text.add(this.buf.toString());
/*      */     
/*  475 */     Textifier textifier = createTextifier();
/*  476 */     this.text.add(textifier.getText());
/*  477 */     return textifier;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitClassEnd() {
/*  482 */     this.text.add("}\n");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visit(String paramString, Object paramObject) {
/*  491 */     this.buf.setLength(0);
/*  492 */     appendComa(this.valueNumber++);
/*      */     
/*  494 */     if (paramString != null) {
/*  495 */       this.buf.append(paramString).append('=');
/*      */     }
/*      */     
/*  498 */     if (paramObject instanceof String) {
/*  499 */       visitString((String)paramObject);
/*  500 */     } else if (paramObject instanceof Type) {
/*  501 */       visitType((Type)paramObject);
/*  502 */     } else if (paramObject instanceof Byte) {
/*  503 */       visitByte(((Byte)paramObject).byteValue());
/*  504 */     } else if (paramObject instanceof Boolean) {
/*  505 */       visitBoolean(((Boolean)paramObject).booleanValue());
/*  506 */     } else if (paramObject instanceof Short) {
/*  507 */       visitShort(((Short)paramObject).shortValue());
/*  508 */     } else if (paramObject instanceof Character) {
/*  509 */       visitChar(((Character)paramObject).charValue());
/*  510 */     } else if (paramObject instanceof Integer) {
/*  511 */       visitInt(((Integer)paramObject).intValue());
/*  512 */     } else if (paramObject instanceof Float) {
/*  513 */       visitFloat(((Float)paramObject).floatValue());
/*  514 */     } else if (paramObject instanceof Long) {
/*  515 */       visitLong(((Long)paramObject).longValue());
/*  516 */     } else if (paramObject instanceof Double) {
/*  517 */       visitDouble(((Double)paramObject).doubleValue());
/*  518 */     } else if (paramObject.getClass().isArray()) {
/*  519 */       this.buf.append('{');
/*  520 */       if (paramObject instanceof byte[]) {
/*  521 */         byte[] arrayOfByte = (byte[])paramObject;
/*  522 */         for (byte b = 0; b < arrayOfByte.length; b++) {
/*  523 */           appendComa(b);
/*  524 */           visitByte(arrayOfByte[b]);
/*      */         } 
/*  526 */       } else if (paramObject instanceof boolean[]) {
/*  527 */         boolean[] arrayOfBoolean = (boolean[])paramObject;
/*  528 */         for (byte b = 0; b < arrayOfBoolean.length; b++) {
/*  529 */           appendComa(b);
/*  530 */           visitBoolean(arrayOfBoolean[b]);
/*      */         } 
/*  532 */       } else if (paramObject instanceof short[]) {
/*  533 */         short[] arrayOfShort = (short[])paramObject;
/*  534 */         for (byte b = 0; b < arrayOfShort.length; b++) {
/*  535 */           appendComa(b);
/*  536 */           visitShort(arrayOfShort[b]);
/*      */         } 
/*  538 */       } else if (paramObject instanceof char[]) {
/*  539 */         char[] arrayOfChar = (char[])paramObject;
/*  540 */         for (byte b = 0; b < arrayOfChar.length; b++) {
/*  541 */           appendComa(b);
/*  542 */           visitChar(arrayOfChar[b]);
/*      */         } 
/*  544 */       } else if (paramObject instanceof int[]) {
/*  545 */         int[] arrayOfInt = (int[])paramObject;
/*  546 */         for (byte b = 0; b < arrayOfInt.length; b++) {
/*  547 */           appendComa(b);
/*  548 */           visitInt(arrayOfInt[b]);
/*      */         } 
/*  550 */       } else if (paramObject instanceof long[]) {
/*  551 */         long[] arrayOfLong = (long[])paramObject;
/*  552 */         for (byte b = 0; b < arrayOfLong.length; b++) {
/*  553 */           appendComa(b);
/*  554 */           visitLong(arrayOfLong[b]);
/*      */         } 
/*  556 */       } else if (paramObject instanceof float[]) {
/*  557 */         float[] arrayOfFloat = (float[])paramObject;
/*  558 */         for (byte b = 0; b < arrayOfFloat.length; b++) {
/*  559 */           appendComa(b);
/*  560 */           visitFloat(arrayOfFloat[b]);
/*      */         } 
/*  562 */       } else if (paramObject instanceof double[]) {
/*  563 */         double[] arrayOfDouble = (double[])paramObject;
/*  564 */         for (byte b = 0; b < arrayOfDouble.length; b++) {
/*  565 */           appendComa(b);
/*  566 */           visitDouble(arrayOfDouble[b]);
/*      */         } 
/*      */       } 
/*  569 */       this.buf.append('}');
/*      */     } 
/*      */     
/*  572 */     this.text.add(this.buf.toString());
/*      */   }
/*      */   
/*      */   private void visitInt(int paramInt) {
/*  576 */     this.buf.append(paramInt);
/*      */   }
/*      */   
/*      */   private void visitLong(long paramLong) {
/*  580 */     this.buf.append(paramLong).append('L');
/*      */   }
/*      */   
/*      */   private void visitFloat(float paramFloat) {
/*  584 */     this.buf.append(paramFloat).append('F');
/*      */   }
/*      */   
/*      */   private void visitDouble(double paramDouble) {
/*  588 */     this.buf.append(paramDouble).append('D');
/*      */   }
/*      */   
/*      */   private void visitChar(char paramChar) {
/*  592 */     this.buf.append("(char)").append(paramChar);
/*      */   }
/*      */   
/*      */   private void visitShort(short paramShort) {
/*  596 */     this.buf.append("(short)").append(paramShort);
/*      */   }
/*      */   
/*      */   private void visitByte(byte paramByte) {
/*  600 */     this.buf.append("(byte)").append(paramByte);
/*      */   }
/*      */   
/*      */   private void visitBoolean(boolean paramBoolean) {
/*  604 */     this.buf.append(paramBoolean);
/*      */   }
/*      */   
/*      */   private void visitString(String paramString) {
/*  608 */     appendString(this.buf, paramString);
/*      */   }
/*      */   
/*      */   private void visitType(Type paramType) {
/*  612 */     this.buf.append(paramType.getClassName()).append(".class");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitEnum(String paramString1, String paramString2, String paramString3) {
/*  618 */     this.buf.setLength(0);
/*  619 */     appendComa(this.valueNumber++);
/*  620 */     if (paramString1 != null) {
/*  621 */       this.buf.append(paramString1).append('=');
/*      */     }
/*  623 */     appendDescriptor(1, paramString2);
/*  624 */     this.buf.append('.').append(paramString3);
/*  625 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public Textifier visitAnnotation(String paramString1, String paramString2) {
/*  630 */     this.buf.setLength(0);
/*  631 */     appendComa(this.valueNumber++);
/*  632 */     if (paramString1 != null) {
/*  633 */       this.buf.append(paramString1).append('=');
/*      */     }
/*  635 */     this.buf.append('@');
/*  636 */     appendDescriptor(1, paramString2);
/*  637 */     this.buf.append('(');
/*  638 */     this.text.add(this.buf.toString());
/*  639 */     Textifier textifier = createTextifier();
/*  640 */     this.text.add(textifier.getText());
/*  641 */     this.text.add(")");
/*  642 */     return textifier;
/*      */   }
/*      */ 
/*      */   
/*      */   public Textifier visitArray(String paramString) {
/*  647 */     this.buf.setLength(0);
/*  648 */     appendComa(this.valueNumber++);
/*  649 */     if (paramString != null) {
/*  650 */       this.buf.append(paramString).append('=');
/*      */     }
/*  652 */     this.buf.append('{');
/*  653 */     this.text.add(this.buf.toString());
/*  654 */     Textifier textifier = createTextifier();
/*  655 */     this.text.add(textifier.getText());
/*  656 */     this.text.add("}");
/*  657 */     return textifier;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitAnnotationEnd() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitFieldAnnotation(String paramString, boolean paramBoolean) {
/*  671 */     return visitAnnotation(paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitFieldTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/*  677 */     return visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitFieldAttribute(Attribute paramAttribute) {
/*  682 */     visitAttribute(paramAttribute);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFieldEnd() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitParameter(String paramString, int paramInt) {
/*  695 */     this.buf.setLength(0);
/*  696 */     this.buf.append(this.tab2).append("// parameter ");
/*  697 */     appendAccess(paramInt);
/*  698 */     this.buf.append(' ').append((paramString == null) ? "<no name>" : paramString)
/*  699 */       .append('\n');
/*  700 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public Textifier visitAnnotationDefault() {
/*  705 */     this.text.add(this.tab2 + "default=");
/*  706 */     Textifier textifier = createTextifier();
/*  707 */     this.text.add(textifier.getText());
/*  708 */     this.text.add("\n");
/*  709 */     return textifier;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitMethodAnnotation(String paramString, boolean paramBoolean) {
/*  715 */     return visitAnnotation(paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitMethodTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/*  721 */     return visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitParameterAnnotation(int paramInt, String paramString, boolean paramBoolean) {
/*  727 */     this.buf.setLength(0);
/*  728 */     this.buf.append(this.tab2).append('@');
/*  729 */     appendDescriptor(1, paramString);
/*  730 */     this.buf.append('(');
/*  731 */     this.text.add(this.buf.toString());
/*  732 */     Textifier textifier = createTextifier();
/*  733 */     this.text.add(textifier.getText());
/*  734 */     this.text.add(paramBoolean ? ") // parameter " : ") // invisible, parameter ");
/*  735 */     this.text.add(Integer.valueOf(paramInt));
/*  736 */     this.text.add("\n");
/*  737 */     return textifier;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMethodAttribute(Attribute paramAttribute) {
/*  742 */     this.buf.setLength(0);
/*  743 */     this.buf.append(this.tab).append("ATTRIBUTE ");
/*  744 */     appendDescriptor(-1, paramAttribute.type);
/*      */     
/*  746 */     if (paramAttribute instanceof Textifiable) {
/*  747 */       ((Textifiable)paramAttribute).textify(this.buf, this.labelNames);
/*      */     } else {
/*  749 */       this.buf.append(" : unknown\n");
/*      */     } 
/*      */     
/*  752 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitCode() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFrame(int paramInt1, int paramInt2, Object[] paramArrayOfObject1, int paramInt3, Object[] paramArrayOfObject2) {
/*  762 */     this.buf.setLength(0);
/*  763 */     this.buf.append(this.ltab);
/*  764 */     this.buf.append("FRAME ");
/*  765 */     switch (paramInt1) {
/*      */       case -1:
/*      */       case 0:
/*  768 */         this.buf.append("FULL [");
/*  769 */         appendFrameTypes(paramInt2, paramArrayOfObject1);
/*  770 */         this.buf.append("] [");
/*  771 */         appendFrameTypes(paramInt3, paramArrayOfObject2);
/*  772 */         this.buf.append(']');
/*      */         break;
/*      */       case 1:
/*  775 */         this.buf.append("APPEND [");
/*  776 */         appendFrameTypes(paramInt2, paramArrayOfObject1);
/*  777 */         this.buf.append(']');
/*      */         break;
/*      */       case 2:
/*  780 */         this.buf.append("CHOP ").append(paramInt2);
/*      */         break;
/*      */       case 3:
/*  783 */         this.buf.append("SAME");
/*      */         break;
/*      */       case 4:
/*  786 */         this.buf.append("SAME1 ");
/*  787 */         appendFrameTypes(1, paramArrayOfObject2);
/*      */         break;
/*      */     } 
/*  790 */     this.buf.append('\n');
/*  791 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitInsn(int paramInt) {
/*  796 */     this.buf.setLength(0);
/*  797 */     this.buf.append(this.tab2).append(OPCODES[paramInt]).append('\n');
/*  798 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIntInsn(int paramInt1, int paramInt2) {
/*  803 */     this.buf.setLength(0);
/*  804 */     this.buf.append(this.tab2)
/*  805 */       .append(OPCODES[paramInt1])
/*  806 */       .append(' ')
/*  807 */       .append((paramInt1 == 188) ? TYPES[paramInt2] : 
/*  808 */         Integer.toString(paramInt2)).append('\n');
/*  809 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitVarInsn(int paramInt1, int paramInt2) {
/*  814 */     this.buf.setLength(0);
/*  815 */     this.buf.append(this.tab2).append(OPCODES[paramInt1]).append(' ').append(paramInt2)
/*  816 */       .append('\n');
/*  817 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitTypeInsn(int paramInt, String paramString) {
/*  822 */     this.buf.setLength(0);
/*  823 */     this.buf.append(this.tab2).append(OPCODES[paramInt]).append(' ');
/*  824 */     appendDescriptor(0, paramString);
/*  825 */     this.buf.append('\n');
/*  826 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/*  832 */     this.buf.setLength(0);
/*  833 */     this.buf.append(this.tab2).append(OPCODES[paramInt]).append(' ');
/*  834 */     appendDescriptor(0, paramString1);
/*  835 */     this.buf.append('.').append(paramString2).append(" : ");
/*  836 */     appendDescriptor(1, paramString3);
/*  837 */     this.buf.append('\n');
/*  838 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/*  845 */     if (this.api >= 327680) {
/*  846 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
/*      */       return;
/*      */     } 
/*  849 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, (paramInt == 185));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/*  856 */     if (this.api < 327680) {
/*  857 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*      */       return;
/*      */     } 
/*  860 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   private void doVisitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/*  865 */     this.buf.setLength(0);
/*  866 */     this.buf.append(this.tab2).append(OPCODES[paramInt]).append(' ');
/*  867 */     appendDescriptor(0, paramString1);
/*  868 */     this.buf.append('.').append(paramString2).append(' ');
/*  869 */     appendDescriptor(3, paramString3);
/*  870 */     this.buf.append('\n');
/*  871 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/*  877 */     this.buf.setLength(0);
/*  878 */     this.buf.append(this.tab2).append("INVOKEDYNAMIC").append(' ');
/*  879 */     this.buf.append(paramString1);
/*  880 */     appendDescriptor(3, paramString2);
/*  881 */     this.buf.append(" [");
/*  882 */     this.buf.append('\n');
/*  883 */     this.buf.append(this.tab3);
/*  884 */     appendHandle(paramHandle);
/*  885 */     this.buf.append('\n');
/*  886 */     this.buf.append(this.tab3).append("// arguments:");
/*  887 */     if (paramVarArgs.length == 0) {
/*  888 */       this.buf.append(" none");
/*      */     } else {
/*  890 */       this.buf.append('\n');
/*  891 */       for (byte b = 0; b < paramVarArgs.length; b++) {
/*  892 */         this.buf.append(this.tab3);
/*  893 */         Object object = paramVarArgs[b];
/*  894 */         if (object instanceof String) {
/*  895 */           Printer.appendString(this.buf, (String)object);
/*  896 */         } else if (object instanceof Type) {
/*  897 */           Type type = (Type)object;
/*  898 */           if (type.getSort() == 11) {
/*  899 */             appendDescriptor(3, type.getDescriptor());
/*      */           } else {
/*  901 */             this.buf.append(type.getDescriptor()).append(".class");
/*      */           } 
/*  903 */         } else if (object instanceof Handle) {
/*  904 */           appendHandle((Handle)object);
/*      */         } else {
/*  906 */           this.buf.append(object);
/*      */         } 
/*  908 */         this.buf.append(", \n");
/*      */       } 
/*  910 */       this.buf.setLength(this.buf.length() - 3);
/*      */     } 
/*  912 */     this.buf.append('\n');
/*  913 */     this.buf.append(this.tab2).append("]\n");
/*  914 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitJumpInsn(int paramInt, Label paramLabel) {
/*  919 */     this.buf.setLength(0);
/*  920 */     this.buf.append(this.tab2).append(OPCODES[paramInt]).append(' ');
/*  921 */     appendLabel(paramLabel);
/*  922 */     this.buf.append('\n');
/*  923 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLabel(Label paramLabel) {
/*  928 */     this.buf.setLength(0);
/*  929 */     this.buf.append(this.ltab);
/*  930 */     appendLabel(paramLabel);
/*  931 */     this.buf.append('\n');
/*  932 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLdcInsn(Object paramObject) {
/*  937 */     this.buf.setLength(0);
/*  938 */     this.buf.append(this.tab2).append("LDC ");
/*  939 */     if (paramObject instanceof String) {
/*  940 */       Printer.appendString(this.buf, (String)paramObject);
/*  941 */     } else if (paramObject instanceof Type) {
/*  942 */       this.buf.append(((Type)paramObject).getDescriptor()).append(".class");
/*      */     } else {
/*  944 */       this.buf.append(paramObject);
/*      */     } 
/*  946 */     this.buf.append('\n');
/*  947 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIincInsn(int paramInt1, int paramInt2) {
/*  952 */     this.buf.setLength(0);
/*  953 */     this.buf.append(this.tab2).append("IINC ").append(paramInt1).append(' ')
/*  954 */       .append(paramInt2).append('\n');
/*  955 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTableSwitchInsn(int paramInt1, int paramInt2, Label paramLabel, Label... paramVarArgs) {
/*  961 */     this.buf.setLength(0);
/*  962 */     this.buf.append(this.tab2).append("TABLESWITCH\n");
/*  963 */     for (byte b = 0; b < paramVarArgs.length; b++) {
/*  964 */       this.buf.append(this.tab3).append(paramInt1 + b).append(": ");
/*  965 */       appendLabel(paramVarArgs[b]);
/*  966 */       this.buf.append('\n');
/*      */     } 
/*  968 */     this.buf.append(this.tab3).append("default: ");
/*  969 */     appendLabel(paramLabel);
/*  970 */     this.buf.append('\n');
/*  971 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLookupSwitchInsn(Label paramLabel, int[] paramArrayOfint, Label[] paramArrayOfLabel) {
/*  977 */     this.buf.setLength(0);
/*  978 */     this.buf.append(this.tab2).append("LOOKUPSWITCH\n");
/*  979 */     for (byte b = 0; b < paramArrayOfLabel.length; b++) {
/*  980 */       this.buf.append(this.tab3).append(paramArrayOfint[b]).append(": ");
/*  981 */       appendLabel(paramArrayOfLabel[b]);
/*  982 */       this.buf.append('\n');
/*      */     } 
/*  984 */     this.buf.append(this.tab3).append("default: ");
/*  985 */     appendLabel(paramLabel);
/*  986 */     this.buf.append('\n');
/*  987 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMultiANewArrayInsn(String paramString, int paramInt) {
/*  992 */     this.buf.setLength(0);
/*  993 */     this.buf.append(this.tab2).append("MULTIANEWARRAY ");
/*  994 */     appendDescriptor(1, paramString);
/*  995 */     this.buf.append(' ').append(paramInt).append('\n');
/*  996 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitInsnAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 1002 */     return visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTryCatchBlock(Label paramLabel1, Label paramLabel2, Label paramLabel3, String paramString) {
/* 1008 */     this.buf.setLength(0);
/* 1009 */     this.buf.append(this.tab2).append("TRYCATCHBLOCK ");
/* 1010 */     appendLabel(paramLabel1);
/* 1011 */     this.buf.append(' ');
/* 1012 */     appendLabel(paramLabel2);
/* 1013 */     this.buf.append(' ');
/* 1014 */     appendLabel(paramLabel3);
/* 1015 */     this.buf.append(' ');
/* 1016 */     appendDescriptor(0, paramString);
/* 1017 */     this.buf.append('\n');
/* 1018 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitTryCatchAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 1024 */     this.buf.setLength(0);
/* 1025 */     this.buf.append(this.tab2).append("TRYCATCHBLOCK @");
/* 1026 */     appendDescriptor(1, paramString);
/* 1027 */     this.buf.append('(');
/* 1028 */     this.text.add(this.buf.toString());
/* 1029 */     Textifier textifier = createTextifier();
/* 1030 */     this.text.add(textifier.getText());
/* 1031 */     this.buf.setLength(0);
/* 1032 */     this.buf.append(") : ");
/* 1033 */     appendTypeReference(paramInt);
/* 1034 */     this.buf.append(", ").append(paramTypePath);
/* 1035 */     this.buf.append(paramBoolean ? "\n" : " // invisible\n");
/* 1036 */     this.text.add(this.buf.toString());
/* 1037 */     return textifier;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLocalVariable(String paramString1, String paramString2, String paramString3, Label paramLabel1, Label paramLabel2, int paramInt) {
/* 1044 */     this.buf.setLength(0);
/* 1045 */     this.buf.append(this.tab2).append("LOCALVARIABLE ").append(paramString1).append(' ');
/* 1046 */     appendDescriptor(1, paramString2);
/* 1047 */     this.buf.append(' ');
/* 1048 */     appendLabel(paramLabel1);
/* 1049 */     this.buf.append(' ');
/* 1050 */     appendLabel(paramLabel2);
/* 1051 */     this.buf.append(' ').append(paramInt).append('\n');
/*      */     
/* 1053 */     if (paramString3 != null) {
/* 1054 */       this.buf.append(this.tab2);
/* 1055 */       appendDescriptor(2, paramString3);
/*      */       
/* 1057 */       TraceSignatureVisitor traceSignatureVisitor = new TraceSignatureVisitor(0);
/* 1058 */       SignatureReader signatureReader = new SignatureReader(paramString3);
/* 1059 */       signatureReader.acceptType(traceSignatureVisitor);
/* 1060 */       this.buf.append(this.tab2).append("// declaration: ")
/* 1061 */         .append(traceSignatureVisitor.getDeclaration()).append('\n');
/*      */     } 
/* 1063 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Printer visitLocalVariableAnnotation(int paramInt, TypePath paramTypePath, Label[] paramArrayOfLabel1, Label[] paramArrayOfLabel2, int[] paramArrayOfint, String paramString, boolean paramBoolean) {
/* 1070 */     this.buf.setLength(0);
/* 1071 */     this.buf.append(this.tab2).append("LOCALVARIABLE @");
/* 1072 */     appendDescriptor(1, paramString);
/* 1073 */     this.buf.append('(');
/* 1074 */     this.text.add(this.buf.toString());
/* 1075 */     Textifier textifier = createTextifier();
/* 1076 */     this.text.add(textifier.getText());
/* 1077 */     this.buf.setLength(0);
/* 1078 */     this.buf.append(") : ");
/* 1079 */     appendTypeReference(paramInt);
/* 1080 */     this.buf.append(", ").append(paramTypePath);
/* 1081 */     for (byte b = 0; b < paramArrayOfLabel1.length; b++) {
/* 1082 */       this.buf.append(" [ ");
/* 1083 */       appendLabel(paramArrayOfLabel1[b]);
/* 1084 */       this.buf.append(" - ");
/* 1085 */       appendLabel(paramArrayOfLabel2[b]);
/* 1086 */       this.buf.append(" - ").append(paramArrayOfint[b]).append(" ]");
/*      */     } 
/* 1088 */     this.buf.append(paramBoolean ? "\n" : " // invisible\n");
/* 1089 */     this.text.add(this.buf.toString());
/* 1090 */     return textifier;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLineNumber(int paramInt, Label paramLabel) {
/* 1095 */     this.buf.setLength(0);
/* 1096 */     this.buf.append(this.tab2).append("LINENUMBER ").append(paramInt).append(' ');
/* 1097 */     appendLabel(paramLabel);
/* 1098 */     this.buf.append('\n');
/* 1099 */     this.text.add(this.buf.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMaxs(int paramInt1, int paramInt2) {
/* 1104 */     this.buf.setLength(0);
/* 1105 */     this.buf.append(this.tab2).append("MAXSTACK = ").append(paramInt1).append('\n');
/* 1106 */     this.text.add(this.buf.toString());
/*      */     
/* 1108 */     this.buf.setLength(0);
/* 1109 */     this.buf.append(this.tab2).append("MAXLOCALS = ").append(paramInt2).append('\n');
/* 1110 */     this.text.add(this.buf.toString());
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
/*      */   public void visitMethodEnd() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Textifier visitAnnotation(String paramString, boolean paramBoolean) {
/* 1131 */     this.buf.setLength(0);
/* 1132 */     this.buf.append(this.tab).append('@');
/* 1133 */     appendDescriptor(1, paramString);
/* 1134 */     this.buf.append('(');
/* 1135 */     this.text.add(this.buf.toString());
/* 1136 */     Textifier textifier = createTextifier();
/* 1137 */     this.text.add(textifier.getText());
/* 1138 */     this.text.add(paramBoolean ? ")\n" : ") // invisible\n");
/* 1139 */     return textifier;
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
/*      */   public Textifier visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 1159 */     this.buf.setLength(0);
/* 1160 */     this.buf.append(this.tab).append('@');
/* 1161 */     appendDescriptor(1, paramString);
/* 1162 */     this.buf.append('(');
/* 1163 */     this.text.add(this.buf.toString());
/* 1164 */     Textifier textifier = createTextifier();
/* 1165 */     this.text.add(textifier.getText());
/* 1166 */     this.buf.setLength(0);
/* 1167 */     this.buf.append(") : ");
/* 1168 */     appendTypeReference(paramInt);
/* 1169 */     this.buf.append(", ").append(paramTypePath);
/* 1170 */     this.buf.append(paramBoolean ? "\n" : " // invisible\n");
/* 1171 */     this.text.add(this.buf.toString());
/* 1172 */     return textifier;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitAttribute(Attribute paramAttribute) {
/* 1182 */     this.buf.setLength(0);
/* 1183 */     this.buf.append(this.tab).append("ATTRIBUTE ");
/* 1184 */     appendDescriptor(-1, paramAttribute.type);
/*      */     
/* 1186 */     if (paramAttribute instanceof Textifiable) {
/* 1187 */       ((Textifiable)paramAttribute).textify(this.buf, null);
/*      */     } else {
/* 1189 */       this.buf.append(" : unknown\n");
/*      */     } 
/*      */     
/* 1192 */     this.text.add(this.buf.toString());
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
/*      */   protected Textifier createTextifier() {
/* 1205 */     return new Textifier();
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
/*      */   protected void appendDescriptor(int paramInt, String paramString) {
/* 1220 */     if (paramInt == 5 || paramInt == 2 || paramInt == 4) {
/*      */       
/* 1222 */       if (paramString != null) {
/* 1223 */         this.buf.append("// signature ").append(paramString).append('\n');
/*      */       }
/*      */     } else {
/* 1226 */       this.buf.append(paramString);
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
/*      */   protected void appendLabel(Label paramLabel) {
/* 1238 */     if (this.labelNames == null) {
/* 1239 */       this.labelNames = new HashMap<>();
/*      */     }
/* 1241 */     String str = this.labelNames.get(paramLabel);
/* 1242 */     if (str == null) {
/* 1243 */       str = "L" + this.labelNames.size();
/* 1244 */       this.labelNames.put(paramLabel, str);
/*      */     } 
/* 1246 */     this.buf.append(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendHandle(Handle paramHandle) {
/* 1256 */     int i = paramHandle.getTag();
/* 1257 */     this.buf.append("// handle kind 0x").append(Integer.toHexString(i))
/* 1258 */       .append(" : ");
/* 1259 */     boolean bool = false;
/* 1260 */     switch (i) {
/*      */       case 1:
/* 1262 */         this.buf.append("GETFIELD");
/*      */         break;
/*      */       case 2:
/* 1265 */         this.buf.append("GETSTATIC");
/*      */         break;
/*      */       case 3:
/* 1268 */         this.buf.append("PUTFIELD");
/*      */         break;
/*      */       case 4:
/* 1271 */         this.buf.append("PUTSTATIC");
/*      */         break;
/*      */       case 9:
/* 1274 */         this.buf.append("INVOKEINTERFACE");
/* 1275 */         bool = true;
/*      */         break;
/*      */       case 7:
/* 1278 */         this.buf.append("INVOKESPECIAL");
/* 1279 */         bool = true;
/*      */         break;
/*      */       case 6:
/* 1282 */         this.buf.append("INVOKESTATIC");
/* 1283 */         bool = true;
/*      */         break;
/*      */       case 5:
/* 1286 */         this.buf.append("INVOKEVIRTUAL");
/* 1287 */         bool = true;
/*      */         break;
/*      */       case 8:
/* 1290 */         this.buf.append("NEWINVOKESPECIAL");
/* 1291 */         bool = true;
/*      */         break;
/*      */     } 
/* 1294 */     this.buf.append('\n');
/* 1295 */     this.buf.append(this.tab3);
/* 1296 */     appendDescriptor(0, paramHandle.getOwner());
/* 1297 */     this.buf.append('.');
/* 1298 */     this.buf.append(paramHandle.getName());
/* 1299 */     if (!bool) {
/* 1300 */       this.buf.append('(');
/*      */     }
/* 1302 */     appendDescriptor(9, paramHandle.getDesc());
/* 1303 */     if (!bool) {
/* 1304 */       this.buf.append(')');
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
/*      */   private void appendAccess(int paramInt) {
/* 1316 */     if ((paramInt & 0x1) != 0) {
/* 1317 */       this.buf.append("public ");
/*      */     }
/* 1319 */     if ((paramInt & 0x2) != 0) {
/* 1320 */       this.buf.append("private ");
/*      */     }
/* 1322 */     if ((paramInt & 0x4) != 0) {
/* 1323 */       this.buf.append("protected ");
/*      */     }
/* 1325 */     if ((paramInt & 0x10) != 0) {
/* 1326 */       this.buf.append("final ");
/*      */     }
/* 1328 */     if ((paramInt & 0x8) != 0) {
/* 1329 */       this.buf.append("static ");
/*      */     }
/* 1331 */     if ((paramInt & 0x20) != 0) {
/* 1332 */       this.buf.append("synchronized ");
/*      */     }
/* 1334 */     if ((paramInt & 0x40) != 0) {
/* 1335 */       this.buf.append("volatile ");
/*      */     }
/* 1337 */     if ((paramInt & 0x80) != 0) {
/* 1338 */       this.buf.append("transient ");
/*      */     }
/* 1340 */     if ((paramInt & 0x400) != 0) {
/* 1341 */       this.buf.append("abstract ");
/*      */     }
/* 1343 */     if ((paramInt & 0x800) != 0) {
/* 1344 */       this.buf.append("strictfp ");
/*      */     }
/* 1346 */     if ((paramInt & 0x1000) != 0) {
/* 1347 */       this.buf.append("synthetic ");
/*      */     }
/* 1349 */     if ((paramInt & 0x8000) != 0) {
/* 1350 */       this.buf.append("mandated ");
/*      */     }
/* 1352 */     if ((paramInt & 0x4000) != 0) {
/* 1353 */       this.buf.append("enum ");
/*      */     }
/*      */   }
/*      */   
/*      */   private void appendComa(int paramInt) {
/* 1358 */     if (paramInt != 0) {
/* 1359 */       this.buf.append(", ");
/*      */     }
/*      */   }
/*      */   
/*      */   private void appendTypeReference(int paramInt) {
/* 1364 */     TypeReference typeReference = new TypeReference(paramInt);
/* 1365 */     switch (typeReference.getSort()) {
/*      */       case 0:
/* 1367 */         this.buf.append("CLASS_TYPE_PARAMETER ").append(typeReference
/* 1368 */             .getTypeParameterIndex());
/*      */         break;
/*      */       case 1:
/* 1371 */         this.buf.append("METHOD_TYPE_PARAMETER ").append(typeReference
/* 1372 */             .getTypeParameterIndex());
/*      */         break;
/*      */       case 16:
/* 1375 */         this.buf.append("CLASS_EXTENDS ").append(typeReference.getSuperTypeIndex());
/*      */         break;
/*      */       case 17:
/* 1378 */         this.buf.append("CLASS_TYPE_PARAMETER_BOUND ")
/* 1379 */           .append(typeReference.getTypeParameterIndex()).append(", ")
/* 1380 */           .append(typeReference.getTypeParameterBoundIndex());
/*      */         break;
/*      */       case 18:
/* 1383 */         this.buf.append("METHOD_TYPE_PARAMETER_BOUND ")
/* 1384 */           .append(typeReference.getTypeParameterIndex()).append(", ")
/* 1385 */           .append(typeReference.getTypeParameterBoundIndex());
/*      */         break;
/*      */       case 19:
/* 1388 */         this.buf.append("FIELD");
/*      */         break;
/*      */       case 20:
/* 1391 */         this.buf.append("METHOD_RETURN");
/*      */         break;
/*      */       case 21:
/* 1394 */         this.buf.append("METHOD_RECEIVER");
/*      */         break;
/*      */       case 22:
/* 1397 */         this.buf.append("METHOD_FORMAL_PARAMETER ").append(typeReference
/* 1398 */             .getFormalParameterIndex());
/*      */         break;
/*      */       case 23:
/* 1401 */         this.buf.append("THROWS ").append(typeReference.getExceptionIndex());
/*      */         break;
/*      */       case 64:
/* 1404 */         this.buf.append("LOCAL_VARIABLE");
/*      */         break;
/*      */       case 65:
/* 1407 */         this.buf.append("RESOURCE_VARIABLE");
/*      */         break;
/*      */       case 66:
/* 1410 */         this.buf.append("EXCEPTION_PARAMETER ").append(typeReference
/* 1411 */             .getTryCatchBlockIndex());
/*      */         break;
/*      */       case 67:
/* 1414 */         this.buf.append("INSTANCEOF");
/*      */         break;
/*      */       case 68:
/* 1417 */         this.buf.append("NEW");
/*      */         break;
/*      */       case 69:
/* 1420 */         this.buf.append("CONSTRUCTOR_REFERENCE");
/*      */         break;
/*      */       case 70:
/* 1423 */         this.buf.append("METHOD_REFERENCE");
/*      */         break;
/*      */       case 71:
/* 1426 */         this.buf.append("CAST ").append(typeReference.getTypeArgumentIndex());
/*      */         break;
/*      */       case 72:
/* 1429 */         this.buf.append("CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT ").append(typeReference
/* 1430 */             .getTypeArgumentIndex());
/*      */         break;
/*      */       case 73:
/* 1433 */         this.buf.append("METHOD_INVOCATION_TYPE_ARGUMENT ").append(typeReference
/* 1434 */             .getTypeArgumentIndex());
/*      */         break;
/*      */       case 74:
/* 1437 */         this.buf.append("CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT ").append(typeReference
/* 1438 */             .getTypeArgumentIndex());
/*      */         break;
/*      */       case 75:
/* 1441 */         this.buf.append("METHOD_REFERENCE_TYPE_ARGUMENT ").append(typeReference
/* 1442 */             .getTypeArgumentIndex());
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void appendFrameTypes(int paramInt, Object[] paramArrayOfObject) {
/* 1448 */     for (byte b = 0; b < paramInt; b++) {
/* 1449 */       if (b > 0) {
/* 1450 */         this.buf.append(' ');
/*      */       }
/* 1452 */       if (paramArrayOfObject[b] instanceof String) {
/* 1453 */         String str = (String)paramArrayOfObject[b];
/* 1454 */         if (str.startsWith("[")) {
/* 1455 */           appendDescriptor(1, str);
/*      */         } else {
/* 1457 */           appendDescriptor(0, str);
/*      */         } 
/* 1459 */       } else if (paramArrayOfObject[b] instanceof Integer) {
/* 1460 */         switch (((Integer)paramArrayOfObject[b]).intValue()) {
/*      */           case 0:
/* 1462 */             appendDescriptor(1, "T");
/*      */             break;
/*      */           case 1:
/* 1465 */             appendDescriptor(1, "I");
/*      */             break;
/*      */           case 2:
/* 1468 */             appendDescriptor(1, "F");
/*      */             break;
/*      */           case 3:
/* 1471 */             appendDescriptor(1, "D");
/*      */             break;
/*      */           case 4:
/* 1474 */             appendDescriptor(1, "J");
/*      */             break;
/*      */           case 5:
/* 1477 */             appendDescriptor(1, "N");
/*      */             break;
/*      */           case 6:
/* 1480 */             appendDescriptor(1, "U");
/*      */             break;
/*      */         } 
/*      */       } else {
/* 1484 */         appendLabel((Label)paramArrayOfObject[b]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/Textifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */