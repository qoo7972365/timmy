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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CheckFieldAdapter
/*     */   extends FieldVisitor
/*     */ {
/*     */   private boolean end;
/*     */   
/*     */   public CheckFieldAdapter(FieldVisitor paramFieldVisitor) {
/*  86 */     this(327680, paramFieldVisitor);
/*  87 */     if (getClass() != CheckFieldAdapter.class) {
/*  88 */       throw new IllegalStateException();
/*     */     }
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
/*     */   protected CheckFieldAdapter(int paramInt, FieldVisitor paramFieldVisitor) {
/* 102 */     super(paramInt, paramFieldVisitor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/* 108 */     checkEnd();
/* 109 */     CheckMethodAdapter.checkDesc(paramString, false);
/* 110 */     return new CheckAnnotationAdapter(super.visitAnnotation(paramString, paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 116 */     checkEnd();
/* 117 */     int i = paramInt >>> 24;
/* 118 */     if (i != 19) {
/* 119 */       throw new IllegalArgumentException("Invalid type reference sort 0x" + 
/* 120 */           Integer.toHexString(i));
/*     */     }
/* 122 */     CheckClassAdapter.checkTypeRefAndPath(paramInt, paramTypePath);
/* 123 */     CheckMethodAdapter.checkDesc(paramString, false);
/* 124 */     return new CheckAnnotationAdapter(super.visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute paramAttribute) {
/* 130 */     checkEnd();
/* 131 */     if (paramAttribute == null) {
/* 132 */       throw new IllegalArgumentException("Invalid attribute (must not be null)");
/*     */     }
/*     */     
/* 135 */     super.visitAttribute(paramAttribute);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 140 */     checkEnd();
/* 141 */     this.end = true;
/* 142 */     super.visitEnd();
/*     */   }
/*     */   
/*     */   private void checkEnd() {
/* 146 */     if (this.end)
/* 147 */       throw new IllegalStateException("Cannot call a visit method after visitEnd has been called"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/CheckFieldAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */