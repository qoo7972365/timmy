/*     */ package javax.lang.model.type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum TypeKind
/*     */ {
/*  46 */   BOOLEAN,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   BYTE,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   SHORT,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   INT,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   LONG,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   CHAR,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   FLOAT,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   DOUBLE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   VOID,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   NONE,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   NULL,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   ARRAY,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   DECLARED,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   ERROR,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   TYPEVAR,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   WILDCARD,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   PACKAGE,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   EXECUTABLE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   OTHER,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   UNION,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   INTERSECTION;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPrimitive() {
/* 162 */     switch (this) {
/*     */       case BOOLEAN:
/*     */       case BYTE:
/*     */       case SHORT:
/*     */       case INT:
/*     */       case LONG:
/*     */       case CHAR:
/*     */       case FLOAT:
/*     */       case DOUBLE:
/* 171 */         return true;
/*     */     } 
/*     */     
/* 174 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/type/TypeKind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */