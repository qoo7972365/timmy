/*     */ package jdk.internal.org.objectweb.asm.commons;
/*     */ 
/*     */ import jdk.internal.org.objectweb.asm.ClassVisitor;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StaticInitMerger
/*     */   extends ClassVisitor
/*     */ {
/*     */   private String name;
/*     */   private MethodVisitor clinit;
/*     */   private final String prefix;
/*     */   private int counter;
/*     */   
/*     */   public StaticInitMerger(String paramString, ClassVisitor paramClassVisitor) {
/*  81 */     this(327680, paramString, paramClassVisitor);
/*     */   }
/*     */ 
/*     */   
/*     */   protected StaticInitMerger(int paramInt, String paramString, ClassVisitor paramClassVisitor) {
/*  86 */     super(paramInt, paramClassVisitor);
/*  87 */     this.prefix = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*  94 */     this.cv.visit(paramInt1, paramInt2, paramString1, paramString2, paramString3, paramArrayOfString);
/*  95 */     this.name = paramString1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*     */     MethodVisitor methodVisitor;
/* 102 */     if ("<clinit>".equals(paramString1)) {
/* 103 */       byte b = 10;
/* 104 */       String str = this.prefix + this.counter++;
/* 105 */       methodVisitor = this.cv.visitMethod(b, str, paramString2, paramString3, paramArrayOfString);
/*     */       
/* 107 */       if (this.clinit == null) {
/* 108 */         this.clinit = this.cv.visitMethod(b, paramString1, paramString2, null, null);
/*     */       }
/* 110 */       this.clinit.visitMethodInsn(184, this.name, str, paramString2, false);
/*     */     } else {
/*     */       
/* 113 */       methodVisitor = this.cv.visitMethod(paramInt, paramString1, paramString2, paramString3, paramArrayOfString);
/*     */     } 
/* 115 */     return methodVisitor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 120 */     if (this.clinit != null) {
/* 121 */       this.clinit.visitInsn(177);
/* 122 */       this.clinit.visitMaxs(0, 0);
/*     */     } 
/* 124 */     this.cv.visitEnd();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/StaticInitMerger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */