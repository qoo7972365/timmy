/*     */ package jdk.internal.org.objectweb.asm.commons;
/*     */ 
/*     */ import jdk.internal.org.objectweb.asm.AnnotationVisitor;
/*     */ import jdk.internal.org.objectweb.asm.ClassVisitor;
/*     */ import jdk.internal.org.objectweb.asm.FieldVisitor;
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
/*     */ public class RemappingClassAdapter
/*     */   extends ClassVisitor
/*     */ {
/*     */   protected final Remapper remapper;
/*     */   protected String className;
/*     */   
/*     */   public RemappingClassAdapter(ClassVisitor paramClassVisitor, Remapper paramRemapper) {
/*  81 */     this(327680, paramClassVisitor, paramRemapper);
/*     */   }
/*     */ 
/*     */   
/*     */   protected RemappingClassAdapter(int paramInt, ClassVisitor paramClassVisitor, Remapper paramRemapper) {
/*  86 */     super(paramInt, paramClassVisitor);
/*  87 */     this.remapper = paramRemapper;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*  93 */     this.className = paramString1;
/*  94 */     super.visit(paramInt1, paramInt2, this.remapper.mapType(paramString1), this.remapper
/*  95 */         .mapSignature(paramString2, false), this.remapper.mapType(paramString3), (paramArrayOfString == null) ? null : this.remapper
/*  96 */         .mapTypes(paramArrayOfString));
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/* 101 */     AnnotationVisitor annotationVisitor = super.visitAnnotation(this.remapper.mapDesc(paramString), paramBoolean);
/*     */     
/* 103 */     return (annotationVisitor == null) ? null : createRemappingAnnotationAdapter(annotationVisitor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 109 */     AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(paramInt, paramTypePath, this.remapper
/* 110 */         .mapDesc(paramString), paramBoolean);
/* 111 */     return (annotationVisitor == null) ? null : createRemappingAnnotationAdapter(annotationVisitor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor visitField(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject) {
/* 117 */     FieldVisitor fieldVisitor = super.visitField(paramInt, this.remapper
/* 118 */         .mapFieldName(this.className, paramString1, paramString2), this.remapper
/* 119 */         .mapDesc(paramString2), this.remapper.mapSignature(paramString3, true), this.remapper
/* 120 */         .mapValue(paramObject));
/* 121 */     return (fieldVisitor == null) ? null : createRemappingFieldAdapter(fieldVisitor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/* 127 */     String str = this.remapper.mapMethodDesc(paramString2);
/* 128 */     MethodVisitor methodVisitor = super.visitMethod(paramInt, this.remapper.mapMethodName(this.className, paramString1, paramString2), str, this.remapper
/* 129 */         .mapSignature(paramString3, false), (paramArrayOfString == null) ? null : this.remapper
/*     */         
/* 131 */         .mapTypes(paramArrayOfString));
/* 132 */     return (methodVisitor == null) ? null : createRemappingMethodAdapter(paramInt, str, methodVisitor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInnerClass(String paramString1, String paramString2, String paramString3, int paramInt) {
/* 140 */     super.visitInnerClass(this.remapper.mapType(paramString1), (paramString2 == null) ? null : this.remapper
/* 141 */         .mapType(paramString2), paramString3, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitOuterClass(String paramString1, String paramString2, String paramString3) {
/* 146 */     super.visitOuterClass(this.remapper.mapType(paramString1), (paramString2 == null) ? null : this.remapper
/* 147 */         .mapMethodName(paramString1, paramString2, paramString3), (paramString3 == null) ? null : this.remapper
/* 148 */         .mapMethodDesc(paramString3));
/*     */   }
/*     */   
/*     */   protected FieldVisitor createRemappingFieldAdapter(FieldVisitor paramFieldVisitor) {
/* 152 */     return new RemappingFieldAdapter(paramFieldVisitor, this.remapper);
/*     */   }
/*     */ 
/*     */   
/*     */   protected MethodVisitor createRemappingMethodAdapter(int paramInt, String paramString, MethodVisitor paramMethodVisitor) {
/* 157 */     return new RemappingMethodAdapter(paramInt, paramString, paramMethodVisitor, this.remapper);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AnnotationVisitor createRemappingAnnotationAdapter(AnnotationVisitor paramAnnotationVisitor) {
/* 162 */     return new RemappingAnnotationAdapter(paramAnnotationVisitor, this.remapper);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/RemappingClassAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */