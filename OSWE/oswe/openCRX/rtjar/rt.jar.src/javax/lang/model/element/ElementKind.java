/*     */ package javax.lang.model.element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ElementKind
/*     */ {
/*  44 */   PACKAGE,
/*     */ 
/*     */ 
/*     */   
/*  48 */   ENUM,
/*     */   
/*  50 */   CLASS,
/*     */   
/*  52 */   ANNOTATION_TYPE,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   INTERFACE,
/*     */ 
/*     */ 
/*     */   
/*  61 */   ENUM_CONSTANT,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   FIELD,
/*     */   
/*  68 */   PARAMETER,
/*     */   
/*  70 */   LOCAL_VARIABLE,
/*     */   
/*  72 */   EXCEPTION_PARAMETER,
/*     */ 
/*     */ 
/*     */   
/*  76 */   METHOD,
/*     */   
/*  78 */   CONSTRUCTOR,
/*     */   
/*  80 */   STATIC_INIT,
/*     */   
/*  82 */   INSTANCE_INIT,
/*     */ 
/*     */   
/*  85 */   TYPE_PARAMETER,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   OTHER,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   RESOURCE_VARIABLE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClass() {
/* 107 */     return (this == CLASS || this == ENUM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInterface() {
/* 117 */     return (this == INTERFACE || this == ANNOTATION_TYPE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isField() {
/* 127 */     return (this == FIELD || this == ENUM_CONSTANT);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/element/ElementKind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */