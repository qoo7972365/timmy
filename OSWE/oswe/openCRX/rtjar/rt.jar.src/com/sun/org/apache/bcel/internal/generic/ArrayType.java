/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ArrayType
/*     */   extends ReferenceType
/*     */ {
/*     */   private int dimensions;
/*     */   private Type basic_type;
/*     */   
/*     */   public ArrayType(byte type, int dimensions) {
/*  77 */     this(BasicType.getType(type), dimensions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayType(String class_name, int dimensions) {
/*  86 */     this(new ObjectType(class_name), dimensions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayType(Type type, int dimensions) {
/*  95 */     super((byte)13, "<dummy>");
/*     */     ArrayType array;
/*  97 */     if (dimensions < 1 || dimensions > 255) {
/*  98 */       throw new ClassGenException("Invalid number of dimensions: " + dimensions);
/*     */     }
/* 100 */     switch (type.getType()) {
/*     */       case 13:
/* 102 */         array = (ArrayType)type;
/* 103 */         this.dimensions = dimensions + array.dimensions;
/* 104 */         this.basic_type = array.basic_type;
/*     */         break;
/*     */       
/*     */       case 12:
/* 108 */         throw new ClassGenException("Invalid type: void[]");
/*     */       
/*     */       default:
/* 111 */         this.dimensions = dimensions;
/* 112 */         this.basic_type = type;
/*     */         break;
/*     */     } 
/*     */     
/* 116 */     StringBuffer buf = new StringBuffer();
/* 117 */     for (int i = 0; i < this.dimensions; i++) {
/* 118 */       buf.append('[');
/*     */     }
/* 120 */     buf.append(this.basic_type.getSignature());
/*     */     
/* 122 */     this.signature = buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getBasicType() {
/* 129 */     return this.basic_type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getElementType() {
/* 136 */     if (this.dimensions == 1) {
/* 137 */       return this.basic_type;
/*     */     }
/* 139 */     return new ArrayType(this.basic_type, this.dimensions - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDimensions() {
/* 144 */     return this.dimensions;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 148 */     return this.basic_type.hashCode() ^ this.dimensions;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object type) {
/* 153 */     if (type instanceof ArrayType) {
/* 154 */       ArrayType array = (ArrayType)type;
/* 155 */       return (array.dimensions == this.dimensions && array.basic_type.equals(this.basic_type));
/*     */     } 
/* 157 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/ArrayType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */