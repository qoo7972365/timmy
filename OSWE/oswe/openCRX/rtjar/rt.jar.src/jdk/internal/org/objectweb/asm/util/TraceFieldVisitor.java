/*     */ package jdk.internal.org.objectweb.asm.util;
/*     */ 
/*     */ import jdk.internal.org.objectweb.asm.AnnotationVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Attribute;
/*     */ import jdk.internal.org.objectweb.asm.FieldVisitor;
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
/*     */ public final class TraceFieldVisitor
/*     */   extends FieldVisitor
/*     */ {
/*     */   public final Printer p;
/*     */   
/*     */   public TraceFieldVisitor(Printer paramPrinter) {
/*  78 */     this(null, paramPrinter);
/*     */   }
/*     */   
/*     */   public TraceFieldVisitor(FieldVisitor paramFieldVisitor, Printer paramPrinter) {
/*  82 */     super(327680, paramFieldVisitor);
/*  83 */     this.p = paramPrinter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/*  89 */     Printer printer = this.p.visitFieldAnnotation(paramString, paramBoolean);
/*  90 */     AnnotationVisitor annotationVisitor = (this.fv == null) ? null : this.fv.visitAnnotation(paramString, paramBoolean);
/*     */     
/*  92 */     return new TraceAnnotationVisitor(annotationVisitor, printer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/*  98 */     Printer printer = this.p.visitFieldTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*     */     
/* 100 */     AnnotationVisitor annotationVisitor = (this.fv == null) ? null : this.fv.visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*     */     
/* 102 */     return new TraceAnnotationVisitor(annotationVisitor, printer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute paramAttribute) {
/* 107 */     this.p.visitFieldAttribute(paramAttribute);
/* 108 */     super.visitAttribute(paramAttribute);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 113 */     this.p.visitFieldEnd();
/* 114 */     super.visitEnd();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/TraceFieldVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */