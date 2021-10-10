/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.Constants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BasicType
/*     */   extends Type
/*     */ {
/*     */   BasicType(byte type) {
/*  75 */     super(type, Constants.SHORT_TYPE_NAMES[type]);
/*     */     
/*  77 */     if (type < 4 || type > 12)
/*  78 */       throw new ClassGenException("Invalid type: " + type); 
/*     */   }
/*     */   
/*     */   public static final BasicType getType(byte type) {
/*  82 */     switch (type) { case 12:
/*  83 */         return VOID;
/*  84 */       case 4: return BOOLEAN;
/*  85 */       case 8: return BYTE;
/*  86 */       case 9: return SHORT;
/*  87 */       case 5: return CHAR;
/*  88 */       case 10: return INT;
/*  89 */       case 11: return LONG;
/*  90 */       case 7: return DOUBLE;
/*  91 */       case 6: return FLOAT; }
/*     */ 
/*     */     
/*  94 */     throw new ClassGenException("Invalid type: " + type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object type) {
/* 102 */     return (type instanceof BasicType) ? ((((BasicType)type).type == this.type)) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 108 */     return this.type;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/BasicType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */