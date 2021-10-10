/*     */ package com.sun.xml.internal.ws.org.objectweb.asm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   Item(int index) {
/* 146 */     this.index = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Item(int index, Item i) {
/* 156 */     this.index = index;
/* 157 */     this.type = i.type;
/* 158 */     this.intVal = i.intVal;
/* 159 */     this.longVal = i.longVal;
/* 160 */     this.strVal1 = i.strVal1;
/* 161 */     this.strVal2 = i.strVal2;
/* 162 */     this.strVal3 = i.strVal3;
/* 163 */     this.hashCode = i.hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(int intVal) {
/* 172 */     this.type = 3;
/* 173 */     this.intVal = intVal;
/* 174 */     this.hashCode = Integer.MAX_VALUE & this.type + intVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(long longVal) {
/* 183 */     this.type = 5;
/* 184 */     this.longVal = longVal;
/* 185 */     this.hashCode = Integer.MAX_VALUE & this.type + (int)longVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(float floatVal) {
/* 194 */     this.type = 4;
/* 195 */     this.intVal = Float.floatToRawIntBits(floatVal);
/* 196 */     this.hashCode = Integer.MAX_VALUE & this.type + (int)floatVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(double doubleVal) {
/* 205 */     this.type = 6;
/* 206 */     this.longVal = Double.doubleToRawLongBits(doubleVal);
/* 207 */     this.hashCode = Integer.MAX_VALUE & this.type + (int)doubleVal;
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
/*     */   void set(int type, String strVal1, String strVal2, String strVal3) {
/* 224 */     this.type = type;
/* 225 */     this.strVal1 = strVal1;
/* 226 */     this.strVal2 = strVal2;
/* 227 */     this.strVal3 = strVal3;
/* 228 */     switch (type) {
/*     */       case 1:
/*     */       case 7:
/*     */       case 8:
/*     */       case 13:
/* 233 */         this.hashCode = Integer.MAX_VALUE & type + strVal1.hashCode();
/*     */         return;
/*     */       case 12:
/* 236 */         this
/* 237 */           .hashCode = Integer.MAX_VALUE & type + strVal1.hashCode() * strVal2.hashCode();
/*     */         return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 243 */     this
/* 244 */       .hashCode = Integer.MAX_VALUE & type + strVal1.hashCode() * strVal2.hashCode() * strVal3.hashCode();
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
/*     */   boolean isEqualTo(Item i) {
/* 256 */     if (i.type == this.type) {
/* 257 */       switch (this.type) {
/*     */         case 3:
/*     */         case 4:
/* 260 */           return (i.intVal == this.intVal);
/*     */         case 5:
/*     */         case 6:
/*     */         case 15:
/* 264 */           return (i.longVal == this.longVal);
/*     */         case 1:
/*     */         case 7:
/*     */         case 8:
/*     */         case 13:
/* 269 */           return i.strVal1.equals(this.strVal1);
/*     */         case 14:
/* 271 */           return (i.intVal == this.intVal && i.strVal1.equals(this.strVal1));
/*     */         case 12:
/* 273 */           return (i.strVal1.equals(this.strVal1) && i.strVal2
/* 274 */             .equals(this.strVal2));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 279 */       return (i.strVal1.equals(this.strVal1) && i.strVal2
/* 280 */         .equals(this.strVal2) && i.strVal3
/* 281 */         .equals(this.strVal3));
/*     */     } 
/*     */     
/* 284 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/org/objectweb/asm/Item.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */