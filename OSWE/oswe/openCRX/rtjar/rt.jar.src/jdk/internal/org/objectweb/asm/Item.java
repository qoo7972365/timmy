/*     */ package jdk.internal.org.objectweb.asm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Item
/*     */ {
/*     */   int index;
/*     */   int type;
/*     */   int intVal;
/*     */   long longVal;
/*     */   String strVal1;
/*     */   String strVal2;
/*     */   String strVal3;
/*     */   int hashCode;
/*     */   Item next;
/*     */   
/*     */   Item() {}
/*     */   
/*     */   Item(int paramInt) {
/* 151 */     this.index = paramInt;
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
/*     */   Item(int paramInt, Item paramItem) {
/* 163 */     this.index = paramInt;
/* 164 */     this.type = paramItem.type;
/* 165 */     this.intVal = paramItem.intVal;
/* 166 */     this.longVal = paramItem.longVal;
/* 167 */     this.strVal1 = paramItem.strVal1;
/* 168 */     this.strVal2 = paramItem.strVal2;
/* 169 */     this.strVal3 = paramItem.strVal3;
/* 170 */     this.hashCode = paramItem.hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(int paramInt) {
/* 180 */     this.type = 3;
/* 181 */     this.intVal = paramInt;
/* 182 */     this.hashCode = Integer.MAX_VALUE & this.type + paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(long paramLong) {
/* 192 */     this.type = 5;
/* 193 */     this.longVal = paramLong;
/* 194 */     this.hashCode = Integer.MAX_VALUE & this.type + (int)paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(float paramFloat) {
/* 204 */     this.type = 4;
/* 205 */     this.intVal = Float.floatToRawIntBits(paramFloat);
/* 206 */     this.hashCode = Integer.MAX_VALUE & this.type + (int)paramFloat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(double paramDouble) {
/* 216 */     this.type = 6;
/* 217 */     this.longVal = Double.doubleToRawLongBits(paramDouble);
/* 218 */     this.hashCode = Integer.MAX_VALUE & this.type + (int)paramDouble;
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
/*     */ 
/*     */   
/*     */   void set(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 235 */     this.type = paramInt;
/* 236 */     this.strVal1 = paramString1;
/* 237 */     this.strVal2 = paramString2;
/* 238 */     this.strVal3 = paramString3;
/* 239 */     switch (paramInt) {
/*     */       case 7:
/* 241 */         this.intVal = 0;
/*     */       case 1:
/*     */       case 8:
/*     */       case 16:
/*     */       case 30:
/* 246 */         this.hashCode = Integer.MAX_VALUE & paramInt + paramString1.hashCode();
/*     */         return;
/*     */       case 12:
/* 249 */         this
/* 250 */           .hashCode = Integer.MAX_VALUE & paramInt + paramString1.hashCode() * paramString2.hashCode();
/*     */         return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     this
/* 259 */       .hashCode = Integer.MAX_VALUE & paramInt + paramString1.hashCode() * paramString2.hashCode() * paramString3.hashCode();
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
/*     */   void set(String paramString1, String paramString2, int paramInt) {
/* 274 */     this.type = 18;
/* 275 */     this.longVal = paramInt;
/* 276 */     this.strVal1 = paramString1;
/* 277 */     this.strVal2 = paramString2;
/* 278 */     this
/* 279 */       .hashCode = Integer.MAX_VALUE & 18 + paramInt * this.strVal1.hashCode() * this.strVal2.hashCode();
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
/*     */   void set(int paramInt1, int paramInt2) {
/* 293 */     this.type = 33;
/* 294 */     this.intVal = paramInt1;
/* 295 */     this.hashCode = paramInt2;
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
/*     */   boolean isEqualTo(Item paramItem) {
/* 309 */     switch (this.type) {
/*     */       case 1:
/*     */       case 7:
/*     */       case 8:
/*     */       case 16:
/*     */       case 30:
/* 315 */         return paramItem.strVal1.equals(this.strVal1);
/*     */       case 5:
/*     */       case 6:
/*     */       case 32:
/* 319 */         return (paramItem.longVal == this.longVal);
/*     */       case 3:
/*     */       case 4:
/* 322 */         return (paramItem.intVal == this.intVal);
/*     */       case 31:
/* 324 */         return (paramItem.intVal == this.intVal && paramItem.strVal1.equals(this.strVal1));
/*     */       case 12:
/* 326 */         return (paramItem.strVal1.equals(this.strVal1) && paramItem.strVal2.equals(this.strVal2));
/*     */       case 18:
/* 328 */         return (paramItem.longVal == this.longVal && paramItem.strVal1.equals(this.strVal1) && paramItem.strVal2
/* 329 */           .equals(this.strVal2));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     return (paramItem.strVal1.equals(this.strVal1) && paramItem.strVal2.equals(this.strVal2) && paramItem.strVal3
/* 337 */       .equals(this.strVal3));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/Item.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */