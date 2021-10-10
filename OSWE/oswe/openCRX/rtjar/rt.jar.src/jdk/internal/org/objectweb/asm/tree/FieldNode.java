/*     */ package jdk.internal.org.objectweb.asm.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import jdk.internal.org.objectweb.asm.AnnotationVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Attribute;
/*     */ import jdk.internal.org.objectweb.asm.ClassVisitor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldNode
/*     */   extends FieldVisitor
/*     */ {
/*     */   public int access;
/*     */   public String name;
/*     */   public String desc;
/*     */   public String signature;
/*     */   public Object value;
/*     */   public List<AnnotationNode> visibleAnnotations;
/*     */   public List<AnnotationNode> invisibleAnnotations;
/*     */   public List<TypeAnnotationNode> visibleTypeAnnotations;
/*     */   public List<TypeAnnotationNode> invisibleTypeAnnotations;
/*     */   public List<Attribute> attrs;
/*     */   
/*     */   public FieldNode(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject) {
/* 176 */     this(327680, paramInt, paramString1, paramString2, paramString3, paramObject);
/* 177 */     if (getClass() != FieldNode.class) {
/* 178 */       throw new IllegalStateException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldNode(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, Object paramObject) {
/* 208 */     super(paramInt1);
/* 209 */     this.access = paramInt2;
/* 210 */     this.name = paramString1;
/* 211 */     this.desc = paramString2;
/* 212 */     this.signature = paramString3;
/* 213 */     this.value = paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/* 223 */     AnnotationNode annotationNode = new AnnotationNode(paramString);
/* 224 */     if (paramBoolean) {
/* 225 */       if (this.visibleAnnotations == null) {
/* 226 */         this.visibleAnnotations = new ArrayList<>(1);
/*     */       }
/* 228 */       this.visibleAnnotations.add(annotationNode);
/*     */     } else {
/* 230 */       if (this.invisibleAnnotations == null) {
/* 231 */         this.invisibleAnnotations = new ArrayList<>(1);
/*     */       }
/* 233 */       this.invisibleAnnotations.add(annotationNode);
/*     */     } 
/* 235 */     return annotationNode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 241 */     TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(paramInt, paramTypePath, paramString);
/* 242 */     if (paramBoolean) {
/* 243 */       if (this.visibleTypeAnnotations == null) {
/* 244 */         this.visibleTypeAnnotations = new ArrayList<>(1);
/*     */       }
/* 246 */       this.visibleTypeAnnotations.add(typeAnnotationNode);
/*     */     } else {
/* 248 */       if (this.invisibleTypeAnnotations == null) {
/* 249 */         this.invisibleTypeAnnotations = new ArrayList<>(1);
/*     */       }
/* 251 */       this.invisibleTypeAnnotations.add(typeAnnotationNode);
/*     */     } 
/* 253 */     return typeAnnotationNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute paramAttribute) {
/* 258 */     if (this.attrs == null) {
/* 259 */       this.attrs = new ArrayList<>(1);
/*     */     }
/* 261 */     this.attrs.add(paramAttribute);
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
/*     */   public void visitEnd() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void check(int paramInt) {
/* 283 */     if (paramInt == 262144) {
/* 284 */       if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations
/* 285 */         .size() > 0) {
/* 286 */         throw new RuntimeException();
/*     */       }
/* 288 */       if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations
/* 289 */         .size() > 0) {
/* 290 */         throw new RuntimeException();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(ClassVisitor paramClassVisitor) {
/* 302 */     FieldVisitor fieldVisitor = paramClassVisitor.visitField(this.access, this.name, this.desc, this.signature, this.value);
/* 303 */     if (fieldVisitor == null) {
/*     */       return;
/*     */     }
/*     */     
/* 307 */     byte b2 = (this.visibleAnnotations == null) ? 0 : this.visibleAnnotations.size(); byte b1;
/* 308 */     for (b1 = 0; b1 < b2; b1++) {
/* 309 */       AnnotationNode annotationNode = this.visibleAnnotations.get(b1);
/* 310 */       annotationNode.accept(fieldVisitor.visitAnnotation(annotationNode.desc, true));
/*     */     } 
/* 312 */     b2 = (this.invisibleAnnotations == null) ? 0 : this.invisibleAnnotations.size();
/* 313 */     for (b1 = 0; b1 < b2; b1++) {
/* 314 */       AnnotationNode annotationNode = this.invisibleAnnotations.get(b1);
/* 315 */       annotationNode.accept(fieldVisitor.visitAnnotation(annotationNode.desc, false));
/*     */     } 
/* 317 */     b2 = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size();
/* 318 */     for (b1 = 0; b1 < b2; b1++) {
/* 319 */       TypeAnnotationNode typeAnnotationNode = this.visibleTypeAnnotations.get(b1);
/* 320 */       typeAnnotationNode.accept(fieldVisitor.visitTypeAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, true));
/*     */     } 
/*     */ 
/*     */     
/* 324 */     b2 = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size();
/* 325 */     for (b1 = 0; b1 < b2; b1++) {
/* 326 */       TypeAnnotationNode typeAnnotationNode = this.invisibleTypeAnnotations.get(b1);
/* 327 */       typeAnnotationNode.accept(fieldVisitor.visitTypeAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, false));
/*     */     } 
/*     */     
/* 330 */     b2 = (this.attrs == null) ? 0 : this.attrs.size();
/* 331 */     for (b1 = 0; b1 < b2; b1++) {
/* 332 */       fieldVisitor.visitAttribute(this.attrs.get(b1));
/*     */     }
/* 334 */     fieldVisitor.visitEnd();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/FieldNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */