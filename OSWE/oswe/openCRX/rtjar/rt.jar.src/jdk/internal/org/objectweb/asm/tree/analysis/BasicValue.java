/*     */ package jdk.internal.org.objectweb.asm.tree.analysis;
/*     */ 
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicValue
/*     */   implements Value
/*     */ {
/*  72 */   public static final BasicValue UNINITIALIZED_VALUE = new BasicValue(null);
/*     */   
/*  74 */   public static final BasicValue INT_VALUE = new BasicValue(Type.INT_TYPE);
/*     */   
/*  76 */   public static final BasicValue FLOAT_VALUE = new BasicValue(Type.FLOAT_TYPE);
/*     */   
/*  78 */   public static final BasicValue LONG_VALUE = new BasicValue(Type.LONG_TYPE);
/*     */   
/*  80 */   public static final BasicValue DOUBLE_VALUE = new BasicValue(Type.DOUBLE_TYPE);
/*     */ 
/*     */   
/*  83 */   public static final BasicValue REFERENCE_VALUE = new BasicValue(
/*  84 */       Type.getObjectType("java/lang/Object"));
/*     */   
/*  86 */   public static final BasicValue RETURNADDRESS_VALUE = new BasicValue(Type.VOID_TYPE);
/*     */   
/*     */   private final Type type;
/*     */ 
/*     */   
/*     */   public BasicValue(Type paramType) {
/*  92 */     this.type = paramType;
/*     */   }
/*     */   
/*     */   public Type getType() {
/*  96 */     return this.type;
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 100 */     return (this.type == Type.LONG_TYPE || this.type == Type.DOUBLE_TYPE) ? 2 : 1;
/*     */   }
/*     */   
/*     */   public boolean isReference() {
/* 104 */     return (this.type != null && (this.type
/* 105 */       .getSort() == 10 || this.type.getSort() == 9));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 110 */     if (paramObject == this)
/* 111 */       return true; 
/* 112 */     if (paramObject instanceof BasicValue) {
/* 113 */       if (this.type == null) {
/* 114 */         return (((BasicValue)paramObject).type == null);
/*     */       }
/* 116 */       return this.type.equals(((BasicValue)paramObject).type);
/*     */     } 
/*     */     
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     return (this.type == null) ? 0 : this.type.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 130 */     if (this == UNINITIALIZED_VALUE)
/* 131 */       return "."; 
/* 132 */     if (this == RETURNADDRESS_VALUE)
/* 133 */       return "A"; 
/* 134 */     if (this == REFERENCE_VALUE) {
/* 135 */       return "R";
/*     */     }
/* 137 */     return this.type.getDescriptor();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/analysis/BasicValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */