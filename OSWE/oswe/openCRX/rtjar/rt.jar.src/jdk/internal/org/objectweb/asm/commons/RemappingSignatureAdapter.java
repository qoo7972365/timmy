/*     */ package jdk.internal.org.objectweb.asm.commons;
/*     */ 
/*     */ import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RemappingSignatureAdapter
/*     */   extends SignatureVisitor
/*     */ {
/*     */   private final SignatureVisitor v;
/*     */   private final Remapper remapper;
/*     */   private String className;
/*     */   
/*     */   public RemappingSignatureAdapter(SignatureVisitor paramSignatureVisitor, Remapper paramRemapper) {
/*  80 */     this(327680, paramSignatureVisitor, paramRemapper);
/*     */   }
/*     */ 
/*     */   
/*     */   protected RemappingSignatureAdapter(int paramInt, SignatureVisitor paramSignatureVisitor, Remapper paramRemapper) {
/*  85 */     super(paramInt);
/*  86 */     this.v = paramSignatureVisitor;
/*  87 */     this.remapper = paramRemapper;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitClassType(String paramString) {
/*  92 */     this.className = paramString;
/*  93 */     this.v.visitClassType(this.remapper.mapType(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInnerClassType(String paramString) {
/*  98 */     String str1 = this.remapper.mapType(this.className) + '$';
/*  99 */     this.className += '$' + paramString;
/* 100 */     String str2 = this.remapper.mapType(this.className);
/*     */     
/* 102 */     int i = str2.startsWith(str1) ? str1.length() : (str2.lastIndexOf('$') + 1);
/* 103 */     this.v.visitInnerClassType(str2.substring(i));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitFormalTypeParameter(String paramString) {
/* 108 */     this.v.visitFormalTypeParameter(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeVariable(String paramString) {
/* 113 */     this.v.visitTypeVariable(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitArrayType() {
/* 118 */     this.v.visitArrayType();
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitBaseType(char paramChar) {
/* 124 */     this.v.visitBaseType(paramChar);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitClassBound() {
/* 129 */     this.v.visitClassBound();
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitExceptionType() {
/* 135 */     this.v.visitExceptionType();
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterface() {
/* 141 */     this.v.visitInterface();
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitInterfaceBound() {
/* 147 */     this.v.visitInterfaceBound();
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitParameterType() {
/* 153 */     this.v.visitParameterType();
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitReturnType() {
/* 159 */     this.v.visitReturnType();
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitSuperclass() {
/* 165 */     this.v.visitSuperclass();
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeArgument() {
/* 171 */     this.v.visitTypeArgument();
/*     */   }
/*     */ 
/*     */   
/*     */   public SignatureVisitor visitTypeArgument(char paramChar) {
/* 176 */     this.v.visitTypeArgument(paramChar);
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 182 */     this.v.visitEnd();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/RemappingSignatureAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */