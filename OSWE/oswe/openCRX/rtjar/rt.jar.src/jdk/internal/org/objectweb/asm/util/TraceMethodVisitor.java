/*     */ package jdk.internal.org.objectweb.asm.util;
/*     */ 
/*     */ import jdk.internal.org.objectweb.asm.AnnotationVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Attribute;
/*     */ import jdk.internal.org.objectweb.asm.Handle;
/*     */ import jdk.internal.org.objectweb.asm.Label;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
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
/*     */ public final class TraceMethodVisitor
/*     */   extends MethodVisitor
/*     */ {
/*     */   public final Printer p;
/*     */   
/*     */   public TraceMethodVisitor(Printer paramPrinter) {
/*  80 */     this((MethodVisitor)null, paramPrinter);
/*     */   }
/*     */   
/*     */   public TraceMethodVisitor(MethodVisitor paramMethodVisitor, Printer paramPrinter) {
/*  84 */     super(327680, paramMethodVisitor);
/*  85 */     this.p = paramPrinter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitParameter(String paramString, int paramInt) {
/*  90 */     this.p.visitParameter(paramString, paramInt);
/*  91 */     super.visitParameter(paramString, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/*  97 */     Printer printer = this.p.visitMethodAnnotation(paramString, paramBoolean);
/*  98 */     AnnotationVisitor annotationVisitor = (this.mv == null) ? null : this.mv.visitAnnotation(paramString, paramBoolean);
/*     */     
/* 100 */     return new TraceAnnotationVisitor(annotationVisitor, printer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 106 */     Printer printer = this.p.visitMethodTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*     */     
/* 108 */     AnnotationVisitor annotationVisitor = (this.mv == null) ? null : this.mv.visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*     */     
/* 110 */     return new TraceAnnotationVisitor(annotationVisitor, printer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute paramAttribute) {
/* 115 */     this.p.visitMethodAttribute(paramAttribute);
/* 116 */     super.visitAttribute(paramAttribute);
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotationDefault() {
/* 121 */     Printer printer = this.p.visitAnnotationDefault();
/* 122 */     AnnotationVisitor annotationVisitor = (this.mv == null) ? null : this.mv.visitAnnotationDefault();
/* 123 */     return new TraceAnnotationVisitor(annotationVisitor, printer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitParameterAnnotation(int paramInt, String paramString, boolean paramBoolean) {
/* 129 */     Printer printer = this.p.visitParameterAnnotation(paramInt, paramString, paramBoolean);
/* 130 */     AnnotationVisitor annotationVisitor = (this.mv == null) ? null : this.mv.visitParameterAnnotation(paramInt, paramString, paramBoolean);
/*     */     
/* 132 */     return new TraceAnnotationVisitor(annotationVisitor, printer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitCode() {
/* 137 */     this.p.visitCode();
/* 138 */     super.visitCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFrame(int paramInt1, int paramInt2, Object[] paramArrayOfObject1, int paramInt3, Object[] paramArrayOfObject2) {
/* 144 */     this.p.visitFrame(paramInt1, paramInt2, paramArrayOfObject1, paramInt3, paramArrayOfObject2);
/* 145 */     super.visitFrame(paramInt1, paramInt2, paramArrayOfObject1, paramInt3, paramArrayOfObject2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInsn(int paramInt) {
/* 150 */     this.p.visitInsn(paramInt);
/* 151 */     super.visitInsn(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIntInsn(int paramInt1, int paramInt2) {
/* 156 */     this.p.visitIntInsn(paramInt1, paramInt2);
/* 157 */     super.visitIntInsn(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitVarInsn(int paramInt1, int paramInt2) {
/* 162 */     this.p.visitVarInsn(paramInt1, paramInt2);
/* 163 */     super.visitVarInsn(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeInsn(int paramInt, String paramString) {
/* 168 */     this.p.visitTypeInsn(paramInt, paramString);
/* 169 */     super.visitTypeInsn(paramInt, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 175 */     this.p.visitFieldInsn(paramInt, paramString1, paramString2, paramString3);
/* 176 */     super.visitFieldInsn(paramInt, paramString1, paramString2, paramString3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 183 */     if (this.api >= 327680) {
/* 184 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
/*     */       return;
/*     */     } 
/* 187 */     this.p.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
/* 188 */     if (this.mv != null) {
/* 189 */       this.mv.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 196 */     if (this.api < 327680) {
/* 197 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */       return;
/*     */     } 
/* 200 */     this.p.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/* 201 */     if (this.mv != null) {
/* 202 */       this.mv.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/* 209 */     this.p.visitInvokeDynamicInsn(paramString1, paramString2, paramHandle, paramVarArgs);
/* 210 */     super.visitInvokeDynamicInsn(paramString1, paramString2, paramHandle, paramVarArgs);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitJumpInsn(int paramInt, Label paramLabel) {
/* 215 */     this.p.visitJumpInsn(paramInt, paramLabel);
/* 216 */     super.visitJumpInsn(paramInt, paramLabel);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLabel(Label paramLabel) {
/* 221 */     this.p.visitLabel(paramLabel);
/* 222 */     super.visitLabel(paramLabel);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLdcInsn(Object paramObject) {
/* 227 */     this.p.visitLdcInsn(paramObject);
/* 228 */     super.visitLdcInsn(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIincInsn(int paramInt1, int paramInt2) {
/* 233 */     this.p.visitIincInsn(paramInt1, paramInt2);
/* 234 */     super.visitIincInsn(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTableSwitchInsn(int paramInt1, int paramInt2, Label paramLabel, Label... paramVarArgs) {
/* 240 */     this.p.visitTableSwitchInsn(paramInt1, paramInt2, paramLabel, paramVarArgs);
/* 241 */     super.visitTableSwitchInsn(paramInt1, paramInt2, paramLabel, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLookupSwitchInsn(Label paramLabel, int[] paramArrayOfint, Label[] paramArrayOfLabel) {
/* 247 */     this.p.visitLookupSwitchInsn(paramLabel, paramArrayOfint, paramArrayOfLabel);
/* 248 */     super.visitLookupSwitchInsn(paramLabel, paramArrayOfint, paramArrayOfLabel);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMultiANewArrayInsn(String paramString, int paramInt) {
/* 253 */     this.p.visitMultiANewArrayInsn(paramString, paramInt);
/* 254 */     super.visitMultiANewArrayInsn(paramString, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitInsnAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 261 */     Printer printer = this.p.visitInsnAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/* 262 */     AnnotationVisitor annotationVisitor = (this.mv == null) ? null : this.mv.visitInsnAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*     */     
/* 264 */     return new TraceAnnotationVisitor(annotationVisitor, printer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTryCatchBlock(Label paramLabel1, Label paramLabel2, Label paramLabel3, String paramString) {
/* 270 */     this.p.visitTryCatchBlock(paramLabel1, paramLabel2, paramLabel3, paramString);
/* 271 */     super.visitTryCatchBlock(paramLabel1, paramLabel2, paramLabel3, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTryCatchAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 277 */     Printer printer = this.p.visitTryCatchAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*     */     
/* 279 */     AnnotationVisitor annotationVisitor = (this.mv == null) ? null : this.mv.visitTryCatchAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*     */     
/* 281 */     return new TraceAnnotationVisitor(annotationVisitor, printer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLocalVariable(String paramString1, String paramString2, String paramString3, Label paramLabel1, Label paramLabel2, int paramInt) {
/* 288 */     this.p.visitLocalVariable(paramString1, paramString2, paramString3, paramLabel1, paramLabel2, paramInt);
/* 289 */     super.visitLocalVariable(paramString1, paramString2, paramString3, paramLabel1, paramLabel2, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitLocalVariableAnnotation(int paramInt, TypePath paramTypePath, Label[] paramArrayOfLabel1, Label[] paramArrayOfLabel2, int[] paramArrayOfint, String paramString, boolean paramBoolean) {
/* 296 */     Printer printer = this.p.visitLocalVariableAnnotation(paramInt, paramTypePath, paramArrayOfLabel1, paramArrayOfLabel2, paramArrayOfint, paramString, paramBoolean);
/*     */ 
/*     */     
/* 299 */     AnnotationVisitor annotationVisitor = (this.mv == null) ? null : this.mv.visitLocalVariableAnnotation(paramInt, paramTypePath, paramArrayOfLabel1, paramArrayOfLabel2, paramArrayOfint, paramString, paramBoolean);
/*     */     
/* 301 */     return new TraceAnnotationVisitor(annotationVisitor, printer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLineNumber(int paramInt, Label paramLabel) {
/* 306 */     this.p.visitLineNumber(paramInt, paramLabel);
/* 307 */     super.visitLineNumber(paramInt, paramLabel);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMaxs(int paramInt1, int paramInt2) {
/* 312 */     this.p.visitMaxs(paramInt1, paramInt2);
/* 313 */     super.visitMaxs(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 318 */     this.p.visitMethodEnd();
/* 319 */     super.visitEnd();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/TraceMethodVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */