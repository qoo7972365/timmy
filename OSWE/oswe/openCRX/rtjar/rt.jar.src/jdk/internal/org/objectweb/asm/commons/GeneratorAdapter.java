/*      */ package jdk.internal.org.objectweb.asm.commons;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import jdk.internal.org.objectweb.asm.ClassVisitor;
/*      */ import jdk.internal.org.objectweb.asm.Handle;
/*      */ import jdk.internal.org.objectweb.asm.Label;
/*      */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*      */ import jdk.internal.org.objectweb.asm.Type;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GeneratorAdapter
/*      */   extends LocalVariablesSorter
/*      */ {
/*      */   private static final String CLDESC = "Ljava/lang/Class;";
/*  118 */   private static final Type BYTE_TYPE = Type.getObjectType("java/lang/Byte");
/*      */ 
/*      */   
/*  121 */   private static final Type BOOLEAN_TYPE = Type.getObjectType("java/lang/Boolean");
/*      */ 
/*      */   
/*  124 */   private static final Type SHORT_TYPE = Type.getObjectType("java/lang/Short");
/*      */ 
/*      */   
/*  127 */   private static final Type CHARACTER_TYPE = Type.getObjectType("java/lang/Character");
/*      */ 
/*      */   
/*  130 */   private static final Type INTEGER_TYPE = Type.getObjectType("java/lang/Integer");
/*      */ 
/*      */   
/*  133 */   private static final Type FLOAT_TYPE = Type.getObjectType("java/lang/Float");
/*      */   
/*  135 */   private static final Type LONG_TYPE = Type.getObjectType("java/lang/Long");
/*      */ 
/*      */   
/*  138 */   private static final Type DOUBLE_TYPE = Type.getObjectType("java/lang/Double");
/*      */ 
/*      */   
/*  141 */   private static final Type NUMBER_TYPE = Type.getObjectType("java/lang/Number");
/*      */ 
/*      */   
/*  144 */   private static final Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");
/*      */ 
/*      */   
/*  147 */   private static final Method BOOLEAN_VALUE = Method.getMethod("boolean booleanValue()");
/*      */ 
/*      */   
/*  150 */   private static final Method CHAR_VALUE = Method.getMethod("char charValue()");
/*      */   
/*  152 */   private static final Method INT_VALUE = Method.getMethod("int intValue()");
/*      */ 
/*      */   
/*  155 */   private static final Method FLOAT_VALUE = Method.getMethod("float floatValue()");
/*      */ 
/*      */   
/*  158 */   private static final Method LONG_VALUE = Method.getMethod("long longValue()");
/*      */ 
/*      */   
/*  161 */   private static final Method DOUBLE_VALUE = Method.getMethod("double doubleValue()");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int ADD = 96;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int SUB = 100;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int MUL = 104;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int DIV = 108;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int REM = 112;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int NEG = 116;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int SHL = 120;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int SHR = 122;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int USHR = 124;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int AND = 126;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int OR = 128;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int XOR = 130;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int EQ = 153;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int NE = 154;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int LT = 155;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int GE = 156;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int GT = 157;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int LE = 158;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int access;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Type returnType;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Type[] argumentTypes;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  271 */   private final List<Type> localTypes = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GeneratorAdapter(MethodVisitor paramMethodVisitor, int paramInt, String paramString1, String paramString2) {
/*  292 */     this(327680, paramMethodVisitor, paramInt, paramString1, paramString2);
/*  293 */     if (getClass() != GeneratorAdapter.class) {
/*  294 */       throw new IllegalStateException();
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
/*      */   
/*      */   protected GeneratorAdapter(int paramInt1, MethodVisitor paramMethodVisitor, int paramInt2, String paramString1, String paramString2) {
/*  315 */     super(paramInt1, paramInt2, paramString2, paramMethodVisitor);
/*  316 */     this.access = paramInt2;
/*  317 */     this.returnType = Type.getReturnType(paramString2);
/*  318 */     this.argumentTypes = Type.getArgumentTypes(paramString2);
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
/*      */   public GeneratorAdapter(int paramInt, Method paramMethod, MethodVisitor paramMethodVisitor) {
/*  336 */     this(paramMethodVisitor, paramInt, (String)null, paramMethod.getDescriptor());
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
/*      */ 
/*      */ 
/*      */   
/*      */   public GeneratorAdapter(int paramInt, Method paramMethod, String paramString, Type[] paramArrayOfType, ClassVisitor paramClassVisitor) {
/*  360 */     this(paramInt, paramMethod, paramClassVisitor
/*  361 */         .visitMethod(paramInt, paramMethod.getName(), paramMethod.getDescriptor(), paramString, 
/*  362 */           getInternalNames(paramArrayOfType)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] getInternalNames(Type[] paramArrayOfType) {
/*  373 */     if (paramArrayOfType == null) {
/*  374 */       return null;
/*      */     }
/*  376 */     String[] arrayOfString = new String[paramArrayOfType.length];
/*  377 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*  378 */       arrayOfString[b] = paramArrayOfType[b].getInternalName();
/*      */     }
/*  380 */     return arrayOfString;
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
/*      */   public void push(boolean paramBoolean) {
/*  394 */     push(paramBoolean ? 1 : 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void push(int paramInt) {
/*  404 */     if (paramInt >= -1 && paramInt <= 5) {
/*  405 */       this.mv.visitInsn(3 + paramInt);
/*  406 */     } else if (paramInt >= -128 && paramInt <= 127) {
/*  407 */       this.mv.visitIntInsn(16, paramInt);
/*  408 */     } else if (paramInt >= -32768 && paramInt <= 32767) {
/*  409 */       this.mv.visitIntInsn(17, paramInt);
/*      */     } else {
/*  411 */       this.mv.visitLdcInsn(Integer.valueOf(paramInt));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void push(long paramLong) {
/*  422 */     if (paramLong == 0L || paramLong == 1L) {
/*  423 */       this.mv.visitInsn(9 + (int)paramLong);
/*      */     } else {
/*  425 */       this.mv.visitLdcInsn(Long.valueOf(paramLong));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void push(float paramFloat) {
/*  436 */     int i = Float.floatToIntBits(paramFloat);
/*  437 */     if (i == 0L || i == 1065353216 || i == 1073741824) {
/*  438 */       this.mv.visitInsn(11 + (int)paramFloat);
/*      */     } else {
/*  440 */       this.mv.visitLdcInsn(Float.valueOf(paramFloat));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void push(double paramDouble) {
/*  451 */     long l = Double.doubleToLongBits(paramDouble);
/*  452 */     if (l == 0L || l == 4607182418800017408L) {
/*  453 */       this.mv.visitInsn(14 + (int)paramDouble);
/*      */     } else {
/*  455 */       this.mv.visitLdcInsn(Double.valueOf(paramDouble));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void push(String paramString) {
/*  466 */     if (paramString == null) {
/*  467 */       this.mv.visitInsn(1);
/*      */     } else {
/*  469 */       this.mv.visitLdcInsn(paramString);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void push(Type paramType) {
/*  480 */     if (paramType == null) {
/*  481 */       this.mv.visitInsn(1);
/*      */     } else {
/*  483 */       switch (paramType.getSort()) {
/*      */         case 1:
/*  485 */           this.mv.visitFieldInsn(178, "java/lang/Boolean", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */         
/*      */         case 2:
/*  489 */           this.mv.visitFieldInsn(178, "java/lang/Character", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */         
/*      */         case 3:
/*  493 */           this.mv.visitFieldInsn(178, "java/lang/Byte", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */         
/*      */         case 4:
/*  497 */           this.mv.visitFieldInsn(178, "java/lang/Short", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */         
/*      */         case 5:
/*  501 */           this.mv.visitFieldInsn(178, "java/lang/Integer", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */         
/*      */         case 6:
/*  505 */           this.mv.visitFieldInsn(178, "java/lang/Float", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */         
/*      */         case 7:
/*  509 */           this.mv.visitFieldInsn(178, "java/lang/Long", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */         
/*      */         case 8:
/*  513 */           this.mv.visitFieldInsn(178, "java/lang/Double", "TYPE", "Ljava/lang/Class;");
/*      */           return;
/*      */       } 
/*      */       
/*  517 */       this.mv.visitLdcInsn(paramType);
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
/*      */   public void push(Handle paramHandle) {
/*  529 */     this.mv.visitLdcInsn(paramHandle);
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
/*      */   private int getArgIndex(int paramInt) {
/*  546 */     int i = ((this.access & 0x8) == 0) ? 1 : 0;
/*  547 */     for (byte b = 0; b < paramInt; b++) {
/*  548 */       i += this.argumentTypes[b].getSize();
/*      */     }
/*  550 */     return i;
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
/*      */   private void loadInsn(Type paramType, int paramInt) {
/*  562 */     this.mv.visitVarInsn(paramType.getOpcode(21), paramInt);
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
/*      */   private void storeInsn(Type paramType, int paramInt) {
/*  575 */     this.mv.visitVarInsn(paramType.getOpcode(54), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadThis() {
/*  582 */     if ((this.access & 0x8) != 0) {
/*  583 */       throw new IllegalStateException("no 'this' pointer within static method");
/*      */     }
/*      */     
/*  586 */     this.mv.visitVarInsn(25, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadArg(int paramInt) {
/*  596 */     loadInsn(this.argumentTypes[paramInt], getArgIndex(paramInt));
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
/*      */   public void loadArgs(int paramInt1, int paramInt2) {
/*  609 */     int i = getArgIndex(paramInt1);
/*  610 */     for (byte b = 0; b < paramInt2; b++) {
/*  611 */       Type type = this.argumentTypes[paramInt1 + b];
/*  612 */       loadInsn(type, i);
/*  613 */       i += type.getSize();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadArgs() {
/*  621 */     loadArgs(0, this.argumentTypes.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadArgArray() {
/*  629 */     push(this.argumentTypes.length);
/*  630 */     newArray(OBJECT_TYPE);
/*  631 */     for (byte b = 0; b < this.argumentTypes.length; b++) {
/*  632 */       dup();
/*  633 */       push(b);
/*  634 */       loadArg(b);
/*  635 */       box(this.argumentTypes[b]);
/*  636 */       arrayStore(OBJECT_TYPE);
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
/*      */   public void storeArg(int paramInt) {
/*  648 */     storeInsn(this.argumentTypes[paramInt], getArgIndex(paramInt));
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
/*      */   public Type getLocalType(int paramInt) {
/*  664 */     return this.localTypes.get(paramInt - this.firstLocal);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setLocalType(int paramInt, Type paramType) {
/*  669 */     int i = paramInt - this.firstLocal;
/*  670 */     while (this.localTypes.size() < i + 1) {
/*  671 */       this.localTypes.add(null);
/*      */     }
/*  673 */     this.localTypes.set(i, paramType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadLocal(int paramInt) {
/*  684 */     loadInsn(getLocalType(paramInt), paramInt);
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
/*      */   public void loadLocal(int paramInt, Type paramType) {
/*  697 */     setLocalType(paramInt, paramType);
/*  698 */     loadInsn(paramType, paramInt);
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
/*      */   public void storeLocal(int paramInt) {
/*  710 */     storeInsn(getLocalType(paramInt), paramInt);
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
/*      */   public void storeLocal(int paramInt, Type paramType) {
/*  724 */     setLocalType(paramInt, paramType);
/*  725 */     storeInsn(paramType, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void arrayLoad(Type paramType) {
/*  735 */     this.mv.visitInsn(paramType.getOpcode(46));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void arrayStore(Type paramType) {
/*  745 */     this.mv.visitInsn(paramType.getOpcode(79));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pop() {
/*  756 */     this.mv.visitInsn(87);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pop2() {
/*  763 */     this.mv.visitInsn(88);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dup() {
/*  770 */     this.mv.visitInsn(89);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dup2() {
/*  777 */     this.mv.visitInsn(92);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dupX1() {
/*  784 */     this.mv.visitInsn(90);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dupX2() {
/*  791 */     this.mv.visitInsn(91);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dup2X1() {
/*  798 */     this.mv.visitInsn(93);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dup2X2() {
/*  805 */     this.mv.visitInsn(94);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void swap() {
/*  812 */     this.mv.visitInsn(95);
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
/*      */   public void swap(Type paramType1, Type paramType2) {
/*  824 */     if (paramType2.getSize() == 1) {
/*  825 */       if (paramType1.getSize() == 1) {
/*  826 */         swap();
/*      */       } else {
/*  828 */         dupX2();
/*  829 */         pop();
/*      */       }
/*      */     
/*  832 */     } else if (paramType1.getSize() == 1) {
/*  833 */       dup2X1();
/*  834 */       pop2();
/*      */     } else {
/*  836 */       dup2X2();
/*  837 */       pop2();
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
/*      */   public void math(int paramInt, Type paramType) {
/*  857 */     this.mv.visitInsn(paramType.getOpcode(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void not() {
/*  865 */     this.mv.visitInsn(4);
/*  866 */     this.mv.visitInsn(130);
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
/*      */   public void iinc(int paramInt1, int paramInt2) {
/*  878 */     this.mv.visitIincInsn(paramInt1, paramInt2);
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
/*      */   public void cast(Type paramType1, Type paramType2) {
/*  891 */     if (paramType1 != paramType2) {
/*  892 */       if (paramType1 == Type.DOUBLE_TYPE) {
/*  893 */         if (paramType2 == Type.FLOAT_TYPE) {
/*  894 */           this.mv.visitInsn(144);
/*  895 */         } else if (paramType2 == Type.LONG_TYPE) {
/*  896 */           this.mv.visitInsn(143);
/*      */         } else {
/*  898 */           this.mv.visitInsn(142);
/*  899 */           cast(Type.INT_TYPE, paramType2);
/*      */         } 
/*  901 */       } else if (paramType1 == Type.FLOAT_TYPE) {
/*  902 */         if (paramType2 == Type.DOUBLE_TYPE) {
/*  903 */           this.mv.visitInsn(141);
/*  904 */         } else if (paramType2 == Type.LONG_TYPE) {
/*  905 */           this.mv.visitInsn(140);
/*      */         } else {
/*  907 */           this.mv.visitInsn(139);
/*  908 */           cast(Type.INT_TYPE, paramType2);
/*      */         } 
/*  910 */       } else if (paramType1 == Type.LONG_TYPE) {
/*  911 */         if (paramType2 == Type.DOUBLE_TYPE) {
/*  912 */           this.mv.visitInsn(138);
/*  913 */         } else if (paramType2 == Type.FLOAT_TYPE) {
/*  914 */           this.mv.visitInsn(137);
/*      */         } else {
/*  916 */           this.mv.visitInsn(136);
/*  917 */           cast(Type.INT_TYPE, paramType2);
/*      */         }
/*      */       
/*  920 */       } else if (paramType2 == Type.BYTE_TYPE) {
/*  921 */         this.mv.visitInsn(145);
/*  922 */       } else if (paramType2 == Type.CHAR_TYPE) {
/*  923 */         this.mv.visitInsn(146);
/*  924 */       } else if (paramType2 == Type.DOUBLE_TYPE) {
/*  925 */         this.mv.visitInsn(135);
/*  926 */       } else if (paramType2 == Type.FLOAT_TYPE) {
/*  927 */         this.mv.visitInsn(134);
/*  928 */       } else if (paramType2 == Type.LONG_TYPE) {
/*  929 */         this.mv.visitInsn(133);
/*  930 */       } else if (paramType2 == Type.SHORT_TYPE) {
/*  931 */         this.mv.visitInsn(147);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Type getBoxedType(Type paramType) {
/*  942 */     switch (paramType.getSort()) {
/*      */       case 3:
/*  944 */         return BYTE_TYPE;
/*      */       case 1:
/*  946 */         return BOOLEAN_TYPE;
/*      */       case 4:
/*  948 */         return SHORT_TYPE;
/*      */       case 2:
/*  950 */         return CHARACTER_TYPE;
/*      */       case 5:
/*  952 */         return INTEGER_TYPE;
/*      */       case 6:
/*  954 */         return FLOAT_TYPE;
/*      */       case 7:
/*  956 */         return LONG_TYPE;
/*      */       case 8:
/*  958 */         return DOUBLE_TYPE;
/*      */     } 
/*  960 */     return paramType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void box(Type paramType) {
/*  971 */     if (paramType.getSort() == 10 || paramType.getSort() == 9) {
/*      */       return;
/*      */     }
/*  974 */     if (paramType == Type.VOID_TYPE) {
/*  975 */       push((String)null);
/*      */     } else {
/*  977 */       Type type = getBoxedType(paramType);
/*  978 */       newInstance(type);
/*  979 */       if (paramType.getSize() == 2) {
/*      */         
/*  981 */         dupX2();
/*  982 */         dupX2();
/*  983 */         pop();
/*      */       } else {
/*      */         
/*  986 */         dupX1();
/*  987 */         swap();
/*      */       } 
/*  989 */       invokeConstructor(type, new Method("<init>", Type.VOID_TYPE, new Type[] { paramType }));
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
/*      */   public void valueOf(Type paramType) {
/* 1003 */     if (paramType.getSort() == 10 || paramType.getSort() == 9) {
/*      */       return;
/*      */     }
/* 1006 */     if (paramType == Type.VOID_TYPE) {
/* 1007 */       push((String)null);
/*      */     } else {
/* 1009 */       Type type = getBoxedType(paramType);
/* 1010 */       invokeStatic(type, new Method("valueOf", type, new Type[] { paramType }));
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
/*      */   public void unbox(Type paramType) {
/* 1023 */     Type type = NUMBER_TYPE;
/* 1024 */     Method method = null;
/* 1025 */     switch (paramType.getSort()) {
/*      */       case 0:
/*      */         return;
/*      */       case 2:
/* 1029 */         type = CHARACTER_TYPE;
/* 1030 */         method = CHAR_VALUE;
/*      */         break;
/*      */       case 1:
/* 1033 */         type = BOOLEAN_TYPE;
/* 1034 */         method = BOOLEAN_VALUE;
/*      */         break;
/*      */       case 8:
/* 1037 */         method = DOUBLE_VALUE;
/*      */         break;
/*      */       case 6:
/* 1040 */         method = FLOAT_VALUE;
/*      */         break;
/*      */       case 7:
/* 1043 */         method = LONG_VALUE;
/*      */         break;
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/* 1048 */         method = INT_VALUE; break;
/*      */     } 
/* 1050 */     if (method == null) {
/* 1051 */       checkCast(paramType);
/*      */     } else {
/* 1053 */       checkCast(type);
/* 1054 */       invokeVirtual(type, method);
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
/*      */   public Label newLabel() {
/* 1068 */     return new Label();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mark(Label paramLabel) {
/* 1078 */     this.mv.visitLabel(paramLabel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Label mark() {
/* 1087 */     Label label = new Label();
/* 1088 */     this.mv.visitLabel(label);
/* 1089 */     return label;
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
/*      */   public void ifCmp(Type paramType, int paramInt, Label paramLabel) {
/*      */     short s;
/* 1105 */     switch (paramType.getSort()) {
/*      */       case 7:
/* 1107 */         this.mv.visitInsn(148);
/*      */         break;
/*      */       case 8:
/* 1110 */         this.mv.visitInsn((paramInt == 156 || paramInt == 157) ? 151 : 152);
/*      */         break;
/*      */       
/*      */       case 6:
/* 1114 */         this.mv.visitInsn((paramInt == 156 || paramInt == 157) ? 149 : 150);
/*      */         break;
/*      */       
/*      */       case 9:
/*      */       case 10:
/* 1119 */         switch (paramInt) {
/*      */           case 153:
/* 1121 */             this.mv.visitJumpInsn(165, paramLabel);
/*      */             return;
/*      */           case 154:
/* 1124 */             this.mv.visitJumpInsn(166, paramLabel);
/*      */             return;
/*      */         } 
/* 1127 */         throw new IllegalArgumentException("Bad comparison for type " + paramType);
/*      */       
/*      */       default:
/* 1130 */         s = -1;
/* 1131 */         switch (paramInt) {
/*      */           case 153:
/* 1133 */             s = 159;
/*      */             break;
/*      */           case 154:
/* 1136 */             s = 160;
/*      */             break;
/*      */           case 156:
/* 1139 */             s = 162;
/*      */             break;
/*      */           case 155:
/* 1142 */             s = 161;
/*      */             break;
/*      */           case 158:
/* 1145 */             s = 164;
/*      */             break;
/*      */           case 157:
/* 1148 */             s = 163;
/*      */             break;
/*      */         } 
/* 1151 */         this.mv.visitJumpInsn(s, paramLabel);
/*      */         return;
/*      */     } 
/* 1154 */     this.mv.visitJumpInsn(paramInt, paramLabel);
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
/*      */   public void ifICmp(int paramInt, Label paramLabel) {
/* 1168 */     ifCmp(Type.INT_TYPE, paramInt, paramLabel);
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
/*      */   public void ifZCmp(int paramInt, Label paramLabel) {
/* 1182 */     this.mv.visitJumpInsn(paramInt, paramLabel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ifNull(Label paramLabel) {
/* 1193 */     this.mv.visitJumpInsn(198, paramLabel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ifNonNull(Label paramLabel) {
/* 1204 */     this.mv.visitJumpInsn(199, paramLabel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void goTo(Label paramLabel) {
/* 1214 */     this.mv.visitJumpInsn(167, paramLabel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ret(int paramInt) {
/* 1225 */     this.mv.visitVarInsn(169, paramInt);
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
/*      */   public void tableSwitch(int[] paramArrayOfint, TableSwitchGenerator paramTableSwitchGenerator) {
/*      */     float f;
/* 1239 */     if (paramArrayOfint.length == 0) {
/* 1240 */       f = 0.0F;
/*      */     } else {
/* 1242 */       f = paramArrayOfint.length / (paramArrayOfint[paramArrayOfint.length - 1] - paramArrayOfint[0] + 1);
/*      */     } 
/*      */     
/* 1245 */     tableSwitch(paramArrayOfint, paramTableSwitchGenerator, (f >= 0.5F));
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
/*      */   public void tableSwitch(int[] paramArrayOfint, TableSwitchGenerator paramTableSwitchGenerator, boolean paramBoolean) {
/* 1261 */     for (byte b = 1; b < paramArrayOfint.length; b++) {
/* 1262 */       if (paramArrayOfint[b] < paramArrayOfint[b - 1]) {
/* 1263 */         throw new IllegalArgumentException("keys must be sorted ascending");
/*      */       }
/*      */     } 
/*      */     
/* 1267 */     Label label1 = newLabel();
/* 1268 */     Label label2 = newLabel();
/* 1269 */     if (paramArrayOfint.length > 0) {
/* 1270 */       int i = paramArrayOfint.length;
/* 1271 */       int j = paramArrayOfint[0];
/* 1272 */       int k = paramArrayOfint[i - 1];
/* 1273 */       int m = k - j + 1;
/* 1274 */       if (paramBoolean) {
/* 1275 */         Label[] arrayOfLabel = new Label[m];
/* 1276 */         Arrays.fill((Object[])arrayOfLabel, label1); byte b1;
/* 1277 */         for (b1 = 0; b1 < i; b1++) {
/* 1278 */           arrayOfLabel[paramArrayOfint[b1] - j] = newLabel();
/*      */         }
/* 1280 */         this.mv.visitTableSwitchInsn(j, k, label1, arrayOfLabel);
/* 1281 */         for (b1 = 0; b1 < m; b1++) {
/* 1282 */           Label label = arrayOfLabel[b1];
/* 1283 */           if (label != label1) {
/* 1284 */             mark(label);
/* 1285 */             paramTableSwitchGenerator.generateCase(b1 + j, label2);
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1289 */         Label[] arrayOfLabel = new Label[i]; byte b1;
/* 1290 */         for (b1 = 0; b1 < i; b1++) {
/* 1291 */           arrayOfLabel[b1] = newLabel();
/*      */         }
/* 1293 */         this.mv.visitLookupSwitchInsn(label1, paramArrayOfint, arrayOfLabel);
/* 1294 */         for (b1 = 0; b1 < i; b1++) {
/* 1295 */           mark(arrayOfLabel[b1]);
/* 1296 */           paramTableSwitchGenerator.generateCase(paramArrayOfint[b1], label2);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1300 */     mark(label1);
/* 1301 */     paramTableSwitchGenerator.generateDefault();
/* 1302 */     mark(label2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void returnValue() {
/* 1309 */     this.mv.visitInsn(this.returnType.getOpcode(172));
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
/*      */   private void fieldInsn(int paramInt, Type paramType1, String paramString, Type paramType2) {
/* 1330 */     this.mv.visitFieldInsn(paramInt, paramType1.getInternalName(), paramString, paramType2
/* 1331 */         .getDescriptor());
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
/*      */   public void getStatic(Type paramType1, String paramString, Type paramType2) {
/* 1346 */     fieldInsn(178, paramType1, paramString, paramType2);
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
/*      */   public void putStatic(Type paramType1, String paramString, Type paramType2) {
/* 1360 */     fieldInsn(179, paramType1, paramString, paramType2);
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
/*      */   public void getField(Type paramType1, String paramString, Type paramType2) {
/* 1375 */     fieldInsn(180, paramType1, paramString, paramType2);
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
/*      */   public void putField(Type paramType1, String paramString, Type paramType2) {
/* 1390 */     fieldInsn(181, paramType1, paramString, paramType2);
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
/*      */   private void invokeInsn(int paramInt, Type paramType, Method paramMethod, boolean paramBoolean) {
/* 1410 */     String str = (paramType.getSort() == 9) ? paramType.getDescriptor() : paramType.getInternalName();
/* 1411 */     this.mv.visitMethodInsn(paramInt, str, paramMethod.getName(), paramMethod
/* 1412 */         .getDescriptor(), paramBoolean);
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
/*      */   public void invokeVirtual(Type paramType, Method paramMethod) {
/* 1424 */     invokeInsn(182, paramType, paramMethod, false);
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
/*      */   public void invokeConstructor(Type paramType, Method paramMethod) {
/* 1436 */     invokeInsn(183, paramType, paramMethod, false);
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
/*      */   public void invokeStatic(Type paramType, Method paramMethod) {
/* 1448 */     invokeInsn(184, paramType, paramMethod, false);
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
/*      */   public void invokeInterface(Type paramType, Method paramMethod) {
/* 1460 */     invokeInsn(185, paramType, paramMethod, true);
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
/*      */   public void invokeDynamic(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/* 1481 */     this.mv.visitInvokeDynamicInsn(paramString1, paramString2, paramHandle, paramVarArgs);
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
/*      */   private void typeInsn(int paramInt, Type paramType) {
/* 1497 */     this.mv.visitTypeInsn(paramInt, paramType.getInternalName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void newInstance(Type paramType) {
/* 1507 */     typeInsn(187, paramType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void newArray(Type paramType) {
/*      */     byte b;
/* 1518 */     switch (paramType.getSort()) {
/*      */       case 1:
/* 1520 */         b = 4;
/*      */         break;
/*      */       case 2:
/* 1523 */         b = 5;
/*      */         break;
/*      */       case 3:
/* 1526 */         b = 8;
/*      */         break;
/*      */       case 4:
/* 1529 */         b = 9;
/*      */         break;
/*      */       case 5:
/* 1532 */         b = 10;
/*      */         break;
/*      */       case 6:
/* 1535 */         b = 6;
/*      */         break;
/*      */       case 7:
/* 1538 */         b = 11;
/*      */         break;
/*      */       case 8:
/* 1541 */         b = 7;
/*      */         break;
/*      */       default:
/* 1544 */         typeInsn(189, paramType);
/*      */         return;
/*      */     } 
/* 1547 */     this.mv.visitIntInsn(188, b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void arrayLength() {
/* 1558 */     this.mv.visitInsn(190);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void throwException() {
/* 1565 */     this.mv.visitInsn(191);
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
/*      */   public void throwException(Type paramType, String paramString) {
/* 1578 */     newInstance(paramType);
/* 1579 */     dup();
/* 1580 */     push(paramString);
/* 1581 */     invokeConstructor(paramType, Method.getMethod("void <init> (String)"));
/* 1582 */     throwException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkCast(Type paramType) {
/* 1593 */     if (!paramType.equals(OBJECT_TYPE)) {
/* 1594 */       typeInsn(192, paramType);
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
/*      */   public void instanceOf(Type paramType) {
/* 1606 */     typeInsn(193, paramType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void monitorEnter() {
/* 1613 */     this.mv.visitInsn(194);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void monitorExit() {
/* 1620 */     this.mv.visitInsn(195);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endMethod() {
/* 1631 */     if ((this.access & 0x400) == 0) {
/* 1632 */       this.mv.visitMaxs(0, 0);
/*      */     }
/* 1634 */     this.mv.visitEnd();
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
/*      */   public void catchException(Label paramLabel1, Label paramLabel2, Type paramType) {
/* 1650 */     Label label = new Label();
/* 1651 */     if (paramType == null) {
/* 1652 */       this.mv.visitTryCatchBlock(paramLabel1, paramLabel2, label, null);
/*      */     } else {
/* 1654 */       this.mv.visitTryCatchBlock(paramLabel1, paramLabel2, label, paramType
/* 1655 */           .getInternalName());
/*      */     } 
/* 1657 */     mark(label);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/GeneratorAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */