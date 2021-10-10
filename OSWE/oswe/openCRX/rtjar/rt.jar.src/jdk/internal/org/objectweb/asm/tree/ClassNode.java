/*     */ package jdk.internal.org.objectweb.asm.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassNode
/*     */   extends ClassVisitor
/*     */ {
/*     */   public int version;
/*     */   public int access;
/*     */   public String name;
/*     */   public String signature;
/*     */   public String superName;
/*     */   public List<String> interfaces;
/*     */   public String sourceFile;
/*     */   public String sourceDebug;
/*     */   public String outerClass;
/*     */   public String outerMethod;
/*     */   public String outerMethodDesc;
/*     */   public List<AnnotationNode> visibleAnnotations;
/*     */   public List<AnnotationNode> invisibleAnnotations;
/*     */   public List<TypeAnnotationNode> visibleTypeAnnotations;
/*     */   public List<TypeAnnotationNode> invisibleTypeAnnotations;
/*     */   public List<Attribute> attrs;
/*     */   public List<InnerClassNode> innerClasses;
/*     */   public List<FieldNode> fields;
/*     */   public List<MethodNode> methods;
/*     */   
/*     */   public ClassNode() {
/* 224 */     this(327680);
/* 225 */     if (getClass() != ClassNode.class) {
/* 226 */       throw new IllegalStateException();
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
/*     */   public ClassNode(int paramInt) {
/* 238 */     super(paramInt);
/* 239 */     this.interfaces = new ArrayList<>();
/* 240 */     this.innerClasses = new ArrayList<>();
/* 241 */     this.fields = new ArrayList<>();
/* 242 */     this.methods = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/* 253 */     this.version = paramInt1;
/* 254 */     this.access = paramInt2;
/* 255 */     this.name = paramString1;
/* 256 */     this.signature = paramString2;
/* 257 */     this.superName = paramString3;
/* 258 */     if (paramArrayOfString != null) {
/* 259 */       this.interfaces.addAll(Arrays.asList(paramArrayOfString));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitSource(String paramString1, String paramString2) {
/* 265 */     this.sourceFile = paramString1;
/* 266 */     this.sourceDebug = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitOuterClass(String paramString1, String paramString2, String paramString3) {
/* 272 */     this.outerClass = paramString1;
/* 273 */     this.outerMethod = paramString2;
/* 274 */     this.outerMethodDesc = paramString3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/* 280 */     AnnotationNode annotationNode = new AnnotationNode(paramString);
/* 281 */     if (paramBoolean) {
/* 282 */       if (this.visibleAnnotations == null) {
/* 283 */         this.visibleAnnotations = new ArrayList<>(1);
/*     */       }
/* 285 */       this.visibleAnnotations.add(annotationNode);
/*     */     } else {
/* 287 */       if (this.invisibleAnnotations == null) {
/* 288 */         this.invisibleAnnotations = new ArrayList<>(1);
/*     */       }
/* 290 */       this.invisibleAnnotations.add(annotationNode);
/*     */     } 
/* 292 */     return annotationNode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 298 */     TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(paramInt, paramTypePath, paramString);
/* 299 */     if (paramBoolean) {
/* 300 */       if (this.visibleTypeAnnotations == null) {
/* 301 */         this.visibleTypeAnnotations = new ArrayList<>(1);
/*     */       }
/* 303 */       this.visibleTypeAnnotations.add(typeAnnotationNode);
/*     */     } else {
/* 305 */       if (this.invisibleTypeAnnotations == null) {
/* 306 */         this.invisibleTypeAnnotations = new ArrayList<>(1);
/*     */       }
/* 308 */       this.invisibleTypeAnnotations.add(typeAnnotationNode);
/*     */     } 
/* 310 */     return typeAnnotationNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute paramAttribute) {
/* 315 */     if (this.attrs == null) {
/* 316 */       this.attrs = new ArrayList<>(1);
/*     */     }
/* 318 */     this.attrs.add(paramAttribute);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInnerClass(String paramString1, String paramString2, String paramString3, int paramInt) {
/* 324 */     InnerClassNode innerClassNode = new InnerClassNode(paramString1, paramString2, paramString3, paramInt);
/*     */     
/* 326 */     this.innerClasses.add(innerClassNode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVisitor visitField(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject) {
/* 332 */     FieldNode fieldNode = new FieldNode(paramInt, paramString1, paramString2, paramString3, paramObject);
/* 333 */     this.fields.add(fieldNode);
/* 334 */     return fieldNode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/* 340 */     MethodNode methodNode = new MethodNode(paramInt, paramString1, paramString2, paramString3, paramArrayOfString);
/*     */     
/* 342 */     this.methods.add(methodNode);
/* 343 */     return methodNode;
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
/* 365 */     if (paramInt == 262144) {
/* 366 */       if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations
/* 367 */         .size() > 0) {
/* 368 */         throw new RuntimeException();
/*     */       }
/* 370 */       if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations
/* 371 */         .size() > 0) {
/* 372 */         throw new RuntimeException();
/*     */       }
/* 374 */       for (FieldNode fieldNode : this.fields) {
/* 375 */         fieldNode.check(paramInt);
/*     */       }
/* 377 */       for (MethodNode methodNode : this.methods) {
/* 378 */         methodNode.check(paramInt);
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
/*     */   
/*     */   public void accept(ClassVisitor paramClassVisitor) {
/* 391 */     String[] arrayOfString = new String[this.interfaces.size()];
/* 392 */     this.interfaces.toArray(arrayOfString);
/* 393 */     paramClassVisitor.visit(this.version, this.access, this.name, this.signature, this.superName, arrayOfString);
/*     */     
/* 395 */     if (this.sourceFile != null || this.sourceDebug != null) {
/* 396 */       paramClassVisitor.visitSource(this.sourceFile, this.sourceDebug);
/*     */     }
/*     */     
/* 399 */     if (this.outerClass != null) {
/* 400 */       paramClassVisitor.visitOuterClass(this.outerClass, this.outerMethod, this.outerMethodDesc);
/*     */     }
/*     */ 
/*     */     
/* 404 */     byte b2 = (this.visibleAnnotations == null) ? 0 : this.visibleAnnotations.size(); byte b1;
/* 405 */     for (b1 = 0; b1 < b2; b1++) {
/* 406 */       AnnotationNode annotationNode = this.visibleAnnotations.get(b1);
/* 407 */       annotationNode.accept(paramClassVisitor.visitAnnotation(annotationNode.desc, true));
/*     */     } 
/* 409 */     b2 = (this.invisibleAnnotations == null) ? 0 : this.invisibleAnnotations.size();
/* 410 */     for (b1 = 0; b1 < b2; b1++) {
/* 411 */       AnnotationNode annotationNode = this.invisibleAnnotations.get(b1);
/* 412 */       annotationNode.accept(paramClassVisitor.visitAnnotation(annotationNode.desc, false));
/*     */     } 
/* 414 */     b2 = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size();
/* 415 */     for (b1 = 0; b1 < b2; b1++) {
/* 416 */       TypeAnnotationNode typeAnnotationNode = this.visibleTypeAnnotations.get(b1);
/* 417 */       typeAnnotationNode.accept(paramClassVisitor.visitTypeAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, true));
/*     */     } 
/*     */ 
/*     */     
/* 421 */     b2 = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size();
/* 422 */     for (b1 = 0; b1 < b2; b1++) {
/* 423 */       TypeAnnotationNode typeAnnotationNode = this.invisibleTypeAnnotations.get(b1);
/* 424 */       typeAnnotationNode.accept(paramClassVisitor.visitTypeAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, false));
/*     */     } 
/*     */     
/* 427 */     b2 = (this.attrs == null) ? 0 : this.attrs.size();
/* 428 */     for (b1 = 0; b1 < b2; b1++) {
/* 429 */       paramClassVisitor.visitAttribute(this.attrs.get(b1));
/*     */     }
/*     */     
/* 432 */     for (b1 = 0; b1 < this.innerClasses.size(); b1++) {
/* 433 */       ((InnerClassNode)this.innerClasses.get(b1)).accept(paramClassVisitor);
/*     */     }
/*     */     
/* 436 */     for (b1 = 0; b1 < this.fields.size(); b1++) {
/* 437 */       ((FieldNode)this.fields.get(b1)).accept(paramClassVisitor);
/*     */     }
/*     */     
/* 440 */     for (b1 = 0; b1 < this.methods.size(); b1++) {
/* 441 */       ((MethodNode)this.methods.get(b1)).accept(paramClassVisitor);
/*     */     }
/*     */     
/* 444 */     paramClassVisitor.visitEnd();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/ClassNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */