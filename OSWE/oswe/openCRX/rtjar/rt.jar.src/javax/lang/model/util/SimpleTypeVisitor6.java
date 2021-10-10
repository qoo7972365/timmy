/*     */ package javax.lang.model.util;
/*     */ 
/*     */ import javax.annotation.processing.SupportedSourceVersion;
/*     */ import javax.lang.model.SourceVersion;
/*     */ import javax.lang.model.type.ArrayType;
/*     */ import javax.lang.model.type.DeclaredType;
/*     */ import javax.lang.model.type.ErrorType;
/*     */ import javax.lang.model.type.ExecutableType;
/*     */ import javax.lang.model.type.NoType;
/*     */ import javax.lang.model.type.NullType;
/*     */ import javax.lang.model.type.PrimitiveType;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import javax.lang.model.type.TypeVariable;
/*     */ import javax.lang.model.type.WildcardType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class SimpleTypeVisitor6<R, P>
/*     */   extends AbstractTypeVisitor6<R, P>
/*     */ {
/*     */   protected final R DEFAULT_VALUE;
/*     */   
/*     */   protected SimpleTypeVisitor6() {
/* 104 */     this.DEFAULT_VALUE = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleTypeVisitor6(R paramR) {
/* 114 */     this.DEFAULT_VALUE = paramR;
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
/*     */   protected R defaultAction(TypeMirror paramTypeMirror, P paramP) {
/* 127 */     return this.DEFAULT_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R visitPrimitive(PrimitiveType paramPrimitiveType, P paramP) {
/* 138 */     return defaultAction(paramPrimitiveType, paramP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R visitNull(NullType paramNullType, P paramP) {
/* 149 */     return defaultAction(paramNullType, paramP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R visitArray(ArrayType paramArrayType, P paramP) {
/* 160 */     return defaultAction(paramArrayType, paramP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R visitDeclared(DeclaredType paramDeclaredType, P paramP) {
/* 171 */     return defaultAction(paramDeclaredType, paramP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R visitError(ErrorType paramErrorType, P paramP) {
/* 182 */     return defaultAction(paramErrorType, paramP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R visitTypeVariable(TypeVariable paramTypeVariable, P paramP) {
/* 193 */     return defaultAction(paramTypeVariable, paramP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R visitWildcard(WildcardType paramWildcardType, P paramP) {
/* 204 */     return defaultAction(paramWildcardType, paramP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R visitExecutable(ExecutableType paramExecutableType, P paramP) {
/* 215 */     return defaultAction(paramExecutableType, paramP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R visitNoType(NoType paramNoType, P paramP) {
/* 226 */     return defaultAction(paramNoType, paramP);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/util/SimpleTypeVisitor6.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */