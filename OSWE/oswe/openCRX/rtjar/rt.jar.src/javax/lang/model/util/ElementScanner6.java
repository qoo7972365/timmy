/*     */ package javax.lang.model.util;
/*     */ 
/*     */ import javax.annotation.processing.SupportedSourceVersion;
/*     */ import javax.lang.model.SourceVersion;
/*     */ import javax.lang.model.element.Element;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @SupportedSourceVersion(SourceVersion.RELEASE_6)
/*     */ public class ElementScanner6<R, P>
/*     */   extends AbstractElementVisitor6<R, P>
/*     */ {
/*     */   protected final R DEFAULT_VALUE;
/*     */   
/*     */   protected ElementScanner6() {
/* 107 */     this.DEFAULT_VALUE = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ElementScanner6(R paramR) {
/* 117 */     this.DEFAULT_VALUE = paramR;
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
/*     */   public final R scan(Iterable<? extends Element> paramIterable, P paramP) {
/* 131 */     R r = this.DEFAULT_VALUE;
/* 132 */     for (Element element : paramIterable)
/* 133 */       r = scan(element, paramP); 
/* 134 */     return r;
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
/*     */   public R scan(Element paramElement, P paramP) {
/* 146 */     return paramElement.accept(this, paramP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final R scan(Element paramElement) {
/* 156 */     return scan(paramElement, (P)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R visitPackage(PackageElement paramPackageElement, P paramP) {
/* 167 */     return scan(paramPackageElement.getEnclosedElements(), paramP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R visitType(TypeElement paramTypeElement, P paramP) {
/* 178 */     return scan(paramTypeElement.getEnclosedElements(), paramP);
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
/* 193 */     if (paramVariableElement.getKind() != ElementKind.RESOURCE_VARIABLE) {
/* 194 */       return scan(paramVariableElement.getEnclosedElements(), paramP);
/*     */     }
/* 196 */     return visitUnknown(paramVariableElement, paramP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R visitExecutable(ExecutableElement paramExecutableElement, P paramP) {
/* 207 */     return scan((Iterable)paramExecutableElement.getParameters(), paramP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R visitTypeParameter(TypeParameterElement paramTypeParameterElement, P paramP) {
/* 218 */     return scan(paramTypeParameterElement.getEnclosedElements(), paramP);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/util/ElementScanner6.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */