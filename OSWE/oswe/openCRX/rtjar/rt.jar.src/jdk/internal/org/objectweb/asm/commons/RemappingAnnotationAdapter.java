/*     */ package jdk.internal.org.objectweb.asm.commons;
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
/*     */ 
/*     */ public class RemappingAnnotationAdapter
/*     */   extends AnnotationVisitor
/*     */ {
/*     */   protected final Remapper remapper;
/*     */   
/*     */   public RemappingAnnotationAdapter(AnnotationVisitor paramAnnotationVisitor, Remapper paramRemapper) {
/*  76 */     this(327680, paramAnnotationVisitor, paramRemapper);
/*     */   }
/*     */ 
/*     */   
/*     */   protected RemappingAnnotationAdapter(int paramInt, AnnotationVisitor paramAnnotationVisitor, Remapper paramRemapper) {
/*  81 */     super(paramInt, paramAnnotationVisitor);
/*  82 */     this.remapper = paramRemapper;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(String paramString, Object paramObject) {
/*  87 */     this.av.visit(paramString, this.remapper.mapValue(paramObject));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnum(String paramString1, String paramString2, String paramString3) {
/*  92 */     this.av.visitEnum(paramString1, this.remapper.mapDesc(paramString2), paramString3);
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString1, String paramString2) {
/*  97 */     AnnotationVisitor annotationVisitor = this.av.visitAnnotation(paramString1, this.remapper.mapDesc(paramString2));
/*  98 */     return (annotationVisitor == null) ? null : ((annotationVisitor == this.av) ? this : new RemappingAnnotationAdapter(annotationVisitor, this.remapper));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitArray(String paramString) {
/* 104 */     AnnotationVisitor annotationVisitor = this.av.visitArray(paramString);
/* 105 */     return (annotationVisitor == null) ? null : ((annotationVisitor == this.av) ? this : new RemappingAnnotationAdapter(annotationVisitor, this.remapper));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/RemappingAnnotationAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */