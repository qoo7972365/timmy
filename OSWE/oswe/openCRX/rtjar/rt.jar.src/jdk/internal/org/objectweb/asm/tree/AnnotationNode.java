/*     */ package jdk.internal.org.objectweb.asm.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotationNode
/*     */   extends AnnotationVisitor
/*     */ {
/*     */   public String desc;
/*     */   public List<Object> values;
/*     */   
/*     */   public AnnotationNode(String paramString) {
/* 103 */     this(327680, paramString);
/* 104 */     if (getClass() != AnnotationNode.class) {
/* 105 */       throw new IllegalStateException();
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
/*     */   public AnnotationNode(int paramInt, String paramString) {
/* 119 */     super(paramInt);
/* 120 */     this.desc = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AnnotationNode(List<Object> paramList) {
/* 130 */     super(327680);
/* 131 */     this.values = paramList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(String paramString, Object paramObject) {
/* 140 */     if (this.values == null) {
/* 141 */       this.values = new ArrayList((this.desc != null) ? 2 : 1);
/*     */     }
/* 143 */     if (this.desc != null) {
/* 144 */       this.values.add(paramString);
/*     */     }
/* 146 */     this.values.add(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnum(String paramString1, String paramString2, String paramString3) {
/* 152 */     if (this.values == null) {
/* 153 */       this.values = new ArrayList((this.desc != null) ? 2 : 1);
/*     */     }
/* 155 */     if (this.desc != null) {
/* 156 */       this.values.add(paramString1);
/*     */     }
/* 158 */     this.values.add(new String[] { paramString2, paramString3 });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString1, String paramString2) {
/* 164 */     if (this.values == null) {
/* 165 */       this.values = new ArrayList((this.desc != null) ? 2 : 1);
/*     */     }
/* 167 */     if (this.desc != null) {
/* 168 */       this.values.add(paramString1);
/*     */     }
/* 170 */     AnnotationNode annotationNode = new AnnotationNode(paramString2);
/* 171 */     this.values.add(annotationNode);
/* 172 */     return annotationNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitArray(String paramString) {
/* 177 */     if (this.values == null) {
/* 178 */       this.values = new ArrayList((this.desc != null) ? 2 : 1);
/*     */     }
/* 180 */     if (this.desc != null) {
/* 181 */       this.values.add(paramString);
/*     */     }
/* 183 */     ArrayList<Object> arrayList = new ArrayList();
/* 184 */     this.values.add(arrayList);
/* 185 */     return new AnnotationNode(arrayList);
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
/*     */   public void check(int paramInt) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(AnnotationVisitor paramAnnotationVisitor) {
/* 217 */     if (paramAnnotationVisitor != null) {
/* 218 */       if (this.values != null) {
/* 219 */         for (byte b = 0; b < this.values.size(); b += 2) {
/* 220 */           String str = (String)this.values.get(b);
/* 221 */           Object object = this.values.get(b + 1);
/* 222 */           accept(paramAnnotationVisitor, str, object);
/*     */         } 
/*     */       }
/* 225 */       paramAnnotationVisitor.visitEnd();
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
/*     */   static void accept(AnnotationVisitor paramAnnotationVisitor, String paramString, Object paramObject) {
/* 241 */     if (paramAnnotationVisitor != null)
/* 242 */       if (paramObject instanceof String[]) {
/* 243 */         String[] arrayOfString = (String[])paramObject;
/* 244 */         paramAnnotationVisitor.visitEnum(paramString, arrayOfString[0], arrayOfString[1]);
/* 245 */       } else if (paramObject instanceof AnnotationNode) {
/* 246 */         AnnotationNode annotationNode = (AnnotationNode)paramObject;
/* 247 */         annotationNode.accept(paramAnnotationVisitor.visitAnnotation(paramString, annotationNode.desc));
/* 248 */       } else if (paramObject instanceof List) {
/* 249 */         AnnotationVisitor annotationVisitor = paramAnnotationVisitor.visitArray(paramString);
/* 250 */         if (annotationVisitor != null) {
/* 251 */           List list = (List)paramObject;
/* 252 */           for (byte b = 0; b < list.size(); b++) {
/* 253 */             accept(annotationVisitor, null, list.get(b));
/*     */           }
/* 255 */           annotationVisitor.visitEnd();
/*     */         } 
/*     */       } else {
/* 258 */         paramAnnotationVisitor.visit(paramString, paramObject);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/AnnotationNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */