/*     */ package jdk.internal.org.objectweb.asm.util;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import jdk.internal.org.objectweb.asm.Attribute;
/*     */ import jdk.internal.org.objectweb.asm.Handle;
/*     */ import jdk.internal.org.objectweb.asm.Label;
/*     */ import jdk.internal.org.objectweb.asm.TypePath;
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
/*     */ public abstract class Printer
/*     */ {
/*     */   static {
/*  97 */     String str = "NOP,ACONST_NULL,ICONST_M1,ICONST_0,ICONST_1,ICONST_2,ICONST_3,ICONST_4,ICONST_5,LCONST_0,LCONST_1,FCONST_0,FCONST_1,FCONST_2,DCONST_0,DCONST_1,BIPUSH,SIPUSH,LDC,,,ILOAD,LLOAD,FLOAD,DLOAD,ALOAD,,,,,,,,,,,,,,,,,,,,,IALOAD,LALOAD,FALOAD,DALOAD,AALOAD,BALOAD,CALOAD,SALOAD,ISTORE,LSTORE,FSTORE,DSTORE,ASTORE,,,,,,,,,,,,,,,,,,,,,IASTORE,LASTORE,FASTORE,DASTORE,AASTORE,BASTORE,CASTORE,SASTORE,POP,POP2,DUP,DUP_X1,DUP_X2,DUP2,DUP2_X1,DUP2_X2,SWAP,IADD,LADD,FADD,DADD,ISUB,LSUB,FSUB,DSUB,IMUL,LMUL,FMUL,DMUL,IDIV,LDIV,FDIV,DDIV,IREM,LREM,FREM,DREM,INEG,LNEG,FNEG,DNEG,ISHL,LSHL,ISHR,LSHR,IUSHR,LUSHR,IAND,LAND,IOR,LOR,IXOR,LXOR,IINC,I2L,I2F,I2D,L2I,L2F,L2D,F2I,F2L,F2D,D2I,D2L,D2F,I2B,I2C,I2S,LCMP,FCMPL,FCMPG,DCMPL,DCMPG,IFEQ,IFNE,IFLT,IFGE,IFGT,IFLE,IF_ICMPEQ,IF_ICMPNE,IF_ICMPLT,IF_ICMPGE,IF_ICMPGT,IF_ICMPLE,IF_ACMPEQ,IF_ACMPNE,GOTO,JSR,RET,TABLESWITCH,LOOKUPSWITCH,IRETURN,LRETURN,FRETURN,DRETURN,ARETURN,RETURN,GETSTATIC,PUTSTATIC,GETFIELD,PUTFIELD,INVOKEVIRTUAL,INVOKESPECIAL,INVOKESTATIC,INVOKEINTERFACE,INVOKEDYNAMIC,NEW,NEWARRAY,ANEWARRAY,ARRAYLENGTH,ATHROW,CHECKCAST,INSTANCEOF,MONITORENTER,MONITOREXIT,,MULTIANEWARRAY,IFNULL,IFNONNULL,";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static final String[] OPCODES = new String[200]; static {
/* 118 */     byte b = 0;
/* 119 */     int i = 0;
/*     */     int j;
/* 121 */     while ((j = str.indexOf(',', i)) > 0) {
/* 122 */       OPCODES[b++] = (i + 1 == j) ? null : str.substring(i, j);
/* 123 */       i = j + 1;
/*     */     } 
/*     */     
/* 126 */     str = "T_BOOLEAN,T_CHAR,T_FLOAT,T_DOUBLE,T_BYTE,T_SHORT,T_INT,T_LONG,";
/* 127 */   } public static final String[] TYPES = new String[12]; static {
/* 128 */     i = 0;
/* 129 */     b = 4;
/* 130 */     while ((j = str.indexOf(',', i)) > 0) {
/* 131 */       TYPES[b++] = str.substring(i, j);
/* 132 */       i = j + 1;
/*     */     } 
/*     */     
/* 135 */     str = "H_GETFIELD,H_GETSTATIC,H_PUTFIELD,H_PUTSTATIC,H_INVOKEVIRTUAL,H_INVOKESTATIC,H_INVOKESPECIAL,H_NEWINVOKESPECIAL,H_INVOKEINTERFACE,";
/*     */   }
/*     */   
/* 138 */   public static final String[] HANDLE_TAG = new String[10]; static {
/* 139 */     i = 0;
/* 140 */     b = 1;
/* 141 */     while ((j = str.indexOf(',', i)) > 0) {
/* 142 */       HANDLE_TAG[b++] = str.substring(i, j);
/* 143 */       i = j + 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int api;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final StringBuffer buf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final List<Object> text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Printer(int paramInt) {
/* 176 */     this.api = paramInt;
/* 177 */     this.buf = new StringBuffer();
/* 178 */     this.text = new ArrayList();
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
/*     */   public Printer visitClassTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 213 */     throw new RuntimeException("Must be overriden");
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
/*     */   public Printer visitFieldTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 296 */     throw new RuntimeException("Must be overriden");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitParameter(String paramString, int paramInt) {
/* 319 */     throw new RuntimeException("Must be overriden");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Printer visitMethodTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 341 */     throw new RuntimeException("Must be overriden");
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 407 */     if (this.api >= 327680) {
/* 408 */       boolean bool = (paramInt == 185) ? true : false;
/* 409 */       visitMethodInsn(paramInt, paramString1, paramString2, paramString3, bool);
/*     */       return;
/*     */     } 
/* 412 */     throw new RuntimeException("Must be overriden");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 421 */     if (this.api < 327680) {
/* 422 */       if (paramBoolean != ((paramInt == 185))) {
/* 423 */         throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
/*     */       }
/*     */       
/* 426 */       visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
/*     */       return;
/*     */     } 
/* 429 */     throw new RuntimeException("Must be overriden");
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
/*     */   public Printer visitInsnAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 489 */     throw new RuntimeException("Must be overriden");
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
/*     */ 
/*     */   
/*     */   public Printer visitTryCatchAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 505 */     throw new RuntimeException("Must be overriden");
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Printer visitLocalVariableAnnotation(int paramInt, TypePath paramTypePath, Label[] paramArrayOfLabel1, Label[] paramArrayOfLabel2, int[] paramArrayOfint, String paramString, boolean paramBoolean) {
/* 523 */     throw new RuntimeException("Must be overriden");
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
/*     */   public List<Object> getText() {
/* 549 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void print(PrintWriter paramPrintWriter) {
/* 559 */     printList(paramPrintWriter, this.text);
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
/*     */   public static void appendString(StringBuffer paramStringBuffer, String paramString) {
/* 571 */     paramStringBuffer.append('"');
/* 572 */     for (byte b = 0; b < paramString.length(); b++) {
/* 573 */       char c = paramString.charAt(b);
/* 574 */       if (c == '\n') {
/* 575 */         paramStringBuffer.append("\\n");
/* 576 */       } else if (c == '\r') {
/* 577 */         paramStringBuffer.append("\\r");
/* 578 */       } else if (c == '\\') {
/* 579 */         paramStringBuffer.append("\\\\");
/* 580 */       } else if (c == '"') {
/* 581 */         paramStringBuffer.append("\\\"");
/* 582 */       } else if (c < ' ' || c > '') {
/* 583 */         paramStringBuffer.append("\\u");
/* 584 */         if (c < '\020') {
/* 585 */           paramStringBuffer.append("000");
/* 586 */         } else if (c < 'Ā') {
/* 587 */           paramStringBuffer.append("00");
/* 588 */         } else if (c < 'က') {
/* 589 */           paramStringBuffer.append('0');
/*     */         } 
/* 591 */         paramStringBuffer.append(Integer.toString(c, 16));
/*     */       } else {
/* 593 */         paramStringBuffer.append(c);
/*     */       } 
/*     */     } 
/* 596 */     paramStringBuffer.append('"');
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
/*     */   static void printList(PrintWriter paramPrintWriter, List<?> paramList) {
/* 609 */     for (byte b = 0; b < paramList.size(); b++) {
/* 610 */       Object object = paramList.get(b);
/* 611 */       if (object instanceof List) {
/* 612 */         printList(paramPrintWriter, (List)object);
/*     */       } else {
/* 614 */         paramPrintWriter.print(object.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString);
/*     */   
/*     */   public abstract void visitSource(String paramString1, String paramString2);
/*     */   
/*     */   public abstract void visitOuterClass(String paramString1, String paramString2, String paramString3);
/*     */   
/*     */   public abstract Printer visitClassAnnotation(String paramString, boolean paramBoolean);
/*     */   
/*     */   public abstract void visitClassAttribute(Attribute paramAttribute);
/*     */   
/*     */   public abstract void visitInnerClass(String paramString1, String paramString2, String paramString3, int paramInt);
/*     */   
/*     */   public abstract Printer visitField(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject);
/*     */   
/*     */   public abstract Printer visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString);
/*     */   
/*     */   public abstract void visitClassEnd();
/*     */   
/*     */   public abstract void visit(String paramString, Object paramObject);
/*     */   
/*     */   public abstract void visitEnum(String paramString1, String paramString2, String paramString3);
/*     */   
/*     */   public abstract Printer visitAnnotation(String paramString1, String paramString2);
/*     */   
/*     */   public abstract Printer visitArray(String paramString);
/*     */   
/*     */   public abstract void visitAnnotationEnd();
/*     */   
/*     */   public abstract Printer visitFieldAnnotation(String paramString, boolean paramBoolean);
/*     */   
/*     */   public abstract void visitFieldAttribute(Attribute paramAttribute);
/*     */   
/*     */   public abstract void visitFieldEnd();
/*     */   
/*     */   public abstract Printer visitAnnotationDefault();
/*     */   
/*     */   public abstract Printer visitMethodAnnotation(String paramString, boolean paramBoolean);
/*     */   
/*     */   public abstract Printer visitParameterAnnotation(int paramInt, String paramString, boolean paramBoolean);
/*     */   
/*     */   public abstract void visitMethodAttribute(Attribute paramAttribute);
/*     */   
/*     */   public abstract void visitCode();
/*     */   
/*     */   public abstract void visitFrame(int paramInt1, int paramInt2, Object[] paramArrayOfObject1, int paramInt3, Object[] paramArrayOfObject2);
/*     */   
/*     */   public abstract void visitInsn(int paramInt);
/*     */   
/*     */   public abstract void visitIntInsn(int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void visitVarInsn(int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void visitTypeInsn(int paramInt, String paramString);
/*     */   
/*     */   public abstract void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3);
/*     */   
/*     */   public abstract void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs);
/*     */   
/*     */   public abstract void visitJumpInsn(int paramInt, Label paramLabel);
/*     */   
/*     */   public abstract void visitLabel(Label paramLabel);
/*     */   
/*     */   public abstract void visitLdcInsn(Object paramObject);
/*     */   
/*     */   public abstract void visitIincInsn(int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void visitTableSwitchInsn(int paramInt1, int paramInt2, Label paramLabel, Label... paramVarArgs);
/*     */   
/*     */   public abstract void visitLookupSwitchInsn(Label paramLabel, int[] paramArrayOfint, Label[] paramArrayOfLabel);
/*     */   
/*     */   public abstract void visitMultiANewArrayInsn(String paramString, int paramInt);
/*     */   
/*     */   public abstract void visitTryCatchBlock(Label paramLabel1, Label paramLabel2, Label paramLabel3, String paramString);
/*     */   
/*     */   public abstract void visitLocalVariable(String paramString1, String paramString2, String paramString3, Label paramLabel1, Label paramLabel2, int paramInt);
/*     */   
/*     */   public abstract void visitLineNumber(int paramInt, Label paramLabel);
/*     */   
/*     */   public abstract void visitMaxs(int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void visitMethodEnd();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/Printer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */