/*     */ package javax.lang.model.util;
/*     */ 
/*     */ import javax.annotation.processing.SupportedSourceVersion;
/*     */ import javax.lang.model.SourceVersion;
/*     */ import javax.lang.model.type.NoType;
/*     */ import javax.lang.model.type.PrimitiveType;
/*     */ import javax.lang.model.type.TypeKind;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class TypeKindVisitor6<R, P>
/*     */   extends SimpleTypeVisitor6<R, P>
/*     */ {
/*     */   protected TypeKindVisitor6() {
/*  96 */     super(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TypeKindVisitor6(R paramR) {
/* 107 */     super(paramR);
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
/*     */   public R visitPrimitive(PrimitiveType paramPrimitiveType, P paramP) {
/* 121 */     TypeKind typeKind = paramPrimitiveType.getKind();
/* 122 */     switch (typeKind) {
/*     */       case BOOLEAN:
/* 124 */         return visitPrimitiveAsBoolean(paramPrimitiveType, paramP);
/*     */       
/*     */       case BYTE:
/* 127 */         return visitPrimitiveAsByte(paramPrimitiveType, paramP);
/*     */       
/*     */       case SHORT:
/* 130 */         return visitPrimitiveAsShort(paramPrimitiveType, paramP);
/*     */       
/*     */       case INT:
/* 133 */         return visitPrimitiveAsInt(paramPrimitiveType, paramP);
/*     */       
/*     */       case LONG:
/* 136 */         return visitPrimitiveAsLong(paramPrimitiveType, paramP);
/*     */       
/*     */       case CHAR:
/* 139 */         return visitPrimitiveAsChar(paramPrimitiveType, paramP);
/*     */       
/*     */       case FLOAT:
/* 142 */         return visitPrimitiveAsFloat(paramPrimitiveType, paramP);
/*     */       
/*     */       case DOUBLE:
/* 145 */         return visitPrimitiveAsDouble(paramPrimitiveType, paramP);
/*     */     } 
/*     */     
/* 148 */     throw new AssertionError("Bad kind " + typeKind + " for PrimitiveType" + paramPrimitiveType);
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
/*     */   public R visitPrimitiveAsBoolean(PrimitiveType paramPrimitiveType, P paramP) {
/* 161 */     return defaultAction(paramPrimitiveType, paramP);
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
/*     */   public R visitPrimitiveAsByte(PrimitiveType paramPrimitiveType, P paramP) {
/* 173 */     return defaultAction(paramPrimitiveType, paramP);
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
/*     */   public R visitPrimitiveAsShort(PrimitiveType paramPrimitiveType, P paramP) {
/* 185 */     return defaultAction(paramPrimitiveType, paramP);
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
/*     */   public R visitPrimitiveAsInt(PrimitiveType paramPrimitiveType, P paramP) {
/* 197 */     return defaultAction(paramPrimitiveType, paramP);
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
/*     */   public R visitPrimitiveAsLong(PrimitiveType paramPrimitiveType, P paramP) {
/* 209 */     return defaultAction(paramPrimitiveType, paramP);
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
/*     */   public R visitPrimitiveAsChar(PrimitiveType paramPrimitiveType, P paramP) {
/* 221 */     return defaultAction(paramPrimitiveType, paramP);
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
/*     */   public R visitPrimitiveAsFloat(PrimitiveType paramPrimitiveType, P paramP) {
/* 233 */     return defaultAction(paramPrimitiveType, paramP);
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
/*     */   public R visitPrimitiveAsDouble(PrimitiveType paramPrimitiveType, P paramP) {
/* 245 */     return defaultAction(paramPrimitiveType, paramP);
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
/*     */   public R visitNoType(NoType paramNoType, P paramP) {
/* 259 */     TypeKind typeKind = paramNoType.getKind();
/* 260 */     switch (typeKind) {
/*     */       case VOID:
/* 262 */         return visitNoTypeAsVoid(paramNoType, paramP);
/*     */       
/*     */       case PACKAGE:
/* 265 */         return visitNoTypeAsPackage(paramNoType, paramP);
/*     */       
/*     */       case NONE:
/* 268 */         return visitNoTypeAsNone(paramNoType, paramP);
/*     */     } 
/*     */     
/* 271 */     throw new AssertionError("Bad kind " + typeKind + " for NoType" + paramNoType);
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
/*     */   public R visitNoTypeAsVoid(NoType paramNoType, P paramP) {
/* 284 */     return defaultAction(paramNoType, paramP);
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
/*     */   public R visitNoTypeAsPackage(NoType paramNoType, P paramP) {
/* 296 */     return defaultAction(paramNoType, paramP);
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
/*     */   public R visitNoTypeAsNone(NoType paramNoType, P paramP) {
/* 308 */     return defaultAction(paramNoType, paramP);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/util/TypeKindVisitor6.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */