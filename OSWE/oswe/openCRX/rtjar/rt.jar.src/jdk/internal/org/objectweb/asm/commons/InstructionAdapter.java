/*      */ package jdk.internal.org.objectweb.asm.commons;
/*      */ 
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
/*      */ public class InstructionAdapter
/*      */   extends MethodVisitor
/*      */ {
/*   76 */   public static final Type OBJECT_TYPE = Type.getType("Ljava/lang/Object;");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InstructionAdapter(MethodVisitor paramMethodVisitor) {
/*   89 */     this(327680, paramMethodVisitor);
/*   90 */     if (getClass() != InstructionAdapter.class) {
/*   91 */       throw new IllegalStateException();
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
/*      */   protected InstructionAdapter(int paramInt, MethodVisitor paramMethodVisitor) {
/*  105 */     super(paramInt, paramMethodVisitor);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitInsn(int paramInt) {
/*  110 */     switch (paramInt) {
/*      */       case 0:
/*  112 */         nop();
/*      */         return;
/*      */       case 1:
/*  115 */         aconst((Object)null);
/*      */         return;
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/*  124 */         iconst(paramInt - 3);
/*      */         return;
/*      */       case 9:
/*      */       case 10:
/*  128 */         lconst((paramInt - 9));
/*      */         return;
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*  133 */         fconst((paramInt - 11));
/*      */         return;
/*      */       case 14:
/*      */       case 15:
/*  137 */         dconst((paramInt - 14));
/*      */         return;
/*      */       case 46:
/*  140 */         aload(Type.INT_TYPE);
/*      */         return;
/*      */       case 47:
/*  143 */         aload(Type.LONG_TYPE);
/*      */         return;
/*      */       case 48:
/*  146 */         aload(Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 49:
/*  149 */         aload(Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 50:
/*  152 */         aload(OBJECT_TYPE);
/*      */         return;
/*      */       case 51:
/*  155 */         aload(Type.BYTE_TYPE);
/*      */         return;
/*      */       case 52:
/*  158 */         aload(Type.CHAR_TYPE);
/*      */         return;
/*      */       case 53:
/*  161 */         aload(Type.SHORT_TYPE);
/*      */         return;
/*      */       case 79:
/*  164 */         astore(Type.INT_TYPE);
/*      */         return;
/*      */       case 80:
/*  167 */         astore(Type.LONG_TYPE);
/*      */         return;
/*      */       case 81:
/*  170 */         astore(Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 82:
/*  173 */         astore(Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 83:
/*  176 */         astore(OBJECT_TYPE);
/*      */         return;
/*      */       case 84:
/*  179 */         astore(Type.BYTE_TYPE);
/*      */         return;
/*      */       case 85:
/*  182 */         astore(Type.CHAR_TYPE);
/*      */         return;
/*      */       case 86:
/*  185 */         astore(Type.SHORT_TYPE);
/*      */         return;
/*      */       case 87:
/*  188 */         pop();
/*      */         return;
/*      */       case 88:
/*  191 */         pop2();
/*      */         return;
/*      */       case 89:
/*  194 */         dup();
/*      */         return;
/*      */       case 90:
/*  197 */         dupX1();
/*      */         return;
/*      */       case 91:
/*  200 */         dupX2();
/*      */         return;
/*      */       case 92:
/*  203 */         dup2();
/*      */         return;
/*      */       case 93:
/*  206 */         dup2X1();
/*      */         return;
/*      */       case 94:
/*  209 */         dup2X2();
/*      */         return;
/*      */       case 95:
/*  212 */         swap();
/*      */         return;
/*      */       case 96:
/*  215 */         add(Type.INT_TYPE);
/*      */         return;
/*      */       case 97:
/*  218 */         add(Type.LONG_TYPE);
/*      */         return;
/*      */       case 98:
/*  221 */         add(Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 99:
/*  224 */         add(Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 100:
/*  227 */         sub(Type.INT_TYPE);
/*      */         return;
/*      */       case 101:
/*  230 */         sub(Type.LONG_TYPE);
/*      */         return;
/*      */       case 102:
/*  233 */         sub(Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 103:
/*  236 */         sub(Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 104:
/*  239 */         mul(Type.INT_TYPE);
/*      */         return;
/*      */       case 105:
/*  242 */         mul(Type.LONG_TYPE);
/*      */         return;
/*      */       case 106:
/*  245 */         mul(Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 107:
/*  248 */         mul(Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 108:
/*  251 */         div(Type.INT_TYPE);
/*      */         return;
/*      */       case 109:
/*  254 */         div(Type.LONG_TYPE);
/*      */         return;
/*      */       case 110:
/*  257 */         div(Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 111:
/*  260 */         div(Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 112:
/*  263 */         rem(Type.INT_TYPE);
/*      */         return;
/*      */       case 113:
/*  266 */         rem(Type.LONG_TYPE);
/*      */         return;
/*      */       case 114:
/*  269 */         rem(Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 115:
/*  272 */         rem(Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 116:
/*  275 */         neg(Type.INT_TYPE);
/*      */         return;
/*      */       case 117:
/*  278 */         neg(Type.LONG_TYPE);
/*      */         return;
/*      */       case 118:
/*  281 */         neg(Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 119:
/*  284 */         neg(Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 120:
/*  287 */         shl(Type.INT_TYPE);
/*      */         return;
/*      */       case 121:
/*  290 */         shl(Type.LONG_TYPE);
/*      */         return;
/*      */       case 122:
/*  293 */         shr(Type.INT_TYPE);
/*      */         return;
/*      */       case 123:
/*  296 */         shr(Type.LONG_TYPE);
/*      */         return;
/*      */       case 124:
/*  299 */         ushr(Type.INT_TYPE);
/*      */         return;
/*      */       case 125:
/*  302 */         ushr(Type.LONG_TYPE);
/*      */         return;
/*      */       case 126:
/*  305 */         and(Type.INT_TYPE);
/*      */         return;
/*      */       case 127:
/*  308 */         and(Type.LONG_TYPE);
/*      */         return;
/*      */       case 128:
/*  311 */         or(Type.INT_TYPE);
/*      */         return;
/*      */       case 129:
/*  314 */         or(Type.LONG_TYPE);
/*      */         return;
/*      */       case 130:
/*  317 */         xor(Type.INT_TYPE);
/*      */         return;
/*      */       case 131:
/*  320 */         xor(Type.LONG_TYPE);
/*      */         return;
/*      */       case 133:
/*  323 */         cast(Type.INT_TYPE, Type.LONG_TYPE);
/*      */         return;
/*      */       case 134:
/*  326 */         cast(Type.INT_TYPE, Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 135:
/*  329 */         cast(Type.INT_TYPE, Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 136:
/*  332 */         cast(Type.LONG_TYPE, Type.INT_TYPE);
/*      */         return;
/*      */       case 137:
/*  335 */         cast(Type.LONG_TYPE, Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 138:
/*  338 */         cast(Type.LONG_TYPE, Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 139:
/*  341 */         cast(Type.FLOAT_TYPE, Type.INT_TYPE);
/*      */         return;
/*      */       case 140:
/*  344 */         cast(Type.FLOAT_TYPE, Type.LONG_TYPE);
/*      */         return;
/*      */       case 141:
/*  347 */         cast(Type.FLOAT_TYPE, Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 142:
/*  350 */         cast(Type.DOUBLE_TYPE, Type.INT_TYPE);
/*      */         return;
/*      */       case 143:
/*  353 */         cast(Type.DOUBLE_TYPE, Type.LONG_TYPE);
/*      */         return;
/*      */       case 144:
/*  356 */         cast(Type.DOUBLE_TYPE, Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 145:
/*  359 */         cast(Type.INT_TYPE, Type.BYTE_TYPE);
/*      */         return;
/*      */       case 146:
/*  362 */         cast(Type.INT_TYPE, Type.CHAR_TYPE);
/*      */         return;
/*      */       case 147:
/*  365 */         cast(Type.INT_TYPE, Type.SHORT_TYPE);
/*      */         return;
/*      */       case 148:
/*  368 */         lcmp();
/*      */         return;
/*      */       case 149:
/*  371 */         cmpl(Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 150:
/*  374 */         cmpg(Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 151:
/*  377 */         cmpl(Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 152:
/*  380 */         cmpg(Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 172:
/*  383 */         areturn(Type.INT_TYPE);
/*      */         return;
/*      */       case 173:
/*  386 */         areturn(Type.LONG_TYPE);
/*      */         return;
/*      */       case 174:
/*  389 */         areturn(Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 175:
/*  392 */         areturn(Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 176:
/*  395 */         areturn(OBJECT_TYPE);
/*      */         return;
/*      */       case 177:
/*  398 */         areturn(Type.VOID_TYPE);
/*      */         return;
/*      */       case 190:
/*  401 */         arraylength();
/*      */         return;
/*      */       case 191:
/*  404 */         athrow();
/*      */         return;
/*      */       case 194:
/*  407 */         monitorenter();
/*      */         return;
/*      */       case 195:
/*  410 */         monitorexit();
/*      */         return;
/*      */     } 
/*  413 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitIntInsn(int paramInt1, int paramInt2) {
/*  419 */     switch (paramInt1) {
/*      */       case 16:
/*  421 */         iconst(paramInt2);
/*      */         return;
/*      */       case 17:
/*  424 */         iconst(paramInt2);
/*      */         return;
/*      */       case 188:
/*  427 */         switch (paramInt2) {
/*      */           case 4:
/*  429 */             newarray(Type.BOOLEAN_TYPE);
/*      */             return;
/*      */           case 5:
/*  432 */             newarray(Type.CHAR_TYPE);
/*      */             return;
/*      */           case 8:
/*  435 */             newarray(Type.BYTE_TYPE);
/*      */             return;
/*      */           case 9:
/*  438 */             newarray(Type.SHORT_TYPE);
/*      */             return;
/*      */           case 10:
/*  441 */             newarray(Type.INT_TYPE);
/*      */             return;
/*      */           case 6:
/*  444 */             newarray(Type.FLOAT_TYPE);
/*      */             return;
/*      */           case 11:
/*  447 */             newarray(Type.LONG_TYPE);
/*      */             return;
/*      */           case 7:
/*  450 */             newarray(Type.DOUBLE_TYPE);
/*      */             return;
/*      */         } 
/*  453 */         throw new IllegalArgumentException();
/*      */     } 
/*      */ 
/*      */     
/*  457 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitVarInsn(int paramInt1, int paramInt2) {
/*  463 */     switch (paramInt1) {
/*      */       case 21:
/*  465 */         load(paramInt2, Type.INT_TYPE);
/*      */         return;
/*      */       case 22:
/*  468 */         load(paramInt2, Type.LONG_TYPE);
/*      */         return;
/*      */       case 23:
/*  471 */         load(paramInt2, Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 24:
/*  474 */         load(paramInt2, Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 25:
/*  477 */         load(paramInt2, OBJECT_TYPE);
/*      */         return;
/*      */       case 54:
/*  480 */         store(paramInt2, Type.INT_TYPE);
/*      */         return;
/*      */       case 55:
/*  483 */         store(paramInt2, Type.LONG_TYPE);
/*      */         return;
/*      */       case 56:
/*  486 */         store(paramInt2, Type.FLOAT_TYPE);
/*      */         return;
/*      */       case 57:
/*  489 */         store(paramInt2, Type.DOUBLE_TYPE);
/*      */         return;
/*      */       case 58:
/*  492 */         store(paramInt2, OBJECT_TYPE);
/*      */         return;
/*      */       case 169:
/*  495 */         ret(paramInt2);
/*      */         return;
/*      */     } 
/*  498 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTypeInsn(int paramInt, String paramString) {
/*  504 */     Type type = Type.getObjectType(paramString);
/*  505 */     switch (paramInt) {
/*      */       case 187:
/*  507 */         anew(type);
/*      */         return;
/*      */       case 189:
/*  510 */         newarray(type);
/*      */         return;
/*      */       case 192:
/*  513 */         checkcast(type);
/*      */         return;
/*      */       case 193:
/*  516 */         instanceOf(type);
/*      */         return;
/*      */     } 
/*  519 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/*  526 */     switch (paramInt) {
/*      */       case 178:
/*  528 */         getstatic(paramString1, paramString2, paramString3);
/*      */         return;
/*      */       case 179:
/*  531 */         putstatic(paramString1, paramString2, paramString3);
/*      */         return;
/*      */       case 180:
/*  534 */         getfield(paramString1, paramString2, paramString3);
/*      */         return;
/*      */       case 181:
/*  537 */         putfield(paramString1, paramString2, paramString3);
/*      */         return;
/*      */     } 
/*  540 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/*  548 */     if (this.api >= 327680) {
/*  549 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
/*      */       return;
/*      */     } 
/*  552 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, (paramInt == 185));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/*  559 */     if (this.api < 327680) {
/*  560 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*      */       return;
/*      */     } 
/*  563 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   private void doVisitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/*  568 */     switch (paramInt) {
/*      */       case 183:
/*  570 */         invokespecial(paramString1, paramString2, paramString3, paramBoolean);
/*      */         return;
/*      */       case 182:
/*  573 */         invokevirtual(paramString1, paramString2, paramString3, paramBoolean);
/*      */         return;
/*      */       case 184:
/*  576 */         invokestatic(paramString1, paramString2, paramString3, paramBoolean);
/*      */         return;
/*      */       case 185:
/*  579 */         invokeinterface(paramString1, paramString2, paramString3);
/*      */         return;
/*      */     } 
/*  582 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/*  589 */     invokedynamic(paramString1, paramString2, paramHandle, paramVarArgs);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitJumpInsn(int paramInt, Label paramLabel) {
/*  594 */     switch (paramInt) {
/*      */       case 153:
/*  596 */         ifeq(paramLabel);
/*      */         return;
/*      */       case 154:
/*  599 */         ifne(paramLabel);
/*      */         return;
/*      */       case 155:
/*  602 */         iflt(paramLabel);
/*      */         return;
/*      */       case 156:
/*  605 */         ifge(paramLabel);
/*      */         return;
/*      */       case 157:
/*  608 */         ifgt(paramLabel);
/*      */         return;
/*      */       case 158:
/*  611 */         ifle(paramLabel);
/*      */         return;
/*      */       case 159:
/*  614 */         ificmpeq(paramLabel);
/*      */         return;
/*      */       case 160:
/*  617 */         ificmpne(paramLabel);
/*      */         return;
/*      */       case 161:
/*  620 */         ificmplt(paramLabel);
/*      */         return;
/*      */       case 162:
/*  623 */         ificmpge(paramLabel);
/*      */         return;
/*      */       case 163:
/*  626 */         ificmpgt(paramLabel);
/*      */         return;
/*      */       case 164:
/*  629 */         ificmple(paramLabel);
/*      */         return;
/*      */       case 165:
/*  632 */         ifacmpeq(paramLabel);
/*      */         return;
/*      */       case 166:
/*  635 */         ifacmpne(paramLabel);
/*      */         return;
/*      */       case 167:
/*  638 */         goTo(paramLabel);
/*      */         return;
/*      */       case 168:
/*  641 */         jsr(paramLabel);
/*      */         return;
/*      */       case 198:
/*  644 */         ifnull(paramLabel);
/*      */         return;
/*      */       case 199:
/*  647 */         ifnonnull(paramLabel);
/*      */         return;
/*      */     } 
/*  650 */     throw new IllegalArgumentException();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLabel(Label paramLabel) {
/*  656 */     mark(paramLabel);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLdcInsn(Object paramObject) {
/*  661 */     if (paramObject instanceof Integer) {
/*  662 */       int i = ((Integer)paramObject).intValue();
/*  663 */       iconst(i);
/*  664 */     } else if (paramObject instanceof Byte) {
/*  665 */       int i = ((Byte)paramObject).intValue();
/*  666 */       iconst(i);
/*  667 */     } else if (paramObject instanceof Character) {
/*  668 */       char c = ((Character)paramObject).charValue();
/*  669 */       iconst(c);
/*  670 */     } else if (paramObject instanceof Short) {
/*  671 */       int i = ((Short)paramObject).intValue();
/*  672 */       iconst(i);
/*  673 */     } else if (paramObject instanceof Boolean) {
/*  674 */       boolean bool = ((Boolean)paramObject).booleanValue() ? true : false;
/*  675 */       iconst(bool);
/*  676 */     } else if (paramObject instanceof Float) {
/*  677 */       float f = ((Float)paramObject).floatValue();
/*  678 */       fconst(f);
/*  679 */     } else if (paramObject instanceof Long) {
/*  680 */       long l = ((Long)paramObject).longValue();
/*  681 */       lconst(l);
/*  682 */     } else if (paramObject instanceof Double) {
/*  683 */       double d = ((Double)paramObject).doubleValue();
/*  684 */       dconst(d);
/*  685 */     } else if (paramObject instanceof String) {
/*  686 */       aconst(paramObject);
/*  687 */     } else if (paramObject instanceof Type) {
/*  688 */       tconst((Type)paramObject);
/*  689 */     } else if (paramObject instanceof Handle) {
/*  690 */       hconst((Handle)paramObject);
/*      */     } else {
/*  692 */       throw new IllegalArgumentException();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIincInsn(int paramInt1, int paramInt2) {
/*  698 */     iinc(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTableSwitchInsn(int paramInt1, int paramInt2, Label paramLabel, Label... paramVarArgs) {
/*  704 */     tableswitch(paramInt1, paramInt2, paramLabel, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLookupSwitchInsn(Label paramLabel, int[] paramArrayOfint, Label[] paramArrayOfLabel) {
/*  710 */     lookupswitch(paramLabel, paramArrayOfint, paramArrayOfLabel);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMultiANewArrayInsn(String paramString, int paramInt) {
/*  715 */     multianewarray(paramString, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void nop() {
/*  721 */     this.mv.visitInsn(0);
/*      */   }
/*      */   
/*      */   public void aconst(Object paramObject) {
/*  725 */     if (paramObject == null) {
/*  726 */       this.mv.visitInsn(1);
/*      */     } else {
/*  728 */       this.mv.visitLdcInsn(paramObject);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void iconst(int paramInt) {
/*  733 */     if (paramInt >= -1 && paramInt <= 5) {
/*  734 */       this.mv.visitInsn(3 + paramInt);
/*  735 */     } else if (paramInt >= -128 && paramInt <= 127) {
/*  736 */       this.mv.visitIntInsn(16, paramInt);
/*  737 */     } else if (paramInt >= -32768 && paramInt <= 32767) {
/*  738 */       this.mv.visitIntInsn(17, paramInt);
/*      */     } else {
/*  740 */       this.mv.visitLdcInsn(Integer.valueOf(paramInt));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void lconst(long paramLong) {
/*  745 */     if (paramLong == 0L || paramLong == 1L) {
/*  746 */       this.mv.visitInsn(9 + (int)paramLong);
/*      */     } else {
/*  748 */       this.mv.visitLdcInsn(Long.valueOf(paramLong));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void fconst(float paramFloat) {
/*  753 */     int i = Float.floatToIntBits(paramFloat);
/*  754 */     if (i == 0L || i == 1065353216 || i == 1073741824) {
/*  755 */       this.mv.visitInsn(11 + (int)paramFloat);
/*      */     } else {
/*  757 */       this.mv.visitLdcInsn(Float.valueOf(paramFloat));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void dconst(double paramDouble) {
/*  762 */     long l = Double.doubleToLongBits(paramDouble);
/*  763 */     if (l == 0L || l == 4607182418800017408L) {
/*  764 */       this.mv.visitInsn(14 + (int)paramDouble);
/*      */     } else {
/*  766 */       this.mv.visitLdcInsn(Double.valueOf(paramDouble));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void tconst(Type paramType) {
/*  771 */     this.mv.visitLdcInsn(paramType);
/*      */   }
/*      */   
/*      */   public void hconst(Handle paramHandle) {
/*  775 */     this.mv.visitLdcInsn(paramHandle);
/*      */   }
/*      */   
/*      */   public void load(int paramInt, Type paramType) {
/*  779 */     this.mv.visitVarInsn(paramType.getOpcode(21), paramInt);
/*      */   }
/*      */   
/*      */   public void aload(Type paramType) {
/*  783 */     this.mv.visitInsn(paramType.getOpcode(46));
/*      */   }
/*      */   
/*      */   public void store(int paramInt, Type paramType) {
/*  787 */     this.mv.visitVarInsn(paramType.getOpcode(54), paramInt);
/*      */   }
/*      */   
/*      */   public void astore(Type paramType) {
/*  791 */     this.mv.visitInsn(paramType.getOpcode(79));
/*      */   }
/*      */   
/*      */   public void pop() {
/*  795 */     this.mv.visitInsn(87);
/*      */   }
/*      */   
/*      */   public void pop2() {
/*  799 */     this.mv.visitInsn(88);
/*      */   }
/*      */   
/*      */   public void dup() {
/*  803 */     this.mv.visitInsn(89);
/*      */   }
/*      */   
/*      */   public void dup2() {
/*  807 */     this.mv.visitInsn(92);
/*      */   }
/*      */   
/*      */   public void dupX1() {
/*  811 */     this.mv.visitInsn(90);
/*      */   }
/*      */   
/*      */   public void dupX2() {
/*  815 */     this.mv.visitInsn(91);
/*      */   }
/*      */   
/*      */   public void dup2X1() {
/*  819 */     this.mv.visitInsn(93);
/*      */   }
/*      */   
/*      */   public void dup2X2() {
/*  823 */     this.mv.visitInsn(94);
/*      */   }
/*      */   
/*      */   public void swap() {
/*  827 */     this.mv.visitInsn(95);
/*      */   }
/*      */   
/*      */   public void add(Type paramType) {
/*  831 */     this.mv.visitInsn(paramType.getOpcode(96));
/*      */   }
/*      */   
/*      */   public void sub(Type paramType) {
/*  835 */     this.mv.visitInsn(paramType.getOpcode(100));
/*      */   }
/*      */   
/*      */   public void mul(Type paramType) {
/*  839 */     this.mv.visitInsn(paramType.getOpcode(104));
/*      */   }
/*      */   
/*      */   public void div(Type paramType) {
/*  843 */     this.mv.visitInsn(paramType.getOpcode(108));
/*      */   }
/*      */   
/*      */   public void rem(Type paramType) {
/*  847 */     this.mv.visitInsn(paramType.getOpcode(112));
/*      */   }
/*      */   
/*      */   public void neg(Type paramType) {
/*  851 */     this.mv.visitInsn(paramType.getOpcode(116));
/*      */   }
/*      */   
/*      */   public void shl(Type paramType) {
/*  855 */     this.mv.visitInsn(paramType.getOpcode(120));
/*      */   }
/*      */   
/*      */   public void shr(Type paramType) {
/*  859 */     this.mv.visitInsn(paramType.getOpcode(122));
/*      */   }
/*      */   
/*      */   public void ushr(Type paramType) {
/*  863 */     this.mv.visitInsn(paramType.getOpcode(124));
/*      */   }
/*      */   
/*      */   public void and(Type paramType) {
/*  867 */     this.mv.visitInsn(paramType.getOpcode(126));
/*      */   }
/*      */   
/*      */   public void or(Type paramType) {
/*  871 */     this.mv.visitInsn(paramType.getOpcode(128));
/*      */   }
/*      */   
/*      */   public void xor(Type paramType) {
/*  875 */     this.mv.visitInsn(paramType.getOpcode(130));
/*      */   }
/*      */   
/*      */   public void iinc(int paramInt1, int paramInt2) {
/*  879 */     this.mv.visitIincInsn(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void cast(Type paramType1, Type paramType2) {
/*  883 */     if (paramType1 != paramType2) {
/*  884 */       if (paramType1 == Type.DOUBLE_TYPE) {
/*  885 */         if (paramType2 == Type.FLOAT_TYPE) {
/*  886 */           this.mv.visitInsn(144);
/*  887 */         } else if (paramType2 == Type.LONG_TYPE) {
/*  888 */           this.mv.visitInsn(143);
/*      */         } else {
/*  890 */           this.mv.visitInsn(142);
/*  891 */           cast(Type.INT_TYPE, paramType2);
/*      */         } 
/*  893 */       } else if (paramType1 == Type.FLOAT_TYPE) {
/*  894 */         if (paramType2 == Type.DOUBLE_TYPE) {
/*  895 */           this.mv.visitInsn(141);
/*  896 */         } else if (paramType2 == Type.LONG_TYPE) {
/*  897 */           this.mv.visitInsn(140);
/*      */         } else {
/*  899 */           this.mv.visitInsn(139);
/*  900 */           cast(Type.INT_TYPE, paramType2);
/*      */         } 
/*  902 */       } else if (paramType1 == Type.LONG_TYPE) {
/*  903 */         if (paramType2 == Type.DOUBLE_TYPE) {
/*  904 */           this.mv.visitInsn(138);
/*  905 */         } else if (paramType2 == Type.FLOAT_TYPE) {
/*  906 */           this.mv.visitInsn(137);
/*      */         } else {
/*  908 */           this.mv.visitInsn(136);
/*  909 */           cast(Type.INT_TYPE, paramType2);
/*      */         }
/*      */       
/*  912 */       } else if (paramType2 == Type.BYTE_TYPE) {
/*  913 */         this.mv.visitInsn(145);
/*  914 */       } else if (paramType2 == Type.CHAR_TYPE) {
/*  915 */         this.mv.visitInsn(146);
/*  916 */       } else if (paramType2 == Type.DOUBLE_TYPE) {
/*  917 */         this.mv.visitInsn(135);
/*  918 */       } else if (paramType2 == Type.FLOAT_TYPE) {
/*  919 */         this.mv.visitInsn(134);
/*  920 */       } else if (paramType2 == Type.LONG_TYPE) {
/*  921 */         this.mv.visitInsn(133);
/*  922 */       } else if (paramType2 == Type.SHORT_TYPE) {
/*  923 */         this.mv.visitInsn(147);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void lcmp() {
/*  930 */     this.mv.visitInsn(148);
/*      */   }
/*      */   
/*      */   public void cmpl(Type paramType) {
/*  934 */     this.mv.visitInsn((paramType == Type.FLOAT_TYPE) ? 149 : 151);
/*      */   }
/*      */   
/*      */   public void cmpg(Type paramType) {
/*  938 */     this.mv.visitInsn((paramType == Type.FLOAT_TYPE) ? 150 : 152);
/*      */   }
/*      */   
/*      */   public void ifeq(Label paramLabel) {
/*  942 */     this.mv.visitJumpInsn(153, paramLabel);
/*      */   }
/*      */   
/*      */   public void ifne(Label paramLabel) {
/*  946 */     this.mv.visitJumpInsn(154, paramLabel);
/*      */   }
/*      */   
/*      */   public void iflt(Label paramLabel) {
/*  950 */     this.mv.visitJumpInsn(155, paramLabel);
/*      */   }
/*      */   
/*      */   public void ifge(Label paramLabel) {
/*  954 */     this.mv.visitJumpInsn(156, paramLabel);
/*      */   }
/*      */   
/*      */   public void ifgt(Label paramLabel) {
/*  958 */     this.mv.visitJumpInsn(157, paramLabel);
/*      */   }
/*      */   
/*      */   public void ifle(Label paramLabel) {
/*  962 */     this.mv.visitJumpInsn(158, paramLabel);
/*      */   }
/*      */   
/*      */   public void ificmpeq(Label paramLabel) {
/*  966 */     this.mv.visitJumpInsn(159, paramLabel);
/*      */   }
/*      */   
/*      */   public void ificmpne(Label paramLabel) {
/*  970 */     this.mv.visitJumpInsn(160, paramLabel);
/*      */   }
/*      */   
/*      */   public void ificmplt(Label paramLabel) {
/*  974 */     this.mv.visitJumpInsn(161, paramLabel);
/*      */   }
/*      */   
/*      */   public void ificmpge(Label paramLabel) {
/*  978 */     this.mv.visitJumpInsn(162, paramLabel);
/*      */   }
/*      */   
/*      */   public void ificmpgt(Label paramLabel) {
/*  982 */     this.mv.visitJumpInsn(163, paramLabel);
/*      */   }
/*      */   
/*      */   public void ificmple(Label paramLabel) {
/*  986 */     this.mv.visitJumpInsn(164, paramLabel);
/*      */   }
/*      */   
/*      */   public void ifacmpeq(Label paramLabel) {
/*  990 */     this.mv.visitJumpInsn(165, paramLabel);
/*      */   }
/*      */   
/*      */   public void ifacmpne(Label paramLabel) {
/*  994 */     this.mv.visitJumpInsn(166, paramLabel);
/*      */   }
/*      */   
/*      */   public void goTo(Label paramLabel) {
/*  998 */     this.mv.visitJumpInsn(167, paramLabel);
/*      */   }
/*      */   
/*      */   public void jsr(Label paramLabel) {
/* 1002 */     this.mv.visitJumpInsn(168, paramLabel);
/*      */   }
/*      */   
/*      */   public void ret(int paramInt) {
/* 1006 */     this.mv.visitVarInsn(169, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void tableswitch(int paramInt1, int paramInt2, Label paramLabel, Label... paramVarArgs) {
/* 1011 */     this.mv.visitTableSwitchInsn(paramInt1, paramInt2, paramLabel, paramVarArgs);
/*      */   }
/*      */ 
/*      */   
/*      */   public void lookupswitch(Label paramLabel, int[] paramArrayOfint, Label[] paramArrayOfLabel) {
/* 1016 */     this.mv.visitLookupSwitchInsn(paramLabel, paramArrayOfint, paramArrayOfLabel);
/*      */   }
/*      */   
/*      */   public void areturn(Type paramType) {
/* 1020 */     this.mv.visitInsn(paramType.getOpcode(172));
/*      */   }
/*      */ 
/*      */   
/*      */   public void getstatic(String paramString1, String paramString2, String paramString3) {
/* 1025 */     this.mv.visitFieldInsn(178, paramString1, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */   
/*      */   public void putstatic(String paramString1, String paramString2, String paramString3) {
/* 1030 */     this.mv.visitFieldInsn(179, paramString1, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */   
/*      */   public void getfield(String paramString1, String paramString2, String paramString3) {
/* 1035 */     this.mv.visitFieldInsn(180, paramString1, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */   
/*      */   public void putfield(String paramString1, String paramString2, String paramString3) {
/* 1040 */     this.mv.visitFieldInsn(181, paramString1, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void invokevirtual(String paramString1, String paramString2, String paramString3) {
/* 1046 */     if (this.api >= 327680) {
/* 1047 */       invokevirtual(paramString1, paramString2, paramString3, false);
/*      */       return;
/*      */     } 
/* 1050 */     this.mv.visitMethodInsn(182, paramString1, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */   
/*      */   public void invokevirtual(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 1055 */     if (this.api < 327680) {
/* 1056 */       if (paramBoolean) {
/* 1057 */         throw new IllegalArgumentException("INVOKEVIRTUAL on interfaces require ASM 5");
/*      */       }
/*      */       
/* 1060 */       invokevirtual(paramString1, paramString2, paramString3);
/*      */       return;
/*      */     } 
/* 1063 */     this.mv.visitMethodInsn(182, paramString1, paramString2, paramString3, paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void invokespecial(String paramString1, String paramString2, String paramString3) {
/* 1069 */     if (this.api >= 327680) {
/* 1070 */       invokespecial(paramString1, paramString2, paramString3, false);
/*      */       return;
/*      */     } 
/* 1073 */     this.mv.visitMethodInsn(183, paramString1, paramString2, paramString3, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void invokespecial(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 1078 */     if (this.api < 327680) {
/* 1079 */       if (paramBoolean) {
/* 1080 */         throw new IllegalArgumentException("INVOKESPECIAL on interfaces require ASM 5");
/*      */       }
/*      */       
/* 1083 */       invokespecial(paramString1, paramString2, paramString3);
/*      */       return;
/*      */     } 
/* 1086 */     this.mv.visitMethodInsn(183, paramString1, paramString2, paramString3, paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void invokestatic(String paramString1, String paramString2, String paramString3) {
/* 1092 */     if (this.api >= 327680) {
/* 1093 */       invokestatic(paramString1, paramString2, paramString3, false);
/*      */       return;
/*      */     } 
/* 1096 */     this.mv.visitMethodInsn(184, paramString1, paramString2, paramString3, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void invokestatic(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 1101 */     if (this.api < 327680) {
/* 1102 */       if (paramBoolean) {
/* 1103 */         throw new IllegalArgumentException("INVOKESTATIC on interfaces require ASM 5");
/*      */       }
/*      */       
/* 1106 */       invokestatic(paramString1, paramString2, paramString3);
/*      */       return;
/*      */     } 
/* 1109 */     this.mv.visitMethodInsn(184, paramString1, paramString2, paramString3, paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   public void invokeinterface(String paramString1, String paramString2, String paramString3) {
/* 1114 */     this.mv.visitMethodInsn(185, paramString1, paramString2, paramString3, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void invokedynamic(String paramString1, String paramString2, Handle paramHandle, Object[] paramArrayOfObject) {
/* 1119 */     this.mv.visitInvokeDynamicInsn(paramString1, paramString2, paramHandle, paramArrayOfObject);
/*      */   }
/*      */   
/*      */   public void anew(Type paramType) {
/* 1123 */     this.mv.visitTypeInsn(187, paramType.getInternalName());
/*      */   }
/*      */   
/*      */   public void newarray(Type paramType) {
/*      */     byte b;
/* 1128 */     switch (paramType.getSort()) {
/*      */       case 1:
/* 1130 */         b = 4;
/*      */         break;
/*      */       case 2:
/* 1133 */         b = 5;
/*      */         break;
/*      */       case 3:
/* 1136 */         b = 8;
/*      */         break;
/*      */       case 4:
/* 1139 */         b = 9;
/*      */         break;
/*      */       case 5:
/* 1142 */         b = 10;
/*      */         break;
/*      */       case 6:
/* 1145 */         b = 6;
/*      */         break;
/*      */       case 7:
/* 1148 */         b = 11;
/*      */         break;
/*      */       case 8:
/* 1151 */         b = 7;
/*      */         break;
/*      */       default:
/* 1154 */         this.mv.visitTypeInsn(189, paramType.getInternalName());
/*      */         return;
/*      */     } 
/* 1157 */     this.mv.visitIntInsn(188, b);
/*      */   }
/*      */   
/*      */   public void arraylength() {
/* 1161 */     this.mv.visitInsn(190);
/*      */   }
/*      */   
/*      */   public void athrow() {
/* 1165 */     this.mv.visitInsn(191);
/*      */   }
/*      */   
/*      */   public void checkcast(Type paramType) {
/* 1169 */     this.mv.visitTypeInsn(192, paramType.getInternalName());
/*      */   }
/*      */   
/*      */   public void instanceOf(Type paramType) {
/* 1173 */     this.mv.visitTypeInsn(193, paramType.getInternalName());
/*      */   }
/*      */   
/*      */   public void monitorenter() {
/* 1177 */     this.mv.visitInsn(194);
/*      */   }
/*      */   
/*      */   public void monitorexit() {
/* 1181 */     this.mv.visitInsn(195);
/*      */   }
/*      */   
/*      */   public void multianewarray(String paramString, int paramInt) {
/* 1185 */     this.mv.visitMultiANewArrayInsn(paramString, paramInt);
/*      */   }
/*      */   
/*      */   public void ifnull(Label paramLabel) {
/* 1189 */     this.mv.visitJumpInsn(198, paramLabel);
/*      */   }
/*      */   
/*      */   public void ifnonnull(Label paramLabel) {
/* 1193 */     this.mv.visitJumpInsn(199, paramLabel);
/*      */   }
/*      */   
/*      */   public void mark(Label paramLabel) {
/* 1197 */     this.mv.visitLabel(paramLabel);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/InstructionAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */