/*     */ package jdk.internal.org.objectweb.asm.util;
/*     */ 
/*     */ import jdk.internal.org.objectweb.asm.AnnotationVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CheckAnnotationAdapter
/*     */   extends AnnotationVisitor
/*     */ {
/*     */   private final boolean named;
/*     */   private boolean end;
/*     */   
/*     */   public CheckAnnotationAdapter(AnnotationVisitor paramAnnotationVisitor) {
/*  77 */     this(paramAnnotationVisitor, true);
/*     */   }
/*     */   
/*     */   CheckAnnotationAdapter(AnnotationVisitor paramAnnotationVisitor, boolean paramBoolean) {
/*  81 */     super(327680, paramAnnotationVisitor);
/*  82 */     this.named = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(String paramString, Object paramObject) {
/*  87 */     checkEnd();
/*  88 */     checkName(paramString);
/*  89 */     if (!(paramObject instanceof Byte) && !(paramObject instanceof Boolean) && !(paramObject instanceof Character) && !(paramObject instanceof Short) && !(paramObject instanceof Integer) && !(paramObject instanceof Long) && !(paramObject instanceof Float) && !(paramObject instanceof Double) && !(paramObject instanceof String) && !(paramObject instanceof Type) && !(paramObject instanceof byte[]) && !(paramObject instanceof boolean[]) && !(paramObject instanceof char[]) && !(paramObject instanceof short[]) && !(paramObject instanceof int[]) && !(paramObject instanceof long[]) && !(paramObject instanceof float[]) && !(paramObject instanceof double[]))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  98 */       throw new IllegalArgumentException("Invalid annotation value");
/*     */     }
/* 100 */     if (paramObject instanceof Type) {
/* 101 */       int i = ((Type)paramObject).getSort();
/* 102 */       if (i == 11) {
/* 103 */         throw new IllegalArgumentException("Invalid annotation value");
/*     */       }
/*     */     } 
/* 106 */     if (this.av != null) {
/* 107 */       this.av.visit(paramString, paramObject);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnum(String paramString1, String paramString2, String paramString3) {
/* 114 */     checkEnd();
/* 115 */     checkName(paramString1);
/* 116 */     CheckMethodAdapter.checkDesc(paramString2, false);
/* 117 */     if (paramString3 == null) {
/* 118 */       throw new IllegalArgumentException("Invalid enum value");
/*     */     }
/* 120 */     if (this.av != null) {
/* 121 */       this.av.visitEnum(paramString1, paramString2, paramString3);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString1, String paramString2) {
/* 128 */     checkEnd();
/* 129 */     checkName(paramString1);
/* 130 */     CheckMethodAdapter.checkDesc(paramString2, false);
/* 131 */     return new CheckAnnotationAdapter((this.av == null) ? null : this.av
/* 132 */         .visitAnnotation(paramString1, paramString2));
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitArray(String paramString) {
/* 137 */     checkEnd();
/* 138 */     checkName(paramString);
/* 139 */     return new CheckAnnotationAdapter((this.av == null) ? null : this.av
/* 140 */         .visitArray(paramString), false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 145 */     checkEnd();
/* 146 */     this.end = true;
/* 147 */     if (this.av != null) {
/* 148 */       this.av.visitEnd();
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkEnd() {
/* 153 */     if (this.end) {
/* 154 */       throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkName(String paramString) {
/* 160 */     if (this.named && paramString == null)
/* 161 */       throw new IllegalArgumentException("Annotation value name must not be null"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/CheckAnnotationAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */