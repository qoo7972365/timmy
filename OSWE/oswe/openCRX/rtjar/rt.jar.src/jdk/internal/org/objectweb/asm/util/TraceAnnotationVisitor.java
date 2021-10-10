/*     */ package jdk.internal.org.objectweb.asm.util;
/*     */ 
/*     */ import jdk.internal.org.objectweb.asm.AnnotationVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TraceAnnotationVisitor
/*     */   extends AnnotationVisitor
/*     */ {
/*     */   private final Printer p;
/*     */   
/*     */   public TraceAnnotationVisitor(Printer paramPrinter) {
/*  75 */     this(null, paramPrinter);
/*     */   }
/*     */   
/*     */   public TraceAnnotationVisitor(AnnotationVisitor paramAnnotationVisitor, Printer paramPrinter) {
/*  79 */     super(327680, paramAnnotationVisitor);
/*  80 */     this.p = paramPrinter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(String paramString, Object paramObject) {
/*  85 */     this.p.visit(paramString, paramObject);
/*  86 */     super.visit(paramString, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnum(String paramString1, String paramString2, String paramString3) {
/*  92 */     this.p.visitEnum(paramString1, paramString2, paramString3);
/*  93 */     super.visitEnum(paramString1, paramString2, paramString3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString1, String paramString2) {
/*  99 */     Printer printer = this.p.visitAnnotation(paramString1, paramString2);
/*     */     
/* 101 */     AnnotationVisitor annotationVisitor = (this.av == null) ? null : this.av.visitAnnotation(paramString1, paramString2);
/* 102 */     return new TraceAnnotationVisitor(annotationVisitor, printer);
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitArray(String paramString) {
/* 107 */     Printer printer = this.p.visitArray(paramString);
/*     */     
/* 109 */     AnnotationVisitor annotationVisitor = (this.av == null) ? null : this.av.visitArray(paramString);
/* 110 */     return new TraceAnnotationVisitor(annotationVisitor, printer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 115 */     this.p.visitAnnotationEnd();
/* 116 */     super.visitEnd();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/TraceAnnotationVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */