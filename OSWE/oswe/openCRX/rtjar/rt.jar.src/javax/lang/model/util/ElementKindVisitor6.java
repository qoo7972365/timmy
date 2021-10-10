/*     */ package javax.lang.model.util;
/*     */ 
/*     */ import javax.annotation.processing.SupportedSourceVersion;
/*     */ import javax.lang.model.SourceVersion;
/*     */ import javax.lang.model.element.ElementKind;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.PackageElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.element.TypeParameterElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SupportedSourceVersion(SourceVersion.RELEASE_6)
/*     */ public class ElementKindVisitor6<R, P>
/*     */   extends SimpleElementVisitor6<R, P>
/*     */ {
/*     */   protected ElementKindVisitor6() {
/* 101 */     super((R)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ElementKindVisitor6(R paramR) {
/* 111 */     super(paramR);
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
/*     */   public R visitPackage(PackageElement paramPackageElement, P paramP) {
/* 125 */     assert paramPackageElement.getKind() == ElementKind.PACKAGE : "Bad kind on PackageElement";
/* 126 */     return defaultAction(paramPackageElement, paramP);
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
/*     */   public R visitType(TypeElement paramTypeElement, P paramP) {
/* 141 */     ElementKind elementKind = paramTypeElement.getKind();
/* 142 */     switch (elementKind) {
/*     */       case ANNOTATION_TYPE:
/* 144 */         return visitTypeAsAnnotationType(paramTypeElement, paramP);
/*     */       
/*     */       case CLASS:
/* 147 */         return visitTypeAsClass(paramTypeElement, paramP);
/*     */       
/*     */       case ENUM:
/* 150 */         return visitTypeAsEnum(paramTypeElement, paramP);
/*     */       
/*     */       case INTERFACE:
/* 153 */         return visitTypeAsInterface(paramTypeElement, paramP);
/*     */     } 
/*     */     
/* 156 */     throw new AssertionError("Bad kind " + elementKind + " for TypeElement" + paramTypeElement);
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
/*     */   public R visitTypeAsAnnotationType(TypeElement paramTypeElement, P paramP) {
/* 169 */     return defaultAction(paramTypeElement, paramP);
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
/*     */   public R visitTypeAsClass(TypeElement paramTypeElement, P paramP) {
/* 181 */     return defaultAction(paramTypeElement, paramP);
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
/*     */   public R visitTypeAsEnum(TypeElement paramTypeElement, P paramP) {
/* 193 */     return defaultAction(paramTypeElement, paramP);
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
/*     */   public R visitTypeAsInterface(TypeElement paramTypeElement, P paramP) {
/* 205 */     return defaultAction(paramTypeElement, paramP);
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
/*     */   public R visitVariable(VariableElement paramVariableElement, P paramP) {
/* 220 */     ElementKind elementKind = paramVariableElement.getKind();
/* 221 */     switch (elementKind) {
/*     */       case ENUM_CONSTANT:
/* 223 */         return visitVariableAsEnumConstant(paramVariableElement, paramP);
/*     */       
/*     */       case EXCEPTION_PARAMETER:
/* 226 */         return visitVariableAsExceptionParameter(paramVariableElement, paramP);
/*     */       
/*     */       case FIELD:
/* 229 */         return visitVariableAsField(paramVariableElement, paramP);
/*     */       
/*     */       case LOCAL_VARIABLE:
/* 232 */         return visitVariableAsLocalVariable(paramVariableElement, paramP);
/*     */       
/*     */       case PARAMETER:
/* 235 */         return visitVariableAsParameter(paramVariableElement, paramP);
/*     */       
/*     */       case RESOURCE_VARIABLE:
/* 238 */         return visitVariableAsResourceVariable(paramVariableElement, paramP);
/*     */     } 
/*     */     
/* 241 */     throw new AssertionError("Bad kind " + elementKind + " for VariableElement" + paramVariableElement);
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
/*     */   public R visitVariableAsEnumConstant(VariableElement paramVariableElement, P paramP) {
/* 254 */     return defaultAction(paramVariableElement, paramP);
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
/*     */   public R visitVariableAsExceptionParameter(VariableElement paramVariableElement, P paramP) {
/* 266 */     return defaultAction(paramVariableElement, paramP);
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
/*     */   public R visitVariableAsField(VariableElement paramVariableElement, P paramP) {
/* 278 */     return defaultAction(paramVariableElement, paramP);
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
/*     */   public R visitVariableAsLocalVariable(VariableElement paramVariableElement, P paramP) {
/* 290 */     return defaultAction(paramVariableElement, paramP);
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
/*     */   public R visitVariableAsParameter(VariableElement paramVariableElement, P paramP) {
/* 302 */     return defaultAction(paramVariableElement, paramP);
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
/*     */   public R visitVariableAsResourceVariable(VariableElement paramVariableElement, P paramP) {
/* 316 */     return visitUnknown(paramVariableElement, paramP);
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
/*     */   public R visitExecutable(ExecutableElement paramExecutableElement, P paramP) {
/* 331 */     ElementKind elementKind = paramExecutableElement.getKind();
/* 332 */     switch (elementKind) {
/*     */       case CONSTRUCTOR:
/* 334 */         return visitExecutableAsConstructor(paramExecutableElement, paramP);
/*     */       
/*     */       case INSTANCE_INIT:
/* 337 */         return visitExecutableAsInstanceInit(paramExecutableElement, paramP);
/*     */       
/*     */       case METHOD:
/* 340 */         return visitExecutableAsMethod(paramExecutableElement, paramP);
/*     */       
/*     */       case STATIC_INIT:
/* 343 */         return visitExecutableAsStaticInit(paramExecutableElement, paramP);
/*     */     } 
/*     */     
/* 346 */     throw new AssertionError("Bad kind " + elementKind + " for ExecutableElement" + paramExecutableElement);
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
/*     */   public R visitExecutableAsConstructor(ExecutableElement paramExecutableElement, P paramP) {
/* 359 */     return defaultAction(paramExecutableElement, paramP);
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
/*     */   public R visitExecutableAsInstanceInit(ExecutableElement paramExecutableElement, P paramP) {
/* 371 */     return defaultAction(paramExecutableElement, paramP);
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
/*     */   public R visitExecutableAsMethod(ExecutableElement paramExecutableElement, P paramP) {
/* 383 */     return defaultAction(paramExecutableElement, paramP);
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
/*     */   public R visitExecutableAsStaticInit(ExecutableElement paramExecutableElement, P paramP) {
/* 395 */     return defaultAction(paramExecutableElement, paramP);
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
/*     */   public R visitTypeParameter(TypeParameterElement paramTypeParameterElement, P paramP) {
/* 410 */     assert paramTypeParameterElement.getKind() == ElementKind.TYPE_PARAMETER : "Bad kind on TypeParameterElement";
/* 411 */     return defaultAction(paramTypeParameterElement, paramP);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/util/ElementKindVisitor6.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */