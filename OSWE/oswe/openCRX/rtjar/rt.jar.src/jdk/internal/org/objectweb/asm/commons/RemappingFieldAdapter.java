/*    */ package jdk.internal.org.objectweb.asm.commons;
/*    */ 
/*    */ import jdk.internal.org.objectweb.asm.AnnotationVisitor;
/*    */ import jdk.internal.org.objectweb.asm.FieldVisitor;
/*    */ import jdk.internal.org.objectweb.asm.TypePath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RemappingFieldAdapter
/*    */   extends FieldVisitor
/*    */ {
/*    */   private final Remapper remapper;
/*    */   
/*    */   public RemappingFieldAdapter(FieldVisitor paramFieldVisitor, Remapper paramRemapper) {
/* 77 */     this(327680, paramFieldVisitor, paramRemapper);
/*    */   }
/*    */ 
/*    */   
/*    */   protected RemappingFieldAdapter(int paramInt, FieldVisitor paramFieldVisitor, Remapper paramRemapper) {
/* 82 */     super(paramInt, paramFieldVisitor);
/* 83 */     this.remapper = paramRemapper;
/*    */   }
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/* 88 */     AnnotationVisitor annotationVisitor = this.fv.visitAnnotation(this.remapper.mapDesc(paramString), paramBoolean);
/*    */     
/* 90 */     return (annotationVisitor == null) ? null : new RemappingAnnotationAdapter(annotationVisitor, this.remapper);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 96 */     AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(paramInt, paramTypePath, this.remapper
/* 97 */         .mapDesc(paramString), paramBoolean);
/* 98 */     return (annotationVisitor == null) ? null : new RemappingAnnotationAdapter(annotationVisitor, this.remapper);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/RemappingFieldAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */