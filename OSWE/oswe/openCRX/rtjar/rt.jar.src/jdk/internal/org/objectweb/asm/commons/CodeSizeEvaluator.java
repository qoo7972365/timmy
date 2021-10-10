/*     */ package jdk.internal.org.objectweb.asm.commons;
/*     */ 
/*     */ import jdk.internal.org.objectweb.asm.Handle;
/*     */ import jdk.internal.org.objectweb.asm.Label;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Opcodes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CodeSizeEvaluator
/*     */   extends MethodVisitor
/*     */   implements Opcodes
/*     */ {
/*     */   private int minSize;
/*     */   private int maxSize;
/*     */   
/*     */   public CodeSizeEvaluator(MethodVisitor paramMethodVisitor) {
/*  78 */     this(327680, paramMethodVisitor);
/*     */   }
/*     */   
/*     */   protected CodeSizeEvaluator(int paramInt, MethodVisitor paramMethodVisitor) {
/*  82 */     super(paramInt, paramMethodVisitor);
/*     */   }
/*     */   
/*     */   public int getMinSize() {
/*  86 */     return this.minSize;
/*     */   }
/*     */   
/*     */   public int getMaxSize() {
/*  90 */     return this.maxSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInsn(int paramInt) {
/*  95 */     this.minSize++;
/*  96 */     this.maxSize++;
/*  97 */     if (this.mv != null) {
/*  98 */       this.mv.visitInsn(paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIntInsn(int paramInt1, int paramInt2) {
/* 104 */     if (paramInt1 == 17) {
/* 105 */       this.minSize += 3;
/* 106 */       this.maxSize += 3;
/*     */     } else {
/* 108 */       this.minSize += 2;
/* 109 */       this.maxSize += 2;
/*     */     } 
/* 111 */     if (this.mv != null) {
/* 112 */       this.mv.visitIntInsn(paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitVarInsn(int paramInt1, int paramInt2) {
/* 118 */     if (paramInt2 < 4 && paramInt1 != 169) {
/* 119 */       this.minSize++;
/* 120 */       this.maxSize++;
/* 121 */     } else if (paramInt2 >= 256) {
/* 122 */       this.minSize += 4;
/* 123 */       this.maxSize += 4;
/*     */     } else {
/* 125 */       this.minSize += 2;
/* 126 */       this.maxSize += 2;
/*     */     } 
/* 128 */     if (this.mv != null) {
/* 129 */       this.mv.visitVarInsn(paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeInsn(int paramInt, String paramString) {
/* 135 */     this.minSize += 3;
/* 136 */     this.maxSize += 3;
/* 137 */     if (this.mv != null) {
/* 138 */       this.mv.visitTypeInsn(paramInt, paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 145 */     this.minSize += 3;
/* 146 */     this.maxSize += 3;
/* 147 */     if (this.mv != null) {
/* 148 */       this.mv.visitFieldInsn(paramInt, paramString1, paramString2, paramString3);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 156 */     if (this.api >= 327680) {
/* 157 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
/*     */       return;
/*     */     } 
/* 160 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, (paramInt == 185));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 167 */     if (this.api < 327680) {
/* 168 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */       return;
/*     */     } 
/* 171 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   private void doVisitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 176 */     if (paramInt == 185) {
/* 177 */       this.minSize += 5;
/* 178 */       this.maxSize += 5;
/*     */     } else {
/* 180 */       this.minSize += 3;
/* 181 */       this.maxSize += 3;
/*     */     } 
/* 183 */     if (this.mv != null) {
/* 184 */       this.mv.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/* 191 */     this.minSize += 5;
/* 192 */     this.maxSize += 5;
/* 193 */     if (this.mv != null) {
/* 194 */       this.mv.visitInvokeDynamicInsn(paramString1, paramString2, paramHandle, paramVarArgs);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitJumpInsn(int paramInt, Label paramLabel) {
/* 200 */     this.minSize += 3;
/* 201 */     if (paramInt == 167 || paramInt == 168) {
/* 202 */       this.maxSize += 5;
/*     */     } else {
/* 204 */       this.maxSize += 8;
/*     */     } 
/* 206 */     if (this.mv != null) {
/* 207 */       this.mv.visitJumpInsn(paramInt, paramLabel);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLdcInsn(Object paramObject) {
/* 213 */     if (paramObject instanceof Long || paramObject instanceof Double) {
/* 214 */       this.minSize += 3;
/* 215 */       this.maxSize += 3;
/*     */     } else {
/* 217 */       this.minSize += 2;
/* 218 */       this.maxSize += 3;
/*     */     } 
/* 220 */     if (this.mv != null) {
/* 221 */       this.mv.visitLdcInsn(paramObject);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIincInsn(int paramInt1, int paramInt2) {
/* 227 */     if (paramInt1 > 255 || paramInt2 > 127 || paramInt2 < -128) {
/* 228 */       this.minSize += 6;
/* 229 */       this.maxSize += 6;
/*     */     } else {
/* 231 */       this.minSize += 3;
/* 232 */       this.maxSize += 3;
/*     */     } 
/* 234 */     if (this.mv != null) {
/* 235 */       this.mv.visitIincInsn(paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTableSwitchInsn(int paramInt1, int paramInt2, Label paramLabel, Label... paramVarArgs) {
/* 242 */     this.minSize += 13 + paramVarArgs.length * 4;
/* 243 */     this.maxSize += 16 + paramVarArgs.length * 4;
/* 244 */     if (this.mv != null) {
/* 245 */       this.mv.visitTableSwitchInsn(paramInt1, paramInt2, paramLabel, paramVarArgs);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLookupSwitchInsn(Label paramLabel, int[] paramArrayOfint, Label[] paramArrayOfLabel) {
/* 252 */     this.minSize += 9 + paramArrayOfint.length * 8;
/* 253 */     this.maxSize += 12 + paramArrayOfint.length * 8;
/* 254 */     if (this.mv != null) {
/* 255 */       this.mv.visitLookupSwitchInsn(paramLabel, paramArrayOfint, paramArrayOfLabel);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMultiANewArrayInsn(String paramString, int paramInt) {
/* 261 */     this.minSize += 4;
/* 262 */     this.maxSize += 4;
/* 263 */     if (this.mv != null)
/* 264 */       this.mv.visitMultiANewArrayInsn(paramString, paramInt); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/CodeSizeEvaluator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */