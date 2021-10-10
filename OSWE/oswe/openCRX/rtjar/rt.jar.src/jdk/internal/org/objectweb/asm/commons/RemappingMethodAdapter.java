/*     */ package jdk.internal.org.objectweb.asm.commons;
/*     */ 
/*     */ import jdk.internal.org.objectweb.asm.AnnotationVisitor;
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
/*     */ 
/*     */ public class RemappingMethodAdapter
/*     */   extends LocalVariablesSorter
/*     */ {
/*     */   protected final Remapper remapper;
/*     */   
/*     */   public RemappingMethodAdapter(int paramInt, String paramString, MethodVisitor paramMethodVisitor, Remapper paramRemapper) {
/*  80 */     this(327680, paramInt, paramString, paramMethodVisitor, paramRemapper);
/*     */   }
/*     */ 
/*     */   
/*     */   protected RemappingMethodAdapter(int paramInt1, int paramInt2, String paramString, MethodVisitor paramMethodVisitor, Remapper paramRemapper) {
/*  85 */     super(paramInt1, paramInt2, paramString, paramMethodVisitor);
/*  86 */     this.remapper = paramRemapper;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotationDefault() {
/*  91 */     AnnotationVisitor annotationVisitor = super.visitAnnotationDefault();
/*  92 */     return (annotationVisitor == null) ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/*  97 */     AnnotationVisitor annotationVisitor = super.visitAnnotation(this.remapper.mapDesc(paramString), paramBoolean);
/*     */     
/*  99 */     return (annotationVisitor == null) ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 105 */     AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(paramInt, paramTypePath, this.remapper
/* 106 */         .mapDesc(paramString), paramBoolean);
/* 107 */     return (annotationVisitor == null) ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitParameterAnnotation(int paramInt, String paramString, boolean paramBoolean) {
/* 113 */     AnnotationVisitor annotationVisitor = super.visitParameterAnnotation(paramInt, this.remapper
/* 114 */         .mapDesc(paramString), paramBoolean);
/* 115 */     return (annotationVisitor == null) ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFrame(int paramInt1, int paramInt2, Object[] paramArrayOfObject1, int paramInt3, Object[] paramArrayOfObject2) {
/* 121 */     super.visitFrame(paramInt1, paramInt2, remapEntries(paramInt2, paramArrayOfObject1), paramInt3, 
/* 122 */         remapEntries(paramInt3, paramArrayOfObject2));
/*     */   }
/*     */   
/*     */   private Object[] remapEntries(int paramInt, Object[] paramArrayOfObject) {
/* 126 */     for (byte b = 0; b < paramInt; b++) {
/* 127 */       if (paramArrayOfObject[b] instanceof String) {
/* 128 */         Object[] arrayOfObject = new Object[paramInt];
/* 129 */         if (b > 0) {
/* 130 */           System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, b);
/*     */         }
/*     */         while (true) {
/* 133 */           Object object = paramArrayOfObject[b];
/* 134 */           arrayOfObject[b++] = (object instanceof String) ? this.remapper
/* 135 */             .mapType((String)object) : object;
/* 136 */           if (b >= paramInt)
/* 137 */             return arrayOfObject; 
/*     */         } 
/*     */       } 
/* 140 */     }  return paramArrayOfObject;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 146 */     super.visitFieldInsn(paramInt, this.remapper.mapType(paramString1), this.remapper
/* 147 */         .mapFieldName(paramString1, paramString2, paramString3), this.remapper
/* 148 */         .mapDesc(paramString3));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 155 */     if (this.api >= 327680) {
/* 156 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
/*     */       return;
/*     */     } 
/* 159 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, (paramInt == 185));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 166 */     if (this.api < 327680) {
/* 167 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */       return;
/*     */     } 
/* 170 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doVisitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 181 */     if (this.mv != null) {
/* 182 */       this.mv.visitMethodInsn(paramInt, this.remapper.mapType(paramString1), this.remapper
/* 183 */           .mapMethodName(paramString1, paramString2, paramString3), this.remapper
/* 184 */           .mapMethodDesc(paramString3), paramBoolean);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/* 191 */     for (byte b = 0; b < paramVarArgs.length; b++) {
/* 192 */       paramVarArgs[b] = this.remapper.mapValue(paramVarArgs[b]);
/*     */     }
/* 194 */     super.visitInvokeDynamicInsn(this.remapper
/* 195 */         .mapInvokeDynamicMethodName(paramString1, paramString2), this.remapper
/* 196 */         .mapMethodDesc(paramString2), (Handle)this.remapper.mapValue(paramHandle), paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTypeInsn(int paramInt, String paramString) {
/* 202 */     super.visitTypeInsn(paramInt, this.remapper.mapType(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLdcInsn(Object paramObject) {
/* 207 */     super.visitLdcInsn(this.remapper.mapValue(paramObject));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMultiANewArrayInsn(String paramString, int paramInt) {
/* 212 */     super.visitMultiANewArrayInsn(this.remapper.mapDesc(paramString), paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitInsnAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 218 */     AnnotationVisitor annotationVisitor = super.visitInsnAnnotation(paramInt, paramTypePath, this.remapper
/* 219 */         .mapDesc(paramString), paramBoolean);
/* 220 */     return (annotationVisitor == null) ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTryCatchBlock(Label paramLabel1, Label paramLabel2, Label paramLabel3, String paramString) {
/* 226 */     super.visitTryCatchBlock(paramLabel1, paramLabel2, paramLabel3, (paramString == null) ? null : this.remapper
/* 227 */         .mapType(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTryCatchAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 233 */     AnnotationVisitor annotationVisitor = super.visitTryCatchAnnotation(paramInt, paramTypePath, this.remapper
/* 234 */         .mapDesc(paramString), paramBoolean);
/* 235 */     return (annotationVisitor == null) ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLocalVariable(String paramString1, String paramString2, String paramString3, Label paramLabel1, Label paramLabel2, int paramInt) {
/* 241 */     super.visitLocalVariable(paramString1, this.remapper.mapDesc(paramString2), this.remapper
/* 242 */         .mapSignature(paramString3, true), paramLabel1, paramLabel2, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitLocalVariableAnnotation(int paramInt, TypePath paramTypePath, Label[] paramArrayOfLabel1, Label[] paramArrayOfLabel2, int[] paramArrayOfint, String paramString, boolean paramBoolean) {
/* 249 */     AnnotationVisitor annotationVisitor = super.visitLocalVariableAnnotation(paramInt, paramTypePath, paramArrayOfLabel1, paramArrayOfLabel2, paramArrayOfint, this.remapper
/* 250 */         .mapDesc(paramString), paramBoolean);
/* 251 */     return (annotationVisitor == null) ? annotationVisitor : new RemappingAnnotationAdapter(annotationVisitor, this.remapper);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/RemappingMethodAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */