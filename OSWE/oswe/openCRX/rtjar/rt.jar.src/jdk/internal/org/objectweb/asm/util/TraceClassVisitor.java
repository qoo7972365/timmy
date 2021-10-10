/*     */ package jdk.internal.org.objectweb.asm.util;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import jdk.internal.org.objectweb.asm.AnnotationVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Attribute;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TraceClassVisitor
/*     */   extends ClassVisitor
/*     */ {
/*     */   private final PrintWriter pw;
/*     */   public final Printer p;
/*     */   
/*     */   public TraceClassVisitor(PrintWriter paramPrintWriter) {
/* 132 */     this(null, paramPrintWriter);
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
/*     */   public TraceClassVisitor(ClassVisitor paramClassVisitor, PrintWriter paramPrintWriter) {
/* 145 */     this(paramClassVisitor, new Textifier(), paramPrintWriter);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TraceClassVisitor(ClassVisitor paramClassVisitor, Printer paramPrinter, PrintWriter paramPrintWriter) {
/* 163 */     super(327680, paramClassVisitor);
/* 164 */     this.pw = paramPrintWriter;
/* 165 */     this.p = paramPrinter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/* 172 */     this.p.visit(paramInt1, paramInt2, paramString1, paramString2, paramString3, paramArrayOfString);
/* 173 */     super.visit(paramInt1, paramInt2, paramString1, paramString2, paramString3, paramArrayOfString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitSource(String paramString1, String paramString2) {
/* 178 */     this.p.visitSource(paramString1, paramString2);
/* 179 */     super.visitSource(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitOuterClass(String paramString1, String paramString2, String paramString3) {
/* 185 */     this.p.visitOuterClass(paramString1, paramString2, paramString3);
/* 186 */     super.visitOuterClass(paramString1, paramString2, paramString3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/* 192 */     Printer printer = this.p.visitClassAnnotation(paramString, paramBoolean);
/* 193 */     AnnotationVisitor annotationVisitor = (this.cv == null) ? null : this.cv.visitAnnotation(paramString, paramBoolean);
/*     */     
/* 195 */     return new TraceAnnotationVisitor(annotationVisitor, printer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 201 */     Printer printer = this.p.visitClassTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*     */     
/* 203 */     AnnotationVisitor annotationVisitor = (this.cv == null) ? null : this.cv.visitTypeAnnotation(paramInt, paramTypePath, paramString, paramBoolean);
/*     */     
/* 205 */     return new TraceAnnotationVisitor(annotationVisitor, printer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute paramAttribute) {
/* 210 */     this.p.visitClassAttribute(paramAttribute);
/* 211 */     super.visitAttribute(paramAttribute);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInnerClass(String paramString1, String paramString2, String paramString3, int paramInt) {
/* 217 */     this.p.visitInnerClass(paramString1, paramString2, paramString3, paramInt);
/* 218 */     super.visitInnerClass(paramString1, paramString2, paramString3, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor visitField(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject) {
/* 224 */     Printer printer = this.p.visitField(paramInt, paramString1, paramString2, paramString3, paramObject);
/* 225 */     FieldVisitor fieldVisitor = (this.cv == null) ? null : this.cv.visitField(paramInt, paramString1, paramString2, paramString3, paramObject);
/*     */     
/* 227 */     return new TraceFieldVisitor(fieldVisitor, printer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/* 233 */     Printer printer = this.p.visitMethod(paramInt, paramString1, paramString2, paramString3, paramArrayOfString);
/*     */     
/* 235 */     MethodVisitor methodVisitor = (this.cv == null) ? null : this.cv.visitMethod(paramInt, paramString1, paramString2, paramString3, paramArrayOfString);
/*     */     
/* 237 */     return new TraceMethodVisitor(methodVisitor, printer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 242 */     this.p.visitClassEnd();
/* 243 */     if (this.pw != null) {
/* 244 */       this.p.print(this.pw);
/* 245 */       this.pw.flush();
/*     */     } 
/* 247 */     super.visitEnd();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/TraceClassVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */